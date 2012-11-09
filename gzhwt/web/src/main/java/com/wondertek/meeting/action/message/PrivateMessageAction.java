/**
 * 
 */
package com.wondertek.meeting.action.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.appfuse.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.client.view.MessageView;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.model.Message;
import com.wondertek.meeting.model.MessageRecipient;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MessageService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.StringUtil;

/**
 * @author rain
 * 
 */
public class PrivateMessageAction extends BaseAction {
	private static final long serialVersionUID = -4768432999729666155L;
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	
	private Long meetingId;// 会议ID

	/**
	 * 列出与我会话的用户信息
	 * @return
	 */
	public String listMessageUsers() {
		try {
			Long userId = getUserIdFromSession();
			if (userId == null) {
				return ERROR;
			}

			List<User> messageUserList = messageService.listMessageUsers(meetingId, userId);
			this.getRequest().setAttribute("messageUserList", messageUserList);
		} catch (Exception e) {
			log.error("error ", e);
			return ERROR;
		}

		return SUCCESS;
	}

	/** 列出两人会话的消息 */
	public String listMessages() {
		try {
			Long userId = getUserIdFromSession();
			if (userId == null) {
				return ERROR;
			}

			final String otherUid = getParameter("otherUid");
			final String otherName = getParameter("otherName");

			List<Message> messageList = messageService.listMessages(meetingId, userId, Long.valueOf(otherUid));

			this.getRequest().setAttribute("messageList", messageList);
			this.getRequest().setAttribute("otherUid", otherUid);
			this.getRequest().setAttribute("otherName", otherName);
			this.getRequest().setAttribute("selfUserId", userId);
		} catch (Exception e) {
			log.error("error ", e);
			return ERROR;
		}

		return SUCCESS;
	}

	/** 删除两人会话的消息 */
	public String deleteMessages() {
		try {
			Long uid = getUserIdFromSession();
			if (uid == null) {
				return ERROR;
			}

			final String otherUid = getParameter("otherUid");

			Long otherUidL = null;
			if (StringUtil.isNotEmpty(otherUid)) {
				otherUidL = Long.valueOf(otherUid);
			}

			messageService.deleteAllByUserId(meetingId, uid, otherUidL);
		} catch (Exception e) {
			log.error("error ", e);
		}

		return SUCCESS;
	}
	
	public String wapMessagebox() {
		final String meetingId = getParameter("meetingId");
		final User user = getUserFromSession();
		String select = getParameter("select");
		if (StringUtils.isEmpty(select)) {
			select = "inbox";
		}
		if (StringUtils.equals(select, "inbox")) {
			final List<Message> inbox = messageService.queryInbox(Long.valueOf(meetingId), user.getId());
			getRequest().setAttribute("messages", convertMessageToView(inbox, true));
		} else if (StringUtils.equals(select, "outbox")) {
			final List<Message> outbox = messageService.queryOutbox(Long.valueOf(meetingId), user.getId());
			getRequest().setAttribute("messages", convertMessageToView(outbox, false));
		}

		final String userId = getParameter("selectuser");
		if (StringUtils.isNotEmpty(userId)) {
			getRequest().setAttribute("user", messageService.getUserInfo(Long.valueOf(userId), Long.valueOf(meetingId)));
			select = "new";
		}

		getRequest().setAttribute("meetingId", getParameter("meetingId"));
		getRequest().setAttribute("select", select);
		return SUCCESS;
	}

