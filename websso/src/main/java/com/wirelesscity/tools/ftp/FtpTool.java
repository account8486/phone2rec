package com.wirelesscity.tools.ftp;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.wirelesscity.common.StringUtil;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

@SuppressWarnings("restriction")
public class FtpTool {

	private FtpClient ftpclient;
	private String ipAddress; // FTP地址
	private int ipPort; // 端口
	private String userName; // 用户名
	private String PassWord; // 密码

	/**
	 * 初始化参数FTPTOOL对象
	 * @param ipAddress
	 * @param ipPort
	 * @param userName
	 * @param PassWord
	 * @throws IOException
	 */
	public FtpTool(String ip, int port, String username, String password)
			throws IOException {
		ipAddress = new String(ip);
		ipPort = port;
		if (StringUtil.isEmpty(String.valueOf(port))) {
			port = 21;
		}
		ftpclient = new FtpClient(ipAddress, ipPort);
		userName = new String(username);
		PassWord = new String(password);
	}

	/**
	 * 登录FTP服务器
	 * 
	 * @throws Exception
	 */
	public void login() throws Exception {
		ftpclient.login(userName, PassWord);
	}

	/**
	 * 退出FTP服务器
	 * 
	 * @throws Exception
	 */
	public void logout() throws Exception {
		// 用ftpclient.closeServer()断开FTP出错时用下更语句退出
		ftpclient.sendServer("QUIT\r\n");
		int reply = ftpclient.readServerResponse(); // 取得服务器的返回信息
		System.out.println("reply:" + reply);
	}

	/**
	 * 在FTP服务器上建立指定的目录,当目录已经存在的情下不会影响目录下的文件,这样用以判断FTP
	 * 上传文件时保证目录的存在目录格式必须以"/"根目录开头
	 * 
	 * @param pathList
	 *            String
	 * @throws Exception
	 */
	public void buildList(String pathList) throws Exception {
		ftpclient.ascii();
		StringTokenizer s = new StringTokenizer(pathList, "/"); // sign
		int count = s.countTokens();
		String pathName = "";
		while (s.hasMoreElements()) {
			pathName = pathName + "/" + (String) s.nextElement();
			try {
				ftpclient.sendServer("XMKD " + pathName + "\r\n");
			} catch (Exception e) {
				e = null;
			}
			int reply = ftpclient.readServerResponse();
		}
		ftpclient.binary();
	}

	/**
	 * 取得指定目录下的所有文件名，不包括目录名称 分析nameList得到的输入流中的数，得到指定目录下的所有文件名
	 * 
	 * @param fullPath
	 *            String
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList fileNames(String fullPath) throws Exception {
		ftpclient.ascii(); // 注意，使用字符模式
		TelnetInputStream list = ftpclient.nameList(fullPath);
		byte[] names = new byte[2048];
		int bufsize = 0;
		bufsize = list.read(names, 0, names.length); // 从流中读取
		list.close();
		ArrayList namesList = new ArrayList();
		int i = 0;
		int j = 0;
		while (i < bufsize) {
			if (names[i] == 10) { // 字符模式为10，二进制模式为13
				// 文件名在数据中开始下标为j,i-j为文件名的长度,文件名在数据中的结束下标为i-1
				String tempName = new String(names, j, i - j);
				namesList.add(tempName);
				j = i + 1; // 上一次位置字符模式
			}
			i = i + 1;
		}
		return namesList;
	}

	/**
	 * 上传文件到FTP服务器,destination路径以FTP服务器的"/"开始，带文件名、 上传文件只能使用二进制模式，当文件存在时再次上传则会覆盖
	 * 
	 * @param source
	 *            String
	 * @param destination
	 *            String
	 * @throws Exception
	 */
	public void upFile(String source, String destination) throws Exception {
		buildList(destination.substring(0, destination.lastIndexOf("/")));
		ftpclient.binary(); // 此行代码必须放在buildList之后
		TelnetOutputStream ftpOut = ftpclient.put(destination);
		TelnetInputStream ftpIn = new TelnetInputStream(new FileInputStream(
				source), true);
		byte[] buf = new byte[204800];
		int bufsize = 0;
		while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
			ftpOut.write(buf, 0, bufsize);
		}
		ftpIn.close();
		ftpOut.close();

	}

	/**
	 * JSP中的流上传到FTP服务器, 上传文件只能使用二进制模式，当文件存在时再次上传则会覆盖 字节数组做为文件的输入流,此方法适用于JSP中通过
	 * request输入流来直接上传文件在RequestUpload类中调用了此方法， destination路径以FTP服务器的"/"开始，带文件名
	 * 
	 * @param sourceData
	 *            byte[]
	 * @param destination
	 *            String
	 * @throws Exception
	 */
	public void upFile(byte[] sourceData, String destination) throws Exception {
		buildList(destination.substring(0, destination.lastIndexOf("/")));
		ftpclient.binary(); // 此行代码必须放在buildList之后
		TelnetOutputStream ftpOut = ftpclient.put(destination);
		ftpOut.write(sourceData, 0, sourceData.length);
		// ftpOut.flush();
		ftpOut.close();
	}

	/**
	 * 从FTP文件服务器上下载文件SourceFileName，到本地destinationFileName 所有的文件名中都要求包括完整的路径名在内
	 * 
	 * @param SourceFileName
	 *            String
	 * @param destinationFileName
	 *            String
	 * @throws Exception
	 */
	public void downFile(String SourceFileName, String destinationFileName)
			throws Exception {
		ftpclient.binary(); // 一定要使用二进制模式
		TelnetInputStream ftpIn = ftpclient.get(SourceFileName);
		byte[] buf = new byte[204800];
		int bufsize = 0;
		FileOutputStream ftpOut = new FileOutputStream(destinationFileName);
		while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
			ftpOut.write(buf, 0, bufsize);
		}
		ftpOut.close();
		ftpIn.close();
	}

	/**
	 * 从FTP文件服务器上下载文件，输出到字节数组中
	 * 
	 * @param SourceFileName
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] downFile(String SourceFileName) throws Exception {
		ftpclient.binary(); // 一定要使用二进制模式
		TelnetInputStream ftpIn = ftpclient.get(SourceFileName);
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		byte[] buf = new byte[204800];
		int bufsize = 0;

		while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
			byteOut.write(buf, 0, bufsize);
		}
		byte[] return_arraybyte = byteOut.toByteArray();
		byteOut.close();
		ftpIn.close();
		return return_arraybyte;
	}

}
