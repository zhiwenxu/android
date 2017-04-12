package com.example.view.commonview;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import cn.app9010.supermarket.R;
 
public class ScanDialog extends Dialog implements android.view.View.OnClickListener{
	private Button mComfireBtn;
	private Button mConcelBtn;
	private Context mContext;
	private ProgressDialog pd;
	private String url = "http://do.xiazaiba.com/route.php?ct=stat&ac=stat&g=aHR0cDovL2ltdHQuZGQucXEuY29tLzE2ODkxLzc5NjYwNkQyRDdFQjc5MjQzMkREQUVDQ0EyRkVERTgyLmFwaz9mc25hbWU9Y29tLnRlbmNlbnQubW9iaWxlcXFfNi41LjhfNDIyLmFwaw==";
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0){
				pd.setMax(msg.arg1);
			}
			else
			{
				pd.setProgress(msg.arg1);
			}
		};
	};
    public ScanDialog(Context context) {
    	super(context, R.style.customprogressdialog);
        setContentView(R.layout.scan_dialog);
        mComfireBtn = (Button)findViewById(R.id.comfire_btn);
        mConcelBtn = (Button)findViewById(R.id.concel_btn);
        mComfireBtn.setOnClickListener(this);
        mConcelBtn.setOnClickListener(this);
        mContext = context;
    }
    /**
     * 当窗口焦点改变时调用
     */
    @Override
	public void onWindowFocusChanged(boolean hasFocus) {
    	
    }
 
    /**
     * 给Dialog设置提示信息
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
        	findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }
 
    /**
     * 弹出自定义ProgressDialog
     * 
     * @param context
     *            上下文
     * @param message
     *            提示
     * @param cancelable
     *            是否按返回键取消
     * @param cancelListener
     *            按下返回键监听
     * @return
     */
    public void show(boolean cancelable, OnCancelListener cancelListener) {
        // 按返回键是否取消
        setCancelable(cancelable);
        // 监听返回键处理
        setOnCancelListener(cancelListener);
        // 设置居中
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        show();
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mComfireBtn){
			
			downLoadApk();
			dismiss();
			
		}
		if(v == mConcelBtn){
			dismiss();
		}
	}
	
	public File getFileFromServer(String path) throws Exception{  
	    //如果相等的话表示当前的sdcard挂载在手机上并且是可用的  
	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
	        URL url = new URL(path);  
	        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5000);  
	        //获取到文件的大小 
	        Message message = new Message();
	        message.what = 0;
	        message.arg1 = conn.getContentLength();
	        handler.sendMessage(message); 
	        InputStream is = conn.getInputStream();  
	        File file = new File(Environment.getExternalStorageDirectory(), "9010便利店.apk");  
	        FileOutputStream fos = new FileOutputStream(file);  
	        BufferedInputStream bis = new BufferedInputStream(is);  
	        byte[] buffer = new byte[1024];  
	        int len ;  
	        int total=0;  
	        while((len =bis.read(buffer))!=-1){  
	            fos.write(buffer, 0, len);  
	            total+= len;  
	            //获取当前下载量  
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
	protected void downLoadApk() {  
	    pd = new  ProgressDialog(mContext);  
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	    pd.setMessage("正在下载更新");  
	    pd.show();  
	    
	    new Thread(){  
	        @Override  
	        public void run() {  
	            try {  
	                File file = getFileFromServer(url);  
	                sleep(1000);  
	                installApk(file);  
	                pd.dismiss(); //结束掉进度条对话框  
	            } catch (Exception e) {  
	            }  
	    }}.start();  
	} 
	
	//安装apk   
	protected void installApk(File file) {  
	    Intent intent = new Intent();  
	    //执行动作  
	    intent.setAction(Intent.ACTION_VIEW);  
	    //执行的数据类型  
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");  
	    mContext.startActivity(intent);  
	}  
}