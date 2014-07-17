package com.orawaves.tcal.android.alarm;


/**
 * Register the alarm for feature events
 */


import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.orawaves.tcal.android.recivers.AlarmReciver;


public class AlarmMgnr
{
    private Intent intent;
    Context context;
    private static AlarmManager aMgnr;
    private static PendingIntent sender;
	
    public AlarmMgnr(Context context)
    {
        this.context = context;
    }
	
    public void registerAlarm()
    {
        Calendar cal = Calendar.getInstance();
	    	
        int hour = cal.get(Calendar.HOUR);
	    	
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
	    	
        System.out.println("AlarmMgnr.registerAlarm()");
	    
        intent = new Intent(context, AlarmReciver.class);
	    
        sender = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        aMgnr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
       
        // aMgnr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, sender);
        aMgnr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60000L, sender);
    }
	
    public void unregisterAlarm()
    {
        aMgnr.cancel(sender);
    }

}
