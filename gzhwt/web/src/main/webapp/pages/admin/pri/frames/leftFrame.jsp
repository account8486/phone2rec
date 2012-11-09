 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 <%@ include file="/pages/common/taglibs.jsp" %>
<script type="text/javascript" src="${ctx}/js/admin.js"></script>
<link href='${ctx}/css/style.css' rel='stylesheet' type='text/css' />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">  
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">       
<META HTTP-EQUIV="Expires" CONTENT="0">  

${admin_css}  
${jquery_js}
${accordion_jquery_ui_css }
${accordion_jquery_ui_js }

<div class="main" >
	<div class="left_nav">
	   	<dl>
        	<dt><h5>会议管理</h5></dt>         
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/listMeeting.action" class="act">
				<span class="nav">会议列表</span>
			</a></dd>   
			<c:if test="${ SESSION_ADMIN_USER.role.id eq 1 || SESSION_ADMIN_USER.role.id eq 3 }">
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_baseinfo">
				<span class="nav">添加会议</span>
			</a></dd>   
			 
			 
			<dd><a target = "mainFrame" href="${ctx}/admin/pri/meeting/guide_begin.action">
				<span class="nav">会议向导</span>
			</a></dd> 
			 
			</c:if>
        </dl>
	<div id="editMeeting" style="display:none">
	</div>
       <%-- <dl id="editMeeting" style="display:none">	
        </dl> --%>
        <c:if test="${ SESSION_ADMIN_USER.role.id eq 1 || SESSION_ADMIN_USER.role.id eq 3 }">
        <dl>
        	<dt><h5>系统管理</h5></dt>
        	<c:if test="${ SESSION_ADMIN_USER.canOrg eq 1 }">
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/org/listAndTree.jsp" >
					<span class="nav">组织机构管理</span>
				</a></dd>
			</c:if>
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/user/listAndTree.jsp" >
					<span class="nav">系统用户管理</span>
				</a></dd>
				
	
			
			<c:if test="${ SESSION_ADMIN_USER.role.id eq 1 }">
			
				<%-- 
			<dd><a target = "mainFrame" href="${ctx}/admin/pri/custom/meetingType_list.action" >
					<span class="nav">会议类型管理</span>
				</a></dd>
			--%>
			 <dd><a target = "mainFrame" href="${ctx}/admin/pri/security/list.action">
				<span class="nav">系统角色管理</span>
			</a></dd> 
			
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/version/list.action" >
					<span class="nav">客户端版本管理</span>
				</a></dd>
		
			<dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/access.jsp">
					<span class="nav">统计查询</span>
				</a></dd>
		  	<dd><a target = "mainFrame" href="${ctx}/admin/pri/config/systemConfig.action" >
					<span class="nav">系统配置</span>
				</a></dd>	 
        	</c:if>
        </dl>
        </c:if>
        <dl>
        	<dt><h5>个人设置</h5></dt>
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/user/modifyPwd.jsp" >
					<span class="nav">修改密码</span>
				</a></dd>
        </dl>
    </div>    
</div>   