<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script type="text/javascript">
function showTtMenus(divId){
	$(""+divId+"").slideToggle();
 }
</script>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>会务通平台管理系统</title>
    ${admin_css}
    ${jquery_js}
    <script type="text/javascript" src="${ctx}/js/admin.js"></script>
    <!-- 主样式表 -->
    <link href='${ctx}/css/colorbox.css' rel='stylesheet' type='text/css'/>
    <script type='text/javascript' src='${ctx}/js/jquery.colorbox.js'></script>
    <script type="text/javascript">//<![CDATA[
    function SetCwinHeight(id) {
        var iframeid = document.getElementById(id); //iframe id
        if (document.getElementById) {

            if (iframeid && !window.opera) {
                if (iframeid.contentDocument && iframeid.contentDocument.body.offsetHeight) {
                    iframeid.height = iframeid.contentDocument.body.offsetHeight;

                } else if (iframeid.Document && iframeid.Document.body.scrollHeight) {

                    iframeid.height = iframeid.Document.body.scrollHeight;
                }
            }
        }
    }

    </script>
</head>
<body>
<div class="header">
    <%@ include file="/pages/admin/pri/frames/topFrame.jsp" %>

    <%--<iframe src="${ctx}/pages/admin/pri/frames/topFrame.jsp" id="top_frame" name="top_frame" width="100%" marginwidth="0" height="100%"--%>
            <%--marginheight="0" scrolling="auto" frameborder="0" hspace="0" vspace="0"></iframe>--%>
</div>

<div class="main">
    <div name="left_frame" id="left_frame" class="left_nav">
        <%@ include file="/pages/admin/pri/frames/leftFrame.jsp" %>
    </div>

    <div class="pages">
        <iframe src="${ctx}/pages/admin/pri/meeting/listMeeting.action" id="mainFrame" name="mainFrame" width="100%"  onload="Javascript:SetCwinHeight(this.id)" marginwidth="0" height="100%"
                marginheight="0" scrolling="auto" frameborder="0" hspace="0" vspace="0"></iframe>
                
        
    </div>
    
</div>

<div class="footer">
    版权所有 © 中国移动通信集团贵州有限公司 2012 
</div>
<div id='image_group'></div>

<script type="text/javascript">//<![CDATA[
//$(document).ready(function() {
//    $('#menu_frame').height($('.left_nav').height());
//
//});

function out_preview(data) {
    if (data.retcode != 0) {
        alert(data.retmsg);
        return;
    }
    var ss = "";
    for (var i = 0; i < data.num; i++) {
        var ts = data.url.substring(0, data.url.length - 5) + i + ".jpg";
        ss += "<a id='img_pre_" + i + "' class='img_pre'  href='" + ts + "'></a>\n";
    }

    $("#image_group").html(ss);
    $(".img_pre").colorbox({rel:'img_pre', transition:"none"});
    $("#img_pre_0").click();
}

//关闭窗口时自动退出
/*
 if(self != top){
 top.location = self.location;
 }
 */
</script>
</body>
</html>
