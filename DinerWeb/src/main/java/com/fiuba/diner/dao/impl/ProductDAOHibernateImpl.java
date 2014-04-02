package com.fiuba.diner.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.ProductDAO;
import com.fiuba.diner.model.Product;

@Repository
public class ProductDAOHibernateImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Product get(Integer id) {
		return (Product) this.sessionFactory.getCurrentSession().get(Product.class, id);
	}

	@Override
	public void save(Product product) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(product);
	}

	@Override
	public void delete(Integer productId) {
		Product product = this.get(productId);
		this.sessionFactory.getCurrentSession().delete(product);
	}
}
