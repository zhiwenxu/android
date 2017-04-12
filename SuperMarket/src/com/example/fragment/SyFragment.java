package com.example.fragment;


import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.app9010.supermarket.R;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.Volley;
import com.example.adapter.TabAdapter;
import com.example.crop.CropImageActivity;
import com.example.listener.OnBarPressListener;
import com.example.listener.OnIconChangeListener;
import com.example.login.LoginActivity;
import com.example.main.BandPhoneActivity;
import com.example.main.MainActivity;
import com.example.personal.WebViewActivity;
import com.example.utils.ActionSheetDialog;
import com.example.utils.ActionSheetDialog.OnSheetItemClickListener;
import com.example.utils.ActionSheetDialog.SheetItemColor;
import com.example.utils.ImageLoaderUtil;
import com.example.utils.MultipartRequest;
import com.example.utils.SharePerencesUtil;
import com.example.view.commonview.CircleImageView;
import com.example.viewhelper.SyBarHelper;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") 
public class SyFragment extends Fragment implements OnBarPressListener,OnClickListener{
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private TextView mSyNameTv,mSyHyCardTv;
	private CircleImageView mSyIcon;
	private ViewPager mViewPager;
	private SyBarHelper helper;
	private OnIconChangeListener mIconLisener;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sy, null);
		init(view);
		return view;
	}
	public void init(View view){
		mSyNameTv = (TextView)view.findViewById(R.id.sy_name);
		mSyHyCardTv = (TextView)view.findViewById(R.id.sy_huiyuan);
		mSyIcon = (CircleImageView)view.findViewById(R.id.icon);
		mViewPager = (ViewPager)view.findViewById(R.id.view_pager);
		helper = new SyBarHelper(getActivity());
		mSingleQueue = Volley.newRequestQueue(getActivity());
		helper.setView(view);
		helper.setOnBarPressListener(this);
		mSyIcon.setOnClickListener(this);
		
		TabAdapter tabAdapter = new TabAdapter(((FragmentActivity) getActivity()).getSupportFragmentManager());
		String url[] = {
				SharePerencesUtil.getInstances().getCXHD(),
				SharePerencesUtil.getInstances().getXPCX(),
				SharePerencesUtil.getInstances().getGSXW()
		};
		tabAdapter.setURL(url);
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setAdapter(tabAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pager) {
				// TODO Auto-generated method stub
				helper.changeView(pager);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		//设置头像
		String photo = SharePerencesUtil.getInstances().getHeadUrl();
		if(!photo.equals("")){
			imageLoader.displayImage(photo, mSyIcon);
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		mSyNameTv.setText(SharePerencesUtil.getInstances().getNick());
		mSyHyCardTv.setText("会员卡号  "+SharePerencesUtil.getInstances().getCard());
		
	}
	@Override
	public void onPress(int code) {
		mViewPager.setCurrentItem(code);
	}
	private void customDialog() {
		new ActionSheetDialog(getActivity())
				.builder()
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
					Cursor cursor = getActivity().getContentResolver().query(uri,
							new String[] { MediaColumns.DATA }, null, null,
							null);
					if (null == cursor) {
						return;
					}
					cursor.moveToFirst();
					String path = cursor.getString(cursor
							.getColumnIndex(MediaColumns.DATA));
					cursor.close();
					Intent intent = new Intent(getActivity(), CropImageActivity.class);
					intent.putExtra("path", path);
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				} else {
					Intent intent = new Intent(getActivity(), CropImageActivity.class);
					intent.putExtra("path", uri.getPath());
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				}
			}
		} else if (requestCode == FLAG_CHOOSE_PHONE && resultCode == -1) {
			File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);
			Intent intent = new Intent(getActivity(), CropImageActivity.class);
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
				doUploadTest(file);
			}
		}
	}

	/* 上传图片 */
	private void doUploadTest(File file) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("uuid", SharePerencesUtil.getInstances().getUUID());
		// if (!file.exists()) {
		// Toast.makeText(getApplicationContext(), "图片不存在，测试无效",
		// Toast.LENGTH_SHORT).show();
		// return;
		// }
		MultipartRequest request = new MultipartRequest("http://119.29.115.106:80/app/user/photo/set?",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							JSONObject object = new JSONObject(response);
							String url = object.getJSONObject("data").getString("headurl");
							imageLoader.displayImage(url, mSyIcon,ImageLoaderUtil.getInstance().getOptions());
							SharePerencesUtil.getInstances().setHeadUrl(url);
							if(mIconLisener != null){
								mIconLisener.OnIconChange();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(),
								"uploadError,response = " + error.getMessage(),
								Toast.LENGTH_SHORT).show();
					}
				}, "f_file[]", file, params); // 注意这个key必须是f_file[],后面的[]不能少
		mSingleQueue.add(request);
	}
	private static RequestQueue mSingleQueue;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(!SharePerencesUtil.getInstances().getLogin()){
			Intent intent = new Intent(getActivity(),LoginActivity.class);
			startActivity(intent);
			return;
		}
		//第三方登陆后，首先判断是否绑定手机
		if(SharePerencesUtil.getInstances().getID().equals("")){
			Intent intent = new Intent(getActivity(),BandPhoneActivity.class);
			startActivity(intent);
			return;
		}
		if(v == mSyIcon){
			customDialog();
		}
	}
	
	public void setOnIconChangeListener(OnIconChangeListener listener){
		this.mIconLisener = listener;
	}
	
	public void reSetIcon(){
		String photo = SharePerencesUtil.getInstances().getHeadUrl();
		if(!photo.equals("")){
			imageLoader.displayImage(photo, mSyIcon);
		}
	}
	
}
