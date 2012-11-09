/**
 * 处理会议相关页面主题
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-30
 */
package com.wondertek.meeting.action.custom;

import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.custom.PageTheme;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.custom.PageThemeService;

public class PageThemeAction extends BaseAction {
	private static final long serialVersionUID = -1L;
	
	private MeetingService meetingService;
	private PageThemeService pageThemeService;
	private List<PageTheme> pageThemeList; //用来保存主题列表
	private Long meetingId; //当前的会议ID
	private Long themeId; //用来保存选择的主题ID
	private PageTheme currentTheme; //该会议已经选择的主题
	
	/**
	 *示所有的会议页面主题
	 */
	public String listPageTheme() throws Exception {
		log.info("listPageTheme: meetingId=" + meetingId);
		
		pageThemeList = this.pageThemeService.findAllUsablePageThemes();
		return "pageThemeList";
	}

	/**
	 *保存当前会议的页面主题
	 */
	public String savePageTheme() throws Exception {
		log.info("savePageTheme: meetingId=" + meetingId + ", themeId=" + themeId);
		
		Meeting meeting = this.meetingService.findById(meetingId);
		PageTheme theme = this.pageThemeService.findById(themeId);
		this.meetingService.saveOrUpdate(meeting);
		
		pageThemeList = this.pageThemeService.findAll();
		return this.listPageTheme();
	}
	
	public PageThemeService getPageThemeService() {
		return pageThemeService;
	}

	public void setPageThemeService(PageThemeService pageThemeService) {
		this.pageThemeService = pageThemeService;
	}

	public List<PageTheme> getPageThemeList() {
		return pageThemeList;
	}

	public void setPageThemeList(List<PageTheme> pageThemeList) {
		this.pageThemeList = pageThemeList;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	public PageTheme getCurrentTheme() {
		return currentTheme;
	}

	public void setCurrentTheme(PageTheme currentTheme) {
		this.currentTheme = currentTheme;
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}
