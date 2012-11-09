/**
 * 卡片处理Service实现
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-10
 */
package com.wondertek.meeting.service.impl.rfid;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingAgendaDao;
import com.wondertek.meeting.dao.rfid.CardDao;
import com.wondertek.meeting.dao.rfid.IssueCardDao;
import com.wondertek.meeting.dao.rfid.RfidSignInDao;
import com.wondertek.meeting.dao.rfid.TriggerTaskDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.RfidSignIn;
import com.wondertek.meeting.model.SignEvent;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.rfid.Card;
import com.wondertek.meeting.model.rfid.IssueCard;
import com.wondertek.meeting.model.rfid.TriggerTask;
import com.wondertek.meeting.service.SignEventService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.service.impl.BaseServiceImpl;
import com.wondertek.meeting.service.rfid.CardService;
import com.wondertek.meeting.util.DateUtil;


public class CardServiceImpl extends BaseServiceImpl<Card, Integer> implements
		CardService {
	private CardDao cardDao;
	private IssueCardDao issueCardDao;
	private TriggerTaskDao triggerTaskDao;
	private MeetingAgendaDao meetingAgendaDao;
	private RfidSignInDao rfidSignInDao;
	private SignEventService signEventService;
	private UserService userService;

	public void setCardDao(CardDao cardDao) {
		super.setBaseDao(cardDao);
		this.cardDao = cardDao;
	}

	public void setIssueCardDao(IssueCardDao issueCardDao) {
		this.issueCardDao = issueCardDao;
	}

	/**
	 * 分页查看卡牌列表
	 * card: 查询条件
	 */
	public Pager<Card> findAllCardPager(Card card, int currentPage, int pageSize) throws Exception {
		return this.cardDao.findAllCardPager(card, currentPage, pageSize);
	}
	
	/**
	 * 根据UID查询卡牌
	 */
	public Card findCardByUid(Long meetingId, String uid) {
		return this.cardDao.findCardByUid(meetingId, uid);
	}
	
	/**
	 * 分页查看卡牌发放信息列表
	 * card: 查询条件
	 */
	public Pager<IssueCard> findAllIssueCardPager(IssueCard issueCard, int currentPage, int pageSize) throws Exception {
		return this.issueCardDao.findAllIssueCardPager(issueCard, currentPage, pageSize);
	}
	
	/**
	 * 保存卡牌发放信息
	 */
	public void saveOrUpdateIssueCard(IssueCard issueCard) throws HibernateDaoSupportException {
		this.issueCardDao.saveOrUpdateEntity(issueCard);
	}
	
	/**
	 * 删除卡牌发放信息
	 */
	public void deleteIssueCard(IssueCard issueCard) throws HibernateDaoSupportException {
		this.issueCardDao.delete(issueCard);
	}
	
	/**
	 * 根据UID查询卡牌发放信息
	 */
	public IssueCard findIssueCardByUid(Long meetingId, String uid) {
		return this.issueCardDao.findIssueCardByUid(meetingId, uid);
	}
	
	/**
	 * 根据用户ID查询卡牌发放信息
	 */
	public IssueCard findIssueCardByUserId(Long meetingId, Long userId) {
		return this.issueCardDao.findIssueCardByUserId(meetingId, userId);
	}
	
	/**
	 * 根据ID查询卡牌发放信息
	 */
	public IssueCard findIssueCardById(Integer issueCardId) throws HibernateDaoSupportException {
		return this.issueCardDao.findById(issueCardId, IssueCard.class);
	}

	public void setTriggerTaskDao(TriggerTaskDao triggerTaskDao) {
		this.triggerTaskDao = triggerTaskDao;
	}
	
	/**
	 * 根据会议ID查找触发任务信息
	 */
	public TriggerTask findTriggerTaskByMeetingId(Long meetingId) {
		return this.triggerTaskDao.findTriggerTaskByMeetingId(meetingId);
	}
	
	/**
	 * 查找缺省的触发任务信息
	 * id=1的记录为系统预置的缺省任务信息
	 */
	public TriggerTask findDefaultTriggerTask()  throws HibernateDaoSupportException {
		return this.triggerTaskDao.findById(1, TriggerTask.class);
	}
	
	/**
	 * 修改触发任务信息
	 */
	public void saveOrUpdateTriggerTask(TriggerTask task) throws HibernateDaoSupportException {
		this.triggerTaskDao.saveOrUpdateEntity(task);
	}
	
	/**
	 * 删除触发任务信息
	 */
	public void deleteTriggerTask(TriggerTask task) throws HibernateDaoSupportException {
		this.triggerTaskDao.delete(task);
	}
	
	/**
	 * 执行RFID触发动作
	 */
	public void handleRfidTagTriggerTask(TriggerTask triggerTask, IssueCard issueCard, RfidSignIn rfidSignIn) {
		/*//判断是否需要通过短信发送欢迎信息
		if(triggerTask.getSendSMS() == 1) {
			String smsTemplate = triggerTask.getSmsTemplate();
			User meetingMember = issueCard.getOwner();
			String username = meetingMember.getName();
			String mobile = meetingMember.getMobile();
			
			//替换短信模板中的变量：{username}
			String smsContent = smsTemplate.replaceAll("\\{username\\}", meetingMember.getName());
			//发送短信
			SmsSender.sendSms(mobile, smsContent);
			log.debug("handleRfidTagTriggerTask: send sms to " + mobile + ", content:" + smsContent);
		}
		
		//判断是否需要发送会议议程信息
		if(triggerTask.getSendMeetingAgenda() == 1) {
			User meetingMember = issueCard.getOwner();
			String mobile = meetingMember.getMobile();
			List<MeetingAgenda> agendaList = this.meetingAgendaDao.queryListByMeetingId(issueCard.getMeeting().getId());
			//每个议程发送一条短信
			for(MeetingAgenda agenda : agendaList) {
				StringBuilder sb = new StringBuilder();
				sb.append("议程：" + agenda.getTopic());
				sb.append(", 人员：" + agenda.getHost());
				sb.append(", 日期：" + agenda.getDate());
				sb.append(", 时间：" + agenda.getStartTime() + "-" + agenda.getEndTime());
				sb.append(", 地点：" + agenda.getLocation());
				
				String smsContent = sb.toString();
				//发送短信
				SmsSender.sendSms(mobile, smsContent);
				log.debug("handleRfidTagTriggerTask: send meeting agenda to " + mobile + ", content:" + smsContent);
			}
		}*/
		
		//判断是否需要记录rfid签到信息
//		if(triggerTask.getSignIn() == 1) {
			Long meetingId = issueCard.getMeeting().getId();
			rfidSignIn.setMeetingId(meetingId);
			rfidSignIn.setUser(issueCard.getOwner());
			
			Date now = new Date();
			String signDate = DateUtil.formatDate(now, "yyyy-MM-dd");
			String signTime = DateUtil.formatDate(now, "HH:mm");
//			signDate = "2012-08-10"; //test
//			signTime = "10:20"; //通过模拟时间来测试
			
			rfidSignIn.setSignDate(signDate);
			rfidSignIn.setSignTime(signTime);
			
			rfidSignIn.setSignState(this.getSignState(rfidSignIn, signDate, signTime)); //根据签到时间判断签到状态
			this.rfidSignInDao.saveOrUpdateEntity(rfidSignIn);
//		}
	}
	
	/**
	 * 根据签到时间判断签到状态
	 * 签到的状态，1-正常签到，2：正常签退，3-迟到，4-早退
	 */
	private int getSignState(RfidSignIn signIn, String signDate, String signTime) {
		int state = 0;
		int signType = signIn.getSignType();
		SignEvent se = signIn.getSignEvent();
		
		if(signType == 1 && signTime.compareTo(se.getSignInEndTime().trim()) <= 0) { //正常签到
			state = 1;
		} else if(signType == 1 && signTime.compareTo(se.getSignInEndTime().trim()) > 0 && signTime.compareTo(se.getSignOutBeginTime()) < 0) { //非正常签到，早退或迟到
			state = 3;
		} else if(signType == 2 && signTime.compareTo(se.getSignOutBeginTime().trim()) >= 0) { //正常签退
			state = 2;
		} else if(signType == 2 && signTime.compareTo(se.getSignInEndTime().trim()) > 0 && signTime.compareTo(se.getSignOutBeginTime()) < 0) { //非正常签到，早退或迟到
			state = 4;
		}  
		return state;
	}


	public void setMeetingAgendaDao(MeetingAgendaDao meetingAgendaDao) {
		this.meetingAgendaDao = meetingAgendaDao;
	}

	public void setRfidSignInDao(RfidSignInDao rfidSignInDao) {
		this.rfidSignInDao = rfidSignInDao;
	}
	
	/**
	 * 根据会议ID和用户ID查询对应的RFID签到信息
	 */
	public List<RfidSignIn> querySignIn(Long meetingId, Long userId) {
		return this.rfidSignInDao.querySignIn(meetingId, userId);
	}
	
	/**
	 * 查询对应的RFID签到分页信息
	 */
	public Pager<RfidSignIn> findAllRfidSignInPager(RfidSignIn rfidSignIn, int currentPage, int pageSize) throws Exception  {
		return this.rfidSignInDao.findAllRfidSignInPager(rfidSignIn, currentPage, pageSize);
	}
	
	/**
	 * 查询对应的RFID签到统计信息
	 */
	public Pager findRfidSignInForStaticPager(RfidSignIn rfidSignIn, int currentPage, int pageSize) throws Exception {
		return this.rfidSignInDao.findRfidSignInForStaticPager(rfidSignIn, currentPage, pageSize);
	}
	
	/**
	 * 根据ID查询对应的RFID签到信息
	 */
	public RfidSignIn findRfidSignInById(Long id) throws Exception {
		return this.rfidSignInDao.findById(id, RfidSignIn.class);
	}
	
	/**
	 * 保存RFID签到信息
	 */
	public void saveOrUpdateRfidSignInById(RfidSignIn rfidSignIn) {
		this.rfidSignInDao.saveOrUpdateEntity(rfidSignIn);
	}
	
	/**
	 * 删除RFID签到信息
	 */
	public void deleteRfidSignIn(RfidSignIn rfidSignIn) throws Exception {
		this.rfidSignInDao.delete(rfidSignIn);
	}

	public void setSignEventService(SignEventService signEventService) {
		this.signEventService = signEventService;
	}
	
	/**
	 * 通过签到状态获取用户信息
	 * ids
	 * @return
	 */
	public List getUserBySignStatus(String meetingId, String eventId,
			String signType, String signState){
		return rfidSignInDao.getUserBySignStatus(meetingId, eventId, signType, signState);
	}

	@Override
	public Map<String,List<String>> autoIssue(Meeting meeting, String beginUID) {
		//查找会议的所有参会人员
		Long meetingId=meeting.getId();
		long uid=Long.parseLong(beginUID);
		List<User> userList=this.userService.getMeetingMember(meetingId, null, null, null, null, null);
		for(int i=0;i<userList.size();){
			IssueCard ic = this.findIssueCardByUserId(meetingId, userList.get(i).getId());
			if(ic!=null){  //表示此人已经发过了卡牌
				i++;
				continue;
			}
			IssueCard ic1 = this.findIssueCardByUid(meetingId, String.valueOf(uid));
			if(ic1!=null){  //表示此卡已经被分配
				uid++;
				continue;
			}
			//进行发卡
			this.issueCard(meeting, userList.get(i), String.valueOf(uid));
			uid++;
			i++;
		}
		return null;
	}
	
	public boolean  issueCard(Meeting meeting,User owner,String uid){
		try {
			//先保存卡牌信息
			Card tmpCard = this.findCardByUid(meeting.getId(),uid );  //如果存在则重复利用
			if(tmpCard == null) {
				tmpCard = new Card();
				tmpCard.setUid(uid);
				tmpCard.setState(1);
				tmpCard.setRegisterTime(new Date());
				tmpCard.setMeeting(meeting);
				this.saveOrUpdate(tmpCard);
			}
			
			IssueCard issueCard=new IssueCard();
			//再保存卡牌发放信息
			issueCard.setMeeting(meeting);
			issueCard.setOwner(owner);
			issueCard.setCard(tmpCard);
			issueCard.setIssueTime(new Date());
			issueCard.setState(1);
			this.saveOrUpdateIssueCard(issueCard);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}


}
