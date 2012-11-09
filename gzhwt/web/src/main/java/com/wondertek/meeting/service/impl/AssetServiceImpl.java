package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.AssetDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.Asset;
import com.wondertek.meeting.service.AssetService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 资产管理
 * 
 * @author zqwu
 */
public class AssetServiceImpl extends BaseServiceImpl<Asset, Long>
		implements AssetService {

	private AssetDao assetDao;

	public AssetDao getAssetDao() {
		return assetDao;
	}

	public void setAssetDao(AssetDao assetDao) {
		this.assetDao = assetDao;
		this.basicDao = assetDao;
	}

	/**
	 * 获取资产列表
	 * 
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public Pager<Asset> getAssetList(int currentPage, int pageSize,
			String meetingId, String asset_no, String asset_name)
			throws HibernateDaoSupportException {
		Pager<Asset> pager = null;
		StringBuffer hql = new StringBuffer();
		hql.append("  from Asset where meetingId=" + meetingId);
		
		//条件查询
		if(StringUtil.isNotEmpty(asset_no)){
			hql.append(" and  asset_no like '%" + asset_no + "%'");
		}
		if(StringUtil.isNotEmpty(asset_name)){
			hql.append(" and asset_name like '%" + asset_name + "%'");
		}
		pager = assetDao.findPager(hql.toString(), currentPage, pageSize,
				null);
		
		return pager;
	}

}
