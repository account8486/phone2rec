package com.wondertek.meeting.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.SignInHelper;
import com.wondertek.meeting.common.SysUtil;
import com.wondertek.meeting.dao.MeetingAgendaDao;
import com.wondertek.meeting.dao.MeetingClientMenuDao;
import com.wondertek.meeting.dao.MeetingDao;
import com.wondertek.meeting.dao.MeetingFilesDao;
import com.wondertek.meeting.dao.MeetingMemberDao;
import com.wondertek.meeting.dao.MeetingPostDao;
import com.wondertek.meeting.dao.MeetingSmsDao;
import com.wondertek.meeting.dao.OrganizationDao;
import com.wondertek.meeting.dao.UserApplyDao;
import com.wondertek.meeting.dao.impl.MeetingDaoImpl;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminRole;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MeetingService;

/**
 * 会务相关业务处理
 * 
 * @author John Tang
 * 
 */
public class MeetingServiceImpl extends BaseServiceImpl<Meeting, Long> implements MeetingService {

	private MeetingDao meetingDao;
	MeetingFilesDao meetingFilesDao;
	MeetingMemberDao meetingMemberDao;
	MeetingClientMenuDao meetingClientMenuDao;
	MeetingSmsDao meetingSmsDao;
	UserApplyDao userApplyDao;
	private OrganizationDao organizationDao;

	private MeetingAgendaDao meetingAgendaDao;
	private MeetingPostDao meetingPostDao;

	public void setMeetingDao(MeetingDao meetingDao) {
		this.basicDao = meetingDao;
		this.meetingDao = meetingDao;
	}

