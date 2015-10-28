package com.example.adapter;  

import java.io.File;
import java.util.ArrayList;  
import java.util.List;  
import java.util.Map;  
import com.example.detailedintroduction.R;
import android.annotation.SuppressLint;
import android.content.Context;  
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.ImageView;  
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;  
@SuppressWarnings("rawtypes")
public class AppAdapter extends BaseAdapter {  
	
	private List<Map> mList;  
    private Context mContext;  
    public static final int APP_PAGE_SIZE = 8;  
    public AppAdapter(Context context, List<Map> list, int page) {  
        mContext = context;  
          
        mList = new ArrayList<Map>();  
        int i = page * APP_PAGE_SIZE;  
        int iEnd = i+APP_PAGE_SIZE;  
        while ((i<list.size()) && (i<iEnd)) {  
            mList.add(list.get(i));  
            i++;  
        }  
    }  
    public int getCount() {  
        return mList.size();  
    }  
  
    public Object getItem(int position) {  
        return mList.get(position);  
    }  
  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @SuppressLint("InflateParams") public View getView(int position, View convertView, ViewGroup parent) {  
        Map appInfo = mList.get(position);  
        AppItem appItem;  
        if (convertView == null) {  
            View v = LayoutInflater.from(mContext).inflate(R.layout.member_list, null);  
            appItem = new AppItem();
            
			appItem.alllayout = (RelativeLayout)v.findViewById(R.id.alllayout); 
			appItem.tv_name = (TextView)v.findViewById(R.id.tv_name); 
			appItem.tv_position = (TextView)v.findViewById(R.id.tv_position); 
			appItem.iv_photo = (ImageView)v.findViewById(R.id.imgdetail); 
			appItem.star[0] = (ImageView)v.findViewById(R.id.star1); 
			appItem.star[1] = (ImageView)v.findViewById(R.id.star2); 
			appItem.star[2] = (ImageView)v.findViewById(R.id.star3); 
			appItem.star[3] = (ImageView)v.findViewById(R.id.star4); 
			appItem.star[4] = (ImageView)v.findViewById(R.id.star5); 
			
			v.setTag(appItem);  
			convertView = v;  
		} else {  
			appItem = (AppItem)convertView.getTag();  
		}  
		appItem.tv_name.setText(appInfo.get("name").toString());  
		appItem.tv_position.setText("¹¤ºÅ£º"+appInfo.get("agentid").toString());  
		
		String pathss = appInfo.get("topfile").toString();
		File file=new File(pathss);
		Drawable img = BitmapDrawable.createFromPath(file.getAbsolutePath());
		appItem.iv_photo.setScaleType(ScaleType.CENTER_CROP);
		appItem.iv_photo.setImageDrawable(img);
		
		int NO = (Integer) appInfo.get("number");
		if(NO%2 ==1){
			for (int i = 0; i < (NO/2); i++) {
				appItem.star[i].setBackgroundResource(R.drawable.star);
			}
			appItem.star[NO/2].setBackgroundResource(R.drawable.half_star);
		}else{
			for (int i = 0; i < (NO/2); i++) {
				appItem.star[i].setBackgroundResource(R.drawable.star);
			}
		}
		
		return convertView;  
	}  
	class AppItem {  
		RelativeLayout alllayout;
		ImageView iv_photo;  
		TextView tv_name;  
		TextView tv_position;
		ImageView star[] = new ImageView[5];
	}  
}  
