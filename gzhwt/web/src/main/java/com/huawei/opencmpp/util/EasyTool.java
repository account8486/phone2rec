package com.huawei.opencmpp.util;

import java.util.Calendar;

public class EasyTool {
	public static int getCMPPTimestamp() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);

		return month * 100 * 100 * 100 * 100 + day * 100 * 100 * 100 + hour
				* 100 * 100 + minute * 100 + second;
	}
}
