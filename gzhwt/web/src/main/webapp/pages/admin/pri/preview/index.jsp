<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>会务通平台</title>
<meta name="robots" content="all" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link href="../../../../css/preview/style.css" rel="stylesheet" type="text/css" />
<link href="../../../../css/preview/cv.css" rel="stylesheet" type="text/css" />
	${admin_css}  
	${jquery_js}                                
	${jquery_form_js}                                 
	${util_js} 
<style>
#contentHighSet {
	word-break: break-all;
}

html,body {
	overflow: hidden;
}
</style>

<script type="text/javascript">
	$(function(){
		$(".changeSta").click(function(){
			var src=$(this).attr("src");
			if(src.indexOf("btn_shrink.gif")>0){
				$(this).attr("src","${ctx}/images/preview/btn_spread.gif");
				$(this).parents("table").next().hide();
			}else{
				$(this).attr("src","${ctx}/images/preview/btn_shrink.gif");
				$(this).parents("table").next().show();
			}
			
		})
	});
</script>

</head>

<body bgcolor="#FFFFFF">

	<div class="page_title">
		<h3>会议预览</h3>
	</div>
	<div class="page_form">
	<!--content -->
	<div id="content">
		<div class="resume_write resume_write_mdf">

			<div class="rew_top rew_top_mdf" style="height:50px; overflow:hidden; text-align:center; line-height:60px; font-weight:bold; font-size:18px;">
				<span>${meeting.name }</span>
			</div>

			<div class="rew_down rew_down_mdf">


				<ul class="rew_conl rew_conl_mdf" id="contentHighSet"
					style="height: 479px;width:670px;">
					<table border="0" cellspacing="0" cellpadding="0"
						class="paragraph_title paragraph_title_mdf">
						<tr>
							<td valign="middle">基本信息 <a name="basicInfo"> </a></td>
							<td valign="middle" align="right"><img
								src="${ctx}/images/preview/btn_shrink.gif"
								align="absmiddle" hspace="10" class="changeSta"
								style="cursor: pointer; "/> 
							</td>
						</tr>
					</table>
					<div id="Edu_info">
						<div id="Edu_edit">

							<div id="Edu_edit_57667052">
								<table border="0" cellspacing="0" cellpadding="0"
									class="weight780 weight780_mdf weight670" style="float: left;">
									<tbody>
										<tr>
											<td class="col_name" valign="top" align="left">会议名称</td>
											<td colspan="2" valign="top" align="left">${meeting.name }</td>
										</tr>
										<tr>
											<td valign="top" align="left" class="col_name">会议主题</td>
											<td colspan="2" valign="top"><p style="float: left">${meeting.topic }</p>
											</td>
										</tr>
										<tr>
											<td valign="top" align="left" class="col_name">会议地点</td>
											<td colspan="2" valign="top">${meeting.location }</td>
										</tr>
										<tr>
											<td valign="top" align="left" class="col_name">开始时间</td>
											<td colspan="2" valign="top"><s:date name="#meeting.startTime" format="yyyy-MM-dd HH:mm:ss"/></td>
										</tr>
										<tr>
											<td valign="top" align="left" class="col_name">结束时间</td>
											<td colspan="2" valign="top"><s:date name="#meeting.endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
										</tr>
										<c:if test="${ not empty meeting.freeSmsNum}">
											<tr>
											<td valign="top" align="left" class="col_name">免费短信条数</td>
											<td colspan="2" valign="top">${meeting.freeSmsNum }</td>
											</tr>
										</c:if>
										<tr>
											<td valign="top" align="left" class="col_name">会议主办方</td>
											<td colspan="2" valign="top">${meeting.host }</td>
										</tr>
										<tr>
											<td valign="top" align="left" class="col_name">备注</td>
											<td colspan="2" valign="top">${meeting.comments }</td>
										</tr>
									</tbody>
								</table>

								<p>
									<a href="${ctx}/admin/pri/meeting/guide_step1.action?fromStep=step2&meeting.id=${meetingId}">
									<img src="${ctx}/images/preview/btn_modify.gif"/>
									</a>
								</p>
							</div>
						</div>
					</div>
					
					
					
					<div id="Student">

						<!-- 参会人员 -->
						<table border="0" cellspacing="0" cellpadding="0"
							class="paragraph_title paragraph_title_mdf">
							<tbody>
								<tr>
									<td valign="middle">参会人员<a name="meetingMember"> </a></td>
									<td valign="middle" align="right"><img
										 src="${ctx}/images/preview/btn_shrink.gif"
										 align="absmiddle" hspace="10" class="changeSta"
										style="cursor: pointer; "/> 
									</td>
								</tr>
							</tbody>
						</table>
						<div id="Tra_info">
							<div id="Tra_edit">
								<div id="Tra_edit_27802085">
									<table border="0" cellspacing="0" cellpadding="0"
										class="weight780 weight780_mdf weight670" style="float: left;">
										<tr>
											<td class="col_name" valign="top" align="left" width="5%">姓名</td>
											<td class="col_name" valign="top" align="left" width="10%">性别</td>
											<td class="col_name" valign="top" align="left" width="20%">手机</td>
											<td class="col_name" valign="top" align="left" width="20%">职务</td>
											<td class="col_name" valign="top" align="left" width="30%">邮箱</td>
										</tr>
										<s:iterator value="#userList.pageRecords" var="user">
											<tr>
												<td valign="top" align="left">${user.name }</td>
												<td valign="top" align="left">${user.gender == 0 ? "男":(user.gender == 1 ? "女":"保密") }</td>
												<td valign="top" align="left">${user.mobile }</td>
												<td valign="top" align="left">${user.meetingMember.bookJob }</td>
												<td valign="top" align="left">${user.meetingMember.mailbox}</td>
											</tr>
										</s:iterator>
									</table>
								</div>
							</div>
							<table border="0" cellspacing="0" cellpadding="0"
								class="weight780 weight780_mdf">
									<tr>
										<td width="3%"></td>
										<td valign="top">
											<a href="${ctx}/admin/pri/meeting/guide_step2.action?fromStep=step3&meeting.id=${meetingId}">
											<img hspace="5" vspace="10"
											src="${ctx}/images/preview/btn_addcontinue.gif"
											onclick="addinfo('Tra');" style="cursor: pointer"
											/>
											</a>
											</td>
									</tr>
							</table>
						</div>


						<!-- 会议议程-->
						<table border="0" cellspacing="0" cellpadding="0"
							class="paragraph_title paragraph_title_mdf">
							<tbody>
								<tr>
									<td valign="middle">会议议程<a name="agenda_href"> </a></td>
									<td valign="middle" align="right"><img
									src="${ctx}/images/preview/btn_shrink.gif"
									align="absmiddle" hspace="10" class="changeSta"
									style="cursor: pointer; "/> 
									</td>
								</tr>
							</tbody>
						</table>
						<div id="Tra_info">
							<div id="Tra_edit">
								<div id="Tra_edit_27802085">
									<table border="0" cellspacing="0" cellpadding="0"
										class="weight780 weight780_mdf weight670" style="float: left;">
										<tr>
											<td class="col_name" valign="top" align="left" width="5%">日期</td>
											<td class="col_name" valign="top" align="left" width="10%">时间</td>
											<td class="col_name" valign="top" align="left" width="20%">人员</td>
											<td class="col_name" valign="top" align="left" width="20%">议题</td>
											<td class="col_name" valign="top" align="left" width="30%">地点</td>
											<td class="col_name" valign="top" align="left" width="10%">内容</td>
										</tr>
										<s:iterator value="#agendaList" var="agenda">
										<tr>
											<td valign="top" align="left">${agenda.date }</td>
											<td valign="top" align="left">${agenda.startTime }-${agenda.endTime }</td>
											<td valign="top" align="left">${agenda.host }</td>
											<td valign="top" align="left">${agenda.topic }</td>
											<td valign="top" align="left">${agenda.location }</td>
											<td valign="top" align="left">${agenda.description }</td>
										</tr>
										</s:iterator>

									</table>
								</div>
							</div>
							<table border="0" cellspacing="0" cellpadding="0"
								class="weight780 weight780_mdf">
									<tr>
										<td width="3%"></td>
										<td valign="top">
											<a href="${ctx}/admin/pri/meeting/guide_step3.action?fromStep=step4&meeting.id=${meetingId}">
											<img hspace="5" vspace="10"
											src="${ctx}/images/preview/btn_addcontinue.gif"
											onclick="addinfo('Tra');" style="cursor: pointer"
											/>
											</a>
											</td>
									</tr>
							</table>
						</div>


						<!-- 会议资料-->
						<table border="0" cellspacing="0" cellpadding="0"
							class="paragraph_title paragraph_title_mdf">
							<tbody>
								<tr>
									<td valign="middle">会议资料<a name="meetingFile"> </a></td>
									<td valign="middle" align="right"><img
									src="${ctx}/images/preview/btn_shrink.gif"
									align="absmiddle" hspace="10" class="changeSta"
									style="cursor: pointer; "/> 
									</td>
								</tr>
							</tbody>
						</table>
						<div id="Tra_info">
							<div id="Tra_edit">
								<div id="Tra_edit_27802085">
									<table border="0" cellspacing="0" cellpadding="0"
										class="weight780 weight780_mdf weight670" style="float: left;">
										<tr>
											<td class="col_name" valign="top" align="left" width="5%">文件名称</td>
											<td class="col_name" valign="top" align="left" width="10%">文件类型</td>
											<td class="col_name" valign="top" align="left" width="20%">上传时间</td>
										</tr>
										<s:iterator value="#fileList" var="meetingFile">
										<tr>
											<td valign="top" align="left">${meetingFile.fileName}</td>
											<td valign="top" align="left">
											<c:choose>
														<c:when test="${meetingFile.filePostfix=='JPG'}">
															<img width="16" height="16"
																alt="${meetingFile.filePostfix}"
																title="${meetingFile.filePostfix}"
																src="${ctx}/images/file/filetype/jpg.gif">
														</c:when>
														<c:when test="${meetingFile.filePostfix=='DOC'}">
															<img width="16" height="16"
																alt="${meetingFile.filePostfix}"
																title="${meetingFile.filePostfix}"
																src="${ctx}/images/file/filetype/word.gif">
														</c:when>
														<c:when test="${meetingFile.filePostfix=='BMP'}">
															<img width="16" height="16"
																alt="${meetingFile.filePostfix}"
																title="${meetingFile.filePostfix}"
																src="${ctx}/images/file/filetype/bmp.gif">
														</c:when>
														<c:when test="${meetingFile.filePostfix=='GIF'}">
															<img width="16" height="16"
																alt="${meetingFile.filePostfix}"
																title="${meetingFile.filePostfix}"
																src="${ctx}/images/file/filetype/gif.gif">
														</c:when>
														<c:when test="${meetingFile.filePostfix=='JPG'}">
															<img width="16" height="16"
																alt="${meetingFile.filePostfix}"
																title="${meetingFile.filePostfix}"
																src="${ctx}/images/file/filetype/jpg.gif">
														</c:when>
														<c:when test="${meetingFile.filePostfix=='PNG'}">
															<img width="16" height="16"
																alt="${meetingFile.filePostfix}"
																title="${meetingFile.filePostfix}"
																src="${ctx}/images/file/filetype/jpg.gif">
														</c:when>
														<c:when test="${meetingFile.filePostfix=='XLS'}">
															<img width="16" height="16"
																alt="${meetingFile.filePostfix}"
																title="${meetingFile.filePostfix}"
																src="${ctx}/images/file/filetype/excel.gif">
														</c:when>
														<c:when test="${meetingFile.filePostfix=='TXT'}">
															<img width="16" height="16"
																alt="${meetingFile.filePostfix}"
																title="${meetingFile.filePostfix}"
																src="${ctx}/images/file/filetype/txt.gif">
														</c:when>
														<c:when test="${meetingFile.filePostfix=='PDF'}">
															<img width="16" height="16"
																alt="${meetingFile.filePostfix}"
																title="${meetingFile.filePostfix}"
																src="${ctx}/images/file/filetype/pdf.gif">
														</c:when>
														<c:otherwise>
															<img width="16" height="16"
																alt="${meetingFile.filePostfix}"
																title="${meetingFile.filePostfix}"
																src="${ctx}/images/file/filetype/other.gif">
														</c:otherwise>
													</c:choose>
												</td>
											<td valign="top" align="left"><s:date name="#meetingFile.createTime" format="yyyy-MM-dd HH:mm"/></td>
										</tr>
										</s:iterator>

									</table>
								</div>
							</div>
							<table border="0" cellspacing="0" cellpadding="0"
								class="weight780 weight780_mdf">
									<tr>
										<td width="3%"></td>
										<td valign="top">
											<a href="${ctx}/admin/pri/meeting/guide_step4.action?meeting.id=${meetingId}">
											<img hspace="5" vspace="10"
											src="${ctx}/images/preview/btn_addcontinue.gif"
											onclick="addinfo('Tra');" style="cursor: pointer"
											/>
											</a>
											</td>
									</tr>
							</table>
						</div>
						
						
						<!-- 用餐安排  begin-->
						<table border="0" cellspacing="0" cellpadding="0"
							class="paragraph_title paragraph_title_mdf">
							<tbody>
								<tr>
									<td valign="middle">用餐安排<a name="dinnerList"> </a></td>
									<td valign="middle" align="right"><img
									src="${ctx}/images/preview/btn_shrink.gif"
									align="absmiddle" hspace="10" class="changeSta"
									style="cursor: pointer; "/> 
									</td>
								</tr>
							</tbody>
						</table>
						<div id="Tra_info">
							<div id="Tra_edit">
								<div id="Tra_edit_27802085">
									<table border="0" cellspacing="0" cellpadding="0"
										class="weight780 weight780_mdf weight670" style="float: left;">
										<tr>
											<td class="col_name" valign="top" align="left" width="5%">日期</td>
											<td class="col_name" valign="top" align="left" width="10%">时间</td>
											<td class="col_name" valign="top" align="left" width="20%">时段</td>
											<td class="col_name" valign="top" align="left" width="20%">类型</td>
											<td class="col_name" valign="top" align="left" width="30%">地点</td>
										</tr>
										<s:iterator value="#dinnerList" var="dinner">
										<tr>
											<td valign="top" align="left">${dinner.dinnerDate }</td>
											<td valign="top" align="left">${dinner.startTime }-${dinner.endTime }</td>
											<td valign="top" align="left">${1 == dinner.section ? '早':2==dinner.section?'中':'晚'}餐</td>
											<td valign="top" align="left">${dinnerTypeIdMap[dinner.type].name}</td>
											<td valign="top" align="left">${dinner.address}</td>
										</tr>
										</s:iterator>

									</table>
								</div>
							</div>
							<table border="0" cellspacing="0" cellpadding="0"
								class="weight780 weight780_mdf">
									<tr>
										<td width="3%"></td>
										<td valign="top">
											<a href="${ctx}/admin/pri/meeting/guide_step6.action?meeting.id=${meetingId}">
											<img hspace="5" vspace="10"
											src="${ctx}/images/preview/btn_addcontinue.gif"
											onclick="addinfo('Tra');" style="cursor: pointer"
											/>
											</a>
											</td>
									</tr>
							</table>
						</div>
						<!-- 用餐安排  end-->
						
						<!-- 保存按钮 
						<table border="0" cellspacing="0" cellpadding="0"
							class="rew_con_paragraph">
							<tbody>
								<tr>
									<td align="center"><img hspace="5" vspace="15"
										src="${ctx}/images/preview/btn_submit_2.gif"
										style="cursor: pointer" /></td>
								</tr>
							</tbody>
						</table>
						-->
					</div>
				</ul>


				<ul class="rew_conr rew_conr_mdf" id="contentHighSetLeft"
					style="height: 479px;width:140px;">


					<li class="btn_resume btn_resume_mdf" style="padding-top: 20px;">
						<div class="icon_width icon_width_mdf">
							<span class="text_red">&nbsp;&nbsp;&nbsp;&nbsp;</span> <a href="#basicInfo">基本信息</a>
						</div> <img
						src="${ctx}/images/preview/icon_need.gif"
						class="icon_need"
						/> </li>
					<li class="btn_resume btn_resume_mdf">
						<div class="icon_width icon_width_mdf">
							<span class="text_red">&nbsp;&nbsp;&nbsp;&nbsp;</span> <a href="#meetingMember">参会人员</a>
						</div> 
						<s:if test="#userList.pageRecords.size()>0">
							<img src="${ctx}/images/preview/icon_need.gif" class="icon_need"/>
						</s:if>
						<s:else>
							<img src="${ctx}/images/preview/icon_incomplete.gif" class="icon_need"/>
						</s:else>
						
					</li>
					<li class="btn_resume btn_resume_mdf">
						<div class="icon_width icon_width_mdf">
							<span class="text_red" id="Work_Must">&nbsp;&nbsp;&nbsp;&nbsp;</span> <a
								href="#agenda_href">会议议程</a>
						</div> 
						<s:if test="#agendaList.size()>0">
							<img src="${ctx}/images/preview/icon_need.gif" class="icon_need" />
						</s:if> 
						<s:else>
							<img src="${ctx}/images/preview/icon_incomplete.gif" class="icon_need" />
						</s:else>
					</li>

					<li class="btn_resume btn_resume_mdf">
						<div class="icon_width icon_width_mdf">
							&nbsp;&nbsp;&nbsp;&nbsp; <a href="#meetingFile">会议资料</a>
						</div> 
						
						<s:if test="#fileList.size()>0">
							<img src="${ctx}/images/preview/icon_need.gif" class="icon_need" />
						</s:if> 
						<s:else>
							<img src="${ctx}/images/preview/icon_incomplete.gif" class="icon_need" />
						</s:else>
						
					</li>
					<li class="btn_resume btn_resume_mdf">
						<div class="icon_width icon_width_mdf">
							&nbsp;&nbsp;&nbsp;&nbsp; <a href="#dinnerList">用餐安排</a>
						</div> 
						<s:if test="#dinnerList.size()>0">
							<img src="${ctx}/images/preview/icon_need.gif" class="icon_need" />
						</s:if> 
						<s:else>
							<img src="${ctx}/images/preview/icon_incomplete.gif" class="icon_need" />
						</s:else>
						
					</li>
					<li class="btn_resume btn_resume_mdf">
						<div class="icon_width icon_width_mdf">
							&nbsp;&nbsp;&nbsp;&nbsp; <a href="${ctx}/admin/pri/meeting/guide_step1.action?fromStep=step2&meeting.id=${meetingId}" >返回会议</a>
						</div>
					</li>
				</ul>

				<div
					style="line-height: 2px; height: 2px; clear: both; background: #FFFFFF;">&nbsp;</div>
			</div>
			<div class="redbott">
				<span><img src="${ctx}/images/preview/redbl.gif"
					width="6" height="6" align="left" /> </span> <span><img
					src="${ctx}/images/preview/redbr.gif" width="6"
					height="6" align="right" /> </span>
			</div>
		</div>
	</div>
</div>
	<script type="text/javascript">

var MS = "IE";
if(navigator.userAgent.indexOf("Firefox") > 0) {
    MS = "FF";
}
if (document.getElementById("contentHighSet")!=null) {
    if (document.documentElement.clientHeight > 200) {
        document.getElementById("contentHighSet").style.height = document.documentElement.clientHeight - 120 + "px";
        document.getElementById("contentHighSetLeft").style.height = document.documentElement.clientHeight - 120 + "px";
    }
}

var t = 0;

window.onresize = function() {
    var now = new Date();
    now = now.getTime();
    if (now - t > 30) {
        if (document.getElementById("contentHighSet")!=null) {
            if (document.documentElement.clientHeight > 200) {
                document.getElementById("contentHighSet").style.height = document.documentElement.clientHeight - 120 + "px";
                document.getElementById("contentHighSetLeft").style.height = document.documentElement.clientHeight - 120 + "px";
            }
            t = now;
        }
    }
}



function doFinish() {
	if(confirm("确定结束此会议向导操作吗？")) {
		location.href = "${ctx}/admin/pri/meeting/guide_finish.action?fromStep=step4&meeting.id=${meetingId}";
	}
}
</script>
</body>
</html>