<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>安徽电信会议云平台</title>
<link href="${ctx}/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/zoom.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
<script src="${ctx}/js/jquery-zoom/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery-zoom/artZoom.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
${util_js}

</head>
<body>
	<div class=page_title><h3>会议互动交流 -- ${CURRENT_MEETING}</h3></div>
	<div class="easyui-tabs" border="false" style="padding:10px;">
	<div title="互动交流" style="padding:10px;">
	<div style="padding:5px;width:95%;">
	<form id="newpost" action="${ctx}/admin/pri/interaction/postCreate.action" enctype="multipart/form-data" method="POST">
		<div class="message_sub">
		<input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
		<input type="hidden" name="type" id="type" value="admin"/>
		<input type="hidden" name="doUpload" id="doUpload" value="false"/>		
			<p class="limit">
				<span class="tools">
					<a href="javascript:void(0);" class="emotion">添加表情</a>
					<span class="upload-area">
						<span class="upload-button"><a href="javascript:void(0);" class="picture">上传图片</a></span>
						<input type="file" tabindex="-1" id="upload" name="upload" class="upload-file">
					</span>
					<span id="filename" style="display:none;font-size:12px;color:red;"></span>
				</span>
				<span>目前可以输入<span id="text_limit">140</span>个字符</span>
			</p>
			<p><div class="emotionpanel"></div></p>
  		    <span class="ipt"><textarea id="content" name="content" rows="2">说说您对本次会议的想法和建议吧！</textarea></span>
            <span class="sbm"><a href="javascript:void(0);return false;" id="postAdd" class="btn_common btn_true">发布信息</a></span>
      	</div>
    </form>
      	
      	<ul class="message_list">
      	<c:choose>
			<c:when test="${not empty pager.pageRecords}">
			<c:forEach var="meetingpost" items="${pager.pageRecords}" varStatus="status">
   	 		 <li>
            	<p>
                    <span class="name">${meetingpost.posterName }<c:if test="${not empty meetingpost.posterJob}">${meetingpost.posterJob }</c:if>：</span><span>${meetingpost.content}</span>
                    <c:if test="${not empty meetingpost.contentImg}">
						<div><a class="miniImg artZoom" href="${meetingpost.contentImg}"><img src="${meetingpost.contentImg}"/></a></div>
					</c:if>
                </p>
                <p>
                	<span class="time"><fmt:formatDate value="${meetingpost.createTime }" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/> 发表 | 浏览(${meetingpost.viewCount }) | 评论(${meetingpost.commentCount })</span>
                    <span class="ctrl"><a href="javascript:void(0);" class="postDelete" id="${meetingpost.id }">删除</a> | <a href="${ctx}/admin/pri/interaction/commentList.action?postId=${meetingpost.id }&meetingId=${meetingpost.meetingId}">我要评论</a></span>
                </p>

                <c:if test="${meetingpost.commentCount > 0}">
                <div class="latest_comment">
					<div class="arrow"><em class="arrow_line">◆</em><span>◆</span></div>
					<!-- 评论框 -->
					<div>
						<p style="font-size: 12px; padding-top: 8px;">最新评论</p>
					<c:forEach var="meetingcomment" items="${meetingpost.comments}" varStatus="c">
						<c:if test="${c.count < 11}">
							<dl class="comment_list"><dd>${meetingcomment.creatorName}${meetingcomment.creatorJob}：${meetingcomment.content} <span class="time">-- <fmt:formatDate value="${meetingcomment.time}" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/></span></dd></dl>
						</c:if>
						<c:if test="${c.count == 11}">
						<dl class="comment_list">
						<dd><span class="links">
							<a href="${ctx}/admin/pri/interaction/commentList.action?postId=${meetingpost.id }&meetingId=${meetingpost.meetingId}">所有评论>></a>
						</span></dd>
						</dl>
						</c:if>
					</c:forEach>
					</div>
				</div>
				</c:if>
            </li>
      		</c:forEach>
			</c:when>
			<c:otherwise>
				<li><p><span>没有会议互动交流信息.</span></p></li>
			</c:otherwise>
		</c:choose>
        </ul>
       
	   <div class="top_space">
			<form id="listPostForm" action="${ctx}/admin/pri/interaction/postList.action">
	            <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	            <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
	            <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
	            <input type="hidden" id="queryForList" />
	        </form>
			<%@ include file="/pages/common/page.jsp" %>
	  </div>
    </div>
    </div>
    <div title="投票管理" link="${ctx}/admin/pri/vote/vote_findVoteAll.action?meetingId=${meetingId}" style="padding:10px;"></div>
    </div>

	<script type="text/javascript">
	$(document).ready(
		function() {
			//互动交流页面li hover效果
			$(".message_list li:odd").addClass("odd");
			//互动交流发表信息
			//说说您对本次会议的想法和建议吧！
			$(".message_sub .ipt textarea").focusin(function() {
				$(this).parent().addClass('focus');
				if($(this).html() == "说说您对本次会议的想法和建议吧！"){
					$(this).empty();
				}
			});
			$(".message_sub .ipt textarea").focusout(function() {
				$(this).parent().removeClass('focus');
				if($(this).html() == ""){
					$(this).html('说说您对本次会议的想法和建议吧！');
				}
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
			
			$("#upload").bind("change", function() {
				var filename = $(this).val();
				if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(filename.toLowerCase())) {
					$("#filename").text("图片类型必须是.gif,jpeg,jpg,png中的一种，请重新选择。");
					$("#doUpload").val("false");
				} else {
					$("#filename").text(filename);
					$("#doUpload").val("true");
				}
				$("#filename").show();
			}); 
			
			$("div.emotionpanel").delegate("a", "click", function(event) {
				if ($(this).attr("class") != "close") {
					var value = $(this).attr("value");
					try {
						if ($("#content").val() == "说说您对本次会议的想法和建议吧！") {
							$("#content").val("");
						}
						$("#content").insertAtCaret(value);
						
					} catch(err) {}
				}
				$("div.emotionpanel").fadeOut("slow");
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
			
			$("#postAdd").click( function() {
				if ($("#content").val() == "" || $("#content").val() == "说说您对本次会议的想法和建议吧！") {
					alert("请输入您的想法和建议。");
					return false;
				}
				$("#newpost").submit();
				return false;
			});
			
			$("#queryForList").click (function() {$('#listPostForm').submit();});
			
			$("a.postDelete").click (function() {
				if(confirm("确定要删除该发言吗?")){
					var id = $(this).attr('id');
					$.post(
						"<c:out value='${ctx}'/>/admin/pri/interaction/postDelete.action",
						{"id" :　id},
						function(data, textStatus) {
							if (textStatus == "success") {
								if (data.result) {
									$('#listPostForm').submit();
								} else {
									// error
								}
							} else {
								// error
							}
						}, 
						"json"
					);
				}
			});
		}
	);
	
	$(function(){
		$(".easyui-tabs").tabs({  
			onSelect:function(title){  
				var tab = $(this).tabs("getTab", title); 
				var href = tab.attr("link");
				if (href) {
					location.href = href;
					showLoading(title);
					return false;
				}
			}  
		});
		
	})
	</script>
</body>
</html>