package com.wondertek.meeting.common;

import java.util.Random;

import com.wondertek.meeting.model.AdminRole;
import com.wondertek.meeting.model.AdminUser;

public class SysUtil {

	/**
	 * 判断用户是否是超级管理员
	 * 
	 * @param roleId
	 * @return
	 */
	public static boolean isSuperAdmin(Long roleId) {
		if (roleId == 1) {
			return true;
		}

		return false;
	}





	
	/**
	 * 生成签到码
	 * 
	 * @return
	 */
	public static String genSignCode() {
		return String.valueOf(1000 + new Random().nextInt(8999));
	}
}
