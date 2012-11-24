function $(id){
	return window.document.getElementById(id);
}

var limit = "10:0";  //自动刷新时间(分:秒)
var parselimit=limit.split(":");
parselimit=parselimit[0]*60+parselimit[1]*1;

function refreshAuto()
{
	window.location = url;
}
window.onload = function() {
	window.setTimeout("refreshAuto()",parselimit*1000);
};