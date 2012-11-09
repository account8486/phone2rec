<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>编辑任务</title>
	${admin_css}                                   
	${jquery_js}                          
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}         
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
					//showLoading(title);
					return false;
				}
			}  
		});
		
	$("#gender").val(${spokesman.gender});
});


</script>
</head>
<body>
	<div class="page_title">
		<h3>添加任务  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">	
		<div title="任务列表" link="${ctx}/admin/pri/task/getTaskListPager.action?meetingId=${meetingId}" style="padding:10px;"></div>
	<div title="编辑任务" selected="true" style="padding:10px;"></div>
	</div>
	
	<div class="page_form">
	<form id="updateFrm" action="${ctx}/admin/pri/task/updateTask.action" method="post">
	<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <fieldset>
	        <legend>基本信息</legend>
	        <input type="hidden"  id="id" name="id" value="${task.id }" ></input>	
	           <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>名&nbsp;&nbsp;&nbsp;称：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" id="taskName" name="taskName" value="${task.taskName}" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>	
	        
	        <dl>
	        	<dt>
	            	<label for="title">描述：</label>
	            </dt>
	            <dd>
	            	<textarea id="taskDescription" name="taskDescription"   tabindex="1"  style="width:600px;height:38px">${task.taskDescription}</textarea>
	                <small></small>
	            </dd>
	        </dl>	
	        

	   
	    </fieldset>
	    <div class="neidi">&nbsp;</div>
	    <div class="page_form_sub">
	        <a href="#" onclick="add();" id="addUserBtn" class="btn_common btn_true">保 存</a>　
	    </div>
	   
	</form>
	</div>
	<script type="text/javascript">

		
		function add(){	
			//$("#updateFrm").submit();
			//$("#addUserBtn").attr("disabled","disabled");
			var url="${ctx}/admin/pri/task/updateTask.action";
			//alert(url);
			
			var options ={ 
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'
			    };
			
			$('#updateFrm').ajaxSubmit(options);
			
		}
		
		function returnList(meetingId){
			window.location.href = "${ctx}/admin/pri/task/getTaskListPager.action?meetingId="+meetingId;
		}
		
	    function callback(data){
	    	//hideLoading();
	    	alert(data.retMsg);
	    	returnList(${meetingId});	
	    }
		
		
		
		
	</script>	
</body>
</html>