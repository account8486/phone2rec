<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>机构管理</title>
	${admin_css}                                   
	${jquery_js}
	<script type="text/javascript" src="${ctx}/js/util.js"></script>
	<script type="text/javascript">
		//新增校验
		function validateAdd() {
			var result = true;
		    var name = $("#name").val();
		
		    if (isEmpty(name)) {
		        $("#tip_name").html("请输入机构名称。");
		        $("#tip_name").show();
		        result = false;
		    }
		    
		    var level = $("#level").val();
		
		    if (isEmpty(level)) {
		        $("#tip_level").html("请选择机构级别。");
		        $("#tip_level").show();
		        result = false;
		    }
		    
		    var comments = $("#comments").val();
			
		    if (!isEmpty(comments)) {
		    	if(comments.length > 400) {
		    		$("#tip_comments").html("备注不能超过400个字符。");
		        	$("#tip_comments").show();
		        	result = false;
		    	}
		    }
		
		    return result;
		}
		
		function addOrg(){
			$("[id^='tip_']").hide();
	       	var tmp_bool = validateAdd();
	        if (tmp_bool != true) {
	            return false;
	        }
	        
			$("#addOrgForm").submit();
			$("#addOrgBtn").attr("disabled","disabled");
		}
		
		function retOrgList()
		{
			window.location.href = "${ctx}/pages/admin/pri/org/listAndTree.jsp";
		}
	</script>
</head>
<body>
	<div class="page_title">
		<h3>添加组织</h3>
	</div>
	<div class="page_form">
	<form id="addOrgForm" action="${ctx}/admin/pri/org/add.action" method="post">
		<fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>组织名称：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" id="name" name="org.name" value="${org.name }" tabindex="1" maxlength="100"></input>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	                <small>请输入组织机构名称。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>上级组织：</label>
	            </dt>
	            <dd>
	            	<font style="line-height:35px">${pOrg.name }</font>
			    	<input type="hidden" id="parentId" name="org.parentId" value="${pOrg.id}"/>
			    	<input type="hidden" name="pOrgId" value="${pOrg.id}"/>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>组织级别：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="level" name="org.level" tabindex="3" style="width:15.5%">
						<c:if test="${orgLevel <= 0}"><option value="1">一级</option></c:if>
						<c:if test="${orgLevel <= 1}"><option value="2">二级</option></c:if>
						<c:if test="${orgLevel <= 2}"><option value="3">三级</option></c:if>
						<c:if test="${orgLevel <= 3}"><option value="4">四级</option></c:if>
			    	</select>
			    	<font id="tip_level" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">联&nbsp;&nbsp;系&nbsp;人：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="quart" id="linker" name="org.linker" value="${org.linker }" tabindex="4"  maxlength="50"></input>
			    	<font id="tip_linker" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">联系电话：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="quart" id="linkerTel" name="org.linkerTel" value="${org.linkerTel }" tabindex="5" maxlength="30"></input>
			    	<font id="tip_linkerTel" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" id="org.address" name="org.address" value="${org.address }" tabindex="6" maxlength="200"></input>
			    	<font id="tip_address" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
	            </dt>
	            <dd>
	            	<textarea id="comments" class="medium" name="org.comments" rows="5" tabindex="7">${org.comments }</textarea>
	            	<font id="tip_comments" style="line-height:35px" color="red"></font>
	            	<small>备注不能超过400个字符。</small>
	            </dd>
	        </dl>
	    </fieldset>
		    
		<div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="addOrg();" id="addOrgBtn" class="btn_common btn_true">保 存</a>　<a href="javascript:retOrgList();" id="retOrgListBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	</form>
	</div>
</body>
</html>