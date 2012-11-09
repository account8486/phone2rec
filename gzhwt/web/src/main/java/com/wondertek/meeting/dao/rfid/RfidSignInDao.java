/**
 * RFID签到记录DAO
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-4-6
 */
package com.wondertek.meeting.dao.rfid;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.RfidSignIn;

public interface RfidSignInDao extends BaseDao<RfidSignIn, Long> {
	/**
	 * 根据会议ID和用户ID查询对应Rfid签到记录
	 */
	public List<RfidSignIn> querySignIn(Long meetingId, Long userId);
	
	/**
	 * 查询某一个签到事件下的所有签到
	 * @author zouxiaoming
	 * @param id
	 * @return
	 */
	public Pager<RfidSignIn> findByEventId(Long id,int currentPage, int pageSize) throws Exception;
	
	/**
	 * 分页查看RFID签到信息
	 * card: 查询条件
	 */
	public Pager<RfidSignIn> findAllRfidSignInPager(RfidSignIn rfidSignIn, int currentPage, int pageSize) throws Exception ;
	
	/**
	 * 查询对应的RFID签到统计信息
	 */
	public Pager findRfidSignInForStaticPager(RfidSignIn rfidSignIn, int currentPage, int pageSize) throws Exception ;
	/**
	 * 通过签到状态获取用户信息
	 * ids
	 * @return
	 */
	public List getUserBySignStatus(String meetingId, String eventId,
			String signType, String signState);
	
}
