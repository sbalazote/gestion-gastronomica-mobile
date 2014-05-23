package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.model.Role;

public interface RoleService {

	void save(Role role);

	Role get(Integer id);

	List<Role> getAll();
}
