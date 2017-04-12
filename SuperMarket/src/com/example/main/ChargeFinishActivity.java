package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.personal.WalletActivity;

public class ChargeFinishActivity extends BaseActivity implements OnClickListener{
	
	private TextView mPrice,mAddress,mTime,mType,mDetail,mhd;
	private ImageView hbBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charge_finish);
		initTitle();
		init();
	}
	private void init(){
		mPrice = (TextView)findViewById(R.id.price_tv);
		mAddress = (TextView)findViewById(R.id.md_address);
		mTime = (TextView)findViewById(R.id.xf_time);
		mType = (TextView)findViewById(R.id.pay_type);
		mDetail = (TextView)findViewById(R.id.detail);
		mhd = (TextView)findViewById(R.id.hd_tv);
		hbBtn = (ImageView)findViewById(R.id.hb_btn);
		hbBtn.setOnClickListener(this);
		if(getIntent().getStringExtra("type").equals("积分支付")){
			mPrice.setText(((int)getIntent().getDoubleExtra("amount", 0.00))+"积分");
		}
		else
		{
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");  
			mPrice.setText("￥"+df.format(getIntent().getDoubleExtra("amount", 0.00)/100));
		}
		String info = getIntent().getStringExtra("info");
		
		if(info.equals("")){
			mDetail.setText("无");
		}
		else{
			mDetail.setText(info);
		}
		mAddress.setText(getIntent().getStringExtra("store"));
		mTime.setText(getIntent().getStringExtra("time"));
		mType.setText(getIntent().getStringExtra("type"));
		String preferentialWay = getIntent().getStringExtra("preferentialWay");
		if(!preferentialWay.equals("")){
			mhd.setText(preferentialWay);
		}
	}
	private void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		leftView.setVisibility(View.GONE);
		centerView.setText("交易详情");
		rightView.setText("完成");
		rightView.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_right_tv){
			finish();
		}
		if(v == hbBtn){
			Intent intent = new Intent(this,WalletActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
