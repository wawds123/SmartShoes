package kr.ac.gwnu.cs.smartshoes.pre;

import kr.ac.gwnu.cs.smartshoes.R;
import kr.ac.gwnu.cs.smartshoes.R.layout;
import kr.ac.gwnu.cs.smartshoes.R.menu;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class PreferenceFragment extends Fragment implements OnClickListener{

	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_pre, null);
		
		initControls();
		return view;
	}
	
	private void initControls()
	{
		((Button)view.findViewById(R.id.pre_btn1)).setOnClickListener(this);
		((Button)view.findViewById(R.id.pre_btn2)).setOnClickListener(this);
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
		switch(v.getId())
		{
		case R.id.pre_btn1:
			FragmentChange(new PreferenceUserGuideFragment(), "PreferenceGuideFragment");
			break;
		case R.id.pre_btn2:
			FragmentChange(new PreferenceHelpFragment(), "PreferenceHelpFragment");
			break;
		}
	}
	
}
