package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.SubcategoryDAO;
import com.fiuba.diner.model.Subcategory;

@Repository
public class SubcategoryDAOHibernateImpl implements SubcategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Subcategory get(Integer id) {
		return (Subcategory) this.sessionFactory.getCurrentSession().get(Subcategory.class, id);
	}

	@Override
	public void save(Subcategory subcategory) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(subcategory);
	}

	@Override
	public void delete(Integer subcategoryId) {
		Subcategory subcategory = this.get(subcategoryId);
		this.sessionFactory.getCurrentSession().delete(subcategory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subcategory> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(Subcategory.class).list();
	}
}
