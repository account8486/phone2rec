<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>短信通知</title>
    ${admin_css} ${jquery_js} ${jquery_form_js} ${WdatePicker_js} ${jmpopups_js} ${util_js}   
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>    
    <script type="text/javascript" src="${ctx}/js/ajax-public.js"></script>
    <script language="JavaScript" type="text/javascript">
	$(document).ready(function() {
		$('.easyui-tabs').tabs({  
			onSelect:function(title){  
				var tab = $(this).tabs('getTab', title); 
				var href = tab.attr('link');
				if (href) {
					location.href = href;
					showLoading(title);
					return false;
				}
			}  
		}); 
		
		$("#send_message_tips").click(function(){
			doSendSms();
		});
	});	


	function  doSendSms(){
		url= '${ctx}/admin/pri/journey/addMessageTip.action';
		
		var options = { 
		        url:	   url,
		        success:   callback, 
		        type:      'post',      
		        dataType:  'json'
		    };
			//提交给后台，进行数据的插入
			//showLoading("短信正在发送中......");
		$('#sendMsgFrm').ajaxSubmit(options);
		showLoading("短信正在发送中......");
		
		
		function callback(data){
			hideLoading();
			alert(data.msg);
			
		}
			
		
	}	
	
</script>

<style>
	.textbox {
		BACKGROUND: #FFFFFF;
		BORDER-TOP: #7F9DB9 1px solid;
		BORDER-LEFT: #7F9DB9 1px solid;
		BORDER-RIGHT: #7F9DB9 1px solid;
		BORDER-BOTTOM: #7F9DB9 1px solid;
		FONT-FAMILY: "宋体", "Verdana", "Arial", "Helvetica";
		FONT-SIZE: 12px;
		TEXT-ALIGN: LEFT;
	}
</style>
</head>
<body>
    <!-- title -->
	<div class="mainbox"><div class=page_title><h3>短信通知  -- ${CURRENT_MEETING}</h3></div></div>

	<div class="easyui-tabs" border="false" style="padding:10px;">
	
		<div title="车辆管理"  link="${ctx}/admin/pri/journey/listVehicle.action?meetingId=${meetingId}"   style="padding:10px;"></div>
		<div title="司机管理" link="${ctx}/admin/pri/journey/listVehicleDriver.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="行程管理" link="${ctx}/admin/pri/journey/listJourney.action?meetingId=${meetingId}" style="padding:10px;"></div>	
		
		<div title="短信通知" style="padding:10px;" selected="true">
		<div class="page_form">
		<form  name="sendMsgFrm" id="sendMsgFrm" method="post">
        <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
        <fieldset>
            <legend></legend>
            <dl>
                <dt><label for="messageContent"><font color="red">*</font>短信内容:</label></dt>
                <dd>
                    <textarea class="small" cols="100" rows="1" name="messageContent" id="messageContent" class="textbox">司机({name})同志，请你在({startTime})前从({fromAddress})开车去({toAddress})接送客人：({passengers})</textarea>
               	    <small><font color="red">形如({name})内容为模板参数,请不要修改  </font></small>
                </dd>
            </dl>
            
            
             <dl>
                <dt><label for="sendTime">定时发送时间:</label></dt>
                <dd>
                    <input type="checkbox" name="isTimingSend" id="isTimingSend" value="Y"/>
                    <input id="sendTime" type="text" name="sendTime" value="${sendTime}" class="Wdate" style="width:170px;"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"/>
                            <small><font color="red">勾选将会在您所指定的时间发送短信。未勾选,则即时发送。</font></small>
                </dd>
            </dl>
            
            <div id="userDiv">
             <dl>
                <dt><label for="sendAllFlag">收信人:</label></dt>
                <dd>
                     <c:choose>
	            	 <c:when test="${not empty journeyList}">
                        <c:forEach var="journeyView" items="${journeyList}" varStatus="status">
                        
                        <c:if test="${not empty  journeyView.driverMobile}">
                        	<input type="checkbox" name="journeyId" value="${journeyView.journeyId}"/> ${journeyView.name} (${journeyView.driverName}:${journeyView.driverMobile}) <br>
            			</c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                    		暂无可以发短信提醒的行程！
                    </c:otherwise>
                    </c:choose>
                </dd>
            </dl>
			</div>
           </fieldset>
           
           
        <div align="center" class="page_form_sub">
   			<a href="#"  id="send_message_tips" class="btn_common btn_true">发  送</a>
        </div>
		</form>
		</div>
		</div>
	</div>
</body>
</html>