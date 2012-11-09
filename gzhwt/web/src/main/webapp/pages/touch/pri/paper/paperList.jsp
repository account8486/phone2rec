<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">会场建议</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
    	<ul class="item_list">
    	  <c:choose>
    	  <c:when test="${not empty list}">
    		<c:forEach var="paper" items="${list}" varStatus="status">
    			<c:if test="${status.first }">
    				<li class="first">
    			</c:if>
    			<c:if test="${status.last }">
    				<li class="last">
    			</c:if>
    			<c:if test="${not status.first&&not status.last}">
    				<li>
    			</c:if>
	            	<div class="tools">
	            	<a href="javascript://" class="enterExam" paperId="${paper.id }">参与调查</a>
	            	<a href="javascript://" class="myPaper" paperId="${paper.id }">我的答卷</a>
	            	</div>
	            	<a href="javascript://">${paper.title }(${paper.count})</a>
	            </li>
            </c:forEach>
            </c:when>
            <c:otherwise>
                       您好，暂时没有调查信息
            </c:otherwise>
            </c:choose>
        </ul>
    </div>
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	ul.item_list { padding:10px;}
	ul.item_list li { border:1px solid #ccc; border-top:0px; position:relative;}
	ul.item_list li.first { border-top: 1px solid #ccc; border-top-left-radius: 3px; border-top-right-radius: 3px;}
	ul.item_list li.last { border-bottom-left-radius: 3px; border-bottom-right-radius: 3px;}
	ul.item_list li a { padding-left:10px; margin-right:10px; display:block; line-height:24px; font-size:16px; line-height:2.5; font-weight:bold; }
	ul.item_list li a:active {  background:#f2f2f2 url(${ctx}/images/touch/icon-list-link.png) center right no-repeat;}
	ul.item_list li .tools { position:absolute; right:5px; top:3px;}
	ul.item_list li .tools a { height:30px; float:left; margin:0px; padding:0px;  padding:0px 5px; -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078; text-align:center; line-height:30px; margin-left:10px;}
</style>

<script>
	/*这里是页面私有脚本*/
	$(function(){
		//$('ul.msg_list li:even').addClass('even');
		Li_even('ul.msg_list','odd');
	});
	
	
	
	$(function(){
		/*参与调查*/
		$(".enterExam").click(function(e){
			var id=$(e.target).attr("paperId");
			window.location.href="${ctx }/touch/pri/paper/question_enterExam.action?flag=touch&paperId="+id+"&meetingId=${param.meetingId}";
		});
		
		/*查看我的答卷*/
		$(".myPaper").click(function(e){
			var id=$(e.target).attr("paperId");
			window.location.href="${ctx }/touch/pri/paper/question_findMyAnswerPaper.action?flag=touch&paperId="+id+"&meetingId=${param.meetingId}";
		});
		
		var errMsg="${errMsg}";
		if(errMsg!=null&&errMsg!=""){
			alert(errMsg);
		}
	});
</script>