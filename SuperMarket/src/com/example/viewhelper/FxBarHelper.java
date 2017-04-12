package com.example.viewhelper;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.listener.OnBarPressListener;
import com.example.login.LoginActivity;
import com.example.main.BandPhoneActivity;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;

public class FxBarHelper implements OnClickListener{
	private Context mContext;
	private OnBarPressListener mListener;
	private RelativeLayout mZxBtn,mZpBtn,mBkBtn;
	private TextView mZxTv,mZpTv,mBkTv;
	private View mZxv,mZpv,mBkv;
	
	public void setOnBarPressListener(OnBarPressListener listener){
		this.mListener = listener;
	}
	public FxBarHelper(Context context){
		this.mContext = context;
	}
	public void setView(View view){
		
		mZxBtn = (RelativeLayout)view.findViewById(R.id.zx_btn);
		mZpBtn = (RelativeLayout)view.findViewById(R.id.zp_btn);
		mBkBtn = (RelativeLayout)view.findViewById(R.id.bk_btn);
		
		mZxTv = (TextView)view.findViewById(R.id.zx_tv);
		mZpTv = (TextView)view.findViewById(R.id.zp_tv);
		mBkTv = (TextView)view.findViewById(R.id.bk_tv);
		
		mZxv = view.findViewById(R.id.zx_view);
		mZpv = view.findViewById(R.id.zp_view);
		mBkv = view.findViewById(R.id.bk_view);
		
		mZxBtn.setOnClickListener(this);
		mZpBtn.setOnClickListener(this);
		mBkBtn.setOnClickListener(this);
	}
	
	public void changeView(int code){
		mZxTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		mZpTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		mBkTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		mZxv.setVisibility(View.GONE);
		mZpv.setVisibility(View.GONE);
		mBkv.setVisibility(View.GONE);
		switch (code) {
		case 0:
			mZxTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			mZxv.setVisibility(View.VISIBLE);
			break;
		case 1:
			mZpTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			mZpv.setVisibility(View.VISIBLE);
			break;
		case 2:
			mBkTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			mBkv.setVisibility(View.VISIBLE);
			break;
		}
		if(mListener != null)
			mListener.onPress(code);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mZxBtn){
			changeView(0);
		}
		if(v == mZpBtn){
			changeView(1);
		}
		if(v == mBkBtn){
			if(!SharePerencesUtil.getInstances().getLogin()){
				Intent intent = new Intent(mContext,LoginActivity.class);
				mContext.startActivity(intent);
				return;
			}
			//第三方登陆后，首先判断是否绑定手机
			if(SharePerencesUtil.getInstances().getID().equals("")){
				Intent intent = new Intent(mContext,BandPhoneActivity.class);
				mContext.startActivity(intent);
				return;
			}
			changeView(2);
		}
	}
}
