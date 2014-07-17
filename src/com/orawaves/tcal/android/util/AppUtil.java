package com.orawaves.tcal.android.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class AppUtil {
	
	/**
	 *  Get device email address
	 * @param context
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> getNameEmailDetails(Context context) {
	    ArrayList<String> emlRecs = new ArrayList<String>();
	    HashSet<String> emlRecsHS = new HashSet<String>(); 
	    ContentResolver cr = context.getContentResolver();
	    String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID, 
	            ContactsContract.Contacts.DISPLAY_NAME,
	            ContactsContract.Contacts.PHOTO_ID,
	            ContactsContract.CommonDataKinds.Email.DATA, 
	            ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
	    String order = "CASE WHEN " 
	            + ContactsContract.Contacts.DISPLAY_NAME 
	            + " NOT LIKE '%@%' THEN 1 ELSE 2 END, " 
	            + ContactsContract.Contacts.DISPLAY_NAME 
	            + ", " 
	            + ContactsContract.CommonDataKinds.Email.DATA
	            + " COLLATE NOCASE";
	    String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
	    Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
	    if (cur.moveToFirst()) {
	        do {
	            // names comes in hand sometimes
	            String name = cur.getString(1);
	            String emlAddr = cur.getString(3);

	            // keep unique only
	            if (emlRecsHS.add(emlAddr.toLowerCase())) {
	                emlRecs.add(emlAddr);
	            }
	        } while (cur.moveToNext());
	    }

	    cur.close();
	    return emlRecs;
	}

	public static String getCurrentDateTime()
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");		
		String currentDateandTime = sdf.format(new Date(c.getTimeInMillis()));
		return currentDateandTime;
	}
 
	public static String getNextHourDateTime()
	{
		Calendar c  = Calendar.getInstance();
		c.add(Calendar.HOUR,1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");		
		String nextTime = sdf.format(new Date(c.getTimeInMillis()));
		return nextTime;
	}
	
	public static String getDateDbFormat(String olddate) throws Exception
	{
		SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = parseFormat.parse(olddate);
		return  displayFormat.format(date);
	}
	
	public static String getTimeDbFormat(String oldTime) throws Exception
	{
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
		Date date = parseFormat.parse(oldTime);
		 return displayFormat.format(date); 
	}
	
	
}
