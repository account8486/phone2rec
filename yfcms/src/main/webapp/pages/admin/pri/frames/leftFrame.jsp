<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
${admin_css}
${jquery_js}
<script type="text/javascript" src="${ctx}/js/admin.js"></script>
<div class="main" >
	<div class="left_nav">
         <c:if test="${not empty unitViewList }">
	      		<c:forEach var="securityUnitView" items="${unitViewList}" varStatus="status">
	      		<dt><h5>${securityUnitView.securityParentUnit.unitName}</h5></dt>
	      	        <c:if test="${not empty securityUnitView.sonUnitList}">
	      	        	<dl>
	      	          	<c:forEach var="sonUnit" items="${securityUnitView.sonUnitList}" varStatus="status">
	      	          	   <dd><a target="mainFrame" href="${sonUnit.unitUrl}" onclick="switchCss($(this));">
								 <span class="nav">${sonUnit.unitName}</span>
							   </a></dd>  
	      	         </c:forEach>
	      	         </dl>
	      	        </c:if>
	      	 </c:forEach>
 		</c:if>
 		
    </div>    
</div>   