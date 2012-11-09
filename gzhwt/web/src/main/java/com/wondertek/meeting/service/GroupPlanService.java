package com.wondertek.meeting.service;

import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.GroupPlan;
import com.wondertek.meeting.model.GroupPlanDetail;
import com.wondertek.meeting.model.ImportGroupPlanData;
import com.wondertek.meeting.model.JsonUser;

/**
 * @author John Tang
 */
public interface GroupPlanService extends BaseService<GroupPlan, Long> {
	/**根据会议ID查询出分组计划模版*/
	public List<GroupPlan> listByMeetingId(Long meetingId) throws ServiceException;
	
	/**保存导入的分组计划数据*/
	public String saveImportData(Map<String,List<ImportGroupPlanData>> importDataMap,GroupPlan groupPlan) throws ServiceException;
	
	public void deleteById(GroupPlan groupPlan) throws ServiceException;
	
	/**根据relationID查找当前选择的分组模版ID*/
	public GroupPlan findByRelationId(Long relationId,int type) throws ServiceException;
	/**根据类型查找出所有的分组模版*/
	public List<GroupPlan> findPlanListByType(int type,Long meetingId) throws ServiceException;
	
	/**根据分组ID查询出组员信息*/
	public List<JsonUser> getMemberByGroupId(Long groupId)  throws ServiceException;
	
	/**根据分组计划ID查询出所有的分组和组员信息*/
	public Map<GroupPlanDetail,List<JsonUser>> getGroupMemberInfo(Long planId) throws ServiceException;
	
	/**根据userId,relationId 和type查询出组员信息*/
	public Map<GroupPlanDetail,List<JsonUser>> getGroupMemberInfo(Long userId,Integer type,Long relationId) throws ServiceException;
	
	/** 根据userId,relationId 和type查询出分组信息 */
	public GroupPlanDetail getGroupDetailInfo(Long userId, Integer type, Long relationId) throws ServiceException;
	
	/**根据分组模版ID查找出所有的分组信息*/
	public List<GroupPlanDetail> findPlanDetailList(Long planId) throws ServiceException;
	
	/** 根据分组ID分页查询出组员信息 */
	public Pager<JsonUser> getMemberPagerByGroupId(Long groupId,int currentPage,int pageSize) throws ServiceException;
	
	/**修改分组模版信息*/
	public void modifyGroupPlan(GroupPlan groupPlan) throws ServiceException;
	
	/**根据分组ID删除分组信息和该组下的组员*/
	public void deleteGroupById(Long groupId) throws ServiceException;
	
	/**根据分组模版ID查询当前未分组的成员信息*/
	public List<JsonUser> findMemberListNotInGroup(Long planId,Long meetingId,String name,String mobile,String city,String delegation)  throws ServiceException ;
	
	/**将选择的成员加入该分组*/
	public void addMember2Group(String memberIds,GroupPlanDetail groupPlanDetail)  throws ServiceException;
	
	/**将选择的成员移出该分组*/
	public void removeMemberFromGroup(Long memberId,Long groupId)  throws ServiceException;
	
	/**将选择的成员移到其他分组*/
	public void moveMemberFromGroup(Long memberId,Long groupId,Long otherGroupId)  throws ServiceException;
	
	/**将选择的成员排序*/
	public void sortMemberForGroup(Long memberId,Long groupId,String operator)  throws ServiceException;
}
