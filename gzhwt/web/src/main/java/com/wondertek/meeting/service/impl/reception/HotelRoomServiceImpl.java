package com.wondertek.meeting.service.impl.reception;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.dao.reception.HotelRoomDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.reception.HotelRoom;
import com.wondertek.meeting.model.reception.HotelRoomType;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.impl.BaseServiceImpl;
import com.wondertek.meeting.service.reception.HotelRoomService;
import com.wondertek.meeting.service.reception.HotelRoomTypeService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 酒店客房管理
 * 
 * @author 金祝华
 */
public class HotelRoomServiceImpl extends BaseServiceImpl<HotelRoom, Long> implements HotelRoomService {

	private HotelRoomDao hotelRoomDao;

	private HotelRoomTypeService hotelRoomTypeService;// 客房类型

	private MeetingMemberService meetingMemberService;// 参会人员
	private int i = 1;

	public HotelRoomDao getHotelRoomDao() {
		return hotelRoomDao;
	}

	public void setHotelRoomDao(HotelRoomDao hotelRoomDao) {
		super.setBaseDao(hotelRoomDao);
		this.hotelRoomDao = hotelRoomDao;
	}

	/**
	 * 查询会议下客房列表
	 * 
	 * @param meetingId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<HotelRoom> findPager(HotelRoom hotelRoom, Long meetingId, int currentPage, int pageSize)
			throws ServiceException {
		StringBuilder hql = new StringBuilder();
		hql.append("select h from HotelRoom h where h.meeting.id = :meetingId and h.state != 0");

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);

		if (hotelRoom != null && StringUtil.isNotEmpty(hotelRoom.getRoomNo())) {
			hql.append(" and h.roomNo like '%'||:roomNo||'%'");
			properties.put("roomNo", hotelRoom.getRoomNo());
		}

		try {
			return hotelRoomDao.findPager(hql.toString(), currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find pager error!";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	/**
	 * 查询该会议下已经分配了客房的参会人员列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<Long> queryAssignedUserList(Long meetingId) {
		return hotelRoomDao.queryAssignedUserList(meetingId);
	}

	/**
	 * 查询会议下所有客房
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<HotelRoom> list(Long meetingId) {
		return hotelRoomDao.list(meetingId);
	}

	public HotelRoom findById(Long roomId) throws ServiceException {
		return hotelRoomDao.findById(roomId);
	}

	/**
	 * 删除客房
	 * 
	 * @param meetingId
	 * @param roomId
	 * @return 0:失败，1：成功
	 */
	public String delete(Long meetingId, Long roomId) throws ServiceException {
		HotelRoom oldHotelRoom = null;
		try {
			oldHotelRoom = this.findById(roomId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}

		if (oldHotelRoom == null) {
			return RetCode.SUCCESS;
		}

		oldHotelRoom.setState(SysConstants.DB_STATUS_INVALID);// 状态：无效
		oldHotelRoom.setModifyTime(new Date());

		// 删除参会人员与客房的关联关系
		Set<User> users = oldHotelRoom.getUsers();
		if (users != null && users.size() > 0) {
			Iterator<User> itr = users.iterator();
			while (itr.hasNext()) {
				User user = itr.next();
				updateMemberRoomNo(meetingId, user.getId(), null);
			}
		}

		// 删除客房与用户的关联
		oldHotelRoom.setUsers(null);

		try {
			this.modify(oldHotelRoom);// 逻辑删除
		} catch (ServiceException e) {
			log.error("删除客房失败", e);
			throw new ServiceException("删除失败。");
		}

		return RetCode.FAIL;
	}

	/**
	 * 将参会人员加入房间
	 * 
	 * @param fromRoomId
	 * @param toRoomId
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public String addUserToRoom(Long fromRoomId, Long toRoomId, Long userId) throws ServiceException {
		HotelRoom toHotelRoom = null;
		try {
			toHotelRoom = this.findById(toRoomId);
		} catch (ServiceException e) {
			e.printStackTrace();
			return RetCode.FAIL;
		}

		if (toHotelRoom == null) {
			throw new ServiceException("对不起，该房间已被删除，不能移入。");
		}

		Set<User> users = toHotelRoom.getUsers();

		// 移入的房间已满，不能加入，返回。
		if (users != null && users.size() >= toHotelRoom.getHotelRoomType().getCapability()) {
			throw new ServiceException("对不起，该房间已满，不能移入。");
		}

		// 如果原房间不为空，则从原房间移出。
		if (fromRoomId != null) {
			String result = removeUserFromRoom(userId, fromRoomId);

			// 没有从原房间移出成功，提示失败
			if (!result.equals(RetCode.SUCCESS)) {
				return RetCode.FAIL;
			}
		}

		if (users == null) {
			users = new HashSet<User>();
		}

		User user = new User();
		user.setId(userId);
		users.add(user);

		try {
			// 保存入库
			this.modify(toHotelRoom);
		} catch (Throwable e) {
			log.error(e.toString());
			return RetCode.FAIL;
		}

		// 更新会员成员的房间
		updateMemberRoomNo(toHotelRoom.getMeeting().getId(), userId, toHotelRoom);

		return RetCode.SUCCESS;
	}

	/**
	 * 将住客移出房间
	 * 
	 * @param userId
	 * @param roomId
	 * @return
	 */
	public String removeUserFromRoom(Long userId, Long fromRoomId) throws ServiceException {
		HotelRoom fromHotelRoom = null;
		try {
			fromHotelRoom = this.findById(fromRoomId);
		} catch (ServiceException e) {
			e.printStackTrace();
			return RetCode.FAIL;
		}

		if (fromHotelRoom != null) {
			Set<User> users = fromHotelRoom.getUsers();
			if (users != null && users.size() > 0) {
				boolean changed = false;
				Iterator<User> itr = users.iterator();
				while (itr.hasNext()) {
					User user = itr.next();
					if (user.getId().equals(userId)) {
						itr.remove();
						changed = true;
						break;
					}
				}

				// 如果删除了用户，则保存
				if (changed) {
					try {
						this.modify(fromHotelRoom);
					} catch (Throwable e) {
						log.error(e.toString());
						return RetCode.FAIL;
					}

					// 更新会员成员的房间
					updateMemberRoomNo(fromHotelRoom.getMeeting().getId(), userId, null);
				}
			}
		}

		return RetCode.SUCCESS;
	}

	/**
	 * 智能分配客房<br/>
	 * 
	 * 客房分配只支持单人间和双人间。<br/>
	 * 1、对于VIP，优先分配单人间，如果没有单人间房型，则分配一个双人间（单人住）。<br/>
	 * 2、对于非VIP，如果有双人间房型，按照相同性别两人一间双人间，最后落单的一人住一间双人间，否则都分配单人间。
	 * 
	 * @return
	 */
	public String assignRooms(Long meetingId) throws ServiceException {
		// 查询参会人员列表
		List<MeetingMember> list = meetingMemberService.queryList(meetingId);

		if (list != null && list.size() > 0) {
			// 已经安排了房间的参会人员不再安排。
			List<Long> assignedUserList = this.queryAssignedUserList(meetingId);
			if (assignedUserList != null && assignedUserList.size() > 0) {
				Iterator<MeetingMember> itr = list.iterator();
				while (itr.hasNext()) {
					MeetingMember mm = itr.next();
					if (assignedUserList.contains(mm.getUserId())) {
						itr.remove();// 删除已经安排过了的
					}
				}
			}

			// 男性非vip列表
			List<MeetingMember> maleList = new ArrayList<MeetingMember>();
			// 女性非vip列表
			List<MeetingMember> femaleList = new ArrayList<MeetingMember>();

			// 查询出本会议下的客房房型列表
			List<HotelRoomType> hotelRoomTypeList = hotelRoomTypeService.findListByMeetingId(meetingId);
			if (hotelRoomTypeList == null) {// 这种情况应该不会发生
				log.error("智能分配客房时，没有客房房型列表");
				throw new ServiceException("对不起，没有客房房型，请先添加客房房型。");
			}

			for (MeetingMember member : list) {
				// vip
				if ("Y".equalsIgnoreCase(member.getVip())) {
					assignVip(meetingId, member, hotelRoomTypeList);
				} else {
					// 非vip 女性
					if (member.getUser().getGender() != null && member.getUser().getGender() == 1) {
						femaleList.add(member);
					} else {// 男性（性别没有填写等的，都默认为男性）
						maleList.add(member);
					}
				}
			}

			assignNotVip(meetingId, maleList, hotelRoomTypeList);
			assignNotVip(meetingId, femaleList, hotelRoomTypeList);
		}

		return RetCode.SUCCESS;
	}

	/**
	 * 对于VIP，优先分配单人间，如果没有单人间房型，则分配一个双人间（单人住）。
	 * 
	 * @param member
	 * @param hotelRoomTypeList
	 */
	private void assignVip(Long meetingId, MeetingMember member, List<HotelRoomType> hotelRoomTypeList)
			throws ServiceException {
		Meeting meeting = new Meeting();
		meeting.setId(meetingId);
		HotelRoom room = new HotelRoom();
		room.setMeeting(meeting);// 会议
		room.setState(SysConstants.DB_STATUS_VALID);
		room.setCreateTime(new Date());

		Set<User> users = new HashSet<User>();
		User user = new User();
		user.setId(member.getUserId());
		users.add(user);
		room.setUsers(users);

		// 有单人间类型，优先分配单人间，如果没有单人间，则分配一个双人间。
		for (HotelRoomType type : hotelRoomTypeList) {
			room.setHotelRoomType(type);
			if (type.getCapability() == 1) {
				break;
			}
		}

		// 插入到数据库
		doAdd(room);
	}

	/**
	 * 对于非VIP，如果有双人间房型，按照相同性别两人一间双人间，最后落单的一人住一间双人间，否则都分配单人间。
	 * 
	 * @param userList
	 * @param hotelRoomTypeList
	 */
	private void assignNotVip(Long meetingId, List<MeetingMember> userList, List<HotelRoomType> hotelRoomTypeList)
			throws ServiceException {
		if (userList == null || userList.size() == 0) {
			return;
		}

		// 计算是否有双人间房型
		boolean hasDoubleRoom = false;
		HotelRoomType roomType = null;
		for (HotelRoomType type : hotelRoomTypeList) {
			roomType = type;
			if (type.getCapability() == 2) {
				hasDoubleRoom = true;
				break;
			}
		}

		Meeting meeting = new Meeting();
		meeting.setId(meetingId);
		HotelRoom room = null;
		Set<User> users = null;
		MeetingMember member = null;
		for (int i = 0; i < userList.size(); i++) {
			member = userList.get(i);
			if (hasDoubleRoom) {// 有双人间
				if (i % 2 == 0) {// 奇数时新建房间
					room = new HotelRoom();
					room.setMeeting(meeting);// 会议
					room.setHotelRoomType(roomType);
					room.setState(SysConstants.DB_STATUS_VALID);
					room.setCreateTime(new Date());

					users = new HashSet<User>();
					room.setUsers(users);
					User user = new User();
					user.setId(member.getUserId());
					users.add(user);
					if (i == userList.size() - 1)// 最后落单的
					{
						// 插入数据库
						doAdd(room);
					}
				} else {
					User user = new User();
					user.setId(member.getUserId());
					users.add(user);

					// 插入数据库
					doAdd(room);
				}
			} else {// 单人间
				room = new HotelRoom();
				room.setHotelRoomType(roomType);
				room.setMeeting(meeting);// 会议
				room.setState(SysConstants.DB_STATUS_VALID);
				room.setCreateTime(new Date());

				users = new HashSet<User>();
				room.setUsers(users);
				User user = new User();
				user.setId(member.getUserId());
				users.add(user);

				// 插入数据库
				doAdd(room);
			}
		}
	}

	/**
	 * 新增房间入库
	 * 
	 * @param hotelRoom
	 */
	private void doAdd(HotelRoom hotelRoom) throws ServiceException {
		hotelRoom.setRoomNo(genRoomNo());
		// 插入数据库
		try {
			this.add(hotelRoom);
		} catch (ServiceException e) {
			log.error("添加房间失败", e);
		}

		// 更新参会人员住房信息
		Set<User> users = hotelRoom.getUsers();
		if (users != null && users.size() > 0) {
			Iterator<User> itr = users.iterator();

			while (itr.hasNext()) {
				User user = itr.next();
				updateMemberRoomNo(hotelRoom.getMeeting().getId(), user.getId(), hotelRoom);
			}
		}
	}

	private String genRoomNo() {
		i++;
		if (i > 9999) {
			i = 1;
		}

		return String.valueOf(i);
	}

	/**
	 * 更新参会人员住房
	 * 
	 * @param meetingId
	 * @param userId
	 * @param roomNo
	 */
	private void updateMemberRoomNo(Long meetingId, Long userId, HotelRoom room) throws ServiceException {
		MeetingMember meetingMember = meetingMemberService.selectById(userId, meetingId);
		meetingMember.setHotelRoom(room);
		try {
			meetingMemberService.saveOrUpdate(meetingMember);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("更新参会人员住房信息失败。");
		}
	}

	public HotelRoomTypeService getHotelRoomTypeService() {
		return hotelRoomTypeService;
	}

	public void setHotelRoomTypeService(HotelRoomTypeService hotelRoomTypeService) {
		this.hotelRoomTypeService = hotelRoomTypeService;
	}

	public MeetingMemberService getMeetingMemberService() {
		return meetingMemberService;
	}

	public void setMeetingMemberService(MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}
}
