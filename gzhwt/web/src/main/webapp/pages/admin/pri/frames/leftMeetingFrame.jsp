 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 <%@ include file="/pages/common/taglibs.jsp" %>
 <%@ page import="java.util.*" %>
 <link href='${ctx}/css/style.css' rel='stylesheet' type='text/css' />
 
 ${jquery_js}
     


<%--System.out.println(new Date()+"enter meeting left frames!@!!!"); --%>
  <c:if test="${not empty unitViewList }">
	      		<c:forEach var="securityUnitView" items="${unitViewList}" varStatus="status">
	      		<dt><h5>${securityUnitView.securityParentUnit.unitName}</h5></dt>
	      	        <c:if test="${not empty securityUnitView.sonUnitList}">
	      	        	<dl>
	      	          	<c:forEach var="sonUnit" items="${securityUnitView.sonUnitList}" varStatus="status">
	      	          	   <dd><a  target = "mainFrame" href="${ctx}/${sonUnit.unitUrl}"  <c:if test="${sonUnit.returnDefaultMenu}"> onclick="switchCss($(this));hideObj($('#editMeeting'),'${ctx}');" </c:if>  onclick="switchCss($(this));" >
									<span class="nav">${sonUnit.unitName } </span>
								</a></dd>  
	      	         </c:forEach>
	      	         </dl>
	      	        </c:if>
	      	 </c:forEach>
 </c:if>
 
<%--
<div class="main" >
	<div class="left_nav">
	
	   	<dl>
        	<dt><h5>会议管理</h5></dt>         
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/listMeeting.action" class="act" onclick="hideObj($('#editMeeting'),'${ctx}');" >
				<span class="nav">会议列表</span>
			</a></dd>   
			<c:if test="${ SESSION_ADMIN_USER.role.id eq 1 || SESSION_ADMIN_USER.role.id eq 3 }">
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_baseinfo" onclick="hideObj($('#editMeeting'),'${ctx}');">
				<span class="nav">添加会议</span>
			</a></dd>  
			
			<dd><a target = "mainFrame" href="${ctx}/admin/pri/meeting/guide_begin.action" onclick="hideObj($('#editMeeting'),'${ctx}');">
				<span class="nav">会议向导</span>
			</a></dd> 
			 
			</c:if>
        </dl>
        

   
