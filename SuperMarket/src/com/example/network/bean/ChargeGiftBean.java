package com.example.network.bean;

import java.util.ArrayList;
import java.util.List;

public class ChargeGiftBean {
	private int code = 0;
	private String msg = "";
	private ChargeGiftData data = null;
	private int itemNum = 0;
	private List<Integer> topMoneyList = new ArrayList<Integer>();
	private List<Integer> botMoneyList = new ArrayList<Integer>();
	
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

	public ChargeGiftData getData() {
		return data;
	}

	public void setData(ChargeGiftData data) {
		this.data = data;
	}

	public class ChargeGiftData{
		private String rule = "";

		public String getRule() {
			return rule;
		}

		public void setRule(String rule) {
			this.rule = rule;
		}
	}
	
	public List<Integer> getTopMoneyList() {
		return topMoneyList;
	}

	public List<Integer> getBotMoneyList() {
		return botMoneyList;
	}
	
	public int getItemNum(){
		return itemNum;
	}
	
	public void initList(){
		if(data != null){
			String value = data.getRule();
			String items[] = value.split(",");
			for(int i=0;i<items.length;i++){
				String top = items[i].split(":")[0];
				String bot = items[i].split(":")[1];
				topMoneyList.add(Integer.parseInt(top)/100);
				botMoneyList.add(Integer.parseInt(bot)/100);
			}
			itemNum = topMoneyList.size();
		}
	}
}
