/**
 * 处理会议下创建的特产信息Dao
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-17
 */
package com.wondertek.meeting.dao.reception;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.reception.LocalSpecialty;
import com.wondertek.meeting.model.reception.MeetingSpecialty;

public interface MeetingSpecialtyDao extends BaseDao<MeetingSpecialty, Integer> {
	
	/**
	 * 查找指定会议下的土特产信息
	 */
	public MeetingSpecialty findByMeetingId(Long meetingId);
	
	/**
	 * 从会议特产信息中移除指定ID的特定项
	 */
	public void removeLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, Integer localSpecialtyId);
	
	/**
	 * 从会议特产信息中移除所有的特定项
	 */
	public void removeAllLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId);
	
	/**
	 * 从会议特产信息中添加指定ID的特定项
	 */
	public void addLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, LocalSpecialty localSpecialty);
}
