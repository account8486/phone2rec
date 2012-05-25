<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <title>电信会议云</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <!--浏览器特性-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!--多终端icon-->
    <link href="favicon.ico" type="image/x-icon" rel="Bookmark"/>
    <link href="favicon.ico" type="image/x-icon" rel="shortcut icon"/>
    <link href="res/logo_apple.png" rel="apple-touch-icon"/>
    ${main_css}
    ${jquery_js }
    <link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
      <div class="cont" style="min-height: 200px;">
      	<div>
		<form action="${ctx}/client/pri/vote/vote_processVote.action" id="processVote">
			<c:forEach items="${list }" var="item" >
				<p style="padding: 5px">
				<input type="${voteBase.type==1?"radio":"checkbox" }" value="${item.id }" name="checks"/>
				<span>${item.content }</span>
				</p>
			</c:forEach>
			<input type="hidden" value="${voteBase.id}" name="voteId"/>
			
		</form>	
		</div>
		<div class="page_form_sub">
       		<a class="btn_blue vote" href="#">投票</a>   
       		<a href="javascript:listVote(${voteBase.meeting.id});"   class="btn_blue">投票列表</a>  
    	</div>      
      </div>
           	
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
	window.location.href="${ctx}/client/pri/vote/vote_findEnableVote.action";
}
    
</script>
</body>
</html>