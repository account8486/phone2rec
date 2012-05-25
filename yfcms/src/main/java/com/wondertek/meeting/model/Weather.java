package com.wondertek.meeting.model;

import java.util.Calendar;

public class Weather {
	private String areaCode;                  //地区代码，如合肥：2201
	private String areaName;                  //省份 地区/洲 国家名（国外）
	private String forecastAreaCode;          //"查询的天气预报地区ID"
	private String forecastAreaName;          //查询的天气预报地区名称
	private String updateTime ;               //"最后更新时间 格式：yyyy-MM-dd HH:mm:ss"
	private String weather;                   //"当前天气实况：气温、风向/风力、湿度"
	private String airQuality;                //"第一天 空气质量、紫外线强度" 
	private String lifeIndex;                 //"第一天 天气和生活指数"
	private String comment;                   //备注
	private Calendar refreshDate;
	
	private WeatherForecast date1 = new WeatherForecast();           //第一天预报
	private WeatherForecast date2 = new WeatherForecast();           //第二天预报
	private WeatherForecast date3 = new WeatherForecast();           //第三天预报
	private WeatherForecast date4 = new WeatherForecast();           //第四天预报
	private WeatherForecast date5 = new WeatherForecast();           //第五天预报
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getForecastAreaCode() {
		return forecastAreaCode;
	}
	public void setForecastAreaCode(String forecastAreaCode) {
		this.forecastAreaCode = forecastAreaCode;
	}
	public String getForecastAreaName() {
		return forecastAreaName;
	}
	public void setForecastAreaName(String forecastAreaName) {
		this.forecastAreaName = forecastAreaName;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getAirQuality() {
		return airQuality;
	}
	public void setAirQuality(String airQuality) {
		this.airQuality = airQuality;
	}
	public String getLifeIndex() {
		return lifeIndex;
	}
	public void setLifeIndex(String lifeIndex) {
		this.lifeIndex = lifeIndex;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public WeatherForecast getDate1() {
		return date1;
	}
	public void setDate1(WeatherForecast date1) {
		this.date1 = date1;
	}
	public WeatherForecast getDate2() {
		return date2;
	}
	public void setDate2(WeatherForecast date2) {
		this.date2 = date2;
	}
	public WeatherForecast getDate3() {
		return date3;
	}
	public void setDate3(WeatherForecast date3) {
		this.date3 = date3;
	}
	public WeatherForecast getDate4() {
		return date4;
	}
	public void setDate4(WeatherForecast date4) {
		this.date4 = date4;
	}
	public WeatherForecast getDate5() {
		return date5;
	}
	public void setDate5(WeatherForecast date5) {
		this.date5 = date5;
	}
	public Calendar getRefreshDate() {
		return refreshDate;
	}
	public void setRefreshDate(Calendar refreshDate) {
		this.refreshDate = refreshDate;
	}
}
