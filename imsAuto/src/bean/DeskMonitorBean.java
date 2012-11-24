/**
 * 
 */
package bean;

import java.io.Serializable;

/**
 * @author Administrator
 * 桌面监管Bean
 */
public class DeskMonitorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int corpId;
	private String corpName;
	private String winxp;  //终端数量winxp
	private String win2000;
	private String win2003;
	private String winVista;
	private String buding;  //补丁安装率
	private String zhongduan;  //终端使用率
	private String zhuomian;  //在线桌面终端
	private String weizhuce;  //未注册数量
	private String wailian;  //违规外联
	private String denglu;  //登录终端数
	private String yingzhuce;  //应注册终端数
	private String zhuce;  //注册终端数
	private String zhucelv;  //终端注册率
	private String avlv; // 杀毒软件安装率
	private String unregisteredCount; // 未注册终端数VRV
	private String illegalLink; // 违规外联VRV
	
	private int winxp_sta;
	private int win2000_sta;
	private int win2003_sta;
	private int winVista_sta;
	private int buding_sta;  //补丁安装率
	private int zhongduan_sta;  //终端使用率
	private int zhuomian_sta;  //在线桌面终端
	private int weizhuce_sta;  //未注册数量
	private int wailian_sta;  //违规外联
	private int denglu_sta;  //登录终端数
	private int yingzhuce_sta;  //应注册终端数
	private int zhuce_sta;  //注册终端数
	private int zhucelv_sta;  //终端注册率
	private int avlv_sta; // 杀毒软件安装率
	private int unregisteredCount_sta;
	private int illegalLink_sta;
	
	public String getIllegalLink() {
		return illegalLink;
	}
	public void setIllegalLink(String illegalLink) {
		this.illegalLink = illegalLink;
	}
	public int getIllegalLink_sta() {
		return illegalLink_sta;
	}
	public void setIllegalLink_sta(int illegalLink_sta) {
		this.illegalLink_sta = illegalLink_sta;
	}
	public String getAvlv() {
		return avlv;
	}
	public void setAvlv(String avlv) {
		this.avlv = avlv;
	}
	public int getAvlv_sta() {
		return avlv_sta;
	}
	public void setAvlv_sta(int avlv_sta) {
		this.avlv_sta = avlv_sta;
	}
	public int getWinxp_sta() {
		return winxp_sta;
	}
	public void setWinxp_sta(int winxp_sta) {
		this.winxp_sta = winxp_sta;
	}
	public int getWin2000_sta() {
		return win2000_sta;
	}
	public void setWin2000_sta(int win2000_sta) {
		this.win2000_sta = win2000_sta;
	}
	public int getWin2003_sta() {
		return win2003_sta;
	}
	public void setWin2003_sta(int win2003_sta) {
		this.win2003_sta = win2003_sta;
	}
	public int getWinVista_sta() {
		return winVista_sta;
	}
	public void setWinVista_sta(int winVista_sta) {
		this.winVista_sta = winVista_sta;
	}
	public int getBuding_sta() {
		return buding_sta;
	}
	public void setBuding_sta(int buding_sta) {
		this.buding_sta = buding_sta;
	}
	public int getZhongduan_sta() {
		return zhongduan_sta;
	}
	public void setZhongduan_sta(int zhongduan_sta) {
		this.zhongduan_sta = zhongduan_sta;
	}
	public int getZhuomian_sta() {
		return zhuomian_sta;
	}
	public void setZhuomian_sta(int zhuomian_sta) {
		this.zhuomian_sta = zhuomian_sta;
	}
	public int getWeizhuce_sta() {
		return weizhuce_sta;
	}
	public void setWeizhuce_sta(int weizhuce_sta) {
		this.weizhuce_sta = weizhuce_sta;
	}
	public int getWailian_sta() {
		return wailian_sta;
	}
	public void setWailian_sta(int wailian_sta) {
		this.wailian_sta = wailian_sta;
	}
	public int getDenglu_sta() {
		return denglu_sta;
	}
	public void setDenglu_sta(int denglu_sta) {
		this.denglu_sta = denglu_sta;
	}
	public int getYingzhuce_sta() {
		return yingzhuce_sta;
	}
	public void setYingzhuce_sta(int yingzhuce_sta) {
		this.yingzhuce_sta = yingzhuce_sta;
	}
	public int getZhuce_sta() {
		return zhuce_sta;
	}
	public void setZhuce_sta(int zhuce_sta) {
		this.zhuce_sta = zhuce_sta;
	}
	public int getZhucelv_sta() {
		return zhucelv_sta;
	}
	public void setZhucelv_sta(int zhucelv_sta) {
		this.zhucelv_sta = zhucelv_sta;
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
	public String getWinxp() {
		return winxp;
	}
	public void setWinxp(String winxp) {
		this.winxp = winxp;
	}
	public String getWin2000() {
		return win2000;
	}
	public void setWin2000(String win2000) {
		this.win2000 = win2000;
	}
	public String getWin2003() {
		return win2003;
	}
	public void setWin2003(String win2003) {
		this.win2003 = win2003;
	}
	public String getWinVista() {
		return winVista;
	}
	public void setWinVista(String winVista) {
		this.winVista = winVista;
	}
	public String getBuding() {
		return buding;
	}
	public void setBuding(String buding) {
		this.buding = buding;
	}
	public String getZhongduan() {
		return zhongduan;
	}
	public void setZhongduan(String zhongduan) {
		this.zhongduan = zhongduan;
	}
	public String getZhuomian() {
		return zhuomian;
	}
	public void setZhuomian(String zhuomian) {
		this.zhuomian = zhuomian;
	}
	public String getWeizhuce() {
		return weizhuce;
	}
	public void setWeizhuce(String weizhuce) {
		this.weizhuce = weizhuce;
	}
	public String getWailian() {
		return wailian;
	}
	public void setWailian(String wailian) {
		this.wailian = wailian;
	}
	public String getDenglu() {
		return denglu;
	}
	public void setDenglu(String denglu) {
		this.denglu = denglu;
	}
	public String getYingzhuce() {
		return yingzhuce;
	}
	public void setYingzhuce(String yingzhuce) {
		this.yingzhuce = yingzhuce;
	}
	public String getZhuce() {
		return zhuce;
	}
	public void setZhuce(String zhuce) {
		this.zhuce = zhuce;
	}
	public String getZhucelv() {
		return zhucelv;
	}
	public void setZhucelv(String zhucelv) {
		this.zhucelv = zhucelv;
	}
	public String getUnregisteredCount() {
		return unregisteredCount;
	}
	public void setUnregisteredCount(String unregisteredCount) {
		this.unregisteredCount = unregisteredCount;
	}
	public int getUnregisteredCount_sta() {
		return unregisteredCount_sta;
	}
	public void setUnregisteredCount_sta(int unregisteredCount_sta) {
		this.unregisteredCount_sta = unregisteredCount_sta;
	}

}
