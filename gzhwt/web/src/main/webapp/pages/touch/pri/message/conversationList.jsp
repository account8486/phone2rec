<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">私信</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
    	<p style="margin:0px auto; text-align:center; overflow:auto; padding:10px;">
            <a href="${ctx }/touch/pri/msg/list.action?meetingId=${meetingId}" class="login_btn">+新建私信</a>
            <a id="delBtn" href="#" class="login_btn2">清空</a>
        </p>
        <ul class="msg_list">
			<c:choose>
	            <c:when test="${not empty messageUserList}">
	                <c:forEach var="entity" items="${messageUserList}" varStatus="status">
		        	<li>
		            	<a href="${ctx}/touch/pri/msg/listMessages.action?meetingId=${meetingId}&otherUid=${entity.id}&otherName=${entity.name}">
			                <span class="time">
								<fmt:formatDate value="${entity.message.sendTime}"
                                                                 type="both"
                                                                 pattern="yyyy-MM-dd HH:mm:ss"/>
							</span>
			            	<span class="name">${entity.name }</span>
			                <p class="cont">${fn:substring(entity.message.message, 0, 18) }</p>
		                </a>
		            </li>
             		</c:forEach>
                </c:when>
                <c:otherwise>
                </c:otherwise>
			</c:choose>
        </ul>
    </div>
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	ul.msg_list {}
	ul.msg_list li { padding:10px; border-bottom:1px solid #ccc;}
	ul.msg_list li.even { background:#f2f2f2;}
	ul.msg_list li a { display:block;}
	ul.msg_list li span.time { float:right; color:#999;}
	ul.msg_list li span.name { color:#069; font-weight:bold;}
	a.login_btn { width:48%;  height:38px; display:block; float:left; line-height:38px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:2px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;}
	a.login_btn2 { width:48%;  height:40px; display:block; float:right; line-height:40px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:2px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#333; background:url(${ctx}/images/touch/box_t_bg.png) top center repeat-x; border-left:1px solid #ccc;  border-right:1px solid #ccc;}
</style>

<script>
	/*这里是页面私有脚本*/
	$(function(){
		//$('ul.msg_list li:even').addClass('even');
		Li_even('ul.msg_list','odd');
		
		$("#delBtn").click(function(){
			if(confirm("您确认要删除这些信息吗？"))
			{
				location.href = "${ctx }/touch/pri/msg/deleteMessages.action?meetingId=${meetingId}";
			}
		});
	});
</script>