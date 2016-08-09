package com.lnnu.activity;


import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.lnnu.smarttraffic.CommonMethod;
import com.lnnu.smarttraffic.R;



/**
 * @author guodai
 * 2016年6月12日
 */

public class SimpleMap extends Activity{
	
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private String pathname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置自定义地图样式
				
		setContentView(R.layout.activity_simplemap);
		
		mMapView = (MapView) findViewById(R.id.sm_bmapview);
		
		
		//关闭zoom控件,与比例尺控件
		mMapView.showZoomControls(false);
		mMapView.showScaleControl(false);
		
		mBaiduMap = mMapView.getMap();
		
		mBaiduMap.setTrafficEnabled(true);
		
		UiSettings uiSettings = mBaiduMap.getUiSettings();
		//设置所有手势开关关闭
		uiSettings.setAllGesturesEnabled(false);
		//打开指南针
		uiSettings.setCompassEnabled(true);
				
		//大连中心点121.593478,38.94871
		//设置地图中心点及缩放级别，平移后的点121.615498, 38.925857
		CommonMethod.toAppointedMap(mBaiduMap, 38.925857, 121.615498, 14.2f);
	}
	
	public static void toAppointedMap(BaiduMap map, double lat, double lon, float zoom) {
		// 自定义一个经纬度，先纬度，后经度
		LatLng latLng = new LatLng(lat, lon);
		// 自定义地图状态
		MapStatus mapStatus = new MapStatus.Builder().target(latLng).zoom(zoom)
				.build();
		MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
		// 更新地图状态，加载位置。
		map.animateMapStatus(u);
	}
}
