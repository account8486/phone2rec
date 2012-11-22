package com.wirelesscity.action.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wirelesscity.tools.ftp.FtpService;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = -8843362213171564179L;

	public Map<String, Object> resultMap = new HashMap<String, Object>();

	public Logger log = LoggerFactory.getLogger(this.getClass());

	protected String errMsg;
	
	public FtpService ftpService;
	
	protected int currentPage = 1;

	protected int pageSize = 10;
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public FtpService getFtpService() {
		return ftpService;
	}

	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}

	/**
	 * Convenience method to get the request
	 * 
	 * @return current request
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Convenience method to get the parameter
	 * 
	 * @return current request
	 */
	protected String getParameter(String key) {
		return getRequest().getParameter(key);
	}

	/**
	 * Convenience method to get the response
	 * 
	 * @return current response
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * Convenience method to get the session. This will create a session if one
	 * doesn't exist.
	 * 
	 * @return the session from the request (request.getSession()).
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected Object getFromSession(String key) {
		return getSession().getAttribute(key);
	}

	protected String getStringFromSession(String key) {
		Object get = getSession().getAttribute(key);
		return get == null ? null : get.toString();
	}

	protected void putToSession(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 获取ActionContext对象
	 * @return
	 */
	public ActionContext getContext(){
		return ActionContext.getContext();
	}
	
	/**
	 * 获取ServletContext对象
	 * @return
	 */
	public ServletContext getServletContext(){
		return ServletActionContext.getServletContext();
	}

	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * 把指定的数据放入struts2的value stack中
	 */
	public void setAttribute(String key, Object value) {
		ActionContext.getContext().getValueStack().set(key, value);
	}

}
