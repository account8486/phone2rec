package com.guo.adapter;

import java.util.Vector;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.guo.db.DatabaseHelper;
import com.guo.model.RecordModel;
import com.guo.record.R;
import com.guo.record.RecordListActivity;

public class MyAdapter extends BaseAdapter {
	private Context mContext;
	public Vector<RecordModel> mReord;
	RecordModel rm;
	
	public Vector<RecordModel> getmReord() {
		return mReord;
	}


	public MyAdapter(Context context) {
		mContext = context;
	}

	public void setData(Vector<RecordModel> data) {
		mReord = data;
	}

	class ViewHolder {
		public TextView title;
		public TextView content; 
		public ImageView imgBtnDel;
	}

	@Override
	public int getCount() {
		if (mReord != null) {
			return mReord.size();
		}

		return 0;
	}

	@Override
	public long getItemId(int location) {
		return mReord.get(location).getId();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder vh = null;
		if (view == null) {
			vh = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					R.layout.recorder_item, null);
			//vh.id = (TextView) view.findViewById(com.guo.record.R.id.v_id);
			//不可见
			//vh.id.setVisibility(View.GONE);
			vh.title = (TextView) view
					.findViewById(R.id.v_title);
			
			//加粗
			vh.title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		
			vh.content=(TextView)view.findViewById(R.id.v_content);
			vh.imgBtnDel=(ImageView)view.findViewById(R.id.imgBtnDel);
			
			
			vh.imgBtnDel.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					//int index = (Integer) v.getTag();
				
				}
				
			});
		} else {
			vh = (ViewHolder) view.getTag();
		}
		/**
		if(position%2==1){
			view.setBackgroundColor(Color.GRAY);
			vh.content.setBackgroundColor(Color.GRAY);
		}else{
			view.setBackgroundColor(Color.GREEN);
			vh.content.setBackgroundColor(Color.GREEN);
		}**/
		view.setBackgroundColor(Color.LTGRAY);
		vh.content.setBackgroundColor(Color.WHITE);
		rm = mReord.elementAt(position);
		//vh.id.setText(String.valueOf(rm.getId()));
		vh.title.setText(rm.getTitle()+"("+rm.getRecordTime()+")");
		//设置背景色
		//vh.id.setTextColor(Color.BLACK);
		vh.title.setTextColor(Color.BLACK);
		vh.content.setText(rm.getContent());
	
		view.setTag(vh);
		return view;
	}
	
	public void willDelRecord(View v){
		
	
		//进行赋值
		AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
		builder.setTitle("提示");
		builder.setMessage("是否删除备忘记录?");
		
		/**
		builder.setPositiveButton("确认", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				//删除信息

			
			    
			}
		});
		
		builder.setNegativeButton("取消", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				//TODO 暂时啥都不用做
			}
		});
		*/
		
		builder.create().show();
	
	}

	@Override
	public Object getItem(int location) {
		return mReord.get(location);
	}
	


}
