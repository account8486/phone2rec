package com.wondertek.meeting.action.message;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import com.google.zxing.WriterException;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.client.view.MMSFrag;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.QRCodeGen;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.manager.MasServiceManager;
import com.wondertek.meeting.manager.SmsSender;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.MeetingSms;
import com.wondertek.meeting.model.SignEvent;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.MeetingSmsService;
import com.wondertek.meeting.service.SignEventService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.service.rfid.CardService;
import com.wondertek.meeting.util.MessageSendUtil;
import com.wondertek.meeting.util.StringUtil;
import com.wondertek.meeting.webservice.mas.CPFrag;

/**
 * 发送短信
 * 
 * @author 用户短信管理
 * 
 */
public class MessageManagerAction extends BaseAction {

	private static final long serialVersionUID = -3633713603706583660L;

	MeetingMemberService meetingMemberService;

	UserService userService;

	MeetingSmsService meetingSmsService;

	private String meetingId;

	private MeetingService meetingService;
	
	private List<MMSFrag> mmsList;
	
	SignEventService signEventService;
	
	CardService cardService;
	

	public CardService getCardService() {
		return cardService;
	}

	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}

	public SignEventService getSignEventService() {
		return signEventService;
	}

	public void setSignEventService(SignEventService signEventService) {
		this.signEventService = signEventService;
	}

	/**
	 * 初始化发送界面
	 * 
	 * @return
	 * @throws HibernateDaoSupportException
	 * @throws NumberFormatException
	 */
	public String preSendMessage() throws NumberFormatException, HibernateDaoSupportException {
		String forward = SUCCESS;
		// 获取meetingid
		String meetingId = getRequest().getParameter("meetingId");
		List<MeetingSms> totalSmsMessageList = meetingSmsService.getMeetingSmsList(Long.valueOf(meetingId));
		int smsActCount = 0;
		if (totalSmsMessageList != null && totalSmsMessageList.size() > 0) {
			smsActCount = totalSmsMessageList.size();
		}

		Meeting meeting = meetingService.getMeetingByPk(Long.valueOf(meetingId));
		int preCount = 0;
		if (!StringUtil.isNotNumber(meeting.getFreeSmsNum())) {
			preCount = Integer.parseInt(StringUtil.null2Str(meeting.getFreeSmsNum()));
		}

		
		// 还可以发送数
		int canSendCount = preCount - smsActCount;
		log.debug(",preCount:" + preCount + "smsActCount:" + smsActCount + ",canSendCount:" + canSendCount);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		getRequest().setAttribute("sendTime", sdf.format(new Date()));
		getRequest().setAttribute("meetingId", meetingId);
		//getRequest().setAttribute("serverUrl", getServerUrl());
		getRequest().setAttribute("preCount", preCount);// 可发送
		getRequest().setAttribute("smsActCount", smsActCount);// 已发送
		getRequest().setAttribute("canSendCount", canSendCount);// 还可以发送

		return forward;
	}
	
	/**
	 * 跳转到发送二维码页面
	 * @return
	 * @throws NumberFormatException
	 * @throws HibernateDaoSupportException
	 */
	public String goSendQRCode() throws NumberFormatException, HibernateDaoSupportException {
		
		return SUCCESS;
	}
	
	/**
	 * 发送二维码彩信
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws HibernateDaoSupportException
	 */
	public String sendQRCode() throws NumberFormatException, HibernateDaoSupportException {
		String userIds = getRequest().getParameter("userIds");// 发送用户
		String sendAllFlag = getRequest().getParameter("sendAllFlag");// 指定是全部发送还是部分发送
		String meetingId = getRequest().getParameter("meetingId");// 会议ID
		String messageTitle = getRequest().getParameter("messageTitle");// 彩信标题
		String messageContent = getRequest().getParameter("messageContent");// 彩信文本
		
		log.info("userIds:" + userIds + ",sendAllFlag:" + sendAllFlag + ",meetingId:" + meetingId + ",messageContent:"
				+ messageContent);
		
		// 通过meetingId来获取会议信息
		Meeting meeting = null;
		try {
			meeting = meetingService.findById(Long.valueOf(meetingId));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		if (meeting == null) {
			log.error("后台发送二维码失败，会议信息不存在");
			return SUCCESS;
		}
		
		// 是否全部发送
		List<User> userLst = null;
		if ("Y".equals(sendAllFlag)) {
			userLst = userService.getMeetingMember(Long.valueOf(meetingId), null, null, null, null,null);
		} else {
			if (userIds != null && userIds.length() > 1) {
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			userLst = userService.getMeetingMemberByIds(Long.valueOf(meetingId), userIds);
		}
		
		for (User user : userLst) {
			if (user == null) {
				log.error("后台发送二维码失败，user信息不存在");
				continue;
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("姓名：");
			sb.append(user.getName());
			sb.append("\n");
			sb.append("会议：");
			sb.append(meeting.getName());
			sb.append("\n");
			sb.append("会议号：");
			sb.append(meeting.getId());
			sb.append("\n");
			sb.append("手机号：");
			sb.append(user.getMobile());
			
			log.debug("sms QRCode content:" +sb.toString());
			BufferedImage image = null;
			try {
				image = QRCodeGen.genQRCode(sb.toString());
			} catch (WriterException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				ImageIO.write(image,"jpg", bos);
			} catch (IOException e) {
				e.printStackTrace();
			}
			final byte[] byteArray = bos.toByteArray();
			
			// 发送二维码彩信
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.debug("send qrcode mobile = "+user.getMobile()+" submessage = "+messageTitle);
			SmsSender.sendMms(new String[] { user.getMobile() }, messageTitle, messageContent, byteArray);
			
			//TODO
			//记录彩信发送内容
			// 收信人
			Set<User> recipient = new HashSet<User>();
			recipient.add(user);
			// 基本信息赋值
			MeetingSms meetingSms = new MeetingSms();
			// 会议ID
			meetingSms.setMeetingId(Long.valueOf(meetingId));
			meetingSms.setDelay(false);
			meetingSms.setRecipient(recipient);
			meetingSms.setCreateTime(new Date());
			meetingSms.setState(1);
			meetingSms.setMessages(messageTitle+messageContent);
			log.debug("Send Message Content:" + meetingSms.getMessages());
			// 最后保存
			try {
				meetingSmsService.add(meetingSms);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			
		}
		
		getRequest().setAttribute("meetingId", meetingId);
		this.setMeetingId(meetingId);
		return SUCCESS;
	}
	
	/**
	 * 跳转到发送彩信页面
	 * @return
	 * @throws NumberFormatException
	 * @throws HibernateDaoSupportException
	 */
	public String goSendMMS() throws NumberFormatException, HibernateDaoSupportException {

		return SUCCESS;
	}
	
	/**
	 * 发送彩信
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws HibernateDaoSupportException
	 */
	public String sendMMS() throws NumberFormatException, HibernateDaoSupportException {
		
		try{
		String userIds = getRequest().getParameter("userIds");// 发送用户
		String sendAllFlag = getRequest().getParameter("sendAllFlag");// 指定是全部发送还是部分发送
		String meetingId = getRequest().getParameter("meetingId");// 会议ID
		String messageTitle = getRequest().getParameter("messageTitle");// 彩信标题

		log.info("userIds:" + userIds + ",sendAllFlag:" + sendAllFlag + ",meetingId:" + meetingId );

		// 通过meetingId来获取会议信息
		Meeting meeting = null;
		try {
			meeting = meetingService.findById(Long.valueOf(meetingId));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		if (meeting == null) {
			log.error("后台发送彩信失败，会议信息不存在");
			return SUCCESS;
		}

		// 是否全部发送
		List<User> userLst = null;
		if ("Y".equals(sendAllFlag)) {
			userLst = userService.getMeetingMember(Long.valueOf(meetingId), null, null, null, null, null);
		} else {
			if (userIds != null && userIds.length() > 1) {
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			userLst = userService.getMeetingMemberByIds(Long.valueOf(meetingId), userIds);
		}
		
		for (User user : userLst) {
			if (user == null) {
				log.error("后台发送彩信失败，user信息不存在");
				continue;
			}

//			FileInputStream fis = new FileInputStream(mmsImage);
	        //create FileInputStream which obtains input bytes from a file in a file system
	        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.
	 
	        //InputStream in = resource.openStream();
//	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	        byte[] buf = new byte[1024];
//            for (int readNum; (readNum = fis.read(buf)) != -1;) {
//                bos.write(buf, 0, readNum); 
//            }
//	        byte[] bytes = bos.toByteArray();
			
			// 发送二维码彩信
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//分析数据，动态生成多文本和多图片的彩信
			List<CPFrag> fragList = new ArrayList<CPFrag>();
			if(mmsList !=null && mmsList.size() > 0){
				int idx = 1;
				final String sReqNo = MasServiceManager.getReqNo(); 
				for(MMSFrag mf:mmsList){
					if(mf.getMmsImage() != null){
						FileInputStream fis = new FileInputStream(mf.getMmsImage());
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
				        byte[] buf = new byte[1024];
			            for (int readNum; (readNum = fis.read(buf)) != -1;) {
			                bos.write(buf, 0, readNum); 
			            }
				        byte[] bytes = bos.toByteArray();
				        
						final CPFrag cpfrag2 = new CPFrag();
						cpfrag2.setId(sReqNo +"00"+idx);
						cpfrag2.setLength(bytes.length);
						cpfrag2.setName(sReqNo +"00"+(idx++)+".jpg");
						cpfrag2.setType(9);
						cpfrag2.setContent(bytes); 
						fragList.add(cpfrag2);
					}
					if(StringUtil.isNotEmpty(mf.getMessageContent())){
						final CPFrag cpfrag1 = new CPFrag();
						cpfrag1.setId(sReqNo + "00"+idx);
						cpfrag1.setLength(mf.getMessageContent().length());
						cpfrag1.setName(sReqNo + "00"+(idx++)+".txt");
						cpfrag1.setType(1);
						try {
							cpfrag1.setContent(mf.getMessageContent().getBytes("GBK"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						fragList.add(cpfrag1);
					}
				}
			}
			
			if (fragList == null || fragList.size() == 0) {
				log.error("后台发送彩信失败，彩信信息为空");
				return SUCCESS;
			}
			
			CPFrag[] cpfrags = new CPFrag[fragList.size()];
			for(int i =0;i<fragList.size();i++){
				cpfrags[i]=fragList.get(i);
			}
			log.debug("send mms mobile = "+user.getMobile()+" submessage = "+messageTitle);
			SmsSender.sendMms(new String[] { user.getMobile() }, messageTitle, cpfrags );
			
			//记录彩信发送内容
			// 收信人
			Set<User> recipient = new HashSet<User>();
			recipient.add(user);
			// 基本信息赋值
			MeetingSms meetingSms = new MeetingSms();
			// 会议ID
			meetingSms.setMeetingId(Long.valueOf(meetingId));
			meetingSms.setDelay(false);
			meetingSms.setRecipient(recipient);
			meetingSms.setCreateTime(new Date());
			meetingSms.setState(1);
			
			meetingSms.setMessages(messageTitle);
			
			log.debug("Send Message Content:" + meetingSms.getMessages());
			// 最后保存
			meetingSmsService.add(meetingSms);
			
		}

		getRequest().setAttribute("meetingId", meetingId);
		this.setMeetingId(meetingId);
		
		} catch (Exception e) {
			log.error("mms error 彩信发送异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 调整看是否可以发送短信
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws HibernateDaoSupportException
	 */
	public String checkSmsSendStatus() throws NumberFormatException, HibernateDaoSupportException {
		String forward = SUCCESS;
		// 获取必要参数
		String meetingId = getRequest().getParameter("meetingId");
		String isSelectAll = getRequest().getParameter("isSelectAll");
		String recieverIds = getRequest().getParameter("recieverIds");
		log.debug("检查是否仍旧可以发送短信meetingId:" + meetingId + ",isSelectAll:" + isSelectAll + ",recieverIds" + recieverIds);

		// 会议预订发送数
		Meeting meeting = meetingService.getMeetingByPk(Long.valueOf(meetingId));
		int preCount = Integer.parseInt(StringUtil.null2Str(meeting.getFreeSmsNum()));

		// 当前会议实际发送数
		List<MeetingSms> totalSmsMessageList = meetingSmsService.getMeetingSmsList(Long.valueOf(meetingId));
		int smsActCount = 0;
		if (totalSmsMessageList != null && totalSmsMessageList.size() > 0) {
			smsActCount = totalSmsMessageList.size();
		}
		// 还可以发送数
		int canSendCount = preCount - smsActCount;
		log.debug("preCount:" + preCount + ",smsActCount:" + smsActCount + ",canSendCount:" + canSendCount);

		// 本次需要发送数
		int currentSendNum = 0;
		// 全部发送
		if ("1".equals(isSelectAll)) {
			// 查询此会议下的人数
			List<User> userLst = null;
			userLst = userService.getMeetingMember(Long.valueOf(meetingId), null, null, null, null, null);
			if (userLst != null) {
				currentSendNum = userLst.size();
			}
		} else if ("0".equals(isSelectAll)) {// 部分发送
			if (StringUtil.isNotEmpty(recieverIds)) {
				String[] recieverIdsArr = recieverIds.split(",");
				currentSendNum = recieverIdsArr.length;
			}
		}

		// 可发送
		this.resultMap.put("preCount", preCount);
		// 已发送
		this.resultMap.put("smsActCount", smsActCount);
		// 还可以发送
		this.resultMap.put("canSendCount", canSendCount);

		// 还可以发送短信吗？
		if (canSendCount - currentSendNum >= 0) {
			this.resultMap.put("sendStatus", true);

		} else {
			this.resultMap.put("sendStatus", false);
			String message = "你此次需要发送短信条数为" + currentSendNum + "条(超过实际可发条数" + canSendCount + "条),暂不能发送";
			this.resultMap.put("message", message);
		}

		return forward;
	}

	/**
	 * 所选会议中 需要发送短信的用户选择
	 */
	public String getMeetingMember() {
		String forward = SUCCESS;
		//获取meetingid
		String meetingId = getRequest().getParameter("meetingId");
		String recieverIds = getRequest().getParameter("recieverIds");

		String mobile = getRequest().getParameter("mobile");
		String name = getRequest().getParameter("name");
		String memberLevel = getRequest().getParameter("memberLevel");
		String job = getRequest().getParameter("job");
		String signEventId=getRequest().getParameter("signEventId");
		//TODO 关于短信发送 签到状态的过滤
		String signType=getRequest().getParameter("signType");
		String calSelectNum=getRequest().getParameter("calSelectNum");
		if(StringUtil.isEmpty(calSelectNum)){
			calSelectNum="false";
		}
		
		//取签到事件
		List<SignEvent> signEventLst= signEventService.findByMeetingId(Long.valueOf(meetingId));
		getRequest().setAttribute("signEventLst", signEventLst);
		
		String userIds="";
		if (StringUtil.isNotEmpty(signEventId)) {
			List lstUser = null;
			if ("1".equals(signType)) {
				cardService.getUserBySignStatus(meetingId, signEventId, "1",
						"3");
			}
			if ("2".equals(signType)) {
				lstUser = cardService.getUserBySignStatus(meetingId,
						signEventId, "2", "4");
			}
			
			if(lstUser!=null){
				for (Object obj : lstUser) {
					userIds += String.valueOf(obj) + ",";
				}
			}
		
		}
		
		// 通过meetingId查询所有的用户信息 带到前台页面 供会务人员选择	
		List<User> userLst = null;
		if (StringUtil.isNotEmpty(userIds)) {
//			userLst = userService.getMeetingMemberByIds(
//					Long.valueOf(meetingId),
//					userIds.substring(0, userIds.length() - 1));
		
			userLst = userService.getMeetingMember(Long.valueOf(meetingId),
					mobile, name, memberLevel, job,
					userIds.substring(0, userIds.length() - 1));
			
		} else if(StringUtil.isEmpty(signEventId)){
			userLst = userService.getMeetingMember(Long.valueOf(meetingId),
					mobile, name, memberLevel, job,null);
		}
		log.debug("userIds");
		
		if(userLst!=null){
			for(User user:userLst){
				MeetingMember mm=meetingMemberService.selectById(user.getId(), Long.valueOf(meetingId));
				user.setMeetingMember(mm);
			}
		}

		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		getRequest().setAttribute("sendTime", sdf.format(new Date()));

		getRequest().setAttribute("userLst", userLst);
		getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute("recieverIds", recieverIds);

		// back varibal
		getRequest().setAttribute("mobile", mobile);
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("memberLevel", memberLevel);
		getRequest().setAttribute("job", job);
		getRequest().setAttribute("signEventId", signEventId);
		getRequest().setAttribute("signType", signType);
		getRequest().setAttribute("calSelectNum", calSelectNum);
		
		return forward;

	}

	/**
	 * 发送短信给指定用户 或者全体用户
	 * 
	 * @throws ServiceException
	 * @throws NumberFormatException
	 */
	public String doSendMessage() throws NumberFormatException, ServiceException {
		String forward = SUCCESS;

		String userIds = getRequest().getParameter("userIds");
		String sendAllFlag = getRequest().getParameter("sendAllFlag");// 指定是全部发送还是部分发送
		String meetingId = getRequest().getParameter("meetingId");// 会议ID
		String isTimingSend = getRequest().getParameter("isTimingSend");// 是否需要定时发送
		String sendTime = getRequest().getParameter("sendTime");// all/some
		String messageContent = getRequest().getParameter("messageContent");
		String sign = getRequest().getParameter("sign");

		log.info("userIds:" + userIds + ",sendAllFlag:" + sendAllFlag + ",meetingId:" + meetingId + ",isTimingSend:"
				+ isTimingSend + ",sendTime:" + sendTime + ",messageContent:" + messageContent);

		// 通过meetingId来获取会议信息
		Meeting meeting = meetingService.findById(Long.valueOf(meetingId));
		messageContent = MessageSendUtil.converTemplateToContent(messageContent, meeting, sign);

		// 是否全部发送
		List<User> userLst = null;
		if ("Y".equals(sendAllFlag)) {
			userLst = userService.getMeetingMember(Long.valueOf(meetingId), null, null, null, null, null);
		} else {
			if (userIds != null && userIds.length() > 1) {
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			userLst = userService.getMeetingMemberByIds(Long.valueOf(meetingId), userIds);
		}

		Date dtSendTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dtSendTime = sdf.parse(sendTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		// 进行循环
		for (User user : userLst) {
			// 收信人
			Set<User> recipient = new HashSet<User>();
			recipient.add(user);
			// 基本信息赋值
			MeetingSms meetingSms = new MeetingSms();
			// 会议ID
			meetingSms.setMeetingId(Long.valueOf(meetingId));
			// 是否延迟发送
			if ("Y".equals(isTimingSend)) {
				meetingSms.setDelay(true);
				meetingSms.setSendTime(dtSendTime);
			} else {
				meetingSms.setDelay(false);
			}

			meetingSms.setRecipient(recipient);
			meetingSms.setCreateTime(new Date());
			meetingSms.setState(0);
			// 全部发送
			// 短信内容
			// 先转化内容
			// 查询MEMBER
			MeetingMember member = meetingMemberService.selectById(user.getId(), Long.valueOf(meetingId));
			user.setMeetingMember(member);

			meetingSms.setMessages(MessageSendUtil.converTemplateToContent(messageContent, user));
			log.debug("Send Message Content:" + meetingSms.getMessages());
			// 最后保存
			try {
				meetingSmsService.add(meetingSms);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}

		getRequest().setAttribute("meetingId", meetingId);
		this.setMeetingId(meetingId);
		return forward;
	}

	/**
	 * 获取列表
	 * 
	 * @return
	 * @throws HibernateDaoSupportException
	 * @throws NumberFormatException
	 */
	public String getMessageList() throws NumberFormatException, HibernateDaoSupportException {
		String forward = SUCCESS;
		// 获取List
		String meetingId = getRequest().getParameter("meetingId");
		String messages = getRequest().getParameter("messages");
		String mobile = getRequest().getParameter("mobile");
		String cPage = getRequest().getParameter("currentPage");
		String state = getRequest().getParameter("state");

		if (cPage != null && !"".equals(cPage)) {
			currentPage = Integer.parseInt(cPage);
		}
		log.info("获取短信列表参数:meetingId:" + meetingId + ",messages:" + messages + ",mobile:" + mobile + ",cPage:" + cPage
				+ ",state:" + state);

		// 进行分页处理
		Pager<MeetingSms> pager = null;
		pager = meetingSmsService.getMeetingSmsPager(currentPage, pageSize, Long.valueOf(meetingId), messages, mobile,
				state);

		List<MeetingSms> totalSmsMessageList = meetingSmsService.getMeetingSmsList(Long.valueOf(meetingId));
		int smsActCount = 0;
		if (totalSmsMessageList != null && totalSmsMessageList.size() > 0) {
			smsActCount = totalSmsMessageList.size();
		}
		int preCount = 0;
		Meeting meeting = meetingService.getMeetingByPk(Long.valueOf(meetingId));
		if (!StringUtil.isNotNumber(meeting.getFreeSmsNum())) {
			preCount = Integer.parseInt(StringUtil.null2Str(meeting.getFreeSmsNum()));
		}

		// 还可以发送数
		int canSendCount = preCount - smsActCount;
		log.debug(",preCount:" + preCount + "smsActCount:" + smsActCount + ",canSendCount:" + canSendCount);

		getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute("pager", pager);
		getRequest().setAttribute("messages", messages);
		getRequest().setAttribute("mobile", mobile);
		getRequest().setAttribute("state", state);

		getRequest().setAttribute("preCount", preCount);// 可发送
		getRequest().setAttribute("smsActCount", smsActCount);// 已发送
		getRequest().setAttribute("canSendCount", canSendCount);// 还可以发送

		return forward;
	}

	/**
	 * 获取短信内容
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws NumberFormatException
	 */
	public String getMessage() throws NumberFormatException, ServiceException {
		String forward = SUCCESS;
		String meetingId = getRequest().getParameter("meetingId");
		String messageId = getRequest().getParameter("messageId");
		MeetingSms meetingSms = meetingSmsService.findById(Long.valueOf(messageId));

		// 设置返回消息
		getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute("meetingSms", meetingSms);

		return forward;

	}

	/**
	 * 批量删除
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String batchDelMessage() throws NumberFormatException, ServiceException {
		String forword = SUCCESS;
		String ids = getRequest().getParameter("ids");
		String meetingId = getRequest().getParameter("meetingId");

		if (ids != null && ids.length() > 0) {
			ids = ids.substring(0, ids.length() - 1);
		}
		String[] userIdArray = ids.split(",");
		for (int i = 0; i < userIdArray.length; i++) {
			MeetingSms meetingSms = meetingSmsService.findById(Long.valueOf(userIdArray[i]));
			meetingSms.setState(2);// 表示删除
			// meetingSmsService.delete(meetingSms);
			meetingSmsService.saveOrUpdate(meetingSms);

		}

		this.getRequest().setAttribute("meetingId", meetingId);

		// log.debug("meetingId"+meetingId);
		// Long meetingIdLong=Long.valueOf(meetingId);
		// log.debug("meetingIdLong:"+meetingIdLong);
		// meeting.setId(meetingIdLong);
		// meeting.setId();
		// getRequest().setAttribute("meeting.id", meetingId);

		// Long.parseLong(meetingId);

		return forword;
	}

	public void setMeetingMemberService(MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setMeetingSmsService(MeetingSmsService meetingSmsService) {
		this.meetingSmsService = meetingSmsService;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public List<MMSFrag> getMmsList() {
		return mmsList;
	}

	public void setMmsList(List<MMSFrag> mmsList) {
		this.mmsList = mmsList;
	}



}
