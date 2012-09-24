<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>新增与编辑</title>
	${admin_css}                                   
	${jquery_js}                          
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}   
	${jmpopups_js} ${util_js}      
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>

</head>
<body>
	<div class="page_title">
		<h3></h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">	
	<div title="菜单列表" link="${ctx}/admin/pri/unit/list.action" style="padding:10px;"></div>
	<div title="菜单编辑" selected="true"  style="padding:10px;"></div>
	</div>
	
	<div class="page_form">
	<form id="addFrm"  method="post">
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font> 名称：</label>
	            </dt>
	            <dd>
	            	<input type="text"  class="half" id="unitName" name="unit.unitName" value="" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>角色描述：</label>
	            </dt>
	            <dd>
	            	<input type="text"  class="half" id="unitDescription" name="unit.unitDescription" tabindex="2" maxlength="30" ></input>	 
	            </dd>
	        </dl>
	       
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>菜单类型：</label>
	           </dt>
	            
	        
	            <dd>
	            	<select name="unit.unitType" id="unitType" >
					<option value="">未选择</option>
					<option value="0">一级菜单</option>
					<option value="1">二级菜单</option>
					</select>	 
	            </dd> 
	            
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"> </font>一级模块：</label>
	            </dt>
	            
	            <dd>
	            
	            
	            <select name="unit.unitParentId" id="unitParentId">
					<option value="">未选择</option>
					<%--采用循环的方式来读出数据库中的一级模块(一级菜单)的信息 --%>
					<c:if test="${not empty unitParentList }">
					 <c:forEach var="pUnit" items="${unitParentList}" varStatus="status" >
					 <option value="${pUnit.id}">${pUnit.unitName}</option>
					 </c:forEach>
					</c:if>
				</select>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>URL：</label>
	            </dt>
	            
	            <dd>
	            	<input class="full" name="unit.unitUrl" id="unitUrl"/>
	            </dd>
	        </dl>
	        
	       <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>排序码：</label>
	            </dt>
	            
	            <dd>
	            	<input class="half" name="unit.orderCode" id="orderCode"  />
	            </dd>
	        </dl>
	    </fieldset>
	    <div class="page_form_sub">
	    	  <a href="#" onclick="toUpdateUnit()"  class="btn_common btn_true">保 存</a>　
	    	  <a href="${ctx}/admin/pri/unit/list.action"  class="btn_common btn_true">返回列表</a>　
	    </div>
	</form>
	</div>
	
	<script type="text/javascript">
		// 新增校验
		$().ready(function() {
			
		});
		function toUpdateUnit(){
			//$("#addFrm").submit();
			var url="${ctx}/admin/pri/unit/addUnit.action";
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
</body>
</html>


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
		
		$("#unitParentId").val(${parentSecurityUnit.id});
		$("#unitType").val(1);
		
		
    });
	
</script>

