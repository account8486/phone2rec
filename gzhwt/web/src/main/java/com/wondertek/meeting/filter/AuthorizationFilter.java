package com.wondertek.meeting.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 过滤用户对/pri-* 上下文中的请求,如果用户未登录，则返回用户需要登录
 * 
 * @author 金祝华
 */
public class AuthorizationFilter implements Filter {
	private static Logger log = Logger.getLogger(AuthorizationFilter.class);

	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		String userAgent = req.getHeader("user-agent");
		log.debug("Enter AuthorizationFilter doFilter: userAgent=" + userAgent);

		HttpServletResponse rsp = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		if (uri.indexOf("/pri/") == -1) {
			chain.doFilter(request, response);
			return;
		}

		// 管理后台
		if (uri.indexOf("/admin/") != -1) {
			AdminUser u = (AdminUser) req.getSession().getAttribute(SessionKeeper.SESSION_ADMIN_USER);
			if (u != null) { // 允许访问
				chain.doFilter(request, response);
				return;
			} else {
				// 后台未登录则跳转到后台登陆页面
				// rsp.sendRedirect(req.getContextPath() +
				// "/pages/admin/login.jsp");
				// modified by chif
				// 解决后台session无效后，登陆页面 在iframe内显示的问题
				String admin_login_page = req.getContextPath() + "/pages/admin/login.jsp";
				response.setContentType("text/html");
				((HttpServletResponse) response).setHeader("Cache-Control", "no-cache");
				((HttpServletResponse) response).setHeader("Pragma", "no-cache");
				((HttpServletResponse) response).setDateHeader("Expires", -1);
				java.io.PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<script>");
				out.println("window.open ('" + admin_login_page + "','_top');");
				out.println("</script>");
				out.println("</html>");
				out.flush();
				out.close();
				return;
			}

		} else if (uri.indexOf("/client") != -1) { // 客户端
			User u = (User) req.getSession().getAttribute(SessionKeeper.SESSION_USER);
			if (u == null) {
				// 未登录则自动登录
				String imei = request.getParameter("imei");

				if (StringUtil.isEmpty(imei)) {
					log.error("客户端未传递imei参数，不能进行自动登录。uri=" + uri);
					PrintWriter out = response.getWriter();
					out.println("{\"retcode\":\"2\"}");
					out.println("{\"retmsg\":\"请输入imei码\"}");
					out.flush();
					out.close();
					return;
				} else {

					ServletContext context = req.getSession().getServletContext();
					ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);

					UserService userService = (UserService) ctx.getBean("userService");

					User user = userService.selectByImei(imei);

					if (user == null) {
						log.error("根据imei没有查询到用户。uri=" + uri + ",imei=" + imei);
						PrintWriter out = response.getWriter();
						out.println("{\"retcode\":\"2\"}");
						out.println("{\"retmsg\":\"please login in\"}");
						out.flush();
						out.close();
						return;
					}

					// 将用户信息存入session
					req.getSession().setAttribute(SessionKeeper.SESSION_USER, user);
				}
			}

			chain.doFilter(request, response);
			return;
		} else if (uri.indexOf("/wap/") != -1) { // WAP
			if (req.getSession().getAttribute(SessionKeeper.SESSION_USER) != null) {
				chain.doFilter(request, response);
				return;
			} else {
				//判断是否是进入会议的快捷连接，如果是，则记下会议ID
				String path = "/pages/wap/login.jsp";
				
				if(uri.indexOf("wap/pri/meeting/getMeetingById.action") != -1) {
					String queryString = req.getQueryString();
					String[] ary = queryString.split("&");
					for(String s : ary) {
						if(s.indexOf("meeting.id") != -1) {
							String[] ary2 = s.split("=");
							if(ary2.length == 2) {
								String meetingId = ary2[1];
								path += "?meetingId=" + meetingId;
							}
						}
					}
				}
				
				// 未登录则跳转到web登陆页面
				rsp.sendRedirect(req.getContextPath() + path);
			}
		} else if (uri.indexOf("/touch/") != -1) { // touch
			if (req.getSession().getAttribute(SessionKeeper.SESSION_USER) != null) {
				chain.doFilter(request, response);
				return;
			} else {
				//判断是否是进入会议的快捷连接，如果是，则记下会议ID
				String path = "/pages/touch/login.jsp";
				
				if(uri.indexOf("/pages/touch/pri/index.jsp") != -1) {
					String queryString = req.getQueryString();
					path += "?" + queryString;
				}
				
				// 未登录则跳转到touch登陆页面
				rsp.sendRedirect(req.getContextPath() + path);
			}
		} else { // web前台
			if (req.getSession().getAttribute(SessionKeeper.SESSION_USER) != null
					|| req.getSession().getAttribute(SessionKeeper.SESSION_PREVIEW_USER) != null) {
				chain.doFilter(request, response);
				return;
			} else {
				//判断是否是进入会议的快捷连接，如果是，则记下会议ID
				String path = "/pages/portal/login.jsp";
				
				if(uri.indexOf("portal/pri/meeting/getMeetingById.action") != -1) {
					String queryString = req.getQueryString();
					String[] ary = queryString.split("&");
					for(String s : ary) {
						if(s.indexOf("meeting.id") != -1) {
							String[] ary2 = s.split("=");
							if(ary2.length == 2) {
								String meetingId = ary2[1];
								path += "?meetingId=" + meetingId;
							}
						}
					}
				}
				
				// 未登录则跳转到web登陆页面
				rsp.sendRedirect(req.getContextPath() + path);
			}
		}

		log.info("authorization failed:[request.url= " + req.getRequestURL() + "]");
		return;
	}

	public void destroy() {
	}
}
