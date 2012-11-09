<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<head>
<style type="text/css">
body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend,  p, blockquote, th, td {
    margin: 0;
    padding: 0;
}

fieldset, img {
    border: medium none;
}
caption, cite, code, dfn, th, var, em {
    font-style: normal;
    font-weight: normal;
}
ul, ol {
    list-style: none outside none;
}

table {
    border-collapse: collapse;
    border-spacing: 0;
    font-size: inherit;
}
body {
    background: none repeat scroll 0 0 #f4f4f6;
    color: #333333;
    padding: 5px 0;
}
h1, h2, h3, h4, h5, h6 {
    font-size: 100%;
    font-weight: normal;
}
.clearfix:after {
    clear: both;
    content: " ";
    display: block;
    height: 0;
}

a {
    color: #094C9F;
    text-decoration: none;
}
a:hover {
    color: #FF0000;
    text-decoration: underline;
}


.mod_today {
    overflow: hidden;
    margin-bottom: 5px;
}
.mod_today .m_left {
    /*background: url("../images/weather/weather_today_bg.gif") repeat scroll 0 0 transparent;*/
    background: #208ec3;
    color: #FFFFFF;
    display: inline;
    float: left;
    font: 14px/28px 'Microsoft YaHei';
    height: 48%;
    margin: 0 0 0 0;
    overflow: hidden;
    padding: 3px;
    width: 100%;
}
.mod_today h5 {
    font-family: 'Microsoft YaHei',"黑体",sans-serif;
    font-size: 60px;
    margin: 40px 10px 0;
}
.mod_today .detail {
    font-size: 36px;
    /*margin-top: -45px;*/
    position: relative;
    text-align: center;
}
.mod_today .detail .desc {
    margin-top: 20px;
}

.mod_today .detail .fs_30 {
    margin-right: 10px;
}
.icon_weather {
    height: 180px;
    margin: auto;
    text-align: center;
    width: 180px;
}
.mod_today .day {
    float: left;
    width: 100%;
}
.fs_30 {
    font-size: 60px;
}

.weather_list {
    margin: 0px;
}
.weather_02 {
    background: #ffffff url("../images/weather/weather_bg.gif") repeat-x top ;
    border:1px solid #16648b;
    color: #16648b;
    float: left;
    margin: 0;
    padding: 3px;
    width: 49%;
    height:48%;
}

.weather_03 {
    background: #ffffff url("../images/weather/weather_bg.gif") repeat-x top ;
    border:1px solid #16648b;
    color: #16648b;
    float: right;
    margin: 0;
    padding: 3px;
    width: 49%;
    height:48%;
}
.weather_02 .mod_03, .weather_03 .mod_03 {
    float: left;
    font-size: 30px;
    font-family: 'Microsoft YaHei';
    text-align: center;
    width:100%;
}
.weather_02 .detail, .weather_03 .detail {
    text-align: center;
}
.icon_mid_weather {
    height: 78px;
    margin: auto;
    width: 78px;
}
.mod_03 h5 {
    font-size: 14px;
    margin-bottom: 5px;
}
.mod_03 ul {
    margin-top: -20px;
}
.mod_03 ul {
    margin-top: -20px;
}


.weather_02 h4, .weather_03 h4 {
    font: 40px/60px 'Microsoft YaHei',"黑体",sans-serif;
    margin: 20px 0 15px;
    text-align: center;
}
</style>
</head>
<body>
<div style="position: relative;" class="bd">
		<!-- 右侧主内容 begin -->
        <!-- 天气预报 -->
        <div id="tab_01_ctn1" class="tab_ctn">
			<div class="tab_bd">
				<div class="mod_today clearfix">
					<div class="m_left">
						<div style="position: relative;" class="day">
							<h5>${weather.areaName} ${weather.forecastAreaName}</h5>
							<div style="background: url(${weather.date1.icon1}) 0 0  no-repeat;_background:none;_FILTER: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${weather.date1.icon1}', sizingMethod='crop')" class="icon_weather" title="多云"></div>
							<ul class="detail">
								<li><span class="fs_30 tpte">${weather.date1.temperature}</span> </li>
								<li class="desc">${weather.date1.weather}</li>
								<li class="desc">${weather.date1.windPowerAndDirection}</li>
							</ul>
						</div>
					</div>
				</div>
				
                		<div class="weather_list clearfix">
					<div class="weather_02">
						<h4>${weather.date2.weather}</h4>
						<div class="mod_03">
                                  <div style="background: url(${weather.date2.icon1}) 0 0  no-repeat;_background:none;_FILTER: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${weather.date1.icon1}', sizingMethod='scale')" class="icon_weather" ></div>
							<ul class="detail"> 
								<li class="tpte">${weather.date2.temperature}</li>
								<li class="tpte">${weather.date2.windPowerAndDirection}</li>
							</ul>
						</div>
					</div>
                         <div class="weather_03">
						<h4>${weather.date3.weather}</h4>
						<div class="mod_03">
                                  <div style="background: url(${weather.date3.icon1}) 0 0  no-repeat;_background:none;_FILTER: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${weather.date1.icon1}', sizingMethod='scale')" class="icon_weather" ></div>
							<ul class="detail">
								<li class="tpte">${weather.date3.temperature}</li>
								<li class="tpte">${weather.date3.windPowerAndDirection}</li>
							</ul>
						</div>
					</div>
                         							
                         
				</div>



			</div>
		</div>

          
	</div>
</body>
</html>