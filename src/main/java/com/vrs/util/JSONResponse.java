package com.vrs.util;

import java.util.List;


public class JSONResponse {
	
	private String status; 
	private String message; 
	private List<KeyValuePair<String, String>> errors;
	
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
