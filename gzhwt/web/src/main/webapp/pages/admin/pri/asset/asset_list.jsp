<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产管理</title>
${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title"><h3>资产管理  -- ${CURRENT_MEETING}</h3></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">	
		<div title="资产管理" style="padding:10px;">
	<div class="page_tools">
		<form id="listForm" action="${ctx}/admin/pri/asset/getAssetLst.action">
                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
                <input type="hidden" id="queryForList" />
				<table width="80%">
					<tr>
						<th style="width:60px;">资产编号：</th>
						<td style="width:150px;">
							<input type="text" style="width: 120px; " id="asset_no" name="asset_no" value="${asset_no}"/>
						</td>
						<th style="width:60px;">资产名称：</th>
						<td style="width:150px;">
							<input type="text" style="width: 120px; " id="asset_name" name="asset_name" value="${asset_name}"/>
						</td>
						<td>
							<a href="#" id="queryForList" onclick="$('#listForm').submit();" class="btn_common btn_true">搜 索</a>
						</td>
					</tr>
				</table>
				<a href="${ctx}/admin/pri/asset/editAsset.action?meetingId=${meetingId}" class="btn_common btn_false" id="add">添加资产</a>
        </form>
	</div>
	
	<div>
		<table class="page_datalist">
	    	<thead>
				<tr>
					<th scope="col" width="20%">资产编号</th>
					<th scope="col" width="20%">资产名称</th>
					<th scope="col" width="20%">资产价值</th>
					<th scope="col" width="20%">入库时间</th>
					<th scope="col" width="20%">操作</th>
				</tr>	    	
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="asset" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            	<td align="left">${asset.asset_no}</td>
                            	<td align="left">${asset.asset_name}</td>
                            	<td align="left">${asset.asset_value}</td>
				          		<td align="left">
				          			<fmt:formatDate value="${asset.storage_date}" pattern="yyyy-MM-dd" />
				          		</td>
				          		<td align="center">
									<a href="javascript:void(0);" class="edit" id="${asset.id}">编辑</a>
				          		  &nbsp;<a href="javascript:void(0);" class="delete" id="${asset.id}">删除</a>
				          		</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">没有资产信息.</td>
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
	<script type="text/javascript">
	$(document).ready(
		function() {
			
			$("a.delete").click(
				function() {
					if(confirm("确定要删除该条信息吗?")){
						var id = $(this).attr('id');
						$.post(
								"${ctx}/admin/pri/asset/deleteAsset.action",
								{"id" :　id	},
								function(data, textStatus) {
									alert(data.retMsg);
									if(data.retCode == "0"){
										location.reload();
									}
								}, 
								"json"
						);
					}
					return false;
				}
			);
			
			$("a.edit").click(
				function() {
					var id = $(this).attr('id');
					window.location.href = "${ctx}/admin/pri/asset/editAsset.action?id="+id;
				}
			);
			
		}
	);
	</script>
</body>
</html>