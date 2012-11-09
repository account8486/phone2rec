<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${not empty saveMsgTips}">
			alert(${saveMsgTips});
			</c:if>
			$(".easyui-tabs").tabs({  
				onSelect:function(title){  
					var tab = $(this).tabs("getTab", title); 
					var href = tab.attr("link");
					if (href) {
						location.href = href;
						showLoading(title);
						return false;
					}
				}  
			});
			$("#all_check").change(function () {
				if (this.checked) {
					$("[name='userId']").attr("checked", $("#all_check").attr("checked"));
				} else {
					$("[name='userId']").removeAttr("checked");
				}
			});
        
			//有一个不选上则全不选
			$('input[type="checkbox"][name="userId"]').click(function () {
				   var ckall = true;
				   $('input[type="checkbox"][name="userId"]').each(function (){
					if (!this.checked){ 
						ckall = false;  
						//直接退出循环,不在进行each循环
						return false; 
						}});
				   
				   $('input[type="checkbox"][name="all_check"]').attr('checked', ckall);
		   });		
			
			
		   $("#exportUser").click(function(){
			   exportMeetingUser();
		   });
			
		});
    
    function batchDelete(){
        var retString = "";
        var meetingId=${meeting.id};
        var checks = document.getElementsByName("userId");
        if (checks) {
            for (var i = 0; i < checks.length; i++) {
                var chkObj = checks[i];
                if (chkObj.checked)
                    retString += chkObj.value + ",";
            }
        }
        
        if(retString==""){
        	alert("请选择你要删除的用户!");
        	return;
        }
		//alert(retString);
		//alert(meetingId);
		if(confirm("你确定要批量删除用户吗?")){
			var url="${ctx}/admin/pri/meeting/batchDelMeetingUser.action?meeting.id="+meetingId+"&ids="+retString;			
			this.location=url;
		}
		
    }
    </script>
