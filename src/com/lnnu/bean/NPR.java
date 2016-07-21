package com.lnnu.bean;

public class NPR {
	
	public NPR() {
		super();
	}
	public NPR(int id, String info, double startLon, double startLat,
			double endLon, double endLat) {
		super();
		this.id = id;
		this.info = info;
		this.startLon = startLon;
		this.startLat = startLat;
		this.endLon = endLon;
		this.endLat = endLat;
	}
	@Override
	public String toString() {
		return "NPR [id=" + id + ", info=" + info + ", startLon=" + startLon
				+ ", startLat=" + startLat + ", endLon=" + endLon + ", endLat="
				+ endLat + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
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
	private int id;
	private String info;
	private double startLon;
	private double startLat;
	private double endLon;
	private double endLat;
	

}