	/**
	 * 查询指定用户创建的会议分页数据信息
	 * 
	 * @param currentUser
	 *            会议创建者
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @return Pager<Meeting>
	 */
	public Pager<Meeting> findMeetingPager(Meeting meeting, AdminUser currentUser, int currentPage, int pageSize)
			throws ServiceException {

		String hql = "";
		Map<String, Object> properties = new HashMap<String, Object>();
		// 系统管理员可以查看所有会议
		// 会议管理员可以查看自己所在组织及下属组织创建的会议。
		// 会务人员 只能查看被指定了的会议
		AdminRole role = currentUser.getRole();
		log.debug("roles is ==================={}", role);

		if (SysUtil.isSuperAdmin(currentUser)) {
			log.debug("用户[{}]是系统管理员", currentUser.getName());
			hql = "from Meeting where state<>'9' ";
			if (meeting != null && !"".equals(meeting.getName())) {
				hql += " AND name like '%" + meeting.getName() + "%'";
			}
			hql += " order by id desc";
		} else if(SysUtil.isGroupAdmin(currentUser)) {
			hql = "from Meeting m where m.state<>'9' AND m.creator.org.id in (:childOrgIdList)";
			List<Long> childOrgIdList = organizationDao.getChildOrgIdListWithSelf(currentUser.getOrg().getId());
			properties.put("childOrgIdList", childOrgIdList);
			if (meeting != null && !"".equals(meeting.getName())) {
				hql += " AND name like '%" + meeting.getName() + "%'";
			}
			hql += " order by id desc";
		} else{
			hql = "select m from Meeting m inner join m.meetingAdmins u where m.state != 9 and u.id = :userId";
			properties.put("userId", currentUser.getId());
			if (meeting != null && !"".equals(meeting.getName())) {
				hql += " AND m.name like '%" + meeting.getName() + "%'";
			}
			hql += " order by m.id desc";
		}
		
		log.debug("hql==================={}", hql);

		try {
			return meetingDao.findPager(hql, currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find meeting pager error ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	/**
	 * 通过主键获取信息
	 * 
	 * @param meetingId
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public Meeting getMeetingByPk(Long meetingId) throws HibernateDaoSupportException {

		return meetingDao.findById(meetingId, Meeting.class);

	}
	
	public Meeting findById(Long id)throws ServiceException {
		return meetingDao.findById(id);
	}
	
	public List<User> getMeetingUsers(Meeting meeting) {
		return meetingDao.queryUsers(meeting.getId(), null);
	}

	/**
	 * 获取当前用户参加的会议列表 无分页
	 * 
	 * @param userId
	 */
	public List getMyAttendMeetingList(String userId) {
		StringBuffer hql = new StringBuffer();

		// hql.append("  SELECT meeting.id,meeting.name,meeting.type,meeting.topic,meeting.location  FROM  Meeting meeting,MeetingMember member ");
		// hql.append("  SELECT meeting.id,meeting.name,meeting.type,meeting.topic,meeting.location  FROM  Meeting meeting,MeetingMember m ");
		hql.append("  SELECT meeting  FROM  Meeting meeting,MeetingMember m ");
		hql.append("  where state = 1");
		hql.append(" and meeting.id=m.meetingId ");
		hql.append(" and m.userId=" + userId);
		hql.append(" and (meeting.accessStartTime is null or meeting.accessStartTime<=:currentTime)");
		hql.append(" and (meeting.accessEndTime is null or meeting.accessEndTime>=:currentTime)");
		hql.append("  order by  meeting.startTime desc,meeting.createTime desc ");

		MeetingDaoImpl meetingDaoImpl = new MeetingDaoImpl();
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("currentTime", new Date());
		List lst = meetingDao.getMyAttendMeetingList(userId, hql.toString(), properties);
		
		//TODO
		

		return lst;

	}

	public boolean cleanMeetingData(Meeting meeting) throws ServiceException {

		// 删除会议基础信息
		meetingDao.delete(meeting);

		// 1、会议用户
		this.meetingMemberDao.deleteMeetingMemberByMeetingId(meeting.getId());

		// 2、会议议程
		meetingAgendaDao.deleteAgendaByMeetingId(meeting.getId());

		// 3、会议资料
		this.meetingFilesDao.deleteMeetingFilesByMeetingId(meeting.getId());

		// 4、议程分组

		// 5、就餐信息

		// 6、交流互动
		meetingPostDao.deletePostByMeetingId(meeting.getId());

		// 7、会议用户菜单
		this.meetingClientMenuDao.deleteMeetingClientMenuByMeetingId(meeting.getId());
		// 8、会议短信
		this.meetingSmsDao.deleteMeetingSmsByMeetingId(meeting.getId());
		// 9、会议签到信息
		SignInHelper.getInstance().delSignCodeByMeetingId(meeting.getId());
		// 10.申请加入会议删除
		this.userApplyDao.deleteUserApplyByMeetingId(meeting.getId());

		return false;
	}

	public MeetingFilesDao getMeetingFilesDao() {
		return meetingFilesDao;
	}

	public void setMeetingFilesDao(MeetingFilesDao meetingFilesDao) {
		this.meetingFilesDao = meetingFilesDao;
	}

	public void setMeetingMemberDao(MeetingMemberDao meetingMemberDao) {
		this.meetingMemberDao = meetingMemberDao;
	}

	public void setMeetingSmsDao(MeetingSmsDao meetingSmsDao) {
		this.meetingSmsDao = meetingSmsDao;
	}

	public void setUserApplyDao(UserApplyDao userApplyDao) {
		this.userApplyDao = userApplyDao;
	}

	public void setMeetingClientMenuDao(MeetingClientMenuDao meetingClientMenuDao) {
		this.meetingClientMenuDao = meetingClientMenuDao;
	}

	/**
	 * @param meetingAgendaDao
	 *            the meetingAgendaDao to set
	 */
	public void setMeetingAgendaDao(MeetingAgendaDao meetingAgendaDao) {
		this.meetingAgendaDao = meetingAgendaDao;
	}

	/**
	 * @param meetingPostDao
	 *            the meetingPostDao to set
	 */
	public void setMeetingPostDao(MeetingPostDao meetingPostDao) {
		this.meetingPostDao = meetingPostDao;
	}

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}
}
