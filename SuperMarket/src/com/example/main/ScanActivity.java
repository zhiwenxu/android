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
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;
import cn.jpush.android.api.JPushInterface;

import com.example.application.BaseActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.GetPhoneBean;
import com.example.network.bean.HdBean;
import com.example.personal.CouponActivity;
import com.example.utils.DisplayUtil;
import com.example.utils.Ecoad;
import com.example.utils.ImageLoaderUtil;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.CircleImageView;
import com.example.view.commonview.HdDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 扫一扫页面
 */
public class ScanActivity extends BaseActivity implements OnClickListener,VollerCallBack{
	private ImageView mGetCouponBtn;
	private ImageView mCode1Image,mCode2Image;
	private TextView mCodeInfoTv;
//	private Receiver mReceiver;
	private CircleImageView mIcon;
	private TextView mName;
	private CheckBox mCheckBtn;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private boolean isYhq = true;
	private Timer timer;
	private VolleyNet net;
	TimerTask task = new TimerTask() { 
	    @Override
	    public void run() { 
	      runOnUiThread(new Runnable() {   // UI thread
	        @Override
	        public void run() { 
	        	if(isYhq){
	        		createCode1("1");
					createCode2("1");
	        	}
	        	else
	        	{
	        		createCode1("0");
					createCode2("0");
	        	}
	        } 
	      }); 
	    }
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		initTitle();
		init();
//		registerMessageReceiver();
		brightness();
	}
	
	private void brightness(){
		Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = 1.0f;
        window.setAttributes(lp);
	}
	public void init(){
		mGetCouponBtn = (ImageView)findViewById(R.id.get_coupon_btn);
		mCode1Image = (ImageView)findViewById(R.id.code1_img);
		mCode2Image = (ImageView)findViewById(R.id.code2_img);
		mIcon = (CircleImageView)findViewById(R.id.icon);
		mName = (TextView)findViewById(R.id.name);
		mCheckBtn = (CheckBox)findViewById(R.id.check_btn);
		mCodeInfoTv = (TextView)findViewById(R.id.codeinfo);
		mCheckBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1){
					isYhq = true;
					createCode1("1");
					createCode2("1");
				}
				else
				{
					isYhq = false;
					createCode1("0");
					createCode2("0");
				}
			}
		});
		mGetCouponBtn.setOnClickListener(this);
		mName.setText(SharePerencesUtil.getInstances().getNick());
		imageLoader.displayImage(SharePerencesUtil.getInstances().getHeadUrl(), 
				mIcon, ImageLoaderUtil.getInstance().getIconOptions());
		createCode1("0");
		createCode2("0");
		timer = new Timer();
		timer.schedule(task,1000,1000*60);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		net.Post(ServerURL.GET_HD_URL, null, 1);
	}
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText("余额支付");
		rightView.setText("积分支付");
		leftView.setOnClickListener(this);
		rightView.setOnClickListener(this);
	}
	private String getinfo(String type){
		Calendar ca = Calendar.getInstance();//创建一个日期实例
		ca.setTime(new Date());//实例化一个日期
		int time = ((ca.get(Calendar.DAY_OF_YEAR)-1)*24+ca.get(Calendar.HOUR_OF_DAY))*60+ca.get(Calendar.MINUTE);
		String tStr = time+"";
		String temp = tStr;
		for(int i=0;i<6-tStr.length();i++){
			temp = "0"+temp;
		}
		return SharePerencesUtil.getInstances().getID()+type+"0"+temp;
	}	
	//生成条形码
	private void createCode1(String type){
		Ecoad ecoad = new Ecoad(DisplayUtil.getDisplayWidth(this)-DisplayUtil.dip2px(this, 60),
				DisplayUtil.dip2px(this, 80));
		try {
			mCode1Image.setImageBitmap(ecoad.code1(getinfo(type)));
			mCodeInfoTv.setText(getinfo(type));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//生成二维码
	private void createCode2(String type){
		Ecoad ecoad = new Ecoad(DisplayUtil.dip2px(this, 150), DisplayUtil.dip2px(this, 150));
		try {
			mCode2Image.setImageBitmap(ecoad.code2(getinfo(type)));
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
		if(v.getId() == R.id.common_title_right_tv){
			Intent intent = new Intent(this,JfScanActivity.class);
			startActivity(intent);
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
//			Intent charge = new Intent(ScanActivity.this,ChargeFinishActivity.class);
//			startActivity(charge);
//			if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//	        	ToastUtil.show(ScanActivity.this,"[MyReceiver] 接收到推送下来的自定义消息: " + intent.getExtras().getString(JPushInterface.EXTRA_MESSAGE));
//			}
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

@Override
public void OnSuccess(int requestCode, String result) {
	// TODO Auto-generated method stub
	if(requestCode == 1){
		HdBean bean = GsonHelper.getGson().fromJson(result, HdBean.class);
		if(bean.getCode() == VolleyNet.ONE_CODE){
			if(bean.getData().getValue().size()>0){
				Message msg = new Message();
				msg.what = 200;
				msg.obj = bean;
				mHandler.sendMessage(msg);
			}
		}
	}
}

@Override
public void OnError(int requestCode, String errorMsg) {
	// TODO Auto-generated method stub
	
}

private Handler mHandler = new Handler(){
	public void handleMessage(android.os.Message msg) {
		if(msg.what == 200){
			HdBean bean = (HdBean)msg.obj;
			HdDialog dialog = new HdDialog(ScanActivity.this);
			int type = bean.getData().getValue().get(0).getType();
			String title = bean.getData().getValue().get(0).getName();
			String content = bean.getData().getValue().get(0).getDescribe();
			String from = bean.getData().getValue().get(0).getFromTime();
			String to = bean.getData().getValue().get(0).getToTime();
			dialog.initdata(type, title, content, from, to);
			dialog.show();
			mCheckBtn.setVisibility(View.GONE);
		}
	};
};

}
