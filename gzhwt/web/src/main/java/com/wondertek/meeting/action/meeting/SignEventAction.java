package com.wondertek.meeting.action.meeting;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.SignEvent;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.SignEventService;


public class SignEventAction extends BaseAction implements ModelDriven<SignEvent> {
	private static final long serialVersionUID = 9123060847772877344L;
	
	private SignEvent signEvent=new SignEvent();
		
	private Long meetingId; //会议ID
	
	private SignEventService signEventService;
	
	private MeetingService meetingService;
	
	private String flag;
	
	private SignEvent event;
	
	
	/**
	 * 跳转到添加页面
	 * @author zouxiaoming
	 * @return
	 */
	public String switchAdd(){
		if(getSignEvent().getId()!=null&&getSignEvent().getId()>0){
			try {
				flag="edit";
				setSignEvent(this.signEventService.findById(getSignEvent().getId()));
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		
		/*List<SignEvent> list=this.signEventService.findByMeetingId(meetingId);
		if(list==null||list.isEmpty()){
			return "switchAddFirst";
		}*/
		
		return "switchAdd";
	}
	
	/**
	 * 添加签到事件
	 * @author zouxiaoming
	 * @return
	 */
	public String add(){
		try {
			getSignEvent().setMeetId(meetingId);
			this.signEventService.saveOrUpdate(getSignEvent());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return this.findAll();
	}
	
	
	/**
	 * 查询所有的签到事件
	 * @author zouxiaoming
	 * @return
	 */
	public String findAll(){
		List<SignEvent> list=this.signEventService.findByMeetingId(meetingId);//查询某一个会议下的所有签到事件
		ActionContext.getContext().put("list",list);
		return "findAll";
	}
	
	/**
	 * 删除一条签到事件
	 * @author zouxiaoming
	 * @return
	 */
	public String delete(){
		boolean result=this.signEventService.deleteSignEvent(getSignEvent().getId());
		if(result){
			try {
				this.signEventService.delete(getSignEvent());
				errMsg="删除成功";
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}else{
			errMsg="此签到事件已经产生签到记录，不能删除";
		}
		return this.findAll();
	}
	
	
	@Override
	public SignEvent getModel() {
		return getSignEvent();
	}
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	public Long getMeetingId() {
		return meetingId;
	}

	public void setSignEventService(SignEventService signEventService) {
		this.signEventService = signEventService;
	}

	public SignEventService getSignEventService() {
		return signEventService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setSignEvent(SignEvent signEvent) {
		this.signEvent = signEvent;
	}

	public SignEvent getSignEvent() {
		return signEvent;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}

	public void setEvent(SignEvent event) {
		this.event = event;
	}

	public SignEvent getEvent() {
		return event;
	}
	
	
}
