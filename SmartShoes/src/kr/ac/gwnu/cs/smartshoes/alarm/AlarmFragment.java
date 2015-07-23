package kr.ac.gwnu.cs.smartshoes.alarm;

import java.util.ArrayList;

import kr.ac.gwnu.cs.smartshoes.DBHandler;
import kr.ac.gwnu.cs.smartshoes.DBHelper;
import kr.ac.gwnu.cs.smartshoes.MainActivity;
import kr.ac.gwnu.cs.smartshoes.R;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class AlarmFragment extends Fragment implements OnClickListener{
	private View view;
	private DBHandler dbHandler;
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	private Cursor cursor;
	private ListView alarm_list_view;
	private AlarmListAdapter adapter;
	private ArrayList<Alarm> alarm_list;
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		getActivity().findViewById(R.id.title_back_btn).setVisibility(View.VISIBLE);
		getActivity().findViewById(R.id.title_alarm_add_btn).setVisibility(View.VISIBLE);
		getActivity().findViewById(R.id.title_alarm_save_btn).setVisibility(View.INVISIBLE);
		getActivity().findViewById(R.id.title_back_btn).setOnClickListener(this);
		getActivity().findViewById(R.id.title_alarm_add_btn).setOnClickListener(this);
		dbHelper = new DBHelper(getActivity());
		db = dbHelper.getWritableDatabase();
		db.execSQL("insert into alarms (TIME, REPEAT, CONTENT, REVIEW) values('4:13','월,화,수,목,금','알람',1);");
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		view = inflater.inflate(R.layout.fragment_alarm, null);
		
		initControls();

		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getActivity().findViewById(R.id.title_back_btn).setVisibility(View.VISIBLE);
		getActivity().findViewById(R.id.title_alarm_add_btn).setVisibility(View.VISIBLE);
		getActivity().findViewById(R.id.title_alarm_save_btn).setVisibility(View.INVISIBLE);
		getActivity().findViewById(R.id.title_back_btn).setOnClickListener(this);
		getActivity().findViewById(R.id.title_alarm_add_btn).setOnClickListener(this);
	}

	private void initControls()
	{
		alarm_list_view = (ListView)view.findViewById(R.id.alarm_list);
		dbHandler = DBHandler.open(getActivity());
		try
		{
			cursor = dbHandler.selectAll();
			while(cursor.moveToNext())
			{
				int ID = cursor.getInt(0);
				String TIME = cursor.getString(1);
				String REPEAT = cursor.getString(2);
				String CONTENT = cursor.getString(3);
				int REVIEW = cursor.getInt(4);
				alarm_list = new ArrayList<Alarm>();
				alarm_list.add(new Alarm(ID, TIME, REPEAT, CONTENT, REVIEW));
			}
			cursor.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			invalidate();
		}
	}
	
	private void invalidate()
	{
		adapter = new AlarmListAdapter(getActivity(), R.layout.alarm_list_item, alarm_list);
		alarm_list_view.setAdapter(adapter);
	}
	
	private void FragmentChange(final Fragment fragment)
	{
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.start_enter, R.anim.start_exit, R.anim.end_enter, R.anim.end_exit);
		ft.replace(R.id.activity_main_fragment, fragment,"neviFragment").addToBackStack(null);
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.title_back_btn:
			getFragmentManager().popBackStack();
			break;
		case R.id.title_alarm_add_btn:
			FragmentChange(new AlarmEditFragment());
			break;
		}
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		getActivity().findViewById(R.id.title_back_btn).setVisibility(View.INVISIBLE);
		getActivity().findViewById(R.id.title_alarm_add_btn).setVisibility(View.INVISIBLE);
	}
	
}
