package com.example.workoholic;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WorkingSessionsIO 
{
  // Database fields
  private SQLiteDatabase database;
  private SQLiteHandler dbHelper;
  @SuppressLint("SimpleDateFormat") SimpleDateFormat SDFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
  
  private String[] allColumns = { 
		  SQLiteHandler.COL_ID,
		  SQLiteHandler.COL_BEGIN_TIME,
		  SQLiteHandler.COL_END_TIME,
		  SQLiteHandler.COL_DATE };
  
  public WorkingSessionsIO(Context context) {
    dbHelper = new SQLiteHandler(context);
  }
  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }
  public void close() {
    dbHelper.close();
  }
  public Long AppendANewSession(Date beginTime)
  {
	  ContentValues values = new ContentValues();
	  values.put(SQLiteHandler.COL_BEGIN_TIME,SDFormat.format(beginTime));
	  Long insertId = database.insert(SQLiteHandler.TABLE_NAME, null, values);
	  return insertId;
  }
  public Boolean EndASession(Long sessionId)
  {
	  ContentValues values = new ContentValues();
	  values.put(
		SQLiteHandler.COL_END_TIME,
		SDFormat.format(Calendar.getInstance().getTime())
	  );
	  //database.update(table, values, whereClause, whereArgs)
	  database.update(SQLiteHandler.TABLE_NAME,values,SQLiteHandler.COL_ID +"= "+sessionId , null);
	  return true;
	  //Long insertId = database.insert(SQLiteHandler.TABLE_NAME, null, values);
	  //return insertId;
  }
  public void updateSession(String WorkingSession) {
	//database.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
    ContentValues values = new ContentValues();
    values.put(SQLiteHandler.COL_BEGIN_TIME,"23-07-2013 24:00:00");
    values.put(SQLiteHandler.COL_END_TIME,"23-07-2013 24:20:00");
    values.put(SQLiteHandler.COL_DATE,"23-07-2013");
    database.insert(SQLiteHandler.TABLE_NAME, null, values);
    /*
    values.put(SQLiteHandler.COLUMN_WorkingSession, WorkingSession);
    long insertId = database.insert(SQLiteHandler.TABLE_WorkingSessionS, null,values);
    Cursor cursor = database.query(
    	SQLiteHandler.TABLE_WorkingSessionS,
        allColumns, SQLiteHandler.COL_ID + " = " + insertId, null,
        null, null, null
    );
    cursor.moveToFirst();
    WorkingSession newWorkingSession = cursorToWorkingSession(cursor);
    cursor.close();
    return newWorkingSession;
*/
  }
  public Cursor getAllSessions(){
	  Cursor cursor = database.query(SQLiteHandler.TABLE_NAME,allColumns, null, null, null, null, null);
	  cursor.moveToFirst();
	  return cursor;
  }
  
  public List<WorkingSession> getAllWorkingSessions() 
  {
    List<WorkingSession> sessionsList = new ArrayList<WorkingSession>();
    Cursor cursor = database.query(SQLiteHandler.TABLE_NAME,allColumns, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      WorkingSession workingSession = cursorToSession(cursor);
      sessionsList.add(workingSession);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return sessionsList;
  }
  private WorkingSession cursorToSession(Cursor cursor) {
    WorkingSession WorkingSession = new WorkingSession();
    WorkingSession.setId(cursor.getLong(0));
    WorkingSession.setBeginTime(cursor.getString(1));
    WorkingSession.setEndTime(cursor.getString(2));
    return WorkingSession;
  }
  
}