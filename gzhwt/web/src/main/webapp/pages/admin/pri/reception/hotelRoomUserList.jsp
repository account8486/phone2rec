<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>客房住客管理</title>
	<link rel="stylesheet" href="${ctx}/css/dropdown_hotel.css" type="text/css" media="screen, projection"/>
    <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="${ctx}/css/dropdown_ie.css" media="screen" />
    <![endif]-->
	${admin_css} ${jquery_js}
	<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.dropdownPlain.js"></script>
</head>
<body>
	<c:set var="listSize" value="${fn:length(roomList)}"></c:set>

	<div class="page_title"><h3>客房住客管理 -- ${CURRENT_MEETING}</h3></div>
	
 	<div class="grid_header">
		<table style="width:100%;"><tr>
			<td style="text-align:right;"><a href="javascript:returnList();" id="retBtn" class="btn_common btn_false">返回列表</a></td>
		</tr></table>
 	</div>
	
	<div class="grid">
		<table style="width:100%;">
		<c:forEach var="record" items="${roomList}" varStatus="status">
		<c:if test="${status.count % 6 eq 1}"><tr></c:if>
		<td class="grid_row">
			<div class="hotel_room">
			<table><tr>
			<c:forEach var="user" items="${record.users}" varStatus="sta">
	            <td><ul class="dropdown"><li>
					<div class="grid_item_image">
						<c:if test="${user.gender eq 1}"><img src="${ctx}/images/female.png" title="${user.name }[${user.mobile}]"/></c:if>
						<c:if test="${user.gender ne 1}"><img src="${ctx}/images/male.png" title="${user.name }[${user.mobile}]"/></c:if>
					</div>
					<div class="grid_item">${fn:substring(user.name,0,8)}</div>
					<c:if test="${status.count % 6 eq 0}"><ul class="sub_menu lastitem"></c:if>
					<c:if test="${status.count % 6 ne 0}"><ul class="sub_menu"></c:if>
						<li>&nbsp;&nbsp;<b>序号：${status.count }</b></li>
						<li>
							<a href="javascript:void(0);">移到其他房间</a>
							<ul>
							<c:forEach var="roomTemp" items="${roomList}" >
								<c:if test="${record.id != roomTemp.id}"><li><a href="javascript:void(0);" onclick="addUserToRoom('${user.id}','${record.id}','${roomTemp.id}');">${roomTemp.roomNo}(${fn:substring(roomTemp.hotelRoomType.name, 0, 4) })</a></li></c:if>
							</c:forEach>
							</ul>
						 </li>
						<li><a href="javascript:void(0);" onclick="removeUserFromRoom('${user.id}','${record.id}');">移出该房间</a></li>
					</ul>
				</li></ul></td>
				<c:if test="${sta.last && sta.count eq 1}">
					<td><ul class="dropdown"><li><div class="grid_item_image"></div></li></ul></td>
				</c:if>
			</c:forEach>
			</tr>
			<tr><td colspan="2"><div class="grid_item" style="line-height:35px;">${record.roomNo }(${fn:substring(record.hotelRoomType.name, 0, 4) })</div></td></tr>
			</table></div>
		</td>
		<c:if test="${status.count % 6 eq 0}"></tr></c:if>
		<c:if test="${status.last}">
			<c:if test="${listSize % 6 ne 0}">
				<c:set var="blankCount" value="${6 - (listSize % 6)}"></c:set>
				<c:forEach begin="0" end="${blankCount-1}" step="1">
					<td class="grid_row">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</c:forEach>
			</c:if>
			</tr>
		</c:if>		
		</c:forEach>
		</table>
		<!-- 未分配客房用户列表 -->
		<table style="width:100%;background-color:#FFDEAD;">
		<c:if test="${not empty unAssignedUserList}">
			<tr><td colspan="12"><div class="grid_item" style="line-height:35px;">未分配客房参会人员</div></td></tr>
		</c:if>
		<c:forEach var="record" items="${unAssignedUserList}" varStatus="status">
		<c:if test="${status.count % 12 eq 1}"><tr></c:if>
		<td class="grid_row">
			<ul class="dropdown">
				<li>
					<div class="grid_user_image">
						<c:if test="${record.user.gender eq 1}"><img src="${ctx}/images/female.png" title="${record.user.name }[${record.user.mobile}]"/></c:if>
						<c:if test="${record.user.gender ne 1}"><img src="${ctx}/images/male.png" title="${record.user.name }[${record.user.mobile}]"/></c:if>
					</div>
					<div class="grid_item">${fn:substring(record.user.name,0,8)}</div>
					<c:if test="${status.count % 12 eq 0}"><ul class="sub_menu lastitem"></c:if>
					<c:if test="${status.count % 12 ne 0}"><ul class="sub_menu"></c:if>
						<li>&nbsp;&nbsp;<b>序号：${status.count }</b></li>
						<li>
							<a href="javascript:void(0);">移到房间</a>
							<ul>
							<c:forEach var="roomTemp" items="${roomList}" >
								<li><a href="javascript:void(0);" onclick="addUserToRoom('${record.userId}','','${roomTemp.id}');">${roomTemp.roomNo}(${fn:substring(roomTemp.hotelRoomType.name, 0, 4) })</a></li>
							</c:forEach>
							</ul>
						 </li>
					</ul>
				</li>
			</ul>
		</td>
		<c:if test="${status.count % 12 eq 0}"></tr></c:if>
		<c:if test="${status.last}">
			<c:if test="${fn:length(unAssignedUserList) % 12 ne 0}">
				<c:set var="blankCount" value="${12 - (fn:length(unAssignedUserList) % 12)}"></c:set>
				<c:forEach begin="0" end="${blankCount-1}" step="1">
					<td class="grid_row">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</c:forEach>
			</c:if>
			</tr>
		</c:if>		
		</c:forEach>
		</table>	
	</div>
   
<script type="text/javascript">
	// 返回列表
	function returnList(){
		window.location.href = "${ctx}/admin/pri/room/list.action?meetingId=${meetingId}";
	}
	
	function callback(data){
		alert(data.retmsg);
		if(data.retcode == "0"){
			window.location.href = '${ctx}/admin/pri/room/roomUserList.action?meetingId=${meetingId}';
		}
	}
	
	// 移出房间
	function removeUserFromRoom(userId,roomId){
		if(confirm("确定将该用户移出该房间吗?")){
			var url ="${ctx}/admin/pri/room/removeUserFromRoom.action";
			$.ajax({
				url: url,
				data:{"userId":userId, "roomId":roomId},
		        type:      'post',
		        dataType:  'json',
		        success:   callback
			});
		}
	}
	
	// 移入房间
	function addUserToRoom(userId, fromRoomId, toRoomId){
		if(confirm("确定将该用户移入该房间吗?")){
			var url ="${ctx}/admin/pri/room/addUserToRoom.action";
			$.ajax({
				url: url,
				data:{"userId":userId, "fromRoomId":fromRoomId, "toRoomId":toRoomId},
		        type:      'post',
		        dataType:  'json',
		        success:   callback
			});
		}
	}
</script>
</body>
</html>