package com.example.personal;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


import org.json.JSONException;
import org.json.JSONObject;

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
import com.example.utils.MD5Util;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;

public class ChangePhoneActivity extends BaseActivity implements OnClickListener,VollerCallBack{

	private EditText mOldPhoneEdit,mNewPhoneEdit,mOldCodeEdit,mNewCodeEdit,mPwdEdit;
	private TextView mOldCodeTv,mNewCodeTv;
	private Button mComfireBtn,mConcelBtn;
	private LoadingDialog dialog = null;
	private VolleyNet net;
	private Timer timer; 
	private Timer timer1;
	private int second = 0;
	private int second1 = 0;
	private TimerTask task;
	private TimerTask task1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changephone);
		initTitle();
		init();
	}
	
	private void init(){
		mOldPhoneEdit = (EditText)findViewById(R.id.old_phone_edit);
		mNewPhoneEdit = (EditText)findViewById(R.id.new_phone_edit);
		mOldCodeEdit = (EditText)findViewById(R.id.old_phone_code_edit);
		mNewCodeEdit = (EditText)findViewById(R.id.new_phone_code_edit);
		mPwdEdit = (EditText)findViewById(R.id.pwd_edit);
		mOldCodeTv = (TextView)findViewById(R.id.old_phone_code_tv);
		mNewCodeTv = (TextView)findViewById(R.id.new_phone_code_tv);
		mConcelBtn = (Button)findViewById(R.id.concel_btn);
		mComfireBtn = (Button)findViewById(R.id.comfire_btn);
		mOldCodeTv.setOnClickListener(this);
		mNewCodeTv.setOnClickListener(this);
		mConcelBtn.setOnClickListener(this);
		mComfireBtn.setOnClickListener(this);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
	}
	
	private void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText("更换手机");
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	
	private void getCode(int type){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("usertype", ""+3);
		String oldphone = mOldPhoneEdit.getText().toString();
		String newphone = mNewPhoneEdit.getText().toString();
		if(type == 0){
			if(oldphone.equals("")){
				ToastUtil.show(this, "旧手机号不能为空");
				return;
			}
			map.put("phone",oldphone);
			net.Post(ServerURL.VERIFICATION_CODE_URL, map, 0);
		}
		else
		{
			if(newphone.equals("")){
				ToastUtil.show(this, "新手机号不能为空");
				return;
			}
			map.put("phone",newphone);
			net.Post(ServerURL.VERIFICATION_CODE_URL, map, 1);
		}
	}

	private void changePhone(){
		
		String mOldStr = mOldPhoneEdit.getText().toString();
		String mOldCodeStr = mOldCodeEdit.getText().toString();
		String mNewStr = mNewPhoneEdit.getText().toString();
		String mNewCodeStr = mNewCodeEdit.getText().toString();
		String pass = mPwdEdit.getText().toString();
		if(mOldStr .equals("")){
			ToastUtil.show(this, "旧手机号不能为空");
			return;
		}
		if(mOldCodeStr.equals("")){
			ToastUtil.show(this, "旧手机验证码不能为空");
			return;
		}
		if(mNewStr.equals("")){
			ToastUtil.show(this, "新手机号不能为空");
			return;
		}
		if(mNewCodeStr.equals("")){
			ToastUtil.show(this, "新手机验证码不能为空");
			return;
		}
		if(pass.equals("")){
			ToastUtil.show(this, "密码不能为空");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("oldphone",mOldStr);
		map.put("oldcode",mOldCodeStr);
		map.put("newphone",mNewStr);
		map.put("newCode", mNewCodeStr);
		map.put("oldpass", MD5Util.stringMD5(mOldStr+pass));
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		dialog = new LoadingDialog(this);
		dialog.show("更换中...", true, null);
		dialog.show();
		net.Post(ServerURL.CHANGE_PHONE_URL, map, 3);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
		if(v == mComfireBtn){
			changePhone();
		}
		if(v == mConcelBtn){
			finish();
		}
		if(v == mOldCodeTv){
			getCode(0);
			if(mOldPhoneEdit.getText().toString().equals("")){
				return;
			}
			mOldCodeTv.setClickable(false);
			timer = new Timer();
			task = new TimerTask() { 
				    @Override
				    public void run() { 
				      runOnUiThread(new Runnable() {   // UI thread
				        @Override
				        public void run() { 
				        	mOldCodeTv.setText((60-second)+"秒");
				         if(second >= 60){
				        	 timer.cancel();
				        	 mOldCodeTv.setText("获取验证码");
				        	 mOldCodeTv.setClickable(true);
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
		if(v == mNewCodeTv){
			getCode(1);
			if(mNewPhoneEdit.getText().toString().equals("")){
				return;
			}
			mNewCodeTv.setClickable(false);
			timer1 = new Timer();
			task1 = new TimerTask() { 
			    @Override
			    public void run() { 
			      runOnUiThread(new Runnable() {   // UI thread
			        @Override
			        public void run() { 
			        	mNewCodeTv.setText((60-second1)+"秒");
			         if(second1 >= 60){
			        	 timer1.cancel();
			        	 mNewCodeTv.setText("获取验证码");
			        	 mNewCodeTv.setClickable(true);
			        	 second1 = 0;
			        	 return;
			         }
			         second1 ++;
			        } 
			      }); 
			    }
			};
			timer1.schedule(task1,1,1000);
		}
	}
	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 3){
			dialog.dismiss();
			try {
				JSONObject object = new JSONObject(result);
				if(object.getInt("code") == VolleyNet.ONE_CODE){
					SharePerencesUtil.getInstances().setLoginUserName(mNewPhoneEdit.getText().toString());
					SharePerencesUtil.getInstances().setID(mNewPhoneEdit.getText().toString());
					SharePerencesUtil.getInstances().setUUID(object.getJSONObject("data").getString("uuid"));
					SharePerencesUtil.getInstances().setCard(object.getJSONObject("data").getString("card"));
					ToastUtil.show(ChangePhoneActivity.this, "手机更换成功");
					finish();
				}
				else
				{
					ToastUtil.show(ChangePhoneActivity.this, "手机更换失败");
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
		dialog.dismiss();
	}
}
