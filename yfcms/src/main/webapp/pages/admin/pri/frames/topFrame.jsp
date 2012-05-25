<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="header">
	<h3></h3>
    <div class="nav">
    	<ul>
        	<li class="act"><a href="javascript://">当前位置</a></li>
            <li><a id="logout"  href="javascript://">退出系统</a></li>
        </ul>
    </div>
    <div class="user">
    	欢迎您！
    </div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		var mainFrame = top.document.getElementById("mainFrame");
		$("#logout").bind("click", function(){
			window.location="${ctx}/admin/pri/base/logout.action";
			return false;
		});
		$("#mymeeting").bind("click", function(){
			mainFrame.src="${ctx}/pages/admin/pri/user/listAndTree.jsp";
		});	
	});
</script>
