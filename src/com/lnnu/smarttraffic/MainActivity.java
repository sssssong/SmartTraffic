package com.lnnu.smarttraffic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.lnnu.activity.SimpleMap;

/**
 * @author guodai 2016年5月30日 主界面用于测试程序用，可以进行修改
 */



public class MainActivity extends Activity {

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	
	// 是否开启按钮标记
	boolean noParkFlag = false;
	boolean parkFlag = false;
	boolean monitorFlag = false;
	boolean roadConditonFlag = false;
	boolean positionFlag = false;
	
	//定位相关
	boolean isFirstLoc = true; // 是否首次定位
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner(); // 位置监听

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		CommonMethod.toAppointedMap(mBaiduMap, 38.94871, 121.593478, 14f);

	}

	// 禁停路段的点开与关闭
	public void showNP(View v) {
		ImageButton btn = (ImageButton) v;
		if (!noParkFlag) {
			btn.setImageResource(R.drawable.mousedown_nopark);
			// 禁停路段代码
			noParkFlag = true;
		} else {
			btn.setImageResource(R.drawable.nopark);
			noParkFlag = false;
		}

	}

	// 停车场的打开与关闭
	public void showParks(View v) {
		ImageButton btn = (ImageButton) v;
		if (!parkFlag) {
			btn.setImageResource(R.drawable.mousedown_parking);
			// 禁停路段代码
			parkFlag = true;
		} else {
			btn.setImageResource(R.drawable.parking);
			parkFlag = false;
		}
	}

	// 定位功能
	public void location(View v) {
		ImageButton btn = (ImageButton) v;
		if (!positionFlag) {
			btn.setImageResource(R.drawable.mousedown_position);
			locInits();
			positionFlag = true;
		} else {
			btn.setImageResource(R.drawable.position);
			positionFlag = false;
			// 退出时销毁定位
			mLocClient.stop();
			// 关闭定位图层
			mBaiduMap.setMyLocationEnabled(false);
		}
	}

	// 电子警察
	public void monitor(View v) {
		ImageButton btn = (ImageButton) v;
		if (!monitorFlag) {
			btn.setImageResource(R.drawable.mousedown_monitor);
			// 禁停路段代码
			monitorFlag = true;
		} else {
			btn.setImageResource(R.drawable.monitor);
			monitorFlag = false;
		}
	}

	/**
	 * @author guodai
	 * 2016年6月28日
	 * 实时路况功能
	 */
	public void showRC(View v) {
		ImageButton btn = (ImageButton) v;
		if (!roadConditonFlag) {
			btn.setImageResource(R.drawable.mousedown_roadcondition);
			//开启路况图层
			mBaiduMap.setTrafficEnabled(true);
			roadConditonFlag = true;
		} else {
			btn.setImageResource(R.drawable.roadcondition);
			roadConditonFlag = false;
			mBaiduMap.setTrafficEnabled(false);
		}
	}

	/**
	 * @author guodai
	 * 2016年6月28日
	 * 定位功能初始化
	 */
	public void locInits() {
		mBaiduMap.setMyLocationEnabled(true);
		
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				LocationMode.COMPASS, true, null));

	}
	
	// 展开简图
	public void showSM(View v) {
		Intent intent = new Intent(this, SimpleMap.class);
		startActivity(intent);
	}

	// 导航
	public void toNaviActivity(View v) {
		Toast.makeText(this, "导航界面", 1).show();
	}

	// 路线出行
	public void go(View v) {
		Toast.makeText(this, "出行界面", 1).show();
	}

	// 我的信息
	public void myInfo(View v) {
		Toast.makeText(this, "我的信息", 1).show();
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * @author guodai
	 * 2016年6月28日
	 * 定位监听SDK内部类
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null) {
				return;
			}
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
}
