package com.example.network;

import com.google.gson.Gson;

public class GsonHelper {
	private static Gson gson = null;
	private GsonHelper(){}
	public static Gson getGson(){
		if(gson == null){
			gson = new Gson();
		}
		return gson;
	}
}
