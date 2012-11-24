package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import bean.DevMonitorlsBean;
import bean.DevMonitorsdBean;
import common.CommonUtil;
import common.DBUtil2;
import dao.DevMonitorlsDao;
import dao.DevMonitorsdDao;

public class DevMonitor extends HttpServlet {

	private static final long serialVersionUID = 6644203005174865614L;
	private static Logger logger = Logger.getLogger(DevMonitor.class);

	/**
	 * Constructor of the object.
	 */
	public DevMonitor() {
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
	@SuppressWarnings("unused")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		//flag取自URL地址
	
		String flag=req.getParameter("flag");
		
		List<DevMonitorlsBean> list = new ArrayList<DevMonitorlsBean>();
		DevMonitorlsDao deskDao = new DevMonitorlsDao();
		DevMonitorsdDao sdDao = new DevMonitorsdDao();
		
		if("kt".equals(flag)){
			RequestDispatcher dispatcher = req.getRequestDispatcher("/DevMonitor_kt.jsp");
			dispatcher.forward(req, resp);
		}
		
		if("ls".equals(flag)){
			RequestDispatcher dispatcher = req.getRequestDispatcher("/DevMonitor_ls.jsp");
			dispatcher.forward(req, resp);
		}
		if("pd".equals(flag)){
			RequestDispatcher dispatcher = req.getRequestDispatcher("/DevMonitor_pd.jsp");
			dispatcher.forward(req, resp);
		}
		if("sd".equals(flag)){
			
	       List<DevMonitorsdBean> wuhusdlist= sdDao.getwuhusdList();
	       List<DevMonitorsdBean> huangshansdlist=sdDao.gethuangshansdList();
	       List<DevMonitorsdBean> whcuoList=new ArrayList<DevMonitorsdBean>();
	       List<DevMonitorsdBean> hscuoList=new ArrayList<DevMonitorsdBean>();
	       List<DevMonitorsdBean> whduiList=new ArrayList<DevMonitorsdBean>();
	       List<DevMonitorsdBean> hsduiList=new ArrayList<DevMonitorsdBean>();
	       for(int i=0;i<wuhusdlist.size();i++){
	    	   if(wuhusdlist.get(i).getFlag().equals("1")){
	    		   whcuoList.add(wuhusdlist.get(i));
	    	   }else{
	    		   whduiList.add(wuhusdlist.get(i));
	    	   }
	       }
	       req.setAttribute("whcuoList", whcuoList);
	       //req.setAttribute("whduiList", whduiList);
	       for(int i=0;i<huangshansdlist.size();i++){
	    	   if(huangshansdlist.get(i).getFlag().equals("1")){
	    		   hscuoList.add(huangshansdlist.get(i));
	    	   }else{
	    		   hsduiList.add(huangshansdlist.get(i));
	    	   }
	       }
	       req.setAttribute("whcuoList", whcuoList);
	       req.setAttribute("hscuoList", hscuoList);
	       //req.setAttribute("huangshansdlist", huangshansdlist);
	       RequestDispatcher dispatcher = req.getRequestDispatcher("/DevMonitor_sd.jsp");
			dispatcher.forward(req, resp);
	       
		}
		if("wd".equals(flag)){
			RequestDispatcher dispatcher = req.getRequestDispatcher("/DevMonitor_wd.jsp");
			dispatcher.forward(req, resp);
		}
		if("xf".equals(flag)){
			RequestDispatcher dispatcher = req.getRequestDispatcher("/DevMonitor_xf.jsp");
			dispatcher.forward(req, resp);
		}
			
	}
	
	
	
	public void lsMoniter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/DevMonitor_ls.jsp");
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
