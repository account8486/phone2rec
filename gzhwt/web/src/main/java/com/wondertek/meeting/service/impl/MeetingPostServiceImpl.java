/**
 * 
 */
package com.wondertek.meeting.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingPostDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.MeetingPost;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MeetingPostService;

/**
 * @author rain
 * 
 */
public class MeetingPostServiceImpl extends BaseServiceImpl<MeetingPost, Long> implements MeetingPostService {

	private MeetingPostDao meetingPostDao;

	/**
	 * @param meetingPostDao
	 *            the meetingPostDao to set
	 */
	public void setMeetingPostDao(final MeetingPostDao meetingPostDao) {
		super.setBaseDao(meetingPostDao);
		this.meetingPostDao = meetingPostDao;
	}

	public List<MeetingPost> queryListByMeetingId(final Long meetingId) {
		return meetingPostDao.queryListByMeetingId(meetingId);
	}

	public Pager<MeetingPost> queryPagerByMeetingId(final MeetingPost meetingPost, final int currentPage,
			final int pageSize) throws ServiceException {
		Map<String, Object> properties = new HashMap<String, Object>();
		final String sql = "from MeetingPost where meetingId=" + String.valueOf(meetingPost.getMeetingId())
				+ " order by modifyTime desc";
		return meetingPostDao.findPager(sql, currentPage, pageSize, properties);
	}
	
	
	@Override
	public List<MeetingPost> findByModifyTime(String postId, String meetingId) {
		String hql="from MeetingPost mp where  mp.meetingId="+meetingId+" and mp.id>"+postId;
		log.debug("************"+hql);
		try {
			return this.getObjects(hql);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User getBussinessCardInfo(final Long meetingId, final Long userId) {
		try {
			final Object result = meetingPostDao.getUserInfo(meetingId, userId);
			if (result == null) {
				return null;
			}
			final Object[] resultArray = (Object[]) result;
			final User user = new User();
			user.setMeetingMember(new MeetingMember());
            user.setId(Long.valueOf((Integer)resultArray[0]));
            user.setName((String)resultArray[1]);
            user.setMobile((String)resultArray[2]);
            user.setModifyTime((Date)resultArray[3]);
            user.setState(Integer.valueOf((Short)resultArray[4]));
            user.setGender( Integer.valueOf((Short) (resultArray[5]==null?(short)1:resultArray[5])));
            user.setBirthday((String)resultArray[6]);
            user.getMeetingMember().setJob((String)resultArray[7]);
            user.getMeetingMember().setMemberLevel(Integer.valueOf((Short)resultArray[8]));
			user.getMeetingMember().setSortCode(resultArray[9] == null ? null : Integer
				.valueOf(String.valueOf(resultArray[9])));
            user.getMeetingMember().setMobileIsDisplay(Integer.valueOf((Short) (resultArray[10]==null?(short)1:resultArray[10])));
            user.getMeetingMember().setCity((String)resultArray[11]);
            user.getMeetingMember().setMailbox((String)resultArray[12]);
            user.getMeetingMember().setAddress((String)resultArray[13]);            
            user.getMeetingMember().setAddInContacts(String.valueOf(resultArray[14]));
            user.getMeetingMember().setRoomNumber(String.valueOf(resultArray[15]));
            user.getMeetingMember().setRoomNumberIsDisplay(Integer.valueOf((Short) (resultArray[16]==null?(short)1:resultArray[16])));
            user.getMeetingMember().setJobIsDisplay(Integer.valueOf((Short) (resultArray[17]==null?(short)1:resultArray[17])));
            return user;
		} catch(Exception e) {
			e.printStackTrace();
			log.error("Failure to get user information.", e);
			return null;
		}
	}

	public void addViewCount(final Long postId) {
		meetingPostDao.addViewCount(postId);
	}

	public void addCommentCount(final Long postId) {
		meetingPostDao.addCommentCount(postId);
	}

	public int checkNewPost(final Long meetingId) throws ServiceException {
		return meetingPostDao.checkNewPost(meetingId);
	}

	public void decreaseCommentCount(final Long postId) {
		meetingPostDao.decreaseCommentCount(postId);
	}

	@Override
	public List<MeetingPost> findPostUser(Long meetingId) {
		return this.meetingPostDao.findPostUser(meetingId);
	}

	
}
