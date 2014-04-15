package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.constant.State;
import com.fiuba.diner.dao.OrderDAO;
import com.fiuba.diner.model.Order;

@Repository
public class OrderDAOHibernateImpl implements OrderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Order get(Integer id) {
		return (Order) this.sessionFactory.getCurrentSession().get(Order.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(Order.class).list();
	}

	@Override
	public void save(Order order) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(order);
	}

	@Override
	public void delete(Integer orderId) {
		Order order = this.get(orderId);
		this.sessionFactory.getCurrentSession().delete(order);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Order getOrder(Integer tableId) {
		try {
			String sentence = "select o.id from Order as o inner join o.tables as t inner join o.details as d where t.id = :tableId and d.state <>"
					+ State.FACTURADO.getId() + ")";
			Query query;
			query = this.sessionFactory.getCurrentSession().createQuery(sentence);
			query.setParameter("tableId", tableId);

			return this.get(((Integer) query.list().get(0)));
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
}
