<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="common.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="bean.PowerData,dao.DevMonitorpdDao"%>


<%
	String appPath = request.getContextPath();
	request.setAttribute("appPath", appPath);

	HashMap<String, PowerData> powMap = (HashMap<String, PowerData>) request
			.getSession().getAttribute("powMap");
	PowerData power1 = powMap.get("地下室1号UPS");
	PowerData power2 = powMap.get("地下室2号UPS");
	request.setAttribute("power1", power1);
	request.setAttribute("power2", power2);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>芜湖路机房供电监管</title>
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
body {color:#fff; background:url(${appPath}/images/powerSupply2.png) no-repeat; background-color: black; background-position: top center; }
.back {
	background: red;
}

.back1 {
	background: #000;
	text-align: center;
	font-size: 14px;
	font-family: '黑体';
}

#tip {
	position: absolute;
	color: #333;
	display: none;
}

#tip s {
	display: block;
	position: absolute;
	top: -20px;
	left: 52px;
	width: 0;
	height: 0;
	line-height: 0;
	font-size: 0;
	border-color: transparent transparent #FFF;
	border-style: dashed dashed solid;
	border-width: 10px;
}

#tip .t_box {
	position: relative;
	background-color: #CCC;
	bottom: 0;
	right: 0;
}

#tip .t_box div {
	position: relative;
	background-color: #FFF;
	background: #FFF;
	top: 0;
	left: 0;
}

#tip .t_box .cont {
	font-size: 18px;
	padding: 0.5em;
	white-space: nowrap;
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
			
			<%
			    String agentName="";
			    DevMonitorpdDao dao=new DevMonitorpdDao();
			    
			    
			 %>
			
			
			
			<div id="div1" class="back"
				style="display:none;width: 60px; height: 50px; position: absolute; top: 260px; left: 100px;" onMouse>
				<%
				   String devName="";
				   devName="地下室1号UPS";
				   String tips=dao.getwuhusdList(agentName,devName);
				   if(StringUtil.isNotEmpty(tips)){
				   
				  %>
				  <script type="text/javascript">
				     document.getElementById("div1").style.display = "block";
				  </script>
				  <%
				   }
				 %>
				 
				地下室北侧UPS1
			</div>
			
			
			
			
			
			
			<div class="back"
				style="width: 60px; height: 50px; position: absolute; top: 260px; left: 290px;">
				ATS
			</div>
			<div class="back"
				style="width: 60px; height: 50px; position: absolute; top: 260px; left: 520px;">
				地下室北侧UPS2
			</div>
			
			<div class="back"
				style="width: 60px; height: 50px; position: absolute; top: 260px; left: 800px;">
				12层南侧精密空调配电柜
			</div>

			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 420px; left: 1118px;">
				12层南区UPSA
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 420px; left: 1250px;">
				12层南区UPSB
			</div>

			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 2px;">
				地下室UPSA
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 52px;">
				13层北2区UPSA
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 108px;">
				13层北1区UPSA
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 162px;">
				13层南3区UPSA
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 220px;">
				13层南2区UPSA
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 278px;">
				13层南1区UPSA
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 335px;">
				14层北区UPSA
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 392px;">
				14层南区UPSA
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 450px;">
				调度大厅UPSA
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 530px;">
				地下室UPSB
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 586px;">
				13层北2区UPSB
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 640px;">
				13层北1区UPSB
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 693px;">
				13层南3区UPSB
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 752px;">
				13层南2区UPSB
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 806px;">
				13层南1区UPSB
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 862px;">
				14层北区UPSB
			</div>
			<div class="back"
				style="width: 40px; height: 50px; position: absolute; top: 448px; left: 916px;">
				14层南区UPSB
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