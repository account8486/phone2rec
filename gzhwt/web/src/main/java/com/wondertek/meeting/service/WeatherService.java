package com.wondertek.meeting.service;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.dao.MeetingDao;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.Weather;
import com.wondertek.meeting.webservice.weather.WeatherWebService;
import com.wondertek.meeting.webservice.weather.WeatherWebServiceLocator;

public class WeatherService {
	public Logger log = LoggerFactory.getLogger(this.getClass());
	private MeetingService meetingService;
	private MeetingDao meetingDao;
	private static WeatherWebService weatherWS = new WeatherWebServiceLocator();
	public void getWeatherForecast(){
		log.debug("----------------------------------getWeatherForecast--------------------------------");
		List<Meeting> meetingList = null;
		try {
			meetingList = meetingService.findAll();
		} catch (com.wondertek.meeting.exception.ServiceException e) {
			log.error("获取会议列表失败:{}",e);
			return ;
		}
		for(Meeting m:meetingList){
			log.debug("meeting ==:{}",m);
			Weather weather = new Weather();
			String[] s = null;
			try {			
				s = weatherWS.getWeatherWebServiceSoap().getWeatherbyCityName("合肥");	
				for (String o : s) {
		            System.out.println(o);
		        }
			} catch (RemoteException e1) {
				log.error("调用天气web service错误：{}",e1);
				continue;
			} catch (ServiceException e1) {
				log.error("调用天气web service错误：{}",e1);
				continue;
			}
			
			weather.setAreaCode(m.getCity());
			weather.setAreaName(s[0]);
			weather.setForecastAreaCode(s[2]);
			weather.setForecastAreaName(s[1]);
			weather.setUpdateTime(s[3]);
			weather.setWeather(s[4]);
			weather.setAirQuality(s[5]);
			weather.setLifeIndex(s[6]);
			//第一天预报
			weather.getDate1().setWeather(s[7]);
			weather.getDate1().setTemperature(s[8]);
			weather.getDate1().setWindPowerAndDirection(s[9]);
			weather.getDate1().setIcon1(s[10]);
			weather.getDate1().setIcon2(s[11]);
			//第二天预报
			weather.getDate2().setWeather(s[12]);
			weather.getDate2().setTemperature(s[13]);
			weather.getDate2().setWindPowerAndDirection(s[14]);
			weather.getDate2().setIcon1(s[15]);
			weather.getDate2().setIcon2(s[16]);
			//第三天预报
			weather.getDate3().setWeather(s[17]);
			weather.getDate3().setTemperature(s[18]);
			weather.getDate3().setWindPowerAndDirection(s[19]);
			weather.getDate3().setIcon1(s[20]);
			weather.getDate3().setIcon2(s[21]);
			//第四天预报
			weather.getDate4().setWeather(s[22]);
			weather.getDate4().setTemperature(s[23]);
			weather.getDate4().setWindPowerAndDirection(s[24]);
			weather.getDate4().setIcon1(s[25]);
			weather.getDate4().setIcon2(s[26]);
			//第五天预报
			weather.getDate5().setWeather(s[27]);
			weather.getDate5().setTemperature(s[28]);
			weather.getDate5().setWindPowerAndDirection(s[29]);
			weather.getDate5().setIcon1(s[30]);
			weather.getDate5().setIcon2(s[31]);
			//放到
			Constants.weatherMap.remove(m.getCity());
			Constants.weatherMap.put(m.getCity(), weather);
		}	
		return ;
	}
	

