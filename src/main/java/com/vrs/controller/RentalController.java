package com.vrs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.vrs.model.Branch;
import com.vrs.model.Role;
import com.vrs.model.User;
import com.vrs.model.Vehicle;
import com.vrs.services.RentalServices;
import com.vrs.services.UserServices;
import com.vrs.util.ErrorUtil;
import com.vrs.util.JSONResponse;
import com.vrs.util.JSONUtil;

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
		
		for(Role role: roles) { 
			if(role.getRoleName().equals("admin")) { 
				roleType = "admin";  
				break; 
			} else if(role.getRoleName().equals("technical")) { 
				roleType = "technical"; 
			} else if(role.getRoleName().equals("staff")) { 
				roleType = "staff";
			}
		}
		
		
		mav.setViewName("index"); 
		
		mav.addObject("userType", roleType);
		
		/*
		 * userType/roleType allow the view to render divs accordingly ... for example
		 * if the user is admin display users management, if the user is technical
		 * do not display users display back and restore functionalities. 
		 *  
		 */
		
		if(roleType.equals("admin")) { 
			/*
			 * The user management and branch management will be only displayed to 
			 * administrators. 
			 */
			
			mav.addObject("users", userServices.listUsers());
			mav.addObject("countries", rentalServices.getCountries()); 
			
		} else if(roleType.equals("staff")) { 
			/*
			 * The staff will not see user and branch related data but, list of 
			 * vehicles on the branch they are assigned to as well as that 
			 * branch details.
			 */
			mav.addObject("branch", rentalServices.findBranch(user.getBranchId()));
			mav.addObject("vehicles", rentalServices.getVehicles(user.getBranchId()));
			
		} else if(roleType.equals("technical")) { 
			/*
			 * backup/restore and other configuration functionalities. 
			 */
		}

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

	@RequestMapping(value = "/vehicle/editVehicle", method = RequestMethod.GET)
	public ModelAndView getVehicle(@RequestParam(required=false) String vin) {
		logger.info("entry getVehicle()");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("editVehicle");
		
		List<String> models = new ArrayList<String>(); 

		Vehicle vehicle = rentalServices.findVehicle(vin);
		if(vehicle == null) { 
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
		 * city id is hard coded, we will have to obtain that from the logged in
		 * user somehow.
		 */

		mav.addObject("vehicle", vehicle);
		mav.addObject("branches", branches);
		mav.addObject("makes", makes);
		mav.addObject("models", models);
		
		return mav;
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
	public ModelAndView getBracnh(@RequestParam(required = false) String branchId) { 
		logger.info("entry getBranch()"); 
		
		ModelAndView mav = new ModelAndView(); 
		mav.setViewName("editBranch"); 
		
		Branch branch; 
		
		if(branchId != null && branchId.length() > 0) {
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
		 * we have hardcoded country id (1 for UK) ... we have to 
		 * somehow retrieve this from the logged in user and replace
		 * it here.
		 */
		
		return mav; 
	}
	
	@RequestMapping(value = "/branch/editBranch", method = RequestMethod.POST)
	public @ResponseBody JSONResponse editBranch(@Valid Branch branch, BindingResult result, 
			HttpServletRequest request) { 
		
		if(result.hasErrors()) { 
			return JSONUtil.createFailureResponse(
					"Please provide valid data to the corresponding fields.",
					ErrorUtil.listErrors(result));
		} else { 
			int cityId = Integer.parseInt(request.getParameter("city")); 
			branch.setCityId(cityId); 
			
			if(rentalServices.findBranch(branch.getId()) == null) { 
				rentalServices.addBranch(branch); 
			} else { 
				rentalServices.updateBranch(branch); 
			}
			return JSONUtil
					.createSuccessResponse("The branch information is added/updated to the system.");
		}
	}
}
