<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/portal/common/header.jsp" %>
<script src="${ctx}/js/jquery-zoom/jquery.artZoom.js"></script>
<link href="${ctx}/js/jquery-zoom/jquery.artZoom.css" rel="stylesheet" type="text/css">

<div class="w960">
	<div class="cbox"><div class="title"><h5 id="caption"></h5></div></div>

	<form id="newpost" action="${ctx}/portal/pri/interaction/postCreate.action" enctype="multipart/form-data" method="POST">
	<div class="message_sub">
		<input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
		<input type="hidden" name="type" id="type" value="portal"/>
		<input type="hidden" name="doUpload" id="doUpload" value="false"/>
		<input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
		<input type="hidden" name="menu_name" id="menu_name" value="${param.menu_name}"/>	
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
		<span class="sbm">
			<a href="javascript:void(0);" id="postAdd" class="btn_blue">发布信息</a>
		</span>
	</div>
	</form>

	<ul class="message_list" style="margin-top:75px;">
     	<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<c:forEach var="meetingpost" items="${pager.pageRecords}" varStatus="status">
  	 		 <li>
           	<p>
                   <span <c:if test="${not empty meetingpost.user}">class="name"</c:if> id="${meetingpost.id}">
                   	${meetingpost.posterName }<c:if test="${not empty meetingpost.posterJob}">(${meetingpost.posterJob})</c:if> ：
                   </span>
                   <c:if test="${not empty meetingpost.user}">
				<div class="card" id="card${meetingpost.id}">
					<h1>${meetingpost.posterName }的个人信息<a parentid="card${meetingpost.id}" class="close" href="javascript:void(0);"></a></h1>
					<table>
					<tr>
						<td class="label">职务：</td>
						<td class="value"><c:if test="${not empty meetingpost.posterJob}">${meetingpost.posterJob}</c:if></td>
						<td class="label">等级：</td>
						<td class="value">${meetingpost.user.meetingMember.memberLevel}</td>
					</tr>
					<tr>
						<td class="label">邮箱：</td>
						<td class="value">${meetingpost.user.meetingMember.mailbox}</td>
						<td class="label">房号：</td>
						<td class="value">
							<c:if test="${meetingpost.user.meetingMember.roomNumberIsDisplay eq 1}">${meetingpost.user.meetingMember.roomNumber}</c:if>
						</td>
					</tr>
					<tr>
						<td class="label">城市：</td>
						<td class="value">${meetingpost.user.meetingMember.city}</td>
						<td class="label">手机：</td>
						<td class="value">
							<c:if test="${meetingpost.user.meetingMember.mobileIsDisplay eq 1}">${meetingpost.user.mobile}</c:if>
						</td>
					</tr>
					<tr>
						<td class="value" colspan="3"><b>地址：</b>${meetingpost.user.meetingMember.address}</td>
						<td style="text-align:right;"><a href="${ctx}/portal/pri/message/list.action?meetingId=${meetingId}&selectuser=${meetingpost.user.id}&select=new&menu_id=${param.menu_id}" class="btn_blue">发私信</a></td>
					</tr>
					</table>
				</div>
				</c:if>
					<span>${meetingpost.content}</span>
                   	<c:if test="${not empty meetingpost.contentImg}">
                   		<div>
                   			<a href="${meetingpost.contentImg}" rel="${meetingpost.contentImg}"><img class="artZoom" src="${meetingpost.contentImg}" /></a>
                   		</div>
                   	</c:if>
               </p>
               <p>
               	<span class="time">
               		<fmt:formatDate value="${meetingpost.createTime }" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/> 发表 | 浏览(${meetingpost.viewCount}) | 评论(${meetingpost.commentCount})
               	</span>
                <span class="ctrl">
                	<c:if test="${not empty meetingpost.user}"> 
                		<a href="${ctx}/portal/pri/message/list.action?meetingId=${meetingId}&selectuser=${meetingpost.user.id}&select=new&menu_id=${param.menu_id}">发私信</a>&nbsp;
                	</c:if>
                	<a href="${ctx}/portal/pri/interaction/commentList.action?postId=${meetingpost.id}&meetingId=${meetingpost.meetingId}&menu_id=${param.menu_id}">我要评论</a>
                </span>
               </p>
               
                <!-- 评论框 -->
                <c:if test="${meetingpost.commentCount > 0}">
                <div class="latest_comment">
					<div class="arrow"><em class="arrow_line">◆</em><span>◆</span></div>
					<div>
						<p style="font-size: 12px; padding-top: 8px;">最新评论</p>
					<c:forEach var="meetingcomment" items="${meetingpost.comments}" varStatus="c">
						<c:if test="${c.count < 11}">
							<dl class="comment_list"><dd>${meetingcomment.creatorName}${meetingcomment.creatorJob}：${meetingcomment.content} <span class="time">-- <fmt:formatDate value="${meetingcomment.time}" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/></span></dd></dl>
						</c:if>
						<c:if test="${c.count == 11}">
						<dl class="comment_list">
						<dd><span class="links">
							<a href="${ctx}/portal/pri/interaction/commentList.action?postId=${meetingpost.id}&meetingId=${meetingpost.meetingId}&menu_id=${param.menu_id}">所有评论>></a>
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
       <br>
       
   <div class="postPager">
		<form id="listPostForm" action="${ctx}/portal/pri/interaction/postList.action">
            <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
            <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
            <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
            <input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
			<input type="hidden" name="menu_name" id="menu_name" value="${param.menu_name}"/>
            <input type="hidden" id="queryForList" />
        </form>
		<%@ include file="/pages/common/page.jsp" %>
  </div>
</div>
<%@ include file="/pages/portal/common/footer.html" %>
	
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
		
		$('.artZoom').artZoom({
			path: '${ctx}/js/jquery-zoom/images',	// 设置artZoom图片文件夹路径
			preload: true,		// 设置是否提前缓存视野内的大图片
			blur: true,			// 设置加载大图是否有模糊变清晰的效果
			
			// 语言设置
			left: '左旋转',		// 左旋转按钮文字
			right: '右旋转',		// 右旋转按钮文字
			source: '看原图'		// 查看原图按钮文字
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
		
		$("a.picture").click(function(){
			//$("#upload").click();
		});
		
		$("#upload").bind("change", function() {
			var filename = $(this).val();
			//var postfix = (/[.]/.exec(filename)) ? /[^.]+$/.exec(filename) : undefined;
			//if (postfix) {
			if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(filename.toLowerCase())) {
				$("#filename").text("图片类型必须是.gif,jpeg,jpg,png中的一种，请重新选择。");
				$("#doUpload").val("false");
			} else {
				$("#filename").text($(this).val());
				$("#doUpload").val("true");
			}
			$("#filename").show();
			return false;
			//}
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
			return false;
		});		
		
		$("span.name").click(function(){
			var id = $(this).attr('id');
			$("#card"+id).fadeIn("slow");
		});
		
		$("a.close").bind("click", function(){
			var parentid = $(this).attr('parentid');
			$("#"+parentid).fadeOut("slow");
		});
		
		$("#queryForList").click (function() {$('#listPostForm').submit();});

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
		
		$("#postAdd").click(function() {
			if ($("#content").val() == "" || $("#content").val() == "说说您对本次会议的想法和建议吧！") {
				alert("请输入您的想法和建议。");
				return false;
			}
			$("#newpost").submit();
			return false;
		});			
	});
</script>