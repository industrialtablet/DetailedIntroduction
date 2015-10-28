package com.example.activity;


import com.example.detailedintroduction.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;  
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;  
import android.os.Handler;  
import android.os.Looper;  
import android.os.Message;  
import android.view.KeyEvent;  
import android.view.View;  
import android.view.Window;  
import android.widget.AdapterView;  
import android.widget.AdapterView.OnItemClickListener;  
import android.widget.GridView;  
import android.widget.Toast;

import com.example.service.ProfileService;
import com.example.uitl.ScrollLayout.OnScreenChangeListenerDataLoad;
import com.example.adapter.AppAdapter;
import com.example.uitl.PageControlView;
import com.example.uitl.PersonalProfile;
import com.example.uitl.ScrollLayout;

@SuppressLint("ShowToast") public class MainActivity extends Activity {
	private int num = 0;
	@SuppressWarnings("rawtypes")
	List<Map> list;
	private ScrollLayout mScrollLayout;  
	private static final float APP_PAGE_SIZE = 8.0f;  
	private PageControlView pageControl;  
	public MyHandler myHandler;  
	public int n=0;  
	private DataLoading dataLoad;  
	public static String data = "/storage/external_storage/sdcard1/Service-evaluating/";
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activitymain);
		dataLoad = new DataLoading();  
		mScrollLayout = (ScrollLayout)findViewById(R.id.ScrollLayoutTest);  
		myHandler = new MyHandler(this,1);  
		//起一个线程更新数据  
		MyThread m = new MyThread();  
		new Thread(m).start();  
	}   
	/** 
	 * gridView 的onItemLick响应事件 
	 */  
	public OnItemClickListener listener = new OnItemClickListener() {  
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			int mCurScreen = ScrollLayout.mCurScreen;
			int number = 8*mCurScreen+position;
			String name = (String) list.get(number).get("name");
			String professional = (String) list.get(number).get("professional");
			int NO = (Integer) list.get(number).get("number");
			String topfile = (String) list.get(number).get("topfile");
			String introductionfile = (String) list.get(number).get("introductionfile");
			String introduction = (String) list.get(number).get("introduction");
			String agentid = (String) list.get(number).get("agentid");
			
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("name", name);
			bundle.putString("professional", professional);
			bundle.putString("NO", NO+"");
			bundle.putString("topfile", topfile+"");
			bundle.putString("introductionfile", introductionfile);
			bundle.putString("introduction", introduction);
			bundle.putString("agentid", agentid);
			intent.putExtras(bundle);
			intent.setClass(MainActivity.this, DetailedIntroduction.class);
			startActivity(intent);
		}  
	};  
	@Override  
	protected void onDestroy() {  
		android.os.Process.killProcess(android.os.Process.myPid());  
		super.onDestroy();  
	}  

	public boolean onKeyDown(int keyCode, KeyEvent event) {  
		if (keyCode == KeyEvent.KEYCODE_BACK) {  
			finish();
			return true;  
		}  
		return super.onKeyDown(keyCode, event);  
	}  
	// 更新后台数据  
	class MyThread implements Runnable {  
		public void run() {  
			try {  
				Thread.sleep(1000*3);  
			} catch (InterruptedException e) {  
				e.printStackTrace();
			}  
			String msglist = "1";  
			Message msg = new Message();  
			Bundle b = new Bundle();// 存放数据  
			b.putString("rmsg", msglist);  
			msg.setData(b);
			MainActivity.this.myHandler.sendMessage(msg); // 向Handler发送消息,更新UI  
		}  
	}  

	class MyHandler extends Handler {  
		private MainActivity mContext;  
		public MyHandler(Context conn,int a) {  
			mContext = (MainActivity)conn;  
		}  

		public MyHandler(Looper L) {  
			super(L);  
		}  
		// 子类必须重写此方法,接受数据  
		@SuppressWarnings("rawtypes")
		@Override  
		public void handleMessage(Message msg) {  
			super.handleMessage(msg);  
			Bundle b = msg.getData();  
			String rmsg = b.getString("rmsg");  
			if ("1".equals(rmsg)) {
				list = new ArrayList<Map>();
				try {
					File f = new File(data+"employee.xml");   
			        InputStream ssss = new FileInputStream(f);
					List<PersonalProfile> infos = ProfileService.getPersonalProfile(ssss);
					for (PersonalProfile info :infos) {
						int NO = Integer.parseInt(info.getRating());
						if(NO<0){
							NO = 0;
						}else if(NO>10){
							NO=10;
						}
						num = num+1;
						Map<String, Object> map = new HashMap<String, Object>();  
						map.put("name", info.getName());  
						map.put("professional", info.getDepartment());
						map.put("number", NO);
						map.put("topfile", data+info.getAgentid()+"/"+info.getAgentid()+".jpg");
						map.put("introductionfile", data+info.getAgentid()+"/introduction");
						map.put("introduction" , info.getIntroduction());
						map.put("agentid" , info.getAgentid());
						list.add(map);
					}
					ssss.close();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(MainActivity.this, "请插入内存卡再重新打开", 1).show();
				}
				int pageNo = (int)Math.ceil( list.size()/APP_PAGE_SIZE);  
				for (int i = 0; i < pageNo; i++) {
					GridView appPage = new GridView(mContext);  
					appPage.setAdapter(new AppAdapter(mContext, list, i));  
					appPage.setNumColumns(4);
					appPage.setOnItemClickListener(listener);  
					mScrollLayout.addView(appPage);  
				}  
				pageControl = (PageControlView) findViewById(R.id.pageControl);  
				pageControl.bindScrollViewGroup(mScrollLayout);  
				dataLoad.bindScrollViewGroup(mScrollLayout);  
			}  
		}
		@SuppressWarnings("unused")
		private Object integer(String rating) {
			return null;
		}  
	}  
	//分页数据  
	class DataLoading {  
		private int count;  
		public void bindScrollViewGroup(ScrollLayout scrollViewGroup) {  
			this.count=scrollViewGroup.getChildCount();  
			scrollViewGroup.setOnScreenChangeListenerDataLoad(new OnScreenChangeListenerDataLoad() {  
				public void onScreenChange(int currentIndex) {  
				}  
			});  
		}  
		@SuppressWarnings("unused")
		private void generatePageControl(int currentIndex){  
			//如果到最后一页，就加载16条记录  
			if(count==currentIndex+1){
				MyThread m = new MyThread();  
				new Thread(m).start();  
			}  
		}  
	}  
}  