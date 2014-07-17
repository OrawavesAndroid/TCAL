package com.orawaves.tcal.android.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.orawaves.tcal.andorid.dto.DTO;
import com.orawaves.tcal.andorid.dto.TimelineDTO;

public class TimelineDAO implements DAO{

	 private final String TAG = "TimelineDAO";
	    private static TimelineDAO timelineDAO;
	    /**
	     * User DAO instance is created
	     * @return DAO
	     */
	    public static TimelineDAO getInstance()
	    {
	        if (timelineDAO == null)
	        {
	        	timelineDAO = new TimelineDAO();
	        }

	        return timelineDAO;
	    }
	
	@Override
	public boolean insert(DTO dtoObject, SQLiteDatabase dbObject) {
		 try
	        {
			     TimelineDTO timelineDTO = (TimelineDTO) dtoObject;
	            SQLiteStatement stmt = dbObject.compileStatement("insert into timeline (dateandtime,ctype,content,mshare,mshareemail,oshare,oshareemail)values(?,?,?,?,?,?,?)");
				
	            stmt.bindString(1, timelineDTO.getDateAndTime());
	            stmt.bindString(2, timelineDTO.getCtype());
	            stmt.bindString(3, timelineDTO.getContent());
	            stmt.bindString(4, timelineDTO.getmShare());
	            stmt.bindString(5, timelineDTO.getmShareEmail());
	            stmt.bindString(6, timelineDTO.getoShare());
	            stmt.bindString(7, timelineDTO.getoShareEmail());
				
	            return stmt.executeInsert() > 0 ? true : false;
	        }
	        catch (SQLException e)
	        {
	            Log.e(TAG + "insert()", e.getMessage());
	        }
	        finally
	        {
	            dbObject.close();
	        }
	        return false;
	}
	
	
	public boolean updateWithId(int id, DTO dtoObject,SQLiteDatabase dbObject)
	{
		  try
	        {
	            TimelineDTO timelineDTO = (TimelineDTO) dtoObject;
	            ContentValues cValues = new ContentValues();
	            cValues.put("dateandtime", timelineDTO.getDateAndTime());	
	            cValues.put("ctype", timelineDTO.getCtype());	
	            cValues.put("content", timelineDTO.getContent());	
	            cValues.put("mshare", timelineDTO.getmShare());	
	            cValues.put("mshareemail", timelineDTO.getmShareEmail());	
	            cValues.put("oshare", timelineDTO.getoShare());
	            cValues.put("oshareemail", timelineDTO.getoShareEmail());	
	            dbObject.update("timeline", cValues, "id=" +id, null);
				
	            return true;
	        }
	        catch (SQLException e)
	        {
	            Log.e(TAG + "updatetimeline object error", e.getMessage());
	            e.printStackTrace();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	            dbObject.close();
	        }
	        return false;
	}

	@Override
	public boolean update(DTO dtoObject, SQLiteDatabase dbObject) {
		  try
	        {
	            TimelineDTO timelineDTO = (TimelineDTO) dtoObject;
	            ContentValues cValues = new ContentValues();
	            cValues.put("dateandtime", timelineDTO.getDateAndTime());	
	            cValues.put("ctype", timelineDTO.getCtype());	
	            cValues.put("content", timelineDTO.getContent());	
	            cValues.put("mshare", timelineDTO.getmShare());	
	            cValues.put("mshareemail", timelineDTO.getmShareEmail());	
	            cValues.put("oshare", timelineDTO.getoShare());
	            cValues.put("oshareemail", timelineDTO.getoShareEmail());	
	            dbObject.update("timeline", cValues, "id=" + timelineDTO.getId(), null);
				
	            return true;
	        }
	        catch (SQLException e)
	        {
	            Log.e(TAG + "updatetimeline object error", e.getMessage());
	            e.printStackTrace();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	            dbObject.close();
	        }
	        return false;
	}

