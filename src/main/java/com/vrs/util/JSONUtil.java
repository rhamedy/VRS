package com.vrs.util;

import java.util.List;

/**
 * JSONUtility class will be used to rapidly construct different 
 * JSON responses to be returned to browser
 * 
 * @author Rafiullah Hamedy
 * @Date 26-03-2013
 * 
 */

public class JSONUtil {
	
	public static JSONResponse createSuccessResponse() {
		JSONResponse jsonResponse = new JSONResponse(); 
		jsonResponse.setStatus("success"); 
		return jsonResponse; 
	}
	
	public static JSONResponse createSuccessResponse(String message) { 
		JSONResponse jsonResponse = new JSONResponse(); 
		jsonResponse.setStatus("success"); 
		jsonResponse.setMessage(message); 
		return jsonResponse; 
	}
	
	public static JSONResponse createFailureResponse() { 
		JSONResponse jsonResponse = new JSONResponse(); 
		jsonResponse.setStatus("failure"); 
		return jsonResponse; 
	}
	
	public static JSONResponse createFailureResponse(String message) { 
		JSONResponse jsonResponse = new JSONResponse(); 
		jsonResponse.setStatus("failure"); 
		jsonResponse.setMessage(message); 
		return jsonResponse; 
	}
	
	public static JSONResponse createFailureResponse(List<KeyValuePair<String, String>> errors) { 
		JSONResponse jsonResponse = new JSONResponse(); 
		jsonResponse.setStatus("failure"); 
		jsonResponse.setErrors(errors); 
		return jsonResponse; 
	}
	
	public static JSONResponse createFailureResponse(String message, List<KeyValuePair<String, String>> errors) { 
		JSONResponse jsonResponse = new JSONResponse(); 
		jsonResponse.setStatus("failure"); 
		jsonResponse.setMessage(message); 
		jsonResponse.setErrors(errors); 
		return jsonResponse; 
	}
}
