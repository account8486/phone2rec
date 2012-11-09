package com.wondertek.meeting.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.dao.TaskDao;
import com.wondertek.meeting.model.Task;

/**
 * 会议任务数据操作类
 * 
 * @author tangjun
 */
public class TaskDaoImpl extends BaseDaoImpl<Task, Long> implements TaskDao {
	Logger log = LoggerFactory.getLogger(this.getClass());
}
