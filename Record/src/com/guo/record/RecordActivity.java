package com.guo.record;

import com.guo.db.DatabaseHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RecordActivity extends Activity {
	
	private EditText etContent;
	private TextView tvMytextView;
	Button btnSaveInfo;
	Button btnInfoList;
	AlertDialog dialog;
	
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recorder);
		etContent = (EditText) this.findViewById(R.id.et_content);
		btnSaveInfo = (Button) this.findViewById(R.id.save_info);
		btnInfoList=(Button)this.findViewById(R.id.info_list);
		dialog = new AlertDialog.Builder(this).setPositiveButton("确定", null)
				.create();

		etContent.setOnKeyListener(new EditText.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				tvMytextView.setText(etContent.getText().toString());
				return false;
			}
		});

		btnSaveInfo.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (validate()) {
					createDb(etContent.getText().toString());	
					//dialog.setTitle("保存成功！");s
					dialog.setMessage("保存成功！");
					dialog.show();
				}
	           
			}
		});
		
		//跳转到列表页面
		btnInfoList.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				//dialog.setMessage("进入列表页面！");
				//dialog.show();
				doGoToList();
			}
		});
	}
    
    
    private void doGoToList(){
    	Intent intent = new Intent(this, RecordListActivity.class);
    	//参数
    	intent.putExtra("A_name", this.getClass().toString());
		this.startActivity(intent);
    }
    
    /**
     * 进行内容的校验
     * @return
     */
	public boolean validate() {
		String content = etContent.getText().toString();
		System.out.println("content:"+content);
		if (content == null || "".equals(content)) {
			//dialog.setTitle("内容不能为空！");
			dialog.setMessage("内容不能为空！");
			dialog.show();
			return false;
		}
		return true;
	}
	
	public void createDb(String content) {

		/*
		 * SQLiteDatabase myDb = SQLiteDatabase.create(new CursorFactory() {
		 * 
		 * @Override public Cursor newCursor(SQLiteDatabase db,
		 * SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query)
		 * {
		 * 
		 * return null; } });
		
 */
		try {
			SQLiteDatabase myDataBase = this.openOrCreateDatabase(
					"myDataBase.db", MODE_PRIVATE, new CursorFactory() {
						// 创建新的数据库，名称myDatabase，模式MODE_PRIVATE，鼠标工厂
						// 工厂类，一个可选工厂类，当查询时调用来实例化一个光标
						@Override
						public Cursor newCursor(SQLiteDatabase db,
								SQLiteCursorDriver masterQuery,
								String editTable, SQLiteQuery query) {
							return null;
						}
					});
			//判断这张表存在不？
			 DatabaseHelper dbHelper= DatabaseHelper.getDatabaseHelper(this);
			 if(!dbHelper.tabbleIsExist("test")){
					myDataBase.execSQL(" CREATE TABLE test (_id INTEGER PRIMARY KEY,content VARCHAR(10) )  ; ");
			 }
			 
			myDataBase.execSQL(" INSERT INTO test (content) values('"+content+"');");
			
			myDataBase.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// myDb=SQLiteDatabase.openOrCreateDatabase(file, factory);
	}
	
	
	
	
	
}