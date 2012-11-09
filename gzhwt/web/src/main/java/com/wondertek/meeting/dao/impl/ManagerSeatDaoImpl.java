package com.wondertek.meeting.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.lob.SerializableBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.wondertek.meeting.dao.ManagerSeatDao;
import com.wondertek.meeting.model.MeetingSeat;
import com.wondertek.meeting.model.MeetingSeatImage;
import com.wondertek.meeting.model.MeetingSeatTemplate;

public class ManagerSeatDaoImpl extends BaseDaoImpl<MeetingSeat, Integer> implements ManagerSeatDao {

	@Autowired
	private void setSessionFactory1(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<MeetingSeatTemplate> getMeetingTemplate() {
		return this.getHibernateTemplate().find("select distinct name from MeetingSeatTemplate");
	}

	@Override
	public List<MeetingSeatTemplate> getMeetingTemplateByName(String name) {
		return this.getHibernateTemplate().find("from MeetingSeatTemplate where name = ?", name);
	}

	@Override
	public void saveSeatTemplate(MeetingSeatTemplate seatTemplate) {
		this.getHibernateTemplate().save(seatTemplate);
	}

	@Override
	public void saveSeatImage(final MeetingSeatImage msi) {
//		this.getHibernateTemplate().save(msi);

		// 真烦，还得下面这个步骤才能保存blob字段。
		this.getHibernateTemplate().execute(new HibernateCallback<MeetingSeatImage>() {
			@Override
			public MeetingSeatImage doInHibernate(Session session) throws HibernateException, SQLException {
				MeetingSeatImage _msi = new MeetingSeatImage();
				_msi.setMeetingId(msi.getMeetingId());
				_msi.setOffsetX(msi.getOffsetX());
				_msi.setOffsetY(msi.getOffsetY());
				_msi.setImage(Hibernate.createBlob(new byte[0]));
				session.save(_msi);

				session.flush();
				session.refresh(_msi, LockMode.UPGRADE);
				
				_msi.setImage(msi.getImage());
				session.flush();
				return null;
			}

		});
	}
}
