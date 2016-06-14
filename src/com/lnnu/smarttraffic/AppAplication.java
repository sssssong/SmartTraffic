package com.lnnu.smarttraffic;

import java.io.FileOutputStream;
import java.io.InputStream;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;



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
				SDKInitializer.initialize(this);
			try {
				copyMapStyle();
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}
	
	/**
	 * @author guodai
	 * 2016年6月12日
	 * 将地图样式文件从assets目录拷贝到存储空间
	 */
	public void copyMapStyle() throws Exception{
		AssetManager am=this.getAssets();
		InputStream is = am.open("dark");
		FileOutputStream fos = openFileOutput("dark", Context.MODE_PRIVATE);
		byte [] buffer=new byte[1024];
		int len=-1;
		while((len=is.read(buffer))!=-1){
			fos.write(buffer,0,len);
		}
		fos.close();
		is.close();
	}

}
