package com.wondertek.meeting.service;


import java.util.List;

import com.wondertek.meeting.model.BaseModuleTitle;

/**
 * 
 * @author chif
 *
 */
public interface BaseModuleTitleService extends BaseService<BaseModuleTitle, Long> {
	public List<BaseModuleTitle> query(BaseModuleTitle baseModuleTitle );
}
