<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/pages/portal/common/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="w960">
<div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
<div >
    <div style="padding: 0px">
        <div style="width:100%; height:550px;border:0px solid gray" id="container"></div>
    </div>
</div>
</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
<script type="text/javascript" src="${ctx }/js/touch/convertor.js"></script>
<script type="text/javascript">
	var xy = "${meeting.locationXY}";
	var tiny_marker;
	tiny_marker = null;
	
	if (xy == "") {
	    l_x = "117.285574";
	    l_y = "31.868537";
	} else {
	    l_x = xy.split(",")[0];
	    l_y = xy.split(",")[1];
	}
	var tini_map = new BMap.Map("container");            // 创建Map实例
	var ggPoint = new BMap.Point(l_x, l_y);    // 创建点坐标
	BMap.Convertor.translate(ggPoint,2,translateCallback);
	
	function translateCallback(point) {
	    tini_map.centerAndZoom(point, 13);                     // 初始化地图,设置中心点坐标和地图级别。
	    tini_map.enableScrollWheelZoom();  // 开启鼠标滚轮缩放
	    
	    tini_map.addControl(new BMap.NavigationControl()); 
	    tini_map.addControl(new BMap.ScaleControl());  
	    tini_map.addControl(new BMap.OverviewMapControl());  
	    
	    if (xy != "") {
	        tiny_marker = new BMap.Marker(point);  // 创建标注
	        tini_map.addOverlay(tiny_marker);              // 将标注添加到地图中
	    }
	    
		$(".anchorBL").hide();
	}

    function getWindowHeight() {
        var windowHeight = 0;
        if (typeof(window.innerHeight) == 'number') {
            windowHeight = window.innerHeight;
        }
        else {
            if (document.documentElement && document.documentElement.clientHeight) {
                windowHeight = document.documentElement.clientHeight;
            }
            else {
                if (document.body && document.body.clientHeight) {
                    windowHeight = document.body.clientHeight;
                }
            }
        }
        return windowHeight;
    }

    $(document).ready(function() {
        var bodyHeightG  = getWindowHeight();
        if( bodyHeightG - 249 > 0){
            jQuery("#container").height(bodyHeightG - 249);
        }
    });
</script>