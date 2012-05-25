<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.wondertek.meeting.common.CkEditorConfigHelper"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	${admin_css}                                   
	${jquery_js}
	${jquery_form_js}
	${util_js}
	${map_js }
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
<c:set var="meetingId" value="${empty param.meetingId ? content.meetingId : param.meetingId}"></c:set>
<c:set var="contentType" value="${empty param.contentType ? content.contentType : param.contentType}"></c:set>
<c:set var="contentName" value="菜单列表"></c:set>
<c:set var="readonly" value="${empty content.id ? false : true}"></c:set>

	<div class="page_title">
		<h3>${contentName}信息编辑填写</h3>
	</div>
	<div class="page_form">
	<form id="addMeetingContentForm" action="${ctx}/admin/pri/meeting/addMeetingContent.action" method="post" ${contentType == 3 ? 'enctype="multipart/form-data"':''}>
		<input   type="hidden" id="content.id" name="content.id"  value="${content.id}" />
		<input   type="hidden" id="content.meetingId" name="content.meetingId"  value="${meetingId}" />
		<input   type="hidden" id="content.contentType" name="content.contentType"  value="${contentType}" />
		<input   type="hidden" id="content.isList" name="content.isList"  value="1" />
		<input   type="hidden" id="itemIds" name="itemIds"  value="${itemIds}" />
		<s:token />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>${contentName}名称：</label>
	            </dt>
	            <dd>
	            	<input  type="text" id="content_title" name="content.contentTitle"  value="${content.contentTitle}" maxlength="4"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small>${contentName}名称最多4个字。</small>
	            </dd>
	        </dl>
	        <c:if test="${contentType == 3}">
		        <dl>
		        	<dt>
		            	<label for="title"><font color="red">* </font>菜单类型：</label>
		            </dt>
		            <dd>
		            	<input style="width:30px"  type="checkbox" id="content_category" name="category" value="web" ${content.inWeb ? 'checked':''}/>WEB
		            	<input style="width:30px;"  type="checkbox" id="content_category" name="category" value="wap" ${content.inWap ? 'checked':''}/>WAP
		            	<input style="width:30px;"  type="checkbox" id="content_category" name="category" value="client" ${content.inClient ? 'checked':''}/>客户端
		            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
		                <small>请选择菜单显示在哪些终端。</small>
		            </dd>
		        </dl>
		        
		        <dl>
		        	<dt>
		            	<label for="title">客户端图标：</label>
		            </dt>
		            <dd>
		            	<input style="width:98%" class="half" type="hidden" id="default_content_icon" name="defaultIcon"  value="${empty content.icon ? defaultImg : content.icon}" readonly="readonly"/>
		            	
		            	<c:if test="${not empty content.icon}">
		            		<img id="content_icon" src="${content.icon }" width="48px" height="48px"/>
		            	</c:if>
		            	<c:if test="${empty content.icon}">
		            		<img id="content_icon" src="${defaultImg }" width="48px" height="48px"/>
		            	</c:if>
		            	<c:if test="${not empty defaultIcons}">
		            		<c:if test="${not empty content.icon}">
			            		&nbsp;&nbsp;&nbsp;原图标：<a href="javascript:void(0);" onclick="setIcon('${content.icon }');"><img src="${content.icon }" width="48px" height="48px;"/></a>
			            	</c:if>
			            	<br>请选择:</br>
		            		<c:forEach var="icon" items="${defaultIcons}">
			            		<a href="javascript:void(0);" onclick="setIcon('${icon}');"><img src="${icon }" width="48px" height="48px;"/></a>
		            		</c:forEach>
		            	</c:if>
		            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
		                <small>请选择菜单图标,若不满意请在下面上传自定义图标。</small>
		            </dd>
		        </dl>
		        <dl>
		        	<dt>
		            	<label for="title">上传客户端图标：</label>
		            </dt>
		            <dd>
		            	<input  type="file" name="icon"  value="${content.icon}" />
		            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
		                <small>上传菜单图标格式为尺寸96*96 小于30KB。若不上传将使用上面选择的图标。上传后优先使用上传的图标。</small>
		            </dd>
		        </dl>
		        
		        <dl>
		        	<dt>
		            	<label for="title">备注：</label>
		            </dt>
		            <dd>
		            	<textarea style="width:98%" class="half"  id="content_comments" name="content.comments"  >${content.comments}</textarea>
		            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
		                <small>最多100个字,您还可以输入<span id="content_comments_remain">100</span>个字</small>
		            </dd>
		        </dl>
		        
		        <dl>
		        	<dt>
		            	<label for="title">&nbsp;&nbsp;&nbsp;&nbsp;</label>
		            </dt>
		            <dd>
		            	<a href="#"  onclick="addItem();" class="btn_common btn_true">添加条目</a>
		            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
		                <small></small>
		            </dd>
		        </dl>
		        
		        <dl>
		        	<dt>
		            	<label for="title">条目列表：</label>
		            </dt>
		            <dd>
		            	<div>
							<table class="page_datalist">
						    	<thead>
						        	<tr>
						            	<th width="35%">标题</th>
						            	<th width="30%">排序码</th>
					                    <th width="35%">操作</th>
						            </tr>
						        </thead>
						        <tbody id="itemList">
						        </tbody>
						    </table>
						</div>
		            </dd>
		        </dl>
	        </c:if>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>　<a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	</form>
	</div>
