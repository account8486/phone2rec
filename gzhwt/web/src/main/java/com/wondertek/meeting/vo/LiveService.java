/**
 * 会场服务信息类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-4-23
 */
package com.wondertek.meeting.vo;

import java.util.LinkedList;
import java.util.List;

import com.wondertek.meeting.model.BaseObject;

public class LiveService extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private int index;
	private List<LiveServiceInfo> serviceInfoList = new LinkedList<LiveServiceInfo>();;

	public synchronized int getIndex() {
		return index;
	}

	public synchronized List<LiveServiceInfo> getServiceInfoList() {
		return serviceInfoList;
	}

	/**
	 * 放入队列尾
	 */
	public synchronized void push(LiveServiceInfo serviceInfo) {
		index ++;
		serviceInfo.setId(index);
		serviceInfoList.add(serviceInfo); //放入队列尾
	}
	
	/**
	 * 从队列中取最近一个元素，如果队列空则返回null
	 */
	public synchronized LiveServiceInfo pop() {
		if(serviceInfoList.size() <= 0) {
			return null;
		}
		LiveServiceInfo serviceInfo = serviceInfoList.get(0);
		serviceInfoList.remove(0);
		return serviceInfo;
	}
	
	/**
	 * 返回队列元素个数
	 */
	public synchronized int getliveServiceInfoCount() {
		return serviceInfoList.size();
	}
}
