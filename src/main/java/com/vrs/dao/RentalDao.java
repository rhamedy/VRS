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
import com.vrs.model.Vehicle;

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

	public Branch findBranch(int branchId) {
		logger.info("entry findBranch()");

		String SQL = "SELECT * from rental.branch WHERE id = ?";

		try {
			Branch branch = jdbcTemplate.queryForObject(SQL,
					new Object[] { branchId },
					new BeanPropertyRowMapper<Branch>(Branch.class));

			return branch;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public void deleteBranch(int branchId) {
		logger.info("entry deleteBranch()");

		String SQL = "DELETE from rental.branch WHERE id = ? ";

		// make sure that all branch vehicles are deleted first
		jdbcTemplate.update(SQL, new Object[] { branchId });
	}

	public void updateBranch(Branch branch) {
		logger.info("entry updateBranch()");

		String SQL = "UPDATE rental.branch SET name = ?, street_name = ?, postcode = ?"
				+ " WHERE id = ? ";
		jdbcTemplate.update(
				SQL,
				new Object[] { branch.getName(), branch.getStreetName(),
						branch.getPostcode(), branch.getId() });
	}

	public int getMaxBranchId() {
		logger.info("entry getNextBranchId()");

		// max branch id+1 (e.g. id=5, id+1=6) is used to add next branch record

		String SQL = "SELECT MAX(id) FROM rental.branch";

		int branchId = jdbcTemplate.queryForInt(SQL);

		return branchId;
	}

	public void addVehicle(Vehicle vehicle) {
		logger.info("entry addVehicle()");

		String SQL = "INSERT INTO rental.vehicle (vin, number_plate, max_speed, "
				+ "seating, fuel, model_id, available, branch_id) VALUES (?,?,?,?,?,?,?,?)";

		jdbcTemplate.update(
				SQL,
				new Object[] { vehicle.getVin(), vehicle.getNumberPlate(),
						vehicle.getMaxSpeed(), vehicle.getSeating(),
						vehicle.getFuel(), vehicle.getModelId(),
						vehicle.isAvailable(), vehicle.getBranchId() });
	}

	public Vehicle findVehicle(String vin) {
		logger.info("entry findVehicle()");

		String SQL = "SELECT * FROM rental.vehicle WHERE vin = ? ";

		try {
			Vehicle vehicle = jdbcTemplate.queryForObject(SQL,
					new Object[] { vin }, new BeanPropertyRowMapper<Vehicle>(
							Vehicle.class));

			return vehicle;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public void deleteVehicle(String vin) {
		logger.info("entry deleteVehicle()");

		String SQL = "DELETE FROM rental.vehicle WHERE vin = ? ";

		jdbcTemplate.update(SQL, new Object[] { vin });
	}

	public void updateVehicle(Vehicle vehicle) {
		logger.info("entry updateVehicle()");

		String SQL = "UPDATE rental.vehicle SET number_plate = ?, max_speed = ?, "
				+ "seating = ?, fuel = ?, model_id = ?, branch_id = ?, available = ? "
				+ "WHERE vin = ?";

		jdbcTemplate.update(
				SQL,
				new Object[] { vehicle.getNumberPlate(), vehicle.getMaxSpeed(),
						vehicle.getSeating(), vehicle.getFuel(),
						vehicle.getModelId(), vehicle.getBranchId(),
						vehicle.isAvailable(), vehicle.getVin() });
	}

	public void updateVehicleStatus(String vin, boolean status) {
		logger.info("entry upateVehicleStatus()");

		String SQL = "UPDATE rental.vehicle SET available = ? WHERE vin = ?";

		// status will be current value of available, !status will inverse it

		jdbcTemplate.update(SQL, new Object[] { !status, vin });
	}
}
