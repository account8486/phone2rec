//======================================================================
//
//  处理投票显示 add by zouxiaoming
//
//======================================================================
 function lookVote(options,totle){
	var settings=options;
	if(totle!=null)
	{
		if(isNaN(totle))
		{
			alert('参数错误');
			return;
		}
	}
	if(typeof(settings)!='object')
	{
		alert('参数错误');
		return;
	}
	var container = document.getElementById("lookVote");
	container.innerHTML='<dl id="studyvote"></dl>';
	var study_voteCount=0;
	if(totle==null||totle=='')
	{//单项投票
		for(i=0;i<settings.length;i++)
		{
			study_voteCount += parseInt(settings[i].data);
		}
	}
	else
	{//多项投票
		study_voteCount = parseInt(totle);
	}
	var study_votestr="";
		for(i=0;i<settings.length;i++)
		{
			var studyplay_present=settings[i].data/study_voteCount*100;
			if(parseInt(studyplay_present)!=studyplay_present)
			{
				studyplay_present=studyplay_present.toFixed(2);
			}
			study_votestr +='<dd class="dd"><div class="f2">'+settings[i].name+'：</div><div class="outbar"><div class="inbar" style="width:'+studyplay_present+'%;background:'+settings[i].color+';"></div></div> <div class="fl">'+studyplay_present+'% </div></dd><br/>';
		}
	document.getElementById("studyvote").innerHTML=study_votestr;
}