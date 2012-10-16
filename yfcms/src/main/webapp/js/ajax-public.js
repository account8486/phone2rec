/**
 * 用于AJAX请求的公用函数
 * @param url
 * @param successCallback
 * @param isAsync
 * @param failCallback
 */
function ajaxRequest(url, successCallback, isAsync, failCallback){
	var contextPath="/yf";
	if(isAsync == null){
		isAsync = true;
	}
	
	$.ajax({
		 
		 async		: 	isAsync,
		 timeout	:	100000,
	     type		:	"post",
	     dataType	:   "json",
	     url		:   contextPath + url,
	     success	:   function(data, resp, XMLHttpRequest){
				    		//表明是调用成功
							if(XMLHttpRequest.status == 200 && data != null){
									    //不需要提示消息
								 		if(successCallback!=null)
								 			successCallback(data);
								 		
								 	}else if(XMLHttpRequest.status == 0){
									//异常信息
									alert('数据请求失败。');
								}
						    },
	     error		:	function(XMLHttpRequest, textStatus, errorThrown){
	    	 				//异常信息
	                		alert('页面加载失败!');
	                	}
	 });
}


/**
 * 从URL中根据参数名取指定的参数,
 * url：形如: login.msp?userName=aaa&password=1234或userName=aaa&password=1234
 * 使用： var userName = getUrlParam("userName");  //return aaa
 */
function getUrlParam(url, paramName) {
	if(url.indexOf(paramName) == -1)
		return "";
	
	url = url.substring(url.indexOf("?") + 1);
	var params = url.split("&");
	
	for(var i = 0; i < params.length; i ++) {
		var str = params[i];
		var data = str.split("=");
		if(data.length != 2) {
			continue;
		}
		
		if(data[0] == paramName) {
			return data[1];
		}
	}
	return "";
}









