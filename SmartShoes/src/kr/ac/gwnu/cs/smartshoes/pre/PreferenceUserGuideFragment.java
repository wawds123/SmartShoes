package kr.ac.gwnu.cs.smartshoes.pre;

import kr.ac.gwnu.cs.smartshoes.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PreferenceUserGuideFragment extends Fragment{
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_pre_userguide, null);
		return view;
	}
	
	
}
