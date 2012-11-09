<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.wondertek.meeting.model.Meeting" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title>DEMO</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<style type="text/css">
body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td,hr,button,article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{margin: 0;padding: 0;}table{border-collapse: collapse;border-spacing: 0;}th{text-align: inherit;}iframe{display: block;}fieldset,img{border: 0;}address,caption,cite,code,dfn,em,strong,th,var{font-style: normal;font-weight: normal;}ol,ul{list-style: none;}aption,th{text-align: left;}h1,h2,h3,h4,h5,h6{font-size: 100%;font-weight: normal;}q: before,q: after{content: '';}abbr,acronym{border: 0;font-variant: normal;}sup{vertical-align: text-top;}sub{vertical-align: text-bottom;}input,textarea,select{font-family: inherit;font-size: inherit;font-weight: inherit;}input,textarea,select{*font-size: 100%;}legend{color: #000;}: focus{outline: 0;}html{overflow-y: scroll;overflow-x: auto;color: #000;background: #fff;}
body,div,table,form,p,select,input,textarea,option,a { font-family: Verdana, Arial, sans-serif; line-height: 1.5; font-size: 18px; color: #1480b5;}
body { text-align:center; background:#fff;}
div,table { text-align:left;}

a { text-decoration:none;}
a:hover { text-decoration:none; color:#39f;}

.huiyi_detail {}
.huiyi_detail h3 { background:url(../../images/cindex/title_bg_2.jpg) top center repeat-x; height:54px; text-align:center;}
.huiyi_detail h3 div.b2 {  background:url(../../images/cindex/title_bg.jpg) top center no-repeat; display:block; height:54px; line-height:54px; font-weight:bold; font-size:22px; text-align:center; overflow:hidden; margin:0 auto;}
.huiyi_detail .time_bg { background:url(../../images/cindex/time_bg.jpg) top center repeat-x; margin:10px;}
.huiyi_detail .time_bg_l {  background:url(../../images/cindex/time_bg_l.jpg) top left no-repeat; height:105px; overflow:hidden;}
.huiyi_detail .time_bg_r {  background:url(../../images/cindex/time_bg_r.jpg) top right no-repeat; height:105px; overflow:hidden; text-align:center; padding-top:15px;}
.huiyi_detail .time_bg_r p { padding-left:30px; height:26px; overflow:hidden; margin:5px 10px; text-align:left; display:inline-block;}
.huiyi_detail .time_bg_r .start { background:url(../../images/cindex/time_ico.png) 0px 0px no-repeat;}
.huiyi_detail .time_bg_r .end { background:url(../../images/cindex/time_ico.png) 0px -26px no-repeat;}
.huiyi_detail hr { margin:0 10px 5px; clear:both; }
.huiyi_detail .cont { margin:0px 10px; color:#333; line-height:2; clear:both;}

.local_area { margin:0px 10px 5px;}
.local_area .label { width:50px; vertical-align:top;}
.local_area .detail { }

.demo_table {}
.demo_table td { text-align:center; line-height:54px; font-size:22px; font-weight:bolder; text-shadow:0px 1px 0px #fff;}

</style>
</head>
<body>


<!-- 会议内容 -->
<%
	if(request.getAttribute("meeting")!=null){
		Map<String,Object> meetingMap=(HashMap<String,Object>)request.getAttribute("meeting");
%>	
<!-- 
<div style="width:480px;height:100px; background:#f30;">
	sdf
</div> -->

<div class="huiyi_detail">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="background:url(../../images/cindex/title_bg_2.jpg) bottom center repeat-x;">
      <tr>
        <td style="line-height:45px; text-align:center; background:url(../../images/cindex/title_bg.jpg) bottom center no-repeat; font-size:20px; font-weight:bold; padding:2px 5px;">
			<%=meetingMap.get("name")%>
		</td>
      </tr>
    </table>
    <script type="text/javascript">

	var speed=50 
	marquePic2.innerHTML=marquePic1.innerHTML 
	function Marquee(){ 
	if(demo.scrollLeft>=marquePic1.scrollWidth){ 
	demo.scrollLeft=0 
	}else{ 
	demo.scrollLeft++ 
	} 
	} 
	var MyMar=setInterval(Marquee,speed) 
	demo.onmouseover=function() {clearInterval(MyMar)} 
	demo.onmouseout=function() {MyMar=setInterval(Marquee,speed)} 
	
	</script>
	

     <script>
        var width=window.screen.width;
       // alert(width);
        var marquePic1Width=document.getElementById("marquePic1").width;
   
      //  alert(width+','+marquePic1Width);
        
        document.getElementById('demo').style.width=width+100;
        //document.getElementById('demo_table').style.width=800;
        
        </script>
        

     
	<div class="time_bg">
    	<div class="time_bg_l">
			<div class="time_bg_r">
                <p class="start">开始时间:<span><%=meetingMap.get("startTime") %></span></p>
                <p class="end">结束时间:<span><%=meetingMap.get("endTime") %></span></p>
            </div>
        </div>
    </div>
    
    <table class="local_area">
    	<tr>
    		<td  class="label">地点:</td>
    		<td  class="detail"><%=meetingMap.get("location") %></td>
    	</tr>
    </table>
    
    
    <hr size="1" noshade>
    <div class="cont">
     <%=(String.valueOf(meetingMap.get("topic"))).replace("\n","<br/>") %>
    </div>
</div>
	<%
	}
	%>


</body>
</html>