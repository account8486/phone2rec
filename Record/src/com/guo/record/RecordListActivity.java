package com.guo.record;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.guo.adapter.MyAdapter;
import com.guo.db.DatabaseHelper;
import com.guo.model.RecordModel;
import com.guo.util.StringUtil;

public class RecordListActivity extends Activity {
	
		EditText etSearch;
		ListView ltView;
		Button btnRecorderSearch;
	
		private int itemId;
		
		public int getItemId() {
			return itemId;
		}

		public void setItemId(int itemId) {
			this.itemId = itemId;
		}

	@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.info_list);
			
			etSearch=(EditText)this.findViewById(R.id.tips);
			etSearch.setHint("标题或内容快速搜索");
			//TODO查出数据库中的信息并进行展示操作
			ltView=(ListView)this.findViewById(R.id.lt_info_list);
			ltView.setBackgroundColor(Color.BLUE);
			//gdView.setAdapter(null);
			
			btnRecorderSearch=(Button)this.findViewById(R.id.btnRecorderSearch);
		
			btnRecorderSearch.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View v){
					//dialog.setMessage("进入列表页面！");
					//dialog.show();
					doSearch();
				}
			});
			
			this.initDbListToControl(ltView,null);
			
			
		ltView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long arg3) {
				
				//因为无法在builder的监听传递id参数到方法里面，所以只能采用一个简介的方式。
				//应该还是调用的不得法！
				RecordModel rm=(RecordModel)ltView.getItemAtPosition(position);
				setItemId(rm.getId());
				
				AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				builder.setTitle("提示");
				builder.setMessage("是否删除备忘记录:"+rm.getTitle()+"?");
				
				
				
				builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which){
						//删除信息
						/*
						setTitle("点击第" + arg2 + "个项目");
						RecordModel rm=(RecordModel)ltView.getItemAtPosition(arg2);
						deleteRecord(rm.getId());
						initDbListToControl(ltView,null);*/
						deleteRecord();
						initDbListToControl(ltView,null);
						
					}
				});
				
				builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which){
						//TODO 暂时啥都不用做
					}
				});
				
				
				builder.create().show();
				
				
		
				//System.out.println(ltView.getItemAtPosition(arg2));
			}
		});  
		
		
		

	  }
	   
	   
	   
		/**
		 * 删除指定ID的记录
		 * @param id
		 */
		public void deleteRecord(){
			try{
				DatabaseHelper dbHelper=DatabaseHelper.getDatabaseHelper(this);
				SQLiteDatabase db= dbHelper.getWritableDatabase();
				db.execSQL("delete from RECORD where id="+this.getItemId());
				db.close();
				//ListView ltView=(ListView)findViewById(R.id.lt_info_list);
			}catch(Exception e){
				e.printStackTrace();
				
			}
			
			
		}
		
	   
	   /**
	    * 把数据库中的信息查询出来并且展示到界面上去
	    */
	   public void initDbListToControl(ListView ltView,String content){
		  
		   try {
			   
			   DatabaseHelper dbHelper= DatabaseHelper.getDatabaseHelper(this);
			   SQLiteDatabase db=dbHelper.getWritableDatabase();
			   
			    String[] columns=new String[4];
				columns[0]="id";
				columns[1]="title";
				columns[2]="content";
				columns[3]="record_time";
				
				String selection=null;
				if(StringUtil.isNotEmpty(content)){
					selection="  content like  '%"+content+"%' or title like '%"+content+"%' " ;
				}
				
				// Cursor cs=db.rawQuery(" select * from test ", null);
				Cursor cs=db.query("RECORD", columns, selection, null, null, null, " id desc ");
				
				//获取数据库中的数据
				Vector<RecordModel> mReord=new Vector<RecordModel>();
				while(cs.moveToNext()){
					RecordModel recordModel=new RecordModel();
					recordModel.setId(cs.getInt(0));
					recordModel.setTitle(cs.getString(1));
					recordModel.setContent(cs.getString(2));
					recordModel.setRecordTime(cs.getString(3));
					
					mReord.add(recordModel);
				}
				
				db.close();
				
				MyAdapter recorderAdapter=new MyAdapter(this);
				//数据赋值
				recorderAdapter.setData(mReord);
				//数据显示
				ltView.setAdapter(recorderAdapter);
				ltView.setBackgroundColor(Color.BLUE);
				ltView.setVisibility(BIND_AUTO_CREATE);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	   }
	   
	   /**
	    * 进行查询
	    */
	   private void doSearch(){
		   String content =etSearch.getText().toString();
		   if(content!=null&&!"".equals(content)){
			   //进行查询
			   initDbListToControl(this.ltView , content);
		   }else{
			   initDbListToControl(this.ltView , null); 
		   }
	   }
	   
	
	   
	   
	   
}
