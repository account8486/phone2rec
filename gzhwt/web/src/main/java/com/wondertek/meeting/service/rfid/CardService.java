/**
 * 卡片信息处理Service
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-10
 */
package com.wondertek.meeting.service.rfid;

import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.RfidSignIn;
import com.wondertek.meeting.model.rfid.Card;
import com.wondertek.meeting.model.rfid.IssueCard;
import com.wondertek.meeting.model.rfid.TriggerTask;
import com.wondertek.meeting.service.BaseService;

public interface CardService extends BaseService<Card, Integer> {
	/**
	 * 分页查看卡牌列表
	 * card: 查询条件
	 */
	public Pager<Card> findAllCardPager(Card card, int currentPage, int pageSize) throws Exception;
	
	/**
	 * 根据UID查询卡牌
	 */
	public Card findCardByUid(Long meetingId, String uid);
	
	/**
	 * 分页查看卡牌发放信息列表
	 * card: 查询条件
	 */
	public Pager<IssueCard> findAllIssueCardPager(IssueCard issueCard, int currentPage, int pageSize) throws Exception;
	
	/**
	 * 保存卡牌发放信息
	 */
	public void saveOrUpdateIssueCard(IssueCard issueCard) throws HibernateDaoSupportException;
	
	/**
	 * 删除卡牌发放信息
	 */
	public void deleteIssueCard(IssueCard issueCard) throws HibernateDaoSupportException;
	
	/**
	 * 根据UID查询卡牌发放信息
	 */
	public IssueCard findIssueCardByUid(Long meetingId, String uid);
	

	/**
	 * 根据用户ID查询卡牌发放信息
	 */
	public IssueCard findIssueCardByUserId(Long meetingId, Long userId);
	
	/**
	 * 根据ID查询卡牌发放信息
	 */
	public IssueCard findIssueCardById(Integer issueCardId) throws HibernateDaoSupportException;
	
	/**
	 * 根据会议ID查找触发任务信息
	 */
	public TriggerTask findTriggerTaskByMeetingId(Long meetingId);
	
	/**
	 * 查找缺省的触发任务信息
	 */
	public TriggerTask findDefaultTriggerTask() throws HibernateDaoSupportException ;
	
	/**
	 * 修改触发任务信息
	 */
	public void saveOrUpdateTriggerTask(TriggerTask task) throws HibernateDaoSupportException;
	
	/**
	 * 删除触发任务信息
	 */
	public void deleteTriggerTask(TriggerTask task) throws HibernateDaoSupportException;
	
	/**
	 * 执行RFID触发动作
	 */
	public void handleRfidTagTriggerTask(TriggerTask triggerTask, IssueCard issueCard, RfidSignIn rfidSignIn);
	
	/**
	 * 根据会议ID和用户ID查询对应的RFID签到信息
	 */
	public List<RfidSignIn> querySignIn(Long meetingId, Long userId);
	
	/**
	 * 查询对应的RFID签到分页信息
	 */
	public Pager<RfidSignIn> findAllRfidSignInPager(RfidSignIn rfidSignIn, int currentPage, int pageSize) throws Exception ;
	
	/**
	 * 查询对应的RFID签到统计信息
	 */
	public Pager findRfidSignInForStaticPager(RfidSignIn rfidSignIn, int currentPage, int pageSize) throws Exception ;
	
	/**
	 * 根据ID查询对应的RFID签到信息
	 */
	public RfidSignIn findRfidSignInById(Long id) throws Exception ;
	
	/**
	 * 保存RFID签到信息
	 */
	public void saveOrUpdateRfidSignInById(RfidSignIn rfidSignIn);
	
	/**
	 * 删除RFID签到信息
	 */
	public void deleteRfidSignIn(RfidSignIn rfidSignIn) throws Exception ;
	
	/**
	 * 获取信息
	 * @param meetingId
	 * @param eventId
	 * @param signType
	 * @param signState
	 * @return
	 */
	public List getUserBySignStatus(String meetingId, String eventId,
			String signType, String signState);
	
	
	/**
	 * 自动对参会人员发卡匹配
	 * @author zouxiaoming
	 * @param meetingId  会议ID
	 * @param beginUID	起始UID
	 * @return
	 */
	public Map<String,List<String>> autoIssue(Meeting meeting,String beginUID);
	
}
