package com.example.network.bean;

public class LoginBean {
	private int code = 0;
	private String msg = "";
	private LoginData data = null;
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
	
	public void setData(LoginData data){
		this.data = data;
	}
	
	public LoginData getData(){
		return data;
	}
	
	public class LoginData{
		
		private String nick = "";
		private int gender = 0;
		private int rmb = 0;
		private int cents = 0;
		private String ID = "";
		private String uuid = "";
		private String card = "";
		private String headurl = "";
		private String email = "";
		
		public String getHeadurl() {
			return headurl;
		}
		public void setHeadurl(String headurl) {
			this.headurl = headurl;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getNick() {
			return nick;
		}
		public void setNick(String nick) {
			this.nick = nick;
		}
		public int getGender() {
			return gender;
		}
		public void setGender(int gender) {
			this.gender = gender;
		}
		public int getRmb() {
			return rmb;
		}
		public void setRmb(int rmb) {
			this.rmb = rmb;
		}
		public int getCents() {
			return cents;
		}
		public void setCents(int cents) {
			this.cents = cents;
		}
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
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
