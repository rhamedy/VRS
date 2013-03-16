package com.vrs.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vrs.model.Branch;
import com.vrs.model.Vehicle;
import com.vrs.services.RentalServices;
import com.vrs.services.UserServices;

@Controller
public class RentalController {
	
	private static Logger logger = LoggerFactory.getLogger(RentalController.class); 
	
	@Autowired
	private RentalServices rentalServices; 
	
	@Autowired 
	private UserServices userServices; 
	
	@RequestMapping(value = "/home")
	public ModelAndView homepage() { 
		logger.info("inside controller {info}"); 
		ModelAndView mav = new ModelAndView(); 
		
		mav.setViewName("index");
		
		return mav; 
	}
	
	@RequestMapping(value = "/home/public/cities")
	public @ResponseBody
	List<Map<Integer, String>> getCities(@RequestParam String countryId) {
		logger.info("entry getCities()"); 
		
		int parsedCountryId = Integer.parseInt(countryId);
		
		return rentalServices.getCities(parsedCountryId); 
	}
	
	@RequestMapping(value = "/home/public/branches")
	public @ResponseBody List<Branch> getBranches(@RequestParam String cityId) { 
		logger.info("entry getBranches()"); 
		
		int parsedCityId = Integer.parseInt(cityId); 
		
		return rentalServices.getBranches(parsedCityId);  
	}
	
	@RequestMapping(value = "/home/public/vehicles")
	public @ResponseBody List<Vehicle> getVehicles(@RequestParam String branchId) { 
		logger.info("entry getVehicles()"); 
		
		int parseBranchId = Integer.parseInt(branchId); 
		
		return rentalServices.getVehicles(parseBranchId); 
	}
}
