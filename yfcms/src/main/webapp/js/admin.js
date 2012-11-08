//默认运行
$(document).ready(function(){
	//首次启动时进行界面尺寸初始化
	index_resize();
	//侧栏菜单act切换
	$('.left_nav dd a').click(function(){
		$('.left_nav dd a').removeClass('act');
		$(this).addClass('act');
	});
	//侧栏链接target
	//$('.left_nav dd a').attr('target','mainframe');
	
	//给数据列表增加隔行换色
	$('.page_datalist tbody tr:odd').addClass('even');
	
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

//浏览器尺寸变化时运行
	$(window).resize(function(){
		index_resize();
	});

//重新计算工作界面尺寸
function index_resize(){
	
	var page_width;
	var page_height;

	page_width=$(window).width()-195;
	page_height=$(window).height()-100;

	
	//尺寸调整
	
	$('.left_nav').height(page_height);
		
	$('.pages').height(page_height);
	$('.pages').width(page_width);
	
}

function switchCss(obj){
	$('.left_nav dd a').removeClass('act');
	obj.addClass('act');
	//alert(obj.attr("id"));
}



//显示会议内容编辑菜单
function showEditMenu(contentPath,meetingId){
	var menu =  buildEditMenu(contentPath,meetingId);
	$(window.parent.document).contents().find("#editMeeting").html(menu).show();
}

function hideObj(obj){
	obj.hide();
}

//跳转到会议基本信息编辑页面
function gotobase(){
	$(window.parent.document).contents().find("#base").click();
}