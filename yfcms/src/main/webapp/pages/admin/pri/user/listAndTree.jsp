<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>会议云平台</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/ext-all.css"/>
	${admin_css}                                      
	${jquery_js}
	${util_js}
<script type='text/javascript' src='${ctx}/js/ext/ext-all.js'></script>
<script type="text/javascript">
    Ext.onReady(function() {
        Ext.QuickTips.init();
        
        var store = Ext.create('Ext.data.TreeStore', {
            proxy: {
                type: 'ajax',
                url: '${ctx}/admin/pri/org/listLevel.action'
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
            height: 448,
            width: 200,
            useArrows: true,
            autoScroll:true,
            rootVisible: false,
            listeners : {   
                'itemclick' : function(view, record){  
                	$("#list-div").load(
                		"${ctx}/admin/pri/user/list.action",
                		{"organId": record.data.id}
                	);
                }   
            }   
        });
        
        tree.expandAll();
    });
    
    // 加载右侧组织列表
    $(document).ready(function(){
    	$("#list-div").load(
    		"${ctx}/admin/pri/user/list.action",
    		{
    			"errMsg": "${errMsg}",
    			"organId": "${organId}"
    		}
    	);
    });
</script>
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>系统用户管理111</h3>
	</div>
	
	<div id="tree-div" style="float:left;"></div>
	
	<div id="list-div" style="float:left;width:75%;"> 
</div>
</body>
</html>