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
	<style type="text/css">
	ul.user_check { margin:20px; zoom:1; display:block;}
	ul.user_check li { width:90px; overflow:hidden; margin:10px auto; float:left;}
	ul.user_check input { float:left;}
	ul.user_check label { float:left; line-height:12px; padding-left:5px;}
	
	.clear {
		float: none;
		clear: both;
	}
	
	.half{
		padding: 3px;width: 150px;
	}
	</style>
</head>
<body>
	<div class="page_title">
		<s:if test="flag=='edit'">
		<h3>修改奖项信息</h3>
		</s:if>
		<s:else>
		<h3>添加奖项信息</h3>
		</s:else>
	</div>
	
	<div>
	<form id="addLuckyForm" action="${ctx}/admin/pri/lucky/lucky_addLuckyDraw.action" method="post">
	    <input type="hidden"  name="meetingId" value="${meetingId}"/>
	    <input type="hidden"  name="id" value="${lucky.id}"/>
	    <input type="hidden"  name="currentPage" value="${currentPage}"/>
	    
	 
	       
	       			<div style="margin: 15px;">
	        	    <font color="red">* </font>奖项：
	            	<input class="half" type="text" id="name" name="name"  value="${lucky.name}" maxlength="20"/>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	            	<small>如：一等奖,二等奖...</small>
	           		</div>
	       
	       
	        		<div style="margin: 15px;">
	        		<font color="red">* </font>状态：
	            	<select name="state">
	            		<option value="1" ${lucky.state==1?"selected":"" }>启用</option>
	            		<option value="2" ${lucky.state==2?"selected":"" }>不启用</option>
            		</select>
	            	<font id="tip_state" style="line-height:35px" color="red"></font>
	            	 <small>启用可以在摇奖时使用</small>
	            	</div>
	       
	       			<div style="margin: 15px;">
	       			<font color="red">* </font>条件：
	            	 <select name="checked">
	            		<option value="2" ${lucky.checked==2?"selected":""}>否</option>
	            		<option value="1" ${lucky.checked==1?"selected":""}>是</option>
            		</select>
	            	 <small>是否只有参与互动交流的人员可以抽奖</small>
	            	</div>
	        
	        		<div style="margin: 15px;">
	        		 <font color="red">* </font>奖品：
	            	<input class="half" type="text" id="award" name="award"  value="${lucky.award}" maxlength="50"/>
	            	<font id="tip_award" style="line-height:35px" color="red"></font>
	            	 <small>表示获得此奖项的奖品</small>
	            	</div>
	        
	 
	    
	    
	    <div class="page_form_sub" align="left">
	        <a href="#" id="submitBtn" class="btn_common btn_true">保 存</a>
	        <a href="${ctx}/admin/pri/lucky/lucky_findAllLucky.action?meetingId=${meetingId}" id="retBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	    
		   <div id="result">
				 <c:forEach var="user" items="${list}" varStatus="status">
					 <c:if test="${status.index== 0 }">
					 <ul   class="user_check" >
						 <li>
					     			<input name="se"  type="checkbox"/>
					     			<label for="se">全选</label>
					     </li>
					 </ul>
					 <br></br>
					 </c:if>
					 <ul   class="user_check" name="check">
				     		<li>
				     			<input name="users" ${user.checked==1?"checked":""}  type="checkbox" value="${user.id}"/>
				     			<label for="users" title="${user.mobile }">${user.name}</label>
				     		</li>
			     	</ul>
		     	</c:forEach>
			</div>
	</form>
	</div>
<script type="text/javascript">
	$(function(){
		var flag="${flag}"
		if(flag=="edit"){
			$("#submitBtn").html("修改");
		}
		
		$("#submitBtn").click(function(){
			if(validateAdd()){
				if($("#result").css("display")=="none"){
					$("#result").empty();
				}
				if($("select[name=checked]").val()==2){
					var size=$("input[name=users]:checked").size();
					if(size<=1){
						alert("请至少选择一个参与抽奖人员");
						return ;
					}
				}
				$("#addLuckyForm").submit();
			}
		});
		
		
		$("select[name=checked]").change(function(){
			if($(this).val()==1){
				$("#result").hide();
			}else{
				if($("#result").find("*").length==0){
					var param={"meetingId":${meetingId}};
					$.post("${ctx}/admin/pri/lucky/lucky_findPagerMeetingUsers.action",param,function(data){
						$("#result").empty().append(data);
						
					});
				}
				$("#result").show();
			}
		});
		
		$("input[name=se]").click(function(){
			if($(this).attr("checked")=="checked"){
				$("ul[name=check] input").attr("checked",true);
			}else{
				$("ul[name=check] input").attr("checked",false);
			}
		});
		
		$("input[name=users]").click(function(){
			var size=$("input[name=users]").filter(":checked").size();
			if($("input[name=users]").size()==size){
				$("input[name=se]").attr("checked",true);
			}else{
				$("input[name=se]").attr("checked",false);
			}
			
			
		});
		
	});

	//新增校验
	function validateAdd() {
		var result = true;
	    var name = $("#name").val();
	
	    if (isEmpty(name)) {
	        $("#tip_name").html("请输入奖项名称");
	        $("#tip_name").show();
	        result = false;
	    }
	    return result;
	}
	
</script>

</body>
</html>