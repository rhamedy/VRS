package com.vrs.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vrs.model.Role;

/**
 * Comparator is a utility class used to perform simple comparisons, 
 * substractions and differences between list of objects. It is good
 * to have a single cohesive class doing these activites than to copy
 * redundant code everywhere across the classes. 
 * 
 * @author Rafiullah Hamedy
 * @Date 28-03-2013
 * 
 */


public class Comparator {
	
	/*
	 * Difference method's purpose is to find out list of roles the 
	 * user is not assigned to by substracting userRoles from allRoles. 
	 * 
	 */
	
	public static List<Role> difference(List<Role> allRoles,
			List<Role> userRoles) {

		Iterator<Role> iterator = allRoles.iterator();

		while (iterator.hasNext()) {
			Role role = iterator.next();
			for (Role r : userRoles) {
				if (r.getRoleName().equals(role.getRoleName())) {
					iterator.remove();
				}
			}
		}

		return allRoles;
	}

	/*
	 * in filter method, we are receive list of oldRoles and newRoles, our objective is 
	 * to avoid a condition where a role is in the old list and new list and avoid 
	 * deleting the same role fromd database and adding it again ... if a user already 
	 * has the role then we simply drop it off the oldRoles and newRoles list and at the end 
	 * the oldRoles will contain all those roles to be deleted from database and newRoles
	 * all those roles to be added ... the follow method makes sure there is no role intersecting 
	 * both lists. 
	 */
	
	public static Map<String, List<Role>> filterIntersections(List<Role> oldRoles, List<Role> newRoles) {
		Iterator<Role> it = newRoles.iterator(); 
		while(it.hasNext()) {
			int size = oldRoles.size();
			Role role = it.next(); 
			if(oldRoles.size() > 0) { 
				oldRoles = substract(oldRoles, role); 
				if(size != oldRoles.size()) { 
					it.remove();
				}
			} else { 
				break; 
			}
		}
		
		Map<String, List<Role>> rolesMap= new HashMap<String, List<Role>>(); 
		rolesMap.put("toDelete", oldRoles); 
		rolesMap.put("toInsert", newRoles); 
		
		return rolesMap; 
	}
	
	
	/*
	 * Goes through a list of oldRoles to see if role is present in that list, 
	 * if present it drops it from that list and returns updated list.  
	 */
	
	public static List<Role> substract(List<Role> oldRoles, Role role) { 
		Iterator<Role> it = oldRoles.iterator(); 
		while(it.hasNext()) { 
			Role r = it.next(); 
			if(r.getRoleName().equals(role.getRoleName())) { 
				it.remove();
				break; 
			}
		}
		return oldRoles; 
	}
}
