package com.wondertek.meeting.action.asset;

import org.apache.commons.lang.StringUtils;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Asset;
import com.wondertek.meeting.service.AssetService;

public class AssetAction extends BaseAction {

	private static final long serialVersionUID = -1969207588644182985L;

	private AssetService assetService;
	
	private String meetingId;
	
	private Asset asset;
	
	public Asset getAsset() {
		return asset;
	}


	public void setAsset(Asset asset) {
		this.asset = asset;
	}


	public String getMeetingId() {
		return meetingId;
	}


	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}


	public AssetService getAssetService() {
		return assetService;
	}


	public void setAssetService(AssetService assetService) {
		this.assetService = assetService;
	}

	/**
	 * 获取列表
	 * @return
	 * @throws HibernateDaoSupportException 
	 */
	public String getAssetLst() throws HibernateDaoSupportException {
		String meetingId = this.getParameter("meetingId");
		String asset_no = this.getParameter("asset_no");
		String asset_name = this.getParameter("asset_name");

		Pager<Asset> pager = assetService.getAssetList(
				currentPage, pageSize, meetingId, asset_no, asset_name);

		getRequest().setAttribute("pager", pager);
		getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute("asset_no", asset_no);
		getRequest().setAttribute("asset_name", asset_name);

		return SUCCESS;
	}
	
	/**
	 * 编辑资产信息
	 * @return
	 */
	public String editAsset(){
		String meetingId= this.getParameter("meetingId");		
		getRequest().setAttribute("meetingId", meetingId);
		
		String id= this.getParameter("id");
		
		if (StringUtils.isNotEmpty(id)){
			try {
				asset = assetService.findById(Long.valueOf(id));
			} catch (NumberFormatException e) {
				log.info(e.getMessage());
			} catch (ServiceException e) {
				log.info(e.getMessage());
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 删除资产信息
	 * @return
	 */
	public String deleteAsset(){
		try {
			String id = this.getParameter("id");
			Asset delAsset = assetService.findById(Long.valueOf(id));
			assetService.delete(delAsset);
			this.resultMap.put("retCode", "0");
			this.resultMap.put("retMsg", "删除成功!");
			
		} catch (Exception e) {
			log.info(e.getMessage());
			this.resultMap.put("retCode", "1");
			this.resultMap.put("retMsg", e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 保存资产信息
	 * 
	 * @return
	 */
	public String save() {
		
		try {
			assetService.saveOrUpdate(asset);
			this.resultMap.put("retCode", "0");
			this.resultMap.put("meetingId", asset.getMeetingId());
			this.resultMap.put("retMsg", "保存成功!!!!");
		} catch (ServiceException e) {
			this.resultMap.put("retCode", "0");
			this.resultMap.put("retMsg", e.getMessage());
		}
		
		return SUCCESS;
	}

}
