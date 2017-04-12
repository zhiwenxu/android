package com.example.main;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.personal.CouponActivity;
import com.example.utils.DisplayUtil;
import com.example.utils.Ecoad;
import com.example.utils.ImageLoaderUtil;
import com.example.utils.SharePerencesUtil;
import com.example.view.commonview.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class JfScanActivity extends BaseActivity implements OnClickListener{
	private LinearLayout mGetCouponBtn;
	private ImageView mCode1Image,mCode2Image;
//	private Receiver mReceiver;
	private CircleImageView mIcon;
	private TextView mCodeInfoTv;
	private TextView mName;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private Timer timer;
	TimerTask task = new TimerTask() { 
	    @Override
	    public void run() { 
	      runOnUiThread(new Runnable() {   // UI thread
	        @Override
	        public void run() { 
	        	createCode1();
				createCode2();
	        } 
	      }); 
	    }
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jfscan);
		initTitle();
		init();
		brightness();
	}
	private void brightness(){
		Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = 1.0f;
        window.setAttributes(lp);
	}
	private void init(){
		mGetCouponBtn = (LinearLayout)findViewById(R.id.get_coupon_btn);
		mCode1Image = (ImageView)findViewById(R.id.code1_img);
		mCode2Image = (ImageView)findViewById(R.id.code2_img);
		mIcon = (CircleImageView)findViewById(R.id.icon);
		mName = (TextView)findViewById(R.id.name);
		mGetCouponBtn.setOnClickListener(this);
		mCodeInfoTv = (TextView)findViewById(R.id.codeinfo);
		mName.setText(SharePerencesUtil.getInstances().getNick());
		imageLoader.displayImage(SharePerencesUtil.getInstances().getHeadUrl(), 
				mIcon, ImageLoaderUtil.getInstance().getIconOptions());
		createCode1();
		createCode2();
		timer = new Timer();
		timer.schedule(task,1000,1000*60);
	}
	
	private void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText("积分支付");
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	
	private String getinfo(){
		Calendar ca = Calendar.getInstance();//创建一个日期实例
		ca.setTime(new Date());//实例化一个日期
		int time = ((ca.get(Calendar.DAY_OF_YEAR)-1)*24+ca.get(Calendar.HOUR_OF_DAY))*60+ca.get(Calendar.MINUTE);
		String tStr = time+"";
		String temp = tStr;
		for(int i=0;i<6-tStr.length();i++){
			temp = "0"+temp;
		}
		return SharePerencesUtil.getInstances().getID()+"01"+temp;
	}
	//生成条形码
		private void createCode1(){
			Ecoad ecoad = new Ecoad(DisplayUtil.getDisplayWidth(this)-DisplayUtil.dip2px(this, 60),
					DisplayUtil.dip2px(this, 80));
			try {
				mCode1Image.setImageBitmap(ecoad.code1(getinfo()));
				mCodeInfoTv.setText(getinfo());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//生成二维码
		private void createCode2(){
			Ecoad ecoad = new Ecoad(DisplayUtil.dip2px(this, 150), DisplayUtil.dip2px(this, 150));
			try {
				mCode2Image.setImageBitmap(ecoad.code2(getinfo()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
		if(v == mGetCouponBtn){
			Intent intent = new Intent(this,CouponActivity.class);
			startActivity(intent);
		}
	}
//	private class Receiver extends BroadcastReceiver{
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			// TODO Auto-generated method stub
//			Intent charge = new Intent(JfScanActivity.this,ChargeFinishActivity.class);
//			startActivity(charge);
//		}
//    }
//	public void registerMessageReceiver() {
//		mReceiver = new Receiver();
//		IntentFilter filter = new IntentFilter();
//		filter.addCategory("com.example.supermarket");
//		filter.addAction("cn.jpush.android.intent.MESSAGE_RECEIVED");
//		registerReceiver(mReceiver, filter);
//	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
//		unregisterReceiver(mReceiver);
	}
}
