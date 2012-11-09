<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>
<link type="text/css" rel="stylesheet" href="${ctx}/css/gift.css" />
<div class="main">
	<div class="path">
	${meeting_list_url} > ${param.menu_name}详情
    </div>
	
	<div class="path">
		<div>
			<div class="pic"><img width="180px" height="177px" src="${serverUrl}${gift.imgUrl }" /></div>
		</div>
		
		<div>
			<div class="description">
				<div class="comments">${gift.name }</div>
				<div class="price" style="margin-top:5px;"><span class="money">￥<fmt:formatNumber value="${gift.price }" type="currency" pattern="#0.00元"/></span></div>
			</div>
			
			<div>
			<form id="mainForm" action="${ctx}/wap/pri/gift/order.action">
				<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
				<input type="hidden" id="giftId" name="gift.id" value="${gift.id}"/>
				<input type="hidden" id="giftPrice" name="gift.price" value="${gift.price}"/>
				数量&nbsp;<input type="text" id="amount" name="amount" style="width:40px;" value="${amount}" maxlength="6"/>&nbsp;件
				<font style="line-height:35px" color="red">${errMsg }</font>
				<br/>
				<input style="margin-top:5px;" type="submit" id="orderBtn" value="&nbsp;立刻订购&nbsp;"/>
				<a class="btn_blue" style="text-decoration:none;" href="${ctx}/wap/pri/gift/list.action?meetingId=${meetingId}">返回列表</a>
			</form>
			</div>
			
			<div class="description" style="margin-top:5px;">
				<div class="comments">${gift.introduction }</div>
			</div>
		</div>
	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>