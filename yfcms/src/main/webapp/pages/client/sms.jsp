<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type">
	<meta content="width=device-width; initial-scale=1.0; maximum-scale=1.0;" name="viewport" id="viewport">
	<meta content="telephone=no" name="format-detection">
    <link href="${ctx}/css/sms.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<div class="mui_page">
		<div class="mui_cols">
			<div class="mui_col_single">
				<form method="post" name="postform" id="postform"
					action="#">
					<div class="compose">
						<div class="compose_inner">
							<div un="suggest">
								
								<!-- 输入查询框 -->
								<div class="mui_maininput">
									<input type="text" id="showto" name="showto" style="border:solid 1px orange;height:35px;line-height:35px;width:50%" autocomplete="off" maxlength="20" placeholder="输入姓名快速查找">
									<input type="button" id="query" style="float:right;margin-right:30px;width:60px;height:35px;" value="查询" class="mui_btn m_btn_green">
									<span id="sugBtnto" class="mui_inputoperation mui_rounded5 mui_ico_plus" style="margin-top:12px;"></span>
								</div>
								
								<div class="mui_inputline">
									<label for="to" class="mui_inputitem">
										收信人：
									</label>
									<div class="mui_inputcontent mui_inputcontent_withcontact">
										<div id="addrActiveto" style="width: 100%">
											
											<div id="addrInputto">
												<!-- 显示选择的用户信息 -->
											</div>
												
										</div>
									</div>
								</div>
								<div id="addrEditto" class="mui_contactinfo" style="display:none">
									<!-- 删除显示用户信息 -->
									
								</div>
								<div style="display: none;" class="mui_suggestion"
									id="toSugList" >
									<div id="userList" class="mui_ul_singleline mui_ul_nobgcolor">
										<!-- 用户列表 -->
									</div>
								</div>
							</div>
							<!-- suggest end -->

							<div class="mui_maininput">
								<fieldset>
									<textarea id="content" name="content"></textarea>
								</fieldset>
							</div>
						</div>
					</div>
				
					<div class="navbar_bot">
						<ul>
							<li>
								<input type="button" id="sendSms" style="width:80px;text-align:center;height:40px;float: right;margin-bottom:10px;margin-right:12px;" value="发送" class="mui_btn m_btn_green">
							</li>
						</ul>
					</div>
				</form>
			</div>
			
		</div>
	</div>
	
	${jquery_js }
	${map_js }
	
	<script type="text/javascript">
		$("#sugBtnto").click(function(){
			if($(this).hasClass('mui_ico_plus')){
				//显示建议的用户列表
				showSuggest();
			}else{
				//隐藏建议的用户列表
				hideSuggest();
			}
		});
		
		//显示建议的用户列表
		function showSuggest(){
			$("#sugBtnto").removeClass('mui_ico_plus').addClass('mui_ico_minus');
			$("#toSugList").css('display','block');
			if(currentPage == 0){
				moreSuggest();
			}
		}
		
		//隐藏建议的用户列表
		function hideSuggest(){
			$("#sugBtnto").removeClass('mui_ico_minus').addClass('mui_ico_plus');
			$("#toSugList").css('display','none');
		}
		
		//显示更多建议的用户列表
		function moreSuggest(){
			queryUser();
		}
		
		var currentPage = 0;
		var pageSize = 10;
		function queryUser(name){
			$.ajax({
				url: '${ctx}/client/pri/getMeetingUser.action',
				data: {"imei":"${imei}","meetingNo":"${meetingId}","name":name,"pageSize":pageSize,"currentPage":++currentPage},
		        type: 'post',      
		        dataType: 'json',
		        success: callback
			});
		}
		
		//处理返回的用户信息
		function callback(data){
			if(data.retcode == "0"){
				currentPage = data.currentPage;
				//移除点击更多
				$('#moreUser').remove();
				var userListHtml = $("#userList").html();
				
				if(currentPage == 1 && data.totalPage > 0 && data.userList.length > 0){
					userListHtml += "<div class=\"mui_li\"><a href=\"javascript:;\" onclick=\"addUser(-1,'全部参会人员','&nbsp;');\">全部参会人员</a></div>";
				} 
				
				//组织用户信息
				var users = eval(data.userList);
				for(var i = 0; i < users.length; i++){
					userListHtml += "<div class=\"mui_li\"><a href=\"javascript:;\" onclick=\"addUser("+users[i].id+",'"+users[i].name+"','"+users[i].mobile+"');\">"+users[i].name+"&nbsp;&nbsp;"+users[i].mobile+"</a></div>";
				}
				//判断是否显示更多按钮
				if(data.currentPage < data.totalPage){
					userListHtml += "<div id=\"moreUser\" class=\"mui_li\"><a href=\"javascript:;\" onclick=\"moreSuggest();\">点击查看更多</a></div>";
				}
				
				if(users.length == 0){
					userListHtml = "<div class=\"mui_li\"><a href=\"javascript:;\" >没有查询到用户信息</a></div>";
				}
				//更新用户信息
				$("#userList").html(userListHtml);
			}	
		}
		
		//已选择的用户全局map
		var addedUsers = new Map();
		
		var User = function(id, name,mobile) {   
		  this.id = id;   
		  this.name = name;   
		  this.mobile = mobile;   
		 }
		//添加用户到已选择列表
		function addUser(uid,uname,mobile){
			var receiverHtml = $("#addrInputto").html();
			var isAdded = addedUsers.contains(uid);
			if(!isAdded){
				var user = new User(uid,uname,mobile)
				addedUsers.put(uid,user);
				receiverHtml += "<span class=\"mui_contactblock\" id=\""+uid+"\" onclick=\"show("+uid+",'"+uname+"','"+mobile+"');\">"+uname+"</span>";
			}
			$("#addrInputto").html(receiverHtml);
		}
		
		//显示用户详情进行编辑
		function show(uid,uname,mobile){
			var infoHtml = "<ul class=\"mui_contactbtn\"><li><a class=\"mui_btn_nav\" onclick=\"removeUser("+uid+");\">删除</a></li><li><a class=\"mui_btn_nav\" onclick=\"hideInfo();\">取消</a></li></ul>"
							+"<ul class=\"mui_contactname\"><li>"+uname+"</li><li>"+mobile+"</li></ul>";	
			$("#addrEditto").html(infoHtml);
			$("#addrEditto").css('display','block');
		}
		
		//隐藏删除用户的div
		function hideInfo(){
			$("#addrEditto").css('display','none');
		}
		
		//删除用户
		function removeUser(uid){
			$("#"+uid).remove();
			addedUsers.remove(uid);
			hideInfo();
		}
		
		//提示框的查询
		$('#query').click(function(){
			currentPage = 0;
			$("#userList").html('');
			queryUser($("#showto").val());
			showSuggest();
		});
		
		$("#sendSms").click(function(){
			//校验填写的短信内容
			var userIds = addedUsers.getKeys();
			if(userIds.length == 0){
				alert("请选择收信人");
				return false;
			}
			var content = $.trim($("#content").val());
			if(content.length==0){
				alert("请填写短信内容");
				return false;
			}
			
			var isSelectAll = "0";
			if(userIds.indexOf("-1")> -1){
				isSelectAll = "1";
			}			
			
			$.ajax({
				url: '${ctx}/client/pri/meeting/checkSmsSendStatus.action',
				data: {"imei":"${imei}","meetingId":"${meetingId}","recieverIds":userIds,"isSelectAll":isSelectAll},
		        type: 'post',      
		        dataType: 'json',
		        async: false,
		        success: checkStatusCallback(userIds,content)
			});
			
		});
		
		function checkStatusCallback(userIds,content){
			return function(data){
				if(data.sendStatus == false){
					alert(data.message);
					return ;
				}else{
					$.ajax({
						url: '${ctx}/client/pri/meeting/sendSms.action',
						data: {"imei":"${imei}","meetingId":"${meetingId}","userIds":userIds,"content":content},
				        type: 'post',      
				        dataType: 'json',
				        success: smsCallback
					});
				}
			}
		}
		
		function smsCallback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){
				$("#content").val('');
				hideSuggest();
			}	
		}
	</script>
</body>
</html>