<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
    <c:if test="${select == 'inbox'}">
    	<div class="path">${meeting_list_url} > ${param.menu_name}  > 收件箱 </div>
    	<div class="article date">
    		<a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&select=outbox&menu_id=${param.menu_id}">发件箱</a>
    		<a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&select=new&menu_id=${param.menu_id}">新私信</a>
    	</div>
    </c:if> 

    <c:if test="${select == 'outbox'}">
    	<div class="path">${meeting_list_url} > ${param.menu_name}  > 发件箱 </div>
    	<div class="article date">
    		<a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&select=inbox&menu_id=${param.menu_id}">收件箱</a>
    		<a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&select=new&menu_id=${param.menu_id}">新私信</a>
    	</div>
    </c:if>

    <c:if test="${select == 'new'}">
    	<div class="path">${meeting_list_url} > ${param.menu_name}  > 新私信 </div>
    	<div class="article date">
    		<a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&select=inbox&menu_id=${param.menu_id}">收件箱</a>
    		<a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&select=outbox&menu_id=${param.menu_id}">发件箱</a>
    	</div>
		<form id="loginForm" action="${ctx}/wap/pri/message/send.action" method="post">
			<div class="message_sub">
			    <p>
	       			<a href="${ctx}/wap/pri/message/selectUser.action?meetingId=${meetingId}&menu_id=${param.menu_id}">收信人</a>
	       			<c:choose>
					<c:when test="${not empty user}">
	       				<span class="recipient">${user.name}<c:if test="${not empty user.job && user.isShowJob eq 1}">${user.job}</c:if></span>
	       			</c:when>
	       			<c:otherwise>
	       				<span class="recipient">请选择收信人</span>
	       			</c:otherwise>
	       			</c:choose>
       			</p>
       			<p style="color:#999;font-size:12px;">最多可以输入<span id="text_limit">512</span>个字符</p>
		  	    <span class="ipt">
		  	    	<textarea name="message" id="message" rows="3" cols="30"></textarea>
		  	    	<input type="hidden" name="userId" id="userId" value="${user.id}"/>
		  	    </span>
		    </div>
			<div class="message_sub">
		  	    <span class="ipt">
		  	    	<input type="submit" value="发送"/>
		            <input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
		            <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
		  	    </span>
		    </div>	    
	    </form>
    </c:if>

	<c:if test="${select != 'new'}">
	<div class="article">
	  	<c:choose>
	        <c:when test="${not empty messages}">
	        	<ul class="message_list">
	            <c:forEach var="message" items="${messages}" varStatus="status">
	            <li>
	            	<p style="text-align:left;">
	                    <span class="name">
	                    	<c:if test="${select == 'inbox'}">发信人: ${message.sender.name }<c:if test="${not empty message.sender.job && message.sender.isShowJob eq '1'}">${message.sender.job}</c:if></c:if>
			            	<c:if test="${select == 'outbox'}">收信人: 
			            	<c:if test="${fn:length(message.recipients) > 0}">
            				<c:forEach var="recipient" items="${message.recipients}" varStatus="status">
            					${recipient.name }<c:if test="${not empty recipient.job && recipient.isShowJob eq '1'}">${recipient.job}</c:if>
            					<c:if test="${!status.last}">;&nbsp;</c:if>
            				</c:forEach>
            				</c:if>
			            	</c:if>
			            </span>
			            <div class="contentbox"><span class="content">${message.message}</span></div>
	                </p>
	                <p style="text-align:right;">
						<span class="time">${message.time}</span>	                	
	                	<span class="comment">&nbsp;</span>
	                    <span class="ctrl">
	                    	<c:if test="${select == 'inbox'}">
	                    		<a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&select=new&selectuser=${message.sender.id}&menu_id=${param.menu_id}">回复</a>&nbsp;
	                    	</c:if>
	                    	<a href="${ctx}/wap/pri/message/delete.action?meetingId=${meetingId}&id=${message.id }&select=${select}&menu_id=${param.menu_id}">删除</a> 
	                    </span>
	                </p>	            
	            </li>
	            </c:forEach>
	           	</ul>
	        </c:when>
	        <c:otherwise>
	        	<c:if test="${select == 'inbox'}">
		        	<span class="content">没有收到站内私信.</span>
		        </c:if>
		        <c:if test="${select == 'outbox'}">
		        	<span class="content">没有已发送的站内私信.</span>
		        </c:if>
	        </c:otherwise>
	    </c:choose>
	</div>
	</c:if>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>