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
	
	
	function addRole(){
		//$("#addFrm").submit();
		var url="${ctx}/admin/pri/security/addRole.action";
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
    }
    
    


</script>
</head>
<body>
	<div class="page_title">
		<h3></h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">	
	
	
	<div title="角色列表" link="${ctx}/admin/pri/security/list.action" style="padding:10px;"></div>
	<div title="新增角色" selected="true"  style="padding:10px;">
	
	<div class="page_form">
	<form id="addFrm"  method="post">
	
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl style="width:48%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>角色名称：</label>
	            </dt>
	            <dd style="width:40%">
	            	<input type="text"  class="half" id="roleName" name="roleName" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>
	        <dl style="width:48%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>角色描述：</label>
	            </dt>
	            <dd  style="width:40%" >
	            	<input type="text"  class="half" id="roleDescription" name="roleDescription" tabindex="2" maxlength="30"></input>	 
	            </dd>
	        </dl>	
	        
	        <dl>
	          <div style="left:200px">
	      <c:if test="${not empty unitViewList }">
	      		<c:forEach var="securityUnitView" items="${unitViewList}" varStatus="status">
	      	          <input id="" name="unitId" type="checkbox" value="${securityUnitView.securityParentUnit.id}"/>${securityUnitView.securityParentUnit.unitName}<br />
	      	            <c:if test="${not empty securityUnitView.sonUnitList}">
	      	          	<c:forEach var="sonUnit" items="${securityUnitView.sonUnitList}" varStatus="status">
	      	          	   &nbsp;&nbsp;&nbsp; <input name="unitId"  type="checkbox" value="${sonUnit.id}"/>${sonUnit.unitName} <br />
	      	          	</c:forEach>
	      	          </c:if>
	      	 </c:forEach>
	      </c:if>
	    </div>   </dl>
	    
	    
	    
	    </fieldset>
	    
	    
	  
	       
	    <div class="page_form_sub">
	    	  <a href="#" onclick="addRole()" id="addUserBtn" class="btn_common btn_true">保 存</a>　
	    </div>
	   
	</form>
	</div>
	</div>

	</div>
	

</body>
</html>