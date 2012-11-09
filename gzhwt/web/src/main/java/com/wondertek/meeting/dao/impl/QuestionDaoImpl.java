package com.wondertek.meeting.dao.impl;



import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.wondertek.meeting.dao.QuestionDao;
import com.wondertek.meeting.model.Question;

/** 
 * @ClassName: QuestionDaoImpl 
 * @Description: 试题DaoImpl
 * @author zouxiaoming
 * @date Feb 2, 2012 10:20:56 AM 
 *  
 */
public class QuestionDaoImpl extends BaseDaoImpl<Question, Long> implements QuestionDao {
	Log log = LogFactory.getLog(this.getClass());

	@Override
	public void deleteAllByPaperId(final Long paperId) {
		this.getHibernateTemplate().execute(new HibernateCallback<Question>() {

			@Override
			public Question doInHibernate(Session session) throws HibernateException,
					SQLException {
				String hql="delete from  Question q where q.paper.id="+paperId;
				session.createQuery(hql).executeUpdate();
				return null;
			}
		});
	}

	@Override
	public List<Question> findAllQuestion(Long paperId, boolean flag) {
		String hql="from Question q where q.paper.id="+paperId;
		if(flag){
			hql=hql+" and q.state=1";
		}
		List<Question> list=this.getHibernateTemplate().find(hql);
		return list;
	}



	
	
}
