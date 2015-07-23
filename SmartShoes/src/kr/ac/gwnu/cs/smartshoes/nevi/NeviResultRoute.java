package kr.ac.gwnu.cs.smartshoes.nevi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class NeviResultRoute extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			DefaultHttpClient client = new DefaultHttpClient();
			/*String getURL = "http://map.daum.net/route/route.json?routeMode=SHORTEST_REALTIME&routeOption=NONE"+
							"&sX="+params[0]+
							"&sY="+params[1]+
							"&eX="+params[2]+
							"&eY="+params[3];*/
			String getURL = "http://map.daum.net/route/route.json?routeMode=SHORTEST_REALTIME&routeOption=NONE&sX=862540&sY=667938&eX=863549&eY=666837";
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
