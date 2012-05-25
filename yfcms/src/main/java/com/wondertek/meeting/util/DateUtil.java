package com.wondertek.meeting.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 */
public class DateUtil {

	static Logger log = LoggerFactory.getLogger(DateUtil.class);
	public static final String TIME_PATTERN = "HH:mm:ss";
	public final static String YYYY_MM_DD = "yyyy-MM-dd";
	private final static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
	public static SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	public static SimpleDateFormat yyyyMMddHHmmssStr = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat PLAYBILL_TIME_PATTERN = new SimpleDateFormat("yyyyMMdd HH:mm");
	public static String LAST_SECOND = "lastsecond";
	public static String FIRST_SECOND = "firstsecond";
	public static String PRECISION_MIN_LAST_SECOND = "PRECISION_MIN_LAST_SECOND";
	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(TIME_PATTERN, theTime);
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static String getFullDateTime(Date aDate) {
		return DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINESE).format(aDate);
	}

	public static java.sql.Date convertDateToSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static java.sql.Timestamp convertDateToTimestamp(Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	public static String getNowTime(Date date) {
		if (date == null) {
			return "";
		}
		return timeFormat.format(date);
	}

	public static String getDateTime(String sdate) {
		try {
			java.sql.Timestamp date = stringToTimestamp(sdate);
			return dateFormat.format(date);
		} catch (Exception e) {
			return sdate;
		}
	}

	/**
	 * 将14位数字类型yyyyMMddHHmmss时间转换为日期类型时间
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String stringDate = format.format(date);

		return stringDate;
	}

	public static java.sql.Timestamp stringToTimestamp(String timestampStr) {
		if (timestampStr == null || timestampStr.length() < 1)
			return null;
		return java.sql.Timestamp.valueOf(timestampStr);
	}

	/**
	 * 根据日期计算出所在周的日期，并返回大小为7的数组
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getWholeWeekByDate(Date date) {
		String[] ss = new String[7];
		Calendar calendar = Calendar.getInstance();
		for (int i = 0, j = 2; i < 6 && j < 8; i++, j++) {
			calendar.setTime(date);
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.set(Calendar.DAY_OF_WEEK, j);
			ss[i] = getFormatDate(calendar.getTime());
		}
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
		ss[6] = getFormatDate(calendar.getTime());
		return ss;
	}

	/**
	 * 返回格式 yyyyMMdd的日期格式
	 * 
	 * @param d
	 * @return
	 */
	public static String getFormatDate(Date d) {
		if (d == null)
			return null;
		return yyyyMMdd.format(d);
	}

	
	public static String formatLong2DateString(Long time) {
		if (time == null)
			return "";
		return yyyyMMddHHmmssStr.format(time);
	}

	/**
	 * 返回pattern规定的日期格式
	 * 
	 * @param d
	 * @param pattern
	 * @return
	 */
	public static String getFormatDate(Date d, String pattern) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}

	/**
	 * 返回yyyy的日期格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return yyyy.format(Calendar.getInstance().getTime());
	}

	public static Date getDateByString(String pattern) throws ParseException {
		return yyyyMMdd.parse(pattern);
	}

	public static Date getPlayBillTimeByPattern(String date) throws ParseException {
		return PLAYBILL_TIME_PATTERN.parse(date);
	}

	/**
	 * 返回格式 HH:MM
	 * 
	 * @param d
	 * @return
	 */
	public static String getDateTime(Date d) {
		return HHmm.format(d);
	}

	public static Date parseDate(String strDate, String pattern) {
		if (strDate == null || pattern == null) {
			return null;
		}

		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = sdf.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date parseDate(Date srcdate, String flag) {
		if (DateUtil.FIRST_SECOND.equals(flag)) {
			String dateStr = getFormatDate(srcdate) + "000000";
			return parseDate(dateStr, "yyyyMMddHHmmss");
		} else if (DateUtil.LAST_SECOND.equals(flag)) {
			String dateStr = getFormatDate(srcdate) + "235959";
			return parseDate(dateStr, "yyyyMMddHHmmss");
		} else if (DateUtil.PRECISION_MIN_LAST_SECOND.equals(flag)) {
			log.debug("before fomat dateStr=========={}"+srcdate);
			String dateStr = getFormatDate(srcdate,"yyyyMMddHHmm") + "59";
			log.debug("dateStr=========={}"+dateStr);
			return parseDate(dateStr, "yyyyMMddHHmmss");
		} else
			return srcdate;
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String convertDateFormat(final String dateStr,String formatStr) {
		
		try {
			if(dateStr == null || dateStr.length() == 0) return "";
			return DateUtil.getFullDateTime(DateUtil.convertStringToDate(formatStr, dateStr));
		} catch (ParseException e) {
			log.debug("Parse Date String Exception:", e.getCause());
			return dateStr;
		}
	}
	
	public static SimpleDateFormat JOURNEY_TIME_PATTERN = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static Date str2Date(String time){
		if(time == null || time.length() < 16)
			return null;
		try {
			return JOURNEY_TIME_PATTERN.parse(time);
		} catch (Exception e) {
			return null;
		}
	}
}
