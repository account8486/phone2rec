package com.wirelesscity.tools.ftp;

import java.io.IOException;

public class FtpServiceImpl implements FtpService{
	
	private String ftpIpAddress;
	private int ftpPort;
	private String ftpUserName;
	private String ftpPassword;
	
	//FTP路径配置
	private String ftpLocalPathRoot;
	private String ftpRemotePathRoot;
	
	public String getFtpLocalPathRoot() {
		return ftpLocalPathRoot;
	}

	public void setFtpLocalPathRoot(String ftpLocalPathRoot) {
		this.ftpLocalPathRoot = ftpLocalPathRoot;
	}

	public String getFtpRemotePathRoot() {
		return ftpRemotePathRoot;
	}

	public void setFtpRemotePathRoot(String ftpRemotePathRoot) {
		this.ftpRemotePathRoot = ftpRemotePathRoot;
	}

	public void setFtpIpAddress(String ftpIpAddress) {
		this.ftpIpAddress = ftpIpAddress;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	
	public String getFtpIpAddress() {
		return ftpIpAddress;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}
	
	public void testUpload(){
		// TODO Auto-generated method stub
		try {
			FtpTool ftpTool = new FtpTool(this.getFtpIpAddress(),
					this.getFtpPort(), this.getFtpUserName(),
					this.getFtpPassword());
			ftpTool.login();
			ftpTool.buildList("file/user/tttt");

			String source = "D:/config.rar";
			String destination = "/file/user/tttt/test.jar";
			ftpTool.upFile(source, destination);
			ftpTool.logout();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 建立文件夹路径
	 * @param pathList
	 * @throws Exception
	 */
	public void buildList(String pathList) throws Exception{
		FtpTool ftpTool = new FtpTool(this.getFtpIpAddress(),
				this.getFtpPort(), this.getFtpUserName(),
				this.getFtpPassword());
		ftpTool.login();
		ftpTool.buildList(pathList);
		ftpTool.logout();
	}
	
	
	/**
	 * 上传文件到FTP服务器,
	 * destination路径以FTP服务器的"/"开始，
	 * 带文件名、 上传文件只能使用二进制模式，当文件存在时再次上传则会覆盖
	 * @param source
	 *            String
	 * @param destination
	 *            String
	 * @throws Exception
	 */
	public void upFile(String source, String destination) throws Exception{
		FtpTool ftpTool = new FtpTool(this.getFtpIpAddress(),
				this.getFtpPort(), this.getFtpUserName(),
				this.getFtpPassword());
		ftpTool.login();
		ftpTool.upFile(source, destination);
		ftpTool.logout();
	}
	
	
	/**
	 * JSP中的流上传到FTP服务器, 上传文件只能使用二进制模式，当文件存在时再次上传则会覆盖 字节数组做为文件的输入流,此
	 * 方法适用于JSP中通过
	 * request输入流来直接上传文件在RequestUpload类中调用了此方法， 
	 * destination路径以FTP服务器的"/"开始，带文件名
	 * @param sourceData
	 *            byte[]
	 * @param destination
	 *            String
	 * @throws Exception
	 */
	public void upFile(byte[] sourceData, String destination) throws Exception {
		FtpTool ftpTool = new FtpTool(this.getFtpIpAddress(),
				this.getFtpPort(), this.getFtpUserName(),
				this.getFtpPassword());
		ftpTool.login();
		ftpTool.upFile(sourceData, destination);
		ftpTool.logout();
	}
	
	
	
	
	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FtpServiceImpl ftpUtil=new FtpServiceImpl();
		ftpUtil.testUpload();

	}

}
