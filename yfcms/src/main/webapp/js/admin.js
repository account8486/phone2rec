//默认运行
$(function(){
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

//构建会议内容编辑菜单
function buildEditMenu(contentPath,meetingId){
	var menuStr = '<dt><h5>会议内容管理</h5></dt> '+
	'<dd><a id="base" onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_baseinfo&meeting.id='+meetingId+'">'+
	'	<span class="nav">基本信息</span>'+
	'</a></dd>'+
    '<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/pages/admin/pri/meeting/getMeetingUsers.action?isAdmin=1&meeting.id='+meetingId+'">'+
	'	<span class="nav">参会人员</span>'+
	'</a></dd>'+
	'<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/group/list.action?meetingId='+meetingId+'">'+
	'	<span class="nav">分组模版</span>'+
	'</a></dd>'+
    '<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/agenda/agendaList.action?meetingId='+meetingId+'">'+
	'	<span class="nav">会议议程</span>'+
	'</a></dd>'+
    '<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/meeting/getMeetingFilesPager.action?meetingId='+meetingId+'">'+
	'	<span class="nav">资料管理</span>'+
	'</a></dd>'+
    '<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/meeting/listMeetingDinner.action?meetingId='+meetingId+'">'+
	'	<span class="nav">用餐管理</span>'+
	'</a></dd>'+
    '<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/pages/admin/pri/meeting/getMeetingById.action?returnType=edit_map&meeting.id='+meetingId+'">'+
	'	<span class="nav">会场位置</span>'+
	'</a></dd>'+
    '<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/message/getMessageList.action?meetingId='+meetingId+'">'+
	'	<span class="nav">短信管理</span>'+
	'</a></dd>'+
    '<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/interaction/postList.action?meetingId='+meetingId+'">'+
	'	<span class="nav">互动管理</span>'+
	'</a></dd>'+
	'<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/sign/list.action?meetingId='+meetingId+'">'+
	'	<span class="nav">签到管理</span>'+
	'</a></dd>'+
	'<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/gift/list.action?meetingId='+meetingId+'">'+
	'	<span class="nav">礼品管理</span>'+
	'</a></dd>'+
	'<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/journey/listVehicle.action?meetingId='+meetingId+'">'+
	'	<span class="nav">车辆管理</span>'+
	'</a></dd>'+
	'<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/meeting/card_listIssue.action?meetingId='+meetingId+'">'+
	'	<span class="nav">卡牌管理</span>'+
	'</a></dd>'+
	'<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/reception/specialty_show.action?meetingId='+meetingId+'">'+
	'	<span class="nav">土特产管理</span>'+
	'</a></dd>'+
	'<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/hotel/list.action?meetingId='+meetingId+'">'+
	'	<span class="nav">酒店管理</span>'+
	'</a></dd>'+
	'<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/basemenu/getBaseMenuPages.action?meetingId='+meetingId+'">'+
	'<span class="nav">菜单管理</span>'+
	'</a></dd>'+
	'<dd><a onclick="switchCss($(this));" target = "mainFrame" href="'+contentPath+'/admin/pri/spokesman/getSpokesManLst.action?meetingId='+meetingId+'">'+
	'<span class="nav">发言人管理</span>'+
	'</a></dd>';

	return menuStr;
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