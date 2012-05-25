package com.wondertek.meeting.model;

/**浏览图片的文件夹*/
public class Folder extends BaseObject{
	
	private String path;
	private String name;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
