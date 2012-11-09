/**
 * RFID卡牌处理Action
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-10
 */
package com.wondertek.meeting.action.meeting;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.RfidSignIn;
import com.wondertek.meeting.model.SignEvent;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.rfid.Card;
import com.wondertek.meeting.model.rfid.IssueCard;
import com.wondertek.meeting.model.rfid.TriggerTask;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.SignEventService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.service.rfid.CardService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.StringUtil;

@SuppressWarnings("serial")
public class CardAction extends BaseAction {
	private CardService cardService;
	private MeetingService meetingService;
	private UserService userService;
	private SignEventService signEventService;
	
	private Long meetingId;
	private Long userId;
	private Card card = new Card();
	private IssueCard issueCard = new IssueCard();
	private TriggerTask triggerTask;
	private RfidSignIn rfidSignIn;
	private String issueIds; //批量回收的issueCardId串，多个以逗号
	
	private String eventName; 
	private Long signEventId;
	private String flag;

	public String getIssueIds() {
		return issueIds;
	}

	public void setIssueIds(String issueIds) {
		this.issueIds = issueIds;
	}

	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}
	
	/**
	 * 查看卡片信息
	 */
	public String list() throws Exception {
		Meeting meeting = new Meeting();
		meeting.setId(meetingId);
		card.setMeeting(meeting);
		Pager<Card> pager = this.cardService.findAllCardPager(card, currentPage, pageSize);
		this.setAttribute("pager", pager);
		return "listCard";
	}
	
	/**
	 * 删除卡片信息
	 */
	public String delete() throws Exception {
		this.cardService.delete(card);
		
		this.card = new Card(); //重置查询条件
		return list();
	}
	
	/**
	 * 请求添加、修改卡片信息
	 */
	public String editReq() throws Exception {
		if(card.getId() != null) {
			card = this.cardService.findById(card.getId());
		}
		return "cardInfo";
	}
	
	/**
	 * 保存卡片信息
	 */
	public String save() throws Exception {
		//判断卡牌是否存在
		Card entity = this.cardService.findCardByUid(meetingId, card.getUid());
		if(entity != null && !entity.getId().equals(card.getId())) {
			this.errMsg = "卡牌使用的标签UID" + card.getUid() + "已经存在了。";
			return "cardInfo";
		}
		
		if(card.getId() == null) { //add
			entity = new Card();
			entity.setRegisterTime(new Date());
			entity.setState(1);
			Meeting meeting = this.meetingService.findById(meetingId);
			entity.setMeeting(meeting);
		} else {
			entity = this.cardService.findById(card.getId());
			entity.setState(card.getState());
		}
		entity.setUid(card.getUid());
		cardService.saveOrUpdate(entity);
		
		this.card = new Card(); //重置查询条件
		return list();
	}
	
	/**
	 * 查看卡片发放信息
	 */
	public String listIssue() throws Exception {
		Meeting meeting = new Meeting();
		meeting.setId(meetingId);
		issueCard.setMeeting(meeting);
		Pager<IssueCard> pager = this.cardService.findAllIssueCardPager(issueCard, currentPage, pageSize);
		this.setAttribute("pager", pager);
		return "listIssueCard";
	}
	
	/**
	 * 删除卡片登记信息
	 */
	public String deleteIssue() throws Exception {
		this.cardService.deleteIssueCard(issueCard);
		
		this.issueCard = new IssueCard(); //重置查询条件
		return listIssue();
	}
	
	/**
	 * 批量删除卡片登记信息
	 */
	public String batchDeleteIssue() throws Exception {
		log.debug("issueIds:" + issueIds);
		
		if(! StringUtil.isEmpty(issueIds)) {
			String[] aryId = issueIds.split(",");
			for(String id : aryId) {
				IssueCard ic = new IssueCard();
				ic.setId(Integer.parseInt(id));
				this.cardService.deleteIssueCard(ic);
			}
		}
		
		this.issueCard = new IssueCard(); //重置查询条件
		return listIssue();
	}
	
	/**
	 * 查找参会人请求 
	 */
	public String queryOwnerReq() throws Exception {
		return "queryCardOwner";
	}
	
	/**
	 * 查找参会人 
	 */
	public String queryOwner() throws Exception {
		String username = null;
		String mobile = null;
		
		if(issueCard != null && issueCard.getOwner() != null) {
			username = issueCard.getOwner().getName();
			mobile = issueCard.getOwner().getMobile();
		}
		Pager<User> pager = this.userService.queryMeetingMember(meetingId, username, mobile, currentPage, pageSize);
		List<User> list = pager.getPageRecords();
		//检查列表中用户是否已经被发放卡牌
		Map<Long, Boolean> userIssueStatus = new HashMap<Long, Boolean>();
		for(User u : list) {
			IssueCard ic = this.cardService.findIssueCardByUserId(meetingId, u.getId());
			userIssueStatus.put(u.getId(), ic != null); //已经发放过卡牌返回false
		}
		
		this.setAttribute("pager", pager);
		this.setAttribute("userIssueStatus", userIssueStatus);
		return "listCardOwner";
	}
	
	/**
	 * 发卡请求
	 */
	public String issueReq() throws Exception {
		if(!StringUtil.isEmpty(flag)&&flag.equals("auto")){  //表示批量发卡
			int count=0;
			List<User> userList=this.userService.getMeetingMember(meetingId, null, null, null, null, null);
			if(userList!=null){
				for(User u : userList) {
					IssueCard ic = this.cardService.findIssueCardByUserId(meetingId, u.getId());
					if(ic!=null){ //表示已经发过卡了
						count++;
					}
				}
				this.getContext().put("count",count);
				this.getContext().put("userCount",userList.size());
			}
			return "issueCard_auto";
		}
		User user = this.userService.findById(this.issueCard.getOwner().getId());
		this.issueCard.setOwner(user);
		return "issueCard";
	}
	
	/**
	 * 发卡请求
	 * 逻辑：发卡前，要检查该用户是否已经发过卡，发过卡的不能再次发卡，其次要检查指定的UID对应的卡片是否被使用，
	 * 如果没有使用，则创建卡片信息，并进行发卡绑定，如果卡片已经发出，不可再发
	 */
	public String issue() throws Exception {
		//检查UID对应的卡片是否已经被使用
		User owner = this.userService.findById(issueCard.getOwner().getId());
		IssueCard ic = this.cardService.findIssueCardByUid(meetingId, card.getUid());
		if(ic != null) {
			this.errMsg = "当前UID所对应的卡牌已经被使用，请更换卡牌。";
			this.issueCard.setOwner(owner);
			return "issueCard";
		}
		
		Meeting meeting = this.meetingService.findById(meetingId);
		
		//先保存卡牌信息
		Card tmpCard = this.cardService.findCardByUid(meetingId, card.getUid());  //如果存在则重复利用
		if(tmpCard == null) {
			tmpCard = new Card();
			tmpCard.setUid(card.getUid());
			tmpCard.setState(1);
			tmpCard.setRegisterTime(new Date());
			tmpCard.setMeeting(meeting);
			this.cardService.saveOrUpdate(tmpCard);
		}
		
		//再保存卡牌发放信息
		issueCard.setMeeting(meeting);
		issueCard.setOwner(owner);
		issueCard.setCard(tmpCard);
		issueCard.setIssueTime(new Date());
		issueCard.setState(1);
		this.cardService.saveOrUpdateIssueCard(issueCard);
		
		this.issueCard = new IssueCard(); //重置查询条件
		return this.listIssue();
	}
	
	/**
	 * 自动发卡：自动根据用户输入的起始卡牌UID,递增为未分配的参会人员分配卡牌
	 * @author zouxiaoming
	 * @return
	 * @throws Exception 
	 */
	public String autoIssue() throws Exception{
		Meeting meeting = this.meetingService.findById(meetingId);
		this.cardService.autoIssue(meeting, card.getUid());
		return this.queryOwner();
	}
	
	/**
	 * 卡牌挂失申请
	 */
	public String lostReq() throws Exception {
		return "lossCard";
	}
	
	/**
	 * 卡牌挂失
	 */
	public String lost() throws Exception {
		IssueCard ic = this.cardService.findIssueCardById(this.issueCard.getId());
		ic.setLossReason(this.issueCard.getLossReason());
		ic.setLossTime(new Date());
		ic.setState(2); //2-挂失
		this.cardService.saveOrUpdateIssueCard(ic);
		
		this.issueCard = new IssueCard();
		return this.listIssue();
	}
	
	/**
	 * 触发任务管理请求
	 */
	public String taskConfigReq() throws Exception {
		this.triggerTask = this.cardService.findTriggerTaskByMeetingId(meetingId);
		if(this.triggerTask == null) {
			TriggerTask tt = this.cardService.findDefaultTriggerTask();
			triggerTask = new TriggerTask();
			triggerTask.setMeeting(this.meetingService.findById(meetingId));
			triggerTask.setTriggerNotify(tt.getTriggerNotify() == null ? 0 : tt.getTriggerNotify());
			triggerTask.setDelayTime(tt.getDelayTime() == null ? 0 : tt.getDelayTime());
			triggerTask.setSendSMS(tt.getSendSMS() == null ? 0 : tt.getSendSMS());
			triggerTask.setSendMeetingAgenda(tt.getSendMeetingAgenda() == null ? 0 : tt.getSendMeetingAgenda());
			triggerTask.setSendOtherInfo(tt.getSendOtherInfo() == null ? 0 : tt.getSendOtherInfo());
			triggerTask.setDisplayWelcome(tt.getDisplayWelcome() == null ? 0 : tt.getDisplayWelcome());
			triggerTask.setSignIn(tt.getSignIn() == null ? 0 : tt.getSignIn());
			triggerTask.setSmsTemplate(tt.getSmsTemplate());
			triggerTask.setOtherInfo(tt.getOtherInfo());
			this.cardService.saveOrUpdateTriggerTask(this.triggerTask);
		}
		return "triggerTask";
	}
	
	/**
	 * 保存触发任务信息
	 */
	public String saveTaskConfig() throws Exception {
		TriggerTask tt = this.cardService.findTriggerTaskByMeetingId(meetingId);
		Meeting meeting = this.meetingService.findById(meetingId);
		
		if(tt == null) {
			tt = new TriggerTask();
		}
		tt.setMeeting(meeting);
//		tt.setTriggerNotify(triggerTask.getTriggerNotify() == null ? 0 : triggerTask.getTriggerNotify());
//		tt.setDelayTime(triggerTask.getDelayTime() == null ? 0 : triggerTask.getDelayTime());
//		tt.setSendSMS(triggerTask.getSendSMS() == null ? 0 : triggerTask.getSendSMS());
//		tt.setSendMeetingAgenda(triggerTask.getSendMeetingAgenda() == null ? 0 : triggerTask.getSendMeetingAgenda());
//		tt.setSendOtherInfo(triggerTask.getSendOtherInfo());
//		tt.setDisplayWelcome(triggerTask.getDisplayWelcome());
//		tt.setSmsTemplate(triggerTask.getSmsTemplate());
//		tt.setOtherInfo(triggerTask.getOtherInfo());
		tt.setSignIn(triggerTask.getSignIn()== null ? 0 : triggerTask.getSignIn());
		
		this.cardService.saveOrUpdateTriggerTask(tt);
		
		this.errMsg = "RFID触发任务配置信息保存成功。";
		return this.taskConfigReq();
	}
	
	/**
	 * 检查会议ID是否有效 
	 */
	public String checkMeetingId() throws Exception {
		log.debug("checkMeetingId: meetingId=" + meetingId);
		
		if(meetingId == null || meetingId == 0) {
			this.str2Resp("empty");
			return null;
		}
		
		Meeting meeting = this.meetingService.findById(meetingId);
		if(meeting == null) {
			this.str2Resp("invalid");
		} else {
			this.str2Resp("ok");
		}
		return null;
	}
	
	/**
	 * 查询指定会议下的签到事件
	 */
	public String querySignEvents() throws Exception {
		log.debug("checkMeetingId: meetingId=" + meetingId);
		
		if(meetingId == null || meetingId == 0) {
			this.str2Resp("empty", "gbk");
			return null;
		}
		
		Date now = new Date();
		String signDate = DateUtil.formatDate(now, "yyyy-MM-dd");
		
		List<SignEvent> events = this.signEventService.findByMeetingId(meetingId, signDate);
		if(events.size() == 0) {
			this.str2Resp("nodata", "gbk");
			return null;
		} 
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < events.size(); i++) {
			SignEvent event = events.get(i);
			sb.append(event.getId() + ",");
			sb.append(event.getEventName() + ",");
			sb.append(event.getSignDate() + ",");
			sb.append(event.getSignInBeginTime() + ",");
			sb.append(event.getSignInEndTime() + ",");
			sb.append(event.getSignOutBeginTime() + ",");
			sb.append(event.getSignOutEndTime());
			
			if(i < events.size() -1) {
				sb.append(";");
			}
		}
		
		this.str2Resp(sb.toString(), "gbk");
		return null;
	}
	
	/**
	 * 测试模拟RFID标签被感应到的场景 
	 */
	public String rfidTagTrigger() throws Exception {
		if(meetingId == null || rfidSignIn == null 	|| StringUtil.isEmpty(rfidSignIn.getTagId())
				|| signEventId == null || rfidSignIn.getSignType() == null) {
			this.str2Resp("meetingId or rfidSignIn or signEvent is empty");
			return null;
		}
		
		log.debug("rfidTagTrigger: on scan tag, meetingId=" + meetingId 
				+ ", tagId=" + rfidSignIn.getTagId() 
				+ ", signEventId=" + signEventId + ", signType=" + rfidSignIn.getSignType());
		
		//Base64解码
		// TODO Auto-generated method stub add by zouxiaoming 表结构改动请做具体处理
/*		byte[] buf = rfidSignIn.getSignEvent().getBytes("gbk");
		buf = Base64.decode(buf);
		rfidSignIn.setSignEvent(new String(buf, "gbk"));
		
		log.debug("rfidTagTrigger: on scan tag, meetingId=" + meetingId 
				+ ", signEvent=" + rfidSignIn.getSignEvent() + ", tagId=" + rfidSignIn.getTagId());
		*/
		//判断是否需要做通知操作处理
		
/*		TriggerTask task = this.cardService.findTriggerTaskByMeetingId(meetingId);
		if(task == null) {
			this.str2Resp("trigger task not exists");
			return null;
		}
		
		//判断当前配置的触发任务是否需要记录签到信息等操作
		if(task.getSignIn() != 1) {
			this.str2Resp("trigger task not need record rfid sign in");
			return null;
		}*/
		
		IssueCard ic = this.cardService.findIssueCardByUid(meetingId, rfidSignIn.getTagId());
		//判断卡牌是否正在登记
		if(ic == null) { //扫描到的电子标签未在指定的会议中登记，做警报处理
			this.str2Resp("invalid card");
			return null;
		}
		
		//判断卡牌状态
/*		if(ic.getState() != 1) { //扫描到的电子标签已经挂失了，做警报处理
			this.str2Resp("card lost state");
			return null;
		}*/
		
		SignEvent event = this.signEventService.findById(signEventId);
		if(event == null) {
			this.str2Resp("invalid sign event");
			return null;
		}
		
		rfidSignIn.setSignEvent(event);
		cardService.handleRfidTagTriggerTask(null, ic, rfidSignIn);
		this.str2Resp("handle trigger task success");
		return null;
	}
	
	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setIssueCard(IssueCard issueCard) {
		this.issueCard = issueCard;
	}

	public IssueCard getIssueCard() {
		return issueCard;
	}

	public TriggerTask getTriggerTask() {
		return triggerTask;
	}

	public void setTriggerTask(TriggerTask triggerTask) {
		this.triggerTask = triggerTask;
	}

	/**
	 * 显示Rfid签到信息
	 */
	public String listRfidSignIn() throws Exception {
		log.debug("meetingId:" + rfidSignIn.getMeetingId());
		
		if(StringUtil.isNotEmpty(eventName)) {
			SignEvent event = new SignEvent();
			event.setEventName(eventName);
			rfidSignIn.setSignEvent(event);
		}
		
		//查询指定会议下面的签到事件
		List<SignEvent> signEvents = this.signEventService.findByMeetingId(rfidSignIn.getMeetingId());
		this.setAttribute("signEvents", signEvents);
		
		Pager<RfidSignIn> pager = this.cardService.findAllRfidSignInPager(rfidSignIn, currentPage, pageSize);
		this.setAttribute("pager", pager);
		
		return "listRfidSignIn";
	}
	
	/**
	 * 显示Rfid签到统计信息
	 */
	public String rfidSignInForStaic() throws Exception {
		log.debug("meetingId:" + rfidSignIn.getMeetingId());
		
		SignEvent signEvent = this.signEventService.findById(rfidSignIn.getSignEvent().getId());
		this.setAttribute("signEvent", signEvent);
		
		Pager pager = this.cardService.findRfidSignInForStaticPager(rfidSignIn, currentPage, pageSize);
		this.setAttribute("pager", pager);		
		return "rfidSignInStatic";
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public RfidSignIn getRfidSignIn() {
		return rfidSignIn;
	}

	public void setRfidSignIn(RfidSignIn rfidSignIn) {
		this.rfidSignIn = rfidSignIn;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setSignEventService(SignEventService signEventService) {
		this.signEventService = signEventService;
	}

	public Long getSignEventId() {
		return signEventId;
	}

	public void setSignEventId(Long signEventId) {
		this.signEventId = signEventId;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}

}
