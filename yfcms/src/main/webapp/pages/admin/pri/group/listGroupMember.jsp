<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>分组计划管理</title>
	<link rel="stylesheet" href="${ctx}/css/dropdown_style.css" type="text/css" media="screen, projection"/>
    <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="${ctx}/css/dropdown_ie.css" media="screen" />
    <![endif]-->
    
	${admin_css}                                   
	${jquery_js}
	
	<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.dropdownPlain.js"></script>
</head>
<body>
	<div class="page_title">
		<h3>分组模版管理 -- ${groupPlan.planName} -- ${CURRENT_MEETING}</h3>
	</div>
	
 	<div class="neidi" >
 		<a href="${ctx}/admin/pri/group/list.action?meetingId=${meetingId}">分组模版</a> > <a href="${ctx}/admin/pri/group/listGroup.action?groupPlan.id=${groupPlan.id}&meetingId=${meetingId}">${groupPlan.planName}</a> > ${groupPlanDetail.groupName}
 	</div>
   	<div style="margin: 10px;" align="left">
      	<a href="javascript:chooseGroupMember('${groupPlanDetail.id }');" class="btn_common btn_false">添加成员</a>
      	<a href="javascript:returnList('${meetingId }');" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
    </div>
	
	
		   <c:set var="listSize" value="${fn:length(userList)}"></c:set>
			<table class="page_datalist" style="margin-bottom: 50px;">
		    	<thead>
		        	<tr>
		        		<th width="1px">&nbsp;</th>
		            	<th colspan="4">组名：${groupPlanDetail.groupName }&nbsp;&nbsp;&nbsp;总计:${listSize }人</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:if test="${not empty groupPlanDetail.detail }">
			        	<tr>
			        		<td width="1px">&nbsp;</td>
			            	<td colspan="4">${groupPlanDetail.detail }</td>
			            </tr>
		            </c:if>
                    
                    <c:forEach var="user" items="${userList}" varStatus="status">
                        <c:if test="${status.first || (status.index+4)%4 == 0}">
	                        <tr  <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if> >
                            <td></td>
                        </c:if>
                            <td align="left" width="180px">
                            <ul class="dropdown">
					        	<li>序号：${status.index+1 }&nbsp;&nbsp;<a href="#">${user.name }[${user.mobile}]</a>
					        		<ul class="sub_menu">
					        			 <c:if test="${!status.first}"><li><a href="javascript:void(0);" onclick="sortMember('${user.id}','first');">排到最前</a></li>
					        			 <li><a href="javascript:void(0);" onclick="sortMember('${user.id}','pre');">往前排</a></li></c:if>
					        			 <c:if test="${not empty groupList}">
						        			 <li>
						        				<a href="javascript:void(0);">移到其他组</a>
						        				<ul>
						        				<c:forEach var="groupTemp" items="${groupList}" >
						        					<c:if test="${groupPlanDetail.id != groupTemp.id}"><li><a href="javascript:void(0);" onclick="moveMember('${user.id}','${groupTemp.id}');">${groupTemp.groupName}</a></li></c:if>
							                    </c:forEach>
						        				</ul>
						        			 </li>
					        			 </c:if>
					        			 <c:if test="${!status.last}"><li><a href="javascript:void(0);" onclick="sortMember('${user.id}','next');">往后排</a></li>
					        			 <li><a href="javascript:void(0);" onclick="sortMember('${user.id}','last');">排到最后</a></li></c:if>
					        			 <li><a href="javascript:void(0);" onclick="removeMember('${user.id}');">移出该组</a></li>
					        		</ul>
					        	</li>        	
					        </ul>
                            </td>
                        
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
   
	<script type="text/javascript">
		function returnList(meetingId){
			window.location.href = "${ctx}/admin/pri/group/listGroup.action?groupPlan.id="+${groupPlan.id}+"&meetingId="+meetingId;
		}
		function chooseGroupMember(groupId){
			var url= "${ctx}/admin/pri/group/listMemberNotInGroup.action?groupPlan.id=${groupPlan.id}&groupPlanDetail.id=${groupPlanDetail.id}";
	    	window.open(url,'','width=800,height=500,left=200,top=200,scrollbars=yes,location=no');
		}
		
		function removeMember(userId){
			if(confirm("确定将该用户移出该组吗?")){
				var url ="${ctx}/admin/pri/group/removeMemberFromGroup.action";
				$.ajax({
					url: url,
					data:{"userId":userId,"groupPlanDetail.id":${groupPlanDetail.id},"groupPlan.id":${groupPlan.id}},
			        type:      'post',
			        dataType:  'json',
			        success:   callback
				});
			}
		}
		
		function callback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){
				window.location.href = '${ctx}/admin/pri/group/listGroupMember.action?&groupPlanDetail.id=${groupPlanDetail.id}&groupPlan.id=${groupPlan.id}';
			}
		}
		
		function sortMember(userId,operator){
			var url ="${ctx}/admin/pri/group/sortMemberForGroup.action";
			$.ajax({
				url: url,
				data:{"userId":userId,"operator":operator,"groupPlanDetail.id":${groupPlanDetail.id},"groupPlan.id":${groupPlan.id}},
		        type:      'post',
		        dataType:  'json',
		        success:   callback
			});
		}
		
		function moveMember(userId,otherGroupId){
			var url ="${ctx}/admin/pri/group/moveMemberFromGroup.action";
			$.ajax({
				url: url,
				data:{"userId":userId,"otherGroupId":otherGroupId,"groupPlanDetail.id":${groupPlanDetail.id},"groupPlan.id":${groupPlan.id}},
		        type:      'post',
		        dataType:  'json',
		        success:   callback
			});
		}
	</script>
</body>
</html>