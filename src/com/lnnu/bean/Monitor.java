package com.lnnu.bean;

public class Monitor {
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	private int num;
	
	
	

	public Monitor(int num, String name, double latitude, double longitude,
			String url) {
		super();
		this.num = num;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.url = url;
	}
	@Override
	public String toString() {
		return "Monitor [num=" + num + ", name=" + name + ", latitude="
				+ latitude + ", longitude=" + longitude + ", url=" + url + "]";
	}

	private String name;
	private double latitude;
	private double longitude;
	private String url;

}
