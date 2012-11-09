/**
 * 页面定制信息Service实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-29
 */
package com.wondertek.meeting.service.impl.custom;

import java.util.List;
import java.util.Map;

import com.wondertek.meeting.dao.custom.PageCustomDao;
import com.wondertek.meeting.model.custom.PageCustom;
import com.wondertek.meeting.service.custom.PageCustomService;
import com.wondertek.meeting.service.impl.BaseServiceImpl;


public class PageCustomServiceImpl extends BaseServiceImpl<PageCustom, Long>
		implements PageCustomService {
	private PageCustomDao pageCustomDao;

	public PageCustomDao getPageCustomDao() {
		return pageCustomDao;
	}

	public void setPageCustomDao(PageCustomDao pageCustomDao) {
		super.setBaseDao(pageCustomDao);
		this.pageCustomDao = pageCustomDao;
	}
	
	/**
	 * 获取指定会议的属性值
	 */
	public PageCustom getPageCustom(Long meetingId, String propertyName) {
		return this.pageCustomDao.getPageCustom(meetingId, propertyName);
	}
	
	/**
	 * 获取指定会议的页面所有配置属性，以List的形式返回
	 */
	public List<PageCustom> getPagePropertyList(Long meetingId) {
		return this.pageCustomDao.getPagePropertyList(meetingId);
	}
	
	/**
	 * 获取指定会议的所有配置属性，以Map的形式返回
	 */
	public Map<String, String> getPagePropertyMap(Long meetingId) {
		return this.pageCustomDao.getPagePropertyMap(meetingId);
	}
}
