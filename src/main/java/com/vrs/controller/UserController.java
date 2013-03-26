package com.vrs.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vrs.model.Role;
import com.vrs.model.User;
import com.vrs.services.RentalServices;
import com.vrs.services.UserServices;
import com.vrs.util.CustomSqlDateEditor;

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

		// for(Map<Integer, String> m: countries) {
		// for(Map.Entry<Integer, String> entry: m.entrySet()) {
		// logger.info("country.key: {}, country.name: {}", entry.getKey(),
		// entry.getValue());
		// }
		// }

		mav.addObject("countries", countries);
		mav.addObject("user", user);

		return mav;
	}

	@RequestMapping(value = "/user/editUser", method = RequestMethod.GET)
	public ModelAndView editUser(@RequestParam String username) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("editUser");

		User user = userServices.findUser(username);
		
		List<Role> allRoles = userServices.listRoles(); 
		List<Role> userRoles = userServices.getUserRole(user); 
		List<Role> nonUserRoles = userServices.nonUserRoles(allRoles, userRoles); 
		
		mav.addObject("user", user);
		mav.addObject("username", user.getUsername());
		mav.addObject("userRoles", userRoles); 
		mav.addObject("nonUserRoles", nonUserRoles); 

		return mav;
	}

	@RequestMapping(value = "/user/editUser", method = RequestMethod.POST)
	public void editUser(@Valid User user, BindingResult binding,
			@RequestParam String username, HttpServletResponse response) {

		if (binding.hasErrors()) {
			for (FieldError fe : binding.getFieldErrors()) {
				logger.info("binding error : {}", fe.getDefaultMessage());
				response.addHeader("error_" + fe.getField(),
						fe.getDefaultMessage());
			}
			response.setStatus(302);
		} else {
			user.setUsername(username); 
			userServices.updateUser(user); 
			
			response.setStatus(200);
		}

	}

	@RequestMapping(value = "/home/public/accountRequest", method = RequestMethod.POST)
	public void accountRequest(@Valid User user, BindingResult binding,
			HttpServletResponse response) {
		logger.info("entry accountRequest()");

		if (binding.hasErrors()) {
			for (FieldError fe : binding.getFieldErrors()) {
				logger.info("binding error : {}", fe.getDefaultMessage());
				response.addHeader("error_" + fe.getField(),
						fe.getDefaultMessage());
			}
			response.setStatus(302);
		} else {

			userServices.createUser(user);
			response.setStatus(200);
		}
	}
}
