package com.wondertek.meeting.common;
import java.util.ArrayList;
import java.util.List;

import com.ckeditor.CKEditorConfig;
import com.ckeditor.EventHandler;
import com.ckeditor.GlobalEventHandler;

public class CkEditorConfigHelper {
 
	public static CKEditorConfig createConfig() {
		CKEditorConfig config = new CKEditorConfig();
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> subList = new ArrayList<String>();
		List<String> subList2 = new ArrayList<String>();
		subList.add("Source");
//		subList.add("Print");
		subList.add("Preview");
		subList.add("Maximize");
		
//		subList.add("Templates");
		subList.add("Cut");
		subList.add("Copy");
		subList.add("Paste");
		subList.add("PasteText");
		subList.add("PasteFromWord");
		subList.add("Undo");
		subList.add("Redo");
		subList.add("Find");
		subList.add("Replace");
		
		subList.add("-");
//		subList.add("Anchor");
		subList.add("Link");
		subList.add("Unlink");
		subList.add("Table");
		subList.add("Smiley");
		subList.add("Image");
//		subList.add("Flash");
		subList.add("RemoveFormat");
		subList.add("PasteFromWord");
		
		subList2.add("Font");
		subList2.add("FontSize");
		subList2.add("BGColor");
		subList2.add("TextColor");
		subList2.add("Bold");
		subList2.add("Italic");
		subList2.add("Underline");
		subList2.add("SpecialChar");
		subList2.add("Blockquote");
		
		subList.add("-");
		subList2.add("NumberedList");
		subList2.add("BulletedList");
		subList2.add("Outdent");
		subList2.add("Indent");
		subList2.add("JustifyLeft");
		subList2.add("JustifyCenter");
		subList2.add("JustifyRight");
		subList2.add("JustifyBlock");
		list.add(subList);
		list.add(subList2);
//		config.addConfigValue("height", "50px");
		config.addConfigValue("toolbar", list);
		config.addConfigValue("language", "zh-cn");
//		config.addConfigValue("skin", "v2");
		config.addConfigValue("font_names", 
				"宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;"
				+"Arial/Arial;Comic Sans MS/Comic Sans MS;Courier New/Courier New;Georgia/Georgia;"
				+"Lucida Sans Unicode/Lucida Sans Unicode;Tahoma/Tahoma;Times New Roman/Times New Roman;"
				+"Trebuchet MS/Trebuchet MS;Verdana/Vernada;"
				);
		
		//图片上传URL
		config.addConfigValue("filebrowserImageUploadUrl", "ckUploadImage.action?type=image");
		// 图片浏览配置  
		config.addConfigValue("filebrowserImageBrowseUrl", "ckbrowerServer.action?type=image"); 
		
		
//		config.addConfigValue("linkUpload", true);
//		config.addConfigValue("linkUploadURL", "ckbrowerServer.action?type=file");
		return config;
	}
 
	public static EventHandler createEventHandlers() {
		EventHandler handler = new EventHandler();
		handler.addEventHandler("instanceReady","function (ev) { alert(\"Loaded: \" + ev.editor.name); }");
		return handler;
	}
 
	public static GlobalEventHandler createGlobalEventHandlers() {
		GlobalEventHandler handler = new GlobalEventHandler();
		handler.addEventHandler("dialogDefinition","function (ev) {  alert(\"Loading dialog window: \" + ev.data.name); }");
		return handler;
	}
}