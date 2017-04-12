package com.example.personal;

import java.io.File;
import java.util.Date;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.crop.CropImageActivity;
import com.example.utils.ActionSheetDialog;
import com.example.utils.ActionSheetDialog.OnDismisListener;
import com.example.utils.ActionSheetDialog.OnSheetItemClickListener;
import com.example.utils.ActionSheetDialog.SheetItemColor;
import com.example.view.commonview.ReWebChomeClient;
import com.example.view.commonview.ReWebChomeClient.OpenFileChooserCallBack;
import com.example.view.commonview.ReWebViewClient;

public class WebViewActivity extends BaseActivity implements OnClickListener,OpenFileChooserCallBack{
	private static final int FLAG_CHOOSE_IMG = 5;
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final int FLAG_MODIFY_FINISH = 7;
	private static String localTempImageFileName = "";
	public static final File FILE_SDCARD = Environment
			.getExternalStorageDirectory();
	public static final String IMAGE_PATH = "namexpress";
	public static final File FILE_LOCAL = new File(FILE_SDCARD, IMAGE_PATH);
	public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
			"multipart/form-data");
	private WebView mWebView;
	private ReWebChomeClient webChromeClient;
	private ReWebViewClient mClient;
	private TextView centerView;
    
	private String url;
	private Intent mSourceIntent;
	private ValueCallback<Uri> mUploadMsg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
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
		mClient = new ReWebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		};
		webChromeClient = new ReWebChomeClient(this){
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
	}
	@Override
	public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg,
			String acceptType) {
		// TODO Auto-generated method stub
		mUploadMsg = uploadMsg;  
        customDialog();
	}
	private void customDialog() {
		ActionSheetDialog dialog = new ActionSheetDialog(this);
		dialog.setOnDismissListener(new OnDismisListener() {
			@Override
			public void dismiss() {
				// TODO Auto-generated method stub
				if (mUploadMsg != null) {  
	                mUploadMsg.onReceiveValue(null);  
	                mUploadMsg = null;  
	            }  
			}
		});
		dialog.builder()
				.setCancelable(false)
				.setCanceledOnTouchOutside(false)

				.addSheetItem(getResources().getString(R.string.take_photo),
						SheetItemColor.Green, new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								cameraMethod();
							}
						})
				.addSheetItem(
						getResources().getString(
								R.string.Choose_from_the_pictures),
						SheetItemColor.Green, new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								Intent intent = new Intent();
								intent.setAction(Intent.ACTION_PICK);
								intent.setType("image/*");
								startActivityForResult(intent, FLAG_CHOOSE_IMG);

							}
						}).show();
	}
	/*
	 * 拍照
	 */
	public void cameraMethod() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				localTempImageFileName = "";
				localTempImageFileName = String.valueOf((new Date()).getTime())
						+ ".png";
				File filePath = FILE_PIC_SCREENSHOT;
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
				Intent intent1 = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				File f = new File(filePath, localTempImageFileName);
				// localTempImgDir和localTempImageFileName是自己定义的名字
				Uri u = Uri.fromFile(f);
				intent1.putExtra(ImageColumns.ORIENTATION, 0);
				intent1.putExtra(MediaStore.EXTRA_OUTPUT, u);
				startActivityForResult(intent1, FLAG_CHOOSE_PHONE);
			} catch (ActivityNotFoundException e) {
			}
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FLAG_CHOOSE_IMG && resultCode == -1) {
			if (data != null) {
				Uri uri = data.getData();
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					Cursor cursor = this.getContentResolver().query(uri,
							new String[] { MediaColumns.DATA }, null, null,
							null);
					if (null == cursor) {
						return;
					}
					cursor.moveToFirst();
					String path = cursor.getString(cursor
							.getColumnIndex(MediaColumns.DATA));
					cursor.close();
					Intent intent = new Intent(this, CropImageActivity.class);
					intent.putExtra("path", path);
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				} else {
					Intent intent = new Intent(this, CropImageActivity.class);
					intent.putExtra("path", uri.getPath());
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				}
			}
		} else if (requestCode == FLAG_CHOOSE_PHONE && resultCode == -1) {
			File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);
			Intent intent = new Intent(this, CropImageActivity.class);
			intent.putExtra("path", f.getAbsolutePath());
			startActivityForResult(intent, FLAG_MODIFY_FINISH);
		} else if (requestCode == FLAG_MODIFY_FINISH && resultCode == -1) {
			if (data != null) {
				final String path = data.getStringExtra("path");
				File file = new File(path);
				if (!file.exists()) {
					// 若不存在，创建目录，可以在应用启动的时候创建
					file.mkdirs();
				}
//				doUploadTest(file);
				Uri uri = Uri.fromFile(file);  
                mUploadMsg.onReceiveValue(uri);  
			}
		}
	}
	 
}
