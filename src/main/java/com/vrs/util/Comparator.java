package com.vrs.util;

import java.util.Iterator;
import java.util.List;

import com.vrs.model.Role;

public class Comparator {
	public static List<Role> difference(List<Role> allRoles, List<Role> userRoles) { 
		
		Iterator<Role> iterator = allRoles.iterator(); 
		
		while(iterator.hasNext()) { 
			Role role = iterator.next(); 
			for(Role r: userRoles) { 
				if(r.getRoleName().equals(role.getRoleName())) { 
					iterator.remove(); 
				}
			}
		}
		
		return allRoles; 
	}
}
