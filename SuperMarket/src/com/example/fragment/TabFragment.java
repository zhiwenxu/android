package com.example.fragment;

import cn.app9010.supermarket.R;

import com.example.personal.WebViewActivity;
import com.example.utils.SharePerencesUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;

import android.content.Intent;
import android.os.Bundle;  
import android.support.v4.app.Fragment;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TabFragment extends Fragment{
	
	private String url = "";
	private WebView mWebView;
	private WebViewClient mClient;
	private PullToRefreshWebView pw;
	public void setURL(String url){
		this.url = url;
	}
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
    	View view  = inflater.inflate(R.layout.fragment_tab,null);
    	pw = (PullToRefreshWebView)view.findViewById(R.id.webview);
    	mWebView = pw.getRefreshableView();
    	
        return view;  
    }  
    @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onActivityCreated(savedInstanceState); 
        initWebView();
    }  
      
    private void initWebView(){
		mClient = new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				
				String lybk = "http://119.29.249.194/weibo/9010/zd.html?user_name="
						+SharePerencesUtil.getInstances().getNick()+"&user_img="+
						SharePerencesUtil.getInstances().getHeadUrl()+"&mobile="+
						SharePerencesUtil.getInstances().getID();
				if(url.equals(SharePerencesUtil.getInstances().getCXHD())
						|| url.equals(SharePerencesUtil.getInstances().getXPCX())
						|| url.equals(SharePerencesUtil.getInstances().getGSXW())
						||url.equals(SharePerencesUtil.getInstances().getGSZX())
						|| url.equals(SharePerencesUtil.getInstances().getZPXX())
						|| url.equals(lybk)){
					view.loadUrl(url);
				}
				else
				{
					Intent intent = new Intent(getActivity(),WebViewActivity.class);
					intent.putExtra("url", url);
					getActivity().startActivity(intent);
				}
				return true;
			}
			
		};
		WebSettings mWebSettings = mWebView.getSettings();
		mWebSettings.setDomStorageEnabled(true);  
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
		mWebView.loadUrl(url);
		mWebView.setWebViewClient(mClient);
		
	}
}
