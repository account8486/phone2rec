/**
 * 
 */
package com.wondertek.meeting.action.meeting;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.appfuse.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.client.view.CommentView;
import com.wondertek.meeting.client.view.PostView;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.MeetingComment;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.MeetingPost;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MeetingCommentService;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.MeetingPostService;
import com.wondertek.meeting.util.StringUtil;

/**
 * @author rain
 * 
 */
public class MeetingPostAction extends BaseAction {
	@Autowired
	private MeetingPostService meetingPostService;
	@Autowired
	private MeetingCommentService meetingCommentService;
	@Autowired
	private MeetingMemberService meetingMemberService;

	private static final long serialVersionUID = 6848432145941577792L;
	private static final int BUFFER_SIZE = 16 * 1024;
	private static final int COMMENT_COUNT_PER_POST = 3;
	private String savePath;

	public String checkNewPost() {
		try {
			final int count = meetingPostService.checkNewPost(Long.valueOf(getParameter("meetingNo")));
			resultMap.put("retcode", "0");
			resultMap.put("count", count);
		} catch (Exception e) {
			response(1, e.getMessage());
		}
		return SUCCESS;
	}

	public String postAdd() {
		return SUCCESS;
	}
	
	public String postCreate() {
		final String meetingId = getParameter("meetingId");
		final String content = getParameter("content");
		final String type = getParameter("type");
		final String doUploadImage = getParameter("doUploadImage");
		final String doUploadVideo = getParameter("doUploadVideo");
		
		final MeetingPost entity = new MeetingPost();
		entity.setMeetingId(Long.valueOf(meetingId));
		entity.setContent(content);
		entity.setCreateTime(new Date());
		entity.setModifyTime(new Date());
		entity.setViewCount(0);
		entity.setCommentCount(0);
		entity.setVideourl((Boolean.valueOf(doUploadVideo)) ? StringUtils.defaultIfEmpty(fileUploadHandler("video"), "") : "");
		entity.setContentImg((Boolean.valueOf(doUploadImage)) ? StringUtils.defaultIfEmpty(fileUploadHandler("upload"), "") : "");
		
		if (StringUtils.equalsIgnoreCase("admin", type)) {
			final AdminUser admin = (AdminUser) this.getFromSession(SessionKeeper.SESSION_ADMIN_USER);
			entity.setPosterId(admin.getId());
			entity.setPosterJob(admin.getJob());
			entity.setPosterName(admin.getName());
			entity.setPosterType(1);
		} else {
			final User user = (User) this.getFromSession(SessionKeeper.SESSION_USER);
			entity.setPosterId(user.getId());
			entity.setPosterName(user.getName());
			entity.setPosterType(0);
		}		

		try {
			meetingPostService.add(entity);
			return postList_admin();
		} catch (ServiceException e) {
			return ERROR;
		}		
	}

	public String postCreate4Client() {
		final String meetingNo = getParameter("meetingNo");
		final String content = getParameter("content");
		final String imgUrl = getParameter("imgUrl");
		final String type = getParameter("type");

		if (!StringUtils.isNumeric(meetingNo)) {
			response(100, "无效的参数: meetingNo");
		}

		final MeetingPost entity = new MeetingPost();
		entity.setMeetingId(Long.valueOf(meetingNo));
		entity.setContent(content);
		entity.setCreateTime(new Date());
		entity.setModifyTime(new Date());
		entity.setViewCount(0);
		entity.setCommentCount(0);
		entity.setContentImg(StringUtils.defaultIfEmpty(imgUrl, ""));
		if (StringUtils.equalsIgnoreCase("admin", type)) {
			final AdminUser admin = (AdminUser) this.getFromSession(SessionKeeper.SESSION_ADMIN_USER);
			entity.setPosterId(admin.getId());
			entity.setPosterJob(admin.getJob());
			entity.setPosterName(admin.getName());
			entity.setPosterType(1);
		} else {
			final User user = (User) this.getFromSession(SessionKeeper.SESSION_USER);
			entity.setPosterId(user.getId());
			entity.setPosterName(user.getName());
			entity.setPosterType(0);
		}

		try {
			final Long postId = meetingPostService.add(entity);
			response(0, SUCCESS, postId);
		} catch (ServiceException e) {
			response(1, e.getMessage());
		}

		return SUCCESS;
	}

