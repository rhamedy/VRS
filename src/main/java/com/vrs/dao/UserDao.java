package com.vrs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vrs.model.User;

/*
 * UserDao is used by UserServices to manipulate user related 
 * data stored in persistent storage. 
 */

@Repository
public class UserDao {

	private static Logger logger = LoggerFactory.getLogger(UserDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User findUser(String username) {
		String SQL = "SELECT * FROM auth.user WHERE username = ?";

		return jdbcTemplate.queryForObject(SQL, new Object[] { username },
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						
						User user = new User();
						user.setDateOfBirth(rs.getDate("dob"));
						user.setDisabled(rs.getBoolean("disabled"));
						user.setEmail(rs.getString("username"));
						user.setFirstName(rs.getString("first_name"));
						user.setLastName(rs.getString("last_name"));
						user.setCreatedDate(rs.getDate("created_date")); 

						return user;
					}
				});
	}
}
