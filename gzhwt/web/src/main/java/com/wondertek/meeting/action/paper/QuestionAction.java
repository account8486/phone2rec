
package com.wondertek.meeting.action.paper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AnswerPaper;
import com.wondertek.meeting.model.Paper;
import com.wondertek.meeting.model.Question;
import com.wondertek.meeting.model.QuestionItem;
import com.wondertek.meeting.service.AnswerPaperService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.PaperService;
import com.wondertek.meeting.service.QuestionService;
import com.wondertek.meeting.util.StringUtil;


/** 
 * @ClassName: QuestionAction 
 * @Description: 处理考题操作
 * @author zouxiaoming
 * @date Feb 2, 2012 10:20:56 AM 
 *  
 */

@SuppressWarnings("serial")
public class QuestionAction extends BaseAction implements ModelDriven<Question> {
	private Log log = LogFactory.getLog(QuestionAction.class);
	private Question question =new Question();
	private PaperService paperService;
	private QuestionService questionService;
	private MeetingService meetingService;
	private AnswerPaperService answerPaperService;
	private Long meetingId;
	private Long paperId;
	private Paper linkPaper;
	private List<Question> list;
	private String flag;
	private String[] contents;
	private Long[] checks;
	private Long[] quests;
	
	private Long[] singles;
 	
	

