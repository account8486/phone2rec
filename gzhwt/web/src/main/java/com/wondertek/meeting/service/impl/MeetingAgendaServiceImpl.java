/**
 * 
 */
package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingAgendaDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.GroupPlan;
import com.wondertek.meeting.model.MeetingAgenda;
import com.wondertek.meeting.service.MeetingAgendaService;

/**
 * @author rain
 * 
 */
public class MeetingAgendaServiceImpl extends BaseServiceImpl<MeetingAgenda, Long> implements MeetingAgendaService {
	MeetingAgendaDao meetingAgendaDao;

	public List<MeetingAgenda> queryListByMeetingId(final Long meetingId, final Long userId) {
		final List<MeetingAgenda> agendas = meetingAgendaDao.queryListByMeetingId(meetingId);
		for(MeetingAgenda agenda:agendas) {
			try {
				agenda.setGroupPlan(getAgendaGroupPlanByUserLevel(userId, agenda));
			} catch (ServiceException e) {
				log.error("getAgendaGroupPlanByUserLevel error: ", e);
			}
		}
		return agendas;
	}

	public Pager<MeetingAgenda> queryPagerByMeetingId(final MeetingAgenda meetingAgenda, final int currentPage,
			final int pageSize) throws ServiceException {
		final Map<String, Object> properties = new HashMap<String, Object>();
		final String sql = "from MeetingAgenda where meetingId=" + String.valueOf(meetingAgenda.getMeetingId()) + " order by date, startTime, endTime";
		final Pager<MeetingAgenda> pager = meetingAgendaDao.findPager(sql, currentPage, pageSize, properties);
		for(MeetingAgenda agenda:pager.getPageRecords()) {
			final GroupPlan plan = getAgendaGroupPlan(agenda.getId());
			agenda.setGroupPlan(plan);
			if (plan == null) {
				agenda.setDescription("未分组");
			} else {
				agenda.setDescription("已分组");
			}
		}
		return pager;
	}
	
	private GroupPlan getAgendaGroupPlanByUserLevel(final Long userId, final MeetingAgenda agenda) throws ServiceException {
		return getBaseDao().getGroupPlanByUserRights(agenda.getMeetingId(), Constants.GROUP_PLAN_TYPE.AGENDA, userId, agenda.getId());
	}
	
	private GroupPlan getAgendaGroupPlan(final Long agendaId) throws ServiceException {
		return getBaseDao().getGroupPlanByUserRights(Constants.GROUP_PLAN_TYPE.AGENDA, agendaId);
	}

	public List<MeetingAgenda> queryListByDate(final Long meetingId, final String dateStr) throws ServiceException {
		final String hql = "from MeetingAgenda where meetingId=" + meetingId + " and date = date('" + dateStr + "')";
		return meetingAgendaDao.getObjects(hql);
	}

	public void saveList(final List<MeetingAgenda> entities) throws ServiceException {
		for (MeetingAgenda entity : entities) {
			meetingAgendaDao.add(entity);
		}
	}

	public void setMeetingAgendaDao(MeetingAgendaDao meetingAgendaDao) {
		super.basicDao = meetingAgendaDao;
		this.meetingAgendaDao = meetingAgendaDao;
	}

	public void deleteAgendaById(MeetingAgenda agenda) throws ServiceException {
		String hql = "delete from MeetingGroup where type = " + Constants.GROUP_PLAN_TYPE.AGENDA + " and relationId = "
				+ agenda.getId();
		log.info(hql);
		meetingAgendaDao.executeUpdate(hql, new HashMap<String, Object>());
		meetingAgendaDao.delete(agenda);
		log.info("删除分组信息成功 dinnerId = {}", agenda.getId());
	}
	
	/**
	 * 通过meetingID来获取最新时间的会议 来进行下一个议程添加的时间依据
	 * 
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public List<MeetingAgenda> getNewestAgenda(Long meetingId)
			throws HibernateDaoSupportException {
		final String hql = "from MeetingAgenda where meetingId=" + meetingId
				+ " order by  date desc,endTime desc";
		
		return meetingAgendaDao.getObjects(hql);
	}
	
	
	
}