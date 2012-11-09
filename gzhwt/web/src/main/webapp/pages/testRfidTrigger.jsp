<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>模拟测试RFID标签触发</title>
</head>
<body>
<h1>模拟测试RFID标签触发</h1>
<form action="/meeting/admin/base/rfidTagTrigger.action" method="post">
	&nbsp;&nbsp;会议ID：<input type="text" name="meetingId" value="166"/>
	<br/>
	签到事件：<input type="text" name="rfidSignIn.signEvent" value="会议签到"/>
	<br/>
	&nbsp;&nbsp;标签ID：<input type="text" name="rfidSignIn.tagId" value="950001"/>
	<br/>
	
	<input type="submit" value="签到" />
</form>

<br>
<a href="/meeting/admin/base/rfidTagTrigger.action?meetingId=167&rfidSignIn.tagId=950001&rfidSignIn.signEvent=测试签到">测试签到</a>
</body>
</html>