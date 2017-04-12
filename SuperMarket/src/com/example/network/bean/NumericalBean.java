package com.example.network.bean;

import java.util.List;

public class NumericalBean {
	private int code = 0;
	private String msg = "";
	private NumericalData data = null;
	
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

	public NumericalData getData() {
		return data;
	}

	public void setData(NumericalData data) {
		this.data = data;
	}

	public class NumericalValues{
		private String date;
		private String reason;
		private int amount;
		private String userid;
		private String order;
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
	}
	public class NumericalData{
		private int fycount = 0;
		private int total_count = 0;
		private int ret_count = 0;
		private int cents = 0;
		private int fyindex = 0;
		private List<NumericalValues> value;
		
		public int getFyindex() {
			return fyindex;
		}
		public void setFyindex(int fyindex) {
			this.fyindex = fyindex;
		}
		public List<NumericalValues> getValue() {
			return value;
		}
		public void setValue(List<NumericalValues> value) {
			this.value = value;
		}
		public int getFycount() {
			return fycount;
		}
		public void setFycount(int fycount) {
			this.fycount = fycount;
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
		public int getCents() {
			return cents;
		}
		public void setCents(int cents) {
			this.cents = cents;
		}
	}
}