	/**
	 * 添加投票选项信息
	 * @return
	 * @throws ServiceException 
	 */
	public String addQuestion() throws ServiceException{
		
		try {
			linkPaper =this.paperService.findById(paperId);
			question.setPaper(linkPaper);
			question.setCreator(this.getAdminUserFromSession());
			question.setQuestion(com.wondertek.meeting.util.StringUtil.getescapeText(question.getQuestion()));
			log.info("******"+contents.length);
			this.questionService.saveQuestion(question, contents);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return this.findQuestionsByPaperId();
	}
	
	/**
	 * 查出某一个试卷下面所有的考题
	 * @return
	 * @throws ServiceException
	 */
	public String findQuestionsByPaperId() throws ServiceException{
		linkPaper=this.paperService.findById(paperId);
		list=this.paperService.findAllQuestion(paperId, false);
		if(flag!=null&&flag.equals("result")){  // 查看结果
			return "lookResult";
		}
		return  "findByPaperId";
    }
	
	
	/**
	 * 删除题目 级联删除题目选项
	 * @return
	 * @throws ServiceException 
	 */
	public String deleteQuestion() throws ServiceException{
		question=this.questionService.findById(question.getId());
		this.questionService.delete(question);
		return null;
	}
	
	
	/**
	 * 根据id,查出一个试题详细内容
	 * @return
	 * @throws ServiceException
	 */
	public String findQuestion() throws ServiceException{
		question=this.questionService.findById(question.getId());
		log.info(question.getQuestion());
		log.info(question.getItems().size());
		ActionContext.getContext().put("quest", question);
		
		return "findQuestion";
	}
	
	/**
	 * 进入调查
	 * @return
	 * @throws ServiceException 
	 */
	public String enterExam() throws ServiceException{
		linkPaper=this.paperService.findById(paperId);
		meetingId=linkPaper.getMeeting().getId();
		if(flag!=null){
			List<AnswerPaper> list=this.answerPaperService.findMyAnswerPaper(this.getUserIdFromSession(),paperId);
		/*	if(list!=null&&list.size()>0){
				this.errMsg="您已经参与过调查了";
				return "enablePaper";
			}*/
		}
		Pager<Question> pager=null;
		pager=this.questionService.enterExam(paperId, currentPage, 20);
		log.info("****************:"+pager.getTotal());
		ActionContext.getContext().put("pager", pager);
		if(flag!=null&&flag.equals("web")){ // web入口
			return "enterWebExam";
		}else if(flag!=null&&flag.equals("client")){// 客户端入口
			return "enterClientExam";
		}else if(flag!=null&&flag.equals("wap")){  // wap入口
			return "enterWapExam";
		}else if(flag!=null&&flag.equals("touch")){  // wap入口
			return "enterTouchExam";
		}
		return "preveiw";  // 后台admin
	}
	
	/**
	 * 处理提交问卷
	 * @return
	 * @throws ServiceException
	 */
	public String processPager() throws ServiceException{
		linkPaper=this.paperService.findById(paperId);
		List<Long> singleList=new ArrayList<Long>();
		
		/**
		 * 单选题
		 */
		if(singles!=null&&singles.length>0){
			for (final Long single : singles) {
	            final String checkValue = getParameter("checks_" + single);
	            if (!StringUtil.isEmpty(checkValue)) {
	            	singleList.add(Long.parseLong(checkValue));
	            }
			}
		}
	
		
		 /* 多选题 */
        if (checks != null) {
        	singleList.addAll(Arrays.asList(checks));
        }
		if(flag!=null&&flag.equals("wap")){  //wap端在后台验证数据
			int count1=0;
			int count2=0;
			if(this.quests!=null){
				count1=quests.length;
			}
			count2=singleList.size();
			log.debug("size:"+(count1+count2));
			log.debug(linkPaper.getQuetions());
			List<Question> list=this.paperService.findAllQuestion(paperId, true);
			if(list.size()>(count1+count2)){
				this.errMsg="您还有选项没有填写";
				return this.enterExam();
			}
		}
		meetingId=linkPaper.getMeeting().getId();
		this.answerPaperService.processPaper(singleList,quests,contents, this.getUserFromSession(),question.getState(),linkPaper);
		return "enablePaper";
	}
	
	/**
	 * 查看问卷结果
	 * @return
	 * @throws ServiceException 
	 */
	public String lookResult() throws ServiceException{
		question=this.questionService.findById(question.getId());
		linkPaper=this.paperService.findById(paperId);
		ActionContext.getContext().put("linkPaper",linkPaper);
		ActionContext.getContext().put("question",question);
		if(question.getType()==3||question.getType()==1){// 简述题
			List<AnswerPaper> list=this.answerPaperService.findContent(linkPaper.getId(),question.getId());
			ActionContext.getContext().put("list", list);
			return "lookResultWithAjax1";
		}
		String contents="";
		String counts="";
		Set<QuestionItem> items=question.getItems();
		for(QuestionItem v:items){
			contents=contents+v.getContent()+",";
			counts=counts+v.getCount()+",";
		}
		ActionContext.getContext().put("contents", contents);
		ActionContext.getContext().put("counts", counts);
		ActionContext.getContext().put("size", items.size());
		return "lookResultWithAjax";
	}
	
	/**
	 * 查出我对某一个问卷的答卷
	 * @return
	 */
	public String findMyAnswerPaper(){
		try {
			linkPaper=this.paperService.findById(paperId);
			meetingId=this.linkPaper.getMeeting().getId();
			Pager<Question> pager=null;
			pager=this.questionService.enterExam(paperId, currentPage, 20);
			Map<Long,String> myMap=new HashMap<Long,String>();
			List<AnswerPaper> list=this.answerPaperService.findMyAnswerPaper(this.getUserIdFromSession(),paperId);
			if(list==null||list.isEmpty()){
				this.errMsg="您还没有参与此调查";
				return "enablePaper";
			}
			for(AnswerPaper ap:list){
				Long qId=ap.getQuestion().getId();
				if(ap.getType()==1){// 单选题
					myMap.put(qId,ap.getItem().getId().toString());
				}else if(ap.getType()==2){// 多选题
					Long itemId=ap.getItem().getId();
					myMap.put(itemId-10000, itemId.toString());
				}else{// 简述题
					myMap.put(qId, ap.getContent());
				}
			}
			
			ActionContext.getContext().put("pager",pager);
			ActionContext.getContext().put("myMap",myMap);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "myAnswerPaper";
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
	@Override
	public Question getModel() {
		return getQuestion();
	}
	/**
	 * @param list
	 */
	public void setList(List<Question> list) {
		this.list = list;
	}
	/**
	 * @Description
	 * @return the list
	 */
	public List<Question> getList() {
		return list;
	}
	/**
	 * @param question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}
	/**
	 * @Description
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * @param paperId
	 */
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	/**
	 * @Description
	 * @return the paperId
	 */
	public Long getPaperId() {
		return paperId;
	}

	/**
	 * @param linkPaper
	 */
	public void setLinkPaper(Paper linkPaper) {
		this.linkPaper = linkPaper;
	}

	/**
	 * @Description
	 * @return the linkPaper
	 */
	public Paper getLinkPaper() {
		return linkPaper;
	}

	/**
	 * @param contents
	 */
	public void setContents(String[] contents) {
		this.contents = contents;
	}

	/**
	 * @Description
	 * @return the contents
	 */
	public String[] getContents() {
		return contents;
	}

	/**
	 * @param checks
	 */
	public void setChecks(Long[] checks) {
		this.checks = checks;
	}

	/**
	 * @Description
	 * @return the checks
	 */
	public Long[] getChecks() {
		return checks;
	}

	/**
	 * @param answerPaperService
	 */
	public void setAnswerPaperService(AnswerPaperService answerPaperService) {
		this.answerPaperService = answerPaperService;
	}

	/**
	 * @Description
	 * @return the answerPaperService
	 */
	public AnswerPaperService getAnswerPaperService() {
		return answerPaperService;
	}

	/**
	 * @param quests
	 */
	public void setQuests(Long[] quests) {
		this.quests = quests;
	}

	/**
	 * @Description
	 * @return the quests
	 */
	public Long[] getQuests() {
		return quests;
	}

	public void setSingles(Long[] singles) {
		this.singles = singles;
	}

	public Long[] getSingles() {
		return singles;
	}


}
