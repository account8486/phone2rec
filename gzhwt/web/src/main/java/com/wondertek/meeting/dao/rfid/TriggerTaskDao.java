/**
 * 触发任务管理Dao
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-14
 */
package com.wondertek.meeting.dao.rfid;

import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.rfid.TriggerTask;

public interface TriggerTaskDao extends BaseDao<TriggerTask, Integer> {
	/**
	 * 根据会议ID查找触发任务信息
	 */
	public TriggerTask findTriggerTaskByMeetingId(Long meetingId);
}
