<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/pages/portal/common/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="http://app.mapabc.com/apis?&t=ajaxmap&v=2.1.2&key=${mapkey}"></script>

<div class="w960">
<div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
<div >
    <div style="padding: 0px">
        <div style="width:100%; height:550px;border:0px solid gray" id="container"></div>
    </div>
</div>
</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
	var data = "${city}";
    var xy = "${meeting.locationXY}";
    var tiny_marker = null;

    if (xy == "") {
        //l_x = "117.285574"; l_y = "31.868537";
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
        addMarkerOnMap(l_x, l_y);
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
				addMarkerOnMap(l_x, l_y);
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
		addMarkerOnMap(c_x, c_y);
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
//        markerOption.imageUrl = "http://code.mapabc.com/images/lan_1.png";
//        markerOption.picAgent = false;
        markerOption.isEditable = false;
        var Marker = new MMarker(new MLngLat(x, y), markerOption);
        Marker.id = "mark";
        tiny_marker = Marker;
        tini_map.addOverlay(Marker, false);
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