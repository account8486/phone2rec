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
                window.opener.document.getElementById("recieverIds").value =ids;
                
                var num=ids.split(",").length-1;
                // alert(${calSelectNum});
                if(${calSelectNum}){
                	 window.opener.calSelectNum(num);
                }
                
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
          
          
         $("#signEventId").val(${signEventId});
         $("#signType").val(${signType});
         
         
         $("#signEventId").change(function(){
     		//如果为不加入 那么不显示
     		/*
     		if($("#signEventId").val()!=''){
     			alert($("#signEventId").val());
     			$("#queryDiv").hide();
     		}else{
     			$("#queryDiv").show();
     		}*/
     	 });
            
        });
        
        function query(){
        	 var signType=$("#signType").val();
        	 var signEventId=$("#signEventId").val();
        	 
        	if((signType!=''&&signEventId!='')||(signType==''&&signEventId=='')){
        		 $("#listUserForm").submit();
        	}else{
        		alert("签到事件与状态条件必须都要选择才能查询！");
        	}
        	 
        	
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
 
 
 <div class="page_tools page_serach">
	<form id="listUserForm" action="${ctx}/admin/pri/message/getMeetingMember.action">
	
	 	<input type="hidden" name="recieverIds" id="recieverIds" value="${recieverIds}"/>
        <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
		
		
		<table width="80%">
	      <tr style="align:left">
	        <th style="width: 100px; ">手机号码：</th>
	        <td style="width: 150px; "><input type="text" style="width: 150px; " id="mobile" name="mobile"
                                   value="${mobile}"/></td>
	        <th style="width: 50px; ">姓 名：</th>
	        <td style="width: 150px; "><input type="text" style="width: 120px; " id="name" name="name"
                                   value="${name}"/></td>
                                   
     
           
           </tr>
           
           <tr style="align:left" >
				<th style="width: 100px; ">签到事件：</th>
		        <td style="width: 150px; ">
		           
		                <select id="signEventId" name="signEventId">
		            	   <option value="">未选择</option>
		            	     <c:if test="${not empty signEventLst}">
			            	 <c:forEach var="signEvent" items="${signEventLst}" varStatus="status">
			            		<option value="${signEvent.id }"> ${signEvent.eventName}</option>
			            	 </c:forEach>
			            	    </c:if>
						</select>
		          
		         </td>
		         
		        <th style="width:50px;">状 态：</th>
		        <td style="width: 120px; ">
		                <select id="signType" name="signType">
		            	   		<option value="">未选择</option>
			            		<option value="1">迟到</option>
			            		<option value="2">早退</option>
						</select>
		         </td>
		         
		                 <th style="width: 50px; ">级别：</th>
             <td style="width: 150px; ">
             <select id="memberLevel" name="memberLevel" style="width:30%;align:left;" >
                <option value=""></option>
                <c:forEach begin="1" end="5" step="1" varStatus="status" >
                <option value="${status.count}" <c:if test="${status.count eq memberLevel}"> selected="selected"</c:if>>${status.count}级</option>
                </c:forEach>
             </select>
              </td>
              
		         
		         
		         <td><a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a></td> 
         </tr>   
          
	    </table>
	</form>
	</div>
    
       
       
            <fieldset>
            <legend>选择用户</legend>
			  <c:if test="${not empty userLst}">
			  <c:if test="${fn:length(userLst)>10} ">
	          <div align="right" class="page_form_sub"><a href="#" onclick="selectMeetingMembers();" id="addUserBtn" class="btn_common btn_true">确定</a></div>
	         </c:if>
	         </c:if>
            <dl>
                <dt>&nbsp;</dt>
                <dd>
                    <div id="userDiv">
                        <table width="100%" class="page_datalist">

                            <!-- 循环显示出用户 -->

                            <c:choose>
                                <c:when test="${not empty userLst}">
                                    <thead>
                                    <tr>
                                        <th width="15px"><input type="checkbox" name="all_check" id="all_check"/></th>
                                        <th>手机号码</th>
                                        <th>用户姓名</th>
                                        <th>职务</th>
                                   </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${userLst}" varStatus="status">
                                        <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                            <td class="title"><input type="checkbox" name="userId" value="${user.id}"  <c:if test="${fn:contains(recieverIds,user.id)}"> checked="checked" </c:if>   /></td>
                                            <td>${user.mobile}<input type="hidden" name="userMobile" value="${user.mobile}"/></td>
                                            <td>${user.name}  <input type="hidden" name="userName" value="${user.name}"/></td>
                                            <td>${user.meetingMember.bookJob}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </c:when>

                                <c:otherwise>
                                   未搜索到符合条件的会议成员。
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