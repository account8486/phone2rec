package com.wondertek.meeting.action.meeting;

import java.util.Date;
import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.common.SysUtil;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.SignIn;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.SignInService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 签到
 * 
 * @author 金祝华
 */
public class SignInAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3217717041180325042L;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private SignInService signInService;
	private UserService userService;
	private MeetingMemberService meetingMemberService;

	private SignIn signIn;
	private String mobile;
	private String name;

	private Long meetingId;

	/**
	 * 查询签到码列表
	 * 
	 * @return
	 */
	public String list() {

		if (signIn == null) {
			signIn = new SignIn();
		}

		signIn.setMeetingId(meetingId);

		Pager<SignIn> userPager = null;
		try {
			userPager = signInService.findPager(signIn, mobile,name, currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("query list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", userPager);

		return SUCCESS;
	}

	/**
	 * 一键生成签到码
	 * 
	 * @return
	 */
	public String genAllSignCode() {

		List<MeetingMember> list = meetingMemberService.queryMemberList(meetingId);
		if (list == null || list.size() == 0) {
			errMsg = "当前会议没有会议成员，请先新增会议成员。";

			return SUCCESS;
		} else {
			for (MeetingMember member : list) {
				SignIn temp = signInService.querySignIn(member.getMeetingId(), member.getUserId());

				if (temp != null) {
					continue; // 已经存在的不生成。
				}

				temp = new SignIn();
				temp.setMeetingId(member.getMeetingId());
				User user = new User();
				user.setId(member.getUserId());
				temp.setUser(user);
				temp.setSignCode(SysUtil.genSignCode());
				try {
					signInService.saveOrUpdate(temp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		errMsg = "生成签到码成功。";
		return SUCCESS;
	}

	/**
	 * 生成单个签到码
	 * 
	 * @return
	 */
	public String genSignCode() {
		if (signIn == null || signIn.getId() == null) {
			errMsg = "对不起，生成签到码失败。";
			return SUCCESS;
		}

		try {
			signIn = signInService.findById(signIn.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
			errMsg = "对不起，生成签到码失败。";
			return SUCCESS;
		}

		signIn.setSignCode(SysUtil.genSignCode());
		try {
			signInService.saveOrUpdate(signIn);
		} catch (ServiceException e) {
			e.printStackTrace();
			errMsg = "对不起，生成签到码失败。";
			return SUCCESS;
		}

		errMsg = "生成签到码成功。";
		return SUCCESS;
	}

	/**
	 * 客户端签到
	 * 
	 * @return
	 */
	public String clientSignIn() {
		String meetingId = this.getParameter("meetingId");// 会议ID

		// 1.检查参数合法性
		if (StringUtil.isEmpty(meetingId)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("sign.in.params.empty.meetingId"));
			return SUCCESS;
		}

		Long mId = 0L;
		try {
			mId = Long.parseLong(meetingId);
		} catch (NumberFormatException ex) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("sign.in.params.invalid.meetingId"));
			return SUCCESS;
		}

		if (StringUtil.isEmpty(mobile)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("sign.in.params.empty.mobile"));
			return SUCCESS;
		}

		User user = userService.selectByMobile(mobile);

		if (user == null) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("sign.in.params.error.mobile"));
			return SUCCESS;
		}

		signIn = new SignIn();
		signIn.setMeetingId(mId);
		signIn.setUser(user);
		signIn.setSignTime(new Date());
		signIn.setPortalType(SysConstants.PORTAL_TYPE_CLIENT);

		// 2.签到
		try {
			signInService.signIn(signIn);
		} catch (ServiceException e) {
			log.debug(getText(e.getMessage()));
			resultMap.put("retcode", e.getMessage());
			resultMap.put("retmsg", getText(e.getMessage()));
			return SUCCESS;
		}

		// 签到成功
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", getText("sign.in.success"));

		return SUCCESS;
	}

	/**
	 * wap签到（管理员用）
	 * 
	 * @return
	 */
	public String wapSignIn() {
		String signCode = signIn.getSignCode();// 签到码

		// 1.检查参数合法性
		if (meetingId == null) {
			errMsg = getText("sign.in.params.empty.meetingId");
			return ERROR;
		}

		if (StringUtil.isEmpty(mobile)) {
			errMsg = getText("sign.in.params.empty.mobile");
			return INPUT;
		}

		User user = userService.selectByMobile(mobile);

		if (user == null) {
			errMsg = getText("sign.in.params.error.mobile");
			return INPUT;
		}

		if (StringUtil.isEmpty(signCode)) {
			errMsg = getText("sign.in.params.empty.signCode");
			return INPUT;
		}

		signIn = new SignIn();
		signIn.setMeetingId(meetingId);
		signIn.setUser(user);
		signIn.setSignTime(new Date());
		signIn.setSignCode(signCode);
		signIn.setPortalType(SysConstants.PORTAL_TYPE_WAP);

		// 签到
		try {
			signInService.signIn(signIn);
		} catch (ServiceException e) {
			errMsg = getText(e.getMessage());
			return INPUT;
		}

		// 签到成功
		errMsg = getText("sign.in.success");

		return SUCCESS;
	}

	/**
	 * web签到（管理员用）
	 * 
	 * @return
	 */
	public String webSignIn() {
		String signCode = signIn.getSignCode();// 签到码

		// 1.检查参数合法性
		if (meetingId == null) {
			errMsg = getText("sign.in.params.empty.meetingId");
			return ERROR;
		}

		if (StringUtil.isEmpty(mobile)) {
			errMsg = getText("sign.in.params.empty.mobile");
			return INPUT;
		}

		User user = userService.selectByMobile(mobile);

		if (user == null) {
			errMsg = getText("sign.in.params.error.mobile");
			return INPUT;
		}

		if (StringUtil.isEmpty(signCode)) {
			errMsg = getText("sign.in.params.empty.signCode");
			return INPUT;
		}

		signIn = new SignIn();
		signIn.setMeetingId(meetingId);
		signIn.setUser(user);
		signIn.setSignTime(new Date());
		signIn.setSignCode(signCode);
		signIn.setPortalType(SysConstants.PORTAL_TYPE_WEB);

		// 签到
		try {
			signInService.signIn(signIn);
		} catch (ServiceException e) {
			errMsg = getText(e.getMessage());
			return INPUT;
		}

		// 签到成功
		errMsg = getText("sign.in.success");

		return SUCCESS;
	}

	/**
	 * web用户查询签到码
	 * 
	 * @return
	 */
	public String webSignCode() {

		Long userId = this.getUserIdFromSession();// 用户id

		signIn = signInService.querySignIn(meetingId, userId);
		// if (signIn == null || StringUtil.isEmpty(signIn.getSignCode())) {
		// return ERROR;
		// }

		return SUCCESS;
	}

	/**
	 * wap用户查询签到码
	 * 
	 * @return
	 */
	public String wapSignCode() {
		Long userId = this.getUserIdFromSession();// 用户id

		signIn = signInService.querySignIn(meetingId, userId);
		// if (signIn == null || StringUtil.isEmpty(signIn.getSignCode())) {
		// return ERROR;
		// }

		return SUCCESS;
	}

	public SignIn getSignIn() {
		return signIn;
	}

	public void setSignIn(SignIn signIn) {
		this.signIn = signIn;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public SignInService getSignInService() {
		return signInService;
	}

	public void setSignInService(SignInService signInService) {
		this.signInService = signInService;
	}

	public MeetingMemberService getMeetingMemberService() {
		return meetingMemberService;
	}

	public void setMeetingMemberService(MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
}
