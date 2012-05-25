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
			<table  class="content_table" >
                <thead>
                   <tr >	                   
                    <th style="width:40%">投票标题</th>
                    <th style="width:30%;">投票人数</th>
                    <th style="width:30%;">进入投票</th>
                </tr>
                </thead> 
                <tbody> 
                <c:choose>
	            	<c:when test="${not empty list}">
                        <c:forEach var="vote" items="${list}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td>${vote.title }</td>
                                <td >
                                    ${vote.count }
                                </td>
                                <td>
                                <a class="btn_blue enterVote" href="#" voteId="${vote.id }">参与投票</a>
                                
                                <c:if test="${vote.count>0 }">
                                <a class="btn_blue lookVote" style="margin-top: 5px;" href="#"  voteId="${vote.id }">查看结果</a>
                                </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                </c:choose>
				</tbody>
			</table>
			
		</div>
      </div>
           	

<script type="text/javascript">
	
$(function(){
	/*进入投票*/
	$(".enterVote").click(function(e){
		var id=$(e.target).attr("voteId");
		window.location.href="${ctx}/client/pri/vote/voteItem_findItemByVoteId.action?flag=client&voteId="+id+"&menu_id=${param.menu_id}";
	});
	
	/*查看投票结果*/
	$(".lookVote").click(function(e){
		var id=$(e.target).attr("voteId");
		window.location.href="${ctx}/client/pri/vote/vote_lookVoteResult.action?voteId="+id+"&menu_id=${param.menu_id}";
	});
	
	var errMsg="${errMsg}";
	if(errMsg!=null&&errMsg!=""){
		alert(errMsg);
	}
});
    
</script>
</body>
</html>