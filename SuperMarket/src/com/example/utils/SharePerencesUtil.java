package com.example.utils;

import com.example.application.BaseApplication;

import android.content.Context;
import android.content.SharedPreferences;
public class SharePerencesUtil{
	
	private static SharePerencesUtil mSharePerencesUtil;
	private static SharedPreferences sharedPreferences;
	private static Context mContext;
	private SharePerencesUtil(){};
	public static SharePerencesUtil getInstances(){
		mContext = BaseApplication.getInstance();
		if(mSharePerencesUtil == null){
			mSharePerencesUtil = new SharePerencesUtil();
			sharedPreferences = mContext.getSharedPreferences(Constants.SHARE_NAME, 0);
		}
		return mSharePerencesUtil;
	}

	public boolean isFirstStart(){
		return sharedPreferences.getBoolean(Constants.IS_FIRST_START, true);
	}
	
	public void setFirstUse(boolean first){
		sharedPreferences.edit().putBoolean(Constants.IS_FIRST_USE, first).commit();
	}
	//判断是否第一次登录
	public void setFirstStart(boolean first){
		sharedPreferences.edit().putBoolean(Constants.IS_FIRST_START, first).commit();
	}
	//保存正常登陆的有用户名
	public void setLoginUserName(String username){
		sharedPreferences.edit().putString(Constants.SHARE_USER_NAME, username).commit();
	}
	//保存密码
	public void setPassWord(String password){
		sharedPreferences.edit().putString(Constants.SHARE_PASS_WORD, password).commit();
	}
	//保存id
	public void setID(String id){
		sharedPreferences.edit().putString(Constants.SHARE_ID, id).commit();
	}
	//保存uuid
	public void setUUID(String uuid){
		sharedPreferences.edit().putString(Constants.SHARE_UUID, uuid).commit();
	}
	
	//保存昵称
	public void setNick(String nick){
		sharedPreferences.edit().putString(Constants.SHARE_NICK, nick).commit();
	}
	//保存性别
	public void setGender(int gender){
		sharedPreferences.edit().putInt(Constants.SHARE_GENDER, gender).commit();
	}
	//保存积分
	public void setCents(int cents){
		sharedPreferences.edit().putInt(Constants.SHARE_CENTS, cents).commit();
	}
	//保存会员卡
	public void setCard(String card){
		sharedPreferences.edit().putString(Constants.SHARE_CARD, card).commit();
	}
	//保存人民币
	public void setRmb(int rmb){
		sharedPreferences.edit().putInt(Constants.SHARE_RMB, rmb).commit();
	}
	//保存登录状态
	public void setLogin(boolean login){
		sharedPreferences.edit().putBoolean(Constants.SHARE_LOGIN, login).commit();
	}
	public void setOpenid(String openid){
		sharedPreferences.edit().putString(Constants.SHARE_OPENID, openid).commit();
	}
	public void setHeadUrl(String url){
		sharedPreferences.edit().putString(Constants.SHARE_HEADURL, url).commit();
	}
	
	public void setLoginType(int type){
		sharedPreferences.edit().putInt(Constants.SHARE_LOGIN_TYPE, type).commit();
	}
	
	
	public boolean getFirstUse(){
		return sharedPreferences.getBoolean(Constants.IS_FIRST_USE, true);
	}
	public int getLoginType(){
		return sharedPreferences.getInt(Constants.SHARE_LOGIN_TYPE, 0);
	}
	public String getLoginUserName(){
		return sharedPreferences.getString(Constants.SHARE_USER_NAME, "");
	}
	public String getPassWord(){
		return sharedPreferences.getString(Constants.SHARE_PASS_WORD, "");
	}
	public String getID(){
		return sharedPreferences.getString(Constants.SHARE_ID, "");
	}
	public String getUUID(){
		return sharedPreferences.getString(Constants.SHARE_UUID, "");
	}
	
	public String getNick(){
		return sharedPreferences.getString(Constants.SHARE_NICK, "");
	}
	public int getGender(){
		return sharedPreferences.getInt(Constants.SHARE_GENDER, 0);
	}
	
