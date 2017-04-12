package com.example.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.network.bean.XfRecordBean;
import com.example.utils.ListViewHolder;

public class RecordAdapter extends BaseAdapter {

	private Context mContext;
	private XfRecordBean bean;

	public RecordAdapter(Context context, XfRecordBean bean) {
		this.mContext = context;
		this.bean = bean;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bean.getData().getTotal_count();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(view == null){
			view = LayoutInflater.from(mContext).inflate(R.layout.record_list_item,
					null);
		}
		
		TextView recordNum = ListViewHolder.get(view, R.id.record_num);
		TextView recordAddress = ListViewHolder.get(view,R.id.record_address);
		TextView recordTime = ListViewHolder.get(view,R.id.record_time);
		recordNum.setText("Ö§¸¶£¤"
				+ String .format("%.2f",bean.getData().getValue().get(pos).getAmount()/100.0f));
		recordAddress
				.setText(bean.getData().getValue().get(pos).getStore());
		recordTime.setText(bean.getData().getValue().get(pos).getDate());
		
		return view;
	}

}
