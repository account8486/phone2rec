package com.wondertek.meeting.action.meeting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingSeat;
import com.wondertek.meeting.model.MeetingSeatImage;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.ManagerSeatService;

/**
 * 座位安排
 * 
 * @author 周健
 * 
 */
public class MeetingSeatAction extends BaseAction {

	private static final long serialVersionUID = 2351474762817383464L;

	@Autowired
	private ManagerSeatService managerSeatService;

	private int meetingId;

	private int userId;

	public int getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSeatJsp() {
		return BaseAction.SUCCESS;
	}
	
	/**
	 * 查询出当前用户当前会议的座位截图
	 */
	public void getSeatImage() {
		try {
			if(this.meetingId == 0) {
				if(this.getSession().getAttribute("_portal_meeting_") != null) {
					this.meetingId = ((Meeting)this.getSession().getAttribute("_portal_meeting_")).getId().intValue();
				}
				
				if(this.getSession().getAttribute(SessionKeeper.CURRENT_MEETING_ID) != null) {
					this.meetingId = Integer.parseInt(String.valueOf(this.getSession().getAttribute("CURRENT_MEETING_ID")));
				}
			}
			if(this.userId == 0) {
				this.userId = ((User)this.getSession().getAttribute(SessionKeeper.SESSION_USER)).getId().intValue();
			}
			
			log.debug("meetingId: " + meetingId + ", userId:" + userId);
			Map<String, Object> properties = new HashMap<String, Object>(1);
			properties.put("meetingId", meetingId);
			properties.put("userId", userId);
			
			MeetingSeatImage img = (MeetingSeatImage) this.managerSeatService.getUniqueBeanResult(
					"from MeetingSeatImage s where s.meetingId = :meetingId", properties);

			if (img != null) {
				// 获取该用户的座位的x,y坐标
//				MeetingSeat seat = (MeetingSeat) this.managerSeatService.getUniqueBeanResult(
//						"from MeetingSeat s where s.meetingId = :meetingId and s.userId = :userId", properties);
				List<MeetingSeat> ms = this.managerSeatService.getObjects("from MeetingSeat s where s.meetingId = :meetingId and s.userId = :userId", properties);
				if(ms.size() == 0) {
					ImageIO.write(drawErrorImage(400, 500, "对不起，还没有给您安排座位"), "JPEG", ServletActionContext.getResponse().getOutputStream());
					return;
				}
				MeetingSeat seat = ms.get(0);
				if (seat != null) {
					// 画出该用户在会场安排图上的位置
					BufferedImage bi = this.drawUserSeatPosition(img.getImage().getBytes(1, (int) img.getImage().length()), seat.getX(), seat.getY(), img.getOffsetX(), img.getOffsetY());
					
					ImageIO.write(bi, "JPEG", ServletActionContext.getResponse().getOutputStream());
				} else {
					ImageIO.write(drawErrorImage(400, 500, "对不起，还没有给您安排座位"), "JPEG", ServletActionContext.getResponse().getOutputStream());
				}

			} else {
				ImageIO.write(drawErrorImage(400, 500, "对不起，该会议还没有安排座位"), "JPEG", ServletActionContext.getResponse().getOutputStream());
			}

		} catch (Exception e) {
			log.error("getSeatImage error,", e);
			try {
				ImageIO.write(drawErrorImage(400, 500, "对不起，服务器忙，请稍后再试"), "JPEG", ServletActionContext.getResponse().getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private RenderedImage drawErrorImage(int width, int height, String info) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = (Graphics2D) bi.getGraphics();
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(3));
		g.setFont(new Font("微软雅黑", Font.BOLD, 16));
		g.drawString(info, width / 2 - 16 * info.length() / 2, height / 2 + 8);
		g.dispose();
		
		return bi;
	}

	private BufferedImage drawUserSeatPosition(byte[] img, String x, String y, String _offsetX, String _offsetY) {
		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mciis = null;

		// 图上坐标的偏移量
		int offsetX = -(int) Double.parseDouble(_offsetX);
		int offsetY = -(int) Double.parseDouble(_offsetY);

		try {
			// 通过数据库中获取的byte[]字节数组来获得Image对象
			bais = new ByteArrayInputStream(img);
			mciis = new MemoryCacheImageInputStream(bais);

			// BufferedImage bi = ImageIO.read(fiis);
			BufferedImage bi = ImageIO.read(mciis);

			// 获取到g之后就可以对图像进行绘图操作了
			Graphics2D g = (Graphics2D) bi.getGraphics();
			g.setStroke(new BasicStroke(2));
//			g.setColor(new Color(0xf12341));
			g.setColor(new Color(0x0000FF));
			g.drawRoundRect((int)Double.parseDouble(x) + offsetX, (int)Double.parseDouble(y) + offsetY, 62, 47, 60, 60);
//			g.drawRect((int)Double.parseDouble(x) + offsetX, (int)Double.parseDouble(y) + offsetY, 62, 47);
			g.dispose();

			return bi;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (bais != null) {
					bais.close();
				}

				if (mciis != null) {
					mciis.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