</head>
<body>
<div class="page_title"><h3>参会人员管理  -- ${CURRENT_MEETING}</h3></div>
<div class="easyui-tabs" border="false" style="padding:10px;">	
	<div title="用户列表" style="padding:10px;">
		<div class="page_tools">
		<form id="listUserForm" action="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action">
			<input type="hidden" id="meeting.id" name="meeting.id" value="${meeting.id}"/>
			<input type="hidden" id="isAdmin" name="isAdmin" value="1"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table width="90%">
				<tr>
				<th style="width: 60px; ">手机号码：</th>
				<td style="width: 120px; "><input type="text" style="width: 120px; " id="user.mobile" name="mobile" value="${mobile}"/></td>
				<th style="width: 50px; ">姓 名：</th>
				<td style="width: 120px; "><input type="text" style="width: 120px; " id="user.name" name="username" value="${username}"/></td>
				
				<th style="width: 40px; ">级别：</th>
				<td style="width: 40px; ">
             <select id="memberLevel" name="memberLevel">
                <option value=""></option>
                <c:forEach begin="1" end="5" step="1" varStatus="status" >
                <option value="${status.count}" <c:if test="${status.count eq memberLevel}"> selected="selected"</c:if>>${status.count}级</option>
                </c:forEach>
             </select>
           </td>
           <td style="width: 300px; ">
					<a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a>
					<a href="#" onClick="batchDelete()" class="btn_common btn_false">批量删除</a> 
					<a href="#" id="exportUser" class="btn_common btn_false">导出用户</a> 
				</td>
				
				</tr>
			</table>
		</form>
		</div>
	
		<div>
		<table class="page_datalist">
		<thead>
			<tr>
				<th width="3" style="border-right: 0"><input type="checkbox" name="all_check" id="all_check"></input>  </th>
				<th width="30">序号 </th>
				<th width="60">姓名</th>
				<th width="30">性别</th>
				<th width="90">手机号码 </th>
				<th width="90">职务</th>
				<th width="50">房间号</th>
				<th width="60">参会级别</th>
				<th width="30">排序</th>
				 <th width="50">通讯录</th>
				<th width="80">显示手机号码</th>                   
				<th width="60">是否签到</th>
				<th width="80">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr> 
				<c:choose>
				<c:when test="${not empty pager.pageRecords}">
					<c:forEach var="user" items="${pager.pageRecords}" varStatus="status">
						<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
							<td><input type="checkbox" name="userId" value="${user.id}"></input></td>
							<td>${(pager.currentPage-1) * pager.pageSize + status.index + 1}</td>
							<td>${user.name }</td>
							<td>${user.gender == 0 ? "男":(user.gender == 1 ? "女":"保密") }</td>
							<td><a href="##" onclick="showUser(${user.id })">${user.mobile }</a></td>
							<td>${user.meetingMember.bookJob }</td>
							<td>${user.meetingMember.roomNumber}</td>
							<td>${user.meetingMember.memberLevel }</td>
							<td>${user.meetingMember.sortCode }</td>
							<td>
							<c:choose>
							<c:when test="${user.meetingMember.addInContacts eq 'N'}"><font style="color:red;">不加入</font></c:when>
							<c:when test="${user.meetingMember.addInContacts eq 'Y'}">加入</c:when>
							<c:otherwise><font style="color:red;">未设定</font>	</c:otherwise>
							</c:choose>
							</td> 
							<td >
								<c:choose>
									<c:when test="${user.meetingMember.mobileIsDisplay eq 1}">显示</c:when>
									<c:otherwise><font style="color:red;">不显示</font></c:otherwise>
								</c:choose>
							</td>
							<td >
								<c:choose>
									<c:when test="${user.isSigned eq 1}"><font style="color:green;">已签到</font></c:when>
									<c:otherwise>未签到</c:otherwise>
								</c:choose>
							</td>
							<td>
								<a href="##" onclick="editUser(${user.id })">编辑</a>
								&nbsp;
								<a href="##" onclick="delUser(${user.id })">删除</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="datarow"><td colspan="11" align="center"> 无用户信息.</td></tr>
				</c:otherwise>
			</c:choose>
			</tr>
		</tbody>
	</table>
	<%@ include file="/pages/common/page.jsp" %> 
	</div>
	</div>
	<div title="批量添加" link="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=batch_add_members&meeting.id=${meeting.id}" style="padding:10px;"></div>
	<div title="添加用户" link="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_members&meeting.id=${meeting.id}" style="padding:10px;"></div>
	<div title="导入用户" link="${ctx}/admin/pri/import/preImportUser.action?meetingId=${meeting.id}" style="padding:10px;"></div>
	<div title="临时加入审批" link="${ctx}/admin/pri/apply/getUserApplyPager.action?meetingId=${meeting.id}" style="padding:10px;"></div>	
	<div title="发言人管理" link="${ctx}/admin/pri/spokesman/getSpokesManLst.action?meetingId=${meeting.id}" style="padding:10px;"></div>
	<div title="嘉宾管理" link="${ctx}/admin/pri/guest/guest_findAllGuest.action?meetingId=${meeting.id}" style="padding:10px;"></div>
</div>

<script type="text/javascript">
	function delUser(userId)
	{
		if(confirm("您确认要删除该用户吗？"))
		{
			window.location.href  = "${ctx}/pages/admin/pri/meeting/deleteMeetingUser.action?meeting.id=${meeting.id}&userid="+userId;
		}
	}
	
	function editUser(userId){
		window.location.href  = "${ctx}/pages/admin/pri/meeting/getMeetingUserById.action?meeting.id=${meeting.id}&userId="+userId;
	}
	
	function showUser(userId){
		window.location.href  = "${ctx}/pages/admin/pri/meeting/getMeetingUserById.action?&show=show&meeting.id=${meeting.id}&userId="+userId;
	}
	
    function forwardReq(pagePath) {
        window.location.href = pagePath;
    }

    function query() {
        $('#listUserForm').submit();
    }
    
 
    function exportMeetingUser(){
    	if(confirm('确定要导出会议用户吗？')){
    		 var url="${ctx}/admin/pri/export/exportMeetingUser.action?isAdmin=1&meetingId=${meeting.id}";
        	 var name=document.getElementById("user.name").value;
        	 var mobile=document.getElementById("user.mobile").value;
        	 url=url+"&username="+name;
        	 url=url+"&mobile="+mobile;
        	 
        	 document.location.href=url;
    	} 
    	}
</script>
</body>
</html>