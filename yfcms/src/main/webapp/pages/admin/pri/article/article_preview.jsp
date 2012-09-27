<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>文章-${article.title }</title>
	<style type="text/css">
	.article {
		text-align:left;
	}
	.article .article_title {
		text-align:center;
	}
	.article .article_title h1 {
		color:#000;
		font-size:24px;
		font-weight:bold;
	}
	.article .article_subject {
		text-align:center;
	}
	.article .article_subject span {
		text-align:center;
		color:#000;
	}
	
	.article .article_content {
		color:#000;
	}
	</style>
</head>
<body style="margin:0;padding:0;">
		<div class="article">
			<div class="article_title">
				<h1>${article.title }</h1>
			</div>
			<div class="article_subject">
				<span>来源：test&nbsp;&nbsp;&nbsp;&nbsp;
				发布时间：<fmt:formatDate value="${article.createTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></span>
			</div>
			
			<div class="article_content">${article.content}</div>
		</div>
</body>
</html>