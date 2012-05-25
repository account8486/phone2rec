<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
	<%@ include file="/pages/portal/common/header.jsp" %>
	
	<div class="w960">
        <div class="cbox">
        	<div class="title"><h5 id="caption" value="详情"></h5></div>
        </div>
	</div>
	
	<div class="container w960">
		<div class="goods">
			<div class="pic"><img src="${serverUrl}${gift.imgUrl }" /></div>
		</div>
		
		<div class="goods" style="width:300px;">
			<div class="description">
				<div class="title f16">${gift.name }</div>
				<div class="price" style="margin-top:10px;"><span class="money">￥<fmt:formatNumber value="${gift.price }" type="currency" pattern="#0.00元"/></span></div>
			</div>
			
			<div style="margin-top:10px;" >
				<form id="mainForm" action="${ctx}/portal/pri/gift/order.action">
					<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
					<input type="hidden" id="giftId" name="gift.id" value="${gift.id}"/>
					<input type="hidden" id="giftPrice" name="gift.price" value="${gift.price}"/>
					<input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
					数量&nbsp;<input type="text" id="amount" name="giftOrder.amount" style="width:40px;" value="" maxlength="6"/>&nbsp;件
					<font id="tip_amount" style="line-height:35px" color="red"></font>
					<br/>
					<a style="margin-top:10px;" href="#" class="btn_blue" id="orderBtn">立刻订购</a>
					<a class="btn_blue" href="${ctx}/portal/pri/gift/list.action?meetingId=${meetingId}&menu_id=${param.menu_id}">返回列表</a>
				</form>
			</div>
			<div class="description" style="margin-top:10px;">
				<div class="comments">${gift.introduction }</div>
			
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
		    if(!bool || parseInt(amount) == 0){
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