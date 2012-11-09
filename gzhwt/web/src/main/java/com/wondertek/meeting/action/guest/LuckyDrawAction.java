
package com.wondertek.meeting.action.guest;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.LuckyDraw;
import com.wondertek.meeting.model.LuckyHistory;
import com.wondertek.meeting.model.LuckyUser;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingPost;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.LuckyDrawService;
import com.wondertek.meeting.service.LuckyHistoryService;
import com.wondertek.meeting.service.LuckyUserService;
import com.wondertek.meeting.service.MeetingPostService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.StringUtil;

/**
* @ClassName: LuckyDrawAction 
* @Description: 抽奖管理
* @author zouxiaoming
* @date Mar 8, 2012 10:27:24 AM 
*
 */
@SuppressWarnings("serial")
public class LuckyDrawAction extends BaseAction implements ModelDriven<LuckyDraw> {
	private Log log = LogFactory.getLog(LuckyDrawAction.class);
	private LuckyDraw lucky =new LuckyDraw(); 
	private LuckyDrawService luckyDrawService;
	private LuckyHistoryService luckyHistoryService;
	private UserService userService;
	private MeetingService meetingService;
	@Autowired
	private MeetingPostService meetingPostService;
	private LuckyUserService  luckyUserService;
	private Long meetingId;
	private Long userId;
	private String flag;
	private String mobile; // 根据手机号搜索记录
	private String username;  // 根据用户姓名搜索记录
	private Long[] users;
	
	
	/**
	 * 跳转到添加抽奖页面
	 * @return
	 */
	public String switchToAddLucky(){
		Meeting meeting=new Meeting();
		meeting.setId(meetingId);
		List<User> list=this.meetingService.getMeetingUsers(meeting);
		List<LuckyHistory> users=this.luckyHistoryService.findLuckyUserByMeetgingId(meetingId);
		List<User> customUsers=this.changeUsers(users);
		if(customUsers!=null&&customUsers.size()>0){
			for(User user:customUsers){
				if(list.contains(user)){
					list.remove(user);
				}
			}
		}
		ActionContext.getContext().put("list",list);
		ActionContext.getContext().put("users", users);
		return "addLucky";
	}
	/**
	 * 添加/修改抽奖规则
	 * @return
	 * @throws ServiceException 
	 */
	public String addLuckyDraw() throws ServiceException{
		log.info("guest:"+getLucky());
		List<MeetingPost> list=null;
		Meeting meeting=this.meetingService.findById(meetingId);
		getLucky().setMeeting(meeting);
		if(lucky.getChecked()==1){
			list=this.meetingPostService.findPostUser(meetingId);
		}
		this.getLuckyDrawService().saveLucky(lucky, users,list);
		return this.findAllLucky();
	}

	/**
	 * 查出所有的抽奖规则  关联会议  
	 * @return
	 * @throws ServiceException 
	 */
	public String findAllLucky() throws ServiceException{
		log.info("meetingId:"+meetingId);
		Pager<LuckyDraw> pager=null;
		pager=this.getLuckyDrawService().findAllLucky(meetingId, currentPage, pageSize);
		if(!StringUtil.isEmpty(flag)&&flag.equals("ajax")){
			if(pager.getPageRecords()!=null){
				Integer size=pager.getPageRecords().size();
				resultMap.put("size", size);
			}
			return SUCCESS;
		}
		ActionContext.getContext().put("pager", pager);
		log.info("size:"+pager.getPageRecords().size());
		return "luckyList";
	}
	

	/**
	 * 查看某一个规则的详细信息
	 * @return
	 * @throws ServiceException 
	 */
	public String findLuckyById() throws ServiceException{
		lucky=this.getLuckyDrawService().findById(getLucky().getId());
		List<User> list=null;
		log.info("****************"+lucky.getChecked());
		if(lucky.getChecked()!=null&&lucky.getChecked()==2){
			list=this.meetingService.getMeetingUsers(lucky.getMeeting());
			List<LuckyUser> listUser=this.luckyUserService.findLuckyUserByLuckyId(lucky.getId());
			List<User> customUsers=this.changeUsers1(listUser);
			if(list!=null&&customUsers!=null&&customUsers.size()>0){
				for(User user:customUsers){
					
					int index=list.indexOf(user);
				
					if(index>=0){
						list.get(index).setChecked(1);
					}
				}
			}
		}
		ActionContext.getContext().put("list", list);
		return "addLucky";
	}
	
