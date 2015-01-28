package com.myexample.dynamic.adapter;
import com.myexample.dynamic.R;
import com.myexample.dynamic.bean.DynamicComment;
import com.myexample.dynamic.bean.UserClickSpan;

import android.content.Context;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.view.LayoutInflater;
import java.util.List;

public class DynamicCommentAdapter extends BaseAdapter{
	
	private Context context;
	private List<DynamicComment> list;
	private Handler handler;
	
	public DynamicCommentAdapter(Context context,List<DynamicComment> list, Handler handler){
		this.context=context;
		this.list=list;
		this.handler=handler;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ItemView item=null;
		if(view==null){
			item=new ItemView();
			view=LayoutInflater.from(context).inflate(R.layout.yh_dynamic_item_commently, null);
		    item.contentTx=(TextView)view.findViewById(R.id.circle_item_commentContentTx);
		    item.contentTx.setMovementMethod(LinkMovementMethod.getInstance());
			view.setTag(item);
		}else{
			item=(ItemView)view.getTag();
		}
		
		DynamicComment cc=list.get(position);
		
		//直接评论为-1 回复为 回复人id
		if("-1".equals(cc.getReuserId())){
			int start=0;
    		int end=0+cc.getNickname().length()+1;
    		String content=cc.getContent()==null?"":cc.getContent();
    		
    		SpannableString tSS = new SpannableString(cc.getNickname()+"："+content);
    		tSS.setSpan(new UserClickSpan(context,cc.getUserId(),cc.getNickname()), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
    		item.contentTx.setText(tSS);
		}else{
			int start1=0;
    		int end1=0+cc.getNickname().length();
    		String content=cc.getContent()==null?"":cc.getContent();
    		
    		int start2=end1+2;
    		int end2=start2+cc.getReuserName().length()+1;
    		
    		SpannableString tSS = new SpannableString(cc.getNickname()+"回复"+cc.getReuserName()+"："+content);
    		tSS.setSpan(new UserClickSpan(context,cc.getUserId(),cc.getNickname()), start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
    		tSS.setSpan(new UserClickSpan(context,cc.getReuserId(),cc.getReuserName()), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
    		item.contentTx.setText(tSS);
		}
		
		
		final int p=position;
		item.contentTx.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long t2=System.currentTimeMillis();
				if(Math.abs(UserClickSpan.clickTime-t2)<500){
					return;
				}
				
				if(handler!=null){
					 handler.obtainMessage(511,p+1,v.getMeasuredHeight()).sendToTarget();
				 }
			}
		});
		
		return view;
	}
	
	class ItemView{
	 public TextView  contentTx;
		
		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}
	
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
