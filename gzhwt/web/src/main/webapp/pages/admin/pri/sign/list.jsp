<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>签到管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}
    <link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>签到管理  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;" >
		<div title="签到管理" selected="true" style="padding:10px;"></div>
		<div title="RFID签到记录" link="${ctx }/admin/pri/meeting/card_listRfidSignIn.action?rfidSignIn.meetingId=${meetingId}" style="padding:10px;"></div>	
		<div title="签到事件管理" link="${ctx }/admin/pri/meeting/signEvent_findAll.action?meetingId=${meetingId}" style="padding:10px;"></div>	
	</div>
	
	<div class="page_tools page_serach">
	<form id="mainForm" action="${ctx}/admin/pri/sign/list.action">
		<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
		<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		<table width="98%">
		
	      <tr>
	        <th>人员名称：</th>
		    <td><input type="text" id="name" name="name" value="${name}"/></td>                  
	        <th>手机号码：</th>
	        <td><input type="text" id="mobile" name="mobile" value="${mobile}"/></td>
                                   
	        <td><a href="#" id="queryForList" class="btn_common btn_true">搜 索</a></td>
	      </tr>
	    </table>
	    
	</form>
	</div>
	
	<div class="page_tools page_toolbar">
		<a href="#" id="genAllSignCode" class="btn_common btn_false" style="margin-left:5px;">生成签到码</a>
		<font color="gray">（生成所有尚未生成签到码的参会人员的签到码）</font>
		<font color="red">&nbsp;&nbsp;&nbsp;${errMsg }</font>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	            	<th width="2px" style="border-right:0"></th>
	                <th width="7%">会议ID</th>
	                <th width="8%">签到人</th>
	                <th width="12%" >手机号码</th>
	                <th width="8%" >签到码</th>
                    <th width="15%" >签到时间</th>
                    <th width="10%" >签到门户</th>
                    <th>操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="sign" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td ></td>
                                <td>${sign.meetingId }</td>
                                 <td>${sign.user.name }</td>
                                <td>${sign.user.mobile }</td>
                                <td>${sign.signCode }</td>
                                <td ><fmt:formatDate value="${sign.signTime}"
                                                                 type="both"
                                                                 pattern="MM月d日 HH:mm"/></td>
								<td >
                                    <c:choose>
                                        <c:when test="${sign.portalType eq 1}">web
                                        </c:when>
                                        <c:when test="${sign.portalType eq 2}">wap
                                        </c:when>
                                        <c:when test="${sign.portalType eq 3}">客户端
                                        </c:when>
                                    </c:choose></td>                                                                 
                                <td>
                                	<a href="#" onclick="genSignCode(${sign.id});">生成签到码</a>
                                	<a href="${ctx }/admin/pri/meeting/card_listRfidSignIn.action?rfidSignIn.meetingId=${sign.meetingId }&rfidSignIn.user.id=${sign.user.id}">查看RFID签到记录</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="7" align="center"> 没有签到信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>
	</div>
<script type="text/javascript">
	function genSignCode(signId)
	{
		window.location.href = "${ctx}/admin/pri/sign/genSignCode.action?meetingId=${meetingId}&signIn.id=" + signId 
			+ "&mobile="+$("#mobile").val();
	}
	
	$(document).ready(function(){
		$(".easyui-tabs").tabs({  
			onSelect:function(title){  
				var tab = $(this).tabs("getTab", title); 
				var href = tab.attr("link");
				if (href) {
					location.href = href;
					showLoading(title);
					return false;
				}
			}  
		});
		
		$("#genAllSignCode").click(function(){
			var url = "${ctx}/admin/pri/sign/genAllSignCode.action?meetingId=${meetingId}&mobile="+$("#mobile").val();
			window.location.href = url;
		});
		
		$("#queryForList").click(function(){
			$("#mainForm").submit();
		});
	});
	
    jQuery(document).keypress(function(e){
    	if(e.which == 13 ) {
    		var act = document.activeElement.id;
    		switch(act){
    			case 'mobile':$('#queryForList').click();break;
    			case 'jumpPage':jumpTo();break;
    		}
    	} 
    })
</script>
</body>
</html>