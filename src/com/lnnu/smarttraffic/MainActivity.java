package com.lnnu.smarttraffic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lnnu.activity.SimpleMap;
import com.lnnu.bean.Monitor;

/**
 * @author guodai 2016年5月30日 主界面用于测试程序用，可以进行修改
 */
public class MainActivity extends Activity {

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private SharedPreferences sp ;
	private Editor editor;
	
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
		
		//拷贝数据
		File spFile=new File("/data/data/com.lnnu.smarttraffic/shared_prefs/data.xml");
		if (!spFile.exists()) {
			copyAssetsFileToSP("data.xml", spFile);
			Log.e("copy", "success");
		}
		
		sp = getSharedPreferences("data", Context.MODE_PRIVATE);
		
		getMonitorData();

		
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		CommonMethod.toAppointedMap(mBaiduMap, 38.94871, 121.593478, 14f);

	}

	public void getMonitorData(){
		String str_monitor = sp.getString("monitor", "nothing");
		Log.v("data", str_monitor);
		
		Gson gson=new Gson();
		
		List<Monitor> monitors=null;
		monitors=gson.fromJson(str_monitor, new TypeToken<List<Monitor>>(){}.getType());
		
		Log.v("monitor", monitors.toString());
		for (Monitor monitor : monitors) {
			Log.v("monitor", monitor.toString());
		}
		
//		String str_npr = sp.getString("npr", "nothing");
//		Log.v("data", str_npr);
//		
//		String str_road = sp.getString("road", "nothing");
//		Log.v("data", str_road);
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
	
	/**
	 * 从工程资源里面复制相关文件到存储卡上
	 */
	public  void copyAssetsFileToSP(String filename, File des) {
		InputStream inputFile = null;
		OutputStream outputFile = null;
		try {
			
				inputFile =getAssets().open(filename);
				outputFile = new FileOutputStream(des);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputFile.read(buffer)) > 0) {
					outputFile.write(buffer, 0, length);
				}
				outputFile.flush();
				outputFile.close();
				inputFile.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
