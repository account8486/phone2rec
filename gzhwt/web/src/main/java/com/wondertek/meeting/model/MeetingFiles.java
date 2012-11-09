package com.wondertek.meeting.model;

import java.util.Date;

public class MeetingFiles extends BaseObject {
	
	private static final long serialVersionUID = 6316036233924879213L;
	private Long id;//文件主键ID
	private Integer meetingId;
	private Integer fileType;
	private String filePath;
	private String fileAuthor;
	private String fileTag;
	private Double fileSize;
	private Date createTime;
	private Date modifyTime;
    private String creator;
    private String modifier;
    private String comments;
    private Integer  state;
    private String fileSaveName;//文件保存名称
	private String fileName;
	private String displayedFileName; //如果文件名过长，则用来保存进行换行处理后的文件名
	private String filePostfix;
    private Integer prePage = 0;
    private Integer assortId;//记录分类ID
    private String assortName;//分类名称 
    private Integer sortCode;//文件列表排序码
    private String fileAccess;//文件权限
    private Integer downloadFlg; // 是否支持下载（0：不支持，1：支持）
    private Integer previewFlg; // 是否支持预览（0：不支持，1：支持）
    
    private String fileCoverPath;// 文件封面地址
    
	public String getFileCoverPath() {
		return fileCoverPath;
	}
	public void setFileCoverPath(String fileCoverPath) {
		this.fileCoverPath = fileCoverPath;
	}
	public String getFileAccess() {
		return fileAccess;
	}
	public void setFileAccess(String fileAccess) {
		this.fileAccess = fileAccess;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public String getAssortName() {
		return assortName;
	}
	public void setAssortName(String assortName) {
		this.assortName = assortName;
	}
	public Integer getAssortId() {
		return assortId;
	}
	public void setAssortId(Integer assortId) {
		this.assortId = assortId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePostfix() {
		return filePostfix;
	}
	public void setFilePostfix(String filePostfix) {
		this.filePostfix = filePostfix;
	}

    
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}
	public Integer getFileType() {
		return fileType;
	}
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getFileAuthor() {
		return fileAuthor;
	}
	public void setFileAuthor(String fileAuthor) {
		this.fileAuthor = fileAuthor;
	}
	public String getFileTag() {
		return fileTag;
	}
	public void setFileTag(String fileTag) {
		this.fileTag = fileTag;
	}
	public Double getFileSize() {
		return fileSize;
	}
	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFileSaveName() {
		return fileSaveName;
	}
	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }

	public Integer getDownloadFlg() {
		return downloadFlg;
	}

	public void setDownloadFlg(Integer downloadFlg) {
		this.downloadFlg = downloadFlg;
	}

	public Integer getPreviewFlg() {
		return previewFlg;
	}

	public void setPreviewFlg(Integer previewFlg) {
		this.previewFlg = previewFlg;
	}
	public String getDisplayedFileName() {
		return displayedFileName;
	}
	public void setDisplayedFileName(String displayedFileName) {
		this.displayedFileName = displayedFileName;
	}
}
