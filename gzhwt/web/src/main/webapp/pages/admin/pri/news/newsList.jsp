<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache" />
    <title>新闻管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}                   
	${admin_js}    
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
		});

    </script>
</head>
<body>
<div>

		<div class="page_title"><h3>新闻管理  -- ${CURRENT_MEETING}</h3></div>
		
		<div class="easyui-tabs" border="false" style="padding:10px;">	
			<div title="新闻管理" selected="true" style="padding:10px;"></div>
			<div title="土特产管理" link="${ctx}/admin/pri/reception/specialty_show.action?meetingId=${meetingId}" style="padding:10px;"></div>
		</div>
		
        <div >
			
			<div class="page_tools page_toolbar">
                 <a class="btn_common btn_false" href="javascript:forwardReq()">发布新闻</a>
            </div>
              	
            <form id="listNewsForm" action="${ctx}/admin/pri/news/list.action">
            	<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <div class="page_tools page_serach">
                <table style="">
                    <tr>
                        <td width="600">
                            	标题： <input type="text" style="width: 180px; " id="news.title" name="news.title" maxlength="25" value="${news.title}"/>
								&nbsp;&nbsp;&nbsp;
								类型：<select name="news.imageNews" style="width:85px;">
									<option value="">请选择类型</option>
									<option value="0" ${news.imageNews==0 ? "selected" : "" }>普通新闻</option>
									<option value="1" ${news.imageNews==1 ? "selected" : "" }>图片新闻</option>
								</select>
								&nbsp;&nbsp;&nbsp;
								状态：<select name="news.state" style="width:85px;">
									<option value="">请选择状态</option>
									<option value="1" ${news.state==1 ? "selected" : "" }>有效</option>
									<option value="0" ${news.state==0 ? "selected" : "" }>无效</option>
								</select>
                        </td>
                        <td width="60" align="center">                            
							<a id="queryForList" class="btn_common btn_true" onclick="query();" href="##">查询</a>
                        </td>
                    </tr>
                </table>                
                 </div>
                
            </form>

            <table class="page_datalist" >
                <thead>
                <tr>
	            	<th width="2px" style="border-right:0"></th>
	                <th width="35%">标题</th>
	                <th width="8%">类型</th>
	                <th width="6%" >状态</th>
	                <th width="15%" >创建时间</th>
	                <th width="15%" >最后修改时间</th>
                    <th width="6%" >点击数</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="entity" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td ></td>
                                <td title="${entity.title }">${wd:limit(entity.title,24) }</td>
                                <td>${entity.imageNews == 1 ? "图片新闻" : "普通新闻" }</td>
                                <td>${entity.state == 1 ? "有效" : "无效" }</td>
                                <td><fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${entity.lastModifyTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td>${entity.hitCount}</td>
                                <td>
                                	<a href="${ctx}/admin/pri/news/editReq.action?meetingId=${meetingId}&id=${entity.id}">编辑</a>　
                                	<a href="#" onclick="del(${entity.id })">删除</a>
                                	<a href="#" onclick="preview(${entity.id})">预览</a>　
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="7" align="center">
                                没有新闻信息.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <div>
           <%@ include file="/pages/common/page.jsp" %>
            </div>
        </div>
</div>


<script type="text/javascript">

    function query(){
		$('#listNewsForm').submit();
	}
    
	function forwardReq(){
		window.location.href = "${ctx}/admin/pri/news/editReq.action?meetingId=${meetingId}";
	}
	
	function del(id){
		if(confirm("确定要删除选择的信息吗?")){
			var url = '${ctx}/admin/pri/news/delete.action?meetingId=${meetingId}&id='+id;
			window.location.href = url;
		}
	}
	
	function preview(id){
		//window.location.href = url;
		url = "${ctx}/admin/pri/news/preview.action?meetingId=${meetingId}&id="+id;
		window.open(url, "_blank", "location=no,menubar=no,scrollbars=yes,resizable=yes,status=no,height=600,width=800,top=50,left=50");
	}
</script>
</body>
</html>