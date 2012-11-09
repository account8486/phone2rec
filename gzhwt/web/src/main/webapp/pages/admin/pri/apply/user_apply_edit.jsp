<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户管理</title>
	${admin_css}                                   
	${jquery_js}                          
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}         

</head>
<body>
	<div class="page_title">
		<h3>审批用户-- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form">
	<form id="editForm" action="${ctx}/admin/pri/apply/auditUserApply.action" method="post">
	<input   type="hidden" id="meeting.id" name="meeting.id"  value="${meeting.id}" />
	    <fieldset>
	        <legend>基本信息</legend>
	        
	       
	        
	          <dl>
	        	<dt>
	            	<label for="title">审批状态：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half"  value="${userApply.state eq 1 ?'审核成功':'未审核'}"   disabled="disabled"></input>
	            </dd>
	        </dl>	
	        
	                <dl>
	        	<dt>
	            	<label for="title">会议号：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half"  value="${userApply.meetingId}"   disabled="disabled"></input>
	            </dd>
	        </dl>	
	        
	        
	        <dl>
	        	<dt>
	            	<label for="title">手机号码：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half"  value="${userApply.mobile }"   disabled="disabled"></input>
	            </dd>
	        </dl>	
	        
	        <dl>
	        	<dt>
	            	<label for="title">姓名：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half"  value="${userApply.name }"   disabled="disabled"></input>
	            </dd>
	        </dl>	
	        
	       <dl>
	        	<dt>
	            	<label for="title">性别：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half"  value="${userApply.gender eq 0 ?'男':'女'}"  disabled="disabled"></input>
	            </dd>
	       </dl>	
	       
	          <dl>
	        	<dt>
	            	<label for="title">职务：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half"  value="${userApply.job}"  disabled="disabled"></input>
	            </dd>
	       </dl>
	       
	       
	         <dl>
	        	<dt>
	            	<label for="title">城市：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half"  value="${userApply.city}"  disabled="disabled"></input>
	            </dd>
	       </dl>
	       
	         <dl>
	        	<dt>
	            	<label for="title">邮箱：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half"  value="${userApply.mailbox}"  disabled="disabled"></input>
	            </dd>
	       </dl>
	       
	         <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>参会级别：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" style="width:100px"  id="memberLevel" name="memberLevel" tabindex="3">
						<option value="1">一级</option>
						<option value="2">二级</option>
						<option value="3">三级</option>
						<option value="4">四级</option>
						<option value="5">五级</option>
					</select>
	            </dd>
	        </dl>
	        
	        <dl>
                <dt><label for="messageContent"><font color="red">*</font>短信内容:</label></dt>
                <dd>
                    <textarea class="small" cols="100" rows="2" name="messageContent" id="messageContent" class="textbox">尊敬的({0})({4})，您好！普通电脑用户访问${serverUrl}portal，手机用户访问${serverUrl}wap，  输入手机号({2})、密码({3})，即可登录本次会议。会议编号为({1})，会议名称为({5})。</textarea>
                    <small><font color="red">形如({0}),({1}),({2})内容为模板参数,请不要修改  </font></small>
                </dd>
                  
            </dl>
	        	
	        
	        <input type="hidden" name="applyId"  id="applyId" value="${userApply.id}"/>
	               
	    </fieldset>
	    
	    <div class="page_form_sub">
	    	<c:if test="${userApply.state ne 1 }">
	        <a href="#" onclick="addUser();" id="addUserBtn" class="btn_common btn_true">审批通过</a>　</c:if>
	        <a href="${ctx}/admin/pri/apply/getUserApplyPager.action?meetingId=${userApply.meetingId}" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	</form>
	</div>
	<script type="text/javascript">
		function addUser(){	        
			$("#editForm").submit();
			//$("#addUserBtn").attr("disabled","disabled");
		}
		
	
	</script>	
</body>
</html>