	public String wapSendMessage() {
		final String meetingId = getParameter("meetingId");
		final StringBuffer retUrl = new StringBuffer();
		try {
			final String userId = getParameter("userId");
			final String content = getParameter("message");

			if (StringUtils.isEmpty(userId) || !StringUtils.isNumeric(userId)) {
				retUrl.append("/wap/pri/message/messagebox.action?meetingId=").append(meetingId)
						.append("&select=new&menu_id=").append(getParameter("menu_id"));
				getRequest().setAttribute("returnUrl", retUrl.toString());
				getRequest().setAttribute("message", "请先选择收件人。");
				getRequest().setAttribute("title", "站内私信");
				return SUCCESS;
			}

			if (StringUtils.isEmpty(content)) {
				retUrl.append("/wap/pri/message/messagebox.action?meetingId=").append(meetingId)
						.append("&select=new&selectuser=").append(userId).append("&menu_id=")
						.append(getParameter("menu_id"));
				getRequest().setAttribute("returnUrl", retUrl.toString());
				getRequest().setAttribute("message", "请输入私信内容。");
				getRequest().setAttribute("title", "站内私信");
				return SUCCESS;
			}

			if (StringUtils.length(content) > 512) {
				retUrl.append("/wap/pri/message/messagebox.action?meetingId=").append(meetingId)
						.append("&select=new&selectuser=").append(userId).append("&menu_id=")
						.append(getParameter("menu_id"));
				getRequest().setAttribute("returnUrl", retUrl.toString());
				getRequest().setAttribute("message", "私信内容太长，超过512个字符。");
				getRequest().setAttribute("title", "站内私信");
				return SUCCESS;
			}

			final Message entity = new Message();
			entity.setMessage(content);
			entity.setSendTime(new Date());
			entity.setSender(getUserFromSession());
			entity.setSendFlag(0);
			final User recipient = new User();
			recipient.setId(Long.valueOf(userId));
			final MessageRecipient meetingRecipient = new MessageRecipient();
			meetingRecipient.setRecipient(recipient);
			meetingRecipient.setMeetingId(Long.valueOf(meetingId));
			meetingRecipient.setReadFlag(0);
			meetingRecipient.setReadTime(new Date());
			final Set<MessageRecipient> recipientSet = new HashSet<MessageRecipient>();
			recipientSet.add(meetingRecipient);
			entity.setRecipientSet(recipientSet);
			entity.setMeetingId(Long.valueOf(meetingId));
			messageService.add(entity);
			retUrl.append("/wap/pri/message/messagebox.action?meetingId=").append(meetingId)
					.append("&select=outbox&menu_id=").append(getParameter("menu_id"));
			getRequest().setAttribute("returnUrl", retUrl.toString());
			getRequest().setAttribute("message", "发送私信成功。");
		} catch (ServiceException e) {
			log.debug("send private message failure.", e.getMessage());
			retUrl.append("/wap/pri/message/messagebox.action?meetingId=").append(meetingId)
					.append("&select=outbox&menu_id=").append(getParameter("menu_id"));
			getRequest().setAttribute("returnUrl", retUrl.toString());
			getRequest().setAttribute("message", "发送私信失败。");
		}
		return SUCCESS;
	}

	public String wapDeleteMessage() {
		final String msgId = getParameter("id");
		final String select = getParameter("select");
		final User user = getUserFromSession();
		final boolean isInbox = (StringUtils.endsWithIgnoreCase("inbox", select)) ? true : false;
		messageService.deleteById(Long.valueOf(msgId), user.getId(), isInbox);
		final StringBuffer retUrl = new StringBuffer();
		retUrl.append("/wap/pri/message/messagebox.action?meetingId=").append(getParameter("meetingId"))
				.append("&select=").append(select).append("&menu_id=").append(getParameter("menu_id"));
		getRequest().setAttribute("returnUrl", retUrl.toString());
		getRequest().setAttribute("message", "删除私信成功。");
		return SUCCESS;
	}

	public String list() {
		final String meetingId = getParameter("meetingId");
		final User user = getUserFromSession();
		final List<Message> inbox = messageService.queryInbox(Long.valueOf(meetingId), user.getId());
		//更新
		this.updateInboxMessage(inbox);
		
		final List<Message> outbox = messageService.queryOutbox(Long.valueOf(meetingId), user.getId());
		getRequest().setAttribute("inbox", convertMessageToView(inbox, true));
		getRequest().setAttribute("outbox", convertMessageToView(outbox, false));
		getRequest().setAttribute("meetingId", meetingId);
		
		
		//回传回去
		this.setAttribute("userIds", getParameter("selectuser"));
		this.setAttribute("content", getParameter("content"));
		getRequest().setAttribute("users", getUserInfoList(getParameter("selectuser"), Long.valueOf(meetingId)));
		final String select = getParameter("select");
		if (StringUtils.isNotEmpty(select)) {
			getRequest().setAttribute("select", select);
		}
		return SUCCESS;
	}
	
	//更新私信
	private void updateInboxMessage(List<Message> inbox){
		for(Message message:inbox){
			final User user = getUserFromSession();
			messageService.setReadFlag(Long.valueOf(message.getId()), user.getId());
		}
	}
	
	
	