	public int getCents(){
		return sharedPreferences.getInt(Constants.SHARE_CENTS, 0);
	}
	public String getCard(){
		return sharedPreferences.getString(Constants.SHARE_CARD, "");
	}
	public int getRmb(){
		return sharedPreferences.getInt(Constants.SHARE_RMB, 0);
	}
	public boolean getLogin(){
		return sharedPreferences.getBoolean(Constants.SHARE_LOGIN, false);
	}
	
	public String getOpenid(){
		return sharedPreferences.getString(Constants.SHARE_OPENID, "");
	}
	
	public String getHeadUrl(){
		return sharedPreferences.getString(Constants.SHARE_HEADURL, "");
	}

	
	//记录json文件到本地
	public void setCXHD(String str){
		sharedPreferences.edit().putString(Constants.SHARE_CXHD_URL, str).commit();
	}
	public void setXPCX(String str){
		sharedPreferences.edit().putString(Constants.SHARE_XPCX_URL, str).commit();
	}
	public void setGSXW(String str){
		sharedPreferences.edit().putString(Constants.SHARE_GSXW_URL, str).commit();
	}
	public void setGSZX(String str){
		sharedPreferences.edit().putString(Constants.SHARE_GSZX_URL, str).commit();
	}
	public void setZPXX(String str){
		sharedPreferences.edit().putString(Constants.SHARE_ZPXX_URL, str).commit();
	}
	public void setLYBK(String str){
		sharedPreferences.edit().putString(Constants.SHARE_LYBK_URL, str).commit();
	}
	public void setCJWT(String str){
		sharedPreferences.edit().putString(Constants.SHARE_CJWT_URL, str).commit();
	}
	public void setYHTK(String str){
		sharedPreferences.edit().putString(Constants.SHARE_YHTK_URL, str).commit();
	}
	public void setGYWM(String str){
		sharedPreferences.edit().putString(Constants.SHARE_GYWM_URL, str).commit();
	}
	public void setLoad(boolean load){
		sharedPreferences.edit().putBoolean(Constants.SHARE_IS_LOAD, load).commit();
	}
	
	public void setChangeImage(String str){
		sharedPreferences.edit().putString(Constants.SHARE_Changesss_URL, str).commit();
	}
	
	public String getCXHD(){
		return sharedPreferences.getString(Constants.SHARE_CXHD_URL, "");
	}
	public String getXPCX(){
		return sharedPreferences.getString(Constants.SHARE_XPCX_URL, "");
	}
	public String getGSXW(){
		return sharedPreferences.getString(Constants.SHARE_GSXW_URL, "");
	}
	public String getGSZX(){
		return sharedPreferences.getString(Constants.SHARE_GSZX_URL, "");
	}
	public String getZPXX(){
		return sharedPreferences.getString(Constants.SHARE_ZPXX_URL, "");
	}
	public String getLYBK(){
		return sharedPreferences.getString(Constants.SHARE_LYBK_URL, "");
	}
	public String getCJWT(){
		return sharedPreferences.getString(Constants.SHARE_CJWT_URL, "");
	}
	public String getYHTK(){
		return sharedPreferences.getString(Constants.SHARE_YHTK_URL, "");
	}
	public String getGYWM(){
		return sharedPreferences.getString(Constants.SHARE_GYWM_URL, "");
	}
	public String getChangeImage(){ 
		return sharedPreferences.getString(Constants.SHARE_Changesss_URL, "");
	}
	public boolean getLoad(){
		return sharedPreferences.getBoolean(Constants.SHARE_IS_LOAD, false);
	}
	
	//版本检查时间点标记
	public void setVersionDate(long date){
		sharedPreferences.edit().putLong(Constants.SHARE_VERSION_CHECK_DATE, date).commit();
	}
	
	public long getVersionDate(){
		return sharedPreferences.getLong(Constants.SHARE_VERSION_CHECK_DATE, 0);
	}
	
	
}
