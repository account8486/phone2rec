<%@ page contentType="text/html" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="pageCount"
	value='<%=Integer.parseInt(request.getParameter("pageCount"))%>' />
<c:set var="pageNum"
	value='<%=Integer.parseInt(request.getParameter("pageNum"))%>' />
<c:set var="postToUrl" value='<%=request.getParameter("targetUrl")%>' />
	<%
	    String pageCountStr = request.getParameter("pageCount");
	    String pageNumStr = request.getParameter("pageNum");
	    
	    int pageCount = Integer.parseInt(pageCountStr);
	    int pageNum = Integer.parseInt(pageNumStr);
	    int liststep = 10;
	    int listbegin = (pageNum - (int) Math.ceil((double) liststep / 2));
	    if (listbegin < 1) {
	        listbegin = 1;
	    }
	    int listend = pageNum + liststep / 2;
	    if (listend > pageCount) {
	        listend = pageCount + 1;
	    }
	%>

	<%
	    if (pageNum == 1) {
	%>
	<input type="button" id="first"
		value="首页"
		disabled="disabled" />
	<input type="button" id="previous"
		value="上一页"
		disabled="disabled" />
	<%
	    } else {
	%>
	<input type="button" id="first"
		value="首页"
		onclick="goPage('1','${postToUrl }');" />
	<input type="button" id="previous"
		value="上一页"
		onclick="goPage('${pageNum-1}','${postToUrl }');" />
	<%
	    }
	    if (listbegin > 1) {
	%>
	...
	<%
	    }
	    for (int i = listbegin; i < listend; i++) {
	%>
	<%
	    if (i == pageNum) {
	%>
	<span class="currentPage"><%=i%></span>
	<%
	    continue;
	        }
	%>


	<a href="javascript:void(0);" onclick="goPage('<%=i%>','${postToUrl }');return false;"><%=i%></a>

	<%
	    }
	    if (listend <= pageCount) {
	%>
	...
	<%
	    }
	    if (pageCount == pageNum) {
	%>
	<input type="button" id="next"
		value="下一页"
		disabled="disabled" />
	<input type="button" id="last"
		value="末页"
		disabled="disabled" />
	<%
	    } else {
	%>
	<input type="button" id="next"
		value="下一页"
		onclick="goPage('${pageNum+1}','${postToUrl }');" />
	<input type="button" id="last"
		value="末页"
		onclick="goPage('${pageCount}','${postToUrl }');" />
	<%
	    }
	%>
<script type="text/javascript">
function goPage(pageNum, url) {
		if(url.indexOf('?') > -1){
			url += '&currentPage='+pageNum;
		}else{
			url += '?currentPage='+pageNum;
		}
		forwardReq(url);
}
</script>
