<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>分类</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js} ${admin_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>	
	<script type="text/javascript">
	$(document).ready(function() {
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
                $("[name='assortId']").attr("checked", $("#all_check").attr("checked"));
            } else {
                $("[name='assortId']").removeAttr("checked");
            }
        });
        //有一个不选上则全不选
        $('input[type="checkbox"][name="assortId"]').click(function () {
            var ckall = true;
            $('input[type="checkbox"][name="assortId"]').each(function (){
             	if (!this.checked){ 
               		ckall = false;  
       				//直接退出循环,不在进行each循环
               		return false; 
           	    }});
            $('input[type="checkbox"][name="all_check"]').attr('checked', ckall);
        });
	});
	function pageSkip(cpage) {
		$('#currentPage').val(cpage);
		$('#queryForList').click();
	}
	function query() {
		$('#assortFrm').submit();
	}
	function batchDelete(){
		var retString = "";
		var meetingId=${meetingId};
		var checks = document.getElementsByName("assortId");
		if (checks) {
			for (var i = 0; i < checks.length; i++) {
				var chkObj = checks[i];
				if (chkObj.checked)
					retString += chkObj.value + ",";
			}
		}
		
		if(retString==""){
			alert("请选择你要删除的分类!");
			return;
		}
		if(confirm("你确定要批量删除分类吗?")){
			
			var url="${ctx}/admin/pri/files/batchDelAssort.action?meetingId="+meetingId+"&ids="+retString;			
			this.location=url;
		}
	}
    </script>
</head>
<body>
<div>
    <div class=page_title><h3>资料管理 -- ${CURRENT_MEETING}</h3></div>

	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="资料管理" link="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="资料上传" link="${ctx}/admin/pri/meeting/preAddMeetingFile.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="分类管理" selected="true" style="padding:10px;">
			<!-- 查询FORM -->
			<div class="page_tools page_toolbar" style="text-align:left;"> 
				<form id="assortFrm" action="${ctx}/admin/pri/files/getFilesAssortPages.action">
					<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
					<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
					<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
					分类名称：<input type="text" id="assortName" name="assortName" value="${assortName}">
					<a id="queryForList" class="btn_common btn_true" onclick="query();" href="##">查询</a>
					<a href="#" onClick="batchDelete()" class="btn_common btn_false">批量删除</a> 
				</form>
			</div>
			
			<!-- 显示内容TABLE -->
			<table class="page_datalist">
				<thead>
				<tr>
					<th width="3" style="border-right: 0"><input type="checkbox" name="all_check" id="all_check"></input>  </th>
					<th width="250px" align="center">分类名称</th>
					<th>描述</th>
					<th>修改时间</th>
					<th width="120px">操作</th>
				</tr>
				</thead>

				<tbody>
				<c:choose>
					<c:when test="${not empty pager.pageRecords}">
						<c:forEach var="meetingFilesAssort" items="${pager.pageRecords}" varStatus="status">
							<tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
								<td ><input type="checkbox" name="assortId" value="${meetingFilesAssort.id}"></input></td>
								<td align="left">${meetingFilesAssort.assortName}</td>
								<td align="left">${meetingFilesAssort.description}</td>
								<td align="left">
									<fmt:formatDate value="${meetingFilesAssort.modifyTime}"
													type="both"
													pattern="MM月d日 HH:mm"/></td>
													
								<td align="center"><a 
													  href="${ctx}/admin/pri/files/selectAssortById.action?id=${meetingFilesAssort.id}&meetingFileId=${meetingId}">编辑</a>
								  
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="datarow">
							<td colspan="10" align="center">
								 无分类
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
			<div><%@ include file="/pages/common/page.jsp" %></div>
		</div>
		<div title="添加分类" link="${ctx}/admin/pri/files/preAddAssort.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="显示方式" link="${ctx}/admin/pri/meeting/goViewTypeConfig.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
</body>
</html>