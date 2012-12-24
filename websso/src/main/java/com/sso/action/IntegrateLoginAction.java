package com.sso.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.sso.model.SsoSystemConfig;
import com.sso.service.SsoSystemConfigService;
import com.util.EncodeUtil;
import com.util.webSecurity.UserSecurity;
import com.wirelesscity.action.base.BaseAction;
import com.wirelesscity.common.StringUtil;
import com.wirelesscity.exception.ServiceException;
import com.wirelesscity.service.JdbcService;

public class IntegrateLoginAction extends BaseAction {

	private static final long serialVersionUID = 2483103544033809632L;

	JdbcService jdbcService;
	SsoSystemConfigService ssoSystemConfigService;
	
	
	
	public SsoSystemConfigService getSsoSystemConfigService() {
		return ssoSystemConfigService;
	}

	public void setSsoSystemConfigService(
			SsoSystemConfigService ssoSystemConfigService) {
		this.ssoSystemConfigService = ssoSystemConfigService;
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	/**
	 * 门户应急统一登陆
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String integrateLogin() {
		String userName = StringUtil
				.trim(getRequest().getParameter("userName"));
		String password = StringUtil
				.trim(getRequest().getParameter("password"));

		// 统一用户验证此用户是否存在
		if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
			return INPUT;
		}
		//String md5Password = EncodeUtil.md5Encode(password, EncodeUtil.UPPER_CASE_FORMATTER);
		String portalPassword =UserSecurity.Encrypt(password);
		
		log.debug("portalPassword:" + portalPassword);

		// TODO
		// 此处进行数据库的查找与比对,查找数据库t_idm_user中的字段
		/*
		 * StringBuffer searchSql = new StringBuffer();
		 * searchSql.append(" select *  from t_idm_user t where t.cn='"+
		 * userName + "' and t.password='" + md5Password + "'");
		 * 
		 * List lstResult = jdbcService.getList(searchSql.toString());
		 */
		StringBuffer searchSql = new StringBuffer();
		searchSql
				.append(" select * from t_idm_user t where t.cn=? and t.password=? ");
		Object[] objArr = new Object[2];
		objArr[0] = userName;
		objArr[1] = portalPassword;

		List lstResult = jdbcService.getResultWithArgs(searchSql.toString(), objArr);

		if (lstResult != null && lstResult.size() == 1) {

			
			
			HashMap userMap = (HashMap) lstResult.get(0);

			// 如果账户存在并且密码正确的话，查询此用户对应的系统权限及对应的账号信息
			StringBuffer searchSysSql = new StringBuffer();
			searchSysSql
					.append(" select sso.appid, sso.appname,sso.apploginurl, sso.APPLOGINFORMPARANAMES,config.user_name_cfg,config.password_cfg,config.logo_url,ACCOUNT_COLUMN_NAME,FORM_ACTION_URL ");
			searchSysSql.append("  ,config.id as configId");
			searchSysSql
					.append(" from tf_sso_sso sso left join sso_system_config config on config.sso_id = sso.appid ");
			
			//筛选启用的系统
			searchSysSql.append(" where config.USING_ENABLED=1 ");
			// 我的登录对应的信息
			List<HashMap> mySystemLoginLst = new ArrayList<HashMap>();

			// 系统自身的LIST
			List systemList = jdbcService.getList(searchSysSql.toString());
			for (Object obj : systemList) {
				HashMap<String, Object> map = (HashMap<String, Object>) obj;
				System.out.println(map.get("APPLOGINFORMPARANAMES"));
				String appLoginFormParaNames = String.valueOf(map
						.get("APPLOGINFORMPARANAMES"));
				if ("uid".equals(appLoginFormParaNames)) {
					mySystemLoginLst.add(map);
				} else {
					String systemPri = (String) userMap
							.get(appLoginFormParaNames.toUpperCase());
					if ("true".equals(systemPri)) {
						mySystemLoginLst.add(map);
					}
				}
			}
			
			this.setAttribute("mySystemLoginLst", mySystemLoginLst);
			this.setAttribute("password", password);
			this.setAttribute("userName", userName);
			
			
			this.getSession().setAttribute("userName", userName);
			this.getSession().setAttribute("password", password);
			this.getSession().setAttribute("fullName", userMap.get("fullName"));
			
			return SUCCESS;
		} else {
			return INPUT;
		}

	}
	
	
	
	/**
	 * 获取用户的登录信息
	 * @return
	 */
	public String getUserLoginInfo(){
		//String 
		String appId=getRequest().getParameter("appId");
		String accountColumnName=getRequest().getParameter("accountColumnName");
		String userAccount=getRequest().getParameter("userAccount");
		String password=getRequest().getParameter("password");
		String configId=getRequest().getParameter("configId");
		String formatter=StringUtil.trim(getRequest().getParameter("formatter"));
		
	    if(StringUtil.isEmpty(accountColumnName)){
	    	this.resultMap.put("msg", "此业务系统在单点集成中尚未配置字段名参数！");
			this.resultMap.put("result", false);
			return SUCCESS;
	    }
	    
	    if(StringUtil.isEmpty(userAccount)){
	    	this.resultMap.put("msg", "账号参数为空，请稍后再试！");
			this.resultMap.put("result", false);
			return SUCCESS;
	    }
	    
	    if(StringUtil.isEmpty(password)){
	    	this.resultMap.put("password", "密码值为空，请重新登陆后再试！");
			this.resultMap.put("result", false);
			return SUCCESS;
	    }
	    
	   
		
		StringBuffer sql=new StringBuffer();
		sql.append(" select  "+accountColumnName);
		sql.append(" from t_idm_user t  ");
		sql.append(" where t.cn='"+userAccount+"'");
		log.warn("sql:"+sql);
		
		List resultLst=jdbcService.getList(sql.toString());
		if(resultLst!=null&&resultLst.size()>0){
			HashMap columnNameMap=(HashMap)resultLst.get(0);
			String columnNameValue=(String)columnNameMap.get(accountColumnName);
			this.resultMap.put("columnNameValue", columnNameValue);
			this.resultMap.put("result", true);
		}
		
		
		try {
			SsoSystemConfig ssoSystemConfig=ssoSystemConfigService.findById(configId);
			String encodeStle=ssoSystemConfig.getEncodeStyle();
			if (StringUtil.isNotEmpty(encodeStle)) {
				if ("md5".equals(encodeStle)) {
					this.resultMap.put("password",
							EncodeUtil.md5Encode(password,formatter));
				}
				if ("base64".equals(encodeStle)) {
					this.resultMap.put("password",
							EncodeUtil.base64Encode(password,formatter));
				}else{
					this.resultMap.put("password", password);
				}

			}else{
				this.resultMap.put("password", password);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}


}
