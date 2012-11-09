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
		<h3>分组模版管理  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="page_tools page_serach">
		
	</form>
	</div>
	
	<div class="page_tools page_toolbar">
		<a href="javascript:void();"  onclick="gotoAdd();return false;" class="btn_common btn_false">添加模版</a>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	        		<th width="1%">&nbsp;</th>
	            	<th width="18%">模版名称</th>
                    <th width="20%">模版描述</th>
                    <th width="8%">类&nbsp;&nbsp;型</th>
                    <th width="27%" colspan="1">操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach var="plan" items="${list}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if> >
                                <td></td>
                                <td align="left">${plan.planName }</td>
                                <td align="left">${plan.planDesc}</td>
                                <c:set var="type" value="${plan.planType == 1 ? '议程分组':'用餐分组'}"></c:set>
                                <td align="left"> ${type} </td>
                                <td align="center">
                                	<a href="#" onclick="edit('${plan.id}','${plan.meetingId }');">编辑</a>
                                	<a href="#" onclick="listGroup('${plan.id}','${plan.meetingId }');">分组管理</a>
                                	<a href="#" onclick="show('${plan.id}','${plan.meetingId }');">详情</a>
                                	<a href="#" onclick="exportPlan('${plan.id}','${plan.meetingId }');">导出</a>
                                	<a href="#" onclick="del('${plan.id}');">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">
                                没有分组模版.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	        </tbody>
	    </table>
	</div>
<script type="text/javascript">
	function gotoAdd(){
		window.location.href = "${ctx}/pages/admin/pri/group/add.jsp?meetingId=${meetingId}";
	}
	function del(id){
		if(confirm("确定要删除选择的信息吗?")){
			doDelete(id);
		}
	}
	
	function doDelete(id){
		var url = '${ctx}/admin/pri/group/delete.action?groupPlan.id='+id;
		
		$.getJSON(url, callback);
	}
	function show(id,meetingId){
		var url = '${ctx}/admin/pri/group/show.action?groupPlan.id='+id+"&meetingId="+meetingId;
		window.location.href = url;
	}
	function exportPlan(id,meetingId){
		var url = '${ctx}/admin/pri/group/exportGroupPlan.action?groupPlan.id='+id+"&meetingId="+meetingId;
		window.location.href = url;
	}
	function listGroup(id,meetingId){
		var url = '${ctx}/admin/pri/group/listGroup.action?groupPlan.id='+id+"&meetingId="+meetingId;
		window.location.href = url;
	}
	function edit(id,meetingId){
		var url = '${ctx}/admin/pri/group/gotoModify.action?&groupPlan.id='+id+"&meetingId="+meetingId;
		window.location.href = url;
	}
	//回调函数
	function callback(data){
		alert(data.retmsg);
		if(data.retcode == "0"){
			returnList();
		}
	}
	
	function returnList(){
		window.location.href = "${ctx}/admin/pri/group/list.action?meetingId=${meetingId}";
	}
	
</script>
</body>
</html>