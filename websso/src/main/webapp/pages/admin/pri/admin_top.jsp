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
					<!--个性化end  -->
					<td width=50 align=center class="header-link"><a href="javascript:guanbi2()" >注销</a></td>
			  		
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
        <td class="bea-portal-book-primary-menu-single-item-active"  nowrap="true" align="center"><span><a href="${ctx}/pri/admin/getSsoSystemList.action">&nbsp;系统管理</a></span></td>
   
       </tr>
    </table>
      </td>  
           <td class='bea-portal-book-primary-menu-right'>&nbsp;</td>
            </tr>
        </table>
        </div>
        </div>
    <!--头部结束-->