<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>短信管理</title>
	${admin_css}                                   
	${jquery_js}                          
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}         

</head>
<body>
	<div class="page_title">
		<h3>短信内容  -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form">
	<form id="addUserForm" action="${ctx}/pages/admin/pri/meeting/saveMeetingUser.action" method="post">
	<input   type="hidden" id="meeting.id" name="meeting.id"  value="${meeting.id}" />
	    <fieldset>
	        <legend>短信信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title">短信内容</label>
	            </dt> <dd>
	            <textarea class="small" cols="100" rows="2" name="messageContent" id="messageContent" disabled="disabled" class="textbox">${meetingSms.messages}</textarea>
	           </dd>
	        </dl>	
	        
	         <dl>
	        	<dt>
	            	<label for="title">是否定时发送</label>
	            </dt>
	            <dd>
	            <input class="half" align="left" type="text"  value=" <c:choose><c:when test="${meetingSms.delay eq true}">是</c:when><c:otherwise>否</c:otherwise></c:choose>"  readonly="readonly" />
	            </dd>
	         </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title">发送时间</label>
	            </dt>
	            <dd>
	             <input class="half" align="left" type="text"  value="<c:choose><c:when test="${meetingSms.delay eq true}"><fmt:formatDate value="${meetingSms.sendTime}" type="both"  pattern="MM月d日 HH:mm"/> </c:when><c:otherwise><fmt:formatDate value="${meetingSms.createTime}" type="both"  pattern="MM月d日 HH:mm"/></c:otherwise></c:choose>"  readonly="readonly" />
	             
	           
	           </dd>
	        </dl>	
	        
	        <dl>
	        	<dt>
	            	<label for="title">发送状态</label>
	            </dt>
	            <dd>
	            <input class="half" type="text" value="<c:choose><c:when test="${ meetingSms.state eq 0}">未发送 </c:when><c:when test="${ meetingSms.state eq 1}">已发送</c:when> </c:choose>"/>
	            </dd>
	        </dl>	
	        
	         <dl>
	        	<dt>
	            	<label for="title">创建时间</label>
	            </dt>
	            <dd>
	            <input class="half" type="text" value="<fmt:formatDate value="${meetingSms.createTime}" type="both"  pattern="MM月d日 HH:mm"/>"/>
	            </dd>
	        </dl>	   
	         
	           <dl>
	        	<dt>
	            	<label for="title">接收人</label>
	            </dt>
	            <dd>
	      		<c:choose>
	      		<c:when test="${not empty meetingSms.recipient}">
	            <c:forEach  var="user" items="${meetingSms.recipient}" varStatus="status"> 
	            <c:set var="recieverInfo" value="${user.name}[${user.mobile}]"></c:set>
	           	</c:forEach>
	            </c:when>
	            
	            <c:otherwise>
	             <c:set var="recieverInfo" value=" ${meetingSms.recieverName}[ ${meetingSms.mobile}]"></c:set>
	            </c:otherwise>
	      		</c:choose>
	        	<input class="half" type="text" value="${recieverInfo}"/> 
	            </dd>
	      </dl>
	      
	    </fieldset>
	    
	    
	   
	   
	    
	    <div class="page_form_sub">
	        <!-- <a href="#" onclick="addUser();" id="addUserBtn" class="btn_common btn_true">保 存</a>　 -->
	        
	        <a href="${ctx}/admin/pri/message/getMessageList.action?meetingId=${meetingId}" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	</form>
	</div>

</body>
</html>