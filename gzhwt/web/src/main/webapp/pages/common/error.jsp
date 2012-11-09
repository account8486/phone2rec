<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>

<body>
<div style="padding:5px;width:100%;height:100%;"	>
 <h2>
     <div>     	
     	<img src="${ctx}/images/sorry.gif"></img> <font style="font-size:14px;">抱歉，您要查看的页面可能已经删除、更名或暂时不可用</font>
     </div>
 </h2>
 <hr/>
   <h3	style="color: red;font-size:12px;"	>
   <!-- 获得异常对象 -->
   
    	异常信息：<s:property value="exception.message"/>
    <hr />
    	系统堆栈信息：
    <s:property value="exceptionStack"/>
    </h3>
    <%
	    try{
    		org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(com.wondertek.meeting.action.base.BaseAction.class);
	    	log.error("\n=================== error.jsp print error begin========================");
	    	log.error("error.jsp print error",request.getAttribute("exception"));
	    	String uri = request.getRequestURI();
	    	
	    	java.util.Enumeration enu=request.getParameterNames();
	    	log.error("##########request parameters:");
	    	if(enu !=null){
		    	while(enu.hasMoreElements()){
		    		String paraName=(String)enu.nextElement();
		    		log.error(paraName+" === "+request.getParameter(paraName));
		    	}
	    	}
	    	log.error("\n================== error.jsp print error end ==========================");
	    	
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
    %>
</div>
</body>
</html>