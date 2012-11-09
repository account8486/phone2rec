package com.wondertek.meeting.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.dao.ClientVersionDao;
import com.wondertek.meeting.model.ClientVersion;

/**
 * 客户端版本Dao
 * 
 * @author 金祝华
 */
public class ClientVersionDaoImpl extends BaseDaoImpl<ClientVersion, Integer> implements ClientVersionDao {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public ClientVersion findById(Integer id) {
		String hql = "from ClientVersion b where b.id = ?";

		List<ClientVersion> list = this.getHibernateTemplate().find(hql, id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * 查询全部
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ClientVersion> findAll() {
		String hql = "from ClientVersion b order by b.id desc";

		List<ClientVersion> list = this.getHibernateTemplate().find(hql);

		return list;
	}
}
