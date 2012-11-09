package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.dao.DinnerTableDao;
import com.wondertek.meeting.dao.GroupPlanDao;
import com.wondertek.meeting.dao.MeetingDinnerDao;
import com.wondertek.meeting.dao.MeetingMemberDao;
import com.wondertek.meeting.dao.UserDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.DinnerInfo;
import com.wondertek.meeting.model.DinnerTable;
import com.wondertek.meeting.model.GroupPlan;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.model.MeetingDinner;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.UserTableInfo;
import com.wondertek.meeting.model.UserTableInfoWithDate;
import com.wondertek.meeting.service.MeetingDinnerService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.StringUtil;

/**
 * 
 * @author John Tang
 */
public class MeetingDinnerServiceImpl extends BaseServiceImpl<MeetingDinner, Long> implements MeetingDinnerService {

	private MeetingDinnerDao meetingDinnerDao;
	
	private UserDao userDao;
	
	private DinnerTableDao dinnerTableDao;
	
	private GroupPlanDao groupPlanDao;
	
	private MeetingMemberDao meetingMemberDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public DinnerTableDao getDinnerTableDao() {
		return dinnerTableDao;
	}

	public void setDinnerTableDao(DinnerTableDao dinnerTableDao) {
		this.dinnerTableDao = dinnerTableDao;
	}

	public MeetingDinnerDao getMeetingDinnerDao() {
		return meetingDinnerDao;
	}

	public void setMeetingDinnerDao(MeetingDinnerDao meetingDinnerDao) {
		super.basicDao = meetingDinnerDao; //给basicDao赋值
		this.meetingDinnerDao = meetingDinnerDao;
	}

	public List<MeetingDinner> findDinnerList(String address,long meetingId,String dinnerDate)
			throws ServiceException {
		StringBuffer hql = new StringBuffer("from MeetingDinner where meetingId = :meetingId "); 
		Map<String,Object> properties = new HashMap<String,Object>();
		if( !StringUtil.isEmpty(address)){
			hql.append(" and address like :address");
			properties.put("address", "%"+address+"%");
		}
		if( !StringUtil.isEmpty(dinnerDate)){
			hql.append(" and dinnerDate = :dinnerDate");
			properties.put("dinnerDate", dinnerDate);
		}
		
		hql.append(" order by dinnerDate,section");
		properties.put("meetingId",meetingId);
		return this.getObjects(hql.toString(), properties);
	}

	/**查询用户所在会议的分桌信息*/
	/*SELECT md.dinner_date,md.start_time,md.end_time,md.dinner_section,md.dinner_address,md.dinner_type, dt.table_code
		FROM meeting_dinner md 
		LEFT JOIN dinner_table dt ON dt.dinner_id = md.dinner_id 
		AND dt.member_id = 35 WHERE md.meeting_id = 3  ORDER BY md.dinner_date,md.dinner_section,md.start_time*/
	public List<UserTableInfoWithDate> findMemberDinnerList(long meetingId, long memberId)
			throws ServiceException {
		
		//查出所有的就餐信息
		//根据就餐信息查询出该用户的分桌信息
		//有分桌信息的就餐信息和自助餐的就餐信息
		
		String sql = "SELECT md.dinner_id,md.dinner_date,md.start_time,md.end_time,md.dinner_section,md.dinner_address,md.dinner_type,md.comments, a.group_name "
		+" FROM meeting_dinner md" 
		+" LEFT JOIN (SELECT gpd.group_name,mg.relation_id FROM  group_plan_detail gpd,group_plan_member gpm,meeting_group mg "
		+" WHERE  mg.type = "+ Constants.GROUP_PLAN_TYPE.DINNER
		+" AND gpm.member_id = " +memberId
		+" AND mg.plan_id = gpd.plan_id AND gpd.group_id = gpm.group_id ) a ON a.relation_id = md.dinner_id "
		+" WHERE md.meeting_id = "+meetingId
		+" ORDER BY md.dinner_date,md.dinner_section,md.start_time";
		
		List results = meetingDinnerDao.queryListSql(sql, -1, -1, new HashMap());
		
		//按照会议日期进行分组排序
		Map<String,List<DinnerInfo>> resultMap = new LinkedHashMap<String,List<DinnerInfo>>();
		
        for (Object result : results) {
        	//将查询出来的数据转换成对象
        	Object[] resultArray = (Object[]) result;
            DinnerInfo di = new DinnerInfo();
            di.setDinnerId(Long.valueOf(""+ resultArray[0]));
            di.setDinnerDate(DateUtil.convertDateFormat(""+ resultArray[1],"yyyy年MM月dd日"));
            di.setTime(""+ resultArray[2]+" - "+resultArray[3]);
            di.setScetion(""+resultArray[4]);
            di.setAddress(""+resultArray[5]);
            di.setType((null == resultArray[6] || resultArray[6] == "") ? "其他" : Constants.dinnerTypeIdMap.get(""+resultArray[6]).getName() );
            //备注
            di.setComments(""+(null == resultArray[7] ? "":resultArray[7]));
            //处理自助餐的座位信息
            
            String dinnerTable = "自选座位";
            if((resultArray[8] != null && resultArray[8] != "") ){
            	dinnerTable = ""+resultArray[8];
            }else{
            	//当前用户未分组，查询该用户是否有权限查看所有分组的信息
            	//该用餐是否已分组
            	GroupPlan groupPlan = meetingDinnerDao.getGroupPlanByUserRights(meetingId, Constants.GROUP_PLAN_TYPE.DINNER, memberId, di.getDinnerId());
            	log.debug("groupPlan = {}",groupPlan);
            	if(groupPlan != null){
	            	di.setGroupPlanId(groupPlan.getId());
	            	di.setGroupPlanName(groupPlan.getPlanName());
            	}else{
            		di.setGroupPlanId(null);
            		di.setGroupPlanName("");
            	}
            }
            
            //added by zhangguojing at 2012/4/24, 如果是桌宴且未分组，则显示为”指定座位“
            if("3".equals(String.valueOf(resultArray[6])) && "自选座位".equals(dinnerTable)) {
            	dinnerTable = "指定座位";
            }
            di.setDinnerTable(dinnerTable);
            //按日期归类排序
            List<DinnerInfo> diList =  resultMap.get(di.getDinnerDate());
            
            if(diList == null){
            	List<DinnerInfo> temp = new ArrayList<DinnerInfo>();
            	resultMap.put(di.getDinnerDate(), temp);
            	diList = resultMap.get(di.getDinnerDate());
            }
            diList.add(di);
        }
        
        //将resultMap数据转换为UserTableInfoWithDate List
        
        List<UserTableInfoWithDate> resultList = new ArrayList<UserTableInfoWithDate>();
        for (Map.Entry<String, List<DinnerInfo>> entry : resultMap.entrySet()) {
        	UserTableInfoWithDate uti = new UserTableInfoWithDate();
//        	log.debug("dinner date {}",entry.getKey());
        	uti.setDate(entry.getKey());
        	uti.setInfo(entry.getValue());
        	resultList.add(uti);
        }
        
        return resultList;
	}

