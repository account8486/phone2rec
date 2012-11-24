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
<title>桌面监管</title>
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
td.tar div { padding-right:1em; }
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
#tip s { display:block; position:absolute; bottom:-20px; left:10px; width:0; height:0; line-height:0; font-size:0; border-color:#FFF transparent transparent; border-style:solid dashed dashed; border-width:10px; }
#tip .t_box {position:relative;background-color:#CCC;bottom:0;right:0;}
#tip .t_box div {position:relative;background-color:#FFF;background:#FFF;top:0;left:0;}
#tip .t_box .cont { font-size:18px; padding:0.5em; white-space:nowrap; }

.bubble {width:34px; height:17px;}
</style>
<script type="text/javascript" src="${appPath}/images/jquery.min.js"></script>
<script>
//页面变量
var url = "${appPath}/servlet/DeskMonitor";
var minute = ${minute};
var second = ${second};
var times = (minute * 60 + second * 1) * 1000;

function refreshAuto() {
	window.location = url;
}
function resetAll() {
	window.location = url + "?handle=reset";
}
var timer = window.setTimeout("refreshAuto()", times);

function addTips(imgObj, dishiIndex, zhibiaoIndex) {
    var contInfo = '';
    var alarmInfo = ''; 
	var alarmCnt = eval('data[dishiIndex].'+zhibiao[zhibiaoIndex]+'_sta');
	if (alarmCnt > 0) {
		//alarmInfo='告警次数: '+ alarmCnt + '<br />';
	}
	contInfo = alarmInfo + zhibiaoName[zhibiaoIndex] + ': ' + eval('data[dishiIndex].'+zhibiao[zhibiaoIndex]);
	
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
		$('#tip').css({"top":(e.pageY-70)+"px","left":tipLeft+"px"});
		$('#tip s').css({"left":sLeft+"px"});
	}).mouseout(function(){
      $('#tip').hide();
   }) 
}

var dishi = [0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17];
var zhibiaoName = ["应注册终端数", "已注册终端数", "未注册终端数", "未注册终端数", "终端注册率", "杀毒软件安装率", "违规外联", "违规外联"];
var zhibiao = ["yingzhuce", "zhuce", "weizhuce", "unregisteredCount", "zhucelv", "avlv", "wailian", "illegalLink"];

var data = [
	<c:if test="${not empty desklist}">
	<c:forEach items="${desklist}" begin="0" end="19" var="item" varStatus="state">
	{
		area:"${item.corpName}",
		winxp:"${item.winxp}",
		winxp_sta:"${item.winxp_sta}",
		win2000:"${item.win2000}",
		win2000_sta:"${item.win2000_sta}",
		win2003:"${item.win2003}",
		win2003_sta:"${item.win2003_sta}",
		winVista:"${item.winVista}",
		winVista_sta:"${item.winVista_sta}",
		buding:"${item.buding}",
		buding_sta:"${item.buding_sta}",
		zhongduan:"${item.zhongduan}",
		zhongduan_sta:"${item.zhongduan_sta}",
		zhuomian:"${item.zhuomian}",
		zhuomian_sta:"${item.zhuomian_sta}",
		weizhuce:"${item.weizhuce}",
		weizhuce_sta:"${item.weizhuce_sta}",
		wailian:"${item.wailian}",
		wailian_sta:"${item.wailian_sta}",
		denglu:"${item.denglu}",
		denglu_sta:"${item.denglu_sta}",
		yingzhuce:"${item.yingzhuce}",
		yingzhuce_sta:"${item.yingzhuce_sta}",
		zhuce:"${item.zhuce}",
		zhuce_sta:"${item.zhuce_sta}",
		zhucelv:"${item.zhucelv}",
		zhucelv_sta:"${item.zhucelv_sta}",
		avlv:"${item.avlv}",
		avlv_sta:"${item.avlv_sta}",
		unregisteredCount:"${item.unregisteredCount}",
		unregisteredCount_sta:"${item.unregisteredCount_sta}",
		illegalLink:"${item.illegalLink}",
		illegalLink_sta:"${item.illegalLink_sta}"
	},
	</c:forEach>
	</c:if>
	{area:"", winxp:"", win2000:""} ];

$(document).ready(function(){
	//alert('hello');
	//alert(data.length);
	//alert(data[0].name);
	var i, j, k;
	for (k = 0; k < zhibiao.length; k++) {
	
		for (i = 0; i < dishi.length; i++) {
			j = dishi[i];
			obj = $('#'+ zhibiao[k] +' td:eq('+ [i+1] +')').find('img');
			addTips(obj, j, k);
			if ( eval('data['+ j +'].'+zhibiao[k]+'_sta') > 0) {
				$('#'+ zhibiao[k] +' td:eq('+ [i+1] +')').find('img').attr("src", "${appPath}/images/lightRed52.png");
			} else if ( eval('data['+ j +'].'+zhibiao[k]+'_sta') < 0 ) {
				$('#'+ zhibiao[k] +' td:eq('+ [i+1] +')').find('img').attr("src", "${appPath}/images/lightGray104.png");
			}
		}
	}
});
</script>
</head>
<body>
<div class="title"><span>桌面监管<span>
	<span>
		<input type="button" value="手动更新" onclick="resetAll();" />
	</span>
</div>
<div class="box">
<b class="rc-tp"><b></b></b>
<div class="main">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr id="wailian">
		<td class="tar"><div>违规外联(IMS)</div></td>
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
	<tr id="illegalLink">
		<td class="tar"><div>违规外联</div></td>
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
	<tr id="zhucelv">
		<td class="tar"><div>终端注册率</div></td>
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
	<tr id="avlv">
		<td class="tar"><div>杀毒软件安装率</div></td>
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
		<td>&nbsp;</td>
		<td><div>本部</div></td>
		<td><div>合肥</div></td>
		<td><div>安庆</div></td>
		<td><div>蚌埠</div></td>
		<td><div>亳州</div></td>
		<td><div>巢湖</div></td>
		<td><div>池州</div></td>
		<td><div>滁州</div></td>
		<td><div>阜阳</div></td>
		<td><div>淮北</div></td>
		<td><div>淮南</div></td>
		<td><div>黄山</div></td>
		<td><div>六安</div></td>
		<td><div>马鞍山</div></td>
		<td><div>宿州</div></td>
		<td><div>铜陵</div></td>
		<td><div>芜湖</div></td>
		<td><div>宣城</div></td>
	</tr>
</table>
</div>
<b class="rc-bt"><b></b></b>
</div>

<div id="tip" style="display:none;"><div class="t_box"><div><s></s><div class="cont">&nbsp;</div></div></div></div>
</body>
</html>