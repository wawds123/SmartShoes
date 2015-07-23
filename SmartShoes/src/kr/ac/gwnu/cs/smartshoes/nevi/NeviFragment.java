package kr.ac.gwnu.cs.smartshoes.nevi;

import kr.ac.gwnu.cs.smartshoes.R;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class NeviFragment extends Fragment implements OnClickListener,OnEditorActionListener{
	private View view;
	private InputMethodManager imm;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_nevi, null);
		initColtrol();
		return view;
	}
	
	private void initColtrol()
	{
		((Button)view.findViewById(R.id.nevi_search_btn)).setOnClickListener(this);
		imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
		((EditText)view.findViewById(R.id.nevi_editText)).setOnEditorActionListener(this);
	}

	private void KeywordTest()
	{
		if(TextUtils.isEmpty(((EditText)view.findViewById(R.id.nevi_editText)).getText().toString()))
		{
			Toast.makeText(getActivity(), "검색할 위치를 입력하세요", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getActivity(), "search_btn clicked", Toast.LENGTH_SHORT).show();
			imm.hideSoftInputFromWindow(((EditText)view.findViewById(R.id.nevi_editText)).getWindowToken(), 0);
			FragmentChange(new NeviSearchResultFragment(((EditText)view.findViewById(R.id.nevi_editText)).getText().toString().replaceAll(" ", "")), "NeviSearchResultFragment");
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.nevi_search_btn:
			KeywordTest();
			break;
		}
	}
	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch(actionId)
		{
		case EditorInfo.IME_ACTION_SEARCH:
			KeywordTest();
			break;
		}
		return false;
	}
	
	
	private void FragmentChange(final Fragment fragment, final String tag)
	{
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run() {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.start_enter, R.anim.start_exit, R.anim.end_enter, R.anim.end_exit);
				ft.replace(R.id.activity_main_fragment, fragment,tag).addToBackStack(null);
				ft.commit();
			}
		}, 200);
	}
}
