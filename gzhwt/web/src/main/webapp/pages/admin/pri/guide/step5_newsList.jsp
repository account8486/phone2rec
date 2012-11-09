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
</head>
<body>
<div>
        <div>
            <table class="page_datalist" >
                <thead>
                <tr>
	                <th width="35%">标题</th>
	                <th width="8%">类型</th>
	                <th width="6%" >状态</th>
	                <th width="15%" >创建时间</th>
	                <th width="15%" >最后修改时间</th>
                  
                
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="entity" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td title="${entity.title }">${wd:limit(entity.title,24) }</td>
                                <td>${entity.imageNews == 1 ? "图片新闻" : "普通新闻" }</td>
                                <td>${entity.state == 1 ? "有效" : "无效" }</td>
                                <td><fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${entity.lastModifyTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
                           
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

        </div>
</div>
</body>
</html>