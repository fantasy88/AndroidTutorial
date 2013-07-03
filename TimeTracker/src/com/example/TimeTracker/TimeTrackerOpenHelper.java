package com.example.TimeTracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Fantasy
 * Date: 1/16/13
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeTrackerOpenHelper extends SQLiteOpenHelper {

    private static String db_name = "timetracker.db";
    private static String table_name = "MyDB";
    private static int db_version = 4;
    private SQLiteDatabase database;

    public TimeTrackerOpenHelper(Context context) {
        super(context, db_name, null, db_version);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS MyDB (_id INTEGER PRIMARY KEY AUTOINCREMENT,timer VARCHAR,notes VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To change body of implemented methods use File | Settings | File Templates.
        db.execSQL("drop table if exists " + table_name);
        onCreate(db);
    }

    public void SaveTimeRecord(String time,String notes)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("timer",time);
        contentValues.put("notes",notes);

        database.insert(table_name,null,contentValues);
    }

    public Cursor getAllRecords()
    {
        return database.rawQuery("select * from "+table_name,null);
    }
}
