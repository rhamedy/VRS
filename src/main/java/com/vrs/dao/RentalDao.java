package com.vrs.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vrs.model.Branch;

@Repository
public class RentalDao {

	/**
	 * RentalDao is used by RentalController to communicate information
	 * back/forth with database.
	 * 
	 * @author Rafiullah Hamedy
	 * @Date 01-03-2013
	 * 
	 */

	private static Logger logger = LoggerFactory.getLogger(RentalDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//add a new branch to a city (supplied city_id)
	public void addBrunch(Branch branch) {
		logger.info("entry addBrunch()");

		String SQL = "INSERT INTO rental.branch (id,name,city_id,street_name,postcode) "
				+ "VALUES (?,?,?,?,?)";

		jdbcTemplate.update(
				SQL,
				new Object[] { branch.getId(), branch.getName(),
						branch.getCityId(), branch.getStreetName(),
						branch.getPostcode() });
	}
}
