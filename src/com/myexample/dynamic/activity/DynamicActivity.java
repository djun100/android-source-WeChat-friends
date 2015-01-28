package com.myexample.dynamic.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.myexample.dynamic.R;
import com.myexample.dynamic.adapter.DynamicAdapter;

public class DynamicActivity extends Activity{

	private LinearLayout commentLy;
	private EditText editText;
	private ListView listView;
	private boolean kbflag;
	
	private int p,vh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yh_dynamic_main);
	
		listView=(ListView) findViewById(R.id.listview);
		commentLy=(LinearLayout) findViewById(R.id.comment_ly);
		editText=(EditText) findViewById(R.id.editText);
		listView.setAdapter(new DynamicAdapter(DynamicActivity.this,handler));
		
		//监听界面的变化，判断当前页面键盘是否显示
		final View activityRootView = findViewById(R.id.rl);
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
			    	kbflag=false;
		        } 
			}
				
		 }
		});
		
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 411:
				Intent intent=new Intent(DynamicActivity.this,DynamicDetailActivity.class);
				startActivity(intent);
				break;
			case 510:
				//评论
				commentLy.setVisibility(View.VISIBLE);
				editText.setFocusable(true);
				editText.requestFocus();  
				editText.setHint("来说说你的想法");
				editText.setTag("-1");
				showKeyBoard();
				p=msg.arg1;
				vh=msg.arg2;
				scrollList();
				Toast.makeText(DynamicActivity.this, "评论", Toast.LENGTH_SHORT).show();
				break;
			case 511:
				//回复
				commentLy.setVisibility(View.VISIBLE);
				editText.setFocusable(true);
				editText.requestFocus();  
//				editText.setHint("回复"+comment.getNickname()+":");
//				editText.setTag(comment.getFriendId());
				showKeyBoard();
				p=msg.arg1;
				vh=msg.arg2;
				//第一次显示 commentLy没有高度 所以有问题 建议大家固定commentLy的高度 写死在代码 
				//先设置默认的高 R.dimen.commentLy_height 如果要记住之前的评论内容的话
//				vh+=commentLy.getMeasuredHeight();
				vh+=getResources().getDimension(R.dimen.commentLy_height);
				scrollList();
				Toast.makeText(DynamicActivity.this, "回复", Toast.LENGTH_SHORT).show();
				break;
 
			}
		};
	};
	
	private void scrollList(){
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				int  h=listView.getHeight();
				Log.e("xxxxxxxxxx", "h:"+h+"   vh:"+vh);
				listView.setSelectionFromTop(p, h-vh);
			}
		}, 300);
	}
	
	private void  showKeyBoard(){
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
				kbflag=true;
			}
		}, 100);
	}
}
