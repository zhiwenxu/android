package com.example.login;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.main.MainActivity;
import com.example.network.ServerURL;
import com.example.utils.ImageLoaderUtil;
import com.example.utils.SharePerencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class StartActivity extends BaseActivity implements OnClickListener{
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private String adUrl = "";
	private String Url = "";
	private boolean isLoad = false;
	private boolean isJump = false;
	private Button mJumpBtn;
	private RelativeLayout mLayout;
	private ImageView mImage;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		init();
	}
	private void initHandler(){
		handler = new Handler(){
			public void handleMessage(Message msg) {
				if(msg.what == 0){
					try {
						JSONObject object = new JSONObject((String)msg.obj);
						Log.e("",object+"");
						adUrl = object.getJSONArray("everyLoadAd").getJSONObject(0).getString("adImageUrl");
						Url = object.getJSONArray("everyLoadAd").getJSONObject(0).getString("adUrl");
						JSONArray array1 = object.getJSONArray("InAppNewsLinks");
						JSONArray array2 = object.getJSONArray("SetsLinks");
						JSONArray array3 = object.getJSONArray("everyLogin");
						if(array3.length()>0){
							String changeImage = array3.getJSONObject(0).getString("adImageUrl");
							SharePerencesUtil.getInstances().setChangeImage(changeImage);
						}
						//获得咨询链接
						for(int i=0;i<array1.length();i++){
							String name = array1.getJSONObject(i).getString("name");
							String url = array1.getJSONObject(i).getString("url");
							if(name.equals("促销活动")){
								SharePerencesUtil.getInstances().setCXHD(url);
							}
							if(name.equals("新品尝鲜")){
								SharePerencesUtil.getInstances().setXPCX(url);
							}
							if(name.equals("公司新闻")){
								SharePerencesUtil.getInstances().setGSXW(url);
							}
							if(name.equals("公司资讯")){
								SharePerencesUtil.getInstances().setGSZX(url);
							}
							if(name.equals("招聘信息")){
								SharePerencesUtil.getInstances().setZPXX(url);
							}
							if(name.equals("留言板块")){
								SharePerencesUtil.getInstances().setLYBK(url);
							}
						}
						//获得设置链接
						for(int i=0;i<array2.length();i++){
							String name = array2.getJSONObject(i).getString("name");
							String url = array2.getJSONObject(i).getString("url");
							if(name.equals("常见问题")){
								SharePerencesUtil.getInstances().setCJWT(url);
							}
							if(name.equals("用户条款")){
								SharePerencesUtil.getInstances().setYHTK(url);
							}
							if(name.equals("关于我们")){
								SharePerencesUtil.getInstances().setGYWM(url);
							}
						}
						SharePerencesUtil.getInstances().setLoad(true);
						mLayout.setVisibility(View.VISIBLE);
						imageLoader.displayImage(adUrl, mImage,ImageLoaderUtil.getInstance().getAdOptions());
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(isJump){
									
								}
								else
								{
									if(SharePerencesUtil.getInstances().getFirstUse()){
										SharePerencesUtil.getInstances().setFirstUse(false);
										Intent intent = new Intent(StartActivity.this,GuideActivity.class);
										startActivity(intent);
										finish();
									}
									else
									{
										if(SharePerencesUtil.getInstances().getLogin()){
											Intent intent = new Intent(StartActivity.this,MainActivity.class);
											startActivity(intent);
											finish();
										}
										else
										{
											Intent intent = new Intent(StartActivity.this,ChangeActivity.class);
											startActivity(intent);
											finish();
										}
									}
								}
							}
						}, 3000);
						isLoad = true;//加载成功
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		};
	}
	private void init(){
		
		mJumpBtn = (Button)findViewById(R.id.next_btn);
		mLayout = (RelativeLayout)findViewById(R.id.ad_layout);
		mLayout.setOnClickListener(this);
		mImage = (ImageView)findViewById(R.id.ad_image);
		mJumpBtn.setOnClickListener(this);
		initHandler();
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				getJsonUrl();
			}
		}.start();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//加载成功的时候去判断
				if(!isLoad)
				{
					if(SharePerencesUtil.getInstances().getLogin()){
						Intent intent = new Intent(StartActivity.this,MainActivity.class);
						startActivity(intent);
						finish();
					}
					else
					{
						Intent intent = new Intent(StartActivity.this,ChangeActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
		}, 6000);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void getJsonUrl(){  
        String URLs = ServerURL.GET_JSON_URL;
        String result = "";  
        List<NameValuePair> list = new ArrayList<NameValuePair>();  
        try{  
            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(list, HTTP.UTF_8);  
            URL url = new URL(URLs);  
            URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);  
            HttpPost httpPost = new HttpPost(uri);  
            httpPost.setEntity(requestHttpEntity);  
            HttpClient httpClient = new DefaultHttpClient();  
            HttpResponse response = httpClient.execute(httpPost);  
             if (response.getStatusLine().getStatusCode() == 200) {  
                 result = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);//在这里转换  
             }  
             Message msg = new Message();
             msg.obj = result;
             msg.what = 0;
             handler.sendMessage(msg);
        }catch(Exception e){  
            e.printStackTrace();     
        }  
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mJumpBtn){
			isJump = true;
			//加载成功进入引导页，第一次使用时
			if(SharePerencesUtil.getInstances().getFirstUse()){
				SharePerencesUtil.getInstances().setFirstUse(false);
				Intent intent = new Intent(StartActivity.this,GuideActivity.class);
				startActivity(intent);
				finish();
			}
			else
			{
				if(SharePerencesUtil.getInstances().getLogin()){
					Intent intent = new Intent(StartActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
				else
				{
					Intent intent = new Intent(StartActivity.this,ChangeActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}
		if(v == mLayout){
			if(isLoad){
				isJump = true;
				Intent intent = new Intent(StartActivity.this,AdWebViewActivity.class);
				intent.putExtra("url",Url);
				startActivity(intent);
				finish();
			}
		}
	} 
}
