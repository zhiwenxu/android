package com.example.viewhelper;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.listener.OnBarPressListener;

public class CouponBarHelper implements OnClickListener{

	
	private Context mContext;
	private OnBarPressListener mListener;
	private TextView mAllTv,mWuseTv,mUseTv,mGqTv;
	private View mAllv,mWusev,mUsev,mGqv;
	private RelativeLayout mAllBtn,mWuseBtn,mUseBtn,mGqBtn;
	
	public void setView(View view){
		
		mAllBtn = (RelativeLayout)view.findViewById(R.id.all_btn);
		mWuseBtn = (RelativeLayout)view.findViewById(R.id.wuse_btn);
		mUseBtn = (RelativeLayout)view.findViewById(R.id.use_btn);
		mGqBtn = (RelativeLayout)view.findViewById(R.id.gq_btn);
		
		mAllTv = (TextView)view.findViewById(R.id.all_tv);
		mWuseTv = (TextView)view.findViewById(R.id.wuse_tv);
		mUseTv = (TextView)view.findViewById(R.id.use_tv);
		mGqTv = (TextView)view.findViewById(R.id.gq_tv);
		
		mAllv = view.findViewById(R.id.all_view);
		mWusev = view.findViewById(R.id.wuse_view);
		mUsev = view.findViewById(R.id.use_view);
		mGqv = view.findViewById(R.id.gq_view);
		
		mAllBtn.setOnClickListener(this);
		mWuseBtn.setOnClickListener(this);
		mUseBtn.setOnClickListener(this);
		mGqBtn.setOnClickListener(this);
	}
	
	private void changeView(int code){
		
		mAllTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		mWuseTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		mUseTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		mGqTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		
		mAllv.setVisibility(View.GONE);
		mWusev.setVisibility(View.GONE);
		mUsev.setVisibility(View.GONE);
		mGqv.setVisibility(View.GONE);
		
		switch (code) {
		case 0:
			mAllTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			mAllv.setVisibility(View.VISIBLE);
			break;
		case 1:
			mWuseTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			mWusev.setVisibility(View.VISIBLE);
			break;
		case 2:
			mUseTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			mUsev.setVisibility(View.VISIBLE);
			break;
		case 3:
			mGqTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			mGqv.setVisibility(View.VISIBLE);
			break;
		}
		if(mListener != null)
			mListener.onPress(code);
		
	}
	public void setOnBarPressListener(OnBarPressListener listener){
		this.mListener = listener;
	}
	public CouponBarHelper(Context context){
		this.mContext = context;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mAllBtn){
			changeView(0);
		}
		if(v == mWuseBtn){
			changeView(1);
		}
		if(v == mUseBtn){
			changeView(2);
		}
		if(v == mGqBtn){
			changeView(3);
		}
	}
}
