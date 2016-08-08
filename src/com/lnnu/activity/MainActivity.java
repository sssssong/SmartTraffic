package com.lnnu.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnPolylineClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lnnu.bean.Monitor;
import com.lnnu.bean.NPR;
import com.lnnu.bean.Road;
import com.lnnu.smarttraffic.CommonMethod;
import com.lnnu.smarttraffic.R;
import com.lnnu.smarttraffic.R.drawable;
import com.lnnu.smarttraffic.R.id;
import com.lnnu.smarttraffic.R.layout;

/**
 * @author guodai 2016年5月30日 主界面用于测试程序用，可以进行修改
 * @param <T>
 */
public class MainActivity extends Activity  {

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
	
	private BitmapDescriptor monitorIcon;
	private List<Monitor> monitors=null;
	private List<NPR> nprs=null;
	private List<Road> roads=null;
	private Marker monitor;

	//图层相关
	 private View viewBg;
	boolean layer = false;
	private ImageButton btnlayer;
	
	boolean normal = false;
	boolean statellite = false;
	RadioButton btnnormal,btnstatellite;
	RadioGroup radiogroup;
	private PopupWindow mPopWindow;
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
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		//初始化数据！
		sp = getSharedPreferences("data", Context.MODE_PRIVATE);
		initData();
		CommonMethod.toAppointedMap(mBaiduMap, 38.94871, 121.593478, 14f);
		//点击线段，显示num
		mBaiduMap.setOnPolylineClickListener(new OnPolylineClickListener() {
			
			@Override
			public boolean onPolylineClick(Polyline arg0) {
				String id=arg0.getExtraInfo().getInt("num")+"";
				Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();;
				return false;
			}
		});

