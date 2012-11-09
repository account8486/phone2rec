<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/pages/portal/common/header.jsp" %>
<script src="${ctx}/js/jquery-zoom/jquery.artZoom.js"></script>
<link href="${ctx}/js/jquery-zoom/jquery.artZoom.css" rel="stylesheet" type="text/css">
<style type="text/css">
.postheader {margin: 5px 10px;text-align: left;vertical-align: middle;}
.postheader textarea {width: 88%;resize: none;border: 1px solid #BDDAF2;}
.postheader textarea:focus {border: 1px solid #0099FF;box-shadow: 0 0 3px #0099FF;}
.postheader a.send {margin-top: -65px;float: right;}
.postheader a.emotion {font-size: 12px;}
.postheader div.emotionpanel {z-index:999;background-color:#fefefe;border-width:1px 3px 3px 1px;border-color: #BDDAF2;border-style:solid;-webkit-border-radius: 6px;-moz-border-radius:6px;padding:6px;margin:0px 20px;display:none;position: absolute;width: 363px;}
.postheader div.emotionpanel li{float:left; width:28px; height:28px; padding:0 5px 5px 0;}
.postheader div.emotionpanel li a {display:block; text-decoration:none; border:1px dashed #DDD; width:26px; height:26px; line-height:26px; overflow:hidden; text-align:center;}
.postheader div.emotionpanel li a:hover {border-color:#999;}
.postheader div.emotionpanel li a span {display:inline-block;}
.postheader div.emotionpanel li a img {vertical-align:middle;}
.postheader div.emotionpanel ul {zoom:1;}
.postheader div.emotionpanel ul:after{ content:"\0020"; display:block; height:0; line-height:0; clear:both; visibility:hidden;}
.postheader div.emotionpanel li a.close{background-image: url("${ctx}/images/close.gif");background-position: 0 -11px;height: 11px;width: 11px;margin-left: 10px;margin-top: 10px;border: none;}
.postheader div.emotionpanel li a.close:hover {background-position: 0 0;}
.postheader p.limit {display: inline-block;text-align:right;color:#999;font-size:12px;width:88%;}
.postheader span.tools {margin-left: 10px;float:left;}
.post {border: 1px solid #BDDAF2; margin: 0px 10px 5px 10px;}
.postTitle {background-color: #EBF2F7;color: #666666;height: 31px;line-height: 31px;padding-left: 10px;font-size: 12px;}
.title_left {width:50%;text-align: left;display: inline-block;}
.title_right {text-align: right;display: inline-block;width: 48%;}
.title_back {display: inline-block;float: right; margin-top: -30px; margin-right: 10px;}
.post .postContent {padding: 5px;font-size: 12px;}
.postTitle a {color: blue;font-size: 12px;line-height: 1.5;}
.postPager {text-align: center;}
span.card {color:#1DA1FF; cursor:pointer;}
</style>
<div class="w960">
        <div class="cbox"><div class="title"><h5 id="caption"></h5>
        <span class="title_back">
        <%--
        	<a href="${ctx}/portal/pri/interaction/postList.action?meetingId=${meetingId}&menu_id=${param.menu_id}" class="btn_blue">返回</a>
      	 --%>
        </span>
        </div></div>
        <ul class="message_list">
        <div class="post">
			<div class="postTitle">
				<span class="title_left"><b>${post.posterName }</b><c:if test="${not empty post.posterJob}">${post.posterJob}</c:if></span>
				<span class="title_right">浏览(${post.viewCount }) | 评论(${post.commentCount })
				<c:if test="${not empty post.user}">
				 | <a href="${ctx}/portal/pri/message/list.action?meetingId=${meetingId}&select=new&selectuser=${post.user.id}&menu_id=${param.menu_id}">发私信</a>
				 </c:if>
				  | <fmt:formatDate value="${post.createTime }" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/>&nbsp;发表</span>
			</div>
			<div class="postContent">
				<span class="wordbreak">${post.content}</span>
				<c:if test="${not empty post.contentImg}">
				<div>
                	<a href="${post.contentImg}" rel="${post.contentImg}"><img class="artZoom" src="${post.contentImg}" /></a>
                </div>
				</c:if> 
			</div>
		</div>
		</ul>

        <div class="cbox"><div class="title"><h5>评论内容</h5></div></div>
		<ul class="message_list">
      	<c:choose>
			<c:when test="${not empty pager.pageRecords}">
			<c:forEach var="meetingcomment" items="${pager.pageRecords}" varStatus="status">
				<div class="post">
					<div class="postTitle">
						<span class="title_left">
							<c:if test="${not empty meetingcomment.user}"><span class="card" id="${meetingcomment.id }"></c:if>
								<b>${meetingcomment.creatorName }</b><c:if test="${not empty meetingcomment.creatorJob}">(${meetingcomment.creatorJob})</c:if>
							<c:if test="${not empty meetingcomment.user}"></span></c:if>
						</span>
						<span class="title_right"><fmt:formatDate value="${meetingcomment.time }" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/>&nbsp;评论</span>
					</div>
                   <c:if test="${not empty meetingcomment.user}">
					<div class="card" id="card${meetingcomment.id}">
						<h1>${meetingcomment.creatorName }的个人信息<a parentid="card${meetingcomment.id}" class="close" href="javascript:void(0);"></a></h1>
						<table>
						<tr>
							<td class="label">职务：</td>
							<td class="value"><c:if test="${not empty meetingcomment.creatorJob}">${meetingcomment.creatorJob}</c:if></td>
							<td class="label">等级：</td>
							<td class="value">${meetingcomment.user.meetingMember.memberLevel}</td>
						</tr>
						<tr>
							<td class="label">邮箱：</td>
							<td class="value">${meetingcomment.user.meetingMember.mailbox}</td>
							<td class="label">房号：</td>
							<td class="value">
								<c:if test="${meetingcomment.user.meetingMember.roomNumberIsDisplay eq 1}">${meetingcomment.user.meetingMember.roomNumber}</c:if>
							</td>
						</tr>
						<tr>
							<td class="label">城市：</td>
							<td class="value">${meetingcomment.user.meetingMember.city}</td>
							<td class="label">手机：</td>
							<td class="value">
								<c:if test="${meetingcomment.user.meetingMember.mobileIsDisplay eq 1}">${meetingcomment.user.mobile}</c:if>
							</td>
						</tr>
						<tr>
							<td class="value" colspan="3"><b>地址：</b>${meetingcomment.user.meetingMember.address}</td>
							<td style="text-align:right;"><a href="${ctx}/portal/pri/message/list.action?meetingId=${meetingId}&select=new&selectuser=${meetingcomment.user.id}&menu_id=${param.menu_id}" class="btn_blue">发私信</a></td>
						</tr>
						</table>
					</div>
					</c:if>
					<div class="postContent">
						<span class="wordbreak">${meetingcomment.content}</span> 
					</div>
				</div>            
      		</c:forEach>
			</c:when>
			<c:otherwise>
				<p><span style="font-size:12px;">该发言没有评论信息.</span></p><br>
			</c:otherwise>
		</c:choose>
        </ul>  	
		
		<div class="postPager">
			<form id="listForm" action="${ctx}/portal/pri/interaction/commentList.action">
	                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
	                <input type="hidden" name="postId" id="postId" value="${postId}"/>
	                <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
	                <input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
	                <input type="hidden" id="queryForList" />
	        </form>
			<c:if test="${pager.pageCount > 1}">
				<%@ include file="/pages/common/page.jsp" %>
		  	</c:if>
	  	</div>

		<div class="cbox"><div class="title"><h5>我要评论</h5></div></div>
		<div class="postheader">
			<p class="limit">
				<span class="tools"><a href="javascript:void(0);" class="emotion">添加表情</a></span>
				<span>目前可以输入<span id="text_limit">140</span>个字符</span>
			</p>
			<div class="emotionpanel"></div>
			<fieldset><textarea id="content" rows="4" cols="30" name="content"></textarea></fieldset>
			<a id="comment" href="javascript:void(0);" class="btn_blue send">发表评论</a>
		</div>
</div>
<%@ include file="/pages/portal/common/footer.html" %>
	<script type="text/javascript">
	$(document).ready(function() {
		$('.artZoom').artZoom({
			path: '${ctx}/js/jquery-zoom/images',	// 设置artZoom图片文件夹路径
			preload: true,		// 设置是否提前缓存视野内的大图片
			blur: true,			// 设置加载大图是否有模糊变清晰的效果
			
			// 语言设置
			left: '左旋转',		// 左旋转按钮文字
			right: '右旋转',		// 右旋转按钮文字
			source: '看原图'		// 查看原图按钮文字
		});
		
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
			
			$("span.card").click(function(){
				var id = $(this).attr('id');
				$("#card"+id).fadeIn("slow");
			});
			
			$("a.close").bind("click", function(){
				var parentid = $(this).attr('parentid');
				$("#"+parentid).fadeOut("slow");
			});			
			
			$("#queryForList").click (function() {$('#listForm').submit();});
			
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
			
			$("#comment").click (function() {
				if ($("#content").val() == "") {
					alert("请输入您的评论。");
					return false;
				}
				$.post(
						"<c:out value='${ctx}'/>/portal/pri/interaction/commentAdd.action",
						{
							"postNo" : $("#postId").val(),
							"content": $("#content").val()
						},
						function(data, textStatus) {
							if (textStatus == "success") {
								if (data.retcode == 0) {
									window.location.href = window.location.href; 
								}
							}
						}, 
						"json"
					);
			});
		});
	</script>