<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>会议云平台</title>
	${admin_css} ${jquery_js} ${jmpopups_js} ${util_js} ${WdatePicker_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/ext-all-scoped.css"/>
<script type='text/javascript' src='${ctx}/js/ext/ext-all.js'></script>
<script type="text/javascript">
	Ext.scopeResetCSS = true;
    Ext.onReady(function() {
        Ext.QuickTips.init();
        
        var store = Ext.create('Ext.data.TreeStore', {
            proxy: {
                type: 'ajax',
                url: '${ctx}/admin/pri/org/getTreeWithUser.action'
            },
            root: {
                text: '组织机构',
                id: 'src',
                expanded: true
            },
            folderSort: false,
            sorters: [{
                property: 'text',
                direction: 'ASC'
            }]
        });

        var tree = Ext.create('Ext.tree.Panel', {
            store: store,
            viewConfig: {
               // plugins: {
               //     ptype: 'treeviewdragdrop'
               // }
            },
            renderTo: 'tree-div',
            height: 390,
            width: 200,
            useArrows: true,
            autoScroll:true,
            rootVisible: false,
            listeners : {   
                'itemclick' : function(view, record){  
                	$("#list-div").load(
                		"${ctx}/admin/pri/statistics/statSmsByMeeting.action",
                		{"treeId": record.data.id}
                	);
                }   
            }   
        });
        
        tree.expandAll();
    });
    
    // 加载右侧组织列表
    $(document).ready(function(){
    	$("#list-div").load(
       		"${ctx}/admin/pri/statistics/statSmsByMeeting.action",
       		{
       			"errMsg": "${errMsg}",
       			"treeId": "${organId}"
       		}
       	);
    	
    	$(".easyui-tabs").tabs({  
			onSelect:function(title){  
				var tab = $(this).tabs("getTab", title); 
				var href = tab.attr("link");
				if (href) {
					location.href = href;
					showLoading(title);
					return false;
				}
			}  
		});
    });
</script>
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>统计查询 -- 短信统计</h3>
	</div>
	<div class="easyui-tabs" border="false" style="padding:10px;">	
		<div title="访问汇总" link="${ctx}/pages/admin/pri/meeting/access.jsp" style="padding:10px;"></div>
		<div title="访问明细" link="${ctx}/pages/admin/pri/meeting/accessDetail.jsp" style="padding:10px;"></div>
		<div title="互动交流" link="${ctx}/pages/admin/pri/statistics/interaction.jsp" style="padding:10px;"></div>
		<div title="短信统计" selected="true" style="padding:10px;">
			<div id="tree-div" style="float:left;"></div>
			<div id="list-div" style="float:left;width:73%;"> </div>
		</div>
		<div title="用户登录" link="${ctx}/admin/pri/user/loginList.action" style="padding:10px;"></div>
	</div>
</body>
</html>