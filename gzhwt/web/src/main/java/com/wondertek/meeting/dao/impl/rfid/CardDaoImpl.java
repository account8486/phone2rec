/**
 * 卡片处理Dao实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Feb 10, 2012
 */
package com.wondertek.meeting.dao.impl.rfid;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.dao.rfid.CardDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.GroupPlan;
import com.wondertek.meeting.model.rfid.Card;

public class CardDaoImpl extends BaseDaoImpl<Card, Integer> implements CardDao {

	/**
	 * 分页查看卡牌列表
	 * card: 查询条件：会议ID+UID
	 */
	public Pager<Card> findAllCardPager(Card card, int currentPage, int pageSize) throws Exception {
		String hql = "from Card c where c.meeting.id=:meetingId";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("meetingId", card.getMeeting().getId());
		
		if(card.getUid() != null) {
			hql += " and c.uid like :uid";
			param.put("uid", "%" + card.getUid() + "%");
		}
		
		hql += " order by c.id desc";
		
		return this.findPager(hql, currentPage, pageSize, param);
	}

	/**
	 * 根据UID查询卡牌
	 */
	@SuppressWarnings("unchecked")
	public Card findCardByUid(Long meetingId, String uid) {
		String hql = "from Card c where c.meeting.id=? and c.uid=?";
		List<Card> list = this.getHibernateTemplate().find(hql, meetingId, uid);
		return list.size() > 0 ? list.get(0) : null;
	}
}
