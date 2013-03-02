package com.vrs.dao;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * RentalDaoTestCase tests different functionalities of RentalDao class with
 * test data to confirm that the units function as expected.
 * 
 * @author Rafiullah Hamedy
 * @Date 02-03-2013
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/vrs-servlet.xml",
		"file:src/main/resources/vrs-auth.xml",
		"file:src/main/resources/vrs-datasource.xml" })
public class RentalDaoTestCase {

	private static Logger logger = LoggerFactory
			.getLogger(RentalDaoTestCase.class);
	
	
}
