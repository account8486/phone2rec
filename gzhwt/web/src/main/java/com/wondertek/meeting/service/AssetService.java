package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.Asset;

public interface AssetService extends BaseService<Asset, Long> {

	/**
	 * 获取列表
	 * @param currentPage
	 * @param pageSize
	 * @param meetingId
	 * @param asset_no
	 * @param asset_name
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public Pager<Asset> getAssetList(int currentPage, int pageSize,
			String meetingId, String asset_no, String asset_name)
			throws HibernateDaoSupportException;

}
