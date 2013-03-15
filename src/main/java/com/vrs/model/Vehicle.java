package com.vrs.model;

/**
 * Vehicle model object bundles useful attributes of a vehicle into a single
 * object which will make it easy to pass around the object rather than 
 * multiple attributes and even easier when handling multiple vehicles 
 * 
 *  @author Rafiullah Hamedy
 *  @Date	02-03-2013
 */

public class Vehicle {
	
	private String vin; 
	private String numberPlate; 
	private int maxSpeed; 
	private String seating; 
	private String fuel; 
	private int modelId; 
	private boolean available; 
	private int branchId;
	
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public String getSeating() {
		return seating;
	}
	public void setSeating(String seating) {
		this.seating = seating;
	}
	public String getFuel() {
		return fuel;
	}
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	} 
}