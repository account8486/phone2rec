<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
<link href="${ctx}/css/votecss_touch.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/vote_touch.js"></script>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">投票结果</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
    	
        <div class="vote_res" id="lookVote">
        	
        </div>
        
        <p style="padding:0px 10px;">
            	<a href="${ctx}/touch/pri/vote/vote_findEnableVote.action?meetingId=${voteBase.meeting.id }" class="login_btn2">返回投票列表</a>
        </p>
    </div>
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	.vote_res { padding:10px;}
	a.login_btn { height:40px; display:block; margin:10px 0px; margin-top:0px; line-height:40px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:10px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;}
	a.login_btn2 { height:40px; display:block;  margin:10px 0px; margin-top:0px; line-height:40px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:10px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#333; background:url(${ctx}/images/touch/box_t_bg.png) top center repeat-x; border-left:1px solid #ccc;  border-right:1px solid #ccc;}
</style>

<script>
	/*这里是页面私有脚本*/
	$(function(){
		//$('ul.msg_list li:even').addClass('even');
		Li_even('ul.msg_list','odd');
	});
	
	$(function(){
		var size="${size}";
		var counts="${counts}";
		var contents="${contents}"
		counts=counts.slice(0,counts.length-1);
		contents=contents.slice(0,contents.length-1);
		counts=counts.split(",");
		contents=contents.split(",");
		var array=[]
		var colors=['#39c','#f00','#000','#E38','#b57','#888','#d95','#ad5']
		for(var i=0;i<size;i++){
			var data={"name":contents[i],"data":counts[i],"color":colors[i]};
			array.push(data);
		}
		lookVote(array,${voteBase.count});
		
	});
</script>
