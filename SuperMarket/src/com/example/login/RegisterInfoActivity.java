package com.example.login;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.main.MainActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.UserInfoBean;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.limxing.library.AlertView;
import com.limxing.library.OnConfirmeListener;

public class RegisterInfoActivity extends BaseActivity implements OnClickListener,VollerCallBack,OnConfirmeListener{
	
	//注册信息的两部分
	private LinearLayout mRegisterLlOne,mRegisterLlTwo;
	private Button mJumpBtn,mNextBtn;
	private TextView mSexTv,mBirthTv;
	private EditText mEmailEdit,mHyEdit,mGsEdit,mJtEdit,mQuestionEdit,mAnswerEdit;
	private RelativeLayout mGenderBtn,mBirthBtn;
	private boolean isOne = true; //标识注册信息下一步，默认为第一步
	private boolean isWd = true;
	private VolleyNet net;
	private LoadingDialog dialog;
	private int gender;
	private AlertView alertView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_info);
		initTitle();
		init();
	}
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText(getResources().getString(R.string.register_add_info));
		rightView.setVisibility(View.INVISIBLE);
		rightView.setOnClickListener(this);
		leftView.setOnClickListener(this);
	}
	public void init(){
		mRegisterLlOne = (LinearLayout)findViewById(R.id.register_info_ll_one);
		mRegisterLlTwo = (LinearLayout)findViewById(R.id.register_info_ll_two);
		mJumpBtn = (Button)findViewById(R.id.register_jump_btn);
		mNextBtn = (Button)findViewById(R.id.register_next_btn);
		mGenderBtn = (RelativeLayout)findViewById(R.id.gender_btn);
		mSexTv = (TextView)findViewById(R.id.sex_tv);
		mBirthTv = (TextView)findViewById(R.id.birthday_tv);
		mEmailEdit = (EditText)findViewById(R.id.email_edit);
		mHyEdit = (EditText)findViewById(R.id.hy_edit);
		mGsEdit = (EditText)findViewById(R.id.gs_address_edit);
		mJtEdit = (EditText)findViewById(R.id.jt_address_edit);
		mQuestionEdit = (EditText)findViewById(R.id.question_edit);
		mAnswerEdit = (EditText)findViewById(R.id.answer_edit);
		mBirthBtn = (RelativeLayout)findViewById(R.id.birth_btn);
		alertView = new AlertView("",RegisterInfoActivity.this,1950,2020,RegisterInfoActivity.this);
		mJumpBtn.setOnClickListener(this);
		mNextBtn.setOnClickListener(this);
		mGenderBtn.setOnClickListener(this);
		mBirthBtn.setOnClickListener(this);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		dialog = new LoadingDialog(this);
		isWd = getIntent().getBooleanExtra("wd", true);
		if(isWd == true){
			getUserInfo();
			dialog.show("加载中...",false, null);
		}
	}
	
	public void getUserInfo(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", SharePerencesUtil.getInstances().getID());
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		net.Post(ServerURL.USER_INFO_GET_URL, map, 0);
	}
	public void setUserInfo(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", SharePerencesUtil.getInstances().getID());
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		map.put("birthday",mBirthTv.getText().toString());
		map.put("gender",gender+"");
		map.put("email", mEmailEdit.getText().toString());
		map.put("work_type", mHyEdit.getText().toString());
		map.put("company_addr", mGsEdit.getText().toString());
		map.put("home_addr", mJtEdit.getText().toString());
		map.put("sec_question", mQuestionEdit.getText().toString());
		map.put("sec_anwser", mAnswerEdit.getText().toString());
		net.Post(ServerURL.USER_INFO_CHANGE_URL, map, 1);
		dialog.show("保存中...", false, null);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.birth_btn:
			alertView.show();
			break;
		case R.id.gender_btn:
			if(gender == 1){
				mSexTv.setText("女");
				gender = 2;
			}
			else
			{
				mSexTv.setText("男");
				gender = 1;
			}
			break;
		case R.id.common_title_left_btn:
			if(isOne){
				finish();
			}
			else
			{
				//返回到第一步
				mRegisterLlOne.setVisibility(View.VISIBLE);
				mRegisterLlTwo.setVisibility(View.GONE);
				isOne = true;
			}
			break;
		case R.id.register_jump_btn:
			if(isOne){
				//进入到第二步
				mRegisterLlOne.setVisibility(View.GONE);
				mRegisterLlTwo.setVisibility(View.VISIBLE);
				isOne = false;
			}
			else{
				setUserInfo();
			}
			break;
		case R.id.register_next_btn:
			if(isOne){
				//进入到第二步
				mRegisterLlOne.setVisibility(View.GONE);
				mRegisterLlTwo.setVisibility(View.VISIBLE);
				isOne = false;
			}
			else{
				setUserInfo();
			}
			break;
		}
	}
	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 0){
			dialog.dismiss();
			UserInfoBean bean = GsonHelper.getGson().fromJson(result, UserInfoBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				//设置性别
				if(bean.getData().getGender() == 1){
					mSexTv.setText("男");
					gender = 1;
				}else if(bean.getData().getGender() == 2){
					mSexTv.setText("女");
					gender = 2;
				}
				else{
					mSexTv.setText("未知");
					gender = 0;
				}
				mBirthTv.setText(bean.getData().getBirthday());
				mEmailEdit.setText(bean.getData().getEmail());
				mHyEdit.setText(bean.getData().getWork_type());
				mGsEdit.setText(bean.getData().getCompany_addr());
				mJtEdit.setText(bean.getData().getHome_addr());
				mQuestionEdit.setText(bean.getData().getSec_question());
			}
			else
			{
				ToastUtil.show(RegisterInfoActivity.this, "资料获取失败");
			}
		}
		if(requestCode == 1){
			dialog.dismiss();
			UserInfoBean bean = GsonHelper.getGson().fromJson(result, UserInfoBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				if(isWd){
					ToastUtil.show(RegisterInfoActivity.this, "资料修改成功");
					finish();
				}
				else{
					ToastUtil.show(RegisterInfoActivity.this, "资料修改成功");
					Intent intent = new Intent(RegisterInfoActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
			else
			{
				ToastUtil.show(RegisterInfoActivity.this, "资料修改失败");
			}
		}
	}
	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}
	@Override
	public void result(String s) {
		// TODO Auto-generated method stub
		mBirthTv.setText(s.replace("年", "-").replace("月","-").replace("日", ""));
	}
}
