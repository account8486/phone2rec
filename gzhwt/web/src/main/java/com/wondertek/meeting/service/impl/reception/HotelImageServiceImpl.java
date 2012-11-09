package com.wondertek.meeting.service.impl.reception;

import com.wondertek.meeting.dao.reception.HotelImageDao;
import com.wondertek.meeting.model.reception.HotelImage;
import com.wondertek.meeting.service.impl.BaseServiceImpl;
import com.wondertek.meeting.service.reception.HotelImageService;

/**
 * 酒店管理
 * 
 * @author 金祝华
 */
public class HotelImageServiceImpl extends BaseServiceImpl<HotelImage, Long> implements HotelImageService {

	private HotelImageDao hotelImageDao;

	public HotelImageDao getHotelImageDao() {
		return hotelImageDao;
	}

	public void setHotelImageDao(HotelImageDao hotelImageDao) {
		super.setBaseDao(hotelImageDao);
		this.hotelImageDao = hotelImageDao;
	}
}
