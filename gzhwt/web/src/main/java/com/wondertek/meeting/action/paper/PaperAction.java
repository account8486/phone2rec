
package com.wondertek.meeting.action.paper;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.Paper;
import com.wondertek.meeting.model.Question;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.PaperService;
import com.wondertek.meeting.service.QuestionService;

/** 
 * @ClassName: PaperAction 
 * @Description: 处理考试操作
 * @author zouxiaoming
 * @date Feb 2, 2012 10:20:56 AM 
 *  
 */

@SuppressWarnings("serial")
public class PaperAction extends BaseAction implements ModelDriven<Paper> {
	private Log log = LogFactory.getLog(PaperAction.class);
	private Paper paper =new Paper();
	private PaperService paperService;
	private QuestionService questionService;
	private MeetingService meetingService;
	private Long meetingId;
	private List<Paper> list;
	private String flag;
	
	
	/**
	 * 查处某一个会议下的所有调查
	 * @return
	 */
	public String findPaperAll(){
		list=this.paperService.findPaperByMeetId(meetingId);
		return "findAll";
	}
	
	
	/**
	 * 查出某一个会议下所有有效的调查
	 * @return
	 */
	public String findEnablePaper(){
		list=this.paperService.findEnablePaper(meetingId);
		return "findEnablePaper";
	}
	
	/**
	 * 添加调查
	 * @return
	 */
	public String addPaper(){
		log.debug("meetingId:"+meetingId);
		try {
			Meeting meet = this.meetingService.findById(meetingId);
			paper.setMeeting(meet);
			paper.setCreator(this.getAdminUserFromSession());
			this.paperService.saveOrUpdate(paper);
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return this.findPaperAll();
	}
	
	
	/**
	 * 删除调查信息 会级联删除关联的调查试题
	 * @return
	 * @throws ServiceException 
	 */
	public String deletePaper() throws ServiceException{
		this.paperService.deletePaper(paper.getId());
		return null;
	}
	
	/**
	 * 通过Id查找一个调查信息
	 * @return
	 */
	public String findPaperById(){
		try {
			paper=this.paperService.findById(paper.getId());
			List<Question> list=this.paperService.findAllQuestion(paper.getId(), true);
			if(list!=null&&list.size()>0){
				errMsg="true";
			}
			flag="edit";
			return "editPaper";
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 修改调查信息
	 * @return
	 * @throws ServiceException
	 */
	public String updatePaper() throws ServiceException{
		Meeting meet = this.meetingService.findById(meetingId);
		paper.setMeeting(meet);
		paper.setCreator(this.getAdminUserFromSession());
		this.paperService.saveOrUpdate(paper);
		return this.findPaperAll();
	}
	

	
	
	@Override
	public Paper getModel() {
		return getPaper() ;
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
	 * @param paper
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	/**
	 * @Description
	 * @return the paper
	 */
	public Paper getPaper() {
		return paper;
	}
	/**
	 * @param paperService
	 */
	public void setPaperService(PaperService paperService) {
		this.paperService = paperService;
	}
	/**
	 * @Description
	 * @return the paperService
	 */
	public PaperService getPaperService() {
		return paperService;
	}


	/**
	 * @param list
	 */
	public void setList(List<Paper> list) {
		this.list = list;
	}


	/**
	 * @Description
	 * @return the list
	 */
	public List<Paper> getList() {
		return list;
	}


	/**
	 * @param questionService
	 */
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}


	/**
	 * @Description
	 * @return the questionService
	 */
	public QuestionService getQuestionService() {
		return questionService;
	}
	
	 
	

}
