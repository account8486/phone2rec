/**
 * 
 */
package common;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import dao.AppMonitorDao;

/**
 * @author Administrator
 *
 */
public class DomUtil {
	private static Logger logger = Logger.getLogger(DomUtil.class);

	 public static void main(String[] args) throws Exception{ 
		  getAppData("27");
	 }

    /**
     * 更新应用巡检专业指标并保存
     */
	 public static void setAppData() {
		try {
			String path = CommonUtil.class.getClassLoader().getResource("appData.xml").toURI().getPath().substring(1);
			String path2 = CommonUtil.class.getClassLoader().getResource("appDailyData.xml").toURI().getPath().substring(1);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(path);
			Element root = doc.getRootElement();
			
			SAXBuilder sb2 = new SAXBuilder();
			Document doc2 = sb2.build(path2);
			Element root2 = doc2.getRootElement();
			
			Set<String> keys = CommonUtil.AppNo_Method.keySet();  //应用编号集合
			for (String appNo : keys)
			{
				setAppData(root, appNo);
				setAppData2(root2, appNo);
			}
			
			FileOutputStream fOut = new FileOutputStream(path);
			XMLOutputter outp = new XMLOutputter();
			outp.output(doc, fOut);
			fOut = new FileOutputStream(path2);
			outp.output(doc2, fOut);
			fOut.close();
			
		} catch (Exception e) {
			logger.error("更新数据异常-应用巡检专业指标", e);
			e.printStackTrace();
		}
    }

    /**
     * 更新单项应用巡检专业指标数据
     * @param root-数据集合根节点
     * @param appNo-应用编号
     */
	@SuppressWarnings("unchecked")
	private static void setAppData(Element root, String appNo) {
    	AppMonitorDao appDao = new AppMonitorDao();
    	String methodName = CommonUtil.AppNo_Method.get(appNo);
    	Class<? extends AppMonitorDao> appClass = appDao.getClass();
    	Method method = null;
    	if (!methodName.equals(""))  //应用存在专业指标监控
    	{
    		HashMap<String, String> map;
    		try {
				method = appClass.getMethod(methodName);
				map = (HashMap<String, String>) method.invoke(appDao); //获取专业指标监控数据(数据库)
				Set<String> keys = map.keySet();
				Element selectNode = null;
			    for (String key : keys)
			    {
				    selectNode = (Element)XPath.selectSingleNode(root, "//app[@name='"+appNo+"']/"+key);  //查找应用巡检专业指标节点
				    if (selectNode != null)
				    {
					    if (map.get(key) == null)
					    {
						    selectNode.setText("无数据");
					    }
					    else {
						    selectNode.setText(map.get(key));  //更新数据至XML
					    }
				    }
			    }
				
			} catch (Exception e) {
				logger.error("更新数据异常-单项应用巡检专业指标", e);
				e.printStackTrace();
			}
    	}

	}

