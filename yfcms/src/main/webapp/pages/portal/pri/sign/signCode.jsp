<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
	<%@ include file="/pages/portal/common/header.jsp" %>	
	<div class="w960">
	<div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
	        <div class="cbox">
	        	<div class="title"><h5 id="caption">
	        	<c:choose>
		        	<c:when test="${signIn != null && signIn.signCode != null}">
			   			尊敬的用户，您的签到码是：${signIn.signCode}
			   		</c:when>
		            <c:otherwise>
		            	尊敬的用户，系统还没有为您生成签到码，请联系管理员。
		           	</c:otherwise>
				</c:choose>
	        	</h5></div>
	        </div>
	        <br/><br/><br/><br/><br/><br/><br/>
	</div>

	<%@ include file="/pages/portal/common/footer.html" %>
