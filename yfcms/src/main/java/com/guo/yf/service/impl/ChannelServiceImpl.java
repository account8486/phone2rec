package com.guo.yf.service.impl;

import com.guo.yf.dao.ChannelDao;
import com.guo.yf.model.channel.Channel;
import com.guo.yf.service.ChannelService;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.service.impl.BaseServiceImpl;

public class ChannelServiceImpl extends BaseServiceImpl<Channel, Long> implements
 ChannelService {
	ChannelDao channelDao;

	public ChannelDao getChannelDao() {
		return channelDao;
	}

	public void setChannelDao(ChannelDao channelDao) {
		this.basicDao = channelDao;
		this.channelDao = channelDao;
	}

	/**
	 * 获取栏目列表
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<Channel> getChannelPager(int currentPage, int pageSize) {
		Pager<Channel> pagerChannel = null;
		StringBuilder sb = new StringBuilder();
		sb.append("  select chan from Channel chan ");
		
		try {
			pagerChannel = channelDao.findPager(sb.toString(), currentPage,
					pageSize, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return pagerChannel;
	}

}
