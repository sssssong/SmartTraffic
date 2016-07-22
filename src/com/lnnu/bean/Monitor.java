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
	
	private int num;
	
	public Monitor(){};
	
	public Monitor(int num, String name, double latitude, double longitude) {
		super();
		this.num = num;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "Monitor [num=" + num + ", name=" + name + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}

	private String name;
	private double latitude;
	private double longitude;

}
