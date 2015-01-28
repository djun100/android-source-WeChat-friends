package com.myexample.dynamic.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.myexample.dynamic.R;
import com.myexample.dynamic.adapter.DynamicPageAdapter;
import com.myexample.dynamic.bean.Dynamic;

public class DynamicDetailActivity extends FragmentActivity{

	ViewPager viewPager;
	DynamicPageAdapter pageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yh_dynamic_detail);
		
		viewPager=(ViewPager)findViewById(R.id.dynamic_detail_viewpage);
		ArrayList<Dynamic> list=new ArrayList<Dynamic>();
		for (int i = 0; i < 40; i++) {
			list.add(new Dynamic());
		}
		
		pageAdapter=new DynamicPageAdapter(DynamicDetailActivity.this, list);
		viewPager.setAdapter(pageAdapter);
		
	}
	
}
