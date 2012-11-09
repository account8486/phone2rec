/**
 * 处理会议下创建特产信息Action
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-17
 */
package com.wondertek.meeting.action.reception;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.reception.LocalSpecialty;
import com.wondertek.meeting.model.reception.MeetingSpecialty;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.reception.MeetingSpecialtyService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.FileOperatorUtil;

@SuppressWarnings("serial")
public class MeetingSpecialtyAction extends BaseAction {
	private MeetingService meetingService;
	private MeetingSpecialtyService meetingSpecialtyService;
	
	//用于保存请求参数
	private MeetingSpecialty meetingSpecialty = new MeetingSpecialty(); 
	private LocalSpecialty localSpecialty = new LocalSpecialty(); 
	private Long meetingId; 
	private Integer meetingSpecialtyId;
	private Integer localSpecialtyId;
	private String actionType;
	
	//用于上传图片
	private File localSpecialtyImage;  
	private String localSpecialtyImageFileName;
	private static final String IMAGE_UPLOAD_PATH = "specialty";
	
	/**
	 * 查看指定会议下的土特产信息
	 */
	public String show() throws Exception {
		MeetingSpecialty specialty = this.meetingSpecialtyService.findByMeetingId(meetingId);
		this.setAttribute("specialty", specialty);
		
		if(specialty != null) {
			if(specialty.getMemo() != null) {
				specialty.setMemo(specialty.getMemo().replaceAll("\r\n", "<br/>")); //在显示时转换成换行符
			}
			Meeting meeting = this.meetingService.findById(meetingId);
			localSpecialty.setProvince(meeting.getProvince());
			localSpecialty.setCity(meeting.getCity());
			Pager<LocalSpecialty> pager = this.meetingSpecialtyService
				.findAllLocalSpecialtyPager(specialty.getId(), localSpecialty, currentPage, pageSize);
			this.setAttribute("pager", pager);
		}
		return "specialtyList";
	}
	
	/**
	 * 预览指定会议下的土特产信息
	 */
	public String preview() throws Exception {
		MeetingSpecialty specialty = this.meetingSpecialtyService.findByMeetingId(meetingId);
		this.setAttribute("specialty", specialty);
		
		if(specialty != null) {
			if(specialty.getMemo() != null) {
				specialty.setMemo(specialty.getMemo().replaceAll("\r\n", "<br/>")); //在显示时转换成换行符
			}
			Meeting meeting = this.meetingService.findById(meetingId);
			localSpecialty.setProvince(meeting.getProvince());
			localSpecialty.setCity(meeting.getCity());
			Pager<LocalSpecialty> pager = this.meetingSpecialtyService
				.findAllLocalSpecialtyPager(specialty.getId(), localSpecialty, currentPage, pageSize);
			this.setAttribute("pager", pager);
		}
		return "specialtyPreview";
	}
	
	/**
	 * 发布会议下的土特产请求
	 */
	public String addReq() throws Exception {
		Meeting meeting = this.meetingService.findById(meetingId);
		this.setAttribute("meeting", meeting);
		return "meetingSpecialtyInfo";
	}
	
	/**
	 * 编辑会议下的土特产请求
	 */
	public String editReq() throws Exception {
		this.meetingSpecialty = this.meetingSpecialtyService.findById(meetingSpecialtyId);
		Meeting meeting = this.meetingService.findById(meetingId);
		this.setAttribute("meeting", meeting);
		return "meetingSpecialtyInfo";
	}
	
