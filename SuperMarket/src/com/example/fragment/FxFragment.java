package com.example.fragment;


import cn.app9010.supermarket.R;

import com.example.adapter.FxTabAdapter;
import com.example.adapter.TabAdapter;
import com.example.listener.OnBarPressListener;
import com.example.login.LoginActivity;
import com.example.main.BandPhoneActivity;
import com.example.utils.SharePerencesUtil;
import com.example.viewhelper.FxBarHelper;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi") 
public class FxFragment extends Fragment implements OnBarPressListener{
	private ViewPager mViewPager;
	private FxBarHelper helper; 
	private FxTabAdapter tabAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_fx, null);
		init(view);
		return view;
	}
	public void init(View view){
		mViewPager = (ViewPager)view.findViewById(R.id.view_pager_fx);
		helper = new FxBarHelper(getActivity());
		helper.setView(view);
		helper.setOnBarPressListener(this);
		String lybk = "http://119.29.249.194/weibo/9010/zd.html?user_name="
				+SharePerencesUtil.getInstances().getNick()+"&user_img="+
				SharePerencesUtil.getInstances().getHeadUrl()+"&mobile="+
				SharePerencesUtil.getInstances().getID();
		tabAdapter = new FxTabAdapter(((FragmentActivity) getActivity()).getSupportFragmentManager());
		String url[] = {
				SharePerencesUtil.getInstances().getGSZX(),
				SharePerencesUtil.getInstances().getZPXX(),lybk
		};
		tabAdapter.setURL(url);
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setAdapter(tabAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int pager) {
				// TODO Auto-generated method stub
				if(pager == 2){
					if(!SharePerencesUtil.getInstances().getLogin()){
						Intent intent = new Intent(getActivity(),LoginActivity.class);
						startActivity(intent);
						mViewPager.setCurrentItem(pager-1);
						return;
					}
					//第三方登陆后，首先判断是否绑定手机
					if(SharePerencesUtil.getInstances().getID().equals("")){
						Intent intent = new Intent(getActivity(),BandPhoneActivity.class);
						startActivity(intent);
						mViewPager.setCurrentItem(pager-1);
						return;
					}
				}
				helper.changeView(pager);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void onPress(int code) {
		mViewPager.setCurrentItem(code);
	}
}
