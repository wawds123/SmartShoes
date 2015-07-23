package kr.ac.gwnu.cs.smartshoes.alarm;

import java.util.ArrayList;

import kr.ac.gwnu.cs.smartshoes.R;
import kr.ac.gwnu.cs.smartshoes.SET;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

public class AlarmEditRepeatFragment extends Fragment implements OnItemClickListener, OnClickListener{
	private View view;
	private ArrayList<String> arraylist;
	private ListView repeat_listview;
	private AlarmEditRepeatListAdapter adapter;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		getActivity().findViewById(R.id.title_back_btn).setVisibility(View.VISIBLE);
		getActivity().findViewById(R.id.title_alarm_save_btn).setVisibility(View.INVISIBLE);
		getActivity().findViewById(R.id.title_alarm_add_btn).setVisibility(View.INVISIBLE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_alarm_edit_repeat, null);
		initControls();
		return view;
	}
	
	private void initControls()
	{
		arraylist = new ArrayList<String>();
		arraylist.add("월요일");
		arraylist.add("화요일");
		arraylist.add("수요일");
		arraylist.add("목요일");
		arraylist.add("금요일");
		arraylist.add("토요일");
		arraylist.add("일요일");
		
		repeat_listview = (ListView)view.findViewById(R.id.alarm_edit_repeat_listview);
		adapter = new AlarmEditRepeatListAdapter(getActivity(), R.layout.alarm_repeat_list_item, arraylist);
		repeat_listview.setAdapter(adapter);
		repeat_listview.setOnItemClickListener(this);
	}
	
	
	private void checkedListItem()
	{
		ArrayList<String> checked_list = adapter.getChecked();
		for(int i=0; i< checked_list.size(); i++)
		{
			Log.d("AlarmEditRepeatFragment_DEBUG", checked_list.get(i));
			new SET().REPEATS += checked_list.get(i);
		}
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		checkedListItem();
		getActivity().findViewById(R.id.title_back_btn).setVisibility(View.INVISIBLE);
		getActivity().findViewById(R.id.title_alarm_save_btn).setVisibility(View.VISIBLE);
		getActivity().findViewById(R.id.title_alarm_add_btn).setVisibility(View.INVISIBLE);
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
		// TODO Auto-generated method stub
		adapter.setChecked(position);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.title_back_btn:
			getFragmentManager().popBackStack();
			break;
		}
	}
}
