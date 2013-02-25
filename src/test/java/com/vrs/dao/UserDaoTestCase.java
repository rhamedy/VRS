package com.vrs.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vrs.model.Role;
import com.vrs.model.User;
import com.vrs.util.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/vrs-servlet.xml",
		"file:src/main/resources/vrs-auth.xml",
		"file:src/main/resources/vrs-datasource.xml" })
public class UserDaoTestCase {

	private static Logger logger = LoggerFactory
			.getLogger(UserDaoTestCase.class);

	@Autowired
	private UserDao userDao;

	@Test
	public void testCreateUser1() {
		logger.info("entry testCreateUser1()");

		User user1 = TestUtil.createTestUser(); // mock user

		assertNull(userDao.findUser(user1.getUsername())); // test that user
															// does not exists
		userDao.createUser(user1); // create the mock user
		assertNotNull(userDao.findUser(user1.getUsername())); // test that user
																// exists
		userDao.deleteUser(user1); // delete the recently created mock user
	}

	@Test
	public void testCreateRole1() {
		logger.info("entry testCreateRole1()");

		Role role1 = TestUtil.createTestRole(999, "admin_test"); // create mock
																	// role

		assertNull(userDao.findRole(role1.getRoleId())); // initially no such
															// role exists
		userDao.createRole(role1); // create the role
		assertNotNull(userDao.findRole(role1.getRoleId())); // confirm role is
															// created
		userDao.deleteRole(role1); // delete test role
	}

	@Test
	public void testCreateUserRoleRelationship() {
		logger.info("entry testCreateUserRoleRelationship()");

		User user1 = TestUtil.createTestUser(); // return mock user
		Role role1 = TestUtil.createTestRole(999, "admin_test"); // returns mock
																	// role

		userDao.createUser(user1); // create mock user
		userDao.createRole(role1); // create mock role

		assertNull(userDao.retrieveUserRole(user1.getUsername())); // initially
																	// no
																	// relationship
		userDao.createUserRole(user1, role1); // create relationship
		assertNotNull(userDao.retrieveUserRole(user1.getUsername())); // test
																		// relationship
																		// created

		userDao.deleteUserRole(user1, role1); // delete relationship
		assertNull(userDao.retrieveUserRole(user1.getUsername())); // test
																	// relationship
																	// deleted

		userDao.deleteRole(role1); // delete role
		userDao.deleteUser(user1); // delete user
	}

	@Test
	public void testUpdateUser1() {
		logger.info("entry testUpdateUser1()");

		User user1 = TestUtil.createTestUser();

		userDao.createUser(user1);
		assertEquals("raf_test1", userDao.findUser(user1.getUsername())
				.getFirstName());  //verifying created users firstn name

		user1.setFirstName("raf_test1_updated"); //updating the user obj
		user1.setLastName("hamedy_test1_updated");

		userDao.updateUser(user1); //saving the changes to db

		assertEquals("raf_test1_updated", userDao.findUser(user1.getUsername())
				.getFirstName());  //verifying the changes to first name
		assertEquals("hamedy_test1_updated",
				userDao.findUser(user1.getUsername()).getLastName());

		userDao.deleteUser(user1);
	}

	@Test
	public void testUpdateRole1() {
		logger.info("entry testUpdateRole1()");

		Role role1 = TestUtil.createTestRole(999, "admin_test");

		userDao.createRole(role1);

		assertEquals("admin_test", userDao.findRole(999).getRoleName()); //verifying role name
		role1.setRoleName("admin_test_updated"); //update the role name in object
		userDao.updateRole(role1); //save the updates to db
		assertEquals("admin_test_updated", userDao.findRole(999).getRoleName()); //verify updates

		userDao.deleteRole(role1); 
	}

	@Test
	public void testUpdateUserRoleRelation1() {
		logger.info("entry testUpdateUserRoleRelation()");

		User user1 = TestUtil.createTestUser();
		Role role1 = TestUtil.createTestRole(999, "admin_test");

		userDao.createRole(role1);  //save mock user1 to db
		userDao.createUser(user1); //save mock role1 to db
		userDao.createUserRole(user1, role1); //save relations for user1 and role 1

		assertEquals(999, userDao.retrieveUserRole(user1.getUsername())
				.getRoleId()); //verify that user1 has role_id 999

		Role role2 = TestUtil.createTestRole(888, "admin_test2"); //create another mock role obj. with id 888
		userDao.createRole(role2); //save the role into db
		userDao.updateUserRole(user1, role2); //update the user1's role from 999 to 888

		assertEquals(888, userDao.retrieveUserRole(user1.getUsername()) 
				.getRoleId()); //verify update success
		
		userDao.deleteUserRole(user1, role2); 
		userDao.deleteRole(role2); 
		userDao.deleteRole(role1); 
		userDao.deleteUser(user1); 
	}
}
