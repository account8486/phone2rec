/**
 * 新闻action
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-3-6
 */
package com.wondertek.meeting.action.news;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.BeanUtils;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.Folder;
import com.wondertek.meeting.model.News;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.NewsService;
import com.wondertek.meeting.util.FileOperatorUtil;
import com.wondertek.meeting.util.StringUtil;

@SuppressWarnings("serial")
public class NewsAction extends BaseAction {
	private MeetingService meetingService;
	private NewsService newsService;

	//以下属性用于传递参数
	private Integer id;
	private Long meetingId;
	private News news;
	
	//用于上传缩略图
	private File thumbnail;
	private String thumbnailFileName;
	private static final String UPLOAD_IMAGE_PATH = "news_thumbnail"; //上传图片保存目录
	
	//CKEditor上传相关参数
	private File upload;// CKEditor 上传的图片
	private String uploadFileName;
	private String type;
	private String CKEditor;
	private String CKEditorFuncNum;
	private String langCode;// CKeditor的语言
		
		
	/*
	 * 查看新闻列表
	 */
	public String list() throws Exception {
		String from=getParameter("from");
		String forward="newsList";
		
		if(StringUtil.isNotEmpty(from)&&from.contains("guide")){
			forward="guide";
		}
		Pager<News> pager = this.newsService.findAllNewsPager(meetingId,news, currentPage, pageSize);
		this.setAttribute("pager", pager);
		return forward;
	}
	
	/*
	 * 增加或编辑请求
	 * id为空则表示新增加，否则为编辑
	 */
	public String editReq() throws Exception {
		if(this.id != null && this.id > 0) {
			this.news = this.newsService.findById(id);
		} 
		
		return "newsInfo";
	}
	
	/*
	 * 新闻预览
	 */
	public String preview() throws Exception {
		if(this.id != null && this.id > 0) {
			this.news = this.newsService.findById(id);
		} 
		
		return "preview";
	}
	
	/*
	 * 保存新闻信息
	 */
	public String save() throws Exception {
		//TODO
		String guideStep=getRequest().getParameter("guideStep");
		//保存图片
		String filename = null;
		if(this.thumbnail != null) {
			filename = this.uploadImage();
		}
		
		//对新闻正文进行base64编码
		news.setContent(StringUtil.base64Encode(news.getContent()));
		
		if(this.id != null && this.id > 0) { //edit
			News oldNews = this.newsService.findById(id);
			oldNews.setTitle(news.getTitle());
			oldNews.setSource(news.getSource());
			oldNews.setImageNews(news.getImageNews());
			oldNews.setContent(news.getContent());
			oldNews.setState(news.getState());
			
			oldNews.setLastModifyTime(new Date());
			if(filename != null) {
				oldNews.setThumbnailImage(filename);
			}
			this.newsService.saveOrUpdate(oldNews);
		} else {
			news.setState(1);
			news.setThumbnailImage(filename);
			news.setCreateTime(new Date());
			news.setHitCount(0);
			news.setCreator(this.getAdminUserFromSession());
			news.setMeeting(this.meetingService.findById(meetingId));
			this.newsService.saveOrUpdate(news);
		}
		this.news = null;
		if("step5".equals(guideStep)){
			return "step5";
		}
		return this.list();
	}
	
	/**
	 * 上传图片
	 */
	private String uploadImage() throws Exception {
		// 图片要保存到的服务器路径(文件根路径/meetingId/news_thumbnail)
		String filePath = UPLOAD_IMAGE_PATH + File.separator + meetingId;
		File saveDir = new File(this.getDocumentRoot() + filePath);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}

		//生成文件名
		int index = this.thumbnailFileName.lastIndexOf(".");
		String extName = "";
		if (index > 0) {
			extName = this.thumbnailFileName.substring(index);
		}
		String filename = String.valueOf(new Date().getTime()) + extName;

