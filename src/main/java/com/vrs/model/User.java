package com.vrs.model;

import java.sql.Date;

/**
 * Model object used as a placeholder for different users of the system
 * such as Customer, Receptionist, Normal users, Administrator and Manager. 
 * 
 *  @author Rafiullah Hamedy
 *  @Date	25-02-2013
 */

public class User {
	
	private String firstName;
	private String lastName; 
	private Date dob; 
	private String username;
	private String mobile; 
	private String password;
	private Date createdDate;
	private String mobileNo; 
	private String licenseNo; 
	private Date licenseValidity; 
	private String address; 
	private boolean disabled;
	private int branchId; 
	
	//private Role role; 
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public Date getLicenseValidity() {
		return licenseValidity;
	}
	public void setLicenseValidity(Date licenseValidity) {
		this.licenseValidity = licenseValidity;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	} 
//	public Role getRole() {
//		return role;
//	}
//	public void setRole(Role role) {
//		this.role = role;
//	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
}
