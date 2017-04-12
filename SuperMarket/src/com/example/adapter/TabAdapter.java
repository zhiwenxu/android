package com.example.adapter;

import com.example.fragment.TabFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabAdapter extends FragmentPagerAdapter {

	private String url[];
	
	public void setURL(String url[]){
		this.url = url;
	}
	
	public TabAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	@Override  
    public Fragment getItem(int position) {  
        // TODO Auto-generated method stub  
        TabFragment fragment = new TabFragment();  
        fragment.setURL(url[position]);
        Log.e("url", url[position]);
        return fragment;  
    }  
  
    @Override  
    public CharSequence getPageTitle(int position) {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return url.length;  
    }  
  

}
