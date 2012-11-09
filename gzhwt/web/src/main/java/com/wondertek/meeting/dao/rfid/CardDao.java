/**
 * 卡片处理Dao
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Feb 10, 2012
 */
package com.wondertek.meeting.dao.rfid;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.rfid.Card;

public interface CardDao extends BaseDao<Card, Integer> {
	/**
	 * 分页查看卡牌列表
	 * card: 查询条件
	 */
	public Pager<Card> findAllCardPager(Card card, int currentPage, int pageSize) throws Exception;
	
	/**
	 * 根据UID查询卡牌
	 */
	public Card findCardByUid(Long meetingId, String uid);
}
