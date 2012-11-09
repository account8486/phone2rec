/**
 * 
 */
package com.wondertek.meeting.action.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.DataDictConfig;
import com.wondertek.meeting.service.TagService;

/**
 * @author rain
 *
 */
public class SystemConfigAction  extends BaseAction {
	private static final long serialVersionUID = 1007518575787157736L;
	private static final int BUFFER_SIZE = 16 * 1024;
	private static final String CLIENT_LOGIN_BACKGROUND = "images/client/bg.png";
	private static final String CLIENT_LOGIN_BACKGROUND_SMALL = "images/client/bg_small.png";
	private static final String SMALL_IMAGE = "_small.png";
	
	@Autowired
	private TagService tagService;
	
	private static final String dataType = "client_login_bg_pic";
	private static final String dataType_custom = "custom_bg_pic";
	
	private static final String actType = "common";
	
	public String getClientLoginBackgroundImages() {
		final DataDictConfig queryDc=new DataDictConfig();
		queryDc.setDataType(dataType_custom);
		queryDc.setActType(actType);
		final List<DataDictConfig> dataDictConfigList=tagService.query(queryDc);
		if(dataDictConfigList!=null && dataDictConfigList.size()>0){
			getRequest().setAttribute("datas", dataDictConfigList);
			getRequest().setAttribute("serverUrl", getServerUrl());
		}
		return SUCCESS;		
	}
	
	public String saveClientLoginBackgroundImages() {
		final String dataValue = getParameter("dataValue");
		final String dataValue_s = StringUtils.substringBefore(dataValue, ".") + SMALL_IMAGE;
		final File selected = new File(getDocumentRoot() + dataValue);
		final File selected_s = new File(getDocumentRoot() + dataValue_s);
		final File background = new File(getDocumentRoot() + CLIENT_LOGIN_BACKGROUND); 
		final File background_s = new File(getDocumentRoot() + CLIENT_LOGIN_BACKGROUND_SMALL);
		//copy selected image as client background image
		if (selected.exists()) {
			copy(selected, background);
		}
		if (selected_s.exists()) {
			copy(selected_s, background_s);
		}
		
		try {
			final DataDictConfig queryDc=new DataDictConfig();
			queryDc.setDataType(dataType);
			queryDc.setActType(actType);
			final List<DataDictConfig> dataDictConfigList=tagService.query(queryDc);
			if(dataDictConfigList!=null && dataDictConfigList.size()>0){
				final DataDictConfig dc = dataDictConfigList.get(0);
				dc.setDataValue(dataValue);
				tagService.saveOrUpdate(dc);
			}
			resultMap.put("retcode", "0");
		} catch (ServiceException e) {
			e.printStackTrace();
			resultMap.put("retcode", "1");
		}
		return SUCCESS;
	}
	
	public String getBackgroundImage() {
		final DataDictConfig queryDc=new DataDictConfig();
		queryDc.setDataType(dataType);
		queryDc.setActType(actType);
		final List<DataDictConfig> dataDictConfigList=tagService.query(queryDc);
		if(dataDictConfigList!=null && dataDictConfigList.size()>0){
			final DataDictConfig dc = dataDictConfigList.get(0);
			resultMap.put("image", getServerUrl() + dc.getDataValue());
		} else {
			resultMap.put("image", getServerUrl() + CLIENT_LOGIN_BACKGROUND);
		}
		resultMap.put("retcode", "0");
		return SUCCESS;
	}
	
	private void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
