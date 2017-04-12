package com.example.login;

import java.util.HashMap;

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

public class ResetPwdActivity extends BaseActivity implements OnClickListener,VollerCallBack{
	private EditText mPwdEdit1,mPwdEdit2;
	private Button mComfireBtn;
	private String phone;
	private String code;
	private VolleyNet net;
	private LoadingDialog dialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pwd);
		initTitle();
		init();
	}
	
	private void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText("重置密码");
		rightView.setVisibility(View.INVISIBLE);
		leftView.setOnClickListener(this);
	}
	
	private void init(){
		mPwdEdit1 = (EditText)findViewById(R.id.pwd_edit1);
		mPwdEdit2 = (EditText)findViewById(R.id.pwd_edit2);
		mComfireBtn = (Button)findViewById(R.id.comfire_btn);
		mComfireBtn.setOnClickListener(this);
		phone = getIntent().getStringExtra("phone");
		code = getIntent().getStringExtra("code");
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
	}

	private void resetPwd(){
		String pass = mPwdEdit1.getText().toString();
		String pass2 = mPwdEdit2.getText().toString();
		if(pass.equals("")){
			ToastUtil.show(this, "密码不能为空");
			return;
		}
		if(pass2.equals("")){
			ToastUtil.show(this, "确认密码不能为空");
			return;
		}
		if(!pass.equals(pass2)){
			ToastUtil.show(this, "两次密码不一致");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name",phone);
		map.put("pass",MD5Util.stringMD5(phone+pass));
		map.put("code",code);
		dialog = new LoadingDialog(this);
		dialog.show("修改中...", true, null);
		dialog.show();
		net.Post(ServerURL.CHANGE_PWD_URL, map, 0);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
		if(v == mComfireBtn){
			resetPwd();
		}
	}
	@Override
	public void OnSuccess(int requestCode, String result) {
		if(requestCode == 0){
			try {
				JSONObject object = new JSONObject(result);
				if(object.getInt("code") == 1){
					SharePerencesUtil.getInstances().setPassWord(mPwdEdit1.getText().toString());
					ToastUtil.show(ResetPwdActivity.this, "密码重置成功");
					dialog.dismiss();
					finish();
				}
				else
				{
					ToastUtil.show(ResetPwdActivity.this, "密码重置失败");
					dialog.dismiss();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	@Override
	public void OnError(int requestCode, String errorMsg) {
		dialog.dismiss();
	}
}
