package com.vrs.dao;

import java.sql.Date;
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
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vrs.model.Branch;
import com.vrs.model.Vehicle;
import com.vrs.util.KeyValuePair;

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

		String SQL = "UPDATE rental.branch SET name = ?, city_id = ?, street_name = ?, postcode = ?"
				+ " WHERE id = ? ";
		jdbcTemplate.update(
				SQL,
				new Object[] { branch.getName(), branch.getCityId(),
						branch.getStreetName(), branch.getPostcode(),
						branch.getId() });
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
				+ "seating, fuel, model_id, available, branch_id, daily_cost) VALUES (?,?,?,?,?,?,?,?,?)";

		jdbcTemplate.update(
				SQL,
				new Object[] { vehicle.getVin(), vehicle.getNumberPlate(),
						vehicle.getMaxSpeed(), vehicle.getSeating(),
						vehicle.getFuel(), vehicle.getModelId(),
						vehicle.isAvailable(), vehicle.getBranchId(),
						vehicle.getDailyCost() });
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
				+ "seating = ?, fuel = ?, model_id = ?, branch_id = ?, available = ?, daily_cost = ? "
				+ "WHERE vin = ?";

		jdbcTemplate.update(
				SQL,
				new Object[] { vehicle.getNumberPlate(), vehicle.getMaxSpeed(),
						vehicle.getSeating(), vehicle.getFuel(),
						vehicle.getModelId(), vehicle.getBranchId(),
						vehicle.isAvailable(), vehicle.getDailyCost(),
						vehicle.getVin() });
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

	public List<Branch> getBranchList(int cityId) {
		logger.info("entry getBranchList()");

		String SQL = "SELECT * FROM rental.branch WHERE city_id = ?";

		List<Branch> branches = jdbcTemplate.query(SQL,
				new Object[] { cityId }, new BeanPropertyRowMapper<Branch>(
						Branch.class));
		return branches;
	}

	public List<Vehicle> getVehicleList(int branchId) {
		logger.info("entry getVehicleList()");

		String SQL = "SELECT * FROM rental.vehicle WHERE branch_id = ?";

		List<Vehicle> vehicles = jdbcTemplate.query(SQL,
				new Object[] { branchId }, new BeanPropertyRowMapper<Vehicle>(
						Vehicle.class));
		return vehicles;
	}

	// get model and make for a particular vehicle, we use model foreign key
	// in vehicle table (model_id) match it against model table then use the
	// foreign key (make_id) from model table and match it against make table
	// then extract make name and model

	public KeyValuePair<String, String> getMakeAndModelName(String vin) {
		logger.info("entry getMakeAndeModelName()");

		String SQL = "SELECT rmd.name AS mdname, rmk.name AS mkname FROM rental.vehicle rv, "
				+ "rental.model rmd, rental.make rmk WHERE rv.vin = ? AND rv.model_id = rmd.id "
				+ "AND rmd.make_id = rmk.id";

		KeyValuePair<String, String> makeModel = jdbcTemplate.query(SQL,
				new Object[] { vin },
				new ResultSetExtractor<KeyValuePair<String, String>>() {
					public KeyValuePair<String, String> extractData(ResultSet rs)
							throws SQLException {
						while (rs.next()) {
							KeyValuePair<String, String> keyValue = new KeyValuePair<String, String>(
									rs.getString("mdname"), rs
											.getString("mkname"));
							return keyValue;
						}
						return null;
					}
				});

		return makeModel;
	}

	public List<String> listMakes() {
		logger.info("entry listMakes()");

		String SQL = "SELECT name FROM rental.make";

		return jdbcTemplate.queryForList(SQL, String.class);
	}

	public List<String> listMakeModels(int makeId) {
		logger.info("entry listMakeModels()");

		String SQL = "SELECT name FROM rental.model WHERE make_id = ?";

		return jdbcTemplate.queryForList(SQL, new Object[] { makeId },
				String.class);
	}

	public int getMakeId(String makeName) {
		logger.info("entry getMakeId()");

		String SQL = "SELECT id FROM rental.make WHERE name = ?";

		return jdbcTemplate.queryForInt(SQL, new Object[] { makeName });
	}

	public int getModelId(String modelName) {
		logger.info("entry getModelId()");

		String SQL = "SELECT id FROM rental.model WHERE name = ?";

		return jdbcTemplate.queryForInt(SQL, new Object[] { modelName });
	}

	public KeyValuePair<Integer, String> getCity(int cityId) {
		logger.info("entry getCityByBranch()");

		String SQL = "SELECT name FROM rental.city WHERE id = ?";

		String cityName = jdbcTemplate.queryForObject(SQL,
				new Object[] { cityId }, String.class);

		KeyValuePair<Integer, String> kvp = new KeyValuePair<Integer, String>(
				new Integer(cityId), cityName);

		return kvp;

	}

	public KeyValuePair<Integer, String> getCountryByCity(int cityId) {
		logger.info("entry getCountryByCityId()");

		String SQL = "SELECT co.id, co.name FROM rental.country co, "
				+ "rental.city ci WHERE ci.id = ? AND ci.country_id = co.id";

		KeyValuePair<Integer, String> country = jdbcTemplate.query(SQL,
				new Object[] { cityId },
				new ResultSetExtractor<KeyValuePair<Integer, String>>() {
					public KeyValuePair<Integer, String> extractData(
							ResultSet rs) throws SQLException {
						while (rs.next()) {
							KeyValuePair<Integer, String> keyValue = new KeyValuePair<Integer, String>(
									rs.getInt("id"), rs.getString("name"));
							return keyValue;
						}
						return null;
					}
				});

		return country;
	}

	public void addVehicleBooking(String uuid, String username, String vin, Date startDate,
			Date endDate, boolean insurance, double hireCost) {
		logger.info("entry addVehicleBooking()");

		String SQL = "INSERT INTO rental.customer_vehicle(id, vehicle_vin, username,"
				+ "start_date,end_date,insurance,hire_cost) VALUES(?,?,?,?,?,?,?)";

		jdbcTemplate.update(SQL, new Object[] { uuid, vin, username, startDate,
				endDate, insurance, hireCost });
	}
	
	public List<Vehicle> getVehiclesForHire(int branchId) { 
		logger.info("entry getVehiclesFroHire()"); 
		
		String SQL = "SELECT * FROM rental.vehicle WHERE branch_id = ? AND available = true";

		List<Vehicle> vehicles = jdbcTemplate.query(SQL,
				new Object[] { branchId }, new BeanPropertyRowMapper<Vehicle>(
						Vehicle.class));
		return vehicles;
	}
}
