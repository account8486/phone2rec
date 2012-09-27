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
	            editor = K.create('#content');
	    });
	



</script>
</head>
<body>
	<div class="page_title">
		<h3></h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">	
	
	
	<div title="文章列表" link="${ctx}/admin/pri/article/arti_articleList.action" style="padding:10px;"></div>
	<c:if test="${not empty article.id}">
	<div title="编辑文章" selected="true"  style="padding:10px;"></div>
	</c:if>
	
    <c:if test="${empty article.id}">
	<div title="添加文章" selected="true"  style="padding:10px;"></div>
	</c:if>
	</div>
	
	<div class="page_form">
	<form id="addFrm"  method="post">
		<input type="hidden" name="article.id" id="id" value="${article.id}" />
	    <fieldset>
	        <legend>基本信息</legend>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>所属栏目：</label>
	            </dt>
	            <dd style="width:40%">
	            	<select name="article.chanId" id="chanId">
	            	<c:forEach var="chan" items="${channels}" varStatus="status">
	            		<option value="${chan.id}"> ${chan.chanName}</option>
	            	</c:forEach>
	            	</select>	 
	            	
	            	<input id="publishTime" type="text" name="article.publishTime"  value="${article.publishTime}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm' })" readonly="readonly"/>       	
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>标题：</label>
	            </dt>
	            <dd>
	            	<input type="text"  class="half" id="title" name="article.title" value="${article.title}" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>
	        
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>副标题：</label>
	            </dt>
	            <dd>
	            	<input type="text"  class="half"   id="subtitle" name="article.subtitle" tabindex="2" maxlength="30" value="${article.subtitle}"></input>	 
	            </dd>
	        </dl>	
	        
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>文章内容：</label>
	            </dt>
	            <dd>
	            	<textarea id="content" class="medium" style="width:700px;height:350px;" name="article.content" tabindex="2">${article.content}</textarea>
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
			var url="${ctx}/admin/pri/article/arti_saveOrUpdateArticle.action";
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
	    	var url="${ctx}/admin/pri/article/arti_articleList.action";
	    	document.location=url;
	    }
		
		
	
		
		
		
	
	</script>	
</body>
</html>