<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.wondertek.meeting.model.MeetingFiles" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会议菜单管理</title>
  <link href="${ctx}/css/reset.css" rel="stylesheet" type="text/css"/>
  <link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css"/>
  <link href="${ctx}/css/index.css" rel="stylesheet" type="text/css"/>
    ${admin_css}
    ${jquery_js}
    ${util_js}
    ${admin_js}
<script>
function doSbFrm(){
	 if(!getCheckedValue()){
		 return;
	 }
	 $("#chooseManuSet").submit();
}



function  getCheckedValue(){
	var clientObj=document.getElementsByName("clientMenuCk");
	var clientRequestObj=document.getElementsByName("clientRequest");

	for(var i=1; i<=clientObj.length; i++){
	    if(clientObj[i-1].checked){
	    	//alert("clientSorCode"+i);
	    	if(document.getElementById("clientSortCode"+i).value==''){
	    		alert("排序码不能为空,请输入排序码!");
	    		return false;
	    	}
	    	clientRequestObj[i-1].value=clientObj[i-1].value+","+document.getElementById("clientSortCode"+i).value; //如果选中，将value添加到变量s中
	    	
	    	
	    	//alert(clientObj[i-1].value);
	    	}else{
	    	clientRequestObj[i-1].value=clientObj[i-1].value+",unchecked"; //为选中
	    	}
	}
	
	var webObj=document.getElementsByName("webMenuCk");
	var webRequestObj=document.getElementsByName("webRequest");
	
	for(var i=1; i<=webObj.length; i++){
	    if(webObj[i-1].checked){
	    	//alert("webSorCode"+i);
	    	if(document.getElementById("webSortCode"+i).value==''){
	    		alert("排序码不能为空,请输入排序码!");
	    		return false;
	    	}
	    	
	    	webRequestObj[i-1].value=webObj[i-1].value+","+document.getElementById("webSortCode"+i).value; //如果选中，将value添加到变量s中
	    	//alert(webObj[i-1].value);
	    	}else{
	    	webRequestObj[i-1].value=webObj[i-1].value+",unchecked"; 
	    	}
	}
	
	var wapObj=document.getElementsByName("wapMenuCk");
	var wapRequestObj=document.getElementsByName("wapRequest");
	for(var i=1; i<=wapObj.length; i++){
	    if(wapObj[i-1].checked){
	    	//alert("wapSorCode"+i);
	     	if(document.getElementById("wapSortCode"+i).value==''){
	     		alert("排序码不能为空,请输入排序码!");
	    		return false;
	    	}
	     	
	    	wapRequestObj[i-1].value=wapObj[i-1].value+","+document.getElementById("wapSortCode"+i).value; //如果选中，将value添加到变量s中
	    	//alert(wapObj[i-1].value);
	    	}else{
	    	wapRequestObj[i-1].value=wapObj[i-1].value+",unchecked"; 
	    	}
	}
	
	
	var touchObj=document.getElementsByName("touchMenuCk");
	var touchRequestObj=document.getElementsByName("touchRequest");
	for(var i=1; i<=touchObj.length; i++){
	    if(touchObj[i-1].checked){
	    	//alert("touchSorCode"+i);
	     	if(document.getElementById("touchSortCode"+i).value==''){
	     		alert("排序码不能为空,请输入排序码!");
	    		return false;
	    	}
	     	
	    	touchRequestObj[i-1].value=touchObj[i-1].value+","+document.getElementById("touchSortCode"+i).value; //如果选中，将value添加到变量s中
	    	//alert(touchObj[i-1].value);
	    	}else{
	    	touchRequestObj[i-1].value=touchObj[i-1].value+",unchecked"; 
	    	}
	}
	
	return true;
	
}

/**
 * 输入值时候选中
 */
function makeCkboxChecked(node, targetId){
	//如果有输入排序码,那么就直接给勾上
	if($(node).val()!=''){
		$("#"+targetId).attr("checked", true);
	}else{
		$("#"+targetId).attr("checked", false);
	}
}
	
</script>
</head>

