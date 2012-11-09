package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.LuckyDrawDao;
import com.wondertek.meeting.dao.LuckyHistoryDao;
import com.wondertek.meeting.dao.LuckyUserDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.LuckyDraw;
import com.wondertek.meeting.model.LuckyUser;
import com.wondertek.meeting.model.MeetingPost;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.LuckyDrawService;

/**
* @ClassName: LuckyDrawServiceImpl 
* @Description: 抽奖管理
* @author zouxiaoming
* @date Mar 8, 2012 2:03:55 PM 
*
 */
public class LuckyDrawServiceImpl extends BaseServiceImpl<LuckyDraw, Long> implements LuckyDrawService {
	private LuckyDrawDao luckyDrawDao;
	private LuckyHistoryDao luckyHistoryDao;
	private LuckyUserDao luckyUserDao;

	@Override
	public Pager<LuckyDraw> findAllLucky(Long meetingId, int currentPage,
			int pageSize) throws ServiceException {
		String hql="from LuckyDraw gt where gt.meeting.id=:meetingId order by gt.id desc";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);
		return this.luckyDrawDao.findPager(hql, currentPage, pageSize, properties);
	}

	@Override
	public boolean deleteLucky(LuckyDraw lucky) {
		try {
			this.luckyDrawDao.delete(lucky);
			this.luckyHistoryDao.deleteLucky(lucky);  // 删除中奖历史信息
			this.luckyUserDao.deleteLuckyUser(lucky.getId());// 删除可中奖人信息
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean saveLucky(LuckyDraw lucky, Long[] users,List<MeetingPost> list) {
		try {
			this.luckyDrawDao.saveOrUpdateEntity(lucky);
			log.info("luckyId:"+lucky.getId());
			this.luckyUserDao.deleteLuckyUser(lucky.getId());
			if(users!=null&&users.length>0){
				for(Long userId:users){
					LuckyUser saveUsers;
					/*LuckyUser saveUsers=this.luckyUserDao.findLuckyUser(userId,lucky.getMeeting().getId());
					if(saveUsers!=null){
						continue;
					}*/
					saveUsers=new LuckyUser();
					saveUsers.setLucky(lucky);
					User user=new User();
					user.setId(userId);
					saveUsers.setUser(user);
					saveUsers.setMeeting(lucky.getMeeting());
					this.luckyUserDao.saveOrUpdateEntity(saveUsers);
				}
			}
			
			// 表示只能参与互动交流的人员可以抽奖
			if(list!=null&&list.size()>0){
				for(MeetingPost post:list){
					LuckyUser saveUsers;
					/*LuckyUser saveUsers=this.luckyUserDao.findLuckyUser(post.getPosterId(),lucky.getMeeting().getId());
					if(saveUsers!=null){
						continue;
					}*/
					saveUsers=new LuckyUser();
					saveUsers.setLucky(lucky);
					User user=new User();
					user.setId(post.getPosterId());
					saveUsers.setUser(user);
					saveUsers.setMeeting(lucky.getMeeting());
					this.luckyUserDao.saveOrUpdateEntity(saveUsers);
				}
			}
			
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	/**
	 * @param luckyDrawDao
	 */
	public void setLuckyDrawDao(LuckyDrawDao luckyDrawDao) {
		this.basicDao=luckyDrawDao;
		this.luckyDrawDao = luckyDrawDao;
	}

	/**
	 * @Description
	 * @return the luckyDrawDao
	 */
	public LuckyDrawDao getLuckyDrawDao() {
		return luckyDrawDao;
	}

	/**
	 * @param luckyHistoryDao
	 */
	public void setLuckyHistoryDao(LuckyHistoryDao luckyHistoryDao) {
		this.luckyHistoryDao = luckyHistoryDao;
	}

	/**
	 * @Description
	 * @return the luckyHistoryDao
	 */
	public LuckyHistoryDao getLuckyHistoryDao() {
		return luckyHistoryDao;
	}

	/**
	 * @param luckyUserDao
	 */
	public void setLuckyUserDao(LuckyUserDao luckyUserDao) {
		this.luckyUserDao = luckyUserDao;
	}

	/**
	 * @Description
	 * @return the luckyUserDao
	 */
	public LuckyUserDao getLuckyUserDao() {
		return luckyUserDao;
	}

	
	

}
