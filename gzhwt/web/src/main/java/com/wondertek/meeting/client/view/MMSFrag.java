package com.wondertek.meeting.client.view;

import java.io.File;

public class MMSFrag {
	private File mmsImage;
	
	private String mmsImageFileName;
	
	private String mmsImageContentType;
	
	private String messageContent;
	public File getMmsImage() {
		return mmsImage;
	}
	public void setMmsImage(File mmsImage) {
		this.mmsImage = mmsImage;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMmsImageFileName() {
		return mmsImageFileName;
	}
	public void setMmsImageFileName(String mmsImageFileName) {
		this.mmsImageFileName = mmsImageFileName;
	}
	public String getMmsImageContentType() {
		return mmsImageContentType;
	}
	public void setMmsImageContentType(String mmsImageContentType) {
		this.mmsImageContentType = mmsImageContentType;
	}
	
	
}
