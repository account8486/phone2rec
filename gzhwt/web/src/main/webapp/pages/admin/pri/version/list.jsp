<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>客户端版本管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}
    <script type="text/javascript">
	    function openUploadWin() {
	        var url = "${ctx}/pages/admin/pri/version/upload.jsp";
	        window.open(url, "_blank", "height=300,width=800,top=200,left=200");
	    }
	    
	    $(document).ready(function(){
			$("#queryForList").click(function(){
				$("#tip_version").hide();
				var version = $("#version").val();

		       	if (!isEmpty(version)) {
		       		var bool = /^[0-9]{1,6}$/.test(version);
			       	if (bool == false) {
			           $("#tip_version").html("请输入6位以内整数的版本号。");
			           $("#tip_version").show();
			           return;
			       	}
		       	}
			       
				$("#mainForm").submit();
			});
		});
	    
	    jQuery(document).keypress(function(e){
	    	if(e.which == 13 ) {
	    		var act = document.activeElement.id;
	    		switch(act){
	    			case 'version':$('#queryForList').click();break;
	    			case 'jumpPage':jumpTo();break;
	    		}
	    	} 
	    })
	</script>
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>客户端版本管理--<font>用户下载的是最新上传的版本，其它为历史版本记录。</font></h3>
	</div>
	
	<div class="page_tools page_serach">
	<form id="mainForm" action="${ctx}/admin/pri/version/list.action">
		<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		<table width="98%">
	      <tr>
	        <th>版本号：</th>
	        <td><input type="text" id="version" name="version.version"
                                   value="${version.version }"/>
            <font id="tip_version" style="display: none;" color="red"></font></td>
	        <td><a href="#" id="queryForList" class="btn_common btn_true">搜 索</a></td>
	      </tr>
	    </table>
	</form>
	</div>
	
	<div class="page_tools page_toolbar">
		<a href="#" id="goUpload" onclick="openUploadWin();" class="btn_common btn_false" style="margin-left:15px;">上传新版本</a>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	            	<th width="2px" style="border-right:0"></th>
	                <th width="80">安装文件名称</th>
	                <th width="80" >版本号</th>
	                <th width="190" >更新时间</th>
	                <th width="80" >操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                <c:choose>
					<c:when test="${not empty pager.pageRecords}">
					    <c:forEach var="ver" items="${pager.pageRecords}" varStatus="status">
					    <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            <td ></td>
                            <td>${ver.name }</td>
                            <td>${ver.version }</td>
                            <td ><fmt:formatDate value="${ver.modifyTime}"
                                                                 type="both"
                                                                 pattern="MM月d日 HH:mm"/></td>
							<td><a href="${serverUrl}${ver.url }">下载</a></td>
                        </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="4">&nbsp;&nbsp;&nbsp;当前没有客户端版本信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
	    <%@ include file="/pages/common/page.jsp" %>
	</div>
</body>
</html>