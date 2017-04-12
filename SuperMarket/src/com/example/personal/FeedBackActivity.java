package com.example.personal;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.FeedBackBean;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;

public class FeedBackActivity extends BaseActivity implements OnClickListener,VollerCallBack{

	private EditText mContent;
	private Button mCommit;
	private VolleyNet net;
	private LoadingDialog dialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		initTitle();
		init();
	}
	
	private void init(){
		mContent = (EditText)findViewById(R.id.feed_back_edit);
		mCommit = (Button)findViewById(R.id.feed_back_btn);
		mCommit.setOnClickListener(this);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
	}
	
	//初始化标题
	private void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText("意见反馈");
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	private void feedBack(){
		String content = mContent.getText().toString();
		if(content.equals("")){
			ToastUtil.show(this, "反馈内容不能为空！");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("content",content);
		map.put("uuid", SharePerencesUtil.getInstances().getUUID());
		dialog = new LoadingDialog(this);
		dialog.show("反馈中...", true, null);
		dialog.show();
		net.Post(ServerURL.FEED_BACK_URL, map, 0);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
		if(v == mCommit){
			feedBack();
		}
	}

	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 0){
			FeedBackBean bean = GsonHelper.getGson().fromJson(result, FeedBackBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				dialog.dismiss();
				ToastUtil.show(FeedBackActivity.this, "反馈成功");
				mContent.setText("");
			}
			else{
				ToastUtil.show(FeedBackActivity.this,"反馈失败");
			}
		}
	}
	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
}
