package com.vrs.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vrs.model.Booking;
import com.vrs.model.Branch;
import com.vrs.model.Role;
import com.vrs.model.User;
import com.vrs.model.Vehicle;
import com.vrs.services.RentalServices;
import com.vrs.services.UserServices;
import com.vrs.util.ErrorUtil;
import com.vrs.util.JSONResponse;
import com.vrs.util.JSONUtil;
import com.vrs.util.KeyValuePair;

@Controller
public class RentalController {

	private static Logger logger = LoggerFactory
			.getLogger(RentalController.class);

	@Autowired
	private RentalServices rentalServices;

	@Autowired
	private UserServices userServices;

	@RequestMapping(value = "/home")
	public ModelAndView homepage() {
		logger.info("inside controller {info}");

		String roleType = "";

		ModelAndView mav = new ModelAndView();

		User user = userServices.findUser(userServices.getCurrentUsername());

		List<Role> roles = userServices.getUserRole(user);

		logger.info("rentalController home roles.size = " + roles.size());

		for (Role role : roles) {
			if (role.getRoleName().equals("admin")) {
				roleType = "admin";
				break;
			} else if (role.getRoleName().equals("technical")) {
				roleType = "technical";
			} else if (role.getRoleName().equals("staff")) {
				roleType = "staff";
			} else if (role.getRoleName().equals("customer")) {
				roleType = "customer";
			}
		}

		mav.setViewName("index");
		mav.addObject("userType", roleType);

		/*
		 * userType/roleType allow the view to render divs accordingly ... for
		 * example if the user is admin display users management, if the user is
		 * technical do not display users display back and restore
		 * functionalities.
		 */

		if (roleType.equals("admin")) {
			/*
			 * The user management and branch management will be only displayed
			 * to administrators.
			 */

			mav.addObject("users", userServices.listUsers());
			logger.info("end of admin block - ...");

		} else if (roleType.equals("staff")) {
			/*
			 * The staff will not see user and branch related data but, list of
			 * vehicles on the branch they are assigned to as well as that
			 * branch details.
			 */

			mav.addObject("branch",
					rentalServices.findBranch(user.getBranchId()));
			mav.addObject("vehicles",
					rentalServices.getVehicles(user.getBranchId()));

		} else if (roleType.equals("technical")) {
			/*
			 * backup/restore and other configuration functionalities.
			 */
		} else if (roleType.equals("customer")) {
			/*
			 * customers will be allowed to browse available vehicles and book a
			 * vehicle for hire
			 */

		}

		mav.addObject("countries", rentalServices.getCountries());
		mav.addObject("user", user);

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
	public @ResponseBody
	List<Branch> getBranches(@RequestParam String cityId) {
		logger.info("entry getBranches()");

		int parsedCityId = Integer.parseInt(cityId);

		return rentalServices.getBranches(parsedCityId);
	}

	@RequestMapping(value = "/home/public/vehicles")
	public @ResponseBody
	List<Vehicle> getVehicles(@RequestParam String branchId) {
		logger.info("entry getVehicles()");

		int parseBranchId = Integer.parseInt(branchId);

		return rentalServices.getVehicles(parseBranchId);
	}

	@RequestMapping(value = "/vehicle/getVehicle", method = RequestMethod.GET)
	public @ResponseBody
	Vehicle getVehicle(@RequestParam(required = true) String vin) {
		logger.info("entry getVehicle()");

		Vehicle vehicle = rentalServices.findVehicle(vin);
		KeyValuePair<String, String> makeModel = rentalServices
				.getMakeAndModelName(vin);
		vehicle.setMake(makeModel.getValue());
		vehicle.setModel(makeModel.getKey());

		return vehicle;
	}

	@RequestMapping(value = "/vehicle/editVehicle", method = RequestMethod.GET)
	public ModelAndView editVehicle(@RequestParam(required = false) String vin) {
		logger.info("entry editVehicle()");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("editVehicle");

		List<String> models = new ArrayList<String>();

		Vehicle vehicle = rentalServices.findVehicle(vin);
		if (vehicle == null) {
			vehicle = new Vehicle();
			vehicle.setAvailable(true);
		} else {
			vehicle = rentalServices.setMakeAndModel(vehicle);
			int makeId = rentalServices.getMakeId(vehicle.getMake());
			models = rentalServices.listMakeModels(makeId);
		}

		List<Branch> branches = rentalServices.getBranches(5);
		List<String> makes = rentalServices.listMakes();

		/*
		 * to be fixed city id is hard coded, we will have to obtain that from
		 * the logged in user somehow.
		 */

		mav.addObject("vehicle", vehicle);
		mav.addObject("branches", branches);
		mav.addObject("makes", makes);
		mav.addObject("models", models);

		return mav;
	}

	@RequestMapping(value = "/vehicle/hireVehicle", method = RequestMethod.POST)
	public @ResponseBody
	JSONResponse hireVehicle(@RequestParam(required = true) String vin,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(required = true) String insurance) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		Date sqlStartDate = null;
		Date sqlEndDate = null;
		try {
			sqlStartDate = new Date(formatter.parse(startDate).getTime());
			sqlEndDate = new Date(formatter.parse(endDate).getTime());
		} catch (ParseException pe) {
			return JSONUtil
					.createFailureResponse("Invalid date format selected.");
		}

		if (vin == null || vin.length() <= 0) {
			return JSONUtil.createFailureResponse("Missing vehicle identity.");
		} else if (startDate == null) {
			return JSONUtil
					.createFailureResponse("startDate is not specified.");
		} else if (endDate == null) {
			return JSONUtil.createFailureResponse("endDate is not specificed.");
		} else if ((sqlEndDate.getTime() - sqlStartDate.getTime()) < 1) {
			return JSONUtil
					.createFailureResponse("Invalid start date and end date choosen.");
		} else if (insurance.length() <= 0) {
			return JSONUtil
					.createFailureResponse("Whether insurance required is not specified.");
		}

		String username = userServices.getCurrentUsername();
		boolean insuranceBool = insurance.equals("yes") ? true : false;
		String uuid = UUID.randomUUID().toString();
		String systemPassword = userServices
				.getPasswordByUsername("oscar.vehicle.rental.system@gmail.com");

		Booking booking = new Booking();
		booking.setId(uuid);
		booking.setUsername(username);
		booking.setVehicleVin(vin);
		booking.setStartDate(sqlStartDate);
		booking.setEndDate(sqlEndDate);
		booking.setInsurance(insuranceBool);

		rentalServices.addVehicleBooking(booking, systemPassword);

		return JSONUtil
				.createSuccessResponse("The booking has been added to the system.");

	}

