<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>会议云平台</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>	
    <script type="text/javascript">
	$(document).ready(function(){
		var validateQuery = function () {
	    	var mobile = $("#mobile").val();

	       	if (!isEmpty(mobile)) {
	       		var bool = /^[0-9]{0,11}$/.test(mobile);
		       	if (bool == false) {
		           $("#tip_mobile").html("请输入正确的手机号码。");
		           $("#tip_mobile").show();
		           return false;
		       	}
	       	}	
	       	return true;
	    };
	
		$("#queryForList").click(function(){
			$("[id^='tip_']").hide();
			
			var tmp_bool = validateQuery();
			if (tmp_bool != true) {
				return false;
			}
			
			$("#mainForm").submit();
		});
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
		$(document).keypress(function(e){
			if(e.which == 13 ) {
				var act = document.activeElement.id;
				switch(act){
					case 'mobile':
					case 'userName':$('#queryForList').click();break;
					case 'jumpPage':jumpTo();break;
				}
			} 
		});
	});
	</script>
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>统计查询 -- 用户登录统计</h3>
	</div>

	<div class="easyui-tabs" border="false" style="padding:10px;">	
		<div title="访问汇总" link="${ctx}/pages/admin/pri/meeting/access.jsp" style="padding:10px;"></div>
		<div title="访问明细" link="${ctx}/pages/admin/pri/meeting/accessDetail.jsp" style="padding:10px;"></div>
		<div title="互动交流" link="${ctx}/pages/admin/pri/statistics/interaction.jsp" style="padding:10px;"></div>
		<div title="短信统计" link="${ctx}/pages/admin/pri/statistics/sms_statistics.jsp" style="padding:10px;"></div>
		<div title="用户登录" selected="true" style="padding:10px;">
		<div class="page_tools page_serach">
		<form id="mainForm" action="${ctx}/admin/pri/user/loginList.action">
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table width="98%">
			  <tr>
				<th>手机号码：</th>
				<td><input type="text" id="mobile" name="user.mobile"
									   value="${user.mobile }" maxlength="11"/>
				<font id="tip_mobile" style="display: none;" color="red"></font></td>
				<th>用户名称：</th>
				<td><input type="text" id="userName" name="user.name"
									   value="${user.name }"/>
				<font id="tip_userName" style="display: none;" color="red"></font></td>
				<td><a href="#" id="queryForList" class="btn_common btn_true">搜 索</a></td>
			  </tr>
			</table>
		</form>
		</div>
		<div>
			<table class="page_datalist">
				<thead>
					<tr>
						<th width="2px" style="border-right:0"></th>
						<th width="80">手机号码</th>
						<th width="80" >姓名</th>
						<th width="150" >登录时间</th>
						<th width="80" >登录方式</th>
						<th width="240" >登录IP</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<c:choose>
						<c:when test="${not empty pager.pageRecords}">
							<c:forEach var="loginLog" items="${pager.pageRecords}" varStatus="status">
							<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
								<td ></td>
								<td>${loginLog.user.mobile }</td>
								<td>${loginLog.user.name }</td>
								<td>${loginLog.loginTime }</td>
								<td>
									<c:choose>
										<c:when test="${loginLog.portalType eq 1}">web</c:when>
										<c:when test="${loginLog.portalType eq 2}">wap</c:when>
										<c:when test="${loginLog.portalType eq 3}">客户端</c:when>
										<c:otherwise> </c:otherwise>
									</c:choose>
								</td>
								<td>${loginLog.loginIp }</td>
							</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="datarow">
								<td colspan="6">&nbsp;&nbsp;&nbsp;没有用户登录信息.</td>
							</tr>
						</c:otherwise>
					</c:choose>
					</tr>
				</tbody>
			</table>
			<%@ include file="/pages/common/page.jsp" %>
		</div>
		</div>
	</div>
</body>
</html>