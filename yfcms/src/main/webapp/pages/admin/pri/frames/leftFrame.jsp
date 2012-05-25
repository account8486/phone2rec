 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${ctx}/js/admin.js"></script>
<div class="main" >
	<div class="left_nav">

        <dl>
        	<dt><h5>系统管理</h5></dt>

            <dd><a target = "mainFrame" href="${ctx}/admin/pri/user/list.action" >
					<span class="nav">系统用户管理</span>
				</a></dd>
				
        </dl>
      
        <dl>
        	<dt><h5>个人设置</h5></dt>
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/user/modifyPwd.jsp" >
					<span class="nav">修改密码</span>
				</a></dd>
        </dl>
    </div>    
</div>   