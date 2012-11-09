package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.dao.TagDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.DataDictConfig;
import com.wondertek.meeting.model.TagEntity;
import com.wondertek.meeting.service.TagService;

/**
 * 用户角色
 * 
 * @author 金祝华
 */
public class TagServiceImpl extends BaseServiceImpl<DataDictConfig, Long> implements TagService {

	private TagDao tagDao;

	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.basicDao = tagDao;
		this.tagDao = tagDao;
	}

	@Override
	public List<DataDictConfig> query(DataDictConfig config) {
		String hql="from DataDictConfig where actType=:actType and dataType=:dataType order by order_by";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("actType", config.getActType());
		properties.put("dataType", config.getDataType());
		log.debug("输入参数为：{},{}",config.getActType(),config.getDataType());
		List<DataDictConfig>  list = null;
		try {
			list = findPager(hql,0,-1,properties).getPageRecords();
		} catch (ServiceException e) {
			log.error("查询数据字典失败，错误原因：{}",e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DataDictConfig> query(TagEntity entity) {
		List<DataDictConfig> list = new ArrayList<DataDictConfig>();
		StringBuffer sb= new StringBuffer();
		sb.append(" select ");
		sb.append(entity.getEntityValue()).append(",").append(entity.getEntityName());
		sb.append(" from ");
		sb.append(entity.getEntity()).append(" ");
		if(entity.getCondition()!=null && !"".equals(entity.getCondition())){
			sb.append(entity.getCondition());
		}
		try {
			List<Object> result_list = tagDao.queryListSql(sb.toString(), 0, -1,null);
			//to-do:
			for (Object o : result_list) {
				Object[] resultArray = (Object[]) o;
				String entityValue = String.valueOf(resultArray[0]);
				String entityName = String.valueOf(resultArray[1]);
				DataDictConfig ddc =  new DataDictConfig();
				ddc.setDataCode(entityValue);
				ddc.setDataName(entityName);
				list.add(ddc);
			}
			//getObjects(sb.toString());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return list;
	}
}
