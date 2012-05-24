package com.guo.record;

import com.guo.db.DatabaseHelper;
import com.guo.util.StringUtil;
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
	private TextView etTitle;
	Button btnSaveInfo;
	Button btnCancel;
	AlertDialog dialog;
	
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		//初始化界面,指明显示的主界面为recorder的xml文件
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.recorder);
		//通过id查出标题
		etTitle=(EditText)this.findViewById(R.id.et_title);
		etTitle.setHint("请输入备忘标题");
		//内容
		etContent = (EditText) this.findViewById(R.id.et_content);
		etContent.setHint("请输入备忘内容");
		//保存按钮
		btnSaveInfo = (Button) this.findViewById(R.id.save_info);
		//取消
		btnCancel=(Button)this.findViewById(R.id.btn_cancel);
		
		//消息弹出框
		dialog = new AlertDialog.Builder(this).setPositiveButton("确定", null)
				.create();

		
		etContent.setOnKeyListener(new EditText.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return false;
			}
		});

		//保存动作
		btnSaveInfo.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//判断是否为空
				if (validate()) {
					if(createDb(etTitle.getText().toString(),etContent.getText().toString())){
						dialog.setMessage("保存成功！");
						dialog.show();
						
						//进行跳转
						doGoToList();
					}else{
						dialog.setMessage("保存失败！请检查程序逻辑！");
						dialog.show();
					}
				}
			}
		});
		
		//跳转到列表页面
		btnCancel.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				doGoToIndex();
			}
		});
	}
    
    /**
     * 跳转到列表页面
     */
    private void doGoToList(){
    	//创建一个intent对象，表明是两个activity之间的跳转
    	Intent intent = new Intent(this,RecordListActivity.class);
    	//参数,类似HashMap的结构
    	//intent.putExtra("A_name", this.getClass().toString());
    	//跳转
		this.startActivity(intent);
    }
    
    
    /**
     * 跳转到首页九宫格
     */
    private void doGoToIndex(){
    	//创建一个intent对象，表明是两个activity之间的跳转
    	Intent intent = new Intent(this, IndexActivity.class);
    	//参数,类似HashMap的结构
    	intent.putExtra("A_name", this.getClass().toString());
    	//跳转
		this.startActivity(intent);
    }
    
    /**
     * 进行内容的校验
     * @return
     */
	public boolean validate() {
		String title = etTitle.getText().toString();
		String content = etContent.getText().toString();
		System.out.println("content:"+content);
		if (StringUtil.isEmpty(title)) {
			dialog.setMessage("标题不能为空！");
			dialog.show();
			return false;
		}
		
		if (StringUtil.isEmpty(content)) {
			dialog.setMessage("内容不能为空！");
			dialog.show();
			return false;
		}
		return true;
	}
	
	/**
	 * 保存操作
	 * @param content
	 * @return
	 */
	public boolean createDb(String title,String content) {

		try {
			SQLiteDatabase myDataBase = this.openOrCreateDatabase(
					DatabaseHelper.DATABASE_NAME, MODE_PRIVATE, new CursorFactory() {
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
			 if(!dbHelper.tabbleIsExist("RECORD")){
				 	//创建表
					myDataBase.execSQL(" CREATE TABLE RECORD (id INTEGER PRIMARY KEY,title text,content text )  ; ");
			 }
			//插入数据
			myDataBase.execSQL(" INSERT INTO RECORD (title,content) values('"+title+"','"+content+"');");
			
			myDataBase.close();
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	
	
	
	
}