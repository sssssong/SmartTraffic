package com.lnnu.smarttraffic;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;



/**
 * @author guodai
 * 2016年5月30日
 * 全局Application类
 */
public class AppAplication extends Application{
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		 SDKInitializer.initialize(getApplicationContext()); 
	}
	


}
