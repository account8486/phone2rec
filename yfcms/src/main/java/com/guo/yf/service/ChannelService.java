package com.guo.yf.service;

import com.guo.yf.model.channel.Channel;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.service.BaseService;

public interface ChannelService extends BaseService<Channel, Long> {
	public Pager<Channel> getChannelPager(int currentPage, int pageSize);
}
