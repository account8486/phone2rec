<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}
</head>
<body>
	<div class="page_tools page_serach">
	<form id="listUserForm" action="${ctx}/admin/pri/user/list.action">
		<input type="hidden" id="organId" name="organId" value="${organId}"/>
		<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		<table width="98%">
	      <tr>
	        <th>用户名：</th>
	        <td><input type="text" id="mobile" name="user.mobile"
                                   value="${user.mobile }"/></td>
	        <th>姓 名：</th>
	        <td><input type="text" id="name" name="user.name"
                                   value="${user.name}"/></td>   
                                           
            <th>用户状态：</th>
	        <td>
	        	<select name="state" id="state">
	        	<option value="1">有效</option>
	        	<option value="0">无效</option>
	        	</select>
	        </td>
	        
	        <td><a href="#" id="queryForList" class="btn_common btn_true">搜 索</a></td>
	      </tr>
	    </table>
	</form>
	</div>
	
	<div class="page_tools page_toolbar">
		<a href="#" id="goAdd" class="btn_common btn_false" style="margin-left:5px;">添加用户</a>
		<font color="red">&nbsp;&nbsp;&nbsp;${errMsg }</font>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	            	<th width="2px" style="border-right:0"></th>
	                <th width="80">用户名</th>
	                <th width="80" >姓名</th>
	                <th width="190" >组织机构</th>
                    <th width="50" >状态</th>
                    <th width="130" >修改时间</th>
                    <th width="90" >操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="user" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td ></td>
                                <td>${user.mobile }</td>
                                <td>${user.name }</td>
                                <td>${user.org.name }</td>
                                <td >
                                    <c:choose>
                                        <c:when test="${user.state eq 1}">有效
                                        </c:when>
                                        <c:otherwise>
                                            	无效 </c:otherwise>
                                    </c:choose>
                                </td>
                                <td ><fmt:formatDate value="${user.modifyTime}"
                                                                 type="both"
                                                                 pattern="MM月d日 HH:mm"/></td>
                                <td><a href="${ctx}/admin/pri/user/goUpdate.action?id=${user.id}&organId=${organId}">编辑</a>　<a href="#" onclick="delUser(${user.id })">禁用</a></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="7" align="center"> 没有用户信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>
	</div>
<script type="text/javascript">

	function delUser(userId)
	{
		if(confirm("您确认要删除该用户吗？"))
		{
			window.location.href = "${ctx}/admin/pri/user/del.action?id="+ userId + "&organId=" + $("#organId").val();
		}
	}
	
    function forwardReq(pagePath) {
        window.location.href = pagePath;
    }

	
	$("#document").ready(function(){
		$("#goAdd").click(function(){
			var url = "${ctx}/admin/pri/user/goAdd.action?organId=" +$("#organId").val();
			window.location.href = url;
		});
		
		$("#queryForList").click(function(){
			/**
			$("#list-div").load(
				"${ctx}/admin/pri/user/list.action",
				{
					//"organId":$("#organId").val(),
					"user.mobile":$("#mobile").val(),
					"user.name":$("#name").val(),
					"totalPage":$("#totalPage").val(),
					"currentPage":$("#currentPage").val()
				}
			);*/
			
			$("#listUserForm").submit();
		});
		
		$("#state").val(${state});
		
	});
	
	jQuery(document).keypress(function(e){
    	if(e.which == 13 ) {
    		var act = document.activeElement.id;
    		switch(act){
    			case 'mobile':
    			case 'name':$('#queryForList').click();break;
    			case 'jumpPage':jumpTo();break;
    		}
    	} 
    })
</script>
</body>
</html>