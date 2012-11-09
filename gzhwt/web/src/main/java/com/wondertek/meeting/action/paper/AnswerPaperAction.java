
package com.wondertek.meeting.action.paper;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ModelDriven;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.model.AnswerPaper;
import com.wondertek.meeting.service.AnswerPaperService;

/**
 * 
* @ClassName: AnswerPaperAction 
* @Description: 答卷处理
* @author zouxiaoming
* @date Feb 23, 2012 3:41:45 PM 
*
 */

@SuppressWarnings("serial")
public class AnswerPaperAction extends BaseAction implements ModelDriven<AnswerPaper> {
	private Log log = LogFactory.getLog(AnswerPaperAction.class);
	private AnswerPaper answerPaper=new AnswerPaper();
	private AnswerPaperService answerPaperService;
	
	
	@Override
	public AnswerPaper getModel() {
		return answerPaper;
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
	

}
