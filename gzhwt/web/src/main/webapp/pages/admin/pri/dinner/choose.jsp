<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
${admin_css}                                   
${jquery_js}                                 
${jquery_form_js}                       
${jquery_select_js}
</head>
<body>
<div class="page_title">
	<h3>用餐座位安排</h3>
</div>
	<div class="page_form">
		<div class="dinner_choose">
			<table width="100%" cellspacing="0" class="tbcss">
			  <tr>                
			    <td class="nobg" >  
			    	<div style="float:left">  
				    		<c:set var="section" value="${1 == dinner.section ? '早':2==dinner.section?'中':'晚'}"></c:set>
				    		<c:set var="type" value="${1 == dinner.type ? '自助餐':2 == dinner.type ?'宴会餐':'其他'}"></c:set>
				    		<p>${dinner.dinnerDate } 在 <b>${dinner.address}</b> 安排 ${section}餐(${dinner.startTime }-${dinner.endTime },${type})</p>
				    		请填写桌号<input type="text" name="tableCode" id="tabelCode" maxLength="50" /> (例如：1号桌 或 天云阁)
					    	<a href="#" onclick="selectOK();" id="submitBtn" class="btn_common btn_true">保 存</a> 
					    	<a href="#" class="btn_common btn_false" onclick="back2ListDinner();" id="submitBtn" >返回列表</a> 
			    	</div>
				</td>
			  </tr>
			</table>
			<hr></hr>
			<table width="98%" cellspacing="0" style="margin:1px;">
			  <tr>                       
          	    <th id="wait" style="width:33%;text-align:center;font-wight: bold;">候选人员</th>
				<th  style="width:33%;text-align:center;font-wight: bold;">已选人员</th>
				<th id="choose" style="width:33%;text-align:center;font-wight: bold;">已分桌人员</td>
			  </tr>
			  <tr>                       
          	    <th id="usertd1" class="choose_select">      
			    	<select name="a_Users" id="a_Users" class="choose_select_s" ondblclick="addToSelect()" multiple="multiple">
			    		<c:forEach var="user" items="${waitUserList}" varStatus="status">
			    			<option value="${user.id}" >${user.mobile}&nbsp; ${user.name} </option>
			    		</c:forEach>
			    	</select>
				</th>
				<th  id="usertd2" class="choose_select">    
			    	<select name="a_selectUsers" id="a_selectUsers" class="choose_select_s" ondblclick="removeFromSelect();" multiple="multiple">
			    	</select>
				</th>
				<th id="usertd2" class="choose_select">    
			    	<select name="choosedTableUsers" id="choosedTableUsers" class="choose_select_s" multiple="multiple" readonly='readonly'>
			    		<c:forEach var="user" items="${choosedUserTableList}" varStatus="status">
			    			<option value="${user.id}">${user.tableCode } : ${user.name}   </option>
			    		</c:forEach>
			    	</select>
				</td>
			  </tr>
			  <tr>                 
			    <td class="nobg" colspan="1" align="center" style="text-align:center;">     
			     	<a href="#" class="btn_common btn_false" onclick="addToSelect();" >添加</a>            
			    	<!-- <a href="#" class="btn_common btn_false" onclick="addToSelectAll();" >全部添加</a> -->
			    </td>           
			    <td class="nobg" colspan="1" align="center" style="text-align:center;">     
			    	<a href="#" class="btn_common btn_false" onclick="removeFromSelect();" >移出</a>  
			    	<a href="#" class="btn_common btn_false" onclick="removeFromSelectAll();" >全部移出</a>	
				</td>
			    <td class="nobg" colspan="1" align="center" style="text-align:center;">     
			    	<a href="#" class="btn_common btn_true" onclick="removeFromChoosed();" >重新分桌</a>	
				</td>
			  </tr>				           
			</table>
		</div>
	</div>
	
