/**
 * 会场服务POJO
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-4-23
 */

package com.wondertek.meeting.vo;

import com.wondertek.meeting.model.BaseObject;
import com.wondertek.meeting.model.User;

public class LiveServiceInfo extends BaseObject {
	private static final long serialVersionUID = 1L;

	private int id;
	private String serviceName; // 服务名称
	private User requester; // 服务请求人
	private String requestTime; // 服务请求时间


	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
