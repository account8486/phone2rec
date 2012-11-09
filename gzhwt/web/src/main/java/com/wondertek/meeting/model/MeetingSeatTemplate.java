package com.wondertek.meeting.model;

/**
 * 会场座位安排模板物品类
 * 
 * @author 周健
 */
public class MeetingSeatTemplate extends BaseObject {
	private static final long serialVersionUID = 7724435196067129565L;
	
	private Integer id;
	private String name;
	private int seatNo;
	private String x;
	private String y;
	private int type;
	private int seatType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSeatType() {
		return seatType;
	}

	public void setSeatType(int seatType) {
		this.seatType = seatType;
	}

}
