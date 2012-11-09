package com.wondertek.meeting.action.reception;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.reception.Hotel;
import com.wondertek.meeting.model.reception.HotelImage;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.reception.HotelImageService;
import com.wondertek.meeting.service.reception.HotelService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.FileOperatorUtil;
import com.wondertek.meeting.util.StringUtil;

/**
 * 酒店管理
 * 
 * @author 金祝华
 */
public class HotelAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2890708478406837352L;

	private static final String SUB_PATH = "hotel/";// 图片在文件服务器上的存放子路径

	private HotelService hotelService;// 酒店

	private HotelImageService hotelImageService;// 酒店图片

	private MeetingService meetingService;// 会议

	private Hotel hotel;

	private Long meetingId;
	private Long hotelImageId;

	private String[] imgTitle; // 图片标题
	private Integer[] imgSort;// 图片排序

	// 酒店图片
	private File[] img;
	// 酒店图片文件名
	private String[] imgFileName;

	private String[] oldImgUrl;

	private String actionType; // 新增时处理类型

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	public String list() {
		Pager<Hotel> pager = null;
		try {
			pager = hotelService.findPager(hotel, meetingId, currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("query list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", pager);

		return SUCCESS;
	}

	/**
	 * 门户查询酒店列表
	 * 
	 * @return
	 */
	public String portalList() {
		List<Hotel> hotelList = hotelService.findHotelListByMeeting(meetingId);
		this.getRequest().setAttribute("hotelList", hotelList);

		return SUCCESS;
	}

	/**
	 * 获取酒店图片信息
	 * 
	 * @return
	 */
	public String getHotelImage() {
		String hotelId = this.getParameter("hotelId");
		String imgIndex = this.getParameter("imgIndex");// 图片顺序，从1开始计算

		Integer index = StringUtil.parseInteger(imgIndex);

		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			hotel = hotelService.findById(StringUtil.stringToLong(hotelId));

			List<HotelImage> images = hotel.getImages();
			if (index < 1 || index > images.size()) {
				resultMap.put("result", "false");
				return json2Resp(resultMap); // 范围超标
			}
			HotelImage hotelImage = images.get(index - 1);
			resultMap.put("result", "true");
			resultMap.put("title", hotelImage.getTitle());
			resultMap.put("address", hotelImage.getAddress());
			resultMap.put("hotelId", hotelId);
			resultMap.put("imgIndex", imgIndex);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return this.json2Resp(resultMap);
	}

	/**
	 * 跳转到新增
	 * 
	 * @return
	 */
	public String goAdd() {
		Meeting meeting = null;
		try {
			meeting = meetingService.findById(meetingId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}

		this.getRequest().setAttribute("meeting", meeting);

		return SUCCESS;
	}

	/**
	 * 根据名称模糊查询已经存在的酒店列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listByName() {
		List<Hotel> hotelList = hotelService.findHotelListByName(hotel);

		return this.json2Resp(hotelList);
	}

	/**
	 * 根据ID查询酒店信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findById() {
		try {
			hotel = hotelService.findById(hotel.getId());
		} catch (ServiceException e) {
			String errCode = e.getMessage();
			log.error("errCode:" + errCode + "detail:" + e.toString());
		}

		hotel.setMeetings(null);

		return this.json2Resp(hotel);
	}

	/**
	 * 添加酒店
	 * 
	 * @return
	 */
	public String add() {

		// 如果是添加已经存在的酒店到会议，则只要将酒店关联到当前会议即可。
		if (StringUtil.isNotEmpty(actionType) && "chooseHotel".equals(actionType)) {

			try {
				hotel = hotelService.findById(hotel.getId());
			} catch (ServiceException e) {
				log.error(e.toString());
			}

			// 判断该酒店是否已经存在于该会议中。
			Set<Meeting> meetings = hotel.getMeetings();
			if (meetings != null && meetings.size() > 0) {
				Iterator<Meeting> itr = meetings.iterator();
				while (itr.hasNext()) {
					if (itr.next().getId().equals(meetingId)) {
						errMsg = "添加酒店成功。";
						return SUCCESS;// 如果已经存在，直接返回成功。
					}
				}
			}

			Meeting meeting = new Meeting();
			meeting.setId(meetingId);
			hotel.getMeetings().add(meeting);
			try {
				hotelService.modify(hotel);
			} catch (ServiceException e) {
				log.error("关联酒店到当前会议失败，{}", e.toString());
				errMsg = "新增酒店失败。";
				return INPUT;
			}

			return SUCCESS;
		} else {
			if (hotel == null) {
				errMsg = "新增酒店失败。";
				return INPUT;
			}

			Meeting meeting = new Meeting();
			meeting.setId(meetingId);
			Set<Meeting> meetings = new HashSet<Meeting>();
			meetings.add(meeting);
			hotel.setMeetings(meetings);// 会议
			hotel.setState(SysConstants.DB_STATUS_VALID);
			hotel.setCreateTime(new Date());

			// 新增酒店
			try {
				hotelService.add(hotel);
			} catch (ServiceException e) {
				log.error("add hotel failed! ", e);
				errMsg = "对不起，新增酒店失败，请稍后再试。";
				return INPUT;
			}

			// 保存酒店图片
			try {
				if (img != null && img.length > 0) {
					uploadImg();

					// 酒店图片信息存入数据库
					hotelService.modify(hotel);
				}
			} catch (Throwable e) {
				log.error("新增酒店时，图片处理失败！", e);
				errMsg = "新增酒店失败。";
				return INPUT;
			}

			errMsg = "添加酒店成功。";
			return SUCCESS;
		}
	}

	/*
	 * 保存图片到文件服务器，并将图片信息存入DB
	 */
	private void uploadImg() throws Throwable {

		// 酒店图片要保存到的服务器路径(文件根路径/hotel/id)
		StringBuilder sb = new StringBuilder();
		sb.append(this.getDocumentRoot());
		sb.append(SUB_PATH);
		sb.append(hotel.getId());

		File saveDir = new File(sb.toString());
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}

		List<HotelImage> images = new ArrayList<HotelImage>();
		hotel.setImages(images);

		for (int i = 0; i < imgFileName.length; i++) {
			String nowStr = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");

			int index = imgFileName[i].lastIndexOf(".");
			String filePostfix = "";// 文件扩展名
			if (index > 0) {
				filePostfix = imgFileName[i].substring(index);
			}

			// 要保存的目标文件(图片文件名重命名为当前时间)
			sb.delete(0, sb.toString().length());
			sb.append(saveDir);
			sb.append(File.separator);
			sb.append(nowStr);
			sb.append("_");
			sb.append(i);
			sb.append(filePostfix);
			File saveFile = new File(sb.toString());

			sb.delete(0, sb.toString().length());
			sb.append(SUB_PATH);
			sb.append(hotel.getId());
			sb.append("/");
			sb.append(nowStr);
			sb.append("_");
			sb.append(i);
			sb.append(filePostfix);

			// 图片url
			String imgUrl = sb.toString();

			// 存储文件
			FileOperatorUtil.copy(img[i], saveFile);

			HotelImage hotelImage = new HotelImage();
			hotelImage.setTitle(imgTitle[i]);// 标题
			hotelImage.setSort(imgSort[i]);// 排序
			hotelImage.setAddress(imgUrl);// 访问地址
			hotelImage.setUploadTime(new Date());// 上传时间
			hotelImage.setUploadUser(this.getAdminUserFromSession());// 上传人
			// 保存图片对象入库
			hotelImageService.add(hotelImage);

			images.add(hotelImage);
		}
	}

	/**
	 * 跳转到修改
	 * 
	 * @return
	 */
	public String goUpdate() {

		String id = this.getParameter("id");

		Long idL = StringUtil.stringToLong(id);

		try {
			hotel = hotelService.findById(idL);
		} catch (ServiceException e) {
			String errCode = e.getMessage();
			log.error("errCode:" + errCode + "detail:" + e.toString());
		}

		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String update() {
		if (hotel == null) {
			errMsg = "对不起，修改酒店失败！";
			return INPUT;
		}

		Hotel oldHotel = null;
		try {
			oldHotel = hotelService.findById(hotel.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldHotel == null) {
			errMsg = "对不起，修改酒店失败！";
			log.error("酒店不存在，id=" + hotel.getId());
			return INPUT;
		}
		oldHotel.setAddress(hotel.getAddress());
		oldHotel.setAdditionalInfo(hotel.getAddress());
		oldHotel.setBaseInfo(hotel.getBaseInfo());
		oldHotel.setIntroduction(hotel.getIntroduction());
		oldHotel.setPeripheralInfo(hotel.getPeripheralInfo());
		oldHotel.setAdditionalInfo(hotel.getAdditionalInfo());
		oldHotel.setLinking(hotel.getLinking());
		oldHotel.setService(hotel.getService());
		oldHotel.setFacilities(hotel.getFacilities());
		oldHotel.setModifyTime(new Date());

		// 修改
		try {
			if (img != null && img.length > 0) {
				uploadImgForUpdate(oldHotel);
			}

			// 修改酒店信息
			hotelService.modify(oldHotel);
		} catch (Throwable e) {
			errMsg = "对不起，修改酒店失败！";
			return INPUT;
		}

		errMsg = "成功修改酒店。";
		return SUCCESS;
	}
	
	/**
	 * 删除已经上传的图片
	 */
	public String deleteImage() throws Exception {
		if(hotelImageId == null) {
			this.str2Resp("fail");
			return null;
		}
		
		Hotel oldHotel = hotelService.findById(hotel.getId());
		HotelImage image = this.hotelImageService.findById(hotelImageId);
		
		List<HotelImage> images = oldHotel.getImages();
		for(int i = 0; i < images.size(); i++) {
			HotelImage img = images.get(i);
			if(img != null && img.getId().equals(hotelImageId)) {
				images.remove(i);
				break;
			}
		}
		
		this.hotelService.modify(oldHotel);
		this.hotelImageService.delete(image);
		this.str2Resp("ok");
		return null;
	}

	/*
	 * 保存图片到文件服务器，并将图片信息存入DB
	 */
	private void uploadImgForUpdate(Hotel oldHotel) throws Throwable {

		// 酒店图片要保存到的服务器路径(文件根路径/hotel/id)
		StringBuilder sb = new StringBuilder();
		sb.append(this.getDocumentRoot());
		sb.append(SUB_PATH);
		sb.append(hotel.getId());

		File saveDir = new File(sb.toString());
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}

//		List<HotelImage> images = new ArrayList<HotelImage>();
//
//		// 保留剩余的老图片
//		// 剩余老图片个数
//		int oldImgCnt = (oldImgUrl == null) ? 0 : oldImgUrl.length;
//		for (int i = 0; i < oldImgCnt; i++) {
//			Iterator<HotelImage> itr = oldHotel.getImages().iterator();
//			while (itr.hasNext()) {
//				HotelImage hotelImage = itr.next();
//				if (oldImgUrl[i].equalsIgnoreCase(hotelImage.getAddress())) {
//					images.add(hotelImage);
//				}
//			}
//		}

		// 增加新图片
		for (int i = 0; i < img.length; i++) {
			String nowStr = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");

			int index = imgFileName[i].lastIndexOf(".");
			String filePostfix = "";// 文件扩展名
			if (index > 0) {
				filePostfix = imgFileName[i].substring(index);
			}

			// 要保存的目标文件(图片文件名重命名为当前时间)
			sb.delete(0, sb.toString().length());
			sb.append(saveDir);
			sb.append(File.separator);
			sb.append(nowStr);
			sb.append("_");
			sb.append(i);
			sb.append(filePostfix);
			File saveFile = new File(sb.toString());

			sb.delete(0, sb.toString().length());
			sb.append(SUB_PATH);
			sb.append(hotel.getId());
			sb.append("/");
			sb.append(nowStr);
			sb.append("_");
			sb.append(i);
			sb.append(filePostfix);

			// 图片url
			String imgUrl = sb.toString();

			// 将文件存储到文件服务器
			FileOperatorUtil.copy(img[i], saveFile);

			HotelImage hotelImage = new HotelImage();
			hotelImage.setTitle(imgTitle[i]);// 标题
			hotelImage.setSort(imgSort[i]);// 排序
			hotelImage.setAddress(imgUrl);// 访问地址
			hotelImage.setUploadTime(new Date());// 上传时间
			hotelImage.setUploadUser(this.getAdminUserFromSession());// 上传人
			// 保存图片对象入库
			hotelImageService.add(hotelImage);

			oldHotel.getImages().add(hotelImage);
		}
	}

	/**
	 * 删除酒店 这里只删除会议与酒店的映射关系
	 * 
	 * @return
	 */
	public String del() {
		String id = this.getParameter("id");
		Long idL = StringUtil.stringToLong(id);

		Hotel oldHotel = null;
		try {
			oldHotel = hotelService.findById(idL);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldHotel == null) {
			return SUCCESS;
		}

		// 从酒店所属会议列表中移去当前会议
		Set<Meeting> meetings = oldHotel.getMeetings();
		Iterator<Meeting> itr = meetings.iterator();
		while (itr.hasNext()) {
			if (meetingId.equals(itr.next().getId())) {
				itr.remove();
			}
		}
		oldHotel.setModifyTime(new Date());

		// 删除
		try {
			hotelService.modify(oldHotel);
		} catch (ServiceException e) {
			log.error("删除酒店失败", e);
			errMsg = "删除失败。";
		}

		errMsg = "删除成功。";
		return SUCCESS;
	}

	public HotelService getHotelService() {
		return hotelService;
	}

	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	public HotelImageService getHotelImageService() {
		return hotelImageService;
	}

	public void setHotelImageService(HotelImageService hotelImageService) {
		this.hotelImageService = hotelImageService;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public String[] getImgTitle() {
		return imgTitle;
	}

	public void setImgTitle(String[] imgTitle) {
		this.imgTitle = imgTitle;
	}

	public Integer[] getImgSort() {
		return imgSort;
	}

	public void setImgSort(Integer[] imgSort) {
		this.imgSort = imgSort;
	}

	public File[] getImg() {
		return img;
	}

	public void setImg(File[] img) {
		this.img = img;
	}

	public String[] getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String[] imgFileName) {
		this.imgFileName = imgFileName;
	}

	public String[] getOldImgUrl() {
		return oldImgUrl;
	}

	public void setOldImgUrl(String[] oldImgUrl) {
		this.oldImgUrl = oldImgUrl;
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Long getHotelImageId() {
		return hotelImageId;
	}

	public void setHotelImageId(Long hotelImageId) {
		this.hotelImageId = hotelImageId;
	}
}
