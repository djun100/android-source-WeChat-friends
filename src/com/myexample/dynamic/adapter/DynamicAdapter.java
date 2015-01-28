package com.myexample.dynamic.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.myexample.dynamic.bean.DynamicComment;
import com.myexample.dynamic.bean.User;
import com.myexample.dynamic.view.DynamicItemView;

public class DynamicAdapter extends BaseAdapter{
	
	private Context context;
	private Handler handler;
	
	
	public  DynamicAdapter(Context context,Handler handler){
		this.context=context;
		this.handler=handler;
	}
	
	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		DynamicItemView itemView=null;
		if(convertView==null){
			itemView=new DynamicItemView(context);
			convertView=itemView.view;
			convertView.setTag(itemView);
		}else{
			itemView=(DynamicItemView)convertView.getTag();
		}
		itemView.setHandler(handler);
		itemView.setPosition(position);
		itemView.setZan(getUserList());
		itemView.setComment(getDynamicComments());
		
		itemView.setCommentClick();
		
		return convertView;
	}



	public static ArrayList<DynamicComment> getDynamicComments() {
		ArrayList<DynamicComment> list = new ArrayList<DynamicComment>();
		DynamicComment  comment1=new DynamicComment();
		comment1.setNickname("麻仓叶");
		comment1.setContent("1楼 测试");
		
		DynamicComment  comment2=new DynamicComment();
		comment2.setNickname("麻仓叶");
		comment2.setContent("2楼  还是我");
		
		DynamicComment  comment3=new DynamicComment();
		comment3.setNickname("阿拉波");
		comment3.setContent("我现在在最上面");
		comment3.setReuserId(3+"");
		comment3.setReuserName("麻仓叶");
		list.add(comment3);
		list.add(comment2);
		list.add(comment1);
		return list;
	}
	
	public static ArrayList<DynamicComment> getDynamicComments2() {
		ArrayList<DynamicComment> list = new ArrayList<DynamicComment>();
		for (int i = 0; i < 7; i++) {
			DynamicComment  comment1=new DynamicComment();
			comment1.setNickname("麻仓叶");
			comment1.setContent("1楼 测试");
			
			DynamicComment  comment2=new DynamicComment();
			comment2.setNickname("麻仓叶");
			comment2.setContent("2楼  还是我");
			
			DynamicComment  comment3=new DynamicComment();
			comment3.setNickname("阿拉波");
			comment3.setContent("我现在在最上面");
			comment3.setReuserId(3+"");
			comment3.setReuserName("麻仓叶");
			list.add(comment3);
			list.add(comment2);
			list.add(comment1);
		}
		
		return list;
	}
	
	
	public static ArrayList<User> getUserList() {
		ArrayList<User> list = new ArrayList<User>();
		User t1 = new User();
		t1.setName("麻仓叶");
		User t2 = new User();
		t2.setName("阿拉波");
		User t3 = new User();
		t3.setName("张三");
		User t4 = new User();
		t4.setName("空虚");
		User t5 = new User();
		t5.setName("寂寞");
		User t6 = new User();
		t6.setName("冷");
		list.add(t1);
		list.add(t2);
		list.add(t3);
		list.add(t4);
		list.add(t5);
		list.add(t6);
		return list;
	}
	
}
