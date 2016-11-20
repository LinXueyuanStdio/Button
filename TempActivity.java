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
	 * ��ʾ����ʽ��һЩ����
	 */
	int oneNum;
	int twoNum;
	int[] twoNumContainer=new int[25];
	int maxNum=100;
	
	/**
	 * ��
	 */
	private Vibrator vibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //ȥ��������
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
		 * ��maxNum���ڵ�����д������twoNumContainer[]
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
         * �������𶯴�С����ͨ���ı�pattern���趨���������ʱ��̫�̣���Ч�����ܸо����� 
         * 
         */  
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);  
        long [] pattern = {600,400,200,400,200,500};   // ֹͣ ���� ֹͣ ����   
        vibrator.vibrate(pattern,3);           //�ظ����������pattern ���ֻ����һ�Σ�index��Ϊ-1   
	}
	
	public void vibrateOff(){
		vibrator.cancel();
	}
	
	
	
}
