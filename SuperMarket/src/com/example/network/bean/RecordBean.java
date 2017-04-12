package com.example.network.bean;

import java.util.List;


public class RecordBean {
	private int code = 0;
	private String msg = "";
	private ChargeRecordData data = null;
	
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

	public ChargeRecordData getData() {
		return data;
	}

	public void setData(ChargeRecordData data) {
		this.data = data;
	}

	public class ChargeRecordData{
		private String queryid = "";
		private int allcount = 0;
		private List<String> fields = null;
		private List<List<String>> values = null;
		public String getQueryid() {
			return queryid;
		}
		public void setQueryid(String queryid) {
			this.queryid = queryid;
		}
		public int getAllcount() {
			return allcount;
		}
		public void setAllcount(int allcount) {
			this.allcount = allcount;
		}
		public List<String> getFields() {
			return fields;
		}
		public void setFields(List<String> fields) {
			this.fields = fields;
		}
		public List<List<String>> getValues() {
			return values;
		}
		public void setValues(List<List<String>> values) {
			this.values = values;
		}
	}
}
