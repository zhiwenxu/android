package com.example.network.bean;

import java.util.List;

public class CouponBean {
	private int code = 0;
	private String msg = "";
	private CouponData data;
	
	
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

	public CouponData getData() {
		return data;
	}

	public void setData(CouponData data) {
		this.data = data;
	}

	public class CouponData{
		private int count = 0;
		private List<CouponValue> value;
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public List<CouponValue> getValue() {
			return value;
		}
		public void setValue(List<CouponValue> value) {
			this.value = value;
		}
		
	}
	
	public class CouponValue{
		private String use_start;
		private String limit_ext;
		private int min_effect_amount;
		private int limit_type;
		private String recv_time;
		private String coupon_act_id;
		private int coupon_count;
		private int coupon_left;
		private int type;
		private String used_order;
		private String use_end;
		private int status_id;
		private String coupon_id;
		private String name;
		private String send_end;
		private String value;
		private String send_start;
		private String from_valid_pd;
		private String to_valid_pd;
		
		public String getFrom_valid_pd() {
			return from_valid_pd;
		}
		public void setFrom_valid_pd(String from_valid_pd) {
			this.from_valid_pd = from_valid_pd;
		}
		public String getTo_valid_pd() {
			return to_valid_pd;
		}
		public void setTo_valid_pd(String to_valid_pd) {
			this.to_valid_pd = to_valid_pd;
		}
		public String getUse_start() {
			return use_start;
		}
		public void setUse_start(String use_start) {
			this.use_start = use_start;
		}
		public String getLimit_ext() {
			return limit_ext;
		}
		public void setLimit_ext(String limit_ext) {
			this.limit_ext = limit_ext;
		}
		public int getMin_effect_amount() {
			return min_effect_amount;
		}
		public void setMin_effect_amount(int min_effect_amount) {
			this.min_effect_amount = min_effect_amount;
		}
		public int getLimit_type() {
			return limit_type;
		}
		public void setLimit_type(int limit_type) {
			this.limit_type = limit_type;
		}
		public String getRecv_time() {
			return recv_time;
		}
		public void setRecv_time(String recv_time) {
			this.recv_time = recv_time;
		}
		public String getCoupon_act_id() {
			return coupon_act_id;
		}
		public void setCoupon_act_id(String coupon_act_id) {
			this.coupon_act_id = coupon_act_id;
		}
		public int getCoupon_count() {
			return coupon_count;
		}
		public void setCoupon_count(int coupon_count) {
			this.coupon_count = coupon_count;
		}
		public int getCoupon_left() {
			return coupon_left;
		}
		public void setCoupon_left(int coupon_left) {
			this.coupon_left = coupon_left;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getUsed_order() {
			return used_order;
		}
		public void setUsed_order(String used_order) {
			this.used_order = used_order;
		}
		public String getUse_end() {
			return use_end;
		}
		public void setUse_end(String use_end) {
			this.use_end = use_end;
		}
		public int getStatus_id() {
			return status_id;
		}
		public void setStatus_id(int status_id) {
			this.status_id = status_id;
		}
		public String getCoupon_id() {
			return coupon_id;
		}
		public void setCoupon_id(String coupon_id) {
			this.coupon_id = coupon_id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSend_end() {
			return send_end;
		}
		public void setSend_end(String send_end) {
			this.send_end = send_end;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getSend_start() {
			return send_start;
		}
		public void setSend_start(String send_start) {
			this.send_start = send_start;
		}
		
	}
}
