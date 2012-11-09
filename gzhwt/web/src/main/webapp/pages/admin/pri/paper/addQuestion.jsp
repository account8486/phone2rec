<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
<style type="text/css">
	input{
		width:250px;
	}
</style>
${admin_css}                                   
${jquery_js}                                
${jquery_form_js}                                 
${validate_js}                                
${WdatePicker_js}                           
${admin_js}                   
${area_js}        
${util_js}      
</head>
<body>
		
<div class="mainbox">
    <div class="page_title">
    	<s:if test="flag=='edit'">
		<h3>修改题目</h3>
		</s:if>
		<s:else>
		<h3>添加题目</h3>
		</s:else>
	</div>
	<div class="page_form">
	<form id="addPaperForm" action="${ctx}/admin/pri/paper/question_addQuestion.action" method="post" >
	<input   type="hidden""  name="meetingId" id="meet"  value="${param.meetingId }" />
	<input   type="hidden"  name="paperId"  value="${param.paperId }" />
	<fieldset id="add">
       
        <dl class="show">
        	<dt>
            	<label for="type"><font color="red">* </font>题目类型：</label>
            </dt>
            <dd>
            	<select name="type" id="paperType" >
            		<option value="1" ${paper.type==1?"selected":"" }>单选题</option>
            		<option value="2" ${paper.type==2?"selected":"" }>多选题</option>
            		<option value="3" ${paper.type==3?"selected":"" }>简述题</option>
            	</select>
            </dd>
        </dl>
        
        <dl class="show">
        	<dt>
            	<label for="question"><font color="red">* </font>题目标题：</label>
            </dt>
            <dd>
            	<input type="text" name="question"></input>
            	<img  src="${ctx }/images/itemAdd.png" id="addItem" alt="题目选项" style="width:20px;height:20px;" />   
            </dd>
        </dl>
        
         <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>题目选项：</label>
            </dt>
            <dd>
            	<input type="text" name="contents"></input>
            </dd>
        </dl>
        
         <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>题目选项：</label>
            </dt>
            <dd>
            	<input type="text" name="contents"></input>
            </dd>
        </dl>
       
    </fieldset>

    <div class="page_form_sub">
        <a href="javascript:save();"  id="submitBtn" class="btn_common btn_true">保 存</a>    
        <a href="javascript:history.back();"   class="btn_common btn_true">返回</a>    
    </div>

	</form>
	
	</div>
</div>

<script type="text/javascript">
var index=3;
$(function(){
	$("#paper\\.topic").calcWordNum({maxNumber:100,targetid:"meeting_topic_remain"});
	var flag="${flag}"
	if(flag=="edit"){
		$("#addPaperForm").attr("action","${ctx}/admin/pri/paper/paper_updatePaper.action");
		$("#submitBtn").html("修改");
	};
	
	$("#addItem").click(function(){
		if(index>10){
			alert("最多添加10个选项");
			return;
		}
		$("#add").append("<dl><dt><label for='title'><font color='red'>* </font>"+"题目选项"
					+"：</label></dt><dd><input type='text' name='contents'/>"+"<img src='${ctx }/images/itemDelete.png' onClick='deleteItem(this)'   style='width:20px;height:20px;' />"+
					"</dd></dl>");
		index=index+1;
	});
	
	$("#paperType").change(function(){
		if($(this).val()==3){
			$("dl").not(".show").hide();
			$("#addItem").hide();
		}else{
			$("dl").not(".show").show();
			$("#addItem").show();
		}
	});
});
	
function save(){
	var flag=true;
	
	var qTitle=$("input[name=question]").val();
	if(qTitle==null||qTitle==""){
		alert("题目标题不能为空 ");
		return ;
	}
	
	var type=$("#paperType").val();
	var index=0;
	if(parseInt(type)!=3){
		$("input[name=contents]").each(function(){
			var content=$(this).val();
			if(content!=null&&content!=""){
				index=index+1;
			}
		});
		if(index<2){
			flag=false;
			alert("至少填写2个题目选项");
		}
	}
	if(flag){
		$("#addPaperForm").submit();
	}
}

function deleteItem(target){
	$(target).parent().parent().remove();
	index=index-1;
}




</script>
</body>
</html>