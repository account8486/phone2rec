/**
 * 会场服务队列管理类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-4-23
 */
package com.wondertek.meeting.manager.liveservice;

import java.util.HashMap;
import java.util.Map;

import com.wondertek.meeting.vo.LiveService;
import com.wondertek.meeting.vo.LiveServiceInfo;

/**
 * 设计成单例模式类
 */
public class LiveServiceManager {
	private static LiveServiceManager instance;
	
	//会场服务队列容器，key-meetingId, value-liveService
	private Map<Long, LiveService> liveServiceMap = new HashMap<Long, LiveService>();  
	
	private LiveServiceManager() {
	}
	
	/**
	 * 返回RfidTagQueueManager的单例实例
	 */
	public synchronized static LiveServiceManager getInstance() {
		if(instance == null) {
			 instance = new LiveServiceManager();
		}
		return instance;
	}
	
	/**
	 * 放入队列尾
	 */
	public synchronized void push(Long meetingId, LiveServiceInfo serviceInfo) {
		LiveService liveService = this.liveServiceMap.get(meetingId);
		if(liveService == null) {
			liveService = new LiveService();
			this.liveServiceMap.put(meetingId, liveService);
		}
		
		liveService.push(serviceInfo); //放入队列尾
	}
	
	/**
	 * 从队列中取最近一个元素，如果队列空则返回null
	 */
	public synchronized LiveServiceInfo pop(Long meetingId) {
		LiveService liveService = this.liveServiceMap.get(meetingId);
		if(liveService == null || liveService.getliveServiceInfoCount() <= 0) {
			return null;
		}
		
		return liveService.pop();
	}
	
	/**
	 * 返回队列标签个数
	 */
	public synchronized int getliveServiceCount(Long meetingId) {
		LiveService liveService = this.liveServiceMap.get(meetingId);
		if(liveService == null) {
			return 0;
		}
		return liveService.getliveServiceInfoCount();
	}
	
	/**
	 * 根据meetingId返回对应的LiveService
	 */
	public synchronized LiveService getliveService(Long meetingId) {
		return this.liveServiceMap.get(meetingId);
	}
}
