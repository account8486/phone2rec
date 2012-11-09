<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">投票选项</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
     <div class="tab_c" style="display:block;">
    	<form action="${ctx}/touch/pri/vote/vote_processVote.action?menu_id=${param.menu_id}" id="processVote" method="post">
	    	<ul class="radio_list">
	    		<h3>${voteBase.title}</h3>
				<c:forEach items="${list }" var="item" >
					<li>
		            	<label><input  type="${voteBase.type==1?"radio":"checkbox" }" value="${item.id }" name="checks">${item.content }</label>
		            </li>
				</c:forEach>
				<input type="hidden" value="${voteBase.id}" name="voteId"/>
				<input type="hidden" value="${voteBase.meeting.id}" name="meetingId"/>
	        </ul>
	        <p style="padding:0px 10px;">
	            	<a href="javascript://" class="login_btn">投上一票</a>
	        </p>
        </form>
    </div>
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	ul.radio_list { padding:10px; padding-bottom:0px;}
	ul.radio_list li { margin:10px; font-size:16px; font-weight:bold;}
	ul.radio_list li label input { margin-right:10px;}
	a.login_btn { height:40px; display:block; margin:10px 0px; margin-top:0px; line-height:40px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:10px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;}
</style>

<script>
	/*这里是页面私有脚本*/
	$(function(){
		//$('ul.msg_list li:even').addClass('even');
		Li_even('ul.msg_list','odd');
	});
	
	$(function(){
		$("input:first").attr("checked",true);
		/*投票*/
		$(".login_btn").click(function(e){
			var size=$("input:checked").size();
			if(size<=0){
				alert("请选择投票项");
				return ;
			}
			$("#processVote").submit();
		});
	});
</script>