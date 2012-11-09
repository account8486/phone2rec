/**
 * 页面定制信息Service
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-29
 */
package com.wondertek.meeting.service.custom;

import java.util.List;
import java.util.Map;

import com.wondertek.meeting.model.custom.PageCustom;
import com.wondertek.meeting.service.BaseService;

public interface PageCustomService extends BaseService<PageCustom, Long> {
	/**
	 * 根据属性名称获取指定会议的页面属性值
	 */
	public PageCustom getPageCustom(Long meetingId, String propertyName);
	
	/**
	 * 获取指定会议的页面所有配置属性，以List的形式返回
	 */
	public List<PageCustom> getPagePropertyList(Long meetingId);
	
	/**
	 * 获取指定会议的页面所有配置属性，以Map的形式返回
	 */
	public Map<String, String> getPagePropertyMap(Long meetingId);
	
	
}
