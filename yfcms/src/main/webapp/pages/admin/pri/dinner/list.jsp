<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用餐管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}
    ${WdatePicker_js}
</head>
<body>
	<div class="page_title">
		<h3>用餐管理  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="page_tools page_serach">
		<form id="queryForm" action="${ctx}/admin/pri/meeting/listMeetingDinner.action">
		<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
		<table width="80%">
	      <tr>
	        <th style="width: 50px; ">日期：</th>
	        <td style="width: 150px; "><input type="text" style="width: 120px; " id="dinnerDate" name="dinnerDate"
                                   value="${dinnerDate}" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:true,readOnly:true})" readonly="readonly"/></td>
	        <th style="width: 60px; ">用餐地点：</th>
	        <td style="width: 150px; "><input type="text" style="width: 120px; " id="address" name="address"
                                   value="${address}" maxlength="50"/></td>
	        <td><a href="#" id="queryForList" onclick="query();return false;" class="btn_common btn_true">搜 索</a></td>
	      </tr>
	    </table>
	</form>
	</div>
	
	<div class="page_tools page_toolbar">
		<a href="javascript:void();"  onclick="gotoAdd();return false;" class="btn_common btn_false">添加用餐</a>
		<a href="javascript:void();"  onclick="gotoImport();return false;" class="btn_common btn_false">导入</a>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	        		<th width="2">&nbsp;</th>
	            	<th >日&nbsp;&nbsp;期</th>
                    <th >地&nbsp;&nbsp;点</th>
                    <th >用餐时段</th>
                    <th >时&nbsp;&nbsp;间</th>
                    <th >类&nbsp;&nbsp;型</th>
                    <th >备&nbsp;&nbsp;注</th>
                    <th >操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:choose>
                    <c:when test="${not empty dinnerList}">
                        <c:forEach var="dinner" items="${dinnerList}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if> >
                                <td></td>
                                <td align="left">${dinner.dinnerDate }</td>
                                <td align="left">${dinner.address}</td>
                                
                                <c:set var="section" value="${1 == dinner.section ? '早':2==dinner.section?'中':'晚'}"></c:set>
                                <td align="left">${section}餐</td>
                                
                                <td align="left">${dinner.startTime }-${dinner.endTime }</td>
                                
                                <c:set var="type" value="${dinnerTypeIdMap[dinner.type].name}"></c:set>
                                <td align="left"> ${type} </td>
                                <td align="left"> ${dinner.comments} </td>
                                
                                <td align="center">
                                	<c:if test="${!dinnerTypeIdMap[dinner.type].choose}">分组&nbsp;</c:if>
                                	<c:if test="${dinnerTypeIdMap[dinner.type].choose}">
                                		<a href="#" onclick="chooseTable('${dinner.id}');">分组 </a>
                                	</c:if>
	                                <a href="#" onclick="modify('${dinner.id}');">编辑 </a>
                                	<a href="#" onclick="del('${dinner.id}');">删除</a>
                                </td>
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
<script type="text/javascript">
	function gotoAdd(){
		window.location.href = "${ctx}/admin/pri/meeting/gotoAdd.action?meetingId=${meetingId}";
	}
	function modify(id){
		window.location.href = "${ctx}/admin/pri/meeting/getMeetingDinnerById.action?returnType=modify&dinner.id="+id;
	}
	function chooseTable(id){
		window.location.href = "${ctx}/admin/pri/meeting/dinnerChoose.action?dinner.id="+id;
	}
	function importTable(id){
		window.location.href = "${ctx}/admin/pri/meeting/getMeetingDinnerById.action?returnType=import&dinner.id="+id;
	}
	function gotoImport(id){
		window.location.href = "${ctx}/pages/admin/pri/dinner/importPlan.jsp?meetingId=${meetingId}";
	}
	function del(id){
		if(confirm("确定要删除选择的信息吗?")){
			doDelete(id);
		}
	}
	
	function doDelete(id){
		var url = '${ctx}/admin/pri/meeting/deleteMeetingDinner.action?dinner.id='+id;
		
		$.getJSON(url, callback);
	}
	//回调函数
	function callback(data){
		alert(data.retmsg);
		if(data.retcode == "0"){
			returnList();
		}
	}
	
	function returnList(){
		window.location.href = "${ctx}/admin/pri/meeting/listMeetingDinner.action?meetingId=${meetingId}";
	}
	//按条件搜索
	function query() {
        $('#queryForm').submit();
    }
</script>
</body>
</html>