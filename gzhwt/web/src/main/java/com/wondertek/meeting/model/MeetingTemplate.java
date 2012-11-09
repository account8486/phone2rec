package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 会场模版信息
 * 
 * @author tangjun
 */
public class MeetingTemplate extends BaseObject {

	private static final long serialVersionUID = 81039L;
	
	/** 会场ID */
	private Long id;

	/** 会场名称 */
	private String name;

	/** 会场类型 */
	private int type;

	/** 创建时间 */
	private Date createTime;

	/** 修改时间 */
	private Date modifyTime;

	/** 会场图片 */
	private String pic;

	/** 会场描述 */
	private String description;

	/** 会场状态 */
	private Integer state;

	/** 会场数据 */
	private String data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
