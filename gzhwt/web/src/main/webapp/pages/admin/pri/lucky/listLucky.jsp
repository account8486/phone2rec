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
				<h3>奖项信息</h3>
			</div>
			<div class="easyui-tabs" border="false" style="padding:10px;">
			<div title="抽奖管理" style="padding:10px;" selected="true">
            <form id="listLuckyForm" action="${ctx}/admin/pri/lucky/lucky_findAllLucky.action">
                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <input type="hidden" name="meetingId"  value="${meetingId}"/>
                <input type="hidden" id="queryForList"/>
                 <div class="page_tools page_toolbar">
	                 <a class="btn_common btn_false" href="${ctx}/admin/pri/lucky/lucky_switchToAddLucky.action?meetingId=${meetingId}">添加奖项</a>
	                 <a class="btn_common btn_false" href="#" id="beginLucky">开始抽奖</a>
	             </div>
            </form>

            <table class="page_datalist" >
                <thead>
                <tr >
                    <th width="1%" style="border-right: 0"></th>
                    <th width="10%">会议编号</th>
                    <th width="20%">奖项</th>
                    <th width="34%">奖品</th>
                    <th width="20%">状态</th>
                    <th width="15%" colspan="1">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="lucky" items="${pager.pageRecords}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if> content="lucky" >
                                <td align="left"></td>
                                <td align="left">${lucky.meeting.id }</td>
                                <td align="left">${lucky.name }</td>
                                <td align="left">${lucky.award }</td>
                                <td align="left">${lucky.state==1?'启用':'不启用' }</td>
                                <td align="center">
	                                <a href="#" class="updateLucky" luckyId="${lucky.id }">编辑</a>&nbsp;&nbsp;
	                                <a href="#" class="deleteLucky" luckyId="${lucky.id }">删除</a>
	                                <a href="#" class="clearLucky"  luckyId="${lucky.id }">清除结果</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="6" align="center">
                                没有奖项信息.
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
            <div title="中奖信息" link="${ctx}/admin/pri/lucky/lucky_findLuckyHistory.action?meetingId=${meetingId}" style="padding:10px;"></div>
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
    		$('#listLuckyForm').submit();
    	});
    	
    	/*删除奖项 */
		$(".deleteLucky").click(function(e){
			var id=$(e.target).attr("luckyId");
			var param={"id":id};
			if(confirm("确认要删除吗?")){
				$.post("${ctx}/admin/pri/lucky/lucky_deleteLucky.action",param,function(data){
					$(e.target).parent().parent().remove();
					var size=$("tr[content]").size();
					if(size<=0){
						window.location.href="${ctx}/admin/pri/lucky/lucky_findAllLucky.action?meetingId=${meetingId}";
					}
					
				});
			}
		});
    	
    	/*清除奖项中奖结果*/
		$(".clearLucky").click(function(e){
			var id=$(e.target).attr("luckyId");
			var param={"id":id,"meetingId":${meetingId}};
			if(confirm("确认要清除此奖项的中奖结果吗?")){
				$.post("${ctx}/admin/pri/lucky/lucky_clearLuckyHistory.action",param,function(data){
					if(data.retmsg=="ok"){
						alert("清除完成");						
						
					}
				});
			}
		});
    	
		/*修改奖项*/
		$(".updateLucky").click(function(e){
			var id=$(e.target).attr("luckyId");
			window.location.href="${ctx}/admin/pri/lucky/lucky_findLuckyById.action?currentPage=${pager.currentPage}&flag=edit&meetingId=${meetingId}&id="+id;
		});
		
		/*开始抽奖功能*/
		$("#beginLucky").click(function(){
			var meetingId="${meetingId}";
			var param={"meetingId":meetingId,"flag":"ajax"};
			$.post("${ctx}/admin/pri/lucky/lucky_findAllLucky.action",param,function(data){
				if(data.size>0){
					window.open("${ctx}/admin/pri/lucky/lucky_beginLucky.action?meetingId=${meetingId}","","fullscreen=1,menubar=0,toolbar=0,directories=0,location=0,status=0,scrollbars=0");
				}else{
					alert("您还没有添加奖项");
				}
			});
		});
		
    })
</script>
</body>
</html>