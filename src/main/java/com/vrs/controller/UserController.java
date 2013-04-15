package com.vrs.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vrs.model.Branch;
import com.vrs.model.Role;
import com.vrs.model.User;
import com.vrs.services.RentalServices;
import com.vrs.services.UserServices;
import com.vrs.util.CustomSqlDateEditor;
import com.vrs.util.ErrorUtil;
import com.vrs.util.JSONResponse;
import com.vrs.util.JSONUtil;
import com.vrs.util.KeyValuePair;

/**
 * UserController serves user related requests coming from web browser, make use
 * of UserServices to interact with UserDao in order to exchange information
 * back/forth with database.
 * 
 * @author Rafiullah Hamedy
 * @Date 25-02-2013
 * 
 */

@Controller
public class UserController {

	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private UserServices userServices;

	@Autowired
	private RentalServices rentalServices;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		binder.registerCustomEditor(Date.class, new CustomSqlDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/home/public")
	public ModelAndView showHomepage() {
		logger.info("entry showHomepage()");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("public");

		User user = new User();

		List<Map<Integer, String>> countries = rentalServices.getCountries();

		logger.info("countries size{}", countries.size());

		mav.addObject("countries", countries);
		mav.addObject("user", user);

		return mav;
	}

	@RequestMapping(value = "/user/editUser", method = RequestMethod.GET)
	public ModelAndView editUser(@RequestParam(required = false) String username) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("editUser");

		User user = userServices.findUser(username);
		List<Role> userRoles = new ArrayList<Role>();

		if (user == null) {
			user = new User();
			user.setDisabled(true);
			user.setUsername("new");
		} else {
			userRoles = userServices.getUserRole(user);
			Branch branch = rentalServices.findBranch(user.getBranchId());
			KeyValuePair<Integer, String> city = rentalServices.getCity(branch
					.getCityId());
			KeyValuePair<Integer, String> country = rentalServices
					.getCountryByCity(branch.getCityId());
			
			mav.addObject("branch", branch); 
			mav.addObject("city", city); 
			mav.addObject("country", country);
		}

		List<Role> allRoles = userServices.listRoles();
		List<Role> nonUserRoles = userServices
				.nonUserRoles(allRoles, userRoles);

		logger.info("user roles are as follow : ");
		for (Role r : userRoles) {
			logger.info("rolename : " + r.getRoleName());
		}
		logger.info("roles user is not assigned to are as follow :");
		for (Role r : nonUserRoles) {
			logger.info("non-user rolename: " + r.getRoleName());
		}

		mav.addObject("user", user);
		mav.addObject("username", user.getUsername());
		mav.addObject("userRoles", userRoles);
		mav.addObject("nonUserRoles", nonUserRoles);
		mav.addObject("countries", rentalServices.getCountries()); 

		return mav;
	}

	@RequestMapping(value = "/user/editUser", method = RequestMethod.POST)
	public @ResponseBody
	JSONResponse editUser(@Valid User user, BindingResult binding,
			@RequestParam String username, HttpServletResponse response,
			HttpServletRequest request) {

		if (binding.hasErrors()) {
			return JSONUtil.createFailureResponse(
					"Updating user fields failed.",
					ErrorUtil.listErrors(binding));
		} else if (request.getParameter("role") == null) {
			return JSONUtil
					.createFailureResponse("Select atleast one role for this user.");
		} else if(user.getBranchId() == 0) { 
			return JSONUtil
					.createFailureResponse("Select a branch for the user.");
		} else {
			user.setUsername(username);
			if (userServices.findUser(username) == null) {
				// in case new user is added
				userServices.createUser(user);
			} else {
				// in case an existing user is edited
				userServices.updateUser(user);
			}

			List<Role> newRoles = new ArrayList<Role>();
			String[] rolesId = request.getParameterValues("role");

			for (String r : rolesId) {
				int roleId = Integer.parseInt(r);
				newRoles.add(userServices.findRole(roleId));
				logger.info("new selected role : "
						+ userServices.findRole(roleId).getRoleName());
			}

			userServices.updateUserRoles(user, newRoles);

			return JSONUtil
					.createSuccessResponse("Changes are to the user fields are updated successfully!");
		}

	}

	@RequestMapping(value = "/home/public/accountRequest", method = RequestMethod.POST)
	public @ResponseBody
	JSONResponse accountRequest(@Valid User user, BindingResult binding,
			HttpServletResponse response) {
		logger.info("entry accountRequest()");

		if (binding.hasErrors()) {
			return JSONUtil.createFailureResponse(
					"Please provide valid data to the corresponding fields.",
					ErrorUtil.listErrors(binding));
		} else if (user.getUsername() == null) {
			return JSONUtil
					.createFailureResponse("Please provide a valid email address as username.");
		} else {
			user.setDisabled(true);
			userServices.createUser(user);
			return JSONUtil
					.createSuccessResponse("Your account request is stored. You will "
							+ "receive a confirmation email soon.");
		}
	}

	@RequestMapping(value = "/home/public/usernameAvailable", method = RequestMethod.GET)
	public @ResponseBody
	JSONResponse isUsernameAvailable(@RequestParam String username) {
		if (userServices.isUsernameExists(username)) {
			return JSONUtil
					.createFailureResponse("The choosen username is already in use.");
		} else {
			return JSONUtil.createSuccessResponse();
		}
	}
}
