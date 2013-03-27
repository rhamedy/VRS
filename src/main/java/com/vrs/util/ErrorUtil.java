package com.vrs.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorUtil {
	public static List<KeyValuePair<String, String>> listErrors(BindingResult binding) { 
		List<KeyValuePair<String, String>> errors = new ArrayList<KeyValuePair<String, String>>(); 
		KeyValuePair<String, String> kvp; 
		for (FieldError fe : binding.getFieldErrors()) {
			kvp = new KeyValuePair<String, String>(fe.getField(), fe.getDefaultMessage()); 
			errors.add(kvp); 
		}
		return errors; 
	}
}
