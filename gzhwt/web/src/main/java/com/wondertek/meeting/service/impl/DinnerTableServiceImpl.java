package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.dao.DinnerTableDao;
import com.wondertek.meeting.model.DinnerTable;
import com.wondertek.meeting.service.DinnerTableService;

/**
 * 
 * @author John Tang
 */
public class DinnerTableServiceImpl extends BaseServiceImpl<DinnerTable, Long> implements DinnerTableService {

	private DinnerTableDao dinnerTableDao;

	public DinnerTableDao getDinnerTableDao() {
		return dinnerTableDao;
	}

	public void setDinnerTableDao(DinnerTableDao dinnerTableDao) {
		super.basicDao = dinnerTableDao;
		this.dinnerTableDao = dinnerTableDao;
	}
}
