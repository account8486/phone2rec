<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@page import="com.wondertek.meeting.common.CkEditorConfigHelper"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>新闻管理</title>
	${admin_css}                                   
	${jquery_js}
	<script type="text/javascript" src="${ctx}/js/util.js"></script>
	<script type="text/javascript">
	
		function loadList(){
			//admin/pri/news/list.action?meetingId=
			var timeStamp=new Date().getTime();
			var url="${ctx}/admin/pri/news/list.action?from=guide&meetingId=${meetingId}&timeStamp="+timeStamp;
			$("#list").load(url);
		}
	
		$(function() {
			loadList();
			$("#imageNews0").click(function() {
				$("#thumbnailDL").hide();
			});
			$("#imageNews1").click(function() {
				$("#thumbnailDL").show();
			});
			
			$("#backMeeting").click(function(){
				doFinish();
			});
		});
	

		
		function doFinish() {
			if(confirm("确定结束此会议向导操作吗？")) {
				location.href = "${ctx}/admin/pri/meeting/guide_finish.action?fromStep=step4&meeting.id=${meetingId}";
			}
		}
		// 新增校验
		function validate() {
	        var title = $("#title").val();
	        if (isEmpty(title)) {
	            $("#tip_title").html("请输入新闻标题。");
	            $("#tip_title").show();
	            return false;
	        }
	
	        var imageNews = $(":radio[checked]").val();
	        if($.trim(imageNews) == "") {
	        	$("#tip_imageNews").html("请选择新闻类型。");
	            $("#tip_imageNews").show();
	            return false;
	        }
	        
	        if(imageNews == "1") {
	        	var thumbnailImage = "${news.thumbnailImage}";
	        	var thumbnail = $("#thumbnail").val();
	        	if(thumbnail == "" && thumbnailImage == "") {
	        		$("#tip_imageNews").html("请上传新闻缩略图文件。");
		            $("#tip_imageNews").show();
		            return false;
	        	}
	        }
	        
	        var editor = CKEDITOR.instances['editor'].getData();
			if(editor.length == 0){
				alert("请输入新闻正文的内容。");
				return false;
			}
			if(editor.length > 1000000){
				alert("新闻正文内容太长，最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具,否则有可能保存失败。");
				return false;
			}	
			
	        return true;
		}
		
		function save(){
			$("[id^='tip_']").hide();
	       	var tmp_bool = validate();
	        if (! tmp_bool) {
	            return false;
	        }
	        
			$("#addNewsForm").submit();
			$("#saveBtn").attr("disabled","disabled");
		}

		
	</script>
</head>
<body>
	
	<div style="width: 75%;float:left;margin-left: 5px;margin-right: 3px;">
	<div class="page_title">
	<s:if test="news != null">
		<h3>编辑新闻信息</h3>
	</s:if>
	<s:else>
		<h3>发布新闻信息</h3>
	</s:else>
	</div>
	
	<s:if test="meetingType.id != null">
	<div class="page_tools page_toolbar">
			<a type="button" class="btn_common btn_false"
				href="${ctx}/admin/pri/custom/meetingType_listMenu.action?meetingTypeId=${meetingType.id}">菜单定制</a>
	</div>
	</s:if>
		
	<div class="page_form5">
	<form id="addNewsForm" action="${ctx}/admin/pri/news/save.action" method="post" enctype="multipart/form-data">
	    <input type="hidden" name="id"  value="${news.id}" />
	    <input type="hidden" name="meetingId"  value="${meetingId}" />
	    <input type="hidden" name="guideStep" value="step5" />
	    
	    
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>标题：</label>
	            </dt>
	            <dd>
	            	<input type="text" style="width:48%" id="title" name="news.title" value="${news.title }" maxlength="50" ></input>
	            	<small>请输入新闻的标题。</small>
	            	<br/>
	            	<font id="tip_title" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>来源：</label>
	            </dt>
	            <dd>
	            	<input type="text"  style="width:48%"  id="source" name="news.source" value="${news.source }" maxlength="50" ></input>
	            	<br/>
	            	<font id="tip_source" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>类型：</label>
	            </dt>
	            <dd>
	            	<input type="radio" id="imageNews0" name="news.imageNews" value="0" ${empty news || news.imageNews==0 ? "checked" : "" }/><label for="imageNews0">普通新闻</label>
	            	<input type="radio" id="imageNews1" name="news.imageNews" value="1" ${news.imageNews==1 ? "checked" : "" }/><label for="imageNews1">图片新闻</label>
	            	<small>新闻类型分为普通新闻和图片新闻，如果选择图片新闻，则需要上传一个图片做为缩略图显示。</small>
	            	<br/>
	            	<font id="tip_imageNews" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        
	        <dl id="thumbnailDL" style="display:${empty news || news.imageNews==0 ? 'none' : ''};">
	        	<dt>
	            	<label for="title"><font color="red"></font>新闻缩略图：</label>
	            </dt>
	            <dd>
	            	<input  type="file" id="thumbnail" name="thumbnail" />
	            	<br/>
	            	<font id="tip_thumbnail" style="line-height:35px" color="red"></font>
	    			<small>请上传用于幻灯片播放的新闻缩略图片，最佳尺寸为250x183像素</small>
	    			<s:if test="news.thumbnailImage != null && news.thumbnailImage.length() > 0">
	    			<div>
	    				<img src="${serverUrl}${news.thumbnailImage }" width="200" height="120" border="0"/>
	    				<small>已经设定的新闻缩略图片</small>
	    			</div>
	    			</s:if>
	            </dd>
	        </dl>
	        <dl style="display:${not empty news ? '' : 'none'};">
	        	<dt>
	            	<label for="title"><font color="red">*</font>有效状态：</label>
	            </dt>
	            <dd>
	            	<input type="radio" id="state1" name="news.state" value="1" ${empty news || news.state==1 ? "checked" : "" }/><label for="state1">有效</label>
	            	<input type="radio" id="state0" name="news.state" value="0" ${news.state==0 ? "checked" : "" }/><label for="state0">无效</label>
	            	<small>如果状态设为无效，则该新闻不会在门户中显示。</small>
	            	<br/>
	            	<font id="tip_imageNews" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	       	<dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>新闻正文：</label>
	            </dt>
	            <dd>
	            	<textarea  style="width:800px;"  id="editor"  name="news.content" >${wd:base64Decode(news.content)}</textarea>
		            <small>新闻正文最多可以填写1M数据，大约50万个汉字，正文中的图片请使用工具栏中的图片上传工具,否则有可能保存失败。</small>
	            </dd>
	        </dl>   
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	    <input type="hidden" name="guideStep" value="step5" />
	    <input type="hidden" id="stepValue" name="stepValue" value="5" />
	    
	    	<a href="${ctx}/admin/pri/meeting/guide_step4.action?meeting.id=${meetingId}"  class="btn_common btn_false">上一步</a>
	        <a href="#" onclick="save();" id="saveBtn" class="btn_common btn_true">保存</a>　
	        <a href="${ctx}/admin/pri/meeting/guide_step6.action?meeting.id=${meetingId}" class="btn_common btn_true">下一步</a>　
	    </div>
	</form>
	</div>
	
	<div id="list"></div>
	
	</div>
	
	
	<jsp:include page="guideCommonRight.jsp" />
	<div style="clear: both"></div>
	
	<ckeditor:replace replace="editor" basePath="${ctx}/ckeditor/" config="<%=CkEditorConfigHelper.createConfig()%>"/>
</body>
</html>