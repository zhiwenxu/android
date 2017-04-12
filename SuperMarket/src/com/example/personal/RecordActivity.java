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

import com.example.adapter.RecordAdapter;
import com.example.application.BaseActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.XfRecordBean;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 消费记录页面
 */
public class RecordActivity extends BaseActivity implements OnClickListener,VollerCallBack{

	private PullToRefreshListView mListView;
	private View emptyView;
	private RecordAdapter mAdapter;
	private VolleyNet net;
	private LoadingDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		initTitle();
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText(getResources().getString(R.string.record));
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	
	public void init(){
		mListView = (PullToRefreshListView)findViewById(R.id.record_list);
		mListView.setMode(Mode.BOTH);
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getRecord();
			}
		});
		emptyView = LayoutInflater.from(this).inflate(R.layout.list_empty_view, null);
		mListView.setEmptyView(emptyView);
		dialog = new LoadingDialog(this);
		dialog.setMessage("加载中...");
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		getRecord();
		dialog.show();
	}

	public void getRecord(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id",SharePerencesUtil.getInstances().getID());
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		map.put("fromdate","");
		map.put("todate","");
		net.Post(ServerURL.SPEND_RECORD_URL, map, 0);
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
		if(requestCode == 0){//获得消费记录
			mListView.onRefreshComplete();
			XfRecordBean bean = GsonHelper.getGson().fromJson(result, XfRecordBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){//获取成功
				mAdapter = new RecordAdapter(this,bean);
				mListView.setAdapter(mAdapter);
				mListView.setMode(Mode.BOTH);
				dialog.dismiss();
			}
			else//获取失败
			{
				dialog.dismiss();
				ToastUtil.show(RecordActivity.this, "获取消费记录失败");
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
