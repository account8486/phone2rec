/**
 * 页面定制信息DAO实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-29
 */
package com.wondertek.meeting.dao.impl.custom;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.wondertek.meeting.dao.custom.PageCustomDao;
import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.model.custom.PageCustom;

public class PageCustomDaoImpl extends BaseDaoImpl<PageCustom, Long> implements PageCustomDao {
	public PageCustomDaoImpl() {
		super.setCacheQueries(true);
	}
	
	/**
	 * 获取指定的页面属性值
	 */
	@SuppressWarnings("unchecked")
	public PageCustom getPageCustom(Long meetingId, String propertyName) {
		String hql = "from PageCustom pc where pc.meeting.id=? and pc.propertyName=?";
		List<PageCustom> list = this.getHibernateTemplate().find(hql, meetingId, propertyName);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 获取站点的页面所有配置属性，以List的形式返回
	 */
	@SuppressWarnings("unchecked")
	public List<PageCustom> getPagePropertyList(Long meetingId) {
		String hql = "from PageCustom pc where pc.meeting.id=? order by pc.id";
		return this.getHibernateTemplate().find(hql, meetingId);
	}
	
	/**
	 * 获取指定的页面所有配置属性，以Map的形式返回
	 */
	public Map<String, String> getPagePropertyMap(Long meetingId) {
		List<PageCustom> list = this.getPagePropertyList(meetingId);
		Map<String, String> map = new TreeMap<String, String>();
		for(PageCustom custom : list) {
			map.put(custom.getPropertyName(), custom.getPropertyValue());
		}
		return map;
	}
}
