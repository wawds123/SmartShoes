package kr.ac.gwnu.cs.smartshoes.menu;

import kr.ac.gwnu.cs.smartshoes.MainActivity;
import kr.ac.gwnu.cs.smartshoes.NetworkHelper;
import kr.ac.gwnu.cs.smartshoes.R;
import kr.ac.gwnu.cs.smartshoes.alarm.AlarmFragment;
import kr.ac.gwnu.cs.smartshoes.nevi.NeviFragment;
import kr.ac.gwnu.cs.smartshoes.pre.PreferenceFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

public class MenuFragment extends Fragment implements OnClickListener, OnCheckedChangeListener{
	private View view;
	private Handler handler;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_menu, null);
		
		initControl();
		return view;
	}
	
	private void initControl()
	{
		((Button)view.findViewById(R.id.menu_btn1)).setOnClickListener(this);
		((Button)view.findViewById(R.id.menu_btn2)).setOnClickListener(this);
		((Button)view.findViewById(R.id.menu_btn3)).setOnClickListener(this);
		((Switch)view.findViewById(R.id.menu_switch)).setOnCheckedChangeListener(this);
	}

	private void FragmentChange(final Fragment fragment, final String tag)
	{
		handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.start_enter, R.anim.start_exit, R.anim.end_enter, R.anim.end_exit);
				ft.replace(R.id.activity_main_fragment, fragment, tag).addToBackStack(null);
				ft.commit();
			}
		}, 200);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.menu_btn1:
			if(getFragmentManager().findFragmentByTag("neviFragment") == null)
			{
				Toast.makeText(getActivity(), "길안내 시작", Toast.LENGTH_SHORT).show();
				((MainActivity)getActivity()).showContent();
				FragmentChange(new NeviFragment(), "neviFragment");
			}
			else
			{
				((MainActivity)getActivity()).showContent();
			}
			break;
		case R.id.menu_btn2:
			if(getFragmentManager().findFragmentByTag("alarmFragment") == null)
			{
				Toast.makeText(getActivity(), "알람설정 시작", Toast.LENGTH_SHORT).show();
				((MainActivity)getActivity()).showContent();
				FragmentChange(new AlarmFragment(), "alarmFragment");
			}
			else
			{
				((MainActivity)getActivity()).showContent();
			}
			break;
		case R.id.menu_btn3:
			if(getFragmentManager().findFragmentByTag("preferFragment") == null)
			{
				Toast.makeText(getActivity(), "설정 시작", Toast.LENGTH_SHORT).show();
				((MainActivity)getActivity()).showContent();
				FragmentChange(new PreferenceFragment(), "preferFragment");
			}
			else
			{
				((MainActivity)getActivity()).showContent();
			}
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		try
		{
			if(isChecked == true)
			{
				Log.d("MenuFragment_DEBUG","Network conneting~~");
				NetworkHelper netHelper = new NetworkHelper();
				//netHelper.sendToServer("1", "192.168.0.13");
				netHelper.execute(new String[]{"1", "192.168.0.13"});
				netHelper.get();
				Log.d("MenuFragment_DEBUG","Network conneted");
			}
			else
			{
				
			}
		}
		catch(Exception e)
		{
			
		}
	}
	
	
}
