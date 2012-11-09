<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>

<div class="page_tools page_serach">
	<form id="listOrgForm" action="${ctx}/admin/pri/org/list.action" method="post">
		<input type="hidden" id="pOrgId" name="pOrgId" value="${pOrgId}"/>
		<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		<table width="100%">
	      <tr>
	        <th style="width: 120px; ">组织机构名称： </th>
	        <td style="width: 180px; ">
	        	<input type="text" style="width:160px; " id="name" name="org.name" value="${org.name}"/>
	        	<input type="text" style="display:none;"/>
	        </td>
	        <td><a href="#" id="queryForList" class="btn_common btn_true">搜 索</a></td>
	        <td><font color="red">&nbsp;${errMsg }</font></td>
	      </tr>
	    </table>
	</form>
</div>

<div class="page_tools page_toolbar">
	<a href="#" id="goAdd" class="btn_common btn_false" style="margin-left:4px;">添加组织</a>
</div>

<div>
	<table class="page_datalist">
    	<thead>
        	<tr>
            	<th width="2px" style="border-right:0"></th>
                   <th width="190">组织名称</th>
                   <th width="190">上级组织</th>
                   <th width="50">级别</th>
                   <th width="50">状态</th>
                   <th width="140">修改时间</th>
                   <th width="80" colspan="2">操作</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <c:choose>
                   <c:when test="${not empty pager.pageRecords}">
                       <c:forEach var="orga" items="${pager.pageRecords}" varStatus="status">
                           <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                               <td></td>
                               <td align="left">${orga.name }</td>
                               <td align="left">${orga.parent.name }</td>
                               <td>
                               <c:choose>
                               	<c:when test="${orga.level eq 1}">一级</c:when>
                               	<c:when test="${orga.level eq 2}">二级</c:when>
                               	<c:when test="${orga.level eq 3}">三级</c:when>
                               	<c:when test="${orga.level eq 4}">四级</c:when>
                                   <c:otherwise> </c:otherwise>
                               </c:choose>
                               </td>
                               <td>
                               <c:choose>
                               	<c:when test="${orga.state eq 1}">有效</c:when>
                                   <c:otherwise>无效 </c:otherwise>
                               </c:choose>
                               </td>
                               <td><fmt:formatDate value="${orga.modifyTime}"
                                                                type="both"
                                                                pattern="MM月d日 HH:mm"/></td>
                               <td width="40px"><a href="${ctx}/admin/pri/org/goUpdate.action?id=${orga.id}&pOrgId=${pOrgId}">编辑</a></td>
                               <td width="40px"><a href="#" onclick="delOrg(${orga.id})">删除</a></td>
                           </tr>
                       </c:forEach>
                   </c:when>
                   <c:otherwise>
                       <tr class="datarow">
                           <td colspan="8" align="center">没有机构信息.</td>
                       </tr>
                   </c:otherwise>
               	</c:choose>
            </tr>
        </tbody>
    </table>
	<%@ include file="/pages/common/page.jsp" %>
</div>
<script type="text/javascript">
	function delOrg(orgId)
	{
		if(confirm("删除该组织机构将删除其下属组织机构和用户，您确认要删除吗？"))
		{
			window.location.href = "${ctx}/admin/pri/org/del.action?id="+ orgId + "&pOrgId=" +$("#pOrgId").val();;
		}
	}

	$("#document").ready(function(){
		$("#goAdd").click(function(){
			var url = "${ctx}/admin/pri/org/goAdd.action?pOrgId=" +$("#pOrgId").val();
			window.location.href = url;
		});
		
		$("#queryForList").click(function(){
			$("#list-div").load(
				"${ctx}/admin/pri/org/list.action",
				{
					"pOrgId":$("#pOrgId").val(),
					"org.name":$("#name").val(),
					"totalPage":$("#totalPage").val(),
					"currentPage":$("#currentPage").val()
				}
			);
		});
	});
	
	jQuery(document).keypress(function(e){
    	if(e.which == 13 ) {
    		var act = document.activeElement.id;
    		switch(act){
    			case 'name':$('#queryForList').click();break;
    			case 'jumpPage':jumpTo();break;
    		}
    	} 
    })
</script>
