package com.lnnu.activity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption.DrivingPolicy;
import com.lnnu.activity.NavigationActivity.MyDrivingOverlay;
import com.lnnu.smarttraffic.CommonMethod;
import com.lnnu.smarttraffic.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class xianluActivity extends Activity implements OnClickListener{
	private MapView mMapView;
    private BaiduMap mBaiduMap;
    private EditText startEt;
	private EditText endEt;
	private String startPlace;// 开始地点
	private String endPlace;// 结束地点
    Button mindis;
	private RoutePlanSearch routePlanSearch;// 路径规划搜索接口

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_xianlu);
		 init();
	}
    private void init(){
    	mMapView = (MapView) findViewById(com.lnnu.smarttraffic.R.id.mapview);
		mMapView.showZoomControls(false);
		mBaiduMap= mMapView.getMap();
		CommonMethod.toAppointedMap(mBaiduMap, 38.94871, 121.593478, 14f);
		mBaiduMap.setTrafficEnabled(true);
		
	    routePlanSearch = RoutePlanSearch.newInstance();
	    mindis=(Button) findViewById(R.id.road);
	
	    startEt=(EditText)findViewById(R.id.start_et1);
	    endEt=(EditText)findViewById(R.id.end_et1);
	    
	    mindis.setOnClickListener(this);

    }
    private void driving(){
    	DrivingRoutePlanOption drivingOption = new DrivingRoutePlanOption();
    	PlanNode startplace=PlanNode.withCityNameAndPlaceName("大连", startPlace);
 	    PlanNode endplace=PlanNode.withCityNameAndPlaceName("大连", endPlace);
 	    drivingOption.from(startplace);
 	    drivingOption.to(endplace); 	 
 	    drivingOption.trafficPolicy(null);
         drivingOption.policy(DrivingPolicy.ECAR_DIS_FIRST);//设置策略，驾乘检索策略常量：躲避拥堵
         
         routePlanSearch.drivingSearch(drivingOption);
         
         routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
             
             public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
                 
             }
             
             public void onGetTransitRouteResult(TransitRouteResult arg0) {
                 
             }
             
             // 自驾搜索结果回调
             public void onGetDrivingRouteResult(DrivingRouteResult result) {
                 if (result == null
                         || SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
                     Toast.makeText(getApplicationContext(), "未搜索到结果", 0).show();
                     return;
                 }
                 
                 DrivingRouteOverlay drivingOverlay = new MyDrivingOverlay(mBaiduMap);
                 mBaiduMap.setOnMarkerClickListener(drivingOverlay);
                 drivingOverlay.setData(result.getRouteLines().get(0));//设置线路为搜索结果的第一条
                 drivingOverlay.addToMap();
                 drivingOverlay.zoomToSpan();
             }

 			@Override
 			public void onGetBikingRouteResult(BikingRouteResult arg0) {
 				// TODO Auto-generated method stub
 				
 			}
         }); 
    }
    class MyDrivingOverlay extends DrivingRouteOverlay{
        public MyDrivingOverlay(BaiduMap arg0) {
            super(arg0);
        }
        
        public BitmapDescriptor getStartMarker() {
            // 覆写此方法以改变默认起点图标
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }
        

        public BitmapDescriptor getTerminalMarker() {
            // 覆写此方法以改变默认终点图标
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
    	case R.id.road:
    		startPlace = startEt.getText().toString();
			endPlace = endEt.getText().toString();	
			mindis.setEnabled(true);
			driving();
			break;
    	}
    	
    	

    	
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
	
}