<script type="text/javascript">

		/*
		添加备选用户到已选择（左->右）
		*/
		function addToSelect(){			
			var s = $('#a_Users').getAllSelect();
			var ss = $('#a_selectUsers').getAllOption();
			var max = 50;
			if(ss[0].length+s[0].length > max){
				alert('选择太多,每桌最多'+max+'人,已添加了'+ss[0].length+'人'+',还剩'+(max-ss[0].length)+'人,您选择了'+s[0].length+'人');
				return false;
			}
			for(i=0;i<s[0].length;i++){
				$('#a_selectUsers').addOption(s[1][i],s[0][i]);
			}
			$('#a_Users').removeAllSelected();
		}
		/*
		添加所有备选用户到已选择（左->右）
		*/
		function addToSelectAll(){
			var s = $('#a_Users').getAllOption();                           
			for(i=0;i<s[0].length;i++){            
				$('#a_selectUsers').addOption(s[1][i],s[0][i]);    
			}
			$('#a_Users').removeAllOption();
		}
		/*
		删除所有已选用户到已选择（右->左）
		*/
		 function removeFromSelectAll(){
		 	 var s = $('#a_selectUsers').getAllOption();
			for(i=0;i<s[0].length;i++){            
				$('#a_Users').addOption(s[1][i],s[0][i]);    
			}
	    	 $('#a_selectUsers').removeAllOption();   
	     }
		/*
		删除已选用户到已选择（右->左）
		*/                    
	     function removeFromSelect(){
	         var s = $('#a_selectUsers').getAllSelect();
			for(i=0;i<s[0].length;i++){            
				$('#a_Users').addOption(s[1][i],s[0][i]);
			}
	    	 $('#a_selectUsers').removeAllSelected();   
	     }
	     
	     //删除选择的记录，将选中的人员放入待选列表，重新安排分桌
	     function removeFromChoosed(){
	     	 //对选择的对象进行组织
	     	 var s = $('#choosedTableUsers').getAllSelect();
			 var memberList = buildMemberList(s);
			 if(memberList.length == 0){
			 	alert('请先选择人员');
			 	return false;
			 }
			 
			 if(confirm("确定对选定的人员重新分桌吗?")){
			 	var url= '${ctx}/admin/pri/meeting/removeUserFromDinnerTable.action?meetingId=${dinner.meetingId}&dinner.id=${dinner.id}&memberstr='+memberList;
			 	$.ajax({
					url: url,
			        type:      'post',      
			        dataType:  'json',
			        success:   callback
				});
			 }
	     }
		
				
		function selectOK(){
			 var tableCode = $('#tabelCode').val();
			 if(tableCode.length == 0){
			 	alert('请填写桌号');
			 	$('#tabelCode').focus();
			 	return false;
			 }
			 if(tableCode.length > 50){
			 	alert('桌号最多50个字');
			 	$('#tabelCode').focus();
			 	return false;
			 }
			 
			 var s = $('#a_selectUsers').getAllOption();
			 var memberList = buildMemberList(s);
			 if(memberList.length == 0){
			 	alert('请为该桌选择人员');
			 	return false;
			 }
			 submit(memberList,tableCode);
		}
		 
		function  buildMemberList(obj){
			var ids = "";
			
			for(i=0;i<obj[0].length;i++){				
				ids += obj[0][i] + ",";
			}
			
			ids = ids.substring(0,ids.length-1);

			return ids;
		}
		
		function submit(memberList,tableCode){
			var url= '${ctx}/admin/pri/meeting/addUserToDinnerTable.action?meetingId=${dinner.meetingId}&dinner.id=${dinner.id}&memberstr='+memberList;
		
			$.ajax({
					url: url,
			        data: {'tableCode':tableCode},
			        type:      'post',      
			        dataType:  'json',
			        success:   callback
			});
		}
		//回调函数
		function callback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){
				//更新未分桌的用户 已分桌的用户到这个页面永远都是空的，因为还需要根据桌号来判断
				//该页面只处理将未分桌的用户添加到分桌信息中
				//$('#tabelCode').val('');
				//更新用户列表信息
			    //$("#a_selectUsers").empty();
				//setSelectOptionData('a_Users',data.waitUserList);
				//setChoosedTableUsersData('choosedTableUsers',data.choosedUserTableList);
				
				
				//2011-12-12 用刷新页面替代用js刷新
				refresh();
			}
		}
		
		//更新待选用户的信息
		function setSelectOptionData(targetId,jsonData){
		   var targetDiv = "#"+targetId;
			var results = eval(jsonData);
			$(targetDiv).empty();
			for (var i = 0; i < results.length; i++) {
				$("<option value='" + results[i].id + "'>" +results[i].mobile+" " + results[i].name+ "</option>").appendTo(targetDiv);
			}
		}
		//更新已分桌的用户信息
		function setChoosedTableUsersData(targetId,jsonData){
		   var targetDiv = "#"+targetId;
			var results = eval(jsonData);
			$(targetDiv).empty();
			for (var i = 0; i < results.length; i++) {
				$("<option value='" + results[i].id + "'>" + results[i].tableCode + " : "+results[i].name + "</option>").appendTo(targetDiv);
			}
		}
		
		function back2ListDinner(){
			window.location.href = "${ctx}/admin/pri/meeting/listMeetingDinner.action?meetingId="+${dinner.meetingId};
		}
		
		function refresh(){
			window.location.href = "${ctx}/admin/pri/meeting/getMeetingDinnerById.action?returnType=choosetable&dinner.id=${dinner.id}";
		}
		
</script>
</body>
</html>