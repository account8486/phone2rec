package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import bean.AppMonitorBean;
import bean.BetaData;
import bean.DeskMonitorBean;
import bean.NetMonitorBean;
import bean.SecuMonitorBean;
import bean.VRVData;
import common.CommonUtil;
import common.DBUtil2;
import dao.AppMonitorDao;
import dao.DeskMonitorDao;
import dao.NetMonitorDao;
import dao.SecuMonitorDao;
import dao.TestSMS;

/**
 * 主监管servlet
 * @author Administrator
 *
 */
public class AlarmMonitor extends HttpServlet {

	private static final long serialVersionUID = -6016927845918847713L;
	private static Logger logger = Logger.getLogger(AlarmMonitor.class);

	/**
	 * Constructor of the object.
	 */
	public AlarmMonitor() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//设置刷新时间
		setRefreshTimes(req);
		setConfigInfo(req);

		//网络监管
		String netMonitor = getNetMonitor();
		req.setAttribute("netsta", netMonitor);
		
		//应用监管
		String appMonitor = getAppMonitor();
		req.setAttribute("appsta", appMonitor);
		
		//桌面监管
		String deskMonitor = getDeskMonitor();
		req.setAttribute("desksta", deskMonitor);
		
		//安全监管
		String secuSta = getSecuMonitor();
		req.setAttribute("secusta", secuSta);
		
		//告警监管
		String alarmSta = "normal";
		SecuMonitorDao secuDao = new SecuMonitorDao();
		int alarmMonitor = secuDao.getAlarm();
		if (alarmMonitor != 0){
			alarmSta = "alarm";
		}
		req.setAttribute("alarmsta", alarmSta);
		if (alarmSta.equals("alarm") && CommonUtil.getProperty("SMS_ALA").equals("1")){
			TestSMS sms = new TestSMS();
			sms.setMsg(CommonUtil.getMessage("SMS_ALA"));
			sms.setMid(CommonUtil.getMessage("SMS_MID"));
			sms.sendSMS();
		}
		
