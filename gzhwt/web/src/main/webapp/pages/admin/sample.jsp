<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>页面公共方法参考</title>              
${jquery_js}   
<script src="${ctx}/js/syntax/jquery.syntaxhighlighter.min.js"></script>    
<link type="text/css" rel="stylesheet" href="${ctx}/css/sample.css"></link>   
</head>
<body>
		
<div class="mainbox">
    <div class="page_title">
		<h3>WonderTeck Frame Samples</h3>	
	
	</div>
	<div class="page_form">
	
	<fieldset>
        <legend>WD:SELECT TAG使用</legend>

        <dl >
            <dd>
            
            	<pre class="language-javascript">
//就餐类型:
&lt;wd:select id="meeting_type1"  type="dinner_type" &gt;&lt;/wd:radio&gt;	
<wd:select id="meeting_type1" type="dinner_type"></wd:select>     


//选中默认值:
&lt;wd:select id="meeting_type2"  type="dinner_type" selectedValue="3" &gt;&lt;/wd:radio&gt;	
<wd:select id="meeting_type2"  type="dinner_type" selectedValue="3"></wd:select>   

            	
//onchange事件:
&lt;wd:select id="meeting_type3"  type="dinner_type" onchange="tagOnChange()" &gt;&lt;/wd:radio&gt;	
<wd:select id="meeting_type3"  type="dinner_type" onchange="tagOnChange()"></wd:select>
<input type="text" style="width:300px;" id = "input.meeting.type3" readonly></input>         		
            	</pre>
            	
            </dd>
        </dl>
    </fieldset>
	
	<fieldset>
        <legend>WD:RADIO TAG使用</legend>

        <dl >
            <dd>
            	<pre class="language-javascript">
//就餐类型：
&lt;wd:radio name="radio.meeting.type1" type="dinner_type"  &gt;&lt;/wd:radio&gt;
<wd:radio name="radio.meeting.type1" type="dinner_type"></wd:radio>

            	
//选中默认值:
&lt;wd:radio name="radio.meeting.type2" type="dinner_type" checkedValue="3" &gt;&lt;/wd:radio&gt;	
<wd:radio name="radio.meeting.type2" type="dinner_type" checkedValue="3"></wd:radio>
<input type="text" style="width:300px;" id = "input.radio.meeting.type2" readonly></input>            		
            	</pre>
            </dd>
        </dl>
        
    </fieldset>
    
	<fieldset>
        <legend>WD:CHECKBOX TAG使用</legend>

        <dl >
        	
            <dd>
            	<pre  class="language-javascript">
//就餐类型:
&lt;wd:checkbox name="checkbox.meeting.type1" type="dinner_type" &gt;&lt;/wd:checkbox&gt;
<wd:checkbox name="checkbox.meeting.type1" type="dinner_type"></wd:checkbox>
            	
            	
//选中默认值：
&lt;wd:checkbox name="checkbox.meeting.type2" type="dinner_type" checkedValue="2,3,5"&gt;&lt;/wd:checkbox&gt;
<wd:checkbox name="checkbox.meeting.type2" type="dinner_type" checkedValue="2,3,5"></wd:checkbox>
            	</pre>	
            </dd>
        </dl>
        
    </fieldset>
    
   	<fieldset>
        <legend>WD:TRANSLATE TAG使用</legend>

        <dl >
        	
            <dd>
            	<pre  class="language-javascript">
//菜单访问类型:
&lt;wd:translate type="terminal_type" value="CLIENT" &gt;&lt;/wd:translate&gt;
翻译前：CLIENT；翻译后：<wd:translate type="terminal_type" value="CLIENT"></wd:translate>
            	</pre>	
            </dd>
        </dl>
        
    </fieldset>
     
	<fieldset>
        <legend>WD:CUSTOM TAG使用</legend>
        <dl >        	
            <dd>
            	<pre  class="language-javascript">    
/*
type：用来定义标签显示类型，目前有select、radio、checkbox、translate四种
entity：数据库表名，用来定义从哪个表中获取数据
entityValue：entity表中的字段，用来填充input控件的value值
entityName：entity表中的字段，用来填充input控件的text值
condition：可自定义where条件，用于过滤结果集
*/        	
//获取gift礼品表中礼品id和name
&lt;wd:custom name="custom.meeting.type" type="select" entity="gift" entityValue="id" entityName="name"&gt;&lt;/wd:custom&gt;
<wd:custom name="custom.meeting.type" type="select" entity="gift" entityValue="id" entityName="name"></wd:custom>
          
            	
//获取base_menu表中手机客户端的基础菜单列表
&lt;wd:custom name="custom.meeting.type2" type="checkbox" entity="base_menu" entityValue="id" entityName="name" condition="where terminal_type='CLIENT' order by type desc" selectedValue="1,2,4,9"&gt;&lt;/wd:custom&gt;
<wd:custom name="custom.meeting.type2" type="checkbox" entity="base_menu" entityValue="id" entityName="name" condition="where terminal_type='CLIENT' order by type desc" selectedValue="1,2,4,9"></wd:custom>


//获取admin_role表中id和name，默认选中会务人员
&lt;wd:custom name="custom.meeting.type3" type="radio" entity="admin_role" entityValue="id" entityName="role_name" selectedValue="4"&gt;&lt;/wd:custom&gt;
<wd:custom name="custom.meeting.type3" type="radio" entity="admin_role" entityValue="id" entityName="role_name" selectedValue="4"></wd:custom>


//根据会议类型ID，得到会议类型名称，如果表中数据量较大，则加上condition条件可提高查询效率
&lt;wd:custom name="custom.meeting.type4" type="translate" entity="meeting_type" entityValue="id" entityName="name" selectedValue="2" condition="where id=2"&gt;&lt;/wd:custom&gt;
翻译前：2；翻译后：<wd:custom  type="translate" entity="meeting_type" entityValue="id" entityName="name" selectedValue="2" condition="where id=2"></wd:custom>
            	</pre>	
            </dd>
        </dl>
        
    </fieldset>
    
	</div>
</div>
	
<script type="text/javascript">

$().ready(function() {
	
	initSyntax();
	
	$("input[name='radio.meeting.type2']").click(function(){
		var s = "您选中了："+$(this).val()+"；扩展属性值extVal为:"+$(this).attr("extVal");
		$("input[id='input.radio.meeting.type2']").attr("value",s);
	});
});

function tagOnChange(){
	var s = "您选中了："+$("#meeting_type3 option:selected").text()+"；扩展属性值extVal为:"+$("#meeting_type3 option:selected").attr("extVal");
	$("input[id='input.meeting.type3']").attr("value",s);
}

function initSyntax(){
	$.SyntaxHighlighter.init(
		{
			'prettifyBaseUrl': '${ctx}/js/syntax/prettify',
			'baseUrl': '${ctx}/js/syntax'
		}
	);
}

</script>
</body>
</html>