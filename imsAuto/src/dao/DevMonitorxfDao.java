package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.DevMonitorxfBean;
import common.DBUtil2;

public class DevMonitorxfDao 

{
    public List<DevMonitorxfBean> getListByWhere(String where){
	 	List<DevMonitorxfBean> list=new ArrayList<DevMonitorxfBean>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = " select OrderNo,DEVNAME,ID,ch,VALUE,MINALARM,MAXALARM from dbo.devvou where 1=1 "+where;
		
		conn = DBUtil2.getConnection("test");
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				DevMonitorxfBean tb=new DevMonitorxfBean();
				String strValue=rs.getString("VALUE");
				String strMaxalarm=rs.getString("MAXALARM");
				String strMinalarm=rs.getString("MINALARM");
				double value=Float.parseFloat(strValue);
				double maxalarm=Float.parseFloat(strMaxalarm);
				double minalarm=Float.parseFloat(strMinalarm);
				if(value<minalarm||value>maxalarm){
					tb.setFlag("1");
				}
				if(minalarm<value&&value>maxalarm){
					tb.setFlag("0");
				}
				tb.setOrderno(rs.getInt("OrderNo"));
				tb.setDevname(rs.getString("DEVNAME"));
				tb.setId(rs.getInt("ID"));
				tb.setCh(rs.getString("ch"));
				tb.setValue(rs.getString("VALUE"));
				tb.setThreshold_min(rs.getString("MINALARM"));
				tb.setThreshold_max(rs.getString("MAXALARM"));	
				
				list.add(tb);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		return list;
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
