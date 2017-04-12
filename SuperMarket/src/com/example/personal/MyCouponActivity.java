package com.example.personal;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.adapter.MyCouponAdapter;
import com.example.application.BaseActivity;
import com.example.listener.OnBarPressListener;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.CouponBean;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.example.viewhelper.CouponBarHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
/**
 * 我的优惠券页面
 */
public class MyCouponActivity extends BaseActivity implements OnBarPressListener,OnClickListener
	,VollerCallBack{
	
	private PullToRefreshListView mListView;
	private View emptyView;
	private MyCouponAdapter mAdapter;
	private CouponBarHelper helper;
	private LoadingDialog dialog = null;
	private VolleyNet net;
	private int index = 0;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			dialog.dismiss();
			mAdapter.setBean((CouponBean)msg.obj);
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(this).inflate(R.layout.activity_my_coupon, null);
		setContentView(view);
		initTitle();
		init(view);
	}
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText("我的优惠券");
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	public void init(View view){
		mListView = (PullToRefreshListView)findViewById(R.id.my_coupon_list);
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getMyCoupon(index);
			}
		});
		helper = new CouponBarHelper(this);
		helper.setView(view);
		helper.setOnBarPressListener(this);
		mAdapter = new MyCouponAdapter(this);
		mListView.setAdapter(mAdapter);
		emptyView = LayoutInflater.from(this).inflate(R.layout.list_empty_view, null);
		mListView.setEmptyView(emptyView);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		getMyCoupon(0);//默认获取所有
	}
	
	private void getMyCoupon(int code){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id",SharePerencesUtil.getInstances().getID());
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		map.put("type",code+"");
		dialog = new LoadingDialog(this);
		dialog.show("加载中...", true, null);
		dialog.show();
		net.Post(ServerURL.COUPON_USERLIST_URL, map, 0);
	}
	@Override
	public void onPress(int code) {
		// TODO Auto-generated method stub
		switch (code) {
		//导致1-2对调，多了的3可领取用在优惠券页面
		case 0:
			getMyCoupon(0);
			index = 0;
			break;
		case 1:
			getMyCoupon(2);
			index = 2;
			break;
		case 2:
			getMyCoupon(1);
			index = 1;
			break;
		case 3:
			getMyCoupon(4);
			index = 4;
			break;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
	}
	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 0){
			CouponBean bean = GsonHelper.getGson().fromJson(result, CouponBean.class);
			mListView.onRefreshComplete();
			if(bean.getCode() == VolleyNet.ONE_CODE){
				Message message = new Message();
				message.obj = bean;
				handler.sendMessage(message);
			}
			else
			{
				ToastUtil.show(MyCouponActivity.this, "获取优惠券信息失败");
			}
		}
	}
	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}
}
