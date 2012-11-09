package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 资产信息
 * @author zqwu
 *
 */
public class Asset extends BaseObject {

	private static final long serialVersionUID = 7647560509461573283L;
	private Long id;
	private String asset_no;
	private String asset_name;
	private String property;
	private Double asset_value;
	private Date storage_date;
	private String remark;
	private Long meetingId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAsset_no() {
		return asset_no;
	}
	public void setAsset_no(String asset_no) {
		this.asset_no = asset_no;
	}
	public String getAsset_name() {
		return asset_name;
	}
	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Double getAsset_value() {
		return asset_value;
	}
	public void setAsset_value(Double asset_value) {
		this.asset_value = asset_value;
	}
	public Date getStorage_date() {
		return storage_date;
	}
	public void setStorage_date(Date storage_date) {
		this.storage_date = storage_date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	
	
}
