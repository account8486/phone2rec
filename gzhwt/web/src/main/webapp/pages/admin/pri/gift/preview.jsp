<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>会务通平台</title>
	<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/css/gift.css" />
	<link href="${cssdir}/layout.css" rel="stylesheet" type="text/css" />
	${main_css}
	${style_css}                                   
	${jquery_js}
	${jquery_form_js}
	${util_js}
</head>
<body>
	<div class="container">
		<div class="w960">
	        <div class="cbox">
	        	<div class="title"><h5 id="caption">
	        	礼品详情
	        	</h5></div>
	        </div>
		</div>
		
			<div class="goods">
				<div class="pic"><img src="${serverUrl}${gift.imgUrl }" /></div>
			</div>
			
			<div class="goods" style="width:300px;">
				<div class="description">
					<div class="title f16">${gift.name }</div>
					<div class="price" style="margin-top:10px;"><span class="money">￥<fmt:formatNumber value="${gift.price }" type="currency" pattern="#0.00元"/></span></div>
					<div class="comments" style="margin-top:10px;">${gift.introduction }</div>
				
				</div>
				
				<div style="margin-top:15px;" >
				<form id="mainForm" action="${ctx}/portal/pri/gift/order.action">
						<input type="hidden" id="giftId" name="gift.id" value="${gift.id}"/>
						<input type="hidden" id="giftPrice" name="gift.price" value="${gift.price}"/>
				</form>
				</div>
			</div>
		
		<div class="clear"></div>
	</div>
<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">

	// 验证
	function validate() {
		var result = true;
	    var amount = $("#amount").val();
	
	    if (isEmpty(amount)) {
	        $("#tip_amount").html("请输入订购数量。");
	        $("#tip_amount").show();
	        result = false;
	    }else{
		    var bool = /^\d{1,6}$/.test(amount);
		    if(!bool){
		        $("#tip_amount").html("请输入6位以内整数订购数量。");
		        $("#tip_amount").show();
		        result = false;
		    }
	    }
	    
	    return result;
	}
	
	$(document).ready(function(){
		// 查询
		$("#orderBtn").click(function(){

			$("[id^='tip_']").hide();
		   	var tmp_bool = validate();
		    if (tmp_bool != true) {
		        return false;
		    }
		    
			$("#mainForm").submit();
			$("#submitBtn").attr("disabled","disabled");
		});
	});
</script>
</body>
</html>