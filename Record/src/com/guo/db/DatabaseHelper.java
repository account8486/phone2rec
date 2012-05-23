package com.guo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "record.db";
	private static final int DATABASE_VERSION = 1;
	private static DatabaseHelper mDatabaseHelper = null;

	
	public static DatabaseHelper getDatabaseHelper(Context context) {
		if (mDatabaseHelper == null) {
			mDatabaseHelper = new DatabaseHelper(context);
		}
		return mDatabaseHelper;
	}

	// 构造方法：
	DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}
	
	public Cursor query(String selection) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query("test", null, null, null, null, null,
				null);
		
		return cursor;
	}  
	
	/**
     * 判断某张表是否存在
     * @param tabName 表名
     * @return
     */
    public boolean tabbleIsExist(String tableName){
            boolean result = false;
            if(tableName == null){
                    return false;
            }
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                    db = this.getReadableDatabase();
                    String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"+tableName.trim()+"' ";
                    cursor = db.rawQuery(sql, null);
                    if(cursor.moveToNext()){
                            int count = cursor.getInt(0);
                            if(count>0){
                                    result = true;
                            }
                    }
                    
                    db.close();
            } catch (Exception e) {
                    // TODO: handle exception
            }                
            return result;
    }



}
