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
	 * 判断用户是否是超级管理员
	 * 
	 * @param roleId
	 * @return
	 */
	public static boolean isSuperAdmin(AdminUser user) {
		if (user == null) {
			return false;
		}

		AdminRole role = user.getRole();

		if (role != null) {
			if (role.getId() == 1) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断用户是否是会议管理员
	 * 
	 * @param roleId
	 * @return
	 */
	public static boolean isMeetingAdmin(Long roleId) {
		if (roleId == 3 || roleId == 4) {
			return true;
		}

		return false;
	}

	/**
	 * 判断用户是否是会议管理员
	 * 
	 * @param roleId
	 * @return
	 */
	public static boolean isMeetingAdmin(AdminUser user) {
		if (user == null) {
			return false;
		}

		AdminRole role = user.getRole();

		if (role != null) {
			if (role.getId() == 3 || role.getId() == 4) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 判断用户是否是集团管理员
	 * 
	 * @param roleId
	 * @return
	 */
	public static boolean isGroupAdmin(Long roleId) {
		if (roleId == 3) {
			return true;
		}

		return false;
	}
	
	/**
	 * 判断用户是否是集团管理员
	 * 
	 * @param roleId
	 * @return
	 */
	public static boolean isGroupAdmin(AdminUser user) {
		if (user == null) {
			return false;
		}

		AdminRole role = user.getRole();

		if (role != null && role.getId() == 3) {
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
