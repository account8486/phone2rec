/**
 * 页面主题信息Service
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-29
 */
package com.wondertek.meeting.service.custom;

import java.util.List;

import com.wondertek.meeting.model.custom.PageTheme;
import com.wondertek.meeting.service.BaseService;

public interface PageThemeService extends BaseService<PageTheme, Long> {
	/**
	 * 查询所有可用的页面主题
	 * 可用的条件：state==1
	 */
	public List<PageTheme> findAllUsablePageThemes();
}
