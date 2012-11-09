package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.GroupPlanDao;
import com.wondertek.meeting.dao.GroupPlanDetailDao;
import com.wondertek.meeting.dao.GroupPlanMemberDao;
import com.wondertek.meeting.dao.MeetingGroupDao;
import com.wondertek.meeting.dao.MeetingMemberDao;
import com.wondertek.meeting.dao.UserDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.GroupPlan;
import com.wondertek.meeting.model.GroupPlanDetail;
import com.wondertek.meeting.model.GroupPlanMember;
import com.wondertek.meeting.model.ImportGroupPlanData;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.GroupPlanService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 
 * @author John Tang
 */
public class GroupPlanServiceImpl extends BaseServiceImpl<GroupPlan, Long> implements GroupPlanService {

	private MeetingGroupDao meetingGroupDao;
	private UserDao userDao;
	private GroupPlanDao groupPlanDao;
	private GroupPlanDetailDao groupPlanDetailDao;
	private GroupPlanMemberDao groupPlanMemberDao;
	private MeetingMemberDao meetingMemberDao;

	public MeetingMemberDao getMeetingMemberDao() {
		return meetingMemberDao;
	}

	public void setMeetingMemberDao(MeetingMemberDao meetingMemberDao) {
		this.meetingMemberDao = meetingMemberDao;
	}

	public MeetingGroupDao getMeetingGroupDao() {
		return meetingGroupDao;
	}

