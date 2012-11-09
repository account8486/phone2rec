<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">会场位置</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c map_con" style="display:block;">
    	<div id="container" style="height:300px;"></div>
    </div>
<%@ include file="/pages/touch/common/footer.jsp" %>
<style>
	.map_con {
		padding:10px 15px;
	}
</style>

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
	    
	    var opts = {type: BMAP_NAVIGATION_CONTROL_ZOOM,anchor:BMAP_ANCHOR_BOTTOM_RIGHT}  
	    tini_map.addControl(new BMap.NavigationControl(opts)); 
	    
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
        if( bodyHeightG - 172 > 0){
            jQuery("#container").height(bodyHeightG - 172);
        }
    });
</script>


<script type="text/javascript">


</script>