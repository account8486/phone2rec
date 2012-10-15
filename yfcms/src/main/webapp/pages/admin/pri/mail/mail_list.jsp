<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>邮件管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
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
					$("[name='mailId']").attr("checked", $("#all_check").attr("checked"));
				} else {
					$("[name='mailId']").removeAttr("checked");
				}
			});
	    
			//有一个不选上则全不选
			$('input[type="checkbox"][name="mailId"]').click(function () {
				   var ckall = true;
				   $('input[type="checkbox"][name="mailId"]').each(function (){
					if (!this.checked){ 
						ckall = false;  
						//直接退出循环,不在进行each循环
						return false; 
						}});
				   
				   $('input[type="checkbox"][name="all_check"]').attr('checked', ckall);
		   });	
			
			
			
		});
		
		
		function batchDelete(){
	        var retString = "";
	        var checks = document.getElementsByName("mailId");
	        if (checks) {
	            for (var i = 0; i < checks.length; i++) {
	                var chkObj = checks[i];
	                if (chkObj.checked)
	                    retString += chkObj.value + ",";
	            }
	        }
	        
	        if(retString==""){
	        	alert("请选择你要删除的邮件！");
	        	return;
	        }
			//alert(retString);
			//alert(meetingId);
			if(confirm("你确定要批量删除邮件吗？")){
				var url="${ctx}/admin/pri/mail/mail_batchDelMails.action?ids="+retString;			
				this.location=url;
			}
			
	    }
		
	    function query() {
	        $('#sbFrm').submit();
	    }
	    
	    
	    function delmailnel(id){
	    	 if(confirm("确定删除当前邮件吗？")){
				 var url="${ctx}/admin/pri/mailnel/delmailnel.action?id="+id;
				 this.location=url;
			 }
	    }
	    
	    
	    
    </script>
</head>
<body>
<div class="page_title"><h3></h3></div>
<div class="easyui-tabs" border="false" style="padding:10px;">	
	    <div title="邮件列表" style="padding:10px;" selected="true" >
		<div class="page_tools">
		<form id="sbFrm" action="${ctx}/admin/pri/mail/mail_getMailList.action">
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
	
			<a href="#" id="queryForList" onclick="query();"></a>
			
				<table width="80%">
				<tr>
				<th style="width: 100px; ">邮件名称：</th>
				<td style="width: 150px; "><input type="text" style="width: 120px;" id="mailContent" name="mailContent" value="${mailContent}"/></td>
				<td>
					<a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a>
					
					<a href="#" onClick="batchDelete()" class="btn_common btn_false">批量删除</a> 
				</td>
				</tr>
			</table>
			</form>	
			
		</div>
		
		<div>
		<table class="page_datalist">
		<thead>
			<tr>
				<th width="2%">
				<input type="checkbox" name="all_check" id="all_check"></input>
				</th>
				<th width="12%">收件人</th>
				<th width="30%">邮件名称</th>
			    <th width="15%">抄送人</th>
			    <th width="13%">时间</th>
			    <th width="6%">状态</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr> 
				<c:choose>
				<c:when test="${not empty pager.pageRecords}">
					<c:forEach var="mail" items="${pager.pageRecords}" varStatus="status">
						<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
						    <td>
						    <input type="checkbox" name="mailId" value="${mail.id}">
						    </td>
						    <td>${mail.mailTo}</td>
							<td>${mail.mailSubject }</td>
							<td>${mail.mailCc}</td>
							<td>${fn:substring(mail.createTime,0,16) }</td>
							<td>
							<c:choose>
							<c:when test="${mail.sendStatus eq 0}">
							<c:out value="未发送"></c:out>
							</c:when>
							
							<c:when test="${mail.sendStatus eq 1}">
							<c:out value="已发送"></c:out>
							</c:when>
							
							<c:when test="${mail.sendStatus eq 9}">
							<c:out value="发送失败"></c:out>
							</c:when>
							<c:otherwise>
							<c:out value="未知状态"></c:out>
							</c:otherwise>
							</c:choose>
							
							</td>
							<th width="20%">
							</th>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="datarow"><td colspan="11" align="center"> 无邮件信息.</td></tr>
				</c:otherwise>
			</c:choose>
			</tr>
		</tbody>
	</table>
	<%@ include file="/pages/common/page.jsp" %> 
	</div>
	</div>
	
	
	<div title="新增邮件" link="${ctx}/admin/pri/mail/mail_toAddMail.action"  style="padding:10px;"></div>
	
</div>
</body>
</html>