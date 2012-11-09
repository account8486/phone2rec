package com.wondertek.meeting.action.reception;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.reception.DiningRoom;
import com.wondertek.meeting.model.reception.Hotel;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.reception.DiningRoomService;
import com.wondertek.meeting.service.reception.HotelService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 餐厅管理
 * 
 * @author 金祝华
 */
public class DiningRoomAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4969454703739755033L;

	private DiningRoomService diningRoomService;

	private HotelService hotelService;

	private MeetingService meetingService;

	private DiningRoom diningRoom;

	private Long meetingId;

	// 新增时处理类型，为"choose"时表示从已经存在的餐厅中进行选择
	private String actionType;

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	public String list() {
		Pager<DiningRoom> pager = null;
		try {
			pager = diningRoomService.findPager(diningRoom, meetingId, currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("query list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", pager);

		return SUCCESS;
	}

	/**
	 * 跳转到新增
	 * 
	 * @return
	 */
	public String goAdd() {

		// 查询出本会议下的酒店列表
		List<Hotel> hotelList = hotelService.findHotelListByMeeting(meetingId);
		this.getRequest().setAttribute("hotelList", hotelList);

		return SUCCESS;
	}

	/**
	 * 根据名称模糊查询已经存在的餐厅列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listByName() {
		List<DiningRoom> diningRoomList = diningRoomService.findListByName(diningRoom);

		return this.json2Resp(diningRoomList);
	}

	/**
	 * 根据ID查询餐厅信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findById() {
		diningRoom = diningRoomService.findById(diningRoom.getId());

		diningRoom.setMeetings(null);
		diningRoom.getHotel().setMeetings(null);

		return this.json2Resp(diningRoom);
	}

	/**
	 * 添加餐厅
	 * 
	 * @return
	 */
	public String add() {

		// 如果是添加已经存在的餐厅到会议，则只要将餐厅关联到当前会议即可。
		if (StringUtil.isNotEmpty(actionType) && "choose".equals(actionType)) {

			diningRoom = diningRoomService.findById(diningRoom.getId());

			// 判断该餐厅是否已经存在于该会议中。
			Set<Meeting> meetings = diningRoom.getMeetings();
			if (meetings != null && meetings.size() > 0) {
				Iterator<Meeting> itr = meetings.iterator();
				while (itr.hasNext()) {
					if (itr.next().getId().equals(meetingId)) {
						errMsg = "添加餐厅成功。";
						return SUCCESS;// 如果已经存在，直接返回成功。
					}
				}
			}

			Meeting meeting = new Meeting();
			meeting.setId(meetingId);
			diningRoom.getMeetings().add(meeting);
			try {
				diningRoomService.modify(diningRoom);
			} catch (ServiceException e) {
				log.error("关联餐厅到当前会议失败，{}", e.toString());
				errMsg = "新增餐厅失败。";
				return INPUT;
			}

			return SUCCESS;
		} else {
			if (diningRoom == null) {
				errMsg = "新增餐厅失败。";
				return INPUT;
			}

			Meeting meeting = new Meeting();
			meeting.setId(meetingId);
			Set<Meeting> meetings = new HashSet<Meeting>();
			meetings.add(meeting);
			diningRoom.setMeetings(meetings);// 会议
			diningRoom.setState(SysConstants.DB_STATUS_VALID);
			diningRoom.setCreateTime(new Date());

			// 新增餐厅
			try {
				diningRoomService.add(diningRoom);
			} catch (ServiceException e) {
				log.error("add diningRoom failed! ", e);
				errMsg = "对不起，新增餐厅失败，请稍后再试。";
				return INPUT;
			}

			errMsg = "添加餐厅成功。";
			return SUCCESS;
		}
	}

	/**
	 * 跳转到修改
	 * 
	 * @return
	 */
	public String goUpdate() {

		String id = this.getParameter("id");

		Long idL = StringUtil.stringToLong(id);

		diningRoom = diningRoomService.findById(idL);

		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String update() {
		if (diningRoom == null) {
			errMsg = "对不起，修改餐厅失败！";
			return INPUT;
		}

		DiningRoom oldDiningRoom = diningRoomService.findById(diningRoom.getId());

		if (oldDiningRoom == null) {
			errMsg = "对不起，修改餐厅失败！";
			log.error("餐厅不存在，id=" + diningRoom.getId());
			return INPUT;
		}

		oldDiningRoom.setBusinessHours(diningRoom.getBusinessHours());
		oldDiningRoom.setTableCnt(diningRoom.getTableCnt());
		oldDiningRoom.setCapability(diningRoom.getCapability());
		oldDiningRoom.setAdditionalInfo(diningRoom.getAdditionalInfo());
		oldDiningRoom.setFloor(diningRoom.getFloor());
		oldDiningRoom.setModifyTime(new Date());

		try {
			// 修改餐厅信息
			diningRoomService.modify(oldDiningRoom);
		} catch (Throwable e) {
			errMsg = "对不起，修改餐厅失败！";
			return INPUT;
		}

		errMsg = "成功修改餐厅。";
		return SUCCESS;
	}

	/**
	 * 删除餐厅 这里只删除会议与餐厅的映射关系
	 * 
	 * @return
	 */
	public String del() {
		String id = this.getParameter("id");
		Long idL = StringUtil.stringToLong(id);

		DiningRoom oldDiningRoom = diningRoomService.findById(idL);

		if (oldDiningRoom == null) {
			return SUCCESS;
		}

		// 从餐厅所属会议列表中移去当前会议
		Set<Meeting> meetings = oldDiningRoom.getMeetings();
		if (meetings == null || meetings.size() == 0) {
			return SUCCESS;
		}

		Iterator<Meeting> itr = meetings.iterator();
		while (itr.hasNext()) {
			Meeting meeting = itr.next();
			if (meetingId.equals(meeting.getId())) {
				itr.remove();
			}
		}
		oldDiningRoom.setModifyTime(new Date());

		// 删除
		try {
			diningRoomService.modify(oldDiningRoom);
		} catch (ServiceException e) {
			log.error("删除餐厅失败", e);
			errMsg = "删除失败。";
		}

		errMsg = "删除成功。";
		return SUCCESS;
	}

	public DiningRoomService getDiningRoomService() {
		return diningRoomService;
	}

	public void setDiningRoomService(DiningRoomService diningRoomService) {
		this.diningRoomService = diningRoomService;
	}

	public DiningRoom getDiningRoom() {
		return diningRoom;
	}

	public void setDiningRoom(DiningRoom diningRoom) {
		this.diningRoom = diningRoom;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public HotelService getHotelService() {
		return hotelService;
	}

	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}
