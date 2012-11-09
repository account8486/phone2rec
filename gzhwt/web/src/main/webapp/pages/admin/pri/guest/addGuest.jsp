<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.wondertek.meeting.common.CkEditorConfigHelper"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>会务通平台</title>
	${admin_css}                                   
	${jquery_js}                                
	${jquery_form_js}                                 
	${admin_js}                   
	${util_js} 
	<script src="${ctx}/js/jquery-jcrop/jquery.Jcrop.js"></script>	
    <link rel="stylesheet" href="${ctx}/css/jquery-jcrop/jquery.Jcrop.css" type="text/css" />	
    
	<style type="text/css">
	.result_content {
		border:1px dashed #000000; 
		padding:4px 10px 10px 10px; 
		width:420px; 
		background:#FFFFBF; 
		overflow:auto;
	}
	
	.result_content .close_button {
		margin-bottom:5px; 
	}
	
	.result_content .close_button a {
		color:#ff0000;
		float: right;
	}
	
	.result_content .close_button a:hover {
		background:#C0C0C0;
	}
	
	.result_content .result {
		margin-bottom:2px; 
		border-bottom: 1px dotted #C5C5C5;
	}
	
	.result_content .result a {
		float: right;
	}
	
	.result_content .result a:hover {
		background:#C0C0C0;
	}
	
	.specialty_info {
		width:90%;
		border:1px dashed #000000;
		margin-top:10px;
	}
	
	.specialty_info img {
		width:180px;
		height:120px;
		float:right;
		margin:10px;
	}
	
	.specialty_info #specialty_memo {
		padding:10px;
		
	}
	
	.clear {
		float: none;
		clear: both;
	}
	</style>
</head>
<body>
	<div class="page_title">
		<s:if test="flag=='edit'">
		<h3>修改嘉宾信息</h3>
		</s:if>
		<s:else>
		<h3>添加嘉宾信息</h3>
		</s:else>
	</div>
	<div class="page_form">
	
	
	  <fieldset>
	    
	 <div id="makeHeadImgDiv">  
	 <form name="uploadImgFrm" id="uploadImgFrm" enctype="multipart/form-data" method="post" >
		 <dl>
	        	<dt>
	            	<label for="title"><font color="red"> </font>请选择照片：</label>
	            </dt>
	            <dd>
	            	 <input class="half"  type="file" id="headImg" name="headImg"  value="" maxlength="50"/>
	            	 <font id="tip_rank" style="line-height:35px" color="red"></font>
	            	 <button id="upImgBtn">上传</button>
	            </dd>
	     </dl>
	</form>  
	
	 <form name="cutHeadImgFrm" id="cutHeadImgFrm"  method="post" >
	     <dl>
	        	<dt>
	            	<label for="title"><font color="red"> </font>图片展示：</label>
	            </dt>
	            <dd style="width:48%;float:left">
	            	<input type="button" value="采用当前区域作为头像" id="cropButton"/>
	            	<img id="headOriginalUrl" style="display:none;"></img>
	            </dd>
	     </dl>
	     
	      	<input type="hidden"  id="x" name="x" />
			<input type="hidden"  id="y" name="y" />
			<input type="hidden"  id="x2" name="x2" />
			<input type="hidden"  id="y2" name="y2" />
			<input type="hidden"  id="w" name="w" />
			<input type="hidden"  id="h" name="h" />  
			
			<!-- 源图片宽度和高度 -->
			<input type="hidden"  id="width" name="width" value="${param.width}"/>  
			<input type="hidden"  id="height" name="height" value="${param.height}"/>
			<input type="hidden"  id="oldImgPath" name="oldImgPath" value="${param.oldImgPath}"/>  
			<input type="hidden"  id="imgRoot" name="imgRoot" value="${param.imgRoot}"/>	
			<input type="hidden"  id="imgFileExt" name="imgFileExt" value="${param.imgFileExt}"/>	
			
			<!-- 原图图片相关信息 -->
			<input type="hidden" id="bigPicFileName" name="bigPicFileName" />
	</form>	
	  </div> 
	        
	<form id="addGuestForm" action="${ctx}/admin/pri/guest/guest_addGuest.action" method="post">
	    <input type="hidden"  name="meetingId" value="${meetingId}"/>
	    <input type="hidden"  name="id" value="${guest.id}"/>
	    <input type="hidden"  name="currentPage" value="${currentPage}"/>
	    <input type="hidden"  name="queryName" value="${queryName}"/>
	    
	  
	        
	        <dl style="float:left">
	        	<dt>
	            	<label for="title"><font color="red">* </font>姓名：</label>
	            </dt>
	            <dd style="width:48%;float:left">
	            	<img id="img_headUrl" name="img_headUrl" style="width:200px;height:200px;" 
	            	<c:if test="${not empty guest.headUrl }"> src="${guest.headUrl}" </c:if>  src="${ctx}/images/head/head_default.png"  ></img>
	            	<input type="hidden" name="guest.headUrl" id="headUrl" value="${guest.headUrl}" />
	            	
	            	<input class="half" type="text" id="name" name="name"  value="${guest.name}" maxlength="20"/>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        
	        <dl style="float:left">
	        	<dt>
	            	<label for="title">头衔：</label>
	            </dt>
	            <dd style="width:48%;float:left">
	            	
	            	<input class="half" type="text" id="rank" name="rank"  value="${guest.rank}" maxlength="50" />
	            	<font id="tip_rank" style="line-height:35px" color="red"></font>
	            	
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">简介：</label>
	            </dt>
	            <dd>
	            	<textarea  style="width:48%;float:left" id="guest.topic" name="about" rows="2">${guest.about }</textarea>
	            	<small>请限制在100字以内,您还可以输入<span id="meeting_topic_remain">100</span>字</small>
	            	<font id="tip_about" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    
	    <div class="page_form_sub">
	        <a href="#" id="submitBtn" class="btn_common btn_true">保 存</a>
	        <a href="${ctx}/admin/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}" id="retBtn" class="btn_common btn_false">返回列表</a>
	        
	        <a id="refreshCurrentPage" class="btn_common btn_false">刷新</a>
	    </div>
	</form>
	
	
	
	</div>