	@Override
	public boolean delete(DTO dtoObject, SQLiteDatabase dbObject) {
		 try
	        {
			  TimelineDTO timelineDTO = (TimelineDTO) dtoObject;
			    int id = timelineDTO.getId();
	            dbObject.compileStatement("DELETE FROM timeline where id="+id).execute();
	            return true;
	        }
	        catch (Exception e)
	        {
	            Log.e(TAG + "deleteRecord() error", e.getMessage());
	        }
	        return false; 
	}
	
	
	public boolean deleteOldTimeline(String currentTime ,SQLiteDatabase dbObject)
	{
		 try
	        { 
	            dbObject.compileStatement("delete from timeline where dateandtime < '"+currentTime+"'").execute();
	            return true;
	        }
	        catch (Exception e)
	        {
	            Log.e(TAG + "deleteRecord() error", e.getMessage());
	        }
	        return false; 
	}

	@Override
	public List<DTO> getRecords(SQLiteDatabase dbObject) {
		 List<DTO> timelineInfo = new ArrayList<DTO>();
			
	        Cursor cursor = null;
			
	        try
	        {
	            cursor = dbObject.rawQuery("SELECT * FROM timeline", null);
				
	            if (cursor.moveToFirst())
	            {
	                do
	                {
	                    TimelineDTO timeDTO = new TimelineDTO();
	                    timeDTO.setId(cursor.getInt(0));
	                    timeDTO.setDateAndTime(cursor.getString(1));
	                    timeDTO.setCtype(cursor.getString(2));
	                    timeDTO.setContent(cursor.getString(3));
	                    timeDTO.setmShare(cursor.getString(4));
	                    timeDTO.setmShareEmail(cursor.getString(5));
	                    timeDTO.setoShare(cursor.getString(6));
	                    timeDTO.setoShareEmail(cursor.getString(7));
						
	                    timelineInfo.add(timeDTO);
	                }
	                while (cursor.moveToNext());
	            }
	        }
	        catch (Exception e)
	        {
	            Log.e(TAG + "getRecords() error", e.getMessage());
	        }
	        finally
	        {

	            /** Closing the database connections */
	            if (cursor != null && !cursor.isClosed())
	            {
	                cursor.close();
	            }
	            dbObject.close();

	        }

	        return timelineInfo;
	}
	
	public List<DTO> getRecordBetwenDateTime(String currentTime, String featureTime,SQLiteDatabase dbObject)
	{
		 List<DTO> timelineInfo = new ArrayList<DTO>();
			
	        Cursor cursor = null;
			
	        try
	        {
	            cursor = dbObject.rawQuery("SELECT * FROM timeline WHERE dateandtime BETWEEN '"+currentTime+"' AND '"+featureTime+"'",
	                    null);
				
	            if (cursor.moveToFirst())
	            {
	                do
	                {
	                    TimelineDTO timeDTO = new TimelineDTO();
	                    timeDTO.setId(cursor.getInt(0));
	                    timeDTO.setDateAndTime(cursor.getString(1));
	                    timeDTO.setCtype(cursor.getString(2));
	                    timeDTO.setContent(cursor.getString(3));
	                    timeDTO.setmShare(cursor.getString(4));
	                    timeDTO.setmShareEmail(cursor.getString(5));
	                    timeDTO.setoShare(cursor.getString(6));
	                    timeDTO.setoShareEmail(cursor.getString(7));
						
	                    timelineInfo.add(timeDTO);
	                }
	                while (cursor.moveToNext());
	            }
	        }
	        catch (Exception e)
	        {
	            Log.e(TAG + "getRecords() error", e.getMessage());
	        }
	        finally
	        {

	            /** Closing the database connections */
	            if (cursor != null && !cursor.isClosed())
	            {
	                cursor.close();
	            }
	            dbObject.close();

	        }

	        return timelineInfo;
	}

	@Override
	public List<DTO> getRecordInfoByValue(String columnName,
			String columnValue, SQLiteDatabase dbObject) {
		return null;
	}

}
