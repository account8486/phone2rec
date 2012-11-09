<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta name="keywords" content="" />
<meta name="description" content="" />
<!--浏览器特性-->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!--多终端icon-->
<link href="favicon.ico" type="image/x-icon" rel="Bookmark" />
<link href="favicon.ico" type="image/x-icon" rel="shortcut icon" />
<link href="${ctx}/images/logo_apple.png" rel="apple-touch-icon" />
<title>会务通平台</title>
 ${jquery_js}
 ${main_css }
<style type="text/css"> 
body,div,table,form,p,select,input,textarea,option,a {
	font-family: Verdana, Arial, sans-serif;
	line-height: 1.5; font-size: 20px; color: #333;
}
</style>
<script type='text/javascript'>
var g_Interval = 10; 
var user; 
var g_Timer;
var running = false;
var index = 0 ;

function beginRndNum(trigger){

var selectItem = $("#selectTeam").val();
var itemLength = $("#result").children("li").length;
if (user == null || user.length ==0){
alert('没有数据'); 
return; 
} 

if(running){
running = false; 
clearTimeout(g_Timer); 
$('#resultNum').html(user[index].mobile); 
$("#result").append( "<li>"+ user[index].mobile+ ":"+ user[index].name +"</li>"); 
$("#hid").append("<input type='hidden' value="+user[index].id+"></input>");


//插入中奖的历史记录 
var param={"meetingId":"${meetingId}","userId":user[index].id,"id":selectItem};
$.post("${ctx}/admin/pri/lucky/lucky_addLuckyHistory.action",param,function(data){
});
//在数组中去掉已经得奖的用户 
user = user.slice(0, index).concat(user.slice(index + 1, user.length));
$("#start").removeClass("dis");

} 
else{ 
running = true;
$("#start").addClass("dis"); 
beginTimer();
}
}

function updateRndNum(){
if (user.length == 0 ){ 
return false; 
} 
index = Math.floor(Math.random()*user.length);
var obj = user[index]; 
//var num = obj.userName+":"+obj.teamName;
var num = obj.mobile; 
$('#resultNum').html(num); 
}

function beginTimer(){ 
g_Timer = setTimeout(beat, g_Interval);
}

function beat() {
g_Timer = setTimeout(beat, g_Interval);
updateRndNum();
}


function eachArray(lucky,id){
	var flag=true;
	if(lucky.length>0){
		lucky.each(function(){
			if($(this).val()==id){
				flag=false;
				return false;
			}
		});
	}
	return flag;
}

$(function(){
	var meetingId="${meetingId}";
	var param={"meetingId":meetingId,"id":$("#selectTeam").val()};
	$.post("${ctx}/admin/pri/lucky/lucky_getMeetingUsers.action",param,function(data){
		user = new Array();   // 删除已经中奖的人
		var lucky=$("#hid").find("*");
		var ary=data.list;
		user=$.grep(ary,function(n,i){
			return eachArray(lucky,n.id);
		});
	});
	
	$("#selectTeam").change(function(){
		$("#result").empty();
		$("#luckyName").html($(this).find("option:selected").text());
		var meetingId="${meetingId}";
		var param={"meetingId":meetingId,"id":$("#selectTeam").val()};
		$.post("${ctx}/admin/pri/lucky/lucky_getMeetingUsers.action",param,function(data){
			user = new Array(); 
			var lucky=$("#hid").find("*");
			var ary=data.list;
			user=$.grep(ary,function(n,i){
				return eachArray(lucky,n.id);
			});
			index=0;
		});
	});
	
	$("#luckyName").html($("#selectTeam").find("option:first").text());
})
</script>
</head>
<body  style="background:#8ac8f5 url(${ctx}/images/hd_bg.jpg) center  -15px repeat-x;"> 


<div class="cj_bg">
	<div class="cj_num" id="resultNum"></div>
    
    <div class="cj_sele">
    	<span style="font-size:14px; color:#ed3a00;">*请您选择奖项类型</span>
        <select style="display:block; width:130px;" name="selectTeam" id="selectTeam">
         <c:forEach var="lucky" items="${pager.pageRecords}" varStatus="status">
			<option value="${lucky.id }">${lucky.name }</option>
		 </c:forEach>
        </select>
    </div>
    
    <div class="cj_btn">
    	<a href="javascript:beginRndNum(this)"  id="start">抽奖</a>
        <!--<a href="javascript://" class="dis">抽奖不可用</a>-->
    </div>
</div>

<div class="cj_res">
	<h5 id="luckyName"></h5>
    <ul id="result">
    	
    </ul>
</div>

<div style="display: none;" id="hid">

</div>

</body>
</html>