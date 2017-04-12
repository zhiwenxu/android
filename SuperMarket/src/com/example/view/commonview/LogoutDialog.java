package com.example.view.commonview;


import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.application.BaseApplication;
import com.example.login.LoginActivity;
import com.example.utils.SharePerencesUtil;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LogoutDialog extends BaseActivity implements OnClickListener{

	private TextView mLogoutBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logout_dialog_layout);
		init();
	}
	
	private void init(){
		mLogoutBtn = (TextView)findViewById(R.id.logout_btn);
		mLogoutBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mLogoutBtn){
			finish();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		exit();
	}
	private void exit(){
		Intent intent = new Intent();
		//退出登陆，清空登陆信息
		BaseApplication.exit();
		SharePerencesUtil.getInstances().setNick("");
		SharePerencesUtil.getInstances().setGender(0);
		SharePerencesUtil.getInstances().setCents(0);
		SharePerencesUtil.getInstances().setCard("");
		SharePerencesUtil.getInstances().setRmb(0);
		SharePerencesUtil.getInstances().setLogin(false);
		SharePerencesUtil.getInstances().setLoginType(0);
		SharePerencesUtil.getInstances().setPassWord("");
		SharePerencesUtil.getInstances().setUUID("");
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.cancelAll();
	}

}
