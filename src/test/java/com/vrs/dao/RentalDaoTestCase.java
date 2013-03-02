package com.vrs.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vrs.model.Branch;
import com.vrs.util.TestUtil;

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

	@Autowired
	private RentalDao rentalDao;

	@Test
	public void testGetMaxBranchId1() {
		logger.info("entry testGetMaxBranchId()");
	}

	@Test
	public void testAddBranch1() {

		int branchId = rentalDao.getMaxBranchId() + 1;

		Branch mockBranch = TestUtil.createMockBranch(branchId, "branch1_test",
				5, "bradford1_test", "BD71BB"); // mock branch obj

		assertNull(rentalDao.findBranch(mockBranch.getId())); // assert no
																// branch with
																// same id in db

		rentalDao.addBrunch(mockBranch); // add branch to db

		assertNotNull(rentalDao.findBranch(mockBranch.getId()));
		assertEquals("bradford1_test", rentalDao.findBranch(mockBranch.getId())
				.getStreetName()); //assert that branch is added successfully
		
		rentalDao.deleteBranch(mockBranch.getId()); //delete branch
	}
}
