package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.main.MainActivity;
import com.example.utils.ImageLoaderUtil;
import com.example.utils.SharePerencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChangeActivity extends BaseActivity implements OnClickListener{
	private Button mLoginBtn,mRegisterBtn,mVisitorLoginBtn;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageView mChangeImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		init();
	}
	
	public void init(){
		mLoginBtn = (Button)findViewById(R.id.login_btn);
		mRegisterBtn = (Button)findViewById(R.id.register_btn);
		mVisitorLoginBtn = (Button)findViewById(R.id.visitor_login_btn);
		mChangeImage = (ImageView)findViewById(R.id.change_image);
		mLoginBtn.setOnClickListener(this);
		mRegisterBtn.setOnClickListener(this);
		mVisitorLoginBtn.setOnClickListener(this);
		Log.e("", SharePerencesUtil.getInstances().getChangeImage());
		imageLoader.displayImage(SharePerencesUtil.getInstances().getChangeImage(),
				mChangeImage,ImageLoaderUtil.getInstance().getLoinOptions());
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mLoginBtn){
			Intent intent = new Intent(this,LoginActivity.class);
			startActivity(intent);
			finish();
		}
		if(v == mRegisterBtn){
			Intent intent = new Intent(this,RegisterActivity.class);
			intent.putExtra("isstart", true);
			startActivity(intent);
			finish();
		}
		if(v == mVisitorLoginBtn){
			SharePerencesUtil.getInstances().setID("");
			SharePerencesUtil.getInstances().setUUID("");
			SharePerencesUtil.getInstances().setNick("ÓÎ¿ÍµÇÂ¼");
			SharePerencesUtil.getInstances().setGender(0);
			SharePerencesUtil.getInstances().setCents(0);
			SharePerencesUtil.getInstances().setCard("ÎÞ");
			SharePerencesUtil.getInstances().setRmb(0);
			SharePerencesUtil.getInstances().setHeadUrl("");
			SharePerencesUtil.getInstances().setLogin(false);
			SharePerencesUtil.getInstances().setLoginType(0);
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
