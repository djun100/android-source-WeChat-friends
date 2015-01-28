package com.myexample.dynamic.view;
import java.util.ArrayList;

import com.myexample.dynamic.R;
import com.myexample.dynamic.bean.DynamicComment;
import com.myexample.dynamic.bean.User;
import com.myexample.dynamic.bean.UserClickSpan;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.*;
import android.widget.TextView.BufferType;
import android.view.LayoutInflater;

public class DynamicDetailHeadView {

	 public View view;
	 public Context context;
	 private Handler handler;

	 public ImageView  picImg;
	 public ImageView  charmImg;
	 public ImageView  headImg;
	 public TextView  commentTx;
	 public TextView  zanTx;
	 public RelativeLayout  contentRl;
	 public RelativeLayout  picRl;
	 public RelativeLayout  imgRl;
	 public TextView  contentTx;
	 public ImageView  itemImg;


	 public DynamicDetailHeadView(Context context,Handler handler){
		 view=LayoutInflater.from(context).inflate(R.layout.yh_dynamic_detail_head, null);
		 this.context=context;
		 this.handler=handler;
		 find();
	 }

 
	 public DynamicDetailHeadView(View view,Context context){
		 this.view=view;
		 this.context=context;
		 find();
	 }

	 private void find() {
		 picImg=(ImageView)view.findViewById(R.id.dynamic_item_content_pic_img);
		 charmImg=(ImageView)view.findViewById(R.id.dynamic_item_charm_img);
		 headImg=(ImageView)view.findViewById(R.id.dynamic_item_head_img);
		 commentTx=(TextView)view.findViewById(R.id.dynamic_item_comment_tx);
		 zanTx=(TextView)view.findViewById(R.id.dynamic_item_zan_tx);
		 contentRl=(RelativeLayout)view.findViewById(R.id.dynamic_item_content_rl);
		 picRl=(RelativeLayout)view.findViewById(R.id.dynamic_item_content_pic_rl);
		 imgRl=(RelativeLayout)view.findViewById(R.id.dynamic_item_img_rl);
		 contentTx=(TextView)view.findViewById(R.id.dynamic_item_content_tx);
		 itemImg=(ImageView)view.findViewById(R.id.dynamic_item_img);
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

	 
		public void setComment(int count){
			String str =  count+"条评论  ";
			int start = 0;
			int end = str.length();

			SpannableStringBuilder builder = new SpannableStringBuilder(str);
			builder.setSpan(new ClickableSpan() {

				@Override
				public void onClick(View widget) {
//					Toast.makeText(context, "查看全部评论", Toast.LENGTH_SHORT).show();
					handler.obtainMessage(411).sendToTarget();
				}

				@Override
				public void updateDrawState(TextPaint ds) {
					ds.setColor(Color.parseColor("#a7a6a8"));
					ds.setUnderlineText(false);
				}
			}, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			commentTx.setText(builder, BufferType.SPANNABLE);
			
		}
}
