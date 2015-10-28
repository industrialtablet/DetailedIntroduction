package com.example.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.detailedintroduction.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class DetailedIntroduction extends Activity {
	private ImageView star[] = new ImageView[5];
	private TextView name1;
	private TextView introductioncontent;
	private TextView professional;
	private ImageView image;
	private ImageView top;
	private VideoView video;
	private int cun = 0;
	private String pathss;
	private String topfile;
	private String agentid;
	private String introductionfile;
	private String introduction;
	private String []arr;
	int NO;
	File videofile;
	File file;
	public MediaController mediaController;
	private List<File> list;
	private List<Map<String, String>> data;
	private Map<String, String> map;
	private boolean play = true;
	@SuppressLint("HandlerLeak") Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if(!list.isEmpty()){
				arr = data.get(cun).get("path").split("=");
				pathss = arr[0];
				file=new File(pathss); 
				if (msg.what==1 && file.exists()) {
					video.setVisibility(View.INVISIBLE);
					image.setVisibility(View.VISIBLE);
					Drawable img = BitmapDrawable.createFromPath(file.getAbsolutePath());
					image.setScaleType(ScaleType.CENTER_CROP);
					image.setImageDrawable(img);
				}else if(msg.what==2 && file.exists()){
					image.setVisibility(View.INVISIBLE);
					video.setVisibility(View.VISIBLE); 
					video.setVideoPath(file.getAbsolutePath());
					video.setMediaController(mediaController);  
					mediaController.setMediaPlayer(video);  
					video.start();
				}  
			}
		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_detailed_introduction);

		name1 = (TextView) findViewById(R.id.name);
		introductioncontent = (TextView) findViewById(R.id.introduction_content);
		professional = (TextView) findViewById(R.id.professional);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		name1.setText(bundle.getString("name"));
		topfile = bundle.getString("topfile");
		agentid = bundle.getString("agentid");
		professional.setText(agentid);
		
		introductionfile = bundle.getString("introductionfile");
		introduction = bundle.getString("introduction");
		introductioncontent.setText(introduction);
		NO = Integer.parseInt(bundle.getString("NO"));
		
		image = (ImageView) findViewById(R.id.image);
		top = (ImageView) findViewById(R.id.top);
		video = (VideoView) findViewById(R.id.video);
		File file=new File(topfile);
		if(file.exists()){
			Drawable img = BitmapDrawable.createFromPath(file.getAbsolutePath());
			top.setScaleType(ScaleType.CENTER_CROP);
			top.setImageDrawable(img);
		}
		
		star[0] = (ImageView) findViewById(R.id.star1);
		star[1] = (ImageView) findViewById(R.id.star2);
		star[2] = (ImageView) findViewById(R.id.star3);
		star[3] = (ImageView) findViewById(R.id.star4);
		star[4] = (ImageView) findViewById(R.id.star5);
		if(NO%2 ==1){
			for (int i = 0; i < (NO/2); i++) {
				star[i].setBackgroundResource(R.drawable.star);
			}
			star[NO/2].setBackgroundResource(R.drawable.half_star);
		}else{
			for (int i = 0; i < (NO/2); i++) {
				star[i].setBackgroundResource(R.drawable.star);
			}
		}
		geterateListView();
		mediaController = new MediaController(this);
		new Thread(){
			public void run() { 
				while (true) {
					if(!video.isPlaying() && play){
						if(data.size()!=0){
							cun = (cun +1)%(data.size());
							String name= data.get(cun).get("path");
							if(name.contains(".jpg") || name.contains(".png")){
								Message msg = new Message();  
								msg.what = 1;
								handler.sendMessage(msg); 
								play = false;
								try {
									//设置
									Thread.sleep(3000);
									play = true;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else{
								Message msg = new Message();  
								msg.what = 2;
								handler.sendMessage(msg);
							}
						}else{
							video.setVisibility(View.INVISIBLE);
							image.setVisibility(View.VISIBLE);
							image.setBackgroundResource(R.drawable.bg);
						}
					}
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();  
	}
	public void click(View v){
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("name", name1.getText().toString());
		bundle.putString("NO", NO+"");
		bundle.putString("agentid", agentid);
		bundle.putString("introduction", introduction);
		bundle.putString("topfile", topfile);
		intent.putExtras(bundle);
		intent.setClass(DetailedIntroduction.this, EvaluationOfInterface.class);
		startActivity(intent);
		finish();
	}
	private void findAll(File file, List<File> list) {
		File[] subFiles = file.listFiles();
		if (subFiles != null)
			for (File subFile : subFiles) {
				if ((subFile.isFile() && subFile.getName().endsWith(".mp4"))
						||(subFile.isFile() && subFile.getName().endsWith(".avi"))
						||(subFile.isFile() && subFile.getName().endsWith(".rm"))
						||(subFile.isFile() && subFile.getName().endsWith(".rmvb"))
						||(subFile.isFile() && subFile.getName().endsWith(".wmv"))
						||(subFile.isFile() && subFile.getName().endsWith(".3gp"))
						||(subFile.isFile() && subFile.getName().endsWith(".flv"))
						||(subFile.isFile() && subFile.getName().endsWith(".jpg"))
						||(subFile.isFile() && subFile.getName().endsWith(".png"))
						||(subFile.isFile() && subFile.getName().endsWith(".mov"))
						)
					list.add(subFile);
				else if (subFile.isDirectory())// 如果是目录
					findAll(subFile, list); // 递归
			}
	}

	private void geterateListView() {
		list = new ArrayList<File>();
		if(USBcd()){
			findAll(videofile, list);
			Collections.sort(list);
			data = new ArrayList<Map<String, String>>();
			for (File file : list) {
				map = new HashMap<String, String>();
				map.put("path", file.getAbsolutePath());
				data.add(map);
			}
		}else{
//			Toast.makeText(getApplicationContext(), "找不到视屏", 1).show();
		}
	}
	private boolean USBcd(){
		File file = new File(introductionfile);
		if(file.exists()){
			videofile = new File(introductionfile);
			return true;
		}else{
			file.mkdirs();
			return false;
		}
	}
}
