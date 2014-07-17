/**
 * class files for all the DAO's
 */

package com.orawaves.tcal.android.dao;


import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.orawaves.tcal.andorid.dto.DTO;


public interface DAO
{	
    public boolean insert(DTO dtoObject, SQLiteDatabase dbObject);
    public boolean update(DTO dtoObject, SQLiteDatabase dbObject);
    public boolean delete(DTO dtoObject, SQLiteDatabase dbObject);
	
    public List<DTO> getRecords(SQLiteDatabase dbObject);
    public List<DTO> getRecordInfoByValue(String columnName, String columnValue, SQLiteDatabase dbObject);
	
}
