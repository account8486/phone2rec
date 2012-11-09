<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>参会人员</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	
	${admin_css} 
	${jquery_js} ${jmpopups_js} ${util_js}  ${WdatePicker_js} ${jquery_form_js}
	
	<style type="text/css">
		.page_form input[type="text"], input[type="password"], input[type="select"], input[type="search"], input[type="file"], textarea, select{
			margin:0;
		}
		.page_datalist, .page_datalist th, .page_datalist td{
			padding:5px;
		}
		
	</style>
   <script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>

	<div style="width: 75%;float:left;margin-left: 5px;margin-right: 10px;">
	
	<div class="page_title"><h3>参会人员 -- ${CURRENT_MEETING}</h3></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
	<input type="hidden" name="guideStep" value="step2" />
	<input type="hidden" id="stepValue" name="stepValue" value="2" />
		<div title="批量添加" style="padding:10px;" selected="true">
		<div>
	        <form id="batchAddForm" action=""  method="post">
		<table class="page_datalist" id="batch_add_table" >
	    	<thead>
				<tr>
					<th style="width:120px;">姓名</th>
					<th style="width:60px;">性别</th>
					<th style="width:100px;">手机号码</th>
					<th scope="col" >职务</th>
					<th scope="col" >邮箱</th>
					<th scope="col" width="100px">操作</th>
				</tr>	    	
	        </thead>
	        <tbody class="page_form">
	         <tr>
	         <!-- 姓名 -->	
	          <td align="left">
		         <input type="hidden" name="memberList[0].meetingMember.memberLevel" value="1">
		         <input type="hidden" name="memberList[0].meetingMember.vip" value="N">
		         <input type="hidden" name="memberList[0].meetingMember.addInContacts" value="Y">
		         <input type="hidden" name="memberList[0].meetingMember.jobIsDisplay" value="1">
		         <input type="hidden" name="memberList[0].meetingMember.mobileIsDisplay" value="1">
		         <input type="hidden" name="memberList[0].meetingMember.roomNumberIsDisplay" value="1">

	             <input type="text" id="userName" name="memberList[0].name"  maxlength="30" ></input>
	         </td>
              <td align="left"  >
				 <select class="inp_1" id="gender" name="memberList[0].gender" >
						<option value="0">男</option>
						<option value="1">女</option>
						<option value="2">保密</option>
					</select> 
	         </td>
	         
	          <!-- 手机号码 -->
              <td align="left"  >
				 <input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" id="mobile" name="memberList[0].mobile"  maxlength="11" ></input>
	         </td>
              <td align="left"  >
				 <input type="text"  name="memberList[0].meetingMember.bookJob" id="bookJob" maxlength="30" ></input>
	         </td>
              <td align="left"  >
				<input type="text" id="email"  name="memberList[0].meetingMember.mailbox"  maxlength="50" ></input>
	         </td>
	          
			 <td align="center"  style="width:100px">
					    <a id="addEl" href="javascript:void(0);" onClick="insertRow(this);">添加</a>
					    <a href="javascript:void(0);" onClick="delCurrentRow(this);">删除</a>
			 </td>
        </tr>
        
	  </tbody>
	    </table>
	    </form>
	    <div class="page_form_sub">
				<div style="margin-bottom: 10px; margin-top: 10px;padding-left: 200px;" align="center">
				   <a href="${ctx}/admin/pri/meeting/guide_step1.action?fromStep=step2&meeting.id=${meeting.id}" class="btn_common btn_true">上一步</a>
				   <a href="#" id="agendaAdd" class="btn_common btn_true">保存</a>
				   <a href="${ctx}/admin/pri/meeting/guide_step3.action?fromStep=step4&meeting.id=${meetingId}"  class="btn_common btn_true">下一步</a>
					<input type="hidden" value="${meetingId}" id="meetingId" />
				</div>
			</div>
		</div>		
		</div>
	</div>
	
	
	<div id="list">
	</div>
	
	</div>
	
	<jsp:include page="guideCommonRight.jsp" />
	<div style="clear: both"></div>
	

	 
	  
	<script type="text/javascript">
	$("#backMeeting").click(function(){
		doFinish();
	});
	
	function doFinish() {
		if(confirm("确定结束此会议向导操作吗？")) {
			location.href = "${ctx}/admin/pri/meeting/guide_finish.action?fromStep=step4&meeting.id=${meetingId}";
		}
	}
	/**
	*删除当前行
	*/
	function delCurrentRow(obj){
		 var tr = $(obj).parent().parent();
		 if($(tr).parent().find("tr").length == 1){
			 alert("最后一条不能删除！");
			 return;
		 }	
		 if( $(tr).parent().find("tr").length > 1 && confirm("确定删除这条记录?")){
			 $(tr).remove();
			 return ;
		 }
		 
	}
	
	
	var idx = 0;
	/**
	*复制当前行并插入到表格中去
	*/
	function insertRow(obj){
		var tr = $(obj).parent().parent();
		if(validateMyForm($(tr))){
			//深度复制一个tr,包括事件绑定属性
			var $clone = $(tr).clone(true);
			//找到当前List的索引值
			//var idx = $(tr).parent().children(":last").children(":first").html().match(/([[\d]+])/gi)[0].replace("[","").replace("]","");
			idx++;
			//找到TR下的所有input进行name的替换
			$($clone).find("td>input,select").each(function(i, elem){ 
			    $(elem).attr('name',$(elem).attr('name').replace(/([[\d]+])/gi,'['+idx+']'));
				//alert($(elem).attr('name'));
			});
			//保留select的值
			var selects = $(tr).find("select");
	        $(selects).each(function(i) {
	             var select = this;
	             $($clone).find("select").eq(i).val($(select).val());
	        });
			//添加到tr的后面
			$($clone).appendTo($(tr).parent());
		}
	}
	
	//验证form
	function validateMyForm(oldTr){
		//通过JQUERY选择器来获取对应的控件的值
		var userName= $("#userName", oldTr).val();
		if(userName==''){
			alert('请输入用户姓名！');
			return false;
		}
		var mobile= $("#mobile", oldTr).val();
		if(mobile==''){
			alert('请输入手机号！');
			return false;
		}
		
		var reg0 = /^1\d{10}$/;  
		if(mobile.length != 11 || ! reg0.test(mobile)){
			alert('请输入以1开头的11位手机号！');
			return false;
		}
		
		var email = $("#email", oldTr).val();
        if(email!="")  
        {  
	        var pattern =/^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/;  
            if(!pattern.exec(email))  
            {  
             alert('请输入正确的邮箱地址');  
             return false;
            }  
        }
		
        
        
        
		
		return true;
	}
	
	$(document).ready(
		function() {
			loadList();
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
			
			$('#agendaAdd').click(function(){
				//对每一行的数据进行验证
				var trs = $('#batchAddForm').find("tr");
				for(var i = 1;i<trs.length;i++){
					if(!validateMyForm($(trs[i]))){
						return false;
					}
				}
				
				var url = '${ctx}/pages/admin/pri/meeting/batchAddMeetingUser.action?meetingId=${meeting.id}';
				var options = { 
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'         
			    };
				$('#batchAddForm').ajaxSubmit(options);
			});
			
			function callback(data){
				alert(data.saveMsgTips);
				loadList();
			}
			
			//timeStamp=" + 
			function loadList(){
				var timeStamp=new Date().getTime();
				var url="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?from=guide&isAdmin=1&meeting.id=${meetingId}&timeStamp="+timeStamp;
				$("#list").load(url);
			}
			
		}
	);
	

	</script>
</body>
</html>