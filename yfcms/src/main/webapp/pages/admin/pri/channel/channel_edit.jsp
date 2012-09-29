<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>栏目新增与编辑</title>
	${admin_css}                                   
	${jquery_js}                          
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}   
	${jmpopups_js} ${util_js}      
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
<script>
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
		
});


</script>
</head>
<body>
	<div class="page_title">
		<h3></h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">	
	
	
	<div title="栏目列表" link="${ctx}/admin/pri/channel/channelList.action" style="padding:10px;"></div>
	<c:if test="${not empty channel.id}">
	<div title="栏目编辑" selected="true"  style="padding:10px;"></div>
	</c:if>
	
    <c:if test="${empty channel.id}">
	<div title="栏目新增" selected="true"  style="padding:10px;"></div>
	</c:if>
	</div>
	
	<div class="page_form">
	<form id="addFrm"  method="post">
		<input type="hidden" name="channel.id" id="id" value="${channel.id}" />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font> 栏目名称：</label>
	            </dt>
	            <dd style="width:40%">
	            	<input type="text"  class="half" id="chanName" name="channel.chanName" value="${channel.chanName}" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>描述：</label>
	            </dt>
	            <dd  style="width:40%" >
	            	<textarea class="medium" rows="" cols="" id="chanDescription" name="channel.chanDescription">${channel.chanDescription}</textarea>
	            	 
	            </dd>
	        </dl>	
	        
	    </fieldset>
	    
	    
	  
	       
	    <div class="page_form_sub">
	    	  <a href="#" onclick="saveOrUpdate()"  class="btn_common btn_true">保 存</a>　
	    </div>
	   
	</form>
	</div>
	<script type="text/javascript">
		// 新增校验
		
		$().ready(function() {
			
		});

		
		function saveOrUpdate(){
			//$("#addFrm").submit();
			var url="${ctx}/admin/pri/channel/updateChannel.action";
			var options ={ 
					//beforeSubmit: validate,
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'
			    };
			
			$('#addFrm').ajaxSubmit(options);
			
			$("#addUserBtn").attr("disabled","disabled");
		}
		
		
	    function callback(data){
	    	alert(data.retMsg);
	    	var url="${ctx}/admin/pri/channel/channelList.action";
	    	document.location=url;
	    }
		
		
	
		
		
		
	
	</script>	
</body>
</html>