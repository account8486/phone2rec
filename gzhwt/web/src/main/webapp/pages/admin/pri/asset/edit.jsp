<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>资产管理</title>
	${admin_css} ${jquery_js} ${jquery_form_js} ${util_js} ${WdatePicker_js}
</head>
<body>
	<div class="page_title">
		<h3>资产管理  -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form">
	<form id="addForm" action="" method="post">
		<input type="hidden" id="asset.id" name="asset.id"  value="${asset.id}" />
		<c:set var="meetingId" value="${empty param.meetingId ? asset.meetingId : param.meetingId}"></c:set>
		<input   type="hidden" id="asset.meetingId" name="asset.meetingId"  value="${meetingId}" />
		<s:token />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>资产编号：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="asset_no" tabindex="1" name="asset.asset_no" value="${asset.asset_no}" maxlength="20"/>
	            	<font id="tip_asset_no" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>资产名称：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="asset_name" tabindex="1" name="asset.asset_name" value="${asset.asset_name}" maxlength="25"/>
	            	<font id="tip_asset_name" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">&nbsp;属性：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="property" tabindex="2" name="asset.property" value="${asset.property}" maxlength="128"/>
	            	<font id="tip_property" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>价值(元)：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="asset_value" tabindex="3" name="asset.asset_value" value="${asset.asset_value}" maxlength="9" onkeyup="value=value.replace(/[^\d]/g,'')"/>
	            	<font id="tip_asset_value" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">购入日期：</label>
	            </dt>
	            <dd>
					<fmt:formatDate value="${asset.storage_date}" pattern="yyyy-MM-dd" var="storage_date" />
	            	<input type="text" id="storage_date" tabindex="4" readonly="readonly" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'%y-%M-%d'})" name="asset.storage_date" value="${storage_date}" maxlength="10" />
	            	<font id="tip_storage_date" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">备&nbsp;&nbsp;注：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="remark" tabindex="5" name="asset.remark" value="${asset.remark}" maxlength="64"/>
					<font id="tip_remark" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg}</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	    	<div style="margin-bottom: 10px; margin-top: 10px;" align="left">
	       		 <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>
	       		 <a href="javascript:returnList('${meetingId }');" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	        </div>
	    </div>
	</form>
	</div>
	<script type="text/javascript">
		/*
		* 页面输入校验
		*/
		function checkInputInfo(){
			var isRight = true;
			//资产编号
		    var asset_no = $("#asset_no").val();
		    if (isEmpty(asset_no)) {
		        $("#tip_asset_no").html("请输入资产编号。");
		        $("#tip_asset_no").show();
		        isRight = false;
		    } else if (!/^[0-9A-Za-z]+$/.test(asset_no)){
		        $("#tip_asset_no").html("请请输入正确的资产编号(字母或数字)。");
		        $("#tip_asset_no").show();
		        isRight = false;
		    }
			//资产名称
		    var asset_name = $("#asset_name").val();
		    if (isEmpty(asset_name)) {
		        $("#tip_asset_name").html("请输入资产名称。");
		        $("#tip_asset_name").show();
		        isRight = false;
		    }
			//价值
		    var asset_value = $("#asset_value").val();
		    if (isEmpty(asset_value)) {
		        $("#tip_asset_value").html("请输入价值。");
		        $("#tip_asset_value").show();
		        isRight = false;
		    } else if (!/^\d+(\.\d{1,2})?$/.test(asset_value)){
		        $("#tip_asset_value").html("请请输入正确的价值(两位小数)。");
		        $("#tip_asset_value").show();
		        isRight = false;
		    }
			
			return isRight;
		}

		function save(){
			$("[id^='tip_']").hide();
			//页面输入校验
			if (!checkInputInfo()) {
				return;
			}
			//$("#addForm").submit();
			$("#submitBtn").attr("disabled","disabled");
			$("#retUserListBtn").attr("disabled","disabled");
			function callback(data){
				$("#submitBtn").removeAttr("disabled");
				$("#retUserListBtn").removeAttr("disabled");
				if(data.retCode == "0"){
					returnList(data.meetingId);
				} else {
			    	alert(data.retMsg);
				}
		    	
		    }
		    
			var options ={ 
			        url : "${ctx}/admin/pri/asset/save.action",
			        success : callback, 
			        type : 'post',      
			        dataType : 'json'
			    };
			
			$('#addForm').ajaxSubmit(options);
		}
		
		//回调函数
		function callback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){				
				returnList(data.meetingId);
			}
			document.getElementById("submitBtn").disabled = "enabled";
		}
		
		function returnList(meetingId){
			window.location.href = "${ctx}/admin/pri/asset/getAssetLst.action?meetingId=" + meetingId;
		}
		
</script>
</body>
</html>