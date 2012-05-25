<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>分组模版管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}
</head>
<body>
<%
	String retMsg=(String)request.getAttribute("retMsg");
	if(retMsg!=null&&!"".equals(retMsg)){
%>
	<script type="text/javascript">
	alert('<%=retMsg%>');
	</script>
<%
	}
%>
	<div class="page_title">
		<h3>分组模版管理 -- ${groupPlan.planName }  -- ${CURRENT_MEETING}</h3>
	</div>

	<div class="neidi" >
 		<a href="${ctx}/admin/pri/group/list.action?meetingId=${meetingId}&groupPlan.id=${groupPlan.meetingId}">分组模版</a> > ${groupPlan.planName}
 	</div>
	<!--<div class="page_tools page_serach">
	</div>-->
	<div class="page_tools page_toolbar">
		<a href="javascript:void();"  onclick="gotoAdd();return false;" class="btn_common btn_false">添加分组</a><a href="javascript:returnList('${meetingId }');" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	        		<th width="2px">&nbsp;</th>
	            	<th  >组名</th>
	            	<th  >排序</th>
                    <th width="50%">详细内容</th>
                    <th width="130px">操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:choose>
                    <c:when test="${not empty groupList}">
                        <c:forEach var="group" items="${groupList}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if> >
                                <td></td>
                                <td align="left">${group.groupName }</td>
                                <td align="left">${group.showIndex}</td>
                                <td align="left">${group.detail}</td>
                                <td align="center">
                                	<a href="#" onclick="edit('${group.id}','${group.meetingId }');">编辑</a>
                                	<a href="#" onclick="listGroupMember('${group.planId}','${group.id}');">成员管理</a>
                                	<a href="#" onclick="del('${group.planId}','${group.id}');">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">
                                没有分组信息.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	        </tbody>
	    </table>
	</div>
<script type="text/javascript">
	function gotoAdd(){
		window.location.href = "${ctx}/pages/admin/pri/group/addGroup.jsp?meetingId=${meetingId}&planId=${groupPlan.id}";
	}
	function del(planId,groupId){
		if(confirm("确定要删除选择的信息吗?")){
			doDelete(planId,groupId);
		}
	}
	
	function doDelete(planId,groupId){
		var url = '${ctx}/admin/pri/group/deleteGroup.action?groupPlan.id='+planId+'&groupPlanDetail.id='+groupId;
		window.location.href = url;
	}
	
	function listGroupMember(planId,groupId){
		var url = '${ctx}/admin/pri/group/listGroupMember.action?&groupPlanDetail.id='+groupId+'&groupPlan.id='+planId;
		window.location.href = url;
	}
	
	function edit(id,meetingId){
		var url = '${ctx}/admin/pri/group/gotoModifyGroup.action?&groupPlanDetail.id='+id+"&meetingId="+meetingId;
		window.location.href = url;
	}
	
	function returnList(){
		window.location.href = "${ctx}/admin/pri/group/list.action?meetingId=${meetingId}";
	}
	
</script>
</body>
</html>