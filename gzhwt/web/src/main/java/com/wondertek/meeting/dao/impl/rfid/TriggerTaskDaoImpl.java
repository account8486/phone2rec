/**
 * 触发任务管理Dao实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-14
 */
package com.wondertek.meeting.dao.impl.rfid;

import java.util.List;

import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.dao.rfid.TriggerTaskDao;
import com.wondertek.meeting.model.rfid.TriggerTask;

public class TriggerTaskDaoImpl extends BaseDaoImpl<TriggerTask, Integer>
		implements TriggerTaskDao {
	
	/**
	 * 根据会议ID查找触发任务信息
	 */
	public TriggerTask findTriggerTaskByMeetingId(Long meetingId) {
		String hql = "from TriggerTask t where t.meeting.id=?";
		List<TriggerTask> list = this.getHibernateTemplate().find(hql, meetingId);
		return list.size() > 0 ? list.get(0) : null;
	}
}
