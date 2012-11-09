/**
* 页面加载完毕时默认执行函数
* Author: H.yIng & Update Time: 2012-03-22
* Email: hyingreborn@qq.com
**/
$(function(){
	//调试状态开关
	mydebug(false);
	
	$('ul.tab a').click(function(){
		$(this).parent().parent().find('li').removeClass('act');
		$(this).parent().addClass('act');
		var i = $(this).parent().index();
		//alert(i);
		$(this).parent().parent().parent().find('.tab_c').hide();
		$(this).parent().parent().parent().find('.tab_c').eq(i).fadeIn();
	})
	
	$('header h3').click(function(){
		//confirm('点击此处可切换会议');
	})
	
	//公用box,tab切换
	$('.boxa .tit p.tab a').click(function(){
		$(this).parent().find('a').removeClass('act');
		$(this).addClass('act');
		var i = $(this).index();
		//alert(i);
		$(this).parent().parent().parent().find('.tabc').hide();
		$(this).parent().parent().parent().find('.tabc').eq(i).show();
	})
});