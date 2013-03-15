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

import com.vrs.services.RentalServices;
import com.vrs.services.UserServices;

@Controller
public class UserController {

	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private UserServices userServices;

	@Autowired
	private RentalServices rentalServices;

	@RequestMapping(value = "/home/public")
	public ModelAndView showHomepage() {
		logger.info("entry showHomepage()");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("public");

		List<Map<Integer, String>> countries = rentalServices.getCountries();

		logger.info("countries size{}", countries.size());

		// for(Map<Integer, String> m: countries) {
		// for(Map.Entry<Integer, String> entry: m.entrySet()) {
		// logger.info("country.key: {}, country.name: {}", entry.getKey(),
		// entry.getValue());
		// }
		// }

		mav.addObject("countries", countries);

		return mav;
	}

	@RequestMapping(value = "/home/public/cities")
	public @ResponseBody
	List<Map<Integer, String>> getCities(@RequestParam String countryId) {
		int parsedCountryId = Integer.parseInt(countryId);
		return rentalServices.getCities(parsedCountryId); 
	}
}
