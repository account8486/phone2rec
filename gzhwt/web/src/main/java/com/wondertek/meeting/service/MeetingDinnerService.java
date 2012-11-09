package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.model.MeetingDinner;
import com.wondertek.meeting.model.UserTableInfo;
import com.wondertek.meeting.model.UserTableInfoWithDate;

/**
 * @author John Tang
 */
public interface MeetingDinnerService extends BaseService<MeetingDinner, Long> {
	
	/**按会议ID查询用餐安排*/
	public List<MeetingDinner> findDinnerList(String address,long meetingId,String dinnerDate) throws ServiceException;
	/**根据会议ID和用户ID查询该用户的就餐信息*/
	public List<UserTableInfoWithDate> findMemberDinnerList(long meetingId, long memberId) throws ServiceException; 
	
	public void deleteDinnerById(long dinnerId)  throws ServiceException;
	
	/**查询该会议该用餐信息已分桌的用户*/
	public List<UserTableInfo> getUserListInDinner(Long meetingId,Long dinnerId)  throws ServiceException ;
	
	/**查询该会议该用餐信息未分桌的用户*/
	public List<JsonUser> getUserListNotInDinner(Long meetingId,Long dinnerId)  throws ServiceException ;
	
	/**将用户加入该分桌信息表dinner_table*/
	public void addUserToDinnerTable(String memberIds,String tableCode,Long dinnerId,Long meetingId) throws ServiceException;
	
	/**将用户移出该分桌信息表dinner_table*/
	public void removeUserFromDinnerTable(Long dinnerId,String memberIds) throws ServiceException;
	
	/**保存导入的用户分桌信息*/
	public void saveImportDinnerInfo(List<UserTableInfo> importUserTableInfoList,MeetingDinner dinner) throws ServiceException;
	
	/**保存导入的会议用餐信息*/
	public void saveImportDinnerPlan(List<MeetingDinner> dinnerList) throws ServiceException;
}
