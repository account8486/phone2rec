package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.DevMonitorsdBean;
import common.DBUtil2;

public class DevMonitorsdDao 

{
	//芜湖路机房湿度
    public List<DevMonitorsdBean> getwuhusdList(){
	 	List<DevMonitorsdBean> list=new ArrayList<DevMonitorsdBean>();
	 
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select DEVNAME,ID,ch,VALUE,MAXALARM,MINALARM,AgentName FROM dbo.devvou d WHERE d.DEVNAME LIKE '%温湿度%' and d.ch like '%湿度%' and d.ch not like '%指示灯%' and d.ch not like '%通讯%'  and AgentName like '%芜湖路%'  ";
		
		conn = DBUtil2.getConnection("test");
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				DevMonitorsdBean tb=new DevMonitorsdBean();
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
				if(minalarm<value&&value<maxalarm){
					tb.setFlag("0");
				}
				if(minalarm==0.0&&value==0.0&&maxalarm==0.0){
					tb.setFlag("0");
				}
				//tb.setOrderno(rs.getInt("OrderNo"));
				tb.setDevname(rs.getString("DEVNAME"));
				tb.setId(rs.getInt("ID"));
				tb.setCh(rs.getString("ch"));
				tb.setValue(rs.getString("VALUE"));
				tb.setAgentname(rs.getString("AgentName"));
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
    
  //黄山路机房湿度
    public List<DevMonitorsdBean> gethuangshansdList(){
	 	List<DevMonitorsdBean> list=new ArrayList<DevMonitorsdBean>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select DEVNAME,ID,ch,VALUE,MAXALARM,MINALARM,AgentName FROM dbo.devvou d WHERE d.DEVNAME LIKE '%温湿度%' and d.ch like '%湿度%' and d.ch not like '%指示灯%' and d.ch not like '%通讯%'  and AgentName like '%黄山路%'  ";
		
		conn = DBUtil2.getConnection("test");
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				DevMonitorsdBean tb=new DevMonitorsdBean();
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
				if(minalarm<value&&value<maxalarm){
					tb.setFlag("0");
				}
				if(minalarm==0.0&&value==0.0&&maxalarm==0.0){
					tb.setFlag("0");
				}
				//tb.setOrderno(rs.getInt("OrderNo"));
				tb.setDevname(rs.getString("DEVNAME"));
				tb.setId(rs.getInt("ID"));
				tb.setCh(rs.getString("ch"));
				tb.setValue(rs.getString("VALUE"));
				tb.setAgentname(rs.getString("AgentName"));
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
