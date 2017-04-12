package com.example.main;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;
import cn.jpush.android.api.JPushInterface;

import com.example.application.BaseApplication;
import com.example.fragment.FxFragment;
import com.example.fragment.MdFragment;
import com.example.fragment.SyFragment;
import com.example.fragment.WdFragment;
import com.example.listener.OnBarPressListener;
import com.example.listener.OnIconChangeListener;
import com.example.login.LoginActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.GetPhoneBean;
import com.example.personal.FeedBackActivity;
import com.example.personal.SettingActivity;
import com.example.personal.WalletActivity;
import com.example.service.JPushServices;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.utils.VersionCheck;
import com.example.view.commonview.CircleImageView;
import com.example.view.slidingmenu.SlidingMenu;
import com.example.viewhelper.FragmentHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends FragmentActivity implements OnBarPressListener
		,OnClickListener,VollerCallBack,OnIconChangeListener{
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private View mLeftView;
	private View mCenterView;
	private CircleImageView mMainPhoto;
	private TextView mMainNickName;
	private RelativeLayout mLeftTitleBtn;
	private LinearLayout mSyBtn,mWalletBtn,mMdBtn,mKfBtn,mFeedBackBtn,mSettingBtn;
	private ImageView mCenterTitleImage;
	private TextView mCenterTitleTv;
	private SlidingMenu mSlidingMenu;
	private SyFragment mSyFragment;
	private FxFragment mFxFragment;
	private MdFragment mMdFragment;
	private WdFragment mWdFragment;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private FragmentHelper helper;
	private VolleyNet net;
	private String mKfPhoneStr = "";
	private boolean isBack = false;
	private VersionCheck versionCheck;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		BaseApplication application = (BaseApplication)getApplicationContext();
		application.addFragmentActivity(this);
		init();
		initTitle();
		versionCheck = new VersionCheck();
		versionCheck.checkVersion(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMainNickName.setText(SharePerencesUtil.getInstances().getNick());
		String photo = SharePerencesUtil.getInstances().getHeadUrl();
		if(!photo.equals("")){
			imageLoader.displayImage(photo, mMainPhoto);
		}
	}
	private void initTitle(){
		mLeftTitleBtn = (RelativeLayout)mCenterView.findViewById(R.id.common_title_left_btn);
		mCenterTitleTv = (TextView)mCenterView.findViewById(R.id.common_title_center_tv);
		mCenterTitleImage = (ImageView)mCenterView.findViewById(R.id.common_title_center_img);
		mCenterTitleImage.setVisibility(View.VISIBLE);
		mCenterTitleTv.setVisibility(View.GONE);
		mLeftTitleBtn.setOnClickListener(this);
	}
	
	private void sendJPushId(){
		if(!SharePerencesUtil.getInstances().getUUID().equals("")){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("uuid",SharePerencesUtil.getInstances().getUUID());
			map.put("jpushid", JPushInterface.getRegistrationID(getApplicationContext()));
			net.Post(ServerURL.JPUSH_URL, map, 1);
		}
	}
	@SuppressLint("NewApi")
	private void init(){
		mSlidingMenu = (SlidingMenu)findViewById(R.id.main_sliding_menu);
		mLeftView = LayoutInflater.from(this).inflate(R.layout.main_left_view, null);
		mCenterView = LayoutInflater.from(this).inflate(R.layout.main_center_view, null);
		mSlidingMenu.setLeftView(mLeftView);
		mSlidingMenu.setCenterView(mCenterView);
		
		//侧拉按钮
		mSyBtn = (LinearLayout)mLeftView.findViewById(R.id.left_sy_btn);
		mWalletBtn = (LinearLayout)mLeftView.findViewById(R.id.left_wallet_btn);
		mMdBtn =  (LinearLayout)mLeftView.findViewById(R.id.left_md_btn);
		mKfBtn = (LinearLayout)mLeftView.findViewById(R.id.left_kf_btn);
		mFeedBackBtn = (LinearLayout)mLeftView.findViewById(R.id.left_feedback_btn);
		mSettingBtn = (LinearLayout)mLeftView.findViewById(R.id.left_setting_btn);
		
		
		//侧拉头像和昵称
		mMainPhoto = (CircleImageView)mLeftView.findViewById(R.id.main_photo);
		mMainNickName = (TextView)mLeftView.findViewById(R.id.main_nickname);
		
		mSyBtn.setOnClickListener(this);
		mWalletBtn.setOnClickListener(this);
		mMdBtn.setOnClickListener(this);
		mKfBtn.setOnClickListener(this);
		mFeedBackBtn.setOnClickListener(this);
		mSettingBtn.setOnClickListener(this);
		
		mSyFragment = new SyFragment();
		mFxFragment = new FxFragment();
		mMdFragment = new MdFragment();
		mWdFragment = new WdFragment();
		
		mSyFragment.setOnIconChangeListener(this);
		mWdFragment.setOnIconChangeListener(this);
		
		fm = getFragmentManager();
		helper = new FragmentHelper(this);
		helper.setView(mCenterView);
		helper.setOnBarPressListener(this);
		changeFragment(mSyFragment);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		getPhone();//获取客服电话
		sendJPushId();//发送jpushid给服务器
		if(!SharePerencesUtil.getInstances().getLogin()){
			return;
		}
		//第三方登陆后，首先判断是否绑定手机
		if(SharePerencesUtil.getInstances().getID().equals("")){
			Intent intent = new Intent(this,BandPhoneActivity.class);
			startActivity(intent);
		}
	}
	
	private void getPhone(){
		net.Post(ServerURL.GET_PHONE_URL, null, 0);
	}
	// fragment的变换,使用show、hide来代替之前的replace，防止每次都刷新数据和ui
	@SuppressLint("NewApi") 
	public void changeFragment(Fragment to) {
		ft = fm.beginTransaction();
		if (!to.isAdded()) {
			ft.hide(mSyFragment).hide(mFxFragment)
					.hide(mMdFragment).hide(mWdFragment)
					.add(R.id.framelayout, to).show(to).commit();
		} else {
			ft.hide(mSyFragment).hide(mFxFragment)
			.hide(mMdFragment).hide(mWdFragment)
			.show(to).commit();
		}
	}
	@Override
	public void onPress(int code) {
		// TODO Auto-generated method stub
		switch (code) {
		case 0:
			changeFragment(mSyFragment);
			mCenterTitleImage.setVisibility(View.VISIBLE);
			mCenterTitleTv.setVisibility(View.GONE);
			break;
		case 1:
			changeFragment(mFxFragment);
			mCenterTitleImage.setVisibility(View.GONE);
			mCenterTitleTv.setVisibility(View.VISIBLE);
			mCenterTitleTv.setText("动态");
			break;
		case 2:
			mCenterTitleImage.setVisibility(View.GONE);
			mCenterTitleTv.setVisibility(View.VISIBLE);
			mCenterTitleTv.setText("门店");
			changeFragment(mMdFragment);
			break;
		case 3:
			mCenterTitleImage.setVisibility(View.GONE);
			mCenterTitleTv.setVisibility(View.VISIBLE);
			mCenterTitleTv.setText("我的");
			changeFragment(mWdFragment);
			break;
		case 4:
			if(!SharePerencesUtil.getInstances().getLogin()){
				Intent intent = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(intent);
				return;
			}
			//第三方登陆后，首先判断是否绑定手机
			if(SharePerencesUtil.getInstances().getID().equals("")){
				Intent intent = new Intent(this,BandPhoneActivity.class);
				startActivity(intent);
				return;
			}
			Intent intent = new Intent(this,ScanActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mLeftTitleBtn){
			mSlidingMenu.showLeftView();
		}
		
		if(v == mSyBtn){
			mSlidingMenu.showLeftView();
			helper.changeColor(0);
		}
		if(v == mWalletBtn){
			if(!SharePerencesUtil.getInstances().getLogin()){
				Intent intent = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(intent);
				return;
			}
			//第三方登陆后，首先判断是否绑定手机
			if(SharePerencesUtil.getInstances().getID().equals("")){
				Intent intent = new Intent(this,BandPhoneActivity.class);
				startActivity(intent);
				return;
			}
			mSlidingMenu.showLeftView();
			Intent intent = new Intent(this,WalletActivity.class);
			startActivity(intent);
		}
		if(v == mMdBtn){
			mSlidingMenu.showLeftView();
			helper.changeColor(2);
		}
		if(v == mKfBtn){
			if(!mKfPhoneStr.equals("未设置") ||!mKfPhoneStr.equals("")){
				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mKfPhoneStr));
				startActivity(intent);
				mSlidingMenu.showLeftView();
			}
			else{
				ToastUtil.show(this, "客服电话未设置");
			}
			
		}
		if(v == mFeedBackBtn){
			if(!SharePerencesUtil.getInstances().getLogin()){
				Intent intent = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(intent);
				return;
			}
			//第三方登陆后，首先判断是否绑定手机
			if(SharePerencesUtil.getInstances().getID().equals("")){
				Intent intent = new Intent(this,BandPhoneActivity.class);
				startActivity(intent);
				return;
			}
			mSlidingMenu.showLeftView();
			Intent intent = new Intent(this,FeedBackActivity.class);
			startActivity(intent);
		}
		if(v == mSettingBtn){
			if(!SharePerencesUtil.getInstances().getLogin()){
				Intent intent = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(intent);
				return;
			}
			//第三方登陆后，首先判断是否绑定手机
			if(SharePerencesUtil.getInstances().getID().equals("")){
				Intent intent = new Intent(this,BandPhoneActivity.class);
				startActivity(intent);
				return;
			}
			mSlidingMenu.showLeftView();
			Intent intent = new Intent(this,SettingActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 0){
			GetPhoneBean bean = GsonHelper.getGson().fromJson(result, GetPhoneBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				mKfPhoneStr = bean.getData().getPhone();
			}
		}
		if(requestCode == 1){
			Intent intent = new Intent(MainActivity.this,JPushServices.class);
			startService(intent);
		}
	}

	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			if(isBack == false){
				isBack = true;
				ToastUtil.show(MainActivity.this, "再按一次退出");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						isBack = false;
					}
				}, 2000);
				return false;
			}
			if(isBack == true){
				BaseApplication.exit();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this,JPushServices.class);
		stopService(intent);
		super.onDestroy();
	}

	@SuppressLint("NewApi")
	@Override
	public void OnIconChange() {
		// TODO Auto-generated method stub
		String photo = SharePerencesUtil.getInstances().getHeadUrl();
		if(!photo.equals("")){
			imageLoader.displayImage(photo, mMainPhoto);
		}
		if(mWdFragment != null && mWdFragment.isAdded())
		{
			mWdFragment.reSetIcon();
		}
		if(mSyFragment != null && mSyFragment.isAdded())
		{
			
			mSyFragment.reSetIcon();
		}
	}
}
