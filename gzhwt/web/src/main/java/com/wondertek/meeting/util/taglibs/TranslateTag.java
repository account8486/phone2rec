package com.wondertek.meeting.util.taglibs;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.wondertek.meeting.common.ApplicationContextHelper;
import com.wondertek.meeting.model.DataDictConfig;
import com.wondertek.meeting.service.TagService;


public class TranslateTag extends TagSupport
{
	private static final String DEFAULT_ACT_TYPE="common";
	private static Logger log = Logger.getLogger(TranslateTag.class);
	private static final long serialVersionUID = 3408276515071928L;
	private String type=null;
	private String value = null;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int doStartTag() throws JspException
	{
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException
	{
		StringBuffer sb = new StringBuffer();
		try
		{	
			TagService tagService = (TagService) ApplicationContextHelper.getBean("tagService");
			DataDictConfig config = new DataDictConfig();
			config.setActType(DEFAULT_ACT_TYPE);
			config.setDataType(type);
			log.debug("config datatype is " +config.getDataType());
			List<DataDictConfig> list = tagService.query(config);

			if (list != null && list.size() > 0)
			{
				DataDictConfig option = null;
				for (int i = 0; i < list.size(); i++)
				{
					option = list.get(i);
					if (option.getDataCode().equals(value)){
						sb.append(option.getDataName());
						break;
					}
				}
			}
			if(sb.length()<=0){
				sb.append(value);
			}
			log.debug(sb);
		}
		catch (Exception e)
		{
			//throw new JspException("SelectTag error!",e);
			sb.delete(0, sb.length());
			sb.append("<span style=\"color:red; \">标签错误，请联系系统管理员！").append(e.toString()).append("</span>");

			log.error("标签生成失败，错误原因：{}",e);
		}

		JspWriter out = pageContext.getOut();
		try{
			out.print(sb.toString());
		}catch(IOException ex)
		{
			
		}
		return EVAL_PAGE;
	}
	


	public String getType() {
		return type;
	}

	public void setType(String type) {
		log.debug("type is ==========="+type);
		this.type = type;
	}
	
}