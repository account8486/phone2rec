<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>会务通平台</title>
    <link href="${ctx}/css/main.css" rel="stylesheet" type="text/css"/>
    ${admin_css} ${jquery_js} ${util_js} ${admin_js}
    <script type="text/javascript" src="http://app.mapabc.com/apis?&t=ajaxmap&v=2.1.2&key=${mapkey}"></script>
</head>
<body>
<div class="page_title">
    <h3>地图编辑 -- ${CURRENT_MEETING}</h3>
</div>
<div class="page_tools page_serach">
    <table width="100%">
        <tr>
            <th align="center" style="width: 200px; ">请右键点击地图设定标记</th>
            <th align="center" style="width: 60%; "></th>
            <td>&nbsp;&nbsp;&nbsp;<a href="#" id="queryForList" onclick="save();" class="btn_common btn_true">提 交</a></td>
        </tr>
    </table>
</div>
<div style="padding: 5px">
    <div style="padding: 0px">
        <div style="width:100%; height:550px;border:0px solid gray" id="container"></div>
    </div>
</div>

<script type="text/javascript">
	// 根据地址取位置信息
	var data = "${city}";
    var xy = "${meeting.locationXY}";
    var tiny_marker = null;
    var e_x, e_y, l_x, l_y;

    if (xy == "") {
		var mlsp= new MPoiSearchOptions();
	　　mlsp.recordsPerPage=1;
	　　mlsp.pageNum=1;
		var mls =new MPoiSearch();
		mls.setCallbackFunction(searchCallBack);  //设置回调函数
		//按照地址和城市代码搜索
		var address = "${meeting.location}";
		var citycode = data.split(",")[1];
	　　mls.poiSearchByKeywords(address,citycode,mlsp);
    } else {
        l_x = xy.split(",")[0];
        l_y = xy.split(",")[1];
		mapInit(l_x, l_y);
		markMap(l_x, l_y);
    }
	
　　function searchCallBack(data){
　　　　if(data.error_message != undefined){
			setMapCenterByCityCode();
　　　　}else{
　　　　　　var search_xy = data.bounds;
			if (search_xy) {
				l_x = search_xy.split(";")[0];
				l_y = search_xy.split(";")[1];
				mapInit(l_x, l_y);
				markMap(l_x, l_y);
			} else {
				setMapCenterByCityCode();
			}
　　　　}
　　}
	//默认标记城市中心坐标
	function setMapCenterByCityCode() {
		var c_x = data.split(",")[2];
		var c_y = data.split(",")[3];
		mapInit(c_x, c_y);
		markMap(c_x, c_y);
		/**
		也可以用
		var mapComponent = new MapComponent(mapObj);  
  		//设置城市中心点  
		mapComponent.setMapCityCenter("0551");  
		*/
	}
	function markMap(x, y) {
		addMarkerOnMap(x, y)
		addMenu();
		$("#Attribution").html("");
		tini_map.addEventListener(tini_map, MOUSE_RIGHT_CLICK, function (e) {
			e_x = e.eventX;
			e_y = e.eventY;
			$("#version").html("");
		});
	}
    function mapInit(x, y) {
        var mapOptions = new MMapOptions();//构建地图辅助类
        mapOptions.zoom = 13;//要加载的地图的缩放级别
        mapOptions.center = new MLngLat(x, y);//要加载的地图的中心点经纬度坐标
        mapOptions.toolbar = DEFAULT;//设置地图初始化工具条
        mapOptions.toolbarPos = new MPoint(15, 15); //设置工具条在地图上的显示位置
        mapOptions.overviewMap = SHOW; //设置鹰眼地图的状态，SHOW:显示，HIDE:隐藏（默认）
        mapOptions.scale = SHOW; //设置地图初始化比例尺状态，SHOW:显示（默认），HIDE:隐藏。
        mapOptions.returnCoordType = COORD_TYPE_OFFSET;//返回数字坐标
        mapOptions.zoomBox = true;//鼠标滚轮缩放和双击放大时是否有红框动画效果。
        tini_map = new MMap("container", mapOptions); //地图初始化
    }
    function addMarkerOnMap(x, y) {
        var markerOption = new MMarkerOptions();
        markerOption.isEditable = true;
        var Marker = new MMarker(new MLngLat(x, y), markerOption);
        Marker.id = "mark";
        tiny_marker = Marker;
        tini_map.addOverlay(Marker, false);
    }
    function addMenu() {
        var ary=new Array();
        var menuItem1=new MMenuItem();
        menuItem1.objectType=TYPE_MAP;
        menuItem1.order=1;
        menuItem1.menuText="标记地点";
        menuItem1.functionName = function () {
            addMarkerOnMap(e_x, e_y);
        };
        menuItem1.isEnabled=true;
        menuItem1.isHaveSeparator=true;
        menuItem1.id="0001";
        var menuItem2=new MMenuItem();
        menuItem2.objectType=TYPE_MAP;
        menuItem2.order=2;
        menuItem2.menuText="删除标记";
        menuItem2.functionName= function() {
            tini_map.removeAllOverlays();
            tiny_marker = null;
        };
        menuItem2.isEnabled=true;
        menuItem2.isHaveSeparator=true;
        menuItem2.id="0002";
        ary.push(menuItem2);
        ary.push(menuItem1);
        tini_map.addMenuItems(ary);
    }

    function save() {
        if (tini_map == null) {
            alert("请标记地点.");
            $(this).dialog("close");
            return;
        }
        var xy = tiny_marker.lnglat.lngX + "," + tiny_marker.lnglat.latY;
        var url = "${ctx}/pages/admin/pri/meeting/modifyMeetingXY.action?id=" + "${meeting.id}" + "&xy=" + xy;
        $.ajax({
            dataType:"json",
            url:url,
            success:function (data, resp, XMLHttpRequest) {
                alert("地图已保存!");
            }
        });
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

    $(document).ready(function () {
        var bodyHeightG  = getWindowHeight();

        if( bodyHeightG - 99 > 0){
            jQuery("#container").height(bodyHeightG - 99);
        }

    });
</script>
</body>
</html>