package com.wondertek.meeting.util;

import java.util.Comparator;

import com.wondertek.meeting.model.User;

public class UserComparator implements Comparator<User> {
	
	public  int compare(User user1, User user2){
		
		//排序码大的放在后面
		Integer sortCode1=user1.getMeetingMember().getSortCode();
		Integer sortCode2=user2.getMeetingMember().getSortCode();
		int rlst = 0;//向下沉
		if(sortCode1!=null&&sortCode2!=null){
			rlst=sortCode1.compareTo(sortCode2);
		}
		
		return rlst;
	}
	
	
	public static void main(String[] args){
		User user1=new User();
		user1.getMeetingMember().setSortCode(1);
		User user2=new User();
		user2.getMeetingMember().setSortCode(2);
		UserComparator userComparator=new UserComparator();
		System.out.println(userComparator.compare(user1,user2));
		
		
	}


}
