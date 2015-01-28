package com.myexample.dynamic.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.myexample.dynamic.R;
import com.myexample.dynamic.bean.DynamicComment;
import com.myexample.dynamic.bean.User;
import com.myexample.dynamic.bean.UserClickSpan;

public class DynamicItemView {

	public View view;
	public Context context;
	private Handler handler;
	private int position;
	
	public TextView contentTx;
	public TextView zanTx;
	public TextView commentTx;
	public ImageView headImg;
	public RelativeLayout picRl;
	public ImageView img;
	public RelativeLayout imgRl;
	public ImageView charmimg;
	public RelativeLayout contentRl;
	public LinearLayout commentLy;
	public ImageView picImg;
	public Button commentBtn;
	

	public DynamicItemView(Context context) {
		view = LayoutInflater.from(context).inflate(R.layout.yh_dynamic_item, null);
		this.context = context;
		find();
	}

	public DynamicItemView(View view, Context context) {
		this.view = view;
		this.context = context;
		find();
	}

	
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	private void find() {
		contentTx = (TextView) view.findViewById(R.id.dynamic_item_content_tx);
		zanTx = (TextView) view.findViewById(R.id.dynamic_item_zan_tx);
		commentTx = (TextView) view.findViewById(R.id.dynamic_item_comment_tx);
		headImg = (ImageView) view.findViewById(R.id.dynamic_item_head_img);
		commentBtn = (Button) view.findViewById(R.id.dynamic_item_comment_btn);
		picRl = (RelativeLayout) view.findViewById(R.id.dynamic_item_content_pic_rl);
		img = (ImageView) view.findViewById(R.id.dynamic_item_img);
		imgRl = (RelativeLayout) view.findViewById(R.id.dynamic_item_img_rl);
		charmimg = (ImageView) view.findViewById(R.id.dynamic_item_charm_img);
		contentRl = (RelativeLayout) view.findViewById(R.id.dynamic_item_content_rl);
		commentLy = (LinearLayout) view.findViewById(R.id.dynamic_item_comment_ly);
		picImg = (ImageView) view.findViewById(R.id.dynamic_item_content_pic_img);
		zanTx.setMovementMethod(LinkMovementMethod.getInstance());
		commentTx.setMovementMethod(LinkMovementMethod.getInstance());
	}

	public void setZan(ArrayList<User> list) {
		String str = "查看全部" + list.size() + "次赞  ";
		int start = 0;
		int end = str.length();

		SpannableStringBuilder builder = new SpannableStringBuilder(str);
		builder.setSpan(new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Toast.makeText(context, "查看全部赞", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void updateDrawState(TextPaint ds) {
				ds.setColor(Color.parseColor("#a7a6a8"));
				ds.setUnderlineText(false);
			}
		}, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				start = end;
				User u = list.get(i);
				builder.append("" + u.getName());
				end += u.getName().length();
				builder.setSpan(new UserClickSpan(context, u.getUserId()+"",u.getName()), start, end, 0);
				if (i != list.size() - 1) {
					builder.append("，");
					end++;
				}
			}
		}
		zanTx.setText(builder, BufferType.SPANNABLE);
	}

	
	public void setCommentClick(){
			commentBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if(handler!=null){
					 handler.obtainMessage(510,position,view.getMeasuredHeight()).sendToTarget();
				 }
			}
		});
	}
	
	public void setComment(ArrayList<DynamicComment> list){
		String str = "查看全部" + list.size() + "条评论  ";
		int start = 0;
		int end = str.length();

		SpannableStringBuilder builder = new SpannableStringBuilder(str);
		builder.setSpan(new ClickableSpan() {

			@Override
			public void onClick(View widget) {
//				Toast.makeText(context, "查看全部评论", Toast.LENGTH_SHORT).show();
				handler.obtainMessage(411).sendToTarget();
			}

			@Override
			public void updateDrawState(TextPaint ds) {
				ds.setColor(Color.parseColor("#a7a6a8"));
				ds.setUnderlineText(false);
			}
		}, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		commentTx.setText(builder, BufferType.SPANNABLE);
		
		initCommentLayout(list);
	}
	 
	private void  initCommentLayout(ArrayList<DynamicComment> list){
		commentLy.removeAllViewsInLayout();
		if(list!=null&&list.size()>0){
			for (int i = 0; i< list.size(); i++) {
				DynamicComment cc=list.get(i);
				
				View ll=LayoutInflater.from(context).inflate(R.layout.yh_dynamic_item_commently, null);
				TextView contentTx=(TextView) ll.findViewById(R.id.circle_item_commentContentTx);
				contentTx.setMovementMethod(LinkMovementMethod.getInstance());
				//直接评论为-1 回复为 回复人id
				if("-1".equals(cc.getReuserId())){
					int start=0;
	        		int end=0+cc.getNickname().length()+1;
	        		String content=cc.getContent()==null?"":cc.getContent();
	        		
	        		SpannableString tSS = new SpannableString(cc.getNickname()+"："+content);
	        		tSS.setSpan(new UserClickSpan(context,cc.getUserId(),cc.getNickname()), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
	        		contentTx.setText(tSS);
	        		commentLy.addView(ll);
				}else{
					int start1=0;
	        		int end1=0+cc.getNickname().length();
	        		String content=cc.getContent()==null?"":cc.getContent();
	        		
	        		int start2=end1+2;
	        		int end2=start2+cc.getReuserName().length()+1;
	        		
	        		SpannableString tSS = new SpannableString(cc.getNickname()+"回复"+cc.getReuserName()+"："+content);
	        		tSS.setSpan(new UserClickSpan(context,cc.getUserId(),cc.getNickname()), start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	        		tSS.setSpan(new UserClickSpan(context,cc.getReuserId(),cc.getReuserName()), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
	        		contentTx.setText(tSS);
	        		commentLy.addView(ll);
				}
				
				contentTx.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						long t2=System.currentTimeMillis();
						if(Math.abs(UserClickSpan.clickTime-t2)<500){
							return;
						}
						Log.e("contentTx.setOnClickListener", "contentTx.setOnClickListener");
						
						
//						Log.e("view ", "view:"+view.getMeasuredHeight()+"  "+view.getTop()+" "+view.getBottom());
//						Log.e("v ", "v:"+v.getMeasuredHeight()+"  "+v.getTop()+" "+v.getBottom());
						
						int[] a1=new int[2];
						view.getLocationOnScreen(a1);
						int[] a2=new int[2];
						v.getLocationOnScreen(a2);
						
						Log.e("view ", "view:"+a1[0]+"  "+a1[1]+" ");
						Log.e("v ", "v:"+a2[0]+"  "+a2[1]+"  "+v.getMeasuredHeight());
						
						if(handler!=null){
							 handler.obtainMessage(511,position,a2[1]-a1[1]+v.getMeasuredHeight()).sendToTarget();
						 }
					}
				});
				
			}
			
		}else{
			commentLy.setVisibility(View.GONE);
		}
	}
	
	
}
