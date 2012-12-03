<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="addr" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}" />
<c:set var="cssdir" value="${ctx}/css${empty sessionScope.pageThemeName ? '' : sessionScope.pageThemeName}" />
<c:set var="main_css" value="<link href='${cssdir}/main.css' rel='stylesheet' type='text/css' />"/>
<c:set var="jquery_js" value="<script type='text/javascript' src='${ctx}/js/jquery-1.5.min.js'></script>"  />
<c:set var="jquery_form_js" value="<script type='text/javascript' src='${ctx}/js/jquery.form.js'></script>"  />
<c:set var="util_js" value="<script type='text/javascript' src='${ctx}/js/util.js'></script>"  />
<c:set var="admin_css" value="<link href='${ctx}/css/admin.css' rel='stylesheet' type='text/css' />"/>

