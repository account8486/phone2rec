package com.wondertek.meeting.dao.impl;



import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.wondertek.meeting.dao.LuckyUserDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.LuckyUser;

/**
* @ClassName: LuckyUserDaoImpl 
* @Description: 可中奖人管理
* @author zouxiaoming
* @date Mar 20, 2012 4:30:08 PM 
*
 */
public class LuckyUserDaoImpl extends BaseDaoImpl<LuckyUser, Long> implements LuckyUserDao {
	Log log = LogFactory.getLog(this.getClass());

	@Override
	public void deleteLuckyUser(final Long luckyId) {
			this.getHibernateTemplate().execute(new HibernateCallback<LuckyUser>() {

				@Override
				public LuckyUser doInHibernate(Session session) throws HibernateException,
						SQLException {
					String hql="delete from  LuckyUser lh where lh.lucky.id="+luckyId;
					session.createQuery(hql).executeUpdate();
					return null;
				}
			});
			
	}

	@Override
	public LuckyUser findLuckyUser(Long userId, Long meetingId) {
		try {
			String hql="from LuckyUser lu where lu.meeting.id="+meetingId+" and lu.user.id="+userId;
			List<LuckyUser> users=this.getObjects(hql);
			if(users==null||users.size()==0){
				return null;
			}else{
				return users.get(0);
			}
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
}
