package kr.ac.gwnu.cs.smartshoes.nevi;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import kr.ac.gwnu.cs.smartshoes.JParser;
import kr.ac.gwnu.cs.smartshoes.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

public class NeviSearchResultFragment extends Fragment
{
	
	private View view;
	
	private String nevi_fragment_search_text;
	private String API_KEY = "6fd658ed45fa2208b6ff3636251acf78279568ef";
	private ArrayList<HashMap<String, String>> search_list;
	private ListView nevi_search_list_view;
	private NeviSearchListAdapter adapter;
	
	public NeviSearchResultFragment(String nevi_fragment_search_text) {
		this.nevi_fragment_search_text = nevi_fragment_search_text;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_nevi_search, null);
		
		initControls();
		
		NeviSearchTask nst = new NeviSearchTask();
		nst.execute(nevi_fragment_search_text);
		return view;
	}
	
	private void initControls()
	{
		nevi_search_list_view = (ListView)view.findViewById(R.id.nevi_search_list);
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	//apikey=6fd658ed45fa2208b6ff3636251acf78279568ef
	
	class NeviSearchTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			((ProgressBar)view.findViewById(R.id.nevi_search_progressbar)).setVisibility(View.VISIBLE);
		}
		
		@Override
		protected String doInBackground(String... params) 
		{
			try{
				DefaultHttpClient client = new DefaultHttpClient();
				String getURL = "http://apis.daum.net/local/geo/addr2coord?apikey="+API_KEY+"&q="+params[0]+"&output=json";
				HttpGet get = new HttpGet(getURL);
				HttpResponse responseGET = client.execute(get);
				HttpEntity resEntity = responseGET.getEntity();
				if(resEntity != null)
				{
					return EntityUtils.toString(resEntity);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ArrayList<String> key_list = new ArrayList<String>();
			JParser parser = new JParser();
			
			key_list.add("item");
			key_list.add("newAddress");
			key_list.add("mountain");
			key_list.add("buildingAddress");
			key_list.add("placeName");
			key_list.add("lng");
			key_list.add("mainAddress");
			key_list.add("id");
			key_list.add("point_x");
			key_list.add("title");
			key_list.add("point_y");
			key_list.add("isNewAddress");
			key_list.add("point_wx");
			key_list.add("point_wy");
			key_list.add("subAddress");
			key_list.add("localName_1");
			key_list.add("localName_2");
			key_list.add("localName_3");
			key_list.add("lat");
			search_list = parser.Parser(result, key_list);
			
			HashMap<String, String> tmp = search_list.get(0);
			Log.d("NeviSearchResultFragment_DEBUG",tmp.toString());
			ArrayList<Local> local_list = new ArrayList<Local>();
			
			int length = Integer.parseInt(tmp.get("item"));
			
			for(int i=1; i<length; i++)
			{
				HashMap<String, String> item = search_list.get(i);
				Local local = new Local();
				
				local.setNewAddress(item.get("newAddress"));
				local.setMountain(item.get("mountain"));
				local.setBuildingAddress(item.get("buildingAddress"));
				local.setPlaceName(item.get("placeName"));
				local.setLng(item.get("lng"));
				local.setMainAddress(item.get("mainAddress"));
				local.setId(item.get("id"));
				local.setPoint_x(item.get("point_x"));
				local.setTitle(item.get("title"));
				local.setPoint_y(item.get("point_y"));
				local.setIsNewAddress(item.get("isNewAddress"));
				local.setPoint_wx(item.get("point_wx"));
				local.setPoint_wy(item.get("point_wy"));
				local.setSubAddress(item.get("subAddress"));
				local.setLocalName_1(item.get("localName_1"));
				local.setLocalName_2(item.get("localName_2"));
				local.setLocalName_3(item.get("localName_3"));
				local.setLat(item.get("lat"));
				
				local_list.add(local);
			}
			adapter = new NeviSearchListAdapter(getActivity(), R.layout.nevi_search_list_item, local_list);
			nevi_search_list_view.setAdapter(adapter);
			
			((ProgressBar)view.findViewById(R.id.nevi_search_progressbar)).setVisibility(View.INVISIBLE);
		}
	}	
}
