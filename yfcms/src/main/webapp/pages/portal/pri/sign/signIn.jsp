<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/portal/common/header.jsp" %>
    
    <div class="w960">
    <div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
	    <div class="cbody">
		<div class="cbox">	
			<div style="width:500px;magin-left:100px">
				<form id="mainForm" action="${ctx}/portal/pri/meeting/signIn.action" method="post">
					<input type="hidden" name="meetingId" value="${param.meetingId }"/>
					<input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
					<table class="grid">
						<tr class="odd">
							<td width="100" ><span class="srd"> 手机号码：</span></td>
						  	<td width="200"><span class="left">
								<input type="text" id="mobile" name="mobile" value="${mobile}"/>
								</span>
							</td>
						</tr>
						<tr>
							<td ><span class="srd"> 签到码：</span></td>
						  	<td width="200"><span class="right">
								<input type="text" id="signCode" name="signIn.signCode" value="${signIn.signCode }"/>
								</span>
							</td>
						</tr>
		      		</table>
					
					<div class="neidi">
						<font id="tip_err_msg" style="line-height:35px;margin-left:50px;" color="red">
							${errMsg }
						</font>
						<br/>
					</div>
					
					<div class="neidi">
						<input style="margin-left:65px;" type="submit" value="&nbsp;签 到&nbsp;"/>&nbsp;
					</div>
					<br/>
				</form>
			</div>			
		</div>
		</div>
	</div>
<%@ include file="/pages/portal/common/footer.html" %>