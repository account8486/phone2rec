<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人工作台</title>
<link rel="stylesheet" type="text/css" href="/websso/skins/gwgreen/css/body.css">
<link rel="stylesheet" type="text/css" href="/websso/skins/gwgreen/css/book.css">
<link rel="stylesheet" type="text/css" href="/websso/skins/gwgreen/css/button.css">
<link rel="stylesheet" type="text/css" href="/websso/skins/gwgreen/css/form.css">
<link rel="stylesheet" type="text/css" href="/websso/skins/gwgreen/css/layout.css">
<link rel="stylesheet" type="text/css" href="/websso/skins/gwgreen/css/portlet.css">
<link rel="stylesheet" type="text/css" href="/websso/skins/gwgreen/css/window.css">
<link rel="stylesheet" type="text/css" href="/websso/skins/gwgreen/center/css/window-center.css">
<link rel="stylesheet" type="text/css" href="/websso/skins/gwgreen/noborder/css/window-noborder.css">
<link rel="stylesheet" type="text/css" href="/websso/skins/gwgreen/notitle/css/window-notitle.css">

</head>
<center>
<body class="bea-portal-body">
<div class="bea-portal-body-content">
<!--头部开始-->
<div class="bea-portal-body-header"  >
<!-- 个性化begin -->
<table width="950" cellpadding="0" cellspacing="0" border="0">
      <tr>
         <td width="236" class=logo></td>
         <td width="714" class=header valign="top" align="right">
			<table width=455 cellpadding="0" cellspacing="0" border="0"  class=header-menu-back>
			<tr>
			<td width="100" class="siglemenu-link"><div id="mycontent" ></div></td>
			<td align="right" >
  				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="26">
				<tr valign="middle"  height="25">
					<td width=32 align=center class="header-link"><a href="/SG186Portal/appmanager/portal/PersonDesk">首页</a></td>
					<!-- 个性化begin -->
                	
                	<td  class="header-link" align="center"  onMouseMove="javascript:toExit('show','boysoft')" onMouseOut="javascript:toExit('hidden','boysoft')" width=80>个性化定制
                     
					<div id=boysoft style=" VISIBILITY: hidden; Z-INDEX: 1;position:relative;top:2px;left:-32px;" onMouseMove="javascript:toExit('show','boysoft')" onMouseOut="javascript:toExit('hidden','boysoft')">
					<div style="float:none;position:absolute;width:70px;height:72px;">
						<table width="70" height="87"   border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#F5F5F5" style="font-size:12px;margin-left:0px;border:1px #DFDFDF solid;" class=gxh>
						<tr>
							<td align="center" valign="middle"><a href=""" title="定制内容" tabindex="7">定制内容</a></td>
						</tr>
						<tr>
						    <td width="94%"  align="center" valign="middle"><a href="" title="定制风格" tabindex="8">定制风格</a></td>
						</tr>
						<tr>
						    <td valign=middle align="center"><a href="javascript:resetTopdesk();" title="恢复桌面" tabindex="9">恢复桌面</a> </td>
						</tr>
						</table>
      				 </div>
       				</div> 
					</td>
					<!--个性化end  -->
					<td width=50 align=center class="header-link"><a href="http://eip.ah.sgcc.com.cn/SG186Portal/help/help.html" target="_blank">帮助</a></td>
					<td width=50 align=center class="header-link"><a href="javascript:guanbi2()" >注销</a></td>
			  		<form id="formaaaa" name="formaaaa" method="post" action="markup/shell/test.jsp" target="_blank" >
					<td ><input id="doctitle" type="text" name="doctitle" class="header-input" style="width:100px;"><input type="hidden" name="base_id"></td>
			   		<td width=51 align="center"><input type="image" src="skins/gwgreen/images/indeximg/go.gif" border="0" style="height:18px;width:41px;"> </td>
					</form>
				</tr>
			</table>
			</td>
  		 </tr>
    	</table>
       </td>
      </tr>
	</table> 
    
   <div class="bea-portal-ie-table-buffer-div">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
            <td class='bea-portal-book-primary-menu-left'>&nbsp;</td>
            <td class="bea-portal-book-primary-menu-single-container" align="left" nowrap="nowrap">
      <table cellpadding="0" cellspacing="0" border="0">
       <tr>  
        <td   class="bea-portal-book-primary-menu-single-item-active"  nowrap="true" align="center"><span>&nbsp;工作平台</span></td>
        <td class=bea-portal-book-menu-single-item-line></td><td class=bea-portal-book-menu-single-item-line></td>
        <td class="bea-portal-book-primary-menu-single-item"  nowrap="nowrap" align="center"><a href="http://eip.ah.sgcc.com.cn/SG186Portal/appmanager/portal/PersonDesk?_nfpb=true&amp;_pageLabel=SG186_portal_book_11">&nbsp;常用查询</a></td >
        <td class=bea-portal-book-menu-single-item-line></td><td class="bea-portal-book-primary-menu-single-item"  nowrap="nowrap" align="center"><a href="${ctx}/pri/admin/getSsoSystemList.action">&nbsp;系统管理</a></td >
        <td class=bea-portal-book-menu-single-item-line></td>
            <td class="bea-portal-book-primary-menu-single-children"></td>
 </tr>
    </table>
      </td>  
           <td class='bea-portal-book-primary-menu-right'>&nbsp;</td>
            </tr>
        </table>
        </div>
        </div>
    <!--头部结束-->