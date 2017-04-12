package com.example.adapter;

import cn.app9010.supermarket.R;

import com.example.network.bean.NumericalBean;
import com.example.network.bean.RecordBean;
import com.example.utils.ListViewHolder;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NumericalAdapter extends BaseAdapter {

	private Context mContext;
	private NumericalBean bean;
	public NumericalAdapter(Context context,NumericalBean bean){
		this.mContext = context;
		this.bean = bean;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bean.getData().getTotal_count();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.numerical_list_item, null);
		}
		
		TextView numericalNum = ListViewHolder.get(convertView, R.id.numerical_num);
		TextView numericalDetail = ListViewHolder.get(convertView, R.id.numerical_detail);
		TextView numericalTime = ListViewHolder.get(convertView, R.id.numerical_time);
		numericalNum.setText(bean.getData().getValue().get(position).getAmount()+"»ý·Ö");
		numericalDetail.setText(bean.getData().getValue().get(position).getReason());
		numericalTime.setText(bean.getData().getValue().get(position).getDate());
		
		return convertView;
	}

}
