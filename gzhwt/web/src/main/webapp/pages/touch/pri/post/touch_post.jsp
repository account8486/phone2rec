<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>


	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">互动交流</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    
    <div class="tab_c" style="display:block;">
    
	<form id="postAddForm" action="${ctx}/touch/pri/interaction/postAdd.action" method="post">
		<div class="message_sub">
			<p style="color:#999;font-size:12px;">最多可以输入140个字符</p>
	  	    <span class="ipt">
	  	    	<textarea name="content" id="content" rows="6" cols="40"></textarea>
	  	    </span>
	    </div>
		<div class="message_sub">
	  	    <span class="ipt">
	  	    	<input id="publish" name="publish" type="button" value="发布信息"/>
	            <input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
	            <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
	  	    </span>
	    </div>	    
    </form>



<c:set var="meetingId" value="${not empty param.meetingId ? param.meetingId : meeting.id}"/>


	<div>
	  	<c:choose>
	        <c:when test="${not empty pager.pageRecords}">
	        	<ul class="comment_list">
	            <c:forEach var="meetingpost" items="${pager.pageRecords}" varStatus="status">
            	<li>
            	<div class="info">
                	<span class="name">
                	<%--
                	<c:if test="${not empty meetingpost.user}">
                    </c:if>${meetingpost.posterName }<c:if test="${not empty meetingpost.user}">
                    </c:if><c:if test="${not empty meetingpost.posterJob}">(${meetingpost.posterJob})</c:if> --%>
                                                  参会人员
                	</span><span class="time"><fmt:formatDate value="${meetingpost.createTime }" type="both" pattern="yyyy年MM月d日 HH:mm:ss"/></span>
                </div>
                <div class="cont">
                	${meetingpost.content}
                	  <c:if test="${not empty meetingpost.contentImg}">
						<div class="contentImg"><img src="${meetingpost.contentImg}"/></div>
					</c:if>
			
					<c:if test="${not empty meetingpost.videourl}">
						<span class="ctrl"><a href="${ctx}/wap/pri/interaction/downloadVideo.action?link=${meetingpost.videourl}" target="_blank">视频下载</a> | </span>
					</c:if>
					
                </div>
                <div class="tools">
                	<span class="stat">浏览(${meetingpost.viewCount })  评论(${meetingpost.commentCount })</span>
                	
                	<%--
                	<a href="${ctx}/touch/pri/interaction/postDetail.action?meetingId=${meetingId}&postId=${meetingpost.id }&menu_id=${param.menu_id}" class="sub">
                	我要评论
                	</a> --%>
                </div>
            </li>
            
            
	            </c:forEach>
	           	</ul>
	        </c:when>
	        <c:otherwise>
	           <span>该会议没有互动交流信息.</span>
	        </c:otherwise>
	    </c:choose>
	</div>
	
	
		<c:if test="${not empty pager.pageRecords}">
	    <div class="pager">
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a class="down" href="${ctx}/touch/pri/interaction/postView.action?meetingId=${meetingId}&currentPage=1&menu_id=${param.menu_id}">首页</a>
	   			<a class="down"  href="${ctx}/touch/pri/interaction/postView.action?meetingId=${meetingId}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a class="down" href="${ctx}/touch/pri/interaction/postView.action?meetingId=${meetingId}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
	   			<a class="down" href="${ctx}/touch/pri/interaction/postView.action?meetingId=${meetingId}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
	   		</c:if>
	    </div>
	</c:if>
	
	
</div>




<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	ul.comment_list {}
	ul.comment_list li { padding:10px; border-bottom:1px solid #ccc;}
	ul.comment_list li .info { display:block; overflow:auto;}
	ul.comment_list li .info span.name { float:left; color:#069; font-weight:bold;}
	ul.comment_list li .info span.time { float:right; color:#999;}
	ul.comment_list li .cont { font-size:16px; line-height:1.8;}
	ul.comment_list li .tools { overflow:auto;}
	ul.comment_list li .tools span.stat { float:left; color:#666;}
	ul.comment_list li .tools a.sub { float:right; color:#069;}
	
	pager { position:absolute; right:10px; bottom:10px;}
	pager a { width:60px;  height:30px; font-weight:bold; display:inline-block; text-align:center; line-height:30px; margin:10px 0px; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; margin-left:10px;}
	pager a.pre { }
	pager a.down { }
</style>

<script>
	/*这里是页面私有脚本*/
	$("#publish").click(function(){
		
		var content=$("#content").val();
		if(content==''){
			alert("请输入发言内容。");
			return;
		}
		
		$("#postAddForm").submit();
		
	});
	
	
	
</script>