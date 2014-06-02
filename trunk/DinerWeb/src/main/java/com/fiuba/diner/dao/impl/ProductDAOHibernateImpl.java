package com.fiuba.diner.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.ProductDAO;
import com.fiuba.diner.dto.ProductRankingReportDTO;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.model.Subcategory;

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

	@SuppressWarnings("unchecked")
	@Override
	public ProductRankingReportDTO getCategorySubcategoryFromProduct(String productDescription) {
		String sentence = "select c, s from Category as c inner join c.subcategories as s inner join s.products as p where p.description = :productDescription";
		Query query;
		query = this.sessionFactory.getCurrentSession().createQuery(sentence);
		query.setParameter("productDescription", productDescription);
		Object[] array = (Object[]) query.list().get(0);
		ProductRankingReportDTO dto = new ProductRankingReportDTO();
		dto.setCategory(((Category) array[0]).getDescription());
		dto.setSubcategory(((Subcategory) array[1]).getDescription());
		return dto;
	}
}
