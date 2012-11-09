package com.wondertek.meeting.action.meeting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.client.view.WeatherView;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingAgenda;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.Weather;
import com.wondertek.meeting.service.MeetingAgendaService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.WeatherService;
import com.wondertek.meeting.util.MapUtils;
import com.wondertek.meeting.util.StringUtil;

public class MeetingExpandAction extends BaseAction {

	private static final long serialVersionUID = 7229031852775717652L;
	private MeetingService meetingService;
	private WeatherService weatherService ;
	MeetingAgendaService meetingAgendaService;

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	
	public MeetingAgendaService getMeetingAgendaService() {
		return meetingAgendaService;
	}


	public void setMeetingAgendaService(MeetingAgendaService meetingAgendaService) {
		this.meetingAgendaService = meetingAgendaService;
	}


	/**
	 * 位置应用
	 * 获取meeting的信息
	 * 
	 * @return
	 */
	public String getMeetingInfo() {
		String meetingId = getRequest().getParameter("meetingId");

		try {
			Meeting meeting = meetingService.getMeetingByPk(Long
					.parseLong(meetingId));

			// 会议不为空
			if (meeting != null) {

				// 为了提高效率 进行优化
				SimpleDateFormat fat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				Map<String, Object> meetingMap = new HashMap<String, Object>();
				meetingMap.put("id", meeting.getId());
				meetingMap.put("name", meeting.getName());
				//meetingMap.put("topic", meeting.getTopic());
				meetingMap.put("location", meeting.getLocation());
	            if (meeting.getLocationXY() == null || "".equals(meeting.getLocationXY())) {
	            	final String city = ResourceBundle.getBundle("mapabc").getString(meeting.getCity());
	            	final String[] datas = StringUtils.split(city, ",");
	            	meeting.setLocationXY(MapUtils.decode(datas[2]) + "," + MapUtils.decode(datas[3]));
	            }
				meetingMap.put("locationXY", meeting.getLocationXY());
				
				resultMap.put("retcode", 0);
				resultMap.put("retmsg", "查询完成");
				resultMap.put("meeting", meetingMap);

				getRequest().setAttribute("retcode", 0);
				getRequest().setAttribute("retmsg", "查询完成");
				getRequest().setAttribute("meeting", meetingMap);

			} else {// 会议不存在
				resultMap.put("retcode", 1);
				resultMap.put("retmsg", "你所选择的会议不存在或者已结束,请联系系统管理员!");

				getRequest().setAttribute("retcode", 1);
				getRequest()
						.setAttribute("retmsg", "你所选择的会议不存在或者已结束,请联系系统管理员!");

			}
		} catch (Exception e) {
			resultMap.put("retcode", 1);
			resultMap.put("retmsg", "查询信息异常");

			getRequest().setAttribute("retcode", 1);
			getRequest().setAttribute("retmsg", "查询信息异常!");

			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	/**
	 * 会议介绍首页调整
	 * @return
	 */
	public String getMeetingInfoForClientIndex() {
		String meetingId = getRequest().getParameter("meetingId");

		try {
			Meeting meeting = meetingService.getMeetingByPk(Long
					.parseLong(meetingId));

			// 会议不为空
			if (meeting != null) {

				// 为了提高效率 进行优化
				SimpleDateFormat fat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				Map<String, Object> meetingMap = new HashMap<String, Object>();
				meetingMap.put("id", meeting.getId());
				meetingMap.put("name", meeting.getName());
				meetingMap.put("topic", meeting.getTopic());
				meetingMap.put("location", meeting.getLocation());
				meetingMap.put("endTime", fat.format(meeting.getEndTime()));
				meetingMap.put("startTime", fat.format(meeting.getStartTime()));
			
				getRequest().setAttribute("retcode", 0);
				getRequest().setAttribute("retmsg", "查询完成");
				getRequest().setAttribute("meeting", meetingMap);

			} else {// 会议不存在
				getRequest().setAttribute("retcode", 1);
				getRequest()
						.setAttribute("retmsg", "你所选择的会议不存在或者已结束,请联系系统管理员!");

			}
		} catch (Exception e) {
			
			getRequest().setAttribute("retcode", 1);
			getRequest().setAttribute("retmsg", "查询信息异常!");
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	
	
	/**
	 * 会议介绍首页调整
	 * @return
	 */
	public String getMeetingInfoForTouch(){
		String meetingId = getRequest().getParameter("meetingId");

		try {
			Meeting meeting = meetingService.getMeetingByPk(Long
					.parseLong(meetingId));

			// 会议不为空
			if (meeting != null) {

				// 为了提高效率 进行优化
				SimpleDateFormat fat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				Map<String, Object> meetingMap = new HashMap<String, Object>();
				meetingMap.put("id", meeting.getId());
				meetingMap.put("name", meeting.getName());
				meetingMap.put("topic", meeting.getTopic());
				meetingMap.put("location", meeting.getLocation());
				meetingMap.put("endTime", fat.format(meeting.getEndTime()));
				meetingMap.put("startTime", fat.format(meeting.getStartTime()));
			
				getRequest().setAttribute("retcode", 0);
				getRequest().setAttribute("retmsg", "查询完成");
				
				this.resultMap.put("meetingMap", meetingMap);

			} else {// 会议不存在
				getRequest().setAttribute("retcode", 1);
				getRequest()
						.setAttribute("retmsg", "你所选择的会议不存在或者已结束,请联系系统管理员!");

			}
		} catch (Exception e) {
			
			getRequest().setAttribute("retcode", 1);
			getRequest().setAttribute("retmsg", "查询信息异常!");
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String getAttendMeetingList() {
		String forward = SUCCESS;

		try {

			//移除上一次选择的会议session信息
			getSession().removeAttribute("_portal_meeting_");
			
			//移除session中保存的会议主题皮肤信息
			this.getSession().removeAttribute(SessionKeeper.SESSION_KEY_PAGE_THEME_NAME); 
			this.getSession().removeAttribute(SessionKeeper.SESSION_KEY_MEETING_LOGO_IMAGE);
			this.getSession().removeAttribute(SessionKeeper.SESSION_KEY_MEETING_TOP_IMAGE);
			
			// 取得当前用户的ID
			// String
			// userId=(String)getRequest().getSession().getAttribute(SessionKeeper.UserId);
			User user = (User) getRequest().getSession().getAttribute(
					SessionKeeper.SESSION_USER);

			String userId = String.valueOf(user.getId());
			// 获取信息
			@SuppressWarnings("unchecked")
			List<Meeting> lstResult = meetingService.getMyAttendMeetingList(userId);
			for (Meeting meeting : lstResult) {
				List<MeetingAgenda> agendaList = meetingAgendaService
						.queryListByMeetingId(meeting.getId(),
								Long.valueOf(userId));
				List<MeetingAgenda> newAgendaLst=new ArrayList<MeetingAgenda>();
				for (MeetingAgenda agenda : agendaList) {
					if (StringUtil.isNotEmpty(agenda.getAttendee())
							&&agenda.getAttendee().indexOf(userId+"," ) >= 0) {
						newAgendaLst.add(agenda);
					}
				}
				meeting.setMeetingAgendas(newAgendaLst);
			}
			
			// 获取其参加的会议的列表

			// 获取其会议信息 展示到前台页面

			getRequest().setAttribute("meetingList", lstResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return forward;
	}
	
	/**
	 * 获取天气信息
	 * @return
	 */
	public String getWeatherInfo() {
		String meetingId = getRequest().getParameter("meetingId");

		try {
			String basePath = getBasePath() + "/images/weather/";
			Weather w= weatherService.getWeatherForecast(meetingId,basePath);
			//处理图片路径
	
			getRequest().setAttribute("weather", w);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * 获取当日天气情况，目前返回给Android客户端
	 * @return
	 */
	public String getTodayWeather() {
		String meetingId = getRequest().getParameter("meetingId");

		try {
			String basePath = getBasePath() + "/images/weather/client/";
			Weather w= weatherService.getTodayWeather(meetingId,basePath);
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "查询完成");
			resultMap.put("weather", convertToView(w,meetingId));
		} catch (Exception e) {
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "查询完成");
			e.printStackTrace();
		}



		return SUCCESS;
	}

	public WeatherService getWeatherService() {
		return weatherService;
	}

	public void setWeatherService(WeatherService weatherService) {
		this.weatherService = weatherService;
	}
	

	private WeatherView convertToView(Weather weather,String meetingId) {
		String pageUrl = getBasePath()+ "/client/getWeatherInfo.action?meetingId="+meetingId;
		log.debug("pageUrl is " + pageUrl);
		WeatherView wv = new WeatherView();
		wv.setTemperature(weather.getDate1().getTemperature());
		wv.setWeather(weather.getDate1().getWeather());
		wv.setPageUrl(pageUrl);
		wv.setIcon1(weather.getDate1().getIcon1());
		wv.setIcon2(weather.getDate1().getIcon2());
		return wv;
	}
	

}
