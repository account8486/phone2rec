package com.wondertek.meeting.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring ApplicationContext 获取bean辅助类
 * 
 * @author 金祝华
 */
public class ApplicationContextHelper implements ApplicationContextAware {
	private static ApplicationContext ctx;

	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		ApplicationContextHelper.ctx = arg0;
	}

	/**
	 * 通过bean name获取bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}
}
