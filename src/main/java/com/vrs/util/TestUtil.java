package com.vrs.util;

import com.vrs.model.Branch;
import com.vrs.model.Role;
import com.vrs.model.User;
import com.vrs.model.Vehicle;

public class TestUtil {

	public static User createTestUser() {
		User user1 = new User();

		java.sql.Date d = DateUtil.currentDateInSqlFormat();
		java.sql.Date dob = DateUtil.parseDateInSqlFormat("07-07-1986");

		user1.setUsername("user1_test@test.com");
		user1.setCreatedDate(d);
		user1.setDob(dob);
		user1.setFirstName("raf_test1");
		user1.setLastName("hamedy_test1");
		user1.setMobile("0700282663");
		user1.setDisabled(true);

		return user1;
	}

	public static Role createTestRole(int roleId, String roleName) {

		Role role1 = new Role();

		role1.setRoleId(roleId);
		role1.setRoleName(roleName);

		return role1;
	}

	public static Branch createMockBranch(int id, String name, int cityId,
			String streetName, String postcode) {
		Branch branch = new Branch();

		branch.setId(id);
		branch.setCityId(cityId);
		branch.setName(name);
		branch.setStreetName(streetName);
		branch.setPostcode(postcode);

		return branch;
	}

	public static Vehicle createMockVehicle(String vin, String numberPlate,
			int seating, String fuel, boolean available, int modelId,
			int branchId, int maxSpeed) {

		Vehicle vehicle = new Vehicle(); 
		
		vehicle.setVin(vin); 
		vehicle.setAvailable(available); 
		vehicle.setBranchId(branchId); 
		vehicle.setMaxSpeed(maxSpeed); 
		vehicle.setFuel(fuel); 
		vehicle.setSeating(seating);  
		vehicle.setModelId(modelId); 
		vehicle.setNumberPlate(numberPlate); 
		
		return vehicle; 
	}
}
