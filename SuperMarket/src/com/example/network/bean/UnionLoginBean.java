package com.example.network.bean;

public class UnionLoginBean {
	private int code = 0;
	private String msg = "";
	private UnionLoginData data;
	
	
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


	public UnionLoginData getData() {
		return data;
	}


	public void setData(UnionLoginData data) {
		this.data = data;
	}


	public class UnionLoginData{
		private String uuid = "";

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		
	}
}
