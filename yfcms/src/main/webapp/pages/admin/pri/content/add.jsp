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
<c:set var="contentName" value="${contentType == 1 ? '景点' : 2 == contentType ? '酒店':'菜单'}"></c:set>
<c:set var="readonly" value="${empty content.id ? false : true}"></c:set>

	<div class="page_title">
		<h3>${contentName}信息编辑填写</h3>
	</div>
	<div class="page_form">
	<form id="addMeetingContentForm" action="${ctx}/admin/pri/meeting/addMeetingContent.action" method="post" ${contentType == 3 ? 'enctype="multipart/form-data"':''}>
		<input   type="hidden" id="content.id" name="content.id"  value="${content.id}" />
		<input   type="hidden" id="content.meetingId" name="content.meetingId"  value="${meetingId}" />
		<input   type="hidden" id="content.contentType" name="content.contentType"  value="${contentType}" />
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
	                <small>菜单名称最多4个字。</small>
	            </dd>
	        </dl>
	        <c:if test="${contentType == 3}">
		        <!-- 
		        <dl>
		        	<dt>
		            	<label for="title"><font color="red">* </font>菜单序号：</label>
		            </dt>
		            <dd>
		            	<input style="width:98%" class="half" type="text" id="content_sortNo" name="content.sortNo"  value="${content.sortNo}" maxlength="2"/>
		            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
		                <small>请填写菜单显示顺序。只能输入数字1-99</small>
		            </dd>
		        </dl>
		         -->
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
	        </c:if>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>${contentName}介绍：</label>
	            </dt>
	            <dd>
	            	<textarea  style="width:98%" id="editor"  name="content.content" >${content.content}</textarea>
		            <small>内容最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具,否则有可能保存失败。</small>
	            </dd>
	        </dl>
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
			
			var editor = CKEDITOR.instances['editor'].getData();
			if(editor.length == 0){
				alert("请填写内容");
				return false;
			}
			if(editor.length > 1000000){
				alert("内容太长，最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具,否则有可能保存失败。");
				return false;
			}			

			submit();
			document.getElementById("submitBtn").disabled = "disabled";
		}


		function submit(){
			//alert('${meeting.id}');
			var url = '';
			if('${content.id}'==''){
				url= '${ctx}/admin/pri/meeting/addMeetingContent.action';
			}else{
				url= '${ctx}/admin/pri/meeting/modifyMeetingContent.action';				
			}
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
		
		
</script>
<ckeditor:replace replace="editor" basePath="${ctx}/ckeditor/" config="<%=CkEditorConfigHelper.createConfig()%>"/>
</body>
</html>