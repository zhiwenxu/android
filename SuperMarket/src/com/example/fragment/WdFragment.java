package com.example.fragment;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.MediaColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.app9010.supermarket.R;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.application.BaseApplication;
import com.example.crop.CropImageActivity;
import com.example.listener.OnIconChangeListener;
import com.example.login.LoginActivity;
import com.example.login.RegisterInfoActivity;
import com.example.main.BandPhoneActivity;
import com.example.main.MainActivity;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.personal.MenberRankActivity;
import com.example.personal.MyCouponActivity;
import com.example.personal.NumericalActivity;
import com.example.personal.RecordActivity;
import com.example.personal.SettingActivity;
import com.example.personal.WalletActivity;
import com.example.utils.ActionSheetDialog;
import com.example.utils.ActionSheetDialog.OnSheetItemClickListener;
import com.example.utils.ActionSheetDialog.SheetItemColor;
import com.example.utils.MultipartRequest;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("NewApi")
public class WdFragment extends Fragment implements OnClickListener,VollerCallBack{
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private RelativeLayout mWalletBtn,mNumericalBtn,mRecordBtn,mMenberRankBtn,mCouponBtn,mSettingBtn;
	private TextView mWdNameTv,mHyCardTv,mWdEditTv;
	private Button mExitBtn;
	private CircleImageView mWdIcon;
	private VolleyNet net;
	private OnIconChangeListener mIconListener;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_wd, null);
		init(view);
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		mWdNameTv.setText(SharePerencesUtil.getInstances().getNick());
		mHyCardTv.setText("会员卡号  "+SharePerencesUtil.getInstances().getCard());
		
	}
	public void init(View view){
		mWalletBtn = (RelativeLayout)view.findViewById(R.id.walle_btn);
		mNumericalBtn = (RelativeLayout)view.findViewById(R.id.numerical_btn);
		mRecordBtn = (RelativeLayout)view.findViewById(R.id.record_btn);
		mMenberRankBtn = (RelativeLayout)view.findViewById(R.id.menber_rank_btn);
		mCouponBtn = (RelativeLayout)view.findViewById(R.id.coupon_btn);
		mSettingBtn = (RelativeLayout)view.findViewById(R.id.setting_btn);
		mWdNameTv = (TextView)view.findViewById(R.id.wd_name);
		mHyCardTv = (TextView)view.findViewById(R.id.wd_card);
		mWdEditTv = (TextView)view.findViewById(R.id.wd_edit);
		mExitBtn = (Button)view.findViewById(R.id.exit_btn);
		mWdIcon = (CircleImageView)view.findViewById(R.id.icon);
		mSingleQueue = Volley.newRequestQueue(getActivity());
		mExitBtn.setOnClickListener(this);
		mWalletBtn.setOnClickListener(this);
		mNumericalBtn.setOnClickListener(this);
		mRecordBtn.setOnClickListener(this);
		mMenberRankBtn.setOnClickListener(this);
		mCouponBtn.setOnClickListener(this);
		mWdEditTv.setOnClickListener(this);
		mSettingBtn.setOnClickListener(this);
		mWdIcon.setOnClickListener(this);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		String photo = SharePerencesUtil.getInstances().getHeadUrl();
		if(!photo.equals("")){
			imageLoader.displayImage(photo, mWdIcon);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mExitBtn){
			if(SharePerencesUtil.getInstances().getUUID().equals("")){
				Intent intent = new Intent();
				//退出登陆，清空登陆信息
				BaseApplication.exit();
				SharePerencesUtil.getInstances().setNick("");
				SharePerencesUtil.getInstances().setGender(0);
				SharePerencesUtil.getInstances().setCents(0);
				SharePerencesUtil.getInstances().setCard("");
				SharePerencesUtil.getInstances().setRmb(0);
				SharePerencesUtil.getInstances().setLogin(false);
				SharePerencesUtil.getInstances().setLoginType(0);
				SharePerencesUtil.getInstances().setPassWord("");
				SharePerencesUtil.getInstances().setUUID("");
				intent.setClass(getActivity(), LoginActivity.class);
				startActivity(intent);
				return;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("uuid", SharePerencesUtil.getInstances().getUUID());
			net.Post(ServerURL.LOGOUT_URL,map,0);
			return;
		}
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
		if(v == mWdIcon){
			customDialog();
			return;
		}
		Intent intent = new Intent();
		if(v == mWalletBtn){
			intent.setClass(getActivity(), WalletActivity.class);
		}
		if(v == mNumericalBtn){
			intent.setClass(getActivity(), NumericalActivity.class);
		}
		if(v == mRecordBtn){
			intent.setClass(getActivity(), RecordActivity.class);
		}
		if(v == mMenberRankBtn){
			intent.setClass(getActivity(), MenberRankActivity.class);
		}
		if(v == mCouponBtn){
			intent.setClass(getActivity(), MyCouponActivity.class);
		}
		if(v == mWdEditTv){
			intent.setClass(getActivity(), RegisterInfoActivity.class);
			intent.putExtra("wd",true);
		}
		if(v == mSettingBtn){
			intent.setClass(getActivity(), SettingActivity.class);
		}
		startActivity(intent);
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
							imageLoader.displayImage(url, mWdIcon);
							SharePerencesUtil.getInstances().setHeadUrl(url);
							if(mIconListener != null){
								mIconListener.OnIconChange();
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
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 0){
			try {
				JSONObject object = new JSONObject(result);
				int code = object.getInt("code");
				if(code == 1){
					Intent intent = new Intent();
					//退出登陆，清空登陆信息
					BaseApplication.exit();
					SharePerencesUtil.getInstances().setNick("");
					SharePerencesUtil.getInstances().setGender(0);
					SharePerencesUtil.getInstances().setCents(0);
					SharePerencesUtil.getInstances().setCard("");
					SharePerencesUtil.getInstances().setRmb(0);
					SharePerencesUtil.getInstances().setLogin(false);
					SharePerencesUtil.getInstances().setLoginType(0);
					SharePerencesUtil.getInstances().setPassWord("");
					SharePerencesUtil.getInstances().setUUID("");
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
				}
				else
				{
					ToastUtil.show(getActivity(), "登出失败");
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
	
	public void setOnIconChangeListener(OnIconChangeListener listener){
		this.mIconListener = listener;
	}
	
	public void reSetIcon(){
		String photo = SharePerencesUtil.getInstances().getHeadUrl();
		if(!photo.equals("")){
			imageLoader.displayImage(photo, mWdIcon);
		}
	}
	
}
