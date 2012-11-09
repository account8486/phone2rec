<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
    <div id="div_home" class="tab_c" style="display:block;">
    </div>
    
    <div id="div_nav" class="tab_c">
    </div>

</div>

<footer>
	<p class="edition">
    <a href="javascript://" class="act">触屏版</a> |	<a href="${ctx}/wap/pri/meeting/getMeetingById.action?returnType=wap_index&meeting.id=${not empty param.meetingId ? param.meetingId : meeting.id}">标准版</a> 
    </p>
    <p class="copyright">
    	版权所有 © 中国移动通信集团贵州有限公司 2012
    </p>
</footer>

<style type="text/css">
	/*这里是页面私有样式*/
	.home_pic { width:300px; height:100px; margin:10px auto;}
	.home_pic img { width:300px; height:100px; }
	.mtbslide-pagination {
	height:28px;
	width:300px;
	margin-top:10px; auto; margin-bottom:10px;
}
.mtbslide-pagination .pg-btn {
	width:58px;
	height:26px;
	line-height:26px;
	overflow:hidden;
	border:1px solid #c4c4c4;
	text-indent:-9999px;
	background:url(${ctx}/images/touch/icon-list-link.png) no-repeat center center;
}
.mtbslide-pagination .pg-btn a {
	display:block;
	width:58px;
	height:26px;
	text-align:center;
}
.mtbslide-pagination .prev {
	float:left;
	-webkit-transform:rotate(180deg);
}
.mtbslide-pagination .next {
	float:right;
}
.mtbslide-pagination ul {
	margin-top:8px;
}
.mtbslide-pagination .trigs {
	float:left;
	width:180px;
	text-align:center;
}
.mtbslide-pagination .trigs li {
	display:inline-block;
	text-indent:-9999px;
	margin-right:10px;
	width:10px;
	height:10px;
	-webkit-border-radius:5px;
	background:#a9a9a9;
}
.mtbslide-pagination .trigs li.cur {
	background:#069;
}
</style>

<script>
	$("#li_home").click(function(){
		$("#div_home").load(
    		"${ctx}/touch/pri/meeting/getMeetingById.action",
    		{
    			"returnType": "touch_viewmeeting",
    			"meeting.id": "${not empty param.meetingId ? param.meetingId : meeting.id}"
    		}
	   	);
	});
	
	$("#li_nav").click(function(){
		$("#div_nav").load(
			"${ctx}/touch/pri/meeting/getMenuList.action",
    		{
    			"meeting.id": "${not empty param.meetingId ? param.meetingId : meeting.id}"
    		}
	   	);
	});
</script>

</body>
</html>