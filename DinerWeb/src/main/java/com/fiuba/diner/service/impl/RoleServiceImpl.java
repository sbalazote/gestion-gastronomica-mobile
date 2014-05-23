package com.fiuba.diner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.RoleDAO;
import com.fiuba.diner.model.Role;
import com.fiuba.diner.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;

	@Override
	public void save(Role role) {
		this.roleDAO.save(role);
	}

	@Override
	public Role get(Integer id) {
		return this.roleDAO.get(id);
	}

	@Override
	public List<Role> getAll() {
		return this.roleDAO.getAll();
	}

}
