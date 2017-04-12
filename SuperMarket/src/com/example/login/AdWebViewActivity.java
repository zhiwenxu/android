package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.main.MainActivity;
import com.example.utils.SharePerencesUtil;

public class AdWebViewActivity extends BaseActivity implements OnClickListener{
	private WebView mWebView;
	private WebChromeClient webChromeClient;
	private WebViewClient mClient;
	private TextView centerView;
	private String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad_webview);
		initTitle();
		init();
	}
	private void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	private void init(){
		url = getIntent().getStringExtra("url");
		mWebView = (WebView)findViewById(R.id.webview);
		mWebView.loadUrl(url);
		mClient = new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		};
		webChromeClient = new WebChromeClient(){
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
				centerView.setText(title);
			}
		};
		WebSettings mWebSettings = mWebView.getSettings();
		mWebSettings.setDomStorageEnabled(true);  
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
		mWebView.setWebViewClient(mClient);
		mWebView.setWebChromeClient(webChromeClient);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			if(SharePerencesUtil.getInstances().getLogin()){
				Intent intent = new Intent(this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			else
			{
				Intent intent = new Intent(this,ChangeActivity.class);
				startActivity(intent);
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			if(SharePerencesUtil.getInstances().getLogin()){
				Intent intent = new Intent(this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			else
			{
				Intent intent = new Intent(this,ChangeActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}
}
