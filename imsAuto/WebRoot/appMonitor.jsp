<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="common.*"%>
<%
String appPath = request.getContextPath();
request.setAttribute("appPath", appPath);

String minute = CommonUtil.getProperty("minute");
request.setAttribute("minute", minute);
String second = CommonUtil.getProperty("second");
request.setAttribute("second", second);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>应用监管</title>
<link href="${appPath}/images/common.css" rel="stylesheet" type="text/css" />
<style>
html, body, h3, td { color:#fff; font-family: '宋体', Arial, Helvetica; font-weight:bold; }

/* body { background:#033d3d; }   border-left:1px solid #039bb4;  */
body { background:#000; padding:0.5em 1em 0.5em 1em; }
.title { color:#fff; font-size:24px; padding-left:2em; line-height:1em; }
.main { border:4px solid #360; padding:0.5em 1em 0.5em 0em; }

.item h3 { font-weight:normal; color:#fff; font-size:22px; }
td { text-align:center; }
td img { padding:1% 0; }
td.tar { width:10.5em; text-align:right; font-size:14px; }
td.tar div { padding-right:0.5em; }
tr.area td { text-align:center; }
tr.area td div { font-size:14px; width:1em; margin:0 auto; padding-top:0em; }
td.bg_cross { background:url(${appPath}/images/bg_cross.gif) 50% 50% no-repeat; }

.box { background:#000; position:relative; width:1383px; }
.box .rc-tp,.box .rc-bt { position:relative; display:block; height:15px; overflow:hidden; }
.box .rc-tp b,.box .rc-bt b { float:right; width:15px; height:15px; }
.box .rc-tp { margin-bottom:-15px; }
.box .rc-bt { margin-top:-15px; }
.box .rc-tp { background:url(${appPath}/images/bg_lt.gif) no-repeat; }
.box .rc-tp b { background:url(${appPath}/images/bg_rt.gif) no-repeat; }
.box .rc-bt { background:url(${appPath}/images/bg_lb.gif) no-repeat; }
.box .rc-bt b { background:url(${appPath}/images/bg_rb.gif) no-repeat; }

#tip {position:absolute;color:#333;display:none;}
#tip s { display:block; position:absolute; top:-20px; left:52px; width:0; height:0; line-height:0; font-size:0; border-color:transparent transparent #FFF; border-style:dashed dashed solid; border-width:10px; }
#tip .t_box {position:relative;background-color:#CCC;bottom:0;right:0;}
#tip .t_box div {position:relative;background-color:#FFF;background:#FFF;top:0;left:0;}
#tip .t_box .cont { font-size:18px; padding:0.5em; white-space:nowrap; }

.bubble {width:34px; height:17px;}
</style>
<script type="text/javascript" src="${appPath}/images/jquery.min.js"></script>
<script>
//页面变量
var url = "${appPath}/servlet/AppMonitor";
var minute = ${minute};
var second = ${second};
var times = (minute * 60 + second * 1) * 1000;

function refreshAuto() {
	window.location = url;
}
function resetAll() {
	window.location = url + "?handle=reset";
}
function updateData()
{
	window.location = url + "?handle=update";
}
var timer = window.setTimeout("refreshAuto()", times);

function addTips(imgObj, dishiIndex, zhibiaoIndex) {
    var contInfo = '';
    var alarmInfo = ''; 
	var alarmCnt = data[dishiIndex].appHealth_sta;
	if (alarmCnt > 0) {
		//alarmInfo='告警次数: '+ alarmCnt + '<br />';
	}
	//contInfo = alarmInfo + zhibiaoName[zhibiaoIndex] + ': ' + eval('data[dishiIndex].'+zhibiao[zhibiaoIndex]);
	contInfo = zhibiaoName[zhibiaoIndex] + ': ' + eval('data[dishiIndex].'+zhibiao[zhibiaoIndex]);
	
	imgObj.mouseover(function(){
		//var $tip=$('<div id="tip"><div class="t_box"><div><s></s><div class="cont">'+contInfo+'</div></div></div></div>');
		//$('body').append($tip);
		$('#tip .cont').html(contInfo);
		$('#tip').show();
	}).mousemove(function(e){
		var tipLeft, sLeft;
		if ( e.pageX + $('#tip').width() > $('.main').width() ) {
			tipLeft = $('.main').width() - $('#tip').outerWidth(true) + 30;
			sLeft = (e.pageX - tipLeft - 10 > 0) ? e.pageX - tipLeft - 10 : 0;
		} else {
			tipLeft = e.pageX - 20;
			sLeft = 10;
		}
		$('#tip').css({"top":(e.pageY+30)+"px","left":tipLeft+"px"});
		$('#tip s').css({"left":sLeft+"px"});
	}).mouseout(function(){
      $('#tip').hide();
   }) 
}

var dishi = [23, 1, 3, 0, 8, 18, 6, 14, 2, 4, 5, 7, 9, 10, 11, 12, 13, 15, 16, 17, 19, 20, 21, 22];
var zhibiao = ["onlineCountH", "appHealth", "zhuanye", "onlineCount", "dayLogNum", "regCount", "access"];
var zhibiaoName = ["在线用户历史峰值", "健康运行时长", "专业指标", "在线用户数", "日登陆人数", "注册用户总数", "业务系统探测"];

var data = [
<c:if test="${not empty applist}">
<c:forEach items="${applist}" begin="0" end="50" var="item" varStatus="state">
{ name:"${item.appName }",
 onlineCountH:"${item.onlineCountH}", onlineCountH_sta:"${item.onlineCountH_sta}", 
 appHealth:"${item.appHealth}", appHealth_sta:"${item.appHealth_sta}", 
 onlineCount:"${item.onlineCount}", onlineCount_sta:"${item.onlineCount_sta}", 
 dayLogNum:"${item.dayLogNum}", dayLogNum_sta:"${item.dayLogNum_sta}", 
 regCount:"${item.regCount}", regCount_sta:"${item.regCount_sta}",
 dayLogNumH:"${item.dayLogNumH}", dayLogNumH_sta:"${item.dayLogNumH_sta}", 
 zhuanye:"", zhuanye_sta:"${item.zhuanye_sta}",
 access:"${item.access}", access_sta:"${item.access_sta}" }, 
 
 </c:forEach>
</c:if>

{name:"", state:""} ];

// deal with "zhuanye" info \n
function init_zy_data(){
	var temp, i;
	for (i = 0; i < data.length; i++) {
		temp = $('#zy_data li:nth-child('+(i+1)+')').html();
		data[i].zhuanye = (null == temp || temp.length < 1) ? "" : temp.replace(/\s/g, '<br>');
		if (null == temp || temp.length < 1) {
			data[i].zhuanye = '';
		} else {
			data[i].zhuanye = temp.replace(/[\s|]/g, '<br>');
		}
	}
}

$(document).ready(function(){
	init_zy_data();
	var i, j, k, obj;
	for (k = 0; k < zhibiao.length; k++) {
	
		for (i = 0; i < dishi.length; i++) {
			j = dishi[i];
			obj = $('#'+ zhibiao[k] +' td:eq('+ [i+1] +')').find('img');
			addTips(obj, j, k);
			if ( eval('data['+ j +'].'+zhibiao[k]+'_sta') > 0 ) {
				$('#'+ zhibiao[k] +' td:eq('+ [i+1] +')').find('img').attr("src", "${appPath}/images/lightRed52.png");
			} else {
			}
		}
	}
});
</script>
</head>
<body>
<ul id="zy_data" style="display:none;">
<c:if test="${not empty applist}">
<c:forEach items="${applist}" begin="0" end="50" var="item" varStatus="state">
  <li>${item.zhuanye}</li>
</c:forEach>
</c:if>
</ul>
<div class="title"><span>应用监管<span>
	<span>
		<input type="button" value="手动更新" onclick="resetAll();" />
		<input type="button" value="日指标更新" onclick="updateData();" />
	</span>
</div>
<div class="box">
<b class="rc-tp"><b></b></b>
<div class="main">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr id="onlineCountH">
		<td class="tar"><div>在线用户历史峰值</div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
	</tr>
	<tr id="appHealth"><!-- id="data" -->
		<td class="tar"><div>健康运行时长</div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
	</tr>
    <tr id="zhuanye">
		<td class="tar"><div>专业指标</div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
	</tr>
	<tr id="onlineCount">
		<td class="tar"><div>在线用户数</div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
	</tr>
	<tr id="dayLogNum">
		<td class="tar"><div>日登陆人数</div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
	</tr>
	<tr id="regCount">
		<td class="tar"><div>注册用户总数</div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
	</tr>
	<tr id="access">
		<td class="tar"><div>业务系统探测</div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
		<td class="bg_cross"><div><img src="${appPath}/images/lightGreen52.png" class="bubble" /></div></td>
	</tr>

	<tr class="area">
		<td></td>
		<td><div>Ｅ Ｒ Ｐ</div></td>
		<td><div>营销应用</div></td>
		<td><div>生产管理</div></td>
		<td><div>财务管控</div></td>
		<td><div>人资管控</div></td>
		<td><div>企业门户</div></td>
		<td><div>协同办公</div></td>
		<td><div>电力交易</div></td>

		<td><div>营销辅助</div></td>
		<td><div>安全监督</div></td>
		<td><div>应急管理</div></td>
		<td><div>远程培训</div></td>
		<td><div>基建管控</div></td>
		<td><div style="color:#A9A9A9;">项目储备</div></td>
		<td><div>统计管理</div></td>
		<td><div>农电管理</div></td>
		
		<td><div>经济法律</div></td>
		<td><div style="color:#A9A9A9;">电网规划</div></td>
		<td><div>电网前期</div></td>
		<td><div>投资计划</div></td>
		<td><div style="color:#A9A9A9;">内网网站</div></td>
		<td><div>数据中心</div></td>
		<td><div>数据交换</div></td>
		<td><div style="color:#A9A9A9;">高级应用</div></td>
	</tr>
</table>
</div>
<b class="rc-bt"><b></b></b>
</div>

<div id="tip" style="display:none;"><div class="t_box"><div><s></s><div class="cont">&nbsp;</div></div></div></div>
</body>
</html>