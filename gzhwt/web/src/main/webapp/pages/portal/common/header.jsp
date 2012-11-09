<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <title>会务通</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <!--浏览器特性-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!--多终端icon-->
    <link href="favicon.ico" type="image/x-icon" rel="Bookmark"/>
    <link href="favicon.ico" type="image/x-icon" rel="shortcut icon"/>
    <link href="res/logo_apple.png" rel="apple-touch-icon"/>
    ${main_css}
    <link href="${cssdir}/index.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/layout.css" rel="stylesheet" type="text/css" />
    ${jquery_js}
    ${jmpopups_js}      
    ${util_js}
    <!-- My JS -->
    <script type="text/javascript" src="${ctx}/js/portal.js"></script>
    <script type="text/javascript" src="${ctx}/js/ajax-public.js"></script>
    <!-- 主样式表 -->

    <!-- IE识别HTML5标签 -->
    <!--[if IE]>
    <script src="${ctx}/js/html5.js"></script>
    <![endif]-->
    <!-- IE6 PNGfix -->
    <!--[if IE 6]>
    <script src="${ctx}/js/iepng.js" mce_src="${ctx}/js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.iepng');
        /* 将 .iepng 改成你应用了透明PNG的CSS选择器 */
    </script>
    <![endif]-->
	<!--[if IE 6]>
	<style type="text/css">
		#wrap {display:table;height:100%}
	</style>
	<![endif]-->
</head>
<body>


<c:if test="${not empty _portal_meeting_.name && fn:length(_portal_meeting_.name) > 100 }">
<style type="text/css">
.nav .big a {  height:35px; line-height:35px;}
.nav .small {  height:35px; line-height:35px;}
</style>
</c:if>
<div class="header">
	<div class="w960">
		<ul  class="title" style="left:0px;">
           	<li class="iepng">
            	<div style="width:108px; height:53px;">
		            <%-- 显示定制的logo信息 --%>
		            <s:if test="#session.meeting_logo_image_url != null">
	            	<img class="iepng" src="${sessionScope.meeting_logo_image_url }" width="108" height="53" border="0"/>
	            	</s:if>
            	</div>
            </li>
            <li style="margin-top:1px; padding-left:0px;">
                   <h4 title="${_portal_meeting_.name}" style="font-size:22px;width:700px;left:5px;left:0px;" >
         ${_portal_meeting_.name} 
            </h4>
            </li>
           
            
        </ul>
        <%--
             <div class="detail" style="right:0px;bottom:0px;padding-right:0px;padding-bottom:0px;font-size:13px;padding-top:20px;">
            	时间：<fmt:formatDate value="${_portal_meeting_.startTime }" type="both" pattern="yyyy年MM月d日"/> - <fmt:formatDate value="${_portal_meeting_.endTime }" type="both" pattern="yyyy年MM月d日"/><br/>
            	地点：${_portal_meeting_.location}
        </div>
         --%>
        
       
    </div>
    

        
</div>

   <div style="border:1px dashed #800000; padding:15px; display:none;position:fixed;width:160px;height:20px;background-color:#fdd2c5;font-size:19px;" id="div_newMessage" name="div_newMessage"> 	
    </div>

