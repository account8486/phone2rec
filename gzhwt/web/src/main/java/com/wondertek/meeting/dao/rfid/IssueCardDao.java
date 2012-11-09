/**
 * 发卡处理DAO
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Feb 10, 2012
 */
package com.wondertek.meeting.dao.rfid;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.rfid.IssueCard;

public interface IssueCardDao extends BaseDao<IssueCard, Integer> {
	/**
	 * 分页查看卡牌发放信息列表
	 * card: 查询条件
	 */
	public Pager<IssueCard> findAllIssueCardPager(IssueCard issueCard, int currentPage, int pageSize) throws Exception;

	/**
	 * 根据UID查询卡牌发放信息
	 */
	public IssueCard findIssueCardByUid(Long meetingId, String uid);
	
	/**
	 * 根据用户ID查询卡牌发放信息
	 */
	public IssueCard findIssueCardByUserId(Long meetingId, Long userId);
}
