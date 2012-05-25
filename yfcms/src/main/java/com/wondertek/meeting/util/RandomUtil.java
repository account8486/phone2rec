package com.wondertek.meeting.util;

import java.util.Random;

public class RandomUtil {

	/** * 生成一个随机数 六位需求,如果长度不够 就伪生成 * * @return String */
	public static String getRandomCode() {
		Random random = new Random();
		// 默认一个值 不至于程序崩溃
		String reslt = "1258";
		int ran = random.nextInt(9999);
		// 临时变量tempRan储存
		String tempRan = String.valueOf(ran);
		if (tempRan.length() == 4) {
			reslt = tempRan;
		}
		return reslt;
	}
}
