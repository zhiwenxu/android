package com.example.network.bean;

public class UserInfoBean {
	private int code = 0;
	private String msg = "";
	private UserInfo data = null;
	
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


	public UserInfo getData() {
		return data;
	}


	public void setData(UserInfo data) {
		this.data = data;
	}


	public class UserInfo{
		private String id = "";
		private String nick = "";
		private String card = "";
		private String birthday = "";
		private int gender = 0;
		private String email = "";
		private String work_type = "";
		private String company_addr = "";
		private String home_addr = "";
		private String sec_question = "";
		private String rmb = "";
		private String cents = "";
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getNick() {
			return nick;
		}
		public void setNick(String nick) {
			this.nick = nick;
		}
		public String getCard() {
			return card;
		}
		public void setCard(String card) {
			this.card = card;
		}
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public int getGender() {
			return gender;
		}
		public void setGender(int gender) {
			this.gender = gender;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getWork_type() {
			return work_type;
		}
		public void setWork_type(String work_type) {
			this.work_type = work_type;
		}
		public String getCompany_addr() {
			return company_addr;
		}
		public void setCompany_addr(String company_addr) {
			this.company_addr = company_addr;
		}
		public String getHome_addr() {
			return home_addr;
		}
		public void setHome_addr(String home_addr) {
			this.home_addr = home_addr;
		}
		public String getSec_question() {
			return sec_question;
		}
		public void setSec_question(String sec_question) {
			this.sec_question = sec_question;
		}
		public String getRmb() {
			return rmb;
		}
		public void setRmb(String rmb) {
			this.rmb = rmb;
		}
		public String getCents() {
			return cents;
		}
		public void setCents(String cents) {
			this.cents = cents;
		}
	}
}
