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
            <div>
			<table  class="content_table" >
                <thead>
                   <tr >	                   
                    <th style="width:40%">调查标题</th>
                    <th style="width:30%;">参与人数</th>
                    <th style="width:30%;">参与调查</th>
                </tr>
                </thead> 
                <tbody> 
                <c:choose>
	            	<c:when test="${not empty list}">
                        <c:forEach var="paper" items="${list}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td>${paper.title }</td>
                                <td >${paper.count }</td>
                                <td>
                                <a class="btn_blue enterExam" href="#" paperId="${paper.id }">参与调查</a>
                                <a class="btn_blue myPaper" href="#" paperId="${paper.id }" style="margin-top: 5px;">我的答卷</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                </c:choose>
				</tbody>
			</table>
			
		</div>
      </div>
           	

<script type="text/javascript">
	
$(function(){
	/*参与调查*/
	$(".enterExam").click(function(e){
		var id=$(e.target).attr("paperId");
		window.location.href="${ctx}/client/pri/paper/question_enterExam.action?flag=client&paperId="+id+"&menu_id=${param.menu_id}";
	});
	
	/*查看我的答卷*/
	$(".myPaper").click(function(e){
		var id=$(e.target).attr("paperId");
		window.location.href="${ctx}/client/pri/paper/question_findMyAnswerPaper.action?flag=web&paperId="+id+"&menu_id=${param.menu_id}";
	});
	
	var errMsg="${errMsg}";
	if(errMsg!=null&&errMsg!=""){
		alert(errMsg);
	}
});
    
</script>
</body>
</html>