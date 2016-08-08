package com.lnnu.activity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.lnnu.smarttraffic.CommonMethod;
import com.lnnu.smarttraffic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity{
	Button logbtn;
	private MapView mMapView;
    private BaiduMap mBaiduMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_login);
		 logbtn=(Button) findViewById(R.id.logbtn);
	     logbtn.setOnClickListener(new OnClickListener(){
	    	 public void onClick(View v){
	    		 mMapView = (MapView) findViewById(com.lnnu.smarttraffic.R.id.mapview);
	    			mMapView.showZoomControls(false);
	    			mBaiduMap= mMapView.getMap();
	    			CommonMethod.toAppointedMap(mBaiduMap, 38.94871, 121.593478, 14f);
	    			setContentView(R.layout.activity_main);
	    		 }
	     }
	     );
	}
}

		


