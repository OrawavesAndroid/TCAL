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
import com.orawaves.tcal.andorid.dto.UserDTO;


public class UserDAO implements DAO
{

    private final String TAG = "UserDAO";
    private static UserDAO userDAO;
	
    /**
     * User DAO instance is created
     * @return DAO
     */
    public static UserDAO getInstance()
    {
        if (userDAO == null)
        {
            userDAO = new UserDAO();
        }

        return userDAO;
    }
	
    /**
     * delete the Data
     */
    @Override
    public boolean delete(DTO dtoObject, SQLiteDatabase dbObject)
    {
        return false;
    }

    /**
     * Gets the record from the database based on the value passed
     * @param columnName : Database column name
     * @param columnValue : Column Value
     * @param dbObject : Exposes methods to manage a SQLite database Object 
     */
    @Override
    public List<DTO> getRecordInfoByValue(String columnName, String columnValue, SQLiteDatabase dbObject)
    {
        return null;
    }
	
    /**
     * Gets all the records from the database
     * @param dbObject : Exposes methods to manage a SQLite database Object  
     */
    @Override
    public List<DTO> getRecords(SQLiteDatabase dbObject)
    {
        List<DTO> userInfo = new ArrayList<DTO>();
		
        Cursor cursor = null;
		
        try
        {
            cursor = dbObject.rawQuery("SELECT USERID, USERNAME, PASSWORD, ActiveStatus, MOBILENUMBER FROM USER_INFO",
                    null);
			
            if (cursor.moveToFirst())
            {
                do
                {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setUserId(cursor.getString(0));
                    userDTO.setUserName(cursor.getString(1));
                    userDTO.setPassword(cursor.getString(2));
                    userDTO.setActiveStatus(cursor.getString(3));
                    userDTO.setMobileNumber(cursor.getString(4));				   
					
                    userInfo.add(userDTO);
                }
                while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
            Log.e(TAG + "getRecords()", e.getMessage());
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

        return userInfo;
    }

    /**
     * Inserts the data in the SQLite database
     * @param dbObject : Exposes methods to manage a SQLite database Object
     * @param dtoObject : DTO object is passed
     */
    @Override
    public boolean insert(DTO dtoObject, SQLiteDatabase dbObject)
    {
        try
        {
            UserDTO userDTO = (UserDTO) dtoObject;
            SQLiteStatement stmt = dbObject.compileStatement(
                    "INSERT INTO USER_INFO (userId, userName, password, activeStatus, mobileNumber) values (?,?,?,?,?)");
			
            stmt.bindString(1, userDTO.getUserId());
            stmt.bindString(2, userDTO.getUserName());
            stmt.bindString(3, userDTO.getPassword());
            stmt.bindString(4, userDTO.getActiveStatus());
            stmt.bindString(5, userDTO.getMobileNumber());
			
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

    /**
     * Updates the update ActiveStatus  in the SQLite
     * @param dtoObject : DTO object is passed
     * @param dbObject : Exposes methods to manage a SQLite database Object
     * @return boolean : True if data is updated
     */
    public boolean updateActiveStatus(DTO dtoObject, SQLiteDatabase dbObject)
    {
        try
        {
            UserDTO userDTO = (UserDTO) dtoObject;
			
            ContentValues cValues = new ContentValues();
            cValues.put("ActiveStatus", userDTO.getActiveStatus());			
			
            dbObject.update("USER_INFO", cValues, "userId=" + userDTO.getUserId(), null);
			
            return true;
        }
        catch (SQLException e)
        {
            Log.e(TAG + "updateActiveStatus()", e.getMessage());
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

    /**
     * Updates update user Password  table Data from SQLite
     * @param dtoObject : DTO object is passed
     * @param dbObject : Exposes methods to manage a SQLite database Object
     * @return boolean : True if data is to be updated
     */
    public boolean updatePassword(DTO dtoObject, SQLiteDatabase dbObject)
    {
        try
        {
            UserDTO userDTO = (UserDTO) dtoObject;
            ContentValues cValues = new ContentValues();		 			
            cValues.put("password", userDTO.getPassword());
			
            int passwoechanged = dbObject.update("USER_INFO", cValues, "userId=" + userDTO.getUserId(), null);
		
            System.out.println("password" + userDTO.getPassword());
		
            System.out.println("passwoechanged :" + passwoechanged);	
		
            return true;
        }
        catch (SQLException e)
        {
            Log.e(TAG + "updatePassword()", e.getMessage());
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

    /**
     * Updates userMobile Number table Data from SQLite
     * @param dtoObject : DTO object is passed
     * @param dbObject : Exposes methods to manage a SQLite database Object
     * @return boolean : True if data is to be updated
     */
    public boolean updateMobileNumber(DTO dtoObject, SQLiteDatabase dbObject)
    {
        try
        {

            UserDTO userDTO = (UserDTO) dtoObject;
			
            ContentValues cValues = new ContentValues();	
			
            cValues.put("mobileNumber", userDTO.getMobileNumber());
			
            dbObject.update("USER_INFO", cValues, "userId=" + userDTO.getUserId(), null);
			
            return true;
        }
        catch (SQLException e)
        {
            Log.e(TAG + "updateMobileNumber()", e.getMessage());
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
	 
    /**
     * Deletes all the table Data from SQLite
     * @param dtoObject : DTO object is passed
     * @param dbObject : Exposes methods to manage a SQLite database Object
     * @return boolean : True if data is to be deleted
     */
    public boolean deleteRecord(DTO dtoObject, SQLiteDatabase dbObject)
    {
        try
        {
            dbObject.compileStatement("DELETE FROM USER_INFO").execute();
            return true;
        }
        catch (Exception e)
        {
            Log.e(TAG + "deleteRecord()", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(DTO dtoObject, SQLiteDatabase dbObject) 
    {
        try
        {
            UserDTO userDTO = (UserDTO) dtoObject;
			
            ContentValues cValues = new ContentValues();
			
            cValues.put("userName", userDTO.getUserName());
            cValues.put("password", userDTO.getPassword());
			
            dbObject.update("USER_INFO", cValues, "userId=" + userDTO.getUserId(), null);
			
            return true;
        }
        catch (SQLException e)
        {
            Log.e(TAG + "update()", e.getMessage());
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
	
}
