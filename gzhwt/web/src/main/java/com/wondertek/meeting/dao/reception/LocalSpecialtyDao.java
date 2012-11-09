/**
 * 处理全局范围内土特产信息Dao
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-17
 */
package com.wondertek.meeting.dao.reception;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.reception.LocalSpecialty;

public interface LocalSpecialtyDao extends BaseDao<LocalSpecialty, Integer> {

	/**
	 * 分页查看会议土特产中的特产明细列表
	 * localSpecialty: 查询条件:名称 or 省 or 市
	 */
	public Pager<LocalSpecialty> findAllLocalSpecialtyPager
		(Integer meetingSpecialtyId, LocalSpecialty localSpecialty, int currentPage, int pageSize) throws Exception ;
	
	/**
	 * 根据名字，区域查找特产信息
	 */
	public List<Object[]> searchLocalSpecialty(final LocalSpecialty example);
	
	/**
	 * 从指定的会议特产中查找指定ID的土特产项
	 */
	public LocalSpecialty findLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, Integer localSpecialtyId);
}
