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
		${admin_js}                   
		${area_js}        
		${jmpopups_js}   
		${util_js}  
		   
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
		
		
	$("#updateFrm").validate({
			onkeyup: false,
			rules: {
				"detailName": "required"
			},
			messages:{
				"detailName": "请输入任务名称！"
			}
   });
		
	
	$("#chooseUsers").click(function(){
		selectUsers();
	});
	
});


function validate()
{    
    var isSuccess=$("#updateFrm").validate().form();  //.form(); 返回是否验证成功
	  if(isSuccess){
		showLoading("正在保存，请稍候...");
	}
     return isSuccess;
}

function selectUsers(){
	var url="${ctx}/admin/pri/task/getAdminUsers.action?meetingId="+$("#meetingId").val()+"&adminIds="+$("#adminIds").val();
	//alert(url);
	window.open(url,'','width=800,height=500,left=200,top=200,scrollbars=yes,location=no');
}


function add(){	
	function callback(data){
		hideLoading();
		alert(data.retMsg);
		returnList(${detail.meetingId});	
	}
	
	function returnList(meetingId){
		window.location.href = "${ctx}/admin/pri/task/getDetailListPager.action?meetingId="+meetingId;
	}
	var url="${ctx}/admin/pri/task/updateDetail.action";
	
	var options ={ 
			beforeSubmit: validate,
	        url:url,
	        success:callback, 
	        type:'post',      
	        dataType:'json'
	    };
	
	$('#updateFrm').ajaxSubmit(options);
	
}




</script>
</head>
<body>
	<div class="page_title">
		<h3>添加任务  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">	
		<div title="任务列表" link="${ctx}/admin/pri/task/getDetailListPager.action?meetingId=${detail.meetingId}" style="padding:10px;"></div>
		<div title="我的任务" link="${ctx}/admin/pri/task/getDetailListPager.action?meetingId=${detail.meetingId}&myTaskFlag=1" style="padding:10px;"></div>
		<div title="编辑任务" selected="true" style="padding:10px;"></div>
	</div>
	
	<div class="page_form">
	<form id="updateFrm" action="${ctx}/admin/pri/task/updateDetail.action" method="post">
	<input type="hidden" id="meetingId" name="meetingId" value="${detail.meetingId}"/>
	    <fieldset>
	        <legend>基本信息</legend>
	        <input type="hidden"  id="id" name="id" value="${detail.id}" ></input>	
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>名&nbsp;&nbsp;&nbsp;称：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" id="detailName" name="detailName" value="${detail.detailName}" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>	
	        
	       <dl>
                <dt><label for="sendAllFlag">负责人:</label></dt>
                <dd>
                	 <textarea name="recieverMobiles" id="recieverMobiles" readonly="readonly" disabled="disabled" style="width:600px;height:38px">${fn:substring(chargeNames,0,fn:length(chargeNames)-1)}</textarea>
                    <input type="hidden" name="adminIds" id="adminIds" value="${detail.charge}">
                    <a href="#"  id="chooseUsers" class="btn_common btn_false">选择成员</a>
                </dd>
            </dl>
            
	        <dl>
	        	<dt>
	            	<label for="title">内容：</label>
	            </dt>
	            <dd>
	            	<textarea id="detailDescription" name="detailDescription"   tabindex="1"  style="width:600px;height:38px">${detail.detailDescription}</textarea>
	                <small></small>
	            </dd>
	        </dl>	
	        
	    
	    <dl>
        	<dt>
            	<label for="title"><font color="red"></font>开始时间：</label>
            </dt>
            <dd>
            	<input id="executeStartTime" type="text" name="executeStartTime"  value="${fn:substring(detail.executeStartTime,0,16)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',executeStartTime:'%y-%M-%d %H:00:00',maxDate:'#F{$dp.$D(\'executeEndTime\')||\'2019-12-31 08:00\'}' })" readonly="readonly"/>
            	<small>请输入预计的任务开始时间</small>
            </dd>
        </dl>
        
        
        <dl>
        	<dt>
            	<label for="title"><font color="red"></font>结束时间：</label>
            </dt>
            <dd>
            	<input id="executeEndTime" type="text" name="executeEndTime"  value="${fn:substring(detail.executeEndTime,0,16)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',executeEndTime:'%y-%M-%d %H:00:00' })" readonly="readonly"/>
            	<small>请输入预计的任务结束时间</small>
            </dd>
       </dl>

       <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>状态：</label>
            </dt>
            
            <dd>
            	  	<select name="status">
						<%--<option value="">请选择</option> --%>
						
						<option value="0" <c:if test="${detail.status eq '0'}"> selected="selected"</c:if> >未开始</option>
						<option value="1" <c:if test="${detail.status eq '1'}"> selected="selected"</c:if> >开始</option>
						<option value="2" <c:if test="${detail.status eq '2'}"> selected="selected"</c:if> >进行中</option>
						<option value="3" <c:if test="${detail.status eq '3'}"> selected="selected"</c:if> >部分完成</option>
						<option value="4" <c:if test="${detail.status eq '4'}"> selected="selected"</c:if> >完成</option>
		            </select>
           </dd>
       </dl>
       
	    </fieldset>
	    <div class="neidi">&nbsp;</div>
	    <div class="page_form_sub">
	        <a href="#" onclick="add();" id="addUserBtn" class="btn_common btn_true">保 存</a>　
	        <a href="/meeting/admin/pri/task/getDetailListPager.action?meetingId=${detail.meetingId}"  class="btn_common btn_true">返回列表</a>　
	    </div>
	   
	</form>
	</div>

</body>
</html>