package cn.app9010.supermarket.wxapi;


import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.application.BaseActivity;
import com.example.application.BaseApplication;
import com.example.main.MainActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.LoginBean;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler,VollerCallBack{
	
	// IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private VolleyNet net;
    private LoadingDialog dialog;
    private Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
			try {
				JSONObject object = new JSONObject((String)msg.obj);
	    		String openid = object.getString("openid");
				String nick = object.getString("nickname");
				String headurl = object.getString("headimgurl");
				SharePerencesUtil.getInstances().setNick(nick);
				SharePerencesUtil.getInstances().setOpenid(openid);
				SharePerencesUtil.getInstances().setHeadUrl(headurl);
				login(openid, headurl, nick);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    	};
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
    	api = WXAPIFactory.createWXAPI(this, BaseApplication.WXAPP_KEY, false);
    	api.handleIntent(getIntent(), this);
    	dialog = new LoadingDialog(this);
		dialog.setMessage("加载中...");
		dialog.show();
	}
	
	//获得access_token
	private void getaccess_token(String code){
		net = new VolleyNet();
    	net.setOnVellerCallBack(this);
		net.Get("https://api.weixin.qq.com/sns/oauth2/access_token?" +
				"appid=wxe43806bd58c4856e&secret=af82f8f5004523539d281e837703b79f"
				+"&code="+code+"&grant_type=authorization_code", 0);
	}
	//获得微信用户信息
	private void getWxInfo(String access_token , String openid){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("access_token", access_token);
		map.put("openid", openid);
		net.Post("https://api.weixin.qq.com/sns/userinfo?" ,map,1);
	}
	//微信登陆
	private void login(String openid,String headurl,String nickname){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("openid",openid);
		map.put("headurl",headurl);
		map.put("nickname",nickname);
		map.put("type","2");//微信联合登陆
		net.Post(ServerURL.LOGIN_UNION_URL, map, 2);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}
	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		String code = ((SendAuth.Resp)resp).code;
		getaccess_token(code);
	}

	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 0){
			try {
				JSONObject object = new JSONObject(result);
				final String access_token = object.getString("access_token");
				final String openid = object.getString("openid");
				new Thread(){
					public void run() {
						getWeixinUserinfo(access_token,openid);
					};
				}.start();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(requestCode == 2){
			LoginBean bean = GsonHelper.getGson().fromJson(result,LoginBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				if(bean.getData() != null){
					SharePerencesUtil.getInstances().setID(bean.getData().getID());
					SharePerencesUtil.getInstances().setUUID(bean.getData().getUuid());
					SharePerencesUtil.getInstances().setNick(bean.getData().getNick());
					SharePerencesUtil.getInstances().setGender(bean.getData().getGender());
					SharePerencesUtil.getInstances().setCents(bean.getData().getCents());
					SharePerencesUtil.getInstances().setCard(bean.getData().getCard());
					SharePerencesUtil.getInstances().setRmb(bean.getData().getRmb());
					SharePerencesUtil.getInstances().setHeadUrl(bean.getData().getHeadurl());
				}
				SharePerencesUtil.getInstances().setLogin(true);
				SharePerencesUtil.getInstances().setLoginType(2);
				Intent intent = new Intent(this,MainActivity.class);
				startActivity(intent);
				finish();
				if(dialog != null)
					dialog.dismiss();
			}
		}
	}

	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}
	
	/** 
     * 获取微信用户信息 
     * @param access_token 调用凭证 
     * @param openid 普通用户的标识，对当前开发者帐号唯一 
     * @return 
     */  
    public void getWeixinUserinfo(String access_token, String openid){  
        String URLs = "https://api.weixin.qq.com/sns/userinfo?";//微信登录地址  
        String result = "";  
        List<NameValuePair> list = new ArrayList<NameValuePair>();  
  
        NameValuePair pairappid = new BasicNameValuePair("access_token", access_token);  
        NameValuePair pairsecret = new BasicNameValuePair("openid", openid);  
        list.add(pairappid);  
        list.add(pairsecret);  
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
}
