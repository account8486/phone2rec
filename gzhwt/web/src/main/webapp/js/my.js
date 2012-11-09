//默认运行
$(function(){
	//导航点击切换act
	$(".nav li").not('.more').find('a').click(function () {
		$(".nav li").removeClass("act");
		$(this).parent().addClass("act");
	});
	
	//导航
	$(".nav .big li.more a").hover(
	  function () {
		//$(this).parent().addClass("hover");
		$(this).parent().find('ul').fadeIn();
	  },
	  function () {
		//$(this).parent().removeClass("hover");
		$(this).parent().find('ul').hide();
	  }
	);
	
	$(".nav .big li.more ul").hover(
	  function () {
		$(this).show();
		//$(this).parent().addClass("hover");
	  },
	  function () {
		$(this).hide();
		//$(this).parent().removeClass("hover");
	  }
	);
	
	//互动交流页面li hover效果
	$(".message_list li:odd").addClass("odd");
	//互动交流发表信息
	//说说您对本次会议的想法和建议吧！
	$(".message_sub .ipt textarea").focusin(function() {
		$(this).parent().addClass('focus');
		if($(this).html() == "说说您对本次会议的想法和建议吧！"){
			$(this).empty();
		}
	});
	$(".message_sub .ipt textarea").focusout(function() {
		$(this).parent().removeClass('focus');
		if($(this).html() == ""){
			$(this).html('说说您对本次会议的想法和建议吧！');
		}
	});
	
});