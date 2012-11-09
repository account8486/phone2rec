/**
 * RFID读写器感应到的电子标签队列管理类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-14
 */
package com.wondertek.meeting.service.rfid;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.vo.RfidTag;

/**
 * 设计成单例模式类
 */
public class RfidTagQueueManager {
	private static RfidTagQueueManager instance;
	private Map<Long, List<RfidTag>> rfidTagQueueMap = new HashMap<Long, List<RfidTag>>();  //标签队列容器
	
	private RfidTagQueueManager() {
		
	}
	
	/**
	 * 返回RfidTagQueueManager的单例实例
	 */
	public synchronized static RfidTagQueueManager getInstance() {
		if(instance == null) {
			 instance = new RfidTagQueueManager();
		}
		return instance;
	}
	
	/**
	 * 把标签tag放入队列尾
	 */
	public synchronized void push(Long meetingId, RfidTag tag) {
		List<RfidTag> rfidTagQueue = this.rfidTagQueueMap.get(meetingId);
		if(rfidTagQueue == null) {
			rfidTagQueue = new LinkedList<RfidTag>();
			this.rfidTagQueueMap.put(meetingId, rfidTagQueue);
		}
		rfidTagQueue.add(tag); //放入队列尾
	}
	
	/**
	 * 从队列中取最近一个元素，如果队列空则返回null
	 */
	public synchronized RfidTag pop(Long meetingId) {
		List<RfidTag> rfidTagQueue = this.rfidTagQueueMap.get(meetingId);
		if(rfidTagQueue == null || rfidTagQueue.size() <= 0) {
			return null;
		}
		RfidTag tag = rfidTagQueue.get(0);
		rfidTagQueue.remove(0);
		return tag;
	}
	
	/**
	 * 返回队列标签个数
	 */
	public int getRfidTagCount(Long meetingId) {
		List<RfidTag> rfidTagQueue = this.rfidTagQueueMap.get(meetingId);
		if(rfidTagQueue == null) {
			return 0;
		}
		return rfidTagQueue.size();
	}
}
