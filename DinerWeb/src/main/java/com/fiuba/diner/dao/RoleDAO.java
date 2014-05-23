package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Role;

public interface RoleDAO {

	void save(Role role);

	Role get(Integer id);

	List<Role> getAll();
}
