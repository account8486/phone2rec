package com.wondertek.meeting.client.view;

/**
 * 客户端首页九宫格菜单
 * 
 * @author 金祝华
 */
public class MenuView {

	private Long id;

	private Integer type;

	// 菜单名称
	private String name;

	// 图标Url
	private String imgUrl;

	// 内容Url
	private String contentUrl;
	
	private String downloadUrl;// 下载url
	
	private String packageName;// 包名

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
}
