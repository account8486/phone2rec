package com.wondertek.meeting.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 在线用户管理
 * 
 * @author 金祝华
 */
public class OnlineUserListener implements HttpSessionListener {

	public static final String ONLINE_USER_LIST = "ONLINE_USER_LIST";

	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();

		ServletContext application = session.getServletContext();

		@SuppressWarnings("unchecked")
		List<HttpSession> onlineUserList = (List<HttpSession>) application.getAttribute(ONLINE_USER_LIST);

		// 增加用户session
		if (onlineUserList == null) {
			synchronized (this) {
				onlineUserList = new ArrayList<HttpSession>();
				onlineUserList.add(session);
			}
		} else {
			onlineUserList.add(session);
		}

		application.setAttribute(ONLINE_USER_LIST, onlineUserList);
	}

	public void sessionDestroyed(HttpSessionEvent event) {

		HttpSession session = event.getSession();

		ServletContext application = session.getServletContext();

		// 删除用户session
		@SuppressWarnings("unchecked")
		List<HttpSession> onlineUserList = (List<HttpSession>) application.getAttribute(ONLINE_USER_LIST);

		onlineUserList.remove(session);
	}
}
