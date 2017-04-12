package com.example.network;

public class ServerURL {
	public static final String SERVER = "http://119.29.115.106:80"; //服务器地址 
	public static final String USER_EXIST_URL = SERVER+"/app/exist/user";//用户存在性查询
	public static final String PING_PAY_URL = SERVER+"/app/pingpp/charge";//ping++交易对象
	public static final String NEWS_MSG_URL = SERVER+"/app/newmsg";//未读消息查询
	public static final String VERIFICATION_CODE_URL = SERVER+"/app/sms/code";//验证码获取
	public static final String REGISTER_URL = SERVER+"/app/register";//新用户注册
	public static final String LOGIN_URL = SERVER+"/app/login";//用户普通登录
	public static final String LOGIN_UNION_URL = SERVER+"/app/unionlogin";//用户联合登录
	public static final String BAND_URL = SERVER+"/app/band";//绑定手机
	public static final String CHANGE_PHONE_URL = SERVER+"/app/change/phone";//更改手机
	public static final String CHANGE_PWD_URL = SERVER+"/app/repass";//更改密码
	public static final String ICON_SET_URL = SERVER+"/app/user/photo/set";//头像上传
	public static final String USER_INFO_CHANGE_URL = SERVER+"/app/user/modify";//用户信息修改
	public static final String USER_INFO_GET_URL = SERVER+"/app/user/info";//用户信息获取
	public static final String CHARGE_RECORD_URL = SERVER+"/app/charge/record";//充值记录
	public static final String SPEND_RECORD_URL = SERVER+"/app/spend/record";//消费记录
	public static final String CENTS_RECORD_URL = SERVER+"/app/cents/record";//积分记录
	public static final String COUPON_USERLIST_URL = SERVER+"/app/coupon/userlist";//查询我的优惠券
	public static final String COUPON_LIST_URL = SERVER+"/app/coupon/get";//抢优惠券
	public static final String STORE_LIST_URL = SERVER+"/app/store/list";//门店信息列表（使用传经纬度）
	public static final String STORE_PHOTO_URL = SERVER+"/app/store/photo/get?storeid=";//门店图片
	public static final String BONUS_RATE_URL = SERVER+"/app/bonus/rate";//积分兑换率
	public static final String FEED_BACK_URL = SERVER+"/app/service/feedback";//意见反馈
	public static final String GET_PHONE_URL = SERVER+"/app/service/phone";//获得客服电话
	public static final String USER_RANK_URL = SERVER+"/app/level/rule/get";//获得用户等级规则
	public static final String JPUSH_URL = SERVER+"/app/user/jpushid/set";//发送极光推送id
	public static final String GET_JSON_URL = SERVER+"/app/get/urljson";//获得json广告，新闻的文件
	public static final String GET_VERSION_JSON_URL = SERVER+"/app/versionfile/android/get";//获得版本信息，app下载链接
	public static final String LOGOUT_URL = SERVER+"/app/logout";//退出登录接口
	public static final String GET_RECHARGE_GIFT_URL = SERVER+"/app/rechargegift/get";//充值送费规则接口
	public static final String GET_HD_URL = "http://119.29.115.106:8090/app/activity/recent";//获得活动信息接口
}
