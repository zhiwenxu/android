package com.example.network.bean;


public class UserRegisterBean {
	private int code = 0;
	private String msg = "";
	private RegisterData data = null;
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
	public RegisterData getData() {
		return data;
	}

	public void setData(RegisterData data) {
		this.data = data;
	}

	public class RegisterData{
		private String uuid = "";
		private String card = "";
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getCard() {
			return card;
		}
		public void setCard(String card) {
			this.card = card;
		}
		
	}
}
