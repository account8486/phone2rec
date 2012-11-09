package com.wondertek.meeting.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.wondertek.meeting.model.custom.MeetingType;
import com.wondertek.meeting.model.custom.PageTheme;
import com.wondertek.meeting.model.reception.DiningRoom;
import com.wondertek.meeting.model.reception.Hotel;
import com.wondertek.meeting.model.reception.HotelRoom;

/**
 * 会议信息
 *
 * @author tangjun
 */
public class Meeting extends BaseObject {

    private static final long serialVersionUID = 81038L;
    /**
     * 会议ID
     */
    private Long id;

    /**
     * 会议名称
     */
    private String name;

    /**
     * 会议类型
     */
    private MeetingType meetingType;

    /**
     * 会议主题
     */
    private String topic;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 会议地点
     */
    private String location;


    private String locationXY;

    /**
     * 备注
     */
    private String comments;

    /**
     * 会议状态
     */
    private Integer state;
    
    //以下增加属于用于贵州移动3.20上线提出的新需求，by 张国敬
    private String host; //会议主办方
    private String organizer;  //会议承办方
    private String logoImage; //类型Logo图片
    private PageTheme pageTheme; //使用页面主题

	private Set<User> memberSet;

    private Set<Task> taskSet;

    private AdminUser creator;

    private AdminUser modifier;
    
    private Date accessStartTime;
    
    private Date accessEndTime;
    
    private String serviceNumber;
    
    private Long freeSmsNum;
    
    private String notice;
    
    private String province;
    
    private String city;
    
    private String district;

    private String joinTime;
    
    private Set<Hotel> hotels;// 酒店列表
    private Set<HotelRoom> hotelRooms;// 客房列表
    private Set<DiningRoom> diningRooms;// 餐厅列表
    
    private Set<AdminUser> meetingAdmins;//会务人员
    private List<MeetingAgenda> meetingAgendas;
    
	public List<MeetingAgenda> getMeetingAgendas() {
		return meetingAgendas;
	}

	public void setMeetingAgendas(List<MeetingAgenda> meetingAgendas) {
		this.meetingAgendas = meetingAgendas;
	}

	public Set<AdminUser> getMeetingAdmins() {
		return meetingAdmins;
	}

	public void setMeetingAdmins(Set<AdminUser> meetingAdmins) {
		this.meetingAdmins = meetingAdmins;
	}
	
    public Set<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(Set<Hotel> hotels) {
		this.hotels = hotels;
	}

	public Set<HotelRoom> getHotelRooms() {
		return hotelRooms;
	}

	public void setHotelRooms(Set<HotelRoom> hotelRooms) {
		this.hotelRooms = hotelRooms;
	}

	public Set<DiningRoom> getDiningRooms() {
		return diningRooms;
	}

	public void setDiningRooms(Set<DiningRoom> diningRooms) {
		this.diningRooms = diningRooms;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Date getAccessStartTime() {
		return accessStartTime;
	}

	public void setAccessStartTime(Date accessStartTime) {
		this.accessStartTime = accessStartTime;
	}

	public Date getAccessEndTime() {
		return accessEndTime;
	}

	public void setAccessEndTime(Date accessEndTime) {
		this.accessEndTime = accessEndTime;
	}

	public AdminUser getCreator() {
        return creator;
    }

    public void setCreator(AdminUser creator) {
        this.creator = creator;
    }

    public AdminUser getModifier() {
        return modifier;
    }

    public void setModifier(AdminUser modifier) {
        this.modifier = modifier;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationXY() {
        return locationXY;
    }

    public void setLocationXY(String locationXY) {
        this.locationXY = locationXY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Set<User> getMemberSet() {
        return memberSet;
    }

    public void setMemberSet(Set<User> memberSet) {
        this.memberSet = memberSet;
    }

    public Set<Task> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(Set<Task> taskSet) {
        this.taskSet = taskSet;
    }

	public Long getFreeSmsNum() {
		return freeSmsNum;
	}

	public void setFreeSmsNum(Long freeSmsNum) {
		this.freeSmsNum = freeSmsNum;
	}
	
    public String getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public MeetingType getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public PageTheme getPageTheme() {
		return pageTheme;
	}

	public void setPageTheme(PageTheme pageTheme) {
		this.pageTheme = pageTheme;
	}

	



}
