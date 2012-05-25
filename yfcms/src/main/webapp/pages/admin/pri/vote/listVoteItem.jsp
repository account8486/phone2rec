<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>投票管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>投票管理  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="page_tools page_toolbar">
		<a href="${ctx}/pages/admin/pri/vote/addVoteItem.jsp?voteId=${voteBase.id}"  class="btn_common btn_false" style="margin-left:5px;">添加投票项</a>
		<a href="${ctx}/admin/pri/vote/vote_findVoteAll.action?meetingId=${voteBase.meeting.id}"  class="btn_common btn_false" style="margin-left:5px;">投票列表</a>
		<span class="page_title" style="text-align: center;">投票标题 :${voteBase.title}</span>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	            	<th width="2px" style="border-right:0"></th>
	                <th width="80">会议ID</th>
	                <th width="200" >选项内容</th>
	                <th width="80" >被投次数</th>
                    <th width="80" >操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach var="item" items="${list}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            	<td></td>
                                <td>${item.vote.meeting.id }</td>
                                <td>${item.content }</td>
                                <td>${item.count }</td>
                             	<td>
                             	<a href="#" class="deleteVote" itemId="${item.id }" >删除</a>
                             	<a href="#" class="updateVote" itemId="${item.id }" >修改</a>
								</td>
								
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="5" align="center"> 您还有没有添加投票项.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>
	</div>
<script type="text/javascript">
	$(function(){
		/*删除投票选项*/
		$(".deleteVote").click(function(e){
			var id=$(e.target).attr("itemId");
			var param={"id":id};
			if(confirm("确认要删除吗?")){
				$.post("${ctx}/admin/pri/vote/voteItem_deleteVoteItem.action",param,function(data){
					$(e.target).parent().parent().remove();
				});
			}
		});
		
		/*修改投票选项*/
		$(".updateVote").click(function(e){
			var id=$(e.target).attr("itemId");
			window.location.href="${ctx}/admin/pri/vote/voteItem_findVoteItemById.action?voteId=${voteBase.id}&id="+id;
		});
	});
</script>
</body>
</html>