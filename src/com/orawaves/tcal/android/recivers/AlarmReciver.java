package com.orawaves.tcal.android.recivers;

import java.util.List;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.orawaves.tcal.R;
import com.orawaves.tcal.andorid.dto.DTO;
import com.orawaves.tcal.android.activites.PostingTimelineNotification;
import com.orawaves.tcal.android.dao.TimelineDAO;
import com.orawaves.tcal.android.database.DBHandler;
import com.orawaves.tcal.android.util.AppUtil;

public class AlarmReciver extends BroadcastReceiver{

	private final String TAG = "AlarmReciver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "**************Alaram Reciver Called*************");

		String ctime = AppUtil.getCurrentDateTime();
		String fTime =  AppUtil.getNextHourDateTime();
		
		System.out.println("cTime="+ctime+"fTime="+fTime);
		
		List<DTO> dtos  = TimelineDAO.getInstance().getRecordBetwenDateTime(AppUtil.getCurrentDateTime(),
				                                         AppUtil.getNextHourDateTime(), 
				                                         DBHandler.getInstance(context).getWritableDatabase());
		
		
		if (dtos.size() >0) {

			 System.out.println("AlarmReciver.onReceive() Notification");
			 
			  NotificationCompat.Builder mBuilder =
				        new NotificationCompat.Builder(context)
				        .setSmallIcon(R.drawable.ticker)
				        .setContentTitle("Timeline App Alert")
				        .setContentText("Up comming pending posts");
			    mBuilder.setAutoCancel(true);
			    // Creates an explicit intent for an Activity in your app
				Intent resultIntent = new Intent(context, PostingTimelineNotification.class); 
				
				TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
				// Adds the back stack for the Intent (but not the Intent itself)
				stackBuilder.addParentStack(PostingTimelineNotification.class);
				// Adds the Intent that starts the Activity to the top of the stack
				stackBuilder.addNextIntent(resultIntent);
				
				PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(  0,   PendingIntent.FLAG_UPDATE_CURRENT );
				mBuilder.setContentIntent(resultPendingIntent);
				NotificationManager mNotificationManager =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				// mId allows you to update the notification later on.
				mNotificationManager.notify(11, mBuilder.build());
		}
		 
	}
	
}
	
