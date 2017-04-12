package com.example.network.bean;

public class BandBean {
	private int code = 0;
	private String msg = "";
	private BandData data;
	
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



	public BandData getData() {
		return data;
	}



	public void setData(BandData data) {
		this.data = data;
	}



	public class BandData{
		private String uuid;
		private String card;
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
