package com.example.network.bean;

public class PayBean {
	private int code = 0;
	private String msg = "";
	private PayData data;
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
	public PayData getData() {
		return data;
	}
	public void setData(PayData data) {
		this.data = data;
	}
	public class PayData{
		private String charge_obj_string = "";

		public String getCharge_obj_string() {
			return charge_obj_string;
		}
		public void setCharge_obj_string(String charge_obj_string) {
			this.charge_obj_string = charge_obj_string;
		}
	}
}
