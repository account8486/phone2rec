/**
 * 
 */
package bean;

import java.io.Serializable;

/**
 * @author Administrator
 * 应用监管Bean
 */
public class AppMonitorBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6033901697585144850L;
	private String appId; //业务系统ID
	private String appNo;  //业务系统编号
	private String appName;  //业务系统名称
	private String appHealth;  //系统健康运行时间
	private int appHealth_sta;  //系统健康运行状态
	private String onlineCount;  //在线用户数
	private String onlineCountH;  //在线用户历史峰值
	private String dayLogNum;  //日登录人数
	private String dayLogNumH;  //日登录用户历史峰值
	private String regCount;  //注册用户总数
	private String zhuanye;  //专业指标
	private String access; // 系统访问状态
	
	private int onlineCount_sta;
	private int onlineCountH_sta;
	private int dayLogNum_sta;
	private int dayLogNumH_sta;
	private int regCount_sta;
	private int zhuanye_sta;
	private int access_sta;
	
	public String getZhuanye() {
		return zhuanye;
	}
	public void setZhuanye(String zhuanye) {
		this.zhuanye = zhuanye;
	}
	public int getZhuanye_sta() {
		return zhuanye_sta;
	}
	public void setZhuanye_sta(int zhuanye_sta) {
		this.zhuanye_sta = zhuanye_sta;
	}
	public String getOnlineCount() {
		return onlineCount;
	}
	public void setOnlineCount(String onlineCount) {
		this.onlineCount = onlineCount;
	}
	public String getOnlineCountH() {
		return onlineCountH;
	}
	public void setOnlineCountH(String onlineCountH) {
		this.onlineCountH = onlineCountH;
	}
	public String getDayLogNum() {
		return dayLogNum;
	}
	public void setDayLogNum(String dayLogNum) {
		this.dayLogNum = dayLogNum;
	}
	public String getDayLogNumH() {
		return dayLogNumH;
	}
	public void setDayLogNumH(String dayLogNumH) {
		this.dayLogNumH = dayLogNumH;
	}
	public String getRegCount() {
		return regCount;
	}
	public void setRegCount(String regCount) {
		this.regCount = regCount;
	}
	public int getOnlineCount_sta() {
		return onlineCount_sta;
	}
	public void setOnlineCount_sta(int onlineCount_sta) {
		this.onlineCount_sta = onlineCount_sta;
	}
	public int getOnlineCountH_sta() {
		return onlineCountH_sta;
	}
	public void setOnlineCountH_sta(int onlineCountH_sta) {
		this.onlineCountH_sta = onlineCountH_sta;
	}
	public int getDayLogNum_sta() {
		return dayLogNum_sta;
	}
	public void setDayLogNum_sta(int dayLogNum_sta) {
		this.dayLogNum_sta = dayLogNum_sta;
	}
	public int getDayLogNumH_sta() {
		return dayLogNumH_sta;
	}
	public void setDayLogNumH_sta(int dayLogNumH_sta) {
		this.dayLogNumH_sta = dayLogNumH_sta;
	}
	public int getRegCount_sta() {
		return regCount_sta;
	}
	public void setRegCount_sta(int regCount_sta) {
		this.regCount_sta = regCount_sta;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppHealth() {
		return appHealth;
	}
	public void setAppHealth(String appHealth) {
		this.appHealth = appHealth;
	}
	public int getAppHealth_sta() {
		return appHealth_sta;
	}
	public void setAppHealth_sta(int appHealth_sta) {
		this.appHealth_sta = appHealth_sta;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public int getAccess_sta() {
		return access_sta;
	}
	public void setAccess_sta(int access_sta) {
		this.access_sta = access_sta;
	}
}
