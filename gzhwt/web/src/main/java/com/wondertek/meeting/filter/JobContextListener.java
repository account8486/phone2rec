package com.wondertek.meeting.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.wondertek.meeting.job.FileConvertJob;

/**
 * User: sank Date: 11-11-29 Time: 上午1:34
 */
public class JobContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		FileConvertJob.setServletContext(servletContextEvent.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}
}
