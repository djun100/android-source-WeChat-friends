package com.myexample.dynamic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.myexample.dynamic.bean.Dynamic;
import com.myexample.dynamic.fragment.DynamicDetailFragment;

public class DynamicPageAdapter extends FragmentStatePagerAdapter{

	
	private List<Dynamic> list;
	private FragmentActivity activity;
	
	public DynamicPageAdapter(FragmentActivity activity,List<Dynamic> list) {
		super(activity.getSupportFragmentManager());
		if(list==null)list=new ArrayList<Dynamic>();
		this.activity=activity;
		this.list=list;
	}

 

	@Override
	public Fragment getItem(int position) {
//		return list.get(position);
		DynamicDetailFragment fragment=new DynamicDetailFragment();
		fragment.setPosition(position);
		return fragment;
	}

	
	/**
	 * 这个方法是判断 activity 跳转到别的页面后 回来是否刷新的，
	 * 可能跳转后 回来list发生改变 当前positon对应的fragment不是之前的fragment
	 */
	@Override
	public int getItemPosition(Object object) {
		DynamicDetailFragment fragment=(DynamicDetailFragment)object;
		int position=fragment.getPosition();
		if(position>list.size()-1)return POSITION_NONE;
		Dynamic dynamic=list.get(position);
		if(dynamic.getDynamicId().equals(fragment.getDynamicId())){
			Log.e("相同", "getItemPosition相同");
			return POSITION_UNCHANGED;
		}else{
			Log.e("不同", "getItemPosition不同");
			return POSITION_NONE;
		}
	}
	
	
	@Override
	public int getCount() {
		return list.size();
	}

	

	public void notifyDataSetChanged(List<Dynamic> list) {
		if(list==null)list=new ArrayList<Dynamic>();
		this.list=list;
		super.notifyDataSetChanged();
	}
}
