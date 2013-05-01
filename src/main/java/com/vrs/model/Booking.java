package com.vrs.model;

import java.sql.Date;

/**
 * Booking clas with encapsulate all the fields that is required for booking a vehicle.
 * The customer contact, booking start date and end date, insurance details, price and 
 * other useful information will all get bundled into Booking object to make it easy 
 * passing around and increase performance. 
 * 
 *  @author Rafiullah Hamedy
 *  @Date	22-04-2013
 */

public class Booking {
	private String id; 
	private String vehicleVin; 
	private String username; 
	private Date startDate; 
	private Date endDate; 
	private boolean driver = false; 
	private boolean insurance = false; 
	private int extensionCount; 
	private double hireCost = 0; 
	private double damageCost = 0;
	private double chargedAmount = 0; 
	private double remainingAmount = 0; 
	
	public double getChargedAmount() {
		return chargedAmount;
	}
	public void setChargedAmount(double chargedAmount) {
		this.chargedAmount = chargedAmount;
	}
	public double getRemainingAmount() {
		return remainingAmount;
	}
	public void setRemainingAmount(double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVehicleVin() {
		return vehicleVin;
	}
	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public boolean isDriver() {
		return driver;
	}
	public void setDriver(boolean driver) {
		this.driver = driver;
	}
	public boolean isInsurance() {
		return insurance;
	}
	public void setInsurance(boolean insurance) {
		this.insurance = insurance;
	}
	public int getExtensionCount() {
		return extensionCount;
	}
	public void setExtensionCount(int extensionCount) {
		this.extensionCount = extensionCount;
	}
	public double getHireCost() {
		return hireCost;
	}
	public void setHireCost(double hireCost) {
		this.hireCost = hireCost;
	}
	public double getDamageCost() {
		return damageCost;
	}
	public void setDamageCost(double damageCost) {
		this.damageCost = damageCost;
	} 
}
