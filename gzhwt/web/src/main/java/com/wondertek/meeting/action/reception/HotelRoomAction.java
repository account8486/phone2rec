package com.wondertek.meeting.action.reception;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.reception.HotelRoom;
import com.wondertek.meeting.model.reception.HotelRoomType;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.reception.HotelRoomService;
import com.wondertek.meeting.service.reception.HotelRoomTypeService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 酒店客房管理
 * 
 * @author 金祝华
 */
public class HotelRoomAction extends BaseAction {

	private static final long serialVersionUID = -5921627701176190252L;

	private HotelRoomService hotelRoomService;

	private HotelRoomTypeService hotelRoomTypeService;// 客房类型

	private MeetingMemberService meetingMemberService;// 参会人员

	private HotelRoom hotelRoom;

	private Long meetingId;// 保存前台传递过来的会议ID

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	public String list() {
		Pager<HotelRoom> pager = null;
		try {
			pager = hotelRoomService.findPager(hotelRoom, meetingId, currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("query list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", pager);

		// 查询出本会议下的客房类型列表
		List<HotelRoomType> hotelRoomTypeList = hotelRoomTypeService.findListByMeetingId(meetingId);
		this.getRequest().setAttribute("hotelRoomTypeList", hotelRoomTypeList);

		return SUCCESS;
	}

	/**
	 * 跳转到新增
	 * 
	 * @return
	 */
	public String goAdd() {

		// 查询出本会议下的客房类型列表
		List<HotelRoomType> hotelRoomTypeList = hotelRoomTypeService.findListByMeetingId(meetingId);
		this.getRequest().setAttribute("hotelRoomTypeList", hotelRoomTypeList);

		return SUCCESS;
	}

	/**
	 * 添加客房
	 * 
	 * @return
	 */
	public String add() {

		if (hotelRoom == null) {
			errMsg = "新增客房失败。";
			return INPUT;
		}

		Meeting meeting = new Meeting();
		meeting.setId(meetingId);
		hotelRoom.setMeeting(meeting);// 会议
		hotelRoom.setState(SysConstants.DB_STATUS_VALID);
		hotelRoom.setCreateTime(new Date());

		// 新增客房
		try {
			hotelRoomService.add(hotelRoom);
		} catch (ServiceException e) {
			log.error("add hotelRoom failed! ", e);
			errMsg = "对不起，新增客房失败，请稍后再试。";
			return INPUT;
		}

		errMsg = "添加客房成功。";
		return SUCCESS;
	}

	/**
	 * 跳转到修改
	 * 
	 * @return
	 */
	public String goUpdate() {

		String id = this.getParameter("id");

		Long idL = StringUtil.stringToLong(id);

		try {
			hotelRoom = hotelRoomService.findById(idL);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		// 查询出本会议下的客房类型列表
		List<HotelRoomType> hotelRoomTypeList = hotelRoomTypeService.findListByMeetingId(meetingId);
		this.getRequest().setAttribute("hotelRoomTypeList", hotelRoomTypeList);

		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String update() {
		if (hotelRoom == null) {
			errMsg = "对不起，修改客房失败！";
			return INPUT;
		}

		HotelRoom oldHotelRoom = null;
		try {
			oldHotelRoom = hotelRoomService.findById(hotelRoom.getId());
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}

		if (oldHotelRoom == null) {
			errMsg = "对不起，修改客房失败！";
			log.error("客房不存在，id=" + hotelRoom.getId());
			return INPUT;
		}

		// 设置页面可修改字段
		oldHotelRoom.setHotelRoomType(hotelRoom.getHotelRoomType());
		oldHotelRoom.setRoomNo(hotelRoom.getRoomNo());// 房间号
		oldHotelRoom.setFloor(hotelRoom.getFloor());// 楼层
		oldHotelRoom.setModifyTime(new Date());

		try {
			// 修改客房信息
			hotelRoomService.modify(oldHotelRoom);
		} catch (Throwable e) {
			errMsg = "对不起，修改客房失败！";
			return INPUT;
		}

		errMsg = "成功修改客房。";
		return SUCCESS;
	}

	/**
	 * 删除客房
	 * 
	 * @return
	 */
	public String del() {
		String id = this.getParameter("id");
		Long roomId = StringUtil.stringToLong(id);

		errMsg = "删除成功。";

		try {
			hotelRoomService.delete(meetingId, roomId);
		} catch (ServiceException e) {
			errMsg = e.getMessage();
		} catch (Exception ex) {
			errMsg = "删除失败。";
		}

		return SUCCESS;
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
	public String assignRooms() {

		errMsg = "已成功为参会人员分配客房。";

		try {
			hotelRoomService.assignRooms(meetingId);
		} catch (ServiceException e) {
			errMsg = e.getMessage();
		} catch (Exception ex) {
			errMsg = "对不起，分配客房失败，请稍后再试。";
		}

		return SUCCESS;
	}

	/**
	 * 住客列表
	 * 
	 * @return
	 */
	public String roomUserList() {
		List<HotelRoom> roomList = hotelRoomService.list(meetingId);
		this.getRequest().setAttribute("roomList", roomList);

		// 查询参会人员列表
		List<MeetingMember> list = meetingMemberService.queryList(meetingId);

		if (list != null && list.size() > 0) {
			// 已经安排了房间的参会人员。
			List<Long> assignedUserList = hotelRoomService.queryAssignedUserList(meetingId);

			// 未分配房间的参会人员列表
			if (assignedUserList != null && assignedUserList.size() > 0) {
				Iterator<MeetingMember> itr = list.iterator();
				while (itr.hasNext()) {
					MeetingMember mm = itr.next();
					if (assignedUserList.contains(mm.getUserId())) {
						itr.remove();// 删除已经安排过了的
					}
				}
			}
		}

		// 未分配房间的参会人员列表
		this.getRequest().setAttribute("unAssignedUserList", list);
		return SUCCESS;
	}

	/**
	 * 将住客移出房间
	 * 
	 * @return
	 */
	public String removeUserFromRoom() {
		String fromRoomId = this.getParameter("roomId");// 移出的房间id
		String userId = this.getParameter("userId");// 用户id

		String result = null;
		try {
			result = hotelRoomService.removeUserFromRoom(StringUtil.stringToLong(userId),
					StringUtil.stringToLong(fromRoomId));
		} catch (ServiceException e) {
			e.printStackTrace();
			result = RetCode.FAIL;
		}

		resultMap.put("retcode", result);
		if (result.equals(RetCode.SUCCESS)) {
			resultMap.put("retmsg", "移出房间成功。");
		} else {
			resultMap.put("retmsg", "移出房间失败。");
		}

		return SUCCESS;
	}

	/**
	 * 将参会人员加入房间
	 * 
	 * @return
	 */
	public String addUserToRoom() {
		String fromRoomId = this.getParameter("fromRoomId");
		String toRoomId = this.getParameter("toRoomId");
		String userId = this.getParameter("userId");

		String retCode = RetCode.SUCCESS;
		String retMsg = "操作成功。";

		try {
			hotelRoomService.addUserToRoom(StringUtil.stringToLong(fromRoomId), StringUtil.stringToLong(toRoomId),
					StringUtil.stringToLong(userId));
		} catch (ServiceException e) {
			retCode = RetCode.FAIL;
			retMsg = e.getMessage();
		} catch (Exception ex) {
			ex.printStackTrace();
			retCode = RetCode.FAIL;
			retMsg = "操作失败。";
		}

		resultMap.put("retcode", retCode);
		resultMap.put("retmsg", retMsg);
		return SUCCESS;
	}

	public HotelRoomService getHotelRoomService() {
		return hotelRoomService;
	}

	public void setHotelRoomService(HotelRoomService hotelRoomService) {
		this.hotelRoomService = hotelRoomService;
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

	public HotelRoom getHotelRoom() {
		return hotelRoom;
	}

	public void setHotelRoom(HotelRoom hotelRoom) {
		this.hotelRoom = hotelRoom;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
}
