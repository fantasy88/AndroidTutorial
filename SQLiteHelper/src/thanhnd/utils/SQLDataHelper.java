package thanhnd.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLDataHelper extends SQLiteOpenHelper {
	private String DB_Name;
	private static int mNewVersion = 1;
	private Context myContext;
	private SQLiteDatabase myDataBase;
	private String DB_PATH;
	String myPath;

	public SQLDataHelper(Context context, String dbName) {
		super(context, dbName, null, mNewVersion);
		// TODO Auto-generated constructor stub
		this.myContext = context;
		this.DB_Name = dbName;
		DB_PATH="/data/data/"+context.getPackageName()+"/"+"databases/";
		myPath = DB_PATH + DB_Name;
	}
	
	public SQLiteDatabase getSQLiteDatabase()
	{
		return myDataBase;
	}

	public void createDB() throws IOException {
		boolean isExists = checkDB();
		if (!isExists) {
			this.getReadableDatabase();
			try {
				copyDatabase();
			} catch (IOException e) {
				Log.e("<<Error>>", e.getMessage());
			}
		}
	}
	
	public void deleteDBbyName(String dbName)
	{
		myContext.deleteDatabase(dbName);
	}

	private boolean checkDB() {
		SQLiteDatabase checkDB = null;
		try {
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			Log.e("<<SQL Error>>", e.getMessage());
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	private void copyDatabase() throws IOException {
		InputStream inputStream = myContext.getAssets().open(DB_Name);
		OutputStream myOutput = new FileOutputStream(myPath);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		inputStream.close();
	}

	public void openDB() throws SQLiteException {
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	public long InsertRescord(String table_name, ContentValues values) {
		myDataBase = this.getWritableDatabase();
		return myDataBase.insert(table_name, null, values);
	}

	public int DeleteRecord(String table_name, String column_name, String column_id) {
		myDataBase = this.getWritableDatabase();
		return myDataBase.delete(table_name, column_name + " = ?",
				new String[] { column_id });
	}
	
	public int DeleteAll(String table_name) {
		myDataBase = this.getWritableDatabase();
		return myDataBase.delete(table_name, null,null);
	}
	
	public int UpdateRecord(String table_name,ContentValues values,String column_name,String column_id)
	{
		myDataBase = this.getWritableDatabase();
		return myDataBase.update(table_name, values, column_name + " = ?",
	            new String[] { column_id });
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return myDataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
	}

	public Cursor rawQuery(String sql,String[] values)
	{
		Cursor cursor = getReadableDatabase().rawQuery(sql, values);
		return cursor;
	}
}
