package com.example.personal;


import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.application.BaseActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.ChargeGiftBean;
import com.example.network.bean.ChargeGiftBean.ChargeGiftData;
import com.example.network.bean.PayBean;
import com.example.network.bean.UserExistBean;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.pingplusplus.android.Pingpp;

public class RechargeActivity extends BaseActivity implements OnClickListener,VollerCallBack{

	 /**
     * ΢��֧������
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * ΢��֧������
     */
    private static final String CHANNEL_QPAY = "qpay";
    /**
     * ֧��֧������
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    private int type = 0;  //0΢�ţ�1֧������2����
    private int oneMoney = 0,twoMoney = 0,threeMoney = 0;
    private ChargeGiftBean giftBean;
    private RelativeLayout mWxBtn,mZfbBtn,mZhBtn;
    private ImageView mWxG,mZfbG,mZhG;
    private EditText mMoneyEdit,mTargetEdit;
    private TextView mTipTv;
    private Button mNextBtn,mYzBtn;
    
    private RelativeLayout mMoneyOneBtn,mMoneyTwoBtn,mMoneyThreeBtn;
    private TextView mTopOne,mTopTwo,mTopThree;
    private TextView mBotOne,mBotTwo,mBotThree;
    
    private VolleyNet net;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		initTile();
		init();
	}
	public void init(){
		mWxBtn = (RelativeLayout)findViewById(R.id.wx_btn);
		mZfbBtn = (RelativeLayout)findViewById(R.id.zfb_btn);
		mZhBtn = (RelativeLayout)findViewById(R.id.zh_btn);
		mWxG = (ImageView)findViewById(R.id.wx_g);
		mZfbG = (ImageView)findViewById(R.id.zfb_g);
		mZhG = (ImageView)findViewById(R.id.zh_g);
		mMoneyEdit = (EditText)findViewById(R.id.money_edit);
		mTargetEdit = (EditText)findViewById(R.id.targetPhone_edit);
		mTipTv = (TextView)findViewById(R.id.tip_tv);
		mNextBtn = (Button)findViewById(R.id.next_btn);
		mYzBtn = (Button)findViewById(R.id.charge_yz_btn);
		
		mMoneyOneBtn = (RelativeLayout)findViewById(R.id.money_btn_one);
		mMoneyTwoBtn = (RelativeLayout)findViewById(R.id.money_btn_two);
		mMoneyThreeBtn = (RelativeLayout)findViewById(R.id.money_btn_three);
		mMoneyOneBtn.setOnClickListener(this);
		mMoneyTwoBtn.setOnClickListener(this);
		mMoneyThreeBtn.setOnClickListener(this);
		mMoneyEdit.addTextChangedListener(new EditTextChangeListener());
		mTopOne = (TextView)findViewById(R.id.money_top_one);
		mTopTwo = (TextView)findViewById(R.id.money_top_two);
		mTopThree = (TextView)findViewById(R.id.money_top_three);
		mBotOne = (TextView)findViewById(R.id.money_bottom_one);
		mBotTwo = (TextView)findViewById(R.id.money_bottom_two);
		mBotThree = (TextView)findViewById(R.id.money_bottom_three);
		
		mWxBtn.setOnClickListener(this);
		mZfbBtn.setOnClickListener(this);
		mZhBtn.setOnClickListener(this);
		mNextBtn.setOnClickListener(this);
		mYzBtn.setOnClickListener(this);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		mTargetEdit.setText(SharePerencesUtil.getInstances().getID());
		getUserInfoExist();
		getRechargeGift();
	}
	public void initTile(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText(getResources().getString(R.string.recharge));
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	
	private void changeG(int type){
		this.type = type;
		mWxG.setVisibility(View.INVISIBLE);
		mZfbG.setVisibility(View.INVISIBLE);
		mZhG.setVisibility(View.INVISIBLE);
		switch (type) {
		case 0:
			mWxG.setVisibility(View.VISIBLE);
			break;
		case 1:
			mZfbG.setVisibility(View.VISIBLE);
			break;
		case 2:
			mZhG.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	private void getUserInfoExist(){
		String phone = mTargetEdit.getText().toString();
		if(phone.equals("")){
			ToastUtil.show(this, "Ŀ���ֻ��Ų���Ϊ��");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id",phone);
		net.Post(ServerURL.USER_EXIST_URL, map, 1);
	}
	
	private void getRechargeGift(){
		net.Post(ServerURL.GET_RECHARGE_GIFT_URL, null, 2);
	}
	
	
	private void getCharge(){
		String amount = mMoneyEdit.getText().toString();
		String targetid = mTargetEdit.getText().toString();
		if(amount.equals("")){
			ToastUtil.show(this, "����Ϊ��");
			return;
		}
		if(((int)(Float.parseFloat(amount)*100)) < 10000){
			ToastUtil.show(this, "��ֵ������100Ԫ");
			return;
		}
		if(((int)(Float.parseFloat(amount))) % 100 != 0){
			ToastUtil.show(this, "��ֵ������Ϊ100��������");
			return;
		}
		
		if(targetid.equals("")){
			ToastUtil.show(this, "Ŀ���˲���Ϊ��");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("actid",SharePerencesUtil.getInstances().getID());
		map.put("targetid",targetid);
		if(type == 0){
			map.put("channel",CHANNEL_WECHAT);
		}
		else
		{
			map.put("channel",CHANNEL_ALIPAY);
		}
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		map.put("amount",(((int)(Float.parseFloat(amount)*100)))+"");
		net.Post(ServerURL.PING_PAY_URL, map, 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
		if(v == mWxBtn){
			changeG(0);
		}
		if(v == mZfbBtn){
			changeG(1);
		}
		if(v == mZhBtn){
			changeG(2);
		}
		if(v == mNextBtn){
			getCharge();
		}
		if(v == mYzBtn){
			getUserInfoExist();
		}
		if(v == mMoneyOneBtn){
			setMoneyGiftBg(mMoneyOneBtn);
			mMoneyEdit.setText(oneMoney+"");
		}
		if(v == mMoneyTwoBtn){
			setMoneyGiftBg(mMoneyTwoBtn);
			mMoneyEdit.setText(twoMoney+"");
		}
		if(v == mMoneyThreeBtn){
			setMoneyGiftBg(mMoneyThreeBtn);
			mMoneyEdit.setText(threeMoney+"");
		}
	}
	
	//���ÿ�ı���
	private void setMoneyGiftBg(RelativeLayout select){
		mMoneyOneBtn.setBackgroundResource(R.drawable.charge_money_select_nor_bg);
		mMoneyTwoBtn.setBackgroundResource(R.drawable.charge_money_select_nor_bg);
		mMoneyThreeBtn.setBackgroundResource(R.drawable.charge_money_select_nor_bg);
		if(select != null){
			select.setBackgroundResource(R.drawable.charge_money_selected_bg);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    //֧��ҳ�淵�ش���
	    if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
	        if (resultCode == Activity.RESULT_OK) {
	            String result = data.getExtras().getString("pay_result");
	            // ������ֵ
	            // "success" - ֧���ɹ�
	            // "fail"    - ֧��ʧ��
	            // "cancel"  - ȡ��֧��
	            // "invalid" - ֧�����δ��װ��һ����΢�ſͻ���δ��װ�������
	            String errorMsg = data.getExtras().getString("error_msg"); // ������Ϣ
	            String extraMsg = data.getExtras().getString("extra_msg"); // ������Ϣ
	            if(result.equals("success")){
	            	ToastUtil.show(RechargeActivity.this, "֧���ɹ�");
	            	mMoneyEdit.setText("");
	            	mTargetEdit.setText("");
	            	mTipTv.setText("");
	            }
	            else if(result.equals("fail")){
	            	ToastUtil.show(RechargeActivity.this, "֧��ʧ��");
	            }
	            else if(result.equals("cancel")){
	            	ToastUtil.show(RechargeActivity.this, "ȡ��֧��");
	            }
	            else if(result.equals("invalid")){
	            	ToastUtil.show(RechargeActivity.this, "���δ��װ");
	            }
	        }
	    }
	}
	
	class PaymentRequest {
        String channel;
        int amount;
        public PaymentRequest(String channel, int amount) {
            this.channel = channel;
            this.amount = amount;
        }
    }

	@Override
	public void OnSuccess(int requestCode, String data) {
		// TODO Auto-generated method stub
		if(requestCode == 0){
			PayBean bean = GsonHelper.getGson().fromJson(data, PayBean.class);
			if(bean.getCode() == 1){
				Pingpp.createPayment(RechargeActivity.this, bean.getData().getCharge_obj_string());
			}
			else{
				ToastUtil.show(RechargeActivity.this, "֧�������ȡʧ��");
			}
			
		}
		if(requestCode == 1){
			UserExistBean bean = GsonHelper.getGson().fromJson(data, UserExistBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				//����û�����
				String name = bean.getData().getNick();
				
				if(name != null && !name.equals("")){
					if(name.length() == 1){
						
					}
					else if(name.length()>1){
						name = name.replace(name.charAt(1), '*');
					}
				}
				mTipTv.setText(name);
			}
			else
			{
				ToastUtil.show(RechargeActivity.this, "�û�������");
				mTipTv.setText("");
			}
		}
		if(requestCode == 2){
			ChargeGiftBean bean = GsonHelper.getGson().fromJson(data, ChargeGiftBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){
				bean.initList();
				initMoneyGift(bean);
			}
		}
	}
	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
	
	private void initMoneyGift(ChargeGiftBean bean){
		this.giftBean = bean;
		
		switch(bean.getItemNum()){
		case 1:
			oneMoney = giftBean.getTopMoneyList().get(0);
			mMoneyTwoBtn.setVisibility(View.INVISIBLE);
			mMoneyThreeBtn.setVisibility(View.INVISIBLE);
			mTopOne.setText(giftBean.getTopMoneyList().get(0)+"Ԫ");
			mBotOne.setText("��"+giftBean.getBotMoneyList().get(0)+"Ԫ");
			break;
		case 2:
			oneMoney = giftBean.getTopMoneyList().get(0);
			twoMoney = giftBean.getTopMoneyList().get(1);
			mMoneyThreeBtn.setVisibility(View.INVISIBLE);
			mTopOne.setText(giftBean.getTopMoneyList().get(0)+"Ԫ");
			mBotOne.setText("��"+giftBean.getBotMoneyList().get(0)+"Ԫ");
			mTopTwo.setText(giftBean.getTopMoneyList().get(1)+"Ԫ");
			mBotTwo.setText("��"+giftBean.getBotMoneyList().get(1)+"Ԫ");
			break;
		case 3:
			oneMoney = giftBean.getTopMoneyList().get(0);
			twoMoney = giftBean.getTopMoneyList().get(1);
			threeMoney = giftBean.getTopMoneyList().get(2);
			mTopOne.setText(giftBean.getTopMoneyList().get(0)+"Ԫ");
			mBotOne.setText("��"+giftBean.getBotMoneyList().get(0)+"Ԫ");
			mTopTwo.setText(giftBean.getTopMoneyList().get(1)+"Ԫ");
			mBotTwo.setText("��"+giftBean.getBotMoneyList().get(1)+"Ԫ");
			mTopThree.setText(giftBean.getTopMoneyList().get(2)+"Ԫ");
			mBotThree.setText("��"+giftBean.getBotMoneyList().get(2)+"Ԫ");
			break;
		}
	}
	
	class EditTextChangeListener implements TextWatcher{

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			String temp = arg0.toString();
			if(temp.equals(oneMoney+"")){
				setMoneyGiftBg(mMoneyOneBtn);
			}
			else if(temp.equals(twoMoney+"")){
				setMoneyGiftBg(mMoneyTwoBtn);
			}
			else if(temp.equals(threeMoney+"")){
				setMoneyGiftBg(mMoneyThreeBtn);
			}else{
				setMoneyGiftBg(null);
			}
		}
	}
}
