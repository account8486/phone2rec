/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import bean.SecuMonitorBean;
import common.CommonUtil;
import common.DBUtil;

/**
 * @author Administrator
 *
 */
public class SecuMonitorDao {

	private static Logger logger = Logger.getLogger(SecuMonitorDao.class);
	
	/**
	 * 风险最大值
	 * @param corpId
	 * @return
	 */
	public int getRiskMax(int corpId) {
		int risk = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select value from statdba.T_GeographicalRiskMax where corporationid = ? and time like sysdate-1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, corpId);
			rs = ps.executeQuery();
			while (rs.next()) {
				//如果数据大于0，告警
				risk = rs.getInt("value");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return risk;
	}
	
	/**
	 * 漏洞总数
	 * @param corpId
	 * @return
	 */
	public int getLeakCount(int corpId) {
		int leakCount = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select sum(value) count from statdba.T_LeakCountByLevel where corporationid = ? and time like sysdate-1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, corpId);
			rs = ps.executeQuery();
			if (rs.next()) {
				//如果数据大于0，告警
				leakCount = rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return leakCount;
	}
	
	/**
	 * 漏洞状态
	 * @param corpId
	 * @return
	 */
	public int getLeakStatus(int corpId) {
		int leakSta = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select sum(value) count from statdba.T_BugStuSta where corporationid = ? and time like sysdate-1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, corpId);
			rs = ps.executeQuery();
			if (rs.next()) {
				//如果数据大于0，告警
				leakSta = rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return leakSta;
	}
	
	/**
	 * 配置脆弱性
	 * @param corpId
	 * @return
	 */
	public int getFitFraTal(int corpId) {
		int fit = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select sum(value) count from statdba.T_FitFraTalNum where corporationid = ? and time like sysdate-1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, corpId);
			rs = ps.executeQuery();
			if (rs.next()) {
				//如果数据大于0，告警
				fit = rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return fit;
	}
	
	/**
	 * 新接入安全事件
	 * @param corpId
	 * @return
	 */
	public int getSavEvt(int corpId) {
		int savEvt = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select sum(value) count from statdba.T_PntSavEvt where corporationid = ? and time like sysdate-1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, corpId);
			rs = ps.executeQuery();
			if (rs.next()) {
				//如果数据大于0，告警
				savEvt = rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return savEvt;
	}
	
	/**
	 * 新接入安全事件(实时)数据查询
	 * @param bean 安全监管BEAN
	 * @return 数据状态: normal 正常; ""有告警
	 */
	private String getSavEvtRealTime(SecuMonitorBean bean) {
		String status = "normal";
		String corpName = "";
		String savEvt = "";  // 新接入安全事件提示内容
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select b.name, decode(a.sev, 0, '低级', 1, '低级', '中级及以上') rank, sum(a.statnum) amount from T_SIM_ES_EVTIPCOUNT a, t_sim_ah_local b where a.statdate = to_char(sysdate, 'yyyymmdd') and a.devip = b.ip group by b.name, decode(a.sev, 0, '低级', 1, '低级', '中级及以上')";
		try {
			conn = DBUtil.getConnection("soc");
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				corpName = rs.getString("name");
				if (corpName.equals(bean.getCorpName())) {
					//某单位发生安全事件
					status = "";
					bean.setSavEvtRealTime_sta(1);
					savEvt += rs.getString("rank") + ":";
					savEvt += rs.getString("amount") + "\n";
				}
			}
			if (savEvt.equals("")) {
				bean.setSavEvtRealTime("0");
			}
			else {
				bean.setSavEvtRealTime(savEvt.substring(0, savEvt.length()-1));
			}
		} catch (SQLException e) {
			logger.error("查询异常-安全监管新接入安全事件(实时)指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, st, conn);
		}
		return status;
	}
	
	/**
	 * 获取告警监管信息
	 * @return
	 */
	public int getAlarm() {
		int count = -1;
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String sql = "select count(id) count from performancedba.alertinfo where status<>3";
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next()) {
				//如果数据大于0，告警
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return count;
	}
	
	/**
	 * 查询安全监管数据，并装入记录Bean，返回巡检状态
	 * @param bean
	 * @return
	 */
	public String setBean(SecuMonitorBean bean) {		
		String isNormal = "normal";
		int corpId = bean.getCorpId();
		int a = 0;
		
		// 风险最大值
		int riskMax = getRiskMax(corpId);
		bean.setRiskMax(String.valueOf(riskMax));
		if (riskMax != 0){
			a = bean.getRiskMax_sta();
			bean.setRiskMax_sta(++a);
			if (riskMax == -1){
				bean.setRiskMax("无数据");
			}
			isNormal = "";
			logger.error(bean.getCorpName() + CommonUtil.getProperty("riskmax_msg"));
		}
		
		// 漏洞总数
		int leakCount = getLeakCount(corpId);
		bean.setLeakCount(String.valueOf(leakCount));
		if (leakCount != 0){
			a = bean.getLeakCount_sta();
			bean.setLeakCount_sta(++a);
			if (leakCount == -1){
				bean.setLeakCount("无数据");
			}
			isNormal = "";
			logger.error(bean.getCorpName() + CommonUtil.getProperty("leakcount_msg"));
		}
		
		// 漏洞状态
		int leakSta = getLeakStatus(corpId);
		bean.setLeakStatus(String.valueOf(leakSta));
		if (leakSta != 0){
			a = bean.getLeakStatus_sta();
			bean.setLeakStatus_sta(++a);
			if (leakSta == -1){
				bean.setLeakStatus("无数据");
			}
			isNormal = "";
			logger.error(bean.getCorpName() + CommonUtil.getProperty("leakstatus_msg"));
		}
		
		// 配置脆弱性
		int fit = getFitFraTal(corpId);
		bean.setFitFraTal(String.valueOf(fit));
		if (fit != 0){
			a = bean.getFitFraTal_sta();
			bean.setFitFraTal_sta(++a);
			if (fit == -1){
				bean.setFitFraTal("无数据");
			}
			isNormal = "";
			logger.error(bean.getCorpName() + CommonUtil.getProperty("fitfratal_msg"));
		}
		
		//新接入安全事件只监管省公司数据
		if (corpId == 12){
			int savEvt = getSavEvt(corpId);
			bean.setSavEvt(String.valueOf(savEvt));
			if (savEvt != 0){
				a = bean.getSavEvt_sta();
				bean.setSavEvt_sta(++a);
				if (savEvt == -1){
					bean.setSavEvt("无数据");
				}
				isNormal = "";
				logger.error(bean.getCorpName() + CommonUtil.getProperty("savevt_msg"));
			}
		}
		else {
			bean.setSavEvt("无异常");
		}
		
		int alarm = getAlarm();
		bean.setAlarm(alarm);
		if (alarm != 0){
			//isNormal = "";
			//logger.error(CommonUtil.getProperty("alarm_msg"));
		}
		
		String flag = getSavEvtRealTime(bean);
		if (flag.equals("")) {
			isNormal = "";
			logger.error(bean.getCorpName() + CommonUtil.getProperty("savevt_msg_real"));
		}
		
		return isNormal;
	}
}
