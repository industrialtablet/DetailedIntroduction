package com.example.uitl;

import android.content.Context;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

/**
* @author JIN BIN BIN (AstroBoy)
* ����ȫ������
*/
public class CustomVideoView extends VideoView {
	 public CustomVideoView(Context context) {
		  super(context);
		  // TODO Auto-generated constructor stub
		 }
		 public CustomVideoView(Context context, AttributeSet attrs) {
		  super(context, attrs);
		  // TODO Auto-generated constructor stub0 ~3 K( G/ M( B# I) `
		 }
		 public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
		  super(context, attrs, defStyle);
		  // TODO Auto-generated constructor stub
		 }
		 @Override
		 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		  // TODO Auto-generated method stub
		 //�ص㡣
		  int width = getDefaultSize(0, widthMeasureSpec);
		  int height = getDefaultSize(0, heightMeasureSpec);
		  setMeasuredDimension(width, height);
		 }
		 public void setOnPreparedListener(OnPreparedListener l) {
		  // TODO Auto-generated method stub
		  super.setOnPreparedListener(l);
		 }
		 @Override
		 public boolean onKeyDown(int keyCode, KeyEvent event) {
		  // TODO Auto-generated method stub
		  return super.onKeyDown(keyCode, event);
		 }
		}

//extends VideoView {
//
//        private int mVideoWidth;
//        private int mVideoHeight;
//
//        public CustomVideoView(Context context) {
//                super(context);
//        }
//
//        public CustomVideoView(Context context, AttributeSet attrs) {
//                super(context, attrs);
//        }
//
//        public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
//                super(context, attrs, defStyle);
//        }
//
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//                // Log.i("@@@@", "onMeasure");
//
//               //����Ĵ���������Ƶ�Ĳ��ŵĳ����Ǹ��������õĲ���������
//
//                int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
//                int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
//                setMeasuredDimension(width, height);
//        }
//}