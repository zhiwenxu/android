package com.example.network;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.application.BaseApplication;
import com.example.login.LoginActivity;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;

/**
 * @author XUZHIWEN
 * 网络请求加载类
 * 2016/8/9
 */
public class VolleyNet{
	public static final int ONE_CODE = 1;
	public static final int ZERO_CODE = 0;
	private VollerCallBack mVollerCallBack = null;
	/*
	 * post方式请求
	 */
	public void Post(String url,final Map<String, String> map,final int requestCode){
		RequestQueue mQueue = Volley.newRequestQueue(BaseApplication.getInstance());
		StringRequest request = new StringRequest(Method.POST, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(result);
					if(jsonObject.getInt("code") == -1){
						Intent intent1 = new Intent();
						//退出登陆，清空登陆信息
						BaseApplication.exit();
						SharePerencesUtil.getInstances().setNick("");
						SharePerencesUtil.getInstances().setGender(0);
						SharePerencesUtil.getInstances().setCents(0);
						SharePerencesUtil.getInstances().setCard("");
						SharePerencesUtil.getInstances().setRmb(0);
						SharePerencesUtil.getInstances().setLogin(false);
						SharePerencesUtil.getInstances().setPassWord("");
						SharePerencesUtil.getInstances().setLoginType(0);
						intent1.setClass(BaseApplication.getInstance(), LoginActivity.class);
						intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						BaseApplication.getInstance().startActivity(intent1);
						ToastUtil.show(BaseApplication.getInstance(), "登陆过期，请重新登陆");
						return;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(mVollerCallBack != null)
					mVollerCallBack.OnSuccess(requestCode, result);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				if(mVollerCallBack != null)
					mVollerCallBack.OnError(requestCode, error.getMessage());
				ToastUtil.show(BaseApplication.getInstance(), "网络加载错误");
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// TODO Auto-generated method stub
				return map;
			}
		};
		mQueue.add(request);
	}
	
	/*
	 * get方式请求
	 */
	public void Get(String url,final int requestCode){
		RequestQueue mQueue = Volley.newRequestQueue(BaseApplication.getInstance());
		StringRequest request = new StringRequest(url, new Response.Listener<String>() {
			@Override
			public void onResponse(String result) {
				// TODO Auto-generated method stub
				if(mVollerCallBack != null)
					mVollerCallBack.OnSuccess(requestCode, result);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				if(mVollerCallBack != null)
					mVollerCallBack.OnError(requestCode, error.getMessage());
				ToastUtil.show(BaseApplication.getInstance(), "网络加载错误");
			}
		});
		mQueue.add(request);
	}

	
	//设置监听接口
	public void setOnVellerCallBack(VollerCallBack vollerCallBack){
		this.mVollerCallBack = vollerCallBack;
	}
	/**
	 *　网络请求回调接口
	 * 
	 */
	public interface VollerCallBack{
		/*
		 * @param requestCode 请求码
		 * @param result 返回的字符串结果
		 */
		public abstract void OnSuccess(int requestCode,String result);
		
		/*
		 * @param requestCode 请求码
		 * @param result 错误消息
		 */
		public abstract void OnError(int requestCode,String errorMsg);
	}
	
	

}
