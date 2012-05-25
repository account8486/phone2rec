<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>
<div class="main">
	<div class="path">
     ${meeting_list_url} > ${param.menu_name}
    </div>
    
	<div style="margin-left:2px;">
	    <div class="article">
	    <c:choose>
        	<c:when test="${signIn != null && signIn.signCode != null}">
	   			尊敬的用户，您的签到码是：${signIn.signCode}
	   		</c:when>
            <c:otherwise>
            	尊敬的用户，系统还没有为您生成签到码，请联系管理员。
           	</c:otherwise>
		</c:choose>
	   	</div>
   	</div>
	
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>