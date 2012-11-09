/**
 * 处理会议下创建的特产信息Dao实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-17
 */
package com.wondertek.meeting.dao.impl.reception;

import java.util.List;
import java.util.Set;

import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.dao.reception.MeetingSpecialtyDao;
import com.wondertek.meeting.model.reception.LocalSpecialty;
import com.wondertek.meeting.model.reception.MeetingSpecialty;

public class MeetingSpecialtyDaoImpl extends
		BaseDaoImpl<MeetingSpecialty, Integer> implements MeetingSpecialtyDao {
	
	/**
	 * 查找指定会议下的土特产信息
	 */
	@SuppressWarnings("unchecked")
	public MeetingSpecialty findByMeetingId(Long meetingId) {
		String hql = "from MeetingSpecialty ms where ms.meeting.id=?";
		List<MeetingSpecialty> list = this.getHibernateTemplate().find(hql, meetingId);
		MeetingSpecialty ms = list.size() > 0 ? list.get(0) : null;
		return ms;
	}
	
	/**
	 * 从会议特产信息中移除指定ID的特定项
	 */
	public void removeLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, Integer localSpecialtyId) {
		MeetingSpecialty ms = this.getHibernateTemplate().get(MeetingSpecialty.class, meetingSpecialtyId);
		Set<LocalSpecialty> locSet = ms.getLocalSpecialtySet();
		for(LocalSpecialty loc : locSet) {
			if(loc.getId().equals(localSpecialtyId)) {
				locSet.remove(loc);
				break;
			}
		}
		this.getHibernateTemplate().saveOrUpdate(ms);
	}
	
	/**
	 * 从会议特产信息中移除所有的特定项
	 */
	public void removeAllLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId) {
		MeetingSpecialty ms = this.getHibernateTemplate().get(MeetingSpecialty.class, meetingSpecialtyId);
		ms.getLocalSpecialtySet().clear();
		this.getHibernateTemplate().saveOrUpdate(ms);
	}
	
	/**
	 * 从会议特产信息中添加指定ID的特定项
	 */
	public void addLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, LocalSpecialty localSpecialty) {
		MeetingSpecialty ms = this.getHibernateTemplate().get(MeetingSpecialty.class, meetingSpecialtyId);
		ms.getLocalSpecialtySet().add(localSpecialty);
		this.getHibernateTemplate().saveOrUpdate(ms);
	}
}
