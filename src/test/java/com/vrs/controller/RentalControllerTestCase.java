package com.vrs.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/vrs-servlet.xml",
		"file:src/main/resources/vrs-auth.xml",
		"file:src/main/resources/vrs-datasource.xml" })
public class RentalControllerTestCase {
	
	private static Logger logger = LoggerFactory.getLogger(RentalControllerTestCase.class); 
	
	@Autowired
	private RentalController rentalController;

	@Test
	public void testHomepage() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");

		assertEquals(mav.getViewName(), rentalController.homepage()
				.getViewName());
		
		logger.info("test one successfull!.");
	}
}
