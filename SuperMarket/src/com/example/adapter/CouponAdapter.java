package com.example.adapter;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

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

import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.CouponBean;
import com.example.utils.ListViewHolder;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.google.gson.annotations.JsonAdapter;

public class CouponAdapter extends BaseAdapter {

	private Context mContext;
	private CouponBean bean;
	private GetCouponListener mListener;
	public CouponAdapter(Context context){
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.coupon_list_item, null);
		}
		int status_id = bean.getData().getValue().get(position).getStatus_id();
		final int pos = position;
		LinearLayout ll = ListViewHolder.get(convertView, R.id.my_coupon_ll);
		TextView couponType = ListViewHolder.get(convertView, R.id.my_coupon_type);
		TextView couponMoney = ListViewHolder.get(convertView, R.id.my_coupon_money);
		TextView couponMoneyFh = ListViewHolder.get(convertView, R.id.mony_fh_tv);
		TextView couponMJDS = ListViewHolder.get(convertView, R.id.my_coupon_mjds);
		TextView couponStartTime = ListViewHolder.get(convertView, R.id.my_coupon_start_time);
		TextView couponEndTime  = ListViewHolder.get(convertView, R.id.my_coupon_end_time);
		TextView couponBtn  = ListViewHolder.get(convertView, R.id.my_coupon_btn);
		ImageView coupon_img = ListViewHolder.get(convertView, R.id.coupon_img);
		String use_start = bean.getData().getValue().get(position).getUse_start().split(" ")[0];
		String use_end = bean.getData().getValue().get(position).getUse_end().split(" ")[0];
		String from_pd = bean.getData().getValue().get(position).getFrom_valid_pd().split(" ")[1];
		String to_pd = bean.getData().getValue().get(position).getTo_valid_pd().split(" ")[1];
		couponStartTime.setText(use_start+"-"+use_end);
		if(from_pd.equals("00:00:00")&&to_pd.equals("23:59:59")){
			couponEndTime.setText(use_start+"-"+use_end+"(全天)");
		}
		else
		{
			from_pd = from_pd.substring(0, from_pd.length()-3);
			to_pd = to_pd.substring(0, to_pd.length()-3);
			String useStr = from_pd+"-"+to_pd;
			couponEndTime.setText(use_start+"-"+use_end+"("+useStr+")");
		}
		if(bean.getData().getValue().get(position).getType() == 1){
			//满减券
			couponType.setText("满减券");
			couponMoneyFh.setVisibility(View.VISIBLE);
			String values [] = bean.getData().getValue().get(position).getValue().split("-");
			int man = Integer.parseInt(values[0])/100;
			int jian = Integer.parseInt(values[1])/100;
			couponMoney.setText(jian+"");
			couponMJDS.setText("满"+man+"减"+jian+"券");
		}else if(bean.getData().getValue().get(position).getType() == 2)
		{
			//满赠券
			couponType.setText("满赠券");
			couponMoneyFh.setVisibility(View.VISIBLE);
			String values [] = bean.getData().getValue().get(position).getValue().split("\\+");
			int man = Integer.parseInt(values[0])/100;
			int zeng = Integer.parseInt(values[1])/100;
			couponMoney.setText(zeng+"");
			couponMJDS.setText("满"+man+"赠"+zeng+"券");
		}
		else if(bean.getData().getValue().get(position).getType() == 3){
			//折扣券
			couponType.setText("折扣券");
			couponMoneyFh.setVisibility(View.INVISIBLE);
			float ze = Float.parseFloat(bean.getData().getValue().get(position).getValue())*10;
			couponMoney.setText(ze+"折");
			couponMJDS.setText(bean.getData().getValue().get(position).getName());
		}
		
		if(status_id == 1){
			couponBtn.setVisibility(View.INVISIBLE);
			ll.setBackgroundResource(R.drawable.huiseyhq);
			coupon_img.setVisibility(View.VISIBLE);
			coupon_img.setBackgroundResource(R.drawable.ysy0);
		}
		//肯定只有类型3的 可领取，其他的类型不会有
		if(status_id == 3){
			couponBtn.setText("立即领取");
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
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("uuid",SharePerencesUtil.getInstances().getUUID());
				map.put("couponactid",bean.getData().getValue().get(pos).getCoupon_act_id());
				VolleyNet net = new VolleyNet();
				net.Post(ServerURL.COUPON_LIST_URL, map, 1);
				net.setOnVellerCallBack(new VollerCallBack() {
					@Override
					public void OnSuccess(int requestCode, String result) {
						// TODO Auto-generated method stub
						if(requestCode == 1){
							try {
								JSONObject object = new JSONObject(result);
								if(object.getInt("code") == 1){
									ToastUtil.show(mContext, "领取成功");
									if(mListener != null){
										mListener.OnGetCoupon();
									}
								}
								else
								{
									ToastUtil.show(mContext,object.getString("msg"));
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
				});
			}
		});
		return convertView;
	}

	public void setOnGetCouponListener(GetCouponListener listener){
		this.mListener = listener;
	}
	public interface GetCouponListener{
		public abstract void OnGetCoupon();
	}
}
