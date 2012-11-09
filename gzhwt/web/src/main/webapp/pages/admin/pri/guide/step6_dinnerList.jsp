<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>

	
	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	        		<th width="2">&nbsp;</th>
	            	<th >日&nbsp;&nbsp;期</th>
	            	<th >时&nbsp;&nbsp;间</th>
                    <th >用餐时段</th>
                    <th >类&nbsp;&nbsp;型</th>
                    <th >地&nbsp;&nbsp;点</th>
                  
	            </tr>
	        </thead>
	        <tbody>
	            <c:choose>
                    <c:when test="${not empty dinnerList}">
                        <c:forEach var="dinner" items="${dinnerList}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if> >
                                <td></td>
                                <td align="left">${dinner.dinnerDate }</td>
                                <td align="left">${dinner.startTime }-${dinner.endTime }</td>
                              
                                
                                <c:set var="section" value="${1 == dinner.section ? '早':2==dinner.section?'中':'晚'}"></c:set>
                                <td align="left">${section}餐</td>
                                
                             
                                
                                <c:set var="type" value="${dinnerTypeIdMap[dinner.type].name}"></c:set>
                                <td align="left"> ${type} </td>
                                  <td align="left">${dinner.address}</td>
                               
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">
                                没有用餐信息.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	        </tbody>
	    </table>
	</div>

</body>
</html>