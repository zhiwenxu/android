package com.example.network.bean;

public class UserExistBean {
	private int code = 0;  //0不存在，1存在
	private String msg = "";//OK,用户不存在
	private UserExitData data;//空，用户名
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
	public UserExitData getData() {
		return data;
	}
	public void setData(UserExitData data) {
		this.data = data;
	}
	
	public class UserExitData{
		private String nick;
		public void setNick(String nick){
			this.nick = nick;
		}
		
		public String getNick(){
			return nick;
		}
	}
}
