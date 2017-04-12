package com.example.login;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.app9010.supermarket.R;

import com.example.adapter.ViewPagerAdapter;
import com.example.application.BaseActivity;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GuideActivity extends BaseActivity implements OnClickListener,OnPageChangeListener,VollerCallBack{
	//引导图片资源  
    private static final int[] pics = { R.drawable.ydy1,  
            R.drawable.ydy2, R.drawable.ydy3};  
	private ViewPager vp;  
    private ViewPagerAdapter vpAdapter;  
    private List<View> views; 
    private Button mEnterBtn;
    private VolleyNet net;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private String mUrl1,mUrl2,mUrl3;
    private List<String> urls = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		net.Post(ServerURL.GET_JSON_URL, null, 0);
	}
	private void init(boolean network){
		views = new ArrayList<View>();  
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  
                LinearLayout.LayoutParams.WRAP_CONTENT);  
        //初始化引导图片列表  
        for(int i=0; i<pics.length; i++) {  
            ImageView iv = new ImageView(this);  
            iv.setLayoutParams(mParams);
            if(network){
            	if(i == 0){
            		imageLoader.displayImage(urls.get(i), iv, ImageLoaderUtil.getInstance().getGuideOptions());
            	}
            	else if( i == 1){
            		imageLoader.displayImage(urls.get(i), iv, ImageLoaderUtil.getInstance().getGuideOptions2());
            	}
            	else
            	{
            		imageLoader.displayImage(urls.get(i), iv, ImageLoaderUtil.getInstance().getGuideOptions3());
            	}
            	
            }
            else
            {
            	iv.setImageResource(pics[i]);  
            }
            views.add(iv);  
        }  
        vp = (ViewPager) findViewById(R.id.viewpager);
        mEnterBtn = (Button) findViewById(R.id.guide_start_btn);
        //初始化Adapter  
        vpAdapter = new ViewPagerAdapter(views);  
        vp.setAdapter(vpAdapter);  
        //绑定回调  
        vp.setOnPageChangeListener(this);  
        mEnterBtn.setOnClickListener(this);
	}
    //当滑动状态改变时调用  
    @Override  
    public void onPageScrollStateChanged(int arg0) {  
        // TODO Auto-generated method stub  
    }  
    //当当前页面被滑动时调用  
    @Override  
    public void onPageScrolled(int arg0, float arg1, int arg2) {  
        // TODO Auto-generated method stub  
    }  
    //当新的页面被选中时调用  
    @Override  
    public void onPageSelected(int arg0) {  
    	if(arg0 == views.size()-1){
    		mEnterBtn.setVisibility(View.VISIBLE);
    	}
    	else{
    		mEnterBtn.setVisibility(View.INVISIBLE);
    	}
    }  
    @Override  
    public void onClick(View v) {  
        if(v == mEnterBtn){
        	Intent intent = new Intent(this,ChangeActivity.class);
        	startActivity(intent);
        	finish();
        }
    }
	@Override
	public void OnSuccess(int requestCode, String result) {
		if(requestCode == 0){
			try {
				JSONObject object = new JSONObject(result);
				JSONArray array = object.getJSONArray("firstLoadPics");
				for(int i = 0;i<array.length();i++){
					String name = array.getJSONObject(i).getString("name");
					if(name.equals("Pic1")){
						mUrl1 = array.getJSONObject(i).getString("url");
					}
					if(name.equals("Pic2")){
						mUrl2 = array.getJSONObject(i).getString("url");
					}
					if(name.equals("Pic3")){
						mUrl3 = array.getJSONObject(i).getString("url");
					}
				}
				urls.add(mUrl1);
				urls.add(mUrl2);
				urls.add(mUrl3);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		init(true);
	}
	@Override
	public void OnError(int requestCode, String errorMsg) {
		init(false);
	}  
}
