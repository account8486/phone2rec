package com.wondertek.meeting.action.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingAgenda;
import com.wondertek.meeting.model.MeetingDinner;
import com.wondertek.meeting.model.MeetingFiles;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.FileSystemService;
import com.wondertek.meeting.service.MeetingAgendaService;
import com.wondertek.meeting.service.MeetingDinnerService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 用于导出用户信息
 * @author Administrator
 *
 */
public class ExportMeetingAction extends BaseAction {

	private static final long serialVersionUID = 2912818289991550897L;
	FileSystemService fileSystemService;
	String HTML_FOLD_SUFIX="html";
	String WORD_FOLD_SUFIX="word";
	String PDF_FOLD_SUFIX="pdf";
	MeetingService meetingService;
	UserService userService;
	MeetingAgendaService meetingAgendaService;
	MeetingDinnerService meetingDinnerService;
	

	public MeetingDinnerService getMeetingDinnerService() {
		return meetingDinnerService;
	}


	public void setMeetingDinnerService(MeetingDinnerService meetingDinnerService) {
		this.meetingDinnerService = meetingDinnerService;
	}


	public MeetingAgendaService getMeetingAgendaService() {
		return meetingAgendaService;
	}


	public void setMeetingAgendaService(MeetingAgendaService meetingAgendaService) {
		this.meetingAgendaService = meetingAgendaService;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public MeetingService getMeetingService() {
		return meetingService;
	}


	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}


	public FileSystemService getFileSystemService() {
		return fileSystemService;
	}


	public void setFileSystemService(FileSystemService fileSystemService) {
		this.fileSystemService = fileSystemService;
	}



	/**
	 * @param args
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public static void main(String[] args) throws DocumentException, IOException  {
		
		String inputFile = "conf/template/test.html";
        String url = new File(inputFile).toURI().toURL().toString();
        String outputFile = "firstdoc.pdf";
        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);
        // 解决中文支持问题
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("C:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        // 解决图片的相对路径问题
        renderer.getSharedContext().setBaseURL("file:/D:/Work/Demo2do/Yoda/branch/Yoda%20-%20All/conf/template/");
        
        renderer.layout();
        renderer.createPDF(os);
        os.close();
	}
	
	
	/**
	 * 生成PDF
	 * @deprecated
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public String generateHtmltoPdf() throws IOException, Exception {
		String meetingId = getRequest().getParameter("meetingId");
		// TODO 生成HTML
		// 得到配置对象
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setOutputEncoding("utf-8");
		// 设置生成模板加载方式（由servletcontext生成）
		configuration.setServletContextForTemplateLoading(
				ServletActionContext.getServletContext(), "WEB-INF/ftl");
		// 生成数据模型
		Map<Object, Object> root = new HashMap<Object, Object>();
		// 把信息带回
		getNeedMeetingInfo(root, meetingId);
		// 得到模板
		Template template = configuration.getTemplate("meeting.ftl");
		// 创建文件夹
		String uploadFoldPathStr = fileSystemService.getDocumentRoot()
				+ "download" + File.separator + "pdf" + File.separator
				+ this.HTML_FOLD_SUFIX + meetingId;
		File uploadFoldPath = new File(uploadFoldPathStr);
		if (!uploadFoldPath.exists()) {
			uploadFoldPath.mkdirs();
		}
		// 文件保存名
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileSaveName = sdf.format(dt) + ".html";
		File afile = new File(uploadFoldPathStr + File.separator + fileSaveName);
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(afile), "utf-8"));

		try {
			// 输出模板
			// template.process(root, this.getResponse().getWriter());
			template.process(root, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		// TODO生成PDF
		String inputFile = uploadFoldPathStr + File.separator + fileSaveName;
		String url = new File(inputFile).toURI().toURL().toString();

		// 生成PDF路径
		String generatorFilePath = fileSystemService.getDocumentRoot()
				+ "download" + File.separator + "pdf" + File.separator
				+ this.PDF_FOLD_SUFIX + meetingId;
		File generatorFoldPath = new File(generatorFilePath);
		if (!generatorFoldPath.exists()) {
			generatorFoldPath.mkdirs();
		}
		
		fileSaveName= sdf.format(dt)+ ".pdf";
		String outputFile = generatorFilePath + File.separator +fileSaveName;
		
		OutputStream os = new FileOutputStream(outputFile);
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(url);
		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		
		StringUtil stringUtil=new StringUtil();
		Properties prop=stringUtil.reader("config.properties");
		String pdfFont=prop.getProperty("pdf_font_linux");
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().contains("windows")) {
			pdfFont=prop.getProperty("pdf_font_windows");
		}
		
		fontResolver.addFont(pdfFont,
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		// 解决图片的相对路径问题
		/*
		renderer.getSharedContext()
				.setBaseURL(
						"file:/D:/Work/Demo2do/Yoda/branch/Yoda%20-%20All/conf/template/");*/
		renderer.layout();
		renderer.createPDF(os);
		os.close();
		
		
		// 现在开始进行下载操作
		MeetingFiles meetingFiles = new MeetingFiles();
		meetingFiles.setFilePath("download" + File.separator + "pdf" + File.separator
				+ this.PDF_FOLD_SUFIX+ meetingId);
		meetingFiles.setFileName(fileSaveName);
		meetingFiles.setFileSaveName(fileSaveName);
		this.getRequest().setAttribute("meetingFiles", meetingFiles);
		
