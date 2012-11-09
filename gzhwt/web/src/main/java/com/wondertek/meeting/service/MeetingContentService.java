package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingContent;


public interface MeetingContentService extends BaseService<MeetingContent, Long> {
	
	public List<MeetingContent> getMeetingContentList(
			MeetingContent meetingContent);
	
	/**更新自定义菜单的内容*/
	public Long modifyMeetingContent(MeetingContent content,String basePath,String itemIds) throws ServiceException;
	/**处理自定义菜单的内容*/
	public Long saveMeetingContent(MeetingContent content,String basePath,String itemIds) throws ServiceException;
	public void deleteContent(MeetingContent content) throws ServiceException;
	
	/**根据类型查询景点酒店信息*/
	public Pager<MeetingContent> findMeetingContentPager(Long meetingId,int type,int currentPage,int pageSize,String title) throws ServiceException;
	
	public List<MeetingContent> findContentItem(Long parentId) throws ServiceException;
	
	public List<MeetingContent> findContentItem(String itemIds) throws ServiceException;
}
