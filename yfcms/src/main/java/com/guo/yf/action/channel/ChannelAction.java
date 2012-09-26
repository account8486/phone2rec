package com.guo.yf.action.channel;

import java.util.Date;

import com.guo.yf.model.channel.Channel;
import com.guo.yf.service.ChannelService;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.util.StringUtil;

public class ChannelAction extends BaseAction {

	private static final long serialVersionUID = 5558659082553453127L;

	ChannelService channelService;
	Channel channel;

	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}



	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String channelList() {

		this.setAttribute("pager",
				channelService.getChannelPager(currentPage, pageSize));

		return SUCCESS;
	}

	/**
	 * 删除当前用户
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String delChannel() throws NumberFormatException, ServiceException {
		String id = this.getParameter("id");
		channelService.delete(channelService.findById(Long.valueOf(id)));
		return this.channelList();
	}

	/**
	 * 进行栏目信息的查找与显示
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String toEditChannel() throws NumberFormatException,
			ServiceException {
		String id = this.getParameter("id");
		this.setAttribute("channel", channelService.findById(Long.valueOf(id)));

		return SUCCESS;
	}

	/**
	 * 进行栏目的添加
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String toAddChannel() throws NumberFormatException, ServiceException {

		return SUCCESS;
	}

	/**
	 * update channel
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String updateChannel() throws ServiceException {
		if (channel != null) {
			// 如果无主键ID
			if (channel.getId() == null
					|| StringUtil.isEmpty(String.valueOf(channel.getId()))) {
				channel.setCreateTime(new Date());
				channel.setCreator(this.getAdminUserIdFromSession());
				channelService.saveOrUpdate(channel);
			} else {
				Channel oldChan = channelService.findById(channel.getId());
				oldChan.setChanName(this.channel.getChanName());
				oldChan.setChanDescription(this.channel.getChanDescription());
				oldChan.setModifyTime(new Date());
				oldChan.setModifier(this.getAdminUserIdFromSession());
				channelService.saveOrUpdate(oldChan);
			}
		}

		this.resultMap.put("retMsg", "保存成功！");

		return SUCCESS;
	}

}
