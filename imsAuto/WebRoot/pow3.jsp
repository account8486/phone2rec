<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="common.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="bean.PowerData"%>
<%
	String appPath = request.getContextPath();
	request.setAttribute("appPath", appPath);

	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>黄山路20楼机房供电监管</title>
		<link href="${appPath}/images/common.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="${appPath}/images/jquery.min.js"></script>
		<script type="text/javascript">

function addTips(imgObj) {
    var contInfo = 'ssssss';
	imgObj.mouseover(function(){
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
function addTips(imgObj, info) {
   imgObj.mouseover(function(event){//绑定事件处理
		event.stopPropagation();
		var offset = $(event.target).offset();//取消事件冒泡
		$("#racePop").css({ top:offset.top + $(event.target).height() + "px", left:offset.left });//设置弹出层位置
		$('#racePop').html(info);
		$("#racePop").show(speed);//动画显示
	});
	imgObj.mouseout(function(event) { $("#racePop").hide(speed) });//单击弹出层则自身隐藏
}
var speed = 300;//动画速度
$(document).ready(function(){
	var obj = $('#db_1');
	var info1 = "${power1.alarmInf}";
	if (info1 != '设备运行正常') {
		obj.css('color', 'red');
	}
	addTips(obj, info1);
	var obj2 = $('#db_2');
	var info2 = "${power2.alarmInf}";
	if (info2 != '设备运行正常') {
		obj2.css('color', 'red');
	}
	addTips(obj2, info2);
});
</script>
		<style type="text/css">
body {color:#fff;background:url(${appPath}/images/pow3.png) no-repeat; background-color: black; background-position: top center; }


.back1 {
	background: #000;
	text-align: center;
	font-size: 14px;
	font-family: '黑体';
}






.raceShow {
	color: #333;
	background-color: #f1f1f1;
	border: solid 1px #ccc;
	position: absolute;
	display: none;
	width: 200px;
	height: 100px;
	padding: 5px;
	font-size: 14px;
}
</style>
	</head>
	<body>
		<div
			style="width: 1341px; height: 756px; position: absolute; left: 50px;">
			
			<div id='db_1' class="back1"
				style="width: 47px; height: 74px; line-height: 62px; position: absolute; top: 381px; left: 219px;">
				UPS1
			</div>
			
			
			<div id='db_2' class="back1"
				style="width: 49px; height: 73px; line-height: 62px; position: absolute; top: 382px; left: 436px;">
				UPS2
			</div>
			
		</div>
		<div id="tip" style="display: none;">
			<div class="t_box">
				<div>
					<s></s>
					<div class="cont">
						&nbsp;
					</div>
				</div>
			</div>
		</div>
		<div id="racePop" class="raceShow">
			这里是弹出层效果
		</div>
	</body>
</html>