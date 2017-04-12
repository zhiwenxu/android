package com.example.view.commonview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.example.application.BaseActivity;
import com.example.utils.ToastUtil;

public class MdDialog extends BaseActivity implements OnClickListener{
	private Button mCloseBtn;
	private TextView mNameTv,mAddressTv,mDistanceTv;
	private StarBar mdStarBar;
	private RelativeLayout mDhBtn,mPhoneBtn;
	private String phoneStr = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.md_dialog);
		init();
	}
	private void init(){
		WindowManager m = getWindowManager();  
        Display d = m.getDefaultDisplay();  //Ϊ��ȡ��Ļ����  
        LayoutParams p = getWindow().getAttributes();  //��ȡ�Ի���ǰ�Ĳ���ֵ  
        p.height = (int) (d.getHeight() * 0.7);   //�߶�����Ϊ��Ļ��0.3
        p.width = (int) (d.getWidth() * 1.0);    //�������Ϊ��Ļ��1.0
        getWindow().setAttributes(p);     //������Ч
        getWindow().setGravity(Gravity.CENTER);                 //���ÿ��Ҷ���
		
		mCloseBtn = (Button)findViewById(R.id.close_btn);
		mNameTv = (TextView)findViewById(R.id.name);
		mAddressTv = (TextView)findViewById(R.id.address_tv);
		mDistanceTv = (TextView)findViewById(R.id.distance);
		mdStarBar = (StarBar)findViewById(R.id.md_starBar);
		mDhBtn = (RelativeLayout)findViewById(R.id.dh_btn);
		mPhoneBtn = (RelativeLayout)findViewById(R.id.phone_btn);
		
		mNameTv.setText("�ŵ����ƣ�"+getIntent().getStringExtra("name"));
		mAddressTv.setText(getIntent().getStringExtra("address"));
		mDistanceTv.setText(getIntent().getStringExtra("distance"));
		mdStarBar.setStarMark(getIntent().getIntExtra("rate", 0)/20);
		phoneStr = getIntent().getStringExtra("phone");
		mCloseBtn.setOnClickListener(this);
		mDhBtn.setOnClickListener(this);
		mPhoneBtn.setOnClickListener(this);
		mdStarBar.setIntegerMark(false);
		mdStarBar.setcantMark();
	}
	public void navi(){
        double lat = getIntent().getDoubleExtra("mlat", 0.0);
        double lon = getIntent().getDoubleExtra("mlong", 0.0);    
        LatLng pt1 = new LatLng(lat, lon);  
        lat = getIntent().getDoubleExtra("lat", 0.0); 
        lon = getIntent().getDoubleExtra("long", 0.0);
        LatLng pt2 = new LatLng(lat, lon);  
        NaviParaOption para = new NaviParaOption();  
        para.startPoint(pt1);
        para.startName("�ҵ�λ��");
        para.endPoint(pt2);
        para.endName(getIntent().getStringExtra("name"));
        try {  
             BaiduMapNavigation.openBaiduMapNavi(para, this);  
        } catch (BaiduMapAppNotSupportNaviException e) {  
            e.printStackTrace();  
              ToastUtil.show(MdDialog.this, "û�аٶȵ�ͼ");
        }  
	}

	@Override
	public void onClick(View v) {
		if(v == mCloseBtn){
			finish();
		}
		if(v == mPhoneBtn){
			Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneStr));
			startActivity(intent);
			finish();
		}
		if(v == mDhBtn){
			navi();
			finish();
		}
	}
}
