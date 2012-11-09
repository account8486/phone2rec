/**
* 存放预定义好的函数以备执行
* Author: H.yIng & Update Time: 2012-03-22
* Email: hyingreborn@qq.com
**/
//调试
function mydebug(state){
	if(state == true) { 
		//alert('现在是开发阶段');
		var debugbox = "<div class='debugbox'>这里是开发工具区</div>";
		$('body').prepend(debugbox);
	} else {
		//alert('调试已关闭');
	}	
}

//加载内部页面
function Loading_Page(url){
	$('#main_content').load(url+'.html',{},function(){
		//加载后执行
		
		//尺寸计算
		var left_nav_height;
		var main_cont_width;
		
		function fwresize(){
			left_nav_height=$('#main_cont').height();
			main_cont_width=$('#main_content').width()-$('#left_nav').width()-4;
			//尺寸调整
			//$('#left_nav').height(left_nav_height);
			$('#main_cont').width(main_cont_width);
		}
		
		fwresize();
		
	});
}

//隔行换色
function Li_even(ula,isodd){
	if(isodd == 'odd') {
		$(ula + ' li:odd').addClass('even');
	} else {
		$(ula + ' li:even').addClass('even');
	}	
}