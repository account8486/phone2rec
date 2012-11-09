/**
 * 处理全局范围内土特产信息Dao实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-17
 */
package com.wondertek.meeting.dao.impl.reception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.dao.reception.LocalSpecialtyDao;
import com.wondertek.meeting.model.reception.LocalSpecialty;
import com.wondertek.meeting.util.StringUtil;

public class LocalSpecialtyDaoImpl extends BaseDaoImpl<LocalSpecialty, Integer>
		implements LocalSpecialtyDao {

	/**
	 * 分页查看会议土特产中的特产明细列表
	 * localSpecialty: 查询条件:名称 or 省 or 市
	 */
	public Pager<LocalSpecialty> findAllLocalSpecialtyPager(
			Integer meetingSpecialtyId, LocalSpecialty localSpecialty, 
			int currentPage, int pageSize) throws Exception {
		
		String hql = "select s from LocalSpecialty s join s.meetingSpecialtySet m where m.id=:id ";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", meetingSpecialtyId);
		
		if(! StringUtil.isEmpty(localSpecialty.getName())) {
			hql += " and s.name like :name";
			param.put("name", "%" + localSpecialty.getName() + "%");
		}
		
		if(! StringUtil.isEmpty(localSpecialty.getProvince())) {
			hql += " and s.province = :province";
			param.put("province", localSpecialty.getProvince());
		}
		
		if(! StringUtil.isEmpty(localSpecialty.getCity())) {
			hql += " and s.city = :city";
			param.put("city", localSpecialty.getCity());
		}
		
		hql += " order by s.id desc";
		return this.findPager(hql, currentPage, pageSize, param);
	}
	
	/**
	 * 根据名字，区域查找特产信息
	 * 限制最多返回10条记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> searchLocalSpecialty(final LocalSpecialty example) {
		final String hql = "select s.id, s.name from LocalSpecialty s where s.name like :name and s.province=:province " +
				"and s.city=:city  order by s.id desc";
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<Object[]>>() {
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql)
					.setString("name", "%" + example.getName() + "%")
					.setString("province", example.getProvince())
					.setString("city", example.getCity())
					.setMaxResults(10)
					.list();
			}
		});
	}
	
	/**
	 * 从指定的会议特产中查找指定ID的土特产项
	 */
	public LocalSpecialty findLocalSpecialtyFromMeetingSpecialty(
			Integer meetingSpecialtyId, Integer localSpecialtyId) {
		String hql = "select s from LocalSpecialty s join s.meetingSpecialtySet m" +
				" where m.id=? and s.id=? ";
		List<LocalSpecialty> list = this.getHibernateTemplate().find(hql, meetingSpecialtyId, localSpecialtyId);
		return list.size() > 0 ? list.get(0) : null;
	}
}
