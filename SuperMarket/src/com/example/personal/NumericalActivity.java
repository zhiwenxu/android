package com.example.personal;

import java.util.HashMap;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.adapter.NumericalAdapter;
import com.example.application.BaseActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.NumericalBean;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class NumericalActivity extends BaseActivity implements OnClickListener,VollerCallBack{
	private PullToRefreshListView mListView;
	private View emptyView;
	private NumericalAdapter mAdapter;
	private VolleyNet net;
	private LoadingDialog dialog;
	private TextView numericalNum;
	private ImageView yuan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_numerical);
		initTitle();
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void init(){
		mListView = (PullToRefreshListView)findViewById(R.id.numberical_list);
		numericalNum = (TextView)findViewById(R.id.numerical_header_num);
		yuan = (ImageView)findViewById(R.id.yuan_iv);
		yuan.setImageResource(R.drawable.bl_amin);  
		AnimationDrawable  animationDrawable = (AnimationDrawable) yuan.getDrawable();  
		if(!animationDrawable.isRunning()){
			animationDrawable.start();  
		}
		mListView.setMode(Mode.BOTH);
		emptyView = LayoutInflater.from(this).inflate(R.layout.list_empty_view, null);
		mListView.setEmptyView(emptyView);
		dialog = new LoadingDialog(this);
		dialog.setMessage("加载中...");
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getNumerical();
			}
		});
		getNumerical();
		dialog.show();
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getNumerical();
			}
		});
	}
	
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText(getResources().getString(R.string.numerical));
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}

	public void getNumerical(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id",SharePerencesUtil.getInstances().getID());
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		map.put("fromdate","");
		map.put("todate","");
		net.Post(ServerURL.CENTS_RECORD_URL, map, 0);
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
		if(requestCode == 0){//积分查询
			mListView.onRefreshComplete();
			NumericalBean bean = GsonHelper.getGson().fromJson(result, NumericalBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){//查询成功
				numericalNum.setText(""+bean.getData().getCents());
				mAdapter = new NumericalAdapter(this,bean);
				mListView.setAdapter(mAdapter);
				dialog.dismiss();
			}
			else{//查询失败
				dialog.dismiss();
				ToastUtil.show(NumericalActivity.this, "积分信息获取失败");
			}
		}
	}

	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		mListView.onRefreshComplete();
		dialog.dismiss();
		
	}
}
