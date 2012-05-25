package com.wondertek.meeting.action.base;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.common.SysUtil;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.util.IPRequest;
import com.wondertek.meeting.util.JsonUtil;

/**
 * Implementation of <strong>ActionSupport</strong> that contains convenience
 * methods for subclasses. For example, getting the current user and saving
 * messages/errors. This class is intended to be a base class for all Action
 * classes.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 3525445612504421307L;

	public static final String PORTAL = "portal";

	public Map<String, Object> resultMap = new HashMap<String, Object>();

	/**
	 * Transient log to prevent session synchronization issues - children can
	 * use instance for logging.
	 */
	public Logger log = LoggerFactory.getLogger(this.getClass());

	protected int currentPage = 1;

	protected int pageSize = 10;

	protected String errMsg;

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

//	protected Long getMeetingIdFromSession() {
//		Object o = getSession().getAttribute("_portal_meeting_");
//		if (o != null) {
//			Meeting meeting = (Meeting) o;
//			if (meeting != null) {
//				return meeting.getId();
//			}
//		} else {
//			Object obj = getSession().getAttribute(SessionKeeper.CURRENT_MEETING_ID);
//			if (obj != null) {
//				return (Long) obj;
//			}
//		}
//
//		return null;
//	}

	protected User getUserFromSession() {
		Object o = getSession().getAttribute(SessionKeeper.SESSION_USER);

		if (o != null) {
			return (User) o;
		}

		return null;
	}

	protected Long getUserIdFromSession() {
		Object o = getSession().getAttribute(SessionKeeper.SESSION_USER);
		if (o != null) {
			User user = (User) o;
			if (user != null) {
				return user.getId();
			}
		}

		return null;
	}

	protected AdminUser getAdminUserFromSession() {
		Object o = getSession().getAttribute(SessionKeeper.SESSION_ADMIN_USER);

		if (o != null) {
			return (AdminUser) o;
		}

		return null;
	}

	protected Long getAdminUserIdFromSession() {
		Object o = getSession().getAttribute(SessionKeeper.SESSION_ADMIN_USER);
		if (o != null) {
			AdminUser user = (AdminUser) o;
			if (user != null) {
				return user.getId();
			}
		}

		return null;
	}

	protected Long getOrgIdFromSession() {
		AdminUser sessionUser = this.getAdminUserFromSession();
		if (sessionUser == null || sessionUser.getOrg() == null) {
			return null;
		}

		return sessionUser.getOrg().getId();
	}

	protected Integer getOrgLevelFromSession() {
		Object o = getSession().getAttribute(SessionKeeper.SESSION_ADMIN_USER);
		if (o != null) {
			AdminUser user = (AdminUser) o;
			if (user != null) {
				if (user.getOrg() != null) {
					return user.getOrg().getLevel();
				}
			}
		}

		return null;
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

	protected boolean isAdmin() {
		final AdminUser user = (AdminUser) getFromSession(SessionKeeper.SESSION_ADMIN_USER);
		if (SysUtil.isMeetingAdmin(user) || SysUtil.isSuperAdmin(user)) {
			return true;
		} else {
			return false;
		}
	}

	protected String modulePath() {
		if (this.isAdmin()) {
			return "admin";
		} else {
			return "portal";
		}
	}

	/**
	 * 判断某用户是否拥有某权限
	 * 
	 * @param permissionId
	 * @return
	 */
	public boolean hasPermission(String permissionId) {
		Map session = ActionContext.getContext().getSession();
		String permIds = session.get(SessionKeeper.PermissionIds).toString();
		if (StringUtils.isEmpty(permIds)) {
			return false;
		}
		String[] tmpIds = permIds.replace("[", "").replace("]", "").split(",");
		Collection<String> permissionIds = new HashSet<String>(tmpIds.length);
		for (String s : tmpIds) {
			permissionIds.add(s.trim());
		}

		return permissionIds.contains(permissionId);
	}

	/**
	 * 得到当前用户的访问客户端IP
	 * 
	 * @return
	 */
	public String getLoginIP() {
		return IPRequest.getIpAddress(ServletActionContext.getRequest());
	}

	public String json2Resp(Object obj) {
		PrintWriter out = null;
		getResponse().setContentType("application/json;charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		try {
			out = getResponse().getWriter();
			// String result = JSON.toJSONString(obj);
			String result = JsonUtil.object2json(obj);
			log.debug(result);
			out.write(result);
		} catch (Exception e) {
			log.error("json2Resp error ", e);
		} finally {
			if (null != out) {
				out.close();
			}
		}

		return null;
	}

	public String str2Resp(String str) {
		PrintWriter out = null;
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		try {
			out = getResponse().getWriter();
			log.debug("str2Resp {} ", str);
			out.write(str);
		} catch (Exception e) {
			log.error("str2Resp error ", e);
		} finally {
			if (null != out) {
				out.close();
			}
		}

		return null;
	}



	/**
	 * 获取服务器地址
	 * 
	 * @return
	 */
	public String getBasePath() {
		String path = getRequest().getContextPath();
		String bases = getRequest().getHeader("X-FORWARDED-HOST");
		if (bases == null || bases.length() < 1) {
			bases = getRequest().getHeader("Host");
		}

		if (bases == null || bases.length() < 1) {
			bases = getRequest().getServerName() + ":" + getRequest().getServerPort();
		}
		String basePath = getRequest().getScheme() + "://" + bases + path;
		return basePath;
	}
	
	/**统计访问的菜单*/
	public String analyticsLog(){
		log.debug("analyticsLog menu_id = {}",getRequest().getAttribute("menu_id"));
		return SUCCESS;
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
