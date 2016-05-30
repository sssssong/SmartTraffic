package com.lnnu.smarttraffic;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * @author guodai
 * 2016年5月30日
 * 主界面用于测试程序用，可以进行修改
 */
public class MainActivity extends Activity {
	
	private MapView mMapView;
    private BaiduMap mBaiduMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		 Intent intent = getIntent();
	        if (intent.hasExtra("x") && intent.hasExtra("y")) {
	            // 当用intent参数时，设置中心点为指定点
	            Bundle b = intent.getExtras();
	            LatLng p = new LatLng(b.getDouble("y"), b.getDouble("x"));
	            mMapView = new MapView(this,
	                    new BaiduMapOptions().mapStatus(new MapStatus.Builder()
	                            .target(p).build()));
	        } else {
	            mMapView = new MapView(this, new BaiduMapOptions());
	        }
	        setContentView(mMapView);
	        mBaiduMap = mMapView.getMap();
	}

	 @Override
	    protected void onPause() {
	        super.onPause();
	        // activity 暂停时同时暂停地图控件
	        mMapView.onPause();
	    }

	    @Override
	    protected void onResume() {
	        super.onResume();
	        // activity 恢复时同时恢复地图控件
	        mMapView.onResume();
	    }

	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        // activity 销毁时同时销毁地图控件
	        mMapView.onDestroy();
	    }
	
}
