<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统配置</title>
	${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	<script src="${ctx}/js/jquery-zoom/jquery.artZoom.js"></script>
	<link href="${ctx}/js/jquery-zoom/jquery.artZoom.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
		$(document).ready(function() {
			$('.artZoom').artZoom({path:'${ctx}/js/jquery-zoom/images',preload:true,blur:true,left:'',right:'',source:''});			
		    $('.easyui-tabs').tabs({  
				onSelect:function(title){  
					var tab = $(this).tabs('getTab', title); 
					var href = tab.attr('link');
					if (href) {
						location.href = href;
						showLoading(title);
						return false;
					}
				}  
			});
		    $("#updateBtn").click(function(){
		 	   showLoading("正在更新中......");
		 	  	var dataValue = $("input:radio:checked").attr("dataValue");
				$.post("${ctx}/admin/pri/config/saveClientBgImages.action",	{"dataValue":dataValue},
						function(data, textStatus) {
							if (textStatus == "success" && data.retcode == 0) {
							   location.reload();
							   return false;
							}
						}, 
						"json"
				);		 	   
		 	   
		    });
		});
	</script>
</head>
<body>
	<div class="mainbox"><div class="page_title"><h3>系统配置 -- 客户端登录背景</h3></div></div>
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<%--
		<div title="密码找回" link="${ctx}/admin/pri/dict/getDataDictConfig.action" style="padding:10px;"></div>
		--%>
		
		<div title="客户端登录背景" style="padding:10px;" selected="true">
			<div style="border-bottom: 1px solid #ccc; margin-bottom: 10px;">
				<a href="${serverUrl}images/client/bg_small.png" rel="${serverUrl}images/client/bg.png"><img class="artZoom" src="${serverUrl}images/client/bg_small.png" /></a>
				<div style="width:100px;text-align:center;">当前背景图片</div>
			</div>
			 
			 <c:if test="${not empty datas}">
			 <div style="border-bottom: 1px solid #ccc; margin-bottom: 10px;">
			 <table>
			 <c:forEach var="data" items="${datas}" varStatus="status">
			 	<c:if test="${(status.count-1) % 6 eq 0}"><tr></c:if>
			 	<td>
			 		<c:set var="name" value="${fn:substringBefore(data.dataValue,'.')}"/> 
			 		<a href="${serverUrl}${name}_small.png" rel="${serverUrl}${data.dataValue}"><img class="artZoom" src="${serverUrl}${name}_small.png" /></a>
			 		<div style="width:100px;text-align:center;"><input type="radio" name="dataCode" dataValue="${data.dataValue}" value="${data.dataCode}" />${data.dataName}</div>
			 	</td>
			 	<c:if test="${status.count % 6 eq 0}"></tr></c:if>
			 </c:forEach>
			 </table>
			 </div>
			 </c:if>
			 <a href="javascript:;" id="updateBtn" class="btn_common btn_true">保存 </a>
		</div>
	</div>   
</body>
</html>