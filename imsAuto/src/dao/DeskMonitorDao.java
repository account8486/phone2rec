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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import bean.DeskMonitorBean;
import bean.VRVData;
import common.CommonUtil;
import common.DBUtil;
import common.DBUtil2;

/**
 * @author Administrator
 * 桌面监管数据访问类
 */
public class DeskMonitorDao {
	
	private static Logger logger = Logger.getLogger(DeskMonitorDao.class);

	/**
	 * 未注册数量
	 * @param corpId
	 * @return
	 */
	public int getWeizhuce(int corpId) {
		int weizhuce = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Calendar cadar1 = Calendar.getInstance();
		Calendar cadar2 = Calendar.getInstance();
		int data = -1;
		String sql = "select * from (select value, time ltime, sysdate nowtime from statdba.T_DTURGCount where corporationid = ? and To_char(time, 'yyyy-mm-dd') = To_char(sysdate, 'yyyy-mm-dd') order by time desc) where rownum=1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, corpId);
			rs = ps.executeQuery();
			if (rs.next()) {
				Timestamp ltime = rs.getTimestamp("ltime");  //最近一条5分钟记录时间
				Timestamp nowtime = rs.getTimestamp("nowtime");  //系统时间
				data = rs.getInt("value");  //最近一条5分钟记录数据
				cadar1.setTimeInMillis(ltime.getTime());
				cadar2.setTimeInMillis(nowtime.getTime());
				cadar1.set(Calendar.SECOND, 1);
				cadar2.add(Calendar.MINUTE, -5);
				cadar2.set(Calendar.SECOND, 0);
				if (cadar1.after(cadar2)){
					//如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，且记录数据为0，说明正常
					weizhuce = data;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return weizhuce;
	}
	
	/**
	 * 违规外联
	 * @param corpId
	 * @return
	 */
	public int getWailian(int corpId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int data = -1;
		String sql = "select * from (select value, time ltime, sysdate nowtime from statdba.T_ViolationsLinkCount where corporationid = ? and To_char(time, 'yyyy-mm-dd') = To_char(sysdate-1, 'yyyy-mm-dd') order by time desc) where rownum=1";
		try {
			conn = DBUtil.getConnection("ims");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, corpId);
			rs = ps.executeQuery();
			if (rs.next()) {
				data = rs.getInt("value");  //最近一条记录数据
				if (data == 0){
					//如果数据库中最近一条记录数据为0，说明正常	
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return data;
	}	

	/**
	 * 查询单位VRV数据，加入集合
	 * @param vrv
	 * @return true 数据存在
	 */
	private boolean getData_VRV(VRVData vrv) {
		boolean dataExisted = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select organname,count1,count2,registercount,count2-registercount CC from dbo.mapregion where organname=?";
		try {
			conn = DBUtil2.getConnection("vrv");
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, vrv.getCorpName());
				rs = ps.executeQuery();
				if (rs.next()) {
					int count1 = rs.getInt("count1"); // 杀毒软件安装数
					int count2 = rs.getInt("count2"); // 应注册计算机数
					int registerCount = rs.getInt("registercount"); // 已注册计算机数
					int unReg = rs.getInt("CC"); // 未注册计算机数
					vrv.setAvCount(count1);
					vrv.setRegisteredCount(registerCount);
					vrv.setRegisterCount(count2);
					vrv.setUnregisteredCount(unReg);
					dataExisted = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("ERROR:" + e.getMessage());
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return dataExisted;
	}

	/**
	 * 查询所有VRV数据，加入集合
	 * @return list
	 */
	public List<VRVData> getVRVData() {
		List<VRVData> list = null;
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = DBUtil2.getConnection("vrv");
			if (conn != null) {
				list = new ArrayList<VRVData>();
				sm = conn.createStatement();
				
				// 注册数(已、应、未) 杀毒安装数
				sql = "select organname,count1,count2,registercount,count2-registercount CC from dbo.mapregion order by regid asc";
				rs = sm.executeQuery(sql);
				while (rs.next()) {
					VRVData vrv = new VRVData();
					String corpName = rs.getString("organname"); // 公司名称
					int count1 = rs.getInt("count1"); // 杀毒软件安装数
					int count2 = rs.getInt("count2"); // 应注册计算机数
					int registerCount = rs.getInt("registercount"); // 已注册计算机数
					int unReg = rs.getInt("CC"); // 未注册计算机数
					vrv.setCorpName(corpName);
					vrv.setAvCount(count1);
					vrv.setRegisteredCount(registerCount);
					vrv.setRegisterCount(count2);
					vrv.setUnregisteredCount(unReg);
					list.add(vrv);
				}
				
				// 违规外联(本部)
				sql = "select count(errorid) counts from dbo.errormessage where errortype = 0 and datediff(day, errortime, getdate()) = 0";
				rs = sm.executeQuery(sql);
				if (rs.next()) {
					int counts = rs.getInt("counts"); // 违规外联数
					list.get(0).setIllegalLink(counts);
				}
				
				// 违规外联(地市公司)
				sql = "select count(organname) counts, organname from dbo.mapwarning where errtype = 0 group by organname";
				rs = sm.executeQuery(sql);
				while (rs.next()) {
					String corpName = rs.getString("organname");
					int counts = rs.getInt("counts"); // 违规外联数
					for (VRVData vrv : list) {
						if (corpName.equals(vrv.getCorpName())) {
							vrv.setIllegalLink(counts);
							break;
						}
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR3:" + Calendar.getInstance().getTime().toString() + e.getMessage());
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return list;
	}	

	/**
	 * 将VRV数据添加入DeskMonitorBean
	 * @param bean 桌面监管Bean
	 * @return 数据状态 normal正常
	 */
	private String setVRVData(DeskMonitorBean bean, VRVData vrv) {
		String isNormal = "normal";
		String parten = "#.##";
		DecimalFormat decimal = new DecimalFormat(parten);
		int tmpValue;
		
		bean.setYingzhuce(String.valueOf(vrv.getRegisterCount())); // 应注册
		bean.setZhuce(String.valueOf(vrv.getRegisteredCount())); // 已注册
		bean.setUnregisteredCount(String.valueOf(vrv.getUnregisteredCount())); // 未注册
		double zcl = (double)vrv.getRegisteredCount()/vrv.getRegisterCount(); // 终端注册率
		double azl = (double)vrv.getAvCount()/vrv.getRegisterCount(); // 杀毒软件安装率
		bean.setIllegalLink(String.valueOf(vrv.getIllegalLink())); // 违规外联VRV
		bean.setZhucelv(decimal.format(zcl * 100) + "%");
		bean.setAvlv(decimal.format(azl * 100) + "%");
//		if (vrv.getUnregisteredCount() > 0) {
//			tmpValue = bean.getUnregisteredCount_sta();
//			bean.setUnregisteredCount_sta(1);
//			isNormal = "";
//		}
		if (zcl <1) {
			tmpValue = bean.getZhucelv_sta();
			bean.setZhucelv_sta(1);
			isNormal = "";
		}
		if (azl <1) {
			tmpValue = bean.getAvlv_sta();
			bean.setAvlv_sta(1);
			isNormal = "";
		}
		if (vrv.getIllegalLink() > 0) {
			tmpValue = bean.getIllegalLink_sta();
			bean.setIllegalLink_sta(1);
			isNormal = "";
		}
	
		return isNormal;
	}

	/**
	 * 讲DeskMonitorBean中VRV数据状态设置为无数据
	 * @param bean
	 * @return
	 */
	private String clearVRVData(DeskMonitorBean bean) {

		int tmpValue;
		tmpValue = bean.getYingzhuce_sta();
		bean.setYingzhuce_sta(-1);
		bean.setYingzhuce("无数据");
		
		tmpValue = bean.getZhuce_sta();
		bean.setZhuce_sta(-1);
		bean.setZhuce("无数据");

		tmpValue = bean.getUnregisteredCount_sta();
		bean.setUnregisteredCount_sta(-1);
		bean.setUnregisteredCount("无数据");
		
		tmpValue = bean.getZhucelv_sta();
		bean.setZhucelv_sta(-1);
		bean.setZhucelv("无数据");
		
		tmpValue = bean.getAvlv_sta();
		bean.setAvlv_sta(-1);
		bean.setAvlv("无数据");
		
		tmpValue = bean.getIllegalLink_sta();
		bean.setIllegalLink_sta(-1);
		bean.setIllegalLink("无数据");

		return null;
	}

	/**
	 * 查询VRV违规外联数，加入集合
	 * @param vrv
	 */
	private boolean getIllegalLink(VRVData vrv) {
		boolean dataExisted = false;
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String sql = "";
		if (vrv.getCorpName().equals("安徽电力公司")) {
			sql = "select count(errorid) counts from dbo.errormessage where errortype = 0";
		}
		else {
			sql = "select count(errid) counts from dbo.mapwarning where errtype = 0 and organname = '" + vrv.getCorpName() + "'";
		}
		try {
			conn = DBUtil2.getConnection("vrv");
			if (conn != null) {
				sm = conn.createStatement();
				rs = sm.executeQuery(sql);
				if (rs.next()) {
					int counts = rs.getInt("counts"); // 违规外联数
					vrv.setIllegalLink(counts);
				}
				dataExisted = true;
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("ERROR:" + e.getMessage());
		} finally {
			DBUtil.release(rs, sm, conn);
		}
		return dataExisted;
	}

	/**
	 * 巡检桌面监管状态，并返回结果
	 * @param bean
	 * @param vrvList
	 * @return
	 */
	public String setBean2(DeskMonitorBean bean, List<VRVData> vrvList) {
		int corpId = bean.getCorpId();
		String isNormal = "normal";
		
		// 处理VRV数据
		String corpName = CommonUtil.ID_Name.get(bean.getCorpId());
		if (vrvList != null) {
			for (VRVData vrv : vrvList) {
				if (corpName.equals(vrv.getCorpName())) {
					isNormal = setVRVData(bean, vrv);
					break;
				}
			}
		}
		else {
			clearVRVData(bean);
			isNormal = "";
		}
		
		// 违规外联IMS
		int wail = getWailian(corpId);
		bean.setWailian(String.valueOf(wail));
		if (wail == -1){
			bean.setWailian_sta(-1);
			bean.setWailian("无数据");
			isNormal = "";
			logger.error(bean.getCorpName() + "-" + CommonUtil.getProperty("wailian_msg"));
		}
		else if (wail > 0) {
			bean.setWailian_sta(1);
			isNormal = "";
		}
		
		return isNormal;
	}
	
	/**
	 * winxp终端数
	 * @param deptId
	 * @return
	 */
//	public int getWinxp(int corpId) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		int winxp = -1;
//		String sql = "select value from statdba.T_TerminalCountByOS where corporationid = ? and dimension_column1=7 and To_char(time, 'yyyy-mm-dd') = To_char(sysdate-1, 'yyyy-mm-dd')";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				winxp = rs.getInt("value");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return winxp;
//	}
	
	/**
	 * win2000终端数
	 * @param corpId
	 * @return
	 */
//	public int getWin2000(int corpId) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		int win2000 = -1;
//		String sql = "select value from statdba.T_TerminalCountByOS where corporationid = ? and time like sysdate-1 and dimension_column1=5";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				win2000 = rs.getInt("value");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return win2000;
//	}
	
	/**
	 * win2003终端数
	 * @param corpId
	 * @return
	 */
//	public int getWin2003(int corpId) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		int win2003 = -1;
//		String sql = "select value from statdba.T_TerminalCountByOS where corporationid = ? and time like sysdate-1 and dimension_column1=6";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				win2003 = rs.getInt("value");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return win2003;
//	}
	
	/**
	 * winVista终端数
	 * @param corpId
	 * @return
	 */
//	public int getWinVista(int corpId) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		int winvista = -1;
//		String sql = "select value from statdba.T_TerminalCountByOS where corporationid = ? and time like sysdate-1 and dimension_column1=8";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				winvista = rs.getInt("value");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return winvista;
//	}
	
	/**
	 * 补丁安装率
	 * @param corpId
	 * @return
	 */
//	public double getBuding(int corpId) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		double buding = -1;
//		String sql = "select value from statdba.T_PatchInstallRate where corporationid = ? and To_char(time, 'yyyy-mm-dd') = To_char(sysdate-1, 'yyyy-mm-dd')";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				buding = rs.getDouble("value");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return buding;
//	}
	
	/**
	 * 终端使用率
	 * @param corpId
	 * @return
	 */
//	public double getZhongduan(int corpId) {
//		double zhongduan = -1;
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Calendar cadar1 = Calendar.getInstance();
//		Calendar cadar2 = Calendar.getInstance();
//		String sql = "select * from (select value, time ltime, sysdate nowtime from statdba.T_LANDesktopUtilityRate where corporationid = ? and To_char(time, 'yyyy-mm-dd') = To_char(sysdate, 'yyyy-mm-dd') order by time desc) where rownum = 1";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				Timestamp ltime = rs.getTimestamp("ltime");  //最近一条5分钟记录时间
//				Timestamp nowtime = rs.getTimestamp("nowtime");  //系统时间
//				cadar1.setTimeInMillis(ltime.getTime());
//				cadar2.setTimeInMillis(nowtime.getTime());
//				cadar1.set(Calendar.SECOND, 1);
//				cadar2.add(Calendar.MINUTE, -5);
//				cadar2.set(Calendar.SECOND, 0);
//				if (cadar1.after(cadar2)){
//					zhongduan = rs.getDouble("value");  //如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，说明正常
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return zhongduan;
//	}
	
	/**
	 * 在线桌面终端
	 * @param corpId
	 * @return
	 */
//	public int getZhuomian(int corpId) {
//		int zhuomian = -1;
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Calendar cadar1 = Calendar.getInstance();
//		Calendar cadar2 = Calendar.getInstance();
//		String sql = "select * from (select value, time ltime, sysdate nowtime from statdba.T_NetworkDesktopOnlineCount where corporationid = ? and To_char(time, 'yyyy-mm-dd') = To_char(sysdate, 'yyyy-mm-dd') order by time desc) where rownum=1";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				Timestamp ltime = rs.getTimestamp("ltime");  //最近一条5分钟记录时间
//				Timestamp nowtime = rs.getTimestamp("nowtime");  //系统时间
//				cadar1.setTimeInMillis(ltime.getTime());
//				cadar2.setTimeInMillis(nowtime.getTime());
//				cadar1.set(Calendar.SECOND, 1);
//				cadar2.add(Calendar.MINUTE, -5);
//				cadar2.set(Calendar.SECOND, 0);
//				if (cadar1.after(cadar2)){
//					zhuomian = rs.getInt("value");  //如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，说明正常
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return zhuomian;
//	}
	
	/**
	 * 登录终端数
	 * @param corpId
	 * @return
	 */
//	public int getDenglu(int corpId) {
//		int denglu = -1;
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Calendar cadar1 = Calendar.getInstance();
//		Calendar cadar2 = Calendar.getInstance();
//		String sql = "select * from (select value, time ltime, sysdate nowtime from statdba.T_LANDesktopCount where corporationid = ? order by time desc) where rownum=1";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				Timestamp ltime = rs.getTimestamp("ltime");  //最近一条5分钟记录时间
//				Timestamp nowtime = rs.getTimestamp("nowtime");  //系统时间
//				cadar1.setTimeInMillis(ltime.getTime());
//				cadar2.setTimeInMillis(nowtime.getTime());
//				cadar1.set(Calendar.SECOND, 1);
//				cadar2.add(Calendar.MINUTE, -5);
//				cadar2.set(Calendar.SECOND, 0);
//				if (cadar1.after(cadar2)){
//					//如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，说明正常
//					denglu = rs.getInt("value");
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return denglu;
//	}
	
	/**
	 * 应注册终端数
	 * @param corpId
	 * @return
	 */
//	public int getYingzhuce(int corpId) {
//		int zcs = -1;
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Calendar cadar1 = Calendar.getInstance();
//		Calendar cadar2 = Calendar.getInstance();
//		String sql = "select * from (select value, time ltime, sysdate nowtime from statdba.T_NetworkDTNeedRegCount where corporationid = ? order by time desc) where rownum=1";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				Timestamp ltime = rs.getTimestamp("ltime");  //最近一条5分钟记录时间
//				Timestamp nowtime = rs.getTimestamp("nowtime");  //系统时间
//				cadar1.setTimeInMillis(ltime.getTime());
//				cadar2.setTimeInMillis(nowtime.getTime());
//				cadar1.set(Calendar.SECOND, 1);
//				cadar2.add(Calendar.MINUTE, -5);
//				cadar2.set(Calendar.SECOND, 0);
//				if (cadar1.after(cadar2)){
//					//如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，说明正常
//					zcs = rs.getInt("value");
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return zcs;
//	}
	
	/**
	 * 注册终端数
	 * @param corpId
	 * @return
	 */
//	public int getZhuce(int corpId) {
//		int zhuce = -1;
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Calendar cadar1 = Calendar.getInstance();
//		Calendar cadar2 = Calendar.getInstance();
//		String sql = "select * from (select value, time ltime, sysdate nowtime from statdba.T_DesktopTerminalRGCount where corporationid = ? order by time desc) where rownum=1";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				Timestamp ltime = rs.getTimestamp("ltime");  //最近一条5分钟记录时间
//				Timestamp nowtime = rs.getTimestamp("nowtime");  //系统时间
//				cadar1.setTimeInMillis(ltime.getTime());
//				cadar2.setTimeInMillis(nowtime.getTime());
//				cadar1.set(Calendar.SECOND, 1);
//				cadar2.add(Calendar.MINUTE, -5);
//				cadar2.set(Calendar.SECOND, 0);
//				if (cadar1.after(cadar2)){
//					//如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，说明正常
//					zhuce = rs.getInt("value");
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return zhuce;
//	}
	
	/**
	 * 终端注册率
	 * @param corpId
	 * @return
	 */
//	public double getZhucelv(int corpId) {
//		double zcl = -1;
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Calendar cadar1 = Calendar.getInstance();
//		Calendar cadarNow = Calendar.getInstance();
//		String sql = "select * from (select value, time ltime, sysdate nowtime from statdba.T_DesktopTerminalRGRate where corporationid = ? order by time desc) where rownum=1";
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				Timestamp ltime = rs.getTimestamp("ltime");  //最近一条5分钟记录时间
//				Timestamp nowtime = rs.getTimestamp("nowtime");  //系统时间
//				cadarNow.setTimeInMillis(nowtime.getTime());
//				cadarNow.add(Calendar.MINUTE, -5);
//				cadarNow.set(Calendar.SECOND, 0);
//
//				cadar1.setTimeInMillis(ltime.getTime());
//				cadar1.set(Calendar.SECOND, 1);
//				if (cadar1.after(cadarNow)){
//					//如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，说明正常
//					zcl = rs.getDouble("value");
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//		return zcl;
//	}
	
	/**
	 * 五分钟数据
	 * @param corpId
	 * @param deskBean
	 */
//	public void getFiveMinuteData(int corpId, DeskMonitorBean deskBean) {
//		Connection conn = null;
//		Statement sm = null;
//		ResultSet rs = null;
//		Calendar cadarNow = Calendar.getInstance();
//		Calendar cadar1 = Calendar.getInstance();
//		Timestamp atime = null;
//		Timestamp btime = null;
//		Timestamp ctime = null;
//		Timestamp dtime = null;
//		int a = 0;
//		String sql;
//		sql = "select (select time from (select time from statdba.T_LANDesktopCount where corporationid = "+corpId+" order by time desc) where rownum=1) atime, " +
//				"(select time from (select time from statdba.T_NetworkDTNeedRegCount where corporationid = "+corpId+" order by time desc) where rownum=1) btime, " +
//				"(select time from (select time from statdba.T_DesktopTerminalRGCount where corporationid = "+corpId+" order by time desc) where rownum=1) ctime, " +
//				"(select time from (select time from statdba.T_DesktopTerminalRGRate where corporationid = "+corpId+" order by time desc) where rownum=1) dtime, " +
//				"sysdate nowtime from dual";
//		try {
//			conn = DBUtil.getConnection("ims");
//			sm = conn.createStatement();
//			rs = sm.executeQuery(sql);
//			if (rs.next()) {
//				Timestamp nowtime = rs.getTimestamp("nowtime");  //系统时间
//				cadarNow.setTimeInMillis(nowtime.getTime());
//				cadarNow.add(Calendar.MINUTE, -5);
//				cadarNow.set(Calendar.SECOND, 0);
//				
//				atime = rs.getTimestamp("atime");  //最近一条5分钟登录终端数记录时间
//				btime = rs.getTimestamp("btime");  //最近一条5分钟应注册终端数记录时间
//				ctime = rs.getTimestamp("ctime");  //最近一条5分钟注册终端数记录时间
//				dtime = rs.getTimestamp("dtime");  //最近一条5分钟终端注册率记录时间
//				
//				if (atime != null){
//					cadar1.setTimeInMillis(atime.getTime());
//					cadar1.set(Calendar.SECOND, 1);
//					if (cadar1.after(cadarNow)){
//						//deskBean.setDenglu("normal");  //如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，说明正常
//					}
//					else {
//						a = deskBean.getDenglu();
//						deskBean.setDenglu(++a);
//					}
//				}
//				if (btime != null){
//					cadar1.setTimeInMillis(btime.getTime());
//					cadar1.set(Calendar.SECOND, 1);
//					if (cadar1.after(cadarNow)){
//						//deskBean.setYingzhuce("normal");  //如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，说明正常
//					}
//				}
//				if (ctime != null){
//					cadar1.setTimeInMillis(ctime.getTime());
//					cadar1.set(Calendar.SECOND, 1);
//					if (cadar1.after(cadarNow)){
//						//deskBean.setZhuce("normal");  //如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，说明正常
//					}
//				}
//				if (dtime != null){
//					cadar1.setTimeInMillis(dtime.getTime());
//					cadar1.set(Calendar.SECOND, 1);
//					if (cadar1.after(cadarNow)){
//						//deskBean.setZhucelv("normal");  //如果当前时间减去5分钟小于数据库中最近一条5分钟记录时间，说明正常
//					}
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, sm, conn);
//		}
//	}
	
	/**
	 * 获取各操作系统终端数
	 * @param corpId
	 * @param deskBean
	 */
//	public void getTerminalCount(int corpId, DeskMonitorBean deskBean) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		String sql = "select (select value from  statdba.T_TerminalCountByOS where corporationid = ? and To_char(time, 'yyyy-mm-dd') = To_char(sysdate-1, 'yyyy-mm-dd') and dimension_column1=7) winxp, " +
//				"(select value from  statdba.T_TerminalCountByOS where corporationid = ? and To_char(time, 'yyyy-mm-dd') = To_char(sysdate-1, 'yyyy-mm-dd') and dimension_column1=5) win2000, " +
//				"(select value from statdba.T_TerminalCountByOS where corporationid = ? and To_char(time, 'yyyy-mm-dd') = To_char(sysdate-1, 'yyyy-mm-dd') and dimension_column1=6) win2003, " +
//				"(select value from statdba.T_TerminalCountByOS where corporationid = ? and To_char(time, 'yyyy-mm-dd') = To_char(sysdate-1, 'yyyy-mm-dd') and dimension_column1=8) winvista, " +
//				"sysdate nowtime from dual";
//		
//		try {
//			conn = DBUtil.getConnection("ims");
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, corpId);
//			ps.setInt(2, corpId);
//			ps.setInt(3, corpId);
//			ps.setInt(4, corpId);
//			rs = ps.executeQuery();
//			if (rs.next()){
//				String winxp = rs.getString("winxp");
//				if (winxp != null){
//					//deskBean.setWinxp("normal");
//				}
//				String win2000 = rs.getString("win2000");
//				if (win2000 != null){
//					//deskBean.setWin2000("normal");
//				}
//				String win2003 = rs.getString("win2003");
//				if (win2003 != null){
//					//deskBean.setWin2003("normal");
//				}
//				String winvista = rs.getString("winvista");
//				if (winvista != null){
//					//deskBean.setWinVista("normal");
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBUtil.release(rs, ps, conn);
//		}
//	}
	
	/**
	 * 巡检桌面监管状态，并返回结果
	 * @param bean
	 * @return
	 */
	public String setBean(DeskMonitorBean bean, List<VRVData> vrvList) {
		int corpId = bean.getCorpId();
		String isNormal = "normal";
		
		// 处理VRV数据
		String corpName = CommonUtil.ID_Name.get(bean.getCorpId());
		if (vrvList != null) {
			for (VRVData vrv : vrvList) {
				if (corpName.equals(vrv.getCorpName())) {
					isNormal = setVRVData(bean, vrv);
					break;
				}
			}
		}
		else {
			clearVRVData(bean);
			isNormal = "";
		}
		
		// 违规外联IMS
		int wail = getWailian(corpId);
		bean.setWailian(String.valueOf(wail));
		if (wail == -1){
			bean.setWailian_sta(-1);
			bean.setWailian("无数据");
			isNormal = "";
			logger.error(bean.getCorpName() + "-" + CommonUtil.getProperty("wailian_msg"));
		}
		else if (wail > 0) {
			bean.setWailian_sta(1);
			isNormal = "";
		}
		
		return isNormal;
	}
}
