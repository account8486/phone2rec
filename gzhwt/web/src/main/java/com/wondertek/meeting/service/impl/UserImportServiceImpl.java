package com.wondertek.meeting.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.wondertek.meeting.common.SignInHelper;
import com.wondertek.meeting.dao.MeetingMemberDao;
import com.wondertek.meeting.dao.UserDao;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.UserImportService;
import com.wondertek.meeting.util.Encrypt;
import com.wondertek.meeting.util.RandomUtil;
import com.wondertek.meeting.util.StringUtil;

public class UserImportServiceImpl extends BaseServiceImpl implements
		UserImportService {

	UserDao userDao;
	MeetingMemberDao meetingMemberDao;

	/**
	 * 进行用户的导入操作
	 * 
	 * @param importUserList
	 */
	public HashMap saveImportUser(List<User> importUserList, Long meetingId,
			Integer memberLevel) {
		HashMap tipsMap = new HashMap();
		if (importUserList == null || importUserList.size() <= 0) {
			return null;
		}

		int importNum = 0;
		int addNum = 0;
		int updateNum = 0;

		// 当用户导入时,模板中存在重复的记录时,那么导入第一条，后面的不再导入

		String userMobiles = "";
		String repeatedUserMobiles = "";

		// 判断是否需要导入
		for (int i = 0; i < importUserList.size(); i++) {
			// 开始进行循环
			User importUser = importUserList.get(i);

			MeetingMember updateMeetingMember = importUser.getMeetingMember();
			if (importUser.getMobile() == null
					|| userMobiles.indexOf(importUser.getMobile()) >= 0) {
				// 为空或者已经包含在里面了 就不进行导入了
				if (importUser.getMobile() != null
						&& repeatedUserMobiles.indexOf(importUser.getMobile()) < 0) {
					repeatedUserMobiles = repeatedUserMobiles
							+ importUser.getMobile() + ",";
				}
				continue;
			} else {
				userMobiles = userMobiles + "," + importUser.getMobile();
				importNum++;
			}

			Integer tempSortCode = null;
			String addInContacts = null;
			String roomNumber = null;

			if (importUser.getMeetingMember().getSortCode() != null) {
				tempSortCode = importUser.getMeetingMember().getSortCode();
			}

			if (importUser.getMeetingMember().getAddInContacts() != null) {
				addInContacts = importUser.getMeetingMember()
						.getAddInContacts();
			}
			// 房间号
			if (importUser.getMeetingMember().getRoomNumber() != null) {
				roomNumber = importUser.getMeetingMember().getRoomNumber();
			}
			// 默认是否显示
			Integer tempMobileIsDisppaly = null;
			if (importUser.getMeetingMember().getMobileIsDisplay() != null) {
				tempMobileIsDisppaly = importUser.getMeetingMember()
						.getMobileIsDisplay();
			} else {
				// 默认是可以显示的
				tempMobileIsDisppaly = 1;
			}

			// 查询当前的是否存在
			if (userDao.selectByMobile(importUser.getMobile()) != null) {
				// 存在就不保存了 直接拿已经查出来的实体,用最后的数据来更新
				User transientUser = userDao.selectByMobile(importUser
						.getMobile());
				transientUser.setModifyTime(new Date());

				transientUser.setName(StringUtil.replaceBlank(importUser
						.getName()));
				transientUser.setGender(importUser.getGender());
				transientUser.setBirthday(importUser.getBirthday());

				// 如果密码存在 就不更新密码了
				if (transientUser.getPassword() == null
						|| "".equals(transientUser.getPassword())) {
					transientUser.setPassword(Encrypt.encrypt(
							RandomUtil.getRandomCode(), null));
				}

				// 如果传过来密码的话 那么就搞进来 修改密码
				if (importUser.getPassword() != null
						&& !"".equals(importUser.getPassword())) {
					transientUser.setPassword(Encrypt.encrypt(
							StringUtil.replaceBlank(importUser.getPassword()),
							null));
				}
				transientUser.setState(1);
				// 更新
				userDao.saveOrUpdateUser(transientUser);
				importUser = transientUser;

				updateNum++;

			} else {
				// 新增的条数
				importUser.setCreateTime(new Date());
				importUser.setPassword(Encrypt.encrypt(
						RandomUtil.getRandomCode(), null));
				// 设置状态
				importUser.setState(1);
				// 不存在,保存
				userDao.saveOrUpdateEntity(importUser);
				addNum++;
			}

			// 生成签到码
			try {
				SignInHelper.getInstance().genSignCode(meetingId,
						importUser.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 最后一步　保存用户与会议的关系
			MeetingMember meetingMember = new MeetingMember();
			meetingMember.setMailbox(StringUtil
					.replaceBlank(updateMeetingMember.getMailbox()));
			meetingMember.setJob(StringUtil.replaceBlank(updateMeetingMember
					.getJob()));
			meetingMember.setBookJob(StringUtil
					.replaceBlank(updateMeetingMember.getBookJob()));
			meetingMember.setCity(StringUtil.replaceBlank(updateMeetingMember
					.getCity()));
			meetingMember.setAddress(StringUtil
					.replaceBlank(updateMeetingMember.getAddress()));
			meetingMember.setUserId(importUser.getId());
			meetingMember.setMeetingId(meetingId);// 赋值会议号
			meetingMember.setMemberLevel(memberLevel);
			meetingMember.setMemberLevel(memberLevel);
			meetingMember.setSortCode(tempSortCode);
			meetingMember.setMobileIsDisplay(tempMobileIsDisppaly);
			meetingMember.setRoomNumber(roomNumber);
			meetingMember.setModifyTime(new Date());
			meetingMember.setVip(updateMeetingMember.getVip());

			// 单位
			meetingMember.setDepartment(updateMeetingMember.getDepartment());

			// 为空就默认显示
			if (updateMeetingMember.getRoomNumberIsDisplay() == null) {
				meetingMember.setRoomNumberIsDisplay(1);
			} else {
				meetingMember.setRoomNumberIsDisplay(updateMeetingMember
						.getRoomNumberIsDisplay());
			}

			// 是否显示职位,为空默认不显示
			if (updateMeetingMember.getJobIsDisplay() == null) {
				meetingMember.setJobIsDisplay(0);
			} else {
				meetingMember.setJobIsDisplay(updateMeetingMember
						.getJobIsDisplay());
			}

			// 默认为加入
			if (StringUtil.isNotEmpty(addInContacts)) {
				meetingMember.setAddInContacts(addInContacts);
			} else {
				meetingMember.setAddInContacts("Y");
			}
			
			//上传权限
			meetingMember.setUploadAuthority(updateMeetingMember.getUploadAuthority());
			meetingMember.setDelegation(updateMeetingMember.getDelegation());
			meetingMember.setInPrivateMessage(updateMeetingMember.isInPrivateMessage());
			
			meetingMemberDao.saveOrUpdateEntity(meetingMember);
		}

		tipsMap.put("importNum", importNum);
		tipsMap.put("addNum", addNum);// 新增条数
		tipsMap.put("updateNum", updateNum);// 更新条数
		tipsMap.put("repeatedUserMobiles", repeatedUserMobiles);

		return tipsMap;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setMeetingMemberDao(MeetingMemberDao meetingMemberDao) {
		this.meetingMemberDao = meetingMemberDao;
	}

}
