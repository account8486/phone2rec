package com.wondertek.meeting.action.gift;

import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Gift;
import com.wondertek.meeting.model.GiftOrder;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.GiftOrderService;
import com.wondertek.meeting.service.GiftService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.FileOperatorUtil;
import com.wondertek.meeting.util.StringUtil;

/**
 * 礼品管理
 * 
 * @author 金祝华
 */
public class GiftAction extends BaseAction {

	private static final long serialVersionUID = -3102151360541524276L;
	private static final String SUB_PATH = "gift/";

	private GiftService giftService;
	private GiftOrderService giftOrderService;

	private Gift gift;
	private GiftOrder giftOrder;
	private String amount;// wap传递的订购 数量

	// 礼品图片
	private File img;
	// 礼品图片文件名
	private String imgFileName;
	
	private Long meetingId;

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	public String list() {

		if (gift == null) {
			gift = new Gift();
		}

		gift.setMeetingId(meetingId);

		Pager<Gift> pager = null;
		try {
			pager = giftService.listPager(gift, currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("query gift list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", pager);

		return SUCCESS;
	}

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	public String portalList() {

		if (gift == null) {
			gift = new Gift();
		}

		gift.setMeetingId(meetingId);

		Pager<Gift> pager = null;
		try {
			pager = giftService.listPager(gift, currentPage, 20);
		} catch (ServiceException e) {
			log.error("query gift list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", pager);

		return SUCCESS;
	}

	/**
	 * 客户端查询列表
	 * 
	 * @return
	 */
	public String clientList() {

		if (gift == null) {
			gift = new Gift();
		}

		String mId = this.getParameter("meetingId");
		try {
			Long meetingId = Long.parseLong(mId);
			gift.setMeetingId(meetingId);
		} catch (Exception e) {
			log.error("meetingID不是数字型,mId=" + mId);
			return ERROR;
		}

		Pager<Gift> pager = null;
		try {
			pager = giftService.listPager(gift, currentPage, 10);
		} catch (ServiceException e) {
			log.error("query gift list error: " + e.toString());
		}

		this.getRequest().setAttribute("meetingId", mId);
		this.getRequest().setAttribute("pager", pager);

		return SUCCESS;
	}

	/**
	 * 跳转到新增
	 * 
	 * @return
	 */
	public String goAdd() {

		// nothing to do for the moment
		log.debug("meetingId=" + meetingId);
		
		return SUCCESS;
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	public String add() {

		if (gift == null) {
			errMsg = "新增礼品失败。";
			return INPUT;
		}

		gift.setMeetingId(meetingId);// 会议id
		gift.setModifyTime(new Date());
		gift.setState(SysConstants.DB_STATUS_VALID);

		// 新增礼品
		try {
			giftService.add(gift);
		} catch (ServiceException e) {
			log.error("add gift failed! ", e);
			errMsg = "新增礼品失败。";
			return INPUT;
		}

		// 保存礼品图片
		try {
			uploadImg();

			// 更新礼品图片url
			giftService.modify(gift);
		} catch (Throwable e) {

			log.error("新增礼品时，图片处理失败！", e);
			errMsg = "新增礼品失败。";
			return INPUT;
		}

		errMsg = "成功添加礼品。";
		return SUCCESS;
	}

	private void uploadImg() throws Throwable {
		// 礼品图片要保存到的服务器路径(文件根路径/gift/id)
		File saveDir = new File(this.getDocumentRoot() + SUB_PATH + String.valueOf(gift.getId()));
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}

		String nowStr = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");

		int index = imgFileName.lastIndexOf(".");
		String filePostfix = "";
		if (index > 0) {
			filePostfix = imgFileName.substring(index);
		}

		// 要保存的目标文件
		File saveFile = new File(saveDir + File.separator + nowStr + filePostfix);

		// 图片url
		String imgUrl = SUB_PATH + String.valueOf(gift.getId()) + "/" + nowStr + filePostfix;

		// 存储文件
		FileOperatorUtil.copy(img, saveFile);

		gift.setImgUrl(imgUrl);
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
			gift = giftService.findById(idL);
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
		if (gift == null) {
			errMsg = "对不起，修改礼品失败！";
			return INPUT;
		}

		Gift oldGift = null;
		try {
			oldGift = giftService.findById(gift.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldGift == null) {
			errMsg = "对不起，修改礼品失败！";
			log.error("礼品不存在，id=" + gift.getId());
			return INPUT;
		}

		oldGift.setName(gift.getName());
		oldGift.setPrice(gift.getPrice());
		oldGift.setIntroduction(gift.getIntroduction());
		oldGift.setModifyTime(new Date());

		// 修改
		try {
			if (img != null) {
				uploadImg();
				// 更新礼品图片url
				oldGift.setImgUrl(gift.getImgUrl());
			}

			giftService.modify(oldGift);
		} catch (Throwable e) {
			errMsg = "对不起，修改礼品失败！";
			return INPUT;
		}

		errMsg = "成功修改礼品。";
		return SUCCESS;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String del() {

		String id = this.getParameter("id");
		Long idL = StringUtil.stringToLong(id);

		Gift oldGift = null;
		try {
			oldGift = giftService.findById(idL);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldGift == null) {
			return SUCCESS;
		}

		oldGift.setState(0);
		oldGift.setModifyTime(new Date());

		// 删除
		try {
			giftService.modify(oldGift);
		} catch (ServiceException e) {
			log.error("删除礼品失败", e);
			errMsg = "删除失败。";
		}

		errMsg = "删除成功。";
		return SUCCESS;
	}

	/**
	 * 礼品详情
	 * 
	 * @return
	 */
	public String getDetail() {

		String id = this.getParameter("id");
		Long idL = StringUtil.stringToLong(id);

		try {
			gift = giftService.findById(idL);
			gift.setIntroduction(gift.getIntroduction().replaceAll("\r\n", "<br/>"));
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * 订购礼品
	 * 
	 * @return
	 */
	public String wapOrder() {
		if (StringUtil.isEmpty(amount)) {
			errMsg = "请输入订购数量";
			return INPUT;
		}

		Matcher m = Pattern.compile("^\\d{1,6}$").matcher(amount);

		if (!m.find() || Integer.parseInt(amount) == 0) {
			errMsg = "请输入6位以内整数订购数量。";
			return INPUT;
		}

		Long giftId = gift.getId();

		try {
			gift = giftService.findById(giftId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (giftOrder == null) {
			giftOrder = new GiftOrder();
		}
		giftOrder.setAmount(Integer.parseInt(amount));
		giftOrder.setMeetingId(meetingId);// 会议id
		User user = new User();
		user.setId(this.getUserIdFromSession());
		giftOrder.setUser(user); // 用户
		giftOrder.setGift(gift);// 礼品
		giftOrder.setTotalPrice(giftOrder.getAmount() * gift.getPrice());// 订单金额
		giftOrder.setModifyTime(new Date());// 时间
		giftOrder.setState(SysConstants.DB_STATUS_VALID);// 状态

		try {
			giftOrderService.add(giftOrder);
		} catch (ServiceException e) {
			errMsg = "对不起，订购失败。";
			log.error("订购失败！", e);
		}

		errMsg = "订购成功。";

		return SUCCESS;
	}

	/**
	 * 订购礼品
	 * 
	 * @return
	 */
	public String clientOrder() {
		if (StringUtil.isEmpty(amount)) {
			errMsg = "请输入订购数量";
			return INPUT;
		}

		Matcher m = Pattern.compile("^\\d{1,6}$").matcher(amount);

		if (!m.find() || Integer.parseInt(amount) == 0) {
			errMsg = "请输入6位以内整数订购数量。";
			return INPUT;
		}

		Long giftId = gift.getId();

		try {
			gift = giftService.findById(giftId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (giftOrder == null) {
			giftOrder = new GiftOrder();
		}
		giftOrder.setAmount(Integer.parseInt(amount));
		giftOrder.setMeetingId(gift.getMeetingId());// 会议id
		User user = new User();
		user.setId(this.getUserIdFromSession());
		giftOrder.setUser(user); // 用户
		giftOrder.setGift(gift);// 礼品
		giftOrder.setTotalPrice(giftOrder.getAmount() * gift.getPrice());// 订单金额
		giftOrder.setModifyTime(new Date());// 时间
		giftOrder.setState(SysConstants.DB_STATUS_VALID);// 状态

		try {
			giftOrderService.add(giftOrder);
		} catch (ServiceException e) {
			errMsg = "对不起，订购失败。";
			log.error("订购失败！", e);
		}

		errMsg = "订购成功。";

		return SUCCESS;
	}

	/**
	 * 订购礼品
	 * 
	 * @return
	 */
	public String order() {
		Long giftId = gift.getId();

		try {
			gift = giftService.findById(giftId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		giftOrder.setMeetingId(meetingId);// 会议id
		User user = new User();
		user.setId(this.getUserIdFromSession());
		giftOrder.setUser(user); // 用户
		giftOrder.setGift(gift);// 礼品
		giftOrder.setTotalPrice(giftOrder.getAmount() * gift.getPrice());// 订单金额
		giftOrder.setModifyTime(new Date());// 时间
		giftOrder.setState(SysConstants.DB_STATUS_VALID);// 状态

		try {
			giftOrderService.add(giftOrder);
		} catch (ServiceException e) {
			errMsg = "对不起，订购失败。";
			log.error("订购失败！", e);
		}

		errMsg = "订购成功。";

		return SUCCESS;
	}

	public String orderSuc() {
		return SUCCESS;
	}

	public GiftService getGiftService() {
		return giftService;
	}

	public void setGiftService(GiftService giftService) {
		this.giftService = giftService;
	}

	public Gift getGift() {
		return gift;
	}

	public void setGift(Gift gift) {
		this.gift = gift;
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public GiftOrderService getGiftOrderService() {
		return giftOrderService;
	}

	public void setGiftOrderService(GiftOrderService giftOrderService) {
		this.giftOrderService = giftOrderService;
	}

	public GiftOrder getGiftOrder() {
		return giftOrder;
	}

	public void setGiftOrder(GiftOrder giftOrder) {
		this.giftOrder = giftOrder;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
}
