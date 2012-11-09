/**
 * 页面主题信息DAO实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-29
 */
package com.wondertek.meeting.dao.impl.custom;

import java.util.List;

import com.wondertek.meeting.dao.custom.PageThemeDao;
import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.model.custom.PageTheme;

public class PageThemeDaoImpl extends BaseDaoImpl<PageTheme, Long> implements PageThemeDao {
	/**
	 * 查询所有可用的页面主题
	 * 可用的条件：state==1
	 */
	@SuppressWarnings("unchecked")
	public List<PageTheme> findAllUsablePageThemes() {
		String hql = "from PageTheme pt where pt.state=1 order by pt.id";
		return this.getHibernateTemplate().find(hql);
	}
}
