package com.vrs.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models a response with a status message indicating 
 * success or failure, a message and a list of errors. An object 
 * of this class will be used through out the system to inform the 
 * browser of activities status. 
 * 
 * @author Rafiullah Hamedy
 * @Date 26-03-2013
 * 
 */

public class JSONResponse {
	
	private String status; 
	private String message; 
	private List<KeyValuePair<String, String>> errors; 
	
	public JSONResponse() { 
		this.errors = new ArrayList<KeyValuePair<String, String>>();
		this.message = ""; 
		this.status = "failure";
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<KeyValuePair<String, String>> getErrors() {
		return errors;
	}
	public void setErrors(List<KeyValuePair<String, String>> errors) {
		this.errors = errors;
	} 
}
