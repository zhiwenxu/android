package com.example.fragment;


import java.io.Serializable;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.adapter.MdAdapter;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.StoreInfoBean;
import com.example.utils.DisplayUtil;
import com.example.utils.ImageLoaderUtil;
import com.example.utils.ListViewHolder;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.example.view.commonview.StarBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("NewApi")
public class MdFragment extends Fragment implements VollerCallBack,OnItemClickListener,OnClickListener{
	private PullToRefreshListView mListView;
	private RelativeLayout mMapLayout;
	private RelativeLayout mfjLayout;
	private RelativeLayout mOneItemLayout;
	
	private ImageView mOneItemIcon;
	private TextView mOneItemName;
	private TextView mOneItemAddr;
	private TextView mOneItemDistance;
	private StarBar mOneItemStarBar;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageView mMdLocationBtn,mMdBlueBtn;
	private MdAdapter mAdapter;
	private VolleyNet net;
	private TextView mMdCount;
	private LoadingDialog dialog;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private double mlat,mlon;
	private boolean ifFrist = true;
	private boolean isBig = false;//���Ƶ�ͼ����
	private StoreInfoBean bean;
	private BitmapDescriptor mIconMaker;
	private PopupWindow window;
	private View abView;
	private View viewLayout;
	private boolean isFirstLocation = true;
	private LocationMode mCurrentMode = LocationMode.NORMAL;
	private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private BitmapDescriptor mCurrentMarker;
	//���ڶ�λ
	public LocationClient mLocationClient = null;
	
