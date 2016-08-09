package com.lnnu.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lnnu.smarttraffic.R;

/**
 * @author suruidong
 * 2016年5月30日
 */
public class ParkInfoActivity extends Activity {
	

    private String num;
  private  TextView name ;
  private TextView address;
  private TextView phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 Intent intent = getIntent();
		 Bundle bundleExtra = intent.getBundleExtra("poiInfo");
		 
		 String nameStr = bundleExtra.getString("name");
		 num = bundleExtra.getString("phone");
		 String addressStr = bundleExtra.getString("address");
		 setContentView(R.layout.activity_parkinfo);
				 
	        name=(TextView) findViewById(R.id.stopname);
	        address=(TextView) findViewById(R.id.sto);
	        phone=(TextView) findViewById(R.id.sto3);
	        
	        name.setText(nameStr);
	        address.setText(addressStr);
	        if (num=="") {
	        	 phone.setText("联系方式："+"暂无");
			}else {
				 phone.setText("联系方式："+num);
			}
	       
	      
	}

	public void back(View v){
		finish();
	}
	
	public void call(View v){
		Intent intent=new Intent();
	    intent.setAction("android.intent.action.CALL");
	    intent.setData(Uri.parse("tel:"+num));
	    startActivity(intent);
	}
	
}
