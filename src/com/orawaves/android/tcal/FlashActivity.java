package com.orawaves.android.tcal;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.Window;

import com.orawaves.tcal.R;
import com.orawaves.tcal.andorid.dto.DTO;
import com.orawaves.tcal.android.activites.MenuActivity;
import com.orawaves.tcal.android.alarm.AlarmMgnr;
import com.orawaves.tcal.android.dao.TimelineDAO;
import com.orawaves.tcal.android.database.DBHandler;
import com.orawaves.tcal.android.util.Constants;

public class FlashActivity extends Activity {
		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.flash);
	
		/**setup the UI */
		creatingSplashScreen();
		
	}
	
	/**
     * setting the time for flash screen
     */
    private void creatingSplashScreen()
    {
        new CountDownTimer(2000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
            }
            public void onFinish()
            {
            	setupUI();
            }

        }.start();
    }
    
    /**
     * setup the GUI
     */
    private void setupUI(){
    	 
    	 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(FlashActivity.this);
    	 boolean appIsInitialized = prefs.getBoolean(Constants.KEY_APP_IS_INITIALIZED, false);
    	  
    	  if (!appIsInitialized) {
		    
    		  SharedPreferences prefss = PreferenceManager.getDefaultSharedPreferences(FlashActivity.this);
			  Editor editor = prefss.edit();
			  editor.putBoolean(Constants.KEY_APP_IS_INITIALIZED, true);
			  editor.commit();

			  //Start the AlarmManager 
	    	  AlarmMgnr alarmMgnr = new AlarmMgnr(FlashActivity.this);
	          alarmMgnr.registerAlarm();
		  }
    	  
    	// Calling the menu screen
    	Intent menuIntent  = new Intent(this, MenuActivity.class);
      	startActivity(menuIntent);
      	finish(); 
    	
	}
}