<script type="text/javascript">
	$(function(){
		$("#guest\\.topic").calcWordNum({maxNumber:200,targetid:"meeting_topic_remain"});
		
		$("#refreshCurrentPage").click(function(){
			//document.url=location.href;	
			window.location.reload();
		});
		
		var flag="${flag}"
		if(flag=="edit"){
			$("#submitBtn").html("修改");
		}
		
		$("#submitBtn").click(function(){
			if(validateAdd()){
				$("#addGuestForm").submit();
			}
		});
		
		$("#upImgBtn").click(function(){
			uploadHeadImg();
		});
		
		
		$('#cropButton').click(function(){
		    var x = $("#x").val();
			var y = $("#y").val();
			var w = $("#w").val();
			var h = $("#h").val();	
			if(w == 0 || h == 0 ){
				alert("您还没有选择图片的剪切区域,不能进行剪切图片!");
				return;
			}	
			//alert("你要剪切图片的X坐标: "+x + ",Y坐标: " + y + ",剪切图片的宽度: " + w + ",高度：" + h );
			if(confirm("确定按照当前大小剪切图片吗")){				
				//document.form1.submit();
			    //$("#makeHeadImgDiv").toggle();
			    function loadHeadUrl(data){
			    	//alert(data.headUrl);
			    	 alert(data.retMsg);
			    	 $("#img_headUrl").attr("src",data.headUrl);
			    	 $("#headUrl").val(data.headUrl);
			    	//alert($("#headUrl").val());
			    }
				var url="${ctx}/admin/pri/guest/guest_cutHeadImg.action";
				var options ={ 
						//beforeSubmit: validate,
				        url:	   url,
				        success:   loadHeadUrl, 
				        type:      'post',      
				        dataType:  'json'
				};
				$('#cutHeadImgFrm').ajaxSubmit(options);
			}
	 });
		
	
		
	});
	
	
	
	function uploadHeadImg(){
		
		if(confirm("确定上传头像图片?")){
			var url="${ctx}/admin/pri/guest/guest_uploadHeadImg.action";
		
			function loadUrl(data){
				$("#upImgBtn").css("display","none");
				$("#headOriginalUrl").attr("src",data.originalUrl);
				$("#headOriginalUrl").css("display","block");
				$("#bigPicFileName").val(data.bigPicFileName);
				
				alert(data.retMsg);
				//alert("success!");
				$('#headOriginalUrl').Jcrop({
					onChange: showCoords,
					onSelect: showCoords
				});
			}
			var options ={ 
					//beforeSubmit: validate,
			        url:	   url,
			        success:   loadUrl, 
			        type:      'post',      
			        dataType:  'json'
			};
			
			$('#uploadImgFrm').ajaxSubmit(options);
		}
	}
	
	
	function showCoords(c)
	{
		$('#x').val(c.x);
		$('#y').val(c.y);
		$('#x2').val(c.x2);
		$('#y2').val(c.y2);
		$('#w').val(c.w);
		$('#h').val(c.h);	
		
	//	$('#labelX').val(c.x);
	//	$('#labelY').val(c.y);
	//	$('#labelX2').val(c.x2);
	//	$('#labelY2').val(c.y2);
	//	$('#labelW').val(c.w);
	//	$('#labelH').val(c.h);	
		
		//显示剪切按键
	//	jQuery('#cropTd').css("display","");
				
	}
	
	
	//新增校验
	function validateAdd() {
		var result = true;
	    var name = $("#name").val();
	
	    if (isEmpty(name)) {
	        $("#tip_name").html("请输入嘉宾姓名");
	        $("#tip_name").show();
	        result = false;
	    }
	    return result;
	}
	
</script>

</body>
</html>