<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<select id="province" name="meeting.province" style="width:80px;" ></select>
<select id="city" name="meeting.city"   style="width:80px;" ></select>
<select style="display :none;" id="district" name="meeting.district"></select>


<script type="text/javascript">
$().ready(function() {
	//初始化省市县数据
	var provinceList = _area_data_;
	var options_province = '';
	var obj_province = document.getElementById("province");	
	for(p=0;p<provinceList.length;p++){
		obj_province.options.add(new Option(_area_data_[p].name,_area_data_[p].value));
	}
	/*
	var obj_city = document.getElementById("city");
	obj_city.options.add(new Option(_area_data_[0].c[0].name,_area_data_[0].c[0].value));
	*/
	getCityList($("#province").val());
	$("#province").change(function() {
		getCityList($("#province").val());
	});
	
	//initArea("${meeting.province}","${meeting.city}");
});

function initArea(province,city){
	//alert("province="+province);
	var _province_ = province==""?"01":province;
	var _city_ = city==""?"0101":city;
	$("#province").val(_province_);
	getCityList(_province_);
	$("#city").val(_city_);
}

function getCityList(province){
	var obj_city = document.getElementById("city");

	obj_city.options.length=0;

	for(i=0;i<_area_data_.length;i++){
		//alert(_area_data_[i].value+"|"+province);
		if(_area_data_[i].value == province){
			//alert(_area_data_[i].name);
			for(j=0;j<_area_data_[i].c.length;j++){
				obj_city.options.add(new Option(_area_data_[i].c[j].name,_area_data_[i].c[j].value));
//				options_city += "<option value='"+_area_data_[i].c[j].value+"'>"+_area_data_[i].c[j].name+"</option>";
//				alert(options_city);
			}
//			$("#city").html(options_city);
			break;
		}
	}
	
}
</script>