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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author suruidong
 * 2016年5月30日
 */
public class ParkInfoActivity extends Activity {
	
	private MapView mMapView;
    private BaiduMap mBaiduMap;
    TextView packInfo ;
    ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_park);
		
		 Intent intent = getIntent();
		 setContentView(R.layout.activity_parkinfo);
	       TextView tv=(TextView) findViewById(R.id.sname1);
	       tv.setBackgroundResource(R.drawable.textstyle);
	       TextView tv2=(TextView) findViewById(R.id.stopname2);
	       tv2.setBackgroundResource(R.drawable.textstyle);
	       back=(ImageView) findViewById(R.id.imageView1);
	       
	       back.setOnClickListener(new ImageView.OnClickListener()
	       {
	                       @Override
	                       public void onClick(View v) {
	                    	   Intent intent=new Intent();
	                    	   intent.setClass(ParkInfoActivity.this,ParkActivity.class);
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
	
}
