/**
 * 
 */
package dao;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import bean.BetaData;
import bean.NetMonitorBean;
import common.CommonUtil;
import common.DBUtil;
import common.DBUtil2;
import common.DomUtil;

/**
 * @author Administrator
 *
 */
public class NetMonitorDao {

	private static Logger logger = Logger.getLogger(NetMonitorDao.class);
	
	/**
	 * 网络带宽利用率数据
	 * @param deptId
	 * @return
	 */
	public double getDaikuan(int deptId) {
		//String flag = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double daikuan = -1;
		String sql = "select value from statdba.T_MsgNetUseRat where corporationid = ? and dimension_column1=2 and dimension_column2=3 and time like sysdate - 1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deptId);
			rs = ps.executeQuery();
			if (rs.next()) {
				daikuan = rs.getDouble("value");
//				double bandWidth = Double.valueOf(CommonUtil.getProperty("bandwidth"));
//				if (bandWidth == -1){
//					flag = "normal";
//				}
//				else {
//					double dk = rs.getDouble("value");
//					if (dk > bandWidth){
//						flag = "normal";
//					}
//				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return daikuan;
	}
	
	/**
	 * 网络丢包率数据
	 * @param deptId
	 * @return
	 */
	public double getDiubao(int deptId) {
		//String flag = "";
		double diubao = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select value from statdba.T_MsgNetDrpPktRat where corporationid = ? and dimension_column1=2 and dimension_column2=3 and time like sysdate - 1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deptId);
			rs = ps.executeQuery();
			if (rs.next()) {
				diubao = rs.getDouble("value");
//				double packetLoss = Double.valueOf(CommonUtil.getProperty("packetloss"));
//				if (packetLoss == -1){
//					flag = "normal";
//				}
//				else {
//					double db = rs.getDouble("value");
//					if (db <= packetLoss){
//						flag = "normal";
//					}
//				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return diubao;
	}
	
	/**
	 * 网络延时峰值数据
	 * @param deptId
	 * @return
	 */
	public int getDelay_max(int deptId) {
		//String flag = "";
		int delay_max = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select value from statdba.T_MsgNetDef where corporationid = ? and dimension_column1=2 and dimension_column2=1 and time like sysdate - 1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deptId);
			rs = ps.executeQuery();
			if (rs.next()) {
				delay_max = rs.getInt("value");
//				double delayMax = Double.valueOf(CommonUtil.getProperty("delaymax"));
//				if (delayMax == -1){
//					flag = "normal";
//				}
//				else {
//					double db = rs.getDouble("value");
//					if (db <= delayMax){
//						flag = "normal";
//					}
//				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return delay_max;
	}
	
	/**
	 * 网络延时均值数据
	 * @param deptId
	 * @return
	 */
	public int getDelay_avg(int deptId) {
		//String flag = "";
		int delay_avg = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select value from statdba.T_MsgNetDef where corporationid = ? and dimension_column1=2 and dimension_column2=3 and time like sysdate - 1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deptId);
			rs = ps.executeQuery();
			if (rs.next()) {
				delay_avg = rs.getInt("value");
//				double delayAvg = Double.valueOf(CommonUtil.getProperty("delayavg"));
//				if (delayAvg == -1){
//					flag = "normal";
//				}
//				else {
//					double db = rs.getDouble("value");
//					if (db <= delayAvg){
//						flag = "normal";
//					}
//				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return delay_avg;
	}

	/**
	 * 返回BETA数据
	 * @return
	 */
	private List<BetaData> getBetaData() {
		List<BetaData> list = null;
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String sql = "";
		int rec = 0;
		try {
			conn = DBUtil2.getConnection("beta");
			if (conn != null) {
				list = new ArrayList<BetaData>();
				sm = conn.createStatement();
				sql = "select bcreatedtime, bdescriptor from monitor_history where bcreatedtime > dateadd(day, -2, getdate()) and (bdescriptor like '%断开%' or bdescriptor like '%线路连通告警撤消%') order by bcreatedtime asc";
				rs = sm.executeQuery(sql);
				while (rs.next()) {
					BetaData beta = new BetaData();
					String msg = rs.getString("bdescriptor");
					beta.setTimeOrder(rec);
					beta.setMessages(msg);
					list.add(beta);
					rec++;
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR-BETA:" + Calendar.getInstance().getTime().toString() + e.getMessage());
		}
		return list;
	}
	
	/**
	 * 返回BETA告警记录
	 * @return
	 */
	public List<BetaData> getBetaList() {
		List<BetaData> alarmList = null;
		List<BetaData> betaList = null;
		int start = -1;
		alarmList = getBetaData(); // BETA数据
		if (alarmList != null) {
			for (BetaData beta : alarmList) {
				String msg = beta.getMessages();
				int al = msg.indexOf("断开");
				if (al != -1) {
					// 此记录为告警记录，处理
					int len = msg.indexOf("发生告警");
					String add = msg.substring(0, len);
					beta.setAlarmType("alarm"); // 标记告警记录
					start = alarmList.indexOf(beta);
					for (int i = start; i < alarmList.size(); i++) {
						String msg2 = alarmList.get(i).getMessages();
						if (msg2.indexOf("线路连通告警撤消") != -1 && msg2.indexOf(add) != -1) {
							//找到匹配的告警撤销记录
							beta.setAlarmType("normal"); // 撤销告警记录
							break;
						}
					}
				}
				else {
					// 此记录非告警记录，移除
					beta.setAlarmType("normal");
				}
			}

			betaList = new ArrayList<BetaData>();
			String filters = CommonUtil.getProperty("FILTER_LINK"); // 过滤链路集合
			for (BetaData beta : alarmList) {
				if (beta.getAlarmType().equals("alarm")) {
					String[] links = filters.split(";");
					boolean filter = false;
					for (String link : links) {
						String[] nodes = link.split(",");
						String node0 = nodes[0];
						String node1 = nodes[1];
						String info = beta.getMessages();
						if (info.indexOf(node0)!=-1 && info.indexOf(node1)!=-1) {
							filter = true; // 如此记录已在过滤链路中，则过滤之
							break;
						}
					}
					if (filter == false) {
						betaList.add(beta); // 添加告警记录
					}
				}
			}
		}
		return betaList;
	}	
	
	
	/**
	 * 查询网络监管数据，并装入记录Bean，返回巡检状态
	 * @param bean
	 * @param betaList
	 * @return
	 */
	public String setBean2(NetMonitorBean bean, List<BetaData> betaList) {
		
		String parten = "#.##";
		DecimalFormat decimal = new DecimalFormat(parten);
		
		String isNormal = "normal";
		int deptId = bean.getDeptId();
		
		if (betaList == null) {
			// BETA数据无法获取
			bean.setLink_sta(-1);
			bean.setLink("无数据");
			isNormal = "";
		}
		else {
			if (betaList.size() == 0) {
				bean.setLink("链路正常");
				bean.setLink_sta(0);
			}
			else {
				bean.setLink("链路正常");
				String msg = "";
				for (BetaData beta : betaList) {
					String resource = beta.getResource();
					String destination = beta.getDestination();
					String linkName = CommonUtil.Link_Name.get(deptId);
					if (resource.indexOf(linkName) != -1 || destination.indexOf(linkName)!= -1) {
						msg += beta.getMessages() + "<br>";
					}
				}
				if (!msg.equals("")) {
					bean.setLink(msg.substring(0, msg.length()-4));
					bean.setLink_sta(1);
					isNormal = "";
				}
			}
		}
		
		double daikuan = getDaikuan(deptId);
		int a = bean.getDaikuan_sta();
		if (daikuan == -1){
			bean.setDaikuan_sta(++a);
			bean.setDaikuan("无数据");
			isNormal = "";
			logger.error(bean.getDeptName() + CommonUtil.getProperty("bandwidth_msg"));
		}
		else {
			bean.setDaikuan( decimal.format(daikuan * 100) + "%");
		}

		double diubao = getDiubao(deptId);
		a = bean.getDiubao_sta();
		if (diubao == -1){
			bean.setDiubao_sta(++a);
			bean.setDiubao("无数据");
			isNormal = "";
			logger.error(bean.getDeptName() + CommonUtil.getProperty("packetloss_msg"));
		}
		else {
			if (diubao == 0){
				bean.setDiubao("0");
			}
			else {
				bean.setDiubao(decimal.format(diubao * 100) + "%");
			}
		}
		
		int delay_max = getDelay_max(deptId);
		a = bean.getDelay_max_sta();
		if (delay_max == -1){
			bean.setDelay_max_sta(++a);
			isNormal = "";
			bean.setDelay_max("无数据");
			logger.error(bean.getDeptName() + CommonUtil.getProperty("delaymax_msg"));
		}
		else {
			bean.setDelay_max(String.valueOf(delay_max));
		}
		
		int delay_avg = getDelay_avg(deptId);
		a = bean.getDelay_avg_sta();
		if (delay_avg == -1){
			bean.setDelay_avg_sta(++a);
			isNormal = "";
			bean.setDelay_avg("无数据");
			logger.error(bean.getDeptName() + CommonUtil.getProperty("delayavg_msg"));
		}
		else {
			bean.setDelay_avg(String.valueOf(delay_avg));
		}
		return isNormal;
	}

	/**
	 * 判断地市链路所处平面
	 * @param msg 告警信息描述
	 * @param corpName 地市名称
	 * @return "155" "622" ""
	 */
	private static String checkDomain(String msg, String corpName) {
		String netName = "";
		String [] app = DomUtil.getBetaConfigData(corpName);
		String[] net_155 = app[0].split(","); // 155平面
		String[] net_622 = app[1].split(","); // 622平面
		int cout_155 = 0;
		int cout_622 = 0;
		for (int i = 0; i<net_155.length; i++) {
			int index_155 = msg.indexOf(net_155[i]);
			if (index_155 != -1) {
				cout_155++;
			}
		}
		for (int i = 0; i<net_622.length; i++) {
			int index_622 = msg.indexOf(net_622[i]);
			if (index_622 != -1) {
				cout_622++;
			}
		}
		if (cout_155 == 2) {
			// 155平面
			netName = "(155M)";
		}
		else if (cout_622 == 2) {
			// 622平面
			netName = "(622M)";
		}
		else {
			// 跨地市链路
		}
		return netName;
	}
	
	/**
	 * 设置链路连通性告警状态
	 * @param bean
	 * @param betaList BETA告警数据集
	 * @return "normal" 正常 ""有告警
	 */
	private String setLinkState(NetMonitorBean bean, List<BetaData> betaList) {
		String isNormal = "normal";
		int deptId = bean.getDeptId();
		
		if (deptId == 12) {
			// 省公司-国网
			bean.setLink("链路正常");
			bean.setLink_sta(0);
			int state = isConnect();
			if (state != 200) {
				bean.setLink("链路异常");
				bean.setLink_sta(1);
			}
		}
		else {
			if (betaList == null) {
				// BETA数据无法获取
				bean.setLink_sta(-1);
				bean.setLink("无数据");
				isNormal = "";
			}
			else {
				String info = "";
				bean.setLink("链路正常");
				bean.setLink_sta(0);
				if (deptId == 1200) {
					// 省公司本部
					String strNodes = CommonUtil.getProperty("PRO_NODE");
					String[] nodes = strNodes.split(",");
					for (BetaData beta : betaList) {
						String msg = beta.getMessages();
						for (int i = 0; i<nodes.length; i++) {
							if (msg.indexOf(nodes[i]) != -1) {
								info += beta.getMessages() + "<br>";
								break;
							}
						}
					}
				}
				else {
					// 地市公司
					for (BetaData beta : betaList) {
						String msg = beta.getMessages();
						String linkName = CommonUtil.Link_Name.get(deptId);
						if (msg.indexOf(linkName) != -1) {
							String netName = checkDomain(msg, linkName);
							info += beta.getMessages() + netName + "<br>";
						}
					}
				}
				if (!info.equals("")) {
					bean.setLink(info.substring(0, info.length()-4));
					bean.setLink_sta(1);
					isNormal = "";
				}
			}
		}
		return isNormal;
	}
	
	/**
	 * 查询网络监管数据，并装入记录Bean，返回巡检状态
	 * @param bean
	 */
	public String setBean(NetMonitorBean bean, List<BetaData> betaList) {
		
		String parten = "#.##";
		DecimalFormat decimal = new DecimalFormat(parten);
		
		String isNormal = "normal";
		int deptId = bean.getDeptId();
		
		// 链路连通性
		isNormal = setLinkState(bean, betaList);
		
		if (deptId == 12) {
			// 省公司-国网，下列指标不考核
			bean.setDaikuan("正常");
			bean.setDiubao("正常");
			bean.setDelay_max("正常");
			bean.setDelay_avg("正常");
		}
		else {
			// 网络带宽利用率
			double daikuan = getDaikuan(deptId);
			int a = bean.getDaikuan_sta();
			if (daikuan == -1){
				bean.setDaikuan_sta(++a);
				bean.setDaikuan("无数据");
				isNormal = "";
				logger.error(bean.getDeptName() + CommonUtil.getProperty("bandwidth_msg"));
			}
			else {
				bean.setDaikuan( decimal.format(daikuan * 100) + "%");
			}

			// 网络丢包率
			double diubao = getDiubao(deptId);
			a = bean.getDiubao_sta();
			if (diubao == -1){
				bean.setDiubao_sta(++a);
				bean.setDiubao("无数据");
				isNormal = "";
				logger.error(bean.getDeptName() + CommonUtil.getProperty("packetloss_msg"));
			}
			else {
				if (diubao == 0){
					bean.setDiubao("0");
				}
				else {
					bean.setDiubao(decimal.format(diubao * 100) + "%");
				}
			}
			
			// 网络延时峰值
			int delay_max = getDelay_max(deptId);
			a = bean.getDelay_max_sta();
			if (delay_max == -1){
				bean.setDelay_max_sta(++a);
				isNormal = "";
				bean.setDelay_max("无数据");
				logger.error(bean.getDeptName() + CommonUtil.getProperty("delaymax_msg"));
			}
			else {
				bean.setDelay_max(String.valueOf(delay_max));
			}
			
			// 网络延时均值
			int delay_avg = getDelay_avg(deptId);
			a = bean.getDelay_avg_sta();
			if (delay_avg == -1){
				bean.setDelay_avg_sta(++a);
				isNormal = "";
				bean.setDelay_avg("无数据");
				logger.error(bean.getDeptName() + CommonUtil.getProperty("delayavg_msg"));
			}
			else {
				bean.setDelay_avg(String.valueOf(delay_avg));
			}			
		}
		return isNormal;
	}

	private static int isConnect() {
		
		String url = CommonUtil.getProperty("STATE_URL");	
		URL testUrl;
			int state = 0;
			try {
				testUrl = new URL(url);
				URLConnection urlConn = testUrl.openConnection();
				HttpURLConnection httpConn = (HttpURLConnection)urlConn;
				state = httpConn.getResponseCode();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				state = -1;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				state = -1;
			}
			return state;
	}
	public static void main(String[] args) {
		String filters = CommonUtil.getProperty("FILTER_LINK");
		String[] links = filters.split(";");
		System.out.println(links.length);
		for (int i=0; i<links.length; i++) {
			String[] nodes = links[i].split(",");
			System.out.println(nodes[0]);
		}
		//System.out.println(links);
	}
}
