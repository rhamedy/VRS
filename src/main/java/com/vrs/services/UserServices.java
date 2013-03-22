package com.vrs.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrs.dao.UserDao;
import com.vrs.model.Role;
import com.vrs.model.User;
import com.vrs.util.Comparator;

/**
 * Is used by Controller class to communicate with dao's and views.
 * 
 * @author Rafiullah Hamedy
 * @Date 25-02-2012
 */
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

	public List<Role> getUserRole(User user) {
		logger.info("entry getUserRole()");

		return userDao.getUserRoles(user);
	}

	public List<User> listUsers() {
		logger.info("entry listUsers()");

		return userDao.listUsers();
	}

	public List<Role> listRoles() {
		logger.info("entry listRoles()");

		return userDao.listRoles();
	}

	public List<Role> nonUserRoles(List<Role> allRoles, List<Role> userRoles) {
		logger.info("entry nonUserRoles()");

		List<Role> nonUserRoles = Comparator.difference(allRoles, userRoles);

		logger.info("allRoles size = " + allRoles.size()
				+ ", userRoles size = " + userRoles.size()
				+ ", nonUserRoles size = " + nonUserRoles.size());
		
		return nonUserRoles; 
	}
	
	public void updateUser(User user) { 
		logger.info("entry updateUser()"); 
		
		userDao.updateUser(user); 
	}
}
