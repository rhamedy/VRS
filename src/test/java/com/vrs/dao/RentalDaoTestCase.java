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
import com.vrs.model.Vehicle;
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

		int branchId = rentalDao.getMaxBranchId() + 1;

		Branch mockBranch = TestUtil.createMockBranch(branchId, "branch1_test",
				5, "bradford1_test", "BD71DP");
		
		rentalDao.addBrunch(mockBranch); 
		
		//getMaxBranchId always returns max (id) of all rows in branch table
		//to add a new branch we increment the id and add the branch (id=primary key)
		
		assertEquals(branchId, rentalDao.getMaxBranchId()); 
		
		//above we test that our previously added branch is latest, max one
		
		rentalDao.deleteBranch(branchId); 
		
		//we delete the recently added branch, then getMaxBranchId should
		//return branchId -1 (branchId was previously added mock branch)
		//as follow assertion tests the above fact
		
		assertEquals(branchId - 1, rentalDao.getMaxBranchId()); 
	}

	@Test
	public void testAddBranch1() {

		int branchId = rentalDao.getMaxBranchId() + 1; // new mock branch
														// primary key

		Branch mockBranch = TestUtil.createMockBranch(branchId, "branch1_test",
				5, "bradford1_test", "BD71BB"); // mock branch obj

		assertNull(rentalDao.findBranch(mockBranch.getId())); // assert no
																// branch with
																// same id in db

		rentalDao.addBrunch(mockBranch); // add branch to db

		assertNotNull(rentalDao.findBranch(mockBranch.getId()));
		assertEquals("bradford1_test", rentalDao.findBranch(mockBranch.getId())
				.getStreetName()); // assert that branch is added successfully

		rentalDao.deleteBranch(mockBranch.getId()); // delete branch
	}

	@Test
	public void testUpdateBranch1() {
		logger.info("entry testUpdateBranch1()");

		int branchId = rentalDao.getMaxBranchId() + 1;

		Branch mockBranch = TestUtil.createMockBranch(branchId, "branch1_test",
				5, "bradford1_test", "BD71DP");

		rentalDao.addBrunch(mockBranch);

		assertEquals("bradford1_test", rentalDao.findBranch(mockBranch.getId())
				.getStreetName()); // verify streetName before updating it

		Branch updateBranch = rentalDao.findBranch(mockBranch.getId());
		updateBranch.setStreetName("bradford1_test_updated"); // update the
																// streetName of
																// branch

		rentalDao.updateBranch(updateBranch); // save changes to db

		assertEquals("bradford1_test_updated",
				rentalDao.findBranch(updateBranch.getId()).getStreetName()); // verify
																				// the
																				// changes

		rentalDao.deleteBranch(updateBranch.getId());

	}

	@Test
	public void testAddVehicle1() {
		logger.info("entry testAddVehicle1()");

		Vehicle mockVehicle = TestUtil.createMockVehicle("a1b2c3d4e5_test",
				"AA 23 V6", "Four", "Petrol", true, 16, 1, 220);
		// 16 is model for corolla from model table, 1 is branch for bradford
		// from branch table
		// true means car is available for hire, 220 is max speed

		// assertNull verifies that mockVehicle is not already in database

		assertNull(rentalDao.findVehicle(mockVehicle.getVin()));

		rentalDao.addVehicle(mockVehicle); // add vehicle to database

		assertNotNull(rentalDao.findVehicle(mockVehicle.getVin())); // asserts
																	// vehicle
																	// in db
		assertEquals("AA 23 V6", rentalDao.findVehicle(mockVehicle.getVin())
				.getNumberPlate());

		// assertEquals verify that a vehicle with plateNumber=AA 23 V6 is in db

		rentalDao.deleteVehicle(mockVehicle.getVin()); // delete mockVehicle
														// after testing
	}

	@Test
	public void testUpdateVehicle1() {
		logger.info("entry testUpdateVehicle1()");

		Vehicle mockVehicle = TestUtil.createMockVehicle("a1b2c3d4e5_test",
				"AA 23 V6", "Four", "Petrol", true, 16, 1, 220);

		rentalDao.addVehicle(mockVehicle); // above vehicle added in db

		assertEquals("AA 23 V6", rentalDao.findVehicle(mockVehicle.getVin())
				.getNumberPlate());

		// above assertEquals verifies that a vehicle with plateNumber AA 23 V6
		// is in db
		// lets update a few field of the above vehicle in db

		Vehicle updateVehicle = TestUtil.createMockVehicle("a1b2c3d4e5_test",
				"BB 32 ZX", "Two", "Petrol", true, 16, 1, 220);
		// same vin number however, different plateNumber and seating type

		rentalDao.updateVehicle(updateVehicle);

		assertEquals("BB 32 ZX", rentalDao.findVehicle(updateVehicle.getVin())
				.getNumberPlate());
		assertEquals("Two", rentalDao.findVehicle(updateVehicle.getVin())
				.getSeating());

		// above assertEquals verifies that numberPlate and seating fields are
		// updated

		rentalDao.deleteVehicle(updateVehicle.getVin());
	}

	@Test
	public void testUpdateVehcileStatus1() {
		logger.info("entry testUpdateVehicleStatus1()");

		Vehicle mockVehicle = TestUtil.createMockVehicle("a1b2c3d4e5_test",
				"AA 23 V6", "Four", "Petrol", true, 16, 1, 220);

		rentalDao.addVehicle(mockVehicle);

		assertEquals(true, rentalDao.findVehicle(mockVehicle.getVin())
				.isAvailable());

		// status of mockVehicle in db is true, lets change it to false and
		// update db
		// updateVehicleStatus inverses value of available (if true false, else
		// true)

		rentalDao.updateVehicleStatus(mockVehicle.getVin(),
				mockVehicle.isAvailable());

		assertEquals(false, rentalDao.findVehicle(mockVehicle.getVin())
				.isAvailable());

		rentalDao.deleteVehicle(mockVehicle.getVin());
	}
}
