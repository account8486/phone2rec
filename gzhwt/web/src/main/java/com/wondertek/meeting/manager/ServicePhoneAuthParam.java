/**
 * 
 */
package com.wondertek.meeting.manager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author rain
 * 
 */
public class ServicePhoneAuthParam {
	private String servicePhone;
	private String startTime;
	private String endTime;
	private String freeSmsNum;
	private String meetingId;
	private String cityCode;

	/**
	 * @return the servicePhone
	 */
	public String getServicePhone() {
		return servicePhone;
	}

	/**
	 * @param servicePhone
	 *            the servicePhone to set
	 */
	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the freeSmsNum
	 */
	public String getFreeSmsNum() {
		return freeSmsNum;
	}

	/**
	 * @param freeSmsNum
	 *            the freeSmsNum to set
	 */
	public void setFreeSmsNum(String freeSmsNum) {
		this.freeSmsNum = freeSmsNum;
	}

	/**
	 * @return the meetingId
	 */
	public String getMeetingId() {
		return meetingId;
	}

	/**
	 * @param meetingId
	 *            the meetingId to set
	 */
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode
	 *            the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	protected String md5Sign() {
		final StringBuffer md5Str = new StringBuffer();
		md5Str.append(getMeetingId()).append(getServicePhone()).append("AHHF").append(getStartTime())
				.append(getEndTime()).append(getFreeSmsNum());
		return StringUtils.upperCase(DigestUtils.md5Hex(md5Str.toString()));
	}

	protected String toXml() {
		final StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"gb2312\"?><Request>")
				.append("<param name='mid'>" + getMeetingId() + "</param>")
				.append("<param name='tid'>" + getServicePhone() + "</param>")
				.append("<param name='citycode'>AHHF</param>")
				.append("<param name='stime'>" + getStartTime() + "</param>")
				.append("<param name='etime'>" + getEndTime() + "</param>")
				.append("<param name='freesms'>" + getFreeSmsNum() + "</param>")
				.append("<param name='sign'>" + md5Sign() + "</param>").append("</Request>");
		return xml.toString();
	}
}
