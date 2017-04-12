package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.network.bean.CouponBean;
import com.example.utils.ListViewHolder;

public class MyCouponAdapter extends BaseAdapter {

	private Context mContext;
	private CouponBean bean;
	public MyCouponAdapter(Context context){
		this.mContext = context;
	}
	public void setBean(CouponBean bean){
		this.bean = bean;
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(bean == null || bean.getData() == null){
			return 0;
		}
		else
		{
			return bean.getData().getCount();
		}
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.my_coupon_list_item, null);
		}
		int status_id = bean.getData().getValue().get(position).getStatus_id();
		LinearLayout ll = ListViewHolder.get(convertView, R.id.my_coupon_ll);
		TextView couponType = ListViewHolder.get(convertView, R.id.my_coupon_type);
		TextView couponMoney = ListViewHolder.get(convertView, R.id.my_coupon_money);
		TextView couponMoneyFh = ListViewHolder.get(convertView, R.id.mony_fh_tv);
		TextView couponMJDS = ListViewHolder.get(convertView, R.id.my_coupon_mjds);
		TextView couponStartTime = ListViewHolder.get(convertView, R.id.my_coupon_start_time);
		TextView couponEndTime  = ListViewHolder.get(convertView, R.id.my_coupon_end_time);
		TextView couponBtn  = ListViewHolder.get(convertView, R.id.my_coupon_btn);
		ImageView coupon_img = ListViewHolder.get(convertView, R.id.my_coupon_img);
		String use_start = bean.getData().getValue().get(position).getUse_start().split(" ")[0];
		String use_end = bean.getData().getValue().get(position).getUse_end().split(" ")[0];
		String from_pd = bean.getData().getValue().get(position).getFrom_valid_pd().split(" ")[1];
		String to_pd = bean.getData().getValue().get(position).getTo_valid_pd().split(" ")[1];
		couponStartTime.setText(use_start+"-"+use_end);
		if(from_pd.equals("00:00:00")&&to_pd.equals("23:59:59")){
			couponEndTime.setText(use_start+"-"+use_end+"(È«Ìì)");
		}
		else
		{
			from_pd = from_pd.substring(0, from_pd.length()-3);
			to_pd = to_pd.substring(0, to_pd.length()-3);
			String useStr = from_pd+"-"+to_pd;
			couponEndTime.setText(use_start+"-"+use_end+"("+useStr+")");
		}
		
		
		if(bean.getData().getValue().get(position).getType() == 1){
			//Âú¼õÈ¯
			couponType.setText("Âú¼õÈ¯");
			couponMoneyFh.setVisibility(View.VISIBLE);
			String values [] = bean.getData().getValue().get(position).getValue().split("-");
			int man = Integer.parseInt(values[0])/100;
			int jian = Integer.parseInt(values[1])/100;
			couponMoney.setText(jian+"");
			couponMJDS.setText("Âú"+man+"¼õ"+jian+"È¯");
		}else if(bean.getData().getValue().get(position).getType() == 2)
		{
			//ÂúÔùÈ¯
			couponType.setText("ÂúÔùÈ¯");
			couponMoneyFh.setVisibility(View.VISIBLE);
			String values [] = bean.getData().getValue().get(position).getValue().split("\\+");
			int man = Integer.parseInt(values[0])/100;
			int zeng = Integer.parseInt(values[1])/100;
			couponMoney.setText(zeng+"");
			couponMJDS.setText("Âú"+man+"Ôù"+zeng+"È¯");
		}
		else if(bean.getData().getValue().get(position).getType() == 3){
			//ÕÛ¿ÛÈ¯
			couponType.setText("ÕÛ¿ÛÈ¯");
			couponMoneyFh.setVisibility(View.INVISIBLE);
			float ze = Float.parseFloat(bean.getData().getValue().get(position).getValue())*10;
			
			couponMoney.setText(String.format("%.1f", ze)+"ÕÛ");
			couponMJDS.setText(bean.getData().getValue().get(position).getName());
		}
		if(status_id == 1){
			couponBtn.setVisibility(View.INVISIBLE);
			ll.setBackgroundResource(R.drawable.huiseyhq);
			coupon_img.setVisibility(View.VISIBLE);
			coupon_img.setBackgroundResource(R.drawable.ysy0);
		}
		if(status_id == 2){
			couponBtn.setText("Î´Ê¹ÓÃ");
			couponBtn.setVisibility(View.VISIBLE);
			if(bean.getData().getValue().get(position).getType() == 1){
				ll.setBackgroundResource(R.drawable.lanseyhq);
			}
			else if(bean.getData().getValue().get(position).getType() == 2)
			{
				ll.setBackgroundResource(R.drawable.luseyhq);
			}
			else {
				ll.setBackgroundResource(R.drawable.hongseyhq);
			}
			coupon_img.setVisibility(View.INVISIBLE);
		}
		if(status_id == 4){
			couponBtn.setVisibility(View.INVISIBLE);
			ll.setBackgroundResource(R.drawable.huiseyhq);
			coupon_img.setVisibility(View.VISIBLE);
			coupon_img.setBackgroundResource(R.drawable.ygq00);
		}
		couponBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
			}
		});
		return convertView;
	}

}
