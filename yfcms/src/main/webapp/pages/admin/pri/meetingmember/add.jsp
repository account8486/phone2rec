<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户新增与编辑</title>
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
					showLoading(title);
					return false;
				}
			}  
		});
		
	$("#addInContacts").change(function(){
		//如果为不加入 那么不显示
		if($("#addInContacts").val()=='N'){
			$("#mobileIsDisplay").val('0');
			$("#roomNumberIsDisplay").val('0');
		}else{
			$("#mobileIsDisplay").val('1');
			$("#roomNumberIsDisplay").val('1');
		}	
	});
	
	
});


</script>
</head>
<body>
	<div class="page_title">
		<h3>添加用户  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">	
	
	<div title="用户列表" link="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}" style="padding:10px;"></div>
	<div title="添加用户" selected="true" style="padding:10px;"></div>
	<div title="导入用户" link="${ctx}/admin/pri/import/preImportUser.action?meetingId=${meeting.id}" style="padding:10px;"></div>
	<div title="临时加入审批" link="${ctx}/admin/pri/apply/getUserApplyPager.action?meetingId=${meeting.id}" style="padding:10px;"></div>	
	</div>
	
	<div class="page_form">
	<form id="addUserForm" action="${ctx}/pages/admin/pri/meeting/saveMeetingUser.action" method="post">
	<input   type="hidden" id="meeting.id" name="meeting.id"  value="${meeting.id}" />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>手机号码：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" onkeyup="value=value.replace(/[^\d]/g,'')" id="mobile" name="mobile" value="${member.mobile }"   <c:if test="${not empty member}">readonly="readonly"</c:if> tabindex="1" maxlength="11"></input>
	                <small>请输入11位手机号码</small>
	            </dd>
	        </dl>	
	        
	        
	        <c:if test="${not empty member}">
	         <dl>
	        	<dt>
	            	<label for="title">密码：</label>
	            </dt>
	            <dd>
	            	<input type="password" class="half" id="password" name="password" value="${member.password }" tabindex="1" maxlength="11"></input>
	                <small>如需要修改密码,在此处修改</small>
	            </dd>
	        </dl>	
	        </c:if>
	              
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>姓&nbsp;&nbsp;&nbsp;名：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" id="name" name="userName" value="${member.name }" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>	
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>参会级别：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="memberLevel" name="memberLevel" tabindex="3">
						<option value="1">一级</option>
						<option value="2">二级</option>
						<option value="3">三级</option>
						<option value="4">四级</option>
						<option value="5">五级</option>
					</select>
	            </dd>
	        </dl>	
	        
	       <dl>
	        	<dt>
	            	<label for="title"><font color="red"> </font>排序：</label>
	            </dt>
	              <dd>
	            	<input type="text" class="half" onkeyup="value=value.replace(/[^\d]/g,'')" id="sortCode" name="sortCode" style="width:45px;" value="${member.meetingMember.sortCode}" tabindex="3" maxlength="5"></input>	            	
	            	<small>为参会人员在通讯录中的位置，数字越小排名越靠前，不填则默认排到最后。</small>
	            </dd>
	           
	        </dl>	           
	    </fieldset>
	    
	      <fieldset>
	       <legend>通讯录设置</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"> </font>加入通讯录：</label>
	            </dt>
	              <dd>
	       		   <select class="inp_1" id="addInContacts" name="addInContacts" tabindex="3">
	                <option value="Y" <c:if test="${member.meetingMember.addInContacts eq 'Y'}">selected="selected"</c:if> >加入</option>
	                <option value="N" <c:if test="${member.meetingMember.addInContacts eq 'N'}">selected="selected"</c:if> > <font style="color:red;">不加入</font></option>
	         
	      
	            	</select>            	
	            </dd>
	       </dl>	
	        
	        
	       <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>显示手机号码：</label>
	            </dt>
	              <dd>
	                <select class="inp_1" id="mobileIsDisplay" name="mobileIsDisplay" tabindex="3">
	                
	                <option value="1" <c:if test="${member.meetingMember.mobileIsDisplay eq 1 }">selected="selected"</c:if> >显示</option>
	                <option value="0" <c:if test="${member.meetingMember.mobileIsDisplay eq 0 }">selected="selected"</c:if> ><font style="color:red;">不显示</font></option>
	               
	            	</select>            	
	            </dd>
	       </dl>
	       
	       
	       <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>显示房间号：</label>
	            </dt>
	              <dd>
	                <select class="inp_1" id="roomNumberIsDisplay" name="roomNumberIsDisplay" tabindex="3">
	                
	                <option value="1" <c:if test="${member.meetingMember.roomNumberIsDisplay eq 1 }">selected="selected"</c:if> >显示</option>
	                <option value="0" <c:if test="${member.meetingMember.roomNumberIsDisplay eq 0 }">selected="selected"</c:if> ><font style="color:red;">不显示</font></option>
	               
	            	</select>            	
	            </dd>
	       </dl>
	       
	       	
	       
	       
	      </fieldset>
	    
	    
	    <fieldset>
	        <legend>其他信息</legend>
	        
	         <dl>
	        	<dt>
	            	<label for="title"><font color="red"> </font>单&nbsp;&nbsp;&nbsp;位：</label>
	            </dt>
	              <dd>
	            	<input type="text" class="half" id="department" name="department"  value="${member.meetingMember.department}" tabindex="3" maxlength="20"></input>	            	
	            	<small></small>
	            </dd>
	        </dl>
	        
	        
	        <dl>
	        	<dt>
	            	<label for="title">职&nbsp;&nbsp;&nbsp;位：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" name="bookJob" id="bookJob" value="${member.meetingMember.bookJob }" maxlength="30" tabindex="4"></input>
	            	<small>显示在通讯录信息上,可以有多个职位</small>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title">职位简称：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" name="job" value="${member.meetingMember.job }" maxlength="30" tabindex="4"></input>
	            	<small>用户分组,就餐,发短信功能使用，用于领导的尊称</small>
	            </dd>
	        </dl>
	       
	       <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>显示职位简称：</label>
	            </dt>
	              <dd>
	                <select class="inp_1" id="jobIsDisplay" name="jobIsDisplay" tabindex="3">
	               
	                <option value="0" <c:if test="${member.meetingMember.jobIsDisplay eq 0 }">selected="selected"</c:if> ><font style="color:red;">不显示</font></option>
	                <option value="1" <c:if test="${member.meetingMember.jobIsDisplay eq 1 }">selected="selected"</c:if> >显示</option>
	                
	            	</select>            	
	            </dd>
	       </dl>
	        
	      
	 
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"> </font>房间号：</label>
	            </dt>
	              <dd>
	            	<input type="text" class="half" id="roomNumber" name="roomNumber" style="width:200px;" value="${member.meetingMember.roomNumber}" tabindex="3" maxlength="20"></input>	            	
	            	<small>住宿信息</small>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title">性&nbsp;&nbsp;&nbsp;别：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="gender" name="gender" tabindex="5">
						<option value="0">男</option>
						<option value="1">女</option>
						<option value="2">保密</option>
						<option value=""></option>
					</select>
	            </dd>
	        </dl>	     
	        <dl>
	        	<dt>
	            	<label for="title">生&nbsp;&nbsp;&nbsp;日：</label>
	            </dt>
	            <dd>
	            	<input id="birthday" type="text" name="birthday"  value="${fn:substring(member.birthday,0,10)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd'})" readonly="readonly" tabindex="6"/>
	            </dd>	           
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">城&nbsp;&nbsp;&nbsp;市：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" id="city" name="city" value="${member.meetingMember.city}" tabindex="2" maxlength="8" tabindex="7"></input>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">邮&nbsp;&nbsp;&nbsp;箱：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" name="mailbox" value="${member.meetingMember.mailbox }" maxlength="50"  tabindex="8"></input>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">地&nbsp;&nbsp;&nbsp;址：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" name="address" value="${member.meetingMember.address }" maxlength="100" tabindex="9"></input>
	            	<small>地址请限制在100个字符以内</small>
	            </dd>
	        </dl>	       
	    </fieldset>
	    <div class="neidi">&nbsp;</div>
	    <div class="page_form_sub">
	        <a href="#" onclick="addUser();" id="addUserBtn" class="btn_common btn_true">保 存</a>　
	        <a href="javascript:retUserList();" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	   
	</form>
	</div>
	<script type="text/javascript">
		// 新增校验
		
		$().ready(function() {
			$("#memberLevel").val("${member_level}");
			$("#gender").val("${member.gender}");
			//alert('${meeting.id}');
			
			
			$("#addUserForm").validate({
				onkeyup: false,
				rules: {
					"mobile": {
						required :true,
						minlength: 11,
						maxlength: 11,
						digits: true,
						isMobile:true
					},
					"userName": "required",
					"mailbox":{
						email:true
					},
					"sortCode":{
						digits: true
					}
				},
				messages: {
					"mobile": "请输入以数字1开头的11位手机号码！",	
					"userName": "请输入参会人员姓名！",
					"sortCode":"请输入用户排序数字!"
				}
			});
			
		});
		function addUser(){	        
			$("#addUserForm").submit();
			//$("#addUserBtn").attr("disabled","disabled");
		}
		
		jQuery.validator.addMethod("isMobile", function(value, element) {
		    var mobileVal=$("#mobile").val();
		    if(mobileVal!=''){
		    	if(mobileVal.substr(0,1)!='1'){
		    		  return false;
		    	}
		    }
			return true;
		}, "手机号码首字母必须为数字1");

		function retUserList()
		{
			window.location.href = "${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?isAdmin=1&meeting.id=${meeting.id}";
		}
	</script>	
</body>
</html>