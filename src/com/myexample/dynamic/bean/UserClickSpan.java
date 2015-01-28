package com.myexample.dynamic.bean;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class UserClickSpan extends ClickableSpan{

	private String userId;
	private String name;
	private String color="#6a8694";
	private Context context;
	public static   long clickTime;
	
	public UserClickSpan(Context context,String userId,String name){
		this.userId=userId;
		this.name=name;
		this.context=context;
	}
	
	public UserClickSpan(Context context,String userId,String name,String color){
		this.userId=userId;
		this.name=name;
		this.context=context;
		this.color=color;
	}
	
	@Override
	public void onClick(View widget) {
		clickTime=System.currentTimeMillis();
		Log.e("我点击了", "我点击了");
		Toast.makeText(context, "我点击了:"+name, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		 ds.setColor(Color.parseColor(color));
		 ds.setUnderlineText(false); 
	}
	
}
