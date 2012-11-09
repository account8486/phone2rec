<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

	<div class="w960" style="min-height:300px;">
	<div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
		<div>
			<table  class="content_table" >
                <thead>
                   <tr >	                   
                    <th style="width:40%">投票标题</th>
                    <th style="width:30%;">参与投票人数</th>
                    <th style="width:30%;">进入投票</th>
                </tr>
                </thead> 
                <tbody> 
                <c:choose>
	            	<c:when test="${not empty list}">
                        <c:forEach var="vote" items="${list}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td>${vote.title }</td>
                                <td >
                                    ${vote.count }
                                </td>
                                <td>
                                <a class="btn_blue enterVote" href="#" voteId="${vote.id }">参与投票</a>
                               
                                <c:if test="${vote.count>0 }">
                                <a class="btn_blue lookVote" href="#"  voteId="${vote.id }">查看结果</a>
                                </c:if> 
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="3" align="center">
                                没有投票信息.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
				</tbody>
			</table>
			
		</div>

	</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
	
$(function(){
	/*进入投票*/
	$(".enterVote").click(function(e){
		var id=$(e.target).attr("voteId");
		window.location.href="${ctx}/portal/pri/meeting/voteItem_findItemByVoteId.action?flag=web&voteId="+id+"&menu_id=${param.menu_id}";
	});
	
	/*查看投票结果*/
	$(".lookVote").click(function(e){
		var id=$(e.target).attr("voteId");
		window.location.href="${ctx}/portal/pri/meeting/vote_lookVoteResult.action?flag=web&voteId="+id+"&menu_id=${param.menu_id}";
	});
	
	var errMsg="${errMsg}";
	if(errMsg!=null&&errMsg!=""){
		alert(errMsg);
	}
});
    
</script>