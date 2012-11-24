package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import bean.PowerData;
import dao.PowMonitorDao;

public class PowMonitor extends HttpServlet {

	private static final long serialVersionUID = -2060757160035192278L;
	private static Logger logger = Logger.getLogger(PowMonitor.class);

	public PowMonitor() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String handle = req.getParameter("handle");
		String status = "normal";
		PowMonitorDao powDao = new PowMonitorDao();
		HashMap<String, PowerData> powMap = powDao.getPowerData();
		for (String key : powMap.keySet()) {
			PowerData power = powMap.get(key);
			if (power.getAlarmInf().equals("")) {
				power.setAlarmInf("设备运行正常");
			}
			else {
				status = "alarm";
			}
		}
		req.getSession().setAttribute("powMap", powMap);
		if (handle != null){
			if (handle.equals("reset")){
				//复位操作，将巡检记录清空
				
			}
		}
		else {
			//首次打开页面，初始化巡检记录
			if (req.getSession().getAttribute("netlist") == null){
				
			}
			//Session已存在，根据巡检结果修改Session值
			else {

			}
		}
		logger.info("机房供电监管巡检");
		RequestDispatcher dispatcher = req.getRequestDispatcher("/powerMonitor.jsp");
		dispatcher.forward(req, resp);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
