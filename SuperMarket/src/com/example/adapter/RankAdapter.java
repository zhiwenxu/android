package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.utils.ListViewHolder;

public class RankAdapter extends BaseAdapter {
	private Context mContext;
	private String rules[];
	public RankAdapter(Context context){
		this.mContext = context;
	}
	public void setRules(String rule[]){
		this.rules = rule;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(rules == null || rules.length == 0){
			return 0;
		}
		return rules.length;
	}
	

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(v == null){
			v = LayoutInflater.from(mContext).inflate(R.layout.rank_list_item, null);
		}
		TextView one = ListViewHolder.get(v, R.id.one);
		TextView two = ListViewHolder.get(v, R.id.two);
		one.setText(rules[pos].split(":")[0]);
		two.setText(rules[pos].split(":")[1]);
		return v;
	}

}
