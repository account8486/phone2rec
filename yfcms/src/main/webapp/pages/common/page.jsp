<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:if test="${pager.pageCount > 1}">
<div class="manu" style="text-align:center;align:center;">
	<table width="96%" align="center" border="0" id="table29">
	    <tr align="center">
	        <td id="pageDesc" align="center">
	           <div class=pagination>
	           		<font class="page_text">第${pager.currentPage}/${pager.pageCount}页&nbsp;&nbsp;共${pager.total}条&nbsp;</font>
	               <a href="#" onclick="pageSkip('1');">首页</a>
	               <c:if test="${pager.currentPage>1}">
	               <a href="#" onclick="pageSkip('${pager.currentPage - 1}');">上一页</a>
	               </c:if>
	               
	               <c:if test="${pager.currentPage<pager.pageCount}">
	               <a href="#" onclick="pageSkip('${pager.currentPage + 1}')">下一页</a>
	               </c:if>
	               <a href="#" onclick="pageSkip('${pager.pageCount}');">末页</a>
	               <font class="page_text">到第&nbsp;</font>
	               <input type="text" id="jumpPage" name="jumpPage" size="3" value="">
	               <font class="page_text">&nbsp;页&nbsp;</font>
	               <a href="#" onclick="jumpTo()">跳 转</a>
	           </div>
	       </td>
		</tr>
	</table>
</div>
</c:if>