<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>贵州会议信息导出</title>

<style type="text/css">  
body {  
    font-family:SimSun;   
    font-size:16px;  
    font-style:italic;  
    font-weight:bold;  
} 
.title_top 
{
font-size: 22px;
background:#66FF99
}


.title {
	font-size: large
}
</style>

</head>

<body>
<p align="center" class="title_top"> ${meeting.name} </p>

<p class="title">1.会议基本信息</p>
<table width="593" height="265" border="1" cellpadding="0px" cellspacing="0px">

  <tr>
    <td>会议号:</td>
    <td colspan="2">${meeting.id} </td>
  </tr>
  <tr>
    <td width="129">名称：</td>
    <td width="458" colspan="2">${meeting.name} </td>
  </tr>
  <tr>
    <td>主题：</td>
    <td colspan="2">${meeting.topic} </td>
  </tr>
  <tr>
    <td>起始时间:</td>
    <td colspan="2">${meeting.startTime} --- ${meeting.endTime} </td>
  </tr>
  <tr>
    <td>会议主办方：</td>
    <td colspan="2">${meeting.host} </td>
  </tr>

</table>
<p class="">&nbsp;</p>
<p>&nbsp;</p>
<p class="title">2.会议议程信息</p>
<table width="599" height="81" border="1" cellpadding="0px" cellspacing="0px">
  <tr>
    <td width="90">日期</td>
    <td width="106">时间</td>
    <td width="97">议题</td>
    <td width="113">地点</td>
    <td width="159">主持人</td>
  </tr>
  
<#list meetingAgendaList as agenda >
 <tr>
    <td>${agenda.date}</td>
    <td>${agenda.startTime}-${agenda.endTime}</td>
    <td>${agenda.topic}</td>
    <td>${agenda.location}</td>
    <td>${agenda.host}</td>
  </tr>
</#list>
</table>


<p class="">3.会议成员信息</p>
<table width="604" height="84" border="1" cellpadding="0px" cellspacing="0px">
  <tr>
    <td width="90">姓名</td>
    <td width="100">手机号</td>
    <td width="222">邮箱地址</td>
    <td width="182">职务</td>
  </tr>
  
  <#list userList as user>
  <tr>
    <td>${user.name}</td>
    <td>${user.mobile}</td>
    <td>${user.meetingMember.mailbox}</td>
    <td>${user.meetingMember.job}</td>
  </tr>
 </#list>
 
</table>
<p class="title">&nbsp;</p>
<p class="title">4.会议用餐安排</p>
<table width="599" height="81" border="1" cellpadding="0px" cellspacing="0px">
  <tr>
    <td width="90">日期</td>
    <td width="106">时间</td>
    <td width="97">地址</td>
  </tr>
  
  <#list dinnerList as dinner>
  <tr>
    <td>${dinner.dinnerDate}</td>
    <td>${dinner.startTime}-${dinner.endTime}</td>
    <td>${dinner.address}</td>
  </tr>
  </#list>
</table>
<p class="title">&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
</body>
</html>
