package com.example.network.bean;

import java.util.List;

public class WalletBean {
	private int code = 0;
	private String msg = "";
	private WalletData data;
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public WalletData getData() {
		return data;
	}

	public void setData(WalletData data) {
		this.data = data;
	}

	public class WalletData{
		private int fycount;
		private int rmb;
		private int total_count;
		private int ret_count;
		private int fyindex;
		private List<WalletValue> value;
		public int getFycount() {
			return fycount;
		}
		public void setFycount(int fycount) {
			this.fycount = fycount;
		}
		public int getRmb() {
			return rmb;
		}
		public void setRmb(int rmb) {
			this.rmb = rmb;
		}
		public int getTotal_count() {
			return total_count;
		}
		public void setTotal_count(int total_count) {
			this.total_count = total_count;
		}
		public int getRet_count() {
			return ret_count;
		}
		public void setRet_count(int ret_count) {
			this.ret_count = ret_count;
		}
		public int getFyindex() {
			return fyindex;
		}
		public void setFyindex(int fyindex) {
			this.fyindex = fyindex;
		}
		public List<WalletValue> getValue() {
			return value;
		}
		public void setValue(List<WalletValue> value) {
			this.value = value;
		}
	}
	
	public class WalletValue{
		private String date;
		private String method;
		private String actuser;
		private int amout;
		private int giftAmount;
		private String targetuser;
		private String order;
		
		public int getGiftAmount() {
			return giftAmount;
		}
		public void setGiftAmount(int giftAmount) {
			this.giftAmount = giftAmount;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public String getActuser() {
			return actuser;
		}
		public void setActuser(String actuser) {
			this.actuser = actuser;
		}
		public int getAmout() {
			return amout;
		}
		public void setAmout(int amout) {
			this.amout = amout;
		}
		public String getTargetuser() {
			return targetuser;
		}
		public void setTargetuser(String targetuser) {
			this.targetuser = targetuser;
		}
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
	}
}
