package com.vrs.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

	// add a new branch to a city (supplied city_id)
	
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

	//find the record with supplied id, wraps it in a obj, return it
	
	public Branch findBranch(int branchId) {
		logger.info("entry findBranch()");

		String SQL = "SELECT * from rental.branch WHERE id = ?";

		try {
			Branch branch = jdbcTemplate.queryForObject(SQL,
					new Object[] { branchId }, new BeanPropertyRowMapper<Branch>(
							Branch.class));
			
			return branch;
		} catch(EmptyResultDataAccessException ex) { 
			return null;
		}
	}
	
	//delete a branch from the system
	
	public void deleteBranch(int branchId) { 
		logger.info("entry deleteBranch()"); 
		
		String SQL = "DELETE from rental.branch WHERE id = ? "; 
		
		//make sure that all branch vehicles are deleted first
		jdbcTemplate.update(SQL, new Object[]{ branchId });  
	}

	// max branch id+1 (e.g. id=5, id+1=6) is used to add next branch record
	
	public int getMaxBranchId() {
		logger.info("entry getNextBranchId()");

		String SQL = "SELECT MAX(id) FROM rental.branch";

		int branchId = jdbcTemplate.queryForInt(SQL);

		return branchId;
	}
}
