package com.wondertek.meeting.action.journey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.manager.SmsSender;
import com.wondertek.meeting.model.Journey;
import com.wondertek.meeting.model.JourneyMember;
import com.wondertek.meeting.model.MeetingSms;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.Vehicle;
import com.wondertek.meeting.model.VehicleDriver;
import com.wondertek.meeting.service.GroupPlanService;
import com.wondertek.meeting.service.JourneyMemberService;
import com.wondertek.meeting.service.JourneyService;
import com.wondertek.meeting.service.MeetingGroupService;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.MeetingSmsService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.service.VehicleDriverService;
import com.wondertek.meeting.service.VehicleService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.MessageSendUtil;
import com.wondertek.meeting.util.StringUtil;
import com.wondertek.meeting.vo.JourneyView;

/**
 * 会议行程信息
 * @author John Tang
 *
 */
public class JourneyAction extends BaseAction {

	private static final long serialVersionUID = 527033506779L;
	
	private JourneyService journeyService;
	private VehicleService vehicleService;
	private VehicleDriverService vehicleDriverService;
	private GroupPlanService groupPlanService;
	private MeetingGroupService meetingGroupService;
	private Journey journey;
	MeetingMemberService meetingMemberService;
	UserService userService;
	JourneyMemberService journeyMemberService;
	MeetingSmsService meetingSmsService;
	public MeetingSmsService getMeetingSmsService() {
		return meetingSmsService;
	}

	public void setMeetingSmsService(MeetingSmsService meetingSmsService) {
		this.meetingSmsService = meetingSmsService;
	}

	public JourneyMemberService getJourneyMemberService() {
		return journeyMemberService;
	}

