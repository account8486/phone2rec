package com.wondertek.meeting.action.meeting;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.WriterException;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.QRCodeGen;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MeetingService;

/**
 * 二维码
 * 
 * @author 金祝华
 */
public class QRCodeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4742151988730136890L;

	private MeetingService meetingService;

	private Long meetingId;// 会议ID

	/**
	 * 查询二维码
	 * 
	 * @return
	 */
	public String queryQRCode() {
		User user = this.getUserFromSession();

		Meeting meeting = null;
		try {
			meeting = meetingService.findById(meetingId);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		if (user == null || meeting == null) {
			log.error("查询二维码是会议或者用户信息为null!");
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("姓名：");
		sb.append(user.getName());
		sb.append("\n");
		sb.append("会议：");
		sb.append(meeting.getName());
		sb.append("\n");
		sb.append("会议号：");
		sb.append(meeting.getId());
		sb.append("\n");
		sb.append("手机号：");
		sb.append(user.getMobile());

		BufferedImage image = null;
		try {
			image = QRCodeGen.genQRCode(sb.toString());
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (image != null) {
			try {
				ImageIO.write(image, "JPG", this.getResponse().getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}
