package com.example.viewhelper;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.listener.OnBarPressListener;
import com.example.main.BandPhoneActivity;
import com.example.utils.SharePerencesUtil;

public class FragmentHelper implements OnClickListener{
	
	private LinearLayout syBtn,fxBtn,mdBtn,wdBtn;
	private ImageView mScanBtn;
	private ImageView syImg,fxImg,mdImg,wdImg;
	private TextView syTv,fxTv,mdTv,wdTv;
	private Context mContext;
	private OnBarPressListener mListener;
	
	public FragmentHelper(Context context){
		this.mContext = context;
	}
	public void setView(View view){
		syBtn = (LinearLayout)view.findViewById(R.id.sy_btn);
		fxBtn = (LinearLayout)view.findViewById(R.id.fx_btn);
		mdBtn = (LinearLayout)view.findViewById(R.id.md_btn);
		wdBtn = (LinearLayout)view.findViewById(R.id.wd_btn);
		syImg = (ImageView)view.findViewById(R.id.sy_img);
		fxImg = (ImageView)view.findViewById(R.id.fx_img);
		mdImg = (ImageView)view.findViewById(R.id.md_img);
		wdImg = (ImageView)view.findViewById(R.id.wd_img);
		mScanBtn = (ImageView)view.findViewById(R.id.sys_btn);
		syTv = (TextView)view.findViewById(R.id.sy_tv);
		fxTv = (TextView)view.findViewById(R.id.fx_tv);
		mdTv = (TextView)view.findViewById(R.id.md_tv);
		wdTv = (TextView)view.findViewById(R.id.wd_tv);
		syBtn.setOnClickListener(this);
		fxBtn.setOnClickListener(this);
		mdBtn.setOnClickListener(this);
		wdBtn.setOnClickListener(this);
		mScanBtn.setOnClickListener(this);
	}
	public void changeColor(int code){
		if(code != 4){
			syImg.setBackgroundResource(R.drawable.sy);
			fxImg.setBackgroundResource(R.drawable.fx);
			mdImg.setBackgroundResource(R.drawable.md);
			wdImg.setBackgroundResource(R.drawable.wd);
			syTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
			fxTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
			mdTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
			wdTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_gray));
		}
		switch (code) {
		case 0:
			syImg.setBackgroundResource(R.drawable.syax);
			syTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			if(mListener != null){
				mListener.onPress(0);
			}
			break;
		case 1:
			fxImg.setBackgroundResource(R.drawable.fxax);
			fxTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			if(mListener != null){
				mListener.onPress(1);
			}
			break;
		case 2:
			mdImg.setBackgroundResource(R.drawable.mdax);
			mdTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			if(mListener != null){
				mListener.onPress(2);
			}
			break;
		case 3:
			wdImg.setBackgroundResource(R.drawable.wdax);
			wdTv.setTextColor(mContext.getResources().getColor(R.color.bottom_bar_tv_color_red));
			if(mListener != null){
				mListener.onPress(3);
			}
			break;
		case 4:
			if(mListener != null){
				mListener.onPress(4);
			}
			break;
		}
	}
	public void setOnBarPressListener(OnBarPressListener listener){
		this.mListener = listener;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == syBtn){
			changeColor(0);
		}
		if(v == fxBtn){
			changeColor(1);
		}
		if(v == mdBtn){
			changeColor(2);
		}
		if(v == wdBtn){
			changeColor(3);
		}
		if(v == mScanBtn){
			changeColor(4);
		}
	}
}
