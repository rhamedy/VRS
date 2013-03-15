package com.vrs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vrs.model.Branch;
import com.vrs.model.Vehicle;

@Repository
public class RentalDao {

	/**
	 * RentalDao is used by RentalController to communicate information
	 * back/forth to/from database.
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
			// exception thrown if no result is found
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
			// this exception is thrown if no match is found
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

	public boolean isAnyVehiclesRegisteredToBranch(int branchId) {
		logger.info("entry isAnyVehicleRegisteredToBranch()");

		String SQL = "SELECT COUNT (*) FROM rental.vehicle WHERE branch_id = ?";

		// branch_id is foreign key in vehicle table, hence the SQL

		int count = jdbcTemplate.queryForInt(SQL, new Object[] { branchId });

		return (count > 0) ? true : false;
	}

	public void deleteBranchVehicles(int branchId) {
		logger.info("entry deleteBranchVehicles()");

		String SQL = "DELETE From rental.vehicle WHERE branch_id = ?";

		jdbcTemplate.update(SQL, new Object[] { branchId });
	}

	public List<Vehicle> getBranchVehicles(int branchId) {
		logger.info("entry getBranchVehicles()");

		String SQL = "SELECT * FROM rental.vehicle WHERE branch_id = ?";

		List<Vehicle> vehicles = jdbcTemplate.query(SQL,
				new Object[] { branchId }, new BeanPropertyRowMapper<Vehicle>(
						Vehicle.class));

		logger.info("value of vehicles is : " + vehicles);
		logger.info("size of vehicles is : " + vehicles.size());

		return vehicles;
	}

	public List<Map<Integer, String>> getCountryList() {
		logger.info("entry getCountryList()");

		String SQL = "SELECT * FROM rental.country";

		List<Map<Integer, String>> countries = jdbcTemplate.query(SQL,
				new RowMapper<Map<Integer, String>>() {
					public Map<Integer, String> mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Map<Integer, String> countryTemp = new HashMap<Integer, String>();
						countryTemp.put(rs.getInt("id"), rs.getString("name"));
						return countryTemp;
					}
				});

		return countries;
	}

	public List<Map<Integer, String>> getCityList(int countryId) {
		logger.info("entry getCityList()");

		String SQL = "SELECT * FROM rental.city WHERE country_id = ?";

		List<Map<Integer, String>> cities = jdbcTemplate.query(SQL,
				new Object[] { countryId },
				new RowMapper<Map<Integer, String>>() {
					public Map<Integer, String> mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Map<Integer, String> cityTemp = new HashMap<Integer, String>();
						cityTemp.put(rs.getInt("id"), rs.getString("name"));
						return cityTemp;
					}
				});

		return cities;
	}
}
