package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.fragment.FxTabFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FxTabAdapter extends FragmentPagerAdapter {

	private String url[];
	private List<FxTabFragment> fragments = new ArrayList<FxTabFragment>();
	public void reLoadUrl(String l){
		fragments.get(2).reLoad(l);
	}
	public void setURL(String url[]){
		this.url = url;
	}
	
	public FxTabAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	@Override  
    public Fragment getItem(int position) {  
        // TODO Auto-generated method stub  
        FxTabFragment fragment = new FxTabFragment();  
        fragments.add(fragment);
        fragment.setURL(url[position]);
        fragment.setPage(position);
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
