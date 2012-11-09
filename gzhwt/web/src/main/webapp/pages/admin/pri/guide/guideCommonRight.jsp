<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<style type="text/css">
 .progress { background:#f5f5f5; border:1px solid #50bde7; width:95%; display:block; height:30px;margin-left: 3px;}
 .progress .num { background:#82dafc; height:100%; width:0%; display:block;}
</style>
<div style="width:20%;float:left; margin-top: 10px;height: 600px;border: 1px dashed #ddd" id="showProcess">
	<br/>
 	<div style="padding-left: 5px;"><h3>资料完成度:<span id="processShow"></span></h3></div>
	<span class="progress"><span class="num"></span></span>
	<%--<div style="width: 90%;margin-top: 20px;margin-left: 8px;text-indent: 2em;"> --%>
	<div style="width: 90%;margin-top: 20px;margin-left: 8px">
		<ul>
		<c:set var="meetingId" value="${not empty meetingId ?meetingId : meeting.id}"></c:set>
		<c:set var="meetingId" value="${not empty meetingId ?meetingId : param.meetingId}"></c:set>
		<li style="padding-top: 5px;"><a class="btn_common btn_true" href="${ctx}/admin/pri/meeting/guide_step1.action?fromStep=step2&meeting.id=${meetingId}">基本信息</a>&nbsp;&nbsp;&nbsp;<img src="${ctx}/images/comp.png" width="18" height="18" style="vertical-align:middle"/></li>
		<li style="padding-top: 5px;"><a class="btn_common btn_true" href="${ctx}/admin/pri/meeting/guide_step2.action?fromStep=step3&meeting.id=${meetingId}">参会人员</a>&nbsp;&nbsp;&nbsp;<img width="18" height="18" id="info1" style="vertical-align:middle;display: display"/></li>
		<li style="padding-top: 5px;"><a class="btn_common btn_true" href="${ctx}/admin/pri/meeting/guide_step3.action?fromStep=step4&meeting.id=${meetingId}">会议议程</a>&nbsp;&nbsp;&nbsp;<img width="18" height="18" id="info2" style="vertical-align:middle;display: display"/></li>
		<li style="padding-top: 5px;"><a class="btn_common btn_true" href="${ctx}/admin/pri/meeting/guide_step4.action?meeting.id=${meetingId}">会议资料</a>&nbsp;&nbsp;&nbsp;<img width="18" height="18" id="info3" style="vertical-align:middle;display: display"/></li>
		<li style="padding-top: 5px;"><a class="btn_common btn_true" href="${ctx}/admin/pri/meeting/guide_step5.action?meeting.id=${meetingId}">信息发布</a>&nbsp;&nbsp;&nbsp;<img  width="18" height="18" id="info_news" style="vertical-align:middle;display: display"/></li>
		<li style="padding-top: 5px;"><a class="btn_common btn_true" href="${ctx}/admin/pri/meeting/guide_step6.action?meeting.id=${meetingId}">用餐安排</a>&nbsp;&nbsp;&nbsp;<img  width="18" height="18" id="info_dinner" style="vertical-align:middle;display: display"/></li>
		<li style="padding-top: 5px;"><a class="btn_common btn_true" href="${ctx}/admin/pri/meeting/guide_step7.action?meetingId=${meetingId}">互动管理</a>&nbsp;&nbsp;&nbsp;<img   width="18" height="18" id="info_post" style="vertical-align:middle;display: display"/></li>
		<%--
		<li style="padding-top: 5px;"><a href="${ctx}/pages/admin/pri/guide/step8_groupAdd.jsp?meetingId=${meetingId}">分组模板</a><img src="${ctx}/images/comp.png" width="18" height="18" id="info_group" style="display: none"/></li>
		 --%>
		<li style="padding-top: 5px;"><a class="btn_common btn_true" href="${ctx}/pages/admin/pri/meeting/preView.action?meetingId=${meetingId}">会议预览</a>&nbsp;&nbsp;&nbsp;</li>
		<li style="padding-top: 5px;"><a class="btn_common btn_true" href="${ctx}/admin/export/generateHtml.action?meetingId=${meetingId}" target="_blank">会议导出</a>&nbsp;&nbsp;&nbsp;</li>
		<li style="padding-top: 5px;"><a class="btn_common btn_true" href="##" id="backMeeting">返回会议</a>&nbsp;&nbsp;&nbsp;<img src="${ctx}/images/comp.png" width="18" height="18" id="info3" style="vertical-align:middle;display: none"/></li>
		</ul>
	</div>
	
<%--
<c:if test="${not empty meeting.id}">
<div style="width:8%;float:left; margin-top: 10px;">
<!-- 左边导航菜单 -->
<div class="main" style="height:230px;" >
	<div class="left_nav">
	   	<dl>
        	<dt><h5>会议向导</h5></dt>         
            <dd>
	            <a target = "mainFrame" href="" class="act">
					<span class="nav">基本信息</span>
				</a>
			</dd>   
			 <dd>
	            <a target = "mainFrame" href="" >
					<span class="nav">参会人员</span>
				</a>
			</dd>  
			 <dd>
	            <a target = "mainFrame" href="">
					<span class="nav">会议议程</span>
				</a>
			</dd>   
			 <dd>
	            <a target = "mainFrame" href="">
					<span class="nav">会议资料</span>
				</a>
			</dd>   
			 <dd>
	            <a target = "mainFrame" href="javascript://" id="">
					<span class="nav"></span>
				</a>
			</dd>   
        </dl>
    </div>    
	</div> 
</div>
</c:if>	
 --%>
 
	<div id="intro" style="width:90%;margin-top: 45px;margin-left: 8px;text-indent: 2em;"></div>
	
</div>

<script type="text/javascript">

	function doFinish() {
		if(confirm("确定结束此会议向导操作吗？")) {
			location.href = "${ctx}/admin/pri/meeting/guide_finish.action?fromStep=step4&meeting.id=${meetingId}";
		}
	}

	$(function(){
		var meetingId="${meetingId}";
		if(meetingId!=null&&meetingId!=''){
			$.post("${ctx}/pages/admin/pri/meeting/staticsProcess.action",{"meetingId":meetingId},function(data){
				var result=data.result;
				$(".num").css("width",result);
				$("#processShow").html(result);
				//从第三步开始
				var stepValue=$("#stepValue").val();
				//alert(stepValue);
				if(stepValue>6){
				}
				
				if(!data.info_post){
					$("#info_post").attr("src","${ctx}/images/overlays/information.png");
					$("#info_post").show();
					}
				$("#info1").attr("src","${ctx}/images/overlays/information.png");
				$("#info2").attr("src","${ctx}/images/overlays/information.png");
				$("#info3").attr("src","${ctx}/images/overlays/information.png");
				$("#info_news").attr("src","${ctx}/images/overlays/information.png");
				$("#info_dinner").attr("src","${ctx}/images/overlays/information.png");
				$("#info_post").attr("src","${ctx}/images/overlays/information.png");
				
				
				
				
				if(data.info1){
					$("#info1").attr("src","${ctx}/images/comp.png");
					$("#info1").show();
				}
				if(data.info2){
					$("#info2").attr("src","${ctx}/images/comp.png");
					$("#info2").show();
				}
				if(data.info3){
					$("#info3").attr("src","${ctx}/images/comp.png");
					$("#info3").show();
				}
				if(data.info_news){
					$("#info_news").attr("src","${ctx}/images/comp.png");
					$("#info_news").show();
				}
				if(data.info_dinner){
					$("#info_dinner").attr("src","${ctx}/images/comp.png");
					$("#info_dinner").show();
				}
				if(data.info_post){
					$("#info_post").attr("src","${ctx}/images/comp.png");
					$("#info_post").show();
				}
				if(data.info_group){
					$("#info_group").attr("src","${ctx}/images/comp.png");
					$("#info_group").show();
				}
				
			});
		}
	})
</script>