<script type="text/javascript">
		function save(){
			//var formData = $('#addMeetingForm').formJsonData(); 
			//alert(formData);
			var name = $('#content_title').val();
			name = $.trim(name);
			if(name.length == 0 ){
				alert("请输入${contentName}名称.");
				return false;
			}
			if(name.length > 4){
				alert("${contentName}名称不能超过4个字.");
				return false;
			}
			
			submit();
			document.getElementById("submitBtn").disabled = "disabled";
		}


		function submit(){
			//alert('${meeting.id}');
			var url = '';
			if('${content.id}'==''){
				url= '${ctx}/admin/pri/meeting/addContentList.action';
			}else{
				url= '${ctx}/admin/pri/meeting/modifyContentList.action';
			}
			$("#itemIds").val(addedItems.getKeys());
			$('#addMeetingContentForm').attr('action',url).submit();
		}
		
		function returnList(){
			window.location.href = "${ctx}/admin/pri/meeting/listMeetingContent.action?contentType=${contentType}&meetingId=${meetingId}";
		}
		
		function setIcon(img){
			$('#default_content_icon').val(img);
			$('#content_icon').attr('src',img);
		}
		
		$("#content_comments").calcWordNum({maxNumber:100,targetid:"content_comments_remain"});
		
		function addItem(){
			var url = "${ctx}/admin/pri/meeting/gotoAddContentItem.action?contentType=${contentType}&meetingId=${meetingId}&parentId=${content.id}";
			window.open(url,'','width=800,height=500,left=200,top=200,scrollbars=yes,location=no');
		}
		
		var addedItems = new Map();
		
		function appendItem(id,title,sortNo){
			var data = "<tr><td align='left'>"+title+"</td><td align='left'>"+sortNo+"</td>"
					   +"<td align='center'><a href='#' onclick='modifyItem("+id+");'>编辑</a>&nbsp;&nbsp;"
					   +"<a href='#' onclick='delItem("+id+");'>删除</a></tr>"
			$(data).appendTo($("#itemList"));
			addedItems.put(id,title);
		}
		
		function addIdIntoItems(id,title){
			addedItems.put(id,title);
		}
		var itemIdsTemp = '${itemIds}';

		if(itemIdsTemp.length > 0){
			var idsArray = itemIdsTemp.split(",");
			for(var i=0;i<idsArray.length;i++){
				addedItems.put(idsArray[i],"");
			}
		}
		
		refreshItems();
		
		function refreshItems(){
			var itemIds = addedItems.getKeys();
			if(itemIds.length > 0){
				$.ajax({
					url: '${ctx}/admin/pri/meeting/listContentItem.action',
					data: {"meetingId":"${meetingId}","itemIds":itemIds},
			        type: 'post',
			        dataType: 'json',
			        success: callback
				});
			}
		}
		
		function callback(data){
			if(data.retcode == "0"){
				$("#itemList").empty();
				var result = data.result;
				for(var i =0 ;i<result.length;i++){
					appendItem(result[i].id,result[i].contentTitle,result[i].sortNo);
				}
			}
		}
		
		
		function modifyItem(id){
			//window.location.href = url;
			var url = "${ctx}/admin/pri/meeting/getMeetingContentById.action?returnType=modify&content.id="+id;
			window.open(url,'','width=800,height=500,left=200,top=200,scrollbars=yes,location=no');
		}
		
		function delItem(id){
			if(confirm("确定要删除选择的信息吗?")){
				doDelete(id);
			}
		}
		
		function doDelete(id){
			var url = '${ctx}/admin/pri/meeting/deleteMeetingContent.action?content.id='+id;
			
			$.getJSON(url, itemCallback);
		}
		//回调函数
		function itemCallback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){
				refreshItems();
			}
		}
		
</script>
</body>
</html>