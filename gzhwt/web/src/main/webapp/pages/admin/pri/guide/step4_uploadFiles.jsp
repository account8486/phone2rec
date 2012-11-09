<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>资料管理  -- ${CURRENT_MEETING}</title>
	${admin_css} ${jquery_js} ${jquery_form_js} ${validate_js} ${WdatePicker_js} ${jmpopups_js} ${util_js}
	<script type="text/javascript">
		$(document).ready(function() {
			loadList();
			//用户级别选择增加全选功能
			$("#memberLevelSelectAll").click(function() {
				var checked = $("#memberLevelSelectAll").prop("checked");
				$(":checkbox[name='memberLevel']").attr("checked", checked);
			});
			
			
			var intro="会议资料是对会议的有效辅助,对会议中使用到的资料进行上传,所有的参会人员可以进行浏览,实现会议的无纸化。";
			$("#intro").html(intro);
		});
		
		function loadList(){
			var timeStamp=new Date().getTime();
			var url="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?from=guide&meetingId=${meetingId}&timeStamp="+timeStamp;
			$("#list").load(url);
		}
		
		
		function doSbFrm(){
			var uploadTxt=$("#upload").val();
			var assortId=$("#assortId").val();
			if(uploadTxt==''){
			   //alert("请选择要上传的文件!");
			   if(confirm("您没有选择要上传的会议资料文件，确定忽略会议资料上传完成会议创建操作吗？")) {
				   location.href = "${ctx}/admin/pri/meeting/guide_finish.action?guideStep=step4&meeting.id=${meetingId}";
			   	}
			   return;
			}
			showLoading("正在上传文件中......");
			$("#upFrm").submit();
		}
		
		function doFinish() {
			if(confirm("确定结束此会议向导操作吗？")) {
				location.href = "${ctx}/admin/pri/meeting/guide_finish.action?fromStep=step4&meeting.id=${meetingId}";
			}
		}
	</script>
</head>
<body>


<script type="text/javascript">
	$(function(){
		$("#backMeeting").click(function(){
			doFinish();
		});
		
	});
</script>
	
<div style="width: 75%;float:left;margin-left: 5px;margin-right: 20px;">
	<div class="mainbox"><div class="page_title"><h3>会议向导 -> 第四步：上传会议资料信息</h3></div></div>
	
			<div class="page_form5">
			<form id="upFrm" action="${ctx}/admin/pri/meeting/doFileUpload.action" enctype="multipart/form-data" method="POST" >
				<fieldset>
				<dl>
				<dt class="title"><label for="title">选择上传文件</label></dt>
				<dd><input name="upload" id="upload" type="file"/><font color="red">&nbsp;系统最大支持100M文件。</font></dd>
				</dl>
				<dl>
				<dt><label for="title">排序</label></dt>
				<dd>
				<input class="half" name="sortCode" id="sortCode" onkeyup="value=value.replace(/[^\d]/g,'')" type="text" maxlength="5"/>
				</dd>
				</dl>
				<dl>
				<dt><label for="title">备注</label></dt>
				<dd>
					<input class="half" name="fileComment" type="text" maxlength="20"/>
					<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}" />
					<input type="hidden" name="guideStep" value="step4" />
					<input type="hidden" id="stepValue" name="stepValue" value="4" />
				</dd>
				</dl>
				<dl>
				<dt><label for="title">权限</label></dt>
				<dd>
					一级<input type="checkbox"  name="memberLevel" value="1" />
					二级<input type="checkbox"  name="memberLevel" value="2" />
					三级<input type="checkbox"  name="memberLevel" value="3" />
					四级<input type="checkbox"  name="memberLevel" value="4" />
					五级<input type="checkbox"  name="memberLevel" value="5" />
					
					<input type="checkbox" id="memberLevelSelectAll" style="margin-left:40px;" /><label for="userLevelSelectAll">全选</label>
				</dd>
				</dl>
				<dl>
					<dt><label for="title">支持下载</label></dt>
					<dd>
						<input type="radio" value="1" name="downloadFlg">是
						<input type="radio" checked="checked" value="0" name="downloadFlg">否 
					</dd>
				</dl>
				<%-- 贵州会务通不支持预览功能
				<dl>
					<dt><label for="title">支持预览</label></dt>
					<dd>
					<input type="radio" value="1" name="previewFlg">是
					<input type="radio" checked="checked" value="0" name="previewFlg">否 
					</dd>
				</dl>
				--%>
				<!-- 类别选择 -->
				<dl>
				<dt><label for="title">文件夹</label></dt>
				<dd>
				<select id="assortId" name="assortId" style="width:200px;">
				  <option value=""> </option>
				  <c:forEach var="meetingFilesAssort" items="${meetingFilesAssortLst}" varStatus="status">
				  <c:if test="${not empty meetingFilesAssort}">
				  <option value="${meetingFilesAssort.id }">${meetingFilesAssort.assortName }</option>
				  </c:if>
				  </c:forEach>
				</select>
				</dd>
				</dl>
				
				<dl>
				<dt class="title"><label for="title">上传资料封面</label></dt>
				<dd><input name="fileCover" id="fileCover" type="file"/><font color="black">&nbsp;建议上传尺寸为139*189大小的图片。</font></dd>
				</dl>
				
				<s:if test="meetingFilesList.size() > 0">
				<div>
					<h3 style="color:red;">已经上传了${fn:length(meetingFilesList) }个会议资料文件：</h3>
					<div style="margin-left:110px;">
						<ul>
						<c:forEach items="${meetingFilesList }" var="file">
							<li>${file.fileName }</li>
						</c:forEach>
						</ul>
					</div>
				</div>
				</s:if>
				
				</fieldset>
				
				<div><font color="red">${retMsg}</font></div>
				
				<div class="page_form_sub">
				 <a href="${ctx}/admin/pri/meeting/guide_step3.action?fromStep=step4&meeting.id=${meetingId}" class="btn_common btn_true">上一步</a>
				
				 <s:if test="meetingFilesList.size() > 0">
				 <a href="#" onclick="doSbFrm();" id="addUserBtn" class="btn_common btn_true">继续上传</a> 
				 </s:if>
				 <s:else>
				 <a href="#" onclick="doSbFrm();" id="addUserBtn" class="btn_common btn_true">上传</a> 
				 </s:else>
				
				      <a href="${ctx}/admin/pri/meeting/guide_step5.action?guideStep=step5&meeting.id=${meetingId}"   class="btn_common btn_true">下一步</a>
	        
				
				</div>
			</form>
	</div>
	
	<div id="list"></div>
</div>

<jsp:include page="guideCommonRight.jsp" />
<div style="clear: both"></div>
</body>
</html>