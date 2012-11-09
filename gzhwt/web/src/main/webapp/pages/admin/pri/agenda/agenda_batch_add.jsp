<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>会议议程</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	
	${admin_css} 
	${jquery_js} ${jmpopups_js} ${util_js}  ${WdatePicker_js} ${jquery_form_js}
	
	<style type="text/css">
		.page_form input[type="text"], input[type="password"], input[type="select"], input[type="search"], input[type="file"], textarea, select{
			margin:0;
		}
		.page_datalist, .page_datalist th, .page_datalist td{
			padding:5px;
		}
		
	</style>
	
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title"><h3>会议议程 -- ${CURRENT_MEETING}</h3></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="议程管理" link="${ctx}/admin/pri/agenda/agendaList.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="批量添加" style="padding:10px;" selected="true">
		<div>
	        <form id="batchAddForm" action="${ctx}/admin/pri/agenda/batchAdd.action"  method="post">
		<table class="page_datalist" id="batch_add_table" >
	    	<thead>
				<tr>
					<th scope="col" width="100px">日期</th>
					<th scope="col" width="60px">开始时间</th>
					<th scope="col" width="60px">结束时间</th>
					<th scope="col" width="100px">主持人</th>
					<th scope="col" width="100px">议题</th>
					<th scope="col" >地点</th>
					<th scope="col" >内容</th>
					<th scope="col" width="100px">操作</th>
				</tr>	    	
	        </thead>
	        <tbody class="page_form">
	         <tr>
	         
	          <!-- 日期 -->
              <td align="left"  >
		         <input type="hidden" name="agendaList[0].meetingId" value="${meetingId }">
				 <input id="date" type="text"
									name="agendaList[0].date" class="Wdate" 
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
									value=""
									readonly="readonly" />
	         </td>
	            	
	         <!-- 开始时间 -->	
	          <td align="left">
	            			<input id="startTime" 
							type="text" name="agendaList[0].startTime" class="Wdate"
							value=""
							onfocus="WdatePicker({dateFmt:'HH:mm'})"
							readonly="readonly"  />
	         </td>
	            		
	         <!-- 结束时间 -->	 		
	         <td align="left">
	            				<input id="endTime" type="text"
							name="agendaList[0].endTime" class="Wdate"  
							value=""
							onfocus="WdatePicker({isShowClear:false,dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'startTime\')||\'2020-10-01\'}'})"
							readonly="readonly"  />
	        </td>
	          
	          <!-- 主持人 --> 
	       <td align="left">  
	       		<input id="host" type="text" class="textInput" name="agendaList[0].host"
							 maxlength="30"/>	
         	</td>	
	          <!-- 议题 --> 
	       <td align="left">  
	       		<input id="topic" type="text" name="agendaList[0].topic"
							value="" maxlength="128" />	
         	</td>	
          <!-- 地点 -->
		  <td align="left">
		  <input id="location" type="text" name="agendaList[0].location"
							value=""  maxlength="128" />
		  </td>
			
		<!-- 内容 -->
		<td align="left">
		<input id="description" type="text" name="agendaList[0].description"
							value=""  maxlength="1000" />
		<!--<textarea  id="description" rows="1000" cols="40"
								name="description" style="height:80px;"></textarea>	
		--></td>
				     
		 <td align="center"  style="width:100px">
				    <a id="addEl" href="javascript:void(0);" onClick="insertRow(this);">添加</a>
				    <a href="javascript:void(0);" onClick="delCurrentRow(this);">删除</a>
		 </td>
		 
        </tr>
        
	  </tbody>
	    </table>
	    </form>
	    <div class="page_form_sub">
				<div style="margin-bottom: 10px; margin-top: 10px;padding-left: 200px;" align="center">
					<a href="#" id="agendaAdd" class="btn_common btn_true">保存</a>
					<input type="hidden" value="${meetingId}" id="meetingId" />
				</div>
			</div>
		</div>		
		</div>
		<div title="议程编辑" link="${ctx}/admin/pri/agenda/agendaAdd.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="议程导入" link="${ctx}/admin/pri/agenda/agendaImport.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
	
	<script type="text/javascript">
	/**
	*删除当前行
	*/
	function delCurrentRow(obj){
		 var tr = $(obj).parent().parent();
		 if($(tr).parent().find("tr").length == 1){
			 alert("最后一条不能删除！");
			 return;
		 }	
		 if( $(tr).parent().find("tr").length > 1 && confirm("确定删除这条记录?")){
			 $(tr).remove();
			 return ;
		 }
		 
	}
	
	
	var idx = 0;
	/**
	*复制当前行并插入到表格中去
	*/
	function insertRow(obj){
		var tr = $(obj).parent().parent();
		if(validateMyForm($(tr))){
			//深度复制一个tr,包括事件绑定属性
			var $clone = $(tr).clone(true);
			//找到当前List的索引值
			//var idx = $(tr).parent().children(":last").children(":first").html().match(/([[\d]+])/gi)[0].replace("[","").replace("]","");
			idx++;
			//找到TR下的所有input进行name的替换
			$($clone).find("td>input,select").each(function(i, elem){ 
			    $(elem).attr('name',$(elem).attr('name').replace(/([[\d]+])/gi,'['+idx+']'));
				//alert($(elem).attr('name'));
			});
			//保留select的值
			var selects = $(tr).find("select");
	        $(selects).each(function(i) {
	             var select = this;
	             $($clone).find("select").eq(i).val($(select).val());
	        });
			//添加到tr的后面
			$($clone).appendTo($(tr).parent());
		}
	}
	
	//验证form
	function validateMyForm(oldTr){
		//通过JQUERY选择器来获取对应的控件的值
		var date= $("#date", oldTr).val();
		if(date==''){
			alert('请选择日期！');
			return false;
		}
		var startTime= $("#startTime", oldTr).val();
		if(startTime==''){
			alert('请选择开始时间！');
			return false;
		}
		var endTime = $("#endTime", oldTr).val();
		if(endTime==''){
			alert('请选择结束时间！');
			return false;
		}
		return true;
	}
	
	$(document).ready(
		function() {
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
			
			$('#agendaAdd').click(function(){
				//对每一行的数据进行验证
				var trs = $('#batchAddForm').find("tr");
				for(var i = 1;i<trs.length;i++){
					if(!validateMyForm($(trs[i]))){
						return false;
					}
				}
				
				var url = '${ctx}/admin/pri/agenda/batchAdd.action';
				var options = { 
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'         
			    };
				$('#batchAddForm').ajaxSubmit(options);
			});
			
			function callback(data){
				alert(data.retmsg);
			}
		}
	);
	</script>
</body>
</html>