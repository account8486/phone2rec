/**
 * 
 */
package bean;

import java.io.Serializable;

/**
 * @author Administrator
 * 安全监管Bean
 */
public class SecuMonitorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8128709695029221181L;
	private int corpId;
	private String corpName;
	private String riskMax;  //风险最大值
	private String leakCount;  //漏洞总数
	private String leakStatus;  //漏洞状态
	private String fitFraTal;  //配置脆弱性
	private String savEvt;  //新接入安全事件
	private String savEvtRealTime;  // 新接入安全事件(实时)
	private int alarm;  //告警监管
	
	private int riskMax_sta;  //风险最大值
	private int leakCount_sta;  //漏洞总数
	private int leakStatus_sta;  //漏洞状态
	private int fitFraTal_sta;  //配置脆弱性
	private int savEvt_sta;  //新接入安全事件
	private int savEvtRealTime_sta;  // 新接入安全事件状态(实时)
	public int getRiskMax_sta() {
		return riskMax_sta;
	}
	public void setRiskMax_sta(int riskMax_sta) {
		this.riskMax_sta = riskMax_sta;
	}
	public int getLeakCount_sta() {
		return leakCount_sta;
	}
	public void setLeakCount_sta(int leakCount_sta) {
		this.leakCount_sta = leakCount_sta;
	}
	public int getLeakStatus_sta() {
		return leakStatus_sta;
	}
	public void setLeakStatus_sta(int leakStatus_sta) {
		this.leakStatus_sta = leakStatus_sta;
	}
	public int getFitFraTal_sta() {
		return fitFraTal_sta;
	}
	public void setFitFraTal_sta(int fitFraTal_sta) {
		this.fitFraTal_sta = fitFraTal_sta;
	}
	public int getSavEvt_sta() {
		return savEvt_sta;
	}
	public void setSavEvt_sta(int savEvt_sta) {
		this.savEvt_sta = savEvt_sta;
	}
	public int getCorpId() {
		return corpId;
	}
	public void setCorpId(int corpId) {
		this.corpId = corpId;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getRiskMax() {
		return riskMax;
	}
	public void setRiskMax(String riskMax) {
		this.riskMax = riskMax;
	}
	public String getLeakCount() {
		return leakCount;
	}
	public void setLeakCount(String leakCount) {
		this.leakCount = leakCount;
	}
	public String getLeakStatus() {
		return leakStatus;
	}
	public void setLeakStatus(String leakStatus) {
		this.leakStatus = leakStatus;
	}
	public String getFitFraTal() {
		return fitFraTal;
	}
	public void setFitFraTal(String fitFraTal) {
		this.fitFraTal = fitFraTal;
	}
	public String getSavEvt() {
		return savEvt;
	}
	public void setSavEvt(String savEvt) {
		this.savEvt = savEvt;
	}
	public int getAlarm() {
		return alarm;
	}
	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}
	public String getSavEvtRealTime() {
		return savEvtRealTime;
	}
	public void setSavEvtRealTime(String savEvtRealTime) {
		this.savEvtRealTime = savEvtRealTime;
	}
	public int getSavEvtRealTime_sta() {
		return savEvtRealTime_sta;
	}
	public void setSavEvtRealTime_sta(int savEvtRealTime_sta) {
		this.savEvtRealTime_sta = savEvtRealTime_sta;
	}
}
