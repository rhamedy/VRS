package com.vrs.dao;

import java.util.List;

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

	// initialize JdbcTemplate with DataSource object (database configuration)

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void createUser(User user) {
		logger.info("entry crateUser()");

		String SQL = "INSERT INTO auth.user (username, first_name, last_name, "
				+ "password, dob, mobile, disabled, created_date, branch_id) VALUES (?,?,?,?,?,?,?,?,?)";

		logger.info("updateUser branchId = " + user.getBranchId());
		
		jdbcTemplate.update(
				SQL,
				new Object[] { user.getUsername(), user.getFirstName(),
						user.getLastName(), user.getPassword(), user.getDob(),
						user.getMobile(), user.isDisabled(),
						user.getCreatedDate(), user.getBranchId() });
	}

	public void createRole(Role role) {
		logger.info("entry createRole()");

		String SQL = "INSERT INTO auth.role (role_id, role_name) VALUES (?,?)";

		jdbcTemplate.update(SQL,
				new Object[] { role.getRoleId(), role.getRoleName() });
	}

	public void createUserRole(User user, Role role) {
		logger.info("entry createUserRole");

		logger.info("username {}, role_id{}", user.getUsername(),
				role.getRoleId());

		String SQL = "INSERT INTO auth.user_role (username, role_id) VALUES (?,?)";

		jdbcTemplate.update(SQL,
				new Object[] { user.getUsername(), role.getRoleId() });
	}

	public void updateRole(Role role) {
		logger.info("entry updateRole()");

		String SQL = "UPDATE auth.role SET role_name = ? WHERE role_id = ?";

		jdbcTemplate.update(SQL,
				new Object[] { role.getRoleName(), role.getRoleId() });
	}

	public void insertUserRole(User user, Role role) {
		logger.info("entry insertUserRole()");

		String SQL = "INSERT INTO auth.user_role(role_id, username) VALUES (?,?)";

		jdbcTemplate.update(SQL,
				new Object[] { role.getRoleId(), user.getUsername() });
	}

	public User findUser(String username) {
		logger.info("entry findUser");

		String SQL = "SELECT * FROM auth.user WHERE username = ?";

		try {
			User user = (User) jdbcTemplate.queryForObject(SQL,
					new Object[] { username }, new BeanPropertyRowMapper<User>(
							User.class));

			logger.info("found user : ");
			logger.info("user.username = " + user.getUsername());
			logger.info("user.lastName = " + user.getLastName());

			return user;

		} catch (EmptyResultDataAccessException ex) {
			ex.printStackTrace();
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

	public List<Role> getUserRoles(User user) {
		logger.info("entry getUserRole()");

		String SQL = "SELECT r.role_id, r.role_name FROM auth.user u, auth.role r, auth.user_role ur WHERE"
				+ " u.username = ? AND u.username = ur.username AND ur.role_id = r.role_id";

		List<Role> role = jdbcTemplate.query(SQL,
				new Object[] { user.getUsername() },
				new BeanPropertyRowMapper<Role>(Role.class));

		return role;
	}

	public List<User> listUsers() {
		logger.info("entry listUsers()");

		String SQL = "SELECT * FROM auth.user";

		List<User> users = jdbcTemplate.query(SQL,
				new BeanPropertyRowMapper<User>(User.class));

		return users;
	}

	public List<Role> listRoles() {
		logger.info("entry listRoles()");

		String SQL = "SELECT * FROM auth.role";

		List<Role> roles = jdbcTemplate.query(SQL,
				new BeanPropertyRowMapper<Role>(Role.class));

		return roles;
	}

	public void updateUser(User user) {
		logger.info("entry updateUser()");

		String SQL = "UPDATE auth.user SET first_name = ?, last_name = ?, dob = ?, mobile = ?, "
				+ "disabled = ?, license_no = ?, license_validity = ?, branch_id = ? WHERE username = ?";

		logger.info("updateUser branchId = " + user.getBranchId());
		
		jdbcTemplate.update(
				SQL,
				new Object[] { user.getFirstName(), user.getLastName(),
						user.getDob(), user.getMobile(), user.isDisabled(),
						user.getLicenseNo(), user.getLicenseValidity(),
						user.getBranchId(), user.getUsername() });
	}

	public void updatePassword(User user) {
		logger.info("entry updatePassword()");

		String SQL = "UPDATE auth.user SET password = ? WHERE username = ?";

		jdbcTemplate.update(SQL,
				new Object[] { user.getPassword(), user.getUsername() });
	}

	public boolean isUsernameExists(String username) {
		logger.info("entry isUsernameExists()");

		String SQL = "SELECT COUNT(*) FROM auth.user WHERE username = ?";

		int result = jdbcTemplate.queryForInt(SQL, new Object[] { username });

		return (result > 0) ? true : false;
	}
	
	public String getPasswordByUsername(String username) { 
		logger.info("entry getPasswordByUsername()"); 
		
		String SQL = "SELECT password FROM auth.user WHERE username = ?"; 
		
		return jdbcTemplate.queryForObject(SQL, new Object[]{ username }, String.class); 
	}
}