<body>
  <div>
        <div>
           <div class=page_title><h3>用户级别  -- ${CURRENT_MEETING}</h3> </div>
           
           <form id="chooseManuSet" name="chooseManuSet" action="${ctx}/admin/pri/menu/saveMenuForMeeting.action" method="post">
           
         	<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
         	<input type="hidden" id="memberLevel" name="memberLevel" value="${memberLevel}"/>
         	
            <table class="page_datalist" style="width:100%">
            <tr>
            
            <!-- 第一列 START -->
         
            <td valign="top">
           
            <table class="page_datalist">
                <thead>
                <tr>          
                    <th>客户端</th>
                    <th>排序码</th>
                </tr>
                </thead>
            <c:choose>
                    <c:when test="${not empty clientMenuList}">
                        <c:forEach var="clientMenu" items="${clientMenuList}" varStatus="status">
                            <tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
                            
                                <td id="td_${clientMenu.id}" align="left">
                                <input type="hidden" name="clientRequest"/>
                                <input type="checkbox"  name="clientMenuCk" id="clientMenuCk${status.count}" value="${clientMenu.id}" <c:if test="${fn:contains(choosedClientMenuStr,clientMenu.id)}"> checked="checked" </c:if>   >
                                ${clientMenu.name}
                                </td>  
                                <td><input maxlength="4" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" onblur="makeCkboxChecked(this, 'clientMenuCk${status.count}');"  style="width:100px,height=60px;" name="clientSortCode" id="clientSortCode${status.count}" value="<c:if test="${not empty clientMenu.orderCode&&clientMenu.orderCode!='null'}">${clientMenu.orderCode}</c:if>"/> </td>
                             
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">
                         客户端无会议菜单
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </td>
        
          <!-- 第一列 END -->
       
          <!-- 第二列 START -->
            <td valign="top">
            <!-- WEB -->
            <table class="page_datalist">
                <thead>
                <tr>          
                    <th>WEB</th>
                    <th>排序码</th>
                </tr>
                </thead>
            <c:choose>
                    <c:when test="${not empty webMenuList}">
                        <c:forEach var="clientMenu" items="${webMenuList}" varStatus="status">
                            <tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
                            
                                <td id="td_${clientMenu.id}" align="left">
                                
                                <!-- 
                                <c:if test="${clientMenu.name eq '首页'}">
                                 <div id="div_${clientMenu.id}" style="position:absolute; width:30px; height:30px; display:block; top:0px; left:0px"></div>
                                 <script>$("#div_" + ${clientMenu.id}).css("top", $("#td_" + ${clientMenu.id}).css("top"));
                                 $("#div_" + ${clientMenu.id}).css("left", $("#td_" + ${clientMenu.id}).css("left"))</script>
                                 </c:if> -->
                                 
                                <input type="hidden" name="webRequest"/>
                                <input type="checkbox"  name="webMenuCk" id="webMenuCk${status.count}" value="${clientMenu.id}" <c:if test="${clientMenu.name eq '首页'}"></c:if>  <c:if test="${fn:contains(choosedWebMenuStr,clientMenu.id)}"> checked="checked" </c:if>  >
                                ${clientMenu.name}</td>  
                                <td><input type="text" maxlength="4" onkeyup="value=value.replace(/[^\d]/g,'')"  onblur="makeCkboxChecked(this, 'webMenuCk${status.count}');" style="width:100px"  name="webSorCode" id="webSortCode${status.count}" value="<c:if test="${not empty clientMenu.orderCode&&clientMenu.orderCode!='null'}">${clientMenu.orderCode}</c:if>"/> </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">
                         WEB无会议菜单
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </td>
          <!-- 第二列 END -->
            
          <!-- 第三列 START -->
            <td valign="top">
            <!-- CLIENT -->
            <table class="page_datalist">
                <thead>
                <tr>          
                    <th  align="center">WAP</th>
                    <th>排序码</th>
                </tr>
                </thead>
            <c:choose>
                    <c:when test="${not empty wapMenuList}">
                        <c:forEach var="clientMenu" items="${wapMenuList}" varStatus="status" >
                            <tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
                            
                                <td id="td_${clientMenu.id}" align="left">
                        
                                
                                <input type="hidden" name="wapRequest"/>
                                <input type="checkbox"  name="wapMenuCk" id="wapMenuCk${status.count}" value="${clientMenu.id}"     <c:if test="${fn:contains(choosedWapMenuStr,clientMenu.id)}"> checked="checked"  </c:if>  >
                                ${clientMenu.name}</td>  
                                
                               <td>  <input type="text" maxlength="4"  style="width:100px"  name="wapSortCode"  id="wapSortCode${status.count}" onkeyup="value=value.replace(/[^\d]/g,'')"  onblur="makeCkboxChecked(this, 'wapMenuCk${status.count}');" value="<c:if test="${not empty clientMenu.orderCode&&clientMenu.orderCode!='null'}">${clientMenu.orderCode}</c:if>"/></td>
                            </tr>                     
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">
                         WAP无会议菜单
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </td>
          <!-- 第三列 END -->
          
          
          <!-- 第四列 START -->
            <td valign="top">
            <!-- touch -->
            <table class="page_datalist">
                <thead>
                <tr>          
                    <th  align="center">TOUCH</th>
                    <th>排序码</th>
                </tr>
                </thead>
            <c:choose>
                    <c:when test="${not empty touchMenuList}">
                        <c:forEach var="clientMenu" items="${touchMenuList}" varStatus="status" >
                            <tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
                            
                                <td id="td_${clientMenu.id}" align="left">
                        
                                
                                <input type="hidden" name="touchRequest"/>
                                <input type="checkbox"  name="touchMenuCk" id="touchMenuCk${status.count}" value="${clientMenu.id}"     <c:if test="${fn:contains(choosedTouchMenuStr,clientMenu.id)}"> checked="checked"  </c:if>  >
                                ${clientMenu.name}</td>  
                                
                               <td>  <input type="text" maxlength="4"  style="width:100px"  name="touchSortCode"  id="touchSortCode${status.count}" onkeyup="value=value.replace(/[^\d]/g,'')"  onblur="makeCkboxChecked(this, 'touchMenuCk${status.count}');" value="<c:if test="${not empty clientMenu.orderCode&&clientMenu.orderCode!='null'}">${clientMenu.orderCode}</c:if>"/></td>
                            </tr>                     
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">
                         touch无会议菜单
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </td>
          <!-- 第四列 END -->
          
          
          
         </tr>
            
          <!-- 第二行 -->
          <tr>
              <td colspan="3" align="center" >
               <a href="#" onclick="doSbFrm();" id="addUserBtn" class="btn_common btn_true">保存菜单</a>
        	   <a href="${ctx}/pages/admin/pri/menu/member_level_list.jsp?meetingId=${meetingId}" id="submitBtn" class="btn_common btn_false">返回列表</a>  
              </td>
              </tr>
            </table>
            </form>
           <div>
               </div>
        </div>
    </div>


 <!-- 如果非空 提示语句 -->
        <c:if test="${not empty msgFromSave}">
        <script>
		alert('${msgFromSave}');</script>
        </c:if>

</div>

</body>
</html>