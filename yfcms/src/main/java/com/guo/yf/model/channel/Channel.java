package com.guo.yf.model.channel;
import java.util.Date;
import com.wondertek.meeting.model.BaseObject;

public class Channel extends BaseObject {

	private static final long serialVersionUID = 1673702930885017631L;
	private Long id;// 用户id")
	private String chanName;

	private String chanDescription;

	private Date createTime;// 创建时间

	private Long creator;

	private Date modifyTime;// 修改时间

	private Long modifier;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChanName() {
		return chanName;
	}

	public void setChanName(String chanName) {
		this.chanName = chanName;
	}

	public String getChanDescription() {
		return chanDescription;
	}
	public void setChanDescription(String chanDescription) {
		this.chanDescription = chanDescription;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

}
