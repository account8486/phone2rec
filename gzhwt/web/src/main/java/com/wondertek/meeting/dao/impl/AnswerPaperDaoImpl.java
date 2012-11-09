package com.wondertek.meeting.dao.impl;




import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.wondertek.meeting.dao.AnswerPaperDao;
import com.wondertek.meeting.model.AnswerPaper;
import com.wondertek.meeting.model.Question;

/**
 * 
* @ClassName: AnswerPaperDao 
* @Description: 答卷DAO接口实现
* @author zouxiaoming
* @date Feb 23, 2012 3:00:30 PM 
*
 */
public class AnswerPaperDaoImpl extends BaseDaoImpl<AnswerPaper, Long> implements AnswerPaperDao {

	Log log = LogFactory.getLog(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	public List<AnswerPaper> findContent(Long pageId,Long questionId) {
		String hql="from AnswerPaper ap where ap.paper.id="+pageId+" and  ap.type in(1,3)  and ap.question.id="+questionId;
		hql+= " order by answerTime desc ";
		List<AnswerPaper> list=this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public void deleteAllAnswerByPaperId(final Long paperId) {
		this.getHibernateTemplate().execute(new HibernateCallback<Question>() {

			@Override
			public Question doInHibernate(Session session) throws HibernateException,
					SQLException {
				String hql="delete from  AnswerPaper aq where aq.paper.id="+paperId;
				session.createQuery(hql).executeUpdate();
				return null;
			}
		});
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnswerPaper> findMyAnswerPaper(Long userId,
			Long paperId) {
		String hql="from AnswerPaper ap where ap.paper.id="+paperId+" and ap.user.id="+userId;
		List<AnswerPaper> list=this.getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnswerPaper> findAllByPaperId(Long paperId) {
		String hql="from AnswerPaper ap where ap.paper.id="+paperId;
		List<AnswerPaper> list=this.getHibernateTemplate().find(hql);
		return list;
	}

	
}
