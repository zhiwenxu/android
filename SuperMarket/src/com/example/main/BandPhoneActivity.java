package com.example.main;

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
import cn.jpush.android.api.JPushInterface;

import com.example.application.BaseActivity;
import com.example.application.BaseApplication;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.BandBean;
import com.example.service.JPushServices;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;

public class BandPhoneActivity extends BaseActivity implements OnClickListener,VollerCallBack{
	
	private EditText mPhoneEdit,mCodeEdit;
	private Button mBandBtn;
	private TextView mYzmBtn;
	private VolleyNet net;
	private LoadingDialog dialog = null;
	private Timer timer; 
	private int second = 0;
	private TimerTask task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_band_phone);
		initTitle();
		init();
	}
	
	private void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText("绑定手机号");
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	
	private void init(){
		mPhoneEdit = (EditText)findViewById(R.id.phone_edit);
		mCodeEdit = (EditText)findViewById(R.id.code_edit);
		mBandBtn = (Button)findViewById(R.id.band_btn);
		mYzmBtn = (TextView)findViewById(R.id.yzm);
		mBandBtn.setOnClickListener(this);
		net = new VolleyNet();
		mYzmBtn.setOnClickListener(this);
	}
	
	private void getCode(){
		net.setOnVellerCallBack(this);
		String phone = mPhoneEdit.getText().toString();
		if(phone.equals("")){
			ToastUtil.show(this, "手机号不能为空");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phone",phone);
		map.put("usertype", ""+2);
		net.Post(ServerURL.VERIFICATION_CODE_URL, map, 1);
	}
	
	private void band(){
		net.setOnVellerCallBack(this);
		String phone = mPhoneEdit.getText().toString();
		String code= mCodeEdit.getText().toString();
		if(phone.equals("")){
			ToastUtil.show(this, "手机号不能为空");
			return;
		}
		if(code.equals("")){
			ToastUtil.show(this, "验证码不能为空");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phone",phone);
		map.put("code",code);
		map.put("openid",SharePerencesUtil.getInstances().getOpenid());
		map.put("type",SharePerencesUtil.getInstances().getLoginType()+"");
		dialog = new LoadingDialog(this);
		dialog.show("绑定中...", true, null);
		dialog.show();
		net.Post(ServerURL.BAND_URL, map, 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
		if(v == mBandBtn){
			band();
		}
		if(v == mYzmBtn){
			getCode();
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
			timer.schedule(task,1000,1000);
		}
	}
	
	public void setPushId(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		map.put("jpushid", JPushInterface.getRegistrationID(getApplicationContext()));
		net.Post(ServerURL.JPUSH_URL, map, 1);
	}
	@Override
	public void OnSuccess(int requestCode, String result) {
		if(requestCode == 0){
			BandBean bean = GsonHelper.getGson().fromJson(result, BandBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				if(dialog != null){
					dialog.dismiss();
				}
				ToastUtil.show(BandPhoneActivity.this, "手机绑定成功");
				SharePerencesUtil.getInstances().setID(mPhoneEdit.getText().toString());
				if(bean.getData() != null){
					SharePerencesUtil.getInstances().setUUID(bean.getData().getUuid());
					SharePerencesUtil.getInstances().setCard(bean.getData().getCard());
				}
				BaseApplication.FragmentExit();
				Intent intent = new Intent(BandPhoneActivity.this,MainActivity.class);
				startActivity(intent);
				
				finish();
			}
		}
			if(requestCode == 1){
				Intent intent = new Intent(BandPhoneActivity.this,JPushServices.class);
				startService(intent);
			}
	}
	@Override
	public void OnError(int requestCode, String errorMsg) {
		if(dialog != null){
			dialog.dismiss();
		}
	}
}
