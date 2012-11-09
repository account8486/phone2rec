<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/pages/common/taglibs.jsp" %>
 <c:forEach var="user" items="${list}" varStatus="status">
					 <c:if test="${status.index== 0 }">
					 <ul   class="user_check" >
						 <li>
					     			<input name="se"  type="checkbox"/>
					     			<label for="se">全选</label>
					     </li>
					 </ul>
					 <br></br>
					 </c:if>
					 <ul   class="user_check" name="check">
				     		<li>
				     			<input name="users" ${user.checked==1?"checked":""}  type="checkbox" value="${user.id}"/>
				     			<label for="users" title="${user.mobile }">${user.name}</label>
				     		</li>
			     	</ul>
</c:forEach>


