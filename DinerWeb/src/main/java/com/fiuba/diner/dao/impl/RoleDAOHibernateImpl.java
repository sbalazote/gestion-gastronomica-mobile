package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.RoleDAO;
import com.fiuba.diner.model.Role;

@Repository
public class RoleDAOHibernateImpl implements RoleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Role role) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(role);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(Role.class).list();
	}

	@Override
	public Role get(Integer id) {
		return (Role) this.sessionFactory.getCurrentSession().get(Role.class, id);
	}
}