	/**
	 * 保存会议下的土特产信息
	 */
	public String save() throws Exception {
		Meeting meeting = this.meetingService.findById(meetingId);
		MeetingSpecialty entity = null;
		if(meetingSpecialtyId == null || meetingSpecialtyId == 0) { //add
			entity = new MeetingSpecialty();
			entity.setMeeting(meeting);
			entity.setMemo(this.meetingSpecialty.getMemo());
			entity.setCreateTime(new Date());
			entity.setState(1);
		} else {
			entity = this.meetingSpecialtyService.findById(meetingSpecialtyId);
			entity.setMemo(this.meetingSpecialty.getMemo());
			entity.setState(this.meetingSpecialty.getState());
			
			//如果会议的区域地址已经修改且与当前编辑的特产信息的区域不同，则调整特产所属的区域与会议区域保持一致
			String province = meeting.getProvince();
			String city = meeting.getCity();
			if(!province.equals(entity.getProvince()) || !city.equals(entity.getCity())) {
				this.meetingSpecialtyService.removeAllLocalSpecialtyFromMeetingSpecialty(meetingSpecialtyId);
			}
		}
		entity.setProvince(meeting.getProvince());
		entity.setCity(meeting.getCity());
		this.meetingSpecialtyService.saveOrUpdate(entity);
		
		this.meetingSpecialty = new MeetingSpecialty();
		this.localSpecialty = new LocalSpecialty();
		return this.show();
	}
	
	/**
	 * 删除会议下的土特产请求
	 */
	public String delete() throws Exception {
		MeetingSpecialty entity = this.meetingSpecialtyService.findById(meetingSpecialtyId);
		this.meetingSpecialtyService.delete(entity);

		this.meetingSpecialty = new MeetingSpecialty();
		this.localSpecialty = new LocalSpecialty();
		return this.show();
	}

	/**
	 * 添加会议下的土特产明细信息请求
	 */
	public String addLocalSpecialtyReq() throws Exception {
		Meeting meeting = this.meetingService.findById(meetingId);
		this.setAttribute("meeting", meeting);
		return "localSpecialtyInfo";
	}
	
	
	/**
	 * 添加会议下的土特产明细信息请求
	 */
	public String editLocalSpecialtyReq() throws Exception {
		Meeting meeting = this.meetingService.findById(meetingId);
		this.setAttribute("meeting", meeting);
		this.localSpecialty = this.meetingSpecialtyService.findLocalSpecialtyById(localSpecialtyId);
		this.actionType = "editLocalSpecialty";
		return "localSpecialtyInfo";
	}
	
	/**
	 * 查找指定区域下的土特产明细信息列表
	 */
	public String searchLocalSpecialty() throws Exception {
		List<Object[]> specialtyList = this.meetingSpecialtyService.searchLocalSpecialty(localSpecialty);
		this.resultMap.put("specialtyList", specialtyList);
		return "json";
//		return this.json2Resp(specialtyList);
	}
	
	/**
	 * 查找ID的土特产明细
	 */
	public String findSpecialty() throws Exception {
		LocalSpecialty specialty = this.meetingSpecialtyService.findLocalSpecialtyById(localSpecialtyId);
		specialty.setMeetingSpecialtySet(null); //去除关联避开延迟加载的问题
		
		return this.json2Resp(specialty);
	}
	
	/**
	 * 添加土特产信息
	 */
	public String addLocalSpecialty() throws Exception {
		Meeting meeting = this.meetingService.findById(meetingId);
		
		if(this.localSpecialtyId == null || this.localSpecialtyId == 0) { //add
			localSpecialty.setProvince(meeting.getProvince());
			localSpecialty.setCity(meeting.getCity());
			this.meetingSpecialtyService.saveOrUpdateLocalSpecialty(localSpecialty); //保存关联
			
			//上传图片信息
			if(this.localSpecialtyImage != null) {
				String imageUrl = this.uploadImage(meetingSpecialtyId);
				localSpecialty.setImage(imageUrl);
				this.meetingSpecialtyService.saveOrUpdateLocalSpecialty(localSpecialty); //更新图片地址
			}
			
			//设置关联
			this.meetingSpecialtyService.addLocalSpecialtyFromMeetingSpecialty(meetingSpecialtyId, localSpecialty);
		} else { //使用已经在系统中存在的土特产信息
			if("chooseLocalSpecialty".equals(actionType)) { //选择已经存在的
				//判断是否存在关联
				LocalSpecialty entity = this.meetingSpecialtyService.findLocalSpecialtyFromMeetingSpecialty(meetingSpecialtyId, localSpecialtyId);
				if(entity != null) {
					this.errMsg = "选择的土特产[" + entity.getName() + "]已经在本次会议中发布过了";
					this.setAttribute("meeting", meeting);
					return "localSpecialtyInfo";
				}
				
				//把土特产信息保存到会议关联的特产信息下面
				entity = this.meetingSpecialtyService.findLocalSpecialtyById(localSpecialtyId);
				this.meetingSpecialtyService.addLocalSpecialtyFromMeetingSpecialty(meetingSpecialtyId, entity);
			} else if("editLocalSpecialty".equals(actionType)) { //编辑已经存在的
				LocalSpecialty entity = this.meetingSpecialtyService.findLocalSpecialtyById(localSpecialtyId);
				entity.setName(localSpecialty.getName());
				entity.setMemo(localSpecialty.getMemo());
				
				//上传图片信息
				if(this.localSpecialtyImage != null) {
					String imageUrl = this.uploadImage(meetingSpecialtyId);
					entity.setImage(imageUrl);
				}
				
				this.meetingSpecialtyService.saveOrUpdateLocalSpecialty(entity); //更新
			}
		}
		
		this.meetingSpecialty = new MeetingSpecialty();
		this.localSpecialty = new LocalSpecialty();
		return this.show();
	}
	
