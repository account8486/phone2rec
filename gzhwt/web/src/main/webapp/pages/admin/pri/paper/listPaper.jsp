<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>会务通平台</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}
    <link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>会场建议 -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="easyui-tabs" border="false" style="padding:10px;">
	<div title="互动交流" link="${ctx}/admin/pri/interaction/postList.action?meetingId=${meetingId}" style="padding:10px;"></div>
	<div title="投票管理" style="padding:10px;" link="${ctx}/admin/pri/vote/vote_findVoteAll.action?meetingId=${meetingId}"></div>
	<div title="会场建议" style="padding:10px;" selected="true">
	<div class="page_tools page_toolbar">
		<a href="${ctx}/pages/admin/pri/paper/addPaper.jsp?meetingId=${meetingId}" class="btn_common btn_false" style="margin-left:5px;">添加调查</a>
		<font color="red">&nbsp;&nbsp;&nbsp;${errMsg }</font>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	            	<th width="2px" style="border-right:0"></th>
	                <th width="80">会议ID</th>
	                <th width="140" >调查标题</th>
                    <th width="80" >创建人</th>
                    <th width="80" >状态</th>
                    <th width="80" >操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach var="paper" items="${list}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            	<td></td>
                                <td>${paper.meeting.id }</td>
                                <td>
                                	<a href="${ctx}/admin/pri/paper/question_findQuestionsByPaperId.action?paperId=${paper.id}&meetingId=${meetingId}" >${paper.title }</a>
                                </td>
                                <td>${paper.creator.name}</td>
                                <td>${paper.state==1?"启用":"未启用"}</td>
                             	<td>
                             	<a href="#" class="deletePaper" paperId="${paper.id }" >删除</a>
                             	<a href="#" class="updatePaper" paperId="${paper.id }" >修改</a>
                             	<a href="#" class="preview" paperId="${paper.id }" >预览</a>
                             	<a href="#" class="findResult" paperId="${paper.id }" count="${paper.count}" } >查看结果</a>
								</td>
								
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="6" align="center"> 没有调查信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>
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
		
		/*删除调查*/
		$(".deletePaper").click(function(e){
			var id=$(e.target).attr("paperId");
			var param={"id":id};
			if(confirm("确认要删除吗?")){
			$.post("${ctx}/admin/pri/paper/paper_deletePaper.action",param,function(data){
				$(e.target).parent().parent().remove();
			});
			}
		});
		/*预览*/
		$(".preview").click(function(e){
			var id=$(e.target).attr("paperId");
			window.location.href="${ctx}/admin/pri/paper/question_enterExam.action?meetingId=${meetingId}&paperId="+id;
		});
		
		/*修改调查*/
		$(".updatePaper").click(function(e){
			var id=$(e.target).attr("paperId");
			window.location.href="${ctx}/admin/pri/paper/paper_findPaperById.action?meetingId=${meetingId}&id="+id;
		});
		
		/*查看结果*/
		$(".findResult").click(function(e){
			var id=$(e.target).attr("paperId");
			var count=$(e.target).attr("count");
			if(count<=0){
				alert("暂时没有结果");
				return;
			}
			window.location.href="${ctx}/admin/pri/paper/question_findQuestionsByPaperId.action?flag=result&meetingId=${meetingId}&paperId="+id;
		});
	});
</script>
</body>
</html>