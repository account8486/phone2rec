package com.wondertek.meeting.action.plugin;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;

public class JxlsResult extends StrutsResultSupport {
	public Logger _logger = LoggerFactory.getLogger(this.getClass());
    private String fileName;

    public JxlsResult() {
        super();
    }

    public JxlsResult(String location) {
        super(location);
    }

    protected void doExecute(String location, ActionInvocation invocation) {
    	String method = "exportPlugin";
        try {
        	_logger.debug(method,"Begin export file");
        	HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
			InputStream template = this.getClass().getClassLoader().getResourceAsStream(location);
			
//			Map beans = (Map)request.getAttribute("beans"); //直接取map对象
		    Map beans = prepareBeans(invocation.getAction()); //通过反射取参数值
			
			String fileName = getFileName();
			_logger.debug(method+" beans = "+beans);
			_logger.debug(method+" fileName = "+fileName);
			_logger.debug(method+" template location = "+location);
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			_logger.debug(method,"transform workbook");
			XLSTransformer transformer = new XLSTransformer();
			Workbook workbook;
			workbook = transformer.transformXLS(template, beans);
//			org.apache.poi.ss.usermodel.Workbook workbook = new XLSTransformer().transformXLS(template, beans);
			_logger.debug(method,"transform workbook end");
			workbook.write(out);

			byte[] result = out.toByteArray();

			fileName = fileName == null ? invocation.getProxy().getActionName() : conditionalParse(fileName, invocation);

			response.setContentType("application/vnd.ms-excel");
			response.setContentLength(result.length);
			response.setHeader("charset", "utf-8");
			
			String agent = request.getHeader("USER-AGENT");  
	        
			//IE6下载文件名超过17个汉字的时候就有截断的问题
			if(null != agent && -1 != agent.indexOf("MSIE")){  
	        	fileName = URLEncoder.encode(fileName,"UTF-8");
	        	if (fileName.length() > 150) {
					String guessCharset = "gb2312";
		//			根据request的locale 得出可能的编码，中文操作系统通常是gb2312
					fileName = new String(fileName.getBytes(guessCharset), "ISO8859-1");
				}
	        }else if(null != agent && -1 != agent.indexOf("Firefox")){  
	        	fileName = MimeUtility.encodeText(fileName,"UTF-8","B");  
	        } 
			
			_logger.debug(method+" fileName = "+fileName);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.getOutputStream().write(result);
		} catch (Exception e) {
			_logger.error(method+" export file error: ",e);
		}
		_logger.debug(method+" end export file");
    }


    private String getFileName() {
        return new StringBuffer()
                .append(fileName == null ? "report" : fileName)
                .append(".xls")
                .toString();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    private Map prepareBeans(Object rootObj) throws Exception {
        HashMap<Object, Object> beans = new HashMap<Object, Object>();

        if (rootObj != null) {
            PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(rootObj);

            for (PropertyDescriptor property : properties) {
            	try {
	                String propertyName = property.getName();
	                Object propertyValue;
						propertyValue = PropertyUtils.getProperty(rootObj, propertyName);
	
	                if (propertyValue != null) {
	                    beans.put(propertyName, propertyValue);
	                }
            	} catch (Exception e) {
            		_logger.error("prepareBeans error ",e);
            		continue;
            	}
            }
        }

        return beans;
    }

}