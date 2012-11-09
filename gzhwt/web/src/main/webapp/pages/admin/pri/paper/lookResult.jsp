<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
<style type="text/css">
	input{
		width:250px;
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
<link href="${ctx}/css/votecss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/vote.js"></script>      
</head>
<body>
		
<div class="mainbox">
    <div class="page_title">
    	<h3>调查标题：${linkPaper.title }&nbsp;|&nbsp;参与人数：${linkPaper.count }</h3>
	</div>
	<div class="page_tools page_toolbar">
		<a href="${ctx}/admin/pri/paper/paper_findPaperAll.action?meetingId=${meetingId}"  class="btn_common btn_false" style="margin-left:5px;">调查列表</a>
		<span id="tip" style="display:none">点击列表可以查看结果</span>
	</div>
	<c:choose>
		<c:when test="${not empty list}">
			<div>
				<span id="result" style="width:45%; overflow:hidden; margin:0px; float:right; ">
					
				</span>
				
				<div style="border:1px solid skyblue;width:45%;  overflow:hidden;  margin:0px; float:left;font-weight: bolder;">
					 <c:forEach var="item" items="${list}" varStatus="status">
				      <div style="margin: 15px;cursor: pointer;" class="result" id="${item.id }">
				           <span>${status.count}、</span>
				           <span class="show">${item.question }</span>
				           <span style="color:skyblue">[
				           <c:if test="${item.type==1}">
                                	 单选题
                           </c:if>
                           <c:if test="${item.type==2}">
                             	    多选题
                           </c:if>
                           <c:if test="${item.type==3}">
                              	   简述题
                            </c:if>]
				           </span>
				      </div>
				   </c:forEach>
				</div>
				<div class="clearfix"></div>
			</div>
		</c:when>
		<c:otherwise>
            <div style="font-weight: bolder;color: skyblue;padding-left: 20px;">暂无调查结果</div>
        </c:otherwise>
	</c:choose>
	
</div>

<script type="text/javascript">
	$(function(){
		$("div.result").click(function(){
			$("span.show").css("background-color","#fff");
			$(this).children(".show").css("background-color","yellow");
			var paperId="${linkPaper.id}";
			var id=$(this).attr("id");
			var param={"paperId":paperId,"id":id};
			$.post("${ctx}/admin/pri/paper/question_lookResult.action",param,function(data){
				$("#result").empty();
				$("#result").html(data);
			})
		});
		$("div.result").first().click();
		$("#tip").show(3000);

		setInterval("reloadResult()",100000);
	})
	
	//进行页面的reload操作
	function reloadResult(){
		window.location.reload();
	}
	
	

</script>
</body>
</html>