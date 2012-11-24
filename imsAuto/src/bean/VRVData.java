/**
 * 
 */
package bean;

import java.io.Serializable;

/**
 * @author Administrator
 * VRV数据集
 */
public class VRVData implements Serializable {

	private static final long serialVersionUID = -8953069980783272686L;

	private String corpName; // 公司名称
	private int avCount; // 杀毒软件安装数
	private int registeredCount; // 已注册计算机数
	private int registerCount; // 应注册计算机数
	private int unregisteredCount; // 未注册计算机数
	private int illegalLink; // 违规外联数
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public int getAvCount() {
		return avCount;
	}
	public void setAvCount(int avCount) {
		this.avCount = avCount;
	}
	public int getRegisteredCount() {
		return registeredCount;
	}
	public void setRegisteredCount(int registeredCount) {
		this.registeredCount = registeredCount;
	}
	public int getRegisterCount() {
		return registerCount;
	}
	public void setRegisterCount(int registerCount) {
		this.registerCount = registerCount;
	}
	public int getUnregisteredCount() {
		return unregisteredCount;
	}
	public void setUnregisteredCount(int unregisteredCount) {
		this.unregisteredCount = unregisteredCount;
	}
	public int getIllegalLink() {
		return illegalLink;
	}
	public void setIllegalLink(int illegalLink) {
		this.illegalLink = illegalLink;
	}
}
