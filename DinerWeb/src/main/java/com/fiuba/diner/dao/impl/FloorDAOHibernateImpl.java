package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.FloorDAO;
import com.fiuba.diner.model.Floor;

@Repository
public class FloorDAOHibernateImpl implements FloorDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Floor get(Integer id) {
		return (Floor) this.sessionFactory.getCurrentSession().get(Floor.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Floor> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(Floor.class).list();
	}

	@Override
	public void save(Floor floor) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(floor);
	}

}
