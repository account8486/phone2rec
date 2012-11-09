<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">私信</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
    	<p style="margin:0px auto; overflow:auto; padding:10px; font-size:20px; font-weight:bold; line-height:40px;">
            <a href="${ctx }/touch/pri/msg/list.action?meetingId=${meetingId}&selectuser=${otherUid}" class="login_btn">+发送私信</a>
            ${otherName }
        </p>
		<ul class="msg_dia">
			<c:choose>
	            <c:when test="${not empty messageList}">
	                <c:forEach var="entity" items="${messageList}" varStatus="status">
		            <c:choose>
	            		<c:when test="${entity.sender.id eq selfUserId}">
	            			<li class="cont">
	            		</c:when>
		                <c:otherwise>
		                	<li class="back">
		                </c:otherwise>
					</c:choose>
		            	<p class="cont">${entity.message }</p>
		                <p class="time"><fmt:formatDate value="${entity.sendTime}"
                                                                 type="both"
                                                                 pattern="yyyy-MM-dd HH:mm:ss"/></p>
		            </li>
             		</c:forEach>
                </c:when>
                <c:otherwise>
                </c:otherwise>
			</c:choose>
        </ul>
        <p style="margin:0px auto; text-align:center; overflow:auto; padding:10px;">
            <a id="delBtn" href="#" class="login_btn2" style="width:95%; float:inherit; margin:0px auto;">清空</a>
        </p>
    </div>
    
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	ul.msg_dia { text-align:center;}
	ul.msg_dia li { width:300px; overflow:hidden; margin:0px auto; text-align:left; background:url(${ctx}/images/touch/msg_dia_go.png) -300px 0px repeat-y;}
	ul.msg_dia li p { padding:0px 10px;}
	ul.msg_dia li p.cont { background:url(${ctx}/images/touch/msg_dia_go.png) 0px 0px no-repeat; padding-top:5px; font-size:16px;}
	ul.msg_dia li p.time { background:url(${ctx}/images/touch/msg_dia_go.png) -600px bottom no-repeat; padding-bottom:15px; color:#666;}
	ul.msg_dia li.back { background:url(${ctx}/images/touch/msg_dia_back.png) -300px 0px repeat-y; margin-bottom:10px;}
	ul.msg_dia li.back p.cont { background:url(${ctx}/images/touch/msg_dia_back.png) 0px 0px no-repeat; padding-top:15px;}
	ul.msg_dia li.back p.time { background:url(${ctx}/images/touch/msg_dia_back.png) -600px bottom no-repeat; padding-bottom:5px;}
	ul.msg_dia li.back {}
	a.login_btn { width:48%;  height:38px; display:block; float:right; line-height:38px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:2px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;}
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
				window.location.href = "${ctx }/touch/pri/msg/deleteMessages.action?meetingId=${meetingId}&otherUid=${otherUid}";
			}
		});
	});
</script>