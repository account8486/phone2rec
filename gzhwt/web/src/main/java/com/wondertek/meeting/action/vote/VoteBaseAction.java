/** 
*  
*/
package com.wondertek.meeting.action.vote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.VoteBaseInfo;
import com.wondertek.meeting.model.VoteItem;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.VoteBaseInfoService;
import com.wondertek.meeting.service.VoteHistoryService;
import com.wondertek.meeting.service.VoteItemService;

/** 
 * @ClassName: VoteBaseAction 
 * @Description: 处理投票操作
 * @author zouxiaoming
 * @date Jan 9, 2012 2:19:38 PM 
 *  
 */
@SuppressWarnings("serial")
public class VoteBaseAction extends BaseAction implements ModelDriven<VoteBaseInfo> {
	private Log log = LogFactory.getLog(VoteBaseAction.class);
	private VoteBaseInfo voteBase=new VoteBaseInfo();
	private VoteBaseInfoService  voteService;
	private MeetingService meetingService;
	private VoteItemService voteItemService;
	private VoteHistoryService voteHistoryService;
	private Long voteId;
	private Long meetingId;
	private List<VoteBaseInfo> list;
	private String[] checks;  // 所有投票选择的投票项目
	private String flag;
	
	 
	/**
	 * 添加投票基本信息
	 * @return
	 */
	public String addVoteBase(){
		
		try {
			Meeting meet = this.meetingService.findById(meetingId);
			voteBase.setMeeting(meet);
			this.voteService.saveOrUpdate(voteBase);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return findVoteAll();
	}
	
	/**
	 * 删除投票信息 会级联删除关联的投票项
	 * @return
	 * @throws ServiceException 
	 */
	public String deleteVoteBase() throws ServiceException{
		voteBase=this.voteService.findById(voteBase.getId());
		log.info(voteBase);
		this.voteService.deleteVote(voteBase);
		return null;
	}
	
	/**
	 * 通过Id查找一个投票信息
	 * @return
	 */
	public String findVoteById(){
		try {
			voteBase=this.voteService.findById(voteId);
			if(flag!=null&&flag.equals("find")){
				return "findVote";
			}
			if(voteBase.getItems()!=null&&voteBase.getItems().size()>1){
				errMsg="true";  // 可编辑状态
			}
			flag="edit";
			return "editVote";
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改投票信息
	 * @return
	 * @throws ServiceException
	 */
	public String updateVote() throws ServiceException{
		Meeting meet = this.meetingService.findById(meetingId);
		voteBase.setMeeting(meet);
		this.voteService.updateVote(voteBase);
		return  this.findVoteAll();
	}
	
	
	/**
	 * 查处某一个会议下的所有投票
	 * @return
	 */
	public String findVoteAll(){
		list=this.voteService.findVoteByMeetId(meetingId);
		return "findAll";
	}
	
	/**
	 * 查出某一个会议下所有有效的投票
	 * @return
	 */
	public String findEnableVote(){
		log.info("************"+meetingId);
		list=this.voteService.findVoteByMeetIdAndState(meetingId);
		
		return "findEnableVote";
		
	}
	
	/**
	 * 处理投票操作
	 * @return
	 * @throws ServiceException 
	 */
	public String processVote() throws ServiceException{
		if(checks==null||checks.length<=0){
			this.errMsg="请选择投票项";
			log.info("*****************"+"noCheck");
			return "needCheck";
			
		}
		/**
		 * 防止刷新
		 */
		boolean flag=this.voteHistoryService.validateVote(this.getUserIdFromSession(), voteId);
		if(flag){
			return this.findEnableVote();
		}
		
		this.voteService.processVote(voteId,checks,this.getUserFromSession());
		return this.lookVoteResult();
	}
	
	
	
	
	/**
	 * 查看投票结果
	 * @return
	 * @throws ServiceException 
	 */
	public String lookVoteResult() throws ServiceException{
		
		voteBase=this.voteService.findById(voteId);
		String contents="";
		String counts="";
		List<VoteItem> list=this.voteItemService.findItemByVoteId(voteId);
		for(VoteItem v:list){
			contents=contents+v.getContent()+",";
			counts=counts+v.getCount()+",";
		}
		ActionContext.getContext().put("contents", contents);
		ActionContext.getContext().put("counts", counts);
		ActionContext.getContext().put("size", list.size());
		ActionContext.getContext().put("list", list);
		return "lookVoteResult";
	}
	
	@Override
	public VoteBaseInfo getModel() {
		return voteBase;
	}
	/**
	 * @param voteBase
	 */
	public void setVoteBase(VoteBaseInfo voteBase) {
		this.voteBase = voteBase;
	}
	/**
	 * @Description
	 * @return the voteBase
	 */
	public VoteBaseInfo getVoteBase() {
		return voteBase;
	}
	/**
	 * @param voteService
	 */
	public void setVoteService(VoteBaseInfoService voteService) {
		this.voteService = voteService;
	}
	/**
	 * @Description
	 * @return the voteService
	 */
	public VoteBaseInfoService getVoteService() {
		return voteService;
	}

	/**
	 * @param voteId
	 */
	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}

	/**
	 * @Description
	 * @return the voteId
	 */
	public Long getVoteId() {
		return voteId;
	}

	/**
	 * @param list
	 */
	public void setList(List<VoteBaseInfo> list) {
		this.list = list;
	}

	/**
	 * @Description
	 * @return the list
	 */
	public List<VoteBaseInfo> getList() {
		return list;
	}

	/**
	 * @param meetingService
	 */
	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	/**
	 * @Description
	 * @return the meetingService
	 */
	public MeetingService getMeetingService() {
		return meetingService;
	}

	/**
	 * @param meetingId
	 */
	public void setMeetingId(Long meetingId) {
		if(meetingId!=null&&(meetingId instanceof Long)){
			this.meetingId = meetingId;
		}else{
			this.meetingId=135L;
		}
	}

	/**
	 * @Description
	 * @return the meetingId
	 */
	public Long getMeetingId() {
		return meetingId;
	}

	/**
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @Description
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param checks
	 */
	public void setChecks(String[] checks) {
		this.checks = checks;
	}

	/**
	 * @Description
	 * @return the checks
	 */
	public String[] getChecks() {
		return checks;
	}

	/**
	 * @param voteItemService
	 */
	public void setVoteItemService(VoteItemService voteItemService) {
		this.voteItemService = voteItemService;
	}

	/**
	 * @Description
	 * @return the voteItemService
	 */
	public VoteItemService getVoteItemService() {
		return voteItemService;
	}

	/**
	 * @param voteHistoryService
	 */
	public void setVoteHistoryService(VoteHistoryService voteHistoryService) {
		this.voteHistoryService = voteHistoryService;
	}

	/**
	 * @Description
	 * @return the voteHistoryService
	 */
	public VoteHistoryService getVoteHistoryService() {
		return voteHistoryService;
	}

}
