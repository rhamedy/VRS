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
			v = setMakeAndModel(v); 
		}
		
		return vehicles; 
	}
	
	public Vehicle setMakeAndModel(Vehicle vehicle) { 
		KeyValuePair<String, String> makeModel = rentalDao.getMakeAndModelName(vehicle.getVin()); 
		vehicle.setModel(makeModel.getKey()); 
		vehicle.setMake(makeModel.getValue());
		
		return vehicle;
	}
	
	public KeyValuePair<String, String> getMakeAndModelName(String vin) { 
		logger.info("entry getVehicleMadeAndMOdelName()"); 
		
		return rentalDao.getMakeAndModelName(vin); 
	}
	
	public Vehicle findVehicle(String vin) { 
		logger.info("entry findVehicle()"); 
		
		return rentalDao.findVehicle(vin); 
	}
	
	public List<String> listMakes() { 
		logger.info("entry listMakes()"); 
		
		return rentalDao.listMakes();
	}
	
	public List<String> listMakeModels(int makeId) { 
		logger.info("entry listMakeModels()"); 
		
		return rentalDao.listMakeModels(makeId);
	}
	
	public int getMakeId(String makeName) { 
		logger.info("entry getMakeId()"); 
		
		return rentalDao.getMakeId(makeName);
	}
	
	public void addVehicle(Vehicle vehicle) { 
		logger.info("entry addVehicle()"); 
		
		rentalDao.addVehicle(vehicle); 
	}
	
	public void updateVehicle(Vehicle vehicle) { 
		logger.info("entry updateVehicle()"); 
		
		rentalDao.updateVehicle(vehicle); 
	}
	
	public int getModelId(String modelName) { 
		return rentalDao.getModelId(modelName);
	}
}
