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
import common.DomUtil;
import dao.AppMonitorDao;
import bean.AppMonitorBean;

public class AppMonitor extends HttpServlet {

	private static final long serialVersionUID = -2352878811710836266L;
	private static Logger logger = Logger.getLogger(AppMonitor.class);

	/**
	 * Constructor of the object.
	 */
	public AppMonitor() {
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
		String status = "normal";
		List<AppMonitorBean> list = null;
		AppMonitorDao appDao = new AppMonitorDao();

		//首次打开页面，初始化巡检记录
		if (req.getSession().getAttribute("applist") == null){
			list = new ArrayList<AppMonitorBean>();
			for (String id : CommonUtil.resourceId){
				AppMonitorBean appBean = new AppMonitorBean();
				appBean.setAppId(id);
				appBean.setAppNo(CommonUtil.Resource_No.get(id));
				appBean.setAppName(CommonUtil.Syst.get(id));
				int stat = appDao.setBean(appBean);
				if (stat == 1){
					status = "alarm";
				}
				list.add(appBean);
			}
		}
		//Session已存在，根据巡检结果修改Session值
		else {
			list = (List<AppMonitorBean>) req.getSession().getAttribute("applist");
			if (handle != null)
			{
				if (handle.equals("reset"))
				{
					for (AppMonitorBean bean : list){
						// 复位操作，将巡检记录清空
						bean.setAppHealth_sta(0);
						bean.setDayLogNum_sta(0);
						bean.setDayLogNumH_sta(0);
						bean.setOnlineCount_sta(0);
						bean.setOnlineCountH_sta(0);
						bean.setRegCount_sta(0);
						bean.setZhuanye_sta(0);
						bean.setAccess_sta(0);
					}
				}
				if (handle.equals("update"))
				{
					// 更新XML数据
					DomUtil.setAppData();
					logger.info("手动更新XML数据已完成");
				}
			}
			
			for (AppMonitorBean bean : list){
				int stat = appDao.setBean(bean);
				if (stat == 1){
					status = "alarm";
				}
			}
		}

		logger.info("应用监管巡检");
		req.setAttribute("appsta", status);
		req.getSession().setAttribute("applist", list);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/appMonitor.jsp");
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
