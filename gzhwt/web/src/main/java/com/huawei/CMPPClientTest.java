/**
 * CMPP2.0 客户端应用程序(基于华为API)
 * 1. 支持短信收发
 * 2. 支持彩信收发
 *
 * @author: yangxf
 * @date: 20090731
 */

package com.huawei;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitRepMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Cfg;
import com.huawei.opencmpp.hw.AyncSMProxy;
import com.huawei.opencmpp.hw.AyncSMProxyEventListener;

public class CMPPClientTest {
	public static final String CONFIG_FILE="../conf/config.cf";
	public static final String SMPROXY_FILE="smproxy.xml";
	
	// 业务数据库IP
	public String biz_database_ip;
	// 业务数据库名称
	public String biz_database_name;
	// 业务数据库账号
	public String biz_database_user;
	// 业务数据库密码
	public String biz_database_passwd;
	// 业务短信分号
	public String biz_sms_code = "10658234";
	
	// 业务数据库连接
	public static Connection bizConn;

	public Args args=null;
	public AyncSMProxy myProxy=null;
	

	/**
	* 发送短信字段
	*
	* 1  @pk_Total 				相同msg_Id消息总条数
	* 2  @pk_Number 			相同msg_Id的消息序号
	* 3  @registered_Delivery 	是否要求返回状态报告
	* 4  @msg_Level  			信息级别
	* 5  @service_Id 			业务类型
	* 6  @fee_UserType 			计费用户类型字段
	* 7  @fee_Terminal_Id 		被计费用户的号码
	* 8  @tp_Pid 				GSM协议类型
	* 9  @tp_Udhi 				GSM协议类型
	* 10 @msg_Fmt 				消息格式
	* 11 @msg_Src 				消息内容来源
	* 12 @fee_Type 				资费类别
	* 13 @fee_Code 				资费代码(以分为单位)
	* 14 @valid_Time 			存活有效期
	* 15 @at_Time 				定时发送时间
	* 16 @src_Terminal_Id 		源号码
	* 17 @dest_Terminal_Id 		接收短信的MSISDN号码
	* 18 @msg_Content 			消息内容
	* 19 @reserve    			保留
	*/
	
	/* 请设置为1 */
	public int pk_Total=1;
	
	/* 请设置为1 */
	public int pk_Number=1;
	
	/* 1:需要状态报告,0:不需要状态报告 */
	public int registered_Delivery=1;
	
	/* 信息级别 */
	public int msg_Level=0;
	
	/* 业务类别 */
	public String service_Id="MGZ4019902";
	
   /* 计费用户类型字段，
    *  0：对目的终端MSISDN计费；
    *  1：对源终端MSISDN计费；
    *  2：对SP计费;
    *  3:表示本字段无效，对谁计费参见psFeeTerminalID参数。 
    */
	public int fee_UserType=0;
	
	/* 被计费用户的号码（如本字节填空，则表示本字段无效，对谁计费参见byFeeUserType字段。本字段与Fee_UserType字段互斥）
    */
	public String fee_Terminal_Id="";
	
	/*
    GSM协议类型。详细是解释请参考GSM03.40中的9.2.3.9
    此值请填0
    */
	public int tp_Pid=0;
	
	/*
    GSM协议类型。表示是否有头结构，详细是解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐
    请填写为0。
    */
	public int tp_Udhi=0;
	
	/*
    信息格式  0：ASCII串  3：短信写卡操作  4：二进制信息  8：UCS2编码 15：含GB汉字 ......
    */
	public int msg_Fmt=15;
	
	/* 信息内容来源(SP_ID) */
	public String msg_Src="912300";
	
	/* 资费类别 */
	public String fee_Type="01";
	
	/* 资费代码（以分为单位）*/
	public String fee_Code="999";
	
	/* 有效期，一般填空 */
	public java.util.Date valid_Time=null;
	
    /* 定时发送时间，一般填空 */
	public java.util.Date at_Time=null;
	
	/* 源终端MSISDN号码, 即此短消息的主叫地址*/
	public String src_Terminal_Id="10658234";
		
