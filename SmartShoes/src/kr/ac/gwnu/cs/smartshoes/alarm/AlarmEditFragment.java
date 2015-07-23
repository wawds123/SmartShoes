package kr.ac.gwnu.cs.smartshoes.alarm;

import kr.ac.gwnu.cs.smartshoes.DBHelper;
import kr.ac.gwnu.cs.smartshoes.MainActivity;
import kr.ac.gwnu.cs.smartshoes.R;
import kr.ac.gwnu.cs.smartshoes.SET;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class AlarmEditFragment extends Fragment implements OnClickListener, OnTimeChangedListener, OnCheckedChangeListener
{
	private View view;
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	private Alarm alarm;
	private TimePicker timePicker;
	private RelativeLayout alarm_edit_repeat_layout, alarm_edit_content_layout;
	private Switch alarm_edit_switch;
	private String hour;
	private String minute;
	private String content = "";
	private AlertDialog.Builder aDialog;
	private EditText et;
	private int switch_flag=0;
	private SET set = new SET();
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		getActivity().findViewById(R.id.title_alarm_add_btn).setVisibility(View.INVISIBLE);
		getActivity().findViewById(R.id.title_alarm_save_btn).setVisibility(View.VISIBLE);
		getActivity().findViewById(R.id.title_back_btn).setOnClickListener(this);
		getActivity().findViewById(R.id.title_alarm_save_btn).setOnClickListener(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		view = inflater.inflate(R.layout.fragment_alarm_edit, null);
		
		initControls();
		return view;
	}

	private void initControls()
	{
		timePicker = (TimePicker)view.findViewById(R.id.alarm_edit_timePicker);
		timePicker.setOnClickListener(this);
		timePicker.setOnTimeChangedListener(this);
		
		alarm_edit_repeat_layout = (RelativeLayout)view.findViewById(R.id.alarm_edit_repeat_layout);
		alarm_edit_repeat_layout.setOnClickListener(this);
		
		alarm_edit_content_layout = (RelativeLayout)view.findViewById(R.id.alarm_edit_content_layout);
		alarm_edit_content_layout.setOnClickListener(this);
		
		alarm_edit_switch = (Switch)view.findViewById(R.id.alarm_edit_switch);
		alarm_edit_switch.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		getActivity().findViewById(R.id.title_back_btn).setVisibility(View.INVISIBLE);
		getActivity().findViewById(R.id.title_alarm_save_btn).setVisibility(View.INVISIBLE);
		getActivity().findViewById(R.id.title_alarm_add_btn).setVisibility(View.VISIBLE);
	}

	private void FragmentChange(final Fragment fragment, final String tag)
	{
		// TODO Auto-generated method stub
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.start_enter, R.anim.start_exit, R.anim.end_enter, R.anim.end_exit);
		ft.replace(R.id.activity_main_fragment, fragment, tag).addToBackStack(null);
		ft.commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
		switch(v.getId())
		{
		case R.id.title_back_btn:
			getFragmentManager().popBackStack();
			break;
			
		case R.id.title_alarm_save_btn:
			dbHelper = new DBHelper(getActivity());
			alarm = new Alarm();
			
			Log.d("AlarmEditFragment_DEBUG","hour : "+hour + ", minute : " + minute);
			Log.d("AlarmEditFragment_DEBUG","content : "+content);
			Log.d("AlarmEditFragment_DEBUG","switch_flag : "+switch_flag);
			alarm.setTIME(hour+":"+minute);
			alarm.setREPEAT(new SET().REPEATS);
			alarm.setCONTENT(content);
			alarm.setREVIEW(switch_flag);
			db = dbHelper.getWritableDatabase();

			db.execSQL("insert into alarms (TIME, REPEAT, CONTENT, REVIEW) values('"+
						alarm.getTIME()+"','"+
						alarm.getREPEAT()+"','"+
						alarm.getCONTENT()+"',"+
						alarm.getREVIEW()+");");
			//db.execSQL("insert into alarms (TIME, REPEAT, CONTENT, REVIEW) values('4:13','월,화,수,목,금','알람',1);");
			getFragmentManager().popBackStack();
			break;
			
		case R.id.alarm_edit_repeat_layout:
			FragmentChange(new AlarmEditRepeatFragment(), "alarmEditRepeatFragment");
			break;
			
		case R.id.alarm_edit_content_layout:
			final View content_layout = inflater.inflate(R.layout.alarm_edit_content_dialog_layout, (ViewGroup)getActivity().findViewById(R.id.alarm_edit_root_layout));
			et = (EditText)content_layout.findViewById(R.id.alarm_edit_content_dialog_edittext);

			AlertDialog.Builder content_Dialog = new AlertDialog.Builder(((MainActivity)getActivity()));
			
			content_Dialog.setTitle("내용입력");
			content_Dialog.setView(content_layout);
			
			content_Dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					content = et.getText().toString();
					((TextView)view.findViewById(R.id.alarm_textview_content)).setText(content);
				}
			});
			content_Dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			
			AlertDialog content_ad = content_Dialog.show();
			content_ad.show();
			break;
		}
	}

	
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) 
	{
		this.hour = Integer.toString(hourOfDay);
		this.minute = Integer.toString(minute);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(isChecked = true)
		{
			switch_flag = 1;
		}
		else
		{
			switch_flag = 0;
		}
		
	}
}
