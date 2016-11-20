package com.example.floatwindow.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class FlashButton extends SurfaceView implements Callback, Runnable
{
	public FlashButton(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
	}

	public FlashButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public FlashButton(Context context)
	{
		super(context);
		init();
	}
	
	/**
	 * 控件宽高
	 */
	int widthsize;
    int heightsize;
	
	/**
	 * 控制surfaceView
	 */
	private SurfaceHolder surfaceHolder;
	
	/**
	 * 画布
	 */
	Canvas canvas;
	
	/**
	 * 画笔
	 */
	private Paint paint;
	private Paint lighteningBottom;
	private Paint lighteningTop;
	private Paint lighteningMiddle;
	
	/**
	 * 线程死亡标识位
	 */
	private boolean flag;

	/**
	 * 前景填充颜色
	 */
	int foreColor;
	
	/**
	 * 边框颜色
	 */
	int backColor;
	
	/**
	 * 字体颜色
	 */
	int textColor;
	
	/**
	 * 按钮显示的文字
	 */
	String text;
	
	/**
	 * 字体宽度
	 */
	int textSize=30;
	
	/**
	 * 边框宽度
	 */
	int paintWidth=5;
	
	private boolean isTouchDown=false;
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		widthsize=getWidth();
		heightsize=getHeight();

		
		flag = true;
		
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		flag=false;
	}
	
	@Override
	public void run()
	{
		while(flag)
		{
			long start =System.currentTimeMillis();
			draw();
			long end = System.currentTimeMillis();
			try 
			{
				if(end-start<40)
				{
					Thread.sleep(40-(end-start));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if(isTouchDown==false){
				setRotateColor();
				draw();
				try 
				{
					if(end-start<40)
					{
						Thread.sleep(40-(end-start));
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			if (isTouchDown==false) {
				isTouchDown=true;
			}
			if (backColor==Color.argb(255, 255, 255, 255)) {
				setRotateColor();
			}
			break;
			
		case MotionEvent.ACTION_UP:
			setRotateColor();
			break;
			
		case MotionEvent.ACTION_MOVE:
			
			break;
			
		default:
			break;
		}
		return true;
	}
		
	public void init()
	{
		surfaceHolder=this.getHolder();
		surfaceHolder.addCallback(this);
		
		paint =new Paint();
		lighteningBottom=new Paint();
		lighteningMiddle=new Paint();
		lighteningTop=new Paint();
		
		lighteningBottom.setStrokeWidth(2);
		lighteningBottom.setColor(Color.rgb(255, 255, 255));
		lighteningMiddle.setStrokeWidth(4);
		lighteningMiddle.setColor(Color.WHITE);
		lighteningMiddle.setAlpha(200);
		lighteningTop.setStrokeWidth(6);
		lighteningTop.setColor(Color.YELLOW);
		lighteningTop.setAlpha(100);
		
		paintWidth=5;
		text="FlashButton";
		backColor=Color.argb(255, 255, 255, 255);
		foreColor=Color.argb(255, 0, 204, 153);
		textColor=Color.argb(255, 255, 255, 255);
		textSize=30;
		setFocusable(true);

	}
	
	public void draw()
	{
		Canvas canvas =surfaceHolder.lockCanvas();Log.e("3", "dfvd");
		this.canvas=canvas;
		try
		{
			reDraw(canvas);
		}
		catch (Exception e)
		{
			
		}
		finally
		{
			if (canvas!=null) 
			{
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public void reDraw(Canvas canvas){
		//边框
		paint.setColor(backColor);
		canvas.drawRoundRect(new RectF(0, 0, widthsize, heightsize), heightsize/2, heightsize/2, paint);
		//内部填充
		paint.setColor(foreColor);
		canvas.drawRoundRect(new RectF(paintWidth, paintWidth,widthsize-paintWidth, heightsize-paintWidth),
							(heightsize-paintWidth)/2, (heightsize-paintWidth)/2, paint);

		int aveMidP=((heightsize-paintWidth)/2<(widthsize-paintWidth)/2)?
					((heightsize-4*paintWidth)/2):
					((widthsize-4*paintWidth)/2);
		drawLightning(aveMidP/2, aveMidP/2, widthsize/2, heightsize/2,7, canvas);
		drawLightning(widthsize-aveMidP/2, aveMidP/2, widthsize/2, heightsize/2,7, canvas);
		drawLightning(aveMidP/2, heightsize-aveMidP/2, widthsize/2, heightsize/2,7, canvas);
		drawLightning(widthsize-aveMidP/2, heightsize-aveMidP/2, widthsize/2, heightsize/2,7, canvas);
		
		//文字
		paint.setColor(Color.rgb(255, 0, 0));
		paint.setTextSize(textSize);
		if (isChinese(text)>0) {
			Log.e("sdvsd", "111");
			canvas.drawText(text, 
					widthsize/2-isChinese(text)*textSize/2-((text.length()-isChinese(text))*textSize/4),
					heightsize/2+textSize/3, paint);
		}else {
			Log.e("sdvsd", "222");
			canvas.drawText(text, widthsize/2-text.length()*textSize/4,heightsize/2+textSize/3, paint);
		}
		
	}
	
	/**
	 * 在canvas上画一条从（x1,y1）到（x2,y2），震荡程度为paramInt的闪电
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param paramInt 震荡程度
	 * @param canvas
	 */
	public void drawLightning(float x1, float y1, float x2, float y2,
			int paramInt, Canvas canvas) {
		Random localRandom = new Random();
		if (paramInt < localRandom.nextInt(7)) {
			canvas.drawLine(x1, y1, x2, y2, lighteningBottom);
			canvas.drawLine(x1, y1, x2, y2, lighteningMiddle);
			canvas.drawLine(x1, y1, x2, y2, lighteningTop);
			return;
		}
		float x3 = 0, y3 = 0;
		if (localRandom.nextBoolean()) {
			x3 = (float) ((x2 + x1) / 2.0F + ((localRandom.nextInt(8) - 0.5D) * paramInt));
		} else {
			x3 = (float) ((x2 + x1) / 2.0F - ((localRandom.nextInt(8) - 0.5D) * paramInt));
		}
		if (localRandom.nextBoolean()) {
			y3 = (float) ((y2 + y1) / 2.0F + ((localRandom.nextInt(5) - 0.5D) * paramInt));
		} else {
			y3 = (float) ((y2 + y1) / 2.0F - ((localRandom.nextInt(5) - 0.5D) * paramInt));
		}
		drawLightning(x1, y1, x3, y3, paramInt / 2, canvas);
		drawLightning(x2, y2, x3, y3, paramInt / 2, canvas);
		

	}
	
	/**
	 * 反转按钮颜色
	 */
	public void setRotateColor()
	{
		int tempColor=this.foreColor;
		this.foreColor=this.backColor;
		this.backColor=tempColor;
		this.textColor=tempColor;
	}
	
	public void setForeBackColor(int foreColor,int backColor){
		this.foreColor=foreColor;
		this.backColor=backColor;
	}
	
	public void setForeBackTextColor(int foreColor,int backColor,int textColor)
	{
		this.setForeBackColor(foreColor, backColor);
		this.textColor=textColor;
	}
	
	public void setWidthsize(int widthsize)
	{
		this.widthsize = widthsize;
	}
	
	public void setHeightsize(int heightsize)
	{
		this.heightsize = heightsize;
	}
	
	public void setForeColor(int color){
		this.foreColor=color;
	}
	
	public void setBackColor(int color){
		this.backColor=color;
	}
	
	public void setTextColor(int color){
		this.textColor=color;
	}

	public void setText(String text){
		this.text=text;
	}
	
	public void setTextSize(int textSize)
	{
		this.textSize = textSize;
	}
	
	public void setPaintWidth(int width){
		this.paintWidth=width;
	}
	
	public void setIsTouchDown(boolean isTouchDown)
	{
		this.isTouchDown=isTouchDown;

	}
	
	public int getForeColor()
	{
		return foreColor;
	}
	
	public int getBackColor()
	{
		return backColor;
	}
	
	public int getTextColor()
	{
		return textColor;
	}
	
	public String getText()
	{
		return text;
	}
	
	public int getTextSize()
	{
		return textSize;
	}
	
	public int getHeightsize()
	{
		return heightsize;
	}
	
	public int getWidthsize()
	{
		return widthsize;
	}
	
	public int getPaintWidth()
	{
		return paintWidth;
	}
	
	public Canvas getCanvas()
	{
		return canvas;
	}

	/**
	 * 
	 * 判断一个字符是否是中文
	 */
	public static boolean isChinese(char c) {
	      return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
	}
	
	/**
	 * 判断一个字符串是否含有中文
	 * @param str
	 * @return
	 */
	public static int isChinese(String str) {
		int chineseCount=0;
	    if (str == null) 
	    	return chineseCount;
	    for (char c : str.toCharArray()) {
	        if (isChinese(c)) 
	        	chineseCount++;// 有一个中文字符就返回
	    }
	    return chineseCount;
	}
	
}


















