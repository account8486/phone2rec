package com.wondertek.meeting.model.reception;

import java.util.Date;

import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.BaseObject;

/**
 * 酒店图片
 * 
 * @author 金祝华
 */
public class HotelImage extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5640463957063345446L;

	private Long id;

	private String title;// 标题

	private Integer sort; // 顺序

	private String address; // 地址

	private Date uploadTime;// 上传时间

	private AdminUser uploadUser;// 上传人

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public AdminUser getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(AdminUser uploadUser) {
		this.uploadUser = uploadUser;
	}
}
