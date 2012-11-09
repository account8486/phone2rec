package com.wondertek.meeting.job;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.model.MeetingFiles;
import com.wondertek.meeting.service.MeetingFilesService;
import com.wondertek.meeting.util.IPRequest;

/**
 * 用户
 * 
 * @author 金祝华
 */
public class FileConvertJob {

	private static ServletContext servletContext;

	private String documentRoot;
	private String serverUrl;
	private String serverIP;

	String upload_path = "upload";
	String shellcmd = "sh /wd/script/cc.sh ";
	MeetingFilesService meetingFilesService;
	public Logger log = LoggerFactory.getLogger(this.getClass());

	public void convert() {
		if (StringUtils.equals("127.0.0.1", serverIP)) {
			return;
		}
		// do not support windows
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().contains("windows")) {
			return;
		}
		if (!StringUtils.equals(IPRequest.getLocalAdress(), serverIP)) {
			return;
		}
		try {
			// String realpath = servletContext.getRealPath(path) +
			// File.separator;
			// /wd/htdocs/upload/
			String realpath = documentRoot + upload_path + File.separator;
			List<MeetingFiles> meetingFiles = null;

			meetingFiles = meetingFilesService.getUnconvertMeetingFilesList();

			for (MeetingFiles mfile : meetingFiles) {
				mfile.getFileSaveName();
				// 判断文件是否存在
				String dstPath = realpath + mfile.getFileSaveName();

				String cmd = shellcmd + dstPath + " " + realpath + mfile.getMeetingId() + " " + mfile.getId();
				System.out.println(cmd);

				File tempFile = new File(dstPath);
				if (!mfile.getFileSaveName().toLowerCase().matches(".*\\.(doc|pdf|xls|docx|xlsx|ppt|pptx)$")) {
					log.info(mfile.getFileSaveName() + " doese not support!");
					mfile.setState(8);
					meetingFilesService.modify(mfile);
					continue;
				}

				// 如果文件不存在的话
				if (!tempFile.exists()) {
					log.info(mfile.getFileSaveName() + " doese not exist!");
					mfile.setState(9);
					meetingFilesService.modify(mfile);
					continue;
				}
				Process process = Runtime.getRuntime().exec(cmd);
				InputStreamReader ir = new InputStreamReader(process.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);

				process.waitFor();
				String line = input.readLine();
				mfile.setState(1);
				mfile.setPrePage(Integer.parseInt(line));
				meetingFilesService.modify(mfile);
			}
		} catch (Exception e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
	}

	public MeetingFilesService getMeetingFilesService() {
		return meetingFilesService;
	}

	public void setMeetingFilesService(MeetingFilesService meetingFilesService) {
		this.meetingFilesService = meetingFilesService;
	}

	public static void setServletContext(ServletContext st) {
		servletContext = st;
	}

	/**
	 * @return the documentRoot
	 */
	public String getDocumentRoot() {
		return documentRoot;
	}

	/**
	 * @param documentRoot
	 *            the documentRoot to set
	 */
	public void setDocumentRoot(String documentRoot) {
		this.documentRoot = documentRoot;
	}

	/**
	 * @return the serverUrl
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * @param serverUrl
	 *            the serverUrl to set
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	/**
	 * @param serverIP
	 *            the serverIP to set
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
}
