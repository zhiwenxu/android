package com.example.network.bean;

public class GetPhoneBean {
	private int code = 0;
	private String msg = "";
	private PhoneData data;
	public class PhoneData{
		private String phone = "Œ¥…Ë÷√";

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
		
	}
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
	public PhoneData getData() {
		return data;
	}
	public void setData(PhoneData data) {
		this.data = data;
	}
}
