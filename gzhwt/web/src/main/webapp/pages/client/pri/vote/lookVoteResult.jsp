<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <title>会务通平台</title>
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
    <link href="${ctx}/css/votecss.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/js/vote.js"></script>
</head>
<body>
      <div class="cont" style="min-height: 200px;">
      	   <div class="cbox"><div class="title">
				<h5 id="caption">投票标题-->${voteBase.title}</h5>
		   </div></div>
		<div id="lookVote" style="padding-left: 10px;"></div>
		  <br/>
		<div class="page_form_sub" style="padding-left: 10px;">
       		<a class="btn_blue vote enterVoteList" href="#" meetingId="${voteBase.meeting.id }">投票列表</a>   
    	</div> 
      </div>
           	

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
		window.location.href="${ctx}/client/pri/vote/vote_findEnableVote.action";
	});
});
    
</script>
</body>
</html>