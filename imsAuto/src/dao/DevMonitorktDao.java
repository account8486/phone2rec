package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.DevMonitorktBean;
import common.DBUtil2;

public class DevMonitorktDao {

	public List<DevMonitorktBean> getListByWhere(String where){
	 	List<DevMonitorktBean> list=new ArrayList<DevMonitorktBean>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = " select OrderNo,DEVNAME,ID,ch,VALUE,MINALARM,MAXALARM from dbo.devvou where 1=1 "+where;
		
		conn = DBUtil2.getConnection("test");
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				DevMonitorktBean tb=new DevMonitorktBean();
				String strValue=rs.getString("VALUE");
				String strMaxalarm=rs.getString("MAXALARM");
				String strMinalarm=rs.getString("MINALARM");
				double value=Float.parseFloat(strValue);
				double maxalarm =0.0;
				double minalarm=0.0;
				
				try{
				   maxalarm=Float.parseFloat(strMaxalarm);
				}catch(Exception e){}
				try{
				minalarm=Float.parseFloat(strMinalarm);
				}catch(Exception e){}
				
				
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
				tb.setThreshold_min(maxalarm+"");
				tb.setThreshold_max(minalarm+"");	
				
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
