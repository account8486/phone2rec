/**
 * 
 */
package com.wondertek.meeting.action.meeting;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.client.view.AgendaDateView;
import com.wondertek.meeting.client.view.AgendaView;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.GroupPlan;
import com.wondertek.meeting.model.GroupPlanDetail;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.model.MeetingAgenda;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.GroupPlanService;
import com.wondertek.meeting.service.MeetingAgendaService;
import com.wondertek.meeting.service.MeetingGroupService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.StringUtil;

/**
 * @author rain
 * 
 */
public class MeetingAgendaAction extends BaseAction {
	private static final long serialVersionUID = -4159022947164249765L;

	@Autowired
	private MeetingAgendaService meetingAgendaService;
	@Autowired
	private MeetingService meetingService;

	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	UserService userService;
	
	private Long meetingId;
	
	private List<MeetingAgenda> agendaList;
	
	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	/**
	 * return agenda list for mobile client
	 * 
	 * @return
	 */
	public String agendaList() {
		final User user = getUserFromSession();
		final String meetingNo = getParameter("meetingNo");
		if (StringUtils.isEmpty(meetingNo) || !StringUtils.isNumeric(meetingNo)) {
			resultMap.put("retcode", 100);
			resultMap.put("retmsg", "非法参数");
			resultMap.put("content", "会议编号=" + meetingNo);
			return SUCCESS;
		}
		
		final List<MeetingAgenda> agenda = meetingAgendaService.queryListByMeetingId(Long.valueOf(meetingNo), user.getId());
		resultMap.put("retcode", 0);
		resultMap.put("retmsg", "议程列表");
		resultMap.put("content", convertToView(agenda));
		return SUCCESS;
	}
	
	
	/**
	 * 到会议议程批量添加的页面
	 * @return
	 */
	public String toBatchAddAgenda(){
		String meetingId=getRequest().getParameter("meetingId");
		
		//批量添加
		getRequest().setAttribute("meetingId",meetingId);
		
		return SUCCESS;
	}
	
	/**
	 * 会议议程批量添加
	 * @return
	 */
	public String batchAdd(){
		
		try {
			final AdminUser user = getAdminUserFromSession();
			final User creator = new User();
			creator.setId(user.getId());
			if(agendaList != null && agendaList.size() > 0)
				log.debug("agendaList.size() = "+agendaList.size());
			for(MeetingAgenda agenda : agendaList){
				log.debug("agenda= {} ",agenda);
				if(agenda!=null){
					agenda.setCreateTime(new Date());
					agenda.setCreator(creator);
					meetingAgendaService.add(agenda);
				}
			}
			resultMap.put("retmsg", "保存成功");
		} catch (Exception e) {
			log.error("批量添加议程失败：",e);
			resultMap.put("retmsg", "保存失败");
		}
		return SUCCESS;
		
	}

