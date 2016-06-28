package com.lnnu.smarttraffic;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;


/**
 * @author guodai
 * 2016年6月28日
 * 用于更新地图到指定坐标，并放大
 */
public class CommonMethod {

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
