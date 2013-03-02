package com.vrs.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vrs.model.Role;
import com.vrs.model.User;

/**
 * UserDao is used by UserServices to manipulate user related data stored in
 * persistent storage.
 * 
 * @author Rafiullah Hamedy
 * @Date 25-02-2013
 * 
 */

@Repository
public class UserDao {

	private static Logger logger = LoggerFactory.getLogger(UserDao.class);

	private JdbcTemplate jdbcTemplate;

	//initialize JdbcTemplate with DataSource object (database configuration)
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void createUser(User user) {
		logger.info("entry crateUser()");

		String SQL = "INSERT INTO auth.user (username, first_name, last_name, "
				+ "password, dob, mobile, disabled, created_date) VALUES (?,?,?,?,?,?,?,?)";
		
		jdbcTemplate.update(
				SQL,
				new Object[] { user.getUsername(), user.getFirstName(),
						user.getLastName(), user.getPassword(), user.getDob(),
						user.getMobile(), user.isDisabled(),
						user.getCreatedDate() });
	}

	public void createRole(Role role) {
		logger.info("entry createRole()");

		String SQL = "INSERT INTO auth.role (role_id, role_name) VALUES (?,?)";

		jdbcTemplate.update(SQL,
				new Object[] { role.getRoleId(), role.getRoleName() });
	}

	public void createUserRole(User user, Role role) {
		logger.info("entry createUserRole");
		
		logger.info("username {}, role_id{}", user.getUsername(), role.getRoleId());

		String SQL = "INSERT INTO auth.user_role (username, role_id) VALUES (?,?)";

		jdbcTemplate.update(SQL,
				new Object[] { user.getUsername(), role.getRoleId() });
	}
	
	public void updateUser(User user) { 
		logger.info("entry updateUser()"); 
		
		String SQL = "UPDATE auth.user SET first_name = ?, last_name = ?, " +
				"dob = ?, mobile = ?, disabled = ?, password = ? WHERE username = ?"; 
		
		jdbcTemplate.update(SQL, new Object[]{
				user.getFirstName(), 
				user.getLastName(), 
				user.getDob(), 
				user.getMobile(), 
				user.isDisabled(), 
				user.getPassword(), 
				user.getUsername()
		}); 
	}
	
	public void updateRole(Role role) { 
		logger.info("entry updateRole()"); 
		
		String SQL = "UPDATE auth.role SET role_name = ? WHERE role_id = ?";
		
		jdbcTemplate.update(SQL, new Object[]{ role.getRoleName(), role.getRoleId()}); 
	}
	
	public void updateUserRole(User user, Role role) { 
		logger.info("entry updateUserRoleRelationship()"); 
		
		String SQL = "UPDATE auth.user_role SET role_id = ? WHERE username = ?";
		
		jdbcTemplate.update(SQL, new Object[]{ role.getRoleId(), user.getUsername() }); 
	}

	public User findUser(String username) {
		logger.info("entry findUser");

		String SQL = "SELECT * FROM auth.user WHERE username = ?";

		try {
			User user = (User) jdbcTemplate.queryForObject(SQL,
					new Object[] { username }, new BeanPropertyRowMapper<User>(
							User.class));

			return user;

		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public Role retrieveUserRole(String username) {
		logger.info("entry retrieveUserRole");

		String SQL = "SELECT r.role_id, r.role_name FROM auth.role r, "
				+ "auth.user_role ur WHERE ur.username = ? AND ur.role_id = r.role_id";

		try {
			Role role = (Role) jdbcTemplate.queryForObject(SQL,
					new Object[] { username }, new BeanPropertyRowMapper<Role>(
							Role.class));

			return role;

		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public Role findRole(int roleId) {
		logger.info("entry findRole");

		String SQL = "SELECT * FROM auth.role WHERE role_id = ?";

		try {
			Role role = (Role) jdbcTemplate.queryForObject(SQL,
					new Object[] { roleId }, new BeanPropertyRowMapper<Role>(
							Role.class));

			return role;

		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public void deleteUser(User user) {
		logger.info("entry deleteUser");

		String SQL = "DELETE FROM auth.user WHERE username = ?";

		jdbcTemplate.update(SQL, new Object[] { user.getUsername() });
	}

	public void deleteRole(Role role) {
		logger.info("entry deleteRole");

		String SQL = "DELETE FROM auth.role WHERE role_id = ?";

		jdbcTemplate.update(SQL, new Object[] { role.getRoleId() });
	}

	public void deleteUserRole(User user, Role role) {
		logger.info("entry deleteUserRole");

		String SQL = "DELETE FROM auth.user_role WHERE username = ? AND role_id = ?";

		jdbcTemplate.update(SQL,
				new Object[] { user.getUsername(), role.getRoleId() });
	}
}
