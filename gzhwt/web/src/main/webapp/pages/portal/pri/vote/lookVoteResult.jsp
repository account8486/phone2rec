<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>
<link href="${ctx}/css/votecss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/vote.js"></script>

	<div class="w960">
	<div class="cbox"><div class="title">
		<h5 id="caption" value="标题 - ${voteBase.title}"></h5>
	</div></div>
		<div id="lookVote"></div>
		
		<div class="page_form_sub">
       		<a class="btn_blue vote enterVoteList" href="#" meetingId="${voteBase.meeting.id }">投票列表</a>   
    	</div> 
	</div>


<%@ include file="/pages/portal/common/footer.html" %>

<script type="text/javascript">

$(function(){
	var size="${size}";
	var counts="${counts}";
	var contents="${contents}"
	counts=counts.slice(0,counts.length-1);
	contents=contents.slice(0,contents.length-1);
	counts=counts.split(",");
	contents=contents.split(",");
	var array=[]
	var colors=['#39c','#f00','#000','#E38','#b57','#888','#d95','#ad5']
	for(var i=0;i<size;i++){
		var data={"name":contents[i],"data":counts[i],"color":colors[i]};
		array.push(data);
	}
	lookVote(array,${voteBase.count});
	
	/*投票列表*/
	$(".enterVoteList").click(function(e){
		var id=$(e.target).attr("meetingId");
		window.location.href="${ctx}/portal/pri/meeting/vote_findEnableVote.action?meetingId="+id+"&menu_id=${param.menu_id}";
	});
});
    
</script>
