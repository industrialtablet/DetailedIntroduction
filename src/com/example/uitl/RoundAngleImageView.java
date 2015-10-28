package com.example.uitl;

import android.content.Context;  
import android.graphics.Bitmap;  
import android.graphics.Bitmap.Config;  
import android.graphics.Canvas;  
import android.graphics.Paint;  
import android.graphics.Path;  
import android.graphics.PorterDuff;  
import android.graphics.PorterDuffXfermode;  
import android.graphics.RectF;  
import android.util.AttributeSet;  
import android.widget.ImageView;  
   
public class RoundAngleImageView extends ImageView {  
    private int roundWidth = 15;  
    private int roundHeight = 15;  
   
    public RoundAngleImageView(Context context) {  
        super(context);  
        init(context, null);  
    }  
   
    public RoundAngleImageView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        init(context, attrs);  
    }  
   
    public RoundAngleImageView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        init(context, attrs);  
    }  
   
    private void init(Context context, AttributeSet attrs) {  
        if (attrs != null) {  
//            TypedArray a = context.obtainStyledAttributes(attrs,  
//                    R.styleable.RoundAngleImageView);  
//            roundWidth = a.getDimensionPixelSize(  
//                    R.styleable.RoundAngleImageView_roundWidth, roundWidth);  
//            roundHeight = a.getDimensionPixelSize(  
//                    R.styleable.RoundAngleImageView_roundHeight, roundHeight);  
//            a.recycle();  
        } else {  
            float density = context.getResources().getDisplayMetrics().density;  
            roundWidth = (int)(roundWidth * density);  
            roundHeight = (int)(roundHeight * density);  
        }  
    }  
   
    /** ��дdraw() */ 
    @Override 
    public void draw(Canvas canvas) {  
           
        //ʵ����һ����ImageViewһ����С��bitmap  
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),  
                Config.ARGB_8888);  
           
        //ʵ����һ��canvas�����canvas��Ӧ���ڴ�Ϊ�����bitmap  
        Canvas canvas2 = new Canvas(bitmap);  
        if (bitmap.isRecycled()) {  
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(),  
                    Config.ARGB_8888);  
            canvas2 = new Canvas(bitmap);  
        }  
           
        //��imageView�Լ����Ƶ�canvas2�ϣ��������bitmap��������imageView  
        super.draw(canvas2);  
           
        //����canvas��һ��Բ�Ǿ��Σ�������޸�bitmap������  
        drawRoundAngle(canvas2);  
           
        //���ü��õ�bitmap���Ƶ�ϵͳ��ǰcanvas�ϣ������ü��õ�imageview������ʾ����Ļ��  
        Paint paint = new Paint();  
        paint.setXfermode(null);  
        canvas.drawBitmap(bitmap, 0, 0, paint);  
        bitmap.recycle();  
    }  
   
    public void setRoundWidth(int roundWidth, int roundHeight) {  
        this.roundWidth = roundWidth;  
        this.roundHeight = roundHeight;  
    }  
   
    private void drawRoundAngle(Canvas canvas)  
    {  
        Paint maskPaint = new Paint();  
        maskPaint.setAntiAlias(true);  
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));  
        Path maskPath = new Path();    
        maskPath.addRoundRect(new RectF(0.0F, 0.0F, getWidth(), getHeight()), roundWidth, roundHeight, Path.Direction.CW);  
           
        //�������������ģʽ���ǳ��ؼ�  
        maskPath.setFillType(Path.FillType.INVERSE_WINDING);  
        canvas.drawPath(maskPath, maskPaint);  
    }  
}