	public void setJourneyMemberService(JourneyMemberService journeyMemberService) {
		this.journeyMemberService = journeyMemberService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MeetingMemberService getMeetingMemberService() {
		return meetingMemberService;
	}

	public void setMeetingMemberService(MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}


	private Long meetingId;
	
	private String startTime;
	private String endTime;
	
	private List<Vehicle> vehicleList;
	private List<VehicleDriver> driverList;
	
	
	/**
	 * 获取行程列表,供WEB,WAP页面使用
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public String  getJourneyList() throws NumberFormatException, ServiceException {
		String meetingId = this.getParameter("meetingId");
		
		this.setMeetingId(Long.valueOf(meetingId));
		String cPage = getRequest().getParameter("currentPage");
		String from=getRequest().getParameter("from");
		if (cPage != null && !"".equals(cPage)) {
			currentPage = Integer.parseInt(cPage);
		}
		log.info("获取行程-参数列表为:meetingId:" + meetingId + ",cPage:" + cPage);
		Pager<Journey> pager = journeyService.queryPagerByMeetingId(
				Long.valueOf(meetingId), currentPage, pageSize);
		//行程安排
		Pager<JourneyView> pagerJourneyView=changeJourneyToView(pager);
		//TODO
		this.setAttribute("pager", pagerJourneyView);
		this.setAttribute("meetingId", meetingId);

		return SUCCESS;
	}
	
	
	/**
	 * 转化
	 * @param pager
	 * @return
	 * @throws ServiceException
	 */
	private Pager<JourneyView> changeJourneyToView(Pager<Journey> pager) throws ServiceException{
		Pager<JourneyView> pagerJourneyView=new Pager<JourneyView>(currentPage,pageSize);
		
		if (pager.getPageRecords() != null && pager.getPageRecords().size() > 0) {
			List<Journey> journeyList = pager.getPageRecords();
			for (Journey journey : journeyList) {
				// 获取行程人员
				String journeyMemberStr = "";
				List<JourneyMember> journeyMembersLst = journeyMemberService
						.getJourneyMemberByJourney(journey.getId());
				for (JourneyMember member : journeyMembersLst) {
					User user = userService.findById(member.getUserId());
					if (user != null) {
						journeyMemberStr = journeyMemberStr + user.getName()
								+ ",";
					}
				}
				// 对字符串处理下
				if (journeyMemberStr.length() > 0) {
					journeyMemberStr = journeyMemberStr.substring(0,
							journeyMemberStr.length() - 1);
				}

				journey.setJourneyMembers(journeyMemberStr);
				
				VehicleDriver driver=vehicleDriverService.findById(journey.getDriverId());
				Vehicle vehicle=vehicleService.findById(journey.getVehicleId());
				JourneyView journeyView = new JourneyView();
				journeyView.setName(journey.getName());
				journeyView.setDeparture(journey.getDeparture());
				journeyView.setDestination(journey.getDestination());
				if (driver != null) {
					journeyView.setDriverMobile(driver.getMobile());
					journeyView.setDriverName(driver.getName());
				}
				if (vehicle != null) {
					journeyView.setVehicleName(vehicle.getName());
					journeyView.setVehicleNumber(vehicle.getNumber());
					journeyView.setVehicleType(vehicle.getType());
				}
				
				journeyView.setStartTime(journey.getStartTime());
				journeyView.setEndTime(journey.getEndTime());
				journeyView.setJourneyMembers(journey.getJourneyMembers());
				journeyView.setJourneyId(journey.getId());
				
				pagerJourneyView.getPageRecords().add(journeyView);

			}
		}
		
		//把分页信息都转过来
		pagerJourneyView.setPageSize(pager.getPageSize());
		pagerJourneyView.setTotal(pager.getTotal());
		pagerJourneyView.setPageCount(pager.getPageCount());
		pagerJourneyView.setCurrentPage(currentPage);
		
		
		return pagerJourneyView;
		
	}

	public String list(){
		try {
			setVehicleInfo();
			getRequest().setAttribute("pager",journeyService.queryPagerByMeetingId(meetingId, currentPage, pageSize));
		} catch (Exception e) {
			log.error("list journey error ",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 新增行程
	 * @return
	 */
	public String add(){
		try {
			String recieverIds=this.getParameter("recieverIds");
			log.debug("recieverIds:"+recieverIds);
			
			setVehicleInfo();
			journey.setStartTime(DateUtil.str2Date(startTime));
			journey.setEndTime(DateUtil.str2Date(endTime));
			journeyService.add(journey);
			
			//此处进行行程人员的添加
			if(StringUtil.isNotEmpty(recieverIds)){
				String[] recieverIdsArr=recieverIds.split(",");
				for(int i=0;i<recieverIdsArr.length;i++){
					String userId=recieverIdsArr[i];
				//	User user=userService.findById(Long.valueOf(userId));
					JourneyMember journeyMember=new JourneyMember();
					journeyMember.setJourneyId(journey.getId());
					journeyMember.setMeetingId(journey.getMeetingId());
					journeyMember.setNumber(Integer.valueOf(i+1));
					journeyMember.setUserId(Long.valueOf(userId));
					journeyMemberService.saveOrUpdate(journeyMember);
					
					//meetingMemberService.selectById(Long.valueOf(userId), meetingId);
				}
			}
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			resultMap.put("retdata", journey);
			return SUCCESS;
		} catch (Exception e) {
			log.error("添加行程信息失败,",e);
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return SUCCESS;
		}
	}
	
	public String modify(){
		
		try {
			String recieverIds=this.getParameter("recieverIds");
			log.debug("recieverIds:"+recieverIds);
			
			meetingId = journey.getMeetingId();
			setVehicleInfo();
			journey.setStartTime(DateUtil.str2Date(startTime));
			journey.setEndTime(DateUtil.str2Date(endTime));
			journeyService.modify(journey);
			
			//此处进行行程人员的添加
			if(StringUtil.isNotEmpty(recieverIds)){
				journeyMemberService.deleteJourneyMemberByJourneyId(journey.getId());
				String[] recieverIdsArr=recieverIds.split(",");
				for(int i=0;i<recieverIdsArr.length;i++){
					String userId=recieverIdsArr[i];
				//	User user=userService.findById(Long.valueOf(userId));
					//判定是否存在
					
					
					JourneyMember journeyMember=journeyMemberService.selectJourneyMember(journey.getId(), Long.valueOf(userId));
					if(journeyMember!=null){
						//已存在,更新下
						
					}else{
						journeyMember=new JourneyMember();
						journeyMember.setJourneyId(journey.getId());
						journeyMember.setMeetingId(journey.getMeetingId());
						journeyMember.setNumber(Integer.valueOf(i+1));
						journeyMember.setUserId(Long.valueOf(userId));
						journeyMemberService.saveOrUpdate(journeyMember);
					}
					//meetingMemberService.selectById(Long.valueOf(userId), meetingId);
				}
			}
			
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			resultMap.put("retdata", journey);
			return SUCCESS;
		} catch (Exception e) {
			log.error("添加行程信息失败,",e);
			
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return SUCCESS;
		}
	}
	
	public String delete(){
		try {
			journeyService.delete(journey);

			resultMap.put("retmsg", "成功删除行程信息");
			resultMap.put("retcode", RetCode.SUCCESS);
		} catch (Exception e) {
			log.error("删除行程信息失败,",e);
			resultMap.put("retmsg", "删除行程信息失败");
			resultMap.put("retcode", RetCode.FAIL);
		}
		return SUCCESS;
	}
	
	public String getJourneyById(){
		try {
			journey =  journeyService.findById(journey.getId());
			//通过journey的id查询出对应的乘客信息
			List<JourneyMember> journeyMemberLst = journeyMemberService
					.getJourneyMemberByJourney(journey.getId());
			
			String recieverIds="";
			String recieverMobiles="";
			if(journeyMemberLst!=null&&journeyMemberLst.size()>0){
				for(JourneyMember journeyMember:journeyMemberLst){
					recieverIds+=journeyMember.getUserId()+",";
					User user=userService.findById(journeyMember.getUserId());
					recieverMobiles+=user.getName()+"["+user.getMobile()+"],";
				}
			}
			
			meetingId = journey.getMeetingId();
			setVehicleInfo();
			
			this.getRequest().setAttribute("journeyMemberLst",journeyMemberLst);
			this.getRequest().setAttribute("recieverIds",recieverIds);
			this.getRequest().setAttribute("recieverMobiles",recieverMobiles);
		} catch (Exception e) {
			log.error("查询行程信息失败,",e);
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String gotoAdd(){
		setVehicleInfo();
		return SUCCESS;
	}


	/**
	 * 通过会议号获取车辆信息及司机信息
	 */
	private void setVehicleInfo()  {
		try {
			vehicleList = vehicleService.listByMeetingId(meetingId);
			driverList = vehicleDriverService.listByMeetingId(meetingId);
		} catch (Exception e) {
			log.error("setVehicleInfo error ",e);
		}
	}

	/**
	 * 进入发送短信页面
	 * @return
	 */
	public String toJourneySmsTip(){
		String forward=SUCCESS;
		try{
			String meetingId=this.getParameter("meetingId");
			//TODO
			//通过meetingId信息来进行查询
			Pager<Journey> pager = journeyService.queryPagerByMeetingId(
					Long.valueOf(meetingId), 1, 1000);
			Pager<JourneyView> pagerView=this.changeJourneyToView(pager);
			this.setAttribute("meetingId", meetingId);
			this.setAttribute("journeyList", pagerView.getPageRecords());
			this.setAttribute("sendTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return forward;
	}
	
	/**
	 * 短信发送
	 * @return
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public String addMessageTip() throws NumberFormatException,
			ServiceException {

		String forward = SUCCESS;
		String[] journeyIds = this.getRequest().getParameterValues("journeyId");
		String meetingId = this.getParameter("meetingId");
		String messageContent = this.getParameter("messageContent");
		String isTimingSend = getRequest().getParameter("isTimingSend");// 是否需要定时发送
		String sendTime = getRequest().getParameter("sendTime");// all/some

		Date dtSendTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dtSendTime = sdf.parse(sendTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		if (StringUtil.isEmpty(messageContent)) {
			this.resultMap.put("msg", "请输入短信内容！");
			return forward;
		}

		if (journeyIds == null) {
			this.resultMap.put("msg", "请选择短信接收人！");
			return forward;
		}

		StringBuffer receivers = new StringBuffer();

		for (int i = 0; i < journeyIds.length; i++) {
			String journeyId = journeyIds[i];
			Journey journey = journeyService.findById(Long.valueOf(journeyId));

			// 通过journey查处对应的司机信息
			VehicleDriver driver = vehicleDriverService.findById(journey
					.getDriverId());
			if (driver == null) {
				driver = new VehicleDriver();
			}
			Vehicle vehicle = vehicleService.findById(journey.getVehicleId());

			if (driver != null) {
				receivers.append(driver.getName() + ",");
			}

			List<JourneyMember> journeyMemberLst = journeyMemberService
					.getJourneyMemberByJourney(journey.getId());

			// 通过journey的id查询出对应的乘客信息
			String memberNames = "";

			if (journeyMemberLst != null && journeyMemberLst.size() > 0) {
				for (JourneyMember journeyMember : journeyMemberLst) {
					User user = userService.findById(journeyMember.getUserId());
					memberNames += user.getName() + ",";
				}

				memberNames = memberNames
						.substring(0, memberNames.length() - 1);
			}

			messageContent = MessageSendUtil.converTemplateToContent(
					messageContent, journey, driver, vehicle, memberNames);
			log.info("journey act send:driver mobile," + driver.getMobile()
					+ ",messageContent:" + messageContent);

			MeetingSms meetingSms = new MeetingSms();
			
			// 会议ID
			meetingSms.setMeetingId(Long.valueOf(meetingId));
			meetingSms.setCreateTime(new Date());
			meetingSms.setMessages(messageContent);
			meetingSms.setMobile(driver.getMobile());
			meetingSms.setRecieverName(driver.getName());
			meetingSms.setState(0);
			log.debug("Send Message Content:" + meetingSms.getMessages());
			// 定时短信
			if ("Y".equals(isTimingSend)) {
				meetingSms.setDelay(true);
				meetingSms.setSendTime(dtSendTime);
			} else {
				meetingSms.setDelay(false);
			}
			
			// 最后保存
			try {
				meetingSmsService.add(meetingSms);
				this.resultMap.put("msg", "发送成功！");
			} catch (ServiceException e) {
				e.printStackTrace();
				this.resultMap.put("msg", e.getMessage());
			}
		}
		
		return forward;
	}

	public JourneyService getJourneyService() {
		return journeyService;
	}


	public void setJourneyService(JourneyService journeyService) {
		this.journeyService = journeyService;
	}


	public Journey getJourney() {
		return journey;
	}


	public void setJourney(Journey journey) {
		this.journey = journey;
	}


	public Long getMeetingId() {
		return meetingId;
	}


	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public GroupPlanService getGroupPlanService() {
		return groupPlanService;
	}


	public void setGroupPlanService(GroupPlanService groupPlanService) {
		this.groupPlanService = groupPlanService;
	}


	public MeetingGroupService getMeetingGroupService() {
		return meetingGroupService;
	}


	public void setMeetingGroupService(MeetingGroupService meetingGroupService) {
		this.meetingGroupService = meetingGroupService;
	}


	public VehicleService getVehicleService() {
		return vehicleService;
	}


	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}


	public VehicleDriverService getVehicleDriverService() {
		return vehicleDriverService;
	}


	public void setVehicleDriverService(VehicleDriverService vehicleDriverService) {
		this.vehicleDriverService = vehicleDriverService;
	}


	public List<Vehicle> getVehicleList() {
		return vehicleList;
	}


	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}


	public List<VehicleDriver> getDriverList() {
		return driverList;
	}


	public void setDriverList(List<VehicleDriver> driverList) {
		this.driverList = driverList;
	}
	
}