	public BDLocationListener myListener = new MyLocationListener();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		viewLayout = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_md, null);
		init(viewLayout);
		return viewLayout;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
		mListView.setVisibility(View.VISIBLE);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		mLocationClient.stop();
		mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}
	
	private void location(){
		mLocationClient = new LocationClient(getActivity());
		mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // ��gps
        option.setCoorType("bd09ll"); // ������������
        option.setScanSpan(1000*60*5);//5���Ӷ�λһ��
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        
        mCurrentMarker = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),
        		R.drawable.location_icon));
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker,
                                        accuracyCircleFillColor, accuracyCircleStrokeColor));
	}
	
	public void navi(double lat,double lon,String name){
        LatLng pt1 = new LatLng(mlat, mlon);  
        LatLng pt2 = new LatLng(lat, lon);  
        NaviParaOption para = new NaviParaOption();  
        para.startPoint(pt1);
        para.startName("�ҵ�λ��");
        para.endPoint(pt2);
        para.endName(name);
        try {  
             BaiduMapNavigation.openBaiduMapNavi(para, getActivity());  
        } catch (BaiduMapAppNotSupportNaviException e) {  
            e.printStackTrace();  
              ToastUtil.show(getActivity(), "û�аٶȵ�ͼ");
        }  
	}
	public void pop(View b,final MdInfo info){
		if(window != null && window.isShowing()){
			return;
		}
		abView.setVisibility(View.VISIBLE);
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.md_dialog, null);
		TextView mNameTv = (TextView)view.findViewById(R.id.name);
		TextView mAddressTv = (TextView)view.findViewById(R.id.address_tv);
		TextView mDistanceTv = (TextView)view.findViewById(R.id.distance);
		StarBar mdStarBar = (StarBar)view.findViewById(R.id.md_starBar);;
		Button mCloseBtn = (Button)view.findViewById(R.id.close_btn);
		RelativeLayout mDhBtn = (RelativeLayout)view.findViewById(R.id.dh_btn);;
		RelativeLayout mPhoneBtn = (RelativeLayout)view.findViewById(R.id.phone_btn);;
		
		mNameTv.setText("�ŵ����ƣ�"+info.name);
		mAddressTv.setText(info.address);
		mDistanceTv.setText(info.distance);
		mdStarBar.setStarMark(info.rate/20);
		mCloseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(window != null && window.isShowing()){
					window.dismiss();
					abView.setVisibility(View.GONE);
					WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
					lp.alpha = 1.0f;
					getActivity().getWindow().setAttributes(lp);
				}
			}
		});
		mDhBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				navi(info.lat,info.lon,info.name);
			}
		});
		mPhoneBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+info.phone));
				getActivity().startActivity(intent);
			}
		});
		mdStarBar.setIntegerMark(false);
		mdStarBar.setcantMark();
		
		
		window = new PopupWindow(view,DisplayUtil.getDisplayWidth(getActivity())-DisplayUtil.dip2px(getActivity(), 40),
				LayoutParams.WRAP_CONTENT);
		window.showAtLocation(b,Gravity.CENTER, 0, 0);
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = 0.5f;
		getActivity().getWindow().setAttributes(lp);
	}
	
	public void init(View view){
		mListView = (PullToRefreshListView)view.findViewById(R.id.md_list);
		mMdCount = (TextView)view.findViewById(R.id.md_count);
		mMapLayout = (RelativeLayout)view.findViewById(R.id.map_layout);
		mfjLayout = (RelativeLayout)view.findViewById(R.id.fj_layout);
		mOneItemLayout = (RelativeLayout)view.findViewById(R.id.md_one_item);
		mOneItemLayout.setOnClickListener(this);
		abView = (View)view.findViewById(R.id.map_ab_view);
		
		//Ĭ����ʾһ���ŵ���Ϣ
		mOneItemIcon = (ImageView)view.findViewById(R.id.md_icon);
		mOneItemName = (TextView)view.findViewById(R.id.md_store_name);
		mOneItemAddr = (TextView)view.findViewById(R.id.md_address);
		mOneItemDistance = (TextView)view.findViewById(R.id.md_distance);
		mOneItemStarBar = (StarBar)view.findViewById(R.id.md_starBar);
		
		mMdBlueBtn = (ImageView)view.findViewById(R.id.md_blue_btn);
		mMdLocationBtn = (ImageView)view.findViewById(R.id.md_location_btn);
		mMdLocationBtn.setOnClickListener(this);
		
		mfjLayout.setOnClickListener(this);
		dialog = new LoadingDialog(getActivity());
		dialog.setMessage("������...");
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		mAdapter = new MdAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setMode(Mode.BOTH);
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if(mlat != 0){
					mBaiduMap.clear();
					getStoreInfo(mlat,mlon);
				}
			}
		});
		mMapView = (MapView)view.findViewById(R.id.mapview);
		mBaiduMap = mMapView.getMap();
		
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				if(isBig){
					changeMap(0);
					isBig = false;
				}
				else
				{
					changeMap(1);
					isBig = true;
				}
			}
		});
		
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				InfoWindow infoWindow;
				View view = LayoutInflater.from(getActivity()).inflate(R.layout.infowind_layout, null);
				TextView textView = (TextView)view.findViewById(R.id.name_btn);
				final MdInfo info = (MdInfo)marker.getExtraInfo().get("info");
				textView.setText(info.name);
				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						pop(viewLayout,info);
					}
				});
				LatLng ll = marker.getPosition();
				Point point = mBaiduMap.getProjection().toScreenLocation(ll);
				point.y -= DisplayUtil.dip2px(getActivity(), 20);
				infoWindow = new InfoWindow(view,ll,-47);
				mBaiduMap.showInfoWindow(infoWindow);
				return true;
			}
		});
		mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
        // ������λͼ��
        mBaiduMap.setMyLocationEnabled(true);
		location();
		changeMap(1);  //Ĭ�Ͻ���ʱ���ͼ
	}
	
	//�ı��ͼ��С
	private void changeMap(int type){
		if(type == 0){
			//����maplayout�Ŀ�Ϊ225dip
			RelativeLayout.LayoutParams mapLayoutP = (RelativeLayout.LayoutParams) mMapLayout.getLayoutParams(); 
			// ȡ�ؼ�maplayout��ǰ�Ĳ��ֲ���
			mapLayoutP.height = DisplayUtil.dip2px(getActivity(), 225);
			mMapLayout.setLayoutParams(mapLayoutP); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ�aaa
			RelativeLayout.LayoutParams fjLayoutP = (RelativeLayout.LayoutParams) mfjLayout.getLayoutParams();
			fjLayoutP.bottomMargin = DisplayUtil.dip2px(getActivity(), 0);
			mfjLayout.setLayoutParams(fjLayoutP);
			mListView.setVisibility(View.VISIBLE);
			mOneItemLayout.setVisibility(View.GONE);
			mMdBlueBtn.setVisibility(View.VISIBLE);
			mMdLocationBtn.setVisibility(View.GONE);
		}
		else{
			//����maplayout�Ŀ�Ϊ225dip
			RelativeLayout.LayoutParams mapLayoutP = (RelativeLayout.LayoutParams) mMapLayout.getLayoutParams(); 
			// ȡ�ؼ�maplayout��ǰ�Ĳ��ֲ���
			mapLayoutP.height = LayoutParams.MATCH_PARENT;
			mMapLayout.setLayoutParams(mapLayoutP); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ�aaa
			RelativeLayout.LayoutParams fjLayoutP = (RelativeLayout.LayoutParams) mfjLayout.getLayoutParams();
			fjLayoutP.bottomMargin = DisplayUtil.dip2px(getActivity(), 100);
			mfjLayout.setLayoutParams(fjLayoutP);
			mListView.setVisibility(View.GONE);
			mOneItemLayout.setVisibility(View.VISIBLE);
			mMdBlueBtn.setVisibility(View.GONE);
			mMdLocationBtn.setVisibility(View.VISIBLE);
		}
	}
	private void navigateTo(BDLocation location) {
        // ���վ�γ��ȷ����ͼλ��
        if (ifFrist) {
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate mapStatus = MapStatusUpdateFactory.newLatLngZoom(ll, 18); 
            
            //����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯 
            mBaiduMap.animateMapStatus(mapStatus);
            ifFrist = false;
        }
        // ��ʾ����λ��ͼ��
        MyLocationData.Builder builder = new MyLocationData.Builder();
        
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData data = builder.build();
        mBaiduMap.setMyLocationData(data);
    }
	public void addMaker(StoreInfoBean bean){
		//׼�� marker option ��� marker ʹ��  
		for(int i=0;i<bean.getData().getRet_count();i++){
			double lat = bean.getData().getValue().get(i).getLatitude();
			double lon = bean.getData().getValue().get(i).getLongitude();
			MarkerOptions markerOptions = new MarkerOptions().icon(mIconMaker).position(new LatLng(lat,lon));  
			Marker marker = (Marker) mBaiduMap.addOverlay(markerOptions); 
			Bundle bundle = new Bundle();
			MdInfo info = new MdInfo();
			info.name = bean.getData().getValue().get(i).getName();
			info.address = bean.getData().getValue().get(i).getAddress();
			info.rate = bean.getData().getValue().get(i).getRate();
			info.lat = bean.getData().getValue().get(i).getLatitude();
			info.lon = bean.getData().getValue().get(i).getLongitude();
			info.phone = bean.getData().getValue().get(i).getPhone();
			double distance = DistanceUtil.getDistance(new LatLng(mlat, mlon), new LatLng(lat, lon));
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
			if(distance >1000){
				distance = distance/1000;
				info.distance = df.format(distance)+"ǧ��";
			}
			else
			{
				info.distance = df.format(distance)+"��";
			}
			bundle.putSerializable("info", info);
			marker.setExtraInfo(bundle);
		}
	}
	public void getStoreInfo(double lat,double lon){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		map.put("latitude", lat+"");
		map.put("longitude", lon+"");
		map.put("retcount", "4");//���4��
		net.Post(ServerURL.STORE_LIST_URL, map, 0);
	}
	@Override
	public void OnSuccess(int requestCode, String result) {
		// TODO Auto-generated method stub
		if(requestCode == 0){//����ŵ���Ϣ
			mListView.onRefreshComplete();
		    bean = GsonHelper.getGson().fromJson(result, StoreInfoBean.class);
			if(bean.getCode() == VolleyNet.ONE_CODE){//��ȡ�ɹ�
				mMdCount.setText("������"+bean.getData().getRet_count()+"���ŵ�,����鿴");
				mAdapter.setBean(bean);
				addMaker(bean);
				initOneItem(bean);//��ʼ��һ������
				mAdapter.notifyDataSetChanged(); 
				dialog.dismiss();
			}
			else//��ȡʧ��
			{
				dialog.dismiss();
			}
		}
	}

	@Override
	public void OnError(int requestCode, String errorMsg) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}
	//��λ����
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			mlat = location.getLatitude();
			mlon = location.getLongitude();
			navigateTo(location);
			if(isFirstLocation){
				isFirstLocation = false;
				getStoreInfo(mlat,mlon);
				dialog.show();
				mAdapter.setLatLng(new LatLng(mlat,mlon));
				mAdapter.notifyDataSetChanged();
			}
		}
	}
	
	private void initOneItem(StoreInfoBean bean){
		if(bean != null && bean.getData().getRet_count() > 0){
			LatLng mLatLng = new LatLng(mlat, mlon);
			if(mLatLng != null){
				double lat = bean.getData().getValue().get(0).getLatitude();
				double lng = bean.getData().getValue().get(0).getLongitude();
				
				double distance = DistanceUtil.getDistance(mLatLng, new LatLng(lat, lng));
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
				if(distance >1000){
					distance = distance/1000;
					mOneItemDistance.setText(df.format(distance)+"ǧ��");
				}
				else
				{
					mOneItemDistance.setText(df.format(distance)+"��");
				}
			}
			imageLoader.displayImage(ServerURL.STORE_PHOTO_URL+bean.getData().getValue().get(0).getStoreid(), mOneItemIcon, ImageLoaderUtil.getInstance().getOptions());
			mOneItemStarBar.setIntegerMark(false);
			mOneItemName.setText(bean.getData().getValue().get(0).getName());
			mOneItemAddr.setText(bean.getData().getValue().get(0).getAddress());
			mOneItemStarBar.setStarMark(bean.getData().getValue().get(0).getRate()/20);
			mOneItemStarBar.setcantMark();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		TextView distance = (TextView)view.findViewById(R.id.md_distance);
		MdInfo info = new MdInfo();
		info.name = bean.getData().getValue().get(pos-1).getName();
		info.address = bean.getData().getValue().get(pos-1).getAddress();
		info.rate = bean.getData().getValue().get(pos-1).getRate();
		info.distance = distance.getText().toString();
		info.lat = bean.getData().getValue().get(pos-1).getLatitude();
		info.lon = bean.getData().getValue().get(pos-1).getLongitude();
		info.phone = bean.getData().getValue().get(pos-1).getPhone();
		pop(viewLayout, info);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mfjLayout){
			if(isBig){
				changeMap(0);
				isBig = false;
			}
			else
			{
				changeMap(1);
				isBig = true;
			}
		}
		if(v == mMdLocationBtn){
			LatLng ll = new LatLng(mlat,
                    mlon);
            MapStatusUpdate mapStatus = MapStatusUpdateFactory.newLatLngZoom(ll, 18); 
            
            //����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯 
            mBaiduMap.animateMapStatus(mapStatus);
		}
		if(v == mOneItemLayout){
			MdInfo info = new MdInfo();
			info.name = bean.getData().getValue().get(0).getName();
			info.address = bean.getData().getValue().get(0).getAddress();
			info.rate = bean.getData().getValue().get(0).getRate();
			info.distance = mOneItemDistance.getText().toString();
			info.lat = bean.getData().getValue().get(0).getLatitude();
			info.lon = bean.getData().getValue().get(0).getLongitude();
			info.phone = bean.getData().getValue().get(0).getPhone();
			pop(viewLayout, info);
		}
	}
	
	private class MdInfo implements Serializable{
		public String name;
		public String address;
		public float rate; 
		public String distance;
		public String phone;
		public double lat;
		public double lon;
	}
	
}
