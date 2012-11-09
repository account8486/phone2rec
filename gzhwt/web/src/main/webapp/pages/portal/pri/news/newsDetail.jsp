<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

<style type="text/css">
	.news {
		text-align:center;
	}
	.news .news_title {
		text-align:center;
	}
	.news .news_title h1 {
		color:#000;
		font-size:20px;
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
		text-align:left;
		color:#000;
		font-size:14px;
	}
</style>

	<div class="w960">
	<div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
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

	</div>

<%@ include file="/pages/portal/common/footer.html" %>

