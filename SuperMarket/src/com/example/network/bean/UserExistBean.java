package com.example.network.bean;

public class UserExistBean {
	private int code = 0;  //0�����ڣ�1����
	private String msg = "";//OK,�û�������
	private UserExitData data;//�գ��û���
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
