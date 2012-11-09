/**
 * 
 */
package com.wondertek.meeting.action.meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.appfuse.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.client.view.CommentView;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.MeetingComment;
import com.wondertek.meeting.model.MeetingPost;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MeetingCommentService;
import com.wondertek.meeting.service.MeetingPostService;
import com.wondertek.meeting.util.StringUtil;

/**
 * @author rain
 * 
 */
public class MeetingCommentAction extends BaseAction {
	private static final long serialVersionUID = -7851923195052792928L;
	@Autowired
	private MeetingCommentService meetingCommentService;
	@Autowired
	private MeetingPostService meetingPostService;

	public String commentAdd() {
		final String content = getParameter("content");
		final String postNo = getParameter("postNo");
		final String type = getParameter("type");
		if (!StringUtils.isNumeric(postNo)) {
			response(100, "无效的参数");
		}

		final MeetingComment comment = new MeetingComment();
		comment.setContent(content);
		comment.setTime(new Date());
		comment.setPostId(Long.valueOf(postNo));
		if (StringUtils.equalsIgnoreCase("admin", type)) {
			final AdminUser user = (AdminUser) this.getFromSession(SessionKeeper.SESSION_ADMIN_USER);
			comment.setCreator(user.getId());
			comment.setCreatorName(user.getName());
			comment.setCreatorJob(user.getJob());
			comment.setCreatorType(1);
		} else {
			final User user = (User) this.getFromSession(SessionKeeper.SESSION_USER);
			comment.setCreator(user.getId());
			comment.setCreatorName(user.getName());
			comment.setCreatorType(0);
		}
		try {
			final Long commentId = meetingCommentService.add(comment);
			meetingPostService.addCommentCount(Long.valueOf(postNo));
			response(0, SUCCESS, commentId);
		} catch (ServiceException e) {
			response(1, e.getMessage());
		}

		return SUCCESS;
	}

	public String commentDelete() {

		try {
			final Long id = Long.valueOf(getParameter("id"));
			final Long postId = Long.valueOf(getParameter("postId"));
			final MeetingComment comment = new MeetingComment();
			comment.setId(id);
			meetingCommentService.delete(comment);
			meetingPostService.decreaseCommentCount(postId);
			resultMap.put("result", true);
		} catch (Exception e) {
			resultMap.put("result", false);
		}
		return SUCCESS;
	}

	public String commentList_admin() {
		try {
			final Long postId = Long.valueOf(getParameter("postId"));
			final Long meetingId = Long.valueOf(getParameter("meetingId"));
			final Pager<MeetingComment> pager = meetingCommentService.queryPagerByPostId(postId, currentPage, pageSize);
			meetingPostService.addViewCount(postId);
			List<MeetingComment> comments = pager.getPageRecords();
			for (MeetingComment comment : comments) {
				if (comment.getCreatorType() == 0) {
					final User user = meetingPostService.getBussinessCardInfo(meetingId, comment.getCreator());
					comment.setUser(user);
					if (user != null) {
						comment.setCreatorName(user.getName());
						comment.setCreatorJob(getJob(user));
					}
				}
				comment.setContent(StringUtil.convertTextAreaNewLine(convertEmotions(comment.getContent())));
			}
			pager.setPageRecords(comments);
			final MeetingPost post = meetingPostService.findById(postId);
			post.setContent(StringUtil.convertTextAreaNewLine(convertEmotions(post.getContent())));
			if (post.getPosterType() == 0) {
				final User user = meetingPostService.getBussinessCardInfo(post.getMeetingId(), post.getPosterId());
				post.setUser(user);
				if (user != null) {
					post.setPosterName(user.getName());
					post.setPosterJob(getJob(user));
				}
			}
			getRequest().setAttribute("post", post);
			getRequest().setAttribute("pager", pager);
			getRequest().setAttribute("postId", postId);
			getRequest().setAttribute("meetingId", meetingId);
			getRequest().setAttribute("emotions", Constants.emotions);
			return SUCCESS;
		} catch (ServiceException e) {
			log.error("query comments error: ", e.getMessage());
			return ERROR;
		}
	}
	
	private String getJob(final User user) {
		if (user.getMeetingMember().getJobIsDisplay() == 1) {
			return StringUtils.defaultIfEmpty(user.getMeetingMember().getJob(), StringUtils.EMPTY);
		} else {
			return StringUtils.EMPTY;
		}		
	}

	private String convertEmotions(final String content) {
		String regEx = "\\[([0-9]*)\\]";
		Pattern p = Pattern.compile(regEx, Pattern.MULTILINE);
		Matcher m = p.matcher(content);
		return m.replaceAll("<img src='" + this.getBasePath() + "/images/emoji/emoji_" + "$1" + ".gif'/>");
	}

	public String commentList() {
		final String postNo = getParameter("postNo");
		final String meetingId = getParameter("meetingId");
		
		if (StringUtils.isNotEmpty(postNo) && StringUtils.isNotEmpty(meetingId)) {
			final List<MeetingComment> comments = meetingCommentService.queryListByPostId(Long.valueOf(postNo));
			response(0, SUCCESS, convertToView(comments, Long.valueOf(meetingId)));
		} else {
			response(100, "参数不合法。");
		}
		return SUCCESS;
	}

	private List<CommentView> convertToView(final List<MeetingComment> posts, final Long meetingId) {
		final List<CommentView> views = new ArrayList<CommentView>();
		for (int i = 0; i < posts.size(); i++) {
			MeetingComment comment = posts.get(i);
			final CommentView view = new CommentView();
			view.setId(comment.getId());
			view.setPostId(comment.getPostId());
			if (comment.getCreatorType() == 1) { // admin user
				view.setName(comment.getCreatorName());
				view.setJob(comment.getCreatorJob());
			} else { // meeting user
				final User user = meetingPostService.getBussinessCardInfo(meetingId, comment.getCreator());
				if (user != null) {
					view.setName(user.getName());
					view.setJob(getJob(user));
				}
			}
			view.setTime(DateUtil.getDateTime("yyyy年MM月dd日 HH:mm:ss", comment.getTime()));
			view.setContent(StringUtil.convertTextAreaNewLine(comment.getContent()));
			views.add(view);
		}
		return views;
	}

	private void response(final int code, final String msg) {
		resultMap.put("retcode", code);
		resultMap.put("retmsg", msg);
	}

	private void response(final int code, final String msg, final Object content) {
		resultMap.put("retcode", code);
		resultMap.put("retmsg", msg);
		resultMap.put("content", content);
	}
}
