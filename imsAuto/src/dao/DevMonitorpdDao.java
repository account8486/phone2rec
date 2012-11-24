package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.DevMonitorsdBean;

import common.DBUtil2;
import common.StringUtil;

public class DevMonitorpdDao {

	// 芜湖路机房
	public String getwuhusdList(String agentName, String devName) {
		List<DevMonitorsdBean> list = new ArrayList<DevMonitorsdBean>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = " select DEVNAME,ID,ch,VALUE,MAXALARM,MINALARM,AgentName FROM "
				+ " dbo.devvou d WHERE d.AgentName like '%芜湖路%' AND d.OrderNo NOT IN  ((select t.OrderNo FROM devvou t where t.MAXALARM IS  null "
				+ " AND t.MINALARM IS  null )) AND d.DEVNAME NOT LIKE '%空调%' AND d.DEVNAME NOT LIKE '%漏水%' AND d.DEVNAME NOT LIKE '%温湿度%' AND d.DEVNAME NOT LIKE '%消防%'";

		StringBuffer querySql = new StringBuffer(sql);
		querySql.append(" AND d.DEVNAME like '%" + devName + "%'");

		conn = DBUtil2.getConnection("test");
		StringBuffer warning = new StringBuffer();
		try {
			ps = conn.prepareStatement(querySql.toString());
			System.out.println("querySql:"+querySql);
			rs = ps.executeQuery();
			while (rs.next()) {
				DevMonitorsdBean tb = new DevMonitorsdBean();
				String strValue = rs.getString("VALUE");
				String strMaxalarm = rs.getString("MAXALARM");
				String strMinalarm = rs.getString("MINALARM");
				double value = Float.parseFloat(strValue);
				double maxalarm = 0.0;
				double minalarm = 0.0;

				

				// TODO 如果MAXALARM与MINALARM都不为空的话，比较区间
				if (StringUtil.isNotEmpty(strMaxalarm)
						&& StringUtil.isNotEmpty(strMinalarm)) {
					maxalarm = Float.parseFloat(strMaxalarm);
					minalarm = Float.parseFloat(strMinalarm);
					if (value < minalarm || value > maxalarm) {
						// tb.setFlag("1");
						warning.append(rs.getString("CH")).append("<br>");
					}
					/*
					 * if(minalarm<value&&value<maxalarm){ tb.setFlag("0"); }
					 * 
					 * if(minalarm==0.0&&value==0.0&&maxalarm==0.0){
					 * tb.setFlag("0"); }
					 */
				}
				// TODO 如果MAXALARM与MINALARM有一个为空，比较定值
				if (StringUtil.isEmpty(strMaxalarm)
						|| StringUtil.isEmpty(strMinalarm)) {
					if (StringUtil.isNotEmpty(strMaxalarm)) {
						if (value != Float.parseFloat(strMaxalarm)) {
							warning.append(rs.getString("CH")).append("<br>");
						}
					}

					if (StringUtil.isNotEmpty(strMinalarm)) {
						if (value != Float.parseFloat(strMinalarm)) {
							warning.append(rs.getString("CH")).append("<br>");
						}
					}
				}

				

			}
		} catch (Exception e) {

		}
		
		System.out.println("waring:" + warning.toString());
		return warning.toString();
	}

}
