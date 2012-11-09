package com.wondertek.meeting.action.meeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingArea;
import com.wondertek.meeting.service.MeetingAreaService;

/**
 * 会议地点
 * 
 * @author 金祝华
 */
public class MeetingAreaAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4176582736781578667L;

	private MeetingAreaService meetingAreaService;

	/**
	 * 获取所有地点信息
	 * 
	 * @return
	 */
	public String getAreas() {
		List<MeetingArea> list = null;
		try {
			list = meetingAreaService.findAll();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Map<String, List<MeetingArea>> areas = new HashMap<String, List<MeetingArea>>();
		if (list != null && list.size() > 0) {
			//loop sort all the records by parent id,data structure:HashMap
			for (MeetingArea meetingArea : list) {
				List<MeetingArea> areaList = areas.get(meetingArea.getParent());
				if (areaList == null) {
					areaList = new ArrayList<MeetingArea>();
					areas.put(meetingArea.getParent(), areaList);
				}
				areaList.add(meetingArea);
			}
		}

		return this.json2Resp(areas);
	}

	public MeetingAreaService getMeetingAreaService() {
		return meetingAreaService;
	}

	public void setMeetingAreaService(MeetingAreaService meetingAreaService) {
		this.meetingAreaService = meetingAreaService;
	}
}