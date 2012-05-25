<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache" />
    <title>云平台管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}                   
	${admin_js}    
</head>
<body>
<div>

        <div>
            <div class="page_title">
				<h3>云类型列表</h3>
			</div>
			
			<div class="page_tools page_toolbar">
                 <a class="btn_common btn_false" href="javascript:forwardReq()">定义类型</a>
            </div>
              	
            <form id="listMeetingForm" action="${ctx}/admin/pri/custom/meetingType_list.action">
                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <div class="page_tools page_serach">
                <table style="">
                    <tr>
                        <td align="right" width="350">
                            	云类型名称：
                            <input type="text" style="width: 220px; " id="meetingType.name" name="meetingType.name" maxlength="25" value="${meetingType.name}"/>

                        </td>
                        <td width="80" align="center">                            
							<a id="queryForList" class="btn_common btn_true" onclick="query();" href="##">查询</a>
                        </td>
                    </tr>
                </table>                
                 </div>
                
            </form>

            <table class="page_datalist" >
                <thead>
                <tr>
	            	<th width="2px" style="border-right:0"></th>
	                <th width="20%">云类型名称</th>
	                <th width="20%">使用主题</th>
	                <th width="20%" >创建时间</th>
                    <th width="15%" >Logo设定</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="mType" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td ></td>
                                <td>${mType.name }</td>
                                <td>${mType.pageTheme.title }</td>
                                <td><fmt:formatDate value="${mType.createTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td>${not empty mType.logoImage ? "已设定Logo" : "未设定Logo" }</td>
                                <td>
                                	<a href="${ctx}/admin/pri/custom/meetingType_addReq.action?meetingTypeId=${mType.id}">编辑</a>　
                                	<a href="#" onclick="del(${mType.id })">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="7" align="center">
                                没有会议信息.
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


<script type="text/javascript">

    function query(){
		$('#listMeetingForm').submit();
	}
    
	function forwardReq(){
		window.location.href = "${ctx}/admin/pri/custom/meetingType_addReq.action";
	}
	
	function del(id){
		if(confirm("确定要删除选择的信息吗?")){
			doDelete(id);
		}
	}
	
	function doDelete(id){
		var url = '${ctx}/admin/pri/custom/meetingType_delete.action?meetingTypeId='+id;
		window.location.href = url;
		//$.getJSON(url, callback);
	}
	
	
</script>
</body>
</html>