	public String postDelete() {
		final String id = getParameter("id");
		try {
			final MeetingPost post = new MeetingPost();
			post.setId(Long.valueOf(id));
			meetingPostService.delete(post);
			resultMap.put("result", true);
		} catch (Exception e) {
			resultMap.put("result", false);
		}
		return SUCCESS;
	}

	/**
	 * for client
	 * 
	 * @return
	 */
	public String postList() {
		final String meetingNo = getParameter("meetingNo");
		final String rowCount = getParameter("count");
		List<MeetingPost> posts = new ArrayList<MeetingPost>();

		try {
			if (StringUtils.isNumeric(rowCount)) {
				final MeetingPost meetingPost = new MeetingPost();
				meetingPost.setMeetingId(Long.valueOf(meetingNo));
				Pager<MeetingPost> pager = meetingPostService.queryPagerByMeetingId(meetingPost, 1,
						Integer.valueOf(rowCount));
				posts = pager.getPageRecords();
			} else {
				posts = meetingPostService.queryListByMeetingId(Long.valueOf(meetingNo));
			}
			response(0, SUCCESS, convertToView(posts));
		} catch (Exception e) {
			response(100, e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * for admin and portal
	 * 
	 * @return
	 */
	public String postList_admin() {
		final String meetingNo = getParameter("meetingId");
		String fromStep=getRequest().getParameter("fromStep");
		getRequest().setAttribute("meetingId", meetingNo);
		try {
			final MeetingPost meetingPost = new MeetingPost();
			meetingPost.setMeetingId(Long.valueOf(meetingNo));
			final Pager<MeetingPost> pager = meetingPostService.queryPagerByMeetingId(meetingPost, currentPage,
					pageSize);
			List<MeetingPost> posts = pager.getPageRecords();
			for (MeetingPost post : posts) {
				post.setContent(StringUtil.convertTextAreaNewLine(convertEmotions(post.getContent())));
				if (post.getPosterType() == 0) {
					final User user = meetingPostService.getBussinessCardInfo(post.getMeetingId(), post.getPosterId());
					post.setUser(user);
					if (user != null) {
						post.setPosterName(user.getName());
						post.setPosterJob(getJob(user));
					}
				}
				if (post.getCommentCount() > 0) {
					for (Iterator<MeetingComment> it = post.getComments().iterator(); it.hasNext();) {
						MeetingComment comment = it.next();
						comment.setContent(StringUtil.convertTextAreaNewLine(convertEmotions(comment.getContent())));
						if (comment.getCreatorType() == 0) {
							final User user = meetingPostService.getBussinessCardInfo(post.getMeetingId(), comment.getCreator());
							comment.setUser(user);
							if (user != null) {
								comment.setCreatorName(user.getName());
								comment.setCreatorJob(getJob(user));
							}
						}
					}
				}
			}
			pager.setPageRecords(posts);
			getRequest().setAttribute("pager", pager);
			getRequest().setAttribute("emotions", Constants.emotions);
			getRequest().setAttribute("meetingId", meetingNo);
			if("step7".equals(fromStep)){
				return "step7";
			}
			
			// add by zouxiaoming 2012/3/9
			if(!StringUtil.isEmpty(errMsg)&&errMsg.equals("fullScreen")){
				return "fullScreen";
			}
			return SUCCESS;
		} catch (ServiceException e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * add by zouxiaoming 2012/3/9
	 * @return
	 */
	public String postListFullScreen(){
		List<MeetingPost> list=this.meetingPostService.findByModifyTime(getParameter("postId"), getParameter("meetingId"));
		for(MeetingPost post:list){
			System.out.println(post.getUser());
		}
		ActionContext.getContext().put("list",list);
		return SUCCESS;
	}
	
	

	/**
	 * for wap
	 * 
	 * @return
	 */
	public String wapView() {
		final String meetingNo = getParameter("meetingId");
		final String pageNo = getParameter("currentPage");
		if (StringUtils.isNumeric(pageNo)) {
			currentPage = Integer.valueOf(pageNo);
		}
		getRequest().setAttribute("meetingId", meetingNo);
		try {
			final MeetingPost meetingPost = new MeetingPost();
			meetingPost.setMeetingId(Long.valueOf(meetingNo));
			final Pager<MeetingPost> pager = meetingPostService.queryPagerByMeetingId(meetingPost, currentPage,
					pageSize);
			List<MeetingPost> posts = pager.getPageRecords();
			for (MeetingPost post : posts) {
				post.setContent(StringUtil.convertTextAreaNewLine(convertEmotions(post.getContent())));
				if (post.getPosterType() == 0) {
					final User user = meetingPostService.getBussinessCardInfo(post.getMeetingId(), post.getPosterId());
					post.setUser(user);
					if (user != null) {
						post.setPosterName(user.getName());
						post.setPosterJob(getJob(user));
					}
				}
			}
			pager.setPageRecords(posts);
			getRequest().setAttribute("pager", pager);
			return SUCCESS;
		} catch (ServiceException e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	
	/**
	 * for wap
	 * 
	 * @return
	 */
	public String touchView() {
		final String meetingNo = getParameter("meetingId");
		final String pageNo = getParameter("currentPage");
		if (StringUtils.isNumeric(pageNo)) {
			currentPage = Integer.valueOf(pageNo);
		}
		getRequest().setAttribute("meetingId", meetingNo);
		try {
			final MeetingPost meetingPost = new MeetingPost();
			meetingPost.setMeetingId(Long.valueOf(meetingNo));
			final Pager<MeetingPost> pager = meetingPostService.queryPagerByMeetingId(meetingPost, currentPage,
					pageSize);
			List<MeetingPost> posts = pager.getPageRecords();
			for (MeetingPost post : posts) {
				post.setContent(StringUtil.convertTextAreaNewLine(convertEmotions(post.getContent())));
				if (post.getPosterType() == 0) {
					final User user = meetingPostService.getBussinessCardInfo(post.getMeetingId(), post.getPosterId());
					post.setUser(user);
					if (user != null) {
						post.setPosterName(user.getName());
						post.setPosterJob(getJob(user));
					}
				}
			}
			pager.setPageRecords(posts);
			getRequest().setAttribute("pager", pager);
			return SUCCESS;
		} catch (ServiceException e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	

	public String wapDetail() {
		try {
			final Long postId = Long.valueOf(getParameter("postId"));
			final String pageNo = getParameter("currentPage");
			if (StringUtils.isNumeric(pageNo)) {
				currentPage = Integer.valueOf(pageNo);
			}
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
			
			final Pager<MeetingComment> pager = meetingCommentService.queryPagerByPostId(postId, currentPage, pageSize);
			meetingPostService.addViewCount(postId);
			List<MeetingComment> comments = pager.getPageRecords();
			for (MeetingComment comment : comments) {
				comment.setContent(StringUtil.convertTextAreaNewLine(convertEmotions(comment.getContent())));
				if (comment.getCreatorType() == 0) {
					final User user = meetingPostService.getBussinessCardInfo(post.getMeetingId(), comment.getCreator());
					comment.setUser(user);
					if (user != null) {
						comment.setCreatorName(user.getName());
						comment.setCreatorJob(getJob(user));
					}
				}
			}
			pager.setPageRecords(comments);
			getRequest().setAttribute("pager", pager);
			getRequest().setAttribute("meetingId", getParameter("meetingId"));
			return SUCCESS;
		} catch (ServiceException e) {
			log.error("query comments error: ", e.getMessage());
			return ERROR;
		}
	}

	public String wapAdd() {
		final String meetingId = getParameter("meetingId");
		final String content = getParameter("content");
		final String imgUrl = getParameter("imgUrl");

		StringBuffer retUrl = new StringBuffer();
		if (StringUtils.isEmpty(content)) {
			retUrl.append("/wap/pri/interaction/postView.action?meetingId=").append(meetingId).append("&menu_id=")
					.append(getParameter("menu_id"));
			getRequest().setAttribute("returnUrl", retUrl.toString());
			getRequest().setAttribute("title", "互动交流");
			getRequest().setAttribute("message", "请输入发言内容。");
			return "prompt";
		}

		if (StringUtils.length(content) > 140) {
			retUrl.append("/wap/pri/interaction/postView.action?meetingId=").append(meetingId).append("&menu_id=")
					.append(getParameter("menu_id"));
			getRequest().setAttribute("returnUrl", retUrl.toString());
			getRequest().setAttribute("title", "互动交流");
			getRequest().setAttribute("message", "发言内容太长，超过140个字符。");
			return "prompt";
		}

		try {
			final MeetingPost entity = new MeetingPost();
			entity.setMeetingId(Long.valueOf(meetingId));
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setModifyTime(new Date());
			entity.setViewCount(0);
			entity.setCommentCount(0);
			entity.setContentImg(StringUtils.defaultIfEmpty(imgUrl, ""));
			final User user = (User) this.getFromSession(SessionKeeper.SESSION_USER);
			final MeetingMember member = meetingMemberService.selectById(user.getId(), Long.valueOf(meetingId));
			entity.setPosterId(user.getId());
			entity.setPosterJob(member.getJob());
			entity.setPosterName(user.getName());
			entity.setPosterType(0);
			meetingPostService.add(entity);

			final Pager<MeetingPost> pager = meetingPostService.queryPagerByMeetingId(entity, currentPage, pageSize);
			List<MeetingPost> posts = pager.getPageRecords();
			for (MeetingPost post : posts) {
				post.setContent(StringUtil.convertTextAreaNewLine(convertEmotions(post.getContent())));
				if (post.getPosterType() == 0) {
					final User userInfo = meetingPostService.getBussinessCardInfo(post.getMeetingId(), post.getPosterId());
					post.setUser(userInfo);
					if (userInfo != null) {
						post.setPosterName(userInfo.getName());
						post.setPosterJob(getJob(userInfo));
					}
				}
			}
			pager.setPageRecords(posts);
			getRequest().setAttribute("pager", pager);
			getRequest().setAttribute("meetingId", meetingId);
			return SUCCESS;
		} catch (ServiceException e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public String wapAddComment() {
		final String content = getParameter("content");
		final String postId = getParameter("postId");

		StringBuffer retUrl = new StringBuffer();
		if (StringUtils.isEmpty(content)) {
			retUrl.append("/wap/pri/interaction/postDetail.action?postId=").append(postId).append("&menu_id=")
					.append(getParameter("menu_id"));
			getRequest().setAttribute("returnUrl", retUrl.toString());
			getRequest().setAttribute("title", "互动交流");
			getRequest().setAttribute("message", "请输入评论内容。");
			return "prompt";
		}

		if (StringUtils.length(content) > 140) {
			retUrl.append("/wap/pri/interaction/postDetail.action?postId=").append(postId).append("&menu_id=")
					.append(getParameter("menu_id"));
			getRequest().setAttribute("returnUrl", retUrl.toString());
			getRequest().setAttribute("title", "互动交流");
			getRequest().setAttribute("message", "评论内容太长，超过140个字符。");
			return "prompt";
		}

		try {
			final MeetingComment comment = new MeetingComment();
			comment.setContent(content);
			comment.setTime(new Date());
			comment.setPostId(Long.valueOf(postId));
			final User user = (User) this.getFromSession(SessionKeeper.SESSION_USER);
			final String meetingId = getParameter("meetingId");
			final MeetingMember member = meetingMemberService.selectById(user.getId(), Long.valueOf(meetingId));
			comment.setCreator(user.getId());
			comment.setCreatorName(user.getName());
			comment.setCreatorJob(member.getJob());
			comment.setCreatorType(0);
			meetingCommentService.add(comment);
			meetingPostService.addCommentCount(Long.valueOf(postId));
			return wapDetail();
		} catch (ServiceException e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public String convertEmotions(final String content) {
		String regEx = "\\[([0-9]*)\\]";
		Pattern p = Pattern.compile(regEx, Pattern.MULTILINE);
		Matcher m = p.matcher(content);
		return m.replaceAll("<img src='" + this.getBasePath() + "/images/emoji/emoji_" + "$1" + ".gif'/>");
	}

	public String postViewCounter() {
		final String postNo = getParameter("postNo");
		meetingPostService.addViewCount(Long.valueOf(postNo));
		response(0, SUCCESS, postNo);
		return SUCCESS;
	}
	
	private String fileUploadHandler(final String name) {
		StringBuffer url = new StringBuffer();
		try {
			final MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) getRequest();
			final String postfix = getFilePostFix(multiWrapper.getFileNames(name)); 
			final String fileName = getFileName(postfix);
			final Enumeration<String> fileParameterNames = multiWrapper.getFileParameterNames();
			File file = null;
			while (fileParameterNames != null && fileParameterNames.hasMoreElements()) {
				// get the value of this input tag
				final String inputName = fileParameterNames.nextElement();
				if (StringUtils.endsWithIgnoreCase(name, inputName)) {
					final File[] files = multiWrapper.getFiles(inputName);
					file = files[0];
				}
			}
			if (file != null) {
				copy(file, getDestFile(fileName));
				url.append(getServerUrl()).append(getSavePath()).append(fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url.toString();
	}
	
	private String getFilePostFix(final String[] names) {
		if (names == null || names.length == 0) {
			return "png";
		}
		final String fileName = names[0];
		if (StringUtils.isEmpty(fileName)) {
			return "png";
		}
		String[] fileNameInfo = fileName.split("[.]");
		if (fileNameInfo != null && fileNameInfo.length > 1) {
			return fileNameInfo[fileNameInfo.length - 1];
		} else {
			return "png";
		}
	}

	public String postImgUpload() {
		resultMap.put("retcode", "0");
		resultMap.put("retMsg", "上传成功");
		resultMap.put("imgUrl", fileUploadHandler("file"));
		return SUCCESS;
	}
	
	public String downloadVideo() {
		final String link = getParameter("link");
		final String fileName = StringUtils.substringAfterLast(link, "/");
		getRequest().setAttribute("fileName", fileName);
		getRequest().setAttribute("phicalPath", getDocumentRoot()+ getSavePath() + fileName);
		return SUCCESS;
	}
	
	private String getFileName(final String filePostfix) {
		final Date dt = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		final String name = sdf.format(dt);
		final StringBuffer filename = new StringBuffer();
		filename.append(name).append(".").append(filePostfix);
		return filename.toString();
	}

	private File getDestFile(final String fileName) throws Exception {
		final String destPath = getDocumentRoot() + this.getSavePath();
		final File path = new File(destPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		final File destFile = new File(destPath, fileName);
		return destFile;
	}

	private void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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

	private List<PostView> convertToView(final List<MeetingPost> posts) {
		final List<PostView> views = new ArrayList<PostView>();
		for (int i = 0; i < posts.size(); i++) {
			MeetingPost post = posts.get(i);
			final PostView view = new PostView();
			view.setId(post.getId());
			view.setMeetingId(post.getMeetingId());
			if (post.getPosterType() == 1) { // Admin user
				view.setName(post.getPosterName());
				view.setJob(post.getPosterJob());
			} else { // meeting user
				final User user = meetingPostService.getBussinessCardInfo(post.getMeetingId(), post.getPosterId());
				if (user != null) {
					view.setName(user.getName());
					view.setJob(getJob(user));
				}
			}
			view.setCommentCount(post.getCommentCount());
			view.setViewCount(post.getViewCount());
			view.setContent(StringUtil.convertTextAreaNewLine(post.getContent()));
			view.setContentImg(post.getContentImg());
			view.setVideourl(post.getVideourl());
			view.setTime(DateUtil.getDateTime("yyyy年MM月dd日 HH:mm:ss", post.getCreateTime()));
			if (post.getCommentCount() > 0) {
				final List<CommentView> comments = new ArrayList<CommentView>();
				for (final Iterator<MeetingComment> it = post.getComments().iterator(); it.hasNext();) {
					final MeetingComment comment = it.next();
					comments.add(convertCommentToView(comment, post.getMeetingId()));
					if (comments.size() == COMMENT_COUNT_PER_POST) {
						break;
					}
				}
				view.setComments(comments);
			}			
			views.add(view);
		}
		return views;
	}
	
	private CommentView convertCommentToView(final MeetingComment comment, final Long meetingId) { 
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
		return view;
	}
	
	public String getJob(final User user) {
		if (user.getMeetingMember().getJobIsDisplay() == 1) {
			return StringUtils.defaultIfEmpty(user.getMeetingMember().getJob(), StringUtils.EMPTY);
		} else {
			return StringUtils.EMPTY;
		}		
	}

	/**
	 * @return the savePath
	 */
	public String getSavePath() {
		return savePath;
	}

	/**
	 * @param savePath
	 *            the savePath to set
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
}