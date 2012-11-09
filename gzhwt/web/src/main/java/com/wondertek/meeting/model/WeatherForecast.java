package com.wondertek.meeting.model;

public class WeatherForecast {

	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}


	public String getIcon1() {
		return icon1;
	}
	public void setIcon1(String icon1) {
		this.icon1 = icon1;
	}

	public String getIcon2() {
		return icon2;
	}
	public void setIcon2(String icon2) {
		this.icon2 = icon2;
	}

	public String getWindPowerAndDirection() {
		return windPowerAndDirection;
	}
	public void setWindPowerAndDirection(String windPowerAndDirection) {
		this.windPowerAndDirection = windPowerAndDirection;
	}

	private String weather;
	private String temperature;
	private String windPowerAndDirection;
	private String icon1;
	private String icon2;
}
