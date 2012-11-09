<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache" />
    <title>会务通平台</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}                   
	${admin_js} 
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>     
</head>
<body>
<div>

        <div>
            <div class="page_title">
				<h3>中奖信息</h3>
			</div>
			<div class="easyui-tabs" border="false" style="padding:10px;">
			<div title="抽奖管理" link="${ctx}/admin/pri/lucky/lucky_findAllLucky.action?meetingId=${meetingId}" style="padding:10px;"></div>
           	<div title="中奖信息" style="padding:10px;" selected="true">
            <form id="listLuckyForm" action="${ctx}/admin/pri/lucky/lucky_findLuckyHistory.action">
                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <input type="hidden" name="meetingId"  value="${meetingId}"/>
                <div class="page_tools page_serach">
                <table style="">
                    <tr>
                        <td align="right" width="320">
                            	手机号码：
                            <input type="text" style="width: 220px; "  name="mobile" maxlength="25" value="${mobile}"/>

                        </td>
                        <td width="80" align="center">                            
							<a id="queryForList" class="btn_common btn_true"  href="#">查询</a>
                        </td>
                    </tr>
                </table>                
                 </div>
            </form>

            <table class="page_datalist" >
                <thead>
                <tr >
                    <th width="1%" style="border-right: 0"></th>
                    <th width="10%">会议编号</th>
                    <th width="15%">中奖人</th>
                    <th width="15%">手机号</th>
                    <th width="15%">奖项</th>
                    <th width="20%">奖品</th>
                    <th width="20%">中奖时间</th>
                    
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="lucky" items="${pager.pageRecords}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if> content="lucky" >
                                <td align="left"></td>
                                <td align="left">${lucky.meeting.id }</td>
                                <td align="left">${lucky.user.name }</td>
                                <td align="left">${lucky.user.mobile}</td>
                                <td align="left">${lucky.lucky.name}</td>
                                <td align="left">${lucky.lucky.award}</td>
                                <td align="left"><fmt:formatDate value="${lucky.createTime}" type="both" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="7" align="center">
                                没有中奖信息.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <div>
           <%@ include file="/pages/common/page.jsp" %>
            </div>
            </div>
            </div>
        </div>
</div>


<script type="text/javascript">
    $(function(){
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
    	
    	$("#queryForList").click(function(){
    		var mobile=$("input[name=mobile]").val();
    		var regex=/^\d{0,11}$/;
    		if(!regex.test(mobile)){
    			alert("手机号只能是小于11位的数字");
    			return ;
    		}
    		$('#listLuckyForm').submit();
    	});
		
    })
</script>
</body>
</html>