	/**
	 * 上传土特产图片
	 */
	private String uploadImage(Integer localSpecialtyId) throws Exception {
		// 图片要保存到的服务器路径(文件根路径/specialty/id)
		File saveDir = new File(this.getDocumentRoot() + IMAGE_UPLOAD_PATH 
				+ File.separator + localSpecialtyId);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}

		//生成文件名
		String nowStr = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmssSSS");
		int index = this.localSpecialtyImageFileName.lastIndexOf(".");
		String extName = "";
		if (index > 0) {
			extName = localSpecialtyImageFileName.substring(index);
		}

		// 要保存的目标文件
		File saveFile = new File(saveDir + File.separator + nowStr + extName);
		// 图片url
		String imgUrl = IMAGE_UPLOAD_PATH + "/" + localSpecialtyId + "/" + nowStr + extName;
		// 存储文件
		FileOperatorUtil.copy(localSpecialtyImage, saveFile);
		return imgUrl;
	}
	
	//移除土特产信息
	public String removeLocalSpecialty() throws Exception {
		this.meetingSpecialtyService.removeLocalSpecialtyFromMeetingSpecialty(meetingSpecialtyId, localSpecialtyId);
		
		this.meetingSpecialty = new MeetingSpecialty();
		this.localSpecialty = new LocalSpecialty();
		return this.show();
	}
	
	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public MeetingSpecialtyService getMeetingSpecialtyService() {
		return meetingSpecialtyService;
	}

	public void setMeetingSpecialtyService(
			MeetingSpecialtyService meetingSpecialtyService) {
		this.meetingSpecialtyService = meetingSpecialtyService;
	}

	public MeetingSpecialty getMeetingSpecialty() {
		return meetingSpecialty;
	}

	public void setMeetingSpecialty(MeetingSpecialty meetingSpecialty) {
		this.meetingSpecialty = meetingSpecialty;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public Integer getMeetingSpecialtyId() {
		return meetingSpecialtyId;
	}

	public void setMeetingSpecialtyId(Integer meetingSpecialtyId) {
		this.meetingSpecialtyId = meetingSpecialtyId;
	}

	public Integer getLocalSpecialtyId() {
		return localSpecialtyId;
	}

	public void setLocalSpecialtyId(Integer localSpecialtyId) {
		this.localSpecialtyId = localSpecialtyId;
	}

	public LocalSpecialty getLocalSpecialty() {
		return localSpecialty;
	}

	public void setLocalSpecialty(LocalSpecialty localSpecialty) {
		this.localSpecialty = localSpecialty;
	}

	public File getLocalSpecialtyImage() {
		return localSpecialtyImage;
	}

	public void setLocalSpecialtyImage(File localSpecialtyImage) {
		this.localSpecialtyImage = localSpecialtyImage;
	}

	public String getLocalSpecialtyImageFileName() {
		return localSpecialtyImageFileName;
	}

	public void setLocalSpecialtyImageFileName(String localSpecialtyImageFileName) {
		this.localSpecialtyImageFileName = localSpecialtyImageFileName;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	
}
