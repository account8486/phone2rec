<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <title>云平台</title>
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

<div id="wrap">
	<div id="main" class="clearfix">

<div class="header">
    <div class="w960">
    	<div style="float: left; width: 110px; margin-top: 20px;">
    		<%-- 显示定制的logo信息 --%>
            <s:if test="#session.pageCustomMeetingTypeId != null">
            	<img src="${ctx}/portal/pri/custom/showLogoImage.action?meetingTypeId=${sessionScope.pageCustomMeetingTypeId}" width="108" height="53" border="0"/>
            </s:if>
    	</div>
    	<div class="title">
            <h3 id="title_meeting_name" title="${_portal_meeting_.name}" >${fn:escapeXml(_portal_meeting_.name)}</h3>
        </div>
        <div class="detail">
            时间：<fmt:formatDate value="${_portal_meeting_.startTime }" type="both" pattern="yyyy年MM月d日"/> - <fmt:formatDate value="${_portal_meeting_.endTime }" type="both" pattern="yyyy年MM月d日"/><br/>
            地点：${_portal_meeting_.location}
        </div>
    </div>
</div>

<div class="nav">
    <div class="w960">
        <ul id="menu" class="big">
            <c:forEach var="menu" items="${_portal_menulist_}" varStatus="status">
                <c:if test="${status.count  < 7}">
                    <li id="${menu.id}" url="${menu.contentUrl}"><a href="${ctx}/${menu.contentUrl}${_portal_meeting_.id}&menu_id=${menu.id}&menu_name=${menu.name}">${menu.name}</a></li>
                </c:if>
                <c:if test="${status.count  == 7}">
                    <li class="more">
                    <ul>
                </c:if>
                <c:if test="${status.count  >= 7}">
                    <li id="${menu.id}" url="${menu.contentUrl}"><a href="${ctx}/${menu.contentUrl}${_portal_meeting_.id}&menu_id=${menu.id}&menu_name=${menu.name}">${menu.name}</a></li>
                </c:if>
                <c:if test="${status.count  >= 7 && status.last}">
                    </ul>
                    <a href="javascript://">更 多<span>︾</span></a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
        <ul class="small">
            <li id="mymeeting"><a href="javascript://" class="i05 iepng">我的列表</a></li>
            <li id="modifyPwd"><a href="${ctx}/pages/portal/pri/person/modifyPwd.jsp" class="i07 iepng">修改密码</a></li>
            <li id="exit"><a href="javascript://" class="i06 iepng">退出系统</a></li>
        </ul>
    </div>
</div>

<script type="text/javascript">
$(document).ready(function() {
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
	
});
    
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