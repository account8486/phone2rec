/**
 * 卡片发放Dao实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Feb 10, 2012
 */
package com.wondertek.meeting.dao.impl.rfid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.dao.rfid.IssueCardDao;
import com.wondertek.meeting.model.rfid.Card;
import com.wondertek.meeting.model.rfid.IssueCard;

public class IssueCardDaoImpl extends BaseDaoImpl<IssueCard, Integer> implements
		IssueCardDao {
	/**
	 * 分页查看卡牌发放信息列表
	 * issueCard: 查询条件:会议ID+（用卡人手机号/卡牌UID）
	 */
	public Pager<IssueCard> findAllIssueCardPager(IssueCard issueCard, int currentPage, int pageSize) throws Exception {
		String hql = "from IssueCard c where c.meeting.id=:meetingId";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("meetingId", issueCard.getMeeting().getId());
		
		if(issueCard.getOwner() != null && issueCard.getOwner().getMobile() != null) {
			hql += " and c.owner.mobile like :mobile";
			param.put("mobile", "%" + issueCard.getOwner().getMobile() + "%");
		}
		
		if(issueCard.getCard() != null && issueCard.getCard().getUid() != null) {
			hql += " and c.card.uid like :uid";
			param.put("uid", "%" + issueCard.getCard().getUid() + "%");
		}
		
		hql += " order by c.id desc";
		return this.findPager(hql, currentPage, pageSize, param);
	}
	
	/**
	 * 根据UID查询卡牌发放信息
	 */
	@SuppressWarnings("unchecked")
	public IssueCard findIssueCardByUid(Long meetingId, String uid) {
		String hql = "from IssueCard c where c.meeting.id=? and c.card.uid=?";
		List<IssueCard> list = this.getHibernateTemplate().find(hql, meetingId, uid);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 根据用户ID查询卡牌发放信息
	 */
	@SuppressWarnings("unchecked")
	public IssueCard findIssueCardByUserId(Long meetingId, Long userId) {
		String hql = "from IssueCard c where c.meeting.id=? and c.owner.id=?";
		List<IssueCard> list = this.getHibernateTemplate().find(hql, meetingId, userId);
		return list.size() > 0 ? list.get(0) : null;
	}
}