<div class="nav">
    <div class="w960">
        <ul id="menu" class="big">
        	<c:set var="defaultSize" value="7"></c:set>
            <c:forEach var="menu" items="${_portal_menulist_}" varStatus="status">
                <c:if test="${status.count  < defaultSize}">
                    <li id="${menu.id}" url="${menu.contentUrl}"><a href="${ctx}/${menu.contentUrl}${_portal_meeting_.id}&menu_id=${menu.id}&menu_name=${menu.name}">${menu.name}</a></li>
                </c:if>
                <c:if test="${status.count  == defaultSize}">
                    <li class="more">
                    <ul>
                </c:if>
                <c:if test="${status.count  >= defaultSize}">
                    <li id="${menu.id}" url="${menu.contentUrl}"><a href="${ctx}/${menu.contentUrl}${_portal_meeting_.id}&menu_id=${menu.id}&menu_name=${menu.name}">${menu.name}</a></li>
                </c:if>
                <c:if test="${status.count  >= defaultSize&& status.last}">
                    </ul>
                    
            	<a href="javascript://">更 多<span class="arrow"></span></a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
        <ul class="small">
            <li id="mymeeting"><a href="javascript://" class="i05 iepng">我的会议</a></li>
          <!--   <li id="modifyPwd"><a href="${ctx}/portal/pri/person/modifyPwdReq.action?meetingId=${_portal_meeting_.id}" class="i07 iepng">修改密码</a></li> -->
            <li id="exit"><a href="javascript://" class="i06 iepng">退出系统</a></li>
        </ul>
    </div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	//动态处理顶部部署图片
	<c:if test="${not empty sessionScope.meeting_top_image_url }">
	$(".header").css("background", "url(${sessionScope.meeting_top_image_url }) top center no-repeat");
	</c:if>
	<c:if test="${empty sessionScope.meeting_top_image_url }">
	$(".header").css("background", "url(${ctx }/images/portal/top_bg.jpg) top center no-repeat");
	</c:if>
	
	$("#mymeeting").bind("click", function() {
		window.location = "${ctx}/portal/pri/meeting/getAttendMeetingList.action";
		return false;
	});
	
	$("#exit").bind("click", function() {
		window.location = "${ctx}/portal/pri/base/logout.action";
		return false;
	});
	
	$("#menu li").removeClass("act");
	$("#menu li").each(function() {    
		//highlight navigation menu item
		var url = window.location + "";
		var id = $(this).first().attr("id")==""?"undefined":$(this).first().attr("id");
		var exp = "menu_id=" + id;
		if (url.search(exp) != -1) {
			$(this).addClass("act");
		} else if (id == "${param.menu_id}") {
			$(this).addClass("act");
		}
	});
	//set breadcrumb caption
	if($('#menu').find(".act").length > 0){
		var menu = $('#menu').find(".act").first();
		var link = $(menu).find("a").first().attr("href");
		var value = link.match(new RegExp("(^|&)menu_name=([^&]*)(&|$)"));
		var menu_name = (value == null ? "" : unescape(value[2]));
		var title = $("#caption").attr("value");
		if (title) {
			$("#caption").html(menu_name + " - " + title);
		} else {
			$("#caption").html(menu_name);
		}
	}
	
	
	
	
	checkNewMessage();
	locateMessageDiv();
	//去获取最新的私信
	setInterval("checkNewMessage()",60000);
	/**
	window.document.onscroll = function() {
		locateMessageDiv();
	}
	window.onresize = locateMessageDiv;*/
	
});

/**
 * 重定位DIV
 */
function locateMessageDiv(){
	//var windowWidth = $(window).width();
	//var windowHeight = $(window).height();
	//var posX = windowWidth-200;
	//var posY = windowHeight-60;
	//alert(posX);
	//alert(posY);
	//$("#div_newMessage").css("left", posX);
	//$("#div_newMessage").css("top", posY);
	//判断是否为IE6
	if($.browser.msie && parseInt($.browser.version) <= 6){
		$("#div_newMessage").css("position", "absolute");
	}
	
	$("#div_newMessage").css("bottom", 2);
	$("#div_newMessage").css("right", 10);
}


//此处进行AJAX请求
function checkNewMessage(){
	  var url="/portal/pri/message/checkNewMessage.action?meetingId=${_portal_meeting_.id}";
	  //alert(${_portal_meeting_.id});
	  ajaxRequest(url,getNewMessage,false);
	  function getNewMessage(data){
		 var count=data.count;
		  if(count>0){
			  $("#div_newMessage").html("<a href=\"${ctx}/portal/pri/message/list.action?meetingId=${_portal_meeting_.id}\">您有"+count+"条新的私信消息!</a>");
			  $("#div_newMessage").show();
		  }else{
			  $("#div_newMessage").hide();
		  }
	  }
}

//$('body').everyTime('1s',test);
    
function getMenuId(){
	var qs = "${pageContext.request.queryString}";
	var p = qs.split("&");
	for(var i=0;i<p.length;i++){
		if(p[i].indexOf("menu_id")>=0)
		{	
			return p[i].substring(8);
		}
	}
	return "";
}

</script>