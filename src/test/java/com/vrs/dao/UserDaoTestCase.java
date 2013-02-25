package com.vrs.dao;

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
	
	private static Logger logger = LoggerFactory.getLogger(UserDaoTestCase.class); 
	
	@Autowired 
	private UserDao userDao; 
	
	@Test
	public void testCreateUser1() {
		logger.info("entry testCreateUser1()"); 
		
		User user1 = TestUtil.createTestUser(); //mock user
		
		assertNull(userDao.findUser(user1.getUsername())); //test that user does not exists
		userDao.createUser(user1); //create the mock user
		assertNotNull(userDao.findUser(user1.getUsername())); //test that user exists 
		userDao.deleteUser(user1); //delete the recently created mock user
	}
	
	@Test 
	public void testCreateRole1() { 
		logger.info("entry testCreateRole1()"); 
		
		Role role1 = TestUtil.createTestRole(); //create mock role
		
		assertNull(userDao.findRole(role1.getRoleId()));  //initially no such role exists
		userDao.createRole(role1);  //create the role
		assertNotNull(userDao.findRole(role1.getRoleId())); //confirm role is created
		userDao.deleteRole(role1); //delete test role
	}
	
	@Test 
	public void testCreateUserRoleRelationship() { 
		logger.info("entry testCreateUserRoleRelationship()"); 	
		
		User user1 = TestUtil.createTestUser(); //return mock user
		Role role1 = TestUtil.createTestRole(); //returns mock role 

		userDao.createUser(user1); //create mock user
		userDao.createRole(role1); //create mock role
		
		assertNull(userDao.retrieveUserRole(user1.getUsername())); //initially no relationship
		userDao.createUserRole(user1, role1); //create relationship
		assertNotNull(userDao.retrieveUserRole(user1.getUsername())); //test relationship created
		
		userDao.deleteUserRole(user1, role1); //delete relationship
		assertNull(userDao.retrieveUserRole(user1.getUsername())); //test relationship deleted
		
		userDao.deleteRole(role1); //delete role
		userDao.deleteUser(user1); //delete user
	}
}
