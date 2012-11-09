<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
${admin_css}                                   
${jquery_js}                                
${jquery_form_js}                                 
${validate_js}                                
${WdatePicker_js}                           
${admin_js}                   
${jmpopups_js}   
${util_js}
</head>
<body>
		
<div class="mainbox">
    <div class="page_title">
    <c:if test="${meeting.id !='' && meeting.id != null}">
		<h3>基本信息编辑  -- ${CURRENT_MEETING}</h3>	
	</c:if>	
    <c:if test="${meeting.id == null}">
		<h3>添加基本信息</h3>	
	</c:if>		
	
	</div>
	<div class="page_form">
	<form id="addMeetingForm" action="${ctx}/pages/admin/pri/meeting/addMeeting.action" >
	<input   type="hidden" id="meeting.id" name="meeting.id"  value="${meeting.id}" />
	
	<fieldset>
        <legend>基本信息</legend>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</label>
            </dt>
            <dd>
            	<input  type="text" style="width:72%" class="half" id="meeting_name" name="meeting.name"  value="${fn:escapeXml(meeting.name)}" maxlength="40"/>
            	<small>请限制在40字以内,您还可以输入<span id="meeting_name_remain">40</span>字</small>
            	<!-- <small>如标题太长，则可输入空格进行换行设置，若标题中存在多个空格，则以第一个空格作为换行标志<span id="meeting_name_remain">40</span>字</small> -->
            </dd>
        </dl>
        
        <%--贵州会务能不支持会议类型 
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</label>
            </dt>
            <dd>
            
            <c:if test="${meeting.id == null}">
            	<select id="meeting_type" name="meeting.meetingType.id">
            	<s:iterator value="meetingTypeList" var="mType">
            		<option value="${mType.id }" ${meeting.meetingType.id == mType.id ? "selected" : "" }>${mType.name }</option>
            	</s:iterator>
				</select>
            </c:if>
            <c:if test="${meeting.id !='' && meeting.id != null}">
            	<label><wd:custom  type="translate" entity="meeting_type" entityValue="id" entityName="name" selectedValue="${meeting.meetingType.id}" condition="where id=${meeting.meetingType.id}"></wd:custom></label>
            	<input type="hidden" id="meeting_type" name="meeting.meetingType.id" value="${meeting.meetingType.id}"/>
            </c:if>
            </dd>
        </dl>
        --%>
        <input type="hidden" id="meeting_type" name="meeting.meetingType.id" value="1"/>
        
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>主&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</label>
            </dt>
           
            <%--
            	 <dd><input  type="text" style="width:40%" class="half" id="meeting.topic" name="meeting.topic"  value="${fn:escapeXml(meeting.topic)}" maxlength="40"/>
            	<small>请限制在40字以内,您还可以输入<span id="meeting_topic_remain">40</span>字</small> </dd>
            --%>
            	  <dd>
            	<textarea style="width:72%" class="medium" id="meeting.topic" name="meeting.topic" maxlength="200">${fn:escapeXml(meeting.topic)}</textarea>
            	<small>请限制在200字以内,您还可以输入<span id="meeting_topic_remain">200</span>字</small>
            </dd>
           
        </dl>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点：</label>
            </dt>
            <dd>
            	<select id="province" name="meeting.province" style="width:80px;" class="valid"></select>
				<select id="city" name="meeting.city" style="width:80px;" ></select>
            	<input  type="text" id="meeting.location" name="meeting.location" style="width:52%;"  value="${fn:escapeXml(meeting.location)}" maxlength="30"/>
            	<small>请限制在30字以内,您还可以输入<span id="meeting_location_remain">30</span>字</small>
            </dd>
        </dl>
        <dl style="float:left;width:48%">
        	<dt >
            	<label for="title"><font color="red">* </font>会议开始时间：</label>
            </dt>
            <dd style="width:40%">
            	<input id="startTime" type="text" name="meeting.startTime"  value="${fn:substring(meeting.startTime,0,16)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d %H:00:00',maxDate:'#F{$dp.$D(\'endTime\')||\'2019-12-31 08:00\'}' })" readonly="readonly"/>
            </dd>
        </dl>
        <dl style="float:left;width:48%">
        	<dt >
            	<label for="title"><font color="red">* </font>结束时间：</label>
            </dt>
            <dd style="width:40%">
            	<input id="endTime" type="text" name="meeting.endTime"  value="${fn:substring(meeting.endTime,0,16)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d %H:00:00',minDate:'#F{$dp.$D(\'startTime\')||\'2019-12-31 18:00\'}',maxDate:'2019-12-31 18:00'})" readonly="readonly"/>
            </dd>
        </dl>
        <dl >
        	<dt>
            	<label for="title">&nbsp;</label>
            </dt>
            <dd >
            	<small>预计的会议召开时间段。</small>
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title">会议报到时间：</label>
            </dt>
            <dd>
            	<input id="joinTime" type="text" name="meeting.joinTime"  value="${fn:escapeXml(meeting.joinTime)}" class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly="readonly"/>
            	<label id="err_joinTime" class="error"></label>
            	<small>会议可以报到的时间</small>
            </dd>
        </dl>
        
        <%--
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>免费短信条数：</label>
            </dt>
            <dd>
             	<c:if test="${empty meeting.id }">
	                <c:if test="${SESSION_ADMIN_USER.role.id eq 1 }">
						<input style="width:20%" id="freeSmsNum" type="text" name="meeting.freeSmsNum"  value="${meeting.freeSmsNum}" maxlength="8" />	
					</c:if>
					<c:if test="${SESSION_ADMIN_USER.role.id ne 1 }">
	                       <input type="hidden" id="freeSmsNum" name="meeting.freeSmsNum"  value="0" />
	                        <font style="line-height:35px;">请向管理员申请分配</font> 
					</c:if>		
	               </c:if>
	               <c:if test="${not empty meeting.id}">
	                <c:if test="${SESSION_ADMIN_USER.role.id eq 1 }">
						<input style="width:20%" id="freeSmsNum" type="text" name="meeting.freeSmsNum"  value="${meeting.freeSmsNum}" maxlength="8" />
					</c:if>	
	                <c:if test="${SESSION_ADMIN_USER.role.id ne 1 }">
	                       <input type="hidden" id="freeSmsNum" name="meeting.freeSmsNum"  value="${meeting.freeSmsNum}" />
	                       <font style="line-height:35px;">${meeting.freeSmsNum}</font>
					</c:if>	
	               </c:if>
                <small>本次会议可以免费发送的短信条数，超出数目的短信将不能发送出去。</small>
            </dd>
        </dl>
         --%>
         
             <c:if test="${SESSION_ADMIN_USER.role.id ne 1 }">
             	<c:if test="${empty meeting.id }">
             	 <input type="hidden" id="freeSmsNum" name="meeting.freeSmsNum"  value="0" />
             	</c:if>
                <c:if test="${not empty meeting.id}">
             	  <input type="hidden" id="freeSmsNum" name="meeting.freeSmsNum"  value="${meeting.freeSmsNum}" />
             	</c:if>
             </c:if>
             
             <c:if test="${SESSION_ADMIN_USER.role.id eq 1 }">
                 <dl>
	        	 <dt>
	            	<label for="title"><font color="red">* </font>免费短信条数：</label>
	             </dt>
	             
	             <dd>
	             <input style="width:15%" id="freeSmsNum" type="text" name="meeting.freeSmsNum"  value="${meeting.freeSmsNum}" maxlength="8" />	
	             </dd>
            </dl>
            </c:if>
             
         
         
         
        
        <%--贵州会务能不支持总机号码
        <dl>
        	<dt>
            	<label for="title">总机号码：</label>
            </dt>
            <dd>
            	<c:if test="${meeting.id !='' && meeting.id != null}">
		        	<div style="padding-top: 5px;margin-left: 10px"><font style="color:red;">${meeting.serviceNumber}</font></div>
		        	<input type="hidden" id="service_number" name="meeting.serviceNumber"  value="${meeting.serviceNumber}" />
		        </c:if>
				<c:if test="${meeting.id == null}">
					<input style="width:20%" type="text" id="service_number" name="meeting.serviceNumber"  value="${meeting.serviceNumber}" maxlength="20" />
          	    	<small>请输入正确的总机号码，只能使用数字。</small>
 		        </c:if>
            </dd>
        </dl>
        --%>
        <input type="hidden" id="service_number" name="meeting.serviceNumber"  value="" />
        
        <c:choose>
        <c:when test="${meeting.id == null || SESSION_ADMIN_USER.role.id eq 1 || SESSION_ADMIN_USER.role.id eq 3}">
        <!--<dl>
        	<dt>
            	<label for="title">会务人员：</label>
            </dt>
            <dd>
				<c:choose>
                    <c:when test="${not empty meetingAdmins}">
                        <c:forEach var="record" items="${meetingAdmins}" varStatus="status">
             				<input type="checkbox" name="meetingAdmins" value="${record.id }"<c:if test="${record.selected}">checked="true"</c:if>/>
             				${record.mobile }(${record.name })
             			</c:forEach>
                    </c:when>
            	</c:choose>
            </dd>
        </dl>
        --></c:when>
        </c:choose>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>会议主办方：</label>
            </dt>
            <dd>
				<input type="text" id="host" name="meeting.host" style="width:42%" value="${meeting.host}" maxlength="50" />
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title"><font color="red"></font>会议承办方：</label>
            </dt>
            <dd>
				<label  style="width:5%;margin-right:30px;" >序号</label>
				<label  style="width:40%" >名称</label>
            </dd>
        </dl>
        <c:if test="${empty meeting.organizer }">
        <dl class="org_dl">
        	<dt>
            	<label for="title">&nbsp;</label>
            </dt>
            <dd>
				<input type="text" id="org_sort" name="orgList[0].sort"  style="width:5%" value="1" maxlength="5" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				<input type="text" id="organizer" name="orgList[0].organizer"  style="width:35%" value="" maxlength="50" />
		        <a href="javascript:void(0);" onclick="addOrg(this);" class="btn_common btn_true">增  加</a>    
		        <a href="javascript:void(0);" onclick="removeOrg(this);" class="btn_common btn_true">删  除</a>    
            </dd>
        </dl>
        </c:if>
        <c:if test="${not empty meeting.organizer }">
        	<c:forEach var="orgzer" items="${wd:orgsToList(meeting.organizer) }" varStatus="status">
		        <dl class="org_dl">
		        	<dt>
		            	<label for="title">&nbsp;</label>
		            </dt>
		            <dd>
						<input type="text" id="org_sort" name="orgList[${status.index}].sort"  style="width:5%" value="${orgzer.sort}" maxlength="5" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						<input type="text" id="organizer" name="orgList[${status.index}].organizer"  style="width:40%" value="${orgzer.organizer}" maxlength="50" />
					        <a href="javascript:void(0);" onclick="addOrg(this);" class="btn_common btn_true">增  加</a>    
					        <a href="javascript:void(0);" onclick="removeOrg(this);" class="btn_common btn_true">删  除</a>    
		            </dd>
		        </dl>
        	</c:forEach>
        </c:if>
        <dl style="display:none;">
        	<dt>
            	<label for="title"><font color="red">* </font>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</label>
            </dt>
            <dd >
            	<input type="radio" id="state_valid" name="meeting.state" value="1" checked="checked"/>&nbsp;<label for="state_valid">有效</label> 
            	<input type="radio" id="state_invalid"  name="meeting.state" value="0" />&nbsp; <label for="state_invalid">无效</label> 
            </dd>
        </dl>
    </fieldset>
	<fieldset>
		<legend>其他信息</legend>
	    <!--<dl>
        	<dt>
            	<label for="title">通知公告：</label>
            </dt>
            <dd>
            	<input style="width:80%"  id="meeting.notice" type="text" name="meeting.notice"  value="${fn:escapeXml(meeting.notice)}"  maxlength="200"/>
            	<small>请限制在200字以内,您还可以输入<span id="meeting_notice_remain">200</span>字</small>
            </dd>
        </dl>
        --><dl style="float:left;width:48%">
        	<dt>
            	<label for="title">访问开始时间：</label>
            </dt>
            <dd style="float:left;width:40%">
            	<input id="meeting.accessStartTime" type="text" name="meeting.accessStartTime"  value="${fn:substring(meeting.accessStartTime,0,10)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'meeting.accessEndTime\')||\'2020-10-01\'}'})" readonly="readonly"/>
            </dd>
        </dl>
        <dl style="float:left;width:48%">
        	<dt>
            	<label for="title">访问结束时间：</label>
            </dt>
            <dd style="float:left;width:40%">
            	<input id="meeting.accessEndTime" type="text" name="meeting.accessEndTime"  value="${fn:substring(meeting.accessEndTime,0,10)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'meeting.accessStartTime\')||\'2020-10-01\'}',maxDate:'2020-10-01'})" readonly="readonly"/>
            </dd>
        </dl>
        <dl >
        	<dt>
            	<label for="title">&nbsp;</label>
            </dt>
            <dd >
            	<small>设置可以访问会议信息的时间段，不设置则不进行限制。</small>
            </dd>
        </dl>
        
	    <dl>
        	<dt>
            	<label for="title"> &nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
            </dt>
            <dd>
            	<textarea  id="meeting.comments" name="meeting.comments" style="width:72%" maxlength="200">${fn:escapeXml(meeting.comments)}</textarea>
            	<small>请限制在200字以内,您还可以输入<span id="meeting_comments_remain">200</span>字</small>
            </dd>
        </dl>
    </fieldset>
    <div class="page_form_sub">
        <a href="##" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>    
    </div>

	</form>
	
	</div>
