package kr.ac.gwnu.cs.smartshoes.alarm;

import java.util.ArrayList;

import kr.ac.gwnu.cs.smartshoes.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
public class AlarmEditRepeatListAdapter extends ArrayAdapter<String>
{
	private ArrayList<String> list;
	private boolean[] isCheckedConfrim;
	
	public AlarmEditRepeatListAdapter(Context context, int textViewResourceId,
			ArrayList<String> arraylist) {
		super(context, textViewResourceId, arraylist);
		// TODO Auto-generated constructor stub
		this.list = arraylist;
		this.isCheckedConfrim = new boolean[list.size()];
	}
	
	public void setChecked(int position)
	{
		isCheckedConfrim[position] = !isCheckedConfrim[position];
	}
	
	public ArrayList<String> getChecked()
	{
		int tempSize = isCheckedConfrim.length;
		ArrayList<String> mArrayList = new ArrayList<String>();
		for(int i=0; i<tempSize; i++)
		{
			if(isCheckedConfrim[i])
				mArrayList.add(list.get(i));
		}
		return mArrayList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.alarm_repeat_list_item, null);
		
		((CheckedTextView)convertView.findViewById(R.id.alarm_repeat_list_checkedTextView)).setText(list.get(position));
		((CheckedTextView)convertView.findViewById(R.id.alarm_repeat_list_checkedTextView)).setClickable(false);
		((CheckedTextView)convertView.findViewById(R.id.alarm_repeat_list_checkedTextView)).setFocusable(false);
		((CheckedTextView)convertView.findViewById(R.id.alarm_repeat_list_checkedTextView)).setChecked(isCheckedConfrim[position]);
		
		return convertView;
	}

}
