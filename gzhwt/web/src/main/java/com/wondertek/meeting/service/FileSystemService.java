/**
 * 
 */
package com.wondertek.meeting.service;


/**
 * @author rain
 * 
 */
public class FileSystemService {
	private String documentRoot;
	private String serverUrl;

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
}