	public String reserve="";
    
	
	final List<CMPPDeliverMessage> receiveList = new ArrayList<CMPPDeliverMessage>();
	final List<CMPPSubmitMessage> successList = new ArrayList<CMPPSubmitMessage>();
	final List<CMPPSubmitMessage> failedList = new ArrayList<CMPPSubmitMessage>();
	AyncSMProxyEventListener listener = new AyncSMProxyEventListener() {
		public void onReceive(CMPPDeliverMessage deliver) {
			System.out.println("RECEIVE MSG : " + deliver.toString());
			receiveList.add(deliver);
		}

		public void onSubmitSuccess(CMPPSubmitMessage submit,
				CMPPSubmitRepMessage submit_resp) {
			System.out.println("SEND SUCCESS : " + submit.getSequenceId());
			successList.add(submit);
		}

		public void onSubmitFailed(CMPPSubmitMessage submit,
				CMPPSubmitRepMessage submit_resp) {
			System.out.println("SEND FAILED : " + submit.getSequenceId()
					+ ", RESULT " + submit_resp.getResult());
			failedList.add(submit);
		}

	};
	
	
	public CMPPClientTest() {
		//initConfig();
		
		//initDB();
		
		try {
			args = new Cfg(SMPROXY_FILE, false).getArgs("CMPP3Connect");
			myProxy = new AyncSMProxy(args,listener);
			src_Terminal_Id=biz_sms_code;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void initConfig() {
		Properties p = new Properties(); 
		try {
			//p.load(new FileInputStream(CONFIG_FILE)); 
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
//		biz_database_ip		=p.getProperty("BIZ_DATABASE_IP");
//		biz_database_name	=p.getProperty("BIZ_DATABASE_NAME");
//		biz_database_user	=p.getProperty("BIZ_DATABASE_USER");
//		biz_database_passwd	=p.getProperty("BIZ_DATABASE_PASSWD");
//		biz_sms_code		=p.getProperty("BIZ_SMS_CODE");
	
		System.out.println("--------------config.cf-------------------");
		System.out.println("BIZ_DATABASE_IP:" 		+ biz_database_ip);
		System.out.println("BIZ_DATABASE_NAME:" 		+ biz_database_name);
		System.out.println("BIZ_DATABASE_USER:" 		+ biz_database_user);
		System.out.println("BIZ_DATABASE_PASSWD:" 	+ biz_database_passwd);
		System.out.println("BIZ_SMS_CODE:" 			+ biz_sms_code);
		System.out.println("------------------------------------------");
	}
	
	public void initDB() {
		System.out.println("--------------BIZ DB Connection-------------------");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			if(bizConn != null) {
				bizConn.close();
				bizConn=null;
			}
			
			//连接数据库
			String dbUrl="jdbc:mysql://" + biz_database_ip + ":3306/" + biz_database_name + "?user=" + biz_database_user + "&password=" + biz_database_passwd + "&useUnicode=true&characterEncoding=utf-8";
			bizConn = DriverManager.getConnection(dbUrl);
			System.out.println("BIZ DATABASE:" + biz_database_ip + " Connection success!");
		}catch(Exception se) {
			//连接失败
			se.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("------------------------------------------");
	}
	
	public void reInit() {
		initDB();
		/*
		myProxy.close();
		myProxy=null;
		myProxy = new MySMProxy(this, args);
		*/
	}
	String mobile = "";
	public static void main(String[] args) {
		CMPPClientTest client=new CMPPClientTest();
		client.mobile = args[1].trim();
	    client.startApp();
	}
	
	public void startApp() {
		int clockTimer=0;
    	while(true) {
    		// 查询SMProxy与ISMG的TCP连接状态
			String state = myProxy.getConnState();
			if(state != null) {
				// 与短信网关的链接异常
				System.out.println("重连。。");
				myProxy.close();
				myProxy=null;
				myProxy = new AyncSMProxy(args,listener);
			}
			
			try {
				//等待30秒，再发送短信
				System.out.println("等待30秒，再发送短信");
	    		Thread.sleep(30000);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
    		//sendSms();
			
			byte[] msg_Content = "abcd".getBytes();
			try {
				//msg_Content = new String(new String("Hello World你好短信验证码 124df45488").getBytes(),"GBK").getBytes();
//				msg_Content=new String("你好 测试中文你好 测试中文你好eeeefffabcd@#@#$$#*&^(*)()*()那你你快乐".getBytes("GB2312")).getBytes();
				
				msg_Content = "你好 测试中文你好 测试中文你好eeeefffabcd@#@#$$#*&^(*)()*()那你你快乐".getBytes("GBK");
				
				
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String[] dest_Terminal_Id = new String[]{mobile};
			CMPPSubmitMessage submitMsg = new CMPPSubmitMessage(
					pk_Total, 
					pk_Number, 
					registered_Delivery, 
					msg_Level, 
					service_Id, 
					fee_UserType, 
					fee_Terminal_Id, 
					tp_Pid, 
					tp_Udhi, 
					msg_Fmt, 
					msg_Src, 
					fee_Type, 
					fee_Code, 
					null, 
					null, 
					src_Terminal_Id, 
					dest_Terminal_Id, 
					msg_Content, 
					reserve
				);
				
				try {
					
					myProxy.send_by_async(submitMsg);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		
    		if(clockTimer > 360000) {
	    		reInit();
	    		clockTimer=0;
	    	}
	    	
	    	clockTimer += 5;
        }
	}
	
	
	public void sendSms() {
		Statement stmt=null;
		Statement stmt_up=null;
		int maxlength=70;
		String tmpsms = "";
		
		String sql="SELECT smsid, smscontent, svcnum, createtime, mmsflag FROM t_smssend WHERE state='0' order by createtime LIMIT 20";
		try {
			stmt=bizConn.createStatement();
			stmt_up=bizConn.createStatement();
					
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				String smsid=rs.getString(1);
				String smscontent=rs.getString(2);
				String svcnum=rs.getString(3);
				String createtime=rs.getString(4);
				String mmsflag=rs.getString(5);
				String tmpcontent = smscontent;
				tmpsms = "";
				System.out.println("send: " + smsid + "," + smscontent + "," + svcnum + "," + createtime);
				
				String[] dest_Terminal_Id={svcnum};
				while(smscontent.length() > 0){
					if(smscontent.length() > maxlength){
						tmpsms = smscontent.substring(maxlength);
						smscontent = smscontent.substring(0,maxlength);
					}else
						tmpsms = "";
					byte[] msg_Content=new String(smscontent.getBytes("GB2312")).getBytes();
					System.out.println(new String(smscontent.getBytes("GB2312")));
					
					CMPPSubmitMessage submitMsg = new CMPPSubmitMessage(
						pk_Total, 
						pk_Number, 
						registered_Delivery, 
						msg_Level, 
						service_Id, 
						fee_UserType, 
						fee_Terminal_Id, 
						tp_Pid, 
						tp_Udhi, 
						msg_Fmt, 
						msg_Src, 
						fee_Type, 
						fee_Code, 
						null, 
						null, 
						src_Terminal_Id, 
						dest_Terminal_Id, 
						msg_Content, 
						reserve
					);
					
					myProxy.send_by_async(submitMsg);
					smscontent = tmpsms;
				}
				// 移除t_smssend记录
				String upSql="DELETE FROM t_smssend WHERE smsid='" + smsid + "'";
				stmt_up.executeUpdate(upSql);
				
				upSql="INSERT INTO t_smssend_his(smsid, smscontent, svcnum, createtime, state, opttime, remark, mmsflag) VALUES('" + smsid + "', '" + tmpcontent + "', '" + svcnum + "', '" + createtime + "', '1', now(), '操作成功','" + mmsflag + "')";
				stmt_up.executeUpdate(upSql);
				
			}
			rs.close();
			rs=null;
			stmt.close();
			stmt=null;
			stmt_up.close();
			stmt_up=null;
		}catch(Exception e) {
			e.printStackTrace();
			
			try{
				if(stmt != null) 
					stmt.close();
				stmt=null;
				if(stmt_up != null) 
					stmt_up.close();
				stmt_up=null;	
			}catch(Exception se) {
				se.printStackTrace();
			}
		}
	}
	
	public String getSN(String businType, String orgCode) {
		CallableStatement cs;
		String sn="";
	    try {
	    	cs = bizConn.prepareCall("{call p_getsn(?,?,?)}");
	     	cs.setString(1, businType);
	        cs.setString(2, orgCode);
	        cs.registerOutParameter(3, Types.VARCHAR);
	    
	    	cs.execute();
	      	sn = cs.getString(3);
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	System.exit(0);
	    }
	    
	    return sn;
	}
	
	public void processRecvDeliverMsg(CMPPDeliverMessage msg) {
		Statement biz_stmt=null;
		System.out.println("process deliver msg : "+msg);
		
		return ;
		
		/*try {
			int fmt=msg.getMsgFmt();
			String scontent=null;
			
			biz_stmt=bizConn.createStatement();
			System.out.println("fmt=" + fmt);
			if(fmt == 8) {
				scontent=new String (msg.getMsgContent(), "UnicodeBigUnmarked");
			}else {
				scontent=new String (msg.getMsgContent(), "GB2312");
			}
			String mtelephone=msg.getSrcterminalId();
			if(mtelephone.length() >= 11)
				mtelephone=mtelephone.substring(mtelephone.length() - 11);
			
			System.out.println("read: " + mtelephone + "," + "," + scontent.length());
			String orgCode = getOrgCode(mtelephone);
			if(orgCode == null){
				biz_stmt.close();
				biz_stmt=null;
				return;
			}
			String smsid=getSN("SR", "Y00");
			String biz_sql="";
			if(isng(mtelephone))
				biz_sql = "INSERT INTO tmpdb.t_smsrecv_"+orgCode+" (smsid, smscontent, svcnum, smssendtime, createtime, state) VALUES('" + smsid + "', '" + scontent + "', '" + mtelephone + "', now(), now(), '0')";
			else
				biz_sql = "INSERT INTO t_smsrecv_"+orgCode+" (smsid, smscontent, svcnum, smssendtime, createtime, state) VALUES('" + smsid + "', '" + scontent + "', '" + mtelephone + "', now(), now(), '0')";
			biz_stmt.executeUpdate(biz_sql);
			//System.out.println(biz_sql);
			//System.out.println(msg.toString());	
			biz_stmt.close();
			biz_stmt=null;
		}catch(Exception e) {
			e.printStackTrace();
			
			try {
				if(biz_stmt != null)
					biz_stmt.close();
			}catch(Exception se) {
				se.printStackTrace();
			}
			biz_stmt=null;
		}*/
	}
	
	public String getOrgCode(String svcnum) throws Exception{
		Statement stmt=null;
		ResultSet rs = null;
		String orgcode = null;
		try{
			StringBuffer sql = new StringBuffer("select substr(a.optrid,1,1) from t_optrsvcnum a where a.svcnum = '").append(svcnum).append("' union select substr(b.accessoptrid,1,1) from t_optraccess b where b.svcnum = '").append(svcnum).append("' limit 1");
			stmt = bizConn.createStatement();
			rs=stmt.executeQuery(sql.toString());
			if(rs.next())
				orgcode = rs.getString(1);
			if(orgcode != null)
				orgcode = orgcode.toLowerCase();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)
				rs.close();
			rs = null;
			if(stmt != null)
				stmt.close();
			stmt = null;
		}
		if(orgcode == null){
			try{
				StringBuffer sql = new StringBuffer("select substr(a.optrid,1,1) from tmpdb.t_optrsvcnum a where a.svcnum = '").append(svcnum).append("' union select substr(b.accessoptrid,1,1) from tmpdb.t_optraccess b where b.svcnum = '").append(svcnum).append("' limit 1");
				stmt = bizConn.createStatement();
				rs=stmt.executeQuery(sql.toString());
				if(rs.next())
					orgcode = rs.getString(1);
				if(orgcode != null)
					orgcode = orgcode.toLowerCase();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(rs != null)
					rs.close();
				rs = null;
				if(stmt != null)
					stmt.close();
				stmt = null;
			}
		}
		return orgcode;
	}
	
	public boolean isng(String svcnum) throws Exception{
		Statement stmt=null;
		ResultSet rs = null;
		boolean ret = false;
		try{
			String sql = "select 1 from tb_sms_ngsvcnum where svcnum = '"+svcnum+"' limit 1";
			stmt = bizConn.createStatement();
			rs=stmt.executeQuery(sql.toString());
			if(rs.next())
				ret = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)
				rs.close();
			rs = null;
			if(stmt != null)
				stmt.close();
			stmt = null;
		}
		return ret;
	}
	
}

