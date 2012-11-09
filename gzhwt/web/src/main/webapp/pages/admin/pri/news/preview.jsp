<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>新闻-${news.title }</title>
	<style type="text/css">
	.news {
		text-align:center;
	}
	.news .news_title {
		text-align:center;
	}
	.news .news_title h1 {
		color:#000;
		font-size:24px;
		font-weight:bold;
	}
	.news .news_subject {
		text-align:center;
	}
	.news .news_subject span {
		text-align:center;
		color:#000;
	}
	.news .news_content {
		text-align:center;
		color:#000;
		font-size:14px;
	}
	</style>
</head>
<body style="margin:0;padding:0;">
		<div class="news">
			<div class="news_title">
				<h1>${news.title }</h1>
			</div>
			<div class="news_subject">
				<span>来源：${news.source }&nbsp;&nbsp;&nbsp;&nbsp;
				发布时间：<fmt:formatDate value="${news.createTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></span>
			</div>
			<div class="news_content">${wd:base64Decode(news.content) }</div>
		</div>
</body>
</html>