<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedInputStream"%>
<%@ page import="java.io.BufferedOutputStream"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="com.wondertek.meeting.util.ValidateUtil"%>

<%
	//防止IE缓存    
	response.setHeader("pragma", "no-cache");
	response.setHeader("cache-control", "no-cache");
	response.setDateHeader("Expires", 0);
	//设置编码    
	request.setCharacterEncoding("UTF-8");

	java.io.BufferedInputStream bis = null;
	java.io.BufferedOutputStream bos = null;

	try {
		final String fileName = (String) request.getAttribute("fileName");
		response.setContentType("application/octet-stream;charset=UTF-8");

		if (ValidateUtil.isIE()) {
			response.setHeader("Content-disposition",
					"attachment; filename=" + java.net.URLEncoder.encode(fileName, "utf-8"));
		} else {
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
		}

		String documentRoot = String.valueOf(request.getAttribute("documentRoot"));

		bis = new java.io.BufferedInputStream(new FileInputStream((String) request.getAttribute("phicalPath")));

		bos = new java.io.BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}

		bos.flush();
		out.clear();//清空缓存的内容。
		out = pageContext.pushBody();//：参考API
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (bis != null)
			bis.close();
		if (bos != null)
			bos.close();
	}
%>