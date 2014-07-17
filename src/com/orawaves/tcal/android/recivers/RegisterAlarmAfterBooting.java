package com.orawaves.tcal.android.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orawaves.tcal.android.alarm.AlarmMgnr;
 


public class RegisterAlarmAfterBooting extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent i)
	{
		System.out.println("****************** RegisterAlarmAfterBooting Receiver Called ***********************");
		
	 
			AlarmMgnr alarm	= new AlarmMgnr(context);
			alarm.registerAlarm();
		 
	}
}
