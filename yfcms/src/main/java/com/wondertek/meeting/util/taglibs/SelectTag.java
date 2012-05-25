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


public class SelectTag extends TagSupport
{
	private static final String DEFAULT_ACT_TYPE="common";
	private static Logger log = Logger.getLogger(SelectTag.class);
	private static final long serialVersionUID = 3408276515071928L;
	private String type=null;
	private String condition = null;
	private String selectedValue = null;
	private String selectedName = null;
	private String id = null;
	private String name = null;
	private String onchange = null;
	private String onclick = null;
	private String disabled = null;
	private String size = null;
	private String style = null;
	private String nullLabel = null;
	private boolean nullOption = false;
	private boolean multiple = false;
	
	public int doStartTag() throws JspException
	{
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append("<select"+addProperty()+">").append(addExtOption());
			
			//HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			TagService tagService = (TagService) ApplicationContextHelper.getBean("tagService");
			DataDictConfig config = new DataDictConfig();
			config.setActType(DEFAULT_ACT_TYPE);
			config.setDataType(type);
			config.setCondition(condition);
			//config.setOrderBy(orderBy);
			log.debug("config acttype is " +config.getActType());
			log.debug("config datatype is " +config.getDataType());
			log.debug("config condition is " +config.getCondition());
			List<DataDictConfig> list = tagService.query(config);

			if (list != null && list.size() > 0)
			{
				DataDictConfig option = null;
				for (int i = 0; i < list.size(); i++)
				{
					option = list.get(i);
					log.debug("option is {}"+option);
					if (selectedValue != null && selectedValue.trim().length() > 0)
					{
						if (option.getDataCode().equals(selectedValue))
							sb.append("<option extVal='"+option.getDataValue()+"' value='"+option.getDataCode()+"' selected>"+option.getDataName()+"</option>");
						else{
							sb.append("<option extVal='"+option.getDataValue()+"' value='"+option.getDataCode()+"'>"+option.getDataName()+"</option>");
						}
					}else{
						sb.append("<option extVal='"+option.getDataValue()+"' value='"+option.getDataCode()+"'>"+option.getDataName()+"</option>");
					}
				}
			}
			sb.append("</select>");
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
		if (id != null && id.trim().length() > 0){
			sb.append(" id=\"").append(id).append("\"");
		}
		if (name != null && name.trim().length() > 0){
			sb.append(" name=\"").append(name).append("\"");
		}
		if (disabled != null && disabled.trim().length() > 0)
			sb.append(" disabled=\"").append(disabled).append("\"");
		if (onchange != null && onchange.trim().length() > 0)
			sb.append(" onchange=\"").append(onchange).append("\"");
		if (size != null && size.trim().length() > 0)
			sb.append(" size=\"").append(size).append("\"");
		if (style != null && style.trim().length() > 0)
			sb.append(" style=\"").append(style).append("\"");
		if (onclick != null && onclick.trim().length() > 0)
			sb.append(" onclick=\"").append(onclick).append("\"");
		if (multiple)
			sb.append(" multiple=\"").append(multiple).append("\"");
		return sb.toString();
	}
	
	public String addExtOption(){
		StringBuffer sb = new StringBuffer();
		if(nullOption){
			sb.append("<option value=\"\">");
			if(nullLabel != null && nullLabel.trim().length() > 0){
				sb.append(nullLabel);
			}
			sb.append("</option>");
		}
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


	public String getSelectedValue()
	{
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue)
	{
		this.selectedValue = selectedValue;
	}

	public String getOnchange()
	{
		return onchange;
	}

	public void setOnchange(String onchange)
	{
		this.onchange = onchange;
	}

	public String getSelectedName()
	{
		return selectedName;
	}

	public void setSelectedName(String selectedName)
	{
		this.selectedName = selectedName;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public boolean isMultiple()
	{
		return multiple;
	}

	public void setMultiple(boolean multiple)
	{
		this.multiple = multiple;
	}

	public String getSize()
	{
		return size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}
	
	public boolean isNullOption()
	{
		return nullOption;
	}
	
	public void setNullOption(boolean nullOption)
	{
		this.nullOption = nullOption;
	}
	
	public String getNullLabel()
	{
		return nullLabel;
	}
	
	public void setNullLabel(String nullLabel)
	{
		this.nullLabel = nullLabel;
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
		log.debug("type is ==========="+type);
		this.type = type;
	}
	
}