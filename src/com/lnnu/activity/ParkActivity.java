package com.lnnu.activity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;


import com.lnnu.smarttraffic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author suruidong
 * 2016年5月30日
 */
public class ParkActivity extends Activity {
	
	private MapView mMapView;
    private BaiduMap mBaiduMap;
    TextView packInfo ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_park);
		
		 Intent intent = getIntent();
		 packInfo=(TextView) findViewById(R.id.xiangqing);
	        packInfo.setOnClickListener(new TextView.OnClickListener()
	        {
	                        @Override
	                        public void onClick(View v) {
	                        	Intent intent=new Intent();
	                        	intent.setClass(ParkActivity.this,ParkInfoActivity.class);
	                        	startActivity(intent);
	                        	
	                        }
	                
	        });
	}

	 @Override
	    protected void onPause() {
	        super.onPause();
	        // activity 暂停时同时暂停地图控件
//	        mMapView.onPause();
	    }

	    @Override
	    protected void onResume() {
	        super.onResume();
	        // activity 恢复时同时恢复地图控件
//	        mMapView.onResume();
	    }

	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        // activity 销毁时同时销毁地图控件
//	        mMapView.onDestroy();
	    }
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	// TODO Auto-generated method stub
	    	if(keyCode==KeyEvent.KEYCODE_BACK)
	    	{
	    		Intent intent=new Intent();
	        	intent.setClass(ParkActivity.this,MainActivity.class);
	        	startActivity(intent);
	    	}
	    	return super.onKeyDown(keyCode, event);
	    }
}
