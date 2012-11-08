package com.wirelesscity.tools.ftp;

public interface FtpService{
	
	public String getFtpLocalPathRoot() ;
	
	public String getFtpRemotePathRoot();
	
	public void testUpload();
	
	/**
	 * 建立文件夹路径
	 * @param pathList
	 * @throws Exception
	 */
	public void buildList(String pathList) throws Exception;
	
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
	public void upFile(String source, String destination) throws Exception;
	
	
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
	public void upFile(byte[] sourceData, String destination) throws Exception ;
	
	

}
