package kr.ac.gwnu.cs.smartshoes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainFragment extends Fragment{
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("MainFragment_DEBUG", "1");
		view = inflater.inflate(R.layout.fragment_main, null);
		Log.d("MainFragment_DEBUG", "2");
		return view;
	}	
}
