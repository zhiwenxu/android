package com.example.login;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;

/**
 * 忘记密码，确认手机号页面
 *
 */
public class ComfirePhoneActivity extends BaseActivity implements OnClickListener,VollerCallBack{

	private EditText mPhoneEdit,mCodeEdit;
	private Button mComfireBtn;
	private TextView mYzmBtn;
	private LoadingDialog dialog = null;
	private VolleyNet net;
	private Timer timer; 
	private int second = 0;
	private TimerTask task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comfire_phone);
		initTitle();
		init();
	}
	
	private void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText("验证手机号");
		rightView.setVisibility(View.INVISIBLE);
		leftView.setOnClickListener(this);
	}
	
	private void init(){
		mPhoneEdit = (EditText)findViewById(R.id.phone_edit);
		mCodeEdit = (EditText)findViewById(R.id.code_edit);
		mComfireBtn = (Button)findViewById(R.id.comfire_btn);
		mYzmBtn = (TextView)findViewById(R.id.yzm);
		mComfireBtn.setOnClickListener(this);
		mYzmBtn.setOnClickListener(this);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
	}
	/*
	 * 获取验证码
	 */
	public void getCode(){
		String phone = mPhoneEdit.getText().toString();
		if(!phone.equals("")){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("phone",phone);
			map.put("usertype", ""+4);
			net.Post(ServerURL.VERIFICATION_CODE_URL, map, 0);
		}
		else
		{
			ToastUtil.show(this, "手机号不能为空！");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
		if(v == mYzmBtn){
			getCode();//获取验证码
			String phone = mPhoneEdit.getText().toString();
			if(phone.equals("")){
				return;
			}
			mYzmBtn.setClickable(false);
			timer = new Timer();
			task = new TimerTask() { 
			    @Override
			    public void run() { 
			      runOnUiThread(new Runnable() {   // UI thread
			        @Override
			        public void run() { 
			        	mYzmBtn.setText((60-second)+"秒");
			         if(second >= 60){
			        	 timer.cancel();
			        	 mYzmBtn.setText("获取验证码");
			        	 mYzmBtn.setClickable(true);
			        	 second = 0;
			        	 return;
			         }
			         second ++;
			        } 
			      }); 
			    }
			};
			timer.schedule(task,1,1000);
		}
		if(v == mComfireBtn){
			String phone = mPhoneEdit.getText().toString();
			String code = mCodeEdit.getText().toString();
			if(phone.equals("")){
				ToastUtil.show(this,"手机号不能为空");
				return;
			}
			if(code.equals("")){
				ToastUtil.show(this,"验证码不能为空");
				return;
			}
			Intent intent = new Intent(this,ResetPwdActivity.class);
			intent.putExtra("phone", phone);
			intent.putExtra("code", code);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 0){
			
		}
	}

	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
}
