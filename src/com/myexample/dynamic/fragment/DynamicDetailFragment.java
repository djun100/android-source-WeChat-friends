package com.myexample.dynamic.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.myexample.dynamic.R;
import com.myexample.dynamic.activity.DynamicActivity;
import com.myexample.dynamic.activity.DynamicDetailActivity;
import com.myexample.dynamic.adapter.DynamicAdapter;
import com.myexample.dynamic.adapter.DynamicCommentAdapter;
import com.myexample.dynamic.bean.DynamicComment;
import com.myexample.dynamic.view.DynamicDetailHeadView;

public class DynamicDetailFragment extends Fragment{

	private int position;
	private String dynamicId;
	
	private View view;
	private EditText editText;
	private ListView listView;
	private Button commentBtn;
	private RelativeLayout unclickRl;
	private LinearLayout commentLy,commentShowLy;
	private DynamicDetailHeadView headView;
	private boolean kbflag;
	
	private int p,vh;
	
	public DynamicDetailFragment(){
		
	}
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.yh_dynamic_detail_list, container, false);
		listView=(ListView)view.findViewById(R.id.listview);
		commentLy=(LinearLayout)view.findViewById(R.id.comment_ly);
		commentBtn=(Button)view.findViewById(R.id.dynamic_item_comment_btn);
		editText=(EditText)view.findViewById(R.id.editText);
		commentShowLy=(LinearLayout)view.findViewById(R.id.comment_show_ly);
		unclickRl=(RelativeLayout)view.findViewById(R.id.unclick_rl);
		
		//把headview抽出去 可以用群里的生成find 生成
		headView=new DynamicDetailHeadView(getActivity(),handler);
		listView.addHeaderView(headView.view);
		listView.setAdapter(null);
		
		
		headView.setZan(DynamicAdapter.getUserList());
		headView.setComment(position);
		headView.contentTx.setText("当前是fragment+"+position);
		//空实现　为了让事件不往下传递
		unclickRl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
	 
		
		commentBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				handler.obtainMessage(510).sendToTarget();
				
			}
		});
		
		//异步加载数据 这里模拟一下
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				listView.setAdapter(new DynamicCommentAdapter(getActivity(), DynamicAdapter.getDynamicComments2(), handler));
				unclickRl.setVisibility(View.GONE);				
			}
		}, 1000);
		
		//监听界面的变化，判断当前页面键盘是否显示
		final View activityRootView = view.findViewById(R.id.rl);
		activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	
		@Override
		public void onGlobalLayout() {
			if(kbflag){
				Rect r = new Rect();
			    activityRootView.getWindowVisibleDisplayFrame(r);
			    int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
//			    Log.e("", "heightDiff:"+heightDiff+"  (r.bottom - r.top):"+(r.bottom - r.top));
			    if (heightDiff<100) { 
			    	commentLy.setVisibility(View.GONE);
//			    	commentShowLy.setVisibility(View.VISIBLE);
			    	kbflag=false;
		        } 
			}
				
		 }
		});
		
		return view;
	}
 
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 510:
				//评论
				commentLy.setVisibility(View.VISIBLE);
				editText.setFocusable(true);
				editText.requestFocus();  
				editText.setHint("来说说你的想法");
				editText.setTag("-1");
				showKeyBoard();
//				p=msg.arg1;
//				vh=msg.arg2;
//				scrollList();
				Toast.makeText(getActivity(), "评论", Toast.LENGTH_SHORT).show();
				break;
			case 511:
				//回复
				commentLy.setVisibility(View.VISIBLE);
				editText.setFocusable(true);
				editText.requestFocus();  
				editText.setText("");
				editText.setHint("回复 xxx:");
//				editText.setTag(comment.getFriendId());
				showKeyBoard();
				p=msg.arg1;
				vh=msg.arg2;
				Log.e("getMeasuredHeight之前", "vh:"+vh);
				if(commentLy.getMeasuredHeight()==0){
					vh+=getResources().getDimension(R.dimen.commentLy_height)-commentShowLy.getMeasuredHeight();
				}else{
					vh+=commentLy.getMeasuredHeight()-commentShowLy.getMeasuredHeight();
				}
				Log.e("getMeasuredHeight之后", "vh:"+vh);
				scrollList();
				Toast.makeText(getActivity(), "回复", Toast.LENGTH_SHORT).show();
				break;
 
			}
		};
	};
	
	private void scrollList(){
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				int  h=listView.getMeasuredHeight();
				Log.e("xxxxxxxxxx", "h:"+h+"   vh:"+vh+"  "+p);
				listView.setSelectionFromTop(p, h-vh);
			}
		}, 300);
	}
	
	private void  showKeyBoard(){
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
				kbflag=true;
			}
		}, 100);
	}
	
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}
	
	
	
	
}
