package com.example.workoholic;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity
{
	
	private long splashDelay = 5000;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        TimerTask task = new TimerTask(){
        	@Override
        	public void run(){
        		finish();
        		
        		Intent mainIntent = new Intent()
        		.setClass(SplashActivity.this, ViewPagerIndicatorActivity.class);
        		startActivity(mainIntent);
        		
        	}
        };
        Timer timer = new Timer();
        timer.schedule(task, splashDelay);
	}
}
