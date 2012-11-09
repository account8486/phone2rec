<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>在线用户管理</title>
	<link href="${ctx}/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
	${style_css}                                   
	${jquery_js}
</head>
<body>
<div class="container"> 
	<%@ include file="/pages/common/admin_header.jsp" %> 
	<div class="mainbox">
		<div id="main_content">
			<div class=crumbs2>在线用户管理</div>
			<form id="listUserForm" action="${ctx}/admin/pri/user/onlineList.action">
				<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
				<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}" />
			</form>
			<br/>
			<table class=grid>
                <thead>
                <tr height="25px">
                    <th width="10"><input id="checkAll" type="checkbox" onclick="if($(this).attr('checked')==true){$(':checkbox').attr('checked',true)}else {$(':checkbox').attr('checked',false)}"/></th>
                    <th width="150">手机号码</th>
                    <th width="100">姓名</th>
                    <th width="200">机构</th>
                    <th width="100">状态</th>
                    <th width="120">修改时间</th>
                    <th width="" colspan="3">操作</th>
                </tr>
                </thead>
                <c:choose>
                    <c:when test="${not empty onlineList}">
                        <c:forEach var="user" items="${onlineList}" varStatus="status">
                            <tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
                                <td align="center"><input type="checkbox" id="check_${user.id }"
                                                          name="userIds" value="${user.id }"/></td>
                                <td align="left">${user.mobile }</td>
                                <td align="left">${user.name }</td>
                                <td align="left">${user.org.name }</td>
                                <td align="left">有效</td>
                                <td align="left"><fmt:formatDate value="${user.modifyTime}"
                                                                 type="both"
                                                                 pattern="MM月d日 HH:mm"/></td>
                                <td align="center"><a href="${ctx}/admin/pri/user/forceOut.action?id=${user.id}">注销</a></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">
                                没有在线用户信息.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
      		</table>
            <br/>
		</div>
	</div>		
	<%@ include file="/pages/common/footer.html" %> 
</div>
	
<script type="text/javascript">
	
	function pageSkip(value){
		var page = document.getElementById('currentPage');
		page.value = value;
		$('#listUserForm').submit();
	} 	
	
    function jumpTo(){
    	var jumpPage = document.getElementById('jumpPage').value;
		
    	if(jumpPage==null||jumpPage.length==0) {
    		alert('请输入有效的页码');
    		return;
    	}
    	for(var i=0; i < jumpPage.length; i++) {
    		var eachChar = escape(jumpPage.charAt(i));
    		if(eachChar > '9' || eachChar < '0') {
    			alert('请输入有效的页码');
    			return;
    		}
    	}
    	var value=jumpPage;
    	if((parseInt(jumpPage)<1)||
    		(parseInt(jumpPage)>parseInt(document.getElementById('totalPage').value))){
    		value = 1;
    	}
    	pageSkip(value);
    }
</script>
</body>
</html>