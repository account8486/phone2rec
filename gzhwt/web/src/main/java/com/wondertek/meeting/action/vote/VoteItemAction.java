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
import com.wondertek.meeting.model.VoteBaseInfo;
import com.wondertek.meeting.model.VoteItem;
import com.wondertek.meeting.service.VoteBaseInfoService;
import com.wondertek.meeting.service.VoteHistoryService;
import com.wondertek.meeting.service.VoteItemService;

/** 
 * @ClassName: VoteItemAction 
 * @Description: 处理投票选项操作
 * @author zouxiaoming
 * @date Jan 10, 2012 2:19:38 PM 
 *  
 */
@SuppressWarnings("serial")
public class VoteItemAction extends BaseAction implements ModelDriven<VoteItem> {
	private Log log=LogFactory.getLog(VoteItemAction.class);
	private VoteItem item=new VoteItem();
	private VoteItemService voteItemService;
	private VoteBaseInfoService  voteService;
	private VoteHistoryService voteHistoryService;
	private Long voteId;
	private List<VoteItem> list;
	private VoteBaseInfo voteBase;
	private Long meetingId;
	private String flag;
	
	
	/**
	 * 添加投票选项信息
	 * @return
	 * @throws ServiceException 
	 */
	public String addVoteItem() throws ServiceException{
		
		try {
			VoteBaseInfo  vote =this.voteService.findById(voteId);
			item.setVote(vote);
			item.setContent(com.wondertek.meeting.util.StringUtil.getescapeText(item.getContent()));
			this.voteItemService.add(item);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return this.findItemByVoteId();
	}
	
	/**
	 * 删除投票选项
	 * @return
	 * @throws ServiceException 
	 */
	public String deleteVoteItem() throws ServiceException{
		log.info("itemId："+item.getId());
		this.voteItemService.delete(item);
		return "deleteVoteItem";
	}
	
	/**
	 * 通过Id查找一个投票选项信息
	 * @return
	 */
	public String findVoteItemById(){
		try {
			item=this.voteItemService.findById(item.getId());
			ActionContext.getContext().put("item", item);
			flag="edit";
			return "editVoteItem";
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
	public String updateVoteItem() throws ServiceException{
		VoteBaseInfo  vote =this.voteService.findById(voteId);
		item.setVote(vote);
		this.voteItemService.saveOrUpdate(item);
		return  this.findItemByVoteId();
	}
	
	/**
	 * 通过投票id查找其下的所有投票选项
	 * @return
	 * @throws ServiceException 
	 */
	public String findItemByVoteId() throws ServiceException{
		
		voteBase=this.voteService.findById(voteId);
		if(flag!=null){
			boolean result=this.voteHistoryService.validateVote(this.getUserIdFromSession(), voteId);
			if(result){
				errMsg="您好，您已经投过票了!";
				meetingId=voteBase.getMeeting().getId();
				if(flag.equals("touch")){  // touch-wap
					List<VoteBaseInfo> list=this.voteService.findVoteByMeetIdAndState(meetingId);
					ActionContext.getContext().put("list", list);
				}
				return "hasVote";
			}
		}
		
		list=this.voteItemService.findItemByVoteId(voteId);
		if(flag!=null&&flag.equals("web")){
			return "enterVote" ;  // web入口
		}else if(flag!=null&&flag.equals("wap")){  // wap入口
			return "enterWapVote";
		}else if(flag!=null&&flag.equals("client")){  // 客户端入口
			return "enterClientVote";
		}else if(flag!=null&&flag.equals("touch")){  //touch入口
			return "enterTouchVote";
		}
		return "findByVoteId";  
	}
	
	
	@Override
	public VoteItem getModel() {
		return item;
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
	public void setList(List<VoteItem> list) {
		this.list = list;
	}

	/**
	 * @Description
	 * @return the list
	 */
	public List<VoteItem> getList() {
		return list;
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

	/**
	 * @param meetingId
	 */
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @Description
	 * @return the meetingId
	 */
	public Long getMeetingId() {
		return meetingId;
	}


}
