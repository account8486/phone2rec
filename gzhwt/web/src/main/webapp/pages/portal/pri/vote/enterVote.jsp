<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

	<div class="w960">
	<div class="cbox"><div class="title">
		<h5 id="caption" value="标题 - ${voteBase.title}"></h5>
	</div></div>
		<div>
		<form action="${ctx}/portal/pri/meeting/vote_processVote.action?menu_id=${param.menu_id}" id="processVote" method="post">
			<c:forEach items="${list }" var="item" >
				<p style="padding: 5px">
				<input type="${voteBase.type==1?"radio":"checkbox" }" value="${item.id }" name="checks"/>
				<span>${item.content }</span>
				</p>
			</c:forEach>
			<input type="hidden" value="${voteBase.id}" name="voteId"/>
			<input type="hidden" value="${voteBase.meeting.id}" name="meetingId"/>
		</form>	
		</div>
		<div class="page_form_sub">
       		<a class="btn_blue vote" href="#">投票</a>   
       		<a href="javascript:listVote(${voteBase.meeting.id});"   class="btn_blue">投票列表</a>  
    	</div>
	</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">

$(function(){
	$("input:first").attr("checked",true);
	/*投票*/
	$(".vote").click(function(e){
		var size=$("input:checked").size();
		if(size<=0){
			alert("请选择投票项");
			return ;
		}
		$("#processVote").submit();
	});
});

/**
 * 投票列表
 */
function listVote(meetId){
	window.location.href="${ctx}/portal/pri/meeting/vote_findEnableVote.action?menu_id=${param.menu_id}&meetingId="+meetId+"&menu_id=${param.menu_id}";
}
    
</script>