package com.example.network.bean;

import java.util.List;


public class StoreInfoBean {
	private int code = 0;
	private String msg = "";
	private StoreInfoData data = null;
	
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

	public StoreInfoData getData() {
		return data;
	}

	public void setData(StoreInfoData data) {
		this.data = data;
	}

	public class StoreInfoData{
		private int fycount = 0;
		private int total_count = 0;
		private int ret_count = 0;
		private List<StoreInfo> value = null;
		
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
		public List<StoreInfo> getValue() {
			return value;
		}
		public void setValue(List<StoreInfo> value) {
			this.value = value;
		}
	}
	
	public class StoreInfo{
		private String address = "";
		private float rate = 0;
		private double latitude = 0.0;
		private double longitude = 0.0;
		private String storeid;
		private String phone = "";
		private String name = "";
		public String getStoreid() {
			return storeid;
		}
		public void setStoreid(String storeid) {
			this.storeid = storeid;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public float getRate() {
			return rate;
		}
		public void setRate(float rate) {
			this.rate = rate;
		}
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
