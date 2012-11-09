package com.wondertek.meeting.action.datadict;

import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.DataDictConfig;
import com.wondertek.meeting.service.TagService;

public class DataDictConfigAction extends BaseAction {

	private static final long serialVersionUID = -198144583405651745L;
	TagService tagService;
	
	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	/**
	 * 通过参数获取值
	 * @return
	 */
	public String getDataDictConfig(){
		String forward=SUCCESS;
		String dataType="find_back_password";
		String actType="common";
		//查询数据库配置,是否动态
		DataDictConfig queryDc=new DataDictConfig();
		//暂时先写死
		queryDc.setDataType(dataType);
		queryDc.setActType(actType);
		List<DataDictConfig> dataDictConfigList=tagService.query(queryDc);
		//获取第一个,返回到页面
		if(dataDictConfigList!=null&&dataDictConfigList.size()>0){
			getRequest().setAttribute("dataDictConfig",dataDictConfigList.get(0));
		}
		
		return forward;
	}
	
	/**
	 * 更新当前配置
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String updateDataDictConfig() throws NumberFormatException, ServiceException{
		String forward=SUCCESS;
		//主键ID
		String id=getRequest().getParameter("dataDictConfigId");
		//配置值
		String dataValue=getRequest().getParameter("dataValue");
		DataDictConfig dataDictConfig=tagService.findById(Long.valueOf(id));
		dataDictConfig.setDataValue(dataValue);
		
		tagService.saveOrUpdate(dataDictConfig);
		getRequest().setAttribute("dataDictConfig",dataDictConfig);
		
		return forward;
	}

}
