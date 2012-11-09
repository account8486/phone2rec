package com.wondertek.meeting.model;

/**
 * 会议地点
 * 
 * @author 金祝华
 */
public class MeetingArea extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6009426363400676908L;
	private String code;// 编码
	private String name;// 名称
	private String parent;// 父级编码

	public MeetingArea() {
	}

	public MeetingArea(String c, String n) {
		this.code = c;
		this.name = n;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
}
