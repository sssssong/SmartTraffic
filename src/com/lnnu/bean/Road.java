package com.lnnu.bean;


public class Road {
	
	@Override
	public String toString() {
		return "Road [name=" + name + ", num=" + num + ", startLon=" + startLon
				+ ", startLat=" + startLat + ", endLon=" + endLon + ", endLat="
				+ endLat + ", nowStatus=" + nowStatus + ", prevStatus="
				+ prevStatus + "]";
	}
	public Road() {
		super();
	}
	public Road(String name, int num, double startLon, double startLat,
			double endLon, double endLat, int nowStatus, int prevStatus) {
		super();
		this.name = name;
		this.num = num;
		this.startLon = startLon;
		this.startLat = startLat;
		this.endLon = endLon;
		this.endLat = endLat;
		this.nowStatus = nowStatus;
		this.prevStatus = prevStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getStartLon() {
		return startLon;
	}
	public void setStartLon(double startLon) {
		this.startLon = startLon;
	}
	public double getStartLat() {
		return startLat;
	}
	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}
	public double getEndLon() {
		return endLon;
	}
	public void setEndLon(double endLon) {
		this.endLon = endLon;
	}
	public double getEndLat() {
		return endLat;
	}
	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}
	public int getNowStatus() {
		return nowStatus;
	}
	public void setNowStatus(int nowStatus) {
		this.nowStatus = nowStatus;
	}
	public int getPrevStatus() {
		return prevStatus;
	}
	public void setPrevStatus(int prevStatus) {
		this.prevStatus = prevStatus;
	}
	private String name;
	private int num;
	private double startLon;
	private double startLat;
	private double endLon;
	private double endLat;
	private int nowStatus;
	private int prevStatus;

}
