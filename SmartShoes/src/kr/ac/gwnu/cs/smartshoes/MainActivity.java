package kr.ac.gwnu.cs.smartshoes;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends SlidingFragmentActivity implements CanvasTransformer{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title_main);
		
		setBehindContentView(R.layout.activity_menu);
		DisplayMetrics display_metrics = new DisplayMetrics();
		
		getWindowManager().getDefaultDisplay().getMetrics(display_metrics);
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setFadeDegree(0.35f);
		menu.setBehindWidth((int)(display_metrics.widthPixels*0.875));
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindCanvasTransformer(this);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void transformCanvas(Canvas canvas, float percentOpen) {
		// TODO Auto-generated method stub
		float scale = (float) (percentOpen*0.25 + 0.75);
		canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);
	}

}
