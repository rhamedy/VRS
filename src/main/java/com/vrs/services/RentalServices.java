package com.vrs.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrs.dao.RentalDao;
import com.vrs.model.Booking;
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

	public void addVehicleBooking(Booking booking, final String systemPassword) {
		int days = (int) ((booking.getEndDate().getTime() - booking
				.getStartDate().getTime()) / 86400000);
		Vehicle vehicle = rentalDao.findVehicle(booking.getVehicleVin());
		vehicle = setMakeAndModel(vehicle);
		double totalCost = days * vehicle.getDailyCost();
		if (booking.isInsurance()) {
			totalCost += 110; // assuming £110 is the standard insurance amount
		}
		booking.setHireCost(totalCost);
		booking.setDriver(false);
		booking.setDamageCost(0);
		rentalDao.addVehicleBooking(booking);
		String emailBody = MailTemplate.getBookingTemplate(vehicle, booking, "Vehicle Booking Iternity", 
				"You have successfully booked a vehicle of the following details.");
		MailClient.sendEmail(booking.getUsername(), systemPassword,
				"Vehicle Booking Iternity", emailBody); // send email
	}

	public List<Vehicle> getVehiclesForHire(int branchId) {
		logger.info("entry getVehiclesForHire()");

		List<Vehicle> vehicles = rentalDao.getVehiclesForHire(branchId);

		for (Vehicle v : vehicles) {
			v = setMakeAndModel(v);
		}

		return vehicles;
	}

	public void cancelBooking(Booking booking, String systemPassword) {
		rentalDao.cancelBooking(booking);
		Vehicle vehicle = rentalDao.findVehicle(booking.getVehicleVin()); 
		setMakeAndModel(vehicle); 
		String emailBody = MailTemplate
				.getBookingTemplate(vehicle, booking,
						"Vehicle Booking Cancellation Iternity",
						"Your request for cancellation of the following booking is been confirmed.");
		MailClient.sendEmail(booking.getUsername(), systemPassword,
				"Vehicle Booking Cancellation Iternity", emailBody); // send email
	}

	public void extendBooking(Booking booking, String systemPassword) {
		int days = (int) ((booking.getEndDate().getTime() - booking
				.getStartDate().getTime()) / 86400000);
		Vehicle vehicle = rentalDao.findVehicle(booking.getVehicleVin());
		vehicle = setMakeAndModel(vehicle);
		double totalCost = days * vehicle.getDailyCost();
		if (booking.isInsurance()) {
			totalCost += 110; // assuming £110 is the standard insurance amount
		}
		booking.setHireCost(totalCost);
		booking.setDriver(false);
		booking.setDamageCost(0);
		rentalDao.extendBooking(booking);

		String emailBody = MailTemplate
				.getBookingTemplate(vehicle, booking,
						"Vehicle Booking Extension Iternity",
						"Your request for extension of the following booking is been confirmed.");
		MailClient.sendEmail(booking.getUsername(), systemPassword,
				"Vehicle Booking Extension Iternity", emailBody); // send email
	}

	public List<String> vehicleBookings(String vin) {
		logger.info("entry vehicleBookings()");
		List<Booking> bookings = rentalDao.vehicleBookings(vin);
		List<String> unAvailableDates = new ArrayList<String>();
		List<String> dates = new ArrayList<String>();

		logger.info("bookings.size : " + bookings.size());

		if (bookings != null) {
			for (Booking booking : bookings) {
				dates = new ArrayList<String>();
				int days = (int) ((booking.getEndDate().getTime() - booking
						.getStartDate().getTime()) / 86400000);
				Date startDate = booking.getStartDate();
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(startDate.getTime());

				for (int i = 0; i <= days; i++) {
					int day = cal.get(Calendar.DAY_OF_MONTH);
					int month = cal.get(Calendar.MONTH);

					dates.add(cal.get(Calendar.YEAR) + "-"
							+ ((month >= 10) ? month : "0" + month) + "-"
							+ ((day >= 10) ? day : "0" + day));
					cal.add(Calendar.DATE, 1);
				}
				unAvailableDates.addAll(dates); 
			}
		}

		return unAvailableDates;
	}

	public List<Booking> listBookings() {
		logger.info("entry listBookings()");

		return rentalDao.listBookings();
	}

	public boolean validateBookingDates(Date startDate, Date endDate, String vin) {
		logger.info("entry validateBookingDates()");

		return rentalDao.validateBookingDates(startDate, endDate, vin);
	}

	public Booking findBooking(String bookingId) {
		logger.info("entry findBooking()");

		return rentalDao.findBooking(bookingId);
	}
	
	public void deleteBookings(String vin) { 
		logger.info("entry deleteBooking()"); 
		
		rentalDao.deleteBookings(vin); 
	}
	
	public void deleteVehicle(String vin) { 
		logger.info("entry deleteVehicle()"); 
		
		rentalDao.deleteVehicle(vin); 
	}
	
	public List<Booking> retrieveVehicleBookingsRecords(String vin, Date date, boolean current) { 
		logger.info("entry retrieveVehicleBookingByDate()"); 
		
		return rentalDao.retrieveVehicleBookingsRecords(vin, date, current); 
	}
	
	public void deleteCity(int cityId) { 
		logger.info("entry deleteCity()"); 
		
		//List<Branch> cityBranches = rentalServices.cityBranches(cityId); 
		//loop through each branch, delete its vehicles. then delete the branch itself
		//after that delete city
		
		//rentalDao.deleteBranchVehicles(branchId); 
		//rentalDao.deleteBranch(branchId); 
		//rentalDao.deleteCity(cityId); 
	}
	
	public void deleteBranch(int branchId) { 
		logger.info("entry deleteBranch()"); 
		
		//rentalDao.deleteBranchVehicles(branchId); 
		//rentalDao.deleteBranch(branchId); 
	}
	
	public void completeBooking(Booking booking) { 
		logger.info("entry completeBooking()"); 
		
		rentalDao.completeBooking(booking); 
	}
	
	public List<Booking> listActiveBookings(boolean active) {
		logger.info("entry listActiveBookings()");

		return rentalDao.listActiveBookings(active);
	}
	
	public List<Vehicle> getUnAvailableVehicleList(int branchId) { 
		List<Vehicle> vehicles = rentalDao.getUnAvailableVehicleList(branchId); 

		for (Vehicle v : vehicles) {
			v = setMakeAndModel(v);
		}

		return vehicles;
	}
}
