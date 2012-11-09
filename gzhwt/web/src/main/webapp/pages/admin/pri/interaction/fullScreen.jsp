<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta name="keywords" content="" />
<meta name="description" content="" />
<!--浏览器特性-->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!--多终端icon-->
<link href="favicon.ico" type="image/x-icon" rel="Bookmark" />
<link href="favicon.ico" type="image/x-icon" rel="shortcut icon" />
<link href="images/logo_apple.png" rel="apple-touch-icon" />
<title>会务通平台</title>
${jquery_js}
${main_css }
<style type="text/css">
	/* Global */
	body,div,table,form,p,select,input,textarea,option,a {
	font-family: Verdana, Arial, sans-serif;
	line-height: 1.5; font-size: 20px; color: #333;
	}
</style>
<script type="text/javascript">
	function findContent(){
		var postId=$("#contain>li:first-child").attr("postId");
		if(postId==null||postId==""||postId==undefined){
			postId=0;
		}
		var param={"meetingId":${meetingId},"postId":postId};
		$.post("${ctx}/portal/pri/interaction/fullScreen.action",param,function(data){
			if(data!=null&&data!=""){
				$(data).prependTo("#contain").slideDown("slow");
			}
		});
	}
	$(function(){
		// setInterval("findContent()",5000);
		 setInterval("reloadCurrentPager()",30000);
		 $('ul.hd_list li:odd').addClass('odd');
	})	
	
	
	function reloadCurrentPager(){
		window.location.reload();
	}

</script>
</head>
<body style="background:#8ac8f5 url(${ctx}/images/hd_bg.jpg) top center repeat-x;">
	<div class="header" style="background:none;">
		<div class="w960" style="width:802px;">
	    	<ul class="title">
	        	<li class="iepng"><img src="${ctx}/images/portal/logo.png"/></li>
	            <li style="margin-top:5px;"><h3>${fn:escapeXml(_portal_meeting_.name)}</h3></li>
	        </ul>
	        <div class="detail">
	            <a href="javascript:close();" style="margin-top:10px; display:inline-block;"><img src="${ctx}/images/hd_quit.png"/></a>
	        </div>
	    </div>
	</div>
	
	<div class="hd_bg">
	<div class="t"></div>
    
    <div class="m">
    
    	<ul class="hd_list" id="contain">
    	<c:forEach var="post" items="${pager.pageRecords}" varStatus="status">
        	<li postId="${post.id }">
            	<div class="t"></div>
                <div class="m">
                    <span class="cont"><a href="javascript:void">
                     <c:if test="${empty post.user}">
                 	 参会代表
                     </c:if>
                   <c:if test="${post.user.meetingMember.memberLevel eq 1}">
                    ${post.posterName }
                   </c:if>
                    <c:if test="${post.user.meetingMember.memberLevel eq 2}">
                                                   参会代表
                    </c:if>
                    
                    </a>：${wd:convertToHtmlLine(post.content) }</span>
                    <span class="other">
                    	<s><fmt:formatDate value="${post.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>发表</S>
                        <a href="javascript:void">浏览[${post.viewCount }]</a><a href="javascript:void">评论[${post.commentCount }]</a>
                    </span>
                </div>
                <div class="e"></div>                
            </li>
          </c:forEach> 
        </ul>
    
    </div>
    
    <div class="e"></div>
</div>
</body>
</html>