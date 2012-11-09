/**
 * 保存用于感应到的RFID电子标签信息
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-14
 */
package com.wondertek.meeting.vo;

import java.util.Date;

import com.wondertek.meeting.model.BaseObject;

public class RfidTag extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private String uid; //标签UID
	private Date scanTime; //感应扫描时间

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
}