	/**
	 * 更新单项应用巡检日指标数据
	 * @param root 数据集合根节点
	 * @param appNo 应用编号
	 */
	private static void setAppData2(Element root, String appNo) {
    	//获取日指标监控数据(数据库)
		AppMonitorDao appDao = new AppMonitorDao();
    	int onLine = appDao.getOnlineCountReal_H(appNo);
    	int dayLogin = appDao.getDayLoginNum_H(appNo);
    	int regCount = appDao.getRegCount(appNo);
    	
    	Element selectNode = null;
    	try {
			selectNode = (Element)XPath.selectSingleNode(root, "//app[@name='"+appNo+"']/value90");  // 查找应用巡检在线用户历史峰值数据节点	
		    if (selectNode != null)
		    {
			    if (onLine ==  -1)
			    {
				    selectNode.setText("无数据");
			    }
			    else {
				    selectNode.setText(String.valueOf(onLine));  //更新数据至XML
			    }
		    }
		    selectNode = (Element)XPath.selectSingleNode(root, "//app[@name='"+appNo+"']/value91");  //查找应用巡检日登录用户历史峰值数据节点
		    if (selectNode != null)
		    {
			    if (dayLogin ==  -1)
			    {
				    selectNode.setText("无数据");
			    }
			    else {
				    selectNode.setText(String.valueOf(dayLogin));  //更新数据至XML
			    }
		    }
		    selectNode = (Element)XPath.selectSingleNode(root, "//app[@name='"+appNo+"']/value92");  //查找应用巡检注册用户总数数据节点
		    if (selectNode != null)
		    {
			    if (regCount ==  -1)
			    {
				    selectNode.setText("无数据");
			    }
			    else {
				    selectNode.setText(String.valueOf(regCount));  //更新数据至XML
			    }
		    }
		} catch (JDOMException e) {
			logger.error("更新数据异常-应用巡检日指标", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询单项应用巡检专业指标数据
	 * @param appNo:应用编号
	 * @return app[0]数据状态，app[1]数据内容
	 */
	@SuppressWarnings("unchecked")
	public static String[] getAppData(String appNo) {
		String appData = "";
		String [] app = new String[2];
		app[0] = "0";
		app[1] = "";
		try {
			String path = CommonUtil.class.getClassLoader().getResource("appData.xml").toURI().getPath().substring(1);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(path);
			Element root = doc.getRootElement();
			Element appNode = (Element)XPath.selectSingleNode(root, "//app[@name='"+appNo+"']");  //查找指定应用巡检数据节点
			if (appNode != null) {
				List<Element> list = appNode.getChildren();		
				for (Element node : list)
				{
					appData += node.getAttributeValue("name") + ":";
					appData += node.getTextTrim() + "\n ";
					if (node.getTextTrim().equals("无数据")) {
						app[0] = "1"; //存在无数据的专业子指标，进行告警
					}
				}
				app[1] = appData.substring(0, appData.length()-1);
			}
		} catch (Exception e) {
			logger.error("查询数据异常-单项应用巡检专业指标", e);
			e.printStackTrace();
		}
		return app;
	}

	/**
	 * 查询单项应用巡检日指标数据
	 * @param appNo:应用编号
	 * @param key:日指标编号
	 * @return app[0]数据状态，app[1]数据内容
	 */
	public static String[] getAppData2(String appNo, String key) {
		String [] app = new String[2];
		app[0] = "0";
		app[1] = "";
		String appData = "";
		try {
			String path = CommonUtil.class.getClassLoader().getResource("appDailyData.xml").toURI().getPath().substring(1);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(path);
			Element root = doc.getRootElement();
			Element appNode = (Element)XPath.selectSingleNode(root, "//app[@name='"+appNo+"']/"+key);  //查找指定应用巡检数据节点
			if (appNode != null) {
				//appData += appNode.getAttributeValue("name") + ":";
				appData += appNode.getTextTrim();
				app[1] = appData;
				if (appNode.getTextTrim().equals("无数据")) {
					app[0] = "1"; //日指标无数据，进行告警
				}
			}
		} catch (Exception e) {
			logger.error("查询数据异常-单项应用巡检日指标", e);
			e.printStackTrace();
		}
		return app;
	}

	
	/**
	 * 读取地市公司所属两个网络节点信息
	 * @param corpName 地市公司
	 * @return app[0]155平面节点信息，app[1]622平面节点信息
	 */
	@SuppressWarnings("unchecked")
	public static String[] getBetaConfigData(String corpName) {
		String [] app = new String[2];
		app[0] = "";
		app[1] = "";
		try {
			String path = CommonUtil.class.getClassLoader().getResource("betaConfig.xml").toURI().getPath().substring(1);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(path);
			Element root = doc.getRootElement();
			Element corpNode = (Element)XPath.selectSingleNode(root, "//corp[@name='"+corpName+"']");  //查找指定地市IP配置节点
			if (corpNode != null) {
				List<Element> list = (List<Element>)corpNode.getChildren();		
				for (Element node : list)
				{
					String netValue = "";
					String netName = node.getAttributeValue("name");
					if (netName.equals("155" )) {
						netValue = node.getTextTrim();
						app[0] = netValue;
					}
					if (netName.equals("622")) {
						netValue = node.getTextTrim();
						app[1] = netValue;
					}
				}
			}
		} catch (Exception e) {
			logger.error("读取信息异常-BETA网络配置信息", e);
		}
		return app;
	}
	
	/**
	 * 验证设备属性是否匹配
	 * @param devName 设备名称
	 * @param id 设备属性名称
	 * @return
	 */
	public static boolean checkCH(String devName, String id) {
		boolean exists = false;
		try {
			String path = CommonUtil.class.getClassLoader().getResource("powData.xml").toURI().getPath().substring(1);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(path);
			Element root = doc.getRootElement();
			Element devNode = (Element)XPath.selectSingleNode(root, "//dev[@name='"+devName+"']");  //查找指定设备
			if (devNode != null) {
				List<Element> list = (List<Element>)devNode.getChildren();  // 设备属性集合
				for (Element node : list)
				{
					String name = node.getAttributeValue("name");
					if (name.equals(id)) {
						// 属性存在
						exists = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("读取信息异常-供电设备配置信息", e);
		}
		return exists;
	}
	
	
	/**
	 * 获取监管指标声音告警开关状态
	 * @param modName
	 * @param appName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static int getSoundConfig(String modName, String appName) {
		int switchs = 0;
		try {
			String path = CommonUtil.class.getClassLoader().getResource("soundConfig.xml").toURI().getPath().substring(1);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(path);
			Element root = doc.getRootElement();
			Element devNode = (Element)XPath.selectSingleNode(root, "//mod[@name='"+modName+"']");  //查找指定监管模块
			if (devNode != null) {
				List<Element> list = (List<Element>)devNode.getChildren();  // 监管指标集合
				for (Element node : list)
				{
					String name = node.getAttributeValue("name");
					
					if (name.equals(appName)) {
						// 监管指标存在
						switchs = Integer.parseInt(node.getText());
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("读取信息异常-监管指标声音告警配置信息", e);
		}
		return switchs;
	}
}
