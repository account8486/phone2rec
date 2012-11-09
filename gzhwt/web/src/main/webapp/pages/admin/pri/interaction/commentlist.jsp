<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>会务通平台</title>
<link href="${ctx}/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/zoom.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.postbox {font-family: "SimSun","Arial Narrow";}
.posttitle { background-color: #DBEAF8;  font-weight: bold; font-size: 14px; text-align: left; margin-bottom: 5px;margin-right: 0px;padding: 5px 10px;}
.posttitle h5 {font-size: 14px; width: 100%; font-weight: bold;}
.postheader {margin: 5px 10px;	text-align: left;	vertical-align: middle;}
.postheader textarea {margin-left: 10px;width: 88%;height: 100px;resize: none;border: 1px solid #BDDAF2;}
.postheader textarea:focus {border: 1px solid #0099FF;box-shadow: 0 0 3px #0099FF;}
.postheader span.tools {margin-left: 10px;float:left;}
.postheader a.send {margin-top:-65px; margin-right:10px;float: right;}
.postheader a.emotion {margin-left: 10px;}
.postheader div.emotionpanel {background-color:#fefefe;border-width:1px 3px 3px 1px;border-color: #BDDAF2;border-style:solid;-webkit-border-radius: 6px;-moz-border-radius:6px;padding:6px;margin:0px 20px;display:none;position: absolute;width: 363px;}
.postheader div.emotionpanel li{float:left; width:28px; height:28px; padding:0 5px 5px 0;}
.postheader div.emotionpanel li a {display:block; text-decoration:none; border:1px dashed #DDD; width:26px; height:26px; line-height:26px; overflow:hidden; text-align:center;}
.postheader div.emotionpanel li a:hover {border-color:#999;}
.postheader div.emotionpanel li a span {display:inline-block;}
.postheader div.emotionpanel li a img {vertical-align:middle;}
.postheader div.emotionpanel ul {zoom:1;}
.postheader div.emotionpanel ul:after{ content:"\0020"; display:block; height:0; line-height:0; clear:both; visibility:hidden;}
.postheader div.emotionpanel li a.close{background-image: url("${ctx}/images/close.gif");background-position: 0 -11px;height: 11px;width: 11px;margin-left: 10px;margin-top: 10px;border: none;}
.postheader div.emotionpanel li a.close:hover {background-position: 0 0;}
.postheader p.limit {display: inline-block;text-align:right;color:#999;font-size:12px;width:89%;}
.postlist {	margin: 5px 10px;}
.post { border: 1px solid #BDDAF2; margin: 2px 10px;}
.title { background-color: #DBEAF8; color: #666666; height: 31px; line-height: 31px; padding-left: 10px; font-size: 12px;}
.title_left {width:50%;	text-align: left;	display: inline-block;}
.title_right {	text-align: right;    display: inline-block;    width: 48%;}
.title_back {display: inline-block;float: right; margin-top: -23px;}
.postContent {	padding: 5px;	font-size: 12px;}
.title a {color: blue; font-size: 12px; line-height: 1.5;}
</style>
<script src="${ctx}/js/jquery-zoom/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery-zoom/artZoom.min.js" type="text/javascript"></script>
${jquery_form_js} ${util_js}
</head>
<body>
	<div class="postbox">
		<div class=page_title><h3>会议互动管理  -- ${CURRENT_MEETING}</h3></div>
		<div class="postlist">
			<div class="posttitle">
				<h5>互动交流</h5>
				<span class="title_back">
		        	<a href="${ctx}/admin/pri/interaction/postList.action?meetingId=${meetingId}" class="btn_common btn_true">返回</a>
		        </span>
			</div>
			
			<div class="post">
				<div class="title">
					<span class="title_left"> <b>${post.posterName }</b><c:if test="${not empty post.posterJob}">${post.posterJob}</c:if> 发表于  <fmt:formatDate value="${post.createTime }" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
					<span class="title_right">浏览(${post.viewCount }) | 评论(${post.commentCount })</span>
				</div>
				<div class="postContent">
					<span class="wordbreak">${post.content}</span> 
					<c:if test="${not empty post.contentImg}">
						<div><a class="miniImg artZoom" href="${post.contentImg}"><img src="${post.contentImg}"/></a></div>
					</c:if>
				</div>
			</div>			
		</div>
		
		<div class="postlist">
			<div class="posttitle"><h5>评论内容</h5></div>
     		<c:choose>
			<c:when test="${not empty pager.pageRecords}">
			<c:forEach var="meetingcomment" items="${pager.pageRecords}" varStatus="status">
				<div class="post" id="${meetingcomment.id }">
					<div class="title">
						<span class="title_left"> <b>${meetingcomment.creatorName }</b><c:if test="${not empty meetingcomment.creatorJob}">${meetingcomment.creatorJob}</c:if> 发表于  <fmt:formatDate value="${meetingcomment.time }" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
						<span class="title_right"> <a href="javascript:void(0);" class="commentDelete" id="${meetingcomment.id }">删除</a></span>
					</div>
					<div class="postContent">
						<span class="wordbreak">${meetingcomment.content}</span> 
					</div>
				</div>
      		</c:forEach>
			</c:when>
			<c:otherwise>
				<div class="postContent">该发言没有任何评论信息.</div>
			</c:otherwise>
			</c:choose>
		</div>
					
		<div class="postPager">
			<form id="listForm" action="${ctx}/admin/pri/interaction/commentList.action">
                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <input type="hidden" name="postId" id="postId" value="${postId}"/>
                <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
                <input type="hidden" id="queryForList" />
	        </form>
			<%@ include file="/pages/common/page.jsp" %>
	  	</div>
  	
		<div class="postheader">
			<div class="posttitle">我要评论</div>
			<p class="limit">
				<span class="tools"><a href="javascript:void(0);" class="emotion">添加表情</a></span>
				<span>目前可以输入<span id="text_limit">140</span>个字符</span>
			</p>
			<div class="emotionpanel"></div>
			<fieldset><textarea id="content" class="page_form large" rows="4" cols="30" name="content"></textarea></fieldset>
			<a href="javascript:void(0);" class="btn_common btn_true send"  id="commentAdd">发表评论</a>
		</div>
	</div>

	<script type="text/javascript">
	$(document).ready(function() {
		$.fn.extend({
			insertAtCaret: function(myValue){
				var $t=$(this)[0];
				if (document.selection) {
					this.focus();
					sel = document.selection.createRange();
					sel.text = myValue;
					this.focus();
				} else if ($t.selectionStart || $t.selectionStart == '0') {
					var startPos = $t.selectionStart;
					var endPos = $t.selectionEnd;
					var scrollTop = $t.scrollTop;
					$t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
					this.focus();
					$t.selectionStart = startPos + myValue.length;
					$t.selectionEnd = startPos + myValue.length;
					$t.scrollTop = scrollTop;
				} else {
					this.value += myValue;
					this.focus();
				}
			}
		});		
		
		var initFace = function() {
			<c:out escapeXml="false" value='var faces=${emotions};'/>
			var tempArr = [];
			tempArr.push('<ul>');
			for(var i=0, len = faces.length; i<len; i++) {
			tempArr.push([
			    '<li><a href="javascript:void(0);" hideFocus="true" value="' + faces[i].value + '"><img src="${ctx}/' + faces[i].src + '"/><span>&nbsp;</span></a></li>',
			   ].join(""));
			}
			tempArr.push(['<li><a href="javascript:void(0);" class="close"></a></li>'].join(""));
			tempArr.push('</ul>');
			$("div.emotionpanel").html(tempArr.join(""));
		};
		
		//初始化表情
		initFace();
		
		$("#queryForList").click (function() {$('#listForm').submit();});
			
		$("a.emotion").click(function(){
			$("div.emotionpanel").fadeIn("slow");
		});
			
		$("div.emotionpanel").delegate("a", "click", function(event) {
			if ($(this).attr("class") != "close") {
				var value = $(this).attr("value");
				try {
					$("#content").insertAtCaret(value);
				} catch(err) {}
			}
			$("div.emotionpanel").fadeOut("slow");
			return false;
		});
			
		$("#content").bind("keyup", function(event){
			var maxLength = 140;
			if(event.which == 8){// backspace, skip content length check.
				$("#text_limit").html(maxLength-$(this).val().length);
				return true;
			}
			
			if($(this).val().length > maxLength){
				$(this).val($(this).val().substring(0,maxLength));
				$("#text_limit").html(0);
				// scroll to bottom
				$(this).scrollTop(99999) 
				$(this).scrollTop($(this).scrollTop()*12)					
				return false;
			}else{
				$("#text_limit").html(maxLength - $(this).val().length);
			}
		});
			
		$("#commentAdd").click(
			function() {
				if ($("#content").val() == "") {
					alert("请输入您的评论。");
					return false;
				}
				
				var id = $(this).attr('id');
				$.post(
						"<c:out value='${ctx}'/>/admin/pri/interaction/commentAdd.action",
						{
							"postNo" : $("#postId").val(),
							"content": $("#content").val(),
							"type": "admin"
						},
						function(data, textStatus) {
							if (textStatus == "success") {
								if (data.retcode == 0) {
									window.location.href = window.location.href; 
								} else {
									// error
								}
							} else {
								// error
							}
						}, 
						"json"
				);
				return false;
			}
		);
			
		$("a.commentDelete").click (
			function() {
				if(confirm("确定要删除该评论吗?")){
				var id = $(this).attr('id');
				$.post(
						"<c:out value='${ctx}'/>/admin/pri/interaction/commentDelete.action",
						{
							"id" :　id,
							"postId" : $("#postId").val()
						},
						function(data, textStatus) {
							if (textStatus == "success") {
								if (data.result) {
									$("div.post[id='"+id+"']").remove();
								} else {
									// error
								}
							} else {
								// error
							}
						}, 
						"json"
				);
				return false;
				}
			}
		);
	}
);
</script>
</body>
</html>