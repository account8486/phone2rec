package com.wondertek.meeting.action.meeting;

import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.util.MapUtils;

/**
 * User: sank
 * Date: 11-12-9
 * Time: 上午10:34
 */
public class MapAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3216081566462405575L;
	private Meeting meeting;
    private MeetingService meetingService;
    public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

    public String wapView() {
        try {
            meeting = meetingService.findById(meeting.getId());
            if (meeting.getLocationXY() == null || "".equals(meeting.getLocationXY())) {
                //meeting.setLocationXY("117.285574,31.868537");
            	final String city = ResourceBundle.getBundle("mapabc").getString(meeting.getCity());
            	final String[] datas = StringUtils.split(city, ",");
            	meeting.setLocationXY(MapUtils.decode(datas[2]) + "," + MapUtils.decode(datas[3]));
            }
        } catch (ServiceException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public MeetingService getMeetingService() {
        return meetingService;
    }

    public void setMeetingService(MeetingService meetingService) {
        this.meetingService = meetingService;
    }
}
