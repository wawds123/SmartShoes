package kr.ac.gwnu.cs.smartshoes.nevi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class NeviTransCoord extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			DefaultHttpClient client = new DefaultHttpClient();
			String getURL = "http://apis.daum.net/local/geo/transcoord?apikey=6fd658ed45fa2208b6ff3636251acf78279568ef"
					+"&x="+params[0]+"&y="+params[1]+"&fromCoord=WGS84&toCoord=TM&output=json";
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

}
