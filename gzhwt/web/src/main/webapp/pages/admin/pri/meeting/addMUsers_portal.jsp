<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
<link href="${ctx}/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
${style_css}                                   
${jquery_js}                                 
${jquery_form_js}                       
${jquery_select_js}
</head>
<body>
	<div > 		
		<div>
			<table width="100%" cellspacing="0" class="tbcss">
			  <tr>                
			    <td class="nobg" >  
				    <div>   
				    	<div style="float:left">  
					    	<form action="" method="post" id="frm" name="frm">      
					    		<input   type="hidden" id="meeting.id" name="meeting.id"  value="${meeting.id}" />
					    		归属机构：
					    		<select name="org" id="org" style="width:280px;" onchange="getUserByOrg();">			    	
			                    	<c:forEach var="org" items="${orgList}" varStatus="status">
						    			<option value="${org.id}">${org.name}</option>
						    		</c:forEach>
						    	</select>
						    	<input type="button" class="btncss" onclick="addOrgUser();" value="组织机构人员添加"/>
						    	
						    	<p></p>
						    	<!-- 
						    	姓&nbsp;&nbsp;&nbsp;&nbsp;名：
						    	<input type="text" id="username" name="username" />
					    		<input type="button" class="btncss" onclick="getUser();" value="查询"/>
					    		 -->
					    	</form>   
				    	</div>
				    	<div id="waitingdiv" style="display:none;float:left">
					    	<img src="${ctx}/images/loading.gif"></img>
					    	<font color="red">数据加载中，请等待...</font>
				    	</div>
					</div>
				</td>
			  </tr>			           
			</table>
			<hr></hr>
			<table width="98%" cellspacing="0" style="margin:1px;">
			  <tr>                       
          	    <th id="usertd1" style="width:50%;">      
			    	候选人员
				    <br/>        
			    	<select name="a_Users" id="a_Users" style="width:100%; height:280px;" ondblclick="addToSelect()" multiple="multiple">			    	
			    	</select>
				</th>
				<td class="nobg" id="usertd2" style="width:50%;">    
				          已选人员
				    <br/>
			    	<select name="a_selectUsers" id="a_selectUsers" ondblclick="removeFromSelect();" style="width:100%; height:280px;" multiple="multiple">
			    		<c:forEach var="user" items="${userList}" varStatus="status">
			    			<option value="${user.id}">${user.name}[${user.mobile}]</option>
			    		</c:forEach>
			    	</select>
				</td>
			  </tr> 
			  <tr>                 
			    <td class="nobg" colspan="2" align="center" style="text-align:center;">     
			     	<input type="button" class="btncss" onclick="addToSelect();" value="添加"/>            
			    	<input type="button" class="btncss" onclick="removeFromSelect();" value="移出" />  
			    	<input type="button" class="btncss" onclick="addToSelectAll();" value="全部添加"/>            
			    	<input type="button" class="btncss" onclick="removeFromSelectAll();" value="全部移出"/>	
			    	<input type="button" class="btncss" onclick="selectOK();" value="确定选择"/> 		 
				</td>
			  </tr>				           
			</table>
		</div>
	</div>
	
<script type="text/javascript">

$(document).ready(function() {
	getUserByOrg();
 });
 
		function submit(memberList){
			var url= '${ctx}/pages/admin/pri/meeting/modifyMeeting.action?flag=editMember&meeting.id=${meeting.id}&memberstr='+memberList;
		
			$.getJSON(url, callback);
		}
		//回调函数
		function callback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){
				parent.tb_remove();
				parent.location.href = parent.location.href;
			}
		}
		
		
		/*
		添加备选用户到已选择（左->右）
		*/
		function addToSelect(){			
			var s = $('#a_Users').getAllSelect();
			for(i=0;i<s[0].length;i++){            
				$('#a_selectUsers').addOption(s[1][i],s[0][i]);    
			}
		}
		/*
		添加所有备选用户到已选择（左->右）
		*/
		function addToSelectAll(){
			var s = $('#a_Users').getAllOption();                           
			for(i=0;i<s[0].length;i++){            
				$('#a_selectUsers').addOption(s[1][i],s[0][i]);    
			}
		}
		/*
		删除所有已选用户到已选择（右->左）
		*/
		 function removeFromSelectAll(){
	    	 $('#a_selectUsers').removeAllOption();   
	     }
		/*
		删除已选用户到已选择（右->左）
		*/                    
	     function removeFromSelect(){
	    	 $('#a_selectUsers').removeAllSelected();   
	     }
		
		function getUserByOrg(){
			var url = '${ctx}/admin/pri/user/getUserByOrg.action?org_id='+$("#org").val();
			//alert(url);			
			$.getJSON(url, getUserByOrgCallback);			
		}
		
		//回调函数
		function getUserByOrgCallback(data){
			if (data!=null){   
				if(data.retcode == "0" && data.retdata!=null){
					$('#a_Users').clearAll(); 
					for(i=0;i<data.retdata.length;i++){       
						$('#a_Users').addOption(data.retdata[i].name+"["+data.retdata[i].mobile+"]",data.retdata[i].id); 
					} 
				}
			}
		}
				
		function selectOK(){  
			 var s = $('#a_selectUsers').getAllOption();    
			 var memberList = buildMemberList(s); 
			 submit(memberList);
		}
		 
		function  buildMemberList(obj){                         
			var ids = "";
			
			for(i=0;i<obj[0].length;i++){				
				ids += obj[0][i] + ",";
			}
			
			ids = ids.substring(0,ids.length-1);

			return ids;
		}
		
		function addOrgUser(){
			window.location.href = "${ctx}/admin/pri/user/goAdd.action?from=portal&meeting_id=${meeting.id}&org_id="+$("#org").val();
		}
		
</script>
</body>
</html>