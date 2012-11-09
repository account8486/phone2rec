<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8"/>
<meta http-equiv="Cache-control" content="no-cache" />
<title>会务通平台</title>
<link href="${ctx}/css/wap.css" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="${ctx}/css/gift.css" />
<c:set var="meeting_list_url" value="<a href='${ctx}/wap/pri/meeting/getAttendMeetingList.action'>会议列表</a>"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
</head>
<body>
<div class="main">
	<div class="path">
		<div>
			<div class="pic"><img style="width:120px;height:177px;border:0px;" src="${serverUrl}${gift.imgUrl }" /></div>
		</div>
		
		<div>
			<div class="description">
				<div class="comments">${gift.name }</div>
				<div class="price" style="margin-top:5px;"><span class="money">￥<fmt:formatNumber value="${gift.price }" type="currency" pattern="#0.00元"/></span></div>
			</div>
			
			<div>
			<form id="mainForm" action="${ctx}/client/pri/gift/order.action">
				<input type="hidden" id="giftId" name="gift.id" value="${gift.id}"/>
				<input type="hidden" id="mId" name="gift.meetingId" value="${gift.meetingId}"/>
				<input type="hidden" id="giftPrice" name="gift.price" value="${gift.price}"/>
				数量&nbsp;<input type="text" id="amount" name="amount" style="width:40px;" value="${amount}" maxlength="6"/>&nbsp;件
				<font style="line-height:35px" color="red">${errMsg }</font>
				<br/>
				<a style="margin-top:5px;text-decoration:none;" href="#" onclick="doOrder();" class="btn_blue" id="orderBtn">立刻订购</a>
				<a class="btn_blue" style="text-decoration:none;" href="${ctx}/client/pri/gift/list.action">返回列表</a>
			</form>
			</div>
			
			<div class="description" style="margin-top:5px;">
				<div class="comments">${gift.introduction }</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function doOrder(){
		document.getElementById("mainForm").submit();
		document.getElementById("submitBtn").attr("disabled","disabled");
	}
</script>
</body>
</html>