package com.wondertek.meeting.service.impl;

import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.flex.remoting.RemotingDestination;

import com.wondertek.meeting.dao.ManagerSeatDao;
import com.wondertek.meeting.dao.MeetingMemberDao;
import com.wondertek.meeting.dao.UserDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.MeetingSeat;
import com.wondertek.meeting.model.MeetingSeatImage;
import com.wondertek.meeting.model.MeetingSeatTemplate;
import com.wondertek.meeting.service.ManagerSeatService;

@RemotingDestination(channels={"my-amf"}, value="managerSeatService")
public class ManagerSeatServiceImpl extends BaseServiceImpl<MeetingSeat, Integer> implements ManagerSeatService {
	private ManagerSeatDao managerSeatDao;
	
	private MeetingMemberDao meetingMemberDao;
	
	private UserDao userDao;

	public void setManagerSeatDao(ManagerSeatDao managerSeatDao) {
		this.managerSeatDao = managerSeatDao;
		this.basicDao=managerSeatDao;
	}
	
	public void setMeetingMemberDao(MeetingMemberDao meetingMemberDao) {
		this.meetingMemberDao = meetingMemberDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<MeetingSeat> getManageSeatByMeetingId(int meetingId) {
		try {
			Map<String, Object> properties = new HashMap<String, Object>(1);
			properties.put("meetingId", meetingId);
			
			List<MeetingSeat> meetingSeats = this.getObjects("from MeetingSeat s where s.meetingId = :meetingId", properties);
			for(MeetingSeat meetingSeat : meetingSeats) {
				meetingSeat.setUser(this.userDao.findById((long) meetingSeat.getUserId()));
			}
			
			return meetingSeats;
		} catch (ServiceException e) {
			log.error("getManageSeatByMeetingId error: meetingId is " + meetingId, e);
			return null;
		}
	}

	@Override
	public List<MeetingMember> getMeetingMemberByMeetingId(int meetingId) {
		try {
			List<MeetingMember> meetingMembers = meetingMemberDao.queryMemberList((long) meetingId);
			for(MeetingMember meetingMember : meetingMembers) {
				meetingMember.setHotelRoom(null);
				meetingMember.setUser(this.userDao.findById((long) meetingMember.getUserId()));
			}
			return meetingMembers;
		} catch (Exception e) {
			log.error("getMeetingMemberByMeetingId error: meetingId is " + meetingId, e);
			return null;
		}
	}

	@Override
	public boolean submitMeetingSeat(List<MeetingSeat> meetingSeats) {
		try {
			if(meetingSeats.size() == 0) {
				throw new Exception("submitMeetingSeat error size is 0.");
			}
			
			// 先删除表中之前已有的数据，再保存新的，因为每个会议只会有一批会场纪录
			this.executeUpdate("delete from MeetingSeat s where s.meetingId = ?", new Object[]{meetingSeats.get(0).getMeetingId()});
			for (MeetingSeat meetingSeat : meetingSeats) {
				this.add(meetingSeat);
			}
			
			// 保存会场图片
			this.executeUpdate("delete from MeetingSeatImage s where s.meetingId = ?", new Object[]{meetingSeats.get(0).getMeetingId()});
			MeetingSeatImage msi = new MeetingSeatImage();
			msi.setMeetingId(meetingSeats.get(0).getMeetingId());
			Blob blob = Hibernate.createBlob(meetingSeats.get(0).getImage());
			msi.setImage(blob);
			msi.setOffsetX(meetingSeats.get(0).getOffsetX());
			msi.setOffsetY(meetingSeats.get(0).getOffsetY());
			
			this.managerSeatDao.saveSeatImage(msi);
			
			return true;
		} catch (Exception e) {
			log.error("submitMeetingSeat error ,meetingSeats is {}", meetingSeats);
			log.error("submitMeetingSeat error", e);
			return false;
		}
	}

	@Override
	public List<MeetingSeatTemplate> getMeetingTemplate() {
		try {
			return this.managerSeatDao.getMeetingTemplate();
		} catch (Exception e) {
			log.error("getMeetingTemplate error", e);
			return null;
		}
	}

	@Override
	public List<MeetingSeatTemplate> getMeetingTemplateByName(String name) {
		try {
			return this.managerSeatDao.getMeetingTemplateByName(name);
		} catch (Exception e) {
			log.error("getMeetingTemplateByName error:" + name, e);
			return null;
		}
	}

	@Override
	public boolean saveTemplate(List<MeetingSeatTemplate> meetingSeatTemplates) {
		try {
			// 先删除该模板名称的所有信息
			if(meetingSeatTemplates.size() > 0) {
				this.executeUpdate("delete from MeetingSeatTemplate where name = ?", new Object[]{meetingSeatTemplates.get(0).getName()});
			}
			
			// 再插入新的数据
			for(MeetingSeatTemplate template : meetingSeatTemplates) {
				this.managerSeatDao.saveSeatTemplate(template);
			}
			
			return true;
		} catch (Exception e) {
			log.error("getMeetingTemplate error", e);
			return false;
		}
	}
}
