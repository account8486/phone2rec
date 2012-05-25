package com.guo.adapter;

import java.util.Vector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guo.model.RecordModel;

public class MyAdapter extends BaseAdapter {
	private Context mContext;
	public Vector<RecordModel> mReord;
	RecordModel rm;

	public MyAdapter(Context context) {
		mContext = context;
	}

	public void setData(Vector<RecordModel> data) {
		mReord = data;
	}

	class ViewHolder {
		public TextView title;
		public TextView content; 
		public ImageButton imgBtnDel;
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
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder vh = null;
		if (view == null) {
			vh = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					com.guo.record.R.layout.recorder_item, null);
			//vh.id = (TextView) view.findViewById(com.guo.record.R.id.v_id);
			//不可见
			//vh.id.setVisibility(View.GONE);
			vh.title = (TextView) view
					.findViewById(com.guo.record.R.id.v_title);
			//加粗
			vh.title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			//vh.title.set
			vh.content=(TextView)view.findViewById(com.guo.record.R.id.v_content);
			vh.imgBtnDel=(ImageButton)view.findViewById(com.guo.record.R.id.imgBtnDel);
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
		vh.title.setText(rm.getTitle());
		//设置背景色
		//vh.id.setTextColor(Color.BLACK);
		vh.title.setTextColor(Color.BLACK);
		vh.content.setText(rm.getContent());
		vh.imgBtnDel.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				willDelRecord(v,rm.getId());
			}
		});
		view.setTag(vh);
		return view;
	}
	
	public void willDelRecord(View v,int id){
		AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
		builder.setTitle("提示");
		builder.setMessage("是否删除备忘记录?"+id);
		
		
		
		builder.setPositiveButton("确认", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				//删除信息
				System.out.println();
			}
		});
		
		builder.setNegativeButton("取消", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				//TODO 暂时啥都不用做
			}
		});
		
		builder.create().show();
	
	}

	@Override
	public Object getItem(int location) {
		return mReord.get(location);
	}

}
