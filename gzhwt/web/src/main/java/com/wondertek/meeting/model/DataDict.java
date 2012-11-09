package com.wondertek.meeting.model;

import java.util.List;

public class DataDict {

	private String dataType;
	private String dataTypeName;
	private String dataTypeDesc;
	private List<DataDictConfig> configList;
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataTypeName() {
		return dataTypeName;
	}
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
	public String getDataTypeDesc() {
		return dataTypeDesc;
	}
	public void setDataTypeDesc(String dataTypeDesc) {
		this.dataTypeDesc = dataTypeDesc;
	}
	public List<DataDictConfig> getConfigList() {
		return configList;
	}
	public void setConfigList(List<DataDictConfig> configList) {
		this.configList = configList;
	}
}
