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


<style type="text/css">
 body {
        background: #e6edf3;
        font-family: "Lucida Grande", Helvetica, Arial, sans-serif;
        font-size: 12px;
}

#dlmenu {height:30em;}
#menu {list-style-type:none; margin:0 0 1px; padding:0; position:relative; width:13em;z-index:100;}
#menu li {display:block; padding:0; margin:0; position:relative; z-index:100;}
#menu li a, #menu li a:visited {display:block; text-decoration:none;}
#menu li dd {display:none;}
#menu li:hover, #menu li a:hover {border:0;}
#menu li:hover dt a , #menu li a:hover dt a {background:#9bce33 center center; color:black; }

#menu li:hover dd, #menu li a:hover dd {display:block;background:#e6edf3}
#menu li:hover dl, #menu li a:hover dl {height:20em; background:#e6edf3;}
#menu table {border-collapse:collapse; padding:0; margin:-4px; font-size:1em;}

#menu dl {width: 13em; margin: 0; background: #e6edf3; cursor:pointer;}
#menu dt {margin:0; padding: 0; font-size: 1.1em; border-top:1px solid #e6edf3;background:#e6edf3;}
#menu dd {margin:0; padding:0; font-size: 1em; text-align:left; }
.gallery dt a, .gallery dt a:visited {display:block; color:black; padding:5px 5px 5px 5px; background:#9bce33 center center;}
.gallery dd a {color:black; min-height:1em; text-decoration:none; display:block; padding:0px 0px 0px 0px; background:#e6edf3;}
.gallery dd a, .gallery dd a:visited {color:black; min-height:1em; text-decoration:none; display:block; padding:0px 0px 0px 0px; background:#e6edf3;}
* html .gallery dd a, * html .gallery dd a:visited {height:1em;background:#9bce33}
.gallery dd a:hover {background:#9bce33; color:black;}

</style>



<div class="main" >
	<div class="left_nav">
	   	<dl>
        	<dt><h5>会议管理</h5></dt>         
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/listMeeting.action" class="act" onclick="hideObj($('#editMeeting'),'${ctx}');" >
				<span class="nav">会议列表</span>
			</a></dd>   
			
			<%--
			<c:if test="${ SESSION_ADMIN_USER.role.id eq 1 || SESSION_ADMIN_USER.role.id eq 3 }">
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_baseinfo" onclick="hideObj($('#editMeeting'),'${ctx}');">
				<span class="nav">添加会议</span>
			</a></dd>   
			 
			 
			<dd><a target = "mainFrame" href="${ctx}/admin/pri/meeting/guide_begin.action" onclick="hideObj($('#editMeeting'),'${ctx}');">
				<span class="nav">会议向导</span>
			</a></dd> 
			 
			</c:if> --%>
        </dl>
        

   
<c:set var="meetingId" value="${param.meetingId}"/>

	<dt><h5>会议管理</h5></dt>      
	<div id="dlmenu">
	<ul id="menu">
	<li>
	<dl class="gallery">
	<dt><a href="#" class="act">模块1</a></dt>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_baseinfo&meeting.id=${meetingId}"><span class="nav">基本信息</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?isAdmin=1&meeting.id=${meetingId}"><span class="nav">参会人员</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/agenda/agendaList.action?meetingId=${meetingId}"><span class="nav">会议议程</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meetingId}"><span class="nav">资料管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/basemenu/getBaseMenuPages.action?meetingId=${meetingId}"><span class="nav">菜单管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/listMeetingDinner.action?meetingId=${meetingId}"><span class="nav">用餐管理</span></a></dd>
	</dl>
	</li>
	
	<li>
	<dl class="gallery">
	<dt><a href="#">模块2</a></dt>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/group/list.action?meetingId=${meetingId}"><span class="nav">分组模版</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=edit_map&meeting.id=${meetingId}"><span class="nav">会场位置</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/message/getMessageList.action?meetingId=${meetingId}"><span class="nav">短信管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/news/list.action?meetingId=${meetingId}"><span class="nav">信息发布</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/interaction/postList.action?meetingId=${meetingId}"><span class="nav">互动管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}"><span class="nav">嘉宾管理</span></a></dd>
	</dl>
	</li>
	
	
	<li>
	<dl class="gallery">
	<dt><a href="#">模块3</a></dt>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/lucky/lucky_findAllLucky.action?meetingId=${meetingId}"><span class="nav">抽奖管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/sign/list.action?meetingId=${meetingId}"><span class="nav">签到管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/gift/list.action?meetingId=${meetingId}"><span class="nav">礼品管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/journey/listVehicle.action?meetingId=${meetingId}"><span class="nav">车辆管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/meeting/card_listIssue.action?meetingId=${meetingId}"><span class="nav">卡牌管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/hotel/list.action?meetingId=${meetingId}"><span class="nav">酒店管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/custom/meeting_goViewConfig.action?meetingId=${meetingId}"><span class="nav">界面定制</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/asset/getAssetLst.action?meetingId=${meetingId}"><span class="nav">资产管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/task/getDetailListPager.action?meetingId=${meetingId}"><span class="nav">任务管理</span></a></dd>
	</dl>
	</li>
	
	<li>
	<dl class="gallery">
	<dt><a href="#">模块4</a></dt>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/lucky/lucky_findAllLucky.action?meetingId=${meetingId}"><span class="nav">抽奖管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/task/getDetailListPager.action?meetingId=${meetingId}"><span class="nav">任务管理</span></a></dd>
	</dl>
	</li>
	
	<li>
	<dl class="gallery">
	<dt><a href="#">模块5</a></dt>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/lucky/lucky_findAllLucky.action?meetingId=${meetingId}"><span class="nav">抽奖管理</span></a></dd>
	<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="${ctx}/admin/pri/task/getDetailListPager.action?meetingId=${meetingId}"><span class="nav">任务管理</span></a></dd>
	</dl>
	</li>
		
	 </ul>
	
	</div>
	
       <%-- <dl id="editMeeting" style="display:none">	
        </dl> --%>
   <c:if test="${ SESSION_ADMIN_USER.role.id eq 1 || SESSION_ADMIN_USER.role.id eq 3 }">
        <dl>
        	<dt><h5>系统管理</h5></dt>
        	<c:if test="${ SESSION_ADMIN_USER.canOrg eq 1 }">
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/org/listAndTree.jsp">
					<span class="nav">组织机构管理</span>
				</a></dd>
			</c:if>
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/user/listAndTree.jsp">
					<span class="nav">系统用户管理</span>
				</a></dd>
			<c:if test="${ SESSION_ADMIN_USER.role.id eq 1 }">
			
				<%-- 
			<dd><a target = "mainFrame" href="${ctx}/admin/pri/custom/meetingType_list.action" >
					<span class="nav">会议类型管理</span>
				</a></dd>
			--%>
			
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





<script>
$(function() {
	$( "#accordion" ).accordion();
});
</script>
