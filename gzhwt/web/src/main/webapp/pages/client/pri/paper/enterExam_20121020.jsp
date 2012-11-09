<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <title>会务通平台</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <!--浏览器特性-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!--多终端icon-->
    <link href="favicon.ico" type="image/x-icon" rel="Bookmark"/>
    <link href="favicon.ico" type="image/x-icon" rel="shortcut icon"/>
    <link href="res/logo_apple.png" rel="apple-touch-icon"/>
    ${main_css}
    ${jquery_js }
    <link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
 	 
</head>
<body>
          <div class="cont" style="min-height: 200px;">
            <div style="margin: 5px;">
					<p align="center" style="font-size: 15px;font-weight:bolder;color:#CC9900">标题：${linkPaper.title }</p>
		<span id="tip"  style="display: none;color:red;text-align: center">您还有选项没有填写!</span>
		<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<div style="border:1px solid skyblue;"  align="center">
		<form id="questForm" action="${ctx}/client/pri/paper/question_processPager.action"  method="get">
			<input type="hidden" value="${linkPaper.id}" name="paperId"/>
		<div style="text-align: center;color:#CC9900;font-weight: bolder;margin: 10px;">
		匿名：<input type="radio" name="state" value="1" checked="checked"/>
		<span style="padding-left: 15px;">
		署名：<input type="radio" name="state" value="2"/></span>
		
		</div>
		<c:forEach var="quest" items="${pager.pageRecords}" varStatus="status">
		<div>
			<div style="margin: 10px;color:#CC9900;font-weight: bolder;">${status.count}:${quest.question }</div>
			<!-- 简答题 -->
			<c:if test="${quest.type==3}">
				<div class="quest " type="textarea" style="padding: 5px;">
					<span  style="margin-right: 10px;margin-left: 15px;">
					<textarea rows="2" style="width: 90%;height: 75px;"  name="contents" ></textarea>
					<input type="hidden" value="${quest.id }"  name="quests"/>
					</span>
			 	</div>
			</c:if>
			<!-- 单选题或者多选题 -->
			<c:if test="${quest.type!=3}">
			 <div class="quest">
				<c:forEach items="${quest.items }" var="item" varStatus="sta">
						<div style="margin: 5px;">
							<span style="margin-right: 10px;margin-left: 15px;">
							<input type="${quest.type==1?"radio":"checkbox" }" value="${item.id }" name="checks"/>
							<span>${item.content }</span>
							</span>
						</div>
				</c:forEach>
			 </div>
			 </c:if>
			 
		</div>
		<c:if test="${not status.last}">
		<hr color="silver"  size="1"></hr>
		</c:if>
		</c:forEach>
		<div class="page_form_sub" style="text-align:center;margin: 10px">
       		<a class="btn_blue" href="#" id="comQuest" >提交问卷</a>   
    	</div> 
    	</form>
		</div>
		</c:when>
		</c:choose>
		
		<div class="clear"></div>
			
		</div>
      </div>
           	

<script type="text/javascript">
	$(document).ready(function(){
		$("#comQuest").click(function(){
			var flag=true;
			$("div.quest").each(function(i){
				if($(this).attr("type")=='textarea'){
					var content=$(this).find("textarea").val()
					if(content.length==0){
						$(this).css({border:"1px solid red"});
						flag=false;
						return false;
					}else if(content.length>200){
						$(this).css({border:"1px solid red"});
						flag=false;
						alert("不能超过200字符");
						return false;
					}else{
						$(this).css({border:"none"})					
					}
				}
				else if($(this).find("input:checked").size()==0){
					$(this).css({border:"1px solid red"});
					flag=false;
					return false ;
				}
				else{
					$(this).css({border:"none"})					
				}
			});
			if(flag){
				$("#questForm").submit();
			}else{
				$("#tip").fadeIn(3000);
			}
		});
		
		
		var errMsg="${errMsg}";
		if(errMsg!=null&&errMsg!=""){
			alert(errMsg);
		}
	});
</script>
</body>
</html>