<c:set var="meetingId" value="${param.meetingId}"/>

	<div>
	
	<%--会议安排 
	<a href="#" onclick="showTtMenus('#sonDiv_1');"><dt><h5>会议安排</h5></dt></a>
	<div id="sonDiv_1" class="show">
	<dl>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_baseinfo&meeting.id=${meetingId}"><span class="nav">基本信息</span></a></dd>

	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/guide_step1.action?fromStep=step2&meeting.id=${meetingId}"><span class="nav">进入向导</span></a></dd>

	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?isAdmin=1&meeting.id=${meetingId}"><span class="nav">参会人员</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/agenda/agendaList.action?meetingId=${meetingId}"><span class="nav">会议议程</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/group/list.action?meetingId=${meetingId}"><span class="nav">分组模版</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/pages/admin/pri/seat/ManagerSeat.html?contextPath=${ctx}&meetingId=${meetingId}"><span class="nav">会场设置</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meetingId}"><span class="nav">资料管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/news/list.action?meetingId=${meetingId}"><span class="nav">信息发布</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/listMeetingDinner.action?meetingId=${meetingId}"><span class="nav">用餐管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/interaction/postList.action?meetingId=${meetingId}"><span class="nav">互动管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/message/getMessageList.action?meetingId=${meetingId}"><span class="nav">短信管理</span></a></dd>
	
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/card_listRfidSignIn.action?rfidSignIn.meetingId=${meetingId}"><span class="nav">签到管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/card_listIssue.action?meetingId=${meetingId}"><span class="nav">卡牌管理</span></a></dd>
	
	</dl>
	</div>
	
	<%--接待安排 --%>
	<!--  
	<a href="#" onclick="showTtMenus('#sonDiv_2');"><dt><h5>接待安排</h5></dt></a>
	<div id="sonDiv_2" style="display:none">
	<dl>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/listMeetingDinner.action?meetingId=${meetingId}"><span class="nav">用餐管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=edit_map&meeting.id=${meetingId}"><span class="nav">会场位置</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/hotel/list.action?meetingId=${meetingId}"><span class="nav">酒店管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/journey/listVehicle.action?meetingId=${meetingId}"><span class="nav">车辆管理</span></a></dd>
	</dl>
	</div>
	-->
	
	<%--会场互动 --%>
	<!--  
	<a href="#" onclick="showTtMenus('#sonDiv_3');"><dt><h5>会场互动</h5></dt></a>	
	<div id="sonDiv_3" style="display:none">
	<dl>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/interaction/postList.action?meetingId=${meetingId}"><span class="nav">互动管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/lucky/lucky_findAllLucky.action?meetingId=${meetingId}"><span class="nav">抽奖管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/liveService_browse.action?meetingId=${meetingId}"><span class="nav">会场服务</span></a></dd>
	</dl>
	</div>
	-->
	
	<%--会务管理 
	
	<a href="#" onclick="showTtMenus('#sonDiv_4');"><dt><h5>会务管理</h5></dt></a>	
	<div id="sonDiv_4" style="display:none">
	<dl>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/message/getMessageList.action?meetingId=${meetingId}"><span class="nav">短信管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/sign/list.action?meetingId=${meetingId}"><span class="nav">签到管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/card_listIssue.action?meetingId=${meetingId}"><span class="nav">卡牌管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/asset/getAssetLst.action?meetingId=${meetingId}"><span class="nav">资产管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/task/getDetailListPager.action?meetingId=${meetingId}"><span class="nav">任务管理</span></a></dd>
	</dl>
	</div>
	--%>
	
	
	<%--界面个性化
	<a href="#" onclick="showTtMenus('#sonDiv_5');"><dt><h5>界面个性化</h5></dt></a>	
	<div id="sonDiv_5" style="display:none">
	<dl>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/basemenu/getBaseMenuPages.action?meetingId=${meetingId}"><span class="nav">菜单管理</span></a></dd>
	<dd><a onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/custom/meeting_goViewConfig.action?meetingId=${meetingId}"><span class="nav">界面定制</span></a></dd>
	</dl>
	</div>
	</div>
	 --%>
	
	
       <%-- <dl id="editMeeting" style="display:none">	
       </dl> --%>
       
        <%--
        <c:if test="${ SESSION_ADMIN_USER.role.id eq 1 || SESSION_ADMIN_USER.role.id eq 3 }">
        <dl>
        <a href="#" onclick="showTtMenus('#div_sys_manager');"><dt><h5>系统管理</h5></dt></a>	
        <div id="div_sys_manager" style="display:none;">
        	<c:if test="${ SESSION_ADMIN_USER.canOrg eq 1 }">
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/org/listAndTree.jsp">
					<span class="nav">组织机构管理</span>
				</a></dd>
			</c:if>
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/user/listAndTree.jsp">
					<span class="nav">系统用户管理</span>
				</a></dd>
			<c:if test="${ SESSION_ADMIN_USER.role.id eq 1 }">
			
			<dd><a target = "mainFrame" href="${ctx}/admin/pri/custom/meetingType_list.action" >
					<span class="nav">会议类型管理</span>
				</a></dd>
			
			
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/version/list.action" onclick="hideObj($('#editMeeting'),'${ctx}');">
					<span class="nav">客户端版本管理</span>
				</a></dd>
		
			<dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/access.jsp" onclick="hideObj($('#editMeeting'),'${ctx}');">
					<span class="nav">统计查询</span>
				</a></dd>
		  	<dd><a target = "mainFrame" href="${ctx}/admin/pri/config/systemConfig.action" onclick="hideObj($('#editMeeting'),'${ctx}');">
					<span class="nav">系统配置</span>
				</a></dd>	 
        	</c:if>
        </div>
        </dl>
        </c:if>
 					
        
        <dl>
        
        <a href="#" onclick="showTtMenus('#div_person_set');"><dt><h5>个人设置</h5></dt></a>	
        <div id="div_person_set" style="display:none;">
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/user/modifyPwd.jsp" >
					<span class="nav">修改密码</span>
				</a></dd>
		</div>
        </dl>
        
    </div>    
</div>
--%>
