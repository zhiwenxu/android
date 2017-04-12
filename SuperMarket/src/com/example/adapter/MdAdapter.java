package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.network.ServerURL;
import com.example.network.bean.StoreInfoBean;
import com.example.utils.ImageLoaderUtil;
import com.example.utils.ListViewHolder;
import com.example.view.commonview.StarBar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MdAdapter extends BaseAdapter{

	private ImageLoader imageLoader = ImageLoader.getInstance();
	private Context mContext;
	private StoreInfoBean bean; 
	private LatLng mLatLng;
	public void setLatLng(LatLng latLng){
		this.mLatLng = latLng;
	}
	public void setBean(StoreInfoBean bean){
		this.bean = bean;
	}
	public MdAdapter(Context context){
		this.mContext = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(bean != null){
			return bean.getData().getRet_count();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.md_list_item, null);
		}
		TextView mdStoreName = ListViewHolder.get(convertView, R.id.md_store_name);
		TextView mdAddress = ListViewHolder.get(convertView, R.id.md_address);
		TextView mdPeople = ListViewHolder.get(convertView, R.id.md_people);
		TextView mdDistance = ListViewHolder.get(convertView, R.id.md_distance);
		ImageView mdIcon = ListViewHolder.get(convertView, R.id.md_icon);
		
		if(mLatLng != null){
			double lat = bean.getData().getValue().get(position).getLatitude();
			double lng = bean.getData().getValue().get(position).getLongitude();
			
			double distance = DistanceUtil.getDistance(mLatLng, new LatLng(lat, lng));
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
			if(distance >1000){
				distance = distance/1000;
				mdDistance.setText(df.format(distance)+"วงรื");
			}
			else
			{
				mdDistance.setText(df.format(distance)+"รื");
			}
		}
		imageLoader.displayImage(ServerURL.STORE_PHOTO_URL+bean.getData().getValue().get(position).getStoreid(), mdIcon, ImageLoaderUtil.getInstance().getOptions());
		StarBar mdStarBar = ListViewHolder.get(convertView, R.id.md_starBar);
		mdStarBar.setIntegerMark(false);
		mdStoreName.setText(bean.getData().getValue().get(position).getName());
		mdAddress.setText(bean.getData().getValue().get(position).getAddress());
		mdStarBar.setStarMark(bean.getData().getValue().get(position).getRate()/20);
		mdStarBar.setcantMark();
		return convertView;
	}

}