		btnlayer = (ImageButton)findViewById(R.id.img_btn_layer);  
		btnnormal = (RadioButton)findViewById(R.id.rad_normal); 
		btnstatellite = (RadioButton)findViewById(R.id.rad_statellite); 
		 //初始化遮罩层
        viewBg = findViewById(R.id.myView);
        //默认隐藏遮罩层
        viewBg.setVisibility(View.GONE);
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_layer, null); 
	}


	
	// 禁停路段的点开与关闭
	public void showNP(View v) {
		ImageButton btn = (ImageButton) v;
		if (!noParkFlag) {
			btn.setImageResource(R.drawable.mousedown_nopark);
			// 禁停路段代码
			for (NPR  road : nprs) {
				Log.v("road", road.toString());
				LatLng p1=new LatLng(road.getStartLat(), road.getStartLon());
				LatLng p2=new LatLng(road.getEndLat(), road.getEndLon());
				 List<LatLng> points = new ArrayList<LatLng>();
				  points.add(p1);
			        points.add(p2);
			        //红色#FF0000
				        OverlayOptions ooPolyline = new PolylineOptions().width(10)
				                .color(0xAAFF0000).points(points);
				       mBaiduMap.addOverlay(ooPolyline);
			}
			CommonMethod.toAppointedMap(mBaiduMap, 38.94871, 121.593478, 14f);
			noParkFlag = true;
		} else {
			btn.setImageResource(R.drawable.nopark);
			noParkFlag = false;
			mBaiduMap.clear();
		}

	}

	// 停车场的打开与关闭
	public void showParks(View v) {
		ImageButton btn = (ImageButton) v;
		if (!parkFlag) {
			btn.setImageResource(R.drawable.mousedown_parking);
			// 停车场代码
			
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
		
		//矢量化摄像头图标
		monitorIcon = BitmapDescriptorFactory.fromResource(R.drawable.monitor);
		MarkerOptions mOptions=new MarkerOptions();
		mOptions.icon(monitorIcon);
		
		ImageButton btn = (ImageButton) v;
		if (!monitorFlag) {
			btn.setImageResource(R.drawable.mousedown_monitor);
			
			for (Monitor monitor : monitors) {
				Bundle markerInfo=new Bundle();
				markerInfo.putString("name", monitor.getName());
				markerInfo.putString("url", monitor.getUrl());
				LatLng position=new LatLng(monitor.getLatitude(), monitor.getLongitude());
				mOptions.position(position).extraInfo(markerInfo);
				  Marker marker = (Marker) mBaiduMap.addOverlay(mOptions);
			}
			
			monitorFlag = true;
		} else {
			btn.setImageResource(R.drawable.monitor);
			monitorFlag = false;
			mBaiduMap.clear();
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
			drawRoads();
			CommonMethod.toAppointedMap(mBaiduMap, 38.94871, 121.593478, 14f);
			roadConditonFlag = true;
		} else {
			btn.setImageResource(R.drawable.roadcondition);
			roadConditonFlag = false;
			mBaiduMap.setTrafficEnabled(false);
			mBaiduMap.clear();
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
		BDLocation lastKnownLocation = mLocClient.getLastKnownLocation();
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
		Intent intent=new Intent(this,NavigationActivity.class);
		startActivity(intent);
	}

	// 路线出行
	public void go(View v) {
		Toast.makeText(this, "出行界面", 1).show();
	}

	// 我的信息
	public void myInfo(View v) {
		Intent intent=new Intent(this,LoginActivity.class);
		startActivity(intent);
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
	
	public void drawRoads() {
		
	     
		List<OverlayOptions> overlays=new ArrayList<OverlayOptions>();
		for (Road  road : roads) {
			Bundle info=new Bundle();
			info.putInt("num", road.getNum());
			Log.v("road2", road.toString());
			LatLng p1=new LatLng(road.getStartLat(), road.getStartLon());
			Log.v("data", p1.toString());
			LatLng p2=new LatLng(road.getEndLat(), road.getEndLon());
			Log.v("data", p2.toString());
			 List<LatLng> points = new ArrayList<LatLng>();
			  points.add(p1);
		        points.add(p2);
		        //1代表畅通，绿色
			if (road.getNowStatus()==1) {		
			        PolylineOptions ooPolyline = new PolylineOptions().width(10)
			                .color(0xAA17BF00).points(points).extraInfo(info);
			        
			        overlays.add(ooPolyline);
			       Log.v("road3", "绿色");
			       continue;
			}
				//2代表唤醒，黄色
			if (road.getNowStatus()==2) {
				
				PolylineOptions ooPolyline = new PolylineOptions().width(10)
			                .color(0xAAFF9F19).points(points).extraInfo(info);
			        overlays.add(ooPolyline);
			       Log.v("road3", "黄色");
			       continue;
			}
			//3代表拥堵，红色
			if (road.getNowStatus()==3) {
				
				PolylineOptions ooPolyline = new PolylineOptions().width(10)
			                .color(0xAAF23030).points(points).extraInfo(info);
			        overlays.add(ooPolyline);
			       Log.v("road3", "红色");
			}
		}
		mBaiduMap.addOverlays(overlays);
		
	}
	
	
	
	//初始化数据
	public void initData(){
		Gson gson=new Gson();
		String monitor_str = sp.getString("monitor", "nothing");
		String npr_str = sp.getString("npr", "nothing");
		String road_str = sp.getString("road", "nothing");
		
			monitors = gson.fromJson(monitor_str, new TypeToken<List<Monitor>>(){}.getType());
			nprs = gson.fromJson(npr_str, new TypeToken<List<NPR>>(){}.getType());
			roads = gson.fromJson(road_str, new TypeToken<List<Road>>(){}.getType());
			
			
			mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker arg0) {
					Bundle extraInfo = arg0.getExtraInfo();
					Intent intent=new Intent(MainActivity.this, MonitorImage.class);
					intent.putExtra("makrerInfo",extraInfo );
					startActivity(intent);
					return false;
				}
			});
			
	}
	
	
	
	public void layer(View v) {
		ImageButton btn = (ImageButton) v;
		  viewBg.setVisibility(View.VISIBLE);
		  viewBg.startAnimation(AnimationUtils.loadAnimation(getBaseContext(),
	                R.drawable.enterstyles));
		View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_layer, null);  
        mPopWindow = new PopupWindow(contentView);  
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);  
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);  
        int x=-100;
        int y=-120;
        mPopWindow.showAsDropDown(btnlayer,x,y);       
    
	}
	 public boolean onTouchEvent(MotionEvent event) {
     	// TODO Auto-generated method stub
     	if (mPopWindow != null && mPopWindow.isShowing()) {
     		mPopWindow.dismiss();
     		mPopWindow = null;
     	}
     	return super.onTouchEvent(event);
     	}
	 
	public void layerback(View v) {
		ImageButton btn = (ImageButton) v;
		//btn.setImageResource(R.drawable.mousedown_roadcondition);
		
		 viewBg.startAnimation(AnimationUtils.loadAnimation(getBaseContext(),
                 R.drawable.exitstyle));
         viewBg.setVisibility(View.GONE);
         mPopWindow.dismiss();
	}

	//单选按钮事件
		public void setMapMode(View view) {
	        boolean checked = ((RadioButton) view).isChecked();
	        switch (view.getId()) {
	            case R.id.rad_normal:
	                if (checked) {
	                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
	                }
	                break;
	            case R.id.rad_statellite:
	                if (checked) {
	                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
	                }
	                break;
	            default:
	                break;
	        }
	    }

	
	
}