	private List<JsonUser> getUserInfoList(final String selectUsers, final Long meetingId) {
		final List<JsonUser> users = new ArrayList<JsonUser>();
		if (StringUtils.isNotEmpty(selectUsers)) {
			getRequest().setAttribute("selectuser", selectUsers);
			//try {
				//final JSONArray jsonUsers = new JSONArray(selectUsers);
				final String[] userIds = StringUtils.split(selectUsers, ",");
				for (int i = 0; i<userIds.length; i++) {
					//final JSONObject jsonUser = jsonUsers.getJSONObject(i);
					final Long userId = Long.valueOf(userIds[i]);
					users.add(messageService.getUserInfo(userId, meetingId));
				}
				return users;
			//} catch (JSONException e) {
			//	e.printStackTrace();
			//}
		}
		return null;
	}

	public String selectUser() {
		final Long meetingId = Long.valueOf(getParameter("meetingId"));
		final User searchUser = new User();
		searchUser.setName(getParameter("username"));
		searchUser.setMobile(getParameter("mobile"));

		try {
			//final Pager<User> userList = userService.findMeetingUserPager(meetingId, searchUser, currentPage, pageSize);
			final Pager<User> userList =userService.findMeetingUserPagerNotIncludeMe( meetingId, searchUser, currentPage,pageSize,"0",String.valueOf(this.getUserIdFromSession()));
			getRequest().setAttribute("pager", userList);
			getRequest().setAttribute("meetingId", meetingId);
			getRequest().setAttribute("selectUser", getParameter("selectUser"));
			getRequest().setAttribute("mobile", getParameter("mobile"));
			getRequest().setAttribute("username", getParameter("username"));
			getRequest().setAttribute("userIds", getParameter("userIds"));
			this.setAttribute("content", getParameter("content"));
			
		} catch (ServiceException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String checkNewMessage() {
		final String meetingId = getParameter("meetingId");
		final String count = getParameter("count");
		
		if (validateId(meetingId) && validateId(count)) {
			final User user = getUserFromSession();
			final int dbCount = messageService.queryUnReadMessageCount(Long.valueOf(meetingId), user.getId());
			final int clientCount = Integer.valueOf(count);
			responseSuccess();
			if (dbCount > clientCount) {
				resultMap.put("count", dbCount);
			} else {
				resultMap.put("count", clientCount);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 通过web来判断 
	 * @return
	 */
	public String checkNewMessageByWeb() {
		final String meetingId = getParameter("meetingId");
		if (validateId(meetingId)) {
			final User user = getUserFromSession();
			final int dbCount = messageService.queryUnReadMessageCount(Long.valueOf(meetingId), user.getId());
			responseSuccess();
			resultMap.put("count", dbCount);
		}
		
		return SUCCESS;
	}

	public String sendMessage() {
		try {
			final String userId = getParameter("userId");
			final String content = getParameter("message");
			final Long meetingId = Long.valueOf(getParameter("meetingId"));

			if (StringUtils.isEmpty(content) || StringUtils.isEmpty(userId)) {
				responseInvalidParam();
				return SUCCESS;
			}
			
			final Message entity = new Message();
			entity.setMessage(content);
			entity.setSendTime(new Date());
			entity.setSender(getUserFromSession());
			entity.setSendFlag(0);
			
			final Set<MessageRecipient> recipientSet = new HashSet<MessageRecipient>();
			final String[] ids = StringUtils.split(userId, ",");
			for (int i = 0; i<ids.length; i++) {
				final User recipient = new User();
				recipient.setId(Long.valueOf(ids[i]));
				final MessageRecipient meetingRecipient = new MessageRecipient();
				meetingRecipient.setRecipient(recipient);
				meetingRecipient.setMeetingId(Long.valueOf(meetingId));
				meetingRecipient.setReadFlag(0);
				meetingRecipient.setReadTime(new Date());
				recipientSet.add(meetingRecipient);
			}
			entity.setRecipientSet(recipientSet);
			entity.setMeetingId(Long.valueOf(meetingId));			
			entity.setId(messageService.add(entity));
			responseSuccess();
			resultMap.put("message", convertMessageToView(entity, false));
		} catch (ServiceException e) {
			resultMap.put("retcode", 1);
			resultMap.put("retmsg", e.getMessage());
		}
		return SUCCESS;
	}

	public String inbox() {
		final User user = getUserFromSession();
		final Long meetingId = Long.valueOf(getParameter("meetingId"));
		final List<Message> messages = messageService.queryInbox(meetingId, user.getId());
		responseSuccess();
		resultMap.put("messages", convertMessageToView(messages, true));
		return SUCCESS;
	}

	public String outbox() {
		final User user = getUserFromSession();
		final Long meetingId = Long.valueOf(getParameter("meetingId"));
		final List<Message> messages = messageService.queryOutbox(meetingId, user.getId());
		responseSuccess();
		resultMap.put("messages", convertMessageToView(messages, false));
		return SUCCESS;
	}

	public String setReadFlag() {
		final String msgId = getParameter("msgId");
		final User user = getUserFromSession();
		if (validateId(msgId)) {
			messageService.setReadFlag(Long.valueOf(msgId), user.getId());
			responseSuccess();
		}
		return SUCCESS;
	}

	public String deleteById() {
		final String msgId = getParameter("msgId");
		final String type = getParameter("type");
		final User user = getUserFromSession();
		if (StringUtils.isEmpty(type)) {
			responseInvalidParam();
			return SUCCESS;
		}
		if (validateId(msgId)) {
			final boolean isInbox = (StringUtils.endsWithIgnoreCase("inbox", type)) ? true : false;
			messageService.deleteById(Long.valueOf(msgId), user.getId(), isInbox);
			responseSuccess();
		}
		return SUCCESS;
	}

	public String deleteAllByUserId() {
		final User user = getUserFromSession();
		if(StringUtil.isEmpty(getParameter("meetingId"))){
			this.resultMap.put("recCode", 1);
			this.resultMap.put("recMsg","meetingId请求参数不合法");
			return SUCCESS;
		}
		
		final Long meetingId = Long.valueOf(getParameter("meetingId"));
		final String type = getParameter("type");
		if (StringUtils.isEmpty(type)) {
			responseInvalidParam();
			return SUCCESS;
		}
		final boolean isInbox = (StringUtils.endsWithIgnoreCase("inbox", type)) ? true : false;
		messageService.deleteAllByUserId(meetingId, user.getId(), isInbox);
		responseSuccess();
		return SUCCESS;
	}

	private boolean validateId(final String id) {
		if (StringUtils.isEmpty(id) || !StringUtils.isNumeric(id)) {
			responseInvalidParam();
			return false;
		}
		return true;
	}

	private void responseSuccess() {
		resultMap.put("retcode", 0);
		resultMap.put("retmsg", SUCCESS);
	}

	private void responseInvalidParam() {
		resultMap.put("retcode", 100);
		resultMap.put("retmsg", "请求参数不合法");
	}

	private MessageView convertMessageToView(final Message message, final boolean isInbox) {
		final MessageView view = new MessageView();
		view.setId(message.getId());
		view.setMessage(StringUtil.convertTextAreaNewLine(message.getMessage()));
		view.setTime(DateUtil.getDateTime("yyyy年MM月dd日 HH:mm:ss", message.getSendTime()));
		if (isInbox) {
			final JsonUser sender = messageService.getUserInfo(message.getSender().getId(), message.getMeetingId());
			if (sender == null) {
				view.setSender(createJsonUserByUser(message.getSender()));
			} else {
				view.setSender(sender);
			}			
			final Set<MessageRecipient> recipients = message.getRecipientSet();
			for (Iterator<MessageRecipient> it = recipients.iterator(); it.hasNext();) {
				MessageRecipient recipient = it.next();
				view.setStatus(recipient.getReadFlag());
			}
		} else {
			view.setStatus(message.getSendFlag());
			final List<JsonUser> recipients = new ArrayList<JsonUser>();
			for (Iterator<MessageRecipient> it = message.getRecipientSet().iterator(); it.hasNext();) {
				MessageRecipient msgRecipient = it.next();
				if (msgRecipient !=null && msgRecipient.getRecipient() != null) {
					final JsonUser recipient = messageService.getUserInfo(msgRecipient.getRecipient().getId(), msgRecipient.getMeetingId());
					if (recipient == null) {
						recipients.add(createJsonUserByUser(msgRecipient.getRecipient()));
					} else {
						recipients.add(recipient);
					}
				}
			}
			view.setRecipients(recipients);
		}
		return view;
	}
	
	private JsonUser createJsonUserByUser(final User user) {
		JsonUser json = new JsonUser();
		if (user != null ) {
			json.setId(user.getId());
			json.setName(user.getName());
			json.setMobile(user.getMobile());
			json.setJob("");
			json.setIsShowJob("0");
			json.setIsShowMobile("0");
		}
		return json;
	}

	private List<MessageView> convertMessageToView(final List<Message> messages, final boolean isInbox) {
		final List<MessageView> views = new ArrayList<MessageView>();
		for (Message message : messages) {
			views.add(convertMessageToView(message, isInbox));
		}
		return views;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
}