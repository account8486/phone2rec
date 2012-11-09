<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.wondertek.meeting.model.*"%>
<%@ page import="com.wondertek.meeting.util.ValidateUtil"%>

<%


	//防止IE缓存    
	response.setHeader("pragma", "no-cache");
	response.setHeader("cache-control", "no-cache");
	response.setDateHeader("Expires", 0);
	//设置编码    
	request.setCharacterEncoding("UTF-8");

	MeetingFiles meetingFiles = (MeetingFiles) request
			.getAttribute("meetingFiles");
	System.out.println("downloading.....");
	System.out.println("meetingFiles.getFileSaveName():"
			+ meetingFiles.getFileSaveName());
	System.out.println("meetingFiles.getFileName():"
			+ meetingFiles.getFileName());
	System.out
			.println(config.getServletContext().getRealPath(
					meetingFiles.getFilePath() + File.separator
							+ meetingFiles.getFileSaveName()));

	java.io.BufferedInputStream bis = null;
	java.io.BufferedOutputStream bos = null;

	try {
		String fileSaveName = meetingFiles.getFileSaveName();
		//response.setContentType("application/octet-stream");
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		//response.setContentLength(Integer.parseInt(String.valueOf()));
		//response.setContentLength(10000000);
		
		if(meetingFiles.getFileSize()!=null){
		  response.setContentLength(meetingFiles.getFileSize().intValue());
		}
		
		//如果为WAP下载时
		String fileDownloadName = "";
		if ("WAP".equals(String.valueOf(request.getAttribute("from")))) {
			fileDownloadName = meetingFiles.getFileSaveName();
		} else {
			fileDownloadName = meetingFiles.getFileName();
		}
		
		System.out.println("是否属于IE:"+ValidateUtil.isIE());
		
		//IE下下载
		if(ValidateUtil.isIE()){
			
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ java.net.URLEncoder.encode(fileDownloadName,
									"utf-8"));
		}else{
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(fileDownloadName.getBytes("UTF-8"), "ISO8859-1"));
		}
	

		String documentRoot = String.valueOf(request
				.getAttribute("documentRoot"));

		bis = new java.io.BufferedInputStream(new FileInputStream(
				documentRoot
						+meetingFiles.getFilePath()
						+ File.separator
						+ java.net.URLEncoder.encode(fileSaveName,
								"UTF-8")));

		bos = new java.io.BufferedOutputStream(
				response.getOutputStream());
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