	public Weather getWeatherForecast(String meetingId,String realpath){
		String cityName = null; 
		Weather weather = null;

		try{
			cityName = meetingDao.getCityName(meetingId);
		}catch(Exception e){
			return null;
		}
		
		//如果转到第二天，则
		
		if(Constants.weatherMap.get(cityName)==null){
			log.debug("第一次获取天气");
			weather = callWS(cityName, realpath);

			Constants.weatherMap.remove(cityName);
			Constants.weatherMap.put(cityName, weather);			
		}else{

			Calendar c = Calendar.getInstance();
			log.debug("当天：年月日：{}-{}-{}",new Object[]{c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE)});
			
			Calendar refreshDate = Constants.weatherMap.get(cityName).getRefreshDate();
			log.debug("当天：年月日：{}-{}-{}",new Object[]{c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE)});
			if(c.get(Calendar.YEAR)==refreshDate.get(Calendar.YEAR)
					&&c.get(Calendar.MONTH)==refreshDate.get(Calendar.MONTH)
					&&c.get(Calendar.DATE)==refreshDate.get(Calendar.DATE)){
				log.debug("天气已经获取过，直接返回");
				weather =  Constants.weatherMap.get(cityName);
			}else{
				log.debug("日期已经改变，重新获取天气");
				weather = callWS(cityName, realpath);
				Constants.weatherMap.remove(cityName);
				Constants.weatherMap.put(cityName, weather);				
			}
		}
				
		return weather;
	}
	
	private Weather callWS(String areaName,String realpath){

		String[] s = null;
		Weather weather = new Weather();
		try {			
			s = weatherWS.getWeatherWebServiceSoap().getWeatherbyCityName(areaName);	
			
		} catch (RemoteException e1) {
			log.error("调用天气web service错误：{}",e1);
			return weather;
		} catch (ServiceException e1) {
			log.error("调用天气web service错误：{}",e1);
			return weather;
		}
		//weather.setAreaCode(m.getCity());
		weather.setAreaName(s[0]);
		weather.setForecastAreaCode(s[2]);
		weather.setForecastAreaName(s[1]);
		weather.setUpdateTime(s[3]);
		weather.setWeather(s[10]);
		weather.setLifeIndex(s[11]);
		weather.setRefreshDate(Calendar.getInstance());
		
		//第一天预报
		weather.getDate1().setWeather(s[6]);
		weather.getDate1().setTemperature(s[5]);
		weather.getDate1().setWindPowerAndDirection(s[7]);
		weather.getDate1().setIcon1(s[8]);
		weather.getDate1().setIcon2(s[9]);
		//第二天预报
		weather.getDate2().setWeather(s[13]);
		weather.getDate2().setTemperature(s[12]);
		weather.getDate2().setWindPowerAndDirection(s[14]);
		weather.getDate2().setIcon1(s[15]);
		weather.getDate2().setIcon2(s[16]);
		//第三天预报
		weather.getDate3().setWeather(s[18]);
		weather.getDate3().setTemperature(s[17]);
		weather.getDate3().setWindPowerAndDirection(s[19]);
		weather.getDate3().setIcon1(s[20]);
		weather.getDate3().setIcon2(s[21]);
		log.debug("weather ===={}",weather);
		
		String date1Icon1Url = realpath + gifSuffix2PngSuffix(weather.getDate1().getIcon1());
		String date1Icon2Url = realpath + gifSuffix2PngSuffix(weather.getDate1().getIcon2());
		weather.getDate1().setIcon1(date1Icon1Url);
		weather.getDate1().setIcon2(date1Icon2Url);
		String date2Icon1Url = realpath + gifSuffix2PngSuffix(weather.getDate2().getIcon1());
		String date2Icon2Url = realpath + gifSuffix2PngSuffix(weather.getDate2().getIcon2());
		weather.getDate2().setIcon1(date2Icon1Url);
		weather.getDate2().setIcon2(date2Icon2Url);
		String date3Icon1Url = realpath + gifSuffix2PngSuffix(weather.getDate3().getIcon1());
		String date3Icon2Url = realpath + gifSuffix2PngSuffix(weather.getDate3().getIcon2());
		weather.getDate3().setIcon1(date3Icon1Url);
		weather.getDate3().setIcon2(date3Icon2Url);
		
		return weather;
	}
	public Weather getTodayWeather(String meetingId,String realPath){
		
		return getWeatherForecast(meetingId,realPath);
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}


	public MeetingDao getMeetingDao() {
		return meetingDao;
	}


	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}



	private String gifSuffix2PngSuffix(String s){
		return s.replace(".gif", ".png");
	}
	
}
