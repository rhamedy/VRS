package com.vrs.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrs.dao.UserDao;
import com.vrs.model.Role;
import com.vrs.model.User;

@Service
public class UserServices {
	
	private static Logger logger = LoggerFactory.getLogger(UserServices.class); 
	
	@Autowired
	private UserDao userDao; 
	
	public User findUser(String username) {
		logger.info("entry findUser"); 
		
		return userDao.findUser(username); 
	}
	
	public Role findRole(int roleId) { 
		logger.info("entry findRole"); 
		
		return userDao.findRole(roleId); 
	}
	
	public void deleteUser(User user) {
		logger.info("entry deleteUser"); 
		
		userDao.deleteUser(user); 
	}
	
	public void deleteRole(Role role) {
		logger.info("entry deleteRole"); 
		
		userDao.deleteRole(role); 
	}
	
	public void deleteUserRole(User user, Role role) {
		logger.info("entry deleteUserRole");
		
		userDao.deleteUserRole(user, role); 
	}
	
	public void createUser(User user) { 
		logger.info("entry createUser");
		
		userDao.createUser(user); 
	}
	
	public void createRole(Role role) { 
		logger.info("entry createRole"); 
		
		userDao.createRole(role); 
	}
	
	public void createUserRole(User user, Role role) { 
		logger.info("entry createUserRole"); 
		
		userDao.createUserRole(user, role); 
	}
}
