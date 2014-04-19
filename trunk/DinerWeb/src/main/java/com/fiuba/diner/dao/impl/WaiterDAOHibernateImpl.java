package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.WaiterDAO;
import com.fiuba.diner.model.Waiter;

@Repository
public class WaiterDAOHibernateImpl implements WaiterDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Waiter get(Integer id) {
		return (Waiter) this.sessionFactory.getCurrentSession().get(Waiter.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Waiter> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(Waiter.class).list();
	}

	@Override
	public void save(Waiter waiter) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(waiter);
	}

}
