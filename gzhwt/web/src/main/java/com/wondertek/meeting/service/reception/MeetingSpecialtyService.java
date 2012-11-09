/**
 * 处理会议特产信息的Service
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-17
 */
package com.wondertek.meeting.service.reception;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.reception.LocalSpecialty;
import com.wondertek.meeting.model.reception.MeetingSpecialty;
import com.wondertek.meeting.service.BaseService;

public interface MeetingSpecialtyService extends
		BaseService<MeetingSpecialty, Integer> {

	/**
	 * 查找指定会议下的土特产信息
	 */
	public MeetingSpecialty findByMeetingId(Long meetingId);
	
	/**
	 * 分页查看会议土特产中的特产明细列表
	 * localSpecialty: 查询条件:名称 or 省 or 市
	 */
	public Pager<LocalSpecialty> findAllLocalSpecialtyPager(
			Integer meetingSpecialtyId, LocalSpecialty localSpecialty, 
			int currentPage, int pageSize) throws Exception ;
	
	/**
	 * 根据名字，区域查找特产信息
	 */
	public List<Object[]> searchLocalSpecialty(final LocalSpecialty example);
	
	/**
	 * 根据ID查找LocalSpecialty
	 */
	public LocalSpecialty findLocalSpecialtyById(Integer id) throws Exception;
	
	/**
	 * 保存LocalSpecialty
	 */
	public void saveOrUpdateLocalSpecialty(LocalSpecialty specialty);
	
	/**
	 * 从会议特产信息中移除指定ID的特定项
	 */
	public void removeLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, Integer localSpecialtyId);
	
	/**
	 * 从会议特产信息中移除所有的特定项
	 */
	public void removeAllLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId);
	
	/**
	 * 从会议特产信息中添加指定ID的特定项
	 */
	public void addLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, LocalSpecialty localSpecialty);
	
	/**
	 * 从指定的会议特产中查找指定ID的土特产项
	 */
	public LocalSpecialty findLocalSpecialtyFromMeetingSpecialty(Integer meetingSpecialtyId, Integer localSpecialtyId);
}
