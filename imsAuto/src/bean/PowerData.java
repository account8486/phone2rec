/**
 * 
 */
package bean;

/**
 * @author Administrator
 *
 */
public class PowerData {

	private String burden_a;  // A相输出负载
	private String burden_b;  // B相输出负载
	private String burden_c;  // C相输出负载
	private String voltage_a;  // 输出相电压A
	private String voltage_b;  // 输出相电压B
	private String voltage_c;  // 输出相电压C
	private String voltage_pa;  // 旁路A相电压
	private String voltage_pb;  // 旁路B相电压
	private String voltage_pc;  // 旁路C相电压
	private String alarmInf = "";  // 告警信息
	public String getBurden_a() {
		return burden_a;
	}
	public void setBurden_a(String burden_a) {
		this.burden_a = burden_a;
	}
	public String getBurden_b() {
		return burden_b;
	}
	public void setBurden_b(String burden_b) {
		this.burden_b = burden_b;
	}
	public String getBurden_c() {
		return burden_c;
	}
	public void setBurden_c(String burden_c) {
		this.burden_c = burden_c;
	}
	public String getVoltage_a() {
		return voltage_a;
	}
	public void setVoltage_a(String voltage_a) {
		this.voltage_a = voltage_a;
	}
	public String getVoltage_b() {
		return voltage_b;
	}
	public void setVoltage_b(String voltage_b) {
		this.voltage_b = voltage_b;
	}
	public String getVoltage_c() {
		return voltage_c;
	}
	public void setVoltage_c(String voltage_c) {
		this.voltage_c = voltage_c;
	}
	public String getVoltage_pa() {
		return voltage_pa;
	}
	public void setVoltage_pa(String voltage_pa) {
		this.voltage_pa = voltage_pa;
	}
	public String getVoltage_pb() {
		return voltage_pb;
	}
	public void setVoltage_pb(String voltage_pb) {
		this.voltage_pb = voltage_pb;
	}
	public String getVoltage_pc() {
		return voltage_pc;
	}
	public void setVoltage_pc(String voltage_pc) {
		this.voltage_pc = voltage_pc;
	}
	public String getAlarmInf() {
		return alarmInf;
	}
	public void setAlarmInf(String alarmInf) {
		this.alarmInf = alarmInf;
	}
}
