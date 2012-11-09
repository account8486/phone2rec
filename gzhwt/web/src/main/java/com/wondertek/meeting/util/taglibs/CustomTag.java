package com.wondertek.meeting.util.taglibs;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.wondertek.meeting.common.ApplicationContextHelper;
import com.wondertek.meeting.model.DataDictConfig;
import com.wondertek.meeting.model.TagEntity;
import com.wondertek.meeting.service.TagService;

public class CustomTag extends TagSupport {
	private static Logger log = Logger.getLogger(CustomTag.class);
	private static final long serialVersionUID = 3408276515071928L;
	private String type = null;
	private String entity = null;
	private String entityValue = null;
	private String entityName = null;
	private String condition = null;
	private String selectedValue = null;
	private String selectedName = null;
	private String id = null;
	private String name = null;
	private String onchange = null;
	private String onclick = null;
	private String disabled = null;
	private String style = null;
	private String nullLabel = null;
	private boolean nullOption = false;
	private boolean multiple = false;

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		try {
			TagService tagService = (TagService) ApplicationContextHelper.getBean("tagService");
			TagEntity tagEntity = new TagEntity();
			tagEntity.setEntity(entity);
			tagEntity.setEntityName(entityName);
			tagEntity.setEntityValue(entityValue);
			tagEntity.setCondition(condition);
			List<DataDictConfig> list = tagService.query(tagEntity);
			if("select".equals(type)){
				sb = buildSelect(list);				
			}else if("radio".equals(type)){
				sb = buildRadio(list);				
			}else if("checkbox".equals(type)){
				sb = buildCheckBox(list);				
			}else if("translate".equals(type)){
				sb = translate(list);				
			}else{
				sb.append("<span style='color:red; '>标签type属性错误，必须为select、radio或checkbox！</span>");
			}
			log.debug(sb);
		} catch (Exception e) {
			sb.delete(0, sb.length());
			sb.append("<span style=\"color:red; \">标签错误，请联系系统管理员！").append(e.toString()).append("</span>");
			log.error("标签生成失败，错误原因：{}", e);
		}

		JspWriter out = pageContext.getOut();
		try {
			out.print(sb.toString());
		} catch (IOException ex) {
			log.error(ex);
		}
		return EVAL_PAGE;
	}

	public String addProperty() {
		StringBuffer sb = new StringBuffer();
		if (id != null && id.trim().length() > 0) {
			sb.append(" id=\"").append(id).append("\"");
		}
		if (name != null && name.trim().length() > 0) {
			sb.append(" name=\"").append(name).append("\"");
		}
		if (disabled != null && disabled.trim().length() > 0)
			sb.append(" disabled=\"").append(disabled).append("\"");
		if (onchange != null && onchange.trim().length() > 0)
			sb.append(" onchange=\"").append(onchange).append("\"");
		if (style != null && style.trim().length() > 0)
			sb.append(" style=\"").append(style).append("\"");
		if (onclick != null && onclick.trim().length() > 0)
			sb.append(" onclick=\"").append(onclick).append("\"");
		if (multiple)
			sb.append(" multiple=\"").append(multiple).append("\"");
		return sb.toString();
	}

	public String addExtOption() {
		StringBuffer sb = new StringBuffer();
		if (nullOption) {
			sb.append("<option value=\"\">");
			if (nullLabel != null && nullLabel.trim().length() > 0) {
				sb.append(nullLabel);
			}
			sb.append("</option>");
		}
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getSelectedName() {
		return selectedName;
	}

	public void setSelectedName(String selectedName) {
		this.selectedName = selectedName;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public boolean isNullOption() {
		return nullOption;
	}

	public void setNullOption(boolean nullOption) {
		this.nullOption = nullOption;
	}

	public String getNullLabel() {
		return nullLabel;
	}

	public void setNullLabel(String nullLabel) {
		this.nullLabel = nullLabel;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
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


	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getEntityValue() {
		return entityValue;
	}

	public void setEntityValue(String entityValue) {
		this.entityValue = entityValue;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * 生成select标签
	 * @param list
	 * @return
	 */
	private StringBuffer buildSelect(List<DataDictConfig> list){
		StringBuffer sb = new StringBuffer();
		sb.append("<select" + addProperty() + ">").append(addExtOption());
		if (list != null && list.size() > 0) {
			DataDictConfig option = null;
			for (int i = 0; i < list.size(); i++) {
				option = list.get(i);
				if (selectedValue != null && selectedValue.trim().length() > 0) {
					if (option.getDataCode().equals(selectedValue))
						sb.append("<option extVal='" + option.getDataValue() + "' value='" + option.getDataCode()
								+ "' selected>" + option.getDataName() + "</option>");
					else {
						sb.append("<option extVal='" + option.getDataValue() + "' value='" + option.getDataCode()
								+ "'>" + option.getDataName() + "</option>");
					}
				} else {
					sb.append("<option extVal='" + option.getDataValue() + "' value='" + option.getDataCode()
							+ "'>" + option.getDataName() + "</option>");
				}
			}
		}
		sb.append("</select>");
		return sb;
	}

	/**
	 * 生成radio box标签
	 * @param list
	 * @return
	 */
	private StringBuffer buildRadio(List<DataDictConfig> list){
		StringBuffer sb = new StringBuffer();

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
						sb.append("<input type='radio' extVal='"+option.getDataValue()+"' "+addProperty()+" name='" + name + "' value='" + option.getDataCode() + "' checked='checked'>" +option.getDataName()+"</input>");
					else{
						sb.append("<input type='radio' extVal='"+option.getDataValue()+"' "+addProperty()+" name='" + name + "' value='" + option.getDataCode() + "' >" +option.getDataName()+"</input>");
					}
				}else{
					sb.append("<input type='radio' extVal='"+option.getDataValue()+"' "+addProperty()+" name='" + name + "' value='" + option.getDataCode() + "' >" +option.getDataName()+"</input>");
				}
			}
		}
		return sb;
	}

	/**
	 * 生成checkbox标签
	 * @param list
	 * @return
	 */
	private StringBuffer buildCheckBox(List<DataDictConfig> list){
		StringBuffer sb = new StringBuffer();

		if (list != null && list.size() > 0)
		{
			DataDictConfig option = null;
			for (int i = 0; i < list.size(); i++)
			{
				option = list.get(i);
				log.debug("option is {}"+option);
				if (selectedValue != null && selectedValue.trim().length() > 0)
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
		return sb;
	}

	/**
	 * 对指定代码字段进行翻译
	 * @param list
	 * @return
	 */
	private StringBuffer translate(List<DataDictConfig> list){
		StringBuffer sb = new StringBuffer();


		if (list != null && list.size() > 0)
		{
			DataDictConfig option = null;
			for (int i = 0; i < list.size(); i++)
			{
				option = list.get(i);
				if (option.getDataCode().equals(selectedValue)){
					sb.append(option.getDataName());
					break;
				}
			}
		}
		if(sb.length()<=0){
			sb.append(selectedValue);
		}
		
		return sb;
	}
	
	private boolean isChecked(String val)
	{
		log.debug("checkedValue is :["+selectedValue+"] -----Val is"+val);
		String[] checkedValues =  selectedValue.split(",");
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