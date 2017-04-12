package com.example.viewhelper;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.listener.OnBarPressListener;

public class SyBarHelper implements OnClickListener{
	
	private Context mContext;
	private OnBarPressListener mListener;
	private RelativeLayout mCxBtn,mXpBtn,mGsBtn;
	private ImageView mCxImg,mXpImg,mGsImg;
	private TextView mCxTv,mXpTv,mGsTv;
	private View mCxv,mXpv,mGsv;
	
	public void setOnBarPressListener(OnBarPressListener listener){
		this.mListener = listener;
	}
	public SyBarHelper(Context context){
		this.mContext = context;
	}
	public void setView(View view){
		mCxBtn = (RelativeLayout)view.findViewById(R.id.cx_btn);
		mXpBtn = (RelativeLayout)view.findViewById(R.id.xp_btn);
		mGsBtn = (RelativeLayout)view.findViewById(R.id.gs_btn);
		
		mCxImg = (ImageView)view.findViewById(R.id.cx_img);
		mXpImg = (ImageView)view.findViewById(R.id.xp_img);
		mGsImg = (ImageView)view.findViewById(R.id.gs_img);
		
		mCxTv = (TextView)view.findViewById(R.id.cx_tv);
		mXpTv = (TextView)view.findViewById(R.id.xp_tv);
		mGsTv = (TextView)view.findViewById(R.id.gs_tv);
		
		mCxv = view.findViewById(R.id.cx_view);
		mXpv = view.findViewById(R.id.xp_view);
		mGsv = view.findViewById(R.id.gs_view);
		
		mCxBtn.setOnClickListener(this);
		mXpBtn.setOnClickListener(this);
		mGsBtn.setOnClickListener(this);
	}
	
	public void changeView(int code){
		mCxTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		mXpTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		mGsTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		
		mCxImg.setBackgroundResource(R.drawable.cshd);
		mXpImg.setBackgroundResource(R.drawable.xppc);
		mGsImg.setBackgroundResource(R.drawable.gsxw);
		
		mCxv.setVisibility(View.GONE);
		mXpv.setVisibility(View.GONE);
		mGsv.setVisibility(View.GONE);
		
		switch (code) {
		case 0:
			mCxTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			mCxImg.setBackgroundResource(R.drawable.cshdax);
			mCxv.setVisibility(View.VISIBLE);
			break;
		case 1:
			mXpTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			mXpImg.setBackgroundResource(R.drawable.xppcax);
			mXpv.setVisibility(View.VISIBLE);
			break;
		case 2:
			mGsTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			mGsImg.setBackgroundResource(R.drawable.gsxwax);
			mGsv.setVisibility(View.VISIBLE);
			break;
		}
		if(mListener != null)
			mListener.onPress(code);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mCxBtn){
			changeView(0);
		}
		if(v == mXpBtn){
			changeView(1);
		}
		if(v == mGsBtn){
			changeView(2);
		}
	}

}
