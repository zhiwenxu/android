package com.example.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.view.commonview.ScanDialog;

import android.R.dimen;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.style.BulletSpan;

public class VersionCheck implements VollerCallBack{
	
	private Context mContext;
	private ProgressDialog pd;
	private String url = "";
	private String versionName = "";
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0){
				pd.setMax(msg.arg1);
			}
			else if(msg.what == 1)
			{
				pd.setProgress(msg.arg1);
			}
			else if(msg.what == 200){
				String result = (String) msg.obj;
				try {
					JSONObject object = new JSONObject(result);
					if(!object.isNull("versionName")&&!object.isNull("versionCode")&&!object.isNull("appUrl")){
						versionName = object.getString("versionName");
						url = object.getString("appUrl");
						check(versionName);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
	};
	public void checkVersion(Context context){
		this.mContext = context;
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				getVersionInfo();
			}
		}.start();	    
	}
	
	private void check(String versionName){
		//��ȡpackagemanager��ʵ��
	    PackageManager packageManager = mContext.getPackageManager();  
	    //getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ  
	    try {
			PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
			long nowDate = System.currentTimeMillis();
			long checkDate = SharePerencesUtil.getInstances().getVersionDate();
			if(!packInfo.versionName.equals(versionName) && nowDate - checkDate > 3600*12*1000)
			{
				AlertDialog.Builder builder = new Builder(mContext);
				builder.setTitle("�汾����");
				builder.setMessage("�����°汾���뼰ʱ����");
				builder.setPositiveButton("����", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						downLoadApk();
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("�ݲ�����", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create().show();
				SharePerencesUtil.getInstances().setVersionDate(nowDate);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	public File getFileFromServer(String path) throws Exception{  
	    //�����ȵĻ���ʾ��ǰ��sdcard�������ֻ��ϲ����ǿ��õ�  
	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
	        URL url = new URL(path);  
	        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5000);  
	        //��ȡ���ļ��Ĵ�С 
	        Message message = new Message();
	        message.what = 0;
	        message.arg1 = conn.getContentLength();
	        handler.sendMessage(message); 
	        InputStream is = conn.getInputStream();  
	        File file = new File(Environment.getExternalStorageDirectory(), "9010������.apk");  
	        FileOutputStream fos = new FileOutputStream(file);  
	        BufferedInputStream bis = new BufferedInputStream(is);  
	        byte[] buffer = new byte[1024];  
	        int len ;  
	        int total=0;  
	        while((len =bis.read(buffer))!=-1){  
	            fos.write(buffer, 0, len);  
	            total+= len;  
	            //��ȡ��ǰ������  
	            Message msg = new Message();
	            msg.what = 1;
	            msg.arg1 = total;
	            handler.sendMessage(msg);
	        }  
	        fos.close();  
	        bis.close();  
	        is.close();  
	        return file;  
	    }  
	    else{  
	        return null;  
	    }  
	}  
	
	public void getVersionInfo(){  
        String URLs = ServerURL.GET_VERSION_JSON_URL;
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
                 result = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);//������ת��  
             }  
             Message msg = new Message();
             msg.obj = result;
             msg.what = 200;
             handler.sendMessage(msg);
        }catch(Exception e){  
            e.printStackTrace();     
        }  
    }
	@SuppressLint("NewApi")
	protected void downLoadApk() {  
	    pd = new  ProgressDialog(mContext);  
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); 
	    pd.setMessage("�������ظ���");  
	    pd.setProgressNumberFormat(String.format("")); 
	    pd.show();  
	    new Thread(){  
	        @Override  
	        public void run() {  
	            try {  
	                File file = getFileFromServer(url);  
	                sleep(1000);  
	                installApk(file);  
	                pd.dismiss(); //�������������Ի���  
	            } catch (Exception e) {  
	            }  
	    }}.start();  
	} 
	
	//��װapk   
	protected void installApk(File file) {  
	    Intent intent = new Intent();  
	    //ִ�ж���  
	    intent.setAction(Intent.ACTION_VIEW);  
	    //ִ�е���������  
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");  
	    mContext.startActivity(intent);  
	}  
	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 0){
			
		}
	}

	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}

}
