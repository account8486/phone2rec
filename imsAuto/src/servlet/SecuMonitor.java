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
import bean.SecuMonitorBean;
import common.CommonUtil;
import dao.SecuMonitorDao;

public class SecuMonitor extends HttpServlet {

	private static final long serialVersionUID = -3749473873789956275L;
	private static Logger logger = Logger.getLogger(SecuMonitor.class);

	/**
	 * Constructor of the object.
	 */
	public SecuMonitor() {
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

		String handle = req.getParameter("handle");
		String status = "normal";
		List<SecuMonitorBean> list = null;
		SecuMonitorDao secuDao = new SecuMonitorDao();
		if (handle != null){
			//复位操作，将巡检记录清空
			list = new ArrayList<SecuMonitorBean>();
			if (handle.equals("reset")){
				for (Integer id :CommonUtil.deptId){
					SecuMonitorBean secuBean = new SecuMonitorBean();
					secuBean.setCorpName(CommonUtil.Dept.get(id));
					secuBean.setCorpId(id);
					String sta = secuDao.setBean(secuBean);
					if (sta.equals("")){
						status = "alarm";
					}
					list.add(secuBean);
				}
			}
		}
		else {
			//首次打开页面，初始化巡检记录
			if (req.getSession().getAttribute("seculist") == null){
				list = new ArrayList<SecuMonitorBean>();
				for (Integer id :CommonUtil.deptId){
					SecuMonitorBean secuBean = new SecuMonitorBean();
					secuBean.setCorpName(CommonUtil.Dept.get(id));
					secuBean.setCorpId(id);
					String sta = secuDao.setBean(secuBean);
					if (sta.equals("")){
						status = "alarm";
					}
					list.add(secuBean);
				}
			}
			//Session已存在，根据巡检结果修改Session值
			else {
				list = (List<SecuMonitorBean>) req.getSession().getAttribute("seculist");
				for (SecuMonitorBean bean : list){
					String sta = secuDao.setBean(bean);
					if (sta.equals("")){
						status = "alarm";
					}
				}
			}
		}
		logger.info("安全监管巡检");
		req.getSession().setAttribute("seculist", list);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/secuMonitor.jsp");
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
