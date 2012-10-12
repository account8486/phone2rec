<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>栏目新增与编辑</title>
	${admin_css}                                   
	${jquery_js}                                
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}                           
	${admin_js}                         
	${jmpopups_js}   
	${util_js}      
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	
	<script charset="utf-8" src="${ctx}/kindeditor/kindeditor.js"></script>
    <script charset="utf-8" src="${ctx}/kindeditor/lang/zh_CN.js"></script>
    
<script>
	$(document).ready(function() {
		$(".easyui-tabs").tabs({  
			onSelect:function(title){  
				var tab = $(this).tabs("getTab", title); 
				var href = tab.attr("link");
				if (href) {
					location.href = href;
					showLoading(title);
					return false;
				}
			}  
		});
		
		
	});
	
	  var editor;
	  KindEditor.ready(function(K) {
	            editor = K.create('#content', {
					//uploadJson : '${ctx}/admin/ke/ke_uploadAttached.action',
					uploadJson : '${ctx}/kindeditor/jsp/upload_json.jsp',
					fileManagerJson : '${ctx}/kindeditor/jsp/file_manager_json.jsp',
					allowFileManager : true,
					afterCreate : function() {
						/**
						var self = this;
						K.ctrl(document, 13, function() {
							self.sync();
							document.forms['example'].submit();
						});
						K.ctrl(self.edit.doc, 13, function() {
							self.sync();
							document.forms['example'].submit();
						});*/
						
					}
				});
	    });
	



</script>
</head>
<body>
	<div class="page_title">
		<h3></h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">	
	
	
	<div title="我已发送列表" link="${ctx}/admin/pri/mail/mail_getMailList.action" style="padding:10px;"></div>
	<div title="发送邮件" selected="true"  style="padding:10px;"></div>
	</div>
	
	<div class="page_form">
	<form id="addFrm"  method="post">
	
	    <fieldset>
	        <legend>发送界面</legend>
	        <%--
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>定时邮件：</label>
	            </dt>
	            <dd style="width:40%"> 
	            	
	               <input type="checkbox" />
	               <input id="publishTime" type="text" name="" value=""    class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm' })" readonly="readonly"/>       	
	            </dd>
	        </dl>
	         --%>
	         <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>收件人：</label>
	            </dt>
	            <dd>
	            	<input type="text"  class="half" id="title" name="mail.mailTo" value="guoxu@wondertek.com.cn" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>
	        
	            
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>抄送：</label>
	            </dt>
	            <dd>
	            	<input type="text"  class="half" id="title" name="mail.mailCc" value="guoxu@wondertek.com.cn" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>
	        
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>主题：</label>
	            </dt>
	            <dd>
	            	<input type="text"  class="full" id="title" name="mail.mailSubject" value="${mail.title}" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>
	        
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font></label>
	            </dt>
	            <dd>
	            	<textarea id="content" class="medium" style="width:700px;height:350px;" name="mail.mailText" tabindex="2"></textarea>
	            </dd>
	        </dl>	
	        
	    </fieldset>
	    
	    
	    <div class="page_form_sub">
	    	  <a href="#" onclick="saveOrUpdate()"  class="btn_common btn_true">保 存</a>　
	    </div>
	   
	</form>
	</div>
	<script type="text/javascript">
		// 新增校验
		
		$().ready(function() {
			
		});

		
		/**
		*进行表单的ajax请求保存
		*/
		function saveOrUpdate(){
			//alert(editor.html());
			//alert($("#content").val());
			//$("#addFrm").submit();
			
			editor.sync();
			var url="${ctx}/admin/pri/mail/mail_addMail.action";
			var options ={ 
					//beforeSubmit: validate,
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'
			    };
			
			$('#addFrm').ajaxSubmit(options);
			$("#addUserBtn").attr("disabled","disabled");
		}
		
		
	    function callback(data){
	    	alert(data.retMsg);
	    	var url="${ctx}/admin/pri/mail/mail_getMailList.action";
	    	document.location=url;
	    }
		
		
	
		
		
		
	
	</script>	
</body>
</html>