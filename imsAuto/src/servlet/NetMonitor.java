package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import common.CommonUtil;
import dao.NetMonitorDao;
import bean.BetaData;
import bean.NetMonitorBean;;

public class NetMonitor extends HttpServlet {

	private static final long serialVersionUID = -3340357584022963040L;
	private static Logger logger = Logger.getLogger(NetMonitor.class);

	/**
	 * Constructor of the object.
	 */
	public NetMonitor() {
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
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String handle = req.getParameter("handle");
		List<NetMonitorBean> list = null;
		String status = "normal";
		NetMonitorDao netDao = new NetMonitorDao();
		List<BetaData> betaList = netDao.getBetaList();
		if (handle != null){
			list = new ArrayList<NetMonitorBean>();
			if (handle.equals("reset")){
				//复位操作，将巡检记录清空
				for (Integer id :CommonUtil.deptId){
					NetMonitorBean netBean = new NetMonitorBean();
					netBean.setDeptName(CommonUtil.Dept.get(id));
					netBean.setDeptId(id);
					String sta = netDao.setBean(netBean, betaList);
					if (sta.equals("")){
						status = "alarm";
					}
					list.add(netBean);
				}
			}
		}
		else {
			//首次打开页面，初始化巡检记录
			if (req.getSession().getAttribute("netlist") == null){
				list = new ArrayList<NetMonitorBean>();
				for (Integer id :CommonUtil.deptId){
					NetMonitorBean netBean = new NetMonitorBean();
					netBean.setDeptName(CommonUtil.Dept.get(id));
					netBean.setDeptId(id);
					String sta = netDao.setBean(netBean, betaList);
					if (sta.equals("")){
						status = "alarm";
					}
					list.add(netBean);
				}
			}
			//Session已存在，根据巡检结果修改Session值
			else {
				list = (List<NetMonitorBean>) req.getSession().getAttribute("netlist");
				for (NetMonitorBean bean : list){
					String sta = netDao.setBean(bean, betaList);
					if (sta.equals("")){
						status = "alarm";
					}
				}
			}
		}
		logger.info("网络监管巡检");
		req.getSession().setAttribute("netlist", list);
		req.setAttribute("netsta", status);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/netMonitor.jsp");
		dispatcher.forward(req, resp);
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
