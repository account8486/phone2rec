/**
 * 
 */
package bean;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class NetMonitorBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -459172955717339844L;
	private int deptId;
	private String deptName;
	private String daikuan;  //网络带宽利用率
	private String diubao;  //网络丢包率
	private String delay_max;  //网络延时峰值
	private String delay_avg;  //网络延时均值
	private String link; // 链路连通性
	private int daikuan_sta;  //网络带宽利用率状态
	private int diubao_sta;  //网络丢包率状态
	private int delay_max_sta;  //网络延时峰值状态
	private int delay_avg_sta;  //网络延时均值状态
	private int link_sta;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getLink_sta() {
		return link_sta;
	}
	public void setLink_sta(int link_sta) {
		this.link_sta = link_sta;
	}
	public int getDaikuan_sta() {
		return daikuan_sta;
	}
	public void setDaikuan_sta(int daikuan_sta) {
		this.daikuan_sta = daikuan_sta;
	}
	public int getDiubao_sta() {
		return diubao_sta;
	}
	public void setDiubao_sta(int diubao_sta) {
		this.diubao_sta = diubao_sta;
	}
	public int getDelay_max_sta() {
		return delay_max_sta;
	}
	public void setDelay_max_sta(int delay_max_sta) {
		this.delay_max_sta = delay_max_sta;
	}
	public int getDelay_avg_sta() {
		return delay_avg_sta;
	}
	public void setDelay_avg_sta(int delay_avg_sta) {
		this.delay_avg_sta = delay_avg_sta;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDaikuan() {
		return daikuan;
	}
	public void setDaikuan(String daikuan) {
		this.daikuan = daikuan;
	}
	public String getDiubao() {
		return diubao;
	}
	public void setDiubao(String diubao) {
		this.diubao = diubao;
	}
	public String getDelay_max() {
		return delay_max;
	}
	public void setDelay_max(String delay_max) {
		this.delay_max = delay_max;
	}
	public String getDelay_avg() {
		return delay_avg;
	}
	public void setDelay_avg(String delay_avg) {
		this.delay_avg = delay_avg;
	}
	
}
