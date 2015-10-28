package com.example.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.dao.CommentsDAO;
import com.example.detailedintroduction.R;
import com.example.uitl.Comments;

import android.net.Uri;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class EvaluationOfInterface extends Activity {
	private static int comments_number;
	private ImageView star[] = new ImageView[5];
	private ImageView top;
	private TextView name;
	private TextView introductioncontent;
	private TextView agent;
	private String topfile;
	private String introduction;
	private String staff_name;
	private String agentid;
	private int [] stars = {R.id.star1,R.id.star2,R.id.star3,R.id.star4,R.id.star5,};
	static final String TAG =  "CAMERA ACTIVITY"; 
	Camera mCamera;
	int veryGood =0;
	int good = 0;
	int general = 0;
	int bad = 0;
	String [] arr;
	Comments comments;
	CommentsDAO dao;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean previewing;
	int mCurrentCamIndex = 0;
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.evaluation_of_interface);
		top = (ImageView) findViewById(R.id.top);
		name = (TextView) findViewById(R.id.name);
		agent = (TextView) findViewById(R.id.agent);
		introductioncontent = (TextView) findViewById(R.id.introductioncontent);
		for (int i = 0; i < star.length; i++) {
			star[i] = (ImageView) findViewById(stars[i]); 
		}
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		staff_name = bundle.getString("name");
		name.setText("姓名："+staff_name);
		topfile =bundle.getString("topfile");
		File file=new File(topfile); 
		Drawable img = BitmapDrawable.createFromPath(file.getAbsolutePath());
		top.setScaleType(ScaleType.CENTER_CROP);
		top.setImageDrawable(img);
		introduction =bundle.getString("introduction");
		agentid =bundle.getString("agentid");
		introductioncontent.setText(introduction);
		agent.setText(agentid);
		int NO = Integer.parseInt(bundle.getString("NO"));
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
		if(orCamera()){
			surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
			surfaceHolder = surfaceView.getHolder();
			surfaceView.getHolder().setFixedSize(1800, 1480);
			surfaceHolder.addCallback(new SurfaceViewCallback());
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		arr();
//		Toast.makeText(EvaluationOfInterface.this, "arr="+arr, 1).show();
//		Log.v(TAG, arr+"SB");
	}
	public void Camera(){
		if (previewing){
			mCamera.takePicture(null, rawPictureCallback,
					jpegPictureCallback);
		}
	}
	public void click(View v){
		switch (v.getId()) {
		case R.id.very_satisfied_with:
			Toast.makeText(getApplicationContext(), "您选择了非常满意", 1).show();
			arr();
			veryGood = veryGood()+1;
			comments.setVeryGood(veryGood);
			dao.updateComment(comments, agentid);
			comments_number = 1;
			Camera();
			break;
		case R.id.satisfied_with_the:
			Toast.makeText(getApplicationContext(), "您选择了满意", 1).show();
			arr();
			good = good()+1;
			comments.setGood(good);
			dao.updateComment(comments, agentid);
			comments_number = 2;
			Camera();
			break;
		case R.id.general:
			Toast.makeText(getApplicationContext(), "您选择了一般", 1).show();
			general = general()+1;
			comments.setGeneral(general);
			dao.updateComment(comments, agentid);
			comments_number = 3;
			Camera();
			break;
		case R.id.not_satisfied_with:
			Toast.makeText(getApplicationContext(), "您选择了不满意", 1).show();
			bad = bad()+1;
			comments.setBad(bad);
			dao.updateComment(comments, agentid);
			comments_number = 4;
			Camera();
			break;

		default:
			break;
		}
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};	

	PictureCallback rawPictureCallback = new PictureCallback() {
		public void onPictureTaken(byte[] arg0, Camera arg1) {

		}
	};

	PictureCallback jpegPictureCallback = new PictureCallback() {
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			if(orCamera()){
				String type = null;
				if(comments_number==1){
					type = "非常满意";
				}else if(comments_number == 2){
					type = "满意";
				}else if(comments_number == 3){
					type = "一般";
				}else if(comments_number == 4){
					type = "不满意";
				}
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH时mm分ss秒");  
				Date curDate = new Date(System.currentTimeMillis());//获取当前时间  
				String str = formatter.format(curDate);  
				Log.v(TAG, str);
				String fileName = MainActivity.data+agentid+"/commentators/"+agentid+type+str + ".jpg";
				File file = new File(fileName);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdir();
				}
				try {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(file));
					bos.write(arg0);
					bos.flush();
					bos.close();		
					scanFileToPhotoAlbum(file.getAbsolutePath());
				} catch (Exception e) {
				}
				finish();
			};
		}
	};

	public void scanFileToPhotoAlbum(String path) {
		if(orCamera()){
			MediaScannerConnection.scanFile(EvaluationOfInterface.this,
					new String[] { path }, null,
					new MediaScannerConnection.OnScanCompletedListener() {
				public void onScanCompleted(String path, Uri uri) {
					Log.i("TAG", "Finished scanning " + path);
				}
			});
		}
	}
	private final class SurfaceViewCallback implements android.view.SurfaceHolder.Callback {   
		public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
		{
			if(orCamera()){
				if (previewing) {
					mCamera.stopPreview();
					previewing = false;
				}
				try {
					mCamera.setPreviewDisplay(arg0);
					mCamera.startPreview();
					previewing = true;
					setCameraDisplayOrientation(EvaluationOfInterface.this, mCurrentCamIndex, mCamera);
				} catch (Exception e) {}
			}
		}
		public void surfaceCreated(SurfaceHolder holder) {
			if(orCamera()){
				mCamera = openFrontFacingCameraGingerbread();
				Camera.Parameters params = mCamera.getParameters();

				List<String> focusModes = params.getSupportedFocusModes();
				if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
				}
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			if(orCamera()){
				mCamera.stopPreview();
				mCamera.release();
				mCamera = null;
				previewing = false;
			}
		}
	}
	private Camera openFrontFacingCameraGingerbread() {
		if(orCamera()){
			int cameraCount = 0;
			Camera cam = null;
			Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
			cameraCount = Camera.getNumberOfCameras();
			for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
				Camera.getCameraInfo(camIdx, cameraInfo);
				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
					try {
						cam = Camera.open(camIdx);
						mCurrentCamIndex = camIdx;
					} catch (RuntimeException e) {
						Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
					}
				}
			}
			return cam;
		}return null;
	} 
	private static void setCameraDisplayOrientation(Activity activity,int cameraId, Camera camera) 
	{    
		if(orCamera()){
			Camera.CameraInfo info = new Camera.CameraInfo(); 
			Camera.getCameraInfo(cameraId, info);      
			int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
			int degrees = 0;
			switch (rotation) 
			{   
			case Surface.ROTATION_0: degrees = 0; break;         
			case Surface.ROTATION_90: degrees = 90; break;    
			case Surface.ROTATION_180: degrees = 180; break; 
			case Surface.ROTATION_270: degrees = 270; break;  
			}      
			int result;  
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
			{        
				result = (info.orientation + degrees) % 360;     
				result = (360 - result) % 360;   
			} 
			else 
			{  
				result = (info.orientation - degrees + 360) % 360;   
			}
			camera.setDisplayOrientation(result);  
		}
	} 
	public static boolean orCamera(){
		int cameraCount = 0;
		boolean ccc = false;
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		cameraCount = Camera.getNumberOfCameras();
		for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				ccc =  true;
			}
		}
		if(ccc){
			return true;
		}else{
			return false;
		}
	}
	public void arr(){
		dao = new CommentsDAO(this);
		comments = dao.findComment(agentid);
		if(null ==comments){
			dao.addComment(agentid, veryGood, good, general, bad);
			comments = dao.findComment(agentid);
		}
		Log.v(TAG, comments.getAgentid()+"getAgentid");
		Log.v(TAG, comments.getVeryGood()+"getVeryGood");
		Log.v(TAG, comments.getGood()+"getGood");
		Log.v(TAG, comments.getGeneral()+"getGeneral");
		Log.v(TAG, comments.getBad()+"getBad");
	}
	public String agentid(){
		return comments.getAgentid();
	}
	public int veryGood(){
		return comments.getVeryGood();
	}
	public int good(){
		return comments.getGood();
	}
	public int general(){
		return comments.getGeneral();
	}
	public int bad(){
		return comments.getBad();
	}
}
