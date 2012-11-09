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
		if (isEmpty($("#mmsImage").val())) {
			alert("请选择彩信图片。");
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
		<div title="发送彩信" style="padding:10px;" selected="true">
		
		<div class="page_form">
		<form action="${ctx}/admin/pri/message/sendMMS.action" name="sendMsgFrm" id="sendMsgFrm"  enctype="multipart/form-data" method="post">
        <input type="hidden" name="userIds" id="userIds"/>
        <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
        <fieldset>
            <legend></legend>
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
                <dt><label for="sendAllFlag">发送全部成员:</label></dt>
                <dd>
                    <input type="checkbox" name="sendAllFlag" id="sendAllFlag" value="Y" onClick="dealDiv();"/>
                </dd>
            </dl>
            <dl>
                <dt><label for="messageContent"><font color="red">*</font>彩信名称:</label></dt>
                <dd>
                    <input type="text" id="messageTitle" name="messageTitle" value="" size="64" maxlength="64"/>
                </dd>
            </dl>
            <dl>
            	<dt>&nbsp;</dt>
                <dd>
                <label style="float:left;padding-left:10px;"><font color="red">*</font>彩信图片(请上传小于50KB大小的图片):</label>
                <label style="float:left;padding-left:130px;"><font color="red">*</font>彩信文本:</label>
                </dd>
            </dl>
            <dl class="mms_dl">
                <dt>&nbsp;</dt>
					<dd><input name="mmsList[0].mmsImage" id="mmsImage" type="file"/>
	                    <textarea  name="mmsList[0].messageContent" id="messageContent" style="width:35%;height:60px"></textarea>
	                    <a href="javascript:void(0);" onclick="addMms(this);" class="btn_common btn_true">增  加</a>    
				        <a href="javascript:void(0);" onclick="removeMms(this);" class="btn_common btn_true">删  除</a>    
                    </dd>
            </dl>
           
            </fieldset>
        <div align="right" class="page_form_sub">
        	<a href="#" onclick="sbMsgFrm();" id="addUserBtn" class="btn_common btn_true">发  送</a>
        </div>
		</form>
		</div>
		</div>
		<div title="发送二维码" link="${ctx}/admin/pri/message/goSendQRCode.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
<script type="text/javascript">
/**
*删除当前行
*/
function removeMms(obj){
	 var tr = $(obj).parent().parent();
	 if($(tr).parent().find(".mms_dl").length == 1){
		 alert("最后一条不能删除！");
		 return;
	 }	
	 if( $(tr).parent().find(".mms_dl").length > 1 && confirm("确定删除这条记录?")){
		 $(tr).remove();
		 return ;
	 }
	 
}

var idx = 0;
/**
*复制当前行并插入到表格中去
*/
function addMms(obj){
	var tr = $(obj).parent().parent();
	if(validateMyForm($(tr))){
		//深度复制一个tr,包括事件绑定属性
		var $clone = $(tr).clone(true);
		//找到当前List的索引值
		//var idx = $(tr).parent().find(".mms_dl :last").html().match(/([[\d]+])/gi)[0].replace("[","").replace("]","");
		idx++;
		//找到TR下的所有input进行name的替换
		$($clone).find("input,select,textarea").each(function(i, elem){ 
		    $(elem).attr('name',$(elem).attr('name').replace(/([[\d]+])/gi,'['+idx+']'));
			//alert($(elem).attr('name'));
		});
		//保留select的值
		var selects = $(tr).find("select");
        $(selects).each(function(i) {
             var select = this;
             $($clone).find("select").eq(i).val($(select).val());
        });
        
        
		//添加到tr的后面
		$($clone).appendTo($(tr).parent());

		//赋值
		$("#mmsImage", $clone).val('');
		$("#messageContent", $clone).val('');
	}
}

//验证form
function validateMyForm(oldTr){
	//通过JQUERY选择器来获取对应的控件的值
	//var mmsImage= $("#mmsImage", oldTr).val();
	//if(mmsImage==''){
	//	alert('请选择一张不超过50kb大小的图片！');
	//	return false;
	//}
	var messageContent= $("#messageContent", oldTr).val();
	if(messageContent==''){
		alert('请输入彩信文本内容！');
		return false;
	}
	
	return true;
}
</script>	
	
</body>
</html>