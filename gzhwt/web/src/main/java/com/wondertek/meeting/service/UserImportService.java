package com.wondertek.meeting.service;

import java.util.HashMap;
import java.util.List;

import com.wondertek.meeting.model.User;

public interface UserImportService extends BaseService{
	/**
	 * 进行用户的导入操作
	 * 
	 * @param importUserList
	 */
	public HashMap saveImportUser(List<User> importUserList,Long meetingId,Integer memberLevel) ;
}
