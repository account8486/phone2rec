<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>投票管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
    <link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>互动管理  -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="easyui-tabs" border="false" style="padding:10px;">
	<div title="互动交流" link="${ctx}/admin/pri/interaction/postList.action?meetingId=${meetingId}" style="padding:10px;"></div>
	<div title="投票管理" style="padding:10px;" selected="true">
	<div class="page_tools page_toolbar">
		<a href="${ctx}/pages/admin/pri/vote/addVote.jsp?meetingId=${meetingId}"  class="btn_common btn_false" style="margin-left:5px;">添加投票</a>
		<font color="red">&nbsp;&nbsp;&nbsp;${errMsg }</font>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	            	<th width="2px" style="border-right:0"></th>
	                <th width="80">会议ID</th>
	                <th width="140" >投票标题</th>
	                <th width="80" >参与人数</th>
                    <th width="80" >投票类型</th>
                    <th width="80" >状态</th>
                    <th width="80" >操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach var="vote" items="${list}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            	<td></td>
                                <td>${vote.meeting.id }</td>
                                <td>
                                	<a href="${ctx}/admin/pri/vote/voteItem_findItemByVoteId.action?voteId=${vote.id }" >${vote.title }</a>
                                </td>
                                <td>${vote.count }</td>
                                <td>${vote.type==1?"单选":"多选" }</td>
                                <td>${vote.state==1?"启用":"未启用"}</td>
                             	<td>
                             	<a href="#" class="deleteVote" voteId="${vote.id }" >删除</a>
                             	<a href="#" class="updateVote" voteId="${vote.id }" >修改</a>
								</td>
								
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="7" align="center"> 没有投票信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>
	</div>
	</div>
	<div title="会场建议" link="${ctx}/admin/pri/paper/paper_findPaperAll.action?meetingId=${meetingId}" style="padding:10px;"></div>
	
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
		
		
		/*删除投票*/
		$(".deleteVote").click(function(e){
			var id=$(e.target).attr("voteId");
			var param={"id":id};
			if(confirm("确认要删除吗?")){
				$.post("${ctx}/admin/pri/vote/vote_deleteVoteBase.action",param,function(data){
					$(e.target).parent().parent().remove();
				});
			}
		});
		
		/*修改投票*/
		$(".updateVote").click(function(e){
			var id=$(e.target).attr("voteId");
			window.location.href="${ctx}/admin/pri/vote/vote_findVoteById.action?meetingId=${meetingId}&voteId="+id;
		});
	});
</script>
</body>
</html>