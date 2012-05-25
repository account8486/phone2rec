<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>按组选择</title>
    ${admin_css}
    ${jquery_js}
    <script language="JavaScript" type="text/javascript">
        function selectMeetingMembers() {

                var ids = getCheckBoxID();
                var mobiles=getCheckBoxMobile();
                if (ids == '') {
                    alert('请选择短信接收人');
                    return;
                }
                
                //给父窗口的数据复制
                window.opener.document.getElementById("recieverMobiles").value =mobiles;
                window.opener.document.getElementById("recieverIds").value =ids;
                window.close();
            }

        function getCheckBoxID() {
            var retString = "";
            var checks = document.getElementsByName("userId");
            if (checks) {
                for (var i = 0; i < checks.length; i++) {
                    var chkObj = checks[i];
                    if (chkObj.checked)
                        retString += chkObj.value + ",";
                }
            }

            return retString;
        }
        
        
        /**
        *获取选择下得所有电话号码
        */
        function getCheckBoxMobile() {
            var retString = "";
            var checks = document.getElementsByName("userId");
            var mobiles=document.getElementsByName("userMobile");
            var names=document.getElementsByName("userName");
            if (checks) {
                for (var i = 0; i < checks.length; i++) {
                    var chkObj = checks[i];
                    if (chkObj.checked){
                    	retString +=names[i].value+"["+mobiles[i].value + "],";
                    }    
                }
            }

            return retString;
        }

        $(function() {
            $("#all_check").change(function () {
                if (this.checked) {
                    $("[name='userId']").attr("checked", $("#all_check").attr("checked"));
                } else {
                    $("[name='userId']").removeAttr("checked");
                }

            });
            
            $('#planList').change(function(){
            	if($(this).val() != "-1"){
            		$("#planDetailList").val('-1');
					$("#selectGroupSmsForm").submit();
            	}
            });
            $('#planDetailList').change(function(){
            	if($(this).val() != "-1"){
					$("#selectGroupSmsForm").submit();
            	}
            });
            
        });
        
    </script>

</head>
<body>

<div class="page_form">
 <div class="page_tools page_serach">
     <form id="selectGroupSmsForm" action="${ctx }/admin/pri/group/selectGroupSms.action" method="post">
        <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
        <input type="hidden" name="recieverIds" id="recieverIds" value="${recieverIds}"/>
		<table width="80%">
	      <tr>
	        <th style="width: 100px; ">分组模版：</th>
	        <td style="width: 150px; ">
	        	<select id="planList" value="${groupPlan.id }" name="groupPlan.id">
	                <option value="-1">请选择</option>
	                <c:forEach var="plan" items="${groupPlanlist}">
	                	<option value="${plan.id}" ${plan.id == groupPlan.id ? 'selected' : '' } > ${plan.planName}</option>
	                </c:forEach>
	             </select>
	        </td>
	        <th style="width: 50px; ">组&nbsp;&nbsp;&nbsp;&nbsp;名：</th>
	        <td style="width: 150px; ">
				<select id="planDetailList" value="${groupPlanDetail.id }" name="groupPlanDetail.id">
	                <option value="-1">请选择</option>
	                <c:forEach var="detail" items="${groupDetailList}">
	                	<option value="${detail.id}" ${detail.id == groupPlanDetail.id ? 'selected' : '' }> ${detail.groupName}</option>
	                </c:forEach>
	             </select>
			</td>
	      </tr>
	    </table>
	</form>
	</div>
            <fieldset>
            <legend>选择用户</legend>
			  <c:if test="${not empty userList}">
	          <div align="right" class="page_form_sub"><a href="#" onclick="selectMeetingMembers();" id="addUserBtn" class="btn_common btn_true">确定</a></div>
	         </c:if>
            <dl>
                <dt>&nbsp;</dt>
                <dd>
                    <div id="userDiv">
                        <table width="100%" class="page_datalist">
                            <c:choose>
                                <c:when test="${not empty userList}">
                                    <thead>
                                    <tr>
                                        <th width="15px"><input type="checkbox" id="all_check"/></th>
                                        <th>手机号码</th>
                                        <th>用户姓名</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${userList}" varStatus="status">
                                        <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                            <td class="title"><input type="checkbox" name="userId" value="${user.id}"  <c:if test="${fn:contains(recieverIds,user.id)}"> checked="checked" </c:if>   /></td>
                                            <td>${user.mobile}<input type="hidden" name="userMobile" value="${user.mobile}"/></td>
                                            <td>${user.name}  <input type="hidden" name="userName" value="${user.name}"/></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </c:when>
                                <c:otherwise>
                                          当前分组无会议成员。
                                </c:otherwise>
                            </c:choose>
                        </table>
                    </div>
                </dd>
            </dl>
          <c:if test="${not empty userLst}">
          <div align="right" class="page_form_sub"><a href="#" onclick="selectMeetingMembers();" id="addUserBtn" class="btn_common btn_true">确定</a></div>
         </c:if>
        </fieldset>
</div>
</body>
</html>