package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.CategoryDAO;
import com.fiuba.diner.model.Category;

@Repository
public class CategoryDAOHibernateImpl implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Category get(Integer id) {
		return (Category) this.sessionFactory.getCurrentSession().get(Category.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(Category.class).list();
	}

	@Override
	public void save(Category category) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(category);
	}

	@Override
	public void delete(Integer categoryId) {
		Category category = this.get(categoryId);
		this.sessionFactory.getCurrentSession().delete(category);
	}
}
