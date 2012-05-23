package com.guo.record;

import java.util.Vector;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
	
	   @Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.info_list);
			
			etSearch=(EditText)this.findViewById(R.id.tips);
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
			
	  }
	   
	   
	   /**
	    * 把数据库中的信息查询出来并且展示到界面上去
	    */
	   private void initDbListToControl(ListView ltView,String content){
		  
		   try {
			   
			   DatabaseHelper dbHelper= DatabaseHelper.getDatabaseHelper(this);
			   SQLiteDatabase db=dbHelper.getWritableDatabase();
			   
			    String[] columns=new String[2];
				columns[0]="_id";
				columns[1]="content";
				
				String selection=null;
				if(StringUtil.isNotEmpty(content)){
					selection="  content like  '%"+content+"%'";
				}
				
				// Cursor cs=db.rawQuery(" select * from test ", null);
				Cursor cs=db.query("test", columns, selection, null, null, null, " _id desc ");
				
				//获取数据库中的数据
				Vector<RecordModel> mReord=new Vector<RecordModel>();
				while(cs.moveToNext()){
					RecordModel recordModel=new RecordModel();
					recordModel.setId(cs.getInt(0));
					recordModel.setContent(cs.getString(1));
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
		   }
	   }
	   
	
	   
	   
	   
}
