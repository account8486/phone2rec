//////////////////////////////////////////////////////常用JS代码//////////////////////////////////////////////////////
//滚动
function marquee(i, direction)
{
	var obj = document.getElementById("marquee" + i);
	var obj1 = document.getElementById("marquee" + i + "_1");
	var obj2 = document.getElementById("marquee" + i + "_2");

	if (direction == "up")
	{
		if (obj2.offsetTop - obj.scrollTop <= 0)
		{
			obj.scrollTop -= (obj1.offsetHeight + 20);
		}
		else
		{
			var tmp = obj.scrollTop;
			obj.scrollTop++;
			if (obj.scrollTop == tmp)
			{
				obj.scrollTop = 1;
			}
		}
	}
	else
	{
		if (obj2.offsetWidth - obj.scrollLeft <= 0)
		{
			obj.scrollLeft -= obj1.offsetWidth;
		}
		else
		{
			obj.scrollLeft++;
		}
	}
}

//屏蔽鼠标右键
function disabledRightButton()
{
	document.oncontextmenu = function(e){return false;}
	document.onselectstart = function(e){return false;}
	if (navigator.userAgent.indexOf("Firefox") > -1)
	{
		document.writeln("<style>body {-moz-user-select: none;}</style>");
	}
}

//设为首页
function setHomePage()
{
	if(document.all)
	{
		var obj = document.links(0);
		if (obj)
		{
			obj.style.behavior = 'url(#default#homepage)';
			obj.setHomePage(window.location.href);
		}
  	}
	else
	{
		if(window.netscape)
		{
			try
			{
				netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			}
			catch (e)
			{
				alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将[signed.applets.codebase_principal_support]设置为'true'");
			}
		}
		var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
		prefs.setCharPref('browser.startup.homepage', window.location.href);
   	}
}

//加入收藏
function addFavorite()
{
	var url		= document.location.href;
	var title	= document.title;
	if (document.all)
	{
		window.external.addFavorite(url,title);
	}
	else if (window.sidebar)
	{
		window.sidebar.addPanel(title, url,"");
	}
}


//////////////////////////////////////////////////////访问统计相关//////////////////////////////////////////////////////
function GetXMLRequester()
{
	var xmlHttpRequest = false;
	try
	{
		if(window.ActiveXObject)
		{
			for(var i = 5; i; i--)
			{
				try
				{
					if( i == 2 )
					{
						xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
					}
					else
					{
						xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP." + i + ".0" );
						xmlHttpRequest.setRequestHeader("Content-Type","text/xml");
						xmlHttpRequest.setRequestHeader("Content-Type","gb2312");
					}
					break;
				}
				catch(e)
				{
					xmlHttpRequest = false;
				}
			}
		}
		else if(window.XMLHttpRequest)
		{
			xmlHttpRequest = new XMLHttpRequest();
			if (xmlHttpRequest.overrideMimeType)
			{
				xmlHttpRequest.overrideMimeType('text/xml');
			}
		}
	}
	catch(e)
	{
		xmlHttpRequest = false;
	}

	return xmlHttpRequest;
}

function Counter(refer, ip)
{
	var	url = "counter.asp?refer=" + refer + "&ip=" + ip;
	var xmlHttpRequest = GetXMLRequester();
	xmlHttpRequest.open('GET', url, true);
	xmlHttpRequest.send(null);
}

//下拉菜单相关
var navCurrentId = "";
function showMenu(id)
{
	var objMenu = document.getElementById("menu" + id);
	objMenu.style.visibility = "visible";

	var objMenuA = document.getElementById("menuA" + id);
	if (objMenuA.className == "position")
	{
		navCurrentId = objMenuA.uniqueID;
		if (!navCurrentId)
		{
			navCurrentId = objMenuA.clientID;
		}
	}
	else
	{
		objMenuA.className = "position";
	}
}

function hiddMenu(id)
{
	var objMenu = document.getElementById("menu" + id);
	objMenu.style.visibility = "hidden";

	var objMenuA = document.getElementById("menuA" + id);
	if (objMenuA.uniqueID != navCurrentId)
	{
		objMenuA.className = "";
	}
}