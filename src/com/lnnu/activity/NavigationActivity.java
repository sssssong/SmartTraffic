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
public class NavigationActivity extends Activity {
	
	private MapView mMapView;
    private BaiduMap mBaiduMap;
   
    Button staticNavi;
	Button dynamicNavi;
	ImageView back ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_navigation);
		
		 Intent intent = getIntent();
		 staticNavi=(Button) findViewById(R.id.jtdh);
	        back=(ImageView) findViewById(R.id.imageViewd2);
	        staticNavi.setOnClickListener(new Button.OnClickListener()
	        {
	                        @Override
	                        public void onClick(View v) {
//	                        	noParting.setImageDrawable(getResources().getDrawable(R.drawable.aa));//�ڳ���������ͼ��    
//	                        	parting.setImageDrawable(getResources().getDrawable(R.drawable.bb));//�ڳ���������ͼ��    
	                        	
	                        }
	                
	        });
	       
	        
	        back.setOnClickListener(new ImageView.OnClickListener()
	        {
	                        @Override
	                        public void onClick(View v) {
	                     	   Intent intent=new Intent();
	                        	intent.setClass(NavigationActivity.this,MainActivity.class);
	                        	startActivity(intent);
	                        	
	                        }
	                
	        });
	        dynamicNavi=(Button) findViewById(R.id.bi2);
	        dynamicNavi.setOnClickListener(new Button.OnClickListener()
	        {
	                        @Override
	                        public void onClick(View v) {
	                        	
	                        	
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
