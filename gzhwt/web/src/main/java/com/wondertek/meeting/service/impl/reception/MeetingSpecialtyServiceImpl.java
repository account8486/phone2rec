/**
 * 处理会议特产信息的Service实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-17
 */
package com.wondertek.meeting.service.impl.reception;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingDao;
import com.wondertek.meeting.dao.reception.LocalSpecialtyDao;
import com.wondertek.meeting.dao.reception.MeetingSpecialtyDao;
import com.wondertek.meeting.model.reception.LocalSpecialty;
import com.wondertek.meeting.model.reception.MeetingSpecialty;
import com.wondertek.meeting.service.impl.BaseServiceImpl;
import com.wondertek.meeting.service.reception.MeetingSpecialtyService;

public class MeetingSpecialtyServiceImpl extends
		BaseServiceImpl<MeetingSpecialty, Integer> implements
		MeetingSpecialtyService {

	private MeetingDao meetingDao;
	private MeetingSpecialtyDao meetingSpecialtyDao;
	private LocalSpecialtyDao localSpecialtyDao;

	public MeetingDao getMeetingDao() {
		return meetingDao;
	}

	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}

	public void setMeetingSpecialtyDao(MeetingSpecialtyDao meetingSpecialtyDao) {
		super.setBaseDao(meetingSpecialtyDao);
		this.meetingSpecialtyDao = meetingSpecialtyDao;
	}

	public void setLocalSpecialtyDao(LocalSpecialtyDao localSpecialtyDao) {
		this.localSpecialtyDao = localSpecialtyDao;
	}
	
	/**
	 * 查找指定会议下的土特产信息
	 */
	public MeetingSpecialty findByMeetingId(Long meetingId) {
		return this.meetingSpecialtyDao.findByMeetingId(meetingId);
	}
	
	/**
	 * 分页查看会议土特产中的特产明细列表
	 * localSpecialty: 查询条件:名称 or 省 or 市
	 */
	public Pager<LocalSpecialty> findAllLocalSpecialtyPager(
			Integer meetingSpecialtyId, LocalSpecialty localSpecialty, 
			int currentPage, int pageSize) throws Exception  {
		return this.localSpecialtyDao.findAllLocalSpecialtyPager(meetingSpecialtyId, localSpecialty, currentPage, pageSize);
	}
	
	/**
	 * 根据名字，区域查找特产信息
	 */
	public List<Object[]> searchLocalSpecialty(final LocalSpecialty example) {
		return this.localSpecialtyDao.searchLocalSpecialty(example);
	}
	
	/**
	 * 根据ID查找LocalSpecialty
	 */
	public LocalSpecialty findLocalSpecialtyById(Integer id) throws Exception {
		return this.localSpecialtyDao.findById(id, LocalSpecialty.class);
	}
	
	/**
	 * 保存LocalSpecialty
	 */
	public void saveOrUpdateLocalSpecialty(LocalSpecialty specialty) {
		this.localSpecialtyDao.saveOrUpdateEntity(specialty);
	}
	
	/**
	 * 从会议特产信息中移除指定ID的特定项
	 */
	public void removeLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, Integer localSpecialtyId) {
		this.meetingSpecialtyDao.removeLocalSpecialtyFromMeetingSpecialty(meetingSpecialtyId, localSpecialtyId);
	}
	
	/**
	 * 从会议特产信息中移除所有的特定项
	 */
	public void removeAllLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId) {
		this.meetingSpecialtyDao.removeAllLocalSpecialtyFromMeetingSpecialty(meetingSpecialtyId);
	}
	
	/**
	 * 从会议特产信息中添加指定ID的特定项
	 */
	public void addLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, LocalSpecialty localSpecialty) {
		this.meetingSpecialtyDao.addLocalSpecialtyFromMeetingSpecialty(meetingSpecialtyId, localSpecialty);
	}
	
	/**
	 * 从指定的会议特产中查找指定ID的土特产项
	 */
	public LocalSpecialty findLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, Integer localSpecialtyId) {
		return this.localSpecialtyDao.findLocalSpecialtyFromMeetingSpecialty(meetingSpecialtyId, localSpecialtyId);
	} 
}
