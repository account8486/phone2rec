package com.wondertek.meeting.action.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.SignInHelper;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.MeetingSms;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.UserApply;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.MeetingSmsService;
import com.wondertek.meeting.service.UserApplyService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.Encrypt;
import com.wondertek.meeting.util.MessageSendUtil;
import com.wondertek.meeting.util.RandomUtil;
import com.wondertek.meeting.util.StringUtil;

public class UserApplyAction extends BaseAction {

	private static final long serialVersionUID = -9080640196412837858L;

	UserApplyService userApplyService;
	MeetingService meetingService;
	UserService userService;
	MeetingMemberService meetingMemberService;
	private Long meetingId;
	private MeetingSmsService meetingSmsService;

	/**
	 * 用户申请加入
	 * 
	 * @return
	 */
	public String addUserApply() {
		String forward = this.SUCCESS;

		String meetingId = getRequest().getParameter("meetingId");// 会议号
		String mobile = getRequest().getParameter("mobile");// 电话号码
		String name = getRequest().getParameter("name");// 姓名
		String city = getRequest().getParameter("city");// 城市
		String job = getRequest().getParameter("job");// 工作职务
		String mailbox = getRequest().getParameter("mailbox");// 邮箱
		String gender = getRequest().getParameter("gender");// 性别

		log.debug("meetingId:" + meetingId + ",mobile:" + mobile + ",name:"
				+ name + ",city:" + city + ",job:" + job + ",mailbox:"
				+ mailbox + ",gender:" + gender);

		if (StringUtil.isEmpty(meetingId)) {
			resultMap.put("retcode", "1");
			resultMap.put("retmsg", "申请加入失败,会议号不能为空");
			log.debug("申请加入失败,会议号不能为空");
		}

		if (StringUtil.isEmpty(mobile)) {
			resultMap.put("retcode", "1");
			resultMap.put("retmsg", "申请加入失败,电话号码不能为空");
			log.debug("retmsg", "申请加入失败,电话号码不能为空");
		}

		if (StringUtil.isEmpty(name)) {
			resultMap.put("retcode", "1");
			resultMap.put("retmsg", "申请加入失败,姓名不能为空");
			log.debug("retmsg", "申请加入失败,姓名不能为空");
		}

		try {
			// 进行会议是否存在的判断
			Meeting meeting = meetingService.findById(Long.valueOf(meetingId));
			if (meeting == null) {
				resultMap.put("retcode", "1");
				resultMap.put("retmsg", "你所加入的会议不存在。");
				log.debug("retmsg", "你所加入的会议不存在。");
				return forward;
			}
			// 判断这个人是否已经在会议里了
			User user = userService.selectByMobile(mobile);
			if (user != null) {
				// 通过USER_ID来看看是否存在
				MeetingMember member = meetingMemberService.selectById(
						user.getId(), Long.valueOf(meetingId));
				if (member != null) {
					resultMap.put("retcode", "1");
					resultMap.put("retmsg", "加入失败,你已经是会议成员了。");
					log.debug("retmsg", "加入失败,你已经是会议成员了。");
					return forward;
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			resultMap.put("retcode", "1");
			resultMap.put("retmsg", "申请加入失败,请联系系统管理员");
			e.printStackTrace();
		}

		// 如果会议存在 那么判断其是否在会议里
		UserApply userApply = new UserApply();
		userApply.setMobile(mobile);
		userApply.setName(name);
		userApply.setMeetingId(Long.valueOf(meetingId));
		userApply.setCity(city);
		userApply.setJob(job);
		userApply.setMailbox(mailbox);
		userApply.setGender(gender == null || "".equals(gender) ? null
				: Integer.valueOf(gender));
		userApply.setCreateTime(new Date());

		try {
			userApplyService.add(userApply);
			resultMap.put("retcode", "0");
			resultMap.put("retmsg", "申请加入成功,等待会务人员审批.");
			log.debug("申请加入成功,等待会务人员审批.");
		} catch (ServiceException e) {
			resultMap.put("retcode", "1");
			resultMap.put("retmsg", "申请加入失败,请联系系统管理员");
			e.printStackTrace();
		}

		return forward;
	}

	/**
	 * 删除此次申请
	 * 
	 * @return
	 */
	public String deleteUserApply() {
		String forward = SUCCESS;
		// 获取id
		String id = getRequest().getParameter("id");
		try {
			// 查询到这个申请
			UserApply userApply = userApplyService.findById(Long.valueOf(id));
			Long meetingId = userApply.getMeetingId();
			// 放入到其中
			this.setMeetingId(meetingId);
			userApplyService.delete(userApply);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
	
		return forward;
	}

	/**
	 * 做加入会议申请的审批
	 * 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public String auditUserApply() throws NumberFormatException, Exception {
		String forward = SUCCESS;
		// 获取id
		String applyId = getRequest().getParameter("applyId");
		// 审批意见
		String auditOpinion = getRequest().getParameter("auditOpinion");
		String memberLevel=getRequest().getParameter("memberLevel");
		//获取短信内容
		String messageContent = getRequest().getParameter("messageContent");
		
		// 查询到这个申请
		UserApply userApply = userApplyService.findById(Long.valueOf(applyId));

		Long meetingId = userApply.getMeetingId();
		// 放入到其中
		getRequest().setAttribute("meetingId", meetingId);

		User user = userService.selectByMobile(userApply.getMobile());

		// 第一步
		// 数据库中不存在 就重新NEW一个 数据库中存在 就更新之
		if (user == null) {
			user = new User();
			//生成密码
			user.setPassword(Encrypt.encrypt(RandomUtil.getRandomCode(), null));
		}

		user.setMobile(userApply.getMobile());
		user.setName(userApply.getName());
		user.setGender(userApply.getGender());
		user.setCreateTime(new Date());
		//默认设置一个状态 启用
		user.setState(1);
		
		// 保存主表USER
		userService.saveOrUpdate(user);

		// 第二步
		MeetingMember meetingMember = new MeetingMember();
		meetingMember.setMeetingId(userApply.getMeetingId());
		meetingMember.setUserId(user.getId());
		meetingMember.setMemberLevel(Integer.valueOf(memberLevel));
		meetingMember.setSortCode(null);
		meetingMember.setAddInContacts("Y");//加入
		meetingMember.setMobileIsDisplay(1);
		meetingMember.setCity(userApply.getCity());
		meetingMember.setJob(userApply.getJob());
		meetingMember.setMailbox(userApply.getMailbox());
		meetingMember.setRoomNumber("");
		meetingMember.setRoomNumberIsDisplay(1);
		meetingMember.setJobIsDisplay(1);
		meetingMember.setCreateTime(new Date());
		meetingMember.setModifyTime(new Date());
		
		// 保存子表
		meetingMemberService.saveOrUpdate(meetingMember);

		// 第三步
		// 保存完毕后更新UserApply表
		userApply.setState(1);
		userApply.setAuditTime(new Date());
		userApply.setAuditor(this.getAdminUserIdFromSession());
		userApply.setAuditOpinion(auditOpinion);
		userApplyService.saveOrUpdate(userApply);

		this.setMeetingId(meetingId);
		
		//发送短信
		Set<User> recipient = new HashSet<User>();
		recipient.add(user);
		MeetingSms meetingSms = new MeetingSms();
		// 会议ID
		meetingSms.setMeetingId(meetingId);
		meetingSms.setDelay(false);
		meetingSms.setRecipient(recipient);
		meetingSms.setCreateTime(new Date());
		meetingSms.setState(0);
		
		//查询MEMBER
		MeetingMember member=meetingMemberService.selectById(user.getId(), Long.valueOf(meetingId));
		user.setMeetingMember(member);
		
		meetingSms.setMessages(MessageSendUtil.converTemplateToContent(messageContent, user, String.valueOf(meetingId)));
		
		// 最后保存
		try {
			meetingSmsService.add(meetingSms);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		
		// 生成签到码
		try {
			SignInHelper.getInstance().genSignCode(meetingId,
					user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回到对应页面

		return forward;
	}

	/**
	 * 获取列表
	 * 
	 * @return
	 */
	public String getUserApplyPager() {
		String forward = SUCCESS;

		String meetingId = getRequest().getParameter("meetingId");
		String cPage = getRequest().getParameter("currentPage");
		if (cPage != null && !"".equals(cPage)) {
			currentPage = Integer.parseInt(cPage);
		}

		// 进行分页处理
		Pager<UserApply> pager = null;
		pager = userApplyService.getUserApplyPager(currentPage, pageSize,
				Long.valueOf(meetingId));

		getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute("pager", pager);

		return forward;
	}

	/**
	 * 通过ID来获取
	 * 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public String getEditUserApplly() throws NumberFormatException, Exception {
		String forward = SUCCESS;
		// 获取id
		String id = getRequest().getParameter("id");
		UserApply userApply = userApplyService.findById(Long.valueOf(id));

		getRequest().setAttribute("userApply", userApply);

		return forward;
	}

	public void setUserApplyService(UserApplyService userApplyService) {
		this.userApplyService = userApplyService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MeetingMemberService getMeetingMemberService() {
		return meetingMemberService;
	}

	public void setMeetingMemberService(
			MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserApplyService getUserApplyService() {
		return userApplyService;
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public MeetingSmsService getMeetingSmsService() {
		return meetingSmsService;
	}

	public void setMeetingSmsService(MeetingSmsService meetingSmsService) {
		this.meetingSmsService = meetingSmsService;
	}

}
