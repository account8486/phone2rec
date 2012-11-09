<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>
    <div class="article">    
	

	
		<ul class="meeting_list">
			<c:forEach var="JourneyView" items="${pager.pageRecords}" varStatus="status">
        	<li>
            	<h5>${JourneyView.name}
            	</h5>
                <p>
                	司机：${JourneyView.driverName }
                </p>
                <p>
                	车牌号：${JourneyView.vehicleNumber}
                </p>
                <p>
                	车辆品牌：${JourneyView.vehicleName} (${JourneyView.vehicleType})
                </p>
                <p>开始时间：${fn:substring(JourneyView.startTime,0,16)} </p>
                <p>开始地点：${JourneyView.departure} </p>
                <p>结束时间：${fn:substring(JourneyView.startTime,0,16)} </p>
                <p>结束地点：${JourneyView.destination}</p>
                <p>人员:${JourneyView.journeyMembers}</p>
            
            </li>
			</c:forEach>
        </ul>
   
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>