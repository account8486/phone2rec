package com.wondertek.meeting.action.journey;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.model.VehicleDriver;
import com.wondertek.meeting.service.VehicleDriverService;

/**
 * 会议司机信息
 * @author John Tang
 *
 */
public class VehicleDriverAction extends BaseAction {

	private static final long serialVersionUID = 527033506779L;
	
	private VehicleDriverService vehicleDriverService;
	private VehicleDriver vehicleDriver;
	
	private Long meetingId;

	public String list(){
		try {
			getRequest().setAttribute("pager",vehicleDriverService.queryPagerByMeetingId(meetingId, currentPage, pageSize));
		} catch (Exception e) {
			log.error("list vehicleDriver error ",e);
		}
		
		return SUCCESS;
	}
	
	
	public String add(){
		try {
			vehicleDriverService.add(vehicleDriver);
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			resultMap.put("retdata", vehicleDriver);
			return SUCCESS;
		} catch (Exception e) {
			log.error("添加司机信息失败,",e);
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return SUCCESS;
		}
	}
	
	public String modify(){
		
		try {
			vehicleDriverService.modify(vehicleDriver);
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			resultMap.put("retdata", vehicleDriver);
			return SUCCESS;
		} catch (Exception e) {
			log.error("添加司机信息失败,",e);
			
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return SUCCESS;
		}
	}
	
	public String delete(){
		try {
			vehicleDriverService.delete(vehicleDriver);

			resultMap.put("retmsg", "成功删除司机信息");
			resultMap.put("retcode", RetCode.SUCCESS);
		} catch (Exception e) {
			log.error("删除司机信息失败,",e);
			resultMap.put("retmsg", "删除司机信息失败");
			resultMap.put("retcode", RetCode.FAIL);
		}
		return SUCCESS;
	}
	
	public String getVehicleDriverById(){
		try {
			vehicleDriver =  vehicleDriverService.findById(vehicleDriver.getId());
		} catch (Exception e) {
			log.error("查询司机信息失败,",e);
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String gotoAdd(){
		return SUCCESS;
	}


	public VehicleDriverService getVehicleDriverService() {
		return vehicleDriverService;
	}


	public void setVehicleDriverService(VehicleDriverService vehicleDriverService) {
		this.vehicleDriverService = vehicleDriverService;
	}


	public VehicleDriver getVehicleDriver() {
		return vehicleDriver;
	}


	public void setVehicleDriver(VehicleDriver vehicleDriver) {
		this.vehicleDriver = vehicleDriver;
	}


	public Long getMeetingId() {
		return meetingId;
	}


	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	
}
