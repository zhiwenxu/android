package com.example.personal;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.adapter.WalletAdapter;
import com.example.application.BaseActivity;
import com.example.main.ScanActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.WalletBean;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class WalletActivity extends BaseActivity implements OnClickListener,VollerCallBack{

	private PullToRefreshListView mListView;
	private View emptyView;
	private WalletAdapter mWalletAdapter;
	private RelativeLayout mRechargeBtn,mPayBtn,mChangePhoneBtn;
	private VolleyNet net;
	private TextView mWalletAccount;
	private LoadingDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallet);
		initTitle();
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getChargeRecord();
	}
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText(getResources().getString(R.string.wallet));
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	public void init(){
		mListView = (PullToRefreshListView)findViewById(R.id.wallet_list);
		mRechargeBtn = (RelativeLayout)findViewById(R.id.recharge_btn);
		mPayBtn = (RelativeLayout)findViewById(R.id.pay_btn);
		mWalletAccount = (TextView)findViewById(R.id.wallet_account);
		mChangePhoneBtn = (RelativeLayout)findViewById(R.id.wallet_change_phone);
		dialog = new LoadingDialog(this);
		dialog.setMessage("加载中...");
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		mListView.setMode(Mode.BOTH);
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getChargeRecord();
			}
		});
		emptyView = LayoutInflater.from(this).inflate(R.layout.list_empty_view, null);
		mListView.setEmptyView(emptyView);
		dialog.show();
		mRechargeBtn.setOnClickListener(this);
		mPayBtn.setOnClickListener(this);
		mChangePhoneBtn.setOnClickListener(this);
	}
	
	//获得充值记录
	public void getChargeRecord(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id",SharePerencesUtil.getInstances().getID());
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		net.Post(ServerURL.CHARGE_RECORD_URL, map, 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
		if(v == mRechargeBtn){
			Intent intent = new Intent(this,RechargeActivity.class);
			startActivity(intent);
		}
		if(v == mPayBtn){
			Intent intent = new Intent(this,ScanActivity.class);
			startActivity(intent);
		}
		if(v == mChangePhoneBtn){
			Intent intent = new Intent(this,ChangePhoneActivity.class);
			startActivity(intent);
		}
	}
	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		dialog.cancel();
		Log.e("e", result);
		if(requestCode == 0){//查询充值记录
			mListView.onRefreshComplete();
			WalletBean bean = GsonHelper.getGson().fromJson(result, WalletBean.class);
			if(bean.getCode() == 1){//获取记录成功
				mWalletAccount.setText("￥"+String .format("%.2f",bean.getData().getRmb()/100.0f));
				mWalletAdapter = new WalletAdapter(WalletActivity.this,bean);
				mListView.setAdapter(mWalletAdapter);
				mListView.setMode(Mode.BOTH);
			}
			else{//获取记录失败
				mListView.onRefreshComplete();
				ToastUtil.show(WalletActivity.this, "获取充值记录失败");
			}
		}                     
	}
	@Override          
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub       
		mListView.onRefreshComplete();
		dialog.cancel();
	}
}
