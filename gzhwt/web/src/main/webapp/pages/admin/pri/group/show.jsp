<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>分组计划管理</title>
	${admin_css}                                   
	${jquery_js}
</head>
<body>
	<div class="page_title">
		<h3>分组模版管理  -- ${CURRENT_MEETING}</h3>
	</div>
	
   	<div style="margin: 10px;" align="left">
      	<a href="javascript:returnList('${meetingId }');" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
    </div>
	
	
		<c:forEach items="${groupDetailMap}" var="entry">  
		   <c:set var="group" value="${entry.key}"/>
		   <c:set var="userList" value="${entry.value}"/>
		   <c:set var="listSize" value="${fn:length(userList)}"></c:set>
			<table class="page_datalist">
		    	<thead>
		        	<tr>
		        		<th width="1px">&nbsp;</th>
		            	<th colspan="4">组名：${group.groupName }&nbsp;&nbsp;&nbsp;总计:${listSize }人</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:if test="${not empty group.detail }">
			        	<tr>
			        		<td width="1px">&nbsp;</td>
			            	<td colspan="4">${group.detail }</td>
			            </tr>
		            </c:if>
                    <c:forEach var="user" items="${userList}" varStatus="status">
                        <c:if test="${status.first || (status.index+4)%4 == 0}">
	                        <tr  <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if> >
                            <td></td>
                        </c:if>
                            <td align="left" width="180px">${user.name }[${user.mobile}]</td>
                        
                        <c:if test="${(status.index+5)%4 == 0}">
                        	</tr>
                        </c:if>
                        <c:if test="${status.last}">
                        	<c:if test="${(listSize+4) % 4 != 0}">
                        		<c:set var="blankCount" value="${4 - ((listSize+4) % 4)}"></c:set>
                        		<c:forEach begin="0" end="${blankCount-1}" step="1">
                        			<td align="left" width="180px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        		</c:forEach>
                        	</c:if>
                        	</tr>
                        </c:if>
                    </c:forEach>
		        </tbody>
		    </table>
	</c:forEach>

   
	<script type="text/javascript">
		function returnList(meetingId){
			window.location.href = "${ctx}/admin/pri/group/list.action?meetingId="+meetingId;
		}
	</script>
</body>
</html>