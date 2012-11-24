/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import org.apache.log4j.Logger;
import bean.AppMonitorBean;
import common.CommonUtil;
import common.DBUtil;
import common.DomUtil;

/**
 * @author Administrator
 * 应用监管数据访问类
 */
public class AppMonitorDao {
	
	private static Logger logger = Logger.getLogger(AppMonitorDao.class);
	
	/**
	 * 系统健康运行时长
	 * @param resourceId
	 * @return
	 */
	public String getApplicationHealth(String resourceId) {
		String flag = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Calendar cadar1 = Calendar.getInstance();
		Calendar cadar2 = Calendar.getInstance();
		String sql = "select htime, sysdate nowtime from performancedba.BusinessSystemRunningTime5MIN  where resourceid = ? and time like sysdate";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setString(1, resourceId);
			rs = ps.executeQuery();
			if (rs.next()) {
				Timestamp htime = rs.getTimestamp("htime");
				Timestamp nowtime = rs.getTimestamp("nowtime");
				cadar1.setTimeInMillis(htime.getTime());
				cadar2.setTimeInMillis(nowtime.getTime());
				cadar2.add(Calendar.MINUTE, -5);
				cadar2.set(Calendar.SECOND, 0);
				if (cadar1.after(cadar2)){
					flag = "normal";  //如果当前时间减去5分钟小于数据库中最大值时间，说明正常
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return flag;
	}
	
	/**
	 * 系统健康运行时长状态
	 * @param resourceId
	 * @return long[0]:运行时长; long[1]: 0正常-1异常
	 */
	public long[] getApplicationHealth1(String resourceId) {
		long flag = -1;
		long appTime = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Calendar cada = Calendar.getInstance();
		String time1;
		String time2;
		String time3;
		Long appTime1;
		Long appTime2;
		Long appTime3;
		String runtime_l = CommonUtil.getProperty("runtime_l");
		String runtime_h = CommonUtil.getProperty("runtime_h");
		int run_l = 280;
		int run_h = 320;
		if (runtime_l != null && runtime_h != null){
			run_l = Integer.valueOf(runtime_l);
			run_h = Integer.valueOf(runtime_h);
		}

		String sql = "select t.*, sysdate nowtime from performancedba.BusinessSystemRunningTime5MIN t where resourceid = ? and time like sysdate";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setString(1, resourceId);
			rs = ps.executeQuery();
			if (rs.next()) {
				Timestamp nowtime = rs.getTimestamp("nowtime");
				cada.setTimeInMillis(nowtime.getTime());
				Integer minu = cada.get(Calendar.MINUTE);
				int minu1 = 0;
				int minu2 = 0;
				if (minu<10){
					minu1 = 0;
					minu2 = minu;
				}
				else {
					String[] minuarr = minu.toString().split("");
					minu1 = Integer.parseInt(minuarr[1]);
					minu2 = Integer.parseInt(minuarr[2]);
				}
				if (minu2 >=5){
					cada.set(Calendar.MINUTE, minu1*10+5);  //最近5分钟点
					time1 = getFormatTime(cada.get(Calendar.HOUR_OF_DAY), cada.get(Calendar.MINUTE));
				}
				else {
					cada.set(Calendar.MINUTE, minu1*10);  //最近5分钟点
					time1 = getFormatTime(cada.get(Calendar.HOUR_OF_DAY), cada.get(Calendar.MINUTE));
				}
				cada.add(Calendar.MINUTE, -5);  //最近10分钟点
				time2 = getFormatTime(cada.get(Calendar.HOUR_OF_DAY), cada.get(Calendar.MINUTE));
				cada.add(Calendar.MINUTE, -5);  //最近15分钟点
				time3 = getFormatTime(cada.get(Calendar.HOUR_OF_DAY), cada.get(Calendar.MINUTE));
				appTime1 = rs.getLong(time1);  // 五分钟数据
				appTime2 = rs.getLong(time2);  // 十分钟数据
				appTime3 = rs.getLong(time3);  // 十五分钟数据
				if (appTime1 == 0){
					if ((appTime2 - appTime3)>=run_l && (appTime2 - appTime3)<=run_h){
						flag = 0;
					}
					appTime = appTime2;  //最近5分钟点数据尚未入库，返回10分钟点数据
				}
				else {
					if ((appTime1 - appTime2)>=run_l && (appTime1 - appTime2)<=run_h){
						flag = 0;
					}
					appTime = appTime1;  //最近5分钟点数据已入库，返回此数据
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		long[] arrLong = new long[2];
		arrLong[0] = appTime;
		arrLong[1] = flag;
		return arrLong;
	}
	
	
	/**
	 * 系统访问状态
	 * @param dimension
	 * @return 访问状态码, 200表示正常
	 */
	public int getAccessState(String dimension) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int access = -1;
		Calendar cada = Calendar.getInstance();
		Calendar cada2 = Calendar.getInstance();
		
		String sql = "select * from " +
				"(select value, time recenttime, sysdate nowtime from statdba.t_protalhomecode a " +
				"where a.dimension_column1 = ? and time > sysdate - 1 order by time desc) " +
				"where rownum = 1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setString(1, dimension);
			rs = ps.executeQuery();
			if (rs.next())
			{
				Timestamp recent = rs.getTimestamp("recenttime");
				Timestamp nowtime = rs.getTimestamp("nowtime");
				cada.setTime(recent);
				cada.add(Calendar.MINUTE, 5);
				cada2.setTime(nowtime);
				if (cada.after(cada2))
				{
					//最新数据为最近5分钟点数据，数据正常
					access = rs.getInt("value");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return access;
	}
	
	
	/**
	 * 返回系统健康运行时长值
	 * @param rs
	 * @return
	 */
//	public Long getAppTime(ResultSet rs) {
//		Calendar caldar = Calendar.getInstance();
//		String time1;
//		String time2;
//		Long appTime = new Long(-9999);
//		Long appTime1;
//		Long appTime2;
//		try {
//			if (rs.next()){
//				Timestamp nowtime = rs.getTimestamp("nowtime");
//				caldar.setTimeInMillis(nowtime.getTime());
//				Integer minu = caldar.get(Calendar.MINUTE);
//				int minu1 = 0;
//				int minu2 = 0;
//				if (minu<10){
//					minu1 = 0;
//					minu2 = minu;
//				}
//				else {
//					String[] minuarr = minu.toString().split("");
//					minu1 = Integer.parseInt(minuarr[1]);
//					minu2 = Integer.parseInt(minuarr[2]);
//				}
//				if (minu2 >=5){
//					caldar.set(Calendar.MINUTE, minu1*10+5);  //最近5分钟点
//					time1 = getFormatTime(caldar.get(Calendar.HOUR_OF_DAY), caldar.get(Calendar.MINUTE));
//				}
//				else {
//					caldar.set(Calendar.MINUTE, minu1*10);  //最近5分钟点
//					time1 = getFormatTime(caldar.get(Calendar.HOUR_OF_DAY), caldar.get(Calendar.MINUTE));
//				}
//				caldar.add(Calendar.MINUTE, -5);  //最近10分钟点
//				time2 = getFormatTime(caldar.get(Calendar.HOUR_OF_DAY), caldar.get(Calendar.MINUTE));
//				appTime1 = rs.getLong(time1);
//				appTime2 = rs.getLong(time2);
//				appTime = appTime1;
//				if (appTime1 == -9999){
//					appTime = appTime2;
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return appTime;
//	}
	
	/**
	 * 返回系统健康运行时长状态
	 * @param rs
	 * @return
	 */
//	public String getAppTimeSta(ResultSet rs) {
//		String flag = "";
//		Calendar cada = Calendar.getInstance();
//		String time1;
//		String time2;
//		String time3;
//		Long appTime1;
//		Long appTime2;
//		Long appTime3;
//		String runtime_l = CommonUtil.getProperty("runtime_l");
//		String runtime_h = CommonUtil.getProperty("runtime_h");
//		int run_l = 280;
//		int run_h = 320;
//		if (runtime_l != null && runtime_h != null){
//			run_l = Integer.valueOf(runtime_l);
//			run_h = Integer.valueOf(runtime_h);
//		}		
//		try {
//			if (rs.next()) {
//				Timestamp nowtime = rs.getTimestamp("nowtime");
//				cada.setTimeInMillis(nowtime.getTime());
//				Integer minu = cada.get(Calendar.MINUTE);
//				int minu1 = 0;
//				int minu2 = 0;
//				if (minu<10){
//					minu1 = 0;
//					minu2 = minu;
//				}
//				else {
//					String[] minuarr = minu.toString().split("");
//					minu1 = Integer.parseInt(minuarr[1]);
//					minu2 = Integer.parseInt(minuarr[2]);
//				}
//				if (minu2 >=5){
//					cada.set(Calendar.MINUTE, minu1*10+5);  //最近5分钟点
//					time1 = getFormatTime(cada.get(Calendar.HOUR_OF_DAY), cada.get(Calendar.MINUTE));
//				}
//				else {
//					cada.set(Calendar.MINUTE, minu1*10);  //最近5分钟点
//					time1 = getFormatTime(cada.get(Calendar.HOUR_OF_DAY), cada.get(Calendar.MINUTE));
//				}
//				cada.add(Calendar.MINUTE, -5);  //最近10分钟点
//				time2 = getFormatTime(cada.get(Calendar.HOUR_OF_DAY), cada.get(Calendar.MINUTE));
//				cada.add(Calendar.MINUTE, -5);  //最近15分钟点
//				time3 = getFormatTime(cada.get(Calendar.HOUR_OF_DAY), cada.get(Calendar.MINUTE));
//				appTime1 = rs.getLong(time1);
//				appTime2 = rs.getLong(time2);
//				appTime3 = rs.getLong(time3);
//				if (appTime1 == -9999){
//					if ((appTime2 - appTime3)>=run_l && (appTime2 - appTime3)<=run_h){
//						flag = "normal";
//					}
//				}
//				else {
//					if ((appTime1 - appTime2)>=run_l && (appTime1 - appTime2)<=run_h){
//						flag = "normal";
//					}
//				}
//			}
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		return flag;
//	}
//	

	
	/**
	 * 在线用户数
	 * @param dimension
	 * @return
	 */
	public int getOnlineCountReal(String dimension) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int onlineCount = -1;
		Calendar cada = Calendar.getInstance();
		Calendar cada2 = Calendar.getInstance();
		
		String sql = "select * from " +
				"(select value, time recenttime, sysdate nowtime from statdba.T_OnlineCountReal a " +
				"where a.corporationid = 12 and a.dimension_column1 = ? and time > sysdate - 1 order by time desc) " +
				"where rownum = 1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setString(1, dimension);
			rs = ps.executeQuery();
			if (rs.next())
			{
				Timestamp recent = rs.getTimestamp("recenttime");
				Timestamp nowtime = rs.getTimestamp("nowtime");
				cada.setTime(recent);
				cada.add(Calendar.MINUTE, 10);
				cada2.setTime(nowtime);
				if (cada.after(cada2))
				{
					//最新数据为最近5分钟点数据，数据正常
					onlineCount = rs.getInt("value");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return onlineCount;
	}

	/**
	 * 在线用户历史峰值
	 * @param dimension
	 * @return
	 */
	public int getOnlineCountReal_H(String dimension) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int onlineCount_H = -1;
		
		String sql = "select value from statdba.T_OnlineCountRealHistoryMax a where a.corporationid = 12 and a.dimension_column1 = ? and time like sysdate - 1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setString(1, dimension);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//昨日数据，数据正常
				onlineCount_H = rs.getInt("value");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return onlineCount_H;
	}
	
	/**
	 * 日登录人数
	 * @param dimension
	 * @return
	 */
	public int getDayLoginNum(String dimension) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int dayLoginNum = -1;
		Calendar cada = Calendar.getInstance();
		Calendar cada2 = Calendar.getInstance();
		
		String sql = "select * from " +
				"(select value, time recenttime, sysdate nowtime from statdba.T_BusinessDayLoginNum a " +
				"where a.corporationid = 12 and a.dimension_column1 = ? and time > sysdate - 1 order by time desc) " +
				"where rownum = 1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setString(1, dimension);
			rs = ps.executeQuery();
			if (rs.next())
			{
				Timestamp recent = rs.getTimestamp("recenttime");
				Timestamp nowtime = rs.getTimestamp("nowtime");
				cada.setTime(recent);
				cada.add(Calendar.MINUTE, 10);
				cada2.setTime(nowtime);
				if (cada.after(cada2))
				{
					//最新数据为最近5分钟(10分钟)点数据，数据正常
					dayLoginNum = rs.getInt("value");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return dayLoginNum;
	}
	
	/**
	 * 日登录用户历史峰值
	 * @param dimension
	 * @return
	 */
	public int getDayLoginNum_H(String dimension) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int dayLoginNum_H = -1;
		
		String sql = "select value from statdba.T_BusinessDayLoginNumHM a where a.corporationid = 12 and a.dimension_column1 = ? and time like sysdate - 1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setString(1, dimension);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//昨日历史数据，数据正常
				dayLoginNum_H = rs.getInt("value");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return dayLoginNum_H;
	}
	
	/**
	 * 注册用户总数
	 * @param dimension
	 * @return
	 */
	public int getRegCount(String dimension) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int regCount = -1;
		
		String sql = "select value from statdba.T_RegisteredUsersCount a where a.corporationid = 12 and a.dimension_column1 = ? and time like sysdate - 1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setString(1, dimension);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//昨日历史数据，数据正常
				regCount = rs.getInt("value");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return regCount;
	}	
	
	/**
	 * 查询应用监管数据，并装入记录Bean，返回巡检状态
	 * @param bean
	 * @return
	 */
	public int setBean(AppMonitorBean bean) {
		String resourceId = bean.getAppId();
		String appNo = bean.getAppNo();
		String status = "normal";
		int a;
		int stat = 0;
		String modName = "应用";

		// 系统健康运行时长
		int alarm_appTime = 0;
		int switch_appTime = DomUtil.getSoundConfig(modName, "健康运行时长");
		long[] sta = getApplicationHealth1(resourceId);
		long appTime = sta[0];
		if (appTime == 0){
			bean.setAppHealth("无数据");
			status = "";
			alarm_appTime = 1;
		}
		else {
			bean.setAppHealth(String.valueOf(appTime));
		}
		if (sta[1] == -1){
			a = bean.getAppHealth_sta();
			bean.setAppHealth_sta(++a);
			status = "";
			alarm_appTime = 1;
			logger.error(bean.getAppName() + "-" + CommonUtil.getProperty("runtime_msg"));
		}
		alarm_appTime = (alarm_appTime & switch_appTime);
		
		// 在线用户数
		int alarm_onlineCount = 0;
		int switch_onlineCount = DomUtil.getSoundConfig(modName, "在线用户数");
		int onlineCount = getOnlineCountReal(appNo);
		bean.setOnlineCount(String.valueOf(onlineCount));
		if (onlineCount == -1){
			a = bean.getOnlineCount_sta();
			bean.setOnlineCount_sta(++a);
			bean.setOnlineCount("无数据");
			status = "";
			alarm_onlineCount = 1;
			logger.error(bean.getAppName() + "-" + CommonUtil.getMessage("OnLineCount_MSG"));
		}
		alarm_onlineCount = (alarm_onlineCount & switch_onlineCount);

		// 系统访问状态
		int alarm_access = 0;
		int switch_access = DomUtil.getSoundConfig(modName, "业务系统探测");
		int access = getAccessState(appNo);
		bean.setAccess(String.valueOf(access));
		if (access == 200) {
			// 系统访问正常
		}
		else if (access == -1) {
			// 返回状态码数据异常
			if (appNo.equals("27")) {
				// ERP系统不做考核
				bean.setAccess("");
			}
			else {
				bean.setAccess_sta(1);
				bean.setAccess("无数据");
				status = "";
				alarm_access = 1;
			}
		}
		else {
			// 状态码异常
			bean.setAccess_sta(1);
			status = "";
			alarm_access = 1;
		}
		alarm_access = (alarm_access & switch_access);
		
		
		// 在线用户历史峰值
		int alarm_onlineCountH = 0;
		int switch_onlineCountH = DomUtil.getSoundConfig(modName, "在线用户历史峰值");
		if (getOnlineCountReal_H_XML(bean).equals("")) {
			status = "";
			alarm_onlineCountH = 1;
		}
		alarm_onlineCountH = (alarm_onlineCountH & switch_onlineCountH);
		
		// 日登陆人数
		int alarm_dayLogNum = 0;
		int switch_dayLogNum = DomUtil.getSoundConfig(modName, "日登陆人数");
		if (appNo.equals("30") || appNo.equals("63"))
		{
			//内网网站、ERP高级应用系统，日登陆人数指标无数据
			bean.setDayLogNum("");
		}
		else {
			int dayLoginNum = getDayLoginNum(appNo);
			bean.setDayLogNum(String.valueOf(dayLoginNum));
			if (dayLoginNum == -1){
				a = bean.getDayLogNum_sta();
				bean.setDayLogNum_sta(++a);
				bean.setDayLogNum("无数据");
				status = "";
				alarm_dayLogNum = 1;
				logger.error(bean.getAppName() + "-" + CommonUtil.getMessage("DayLoginNum_MSG"));
			}
		}
		alarm_dayLogNum = (alarm_dayLogNum & switch_dayLogNum);
		
		// 日登录用户历史峰值
		int alarm_dayLogNumH = 0;
		int switch_dayLogNumH = 0;
		if (getDayLoginNum_H_XML(bean).equals("")) {
			status = "";
			alarm_dayLogNumH = 1;
		}
		alarm_dayLogNumH = (alarm_dayLogNumH & switch_dayLogNumH);
		
		// 注册用户总数
		int alarm_regCount = 0;
		int switch_regCount = DomUtil.getSoundConfig(modName, "注册用户总数");
		if (getRegCount_XML(bean).equals("")) {
			status = "";
			alarm_regCount = 1;
		}
		alarm_regCount = (alarm_regCount & switch_regCount);

		// 专业指标
		int alarm_zhuanye = 0;
		int switch_zhuanye = DomUtil.getSoundConfig(modName, "专业指标");
		String[] appData = DomUtil.getAppData(appNo);
		bean.setZhuanye(appData[1]);
		if (appData[0].equals("0")) {
			bean.setZhuanye_sta(0);
		}
		else {
			bean.setZhuanye_sta(1);
			status = "";
			alarm_zhuanye = 1;
			logger.error(bean.getAppName() + "-" + CommonUtil.getMessage("ZHUANYE_MSG"));
		}
		alarm_zhuanye = (alarm_zhuanye & switch_zhuanye);
		
		stat = (alarm_appTime | alarm_onlineCount | alarm_access | alarm_onlineCountH | alarm_dayLogNum | alarm_regCount | alarm_zhuanye);
		
		return stat;
	}
	
	/**
	 * 将指定时间转换为'Vhhmm'格式的字符串
	 * @param hour
	 * @param minute
	 * @return
	 */
	private String getFormatTime(int hour, int minute) {
		String sHour = formatTime(hour);
		String sMinu = formatTime(minute);
		String sTime = "V" + sHour + sMinu;
		return sTime;
	}
	
	/**
	 * 将int转换为string，小于10，用0补齐
	 * @param times
	 * @return
	 */
	private String formatTime(Integer times) {
		if (times < 10){
			String ss = "0" + times;
			return ss;
		}
		else {
			return times.toString();
		}
	}
	
	/**
	 * ERP
	 * @return
	 */
	public HashMap<String, String> getData_ERP() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		
		String sql = "";
		
		sql = "select " +
				"(select value from statdba.T_ERPFICOIntegratedRate t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1, " +  //日集成凭证的比率
				"(select value from statdba.T_ERPFICOInvoiceCheckRightRate t " +
				  "where to_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value2, " +  //日发票校验的准确率
				"(select value from statdba.T_ERPMMPurOrdCompleteRate t " +
				  "where to_char(time, 'yyyy-mm-dd') = to_char(add_months(trunc(sysdate, 'MM'), -1), 'yyyy-mm-dd') " +
				  "and corporationid = 12) value3, " +  //采购订单完成率(月指标)
				"(select value from statdba.T_ERPMMPurReqCompleteRate t " +
				  "where to_char(time, 'yyyy-mm-dd') = to_char(add_months(trunc(sysdate, 'MM'), -1), 'yyyy-mm-dd') " +
				  "and corporationid = 12) value4, " +  //采购申请完成率(月指标)
				"(select value from statdba.T_ERPMMMblnrTimelyRate t " +
				  "where to_char(time, 'yyyy-mm-dd') = to_char(add_months(trunc(sysdate, 'MM'), -1), 'yyyy-mm-dd') " +
				  "and corporationid = 12) value5, " +  //收发货凭证录入时间差异(月指标)
				"(select value from statdba.T_ERPMMNewCredence t " +
				  "where to_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value6 " +  //日物料凭证数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据，数据正常
				erpData = rs.getString("value1");
				map.put("value1", erpData);
				erpData = rs.getString("value2");
				map.put("value2", erpData);
				erpData = rs.getString("value3");
				map.put("value3", erpData);
				erpData = rs.getString("value4");
				map.put("value4", erpData);
				erpData = rs.getString("value5");
				map.put("value5", erpData);
				erpData = rs.getString("value6");
				map.put("value6", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-ERP专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 营销应用
	 * @return
	 */
	public HashMap<String, String> getData_YXYY() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_YXBusinessSNWOrderNUM t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1, " +  //日新增工单总数
				"(select value from statdba.T_YXBusinessSystemWorkOrd t " +
				  "where to_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value2 " +  //日办结工单总数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
				erpData = rs.getString("value2");
				map.put("value2", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-营销应用专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 财务管控
	 * @return
	 */
	public HashMap<String, String> getData_CWGK() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_VoucherNumPerDay t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1, " +  //日产生凭证数
				"(select value from statdba.T_BillNumPerDay t " +
				  "where to_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value2 " +  //日产生单据数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
				erpData = rs.getString("value2");
				map.put("value2", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-财务管控专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 营销辅助决策
	 * @return
	 */
	public HashMap<String, String> getData_YXFZ() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_PsdssGeneReportNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //日报表生成数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-营销辅助专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 生产管理
	 * @return
	 */
	public HashMap<String, String> getData_SCGL() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		
		String sql = "";
		
		sql = "select " +
				"(select value from statdba.T_PMSTransmitDefStartRate t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1, " +  //输电缺陷流程启动率
				"(select value from statdba.T_PMSTransmitDefInputRate t " +
				  "where to_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value2, " +  //输电缺陷录入及时率
				"(select value from statdba.T_PMSTransDefStartRate t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value3, " +  //变电缺陷流程启动率
				"(select value from statdba.T_PMSTransDefInputRate t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value4, " +  //变电缺陷录入及时率
				"(select value from statdba.T_PMSDistribDefStartRate t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value5, " +  //配电缺陷流程启动率
				"(select value from statdba.T_PMSDistribDefInputRate t " +
				  "where to_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value6, " +  //配电缺陷录入及时率
				"(select value from statdba.T_PMSDistribFieldRate t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value7, " +  //配电线路必填字段完整率
				"(select value from statdba.T_PMSTransmitTicketRate t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value8, " +  //输电工作票归档率
				"(select value from statdba.T_PMSTransTicketRate t " +
				  "where to_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value9 " +  //变电工作票归档率			  
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据，数据正常
				erpData = rs.getString("value1");
				map.put("value1", erpData);
				erpData = rs.getString("value2");
				map.put("value2", erpData);
				erpData = rs.getString("value3");
				map.put("value3", erpData);
				erpData = rs.getString("value4");
				map.put("value4", erpData);
				erpData = rs.getString("value5");
				map.put("value5", erpData);
				erpData = rs.getString("value6");
				map.put("value6", erpData);
				erpData = rs.getString("value7");
				map.put("value7", erpData);
				erpData = rs.getString("value8");
				map.put("value8", erpData);
				erpData = rs.getString("value9");
				map.put("value9", erpData);				
			}
		} catch (SQLException e) {
			logger.error("查询异常-生产管理专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 安全监督
	 * @return
	 */
	public HashMap<String, String> getData_AQJD() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_BSAJFileDayNUM t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //日安全文件总数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-安全监督专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 应急管理
	 * @return
	 */
	public HashMap<String, String> getData_YJGL() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_YJEventNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(trunc(sysdate-8, 'DAY')+1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //应急事件总数(周指标)
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-应急管理专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 协同办公
	 * @return
	 */
	public HashMap<String, String> getData_XTBG() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_OAArcProcessNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //日新增公文数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-协同办公专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 远程培训
	 * @return
	 */
	public HashMap<String, String> getData_YCPX() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_YCPXLearnTimeLong t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1, " +  //累计学习总时长
				"(select value from statdba.T_YCPXLearnTime t " +
				  "where to_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value2 " +  //累计学习总次数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
				erpData = rs.getString("value2");
				map.put("value2", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-远程培训专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 人资管控
	 * @return
	 */
	public HashMap<String, String> getData_RZGK() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_rzgk_numberofunit t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(add_months(trunc(sysdate, 'MM'), -1), 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //月报表上报单位数(月指标)
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-人资管控专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 基建管控
	 * @return
	 */
	public HashMap<String, String> getData_JJGK() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_JJGKProjjds t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //日基建项目流程处理成功节点数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-基建管控专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 电网基建储备库
	 * @return
	 */
	public HashMap<String, String> getData_DWCB() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_CBKItemNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //累计项目总数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-电网基建储备库专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 农电管理
	 * @return
	 */
	public HashMap<String, String> getData_NDGL() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_NDIndexNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1, " +  //累计农电指标总数
				"(select value from statdba.T_NDDataExchangeNum t " +
				  "where to_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value2 " +  //累计数据交换次数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
				erpData = rs.getString("value2");
				map.put("value2", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-农电管理专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 经济法律
	 * @return
	 */
	public HashMap<String, String> getData_JJFL() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_ELNewContractCount t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(add_months(trunc(sysdate, 'MM'), -1), 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //月新增合同总数(月指标)
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-经济法律专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 电力交易
	 * @return
	 */
	public HashMap<String, String> getData_DLJY() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_TradeDataRecords t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //累计数据记录总数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-电力交易专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 电网规划
	 * @return
	 */
	public HashMap<String, String> getData_DWGH() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_DWGHLayOutNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //累计规划项目总数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-电网规划专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 电网前期
	 * @return
	 */
	public HashMap<String, String> getData_DWQQ() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_DWQQBackUpFileNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //累计支持性文件总数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-电网前期专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 投资计划
	 * @return
	 */
	public HashMap<String, String> getData_TZJH() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_TZJHOperationsNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //累计业务总数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-投资计划专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 企业门户
	 * @return
	 */
	public HashMap<String, String> getData_QYMH() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		
		String sql = "";
		
		sql = "select " +
				"(select value from statdba.T_PortalReceiveTreatsNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1, " +  //日待办接收总数
				"(select value from statdba.T_PortalDealedTreatsNum t " +
				  "where to_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value2, " +  //日待办完成总数
				"(select value from statdba.T_LdapSystemNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value3, " +  //支持单点登录的系统总数
				"(select value from statdba.T_LdapUserNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and corporationid = 12) value4 " +  //目录中的用户总数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据，数据正常
				erpData = rs.getString("value1");
				map.put("value1", erpData);
				erpData = rs.getString("value2");
				map.put("value2", erpData);
				erpData = rs.getString("value3");
				map.put("value3", erpData);
				erpData = rs.getString("value4");
				map.put("value4", erpData);			
			}
		} catch (SQLException e) {
			logger.error("查询异常-企业门户专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 内网网站
	 * @return
	 */
	public HashMap<String, String> getData_NWWZ() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_NewsNum t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //昨日新增新闻条数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-内网网站专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 综合查询
	 * @return
	 */
	public HashMap<String, String> getData_ZHCX() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_DCRecords t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(sysdate - 1, 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //累计数据记录总数
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-综合查询专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}

	/**
	 * 数据交换
	 * @return
	 */
	public HashMap<String, String> getData_SJJH() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String erpData = "无数据";
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = "";
		sql = "select " +
				"(select value from statdba.T_TotalTimesPerMonth t " +
				  "where To_char(time, 'yyyy-mm-dd') = To_char(add_months(trunc(sysdate, 'MM'), -1), 'yyyy-mm-dd') " +
				  "and t.corporationid = 12) value1 " +  //月平均交换次数(月指标)
			  "from dual";
		
		try {
			conn = DBUtil.getConnection("ims");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next())
			{
				//昨日历史数据
				erpData = rs.getString("value1");
				map.put("value1", erpData);
			}
		} catch (SQLException e) {
			logger.error("查询异常-数据交换专业指标数据", e);
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return map;
	}
	
	/**
	 * 在线用户历史峰值XML数据
	 * @param bean
	 * @return
	 */
	public String getOnlineCountReal_H_XML(AppMonitorBean bean) {
		
		String status = "normal";
		String[] appData = DomUtil.getAppData2(bean.getAppNo(), "value90");
		
		bean.setOnlineCountH(appData[1]);
		if (appData[0].equals("0")) {
			bean.setOnlineCountH_sta(0);
		}
		else {
			bean.setOnlineCountH_sta(1);
			status = "";
			logger.error(bean.getAppName() + "-" + CommonUtil.getMessage("OnLineCountH_MSG"));
		}
		return status;
	}
	
	/**
	 * 日登录用户历史峰值XML数据
	 * @param bean
	 * @return
	 */
	public String getDayLoginNum_H_XML(AppMonitorBean bean) {
		
		String status = "normal";
		String[] appData = DomUtil.getAppData2(bean.getAppNo(), "value91");
		
		bean.setDayLogNumH(appData[1]);
		if (appData[0].equals("0")) {
			bean.setDayLogNumH_sta(0);
		}
		else {
			if (bean.getAppNo().equals("30") || bean.getAppNo().equals("63"))
			{
				//内网网站、ERP高级应用系统，日登录用户历史峰值指标无数据
				bean.setDayLogNumH_sta(0);
				bean.setDayLogNumH("");
			}
			else {
				bean.setDayLogNumH_sta(1);
				status = "";
				logger.error(bean.getAppName() + "-" + CommonUtil.getMessage("DayLoginNumH_MSG"));
			}
		}
		return status;
	}

	/**
	 * 注册用户总数XML数据
	 * @param bean
	 * @return
	 */
	public String getRegCount_XML(AppMonitorBean bean) {
		
		String status = "normal";
		String[] appData = DomUtil.getAppData2(bean.getAppNo(), "value92");
		
		bean.setRegCount(appData[1]);
		if (appData[0].equals("0")) {
			bean.setRegCount_sta(0);
		}
		else {
			if (bean.getAppNo().equals("63"))
			{
				// 内网网站，注册用户总数指标无数据
				bean.setRegCount_sta(0);
				bean.setRegCount("");
			}
			else {
				bean.setRegCount_sta(1);
				status = "";
				logger.error(bean.getAppName() + "-" + CommonUtil.getMessage("RegCount_MSG"));
			}
		}
		return status;
	}
}