		// 要保存的目标文件
		File saveFile = new File(saveDir + File.separator + filename);
		// 图片url
		String imgUrl = UPLOAD_IMAGE_PATH + "/"  + meetingId + "/" + filename;
		// 存储文件
		FileOperatorUtil.copy(this.thumbnail, saveFile);
		return imgUrl;
	}
	
	/*
	 * 删除新闻信息
	 */
	public String delete() throws Exception {
		if(id != null) {
			News entity = this.newsService.findById(id);
			this.newsService.delete(entity);
		}
		this.news = null;
		return this.list();
	}
	
	/*
	 * portal和wap的新闻中心显示
	 */
	public String show() throws Exception {
		//门户要显示有效的新闻
		News param = new News();
		param.setState(1);
		Pager<News> pager = this.newsService.findAllNewsPager(meetingId, param, currentPage, pageSize);
		this.setAttribute("pager", pager);
		return "newsList";
	}
	
	/*
	 * portal和wap的新闻详细内容
	 */
	public String detail() throws Exception {
		if(this.id != null && this.id > 0) {
			news = newsService.findById(id);
			//点击数增加
			int hitCount = news.getHitCount() != null ? news.getHitCount() : 0;
			news.setHitCount(hitCount +1);
			newsService.saveOrUpdate(news);
		} 
		return "newsDetail";
	}
	
	/**
	 * 以下用于处理ckEditor中的图片上传
	 */
	
	private boolean checkImageType(String type){
		log.debug("image file type {}",type);
		if(StringUtil.isEmpty(type)){
			return false;
		}
		type = type.toLowerCase();
		if(imgFileExtSet.contains(type)){
			return true;
		}
		return false;
	}
	
	
	 /**
     * 图片文件后缀
     */
    protected static Set<String> imgFileExtSet = new HashSet<String>();
    static{
    	imgFileExtSet.add(".png");
    	imgFileExtSet.add(".jpg");
    	imgFileExtSet.add(".jpeg");
    	imgFileExtSet.add(".bmp");
    	imgFileExtSet.add(".gif");
    }
	
	// CkEditor 上传图片
	//@Action(value = "/ckUploadImage")
	public String uploadImages() throws Exception {
		
		try {
			log.debug(
							"CKEditor uploadImages type {} CKEditor {} CKEditorFuncNum {} langCode {} type {}",
							new String[] { type, CKEditor, CKEditorFuncNum,
									langCode, type });
			
			HttpServletRequest request = ServletActionContext.getRequest();
			FileOutputStream fos;
//			String webRoot = request.getSession().getServletContext().getRealPath(
//					"");
			String webRoot = getDocumentRoot();
			// 获取图片后缀名
			String partRightType = uploadFileName.substring(uploadFileName
					.lastIndexOf("."));
			String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
			// 判断图片的格式
			if (!checkImageType(partRightType)) {
				String path = "";
				String alt_msg = "图片格式只能是 GIF, jpeg, PNG, JPG!";
				str2Resp("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
						+ CKEditorFuncNum
						+ ", '"
						+ path
						+ "' , '"
						+ alt_msg
						+ "');</script>");
			} else {

					uploadFileName =  uploadFileName.substring(0,uploadFileName.lastIndexOf("."))+"_"+System.currentTimeMillis() + partRightType;

					File uploadFilePath = buildFolder(getRequest());
					
					if (uploadFilePath.exists() == false) {
						uploadFilePath.mkdirs();
						log.debug("路径不存在,但是已经成功创建了" + uploadFilePath.getPath());
					}
					fos = new FileOutputStream(new File(uploadFilePath.getPath() , uploadFileName));
					FileInputStream fis = new FileInputStream(getUpload());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
					fis.close();

				// String path = "http://" + request.getServerName() + ":"
				// + request.getServerPort() + request.getContextPath()
				// + Constants.UPLOAD_IMAGES_PATH + uploadFileName;

				log.debug("uploadFilePath.getPath() = "+uploadFilePath.getPath());	
				//不替换，控件不失败路径地址
				String uploadAbsolutePath = uploadFilePath.getPath().replace(webRoot, "").replace("\\", "/");
				log.debug("after replace uploadFilePath uploadAbsolutePath = "+uploadAbsolutePath);	
				
//				String path = getBasePath()
//				+ uploadAbsolutePath+ "/" + uploadFileName;
				String path = getServerUrl()
						+ uploadAbsolutePath+ "/" + uploadFileName;
				log.debug("return path {}",path);
				String alt_msg = "";
				str2Resp("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
						+ CKEditorFuncNum
						+ ", '"
						+ path
						+ "' , '"
						+ alt_msg
						+ "');</script>");

			}
		} catch (Exception e) {
			log.error("图片上传失败",e);
			String path = "";
			String alt_msg = "图片上传失败";
			str2Resp("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
					+ CKEditorFuncNum
					+ ", '"
					+ path
					+ "' , '"
					+ alt_msg
					+ "');</script>");
		}

		return null;
	}
	
	
    /** ~~~ 系统默认建立和使用的以时间字符串作为文件名称的时间格式*/  
    private static final String DEFAULT_SUB_FOLDER_FORMAT_AUTO = "yyyyMMdd";  
    /** ~~~ 这里扩充一下格式，防止手动建立的不统一*/  
    private static final String DEFAULT_SUB_FOLDER_FORMAT_NO_AUTO = "yyyy-MM-dd";  
  
  
    /** 
     * 创建目录 
     *  
     * @return 
     */  
    private File buildFolder(HttpServletRequest request) {  
//        String realPath = request.getSession().getServletContext()  
//                .getRealPath(""); 
    	
        String realPath = getDocumentRoot(); 
  
        String folderdir = realPath +Constants.UPLOAD_IMAGES_PATH;  
        if (log.isDebugEnabled()) {  
            log.debug("folderdir" + folderdir);  
        }  
  
        if (StringUtils.isBlank(folderdir)) {  
            log.error("路径错误:" + folderdir);  
            return null;  
        }  
  
        File floder = new File(folderdir);  
        if (!floder.exists()) {  
            if (!floder.mkdirs()) {  
                log.error("创建文件夹出错！path=" + folderdir);  
                return null;  
            }  
  
        }  
        // 再往下的文件夹都是以时间字符串来命名的，所以获取最新时间的文件夹即可  
        String[] files = floder.list();  
        if (null != files && 0 < files.length) {  
            // 含有子文件夹，则获取最新的一个  
            Date oldDate = null;  
            int index = -1;  
            for (int i = 0; i < files.length; i++) {  
                String fileName = files[i];  
  
                try {  
                    Date thisDate = DateUtils.parseDate(fileName, new String[] {  
                            DEFAULT_SUB_FOLDER_FORMAT_AUTO, DEFAULT_SUB_FOLDER_FORMAT_NO_AUTO });  
                    if (oldDate == null) {  
                        oldDate = thisDate;  
                        index = i;  
                    } else {  
                        if (thisDate.after(oldDate)) {  
                            // 保存最新的时间和数组中的下标  
                            oldDate = thisDate;  
                            index = i;  
                        }  
                    }  
                } catch (ParseException e) {  
                    // 这里异常吃掉，不用做什么，如果解析失败，会建立新的文件夹，防止人为的建立文件夹导致的异常。  
                }  
            }// for  
  
            // 判断当前最新的文件夹下是否已经存在了最大数目的图片  
            if (null != oldDate && -1 != index) {  
                File pointfloder = new File(folderdir + File.separator  
                        + files[index]);  
                if (!pointfloder.exists()) {  
                    if (!pointfloder.mkdirs()) {  
                        log.error("创建文件夹出错！path=" + folderdir);  
                        return null;  
                    }  
                }  
  
                // 如果文件夹下的文件超过了最大值，那么也需要新建一个文件夹  
                String[] pointfloderFiles = pointfloder.list();  
                if (null != pointfloderFiles  
                        && 1000 < pointfloderFiles.length) {  
                    return buildNewFile(folderdir);  
                }  
  
                return pointfloder;  
            }  
              
            // 查找当前子文件夹失败，新建一个  
            return buildNewFile(folderdir);  
        } else {  
            // 不含有子文件夹，新建一个，通常系统首次上传会有这个情况  
            return buildNewFile(folderdir);  
        }  
  
    }  
      
    /** 
     * 创建一个新文件 
     * @param path 
     * @return 
     */  
    private File buildNewFile(String path){  
        // 不含有子文件夹，新建一个，通常系统首次上传会有这个情况  
        File newFile = buildFileBySysTime(path);  
        if (null == newFile) {  
            log.error("创建文件夹失败！newFile=" + newFile);  
        }  
  
        return newFile;  
    }  
  
    /** 
     * 根据当前的时间建立文件夹，时间格式yyyyMMdd 
     *  
     * @param path 
     * @return 
     */  
    private File buildFileBySysTime(String path) {  
        DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);  
        String fileName = df.format(new Date());  
        File file = new File(path + File.separator + fileName);  
        if (!file.mkdirs()) {  
            return null;  
        }  
        return file;  
    }  
	
    /** ~~~ 上传文件的保存路径 */  
    private static final String FILE_UPLOAD_DIR = Constants.UPLOAD_IMAGES_PATH;  
  
    private String fo;
  
    // CkEditor 浏览图片
	//@Action(value = "/ckbrowerServer",results = { @Result(name = "success", location = "/pages/admin/pri/content/browse.jsp")})
    public String processBrowerPost() {  
		try {
	        String typeStr = type;  
	        String floderName = fo;
	          
	        if (log.isDebugEnabled()) {  
	            log.debug("浏览文件，文件格式:{} 目录 {} getServerUrl {}",new String[]{ typeStr,fo,getServerUrl()});  
	        }  
	        // 定位到目标文件夹 ： 上传目录  
	        String realPath = "";  
	        if(StringUtils.isNotBlank(floderName)){  
	            floderName = URLDecoder.decode(floderName);  
	            // 如果请求中存在文件夹名称，则定位到文件夹中  
//	            realPath = getSession().getServletContext().getRealPath(floderName);  
	            realPath = getDocumentRoot()+floderName;  
	            if(log.isInfoEnabled()){  
	                log.info("sub floder:"+realPath);  
	            }  
	        }else if(StringUtils.equalsIgnoreCase(typeStr, "Image")){
	            // 如果请求中不存在文件夹名称，则使用默认的文件夹
//	        	realPath = getSession().getServletContext().getRealPath(FILE_UPLOAD_DIR);
	            realPath = getDocumentRoot();
	            realPath = realPath+FILE_UPLOAD_DIR;
	            if(log.isInfoEnabled()){  
	                log.info("default floder:"+realPath);  
	            }  
	            File folder = new File(realPath);  
	            if(!folder.exists()){
	            	log.warn("创建默认目录"+realPath);
	            	folder.mkdirs();
	            }  
	        }  
	          
	        File folder = new File(realPath);  
	        if(!folder.exists()){
	        	log.warn("目录不存在");
	            return null;  
	        }  
	          
	        // 存储子目录 ,路径需要从/freemarker开始  
	        List<String> subFolderSet = new ArrayList<String>();  
	        // 存储文件夹  
	        List<String> subFileerSet = new ArrayList<String>();  
	          
	        File[] subFiles = folder.listFiles();  
	        if(null != subFiles && 0 < subFiles.length){  
	            for(int i=0;i < subFiles.length; i++){  
	                File _file = subFiles[i];  
	                if(_file.isDirectory()){  
	                    subFolderSet.add(getDefaultFolder(_file));  
	                } else {  
	                    subFileerSet.add(_file.getName());  
	                }  
	            }  
	        }
	        
	        List<Folder> parentFolder = new ArrayList<Folder>(); 
	        List<Folder> childFolder = new ArrayList<Folder>(); 
	        List<String> imgList = new ArrayList<String>(); 
	        
            // 如果是子文件夹，显示上级目录链接  
            if(StringUtils.isNotBlank(floderName) && !checkIsRoot(folder)){  
                String parent = getDefaultFolder(folder.getParentFile());  
                if(log.isDebugEnabled()){  
                    log.debug("发现上级目录:"+ parent);  
                }
                
                Folder p = new Folder();
//                p.setName(parent.substring(1+parent.lastIndexOf("\\")));
                p.setName(parent);
                p.setPath(URLEncoder.encode(parent));
                parentFolder.add(p);
            }  
              
            // 如果是文件夹，则显示文件夹并且可以再次触发下级和上级目录  
            if(0 < subFolderSet.size()){  
                Iterator<String> subFolderSetIndex = subFolderSet.iterator();  
                while(subFolderSetIndex.hasNext()){  
                    String ftemp = subFolderSetIndex.next();  
                    
                    Folder p = new Folder();
                    p.setName(ftemp.substring(1+ftemp.lastIndexOf(File.separator)));
                    p.setPath(URLEncoder.encode(ftemp));
                    childFolder.add(p);
                    
                }  
            }  
              
            // 如果是文件，则点击就选择文件到控件中  
            if(0 < subFileerSet.size()){ 
                Iterator<String> subFileerSetIndex = subFileerSet.iterator();  
                while(subFileerSetIndex.hasNext()){  
                    String ftemp = subFileerSetIndex.next();  
                    String f = getDefaultFolder(folder);  
                    
                    String fileUrl = f + File.separator + ftemp;  

                    log.debug(fileUrl);
//                    fileUrl = getBasePath()+StringUtils.replace(fileUrl, "\\", "/");  
                    fileUrl = getServerUrl()+StringUtils.replace(fileUrl, "\\", "/");  
                      
                    if(log.isDebugEnabled()){  
                        log.debug("添加文件："+fileUrl);  
                    } 
                    imgList.add(fileUrl);
                }  
            }
            
            getRequest().setAttribute("parentFolder", parentFolder);
            getRequest().setAttribute("childFolder", childFolder);
            getRequest().setAttribute("imgList", imgList);
        } catch (Exception e) {  
        	log.error("浏览图片失败",e);
        	
        }
        return SUCCESS;
    }  
      
    /** 
     * 获取文件夹路径 
     * @return 
     */  
    private static String getDefaultFolder(File folder){
        String path = folder.getPath(); 
        
        System.out.println("getDefaultFolder path = "+path);
//        if(!path.endsWith(File.separator)){
//        	path+=File.separator;
//        }
//        System.out.println("getDefaultFolder path = "+path);
//        System.out.println("getDefaultFolder FILE_UPLOAD_DIR = "+FILE_UPLOAD_DIR.replace("/", File.separator));
        path = path.substring(path.indexOf(FILE_UPLOAD_DIR.replace("/", File.separator)));  
        return path;  
    }  
      
    /** 
     * 判断是否是根目录 
     * @param floderName 
     * @return 
     */  
    private static boolean checkIsRoot(File folder){  
        String name = folder.getName();
        System.out.println("checkIsRoot : "+name);
        System.out.println("checkIsRoot : "+FILE_UPLOAD_DIR);
        return FILE_UPLOAD_DIR.endsWith(name);  
    } 

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public File getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(File thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getThumbnailFileName() {
		return thumbnailFileName;
	}

	public void setThumbnailFileName(String thumbnailFileName) {
		this.thumbnailFileName = thumbnailFileName;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCKEditor() {
		return CKEditor;
	}

	public void setCKEditor(String cKEditor) {
		CKEditor = cKEditor;
	}

	public String getCKEditorFuncNum() {
		return CKEditorFuncNum;
	}

	public void setCKEditorFuncNum(String cKEditorFuncNum) {
		CKEditorFuncNum = cKEditorFuncNum;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getFo() {
		return fo;
	}

	public void setFo(String fo) {
		this.fo = fo;
	}
}
