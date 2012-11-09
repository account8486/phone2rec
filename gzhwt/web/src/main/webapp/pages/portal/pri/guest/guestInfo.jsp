<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

	<div class="w960">
	<div class="cbox"><div class="title">
		<h5 id="caption">嘉宾信息</h5>
	</div></div>
		<div>
			<div class="cbox">
		<div class="second_title">
			<h5>姓名</h5>
		</div>
		<div class="cont"><p><span >${guest.name}</span></p></div>
	</div>
	<div class="cbox">
		<div class="second_title">
			<h5>头衔</h5>
		</div>
		<div class="cont"><p><span>${guest.rank }</span></p></div>
	</div>
	<div class="cbox">	
		<div class="second_title">
			<h5>简介</h5>
		</div>
		<div class="cont"><p><span>${guest.about}</span></p></div>
	</div>
	    
		
		
		</div>
		
		<div class="page_form_sub">
       		<a class="btn_blue" href="javascript:history.back();">返回列表</a>   
    	</div> 
	</div>


<%@ include file="/pages/portal/common/footer.html" %>

