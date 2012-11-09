<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
<style type="text/css">
	p{
		margin:15px;
		color:blue;
	}
</style>
${admin_css}                                   
${jquery_js}                                
${jquery_form_js}                                 
${validate_js}                                
${WdatePicker_js}                           
${admin_js}                   
${area_js}        
${util_js}      
</head>
<body>
		
<div class="mainbox">
	<div class="page_form">
	<fieldset id="add">
       
       <p> <span>题目类型：
       <c:if test="${quest.type==1}">单选题</c:if>
       <c:if test="${quest.type==2}">多选题</c:if>
       <c:if test="${quest.type==3}">简述题</c:if>
       </span></p><br/>
       <p> <span>题目标题：${quest.question }</span></p><br/>
        <c:forEach items="${quest.items }" var="item" varStatus="sta">
         	<p><span>题目选项${sta.index+1 }：${item.content }</span></p><br/>
        </c:forEach>
    </fieldset>

    <div class="page_form_sub">
        <a href="javascript:history.back();"   class="btn_common btn_true">返回</a>    
    </div>

	
	</div>
</div>

<script type="text/javascript">

</script>
</body>
</html>