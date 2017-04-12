package com.example.service;


import org.json.JSONException;
import org.json.JSONObject;

import cn.app9010.supermarket.R;
import cn.jpush.android.api.JPushInterface;

import com.example.application.BaseApplication;
import com.example.login.LoginActivity;
import com.example.main.ChargeFinishActivity;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LogoutDialog;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.audiofx.LoudnessEnhancer;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class JPushServices extends Service{
	private Receiver mReceiver;
	private AlertDialog mLogoutDialog = null;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		registerMessageReceiver();
		return super.onStartCommand(intent, flags, startId);
	}
	private class Receiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				try {
					JSONObject jsonObject = new JSONObject(intent.getExtras().getString(JPushInterface.EXTRA_MESSAGE));
					if(jsonObject.getInt("code") == 11){
						Intent intent2 = new Intent(JPushServices.this,LogoutDialog.class);
						intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent2);
					}
					else
					{
						JSONObject data = jsonObject.getJSONObject("data");
					    Intent charge = new Intent(JPushServices.this,ChargeFinishActivity.class);
						charge.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						charge.putExtra("amount",data.getDouble("amount"));
						charge.putExtra("store", data.getString("store"));
						charge.putExtra("time", data.getString("time"));
						String msg = jsonObject.getString("msg");
						String preferentialWay = "";
						if(data.has("preferentialWay")){
							preferentialWay = data.getString("preferentialWay");
						}
						int exist = msg.indexOf("余额");
						if(exist >= 0){
							charge.putExtra("type","余额支付");
						}
						else
						{
							charge.putExtra("type","积分支付");
						}
						String info = "";
						if(!data.isNull("info")){
							info = data.getString("info");
						}
						charge.putExtra("info", info);
						charge.putExtra("preferentialWay", preferentialWay);
						JPushServices.this.startActivity(charge);
					}
					
				    
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
	public void registerMessageReceiver() {
		mReceiver = new Receiver();
		IntentFilter filter = new IntentFilter();
		filter.addCategory("cn.app9010.supermarket");
		filter.addAction("cn.jpush.android.intent.MESSAGE_RECEIVED");
		registerReceiver(mReceiver, filter);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
	
//	private void showLogoutDialog(){
//		View view = LayoutInflater.from(this).inflate(R.layout.logout_dialog_layout, null);
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setView(view);
//		TextView comfireBtn = (TextView)view.findViewById(R.id.logout_btn);
//		comfireBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				mLogoutDialog.dismiss();
//			}
//		});
//		mLogoutDialog = builder.create();
//		mLogoutDialog.show();
//		mLogoutDialog.setOnDismissListener(new OnDismissListener() {
//			@Override
//			public void onDismiss(DialogInterface arg0) {
//				// TODO Auto-generated method stub
//				exit();
//			}
//		});
//		comfireBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				mLogoutDialog.dismiss();
//			}
//		});
//		
//	}
	
}
