// set up drop downs anywhere in the body of the page. I think the bottom of the page is better.. 
	// but you can experiment with effect on loadtime.
	if (mtDropDown.isSupported()) {

		//==================================================================================================
		// create a set of dropdowns
		//==================================================================================================
		// the first param should always be down, as it is here
		//
		// The second and third param are the top and left offset positions of the menus from their actuators
		// respectively. To make a menu appear a little to the left and bottom of an actuator, you could use
		// something like -5, 5
		//
		// The last parameter can be .topLeft, .bottomLeft, .topRight, or .bottomRight to inidicate the corner
		// of the actuator from which to measure the offset positions above. Here we are saying we want the 
		// menu to appear directly below the bottom left corner of the actuator
		//==================================================================================================
		var ms = new mtDropDownSet(mtDropDown.direction.down, 0, 0, mtDropDown.reference.bottomLeft);

		//==================================================================================================
		// create a dropdown menu
		//==================================================================================================
		// the first parameter should be the HTML element which will act actuator for the menu
		//==================================================================================================
		//==================================================================================================

		//==================================================================================================
		// add a sub-menu
		//==================================================================================================
		// to add a sub menu to an existing menu object, call it's addMenu method and pass it the item from
		// the parent menu which should act as it's actuator. To add a submenu to the fourth item of a menu
		// called "theMenu", you would do theMenu.addMenu(theMenu.items[3])
		//==================================================================================================
/*		
		// menu : Get Started
		var menu1 = ms.addMenu(document.getElementById("menu1"));
		menu1.addItem("About Cnooc Ltd", "/about/channel/about1281.asp");
		menu1.addItem("How we run the business", "/about/channel/list.asp");
		menu1.addItem("Milestones", "/about/channel/about1293.asp");        
		menu1.addItem("Careers", "/careers/channel/Careers1307.asp");


		var menu2 = ms.addMenu(document.getElementById("menu2"));
		menu2.addItem("????????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sg.com.cn:7001/office/zxzx/zxdt/gzyw/list_top.jsp");
		menu2.addItem("????????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sg.com.cn:7001/office/zxzx/zxdt/gzyw/list_top.jsp");
 
*/

		var menu2 = ms.addMenu(document.getElementById("menu2"));
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/office/zxzx/zxdt/gzyw/list_top.jsp","_blank");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/office/zxzx/ldjh/list_top.jsp","_blank");
        menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/office/zxzx/zbxx/nbqktb/list_top.jsp","_blank");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/office/zxzx/wsxw/list_top.jsp","_blank");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/office/zxzx/mtbd/list_top.jsp","_blank");
		/*menu2.addItem("???? ", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://www.sgcc.com.cn/gsmt/gjdwzz/default.shtml ");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/office/zxzx/nyzx/list_top.jsp");
		menu2.addItem("?????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/office/zxzx/tgygc/gzjb/list_top.jsp ");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/office/zxzx/ztzl/list_top.jsp ");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/aqdc/default.shtml  ");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/office/zxzx/dlsc/gszscaq/list_top.jsp");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/jjgl/default.shtml");
		menu2.addItem("???? ", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/tydb/default.shtml");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://www.sgcc.com.cn/dlgx/dwtj/default.shtml ");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/ghxx/default.shtml ");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/qygl/gzzd/default.shtml  ");
		menu2.addItem("?????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/qyxxh/default.shtml");
		menu2.addItem("????", "http://portal.sg.com.cn:7001/EP/appmanager/sgear/portal?_nfpb=true&_pageLabel=SGPORTAL_book_36&iframeurl=http://portal.sgcc.com.cn/office/zxzx/wnfw/cydh/default.jsp ");*/

		//==================================================================================================
		// write drop downs into page
		//==================================================================================================
		// this method writes all the HTML for the menus into the page with document.write(). It must be
		// called within the body of the HTML page.
		//==================================================================================================
		mtDropDown.renderAll();
	}