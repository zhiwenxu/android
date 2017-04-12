package com.example.network.bean;

import java.util.List;

public class HdBean {
	private int code = 0;
	private String msg = "";
	private HdData data = null;
	
	
	
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


	public HdData getData() {
		return data;
	}


	public void setData(HdData data) {
		this.data = data;
	}


	public class HdData{
		private int fycount;
		private int total_count;
		private int ret_count;
		private int fyindex;
		private List<HdListData> value;
		public int getFycount() {
			return fycount;
		}
		public void setFycount(int fycount) {
			this.fycount = fycount;
		}
		public int getTotal_count() {
			return total_count;
		}
		public void setTotal_count(int total_count) {
			this.total_count = total_count;
		}
		public int getRet_count() {
			return ret_count;
		}
		public void setRet_count(int ret_count) {
			this.ret_count = ret_count;
		}
		public int getFyindex() {
			return fyindex;
		}
		public void setFyindex(int fyindex) {
			this.fyindex = fyindex;
		}
		public List<HdListData> getValue() {
			return value;
		}
		public void setValue(List<HdListData> value) {
			this.value = value;
		}
	}
	
	public class HdListData{
		private int activityId;
		private int validType;
		private int usedTime;
		private String fromTime;
		private String name;
		private String describe;
		private int type;
		private String value;
		private String toTime;
		public int getActivityId() {
			return activityId;
		}
		public void setActivityId(int activityId) {
			this.activityId = activityId;
		}
		public int getValidType() {
			return validType;
		}
		public void setValidType(int validType) {
			this.validType = validType;
		}
		public int getUsedTime() {
			return usedTime;
		}
		public void setUsedTime(int usedTime) {
			this.usedTime = usedTime;
		}
		public String getFromTime() {
			return fromTime;
		}
		public void setFromTime(String fromTime) {
			this.fromTime = fromTime;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescribe() {
			return describe;
		}
		public void setDescribe(String describe) {
			this.describe = describe;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getToTime() {
			return toTime;
		}
		public void setToTime(String toTime) {
			this.toTime = toTime;
		}
	}
}