		logger.info("主监管页巡检");
		RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
		dispatcher.forward(req, resp);

	}
	
	/**
	 * 设置页面刷新时间
	 * @param req
	 */
	private void setRefreshTimes(HttpServletRequest req) {
		if (req.getParameter("minute") != null && req.getParameter("second") != null){
			String minute = req.getParameter("minute");
			String second = req.getParameter("second");
			CommonUtil.setProperty("minute", minute);
			CommonUtil.setProperty("second", second);
		}
	}
	
	/**
	 * 设置配置信息
	 * @param req
	 */
	private void setConfigInfo(HttpServletRequest req) {
		if (req.getParameter("sms_net") != null){
			String sms_net = req.getParameter("sms_net");
			String sms_app = req.getParameter("sms_app");
			String sms_desk = req.getParameter("sms_desk");
			String sms_secu = req.getParameter("sms_secu");
			String sms_ala = req.getParameter("sms_ala");
			CommonUtil.setProperty("SMS_NET", sms_net);
			CommonUtil.setProperty("SMS_APP", sms_app);
			CommonUtil.setProperty("SMS_DESK", sms_desk);
			CommonUtil.setProperty("SMS_SECU", sms_secu);
			CommonUtil.setProperty("SMS_ALA", sms_ala);
		}
	}
	
	/**
	 * 获取网络监管告警信息
	 * @return
	 */
	private String getNetMonitor() {
		String netMonitor = "normal";
		NetMonitorDao netDao = new NetMonitorDao();
		List<BetaData> betaList = netDao.getBetaList();
		for (Integer id : CommonUtil.deptId){
			NetMonitorBean netBean = new NetMonitorBean();
			netBean.setDeptName(CommonUtil.Dept.get(id));
			netBean.setDeptId(id);
			String sta = netDao.setBean(netBean, betaList);
			if (sta.equals("")){
				netMonitor = "alarm";
				break;
			}
		}
		if (netMonitor.equals("alarm") && CommonUtil.getProperty("SMS_NET").equals("1")){
			TestSMS sms = new TestSMS();
			sms.setMsg(CommonUtil.getMessage("SMS_NET"));
			sms.setMid(CommonUtil.getMessage("SMS_MID"));
			sms.sendSMS();
		}

		return netMonitor;
	}

	/**
	 * 获取应用监管告警信息
	 * @return
	 */
	private String getAppMonitor() {
		String appMonitor = "normal";
		AppMonitorDao appDao = new AppMonitorDao();
		for (String id : CommonUtil.resourceId){
			
			AppMonitorBean appBean = new AppMonitorBean();
			appBean.setAppId(id);
			appBean.setAppNo(CommonUtil.Resource_No.get(id));
			appBean.setAppName(CommonUtil.Syst.get(id));
			int stat = appDao.setBean(appBean);
			if (stat == 1){
				appMonitor = "alarm";
				break;
			}
		}
		if (appMonitor.equals("alarm") && CommonUtil.getProperty("SMS_APP").equals("1")){
			TestSMS sms = new TestSMS();
			sms.setMsg(CommonUtil.getMessage("SMS_APP"));
			sms.setMid(CommonUtil.getMessage("SMS_MID"));
			sms.sendSMS();
		}
		return appMonitor;
	}
	
	/**
	 * 获取桌面监管告警信息
	 * @return
	 */
	private String getDeskMonitor() {
		String deskMonitor = "normal";
		DeskMonitorDao deskDao = new DeskMonitorDao();
		Connection conn = DBUtil2.getConnection("vrv");
		
		if (conn != null) {
			List<VRVData> vrvList = deskDao.getVRVData();
			for (Integer id : CommonUtil.deptId){
				DeskMonitorBean deskBean = new DeskMonitorBean();
				// 扫描桌面监管状态(不包括省电力公司)
				if (id != 12) {
					deskBean.setCorpId(id);
					deskBean.setCorpName(CommonUtil.Dept.get(id));
					String sta = deskDao.setBean2(deskBean, vrvList);
					if (sta.equals("")){
						deskMonitor = "alarm";
						break;
					}
				}
			}
		}
		else {
			deskMonitor = "alarm";
		}
		if (deskMonitor.equals("alarm") && CommonUtil.getProperty("SMS_DESK").equals("1")){
			TestSMS sms = new TestSMS();
			sms.setMsg(CommonUtil.getMessage("SMS_DESK"));
			sms.setMid(CommonUtil.getMessage("SMS_MID"));
			sms.sendSMS();
		}
		return deskMonitor;
		
//		for (Integer id :CommonUtil.deptId){
//			DeskMonitorBean deskBean = new DeskMonitorBean();
//			deskBean.setCorpId(id);
//			deskBean.setCorpName(CommonUtil.Dept.get(id));
//			deskDao.getTerminalCount(id, deskBean);
//			deskBean.setBuding(deskDao.getBuding(id));
//			deskBean.setZhongduan(deskDao.getZhongduan(id));
//			deskBean.setZhuomian(deskDao.getZhuomian(id));
//			deskBean.setWeizhuce(deskDao.getWeizhuce(id));
//			deskBean.setWailian(deskDao.getWailian(id));
//			deskDao.getFiveMinuteData(id, deskBean);
//			Field[] flds = deskBean.getClass().getDeclaredFields();
//			for (Field fld : flds){
//				try {
//					fld.setAccessible(true);
//					if (fld.get(deskBean).toString() == ""){
//						deskMonitor = "";
//						break;
//					}
//				} catch (IllegalArgumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (deskMonitor == ""){
//				break;
//			}
//		}
	}
	
	/**
	 * 获取安全监管告警信息
	 * @return
	 */
	private String getSecuMonitor() {
		String secuMonitor = "normal";
		SecuMonitorDao secuDao = new SecuMonitorDao();
		for (Integer id :CommonUtil.deptId){
			SecuMonitorBean secuBean = new SecuMonitorBean();
			secuBean.setCorpName(CommonUtil.Dept.get(id));
			secuBean.setCorpId(id);
			String sta = secuDao.setBean(secuBean);
			if (sta.equals("")){
				secuMonitor = "alarm";
				break;
			}
		}
		if (secuMonitor.equals("alarm") && CommonUtil.getProperty("SMS_SECU").equals("1")){
			TestSMS sms = new TestSMS();
			sms.setMsg(CommonUtil.getMessage("SMS_SECU"));
			sms.setMid(CommonUtil.getMessage("SMS_MID"));
			sms.sendSMS();
		}

		return secuMonitor;
	}
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
