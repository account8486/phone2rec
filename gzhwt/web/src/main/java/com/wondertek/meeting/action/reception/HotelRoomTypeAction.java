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
import com.wondertek.meeting.model.reception.Hotel;
import com.wondertek.meeting.model.reception.HotelRoomType;
import com.wondertek.meeting.service.reception.HotelRoomTypeService;
import com.wondertek.meeting.service.reception.HotelService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 客房房型管理
 * 
 * @author 金祝华
 */
public class HotelRoomTypeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4969454703739755033L;

	private HotelRoomTypeService hotelRoomTypeService;

	private HotelService hotelService;// 酒店Service

	private HotelRoomType hotelRoomType;

	private Long meetingId;// 保存前台传递过来的会议ID

	// 新增时处理类型，为"choose"时表示从已经存在的客房房型中进行选择
	private String actionType;

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	public String list() {
		Pager<HotelRoomType> pager = null;
		try {
			pager = hotelRoomTypeService.findPager(hotelRoomType, meetingId, currentPage, pageSize);
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
	 * 根据名称模糊查询已经存在的客房房型列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listByName() {
		List<HotelRoomType> hotelRoomTypeList = hotelRoomTypeService.findListByName(hotelRoomType);

		return this.json2Resp(hotelRoomTypeList);
	}

	/**
	 * 根据ID查询客房房型信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findById() {
		hotelRoomType = hotelRoomTypeService.findById(hotelRoomType.getId());

		hotelRoomType.setMeetings(null);
		hotelRoomType.getHotel().setMeetings(null);

		return this.json2Resp(hotelRoomType);
	}

	/**
	 * 添加客房房型
	 * 
	 * @return
	 */
	public String add() {

		// 如果是添加已经存在的客房房型到会议，则只要将客房房型关联到当前会议即可。
		if (StringUtil.isNotEmpty(actionType) && "choose".equals(actionType)) {

			hotelRoomType = hotelRoomTypeService.findById(hotelRoomType.getId());

			// 判断该客房房型是否已经存在于该会议中。
			Set<Meeting> meetings = hotelRoomType.getMeetings();
			if (meetings != null && meetings.size() > 0) {
				Iterator<Meeting> itr = meetings.iterator();
				while (itr.hasNext()) {
					Meeting temp = itr.next();
					if (temp.getId().equals(meetingId)) {
						errMsg = "添加客房房型成功。";
						return SUCCESS;// 如果已经存在，直接返回成功。
					}
				}
			}

			Meeting meeting = new Meeting();
			meeting.setId(meetingId);
			hotelRoomType.getMeetings().add(meeting);
			try {
				hotelRoomTypeService.modify(hotelRoomType);
			} catch (ServiceException e) {
				log.error("关联客房房型到当前会议失败，{}", e.toString());
				errMsg = "新增客房房型失败。";
				return INPUT;
			}

			return SUCCESS;
		} else {
			if (hotelRoomType == null) {
				errMsg = "新增客房房型失败。";
				return INPUT;
			}

			Meeting meeting = new Meeting();
			meeting.setId(meetingId);
			Set<Meeting> meetings = new HashSet<Meeting>();
			meetings.add(meeting);
			hotelRoomType.setMeetings(meetings);// 会议
			hotelRoomType.setState(SysConstants.DB_STATUS_VALID);
			hotelRoomType.setCreateTime(new Date());

			// 新增客房房型
			try {
				hotelRoomTypeService.add(hotelRoomType);
			} catch (ServiceException e) {
				log.error("add hotelRoomType failed! ", e);
				errMsg = "对不起，新增客房房型失败，请稍后再试。";
				return INPUT;
			}

			errMsg = "添加客房房型成功。";
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

		hotelRoomType = hotelRoomTypeService.findById(idL);

		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String update() {
		if (hotelRoomType == null) {
			errMsg = "对不起，修改客房房型失败！";
			return INPUT;
		}

		HotelRoomType oldHotelRoomType = hotelRoomTypeService.findById(hotelRoomType.getId());

		if (oldHotelRoomType == null) {
			errMsg = "对不起，修改客房房型失败！";
			log.error("客房房型不存在，id=" + hotelRoomType.getId());
			return INPUT;
		}

		// 设置页面可修改字段
		oldHotelRoomType.setArea(hotelRoomType.getArea());
		oldHotelRoomType.setCapability(hotelRoomType.getCapability());
		oldHotelRoomType.setBedWidth(hotelRoomType.getBedWidth());
		oldHotelRoomType.setAdditionalInfo(hotelRoomType.getAdditionalInfo());
		oldHotelRoomType.setFacilities(hotelRoomType.getFacilities());
		oldHotelRoomType.setModifyTime(new Date());

		try {
			// 修改客房房型信息
			hotelRoomTypeService.modify(oldHotelRoomType);
		} catch (Throwable e) {
			errMsg = "对不起，修改客房房型失败！";
			return INPUT;
		}

		errMsg = "成功修改客房房型。";
		return SUCCESS;
	}

	/**
	 * 删除客房房型 这里只删除会议与客房房型的映射关系
	 * 
	 * @return
	 */
	public String del() {
		String id = this.getParameter("id");
		Long idL = StringUtil.stringToLong(id);

		HotelRoomType oldHotelRoomType = hotelRoomTypeService.findById(idL);

		if (oldHotelRoomType == null) {
			return SUCCESS;
		}

		// 从客房房型所属会议列表中移去当前会议
		Set<Meeting> meetings = oldHotelRoomType.getMeetings();

		if (meetings == null || meetings.size() == 0) {
			return SUCCESS;
		}

		// 移除客房房型与该会议的关联关系
		Iterator<Meeting> itr = meetings.iterator();
		while (itr.hasNext()) {
			Meeting meeting = itr.next();
			if (meetingId.equals(meeting.getId())) {
				itr.remove();
			}
		}

		try {
			hotelRoomTypeService.modify(oldHotelRoomType);
		} catch (ServiceException e) {
			log.error("删除客房房型失败", e);
			errMsg = "删除失败。";
		}

		errMsg = "删除成功。";
		return SUCCESS;
	}

	public HotelRoomTypeService getHotelRoomTypeService() {
		return hotelRoomTypeService;
	}

	public void setHotelRoomTypeService(HotelRoomTypeService hotelRoomTypeService) {
		this.hotelRoomTypeService = hotelRoomTypeService;
	}

	public HotelService getHotelService() {
		return hotelService;
	}

	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	public HotelRoomType getHotelRoomType() {
		return hotelRoomType;
	}

	public void setHotelRoomType(HotelRoomType hotelRoomType) {
		this.hotelRoomType = hotelRoomType;
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
}
