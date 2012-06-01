package com.guo.record;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class IndexActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);

		GridView gridview = (GridView) findViewById(R.id.GridView);
		ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();

		for (int i = 1; i < 6; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(i==1){
				//记录
				map.put("ItemImage", R.drawable.record);
				map.put("ItemText", "记录");
			}else if(2==i){
				//列表
				map.put("ItemImage", R.drawable.admin);
				map.put("ItemText", "列表");
			}else if(5==i){
				//列表
				map.put("ItemImage", R.drawable.logout);
				map.put("ItemText", "退出");
			}else if(3==i){
				//默认
				map.put("ItemImage", R.drawable.icon);
				map.put("ItemText", "web view");
			}else {
				map.put("ItemImage", R.drawable.icon);
				map.put("ItemText", "相机");
			}
			
			meumList.add(map);
		}

		SimpleAdapter saMenuItem = new SimpleAdapter(this, meumList, // 数据源
				R.layout.menuitem, // xml实现
				new String[] { "ItemImage", "ItemText" }, // 对应map的Key
				new int[] { R.id.ItemImage, R.id.ItemText }); // 对应R的Id

		// 添加Item到网格中
		gridview.setAdapter(saMenuItem);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				jumpPoint(arg2);
			}
		});

	}
	
	/**
	 * 进行功能点的跳转
	 * @param arg2
	 */
	public void jumpPoint( int menuId){
		//System.out.println("click index:" + arg2);
		//跳转
		if(0==menuId){
			doGoToRecord();
		}else if(1==menuId){
			doGoToList();
		}else if(2==menuId){
			//创建一个intent对象，表明是两个activity之间的跳转
	    	Intent intent = new Intent(this, WebViewActivity.class);
	    	//跳转
			this.startActivity(intent);
		}else if(3==menuId){
			//创建一个intent对象，表明是两个activity之间的跳转
	    	Intent intent = new Intent(this, CameraActivity.class);
	    	//跳转
			this.startActivity(intent);
		}else if(4==menuId){
		
			logout();
		}else{
//			AlertDialog dialog=new AlertDialog.Builder(this).setPositiveButton("尚未开发,敬请期待!", null)
//			.create();
//			dialog.show();
			
			Toast.makeText(this, "尚未开发,敬请期待!",  
					                   Toast.LENGTH_LONG).show();  

		}
	}
	
	
    /**
     * 跳转到列表页面
     */
    private void doGoToList(){
    	//创建一个intent对象，表明是两个activity之间的跳转
    	Intent intent = new Intent(this, RecordListActivity.class);
    	//参数,类似HashMap的结构
    	intent.putExtra("A_name", this.getClass().toString());
    	//跳转
		this.startActivity(intent);
    }
    
    
    
    /**
     * 跳转到录入页面
     */
    private void doGoToRecord(){
    	//创建一个intent对象，表明是两个activity之间的跳转
    	Intent intent = new Intent(this, RecordActivity.class);
    	//跳转
		this.startActivity(intent);
    }
    
    /**
     * 退出程序的操作
     */
    private void logout(){
    	Builder builder=new AlertDialog.Builder(this);
    	builder.setTitle("提示");
    	builder.setMessage("是否退出系统");
    	builder.setPositiveButton("确定",  new OnClickListener(){
    		@Override
			public void onClick(DialogInterface dialog, int which){
    			android.os.Process.killProcess(android.os.Process.myPid());
			}
    	});
    	builder.setNegativeButton("取消", null);
    	builder.create().show();
    }
    

}
