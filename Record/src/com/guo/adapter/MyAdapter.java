package com.guo.adapter;

import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guo.model.RecordModel;

public class MyAdapter extends BaseAdapter {
	private Context mContext;
	public Vector<RecordModel> mReord;

	public MyAdapter(Context context) {
		mContext = context;
	}

	public void setData(Vector<RecordModel> data) {
		mReord = data;
	}

	class ViewHolder {
		public TextView id;
		public TextView content;
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

			vh.id = (TextView) view.findViewById(com.guo.record.R.id.v_id);
			vh.content = (TextView) view
					.findViewById(com.guo.record.R.id.v_content);

		} else {
			vh = (ViewHolder) view.getTag();
		}
		
		if(position%2==0){
			view.setBackgroundColor(Color.GRAY);
		}else{
			view.setBackgroundColor(Color.BLUE);
		}
		
		RecordModel rm = mReord.elementAt(position);
		vh.id.setText(String.valueOf(rm.getId()));
		vh.content.setText(rm.getContent());

		view.setTag(vh);

		return view;
	}

	@Override
	public Object getItem(int location) {
		return mReord.get(location);
	}

}