	/**根据dinnerId删除dinner信息和分桌信息*/
	public void deleteDinnerById(long dinnerId) throws ServiceException {
		String hql = "delete from DinnerTable where dinnerId = "+dinnerId;
		meetingDinnerDao.executeUpdate(hql, new HashMap());
		MeetingDinner dinner = new MeetingDinner();
		dinner.setId(dinnerId);
		meetingDinnerDao.delete(dinner);
		log.info("删除用餐信息成功 dinnerId = {}",dinnerId);
	}

	/**查询该会议该用餐信息已分桌的用户*/
//	SELECT * FROM USER WHERE id IN (SELECT user_id FROM meeting_member WHERE meeting_id = 3  AND user_id IN (SELECT member_id FROM dinner_table WHERE dinner_id = 1))
	
	public List<UserTableInfo> getUserListInDinner(Long meetingId,Long dinnerId)  throws ServiceException {
		return userDao.getUserTableInfo(meetingId, dinnerId);
	}
	
	/**查询该会议该用餐信息未分桌的用户*/
//	SELECT * FROM USER WHERE id IN (SELECT user_id FROM meeting_member WHERE meeting_id = 3 and user_id not in (SELECT member_id FROM dinner_table WHERE dinner_id = 1 ))
	public List<JsonUser> getUserListNotInDinner(Long meetingId,Long dinnerId)  throws ServiceException {
		String hql = "FROM User u WHERE u.id IN (SELECT mm.userId FROM MeetingMember mm WHERE mm.meetingId = "+meetingId
			+"  AND mm.userId not IN (SELECT dt.memberId FROM DinnerTable dt WHERE dt.dinnerId = "+dinnerId+"))";
		List<User> userList = userDao.getObjects(hql);
		List<JsonUser> jsonUserList = new ArrayList<JsonUser>(); 
		for(User u:userList){
			JsonUser ju = new JsonUser();
			ju.setId(u.getId());
			ju.setName(u.getName());
			ju.setMobile(u.getMobile());
			jsonUserList.add(ju);
		}
		return jsonUserList;
	}
	
