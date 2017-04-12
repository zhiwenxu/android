package com.example.login;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.RegisterCodeBean;
import com.example.network.bean.UserExistBean;
import com.example.network.bean.UserRegisterBean;
import com.example.utils.MD5Util;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;

public class RegisterActivity extends BaseActivity implements OnClickListener,VollerCallBack{
	
	public static final int USER_EXIST_CODE = 1;//用户存在性检查
	public static final int VERIFICATION_CODE = 2;//发送验证码
	public static final int REGISTER_CODE = 3;
	private Button mRegisterBtn;
	private EditText mPhontEdit,mVerCodeEdit,mPwdEdit,mPwdComfireEdit,mNickEdit;
	private VolleyNet net;
	private Button mCodeBtn;
	private LoadingDialog dialog;
	private Timer timer; 
	private int second = 0;
	private CheckBox checkBox;
	private TimerTask task;
	private boolean isStart = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initTitle();
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText(getResources().getString(R.string.register));
		rightView.setVisibility(View.INVISIBLE);
		rightView.setOnClickListener(this);
		leftView.setOnClickListener(this);
	}
	public void init(){
		mRegisterBtn = (Button)findViewById(R.id.register_btn);
		mPhontEdit = (EditText)findViewById(R.id.register_phone_edit);
		mVerCodeEdit = (EditText)findViewById(R.id.ver_code_edit);
		mPwdEdit = (EditText)findViewById(R.id.register_pwd_edit);
		mPwdComfireEdit = (EditText)findViewById(R.id.register_comfire_pwd_edit);
		mNickEdit = (EditText)findViewById(R.id.nick_edit);
		mCodeBtn = (Button)findViewById(R.id.code_btn);
		checkBox = (CheckBox)findViewById(R.id.check);
		
		mCodeBtn.setOnClickListener(this);
		mRegisterBtn.setOnClickListener(this);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		dialog = new LoadingDialog(this);
		isStart = getIntent().getBooleanExtra("isstart", false);
	}
	/*
	 * 获取验证码
	 */
	public void getCode(){
		if(!mPhontEdit.getText().toString().equals("")){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("phone",mPhontEdit.getText().toString());
			map.put("usertype", ""+1);
			net.Post(ServerURL.VERIFICATION_CODE_URL, map, VERIFICATION_CODE);
		}
		else
		{
			ToastUtil.show(this, "手机号不能为空！");
		}
	}
	//检查用户存在性
	public void checkUserExist(){
		if(!mPhontEdit.getText().toString().equals("")){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id",mPhontEdit.getText().toString());
			net.Post(ServerURL.USER_EXIST_URL, map, USER_EXIST_CODE);
		}
		else
		{
			ToastUtil.show(this, "手机号不能为空！");
		}
		
	}
	public void register(){
		String phone = mPhontEdit.getText().toString();
		String vercode = mVerCodeEdit.getText().toString();
		String pwd = mPwdEdit.getText().toString();
		String comfirepwd = mPwdComfireEdit.getText().toString();
		String nick = mNickEdit.getText().toString();
		if(nick.equals("")){
			ToastUtil.show(this, "昵称不能为空！");
			return;
		}
		if(phone.equals("")){
			ToastUtil.show(this, "手机号不能为空！");
			return;
		}
		if(vercode.equals("")){
			ToastUtil.show(this, "验证码不能为空！");
			return;
		}
		if(pwd.equals("")||comfirepwd.equals("")){
			ToastUtil.show(this, "密码不能为空！");
			return;
		}
		if(!pwd.equals(comfirepwd)){
			ToastUtil.show(this, "密码不一致，请重新输入！");
			return;
		}
		if(!checkBox.isChecked()){
			ToastUtil.show(this, "请先同意用户协议");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phone",phone);
		map.put("code",vercode);
		map.put("pass",MD5Util.stringMD5(phone+pwd));
		map.put("nick",nick);
		net.Post(ServerURL.REGISTER_URL, map, REGISTER_CODE);
		dialog.show("注册中...", false, null);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mRegisterBtn){
			checkUserExist();
		}
		if(v == mCodeBtn){
			getCode();
			if(mPhontEdit.getText().toString().equals("")){
				return;
			}
			mCodeBtn.setClickable(false);
			timer = new Timer();
			task = new TimerTask() { 
			    @Override
			    public void run() { 
			      runOnUiThread(new Runnable() {   // UI thread
			        @Override
			        public void run() { 
			         mCodeBtn.setText((60-second)+"秒");
			         if(second >= 60){
			        	 timer.cancel();
			        	 mCodeBtn.setText("获取验证码");
			        	 mCodeBtn.setClickable(true);
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
		if(v.getId() == R.id.common_title_left_btn){
			back();
		}
	}
	
	public void back(){
		if(isStart){
			Intent intent = new Intent(this,ChangeActivity.class);
			startActivity(intent);
			finish();
		}
		else
		{
			finish();
		}
	}
	
	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == USER_EXIST_CODE){
			UserExistBean bean = GsonHelper.getGson().fromJson(result, UserExistBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				//检查用户存在
				ToastUtil.show(RegisterActivity.this, "用户名已存在");
			}
			else
			{
				register();//用户不存在，可注册
			}
		}
		if(requestCode == VERIFICATION_CODE){
			RegisterCodeBean bean = GsonHelper.getGson().fromJson(result, RegisterCodeBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				//获取验证码成功
				ToastUtil.show(RegisterActivity.this, "获取验证码成功");
			}
			else{
				//获取验证码失败
				ToastUtil.show(RegisterActivity.this, "获取验证码失败");
			}
		}
		if(requestCode == REGISTER_CODE){
			UserRegisterBean bean = GsonHelper.getGson().fromJson(result, UserRegisterBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				//注册成功
				dialog.dismiss();
				SharePerencesUtil.getInstances().setCard(bean.getData().getCard());
				SharePerencesUtil.getInstances().setUUID(bean.getData().getUuid());
				SharePerencesUtil.getInstances().setID(mPhontEdit.getText().toString());
				SharePerencesUtil.getInstances().setPassWord(mPwdEdit.getText().toString());
				SharePerencesUtil.getInstances().setNick(mNickEdit.getText().toString());
				Intent intent = new Intent(RegisterActivity.this,RegisterInfoActivity.class);
				intent.putExtra("wd", false);
				startActivity(intent);
				finish();
			}
			else
			{
				//注册失败
			}
		}
	}
	
	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		ToastUtil.show(this, "requestCode:"+requestCode+"-- errorMsg"+errorMsg);
	}
}
