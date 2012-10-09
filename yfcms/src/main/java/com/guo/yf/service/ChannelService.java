package com.guo.yf.service;

import java.util.List;

import com.guo.yf.model.channel.Channel;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.service.BaseService;

public interface ChannelService extends BaseService<Channel, Long> {
	public Pager<Channel> getChannelPager(int currentPage, int pageSize);
	
	/**
	 * 获取文章数
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public List getArticleNumByChannel() throws HibernateDaoSupportException;
}
