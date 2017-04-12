
package com.example.view.slidingmenu;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

import cn.app9010.supermarket.R;

public class SlidingMenu extends RelativeLayout implements OnItemClickListener {
	// 菜单栏 位置标识
	public static final int SLIDINGMENU_NONE = 0;
	public static final int SLIDINGMENU_MONITOR = 1;
	public static final int SLIDINGMENU_FRIENDLIST = 2;
	public static final int SLIDINGMENU_DAILYLIST = 3;
	public static final int SLIDINGMENU_GALLERYLIST = 4;
	public static final int SLIDINGMENU_QUESTIONLIST = 5;
	public static final int SLIDINGMENU_FAMILY = 6;
	public static final int SLIDINGMENU_SPLSH = 7;
	public static final int SLIDINGMENU_SETTINGUP = 8;
	public static final int SLIDINGMENU_CLOUD = 9;
	private SlidingView mSlidingView;
	private View mLeftView;
	private Context mContext;

	private SlidingMenuAdapter adapter = null;
//	private ListView listView = null;

	private List<SlidingMenuItem> listItems;

	public SlidingMenu(Context context) {
		super(context);
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setLeftView(View view) {
		mContext = this.getContext();

		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

		int width = dm.widthPixels;

		LayoutParams behindParams = new LayoutParams(width,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);

		addView(view, behindParams);

		mLeftView = view;
		adapter = new SlidingMenuAdapter(this.getContext(), 0);
		adapter.setListItems(listItems);
	}

	public void setCenterView(View view) {
		LayoutParams aboveParams = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);

		mSlidingView = new SlidingView(getContext());
		addView(mSlidingView, aboveParams); 
		mSlidingView.setView(view);
		mSlidingView.invalidate();
		mSlidingView.setLeftView(mLeftView);
		mSlidingView.buildDrawingCache(true);
		mSlidingView.setDrawingCacheEnabled(true);
	}

	public SlidingView getSlidView() {
		return mSlidingView;
	}

	public void showLeftView() {
		mSlidingView.showLeftView();
	}

	public void reloadAdapterData() {
		adapter.setListItems(listItems);
		adapter.reloadData();
	}

	public SlidingView getSlidingView() {
		return mSlidingView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
}
