<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>短信发送</title>
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
	});	

    function sbMsgFrm() {
        //使用AJAX请求会议是否短信超过指定条数
        // location.reload();
        var sendAllFlag = document.getElementsByName("sendAllFlag");
        var meetingId=$("#meetingId").val();
        var isSelectAll=0;
        if($("#sendAllFlag").attr("checked")){
        	isSelectAll=1;
        }
        var recieverIds=$("#recieverIds").val();
        //如果未全选中的话
        if (!($("#sendAllFlag").attr("checked"))) {
            var ids = getCheckBoxID();
            if (ids == '') {
                alert('请选择短信接收人');
                return;
            }
            //赋值给隐藏变量
            $("#userIds").val(ids);
        }
        
        var url="/admin/pri/message/checkSmsSendStatus.action?meetingId="+meetingId+"&isSelectAll="+isSelectAll+"&recieverIds="+recieverIds;
        
        function  doSendSms(data){
        	if(data.sendStatus){
        		//提交给后台，进行数据的插入
                showLoading("短信正在发送中......");
                $("#sendMsgFrm").submit();
        	}else{
        		alert(data.message);
        	}
        }
        ajaxRequest(url, doSendSms);
    }

    /**
     * 处理DIV为了好看
     */
    function dealDiv() {
        var sendAllFlag = document.getElementsByName("sendAllFlag");
        if ($("#sendAllFlag").attr("checked")) {
            //隐藏
            $("#userDiv").hide();
        } else {
            //显示
            $("#userDiv").show();
        }
    }


    function getCheckBoxID() {
        var retString = "";
        retString=$("#recieverIds").val();
        return retString;
    }

	function selectUsers(){
		var recieverIds=$("#recieverIds").val();
		var url="${ctx}/admin/pri/message/getMeetingMember.action?meetingId="+$("#meetingId").val()+"&recieverIds="+$("#recieverIds").val();
		window.open(url,'','width=800,height=500,left=200,top=200,scrollbars=yes,location=no');
	}

	/**按组选择成员*/
	function selectGroupUsers(){
		var recieverIds=$("#recieverIds").val();
		var url="${ctx}/admin/pri/group/selectGroupSms.action?groupPlan.id=${groupPlan.id}&meetingId="+$("#meetingId").val()+"&recieverIds="+$("#recieverIds").val();
		window.open(url,'','width=800,height=500,left=200,top=200,scrollbars=yes,location=no');
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
		TEXT-ALIGN: LIFT;
	}
</style>
</head>
<body>
    <!-- title -->
	<div class="mainbox"><div class=page_title><h3>短信管理  -- ${CURRENT_MEETING}</h3></div></div>

	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="短信列表" link="${ctx}/admin/pri/message/getMessageList.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="发送短信" style="padding:10px;" selected="true">
		
		<div class="page_form">
		<form action="${ctx}/admin/pri/message/doSendMessage.action" name="sendMsgFrm" id="sendMsgFrm" method="post">
        <input type="hidden" name="userIds" id="userIds"/>
        <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
        <fieldset>
            <legend>请根据模版填写短信发送内容(此会议下短信可发条数为:<span style="color:red">${preCount}</span>条,已发条数为:<span style="color:red">${smsActCount}</span>条,剩余短信条数为:<span style="color:red">${canSendCount}</span>条)</legend>
            <dl>
                <dt><label for="messageContent"><font color="red">*</font>短信内容:</label></dt>
                <dd>
                    <textarea class="small" cols="100" rows="2" name="messageContent" id="messageContent" class="textbox">尊敬的({0})({4}),您好!Android手机用户云会议客户端软件下载地址为${serverUrl}hyy.apk 普通电脑用户,可登陆网页${serverUrl}portal WAP用户访问网址${serverUrl}wap  输入手机号({2})、密码({3}),即可登录本次会议,会议编号为({1}).会议名称为({5}).</textarea>
               	    <small><font color="red">形如({0}),({1}),({2})内容为模板参数,请不要修改  </font></small>
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
            <dl>
                <dt><label for="sendAllFlag">发送全部成员:</label></dt>
                <dd>
                    <input type="checkbox" name="sendAllFlag" id="sendAllFlag" value="Y" onClick="dealDiv();"/>
                </dd>
            </dl>
            <div id="userDiv">
             <dl>
                <dt><label for="sendAllFlag">收信人:</label></dt>
                <dd>
                	 <textarea name="recieverMobiles" id="recieverMobiles" readonly="readonly" disabled="disabled" style="width:600px;height:38px"></textarea>
                    <input type="hidden" name="recieverIds" id="recieverIds">
                    <a href="#" onclick="selectUsers();" id="addUserBtn" class="btn_common btn_false">选择成员</a>
                    <a href="#" onclick="selectGroupUsers();" class="btn_common btn_false">按组发送</a>
                </dd>
            </dl>
			</div>
            <dl>
                <dt><span style="color:blue;">邀请模板:</span></dt>
                <dd>尊敬的({0})({4}),您好!Android手机用户云会议客户端软件下载地址为${serverUrl}hyy.apk 普通电脑用户,可登陆网页${serverUrl}portal WAP用户访问网址${serverUrl}wap  输入手机号({2})、密码({3}),即可登录本次会议,会议编号为({1}).会议名称为({5}).
                </dd>
            </dl>
            <dl>
                <dt><span style="color:blue;">就餐模板:</span></dt>
                <dd>尊敬的({0})({4}),您好，请于11:50到三楼北京厅，第三桌会餐。
                </dd>
            </dl>
            <dl>
                <dt><span style="color:blue;">分组模板:</span></dt>
                <dd>尊敬的({0})({4}),您好，请于9:50到三楼福建厅，第二桌进行分组讨论。
                </dd>
            </dl>
            </fieldset>
        <div align="right" class="page_form_sub">
        <c:if test="${canSendCount >0 }"><a href="#" onclick="sbMsgFrm();" id="addUserBtn" class="btn_common btn_true">发  送</a></c:if>
        </div>
		</form>
		</div>
		</div>
	</div>
</body>
</html>