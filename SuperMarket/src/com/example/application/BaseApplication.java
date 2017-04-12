package com.example.application;

import java.util.ArrayList;
import java.util.List;


import cn.jpush.android.api.JPushInterface;

import com.baidu.mapapi.SDKInitializer;
import com.example.main.MainActivity;
import com.example.utils.ImageLoaderUtil;
import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;

public class BaseApplication extends Application {
	
	public static List<Activity> activities = new ArrayList<Activity>();
	public static final String WXAPP_KEY = "wxe43806bd58c4856e";
	public static BaseApplication instance;
	public static List<FragmentActivity> fragmentActivitys = new ArrayList<FragmentActivity>();
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		//图片加载工具类初始化
		ImageLoaderUtil.getInstance().init(this);
		//百度地图SDK初始化
		SDKInitializer.initialize(getApplicationContext());  
		JPushInterface.init(this);     		// 初始化 JPush
	}

	public void addFragmentActivity(FragmentActivity fragmentActivity){
		this.fragmentActivitys.add(fragmentActivity);
	}
	
	public static void FragmentExit(){
		for(FragmentActivity fragmentActivity:fragmentActivitys){
			fragmentActivity.finish();
		}
		for(int i=0;i>fragmentActivitys.size();i++){
			fragmentActivitys.remove(i);
		}
	}
	
	public static BaseApplication getInstance(){
		return instance;
	}
	public void addActivity(Activity avtivity){
		if(!activities.contains(avtivity))
		activities.add(avtivity);
	}
	
	public void removeActivity(Activity activity){
		if(activities.contains(activity)){
			activities.remove(activity);
		}
	}
	public static void exit(){
		for(Activity activity : activities){
			activity.finish();
		}
		for(int i=0;i<activities.size();i++){
			activities.remove(i);
		}
		FragmentExit();
	}
	
	
	
}