	/**将用户加入该分桌信息表dinner_table*/
//	insert into dinner_table (dinner_id,member_id,tablecode,meeting_id)
	public void addUserToDinnerTable(String memberIds,String tableCode,Long dinnerId,Long meetingId) throws ServiceException{
		if(memberIds == null || memberIds.length() == 0){
			log.warn("addUserToDinnerTable memberIds is empty!");
			return ;
		}
		/*
		不清除之前的分桌信息
		 * String hql = "delete from DinnerTable where dinnerId = :dinnerId and meetingId = :meetingId and tableCode = :tableCode ";
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("dinnerId", dinnerId);
		properties.put("meetingId", meetingId);
		properties.put("tableCode", tableCode);
		int effected = dinnerTableDao.executeUpdate(hql, properties);
		log.debug("remove origin dinner table effected {}",effected);
		*/
		String[] ids = memberIds.split(",");
		for(String memberId : ids){
			DinnerTable dt = new DinnerTable();
			dt.setDinnerId(dinnerId);
			dt.setMeetingId(meetingId);
			dt.setTableCode(tableCode);
			dt.setMemberId(Long.valueOf(memberId));
			dinnerTableDao.add(dt);
		}
	}
	
	/**将用户移出该分桌信息表dinner_table*/
//	delete from dinner_table where dinner_id = ? and member_id = ?
	public void removeUserFromDinnerTable(Long dinnerId,String memberIds) throws ServiceException{
		if(memberIds == null || memberIds.length() == 0){
			log.warn("removeUserFromDinnerTable memberIds is empty!");
			return ;
		}
		
		String hql = "delete from DinnerTable where dinnerId = :dinnerId and memberId in ("+memberIds+") ";
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("dinnerId", dinnerId);
//		properties.put("memberIds", memberIdsList);
		dinnerTableDao.executeUpdate(hql, properties);
	}
	
	/**保存导入的用户分桌信息*/
	public void saveImportDinnerInfo(List<UserTableInfo> importUserTableInfoList,MeetingDinner dinner)
			throws ServiceException {
		if(importUserTableInfoList == null || importUserTableInfoList.isEmpty() ){
			log.warn("import user dinner info param list is empty");
			return ;
		}
		
		//把该用餐信息的用户都查询出来备用
		StringBuffer hql = new StringBuffer();
		hql.append("  SELECT meetingUser FROM  User meetingUser,MeetingMember meetingMember where meetingUser.id=meetingMember.userId  ");
		hql.append("  AND  meetingMember.meetingId=" + dinner.getMeetingId());
		
		List<User> meetingUserList = userDao.getMeetingMember(hql.toString());
		
		//参加该会议没有成员信息，不再处理数据
		if(meetingUserList == null || meetingUserList.size() == 0){
			log.warn("import user dinner info meeting's member list is empty");
			return ;
		}
		
		//<手机号，用户ID>转换的MAP
		Map<String,Long> userMap = new HashMap<String,Long>();
		for(User u : meetingUserList){
			userMap.put(u.getMobile(), u.getId());
		}
		
		String sql = "from DinnerTable where dinnerId = "+dinner.getId()+" and memberId = ";
		//如果数据量很大，则需要手动控制每次提交的记录数，当然一般会议参会人员不会很多
		for(UserTableInfo uti : importUserTableInfoList){
			//根据手机号查询userId
			Long userId = userMap.get(uti.getMobile());
			if(userId == null){
				log.warn("导入用户分桌信息，用户不在该会议中忽略{}",uti.getMobile());
				continue;
			}
			
			//userId和dinnerId唯一确定一次用餐信息
			List<DinnerTable> dinnerTableList = dinnerTableDao.getObjects(sql+userId);
			if(dinnerTableList.size() > 0){
				//记录已有吗？更新
				DinnerTable dinnerTable = dinnerTableList.get(0);
				dinnerTable.setTableCode(uti.getTableCode());
				dinnerTableDao.modify(dinnerTable);
			}else{
				//没有，保存
				DinnerTable dinnerTable = new DinnerTable();
				dinnerTable.setTableCode(uti.getTableCode());
				dinnerTable.setDinnerId(dinner.getId());
				dinnerTable.setMeetingId(dinner.getMeetingId());
				dinnerTable.setMemberId(userId);
				dinnerTable.setTableCode(uti.getTableCode());
				dinnerTableDao.add(dinnerTable);
			}
			
		}
		
	}

	public void saveImportDinnerPlan(List<MeetingDinner> dinnerList)
			throws ServiceException {
		for(MeetingDinner dinner:dinnerList){
			meetingDinnerDao.add(dinner);
		}
	}

	public GroupPlanDao getGroupPlanDao() {
		return groupPlanDao;
	}

	public void setGroupPlanDao(GroupPlanDao groupPlanDao) {
		this.groupPlanDao = groupPlanDao;
	}

	public MeetingMemberDao getMeetingMemberDao() {
		return meetingMemberDao;
	}

	public void setMeetingMemberDao(MeetingMemberDao meetingMemberDao) {
		this.meetingMemberDao = meetingMemberDao;
	}
	
	

}
