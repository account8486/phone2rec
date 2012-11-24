<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="common.*"%>
<%
String appPath = request.getContextPath();
request.setAttribute("appPath", appPath);
String minute = CommonUtil.getProperty("minute");
request.setAttribute("minute", minute);
String second = CommonUtil.getProperty("second");
request.setAttribute("second", second);
String sms_net = CommonUtil.getProperty("SMS_NET");
request.setAttribute("sms_net", sms_net);
String sms_app = CommonUtil.getProperty("SMS_APP");
request.setAttribute("sms_app", sms_app);
String sms_desk = CommonUtil.getProperty("SMS_DESK");
request.setAttribute("sms_desk", sms_desk);
String sms_secu = CommonUtil.getProperty("SMS_SECU");
request.setAttribute("sms_secu", sms_secu);
String sms_ala = CommonUtil.getProperty("SMS_ALA");
request.setAttribute("sms_ala", sms_ala);

String sms_pow = CommonUtil.getProperty("SMS_POW");
request.setAttribute("sms_pow", sms_pow);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>主监管页</title>
<link href="${appPath}/images/common.css" rel="stylesheet" type="text/css" />
<style>
html, body, h3, td { color:#fff; font-family: "微软雅黑", Arial, Helvetica; font-weight:bold; }

/* body { background:#033d3d; }   border-left:1px solid #039bb4;  */
body { background:#000; padding:1em 3em 1em 2em; }
.title { color:#fff; font-size:30px; padding-left:2em; line-height:2.5em; }
.main { border:4px solid #360; padding:3em 0 3em 0; }

.item h3 { font-weight:normal; color:#fff; font-size:22px; }
td { text-align:center; }
td img { padding:55% 0; }
td.tar { width:9em; text-align:right; font-size:18px; }
td.tar div { height:18%; padding-right:1em; }
tr.area td { text-align:center; }
tr.area td div, tr.area a { font-size:26px; margin:0 auto; padding-top:1.5em; }
tr.area a:hover { color:#ccc;}
td.bg_cross { background:url(${appPath}/images/bg_cross.gif) 50% 50% no-repeat; }

.box { background:#000; position:relative; }
.box .rc-tp,.box .rc-bt { position:relative; display:block; height:15px; overflow:hidden; }
.box .rc-tp b,.box .rc-bt b { float:right; width:15px; height:15px; }
.box .rc-tp { margin-bottom:-15px; }
.box .rc-bt { margin-top:-15px; }
.box .rc-tp { background:url(${appPath}/images/bg_lt.gif) no-repeat; }
.box .rc-tp b { background:url(${appPath}/images/bg_rt.gif) no-repeat; }
.box .rc-bt { background:url(${appPath}/images/bg_lb.gif) no-repeat; }
.box .rc-bt b { background:url(${appPath}/images/bg_rb.gif) no-repeat; }

.red { color:#f00; }
td a { color:#36f; }
</style>
<script type="text/javascript" src="${appPath}/images/jquery.min.js"></script>
<script>
//页面变量
var url = "${appPath}/servlet/AlarmMonitor";
var minute = ${minute};
var second = ${second};
var times = (minute * 60 + second * 1) * 1000;

function refreshAuto() {
	window.location = url + "?alarm=${alarm}&handle=reset";
}

var timer = window.setTimeout("refreshAuto()", times);

function disableAlarm(obj) {
	var isAlarm = $(obj).attr('checked');	// true or false
	
	if (isAlarm) {
		window.location = url + "?alarm=true&handle=reset";
	} else {
		window.location = url + "?alarm=false&handle=reset";
	}
}


function player(id, wav) {
	var obj = $('#'+id);
	//obj[0].stop();
	//alert(obj.html());
	//obj.attr('src', 'sound/'+ wav);
	//obj[0].setAttribute('src', 'sound/'+ wav);
	obj.html('<embed id="alarm" src="${appPath}/sound/'+wav+'" width="0" height="0" autostart="true" loop="false" />');
	//alert(obj.html());
	//obj[0].play();
}

// 设置短信
function setConfig() {
	var sms_net = $('#sms_net').attr("checked");
	sms_net = (sms_net == true ? 1 : 0);
	var sms_app = $('#sms_app').attr("checked");
	sms_app = sms_app == true ? 1 : 0;
	var sms_desk = $('#sms_desk').attr("checked");
	sms_desk = sms_desk == true ? 1 : 0;
	var sms_secu = $('#sms_secu').attr("checked");
	sms_secu = sms_secu == true ? 1 : 0;
	var sms_ala = $('#sms_ala').attr("checked");
	sms_ala = sms_ala == true ? 1 : 0;
	
	var sms_pow = $('#sms_pow').attr("checked");
	sms_pow = sms_pow == true ? 1 : 0;
	
	window.location = url + '?sms_net=' + sms_net + '&sms_app=' + sms_app+ '&sms_desk=' + sms_desk+ '&sms_secu=' + sms_secu+ '&sms_ala=' + sms_ala+ '&sms_pow=' + sms_pow;
}

$(document).ready(function(){
/*
var sta = { appSta:1, deskSta:1, netSta:1, secuSta:1 };
0 - normal
other - alarm
*/
	var sta = { 
appSta:<c:choose><c:when test="${appsta == 'alarm'}">1</c:when><c:otherwise>0</c:otherwise></c:choose>, 
deskSta:<c:choose><c:when test="${desksta == 'alarm'}">1</c:when><c:otherwise>0</c:otherwise></c:choose>, 
netSta:<c:choose><c:when test="${netsta == 'alarm'}">1</c:when><c:otherwise>0</c:otherwise></c:choose>, 
secuSta:<c:choose><c:when test="${secusta == 'alarm'}">1</c:when><c:otherwise>0</c:otherwise></c:choose>, 
alarmSta:<c:choose><c:when test="${alarmsta == 'alarm'}">1</c:when><c:otherwise>0</c:otherwise></c:choose>,
powSta:<c:choose><c:when test="${powsta == 'alarm'}">1</c:when><c:otherwise>0</c:otherwise></c:choose>
};
	var wav = { alarm:'alarm3.mp3', appSta:'app_alarm.wav', deskSta:'desk_alarm.wav', netSta:'net_alarm.wav', secuSta:'secu_alarm.wav', alarmSta:'alarm_alarm.wav', powSta:'pow_alarm.wav' };
	var queue = [];

	var obj, key, i, delay;

	for ( key in sta ) {
		obj = $('#'+key);
		if (eval("sta." + key) != 0) {
			//obj.removeClass('blue');
			obj.addClass('red');
			queue.push(key);
		}
	}

<c:choose>
<c:when test="${alarm == 'false'}">
</c:when>
<c:otherwise>
	// play queue
	delay = 1000;
	for (i = 0; i < queue.length; i++ ) {
		key  = eval('wav.' + queue[i]);
		
		setTimeout("player('alarm', '" + key + "')", delay);
		delay += 4500;
	}
</c:otherwise>
</c:choose>

// 设置短信
var sms_net = ${sms_net};
if (sms_net == 1) { $("#sms_net").attr("checked", "checked"); }
var sms_app = ${sms_app};
if (sms_app == 1){ $("#sms_app").attr("checked", "checked"); }
var sms_desk = ${sms_desk};
if (sms_desk == 1){ $("#sms_desk").attr("checked", "checked"); }
var sms_secu = ${sms_secu};
if (sms_secu == 1){ $("#sms_secu").attr("checked", "checked"); }
var sms_ala = ${sms_ala};
if (sms_ala == 1){ $("#sms_ala").attr("checked", "checked"); }

var sms_pow = ${sms_pow};
if (sms_pow == 1){ $("#sms_pow").attr("checked", "checked"); }

});
</script>
</head>
<body>
<div class="title"><span>主监管页<span>
	<span>
		<!--  
			刷新时间&nbsp;
			分：<input type="text" id='minute' name='minute' size='3' value='<c:out value="${minute}" />' />
			秒：<input type="text" id='second' name='second' size='3' value='<c:out value="${second}" />' />
			<input type="button" value="修改" onclick="setTimes();" />
		-->
		<input type="button" value="手动刷新" onclick="refreshAuto();" />
		<input type="button" value="设置短信" onclick="setConfig();" />
		<input type="checkbox" name="canalarm" <c:choose><c:when test="${alarm == 'false'}"></c:when><c:otherwise>checked=""</c:otherwise></c:choose> onclick="disableAlarm(this)" /><span style="font-size:14px; font-weight:normal;"> 告警声音</span>
	</span>
</div>
<div class="box">
<b class="rc-tp"><b></b></b>
<div class="main">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr class="area">
		<td>
			<a id="appSta" href="${appPath}/servlet/AppMonitor" target="_blank">应用监管</a>
			<input type="checkbox" name="sms_app" id="sms_app" title="设置短信提醒" />
		</td>
		<td>
			<a id="deskSta" href="${appPath}/servlet/DeskMonitor" target="_blank">桌面监管</a>
			<input type="checkbox" name="sms_desk" id="sms_desk" title="设置短信提醒" />
		</td>
		<td>
			<a id="netSta" href="${appPath}/servlet/NetMonitor" target="_blank">网络监管</a>
			<input type="checkbox" name="sms_net" id="sms_net" title="设置短信提醒" />
		</td>
		<td>
			<a id="secuSta" href="${appPath}/servlet/SecuMonitor" target="_blank">安全监管</a>
			<input type="checkbox" name="sms_secu" id="sms_secu" title="设置短信提醒" />
		</td>
		<td>
			<a id="alarmSta" href="javascript:;">告警监管</a>
			<input type="checkbox" name="sms_ala" id="sms_ala" title="设置短信提醒" />
		</td>
		<td>
			<a id="powSta" href="${appPath}/servlet/DevMonitor?flag=kt" target="_blank">空调监管</a>
			<input type="checkbox" name="sms_pow" id="sms_pow" title="设置短信提醒" />
		</td>
		
		
	</tr>
	<tr  class="area">
		
		<td>
			<a id="powSta" href="${appPath}/servlet/DevMonitor?flag=ls" target="_blank">漏水监管</a>
			<input type="checkbox" name="sms_pow" id="sms_pow" title="设置短信提醒" />
		</td>
		
		<td>
			<a id="powSta" href="${appPath}/servlet/DevMonitor?flag=pd" target="_blank">配电监管</a>
			<input type="checkbox" name="sms_pow" id="sms_pow" title="设置短信提醒" />
		</td>
		<td>
			<a id="powSta" href="${appPath}/servlet/DevMonitor?flag=sd" target="_blank">环境监管</a>
			<input type="checkbox" name="sms_pow" id="sms_pow" title="设置短信提醒" />
		</td>
		
		<td>
			<a id="powSta" href="${appPath}/servlet/DevMonitor?flag=wd" target="_blank">温度监管</a>
			<input type="checkbox" name="sms_pow" id="sms_pow" title="设置短信提醒" />
		</td>
		
		<td>
			<a id="powSta" href="${appPath}/servlet/DevMonitor?flag=xf" target="_blank">消防监管</a>
			<input type="checkbox" name="sms_pow" id="sms_pow" title="设置短信提醒" />
		</td>
		
		<td>
			<a id="powSta" href="${appPath}/servlet/PowMonitor" target="_blank">Test</a>
			<input type="checkbox" name="sms_pow" id="sms_pow" title="设置短信提醒" />
		</td>
		
		<td>
			<a id="powSta" href="${appPath}/servlet/Powhs" target="_blank">黄山1</a>
			<input type="checkbox" name="sms_pow" id="sms_pow" title="设置短信提醒" />
		</td>
		
		<td>
			<a id="powSta" href="${appPath}/servlet/Powhse" target="_blank">黄山20</a>
			<input type="checkbox" name="sms_pow" id="sms_pow" title="设置短信提醒" />
		</td>
	</tr>
</table>
</div>
<b class="rc-bt"><b></b></b>
</div>

<div id="alarm"></div>
</body>
</html>