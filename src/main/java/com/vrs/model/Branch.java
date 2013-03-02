package com.vrs.model;


/**
 * Branch object is created for the purpose of bundling all the fields
 * related to a branch to ease the process of passing the branch details 
 * back/forth by simply passing the object 
 * 
 *  @author Rafiullah Hamedy
 *  @Date	01-03-2013
 */

public class Branch {
	
	private int id; 
	private String name; 
	private int cityId; 
	private String streetName; 
	private String postcode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	} 
}
