package kr.ac.gwnu.cs.smartshoes.nevi;

import java.util.ArrayList;

import kr.ac.gwnu.cs.smartshoes.MainActivity;
import kr.ac.gwnu.cs.smartshoes.R;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NeviSearchListAdapter extends ArrayAdapter<Local> implements OnClickListener{

	private ArrayList<Local> items;
	private Local local;
	private Context context;
	public NeviSearchListAdapter(Context context, int textViewResourceId, ArrayList<Local> items) {
		super(context, textViewResourceId, items);
		// TODO Auto-generated constructor stub
		this.items = items;
		this.context = context;
	}
	
	public Local getLocal(int index)
	{
		return items.get(index);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		local = items.get(position);
		
		if(convertView == null)
		{
			convertView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.nevi_search_list_item, null);	
		}
		if(local != null)
		{
			((LinearLayout)convertView.findViewById(R.id.nevi_search_list_item_layout)).setOnClickListener(this);
			String address = local.getLocalName_1() + local.getLocalName_2() + local.getLocalName_3();
			((TextView)convertView.findViewById(R.id.nevi_search_list_item_name)).setText(local.getTitle());
			((TextView)convertView.findViewById(R.id.nevi_search_list_item_address)).setText(address);
			((TextView)convertView.findViewById(R.id.nevi_search_list_item_point)).setText(local.getLat() + local.getLng());
			//((TextView)convertView.findViewById(R.id.nevi_search_list_item_name)).setOnClickListener(this);
			//((TextView)convertView.findViewById(R.id.nevi_search_list_item_address)).setOnClickListener(this);
			
			
		}
		
		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		//case R.id.nevi_search_list_item_name:case R.id.nevi_search_list_item_address:
		case R.id.nevi_search_list_item_layout:
			
			FragmentChange(new NeviSearchResultMapFragment(local));
			break;
			
		}
	}
	
	private void FragmentChange(final Fragment fragment)
	{
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				FragmentTransaction ft = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.start_enter, R.anim.start_exit, R.anim.end_enter, R.anim.end_exit);
				ft.replace(R.id.activity_main_fragment, fragment,"neviFragment").addToBackStack(null);
				ft.commit();
			}
		}, 200);
		
	}

}
