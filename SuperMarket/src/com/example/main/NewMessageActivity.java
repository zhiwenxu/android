package com.example.main;

import android.os.Bundle;

import cn.app9010.supermarket.R;

import com.example.adapter.NewMessageAdapter;
import com.example.application.BaseActivity;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class NewMessageActivity extends BaseActivity {
	private PullToRefreshListView mListView;
	private NewMessageAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_message);
		initTitle();
		init();
	}
	
	public void initTitle(){
		
	}
	
	public void init(){
		mListView = (PullToRefreshListView)findViewById(R.id.new_message_list);
		mAdapter = new NewMessageAdapter(this);
		mListView.setAdapter(mAdapter);
	}
}
