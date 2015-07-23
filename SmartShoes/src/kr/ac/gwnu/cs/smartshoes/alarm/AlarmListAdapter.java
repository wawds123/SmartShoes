package kr.ac.gwnu.cs.smartshoes.alarm;

import java.util.ArrayList;

import kr.ac.gwnu.cs.smartshoes.R;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AlarmListAdapter extends ArrayAdapter<Alarm>{
	
	private ArrayList<Alarm> alarm_list;
	private Alarm alarm;
	private Context context;
	public AlarmListAdapter(Context context, int textViewResourceId, ArrayList<Alarm> arraylist) {
		super(context, textViewResourceId, arraylist);
		// TODO Auto-generated constructor stub
		this.alarm_list = arraylist;
		this.context = context;
	}
	
	public Alarm getAlarm(int index)
	{
		return alarm_list.get(index);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		alarm = alarm_list.get(position);
		if(convertView == null)
		{
			convertView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.alarm_list_item, null);
		}
		if(alarm != null)
		{
			Log.d("AlarmListAdapater_DEBUG", alarm_list.get(position).getTIME());
			Log.d("AlarmListAdapater_DEBUG", alarm_list.get(position).getCONTENT());
			Log.d("AlarmListAdapater_DEBUG", alarm_list.get(position).getREPEAT());
			((TextView)convertView.findViewById(R.id.alarm_list_time)).setText(alarm_list.get(position).getTIME());
			((TextView)convertView.findViewById(R.id.alarm_list_content)).setText(alarm_list.get(position).getCONTENT());
			((TextView)convertView.findViewById(R.id.alarm_list_repeat)).setText(alarm_list.get(position).getREPEAT());
		}
		return convertView;
	}

}
