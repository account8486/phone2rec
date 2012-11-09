<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>参会人员选择</title>

    ${admin_css}
    ${jquery_js}
    ${jquery_form_js}
    ${WdatePicker_js}

    <script language="JavaScript" type="text/javascript">
        function selectMeetingMembers() {
            /**
             if ($(":checkedbox[name='userId'] :checked").size() == 0) {
             alert("至少要选择一条数据.");
             return;
             }
             */
                var ids = getCheckBoxID();
                var mobiles=getCheckBoxMobile();
                if (ids == '') {
                    alert('请选择人员!');
                    return;
                }
                
                //给父窗口的数据复制
                window.opener.document.getElementById("recieverMobiles").value =mobiles;
                window.opener.document.getElementById("adminIds").value =ids;
                
                
                window.close();
                //赋值给隐藏变量
               // $("#userIds").val(ids);
                //提交给后台，进行数据的插入
               // $("#sendMsgFrm").submit();
            }

        function getCheckBoxID() {
            var retString = "";
            var checks = document.getElementsByName("userId");
            if (checks) {
                //alert(checks.length);
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
        	//全选或者全不选
            $("#all_check").change(function () {
                if (this.checked) {
                    $("[name='userId']").attr("checked", $("#all_check").attr("checked"));
                } else {
                    $("[name='userId']").removeAttr("checked");
                }

            });
         
          //有一个不选上则全不选
         $('input[type="checkbox"][name="userId"]').click(function () {
                var ckall = true;
                $('input[type="checkbox"][name="userId"]').each(function (){
                	if (!this.checked){ 
                		ckall = false;  
        				//直接退出循环,不在进行each循环
                		return false; 
                	    }});
                
                $('input[type="checkbox"][name="all_check"]').attr('checked', ckall);
            });
            
        });
        
        function query(){
        	$("#listUserForm").submit();
        }
    </script>

    <style>
        .textbox {
            BACKGROUND: #FFFFFF;
            BORDER-TOP: #7F9DB9 1px solid;
            BORDER-LEFT: #7F9DB9 1px solid;
            BORDER-RIGHT: #7F9DB9 1px solid;
            BORDER-BOTTOM: #7F9DB9 1px solid;
            FONT-FAMILY: "宋体", "Verdana", "Arial", "Helvetica";
            FONT-SIZE: 12px;
            TEXT-ALIGN: LIFT;
        }
    </style>
</head>
<body>


<div class="page_form">
            <fieldset>
            <legend>选择用户</legend>
			  <c:if test="${not empty adminUserList}">
	          <div align="right" class="page_form_sub"><a href="#" onclick="selectMeetingMembers();" id="addUserBtn" class="btn_common btn_true">确定</a></div>
	         </c:if>
            <dl>
                <dt>&nbsp;</dt>
                <dd>
                    <div id="userDiv">
                        <table width="100%" class="page_datalist">

                            <!-- 循环显示出用户 -->

                            <c:choose>
                                <c:when test="${not empty adminUserList}">
                                    <thead>
                                    <tr>
                                        <th width="15px"><input type="checkbox" name="all_check" id="all_check"/></th>
                                        <th>用户名</th>
                                        <th>姓名</th>
                                   <!--      <th>职务</th> -->
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${adminUserList}" varStatus="status">
                                    <c:set var="user_id" value="${user.id},"/>
                                        <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                            <td class="title"><input type="checkbox" name="userId" value="${user.id}"  <c:if test="${fn:contains(adminIds,user_id)}"> checked="checked" </c:if>   /></td>
                                            <td>${user.mobile}<input type="hidden" name="userMobile" value="${user.mobile}"/></td>
                                            <td>${user.name}  <input type="hidden" name="userName" value="${user.name}"/></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </c:when>

                                <c:otherwise>
                                  无管理员
                                </c:otherwise>
                            </c:choose>
                            
                        </table>
                    </div>

                </dd>
            </dl>
          <c:if test="${not empty adminUserList}">
          <div align="right" class="page_form_sub"><a href="#" onclick="selectMeetingMembers();" id="addUserBtn" class="btn_common btn_true">确定</a></div>
         </c:if>
        </fieldset>
        
          
        

</div>

</body>
</html>