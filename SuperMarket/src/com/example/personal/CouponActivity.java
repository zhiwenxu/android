package com.example.personal;

import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.adapter.CouponAdapter;
import com.example.adapter.CouponAdapter.GetCouponListener;
import com.example.application.BaseActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.CouponBean;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 优惠券页面
 */
public class CouponActivity extends BaseActivity implements OnClickListener
	,VollerCallBack,GetCouponListener{
	private PullToRefreshListView mListView;
	private View emptyView;
	private CouponAdapter mAdapter;
	private LoadingDialog dialog = null;
	private VolleyNet net;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon);
		initTitle();
		init();
	}
	
	public void init(){
		mListView = (PullToRefreshListView)findViewById(R.id.coupon_list);
		emptyView = LayoutInflater.from(this).inflate(R.layout.list_empty_view, null);
		mListView.setEmptyView(emptyView);
		mAdapter = new CouponAdapter(this);
		mAdapter.setOnGetCouponListener(this);
		mListView.setAdapter(mAdapter);
		mListView.setMode(Mode.BOTH);
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getCoupon();
			}
		});
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		getCoupon();
		dialog = new LoadingDialog(this);
		dialog.show("加载中...", true, null);
		dialog.show();
	}
	
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText(getResources().getString(R.string.coupon));
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}

	private void getCoupon(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id",SharePerencesUtil.getInstances().getID());
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		map.put("type","3");
		net.setOnVellerCallBack(this);
		net.Post(ServerURL.COUPON_USERLIST_URL, map, 0);
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
			dialog.dismiss();
			mListView.onRefreshComplete();
			CouponBean bean = GsonHelper.getGson().fromJson(result, CouponBean.class);
			if(bean.getCode() == 1){
				mAdapter.setBean(bean);
			}
			else{
				ToastUtil.show(CouponActivity.this, "获取优惠券信息失败");
			}
		}
	}

	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		mListView.onRefreshComplete();
		dialog.dismiss();
	}
	@Override
	public void OnGetCoupon() {
		// TODO Auto-generated method stub
		getCoupon();
		dialog = new LoadingDialog(this);
		dialog.show("加载中...", true, null);
		dialog.show();
	}
}
