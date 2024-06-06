package com.foryou.webapp.dao;

import com.foryou.webapp.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
