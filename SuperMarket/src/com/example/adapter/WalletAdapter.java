package com.example.adapter;
import java.text.DecimalFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.network.bean.WalletBean;
import com.example.utils.ListViewHolder;


public class WalletAdapter extends BaseAdapter {

	private Context mContext;
	private WalletBean bean;
	public WalletAdapter(Context context,WalletBean bean){
		this.mContext = context;
		this.bean = bean;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bean.getData().getTotal_count();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View view, ViewGroup viewgroup) {
		// TODO Auto-generated method stub
		if(view == null){
			view = LayoutInflater.from(mContext).inflate(R.layout.wallet_list_item, null);
		}
		TextView walletMoney = ListViewHolder.get(view, R.id.wallet_momey);
		TextView walletCharge = ListViewHolder.get(view, R.id.wallet_charge);
		TextView walletTime = ListViewHolder.get(view, R.id.wallet_time);
		walletMoney.setText("£¤"+String .format("%.2f",bean.getData().getValue().get(position).getAmout()/100.0f+bean.getData().getValue().get(position).getGiftAmount()/100.0f));
		float charge = bean.getData().getValue().get(position).getAmout()/100.0f;
		String chargeStr = "³äÖµ"+"£¤"+String .format("%.2f",charge)+"Ôª";
		String giftStr = ",ÔùËÍ"+String .format("%.2f",bean.getData().getValue().get(position).getGiftAmount()/100.0f)+"Ôª";
		walletCharge.setText(chargeStr+giftStr);
		walletTime.setText(bean.getData().getValue().get(position).getDate());
		return view;
	}

}
