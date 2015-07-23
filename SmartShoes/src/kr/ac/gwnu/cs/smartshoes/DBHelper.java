package kr.ac.gwnu.cs.smartshoes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper
{
	public static String TAG = "DBHelper";
	public DBHelper(Context context)
	{ super(context, "SmartShoes.db", null, 1); }
	
	public void onCreate(SQLiteDatabase db)
	{
		String tag = TAG + "-onCreate()";
		
		try
		{
			db.execSQL(AlarmSQL());
		}
		catch(Exception ex)
		{ Log.d(tag, "ExceptionCurrent\n" + ex.toString()); }
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		String tag = TAG + "-onUpgrade()";
		
		try
		{
			db.execSQL("drop table if exists alarms;");
			
			onCreate(db);
		}
		catch(Exception ex)
		{ Log.d(tag, "Exception\n" + ex.toString()); }
	}
	
	private String AlarmSQL()
	{
		String sql = "create table alarms("
				     + "ID integer primary key autoincrement not null,"
				     + "TIME text not null, "
				     + "REPEAT text not null, "
				     + "CONTENT text not null, "
				     + "REVIEW integer not null);";
		return sql;
	}
	
	
}