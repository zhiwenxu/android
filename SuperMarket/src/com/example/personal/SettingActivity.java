package com.example.personal;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.application.BaseApplication;
import com.example.login.LoginActivity;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SettingActivity extends BaseActivity implements OnClickListener,VollerCallBack{
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private RelativeLayout mClearBtn,mExitBtn,mQuestionBtn,mYhTkBtn,mAboutUsBtn;
	private RelativeLayout mFeedBackBtn;
	private VolleyNet net;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initTitle();
		init();
	}
	
	private void init(){
		mFeedBackBtn = (RelativeLayout)findViewById(R.id.feedback);
		mExitBtn = (RelativeLayout)findViewById(R.id.exit_btn);
		mClearBtn = (RelativeLayout)findViewById(R.id.clear_btn);
		mQuestionBtn = (RelativeLayout)findViewById(R.id.question_btn);
		mYhTkBtn = (RelativeLayout)findViewById(R.id.yhtk_btn);
		mAboutUsBtn = (RelativeLayout)findViewById(R.id.aboutus_btn);
		
		mQuestionBtn.setOnClickListener(this);
		mYhTkBtn.setOnClickListener(this);
		mAboutUsBtn.setOnClickListener(this);
		mFeedBackBtn.setOnClickListener(this);
		mExitBtn.setOnClickListener(this);
		mClearBtn.setOnClickListener(this);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
	}
	private void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText("APP设置");
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
		if(v == mQuestionBtn){
			Intent intent = new Intent(this,WebViewActivity.class);
			intent.putExtra("url", SharePerencesUtil.getInstances().getCJWT());
			startActivity(intent);
		}
		if(v == mYhTkBtn){
			Intent intent = new Intent(this,WebViewActivity.class);
			intent.putExtra("url", SharePerencesUtil.getInstances().getYHTK());
			startActivity(intent);
		}
		if(v == mAboutUsBtn){
			Intent intent = new Intent(this,WebViewActivity.class);
			intent.putExtra("url", SharePerencesUtil.getInstances().getGYWM());
			startActivity(intent);
		}
		if(v == mFeedBackBtn){
			Intent intent = new Intent(this,FeedBackActivity.class);
			startActivity(intent);
		}
		if(v == mClearBtn){
			imageLoader.clearDiskCache();
			imageLoader.clearMemoryCache();
			ToastUtil.show(this, "清除缓存成功");
		}
		if(v == mExitBtn){
			Map<String, String> map = new HashMap<String, String>();
			map.put("uuid", SharePerencesUtil.getInstances().getUUID());
			net.Post(ServerURL.LOGOUT_URL,map,0);
		}
	}

	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 0){
			try {
				JSONObject object = new JSONObject(result);
				int code = object.getInt("code");
				if(code == 1){
					//退出登陆，清空登陆信息
					BaseApplication.exit();
					Intent intent = new Intent(this,LoginActivity.class);
					SharePerencesUtil.getInstances().setNick("");
					SharePerencesUtil.getInstances().setGender(0);
					SharePerencesUtil.getInstances().setCents(0);
					SharePerencesUtil.getInstances().setCard("");
					SharePerencesUtil.getInstances().setRmb(0);
					SharePerencesUtil.getInstances().setLogin(false);
					SharePerencesUtil.getInstances().setPassWord("");
					SharePerencesUtil.getInstances().setUUID("");
					SharePerencesUtil.getInstances().setLoginType(0);
					startActivity(intent);
				}
				else
				{
					ToastUtil.show(this, "登出失败");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
}
