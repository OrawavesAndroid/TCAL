package com.orawaves.tcal.android.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHandler extends SQLiteOpenHelper
{
    private final String TAG = "DBHandler";
    private static final String DATABASE_NAME = "timeline.db";
    private static final int DATABASE_VERSION = 1;
    private static DBHandler dbHandler;
	
    /**
     * Instance of the database handler
     * @param context :Interface to global information about an application environment. 
     * @return
     */
    public static synchronized DBHandler getInstance(Context context)
    {
        if (dbHandler == null)
        {
            dbHandler = new DBHandler(context);
        }
        return dbHandler;
    }

    /**
     * Gets the Database Object
     * @param isWrtitable :whether readable or writable.
     * @return
     */
    public SQLiteDatabase getDBObject(int isWrtitable)
    {
        return (isWrtitable == 1) ? this.getWritableDatabase() : this.getReadableDatabase();
    }

    /**
     * Constructor
     * @param context :Interface to global information about an application environment. 
     */
    private DBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * Called when the database is created for the first time. This is where the creation of tables 
     * and the initial population of the tables should happen.
     * @param db : The database.  
     */
    public void onCreate(SQLiteDatabase db)
    {
 
    	//db.execSQL("CREATE TABLE PRODUCTGROUP_INFO(productGroupId INTEGER PRIMARY KEY,productGroupName TEXT)");

    	db.execSQL("CREATE TABLE USER_INFO (userId TEXT PRIMARY KEY,userName TEXT,password TEXT,ActiveStatus Text,mobilenumber TEXT NOT NULL)");
    	db.execSQL("create table timeline(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,dateandtime datetime,ctype TEXT,content TEXT,mshare Text,mshareemail TEXT,oshare text,oshareemail text)"); 

        Log.d(TAG, "Tables got created");

    }

    /**
     * Called when the database needs to be upgraded. 
     * @param db : The database. 
     * @param oldVersion : The old database version. 
     * @param newVersion : The new database version.  
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.d(TAG, "PS:: onUpgrade called oldVersion\t" + oldVersion + "   newVersion\t" + newVersion);
    }

}
