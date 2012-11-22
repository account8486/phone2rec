package com.sso.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.util.MD5Encrypt;
import com.wirelesscity.action.base.BaseAction;
import com.wirelesscity.common.StringUtil;
import com.wirelesscity.service.JdbcService;

public class IntegrateLoginAction extends BaseAction {

	private static final long serialVersionUID = 2483103544033809632L;

	JdbcService jdbcService;
	
	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	/**
	 * 门户应急信息统一登陆
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

		String md5Password = MD5Encrypt.getEncrypt().encode(password);
		log.debug("md5Password:" + md5Password);

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
		objArr[1] = md5Password;

		List lstResult = jdbcService.test(searchSql.toString(), objArr);

		if (lstResult != null && lstResult.size() == 1) {

			HashMap userMap = (HashMap) lstResult.get(0);

			// 如果账户存在并且密码正确的话，查询此用户对应的系统权限及对应的账号信息
			StringBuffer searchSysSql = new StringBuffer();
			searchSysSql
					.append(" select sso.appid, sso.appname,sso.apploginurl, sso.APPLOGINFORMPARANAMES,config.user_name_cfg,config.password_cfg,config.logo_url ");
			searchSysSql
					.append(" from tf_sso_sso sso left join sso_system_config config on config.sso_id = sso.appid ");
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
					System.out.println("systemPri:" + systemPri);
					if ("true".equals(systemPri)) {
						mySystemLoginLst.add(map);
					}
				}
			}
			
			this.setAttribute("mySystemLoginLst", mySystemLoginLst);
			
			return SUCCESS;
		} else {
			return INPUT;
		}

	}
	
	
	
	
	public void validateUser(){
		
		
	}

}