	public void setMeetingGroupDao(MeetingGroupDao meetingGroupDao) {
		this.meetingGroupDao = meetingGroupDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public GroupPlanDao getGroupPlanDao() {
		return groupPlanDao;
	}

	public void setGroupPlanDao(GroupPlanDao groupPlanDao) {
		this.basicDao = groupPlanDao;
		this.groupPlanDao = groupPlanDao;
	}

	public GroupPlanDetailDao getGroupPlanDetailDao() {
		return groupPlanDetailDao;
	}

	public void setGroupPlanDetailDao(GroupPlanDetailDao groupPlanDetailDao) {
		this.groupPlanDetailDao = groupPlanDetailDao;
	}

	public GroupPlanMemberDao getGroupPlanMemberDao() {
		return groupPlanMemberDao;
	}

	public void setGroupPlanMemberDao(GroupPlanMemberDao groupPlanMemberDao) {
		this.groupPlanMemberDao = groupPlanMemberDao;
	}

	/** 根据会议ID查询出分组计划模版 */
	public List<GroupPlan> listByMeetingId(Long meetingId) throws ServiceException {
		String hql = "from GroupPlan where meetingId = " + meetingId;
		return groupPlanDao.getObjects(hql);
	}

	/** 保存导入的分组计划数据 */
	public String saveImportData(Map<String, List<ImportGroupPlanData>> importDataMap, GroupPlan groupPlan)
			throws ServiceException {
		int groupCount = 0;
		int memberCount = 0;

		// 查询出参加该会议的用户信息
		StringBuffer hql = new StringBuffer();
		hql.append("  SELECT meetingUser FROM  User meetingUser,MeetingMember meetingMember where meetingUser.id=meetingMember.userId  ");
		hql.append("  AND  meetingMember.meetingId=" + groupPlan.getMeetingId());

		List<User> meetingUserList = userDao.getMeetingMember(hql.toString());

		// 参加该会议没有成员信息，不再处理数据
		if (meetingUserList == null || meetingUserList.size() == 0) {
			log.warn("import group plan  info meeting's member list is empty");
			return null;
		}

		// <手机号，用户ID>转换的MAP
//		Map<String, Long> userMap = new HashMap<String, Long>();
//		for (User u : meetingUserList) {
//			userMap.put(u.getMobile(), u.getId());
//		}
		
		// 保存分组计划
		Long planId = groupPlanDao.add(groupPlan);

		for (Map.Entry<String, List<ImportGroupPlanData>> entry : importDataMap.entrySet()) {
			List<ImportGroupPlanData> detailList = entry.getValue();
			if (detailList != null && detailList.size() > 0) {
				// 保存分组
				ImportGroupPlanData tmp = detailList.get(0);
				GroupPlanDetail groupPlanDetail = new GroupPlanDetail();
				groupPlanDetail.setPlanId(planId);
				groupPlanDetail.setGroupName(tmp.getGroupName());
				groupPlanDetail.setGroupLeader(tmp.getGroupLeader());
				groupPlanDetail.setMeetingId(groupPlan.getMeetingId());
				groupPlanDetail.setDetail(tmp.getDetail());
				groupPlanDetail.setShowIndex(tmp.getShowIndex());
				Long groupId = groupPlanDetailDao.add(groupPlanDetail);
				groupCount++;
				for (ImportGroupPlanData member : detailList) {
					
					//从action传递过来的用户肯定不会重复，用户名重复了，必须输入手机号以便区分
					
					
					//根据用户名判断会议用户列表里有几个重名的用户
					int userNameCount = 0;
					Long uid = null;
					String userName = member.getUserName();
					for(User a : meetingUserList){
						if(a.getName().equalsIgnoreCase(userName)){ //用户名相同
							if (!StringUtil.isEmpty(member.getMobile())){//输入了手机号
								if( a.getMobile().equalsIgnoreCase(
											member.getMobile())) { //手机号相同
									uid = a.getId();
									userNameCount++;
								}else{ //输入的手机号和会议用户中的手机号没匹配上，有冲突，提示下
									
								}
							}else{
								//没有输入手机号，名字相同
								uid = a.getId();
								userNameCount++;
							}
						}
					}
					
					///输入了用户名没匹配上，或者手机号和会议用户中的手机号没匹配上，有冲突，提示下
					if(userNameCount == 0){
						//抛出异常，以回滚事物
						String msg = userName+",手机号["+member.getMobile()+"] ,不是会议用户或者手机号不正确，请检查姓名或手机号是否有误!";
						throw new ServiceException(msg);
					}
					
					//如果action传递过来的用户名不重复，但是会议用户里有重名的，也要输入手机号，以便区分
					if(userNameCount > 1){
						//抛出异常，以回滚事物
						String msg = userName+" 在会议用户中有重名的，请输入手机号以区分!";
						throw new ServiceException(msg);
					}
					
					if (uid != null) {
						GroupPlanMember groupPlanMember = new GroupPlanMember();
						groupPlanMember.setPlanId(planId);
						groupPlanMember.setGroupId(groupId);
						groupPlanMember.setMeetingId(groupPlan.getMeetingId());
						groupPlanMember.setMemberId(uid);
						groupPlanMember.setSortCode(member.getMemberSortCode());
						groupPlanMemberDao.add(groupPlanMember);
						memberCount++;
					}
				}
			}
		}

		String resultMsg = "成功导入个" + groupCount + "分组，" + memberCount + "个用户";
		log.debug("分组计划 {}", resultMsg);
		return resultMsg;
	}

	public void deleteById(GroupPlan groupPlan) throws ServiceException {
		String sql = "delete From GroupPlanMember where planId = " + groupPlan.getId();
		String sql1 = "delete From GroupPlanDetail where planId = " + groupPlan.getId();
		String sql2 = "delete From MeetingGroup where planId = " + groupPlan.getId();
		groupPlanDao.executeUpdate(sql, new HashMap());
		log.info(sql);
		groupPlanDao.executeUpdate(sql1, new HashMap());
		log.info(sql1);
		groupPlanDao.executeUpdate(sql2, new HashMap());
		log.info(sql2);
		groupPlanDao.delete(groupPlan);
	}

	/** 根据relationID查找当前选择的分组模版ID */
	public GroupPlan findByRelationId(Long relationId, int type) throws ServiceException {
		String hql = "from GroupPlan gp where gp.id in ( select mg.planId from MeetingGroup mg where mg.relationId = "
				+ relationId + " and mg.type = " + type + " )";
		List<GroupPlan> list = groupPlanDao.getObjects(hql);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	};
	
	/**根据类型和会议ID查找出所有的分组模版*/
	public List<GroupPlan> findPlanListByType(int type,Long meetingId) throws ServiceException{
		String hql = "from GroupPlan where planType = "+type+" and meetingId = "+meetingId;
		return groupPlanDao.getObjects(hql);
	};
	
	/**根据分组模版ID查找出所有的分组信息*/
	public List<GroupPlanDetail> findPlanDetailList(Long planId) throws ServiceException{
		String hql = "from GroupPlanDetail where planId = "+planId+"  order by showIndex,groupName ";
		return groupPlanDetailDao.getObjects(hql);
	};
	
	/**根据分组计划ID查询出所有的分组和组员信息*/
	public Map<GroupPlanDetail,List<JsonUser>> getGroupMemberInfo(Long planId) throws ServiceException{
		
		//查询出所有的分组
		String hql = "From GroupPlanDetail where planId = "+planId+" order by showIndex,groupName";
		List<GroupPlanDetail> groupList = groupPlanDetailDao.getObjects(hql);
		Map<GroupPlanDetail, List<JsonUser>> resultMap = new LinkedHashMap<GroupPlanDetail, List<JsonUser>>();
		for (GroupPlanDetail g : groupList) {
			resultMap.put(g, getMemberByGroupId(g.getId()));
		}
		return resultMap;
	}

	/** 根据分组ID查询出组员信息 */
	public List<JsonUser> getMemberByGroupId(Long groupId) throws ServiceException {
		String sql = "SELECT u.id,u.name,u.mobile,mm.mobile_is_display,mm.job,mm.job_is_display,u.gender from USER u ,meeting_member mm,group_plan_member gpm " +
		" WHERE u.id=mm.user_id AND mm.meeting_id=gpm.meeting_id AND u.id = gpm.member_id AND  gpm.group_id = :groupId" +
		" ORDER BY gpm.sort_code,u.name";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("groupId", groupId);
		List results = groupPlanDao.queryListSql(sql, -1, -1, properties);
		List<JsonUser> userList = new ArrayList<JsonUser>();
		for (Object result : results) {
			JsonUser user = new JsonUser();
			Object[] resultArray = (Object[]) result;
			user.setId(Long.valueOf("" + resultArray[0]));
			user.setName("" + resultArray[1]);
			user.setMobile("" + resultArray[2]);
			user.setIsShowMobile("" + resultArray[3]);
			user.setJob(""+resultArray[4]);
			user.setIsShowJob(""+resultArray[5]);
			user.setGender(Integer.valueOf(resultArray[6]==null?"0":""+resultArray[6]));
			userList.add(user);
		}
		return userList;
	}
	
	/** 根据分组ID分页查询出组员信息 */
	public Pager<JsonUser> getMemberPagerByGroupId(Long groupId,int currentPage,int pageSize) throws ServiceException {
		String sql = "SELECT u.id,u.name,u.mobile,mm.mobile_is_display,mm.job,mm.job_is_display from USER u ,meeting_member mm,group_plan_member gpm " +
				" WHERE u.id=mm.user_id AND mm.meeting_id=gpm.meeting_id AND u.id = gpm.member_id AND  gpm.group_id = :groupId" +
				" ORDER BY gpm.sort_code,u.name";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("groupId", groupId);
		
		Pager pager = groupPlanDao.findPagerBySql(sql, currentPage, pageSize, properties);
		
		List results = pager.getPageRecords();
		List<JsonUser> userList = new ArrayList<JsonUser>();
		for (Object result : results) {
			JsonUser user = new JsonUser();
			Object[] resultArray = (Object[]) result;
			user.setId(Long.valueOf("" + resultArray[0]));
			user.setName("" + resultArray[1]);
			user.setMobile("" + resultArray[2]);
			user.setIsShowMobile("" + resultArray[3]);
			user.setJob(""+resultArray[4]);
			user.setIsShowJob(""+resultArray[5]);
			userList.add(user);
		}
		pager.setPageRecords(userList);
		return pager;
	}

	/** 根据userId,relationId 和type查询出组员信息 */
	public Map<GroupPlanDetail, List<JsonUser>> getGroupMemberInfo(Long userId, Integer type, Long relationId)
	throws ServiceException {
		String hql = "from GroupPlanDetail where " + "id in "
		+ "(SELECT groupId FROM GroupPlanMember WHERE memberId = :userId " + "and planId in "
		+ "(select planId from MeetingGroup where relationId = :relationId and type = :type)" + ")";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("userId", userId);
		properties.put("relationId", relationId);
		properties.put("type", type);
		List<GroupPlanDetail> groupList = groupPlanDetailDao.getObjects(hql, properties);
		Map<GroupPlanDetail, List<JsonUser>> resultMap = new HashMap<GroupPlanDetail, List<JsonUser>>();
		for (GroupPlanDetail g : groupList) {
			resultMap.put(g, getMemberByGroupId(g.getId()));
		}
		return resultMap;
	}
	
	/** 根据userId,relationId 和type查询出分组信息 */
	public GroupPlanDetail getGroupDetailInfo(Long userId, Integer type, Long relationId)
			throws ServiceException {
		log.debug("getGroupDetailInfo userId={},type={},relationId={}",new String[]{String.valueOf(userId),String.valueOf(type),String.valueOf(relationId)});
		String hql = "from GroupPlanDetail where " + "id in "
				+ "(SELECT groupId FROM GroupPlanMember WHERE memberId = :userId " + "and planId in "
				+ "(select planId from MeetingGroup where relationId = :relationId and type = :type)" + ")";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("userId", userId);
		properties.put("relationId", relationId);
		properties.put("type", type);
		List<GroupPlanDetail> groupList = groupPlanDetailDao.getObjects(hql, properties);
		if(groupList != null && groupList.size() > 0){
			
			//查询出对哪些级别的用户开放用户分组信息，是否对当前用户开放
			GroupPlanDetail gpd = groupList.get(0);
			GroupPlan plan = groupPlanDao.findById(gpd.getPlanId(), GroupPlan.class);
			String sql = "select memberLevel from MeetingMember where meetingId = "+gpd.getMeetingId()+" and userId = "+userId;
			
			String userLevel = String.valueOf( meetingMemberDao.getUniqueBeanResult(sql));
			
			String isOpen = plan.getIsOpen() == null ? Constants.GROUP_PLAN_STATE.CLOSE
					: plan.getIsOpen().contains(String.valueOf(userLevel)) ? Constants.GROUP_PLAN_STATE.OPEN
							: Constants.GROUP_PLAN_STATE.CLOSE;
			gpd.setIsOpen(isOpen);
			return gpd;
		}
		return null;
	}

	public void modifyGroupPlan(GroupPlan groupPlan) throws ServiceException {
		GroupPlan old = groupPlanDao.findById(groupPlan.getId(), GroupPlan.class);
		if(old==null ){
			log.warn("modifyGroupPlan find none.");
			return ;
		}
		if(!groupPlan.getPlanType().equals(old.getPlanType())){
			String hql = "delete from MeetingGroup where relationId = "+old.getId()+" and type = "+old.getPlanType();
			log.info(hql);
			groupPlanDao.executeUpdate(hql, new HashMap());
		}
		old.setPlanName(groupPlan.getPlanName());
		old.setPlanDesc(groupPlan.getPlanDesc());
		old.setPlanType(groupPlan.getPlanType());
		old.setIsOpen(groupPlan.getIsOpen());
		groupPlanDao.modify(old);
	}

	public void deleteGroupById(Long groupId) throws ServiceException {
		String sql = "delete From GroupPlanMember where groupId = " + groupId;
		String sql1 = "delete From GroupPlanDetail where id = " + groupId;
		groupPlanDao.executeUpdate(sql, new HashMap());
		log.info(sql);
		groupPlanDao.executeUpdate(sql1, new HashMap());
		log.info(sql1);
	}
	
	/**根据分组模版ID查询当前未分组的成员信息*/
	public List<JsonUser> findMemberListNotInGroup(Long planId,Long meetingId,String name,String mobile,String city,String delegation)  throws ServiceException {
		StringBuffer sql = new StringBuffer("SELECT u.id,u.name,u.mobile,mm.mobile_is_display,mm.job,mm.job_is_display from USER u ,meeting_member mm " +
				" WHERE u.id=mm.user_id AND mm.meeting_id = :meetingId AND mm.user_id not in (select gpmb.member_id from group_plan_member gpmb where gpmb.plan_id =:planId) "
		);
		
		Map<String, Object> properties = new HashMap<String, Object>();
		if(name != null && name.length() > 0){
			sql.append(" AND u.name like :name ");
			properties.put("name", "%"+name.trim()+"%");
		}
		if(mobile != null && mobile.length() > 0){
			sql.append(" AND u.mobile like :mobile ");
			properties.put("mobile", "%"+mobile.trim()+"%");
		}
		
		if (StringUtil.isNotEmpty(city)) {
			sql.append(" AND mm.city like :city ");
			properties.put("city", "%" + city.trim() + "%");
		}
		if (StringUtil.isNotEmpty(delegation)) {
			sql.append(" AND mm.delegation like :delegation ");
			properties.put("delegation", "%" + delegation.trim() + "%");
		}
		
		
		sql.append(" ORDER BY mm.sort_code,u.name ");
		properties.put("meetingId", meetingId);
		properties.put("planId", planId);
		List results = groupPlanDao.queryListSql(sql.toString(), -1, -1, properties);
		List<JsonUser> userList = new ArrayList<JsonUser>();
		for (Object result : results) {
			JsonUser user = new JsonUser();
			Object[] resultArray = (Object[]) result;
			user.setId(Long.valueOf("" + resultArray[0]));
			user.setName("" + resultArray[1]);
			user.setMobile("" + resultArray[2]);
			user.setIsShowMobile("" + resultArray[3]);
			user.setJob(""+resultArray[4]);
			user.setIsShowJob(""+resultArray[5]);
			userList.add(user);
		}
		return userList;
	}
	
	
	/**将选择的成员加入该分组*/
	public void addMember2Group(String memberIds,GroupPlanDetail groupPlanDetail)  throws ServiceException {
		if(memberIds == null || memberIds.length() == 0 || groupPlanDetail == null){
			return ;
		}
		
		String[] memberIdArray = memberIds.split(",");
		if(memberIdArray.length == 0){
			return ;
		}
		
		String hql = "SELECT MAX(sortCode) FROM GroupPlanMember WHERE groupId = :groupId";
		HashMap<String,Object> properties = new HashMap<String,Object>();
		properties.put("groupId", groupPlanDetail.getId());
		Object sortCode = groupPlanMemberDao.getUniqueBeanResult(hql, properties);
		Integer maxSortCode = 0;
		if(sortCode != null){
			maxSortCode = (Integer)sortCode;
		}
		
		for(String id : memberIdArray){
			id = id.trim();
			if(id == "") continue;
			GroupPlanMember member = new GroupPlanMember();
			member.setMeetingId(groupPlanDetail.getMeetingId());
			member.setGroupId(groupPlanDetail.getId());
			member.setMemberId(Long.valueOf(id));
			member.setPlanId(groupPlanDetail.getPlanId());
			member.setSortCode(++maxSortCode);
			groupPlanMemberDao.add(member);
		}
		
	}
	
	
	/**将选择的成员移到其他分组*/
	public void moveMemberFromGroup(Long memberId,Long groupId,Long otherGroupId)  throws ServiceException {
		String hql = "update GroupPlanMember set groupId = :otherGroupId  WHERE groupId = :groupId and memberId = :memberId";
		HashMap<String,Object> properties = new HashMap<String,Object>();
		properties.put("otherGroupId", otherGroupId);
		properties.put("memberId", memberId);
		properties.put("groupId", groupId);
		groupPlanMemberDao.executeUpdate(hql, properties);
	}
	
	
	/**将选择的成员移出该分组*/
	public void removeMemberFromGroup(Long memberId,Long groupId)  throws ServiceException {
		String hql = "delete FROM GroupPlanMember WHERE groupId = :groupId and memberId = :memberId";
		HashMap<String,Object> properties = new HashMap<String,Object>();
		properties.put("memberId", memberId);
		properties.put("groupId", groupId);
		groupPlanMemberDao.executeUpdate(hql, properties);
	}
	
	
	/**将选择的成员排序*/
	public void sortMemberForGroup(Long memberId,Long groupId,String operator)  throws ServiceException {
		log.debug("sortMemberForGroup memberId ={}, groupId ={}, operator ={}",new String[]{""+memberId, ""+groupId, operator});
		
		if(operator == null || operator.length() == 0){
			return ;
		}
		
		operator = operator.toLowerCase();
		String hql = "FROM GroupPlanMember WHERE groupId = :groupId and memberId = :memberId";
		HashMap<String,Object> properties = new HashMap<String,Object>();
		properties.put("memberId", memberId);
		properties.put("groupId", groupId);
		List<GroupPlanMember> groupPlanMemberList = groupPlanMemberDao.getObjects(hql, properties);
		
		if(groupPlanMemberList.size() > 0){
			GroupPlanMember member = groupPlanMemberList.get(0);
			log.debug("sortMemberForGroup member = {}",member);
			Integer sortCode = member.getSortCode();
			if(sortCode == null){
				sortCode = 0;
			}
			if("first".equals(operator)){
				hql = "SELECT MIN(sortCode) FROM GroupPlanMember WHERE groupId = :groupId";
				properties = new HashMap<String,Object>();
				properties.put("groupId", groupId);
				Object mnsortCode = groupPlanMemberDao.getUniqueBeanResult(hql, properties);
				Integer minSortCode = 0;
				if(mnsortCode != null){
					minSortCode = (Integer)mnsortCode;
				}
				
				log.debug("sortMemberForGroup minSortCode = {}",minSortCode);
				if(minSortCode-1 < 1){
					minSortCode = 2;
					hql = "update GroupPlanMember set sortCode = sortCode+1 where groupId = :groupId";
					properties.put("groupId", groupId);
					groupPlanMemberDao.executeUpdate(hql, properties);
				}
				member.setSortCode(minSortCode-1);
				groupPlanMemberDao.modify(member);
				
			}else if("pre".equals(operator)){
				if(sortCode-1 < 1){
					sortCode = 2;
					hql = "update GroupPlanMember set sortCode = sortCode+1 where groupId = :groupId";
					properties.put("groupId", groupId);
					groupPlanMemberDao.executeUpdate(hql, properties);
				}
				member.setSortCode(sortCode-1);
				groupPlanMemberDao.modify(member);
			}else if("next".equals(operator)){
				member.setSortCode(sortCode+1);
				groupPlanMemberDao.modify(member);
				
			}else if("last".equals(operator)){
				hql = "SELECT MAX(sortCode) FROM GroupPlanMember WHERE groupId = :groupId";
				properties = new HashMap<String,Object>();
				properties.put("groupId", groupId);
				Object mxsortCode = groupPlanMemberDao.getUniqueBeanResult(hql, properties);
				Integer maxSortCode = 0;
				if(mxsortCode != null){
					maxSortCode = (Integer)mxsortCode;
				}
				
				log.debug("sortMemberForGroup maxSortCode = {}",maxSortCode);
				member.setSortCode(maxSortCode+1);
				groupPlanMemberDao.modify(member);
			}
		}
	}
	
	
}
