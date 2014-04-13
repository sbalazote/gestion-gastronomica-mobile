package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fiuba.diner.dao.ParameterDAO;
import com.fiuba.diner.model.Parameter;

public class ParameterDAOHibernateImpl implements ParameterDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Parameter get(Integer id) {
		return (Parameter) this.sessionFactory.getCurrentSession().get(Parameter.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Parameter> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(Parameter.class).list();
	}

	@Override
	public void save(Parameter parameter) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(parameter);
	}

	@Override
	public void delete(Integer parameterId) {
		Parameter parameter = this.get(parameterId);
		this.sessionFactory.getCurrentSession().delete(parameter);
	}
}
