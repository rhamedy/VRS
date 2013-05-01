package com.vrs.services;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vrs.util.MailClient;
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
	
	public String getCurrentUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

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

		if(user.getBranchId() == 999) { 
			String password = userDao.getPasswordByUsername("oscar.vehicle.rental.system@gmail.com");
			String subject = "Account Request Received.";
			String text = "<html><body><br>You will receive a confirmation email with your username and "
					+ "password shortly after your account has been created. <br><br>"
					+ "<br> thank you for your interest in using our services. "
					+ "<br><br> VRS Team</body></html>";

			MailClient.sendEmail(user.getUsername(), password, subject, text); 
		}
		
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
		User oldUser = userDao.findUser(user.getUsername()); 
		if(oldUser.getPassword() == null || oldUser.getPassword().trim().length() == 0) {
			if(!user.isDisabled()) { 
				String partApproved = "Your account is approved. ";
				String partPassword = "Your Temporary password is : 12" + user.getUsername().split("@")[0] + "34";
			
				String password = userDao.getPasswordByUsername("oscar.vehicle.rental.system@gmail.com"); 
				String subject = "Account Password Updated.";
				String text = "<html><body>Your account details has been updated. <br><br>"
						+ partApproved + " <br>"
						+ partPassword + " <br>"
						+ "<br> thank you for your interest in using our services. "
						+ "<br><br> VRS Team</body></html>";
	
				MailClient.sendEmail(user.getUsername(), password, subject, text); 
				user.setPassword("12" + user.getUsername().split("@")[0] + "34");
				userDao.updatePassword(user);  
			}
		}
		
		userDao.updateUser(user); 
	}
	
	public boolean isUsernameExists(String username) { 
		logger.info("entry isUsernameExists()"); 
		
		return userDao.isUsernameExists(username); 
	}
	
	public void updateUserRoles(User user, List<Role> newRoles) { 
		logger.info("entry updateUserRoles()"); 
		
		List<Role> oldRoles = userDao.getUserRoles(user); 
		
		Map<String, List<Role>> mapRoles = Comparator.filterIntersections(oldRoles, newRoles); 
		
		List<Role> rolesToDelete = mapRoles.get("toDelete"); 
		List<Role> rolesToInsert = mapRoles.get("toInsert"); 
		
		logger.info("roles to delete : "); 
		for(Role r: rolesToDelete) { 
			logger.info("role to delete : " + r.getRoleName());
		}
		
		logger.info("roles to insert : "); 
		for(Role r: rolesToInsert) { 
			logger.info("role to insert : " + r.getRoleName());
		}
		
		for(Role r: rolesToDelete) { 
			userDao.deleteUserRole(user, r); 
		}
		for(Role r: rolesToInsert) { 
			userDao.insertUserRole(user, r); 
		}	
	}
	
	public String getPasswordByUsername(String username) { 
		logger.info("entry getPasswordByUsername()"); 
		
		return userDao.getPasswordByUsername(username);
	}
	
	public void changePassword(String password, String username) { 
		logger.info("entry changePassword()"); 
		
		User user = userDao.findUser(username); 
		user.setPassword(password); 
		
		userDao.updatePassword(user); 
		
		if(!getCurrentUsername().equals(username)) {
			if(!userDao.findUser(username).isDisabled()) {
				String pass = getPasswordByUsername("oscar.vehicle.rental.system@gmail.com"); 
				
				String subject = "Password Reset"; 
				String text = "<html><body>Your password has been reset to following : <br><br>"
						+ "Password (Temporary): " + password + "<br><br>"
						+ "Try logging in and change your password. <br>"
						+ "<br> thank you for your interest in using our services. "
						+ "<br><br> VRS Team</body></html>";
	
				MailClient.sendEmail(user.getUsername(), pass, subject, text); 
			}
		}
	}
}