	@RequestMapping(value = "/vehicle/editVehicle", method = RequestMethod.POST)
	public @ResponseBody
	JSONResponse editVehicle(@Valid Vehicle vehicle, BindingResult binding,
			HttpServletRequest request) {
		logger.info("entry editVehicle() POST");

		if (binding.hasErrors()) {
			return JSONUtil.createFailureResponse(
					"Please provide valid data to the corresponding fields.",
					ErrorUtil.listErrors(binding));
		} else if (vehicle.getVin() != null
				&& vehicle.getVin().trim().length() <= 0) {
			return JSONUtil
					.createFailureResponse("Please provide a valid VIN (Vehicle identification number) for this vehicle.");
		} else {
			int branchId = Integer.parseInt(request.getParameter("branch"));
			int modelId = rentalServices.getModelId(request
					.getParameter("model"));

			vehicle.setModelId(modelId);
			vehicle.setBranchId(branchId);

			if (rentalServices.findVehicle(vehicle.getVin()) == null) {
				rentalServices.addVehicle(vehicle);
			} else {
				rentalServices.updateVehicle(vehicle);
			}
			return JSONUtil
					.createSuccessResponse("The vehicle information is added/updated to the system.");
		}
	}

	@RequestMapping(value = "/vehicle/listModels", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, List<String>> getModels(@RequestParam String makeName) {
		int makeId = rentalServices.getMakeId(makeName);
		List<String> models = rentalServices.listMakeModels(makeId);
		Map<String, List<String>> modelsMap = new HashMap<String, List<String>>();
		modelsMap.put("models", models);
		return modelsMap;
	}

	@RequestMapping(value = "/branch/editBranch", method = RequestMethod.GET)
	public ModelAndView getBracnh(
			@RequestParam(required = false) String branchId) {
		logger.info("entry getBranch()");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("editBranch");

		Branch branch;

		if (branchId != null && branchId.length() > 0) {
			int branchIdParsed = Integer.parseInt(branchId);
			branch = rentalServices.findBranch(branchIdParsed);
			mav.addObject("existingBranch", "false");

		} else {
			int newBranchId = rentalServices.getMaxBranchId();
			branch = new Branch();
			branch.setId(++newBranchId);
			mav.addObject("existingBranch", "true");
		}

		mav.addObject("cities", rentalServices.getCities(1));
		mav.addObject("branch", branch);

		/*
		 * we have hardcoded country id (1 for UK) ... we have to somehow
		 * retrieve this from the logged in user and replace it here.
		 */

		return mav;
	}

	@RequestMapping(value = "/branch/editBranch", method = RequestMethod.POST)
	public @ResponseBody
	JSONResponse editBranch(@Valid Branch branch, BindingResult result,
			HttpServletRequest request) {

		if (result.hasErrors()) {
			return JSONUtil.createFailureResponse(
					"Please provide valid data to the corresponding fields.",
					ErrorUtil.listErrors(result));
		} else {
			int cityId = Integer.parseInt(request.getParameter("city"));
			branch.setCityId(cityId);

			if (rentalServices.findBranch(branch.getId()) == null) {
				rentalServices.addBranch(branch);
			} else {
				rentalServices.updateBranch(branch);
			}
			return JSONUtil
					.createSuccessResponse("The branch information is added/updated to the system.");
		}
	}

	@RequestMapping(value = "/vehicle/vehiclesForHire", method = RequestMethod.GET)
	public @ResponseBody
	List<Vehicle> getVehiclesForHire(@RequestParam String branchId) {
		logger.info("entry getVehicles()");

		int parseBranchId = Integer.parseInt(branchId);

		return rentalServices.getVehiclesForHire(parseBranchId);
	}

	@RequestMapping(value = "/vehicle/cancelBooking", method = RequestMethod.GET)
	public @ResponseBody
	JSONResponse cancelBooking(@RequestParam(required = true) String vin) {
		Vehicle vehicle = rentalServices.findVehicle(vin);
		if (vehicle == null) {
			return JSONUtil
					.createFailureResponse("Error.Vehicle cannot be found.");
		}
		rentalServices.cancelBooking(vin);
		// send an email saying booking has been cancelled.
		return JSONUtil
				.createSuccessResponse("The booking has been cancelled successfully.");
	}

	@RequestMapping(value = "/vehicle/extendBooking", method = RequestMethod.GET)
	public @ResponseBody
	JSONResponse extendBooking(@RequestParam(required = true) String vin,
			@RequestParam(required = true) String days) {
		Vehicle vehicle = rentalServices.findVehicle(vin);
		if (vehicle == null) {
			return JSONUtil
					.createFailureResponse("Error. Vehicle cannot be found.");
		}

		int daysInt;

		try {
			daysInt = Integer.parseInt(days);
		} catch (NumberFormatException nfe) {
			return JSONUtil
					.createFailureResponse("Error. Provide valid number of days (in number).");
		}

		rentalServices.extendBooking(vin, daysInt);
		// send an email about the hire period extension.
		return JSONUtil
				.createSuccessResponse("Vehicle hire period extended successfully.");
	}
	
	@RequestMapping(value = "/vehicle/bookingDates", method = RequestMethod.GET)
	public @ResponseBody List<String> unAvailableDates(@RequestParam String vin) { 
		logger.info("entry unAvailableDates()");
		
		//returning only the list part of map for specific user which contains 
		//bookings for other users also
		return rentalServices.vehicleBookings(vin).get("rhamedy@student.bradford.ac.uk"); 
	}
}
