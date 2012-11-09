package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.model.DataDictConfig;
import com.wondertek.meeting.model.TagEntity;

/**
 * 
 * @author chif
 *
 */
public interface TagService extends BaseService<DataDictConfig, Long> {
	public List<DataDictConfig> query(DataDictConfig config);
	public List<DataDictConfig> query(TagEntity entity);
}
