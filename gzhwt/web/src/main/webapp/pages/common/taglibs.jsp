<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://www.wondertek.com.cn/tags/wd" prefix="wd"%>
<%--  system values--%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="addr" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}" />
<%-- handle page theme --%>
<c:set var="cssdir" value="${ctx}/css${empty sessionScope.pageThemeName ? '' : sessionScope.pageThemeName}" />
<c:set var="main_css" value="<link href='${cssdir}/main.css' rel='stylesheet' type='text/css' />"/>

<%-- css file define--%>
<c:set var="style_css" value="<link href='${ctx}/css/style.css' rel='stylesheet' type='text/css' />"/>
<c:set var="admin_css" value="<link href='${ctx}/css/admin.css' rel='stylesheet' type='text/css' />"/>
<%--<c:set var="thickbox_css" value="<link href='${ctx}/css/thickbox.css' rel='stylesheet' type='text/css' />"/>--%>
<c:set var="jquery_ui_css" value="<link href='${ctx}/css/redmond/jquery-ui.css' rel='stylesheet' type='text/css' />"/>
<c:set var="accordion_jquery_ui_css" value="<link href='${ctx}/css/jquery-accordion/jquery-ui.css'  rel='stylesheet' type='text/css' />" />

<%-- js file define --%>
<c:set var="jquery_js" value="<script type='text/javascript' src='${ctx}/js/jquery-1.5.min.js'></script>"  />
<c:set var="jquery_ui_js" value="<script type='text/javascript' src='${ctx}/js/jquery-ui.js'></script>"  />
<c:set var="jquery_form_js" value="<script type='text/javascript' src='${ctx}/js/jquery.form.js'></script>"  />
<c:set var="jquery_select_js" value="<script type='text/javascript' src='${ctx}/js/jquery.select.js'></script>"  />
<c:set var="WdatePicker_js" value="<script type='text/javascript' src='${ctx}/js/my97/WdatePicker.js'></script>"  />
<c:set var="module_js" value="<script type='text/javascript' src='${ctx}/js/jpolite/modules.js'></script>"  />
<c:set var="jquery_myext_js" value="<script type='text/javascript' src='${ctx}/js/jpolite/jquery.myext.js'></script>"  />
<%--<c:set var="jquery_ui_js" value="<script type='text/javascript' src='${ctx}/js/jpolite/jquery.ui.js'></script>"  />--%>
<c:set var="thickbox_js" value="<script type='text/javascript' src='${ctx}/js/thickbox.js'></script>"  />
<c:set var="thickbox2_js" value="<script type='text/javascript' src='${ctx}/js/thickbox2.js'></script>"  />
<c:set var="util_js" value="<script type='text/javascript' src='${ctx}/js/util.js'></script>"  />
<c:set var="easyui_js" value="<script type='text/javascript' src='${ctx}/js/easyui/jquery.easyui.js'></script>"  />
<c:set var="validate_js" value="<script type='text/javascript' src='${ctx}/js/jquery.validate.js'></script>"  />
<c:set var="admin_js" value="<script type='text/javascript' src='${ctx}/js/admin.js'></script>"  />
<c:set var="area_js" value="<script type='text/javascript' src='${ctx}/js/area.js'></script>"  />
<c:set var="jmpopups_js" value="<script type='text/javascript' src='${ctx}/js/jquery.jmpopups.js'></script>"  />
<c:set var="map_js" value="<script type='text/javascript' src='${ctx}/js/map.js'></script>"  />
<c:set var="accordion_jquery_ui_js" value="<script type='text/javascript' src='${ctx}/js/jquery-accordion/jquery-ui.min.js'></script>"  />
<c:set var="validform_js" value="<script type='text/javascript' src='${ctx}/js/Validform_v5.0.js'></script>"  />
<%@ page trimDirectiveWhitespaces="true" %>