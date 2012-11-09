package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.model.BaseModuleTitle;

/**
 *JSP tag
 * 
 * @author chif
 */
public interface BaseModuleTitleDao extends BaseDao<BaseModuleTitle, Long>  {
	public List<BaseModuleTitle> query(BaseModuleTitle baseModuleTitle);
}
