<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

	<div class="w960" style="min-height: 300px">
	<div class="cbox"><div class="title"><h5 id="caption">嘉宾信息</h5></div></div>
		<div>
			<form id="mainForm" action="${ctx}/portal/pri/meeting/lucky_findLuckyHistory.action">
				<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <input type="hidden" name="meetingId"  value="${meetingId}"/>
                <input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
			<table class="content_table">
		      <tr>
		        <td >
	            	手机号码：
	            	<input type="text" style="width: 120px; " id="mobile" name="mobile"
	                                   value="${mobile}"/>
	            	<a href="#" id="queryForList"  class="btn_blue">搜 索</a> 
	            </td>
		       
		      </tr>
		    </table>
			</form>
		
		 <table class="content_table" >
                <thead>
                <tr >
                    <th width="15%">中奖人</th>
                    <th width="15%">手机号</th>
                    <th width="15%">奖项</th>
                    <th width="25%">奖品</th>
                    <th width="25%">中奖时间</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="lucky" items="${pager.pageRecords}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if> >
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
                            <td colspan="5" align="center">
                                没有抽奖信息.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
		
		<div class="clear"></div>
		<%@ include file="/pages/common/page.jsp" %>
		</div>

	</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
	
$(function(){
	$(document).ready(function(){
		// 查询
		$("#queryForList").click(function(){
			var mobile=$("input[name=mobile]").val();
    		var regex=/^\d{0,11}$/;
    		if(!regex.test(mobile)){
    			alert("手机号只能是小于11位的数字");
    			return ;
    		}
			$("#mainForm").submit();
		});
	});
});
    
</script>