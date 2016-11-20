package com.example.floatwindow;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.example.floatwindow.widget.FlashButton;
import com.example.floatwindow.widget.RoundButton;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.widget.TextView;

public class TempActivity extends Activity 
{
	FlashButton flashButton;
	
	/**
	 * 显示计算式的一些变量
	 */
	int oneNum;
	int twoNum;
	int[] twoNumContainer=new int[25];
	int maxNum=100;
	
	/**
	 * 震动
	 */
	private Vibrator vibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去除标题栏
		setContentView(R.layout.temp);
		
		flashButton=(FlashButton) findViewById(R.id.flashButton);
		flashButton.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}
	
	@Override
	protected void onDestroy()
	{

		super.onDestroy();
	}
	
	private void init(){
		/**
		 * 将maxNum以内的素数写入数组twoNumContainer[]
		 */
		boolean isPrime=true;
		for (int i = 12,ii=0; i < maxNum; i++) {
			int sqrtI=(int) Math.sqrt(i);
			for (int j = 2; j <= sqrtI; j++) {
				if (i%j==0) {
					isPrime=false;
					break;
				}
			}
			if (isPrime) {
				twoNumContainer[ii]=i;
				ii++;
			}
			isPrime=true;
		}
		
	}
	
	private void reText(){
		oneNum=(int) (Math.random()*10);
		while (!(oneNum>=2&&oneNum<=9)) {
			oneNum=(int) (Math.random()*10);
		}
		twoNum=twoNumContainer[(int) (Math.random()*twoNumContainer.length)];
		while (twoNum==0) {
			twoNum=twoNumContainer[(int) (Math.random()*twoNumContainer.length)];
			
		}
	}
	
	public void vibrateOn(){
		/** 
         * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到 
         * 
         */  
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);  
        long [] pattern = {600,400,200,400,200,500};   // 停止 开启 停止 开启   
        vibrator.vibrate(pattern,3);           //重复两次上面的pattern 如果只想震动一次，index设为-1   
	}
	
	public void vibrateOff(){
		vibrator.cancel();
	}
	
	
	
}
