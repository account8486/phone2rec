/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import bean.Devvou;
import bean.PowerData;
import common.CommonUtil;
import common.DBUtil;
import common.DBUtil2;
import common.DomUtil;

/**
 * @author Administrator
 * 机房供电监管数据访问类
 */
public class PowMonitorDao {
// select orderno, devname, id, ch, value from dbo.devvou where id in (2101036,2101037)
	
	/**
	 * 获得所要查询的设备状态信息对象Map
	 * @return 设备状态信息
	 */
	public HashMap<String, PowerData> getPowerData() {
		// 
		String strDevices = CommonUtil.getProperty("DEVICE");  // 需要巡检的设备
		String[] devices = strDevices.split(",");
		List<Devvou> devs = getVouData(devices);
		HashMap<String, PowerData> powMap = new HashMap<String,PowerData>();
		for (int i=0; i<devices.length; i++) {
			String devName = devices[i];
			PowerData power = new PowerData();
			powMap.put(devName, power);
		}
		for (Devvou vou : devs) {
			String devName = vou.getDevName();
			for (String key : powMap.keySet()) {
				if (devName.equals(key)) {
					setPowerData(powMap.get(key), vou);
				}
			}
		}
		System.out.println(devs.size());
		return powMap;
		
	}
	
	/**
	 * 将获得的状态值插入设备状态对象
	 * @param power 设备状态对象
	 * @param vou 状态值集合
	 */
	public void setPowerData(PowerData power, Devvou vou) {
		String devName = vou.getDevName();
		String val = vou.getValue();
		String id = vou.getId();
		boolean exist = DomUtil.checkCH(devName, id);
		if (exist == true) {
			if (id.equals("2101036")) {
				power.setBurden_a(val);  // 本机A相输出负载百分比
			}
			if (id.equals("2101037")) {
				power.setBurden_b(val);  // 本机B相输出负载百分比
			}
			if (id.equals("2101038")) {
				power.setBurden_c(val);  // 本机C相输出负载百分比
			}
			if (id.equals("2101004")) {
				power.setVoltage_a(val);  // 交流输出相电压A
			}
			if (id.equals("2101005")) {
				power.setVoltage_b(val);  // 交流输出相电压B
			}
			if (id.equals("2101006")) {
				power.setVoltage_c(val);  // 交流输出相电压C
			}
			if (id.equals("2101021")) {
				power.setVoltage_pa(val);  // 旁路A相电压
			}
			if (id.equals("2101022")) {
				power.setVoltage_pb(val);  // 旁路B相电压
			}
			if (id.equals("2101023")) {
				power.setVoltage_pc(val);  // 旁路C相电压
			}
			String alarmInf = power.getAlarmInf();  // 设备故障告警
			int intId = Integer.parseInt(id);
			if (intId > 2101070) {
				int intVal = Integer.parseInt(val);
				if (intVal == 1) {
					alarmInf += vou.getCh() + ";";
				}
				power.setAlarmInf(alarmInf);
			}
		}
	}
	
	
	/**
	 * 获取需要巡检的设备状态信息
	 * @param devices 需要巡检的设备
	 * @return 状态信息
	 */
	public List<Devvou> getVouData(String[] devices) {
		List<Devvou> devs = null;
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < devices.length; i++) {
			buffer.append("?,");
		}
		buffer.deleteCharAt(buffer.length()-1);
		String sql = "select orderno, devname, id, ch, value from dbo.devvou where devname in ("+buffer.toString()+")";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil2.getConnection("test");
			if (conn != null) {
				devs = new ArrayList<Devvou>();
				ps = conn.prepareStatement(sql);
				for (int i = 0; i < devices.length; i++) {
					ps.setString(i+1, devices[i]);
				}
				rs = ps.executeQuery();
				while (rs.next()) {
					 String devName = rs.getString("devname");
					 String ch = rs.getString("ch");
					 String id = rs.getString("id");
					 String val = rs.getString("value");
					 Devvou dev = new Devvou();
					 dev.setDevName(devName);
					 dev.setCh(ch);
					 dev.setId(id);
					 dev.setValue(val);
					 devs.add(dev);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(rs, ps, conn);
		}
		return devs;
	}
	
	public static void main(String[] args) {
		String strDevices = CommonUtil.getProperty("DEVICE");
		String[] devices = strDevices.split(",");
		for (int i = 0; i<devices.length; i++) {
			//System.out.println(devices[i]);
		}
		//HashMap<String, PowerData> powMap = getPowerData();
		//System.out.println(powMap.size());
	}
}
