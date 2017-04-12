package com.example.network.bean;

public class BonusRateBean {

	private int code = 0;
	private String msg = "";
	private BonusRateData data = null;
	
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

	public BonusRateData getData() {
		return data;
	}

	public void setData(BonusRateData data) {
		this.data = data;
	}

	public class BonusRateData{
		private int rate = 0;

		public int getRate() {
			return rate;
		}
		public void setRate(int rate) {
			this.rate = rate;
		}
		
	}
}
