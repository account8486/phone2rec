<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--[if IE 6]>


<script src="${ctx}/js/DD_belatedPNG.js"></script>
<script>
	/* IE6下png图片透明设置 */
  DD_belatedPNG.fix('.png_bg');  
</script>
<![endif]--> 


<div class="header_top">
    <div class="header_top_logo">
        <img class="png_bg" src="${ctx}/images/common/logo.png">
    </div>
    <div class="welcome">
        <span>欢迎您, ${SESSION_USER.name}</span>
        <img class="png_bg"  src="${ctx}/images/logout.png" /><a href="${ctx}/portal/pri/base/logout.action">退出</a>
    </div>
</div>