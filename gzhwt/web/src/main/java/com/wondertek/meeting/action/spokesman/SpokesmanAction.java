package com.wondertek.meeting.action.spokesman;

import java.util.Date;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.spokesman.Spokesman;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.SpokesmanService;
import com.wondertek.meeting.util.StringUtil;

public class SpokesmanAction extends BaseAction {
	
	private static final long serialVersionUID = 3871997575462757224L;
	
	SpokesmanService spokesmanService;
	MeetingService meetingService;
	
	String meetingId;
	
	public String getMeetingId() {
		return meetingId;
	}


	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}


	public SpokesmanService getSpokesmanService() {
		return spokesmanService;
	}


	public void setSpokesmanService(SpokesmanService spokesmanService) {
		this.spokesmanService = spokesmanService;
	}

	/**
	 * 获取列表
	 * @return
	 * @throws HibernateDaoSupportException 
	 */
	public String getSpokesManLst() throws Exception {
		String meetingId = this.getParameter("meetingId");
		String mobile=this.getParameter("mobile");
		String spokesManname=this.getParameter("spokesManname");

		Pager<Spokesman> pager = spokesmanService.getSpokesmanList(
				currentPage, pageSize,meetingId,mobile,spokesManname);

		getRequest().setAttribute("pager", pager);
		getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute("mobile", mobile);
		getRequest().setAttribute("spokesManname", spokesManname);
		
		Meeting meeting=this.meetingService.findById(Long.parseLong(meetingId));
		this.setAttribute("meeting", meeting);
		return SUCCESS;
	}
	
	/**
	 * 跳转到编辑页面
	 * @return
	 */
	public String toAddSpokesman(){
		String meetingId= this.getParameter("meetingId");
		
		
		getRequest().setAttribute("meetingId", meetingId);
		return SUCCESS;
	}
	
	/**
	 * 保存操作
	 * @return
	 * @throws ServiceException 
	 */
	public String saveSpokesMan() throws ServiceException {
		try {
			String name = this.getParameter("name");
			String mobile = this.getParameter("mobile");
			String gender = this.getParameter("gender");
			String meetingId = this.getParameter("meetingId");
			log.info("name:" + name + ",mobile:" + mobile + ",gender:" + gender
					+ ",meetingId=" + meetingId);
			Spokesman spokesman = new Spokesman();
			spokesman.setName(name);
			spokesman.setMobile(mobile);
			spokesman.setGender(StringUtil.isEmpty(gender) ? null : Integer
					.parseInt(gender));
			spokesman.setCreateTime(new Date());
			spokesman.setCreator(this.getAdminUserIdFromSession());
			spokesman.setMeetingId(Long.valueOf(meetingId));
			spokesmanService.saveOrUpdate(spokesman);
			this.resultMap.put("retCode", "0");
			this.resultMap.put("retMsg", "保存成功！");
		} catch (Exception e) {
			log.info(e.getMessage());
			this.resultMap.put("retCode", "0");
			this.resultMap.put("retMsg", e.getMessage());
		}
		return SUCCESS;
	}
	
	
	/**
	 * 进行更新操作
	 * @return
	 */
	public String toUpdateSpokesMan(){
		
		try{
			String id = this.getParameter("id");
			Spokesman spokesman = spokesmanService.findById(Long.valueOf(id));
			this.setAttribute("spokesman", spokesman);
			this.setAttribute("meetingId", spokesman.getMeetingId());
		}catch(Exception e){
			e.printStackTrace();
		}
	
		
		return SUCCESS;
	}
	
	/**
	 * 更新
	 * @return
	 */
	public String updateSpokesMan(){
		try {
			String id=this.getParameter("id");
			String name = this.getParameter("name");
			String mobile = this.getParameter("mobile");
			String gender = this.getParameter("gender");
			String meetingId = this.getParameter("meetingId");
			
			log.info("id:"+id+",name:" + name + ",mobile:" + mobile + ",gender:" + gender
					+ ",meetingId=" + meetingId);
			Spokesman spokesman =spokesmanService.findById(Long.valueOf(id));
			
			spokesman.setName(name);
			spokesman.setMobile(mobile);
			spokesman.setGender(StringUtil.isEmpty(gender) ? null : Integer
					.parseInt(gender));
			spokesman.setModifyTime(new Date());
			spokesman.setModifier(this.getAdminUserIdFromSession());
			
			spokesmanService.saveOrUpdate(spokesman);
			this.setMeetingId(meetingId);
			
		} catch (Exception e) {
			log.info(e.getMessage());
			this.resultMap.put("retCode", "0");
			this.resultMap.put("retMsg", e.getMessage());
		}
		
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public String deleteSpokesMan() {
		try {
			String id = this.getParameter("id");
			Spokesman spokesman = spokesmanService.findById(Long.valueOf(id));
			spokesmanService.delete(spokesman);
			this.resultMap.put("retCode", "0");
			this.resultMap.put("retMsg", "删除成功!");
			
		} catch (Exception e) {
			log.info(e.getMessage());
			this.resultMap.put("retCode", "1");
			this.resultMap.put("retMsg", e.getMessage());
		}
		return SUCCESS;

	}


	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
	

	
	
	
	

}
