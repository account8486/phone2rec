<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>短信管理</title>
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
        if (isEmpty($("#messageTitle").val())) {
			alert("请输入彩信标题。");
			return;
		}
		if (isEmpty($("#messageContent").val())) {
			alert("请输入彩信文本。");
			return;
		}
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
        
      	//提交给后台，进行数据的插入
		showLoading("彩信正在发送中......");
		$("#sendMsgFrm").submit();
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
		TEXT-ALIGN: LEFT;
	}
</style>
</head>
<body>
    <!-- title -->
	<div class="mainbox"><div class=page_title><h3>短信管理  -- ${CURRENT_MEETING}</h3></div></div>

	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="短信列表" link="${ctx}/admin/pri/message/getMessageList.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="发送短信" link="${ctx}/admin/pri/message/preSendMessage.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="发送彩信" link="${ctx}/admin/pri/message/goSendMMS.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="发送二维码" style="padding:10px;" selected="true">
		
		<div class="page_form">
		<form action="${ctx}/admin/pri/message/sendQRCode.action" name="sendMsgFrm" id="sendMsgFrm" method="post">
        <input type="hidden" name="userIds" id="userIds"/>
        <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
        <fieldset>
            <legend></legend>
            <dl>
                <dt><label for="messageContent"><font color="red">*</font>彩信名称:</label></dt>
                <dd>
                    <input type="text" id="messageTitle" name="messageTitle" value="会务通签到二维码" size="64" maxlength="64"/>
                </dd>
            </dl>	
            <dl>
                <dt><label for="messageContent"><font color="red">*</font>彩信文本:</label></dt>
                <dd>
                    <textarea class="small" cols="100" rows="2" name="messageContent" id="messageContent" class="textbox">尊敬的用户，您的二维码。</textarea>
               	    <small><font color="red"></font></small>
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
            </fieldset>
        <div align="right" class="page_form_sub">
        	<a href="#" onclick="sbMsgFrm();" id="addUserBtn" class="btn_common btn_true">发  送</a>
        </div>
		</form>
		</div>
		</div>
	</div>
</body>
</html>