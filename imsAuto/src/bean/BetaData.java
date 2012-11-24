package bean;

import java.io.Serializable;

/**
 * @author Administrator
 * BETA数据集
 */
public class BetaData implements Serializable {

	private static final long serialVersionUID = 3404661298909595376L;

	private String resource; // 链路源
	private String destination; // 链路目的地
	private String alarmType; // 告警类型: normal、alarm
	private String messages; // 告警内容
	private int timeOrder; // 告警顺序
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public int getTimeOrder() {
		return timeOrder;
	}
	public void setTimeOrder(int timeOrder) {
		this.timeOrder = timeOrder;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
}
