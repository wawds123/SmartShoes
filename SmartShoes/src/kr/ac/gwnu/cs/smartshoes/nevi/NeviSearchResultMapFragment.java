package kr.ac.gwnu.cs.smartshoes.nevi;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.gwnu.cs.smartshoes.JParser;
import kr.ac.gwnu.cs.smartshoes.R;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class NeviSearchResultMapFragment extends Fragment
implements MapView.OpenAPIKeyAuthenticationResultListener,
		   MapView.MapViewEventListener,
		   MapView.CurrentLocationEventListener
{
	private View view;
	private MapView mapView;
	private LinearLayout linearLayout;
	private Local local;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private String provider;
	public NeviSearchResultMapFragment(Local local){this.local = local;}
	private Location location;
	private String[] params;
	private String[] transParams;
	private double Latitude, local_Latitude;
	private double Longtitude, local_Longtitude;
	private String result;
	private MapPolyline polyLine;
	private ArrayList<String> key_list;
	private JParser parser;
	private NeviTransCoord ntc;
	private ArrayList<HashMap<String, String>> transCoord_list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		try
		{
			view = inflater.inflate(R.layout.fragment_nevi_search_map, null);
			linearLayout = (LinearLayout)view.findViewById(R.id.fragment_nevi_search_mapview);
			
			MapView.setMapTilePersistentCacheEnabled(true);
			
			mapView = new MapView(getActivity());
			
			mapView.setDaumMapApiKey("dc6e94e4ea265ddc55c24d700a70034a19e8e38a");
	        mapView.setOpenAPIKeyAuthenticationResultListener(this);
	        mapView.setMapViewEventListener(this);
	        mapView.setCurrentLocationEventListener(this);
	        
			mapView.setMapType(MapView.MapType.Standard);
		
			linearLayout.addView(mapView);
			mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
			
			locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
			provider = locationManager.getBestProvider(new Criteria(), true);
			
			location = locationManager.getLastKnownLocation(provider);
			//Latitude = Integer.parseInt(String.valueOf(Math.round(location.getLatitude())));
			//Longtitude = Integer.parseInt(String.valueOf(Math.round(location.getLongitude())));
			
			Log.d("NeviSearchResultMapFragment_DEBUG", "Current Latitude : " + location.getLatitude() + ", Longtitude : " + location.getLongitude());
			Log.d("NeviSearchResultMapFragment_DEBUG", "Local Latitude : " + local.getLat() + ", Longtitude : " + local.getLng());
			local_Latitude = Double.parseDouble(local.getLat());
			local_Longtitude = Double.parseDouble(local.getLng());
			key_list = new ArrayList<String>();
			
			transParams = new String[]{String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude())};
			
			ntc = new NeviTransCoord();
			ntc.execute(transParams);
			result = ntc.get();
			transCoord_list = new ArrayList<HashMap<String, String>>();
			key_list.add("y");
			key_list.add("x");
			
			parser = new JParser();
			transCoord_list = parser.Parser(result, key_list);
			
			Latitude = (int)Double.parseDouble(transCoord_list.get(0).get("y"));
			Longtitude = (int)Double.parseDouble(transCoord_list.get(0).get("x"));
			
			Log.d("NeviSearchResultMapFragment_DEBUG", "Latitude : " + Latitude);
			Log.d("NeviSearchResultMapFragment_DEBUG", "Longtitude : " + Longtitude);

			transParams = new String[]{"127.0010008251466","37.58175650913604"};
			
			ntc = new NeviTransCoord();
			ntc.execute(transParams);
			result = ntc.get();
			transCoord_list = new ArrayList<HashMap<String,String>>();
			
			transCoord_list = parser.Parser(result, key_list);
			for(int i=0; i<transCoord_list.size(); i++)
			{
				local.setLng(String.valueOf((int)Double.parseDouble(transCoord_list.get(i).get("x"))));
				local.setLat(String.valueOf((int)Double.parseDouble(transCoord_list.get(i).get("y"))));
			}
			
			Log.d("NeviSearchResultMapFragment_DEBUG", "Latitude : " + local.getLat());
			Log.d("NeviSearchResultMapFragment_DEBUG", "Longtitude : " + local.getLng());
						
			params = new String[] {String.valueOf(Longtitude), String.valueOf(Latitude), local.getLng(), local.getLat()};
			
			NeviResultRoute nrr = new NeviResultRoute();
			nrr.execute(params);
			result = nrr.get();
			Log.d("NeviSearchResultMapFragment_DEBUG", result);
			
			ArrayList<HashMap<String, String>> route_list = new ArrayList<HashMap<String, String>>();
			key_list.add("sections");
			key_list.add("guideList");
			key_list.add("guideCode");
			key_list.add("guideMent");
			key_list.add("y");
			key_list.add("x");
			
			parser = new JParser();
			
			route_list = parser.Parser(result, key_list);
			
			for(int i=0; i<route_list.size(); i++)
			{
				Log.d("NeviSearchResultMapFragment_DEBUG", route_list.get(i).toString());
			}
			
			mapOverLay(route_list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return view;
	}

	
	private void mapOverLay(ArrayList<HashMap<String, String>> route_list)
	{
		try
		{
		mapView.removeAllPolylines();
		polyLine = new MapPolyline();
		polyLine.setTag(1000);
		polyLine.setLineColor(Color.argb(128, 255, 51, 0));
		for(int i=1; i< route_list.size()-3; i++)
		{
			Latitude = Double.parseDouble(route_list.get(i).get("y"));
			Longtitude = Double.parseDouble(route_list.get(i).get("x"));
			/*ntc = new NeviTransCoord();
			ntc.execute(new String[]{String.valueOf(Latitude), String.valueOf(Longtitude)});
			result = ntc.get();
			parser = new JParser();
			transCoord_list = parser.Parser(result, key_list);
			
			Latitude = (int)Double.parseDouble(transCoord_list.get(0).get("y"));
			Longtitude = (int)Double.parseDouble(transCoord_list.get(0).get("x"));
			*/
			
			
			
			polyLine.addPoint(MapPoint.mapPointWithCONGCoord(Latitude, Longtitude));
		}
		mapView.addPolyline(polyLine);
		mapView.fitMapViewAreaToShowPolyline(polyLine);
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	// net.daum.mf.map.api.MapView.OpenAPIKeyAuthenticationResultListener
		@Override
		public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int resultCode, String resultMessage) 
		{
			Log.i("NeviSearchFragment_APIKey",	String.format("Open API Key Authentication Result : code=%d, message=%s", resultCode, resultMessage));	
		}
		
		
	// net.daum.mf.map.api.MapView.MapViewEventListener
		@Override
		public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) 
		{
			// TODO Auto-generated method stub
			MapPoint.GeoCoordinate mapPointGeo = mapCenterPoint.getMapPointGeoCoord();
			Log.i("NeviSearchFragment_POINT", String.format("MapView onMapViewCenterPointMoved (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
		}
		@Override
		public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint)
		{
			// TODO Auto-generated method stub
			MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
			alertDialog.setTitle("NeviSearchFragment");
			alertDialog.setMessage(String.format("Double-Tap on (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
			alertDialog.setPositiveButton("확인", null);
			alertDialog.show();
		}
		@Override
		public void onMapViewInitialized(MapView mapView)
		{
			//mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
			mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(local_Latitude,local_Longtitude), 6, true);
		}
		@Override
		public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) 
		{
			MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
			
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
			alertDialog.setTitle("NeviSearchFragment");
			alertDialog.setMessage(String.format("Long-Press on (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
			alertDialog.setPositiveButton("OK", null);
			alertDialog.show();
		}
		@Override
		public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) 
		{
			MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
			Log.d("NeviSearchFragment_POINT", String.format("MapView onMapViewSingleTapped (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
		}
		@Override
		public void onMapViewZoomLevelChanged(MapView mapView, int zoomLevel) 
		{
			Log.d("NeviSearchFragment_POINT", String.format("MapView onMapViewZoomLevelChanged (%d)", zoomLevel));
		}
		
		
	
	// net.daum.mf.map.api.MapView.CurrentLocationEventListener
		@Override
		public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float headingAngle) 
		{
			Log.d("NeviSearchFragment_POINT", String.format("MapView onCurrentLocationDeviceHeadingUpdate: device heading = %f degrees", headingAngle));
		}
		@Override
		public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) 
		{
			MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
			Latitude = Integer.parseInt(String.valueOf(Math.round(mapPointGeo.latitude)));
			Longtitude = Integer.parseInt(String.valueOf(Math.round(mapPointGeo.longitude)));
			Log.d("NeviSearchFragment_POINT", String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
			
			
		}
		@Override
		public void onCurrentLocationUpdateCancelled(MapView mapView) 
		{
			Log.i("NeviSearchFragment_POINT", "MapView onCurrentLocationUpdateCancelled");
		}
		@Override
		public void onCurrentLocationUpdateFailed(MapView mapView) 
		{
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
			alertDialog.setTitle("NeviSearchFragment");
			alertDialog.setMessage("현재 위치 갱신 실패!! :(");
			alertDialog.setPositiveButton("확인", null);
			alertDialog.show();
		}
}
