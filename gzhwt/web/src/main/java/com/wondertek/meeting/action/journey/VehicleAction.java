package com.wondertek.meeting.action.journey;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.model.Vehicle;
import com.wondertek.meeting.service.VehicleService;

/**
 * 会议车辆信息
 * @author John Tang
 *
 */
public class VehicleAction extends BaseAction {

	private static final long serialVersionUID = 527033506779L;
	
	private VehicleService vehicleService;
	private Vehicle vehicle;
	
	private Long meetingId;

	public String list(){
		try {
			getRequest().setAttribute("pager",vehicleService.queryPagerByMeetingId(meetingId, currentPage, pageSize));
		} catch (Exception e) {
			log.error("list vehicle error ",e);
		}
		
		return SUCCESS;
	}
	
	
	public String add(){
		try {
			vehicleService.add(vehicle);
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			resultMap.put("retdata", vehicle);
			return SUCCESS;
		} catch (Exception e) {
			log.error("添加车辆信息失败,",e);
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return SUCCESS;
		}
	}
	
	public String modify(){
		
		try {
			vehicleService.modify(vehicle);
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			resultMap.put("retdata", vehicle);
			return SUCCESS;
		} catch (Exception e) {
			log.error("添加车辆信息失败,",e);
			
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return SUCCESS;
		}
	}
	
	public String delete(){
		try {
			vehicleService.delete(vehicle);

			resultMap.put("retmsg", "成功删除车辆信息");
			resultMap.put("retcode", RetCode.SUCCESS);
		} catch (Exception e) {
			log.error("删除车辆信息失败,",e);
			resultMap.put("retmsg", "删除车辆信息失败");
			resultMap.put("retcode", RetCode.FAIL);
		}
		return SUCCESS;
	}
	
	public String getVehicleById(){
		try {
			vehicle =  vehicleService.findById(vehicle.getId());
		} catch (Exception e) {
			log.error("查询车辆信息失败,",e);
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String gotoAdd(){
		return SUCCESS;
	}


	public VehicleService getVehicleService() {
		return vehicleService;
	}


	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}


	public Vehicle getVehicle() {
		return vehicle;
	}


	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}


	public Long getMeetingId() {
		return meetingId;
	}


	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	
}
