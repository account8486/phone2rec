package com.wirelesscity.action.stats;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wirelesscity.action.base.BaseAction;
import com.wirelesscity.service.JdbcService;
import com.wirelesscity.tools.ftp.FtpService;

public class NodeAction extends BaseAction {


	private static final long serialVersionUID = -8690208905901291129L;
	
	JdbcService jdbcService;
	FtpService ftpService;
	
	
	
	public FtpService getFtpService() {
		return ftpService;
	}


	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}


	public JdbcService getJdbcService() {
		return jdbcService;
	}


	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String nodeStats() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  select  node.nodeid,node.name,net.parent_id  from oms_wireless.node node ,oms_wireless.node_net  net  ");
		sql.append("  where node.nodeid=net.node_id ");

		log.debug("sql:" + sql.toString());
		List lstResult = jdbcService.getList(sql.toString());
		
		StringBuffer buffer=new StringBuffer();
		
		for (Object obj : lstResult) {
			Map<String, Object> dataMap = (Map<String, Object>) obj;
			Set<Map.Entry<String, Object>> entrySet = dataMap.entrySet();
			Iterator<Map.Entry<String, Object>> it = entrySet.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = it.next();
				if(it.hasNext()){
					buffer.append(entry.getValue()).append("|");
				}else{
					buffer.append(entry.getValue());
				}	
			}
			
		    buffer.append("\r\n");
		}
		
		System.out.println(buffer.toString());
		
		//把StringBuffer写入文件
		String filepath="E:/devfile/test.txt";
		FileOutputStream   fo   =null; 
		DataOutputStream   ds   =   null; 
	    fo =new  FileOutputStream(filepath,   false);  
	    ds =new  DataOutputStream(fo); 
	    ds.write(buffer.toString().getBytes("utf-8"));
	    
	    ftpService.upFile(filepath, "/file/node.txt");
	    
		return SUCCESS;
	}
	

}
