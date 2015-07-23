package kr.ac.gwnu.cs.smartshoes;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JParser 
{
	private static String TAG = "JParser";
	
	private int index;
	private ArrayList<HashMap<String, String>> temp;
	
	public ArrayList<HashMap<String, String>> Parser(String data, ArrayList<String> key_list)
	{
		String tag = TAG + "-Parser()";
		
		HashMap<String, String> hash_map = new HashMap<String, String>();
		JSONObject jsonObject = null;
		
		try
		{
			temp = new ArrayList<HashMap<String,String>>();
			
			index = 0;
			
			jsonObject = new JSONObject(data);
			if(jsonObject.has("channel")){
				JSONObject channel = jsonObject.getJSONObject("channel");
				try
				{temp.addAll(fun(channel.getJSONArray("item"), key_list)); }
				catch(Exception ex)
				{}
			}
			else if(jsonObject.has("walk"))
			{
				JSONObject walk = jsonObject.getJSONObject("walk");
				try
				{
					temp.addAll(fun(walk.getJSONArray("sections"), key_list));
				}
				catch(Exception e)
				{
					
				}
			}
			else
			{
				Log.d("JParser_route","1");
				hash_map = fun2(jsonObject, key_list);
			}

		}
		catch(Exception ex){ Log.d(tag, "Exception\n" + ex.toString()); }
		
		
		
		temp.add(hash_map);
		
		return temp;
	}
	private ArrayList<HashMap<String, String>> fun(JSONArray list, ArrayList<String> key_list)
	{
		String tag = TAG + "-fun()";
		
		ArrayList<HashMap<String, String>> array_list = new ArrayList<HashMap<String,String>>();
		
		try
		{
			HashMap<String, String> hash_map = new HashMap<String, String>();

			hash_map.put(key_list.get(index++), Integer.toString(list.length()));

			int a = index;
			
			array_list.add(hash_map);
			
			for(int i=0;i<list.length();i++)
			{
				HashMap<String, String> hm = new HashMap<String, String>();
				JSONObject object = list.getJSONObject(i);
				
				index = a;
				
				while(true)
				{
					try
					{
						hm.putAll(fun1(object, key_list));
						
						if(hm.get(key_list.get(index))!=null)
							break;
					}
					catch(Exception ex) { break; }
				}
				array_list.add(hm);
			}
		}
		catch(Exception ex)
		{ Log.d(tag, "Exception\n" + ex.toString()); }
		
		return array_list;
	}
	
	
	
	
	private HashMap<String, String> fun1(JSONObject object, ArrayList<String> key_list)
	{
		HashMap<String, String> hm = new HashMap<String, String>();
		Log.d("JParser_route","2");
		try
		{
			Log.d("JParser_route","3");
			JSONObject obj = object.getJSONObject(key_list.get(index));
			Log.d("JParser_route","4");
			index++;
			Log.d("JParser_route","5");
			Log.d("JParser_length",""+obj.length());
			for(int i=0;i<obj.length();i++)
			{
				try
				{
					HashMap<String, String> tmp = null;
					
					tmp = fun1(obj, key_list);
					
					hm.putAll(tmp);
				}
				catch(Exception ef){}
			}
		}
		catch(Exception ex)
		{
			try 
			{
				temp.addAll((fun(object.getJSONArray(key_list.get(index)), key_list)));
				
				hm = null;
			}
			catch (JSONException e)
			{
				try
				{ hm.put(key_list.get(index), object.getString(key_list.get(index))); }
				catch (JSONException e1){}
				
				index++;
			}
		}
		return hm;
	}
	
	private HashMap<String, String> fun2(JSONObject object, ArrayList<String> key_list)
	{
		HashMap<String, String> hm = new HashMap<String, String>();
		try
		{
			for(;index<object.length();index++)
			{
				hm.put(key_list.get(index), object.getString(key_list.get(index)));
			}
		}
		catch(Exception e)
		{
			
		}
		return hm;
	}
}