</div>
	
<script type="text/javascript">

/**
*删除当前行
*/
function removeOrg(obj){
	 var tr = $(obj).parent().parent();
	 if($(tr).parent().find(".org_dl").length == 1){
		 alert("最后一条不能删除！");
		 return;
	 }	
	 if( $(tr).parent().find(".org_dl").length > 1 && confirm("确定删除这条记录?")){
		 $(tr).remove();
		 return ;
	 }
	 
}


var idx = '${fn:length(wd:orgsToList(meeting.organizer) )-1 }';
if(idx < 0){
	idx = 0;
}
/**
*复制当前行并插入到表格中去
*/
function addOrg(obj){
	var tr = $(obj).parent().parent();
	var sort = $("#org_sort", $(tr).parent().find(".org_dl").last()).val();
	if(validateMyForm($(tr))){
		//深度复制一个tr,包括事件绑定属性
		var $clone = $(tr).clone(true);
		//找到当前List的索引值
		//var idx = $(tr).parent().children(":last").children(":first").html().match(/([[\d]+])/gi)[0].replace("[","").replace("]","");
		idx++;
		//找到TR下的所有input进行name的替换
		$($clone).find("input,select").each(function(i, elem){ 
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
		$("#org_sort", $clone).val(1+parseInt(sort));
		$("#organizer", $clone).val('');
	}
}

//验证form
function validateMyForm(oldTr){
	//通过JQUERY选择器来获取对应的控件的值
	var org_sort= $("#org_sort", oldTr).val();
	if(org_sort==''){
		alert('请输入排序号！');
		return false;
	}
	var organizer= $("#organizer", oldTr).val();
	if(organizer==''){
		alert('请输入承办方！');
		return false;
	}
	
	return true;
}






$().ready(function() {
	$("#meeting_name").calcWordNum({maxNumber:40,targetid:"meeting_name_remain"});
	$("#meeting\\.topic").calcWordNum({maxNumber:200,targetid:"meeting_topic_remain"});
	$("#meeting\\.location").calcWordNum({maxNumber:30,targetid:"meeting_location_remain"});
	$("#meeting\\.notice").calcWordNum({maxNumber:200,targetid:"meeting_notice_remain"});
	$("#meeting\\.comments").calcWordNum({maxNumber:200,targetid:"meeting_comments_remain"});
	
	// 初始化省份、城市
	var areas = "";// 存放省份、城市
	function initMeetingArea(proviceCode){
		$.post(
                "${ctx}/admin/pri/base/getAreas.action",
                {},
                function (data, textStatus) {
                    if (textStatus == "success") {
                    	areas = data;
                    	if (data.province == null){
                			return;
                		}
                		
                		var sel = $("#province");
                		sel.empty();
                		$.each(data.province, function(i, item){
                				var opt = new Option(item.name, item.code);
                    			sel.get(0).options.add(opt);
                		});
                		
                		if(proviceCode==''){
                			//新建会议 默认选贵州,两种写法都可以
                			//$("#province option[value='17']").attr("selected", "selected");
                			  $("#province").val('17');
                		}else{
                			$("#province").val(proviceCode);
                		}
                		
                		$("#province").change();
                		
                    }
                },
                "json"
    	);
	}
	
	$("#province").change(function(){
		var provinceCode = $("#province").val();
		var sel = $("#city");
		
		/*
		var str = "";
		$.each(sel.get(0).options, function(i, item){
			str += "insert into meeting_area values('" +item.value + "','" + item.text +"','"+item.value.substring(0,2)+ "');\r\n";
		});
		alert(str);*/
		
		sel.empty();
		$.each(areas[provinceCode], function(i, item){
			var opt = new Option(item.name, item.code);
			sel.get(0).options.add(opt);
		});
		//默认选中贵州贵阳
		$("#city").val("${empty meeting.city ? '1757816' : meeting.city}");
	});
	
	//根据已有数据获取省市编码,默认选中贵州贵阳
	initMeetingArea("${empty meeting.province ? '17' : meeting.province}"); 
	
	if('${meeting.id}'!=''){
		//added for meeting guide
		if("${fromStep}" != "") {
			showEditMenu("${ctx}", "${meeting.id}");
		}
		
		gotobase();		
		//$("#meeting_state").val(${meeting.state});
	    $("input[name='meeting.state'][value=${meeting.state}]").attr("checked",true);  
	}
	
	jQuery.validator.addMethod("areaValid", function(value, element) {
	    if ( $("#city").val() == "0000" ){
	    	return false;
	    }
		return true;
	}, "请选择省份和城市");
	
	jQuery.validator.addMethod("serviceNumDepend", function(value, element) {
		if($("#service_number").val() == ""){
			return true;
		}else  if ( $("#startTime").val() == "" || $("#endTime").val() =="" || $("#freeSmsNum").val() ==""){
	    	return false;
	    }
		return true;
	}, "请输入开始时间、结束时间和免费短信条数");
	

	$("#addMeetingForm").validate({
		onkeyup: false,
		rules: {
			"meeting.name": "required",
			"meeting.meetingType.id": "required",
			"meeting.topic": {
				required :true,
				maxlength: 2000
			},
			"meeting.city": {
				areaValid:true
			},
			"meeting.location": "required",
			"meeting.startTime": "required",
			"meeting.endTime": "required",
			"meeting.freeSmsNum": {
				required :true,
				maxlength: 8,
				digits: true
			},
			"meeting.host": "required",
			"meeting.serviceNumber": {
				serviceNumDepend:true,
				minlength: 7,
				number: true,
				remote:{
				    type: "POST",
				    dataType:"json",
				    url: "${ctx}/pages/admin/pri/meeting/checkServiceNumber.action",
				    data: {
				    		"meeting.startTime":function(){ return jQuery("#startTime").val();},
				    		"meeting.endTime":function(){ return jQuery("#endTime").val();},
				    		"meeting.freeSmsNum":function(){ return jQuery("#freeSmsNum").val();}
				    	   }				   
				}
			}
		},
		messages: {
			"meeting.name": {
				required :"请输入名称！",
				maxlength:"名称已超过25个字！"
			},
			"meeting.meetingType.id": {
				required :"请选择类型！"
			},
			"meeting.topic": {
				required :"请输入主题！",
				maxlength:"主题内容已超过2000字！"
			},
			"meeting.location": {
				required :"请输入地点！",
				maxlength:"地点已超过30字！"
			},
			"meeting.host": {
				required :"请输入会议的主办方信息！"
			},
			"meeting.startTime": "请输入开始时间！",
			"meeting.endTime": "请输入结束时间！",
			"meeting.serviceNumber":{
				required :"请输入总机号码！",
				minlength: "请至少输入7位数字！",
				number: "请至少输入7位数字！",
				remote: "总机号码不存在，请检查！"
			} ,
			"meeting.freeSmsNum":{
				required :"请输入免费发送短信条数！",
				maxlength: "不能超过8位数字！",
				digits: "请输入整数！"
			}
		}
	});
});

		function save(){
			
			if($("#submitBtn").hasClass("btn_false")){
				alert("您已经添加过本次编辑内容，请不要重复保存！");
				return false;
			}
			
			//判断会议加会时间是否在开始和结束时间之内
			/*$("#err_joinTime").html("");
			var joinTime =$("#joinTime").val();
		    if(joinTime != "") {
		    	var startTime = $("#startTime").val();
		    	var endTime = $("#endTime").val();
		    	if(joinTime < startTime || joinTime > endTime) {
		    		$("#err_joinTime").html("会议可以报到的时间，必须介于会议开始和结束时间之内。");
		    		return false;
		    	}
		    }*/

			submit();
			//document.getElementById("submitBtn").disabled = "disabled";
		}


		function submit(){
			var url = '';
			if('${meeting.id}'==''){
				url= '${ctx}/pages/admin/pri/meeting/addMeeting.action';
			}else{
				url= '${ctx}/pages/admin/pri/meeting/modifyMeeting.action';				
			}
			
			var options = { 
					beforeSubmit: validate,
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'         
			    };
			$('#addMeetingForm').ajaxSubmit(options);		
		}
		//回调函数
		function callback(data){
			hideLoading();
			alert(data.retmsg);
			if(data.retcode=="0"){
				showEditMenu("${ctx}",data.retdata.id);
				gotobase();		
			}else{

				$("#submitBtn").removeClass("btn_false");
				$("#submitBtn").addClass("btn_true");
			}
		}
		
		function validate()
		{    
		    var isSuccess=$("#addMeetingForm").validate().form(); //.form(); 返回是否验证成功
		    if(isSuccess){
				if('${meeting.id}'==''){
					$("#submitBtn").removeClass("btn_true");
					$("#submitBtn").addClass("btn_false");
				}
				showLoading("正在保存，请稍候...");
				
			}
		     return isSuccess;
		}
		

</script>
</body>
</html>