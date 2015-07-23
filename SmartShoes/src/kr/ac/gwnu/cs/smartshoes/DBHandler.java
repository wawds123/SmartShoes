package kr.ac.gwnu.cs.smartshoes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {

	private DBHelper helper;
	private SQLiteDatabase db;
	private Context context;
	private String TAB_NAME = "alarms";
	
	public DBHandler(Context context) {		//DML ����
		// TODO Auto-generated constructor stub
		this.context = context;
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();	//db�� open��
	}
	
	public static DBHandler open(Context context) throws SQLException{
		DBHandler handler = new DBHandler(context);
		return handler;
	}
	
	public void close()
	{
		helper.close();
	}
	
	//SQL�� �ۼ�
	//INSERT
	public long insert(String time, String repeat, String content, String review)
	{
		ContentValues values = new ContentValues();
		values.put("time", time);
		values.put("repeat", repeat);
		values.put("content", content);
		values.put("review", review);
		
		long result = db.insert(TAB_NAME, null, values);
		return result;
	}
	
	//SELECT
	public Cursor selectAll()
	{
		Cursor cursor = db.rawQuery("select * from alarms",  null);
		return cursor;
	}

}
