/**
 * 
 */
package common;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

/**
 * @author Administrator
 * 定时器：执行每日更新XML数据
 *
 */
public class TaskListener implements ServletContextListener {
	
	private static Logger logger = Logger.getLogger(TaskListener.class);
	Timer timer;

	public void contextDestroyed(ServletContextEvent arg0) {
		timer.cancel();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// 设置每天更新时间
		int setHour = Integer.parseInt(CommonUtil.getProperty("TASK_HOUR"));
		int setMinute = Integer.parseInt(CommonUtil.getProperty("TASK_MIN"));
		Calendar cadar = Calendar.getInstance();
		cadar.add(Calendar.DAY_OF_MONTH, 1);
		cadar.set(Calendar.HOUR_OF_DAY, setHour);
		cadar.set(Calendar.MINUTE, setMinute);
		cadar.set(Calendar.SECOND, 0);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {updateData();}
		}, cadar.getTime(), 24 * 60 * 60 * 1000);
	}

	/**
	 * 每日定时更新XML数据
	 */
	public void updateData() {
		DomUtil.setAppData();
		logger.info("定时更新XML数据已完成");
	}
}