	/**
	 * return agenda list for admin and portal
	 * 后台议程 列表Admin
	 * @return
	 */
	public String agendaList_admin() {
		try {

			String from=this.getParameter("from");
			getRequest().setAttribute("meetingId", getParameter("meetingId"));
			final Long meetingId = Long.valueOf(getParameter("meetingId"));
			if (isAdmin() && getRequest().getRequestURI().indexOf("/admin/") != -1) {
				final MeetingAgenda agenda = new MeetingAgenda();
				agenda.setMeetingId(meetingId);
				if(StringUtil.isNotEmpty(from)&&from.contains("guide")){
					pageSize=10000;
				}
				final Pager<MeetingAgenda> pager = meetingAgendaService.queryPagerByMeetingId(agenda, currentPage,
						pageSize);
				getRequest().setAttribute("pager", pager);
			} else {
				final User user = getUserFromSession();
				final List<MeetingAgenda> agendaList = meetingAgendaService.queryListByMeetingId(meetingId, user.getId());
				final List<AgendaDateView> views = convertToView(agendaList);
				getRequest().setAttribute("views", views);
			}
			
			//如果是向导的话
			if(StringUtil.isNotEmpty(from)&&from.contains("guide")){
				return "guide";
			}
			
			return SUCCESS;
		} catch (ServiceException e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	public String agendaList4Touch() {
		try {
			getRequest().setAttribute("meetingId", getParameter("meetingId"));
			final Long meetingId = Long.valueOf(getParameter("meetingId"));
			if (isAdmin() && getRequest().getRequestURI().indexOf("/admin/") != -1) {
				final MeetingAgenda agenda = new MeetingAgenda();
				agenda.setMeetingId(meetingId);
				final Pager<MeetingAgenda> pager = meetingAgendaService.queryPagerByMeetingId(agenda, currentPage,
						pageSize);
				getRequest().setAttribute("pager", pager);
			} else {
				final User user = getUserFromSession();
				final List<MeetingAgenda> agendaList = meetingAgendaService.queryListByMeetingId(meetingId, user.getId());
				final List<AgendaDateView> views = convertToView(agendaList);
				getRequest().setAttribute("views", views);
			}
			return SUCCESS;
		} catch (ServiceException e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public String agendaSave() {
		final AdminUser user = getAdminUserFromSession();
		final String id = getParameter("id");
		final String meetingNo = getParameter("meetingId");
		final String startTime = getParameter("startTime");
		final String endTime = getParameter("endTime");
		final String topic = getParameter("topic");
		final String description = getParameter("description");
		final String date = getParameter("date");
		final String host = StringUtils.defaultIfEmpty(getParameter("host"), StringUtils.EMPTY);
		final String location = StringUtils.defaultIfEmpty(getParameter("location"), StringUtils.EMPTY);
		//添加参会人员
		String attendee=StringUtil.replaceBlank(getParameter("attendee"));
		
		log.info("id:" + id + ",meetingNo:" + meetingNo + ",startTime:"
				+ startTime + ",endTime:" + endTime + ",topic:" + topic
				+ ",description:" + description + ",date:" + date + ",host:"
				+ host + ",location:" + location + ",attendee:" + attendee);
		
		final User creator = new User();
		creator.setId(user.getId());

		try {
			MeetingAgenda agenda;
			if (StringUtils.isEmpty(id)) {
				agenda = new MeetingAgenda();
				agenda.setDescription(description);
				agenda.setMeetingId(Long.valueOf(meetingNo));
				agenda.setTopic(topic);
				agenda.setHost(host);
				agenda.setLocation(location);
				agenda.setDate(date);
				agenda.setStartTime(startTime);
				agenda.setEndTime(endTime);
				agenda.setAttendee(attendee);
				agenda.setCreateTime(new Date());
				agenda.setCreator(creator);
				meetingAgendaService.add(agenda);
			} else {
				agenda = meetingAgendaService.findById(Long.valueOf(id));
				agenda.setTopic(topic);
				agenda.setHost(host);
				agenda.setLocation(location);
				agenda.setDate(date);
				agenda.setStartTime(startTime);
				agenda.setEndTime(endTime);
				agenda.setDescription(description);
				agenda.setModifier(creator);
				agenda.setModifyTime(new Date());
				agenda.setAttendee(attendee);
				meetingAgendaService.modify(agenda);
			}
			resultMap.put("result", true);
		} catch (ServiceException e) {
			e.printStackTrace();
			resultMap.put("result", false);
		}
		return SUCCESS;
	}

	/**
	 * add agenda by admin
	 * 
	 * @return
	 */
	public String agendaAdd() {
		try {
			final String meetingNo = getParameter("meetingId");
			// TODO通过会议ID来获取最新议程的时间ORDER BY
			List<MeetingAgenda> meetingAgendaLst = meetingAgendaService
					.getNewestAgenda(Long.valueOf(meetingNo));
			
			if(meetingAgendaLst!=null&&meetingAgendaLst.size()>0){
				getRequest().setAttribute("newestAgenda", meetingAgendaLst.get(0));
			}
			
			//返回普通JSP信息
			getRequest().setAttribute("meetingId", meetingNo);
			
			//返回JSON数据
			if(meetingAgendaLst!=null&&meetingAgendaLst.size()>0){
				this.resultMap.put("newestAgenda",  meetingAgendaLst.get(0));
			}else{
				this.resultMap.put("newestAgenda", new MeetingAgenda());
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * 编辑会议议程
	 * @return
	 */
	public String agendaEdit() {
		final String id = getParameter("id");
		MeetingAgenda agenda = null;
		try {
			agenda = meetingAgendaService.findById(Long.valueOf(id));
			// TODO 通过IDS来查找对应的人,然后提供编辑
			if (StringUtil.isNotEmpty(agenda.getAttendee())) {
				log.debug(agenda.getAttendee().substring(0,
								agenda.getAttendee().length() - 1));
				List<User> userLst = userService.getMeetingMemberByIds(
						agenda.getMeetingId(),
						agenda.getAttendee().substring(0,
								agenda.getAttendee().length() - 1));
				String attendeeNames = "";
				for (User user : userLst) {
					attendeeNames += user.getName() + "[" + user.getMobile()
							+ "],";
				}
				
				getRequest().setAttribute("attendeeNames", attendeeNames);
				log.debug("attendeeNames:"+attendeeNames);
				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ERROR;
		} catch (ServiceException e) {
			e.printStackTrace();
			return ERROR;
		}
		getRequest().setAttribute("agenda", agenda);
		return SUCCESS;
	}

	public String agendaDelete() {
		final String id = getParameter("id");

		try {
			final MeetingAgenda agenda = new MeetingAgenda();
			agenda.setId(Long.valueOf(id));
			meetingAgendaService.deleteAgendaById(agenda);
			resultMap.put("result", true);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			resultMap.put("result", false);
		} catch (ServiceException e) {
			e.printStackTrace();
			resultMap.put("result", false);
		}
		return SUCCESS;
	}

	public String groupDetail() {
		final User user = getUserFromSession();
		final String agendaId = getParameter("agendaId");
		final GroupPlanDetail group = this.getGroupPlanDetail(Long.valueOf(agendaId));
		final List<JsonUser> members = getGroupMemberInfo(group.getId());
		getRequest().setAttribute("group", group);
		getRequest().setAttribute("members", members);
		getRequest().setAttribute("meetingId", getParameter("meetingId"));
		getRequest().setAttribute("userId", user.getId());
		return SUCCESS;
	}

	public String groupDetail_client() {
		final String groupId = getParameter("groupId");
		List<JsonUser> members = getGroupMemberInfo(Long.valueOf(groupId));
		resultMap.put("retcode", 0);
		resultMap.put("retmsg", "分组详情");
		resultMap.put("members", members);
		return SUCCESS;
	}

	public String wapView() {
		try {
			final String meetingId = getParameter("meetingId");
			final User user = getUserFromSession();
			final String index = getParameter("index");
			int idx = 0;
			if (StringUtils.isNotEmpty(index)) {
				idx = Integer.valueOf(index);
			}
			final List<MeetingAgenda> agenda = meetingAgendaService.queryListByMeetingId(Long.valueOf(meetingId), user.getId());
			if (agenda.size() > 0) {
				final List<AgendaDateView> view = convertToView(agenda);
				getRequest().setAttribute("view", view.get(idx));
				getRequest().setAttribute("preIdx", idx - 1);
				getRequest().setAttribute("nextIdx", idx + 1);
				getRequest().setAttribute("lastIdx", view.size());
				getRequest().setAttribute("meetingId", meetingId);
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("view agenda from wap: ", e.getMessage());
			return ERROR;
		}
	}

	public String agendaImport() {
		final String meetingId = getParameter("meetingId");
		final String action = getParameter("action");
		if (StringUtils.equals("doImport", action)) {
			doImport(Long.valueOf(meetingId));
		}
		getRequest().setAttribute("meetingId", meetingId);
		
		//判断是否是会议向导提交的请求
		String guideStep = getRequest().getParameter("guideStep");
		if("step3".equals(guideStep)) {
			return "guide_step4";
		}
		return SUCCESS;
	}

	private void doImport(final Long meetingId) {
		final List<MeetingAgenda> agendas = new ArrayList<MeetingAgenda>();
		try {
			final WorkbookSettings setting = new WorkbookSettings();
			setting.setLocale(Locale.CHINA);
			final Workbook book = Workbook.getWorkbook(this.upload);
			final Sheet sheet = book.getSheet(0);
			final int rowNum = sheet.getRows();
			final int rowStart = 2;
			for (int i = rowStart; i < rowNum; i++) {
				MeetingAgenda agenda = new MeetingAgenda();
				Cell[] cells = sheet.getRow(i);
				agenda.setMeetingId(meetingId);
				agenda.setTopic(cells[0].getContents());
				agenda.setHost(cells[1].getContents());
				final DateCell dCell = (DateCell) cells[2];
				agenda.setDate(DateUtil.getDateTime("yyyy-MM-dd", dCell.getDate()));
				agenda.setStartTime(formatTime(cells[3].getContents()));
				agenda.setEndTime(formatTime(cells[4].getContents()));
				if (cells.length > 5) {
					agenda.setLocation(cells[5].getContents());
				}
				if (cells.length > 6) {
					agenda.setDescription(cells[6].getContents());
				}
				agendas.add(agenda);
			}
			book.close();
			final String tips = validateImportDate(agendas);
			if ("".equals(tips)) {
				meetingAgendaService.saveList(agendas);
				getRequest().setAttribute("importMsg", "成功导入" + agendas.size() + "个议程.");
			} else {
				getRequest().setAttribute("importMsg", tips);
			}
		} catch (jxl.read.biff.BiffException jxl) {
			getRequest().setAttribute("importMsg", "导入失败, 文件格式不正确。");
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("importMsg", "导入失败, 请稍后重试或联系系统管理员。");
		}
	}
	
	private String formatTime(final String sTime) {
		String result = StringUtils.replace(sTime, "：", ":");
		if (sTime.length() == 4) {
			result = "0" + sTime;
		}
		return result;
	}

	private String validateImportDate(final List<MeetingAgenda> agendas) {
		for (MeetingAgenda agenda : agendas) {
			if (StringUtils.isEmpty(agenda.getTopic()) || StringUtils.length(agenda.getTopic()) > 128) {
				return "标题不能为空, 或长度超过128个字符。";
			}
			if (StringUtils.length(agenda.getHost()) > 30) {
				return "人员不能超过30个字符。"; 
			}
			if (StringUtils.length(agenda.getLocation()) > 128) {
				return "地点不能超过128个字符。"; 
			}
			if (StringUtils.isEmpty(agenda.getDate())) {
				return "日期不能为空。";
			}
			if (StringUtils.isEmpty(agenda.getStartTime()) || StringUtils.isEmpty(agenda.getEndTime())) {
				return "开始和结束时间不能为空。";
			}
			if (StringUtils.length(agenda.getDescription()) > 512) {
				return "议程内容超过512个字符了。";
			}
		}
		return "";
	}

	/**
	 * 把查询出来的会议议程列表封装
	 * @param agendas
	 * @return
	 */
	private List<AgendaDateView> convertToView(final List<MeetingAgenda> agendas) {
		final Map<String, List<AgendaView>> viewMap = new LinkedHashMap<String, List<AgendaView>>();
		for (int i = 0; i < agendas.size(); i++) {
			MeetingAgenda agenda = agendas.get(i);
			final AgendaView view = new AgendaView();
			view.setId(agenda.getId());
			final GroupPlanDetail group = getGroupPlanDetail(agenda.getId());
			if (group == null) {
				view.setHasGroup(false);
				final GroupPlan groupPlan = agenda.getGroupPlan();
				if (groupPlan != null) {
					view.setGroupPlanId(groupPlan.getId());	
				}
			} else {
				view.setHasGroup(true);
				view.setGroupId(group.getId());
				view.setGroupName(group.getGroupName());
				view.setGroupDetail(group.getDetail());
				view.setGroupPlanId(group.getPlanId());
				view.setGroupPlanIsOpen(group.getIsOpen());
			}
			view.setMeetingId(agenda.getMeetingId());
			view.setDate(agenda.getDate());
			view.setHostName(agenda.getHost());
			view.setStartTime(agenda.getStartTime());
			view.setEndTime(agenda.getEndTime());
			view.setDescription(StringUtil.convertTextAreaNewLine(StringUtils.defaultIfEmpty(agenda.getDescription(), "")));
			view.setTopic(agenda.getTopic());
			view.setLocation(agenda.getLocation());
			//转化参与人员
			//TODO
			if (StringUtil.isNotEmpty(agenda.getAttendee())) {
				List<User> userLst = userService.getMeetingMemberByIds(
						agenda.getMeetingId(),
						agenda.getAttendee().substring(0,
								agenda.getAttendee().length() - 1));
				String attendeeNames = "";
				for (User user : userLst) {
					attendeeNames += user.getName() + ",";
				}
				
				//getRequest().setAttribute("attendeeNames", attendeeNames);
				log.debug("attendeeNames:"+attendeeNames);
				view.setAttendeeNames(attendeeNames);
			}
			
			
			
			if (viewMap.containsKey(agenda.getDate())) {
				viewMap.get(agenda.getDate()).add(view);
			} else {
				final List<AgendaView> views = new ArrayList<AgendaView>();
				views.add(view);
				viewMap.put(agenda.getDate(), views);
			}
		}

		final List<AgendaDateView> dateViews = new ArrayList<AgendaDateView>();
		for (Map.Entry<String, List<AgendaView>> entry : viewMap.entrySet()) {
			final AgendaDateView dateView = new AgendaDateView();
			dateView.setDate(DateUtil.convertDateFormat(entry.getKey(), "yyyy-MM-dd"));
			dateView.setAgendaViews(entry.getValue());
			dateViews.add(dateView);
		}
		return dateViews;
	}

	private GroupPlanDetail getGroupPlanDetail(final Long agendaId) {
		final User user = getUserFromSession();
		GroupPlanDetail group = null;
		try {
			group = groupPlanService.getGroupDetailInfo(user.getId(), Constants.GROUP_PLAN_TYPE.AGENDA, agendaId);
		} catch (ServiceException e) {
			log.debug("do not have group plan", e.getMessage());
		}
		return group;
	}

	private List<JsonUser> getGroupMemberInfo(final Long groupId) {
		try {
			return groupPlanService.getMemberByGroupId(groupId);
		} catch (ServiceException e) {
			log.debug("do not have group members", e.getMessage());
		}
		return null;
	}

	/**
	 * @param upload
	 *            the upload to set
	 */
	public void setUpload(File upload) {
		this.upload = upload;
	}

	/**
	 * @param uploadFileName
	 *            the uploadFileName to set
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * @param uploadContentType
	 *            the uploadContentType to set
	 */
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	private Long groupPlanId;// 选择的分组模版ID
	private MeetingAgenda meetingAgenda;
	private GroupPlan oldGroupPlan;
	private GroupPlanService groupPlanService;
	private MeetingGroupService meetingGroupService;

	/** 到选择分组模版的页面 */
	public String choose() {
		try {
			meetingAgenda = meetingAgendaService.findById(meetingAgenda.getId());
			// 根据议程ID查找当前选择的分组模版ID
			oldGroupPlan = groupPlanService.findByRelationId(meetingAgenda.getId(), Constants.GROUP_PLAN_TYPE.AGENDA);
			// 查找出所有的议程相关的分组模版
			List<GroupPlan> groupPlanList = groupPlanService.findPlanListByType(Constants.GROUP_PLAN_TYPE.AGENDA,
					meetingAgenda.getMeetingId());
			getRequest().setAttribute("groupPlanList", groupPlanList);
		} catch (Exception e) {
			log.error("agenda choose groupplan ", e);
		}
		return SUCCESS;
	}

	/** 保存分组的值 */
	public String doChoose() {
		try {

			log.debug("meetingAgenda.getId() = {}", meetingAgenda.getId());
			log.debug("groupPlanId = ", groupPlanId);
			meetingGroupService.updateByRelation(groupPlanId, Constants.GROUP_PLAN_TYPE.AGENDA, meetingAgenda.getId());
			resultMap.put("retmsg", "保存成功");
			resultMap.put("retcode", RetCode.SUCCESS);
		} catch (Exception e) {
			log.error("agenda choose groupplan ", e);
			resultMap.put("retmsg", "保存失败");
			resultMap.put("retcode", RetCode.FAIL);
		}
		return SUCCESS;
	}

	public MeetingAgendaService getMeetingAgendaService() {
		return meetingAgendaService;
	}

	public void setMeetingAgendaService(MeetingAgendaService meetingAgendaService) {
		this.meetingAgendaService = meetingAgendaService;
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public Long getGroupPlanId() {
		return groupPlanId;
	}

	public void setGroupPlanId(Long groupPlanId) {
		this.groupPlanId = groupPlanId;
	}

	public MeetingAgenda getMeetingAgenda() {
		return meetingAgenda;
	}

	public void setMeetingAgenda(MeetingAgenda meetingAgenda) {
		this.meetingAgenda = meetingAgenda;
	}

	public GroupPlan getOldGroupPlan() {
		return oldGroupPlan;
	}

	public void setOldGroupPlan(GroupPlan oldGroupPlan) {
		this.oldGroupPlan = oldGroupPlan;
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


	public Long getMeetingId() {
		return meetingId;
	}


	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}


	public List<MeetingAgenda> getAgendaList() {
		return agendaList;
	}


	public void setAgendaList(List<MeetingAgenda> agendaList) {
		this.agendaList = agendaList;
	}

}