	/**
	 * 删除某一个抽奖规则信息
	 * @return
	 * @throws ServiceException 
	 */
	public String deleteLucky() throws ServiceException{
		lucky=this.getLuckyDrawService().findById(getLucky().getId());
		if(getLucky()!=null){
			this.getLuckyDrawService().deleteLucky(lucky);
		}
		return null;
	}
	
	/**
	 * 插入中奖历史记录
	 * @return
	 * @throws ServiceException
	 */
	public String addLuckyHistory() throws ServiceException{
		LuckyHistory history=new LuckyHistory();
		history.setMeeting(this.meetingService.findById(meetingId));
		history.setLucky(this.luckyDrawService.findById(lucky.getId()));
		history.setAdminUser(this.getAdminUserFromSession());
		history.setUser(this.userService.findById(getUserId()));
		this.luckyHistoryService.saveOrUpdate(history);
		resultMap.put("retmsg", "ok");
		return SUCCESS;
	}
	
	/**
	 * 清除某一个奖项的历史记录
	 * @return
	 */
	public String clearLuckyHistory(){
		try {
			this.luckyHistoryService.clearLuckyHistory(meetingId, lucky.getId());
			resultMap.put("retmsg", "ok");
		} catch (Exception e) {
			resultMap.put("retmsg", "fail");
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	/**
	 * 查看中奖历史信息
	 * @return
	 * @throws ServiceException
	 */
	public String findLuckyHistory() throws ServiceException{
		Pager<LuckyHistory> pager=null;
		pager=this.luckyHistoryService.findAllLuckyHistory(meetingId, currentPage, pageSize, mobile);
		ActionContext.getContext().put("pager", pager);
		return "luckyHistoryList";
	}
	
	/**
	 * 开始抽奖
	 * @return
	 * @throws ServiceException 
	 */
	public String beginLucky() throws ServiceException{
		Pager<LuckyDraw> pager=null;
		pager=this.getLuckyDrawService().findAllLucky(meetingId, currentPage, 30);
		ActionContext.getContext().put("pager",pager);
		return "beginLucky";
	}
	
	/**
	 * 获取参会人员列表
	 * @return
	 */
	public String getMeetingUsers(){
		List<User> customUsers=null;
		List<User> userList=null;
		if(lucky.getId()>0){
			List<LuckyUser> listUser=this.luckyUserService.findLuckyUserByLuckyId(lucky.getId());//查询用户定制的抽奖人
			if(listUser!=null&&listUser.size()>0){
				customUsers=new ArrayList<User>();
				for(LuckyUser lu:listUser){
					customUsers.add(lu.getUser());
				}
				List<LuckyHistory> users=this.luckyHistoryService.findLuckyUserByMeetgingId(meetingId); // 查询已经中奖人
				userList=this.changeUsers(users);
				if(userList!=null&&userList.size()>0){
					for(User u:userList){  // 去掉已经中奖的人
						if(customUsers.contains(u)){
							customUsers.remove(u);
						}
					}
				}
			}
		}
		if(customUsers!=null&&customUsers.size()>0){
			resultMap.put("list",customUsers);
		}else{
			Meeting meeting=new Meeting();
			meeting.setId(meetingId);
			List<User> list=this.meetingService.getMeetingUsers(meeting);
			List<LuckyHistory> users=this.luckyHistoryService.findLuckyUserByMeetgingId(meetingId); // 查询已经中奖人
			userList=this.changeUsers(users);
			if(userList!=null&&userList.size()>0){
				for(User u:userList){  // 去掉已经中奖的人
					if(list.contains(u)){
						list.remove(u);
					}
				}
			}
			resultMap.put("list",list);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取参会人员列表 分页和搜索 AJAX
	 * @return
	 * @throws ServiceException 
	 */
	public String findPagerMeetingUsers() throws ServiceException{
		Meeting meeting=new Meeting();
		meeting.setId(meetingId);
		List<User> list=this.meetingService.getMeetingUsers(meeting);
		List<LuckyHistory> users=this.luckyHistoryService.findLuckyUserByMeetgingId(meetingId);
		List<User> customUsers=this.changeUsers(users);
		if(customUsers!=null&&customUsers.size()>0){
			for(User user:customUsers){
				if(list.contains(user)){
					list.remove(user);
				}
			}
		}
		ActionContext.getContext().put("list",list);
		return "findUsersAjax";
	}
	
	/**
	 * @param meetingService
	 */
	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	/**
	 * @Description
	 * @return the meetingService
	 */
	public MeetingService getMeetingService() {
		return meetingService;
	}

	/**
	 * @param meetingId
	 */
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @Description
	 * @return the meetingId
	 */
	public Long getMeetingId() {
		return meetingId;
	}

	/**
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @Description
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param luckyDrawService
	 */
	public void setLuckyDrawService(LuckyDrawService luckyDrawService) {
		this.luckyDrawService = luckyDrawService;
	}
	/**
	 * @Description
	 * @return the luckyDrawService
	 */
	public LuckyDrawService getLuckyDrawService() {
		return luckyDrawService;
	}
	/**
	 * @param lucky
	 */
	public void setLucky(LuckyDraw lucky) {
		this.lucky = lucky;
	}
	/**
	 * @Description
	 * @return the lucky
	 */
	public LuckyDraw getLucky() {
		return lucky;
	}
	@Override
	public LuckyDraw getModel() {
		return lucky;
	}
	/**
	 * @param luckyHistoryService
	 */
	public void setLuckyHistoryService(LuckyHistoryService luckyHistoryService) {
		this.luckyHistoryService = luckyHistoryService;
	}
	/**
	 * @Description
	 * @return the luckyHistoryService
	 */
	public LuckyHistoryService getLuckyHistoryService() {
		return luckyHistoryService;
	}
	/**
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @Description
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param userService
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	/**
	 * @Description
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}
	/**
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @Description
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param meetingPostService
	 */
	public void setMeetingPostService(MeetingPostService meetingPostService) {
		this.meetingPostService = meetingPostService;
	}
	/**
	 * @Description
	 * @return the meetingPostService
	 */
	public MeetingPostService getMeetingPostService() {
		return meetingPostService;
	}
	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @Description
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param users
	 */
	public void setUsers(Long[] users) {
		this.users = users;
	}
	/**
	 * @Description
	 * @return the users
	 */
	public Long[] getUsers() {
		return users;
	}
	/**
	 * @param luckyUserService
	 */
	public void setLuckyUserService(LuckyUserService luckyUserService) {
		this.luckyUserService = luckyUserService;
	}
	/**
	 * @Description
	 * @return the luckyUserService
	 */
	public LuckyUserService getLuckyUserService() {
		return luckyUserService;
	}
	
	
	public List<User> changeUsers(List<LuckyHistory> listUser){
		List<User> customUsers=null;
		if(listUser!=null&&listUser.size()>0){
			customUsers=new ArrayList<User>();
			for(LuckyHistory lu:listUser){
				customUsers.add(lu.getUser());
			}
		}
		return customUsers;
	}
	
	
	public List<User> changeUsers1(List<LuckyUser> listUser){
		List<User> customUsers=null;
		if(listUser!=null&&listUser.size()>0){
			customUsers=new ArrayList<User>();
			for(LuckyUser lu:listUser){
				customUsers.add(lu.getUser());
			}
		}
		return customUsers;
	}
	
	/*public int removeEmement(List<User> list,User user){
		int index=-1;
		int id1=user.getId().intValue();
		for(int i=0;i<list.size();i++){
			int id2=list.get(i).getId().intValue();
			if(id1==id2){
				index=i;
				break;
			}
		}
		return index;
	}*/

}
