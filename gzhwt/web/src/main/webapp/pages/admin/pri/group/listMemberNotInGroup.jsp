<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>添加成员</title>
    ${admin_css}
    ${jquery_js}
    <script language="JavaScript" type="text/javascript">
        function selectMeetingMembers() {
             var ids = getCheckBoxID();
             if (ids == '') {
                 alert('请选择要添加到改组的成员');
                 return;
             }
             var url = "${ctx}/admin/pri/group/addMember2Group.action"
             $.ajax({
				url: url,
				data:{"memberIds":ids,"groupPlanDetail.id":${groupPlanDetail.id},"groupPlan.id":${groupPlan.id}},
		        type:      'post',
		        dataType:  'json',
		        success:   callback
			});
        }
        
        function callback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){
				var url = '${ctx}/admin/pri/group/listGroupMember.action?&groupPlanDetail.id=${groupPlanDetail.id}&groupPlan.id=${groupPlan.id}';
				window.opener.location.href = url;
	            window.close();
			}
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
        
        function query(){
        	$("#selectGroupMemberForm").submit();
        }
        
        $(function() {
            $("#all_check").change(function () {
                if (this.checked) {
                    $("[name='userId']").attr("checked", $("#all_check").attr("checked"));
                } else {
                    $("[name='userId']").removeAttr("checked");
                }

            });
        });    
    </script>

</head>
<body>

<div class="page_form">
 <div class="page_tools page_serach">
     <form id="selectGroupMemberForm" action="${ctx}/admin/pri/group/listMemberNotInGroup.action" method="post">
        <input type="hidden" name="groupPlan.id" id="groupPlan.id" value="${groupPlan.id}"/>
        <input type="hidden" name="groupPlanDetail.id" id="groupPlanDetail.id" value="${groupPlanDetail.id}"/>
		<table width="80%">
	      <tr>
	        <th style="width: 100px; ">手机号码：</th>
	        <td style="width: 150px; ">
				<input type="text" value="${mobile}" name="mobile" maxlength="11">
	        </td>
	        
	        <th style="width: 50px; ">姓&nbsp;&nbsp;&nbsp;&nbsp;名：</th>
	        <td style="width: 150px; ">
				<input type="text" value="${name}" name="name" maxlength="20">
			</td>
			
			
			 <th style="width: 50px; ">城&nbsp;&nbsp;&nbsp;&nbsp;市：</th>
	        <td style="width: 150px; ">
				<input type="text" value="${city}" name="city" maxlength="20">
			</td>
			
			<th style="width: 50px; ">代表团：</th>
	        <td style="width: 150px; ">
				<input type="text" value="${delegation}" name="delegation" maxlength="20">
			</td>
			
	        <td style="width: 150px; ">
				<a href="#" onclick="query();"  class="btn_common btn_true">搜索</a>
			</td>
	      </tr>
	    </table>
	</form>
	</div>
            <fieldset>
            <legend>选择参会人员</legend>
			  <c:if test="${not empty userList}">
	          <div align="right" style="padding-left: 100px;" class="page_form_sub"><a href="#" onclick="selectMeetingMembers();" id="addUserBtn" class="btn_common btn_true">确定</a></div>
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
                                        <th>职位简称</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${userList}" varStatus="status">
                                        <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                            <td class="title"><input type="checkbox" name="userId" value="${user.id}" /></td>
                                            <td>${user.mobile}</td>
                                            <td>${user.name} </td>
                                            <td>${user.job} </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </c:when>
                                <c:otherwise>
                                          没有符合条件的参会人员。
                                </c:otherwise>
                            </c:choose>
                        </table>
                    </div>
                </dd>
            </dl>
        </fieldset>
</div>
</body>
</html>