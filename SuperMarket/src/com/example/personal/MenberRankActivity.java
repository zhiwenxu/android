package com.example.personal;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.adapter.RankAdapter;
import com.example.application.BaseActivity;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;

public class MenberRankActivity extends BaseActivity implements OnClickListener,VollerCallBack{
	
	private LoadingDialog dialog = null;
	private VolleyNet net;
	private ListView mListView;
	private TextView mLevel;
	private RankAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menber_rank);
		initTitle();
		init();
	}
	public void init(){
		mListView = (ListView)findViewById(R.id.rank_list);
		mLevel = (TextView)findViewById(R.id.level_tv);
		mAdapter = new RankAdapter(this);
		mListView.setAdapter(mAdapter);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		getRank();
	}
	
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText(getResources().getString(R.string.member_rank));
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	private void getRank(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phone",SharePerencesUtil.getInstances().getID());
		dialog = new LoadingDialog(this);
		dialog.show("加载中...", true, null);
		dialog.show();
		net.Post(ServerURL.USER_RANK_URL, map, 0);
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
			try {
				JSONObject object = new JSONObject(result);
				if(object.getInt("code") == 1){
					JSONObject data = object.getJSONObject("data");
					String rule = data.getString("levelrule");//暂时没用
					String level = data.getString("level");
					String rule_x = data.getString("levelrule_x");
					String rules[] = rule_x.split(",");
					mLevel.setText(level);
					mAdapter.setRules(rules);
					mAdapter.notifyDataSetChanged();
					dialog.dismiss();
				}
				else{
					ToastUtil.show(MenberRankActivity.this, "获取等级失败");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}
}
