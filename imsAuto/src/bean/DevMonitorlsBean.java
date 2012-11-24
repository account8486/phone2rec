package bean;

import java.io.Serializable;

public class DevMonitorlsBean implements Serializable
{
	private static final long serialVersionUID = 6033901697585144850L;
	private int orderno; //序号
	private String devname;  //设备名称
	private int id; //编号
	private String ch;  //属性
	private String value; //监控值
	private String Threshold_min;//最小阀值
	private String Threshold_max;//最大阀值 
	private String flag;//判断标志  1-告警，0-正常
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getOrderno() {
		return orderno;
	}
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
	public String getDevname() {
		return devname;
	}
	public void setDevname(String devname) {
		this.devname = devname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCh() {
		return ch;
	}
	public void setCh(String ch) {
		this.ch = ch;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getThreshold_max() {
		return Threshold_max;
	}
	public void setThreshold_max(String threshold_max) {
		Threshold_max = threshold_max;
	}
	public String getThreshold_min() {
		return Threshold_min;
	}
	public void setThreshold_min(String threshold_min) {
		Threshold_min = threshold_min;
	}		
}