<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- IE6 PNGfix -->
    <!--[if IE 6]>
    <script src="${ctx}/js/iepng.js" mce_src="${ctx}/js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.iepng');
        /* 将 .iepng 改成你应用了透明PNG的CSS选择器 */
    </script>
    <![endif]-->
    <script type="text/javascript">
    function choose(obj){
    	window.opener.CKEDITOR.tools.callFunction('${CKEditorFuncNum }',obj);
    	window.close();
    }
	function view(obj){
	  window.location.href='ckbrowerServer.action?type=image&CKEditorFuncNum=2&fo='+obj;
	}
	</script>
</head>
<body >
	<div style='width:100%;float:left;word-break:break-all;' >
		<span><a onclick ="view('%2Fupload%2Fck');return false;" href='javascript:void(0);'>[根目录]</a>
			
	<c:if test="${not empty parentFolder}">
		<c:forEach var="folder" items="${parentFolder}">
			<a href='javascript:void(0)' onclick ="view('${folder.path }');return false;">${folder.name }</a>
		</c:forEach>
	</c:if>
			
		<span/>
	</div>
	<!-- 父级目录 -->
	<!-- 
	<c:if test="${not empty parentFolder}">
		<c:forEach var="folder" items="${parentFolder}">
			<div style='text-align:center;padding:10px;width:80px;float:left;word-break:break-all;'
				 onclick ="view('${folder.path }');">
				 <a href='javascript:void(0)' onclick ="view('${folder.path }');return false;">
				 	<img style='border:none;width:80px;height:80px;' src='${ctx }/images/folder.png' 
				 	title='[父目录]${folder.name }'/>
				 	</a>
				 	<span style='text-align:center'>${folder.name }</span>
			</div>
		</c:forEach>
	</c:if>
	 -->
	<!-- 子目录 -->
	<c:if test="${not empty childFolder}">
		<c:forEach var="folder" items="${childFolder}">
			<div style='text-align:center;padding:10px;width:80px;float:left;word-break:break-all;'
				 onclick ="view('${folder.path }');">
				 <a href='javascript:void(0)' onclick ="view('${folder.path }');return false;">
				 	<img style='border:none;width:80px;height:80px;' src='${ctx }/images/folder_image.png' 
				 	title='[子目录]${folder.name }'/>
				 	</a>
				 	<span style='text-align:center'>${folder.name }</span>
			</div>
		</c:forEach>
	</c:if>
	
	<!-- 图片文件 -->
	<c:if test="${not empty imgList}">
		<br/>
    	<div style='width:100%;float:left;word-break:break-all;'/>
    	<hr/>
	
		<c:forEach var="img" items="${imgList}">
			<div style='width:150px;height:150px;float:left;word-break:break-all;padding:5px;background:#666699;margin:5px;'>
    			<a href='javascript:void(0)' onclick="choose('${img }');"><img style='border:none;width:145px;height:145px;' src='${img }' title='${img }'/></a>
    		</div>
		</c:forEach>
	</c:if>
	
</body>
</html>