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


public class CheckBoxTag extends TagSupport
{
	private static final String DEFAULT_ACT_TYPE="common";
	private static Logger log = Logger.getLogger(CheckBoxTag.class);
	private static final long serialVersionUID = 3408276515071928L;
	private String type=null;
	private String condition = null;
	private String checkedValue = null;
	private String checkedName = null;
	private String id = null;
	private String name = null;
	private String onclick = null;
	private String disabled = null;
	private String style = null;
	
	public int doStartTag() throws JspException
	{
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			
			//HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			TagService tagService = (TagService) ApplicationContextHelper.getBean("tagService");
			DataDictConfig config = new DataDictConfig();
			config.setActType(DEFAULT_ACT_TYPE);
			config.setDataType(type);
			config.setCondition(condition);
			//config.setOrderBy(orderBy);
			log.debug("config acttype is " +config.getActType());
			log.debug("config datatype is " +config.getDataType());
			List<DataDictConfig> list = tagService.query(config);

			if (list != null && list.size() > 0)
			{
				DataDictConfig option = null;
				for (int i = 0; i < list.size(); i++)
				{
					option = list.get(i);
					log.debug("option is {}"+option);
					if (checkedValue != null && checkedValue.trim().length() > 0)
					{
						if (isChecked(option.getDataCode()))
							sb.append(getTag(option.getDataCode(),option.getDataName(),"checked"));
						else{
							sb.append(getTag(option.getDataCode(),option.getDataName(),""));
						}
					}else{
						sb.append(getTag(option.getDataCode(),option.getDataName(),""));
					}
				}
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

	public String addProperty()
	{
		StringBuffer sb = new StringBuffer();
		if (disabled != null && disabled.trim().length() > 0)
			sb.append(" disabled=\"").append(disabled).append("\"");
		if (style != null && style.trim().length() > 0)
			sb.append(" style=\"").append(style).append("\"");
		if (onclick != null && onclick.trim().length() > 0)
			sb.append(" onclick=\"").append(onclick).append("\"");
		return sb.toString();
	}
	

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}


	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}
	
	public String getOnclick(){
		return onclick;
	}
	
	public void setOnclick(String onclick){
		this.onclick = onclick;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCheckedValue() {
		return checkedValue;
	}

	public void setCheckedValue(String checkedValue) {
		this.checkedValue = checkedValue;
	}

	public String getCheckedName() {
		return checkedName;
	}

	public void setCheckedName(String checkedName) {
		this.checkedName = checkedName;
	}

	private boolean isChecked(String val)
	{
		log.debug("checkedValue is :["+checkedValue+"] -----Val is"+val);
		String[] checkedValues =  checkedValue.split(",");
		for(int j=0;j<checkedValues.length;j++)
		{
			if(val.equals(checkedValues[j]))
				return true;
		}
		return false;
	}
	
	private String getTag(String tid,String tname,String checked)
	{
		String t = "<input "+addProperty()+" type='checkbox' value='" + tid + "' "+checked+">" 
					+tname;
		return t;
	}
	
}