		String from = "WEB";
		String documentRoot = fileSystemService.getDocumentRoot();
		getRequest().setAttribute("documentRoot", documentRoot);
		getRequest().setAttribute("from", from);
		

		return SUCCESS;
	}
	
	
	/**
	 * 生成word文档
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public String generateHtml() throws IOException, Exception {
		String meetingId = getRequest().getParameter("meetingId");
		// TODO 生成HTML
		// 得到配置对象
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setOutputEncoding("utf-8");
		// 设置生成模板加载方式（由servletcontext生成）
		configuration.setServletContextForTemplateLoading(
				ServletActionContext.getServletContext(), "WEB-INF/ftl");
		// 生成数据模型
		Map<Object, Object> root = new HashMap<Object, Object>();
		// 把信息带回
		getNeedMeetingInfo(root, meetingId);
		// 得到模板
		Template template = configuration.getTemplate("meeting.ftl");
		// 创建文件夹
		String uploadFoldPathStr = fileSystemService.getDocumentRoot()
				+ "download" + File.separator + "doc" + File.separator
				+ this.WORD_FOLD_SUFIX + meetingId;
		File uploadFoldPath = new File(uploadFoldPathStr);
		if (!uploadFoldPath.exists()) {
			uploadFoldPath.mkdirs();
		}
		// 文件保存名
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileSaveName = sdf.format(dt) + ".doc";
		File afile = new File(uploadFoldPathStr + File.separator + fileSaveName);
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(afile), "utf-8"));

		try {
			// 输出模板
			// template.process(root, this.getResponse().getWriter());
			template.process(root, out);
			
			out.close();
			
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
		
		

		// 现在开始进行下载操作
		MeetingFiles meetingFiles = new MeetingFiles();
		meetingFiles.setFilePath("download" + File.separator + "doc" + File.separator
				+ this.WORD_FOLD_SUFIX+ meetingId);
		meetingFiles.setFileName(fileSaveName);
		meetingFiles.setFileSaveName(fileSaveName);
		this.getRequest().setAttribute("meetingFiles", meetingFiles);
		
		String from = "WEB";
		String documentRoot = fileSystemService.getDocumentRoot();
		getRequest().setAttribute("documentRoot", documentRoot);
		getRequest().setAttribute("from", from);
		

		return SUCCESS;
	}
	
	
	/**
	 * 获取会议所需信息
	 * @param root
	 * @param meetingId
	 * @throws Exception
	 * @throws ServiceException
	 */
	public void getNeedMeetingInfo(Map<Object,Object> root, String meetingId)
			throws Exception, ServiceException {
		// 获取会议信息
		Meeting meeting = meetingService.findById(Long.valueOf(meetingId));
		root.put("meeting", meeting);

		// 获取与会人员信息
		Pager<User> userList = userService.findMeetingUserPager(
				meeting.getId(), new User(), 0, 100000, "1");
		root.put("userList", userList.getPageRecords());

		// 获取议程信息
		MeetingAgenda meetingAgenda = new MeetingAgenda();
		meetingAgenda.setMeetingId(Long.valueOf(meetingId));
		Pager<MeetingAgenda> agendaPager = meetingAgendaService
				.queryPagerByMeetingId(meetingAgenda, currentPage, pageSize);
		List<MeetingAgenda> meetingAgendaList = agendaPager.getPageRecords();
		root.put("meetingAgendaList", meetingAgendaList);

		// 获取用餐信息
		List<MeetingDinner> dinnerList = meetingDinnerService
				.findDinnerList(null, Long.valueOf(meetingId), null);
		root.put("dinnerList", dinnerList);
		
		
	}
	
	


	


	

}
