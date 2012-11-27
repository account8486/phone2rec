package com.manager.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pager.Pager;
import com.sso.model.SsoSystemConfig;
import com.sso.service.SsoSystemConfigService;
import com.wirelesscity.action.base.BaseAction;
import com.wirelesscity.common.StringUtil;
import com.wirelesscity.exception.HibernateDaoSupportException;
import com.wirelesscity.exception.ServiceException;
import com.wirelesscity.service.JdbcService;

public class SsoSystemAction extends BaseAction {

	private static final long serialVersionUID = 8045941175178490600L;

	JdbcService JdbcService;
	
	SsoSystemConfigService ssoSystemConfigService;
	
	
	
	
	public SsoSystemConfigService getSsoSystemConfigService() {
		return ssoSystemConfigService;
	}

	public void setSsoSystemConfigService(
			SsoSystemConfigService ssoSystemConfigService) {
		this.ssoSystemConfigService = ssoSystemConfigService;
	}

	public JdbcService getJdbcService() {
		return JdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		JdbcService = jdbcService;
	}

	public static void main(String[] args) {

	}

	/**
	 * 获取SSO系统列表
	 * 
	 * @return
	 * @throws HibernateDaoSupportException 
	 */
	@SuppressWarnings("rawtypes")
	public String getSsoSystemList() throws HibernateDaoSupportException {
		// String pageSize=this.getParameter("pageSize");
		// String currentPage=this.getParameter("currentPage");

		String appName = StringUtil.trim(this.getParameter("appName"));
		StringBuffer querySql = new StringBuffer();
		StringBuffer conditionSql = new StringBuffer();

		querySql.append(" select t.appid,t.appname,t.apploginurl from tf_sso_sso t ");
		conditionSql.append("  where 1=1  ");

		if (StringUtil.isNotEmpty(appName)) {
			conditionSql.append("  and t.appname like '%" + appName + "%'");
		}

		Pager pager = JdbcService.findPagerBySql(querySql.toString(),
				conditionSql.toString(), currentPage, pageSize);
		
		this.setAttribute("appName", appName);
		this.setAttribute("pager", pager);

		// this.setAttribute("result", lstResult);

		return SUCCESS;
	}
	
	
	/**
	 * 进行系统的配置
	 * @return
	 * @throws ServiceException 
	 */
	@SuppressWarnings("rawtypes")
	public String toConfigSsoSystem() throws ServiceException{
		String appId=this.getParameter("appId");
		log.info("appId:"+appId);
		
		StringBuffer querySql = new StringBuffer();
		StringBuffer conditionSql = new StringBuffer();
		
		querySql.append(" select t.appid,t.appname,t.apploginurl from tf_sso_sso t ");
		conditionSql.append(" where t.appid='"+appId+"'");
		
		List lstResult = JdbcService.getList(querySql.append(conditionSql).toString());

		if(lstResult!=null&&lstResult.size()>0){
			Map ssoSystemMap=(HashMap)lstResult.get(0);
			this.setAttribute("systemEntity", ssoSystemMap);
			
			//查找对应信息
		    StringBuffer hql=new StringBuffer();
		    hql.append(" from  SsoSystemConfig po where po.ssoId='"+appId+"'");
		    
		    List<SsoSystemConfig> configList= ssoSystemConfigService.queryList(hql.toString(), null);
		    if(configList!=null&&configList.size()>0){
		    	//看是否为空
		    	this.setAttribute("systemConfig", configList.get(0));
		    }
		    
		}
		
		
		return SUCCESS;
	}
	
	
	/**
	 * 进行配置
	 * @return
	 * @throws ServiceException 
	 */
	public String configSsoSystem() throws ServiceException{
		String userName=StringUtil.trim(getRequest().getParameter("userName"));
		String password=StringUtil.trim(getRequest().getParameter("password"));
		String appid=StringUtil.trim(getRequest().getParameter("appid"));
		String accountColumnName=StringUtil.trim(getRequest().getParameter("accountColumnName"));
		String systemCofigId=StringUtil.trim(getRequest().getParameter("systemCofigId"));
		
		log.debug("appid:"+appid+",userName:"+userName+",password:"+password+",systemCofigId:"+systemCofigId+",accountColumnName:"+accountColumnName);
		if(StringUtil.isNotEmpty(systemCofigId)){
			SsoSystemConfig config= ssoSystemConfigService.findById(systemCofigId);
			config.setSsoId(appid);
			config.setUserNameCfg(userName);
			config.setPasswordCfg(password);
			config.setCreateTime(new Date());
			config.setLogoUrl(null);
			config.setAccountColumnName(accountColumnName);
			ssoSystemConfigService.saveOrUpdate(config);
		}else{
			//查找对应信息
		    StringBuffer hql=new StringBuffer();
		    hql.append(" from  SsoSystemConfig po where po.ssoId='"+appid+"'");
		    List<SsoSystemConfig> configList= ssoSystemConfigService.queryList(hql.toString(), null);
		    if(configList!=null&&configList.size()>0){
		    	//看是否为空
		    	SsoSystemConfig config=configList.get(0);
		    	config.setUserNameCfg(userName);
				config.setPasswordCfg(password);
				config.setUpdateTime(new Date());
				config.setLogoUrl(null);
				config.setAccountColumnName(accountColumnName);
				ssoSystemConfigService.saveOrUpdate(config);
				
		    }else{
		    	SsoSystemConfig config=new SsoSystemConfig();
				config.setUserNameCfg(userName);
				config.setPasswordCfg(password);
				config.setSsoId(appid);
				config.setCreateTime(new Date());
				config.setLogoUrl(null);
				config.setAccountColumnName(accountColumnName);
				ssoSystemConfigService.saveOrUpdate(config);
		    }
		}

		
		return this.getSsoSystemList();
	}
	
	

}
