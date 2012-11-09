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
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>会场建议  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="page_tools page_toolbar">
		<a href="${ctx}/pages/admin/pri/paper/addQuestion.jsp?paperId=${linkPaper.id}&meetingId=${meetingId}"  class="btn_common btn_false" style="margin-left:5px;">添加题目</a>
		<a href="${ctx}/admin/pri/paper/paper_findPaperAll.action?meetingId=${meetingId}"  class="btn_common btn_false" style="margin-left:5px;">调查列表</a>
		<span class="page_title" style="text-align: center;">调查标题 :${linkPaper.title}</span>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	            	<th width="2px" style="border-right:0"></th>
	                <th width="80">会议ID</th>
	                <th width="200" >题目标题</th>
	                <th width="80" >题目类型</th>
	                <th width="80" >创建人</th>
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
                                <td>${linkPaper.meeting.id }</td>
                                <td>${item.question }</td>
                                <c:if test="${item.type==1}">
                                 <td>单选题</td>
                                </c:if>
                                 <c:if test="${item.type==2}">
                                 <td>多选题</td>
                                </c:if>
                                 <c:if test="${item.type==3}">
                                 <td>简述题</td>
                                </c:if>
                                <td>${item.creator.name }</td>
                             	<td>
                             	<a href="#" class="deleteQuestion" itemId="${item.id }" >删除</a>
                             	<a href="#" class="findQuestion"   itemId="${item.id }" >详细</a>
								</td>
								
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="5" align="center"> 您还有没有添加题目.</td>
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
		/*删除考题*/
		$(".deleteQuestion").click(function(e){
			var id=$(e.target).attr("itemId");
			var param={"id":id,"paperId":${linkPaper.id}};
			if(confirm("确认要删除吗?")){
				$.post("${ctx}/admin/pri/paper/question_deleteQuestion.action",param,function(data){
					$(e.target).parent().parent().remove();
				});
			}
		});
		
		
		/*查看考题详细信息*/
		$(".findQuestion").click(function(e){
			var id=$(e.target).attr("itemId");
			window.location.href="${ctx}/admin/pri/paper/question_findQuestion.action?id="+id;
		});
	});
</script>
</body>
</html>