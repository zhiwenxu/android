package com.example.login;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.main.MainActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.LoginBean;
import com.example.network.bean.UnionLoginBean;
import com.example.utils.MD5Util;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.google.gson.JsonObject;
import com.tencent.connect.UserInfo;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends BaseActivity implements OnClickListener{
	
	public static final int LOGIN_NORMAL = 0;
	public static final int LOGIN_QQ = 1;
	public static final int LOGIN_WECHAT = 2;
	private LoadingDialog dialog = null;
	private Button mLoginBtn;
	private EditText mLoginEdit,mPwdEdit;
	private VolleyNet net;
	private ImageView mWXbtn,mQQbtn;
	private BaseUiListener listener;//腾讯登录
	private Button mForgetBtn;
	private Tencent mTencent;
	private String openid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initTitle();
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
		}
	}
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText(getResources().getString(R.string.login));
		rightView.setText(getResources().getString(R.string.register));
		rightView.setOnClickListener(this);
		leftView.setOnClickListener(this);
	}
	public void init(){
		mLoginBtn = (Button)findViewById(R.id.login_btn);
		mLoginEdit = (EditText)findViewById(R.id.login_username_edit);
		mPwdEdit = (EditText)findViewById(R.id.login_password_edit);
		mWXbtn = (ImageView)findViewById(R.id.wx_login_btn);
		mQQbtn = (ImageView)findViewById(R.id.qq_btn);
		mForgetBtn = (Button)findViewById(R.id.forget_btn);
		listener = new BaseUiListener();
		mLoginEdit.setText(SharePerencesUtil.getInstances().getLoginUserName());
		mPwdEdit.setText(SharePerencesUtil.getInstances().getPassWord());
		mLoginBtn.setOnClickListener(this);
		mWXbtn.setOnClickListener(this);
		mQQbtn.setOnClickListener(this);
		mForgetBtn.setOnClickListener(this);
		net = new VolleyNet();
		net.setOnVellerCallBack(callBack);
	}
	public void login(){
		String name = mLoginEdit.getText().toString();
		String pass = mPwdEdit.getText().toString();
		if(name.equals("")){
			ToastUtil.show(this, "手机号不能为空！");
			return;
		}
		if(pass.equals("")){
			ToastUtil.show(this, "密码不能为空！");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name",name);
		map.put("pass",MD5Util.stringMD5(name+pass));
		dialog = new LoadingDialog(this);
		dialog.show("登录中...", true, null);
		dialog.show();
		net.Post(ServerURL.LOGIN_URL, map, 0);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.common_title_left_btn:
			finish();
			break;
		case R.id.common_title_right_tv:
			Intent register = new Intent(this,RegisterActivity.class);
			register.putExtra("isstart", false);
			startActivity(register);
			break;
		case R.id.login_btn:
			login();
			break;
		case R.id.forget_btn:
			Intent forget = new Intent(this,ComfirePhoneActivity.class);
			startActivity(forget);
			break;
		case R.id.wx_login_btn:
			dialog = new LoadingDialog(this);
			dialog.show("登录中...", true, null);
			dialog.show();
			//微信登录
			final IWXAPI api = WXAPIFactory.createWXAPI(this,"wxe43806bd58c4856e",false);
			// 将该app注册到微信
			api.registerApp("wxe43806bd58c4856e"); 
			 SendAuth.Req req = new SendAuth.Req();  
			 req. scope = "snsapi_userinfo";  
			 req. state = "wechat_sdk_demo_test";  
			 api.sendReq(req);//执行完毕这句话之后，会在WXEntryActivity回调  
		break;
		case R.id.qq_btn:
			dialog = new LoadingDialog(this);
			dialog.show("登录中...", true, null);
			dialog.show();
			 mTencent = Tencent.createInstance("1105941896", this.getApplicationContext());
			 mTencent.login(this, "all", listener);
			break;
		}
	}
	private VollerCallBack callBack = new VollerCallBack() {
		@Override
		public void OnSuccess(int requestCode, String result) {
			// TODO Auto-generated method stub
			if(requestCode == 0){
				try {
					JSONObject jsonObject = new JSONObject(result);
				if(jsonObject.getInt("code") == VolleyNet.ONE_CODE){
					//登录成功
					LoginBean bean = GsonHelper.getGson().fromJson(result, LoginBean.class);
					if(bean.getData() != null){
						SharePerencesUtil.getInstances().setLoginUserName(mLoginEdit.getText().toString());
						SharePerencesUtil.getInstances().setPassWord(mPwdEdit.getText().toString());
						SharePerencesUtil.getInstances().setID(bean.getData().getID());
						SharePerencesUtil.getInstances().setUUID(bean.getData().getUuid());
						SharePerencesUtil.getInstances().setNick(bean.getData().getNick());
						SharePerencesUtil.getInstances().setGender(bean.getData().getGender());
						SharePerencesUtil.getInstances().setCents(bean.getData().getCents());
						SharePerencesUtil.getInstances().setCard(bean.getData().getCard());
						SharePerencesUtil.getInstances().setRmb(bean.getData().getRmb());
						SharePerencesUtil.getInstances().setHeadUrl(bean.getData().getHeadurl());
					}
					SharePerencesUtil.getInstances().setLogin(true);
					SharePerencesUtil.getInstances().setLoginType(0);
					Intent login = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(login);
					dialog.dismiss();
					finish();
				}
				else
				{
					//登录失败
					dialog.dismiss();
					ToastUtil.show(LoginActivity.this, "账号或密码错误！");
				}
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(requestCode == 1){
				LoginBean bean = GsonHelper.getGson().fromJson(result, LoginBean.class);
				if(bean.getCode() == VolleyNet.ONE_CODE){
					if(bean.getData() != null){
						SharePerencesUtil.getInstances().setID(bean.getData().getID());
						SharePerencesUtil.getInstances().setUUID(bean.getData().getUuid());
						SharePerencesUtil.getInstances().setNick(bean.getData().getNick());
						SharePerencesUtil.getInstances().setGender(bean.getData().getGender());
						SharePerencesUtil.getInstances().setCents(bean.getData().getCents());
						SharePerencesUtil.getInstances().setCard(bean.getData().getCard());
						SharePerencesUtil.getInstances().setRmb(bean.getData().getRmb());
						SharePerencesUtil.getInstances().setHeadUrl(bean.getData().getHeadurl());
					}
					SharePerencesUtil.getInstances().setLogin(true);
					SharePerencesUtil.getInstances().setLoginType(1);
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}
		@Override
		public void OnError(int requestCode, String errorMsg) {
			// TODO Auto-generated method stub
			dialog.dismiss();
		}
	};
	
	//登陆
	private void qqlogin(String openid,String headurl,String nickname){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("openid",openid);
			map.put("headurl",headurl);
			map.put("nickname",nickname);
			map.put("type","1");//qq联合登陆
			net.setOnVellerCallBack(callBack);
			net.Post(ServerURL.LOGIN_UNION_URL, map, 1);
	}
	
	 private IUiListener getQQinfoListener = new IUiListener() {
	        @Override
	        public void onComplete(Object response) {
	            try {
	                JSONObject jsonObject = (JSONObject) response;
	                String nickname = jsonObject.getString("nickname");
	                String headurl = jsonObject.getString("figureurl_qq_1");
	                SharePerencesUtil.getInstances().setNick(nickname);
	                SharePerencesUtil.getInstances().setHeadUrl(headurl);
	                qqlogin(openid, headurl, nickname);
	              //处理自己需要的信息
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        @Override
	        public void onError(UiError uiError) {

	        }

	        @Override
	        public void onCancel() {

	        }
	    };
	
	private class BaseUiListener implements IUiListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onComplete(Object object) {
			// TODO Auto-generated method stub
		 JSONObject response = ((JSONObject) object);
		try {
			 String openID = response.getString("openid"); 
			 openid = openID;
	         String accessToken = response.getString("access_token");  
	         String expires = response.getString("expires_in");  
	         SharePerencesUtil.getInstances().setOpenid(openID);
	         mTencent.setOpenId(openID);  
	         mTencent.setAccessToken(accessToken, expires);  
			 UserInfo userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());  
	         userInfo.getUserInfo(getQQinfoListener);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
		}

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Tencent.onActivityResultData(requestCode,resultCode,data,listener);
	}
}
