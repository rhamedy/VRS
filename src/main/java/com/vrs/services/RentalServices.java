package com.vrs.services;

import java.sql.Date;
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
import com.vrs.util.MailClient;
import com.vrs.util.MailTemplate;

/**
 * Is used by Controller class to communicate with dao's and views.
 * 
 * @author Rafiullah Hamedy
 * @Date 15-03-2012
 */

@Service
public class RentalServices {

	private static Logger logger = LoggerFactory
			.getLogger(RentalServices.class);

	@Autowired
	private RentalDao rentalDao;

	public List<Map<Integer, String>> getCountries() {
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

		for (Vehicle v : vehicles) {
			v = setMakeAndModel(v);
		}

		return vehicles;
	}

	public Vehicle setMakeAndModel(Vehicle vehicle) {
		KeyValuePair<String, String> makeModel = rentalDao
				.getMakeAndModelName(vehicle.getVin());
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

	public Branch findBranch(int branchId) {
		return rentalDao.findBranch(branchId);
	}

	public int getMaxBranchId() {
		return rentalDao.getMaxBranchId();
	}

	public void addBranch(Branch branch) {
		rentalDao.addBrunch(branch);
	}

	public void updateBranch(Branch branch) {
		rentalDao.updateBranch(branch);
	}

	public KeyValuePair<Integer, String> getCity(int cityId) {
		return rentalDao.getCity(cityId);
	}

	public KeyValuePair<Integer, String> getCountryByCity(int cityId) {
		return rentalDao.getCountryByCity(cityId);
	}

	public void addVehicleBooking(String uuid, String vin, String username, Date startDate,
			Date endDate, boolean insurance, final String systemPassword) {
		int days = (int)((endDate.getTime() - startDate.getTime())/86400000); 
		Vehicle vehicle = rentalDao.findVehicle(vin); 
		vehicle = setMakeAndModel(vehicle); 
		double totalCost = days * vehicle.getDailyCost(); 
		if(insurance) { 
			totalCost += 110; //assuming £110 is the standard insurance amount
		}
		rentalDao.addVehicleBooking(uuid, username, vin, startDate, endDate, insurance, totalCost); 
		vehicle.setAvailable(false); //this will mark vehicle as unavailable
		rentalDao.updateVehicle(vehicle); 
		String emailBody = MailTemplate.getBookingTemplate(uuid, vehicle, startDate, endDate, insurance, totalCost); 
		MailClient.sendEmail(username, systemPassword, "Vehicle Booking Iternity", emailBody); //send email
	}
	
	public List<Vehicle> getVehiclesForHire(int branchId) { 
		logger.info("entry getVehiclesForHire()"); 
		
		List<Vehicle> vehicles = rentalDao.getVehiclesForHire(branchId); 
		
		for (Vehicle v : vehicles) {
			v = setMakeAndModel(v);
		}

		return vehicles;
	}
	
	public void cancelBooking(String vin) { 
		rentalDao.cancelBooking(vin);
		Vehicle vehicle = rentalDao.findVehicle(vin); 
		vehicle.setAvailable(true); 
		rentalDao.updateVehicle(vehicle); 
	}
	
	public void extendBooking(String vin, int days) { 
		rentalDao.extendBooking(vin, days); 
	}
}
