package com.vrs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RentalController {
	
	private static Logger logger = LoggerFactory.getLogger(RentalController.class); 
	
	@RequestMapping(value = "/home")
	public ModelAndView homepage() { 
		logger.info("inside controller {info}"); 
		ModelAndView mav = new ModelAndView(); 
		
		mav.setViewName("index");
		logger.debug("at the end of the controller{degub}"); 
		
		return mav; 
	}
}
