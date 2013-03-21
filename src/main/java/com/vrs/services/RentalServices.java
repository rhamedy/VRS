package com.vrs.services;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrs.dao.RentalDao;
import com.vrs.model.Branch;
import com.vrs.model.Vehicle;
import com.vrs.util.KeyValuePair;


/**
 * Is used by Controller class to communicate with dao's and views. 
 * 
 * @author Rafiullah Hamedy
 * @Date   15-03-2012
 */

@Service
public class RentalServices {
	
	private static Logger logger = LoggerFactory.getLogger(RentalServices.class); 
	
	@Autowired 
	private RentalDao rentalDao; 
	
	public List<Map<Integer,String>> getCountries() { 
		logger.info("entry getCountries()"); 
		
		return rentalDao.getCountryList(); 
	}
	
	public List<Map<Integer, String>> getCities(int countryId) {
		logger.info("entry getCities()");
		
		return rentalDao.getCityList(countryId); 
	}
	
	public List<Branch> getBranches(int cityId) { 
		logger.info("entry getBranches()"); 
		
		return rentalDao.getBranchList(cityId); 
	}
	
	public List<Vehicle> getVehicles(int branchId) { 
		logger.info("entry getVehicles()"); 
		
		List<Vehicle> vehicles = rentalDao.getVehicleList(branchId); 
		
		for(Vehicle v: vehicles){ 
			KeyValuePair<String, String> makeModel = rentalDao.getMakeAndModelName(v.getVin()); 
			v.setModel(makeModel.getKey()); 
			v.setMake(makeModel.getValue()); 
		}
		
		return vehicles; 
	}
	
	public KeyValuePair<String, String> getMakeAndModelName(String vin) { 
		logger.info("entry getVehicleMadeAndMOdelName()"); 
		
		return rentalDao.getMakeAndModelName(vin); 
	}
}
