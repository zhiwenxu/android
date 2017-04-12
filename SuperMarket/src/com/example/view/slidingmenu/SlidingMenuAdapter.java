/**  
 * Copyright © 2014 Leature Technology Co., Ltd.. All rights reserved.
 *
 * @Title: SlidingMenuAdapter.java
 * @Prject: iStarBaby
 * @Package: cn.leature.istarbaby.slidingmenu
 * @Description: TODO
 * @author: Administrator  
 * @date: 2014-6-11 下午12:11:10
 * @version: V1.0  
 */
package com.example.view.slidingmenu;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SlidingMenuAdapter extends ArrayAdapter<Object> {

	private List<SlidingMenuItem> listItems = null;


	private Context mContext;

	public SlidingMenuAdapter(Context context, int resource) {
		super(context, resource);


		this.mContext = context;
	}

	public void setListItems(List<SlidingMenuItem> listItems) {
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean isEnabled(int position) {

		SlidingMenuItem item = listItems.get(position);

		if (item.isHeader() || item.isFooter()) {
			return false;
		}

		if (item.isGroupTag()) {
			return false;
		}

		return super.isEnabled(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return convertView;
	}

	public void reloadData() {
		notifyDataSetChanged();
	}
}
